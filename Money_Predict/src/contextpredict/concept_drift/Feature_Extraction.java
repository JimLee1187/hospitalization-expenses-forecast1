package contextpredict.concept_drift;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年6月10日 下午5:26:21
 * @Version 1.0
 *
 */
public class Feature_Extraction {
	//计算自相关系数
	public double caculate_autocorrelation(List<Double> data, int k){
		//System.out.println(data);
		assert data.size() > k : "无法计算，数组长度少于" + k;
		double autocorrelation = 0.0;
		double avg = caculate_avg(data);
		double sum_lags = 0.0;
		double sum_total = 0.0;
		//System.out.println("avg:" + avg);
		for(int i = 0; i < data.size(); i++){
			if(i >= k){
				sum_lags += (data.get(i) - avg) * (data.get(i - k) - avg);
			}
			sum_total = Math.pow((data.get(i) - avg), 2);
		}
		//System.out.println("sum_lags: " + sum_lags + " sum_total:" + sum_total);
		autocorrelation = sum_lags / sum_total;
		return autocorrelation;
	}
	//计算偏相关系数
	public double caculate_partial_autocorrelation(List<Double> data, int k){
		double partial_autocorrelation = 0.0;
		double phi1 = this.caculate_autocorrelation(data, 1);
		
		return partial_autocorrelation;
	}
	//计算方差
	public double caculate_variance(List<Double> data){
		assert data.size() > 1 : "无法计算，数组长度小于2";
		double variance = 0.0;
		double avg = caculate_avg(data);
		double sum = 0.0;
		for(double d : data){
			sum += Math.pow((d - avg), 2);
		}
		variance = sum / data.size();
		double sd = Math.sqrt(variance);
		return sd;
	}
	//计算偏度系数
	public double caculate_skewness(List<Double> data){
		assert data.size() > 1 : "无法计算，数组长度小于2";
		double skewness = 0.0;
		double sd = Math.sqrt(caculate_variance(data));
		double avg = caculate_avg(data);
		double sum = 0.0;
		for(double d : data){
			sum += Math.pow((d - avg), 3) / Math.pow(sd, 3);
		}
		skewness = sum / (data.size());
		return skewness;
	}
	//计算峰度系数
	public double caculate_kurtosis(List<Double> data){
		assert data.size() > 1 : "无法计算，数组长度小于2";
		double kurtosis = 0.0;
		double sd = Math.sqrt(caculate_variance(data));
		double avg = caculate_avg(data);
		double sum = 0.0;
		for(double d : data){
			sum += Math.pow((d - avg), 4) / Math.pow(sd, 4);
		}
		kurtosis = sum / (data.size());
		return kurtosis;
	}
	//计算转折点率
	public double caculate_turningpointsrate(List<Double> data){
		double turningpointsrate = 0.0;
		return turningpointsrate;
	}
	//计算双相关
	public double caculate_bicorrelation(List<Double> data, int t){
		assert data.size() > 2 * t : "无法计算，数组长度小于2t";
		assert(t > 0) : "t不能小于1";
		double bicorrelation = 0.0;
		double sum = 0.0;
		int count = 0;
		for(int i = 0; (i + 2 * t) < data.size(); i++){
			sum += (data.get(i) + data.get(i + t) + data.get(i + 2 * t));
			count++;
		}
		bicorrelation = sum / count;
		return bicorrelation;
	}
	//计算互信息
	public double caculate_mutual_information(List<Double> data, int t){
		double mutual_information = 0.0;
		
		return mutual_information;
	}
	
	public static double caculate_avg(List<Double> data){
		double avg = 0.0;
		double sum = 0.0;
		for(double d : data){
			sum += d;
		}
		avg = sum / data.size();
		return avg;
	}
	
	public double[] caculate_features(List<Double> originaldata){
		List<Double> data = originaldata;
//		for(int i = 1; i < originaldata.size(); i++){
//			data.add(originaldata.get(i) - originaldata.get(i - 1));
//		}
		double[] features = new double[5];
		//计算自相关
		double autocorrelation = caculate_autocorrelation(data, 5);
		features[0] = autocorrelation;
		//计算方差
		double variance = caculate_variance(data);
		features[1] = variance;
		//计算偏度系数
		double skewness = caculate_skewness(data);
		features[2] = skewness;
		//计算峰度系数
		double kurtosis = caculate_kurtosis(data);
		features[3] = kurtosis;
		//计算双相关
		double bicorrelation = caculate_bicorrelation(data, 2);
		features[4] = bicorrelation;
		return features;
	}
}
