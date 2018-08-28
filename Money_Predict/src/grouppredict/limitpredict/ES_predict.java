package grouppredict.limitpredict;

import grouppredict.hospital.Hosp_info;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年6月21日 下午5:24:49
 * @Version 1.0
 *
 */
public class ES_predict {
	
	private double alpha = Hosp_info.es_alpha;
	private double beta = Hosp_info.es_beta;
	
	
	public double es_predict(List<Double> data){
		double predict = 0.0;
		double s0 = data.get(0);
		ArrayList<Double> s = new ArrayList<Double>();
		s.add(s0);
		for(int i = 1; i < data.size(); i++){
			double si =  this.alpha * data.get(i) + (1 - this.alpha) * s.get(i - 1);
			s.add(si);
		}
		predict = this.alpha * data.get(data.size()- 1) + (1 - this.alpha) * s.get(s.size() - 1);
		return predict;
	}
	
	public double es_predict_2(List<Double> data){
		double predict = 0.0;
		List<Double> s = new ArrayList<Double>();
		List<Double> ss = new ArrayList<Double>();
		double s0 = data.get(0);
		s.add(s0);
		for(int i = 1; i < data.size(); i++){
			double si =  this.alpha * data.get(i) + (1 - this.alpha) * s.get(i - 1);
			s.add(si);
		}
		
		for(int i = 0; i < s.size()-1; i++){
			ss.add(this.alpha * s.get(i + 1) + (1 - this.alpha) * s.get(i));
		}
		double a = 2 * s.get(s.size() - 1) -ss.get(ss.size() - 1);
		double b = this.alpha / (1 - this.alpha) * (s.get(s.size() - 1) -ss.get(ss.size() - 1));
		predict = a + b;
		return predict;
	}
}
