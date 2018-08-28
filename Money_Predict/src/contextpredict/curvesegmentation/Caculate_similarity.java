package contextpredict.curvesegmentation;

import java.util.ArrayList;
import java.util.List;

import contextpredict.dynamic_time_warping.DynamicTWarp;
import contextpredict.gray_correlation_analysis.Correlation;
import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年6月14日 上午9:34:27
 * @Version 1.0
 *
 */
public class Caculate_similarity {

	private Segmentation his_segment;
	private Segmentation test_segment;
	private double similarity;
	private int index;
	private double curve_weight = HospInfo.curve_weight;
	private int Normalized = HospInfo.Normalized;
	
	/**
	 * 去掉相似的上下文段
	 * @Title: remove_redundancy
	 * @Description: 
	 * @param: @param his_segment
	 * @param: @param test_segment
	 * @param: @param weights
	 * @param: @return   
	 * @return: double   
	 * @throws
	 */
	public double remove_redundancy(Segmentation his_segment, Segmentation test_segment, List<Double> weights){
		assert his_segment.getSegment().get(0).size() > 0 : "左边的分段长度为0！";
		assert test_segment.getSegment().get(0).size() > 0 : "右边的分段长度为0！";
		//如果his_segment的长度大于test_segment
		List<Segmentation> segments = new ArrayList<Segmentation>();
		Segmentation segment = new Segmentation();
		if(his_segment.getSegment().get(0).size() >= test_segment.getSegment().get(0).size()){
			segments = cutsegment(his_segment, test_segment.getSegment().get(0).size());
			segment = test_segment;
		}
		if(his_segment.getSegment().get(0).size() < test_segment.getSegment().get(0).size()){
			segments = cutsegment(test_segment, his_segment.getSegment().get(0).size());
			segment = his_segment;
		}
		
		double simi_max = 0.0;
		for(int i = 0; i < segments.size(); i++){
			double similar = cacula_similarity(segments.get(i), segment, weights);
			//System.out.println("similar: " + similar);
			if(similar > simi_max){
				this.similarity = similar;
				simi_max = similar;
				this.index = i;
			}
		}
		this.his_segment = his_segment;
		this.test_segment = test_segment;
		return simi_max;
	}
	
	/**
	 * 匹配历史上下文
	 * @Title: match_context
	 * @Description: 
	 * @param: @param his_segment
	 * @param: @param test_segment
	 * @param: @param weights   
	 * @return: void   
	 * @throws
	 */
	public void match_context(Segmentation his_segment, Segmentation test_segment, List<Double> weights){
		assert his_segment.getSegment().get(0).size() > test_segment.getSegment().get(0).size() 
		: "第一个list长度需大于第二个list的长度！";
		List<Segmentation> segments = new ArrayList<Segmentation>();
		Segmentation segment = new Segmentation();
		segments = cutsegment(his_segment, test_segment.getSegment().get(0).size());
		segment = test_segment;
		double simi_max = 0.0;
		for(int i = 0; i < segments.size() - 1; i++){
			double similar = cacula_similarity(segments.get(i), segment, weights);
			if(similar > simi_max){
				this.similarity = similar;
				this.index = i;
				simi_max = similar;
			}
			//System.out.println("simi_max: " + simi_max);
		}
		this.his_segment = his_segment;
		this.test_segment = test_segment;
	}
	/**
	 * 计算相似度
	 * @Title: cacula_similarity
	 * @Description: 
	 * @param: @param his_segment
	 * @param: @param test_segment
	 * @param: @param weights
	 * @param: @return   
	 * @return: double   
	 * @throws
	 */
	public double cacula_similarity(Segmentation his_segment, Segmentation test_segment, List<Double> weights){
		assert his_segment.getSegment().get(0).size() == test_segment.getSegment().get(0).size() 
				: "比对的两个分段不相等！";
		Correlation corr = new Correlation();
		double[] corr_arr = new double[his_segment.getSegment().size()];
		for(int i = 0; i < his_segment.getSegment().size(); i++){
			//相对应的曲线存入数组
			List<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
			list.add(his_segment.getSegment().get(i));
			list.add(test_segment.getSegment().get(i));
			//计算相似度,1表示初值法，2表示均值法，3表示区间法，4表示正则化
			//System.out.println("list: " + list);
			corr_arr[i] = corr.caculate_correlation(list, Normalized).get(0);
		}
		//因素的相似度
		double factor_simi = 0.0;
		for(int i = 1; i < corr_arr.length; i++){
			factor_simi += corr_arr[i] * weights.get(i - 1);
		}
		//factor_simi = factor_simi / (his_segment.getSegment().size() - 1);
//		System.out.println("factor_simi: " + factor_simi);
//		System.out.println("corr_arr[0]: " + corr_arr[0]);
		double similar = curve_weight * corr_arr[0] + (1 - curve_weight) * factor_simi;
		//如果相似度大于1
		if(similar > 1){
			System.out.println("factor_simi: " + factor_simi + " corr_arr[0]:"  + corr_arr[0]);
			System.out.println("curve_weight: " + curve_weight + " 1 - curve_weight: " + (1 - curve_weight));
			System.out.print("126curvesegmentation.Caculate_similarity: ");
			for(double d : corr_arr){
				System.out.print(d + " ");
			}
			System.out.println();
		}
		return similar;
	}
	
	public double cacula_similaritydtw(Segmentation his_segment, Segmentation test_segment, List<Double> weights){
		assert his_segment.getSegment().get(0).size() == test_segment.getSegment().get(0).size() 
				: "比对的两个分段不相等！";
		DynamicTWarp dtw = new DynamicTWarp();
		double[] corr_arr = new double[his_segment.getSegment().size()];
		for(int i = 0; i < his_segment.getSegment().size(); i++){
			//相对应的曲线存入数组
			List<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
			list.add(his_segment.getSegment().get(i));
			list.add(test_segment.getSegment().get(i));
			//计算相似度,1表示初值法，2表示均值法，3表示区间法，4表示正则化
			//System.out.println("list: " + list);
			corr_arr[i] = dtw.caculate_correlation(list, Normalized);
		}
		//因素的相似度
		double factor_simi = 0.0;
		for(int i = 1; i < corr_arr.length; i++){
			factor_simi += corr_arr[i] * weights.get(i - 1);
		}
		//factor_simi = factor_simi / (his_segment.getSegment().size() - 1);
//		System.out.println("factor_simi: " + factor_simi);
//		System.out.println("corr_arr[0]: " + corr_arr[0]);
		double similar = curve_weight * corr_arr[0] + (1 - curve_weight) * factor_simi;
		//如果相似度大于1
		if(similar > 1){
			System.out.println("factor_simi: " + factor_simi + " corr_arr[0]:"  + corr_arr[0]);
			System.out.println("curve_weight: " + curve_weight + " 1 - curve_weight: " + (1 - curve_weight));
			System.out.print("126curvesegmentation.Caculate_similarity: ");
			for(double d : corr_arr){
				System.out.print(d + " ");
			}
			System.out.println();
		}
		return similar;
	}
	
	//对上下文进行分段
	public static ArrayList<Segmentation> cutsegment(Segmentation segment, int size){
		ArrayList<Segmentation> segments = new ArrayList<Segmentation>();
		for(int i = 0; i <= segment.getSegment().get(0).size() - size; i++){
			//分段
			Segmentation segm = new Segmentation();
			List<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
			for(int p = 0; p < segment.getSegment().size(); p++){
				ArrayList<Double> list = new ArrayList<Double>();
				for(int j = i; j < size + i; j++){
					list.add(segment.getSegment().get(p).get(j));
				}
				data.add(list);
			}
			segm.setSegment(data);
			segments.add(segm);
		}
		return segments;
	}
	
	public Segmentation getHis_segment() {
		return his_segment;
	}

	public void setHis_segment(Segmentation his_segment) {
		this.his_segment = his_segment;
	}

	public Segmentation getTest_segment() {
		return test_segment;
	}

	public void setTest_segment(Segmentation test_segment) {
		this.test_segment = test_segment;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
