package grouppredict.limitpredict;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年6月21日 下午2:11:32
 * @Version 1.0
 *
 */
public class LinearRegression {
	
	//线性回归
	private double theta0 = 0.0 ;  //截距
    private double theta1 = 0.0 ;  //斜率
    private double alpha = 0.01 ;  //学习速率

    private int max_itea = 20000 ; //最大迭代步数
    
    
    public double getTheta0() {
		return theta0;
	}

	public void setTheta0(double theta0) {
		this.theta0 = theta0;
	}

	public double getTheta1() {
		return theta1;
	}

	public void setTheta1(double theta1) {
		this.theta1 = theta1;
	}

	public double predict(double x){
        return theta0+theta1*x ;
    }

    public double calc_error(double x, double y) {
        return predict(x)-y;
    }
    
    public void gradientDescient(List<Double> x_arr, List<Double> y_arr){
        double sum0 =0.0 ;
        double sum1 =0.0 ;

        for(int i = 0 ; i < x_arr.size() ;i++) {
            sum0 += calc_error(x_arr.get(i), y_arr.get(i)) ;
            sum1 += calc_error(x_arr.get(i), y_arr.get(i)) * x_arr.get(i);
        }

        this.theta0 = theta0 - alpha*sum0/x_arr.size(); 
        this.theta1 = theta1 - alpha*sum1/x_arr.size(); 

    }
    
    public void lineGre(List<Double> x_arr, List<Double> y_arr) {
        int itea = 0 ;
        while( itea< max_itea){
            gradientDescient(x_arr, y_arr);
            itea ++ ;
        }
    }
    
    public double predict_value(List<Double> y_arr){
    	List<Double> x_arr = new ArrayList<Double>();
    	for(int i = 1; i <= y_arr.size(); i++){
    		x_arr.add((double)i);
    	}
    	this.lineGre(x_arr, y_arr);
    	double predict_value = predict(x_arr.size() + 1);
    	return predict_value;
    }
}
