package grouppredict.limitpredict;

import grouppredict.hospital.Hosp_info;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018��6��21�� ����4:33:47
 * @Version 1.0
 *
 */
public class hosp_group_predict {
	
	public void hosp_gro_predict(Hosp_info hosp){
		ArrayList<Integer> predict_rs_group = new ArrayList<Integer>();
		//������������
		int zrs = hosp.getPredict_zrs();
		//ÿ�����������
		//ʹ�����Իع�Ԥ��
		LimitPredict lp = new LimitPredict();
		predict_rs_group = lp.limitpredict(zrs, hosp.getRs_data());
		hosp.setPredict_rs_group(predict_rs_group);
	}
	
	public void hosp_gro_predict_rjhf(Hosp_info hosp){
		ArrayList<Double> predict_rjhf_group = new ArrayList<Double>();
		//ÿ��������˾���������
		List<ArrayList<Double>> group_rjhf_data = new ArrayList<ArrayList<Double>>();
		group_rjhf_data = hosp.getRjhf_data();
		//׼��Ԥ������
		Predict_Method pm = new Predict_Method();
		for(int i = 0; i < group_rjhf_data.size(); i++){
			ArrayList<Double> current_group = group_rjhf_data.get(i);
			double predict_value = pm.es_predict(current_group);
			predict_rjhf_group.add(predict_value);
		}
		hosp.setPredict_rjhf_group(predict_rjhf_group);
	}
}
