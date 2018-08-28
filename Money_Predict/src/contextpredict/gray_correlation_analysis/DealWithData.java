package contextpredict.gray_correlation_analysis;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年1月3日 下午2:46:12
 * @Version 1.0
 *
 */
public class DealWithData {
	
    public ArrayList<Double> list;
	
	public ArrayList<Double> chuzhifa(List<Double> list0){
		list = new ArrayList<Double>();
		double chuzhi = list0.get(0);
		//list.add(chuzhi);
		for(int i = 0; i < list0.size(); i++){
			list.add(list0.get(i)/chuzhi);
		}
		return list;
	}
	
	public ArrayList<Double> junzhifa(List<Double> list0){
		list = new ArrayList<Double>();
		double pingjunzhi = 0;
		double sum = 0;
		for(double d : list0){
			sum += d;
		}
		pingjunzhi = sum / list0.size();
		for(int i = 0; i < list0.size(); i++){
			list.add(list0.get(i)/pingjunzhi);
		}
		return list;
	}
	
	public ArrayList<Double> qujianfa(List<Double> list0){
		list = new ArrayList<Double>();
		double min = list0.get(0), max = list0.get(0);
		for(double d : list0){
			if(d > max){
				max = d;
			}
			if(d < min){
				min = d;
			}
		}
		for(int i = 0; i < list0.size(); i++){
			list.add((list0.get(i) - min)/(max - min));
		}
		return list;
	}
	
	public ArrayList<Double> regularization(List<Double> list0){
		//System.out.println("DealWithData.java:59 " + list0.size());
		list = new ArrayList<Double>();
		List<Double> list1 = new ArrayList<Double>();
		double mean = 0.0;
		double variance = 0.0;
		double sum = 0.0;
		for(double d : list0){
			sum += d;
		}
		mean = sum / list0.size();
		sum = 0.0;
		for(double d : list0){
			sum += Math.pow(d - mean, 2);
		}
		variance = sum/list0.size();
		//System.out.println(variance);
		for(int i = 0; i < list0.size(); i++){
			if(variance != 0){
				list1.add((list0.get(i) - mean)/variance);
				//System.out.println((list0.get(i) - mean)/variance);
			}else{
				list1.add(0.0);
			}		
		}
		
		double min = list1.get(0), max = list1.get(0);
		for(double d : list1){
			if(d > max){
				max = d;
			}
			if(d < min){
				min = d;
			}
		}
		for(int i = 0; i < list1.size(); i++){
			if(max - min != 0){
				list.add((list1.get(i) - min)/(max - min));
				//System.out.println((list1.get(i) - min)/(max - min));
			}else{
				list.add(0.0);
			}
			
		}	
		return list;
	}
}
