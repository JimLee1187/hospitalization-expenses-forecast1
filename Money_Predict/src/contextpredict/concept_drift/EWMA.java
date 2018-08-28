package contextpredict.concept_drift;

import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年6月11日 下午3:25:55
 * @Version 1.0
 *
 */
public class EWMA {
	public double[] ExpWeiMovAvg(List<Double> data){
		Feature_Extraction fe = new Feature_Extraction();
		double[] zt_sigma = new double[2];
		double zt = 0.0;
		if(data.size() == 1){
			zt = data.get(0);
		}
		double Lambda = 2 / (data.size() + 1);
		double sigma = 0.0;
		if(data.size() > 1){
			double variance = fe.caculate_variance(data);
			double variance_sigma = (Lambda / (2 - Lambda)) * (1 - Math.pow((1 - Lambda), 2 * data.size())) * variance;
			sigma = Math.sqrt(variance_sigma);
		}
		zt_sigma[0] = zt;
		zt_sigma[1] = sigma;
		return zt_sigma;
	}	
}
