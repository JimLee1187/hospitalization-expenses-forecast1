package contextpredict.concept_drift;

import java.util.ArrayList;
import java.util.List;

import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年6月14日 下午2:27:05
 * @Version 1.0
 *
 */
public class Concept_drift_point {
	public static int drift_window = HospInfo.drift_window;
	
	public void detective_drift_point(HospInfo hosp){
		List<ArrayList<Double>> originaldata = hosp.getAdjust_originaldata();
		List<Double> weights = hosp.getWeights();
		// 进行差分
		List<ArrayList<Double>> predict_data_diff = diff(originaldata);
		// 归一化
		List<ArrayList<Double>> predict_data_diffnorma = normalized(predict_data_diff);
		// 提取原始数据
		List<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
		for (ArrayList<Double> factor : predict_data_diffnorma) {
			ArrayList<Double> list = new ArrayList<Double>();
			for (int i = 0; i < drift_window; i++) {
				list.add(factor.get(i));
			}
			data.add(list);
		}
		// 创建概念漂移检测对象
		Drift_Detection dd = new Drift_Detection(data, weights);
		for (int i = drift_window; i < predict_data_diffnorma.get(0).size(); i++) {
			ArrayList<Double> new_point = new ArrayList<Double>();
			for (int j = 0; j < predict_data_diffnorma.size(); j++) {
				new_point.add(predict_data_diffnorma.get(j).get(i));
			}
			dd.drift_detection(new_point);
		}
		List<Integer> drift_points = new ArrayList<Integer>();
		for(int i = 0; i < dd.getDrift_points().size(); i++){
			drift_points.add(dd.getDrift_points().get(i) + 1);
		}
		hosp.setDrift_points(drift_points);
	}

	// 差分
	public static ArrayList<ArrayList<Double>> diff(List<ArrayList<Double>> data) {
		assert data.size() > 0 : "没有数据！";
		ArrayList<ArrayList<Double>> data_diff = new ArrayList<ArrayList<Double>>();
		for (ArrayList<Double> list : data) {
			assert list.size() > 2 : "数据量太小，无法做差分！";
			ArrayList<Double> factor_diff = new ArrayList<Double>();
			for (int j = 1; j < list.size(); j++) {
				factor_diff.add(list.get(j) - list.get(j - 1));
			}
			data_diff.add(factor_diff);
		}
		return data_diff;
	}

	// 归一化
	public static ArrayList<ArrayList<Double>> normalized(
			List<ArrayList<Double>> data) {
		assert data.size() > 0 : "没有数据！";
		ArrayList<ArrayList<Double>> data_nor = new ArrayList<ArrayList<Double>>();
		for (ArrayList<Double> list : data) {
			assert list.size() > 2 : "数据量太小，无法做归一化！";
			Normalization norma = new Normalization();
			ArrayList<Double> factor_nor = norma.qujianfa(list);
			data_nor.add(factor_nor);
		}
		return data_nor;
	}
}
