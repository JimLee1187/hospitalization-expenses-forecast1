package grouppredict.limitpredict;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年6月21日 上午11:49:19
 * @Version 1.0
 *
 */
public class LimitPredict {
	private ArrayList<Integer> predict_rs;
	private List<LinearRegression> lrs;

	public ArrayList<Integer> limitpredict(int zrs, List<ArrayList<Double>> rs_data){
		ArrayList<Integer> predict_rs = new ArrayList<Integer>();
    	//计算比例
    	ArrayList<Double> ratios = new ArrayList<Double>();
    	//存放2015年总人数
    	ArrayList<Double> rs_2015 = new ArrayList<Double>();
    	for(ArrayList<Double> list : rs_data){
    		rs_2015.add(list.get(list.size() - 1));
    	}
    	ratios = get_ratio(rs_2015);
    	double[] predict_arr = new double[rs_2015.size()];
    	//做预测
    	for(int i = 0; i < rs_data.size(); i++){
    		Predict_Method pm = new Predict_Method();
    		ArrayList<Double> current_group = rs_data.get(i);
    		if(current_group.size() == 1){
    			predict_arr[i] = current_group.get(0);
    		}
    		if(current_group.size() > 1){
    			predict_arr[i] = pm.lr_predict(current_group);
    		}
    	}
    	double pre_zrs = sum(predict_arr);
    	double diff = (double)zrs - pre_zrs;
    	assert predict_arr.length == ratios.size() : "无法进行补偿！";
    	for(int i = 0; i < predict_arr.length; i++){
    		if(predict_arr[i] + diff * ratios.get(i) >= 0){
        		predict_arr[i] += diff * ratios.get(i);
    		}
    	}
    	
    	for(int i = 0; i < predict_arr.length; i++){
    		predict_rs.add((int) Math.round(predict_arr[i]));
    	}
    	return predict_rs;
	}

	public static ArrayList<Double> get_ratio(ArrayList<Double> rs_2015){
		assert rs_2015.size() > 1 : "无法算比例！";
		ArrayList<Double> ratios = new ArrayList<Double>();
		double sum = 0.0;
		for(double d : rs_2015){
			sum += d;
		}
		for(double d : rs_2015){
			ratios.add(d / sum);
		}
		return ratios;
	}
	
	public static double sum(double[] predict_arr){
		double sum = 0.0;
		for(double d : predict_arr){
			sum += d;
		}
		return sum;
	}
	
	public ArrayList<Integer> getPredict_rs() {
		return predict_rs;
	}

	public void setPredict_rs(ArrayList<Integer> predict_rs) {
		this.predict_rs = predict_rs;
	}

	public List<LinearRegression> getLrs() {
		return lrs;
	}

	public void setLrs(List<LinearRegression> lrs) {
		this.lrs = lrs;
	}
}
