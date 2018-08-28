package contextpredict.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import contextpredict.curvesegmentation.Caculate_similarity;
import contextpredict.curvesegmentation.Segmentation;

public class PredictPointInfo {
	private double similarity;
	private int index;
	private Segmentation testedPcontext;
	private Segmentation historycontext;
	private List<Double> predict_point;
	private List<Caculate_similarity> Point_match_hiscontext;
	private List<ArrayList<Double>> predict_factors;
	
	public double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Segmentation getTestedPcontext() {
		return testedPcontext;
	}
	public void setTestedPcontext(Segmentation testedPcontext) {
		this.testedPcontext = testedPcontext;
	}
	public Segmentation getHistorycontext() {
		return historycontext;
	}
	public void setHistorycontext(Segmentation historycontext) {
		this.historycontext = historycontext;
	}
	public List<Double> getPredict_point() {
		return predict_point;
	}
	public void setPredict_point(List<Double> predict_point) {
		this.predict_point = predict_point;
	}
	public List<Caculate_similarity> getPoint_match_hiscontext() {
		return Point_match_hiscontext;
	}
	public void setPoint_match_hiscontext(
			List<Caculate_similarity> point_match_hiscontext) {
		Point_match_hiscontext = point_match_hiscontext;
	}
	public List<ArrayList<Double>> getPredict_factors() {
		return predict_factors;
	}
	public void setPredict_factors(List<ArrayList<Double>> predict_factors) {
		this.predict_factors = predict_factors;
	}
}
