package contextpredict.gray_correlation_analysis;

import java.util.ArrayList;
import java.util.List;

import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018��4��9�� ����11:29:14
 * @Version 1.0
 *
 */
public class Correlation {
	
	private static double p = HospInfo.gray_corr_p;
	private List<ArrayList<Double>> listed;
	/**
	 * 1��ʾ��ֵ����2��ʾ��ֵ����3��ʾ���䷨��4��ʾ����
	 * @param list
	 * @param index
	 * @param p
	 * @return
	 */
	public ArrayList<Double> caculate_correlation(List<ArrayList<Double>> list, int index){
		List<ArrayList<Double>> listed = new ArrayList<ArrayList<Double>>();
		DealWithData deal = new DealWithData();
		//���indexΪ1��ʹ�ó�ֵ��
		if(index == 1){
			for(ArrayList<Double> a : list){
				listed.add(deal.chuzhifa(a));
			}
		}
		
		//���indexΪ2��ʹ�þ�ֵ��
		if(index == 2){
			for(ArrayList<Double> a : list){
				listed.add(deal.junzhifa(a));
			}
		}

		//���indexΪ3��ʹ�����䷨
		if(index == 3){
			for(ArrayList<Double> a : list){
				listed.add(deal.qujianfa(a));
			}
		}
		
		//���indexΪ4��ʹ������
		if(index == 4){
			for(ArrayList<Double> a : list){
				listed.add(deal.regularization(a));
			}
		}

		//����Caculate_correlate���󣬼��������
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
