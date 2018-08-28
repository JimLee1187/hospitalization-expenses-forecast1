package contextpredict.match_hiscontext;

import java.util.ArrayList;
import java.util.List;

import contextpredict.hospital.HospInfo;

public class Serise_Predict_Method {
	private boolean predictonepoint = HospInfo.predictonepoint;
	//差值预测
	public ArrayList<Double> diff_predict(List<Double> his_data, List<Double> data, int index){
		assert his_data.size() - data.size() - index > 0 : "无法进行预测！";
		ArrayList<Double> predict_data = new ArrayList<Double>();
		double avg_diff = 0.0;
		double sum_diff = 0.0;
		for(int i = 0; i < data.size(); i++){
			sum_diff += his_data.get(i + index) - data.get(i);
		}
		//得到匹配的段平均差值
		avg_diff = sum_diff / data.size();
		int length = his_data.size();
		if(predictonepoint){
			length = index + data.size() + 1;
		}
		for(int i = index + data.size(); i < length; i++){
			predict_data.add(his_data.get(i) - avg_diff);
		}
		return predict_data;
	}
	
	//比例预测
	public ArrayList<Double> ratio_predict(List<Double> his_data, List<Double> data, int index){
		assert his_data.size() - data.size() - index > 0 : "无法进行预测！";
		ArrayList<Double> predict_data = new ArrayList<Double>();
		double avg_ratio = 0.0;
		double sum_ratio = 0.0;
		for(int i = 0; i < data.size(); i++){
			sum_ratio += his_data.get(i + index) / data.get(i);
		}
		//得到匹配的段平均差值
		avg_ratio = sum_ratio / data.size();
		int length = his_data.size();
		if(predictonepoint){
			length = index + data.size() + 1;
		}
		for(int i = index + data.size(); i < length; i++){
			predict_data.add(his_data.get(i) / avg_ratio);
		}
		return predict_data;
	}
	
	//使用指数平滑预测
	public ArrayList<Double> es_predict(List<Double> his_data, List<Double> data, int index){
		assert his_data.size() - data.size() - index > 0 : "无法进行预测！";
		ArrayList<Double> predict_data = new ArrayList<Double>();
		double[] ratio = new double[his_data.size() - index];
		double his_avg = 0.0;
		double sum = 0.0;
		for(int i = index; i < his_data.size(); i++){
			sum += his_data.get(i);
		}
		his_avg = sum / (his_data.size() - index);
		for(int i = index; i < his_data.size(); i++){
			ratio[i - index] = his_data.get(i) / his_avg;
		}
		double avg = 0.0;
		double[] avg_arr = new double[data.size()];
		for(int i = 0; i < data.size(); i++){
			avg_arr[i] = data.get(i) / ratio[i];
		}
		avg = avg(avg_arr);
		for(int i = data.size(); i < ratio.length; i++){
			predict_data.add(avg * ratio[i]);
		}
		return predict_data;
	}
	
	//使用AMRIMA预测
	public ArrayList<Double> AMRIMA_predict(List<Double> his_data, List<Double> data, int index){
		assert his_data.size() - data.size() - index > 0 : "无法进行预测！";
		ArrayList<Double> predict_data = new ArrayList<Double>();
		
		return predict_data;
	}
	
	public static double avg(List<Double> data){
		assert data.size() > 1 : "list中少于两个元素，无法求平均值！";
		double avg = 0.0;
		double sum = 0.0;
		for(double d : data){
			sum += d;
		}
		avg = sum / data.size();
		return avg;
	}
	
	public static double avg(double[] data){
		assert data.length > 1 : "list中少于两个元素，无法求平均值！";
		double avg = 0.0;
		double sum = 0.0;
		for(double d : data){
			sum += d;
		}
		avg = sum / data.length;
		return avg;
	}
}
