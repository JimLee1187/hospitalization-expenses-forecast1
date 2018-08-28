package grouppredict.limitpredict;

import java.util.List;



/**
 *
 * @author  LiJing 
 * @date    2018年6月21日 上午11:49:54
 * @Version 1.0
 *
 */
public class Predict_Method {
	//线性回归
	public double lr_predict(List<Double> y_arr){
		LinearRegression lr = new LinearRegression();
		double pre_value = lr.predict_value(y_arr);
		int size = y_arr.size();
		double diff1 = pre_value - y_arr.get(size - 1);
		double diff2 = y_arr.get(size - 1) - y_arr.get(size - 2);
		pre_value = y_arr.get(size - 1) + (diff1 + diff2) / 2;
		return pre_value;
	}
	//指数平滑 
	public double es_predict(List<Double> y_arr){
		double pre_value = 0.0;
		if(y_arr.size() == 1){
			pre_value = y_arr.get(0);
		}
		if(y_arr.size() == 2){
			pre_value = y_arr.get(1);
		}
		if(y_arr.size() > 2){
			ES_predict es = new ES_predict();
		    pre_value = es.es_predict(y_arr);
			int size = y_arr.size();
			double diff1 = pre_value - y_arr.get(size - 1);
			double diff2 = y_arr.get(size - 1) - y_arr.get(size - 2);
			pre_value = y_arr.get(size - 1) + (diff1 + diff2) / 2;
		}
		return pre_value;
	}
}
