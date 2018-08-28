package contextpredict.concept_drift;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年6月13日 上午11:49:10
 * @Version 1.0
 *
 */
public class Normalization {
	//归一化
	public ArrayList<Double> Zscore_standardization(List<Double> data){
		ArrayList<Double> normnized_data = new ArrayList<Double>();
		Feature_Extraction fe = new Feature_Extraction();
		//计算方差和均值
		double avg = Drift_Detection.caculate_avg(data);
		double var = fe.caculate_variance(data);
		for(double d : data){
			normnized_data.add((d - avg) / var);
		}
		return normnized_data;
	}
	
	public ArrayList<Double> qujianfa(List<Double> data){
		ArrayList<Double> normnized_data = new ArrayList<Double>();
		double min = data.get(0), max = data.get(0);
		for(double d : data){
			if(d > max){
				max = d;
			}
			if(d < min){
				min = d;
			}
		}
		for(int i = 0; i < data.size(); i++){
			normnized_data.add((data.get(i) - min)/(max - min));
		}
		return normnized_data;
	}
	
	public ArrayList<Double> mean_value_normnize(List<Double> data){
		ArrayList<Double> normnized_data = new ArrayList<Double>();
		//计算方差和均值
		double avg = Drift_Detection.caculate_avg(data);
		for(double d : data){
			normnized_data.add((d / avg));
		}
		return normnized_data;
	}
}
