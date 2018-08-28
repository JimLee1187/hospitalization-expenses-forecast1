package contextpredict.match_hiscontext;

import java.util.ArrayList;
import java.util.List;

import contextpredict.curvesegmentation.Caculate_similarity;
import contextpredict.curvesegmentation.Segmentation;
import contextpredict.hospital.PredictPointInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年6月14日 下午5:21:11
 * @Version 1.0
 *
 */
public class point_model_predict {
	
	public PredictPointInfo point_predict(List<Caculate_similarity> ca_simis){
		Serise_Predict_Method predictmethod  = new Serise_Predict_Method();
		//确定使用哪一个历史上下文
		Caculate_similarity max_match_cs = max_match(ca_simis);
		PredictPointInfo predictpoint = new PredictPointInfo();
		//取出相似度最大对象中的待测点上下文和历史上下文
		Segmentation historycontext = max_match_cs.getHis_segment();
		Segmentation testedPcontext = max_match_cs.getTest_segment();
		int index = max_match_cs.getIndex();
		ArrayList<Double> predict_point = new ArrayList<Double>();
		
		List<ArrayList<Double>> predict_factors = new ArrayList<ArrayList<Double>>();
		//做预测
		for(int i = 0; i < testedPcontext.getSegment().size(); i++){
			ArrayList<Double> list = new ArrayList<Double>();
			ArrayList<Double> his_data = historycontext.getSegment().get(i);
			ArrayList<Double> data = testedPcontext.getSegment().get(i);
			if(i == 0){
				//System.out.println("his:" + his_data.size() + " data: " + data.size() + " index:" + index);
				assert his_data.size() - data.size() - index >= 1 : "匹配的上下文无法做预测！";
				predict_point = predictmethod.diff_predict(his_data, data, index);
			}
			list = predictmethod.diff_predict(his_data, data, index);
			predict_factors.add(list);
		}
		//把信息保存到点预测对象中
		//最相似的上下文
		predictpoint.setHistorycontext(historycontext);
		//最相似的相似度
		predictpoint.setSimilarity(max_match_cs.getSimilarity());
		//待测点上下文
		predictpoint.setTestedPcontext(testedPcontext);
		//预测的费用
		predictpoint.setPredict_point(predict_point);
		//待测点上下文匹配到的所有相似上下文
		predictpoint.setPoint_match_hiscontext(ca_simis);
		//预测的费用和因素
		predictpoint.setPredict_factors(predict_factors);
		//index
		predictpoint.setIndex(index);
		return predictpoint;
	}
	
	/**
	 * 找最适合的上下文，使用距离最长的
	 * @Title: max_match
	 * @Description: 
	 * @param: @param ca_simis
	 * @param: @return   
	 * @return: Caculate_similarity   
	 * @throws
	 */
	public Caculate_similarity max_match(List<Caculate_similarity> ca_simis){
		Caculate_similarity max_match_cs = new Caculate_similarity();
		int max_size = 0;
		for(Caculate_similarity cs : ca_simis){
			if(cs.getTest_segment().getSegment().get(0).size() > max_size){
				max_match_cs = cs;
			}
		}
		return max_match_cs;
	}
}
