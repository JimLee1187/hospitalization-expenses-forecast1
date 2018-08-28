package contextpredict.match_hiscontext;

import java.util.ArrayList;
import java.util.List;

import contextpredict.curvesegmentation.Caculate_similarity;
import contextpredict.curvesegmentation.Segmentation;
import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018��6��14�� ����5:01:50
 * @Version 1.0
 *
 */
public class Search_SimilarHisContext {
	private int match_min_window = HospInfo.match_min_window;
	private double match_simi_threshold = HospInfo.match_simi_threshold;
	
	/**
	 * ƥ�������������������Ƶ���ʷ�����Ķ�
	 * @Title: search_simihiscontext
	 * @Description: 
	 * @param: @param hosp
	 * @param: @return   
	 * @return: List<Caculate_similarity>   
	 * @throws
	 */
	public List<Caculate_similarity> search_simihiscontext(HospInfo hosp){
		List<ArrayList<Double>> originaldata = hosp.getAdjust_originaldata();
		List<Segmentation> segments = hosp.getSegments();
		List<Double> weights = hosp.getWeights();
		//��ȡ��ʷ�����Ķ�����ķֶγ���
		int max_size = 0;
		for(Segmentation segment : segments){
			if(segment.getSegment().get(0).size() > max_size){
				max_size = segment.getSegment().get(0).size();
			}
		}
		//���������������
		List<Caculate_similarity> ca_simis = new ArrayList<Caculate_similarity>();
		for(int i = match_min_window; i < max_size - 1; i++){
			Segmentation test_segment = get_testedcontext(originaldata, i);
			//�����������
			double max_simi = 0.0;
			Caculate_similarity max_simics = new Caculate_similarity();
			for(Segmentation his_segment : segments){
				//�����������ƶȶ���
				Caculate_similarity cs = new Caculate_similarity();
				if(his_segment.getSegment().get(0).size() > test_segment.getSegment().get(0).size()){
					cs.match_context(his_segment, test_segment, weights);
				}
				//ȡ�������ƶ�
				if(cs.getSimilarity() > max_simi){
					max_simi = cs.getSimilarity();
					max_simics = cs;
				}
			}
//			System.out.print("60match_hiscontext.Search_SimilarHisContext: ");
//			System.out.println(max_simi);
			if(max_simics.getSimilarity() >  match_simi_threshold){
				//���浱ǰʱ�䴰�ڵ����ƶ�������ʷ������
				ca_simis.add(max_simics);
			}
		}
		return ca_simis;
	}
	
	//��ȡ�����������
	public static Segmentation get_testedcontext(List<ArrayList<Double>> originaldata, int window){
		Segmentation segment = new Segmentation();
		List<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < originaldata.size(); i++){
			ArrayList<Double> list = new ArrayList<Double>();
			for(int j = originaldata.get(0).size() - window; j < originaldata.get(0).size(); j++){
				list.add(originaldata.get(i).get(j));
			}
			data.add(list);
		}
		segment.setSegment(data);
		return segment;
	}
}
