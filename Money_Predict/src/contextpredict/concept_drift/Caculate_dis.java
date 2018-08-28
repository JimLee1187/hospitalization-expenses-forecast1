package contextpredict.concept_drift;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年6月13日 下午3:15:04
 * @Version 1.0
 *
 */
public class Caculate_dis {
		
	public Caculate_dis(){
		super();
	}
	
	public double drift_detection(List<Double> drift_data){
		Feature_Extraction fe = new Feature_Extraction();
		double dis = 0.0;
		ArrayList<Double> left_data = new ArrayList<Double>();
		ArrayList<Double> right_data = new ArrayList<Double>();
		for (int i = 1; i < drift_data.size(); i++) {
			left_data.add(drift_data.get(i - 1));
			right_data.add(drift_data.get(i));
		}
		// 提取特征
		double[] v0 = new double[5];
		double[] v1 = new double[5];
		v0 = fe.caculate_features(left_data);
		v1 = fe.caculate_features(right_data);
		// 计算距离
		dis = caculate_distance_cos(v0, v1);
		//System.out.println("dis: " + dis);
//		for(double d : v0){
//			System.out.print("v0:" + d);
//		}
//		System.out.println();
//		for(double d : v1){
//			System.out.print("v1:" + d);
//		}
		return dis;
	}
	
	/**
	 * 
	 * @Title: caculate_distance_cos
	 * @Description: cos距离计算
	 * @param: @param v0
	 * @param: @param vt
	 * @param: @return   
	 * @return: double   
	 * @throws
	 */
	public double caculate_distance_cos(double[] v0, double[] vt){
		double distance = 0.0;
		double molecular = 0.0;
		double denominator_a = 0.0;
		double denominator_b = 0.0;
		for(int i = 0; i < v0.length; i++){
			double a = v0[i];
			double b = vt[i];
			molecular += a * b;
			denominator_a += Math.pow(a, 2);
			denominator_b += Math.pow(b, 2);
		}
		distance = 1 - (molecular / (Math.sqrt(denominator_a) * Math.sqrt(denominator_b)));
		return distance;
	}
	
	/**
	 * 
	 * @Title: caculate_distance_pear
	 * @Description: 计算pearson距离
	 * @param: @param v0
	 * @param: @param vt
	 * @param: @return   
	 * @return: double   
	 * @throws
	 */
	public double caculate_distance_pear(double[] v0, double[] vt){
		double distance = 0.0;
		double molecular = 0.0;
		double denominator_a = 0.0;
		double denominator_b = 0.0;
		double avg_a = caculate_avg(v0);
		double avg_b = caculate_avg(vt);
		for(int i = 0; i < v0.length; i++){
			double a = v0[i];
			double b = vt[i];
			molecular += (a - avg_a) * (b - avg_b);
			denominator_a += Math.pow(a - avg_a, 2);
			denominator_b += Math.pow(b - avg_b, 2);
		}
		distance = 1 - (molecular /  (Math.sqrt(denominator_a) * Math.sqrt(denominator_b)));
		return distance;
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
}
