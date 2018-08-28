package grouppredict.hospital;


import grouppredict.limitpredict.LinearRegression;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  LiJing 
 * @date    2018年6月21日 上午8:15:53
 * @Version 1.0
 *
 */
public class Hosp_info {
	//医院的基本原始信息
		private String yybm;
		private String rqlb;
		private List<ArrayList<Double>> rs_original_data;
		private List<ArrayList<Double>> rjhf_original_data;
		private List<ArrayList<Double>> zje_original_data;
		private List<ArrayList<Double>> rs_data;
		private List<ArrayList<Double>> rjhf_data;
		private List<String> columns_name;
		//去掉空数据的列的剩下的列组名
		private List<String> columnname;
		//总人数和分组人数
		private ArrayList<Integer> zrs_group;
		private int zrs;
		private int predict_zrs;
		private LinearRegression lr_zrs;
		
		private ArrayList<Integer> rs_group;
		private ArrayList<Integer> predict_rs_group;
		private ArrayList<LinearRegression> lr_rs_group;
		//人均花费和分组人均花费
		private ArrayList<Double> rjhf_group;
		private ArrayList<Double> predict_rjhf_group;
		private ArrayList<LinearRegression> lr_rjhf_group;
		//总金额
		private double zje;
		private double predict_zje;
		private ArrayList<Double> zje_group;
		private ArrayList<Double> predict_zje_group;
		
		public static double es_alpha = 0.3;
		public static double es_beta = 0.3;
		
		public Hosp_info(String yybm, String rqlb){
			super();
			this.yybm = yybm;
			this.rqlb = rqlb;
		}
		
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
		
		public List<ArrayList<Double>> getRs_original_data() {
			return rs_original_data;
		}
		
		public void setRs_original_data(List<ArrayList<Double>> rs_original_data) {
			this.rs_original_data = rs_original_data;
		}
		
		public List<ArrayList<Double>> getRjhf_original_data() {
			return rjhf_original_data;
		}
		
		public void setRjhf_original_data(List<ArrayList<Double>> rjhf_original_data) {
			this.rjhf_original_data = rjhf_original_data;
		}
		
		public List<ArrayList<Double>> getZje_original_data() {
			return zje_original_data;
		}

		public void setZje_original_data(List<ArrayList<Double>> zje_original_data) {
			this.zje_original_data = zje_original_data;
		}

		public List<ArrayList<Double>> getRs_data() {
			return rs_data;
		}

		public void setRs_data(List<ArrayList<Double>> rs_data) {
			this.rs_data = rs_data;
		}

		public List<ArrayList<Double>> getRjhf_data() {
			return rjhf_data;
		}

		public void setRjhf_data(List<ArrayList<Double>> rjhf_data) {
			this.rjhf_data = rjhf_data;
		}

		public List<String> getColumns_name() {
			return columns_name;
		}
		public void setColumns_name(List<String> columns_name) {
			this.columns_name = columns_name;
		}
		public List<String> getColumnname() {
			return columnname;
		}
		public void setColumnname(List<String> columnname) {
			this.columnname = columnname;
		}
		public ArrayList<Integer> getZrs_group() {
			return zrs_group;
		}
		public void setZrs_group(ArrayList<Integer> zrs_group) {
			this.zrs_group = zrs_group;
		}

		public int getZrs() {
			return zrs;
		}
		public void setZrs(int zrs) {
			this.zrs = zrs;
		}
		public int getPredict_zrs() {
			return predict_zrs;
		}
		public void setPredict_zrs(int predict_zrs) {
			this.predict_zrs = predict_zrs;
		}
		public LinearRegression getLr_zrs() {
			return lr_zrs;
		}
		public void setLr_zrs(LinearRegression lr_zrs) {
			this.lr_zrs = lr_zrs;
		}
		public ArrayList<Integer> getRs_group() {
			return rs_group;
		}
		public void setRs_group(ArrayList<Integer> rs_group) {
			this.rs_group = rs_group;
		}
		public ArrayList<Integer> getPredict_rs_group() {
			return predict_rs_group;
		}
		public void setPredict_rs_group(ArrayList<Integer> predict_rs_group) {
			this.predict_rs_group = predict_rs_group;
		}
		public ArrayList<LinearRegression> getLr_rs_group() {
			return lr_rs_group;
		}
		public void setLr_rs_group(ArrayList<LinearRegression> lr_rs_group) {
			this.lr_rs_group = lr_rs_group;
		}
		public ArrayList<Double> getRjhf_group() {
			return rjhf_group;
		}
		public void setRjhf_group(ArrayList<Double> rjhf_group) {
			this.rjhf_group = rjhf_group;
		}
		public ArrayList<Double> getPredict_rjhf_group() {
			return predict_rjhf_group;
		}
		public void setPredict_rjhf_group(ArrayList<Double> predict_rjhf_group) {
			this.predict_rjhf_group = predict_rjhf_group;
		}
		public ArrayList<LinearRegression> getLr_rjhf_group() {
			return lr_rjhf_group;
		}
		public void setLr_rjhf_group(ArrayList<LinearRegression> lr_rjhf_group) {
			this.lr_rjhf_group = lr_rjhf_group;
		}
		public double getZje() {
			return zje;
		}
		public void setZje(double zje) {
			this.zje = zje;
		}
		public double getPredict_zje() {
			return predict_zje;
		}
		public void setPredict_zje(double predict_zje) {
			this.predict_zje = predict_zje;
		}

		public ArrayList<Double> getZje_group() {
			return zje_group;
		}

		public void setZje_group(ArrayList<Double> zje_group) {
			this.zje_group = zje_group;
		}

		public ArrayList<Double> getPredict_zje_group() {
			return predict_zje_group;
		}

		public void setPredict_zje_group(ArrayList<Double> predict_zje_group) {
			this.predict_zje_group = predict_zje_group;
		}
}
