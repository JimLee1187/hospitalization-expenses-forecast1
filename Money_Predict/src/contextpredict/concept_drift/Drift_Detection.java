package contextpredict.concept_drift;

import java.util.ArrayList;
import java.util.List;

import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年6月10日 下午10:06:45
 * @Version 1.0
 *
 */
public class Drift_Detection {
	
	public List<ArrayList<Double>> data;
	private ArrayList<Double> distances;
	private ArrayList<Integer> drift_points;
	private List<Double> weights;
	private double threshold = HospInfo.drift_threshold;
	private int window = HospInfo.drift_window;
	private double curve_weight = HospInfo.curve_weight;
	
	public Drift_Detection(){
		super();
	}
	
	public Drift_Detection(List<ArrayList<Double>> data, List<Double> weights){
		this.data = data;
		this.drift_points = new ArrayList<Integer>();
		this.weights = weights;
	}
	
	public void drift_detection(List<Double> new_poin) {
		Caculate_dis caculate_dis = new Caculate_dis();
		double dis = 0.0;
		for (int i = 0; i < new_poin.size(); i++) {
			data.get(i).add(new_poin.get(i));
		}
		//System.out.println("44data:" + data);
		ArrayList<Double> factor_dis = new ArrayList<Double>();
		for (int i = 0; i < data.size(); i++) {
			ArrayList<Double> factor_data = data.get(i);
			// 判断是否可以计算
			int drift_point = 0;
			if (drift_points.size() > 0) {
				drift_point = drift_points.get(drift_points.size() - 1);
			}
			//System.out.println(data.get(i).size()+ " " + drift_point + " " + window);
			if (data.get(i).size() - drift_point > window) {
				// 如果满足条件，数量大小大于window，计算左右两个数组
				ArrayList<Double> drift_data = new ArrayList<Double>();
				for (int j = factor_data.size() - window - 1; j < factor_data.size(); j++) {
					drift_data.add(factor_data.get(j));
				}
				// 计算距离
				//System.out.println("61drift_data: " + drift_data);
				double distance = caculate_dis.drift_detection(drift_data);
				// 如果距离大于阈值则
				//System.out.println("distance: " + distance);
				factor_dis.add(distance);
			}
		}
		//如果距离大于阈值
		if(factor_dis.size() > 1){
			//System.out.println("72factor_dis: " + factor_dis);
			dis = caculate_dis(factor_dis, curve_weight, weights);
			if(dis > threshold){
				drift_points.add(data.get(0).size() - 1);
			}
		}
	}

	//计算平均值
	public static double caculate_avg(double[] v){
		assert(v.length > 1) : "数组长度小于2";
		double avg = 0.0;
		double sum = 0.0;
		for(double d : v){
			sum += d;
		}
		avg = sum / v.length;
		return avg;
	}
	
	//计算平均值
	public static double caculate_dis(List<Double> v, double curve_weight, List<Double> weights){
		assert(v.size() > 1) : "数组长度小于2";
		double dis = 0.0;
		double factor_sum = 0.0;
		for(int i = 1; i < v.size(); i++){
			factor_sum += v.get(i) * weights.get(i - 1);
		}
		dis = curve_weight * v.get(0) + factor_sum * (1 - curve_weight);
		return dis;
	}
	
	public static double caculate_avg(List<Double> v){
		assert(v.size() > 0) : "数组长度小于1";
		double avg = 0.0;
		double sum = 0.0;
		for(double d : v){
			sum += d;
		}
		avg = sum / v.size();
		return avg;
	}

	public List<ArrayList<Double>> getData() {
		return data;
	}

	public void setData(List<ArrayList<Double>> data) {
		this.data = data;
	}

	public ArrayList<Double> getDistances() {
		return distances;
	}

	public void setDistances(ArrayList<Double> distances) {
		this.distances = distances;
	}

	public ArrayList<Integer> getDrift_points() {
		return drift_points;
	}

	public void setDrift_points(ArrayList<Integer> drift_points) {
		this.drift_points = drift_points;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public int getWindow() {
		return window;
	}

	public void setWindow(int window) {
		this.window = window;
	}
}
