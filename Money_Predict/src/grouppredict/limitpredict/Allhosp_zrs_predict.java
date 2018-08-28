package grouppredict.limitpredict;

import grouppredict.hospital.Hosp_info;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年6月21日 上午11:46:55
 * @Version 1.0
 *
 */
public class Allhosp_zrs_predict {

	public void allhosp_zrs_predict(List<Hosp_info> hosp_infos){
		//统计总人数
		List<Double> allhospzrs = new ArrayList<Double>();
		int true_zrs = 0;
		int pre_zrs = 0;
		int max_duration = 0;
		for(Hosp_info hosp : hosp_infos){
			if(hosp.getZrs_group().size() > max_duration){
				max_duration = hosp.getZrs_group().size();
			}
		}
		int[] allhospzrs_arr = new int[max_duration];
		for(Hosp_info hosp : hosp_infos){
			for(int i = 0; i < hosp.getZrs_group().size(); i++){
				allhospzrs_arr[max_duration - hosp.getZrs_group().size() + i] += hosp.getZrs_group().get(i);
			}
		}
		true_zrs= allhospzrs_arr[allhospzrs_arr.length - 1];
		for(int i = 1; i < allhospzrs_arr.length - 1; i++){
			allhospzrs.add((double)allhospzrs_arr[i]);
		}
		Predict_Method pm = new Predict_Method();
		pre_zrs = (int) Math.round(pm.lr_predict(allhospzrs));
		//System.out.println("pre_zrs: " + pre_zrs + " true_zrs: " + true_zrs);
		//System.out.println("总人数： " + allhospzrs);
		//所有医院的人数分布
		ArrayList<ArrayList<Double>> rs_data = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> rs_true = new ArrayList<Double>();
		ArrayList<Integer> rs_predict = new ArrayList<Integer>();
		for(int i = 0; i < hosp_infos.size(); i++){
			Hosp_info hosp = hosp_infos.get(i);
			ArrayList<Double> hosp_rs = new ArrayList<Double>();
			for(int j = 0; j < hosp.getZrs_group().size() - 1; j++){
				hosp_rs.add((double)hosp.getZrs_group().get(j));
			}
			rs_true.add((double)hosp.getZrs_group().get(hosp.getZrs_group().size() - 1));
			rs_data.add(hosp_rs);
		}
		
		LimitPredict lp = new LimitPredict();
		rs_predict = lp.limitpredict(pre_zrs, rs_data);
		assert rs_data.size() == hosp_infos.size() : "医院总人数预测的不对！";
		for(int i = 0 ; i < hosp_infos.size(); i++){
			hosp_infos.get(i).setPredict_zrs(rs_predict.get(i));
		}
	}
}
