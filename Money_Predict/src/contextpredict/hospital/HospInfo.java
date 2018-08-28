package contextpredict.hospital;

import java.util.ArrayList;
import java.util.List;

import contextpredict.curvesegmentation.Segmentation;

public class HospInfo {
	
	private String yybm;
	private String rqlb;
	private List<String> factor_name;
	private List<ArrayList<Double>> originaldata; 
	private List<Double> true_data;
	private List<ArrayList<Double>> true_factors; 
	private List<double[]> seasonal_factors; 
	private List<ArrayList<Double>> adjust_originaldata;
	private List<Double> adjust_true_data;
	private List<Double> factor_corr;
	private List<Double> weights;
	private List<Integer> drift_points;
	private List<Segmentation> segments;
	private List<Double> adjust_predict_data;
	private List<Double> predict_data;
	private List<PredictPointInfo> Predict_Points_info;
	private List<ArrayList<Double>> predict_factors;
	private boolean have_simihiscontext = true;
	
	public static boolean predictonepoint = true;
	public static boolean sea_ajust_factor = true;
	public static int cycle = 12;
	public static double curve_weight = 0.5;
	public static double gray_corr_p = 0.5;
	public static double drift_threshold = 0.7;
	public static int drift_window = 6;
	public static double simi_threshold = 0.8;
	public static double match_simi_threshold = 0.4;
	public static int match_min_window = 4;
	public static int recent_cycle = 6;
	public static int Normalized = 4;
	public static int remove_year = 1;
	public static int factor_num = 8;
	public static int predict_year = 2015;
	
	public String getYybm() {
		return yybm;
	}
	public void setYybm(String yybm) {
		this.yybm = yybm;
	}
	public String getRqlb() {
		return rqlb;
	}
	public void setRqlb(String rqlb) {
		this.rqlb = rqlb;
	}
	public List<String> getFactor_name() {
		return factor_name;
	}
	public void setFactor_name(List<String> factor_name) {
		this.factor_name = factor_name;
	}
	public List<ArrayList<Double>> getOriginaldata() {
		return originaldata;
	}
	public void setOriginaldata(List<ArrayList<Double>> originaldata) {
		this.originaldata = originaldata;
	}
	public List<double[]> getSeasonal_factors() {
		return seasonal_factors;
	}
	public void setSeasonal_factors(List<double[]> seasonal_factors) {
		this.seasonal_factors = seasonal_factors;
	}
	public List<ArrayList<Double>> getAdjust_originaldata() {
		return adjust_originaldata;
	}
	public void setAdjust_originaldata(List<ArrayList<Double>> adjust_originaldata) {
		this.adjust_originaldata = adjust_originaldata;
	}
	public List<Double> getAdjust_true_data() {
		return adjust_true_data;
	}
	public void setAdjust_true_data(List<Double> adjust_true_data) {
		this.adjust_true_data = adjust_true_data;
	}
	public List<Double> getFactor_corr() {
		return factor_corr;
	}
	public void setFactor_corr(List<Double> factor_corr) {
		this.factor_corr = factor_corr;
	}
	public List<Double> getWeights() {
		return weights;
	}
	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}
	public List<Segmentation> getSegments() {
		return segments;
	}
	public void setSegments(List<Segmentation> segments) {
		this.segments = segments;
	}
	public List<Double> getAdjust_predict_data() {
		return adjust_predict_data;
	}
	public void setAdjust_predict_data(List<Double> adjust_predict_data) {
		this.adjust_predict_data = adjust_predict_data;
	}
	public List<Double> getPredict_data() {
		return predict_data;
	}
	public void setPredict_data(List<Double> predict_data) {
		this.predict_data = predict_data;
	}
	public List<Double> getTrue_data() {
		return true_data;
	}
	public void setTrue_data(List<Double> true_data) {
		this.true_data = true_data;
	}
	public List<PredictPointInfo> getPredict_Points_info() {
		return Predict_Points_info;
	}
	public void setPredict_Points_info(List<PredictPointInfo> Predict_Points_info) {
		this.Predict_Points_info = Predict_Points_info;
	}
	public List<Integer> getDrift_points() {
		return drift_points;
	}
	public void setDrift_points(List<Integer> drift_points) {
		this.drift_points = drift_points;
	}
	public List<ArrayList<Double>> getTrue_factors() {
		return true_factors;
	}
	public void setTrue_factors(List<ArrayList<Double>> true_factors) {
		this.true_factors = true_factors;
	}
	public List<ArrayList<Double>> getPredict_factors() {
		return predict_factors;
	}
	public void setPredict_factors(List<ArrayList<Double>> predict_factors) {
		this.predict_factors = predict_factors;
	}
	public boolean isHave_simihiscontext() {
		return have_simihiscontext;
	}
	public void setHave_simihiscontext(boolean have_simihiscontext) {
		this.have_simihiscontext = have_simihiscontext;
	}
	
}
