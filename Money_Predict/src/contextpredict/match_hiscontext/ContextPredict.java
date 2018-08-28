package contextpredict.match_hiscontext;

import java.util.ArrayList;
import java.util.List;

import contextpredict.curvesegmentation.Caculate_similarity;
import contextpredict.hospital.HospInfo;
import contextpredict.hospital.PredictPointInfo;

/**
 *
 * @author  LiJing 
 * @date    2018��6��15�� ����9:22:29
 * @Version 1.0
 *
 */
public class ContextPredict {

	private boolean sea_ajust_factor = HospInfo.sea_ajust_factor;
	
	public void Cost_Context_predict(HospInfo hosp){
		//Ԥ������
		List<Double> predict_data = new ArrayList<Double>();
		List<Double> adjust_predict_data = new ArrayList<Double>();
		List<PredictPointInfo> Predict_Points_info = new ArrayList<PredictPointInfo>();
		while(adjust_predict_data.size() < 12){
			Search_SimilarHisContext search_context = new Search_SimilarHisContext();
			point_model_predict pmodel_predict = new point_model_predict();
			//��ȡ���ƵĶ�
			List<Caculate_similarity> all_simi_hiscontext = search_context.search_simihiscontext(hosp);
			//��ģԤ��
			if(all_simi_hiscontext.isEmpty()){
				System.out.println(hosp.getYybm() + " " + hosp.getRqlb() + "û�����ƵĶ�");
				hosp.setHave_simihiscontext(false);
				break;
			}else{
				PredictPointInfo prepointinfo = pmodel_predict.point_predict(all_simi_hiscontext);
				//����originaldata
				//��ȡԤ��ĵ�
				List<ArrayList<Double>> data = prepointinfo.getPredict_factors();
				List<ArrayList<Double>> originaldata = hosp.getAdjust_originaldata();
				merge(originaldata, data);
				//����Ԥ������
				adjust_predict_data.addAll(prepointinfo.getPredict_point());
				//������Ԥ����Ϣ
				Predict_Points_info.add(prepointinfo);
			}
		}
		//��ԭԤ���ֵ
		if(sea_ajust_factor){
			if(adjust_predict_data.size() >= 12){
				for(int i = 0; i < hosp.getSeasonal_factors().get(0).length; i++){
					double factor = hosp.getSeasonal_factors().get(0)[i];
					predict_data.add(factor * adjust_predict_data.get(i));
				}
			}
		}else{
			if(adjust_predict_data.size() >= 12){
				predict_data = adjust_predict_data;
			}
		}

		hosp.setAdjust_predict_data(adjust_predict_data);
		hosp.setPredict_data(predict_data);
		hosp.setPredict_Points_info(Predict_Points_info);
	}
	
	public static void merge(List<ArrayList<Double>> originaldata, List<ArrayList<Double>> data){
		for(int i = 0; i < originaldata.size(); i++){
			for(int j = 0; j < data.get(i).size(); j++){
				originaldata.get(i).add(data.get(i).get(j));
			}
		}
	}
}
