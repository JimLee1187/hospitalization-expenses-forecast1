package contextpredict.gray_correlation_analysis;

import java.util.ArrayList;
import java.util.List;

import contextpredict.dynamic_time_warping.DynamicTWarp;

/**
 *
 * @author  LiJing 
 * @date    2018年6月20日 上午11:22:31
 * @Version 1.0
 *
 */
public class Gray_corr_test {
	public static void main(String[] args) {
		Correlation corr = new Correlation();
		DynamicTWarp dtw = new DynamicTWarp();
		//double[] arr1 = {1.4120160163541, 1.47162602699673, 1.69033226371337, 1.38012668899698, 1.58118749961532, 1.42911618270579};
		//double[] arr2 = {1.48059627286493, 1.38471159521268, 1.44809803794298, 1.48431204935358, 1.55082290534687, 1.51301995578784};
		double[] arr1 = {1.07505493244098, 1.22491201542606, 1.12692297166062, 1.38225270028013, 1.02490485666409, 1.26441669349581};
		double[] arr2 = {1.55082290534687, 1.51301995578784, 1.52218654828711, 1.85137542732303, 1.50374860267044, 1.7601413828703};
		ArrayList<Double> list1 = new ArrayList<Double>();
		ArrayList<Double> list2 = new ArrayList<Double>();
		List<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < arr1.length; i++){
			list1.add(arr1[i]);
			list2.add(arr2[i]);
		}
		list.add(list1);
		list.add(list2);
		double dtw_corr = dtw.caculate_correlation(list, 3);
		double correlate = corr.caculate_correlation(list, 3).get(0);
		System.out.println("correlate: " + correlate);
		System.out.println("dtw_corr: " + dtw_corr);
		for(ArrayList<Double> d : corr.getListed()){
			System.out.println(d);
		}
	}
}
