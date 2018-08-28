package contextpredict.seasonal_adjustment_factor;

import java.util.ArrayList;
import java.util.List;

import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年6月15日 下午4:10:54
 * @Version 1.0
 *
 */
public class Seasonal_Adjustment {
	
	public void seasonal_adjustment(HospInfo hosp){
		//需要保存的数据
		List<double[]> seasonal_factors = new ArrayList<double[]>(); 
	    List<ArrayList<Double>> adjust_originaldata = new ArrayList<ArrayList<Double>>();
	    //需要调整的数据
	    List<ArrayList<Double>> originaldata = hosp.getOriginaldata();
	    //做季节性调整，保存所有季节调整对象
	    List<Caculate_Seasonal_Factor> scfs = new  ArrayList<Caculate_Seasonal_Factor>();
		for(int i = 0; i < originaldata.size(); i++){
			Caculate_Seasonal_Factor csf = new Caculate_Seasonal_Factor();
			csf.caculate_sea_factor(originaldata.get(i));
			scfs.add(csf);
			seasonal_factors.add(csf.getSeasonal_factor());
			adjust_originaldata.add((ArrayList<Double>)csf.getAdjust_data());
		}
		//把季节调整因子和调整后的数据保存到hosp对象
		hosp.setSeasonal_factors(seasonal_factors);
		hosp.setAdjust_originaldata(adjust_originaldata);
	}
}
