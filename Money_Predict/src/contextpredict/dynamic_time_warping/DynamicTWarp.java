package contextpredict.dynamic_time_warping;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018��4��16�� ����1:40:12
 * @Version 1.0
 *
 */
public class DynamicTWarp {
	private List<ArrayList<Double>> listed;

	public double caculate_correlation(List<ArrayList<Double>> list, int index){
		double corr = 0.0;
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
		double[] arr0 = new double[listed.get(0).size()];
		double[] arr1 = new double[listed.get(1).size()];
		for(int i = 0; i < listed.get(0).size(); i++){
			arr0[i] =  listed.get(0).get(i);
		}
		for(int i = 0; i < listed.get(1).size(); i++){
			arr1[i] =  listed.get(1).get(i);
		}
		
		Dtw dtw = new Dtw();
		double dis = dtw.getDistance(arr0, arr1);
		corr = 1/(1 + dis);
		this.listed = listed;
		return corr;
	}

	public List<ArrayList<Double>> getListed() {
		return listed;
	}

	public void setListed(List<ArrayList<Double>> listed) {
		this.listed = listed;
	}
}
