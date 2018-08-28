package contextpredict.seasonal_adjustment_factor;

import java.util.ArrayList;
import java.util.List;

import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年6月15日 下午4:27:48
 * @Version 1.0
 *
 */
public class Caculate_Seasonal_Factor {
	
	private double[] seasonal_factor;
	private List<Double> Adjust_data;
	private int cycle = HospInfo.cycle;
	private int recent_cycle = HospInfo.recent_cycle;
	private int remove_year = HospInfo.remove_year;
	
	public void caculate_sea_factor(List<Double> list){
		double[] seasonal_factor = new double[cycle];
		List<Double> Adjust_data = new ArrayList<Double>();
		int count = 0;
		List<Double> data = new ArrayList<Double>();
		while(list.size() - recent_cycle * cycle < 0){
			recent_cycle--;
		}
		for(int i = list.size() - recent_cycle * cycle; i < list.size(); i++){
			data.add(list.get(i));
		}
		//计算调整因子
		while (data.size() - (count + 1) * 12 >= cycle * remove_year) {
			for (int i = 0; i < 12; i++) {
				seasonal_factor[i] += data.get(data.size() - 12 + i - count * 12);
			}
			count++;
		}
		for(int i = 0; i < seasonal_factor.length; i++){
			seasonal_factor[i] = seasonal_factor[i] / count;
		}
		//使用调整因子调整数据序列
		for(int i = 0; i < data.size(); i++){
			int index = i % 12;
			Adjust_data.add(data.get(i) / seasonal_factor[index]);
		}
		this.seasonal_factor = seasonal_factor;
		this.Adjust_data = Adjust_data;
	}

	public double[] getSeasonal_factor() {
		return seasonal_factor;
	}

	public void setSeasonal_factor(double[] seasonal_factor) {
		this.seasonal_factor = seasonal_factor;
	}

	public List<Double> getAdjust_data() {
		return Adjust_data;
	}

	public void setAdjust_data(List<Double> adjust_data) {
		Adjust_data = adjust_data;
	}
}
