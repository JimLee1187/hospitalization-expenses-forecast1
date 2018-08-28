package contextpredict.gray_correlation_analysis;

import java.util.ArrayList;
import java.util.List;

import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年4月9日 上午11:29:14
 * @Version 1.0
 *
 */
public class Correlation {
	
	private static double p = HospInfo.gray_corr_p;
	private List<ArrayList<Double>> listed;
	/**
	 * 1表示初值法，2表示均值法，3表示区间法，4表示正则化
	 * @param list
	 * @param index
	 * @param p
	 * @return
	 */
	public ArrayList<Double> caculate_correlation(List<ArrayList<Double>> list, int index){
		List<ArrayList<Double>> listed = new ArrayList<ArrayList<Double>>();
		DealWithData deal = new DealWithData();
		//如果index为1，使用初值法
		if(index == 1){
			for(ArrayList<Double> a : list){
				listed.add(deal.chuzhifa(a));
			}
		}
		
		//如果index为2，使用均值法
		if(index == 2){
			for(ArrayList<Double> a : list){
				listed.add(deal.junzhifa(a));
			}
		}

		//如果index为3，使用区间法
		if(index == 3){
			for(ArrayList<Double> a : list){
				listed.add(deal.qujianfa(a));
			}
		}
		
		//如果index为4，使用正则化
		if(index == 4){
			for(ArrayList<Double> a : list){
				listed.add(deal.regularization(a));
			}
		}

		//创建Caculate_correlate对象，计算关联性
		Caculate_correlate cc = new Caculate_correlate();
		ArrayList<Double> caculate = cc.caculate(listed, p);
		this.listed = listed;
		return caculate;
	}
	public List<ArrayList<Double>> getListed() {
		return listed;
	}
	public void setListed(List<ArrayList<Double>> listed) {
		this.listed = listed;
	}
}
