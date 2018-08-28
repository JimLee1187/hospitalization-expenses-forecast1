package contextpredict.hospital;

import java.util.ArrayList;
import java.util.List;

import contextpredict.gray_correlation_analysis.Correlation;

public class Caculate_Weight {
	
	public void caculate_weight(HospInfo hosp){
		List<ArrayList<Double>> data = hosp.getAdjust_originaldata();
		ArrayList<Double> weights = new ArrayList<Double>();
		ArrayList<Double> factor_corr = new ArrayList<Double>();
		Correlation corr = new Correlation();
		//1表示初值法，2表示均值法，3表示区间法，4表示正则化
		//System.out.println("contextpredict.hospital.Caculate_Weight.16: " + data.get(0).size());
		factor_corr = corr.caculate_correlation(data, 4); 
		weights = cacu_weight(factor_corr);
//		System.out.println("factor_corr : "+ factor_corr);
//		System.out.println("weights : "+ weights);
		hosp.setFactor_corr(factor_corr);
		hosp.setWeights(weights);
	}
	
	public static ArrayList<Double> cacu_weight(List<Double> weights){
		ArrayList<Double> weights1 = new ArrayList<Double>();
		double sum_weight = 0;
		for(double d : weights){
			sum_weight += d;
		}
		for(double d : weights){
			weights1.add(d/sum_weight);
		}
		return weights1;
	}
}
