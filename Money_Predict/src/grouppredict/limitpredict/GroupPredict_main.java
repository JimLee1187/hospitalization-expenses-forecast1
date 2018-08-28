package grouppredict.limitpredict;

import grouppredict.hospital.Hosp_info;
import grouppredict.hospital.HospstoHosp_infos;

import java.util.ArrayList;
import java.util.List;

import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年6月23日 下午2:58:52
 * @Version 1.0
 *
 */
public class GroupPredict_main {
	
	public ArrayList<Hosp_info> grouppredict(List<HospInfo> hosps){
		ArrayList<Hosp_info> hosp_infos = new ArrayList<Hosp_info>();
		HospstoHosp_infos hosptohosp_info = new HospstoHosp_infos();
		hosp_infos = hosptohosp_info.hospstohosp_infos(hosps);
		Allhosp_zrs_predict azp = new Allhosp_zrs_predict();
		azp.allhosp_zrs_predict(hosp_infos);
		for(Hosp_info hosp : hosp_infos){
			hosp_group_predict hgp = new hosp_group_predict();
			hgp.hosp_gro_predict(hosp);
			hgp.hosp_gro_predict_rjhf(hosp);
			//计算分组总金额预测值
			ArrayList<Double> predict_zje_group = new ArrayList<Double>();
			for(int i = 0; i < hosp.getPredict_rs_group().size(); i++){
				predict_zje_group.add(hosp.getPredict_rs_group().get(i) * hosp.getPredict_rjhf_group().get(i));
			}
			hosp.setPredict_zje_group(predict_zje_group);
			hosp.setPredict_zje(sum(predict_zje_group));
		}
		return hosp_infos;
	}
	
	public static double sum(List<Double> data){
		double sum = 0.0;
		for(double d : data){
			sum += d;
		}
		return sum;
	}
}
