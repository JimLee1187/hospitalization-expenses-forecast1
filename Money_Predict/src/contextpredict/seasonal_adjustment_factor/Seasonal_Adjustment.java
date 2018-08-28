package contextpredict.seasonal_adjustment_factor;

import java.util.ArrayList;
import java.util.List;

import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018��6��15�� ����4:10:54
 * @Version 1.0
 *
 */
public class Seasonal_Adjustment {
	
	public void seasonal_adjustment(HospInfo hosp){
		//��Ҫ���������
		List<double[]> seasonal_factors = new ArrayList<double[]>(); 
	    List<ArrayList<Double>> adjust_originaldata = new ArrayList<ArrayList<Double>>();
	    //��Ҫ����������
	    List<ArrayList<Double>> originaldata = hosp.getOriginaldata();
	    //�������Ե������������м��ڵ�������
	    List<Caculate_Seasonal_Factor> scfs = new  ArrayList<Caculate_Seasonal_Factor>();
		for(int i = 0; i < originaldata.size(); i++){
			Caculate_Seasonal_Factor csf = new Caculate_Seasonal_Factor();
			csf.caculate_sea_factor(originaldata.get(i));
			scfs.add(csf);
			seasonal_factors.add(csf.getSeasonal_factor());
			adjust_originaldata.add((ArrayList<Double>)csf.getAdjust_data());
		}
		//�Ѽ��ڵ������Ӻ͵���������ݱ��浽hosp����
		hosp.setSeasonal_factors(seasonal_factors);
		hosp.setAdjust_originaldata(adjust_originaldata);
	}
}
