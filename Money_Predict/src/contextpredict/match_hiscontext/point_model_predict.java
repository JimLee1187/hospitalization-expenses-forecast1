package contextpredict.match_hiscontext;

import java.util.ArrayList;
import java.util.List;

import contextpredict.curvesegmentation.Caculate_similarity;
import contextpredict.curvesegmentation.Segmentation;
import contextpredict.hospital.PredictPointInfo;

/**
 *
 * @author  LiJing 
 * @date    2018��6��14�� ����5:21:11
 * @Version 1.0
 *
 */
public class point_model_predict {
	
	public PredictPointInfo point_predict(List<Caculate_similarity> ca_simis){
		Serise_Predict_Method predictmethod  = new Serise_Predict_Method();
		//ȷ��ʹ����һ����ʷ������
		Caculate_similarity max_match_cs = max_match(ca_simis);
		PredictPointInfo predictpoint = new PredictPointInfo();
		//ȡ�����ƶ��������еĴ���������ĺ���ʷ������
		Segmentation historycontext = max_match_cs.getHis_segment();
		Segmentation testedPcontext = max_match_cs.getTest_segment();
		int index = max_match_cs.getIndex();
		ArrayList<Double> predict_point = new ArrayList<Double>();
		
		List<ArrayList<Double>> predict_factors = new ArrayList<ArrayList<Double>>();
		//��Ԥ��
		for(int i = 0; i < testedPcontext.getSegment().size(); i++){
			ArrayList<Double> list = new ArrayList<Double>();
			ArrayList<Double> his_data = historycontext.getSegment().get(i);
			ArrayList<Double> data = testedPcontext.getSegment().get(i);
			if(i == 0){
				//System.out.println("his:" + his_data.size() + " data: " + data.size() + " index:" + index);
				assert his_data.size() - data.size() - index >= 1 : "ƥ����������޷���Ԥ�⣡";
				predict_point = predictmethod.diff_predict(his_data, data, index);
			}
			list = predictmethod.diff_predict(his_data, data, index);
			predict_factors.add(list);
		}
		//����Ϣ���浽��Ԥ�������
		//�����Ƶ�������
		predictpoint.setHistorycontext(historycontext);
		//�����Ƶ����ƶ�
		predictpoint.setSimilarity(max_match_cs.getSimilarity());
		//�����������
		predictpoint.setTestedPcontext(testedPcontext);
		//Ԥ��ķ���
		predictpoint.setPredict_point(predict_point);
		//�����������ƥ�䵽����������������
		predictpoint.setPoint_match_hiscontext(ca_simis);
		//Ԥ��ķ��ú�����
		predictpoint.setPredict_factors(predict_factors);
		//index
		predictpoint.setIndex(index);
		return predictpoint;
	}
	
	/**
	 * �����ʺϵ������ģ�ʹ�þ������
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
