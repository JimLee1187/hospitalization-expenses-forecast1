package grouppredict.limitpredict;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年6月21日 下午2:40:46
 * @Version 1.0
 *
 */
public class Test {
	public static void main(String[] args) {
		double[] y_ar = {8268, 8824, 11157, 14670, 18444, 25155};
		Predict_Method pm = new Predict_Method();
		List<Double> y_arr = new ArrayList<Double>();
		for(double d : y_ar){
			y_arr.add(d);
		}
		double predict = pm.lr_predict(y_arr);
		System.out.println(predict);
	}
}
