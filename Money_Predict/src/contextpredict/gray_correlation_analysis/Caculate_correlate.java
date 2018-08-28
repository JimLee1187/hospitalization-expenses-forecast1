package contextpredict.gray_correlation_analysis;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author  LiJing 
 * @date    2018年1月3日 下午2:56:03
 * @Version 1.0
 *
 */
public class Caculate_correlate {
	

	public ArrayList<Double> caculate(List<ArrayList<Double>> listed, double p){
		List<ArrayList<Double>> Absolute_Difference = new ArrayList<ArrayList<Double>>();
		double min = Math.abs(listed.get(0).get(0) - listed.get(1).get(0));
		double max = min;
		for(int k = 1; k < listed.size(); k++){
			ArrayList<Double> li = new ArrayList<Double>();
				for(int i = 0; i < listed.get(k).size(); i++){
						double deta = Math.abs(listed.get(k).get(i)-listed.get(0).get(i));
								if(min > deta){
									min = deta;
								}
								if(max < deta){
									max = deta;
								}
						li.add(deta);
				}
				Absolute_Difference.add(li);
		}
		//System.out.println("max: " + max + "  " + "min: " + min);
		
		//计算关联系数，得到一个关联系数矩阵
		List<ArrayList<Double>> correlatematrix = new ArrayList<ArrayList<Double>>();
		for(ArrayList<Double> a : Absolute_Difference){
			ArrayList<Double> correlate = new ArrayList<Double>();
				for(int i = 0; i < a.size(); i++){
					double corr = (min + p*max)/(a.get(i) + p*max);
					if(Double.isNaN(corr)){
						correlate.add((double)0);
					}
					else{
					correlate.add(corr);
					}
			}
				correlatematrix.add(correlate);
		}

		
		//计算关联度
		ArrayList<Double> correlate_arr = new ArrayList<Double>();
		for(ArrayList<Double> a : correlatematrix){
			double sum = 0;
			for(int i = 0; i < a.size(); i++){
				sum += a.get(i);
			}
			correlate_arr.add(sum/a.size());
		}
		return correlate_arr;
	}
}
