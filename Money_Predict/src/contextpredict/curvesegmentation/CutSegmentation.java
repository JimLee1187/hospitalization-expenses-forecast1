package contextpredict.curvesegmentation;

import java.util.ArrayList;
import java.util.List;

import contextpredict.hospital.HospInfo;


public class CutSegmentation {
	private double simi_threshold  = HospInfo.simi_threshold;
	private int match_min_window = HospInfo.match_min_window;
	
	public ArrayList<Segmentation> cutsegmente(HospInfo hosp){
		//需要用到的数据，原始数据和权重、概念漂移点
		List<ArrayList<Double>> originaldata = hosp.getAdjust_originaldata();
		List<Double> weights = hosp.getWeights();
		List<Integer> drift_points = hosp.getDrift_points();
		//System.out.println("drift_points_size: " + drift_points.size());
		//创建存储所有分段的list
		ArrayList<Segmentation> allsegments = new ArrayList<Segmentation>();
		for(int i = 0; i <= drift_points.size(); i++){
			Segmentation segment = new Segmentation();
			List<ArrayList<Double>> seg = new ArrayList<ArrayList<Double>>();
			if(i == 0){
				for(int p = 0; p < originaldata.size(); p++){
					ArrayList<Double> factor = new ArrayList<Double>();
					for(int j = 0; j < drift_points.get(i); j++){
						factor.add(originaldata.get(p).get(j));
					}
					seg.add(factor);
				}
			}
			if(i > 0 && i < drift_points.size()){
				for(int p = 0; p < originaldata.size(); p++){
					ArrayList<Double> factor = new ArrayList<Double>();
					for(int j = drift_points.get(i - 1); j < drift_points.get(i); j++){
						factor.add(originaldata.get(p).get(j));
					}
					seg.add(factor);
				}
			}
			if(i == (drift_points.size())){
				for(int p = 0; p < originaldata.size(); p++){
					ArrayList<Double> factor = new ArrayList<Double>();
					for(int j = drift_points.get(i - 1); j < originaldata.get(0).size(); j++){
						factor.add(originaldata.get(p).get(j));
					}
					seg.add(factor);
				}
			}
			segment.setSegment(seg);
			if(segment.getSegment().get(0).size() > match_min_window){
				allsegments.add(segment);
			}
		}
//		System.out.println("allsegments: " + allsegments.size());
//		for(Segmentation se : allsegments){
//			System.out.println(se.getSegment().get(0));
//		}
		//比较相似度
		Caculate_similarity caculate_similar = new Caculate_similarity();
		for (int i = 0; i < allsegments.size() - 1; i++) {
			Segmentation segment = allsegments.get(i);
			for (int j = i + 1; j < allsegments.size(); j++) {
				Segmentation segment_compar = allsegments.get(j);
				double simi = caculate_similar.remove_redundancy(segment, segment_compar, weights);
				//System.out.println("simi" + caculate_similar.getSimilarity());
				if (simi >= simi_threshold) {
					if (segment.getSegment().size() >= segment_compar.getSegment().size()) {
						allsegments.remove(j);
					}
					if (segment.getSegment().size() < segment_compar.getSegment().size()) {
						allsegments.remove(i);
						i =0;
						break;
					}
				}
			}
		}
		hosp.setSegments(allsegments);
		return allsegments;
	}
}
