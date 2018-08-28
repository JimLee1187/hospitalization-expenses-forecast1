package contextpredict.concept_drift;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import contextpredict.curvesegmentation.CutSegmentation;
import contextpredict.hospital.Caculate_Weight;
import contextpredict.hospital.HospInfo;
import contextpredict.seasonal_adjustment_factor.Seasonal_Adjustment;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author  LiJing 
 * @date    2018年6月10日 下午6:59:59
 * @Version 1.0
 *
 */
public class Test {
	public static void main(String[] args) {
		String yybm = "500001";
		String rqlb = "zg";
		// 建立医院对象
		HospInfo hosp = new HospInfo();
		// 创建一个二维数组存放医院费用曲线数据
		ArrayList<ArrayList<Double>> content = new ArrayList<ArrayList<Double>>();
		// 存放医院名字
		ArrayList<String> cname = new ArrayList<String>();
		String path = "F:\\data\\HistoryContenPredict\\0625\\" + yybm + "data_" + rqlb + "factor.xls";
		File file = new File(path);
		content = readExcel(file, 0, cname);
		List<ArrayList<Double>> originaldata = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> true_data = new ArrayList<Double>();
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				for (int j = 0; j < 12; j++) {
					true_data.add(content.get(i).get(
							content.get(i).size() - 12 + j));
				}
			}
			originaldata.add(content.get(i));
		}
		List<ArrayList<Double>> true_factors = split(originaldata);
		// 医院编码、类别、原始数据、因素名称,真实费用数据,真实费用因素数据
		hosp.setYybm(yybm);
		hosp.setRqlb(rqlb);
		hosp.setOriginaldata(originaldata);
		hosp.setFactor_name(cname);
		hosp.setTrue_data(true_data);
		hosp.setTrue_factors(true_factors);
		// System.out.println("true: " + true_data);
		// 计算季节性调整因子
		Seasonal_Adjustment sea_adjust = new Seasonal_Adjustment();
		sea_adjust.seasonal_adjustment(hosp);
		List<Double> adjust_true_data = new ArrayList<Double>();
		for (int i = 0; i < hosp.getSeasonal_factors().get(0).length; i++) {
			double factor = hosp.getSeasonal_factors().get(0)[i];
			adjust_true_data.add(true_data.get(i) / factor);
		}
		hosp.setAdjust_true_data(adjust_true_data);
		 System.out.println("调整前的数据： "+ hosp.getOriginaldata().get(0));
		 System.out.println("调整后的数据： "+ hosp.getAdjust_originaldata().get(0));
		 System.out.print("调整因子： ");
		 for(double factor : hosp.getSeasonal_factors().get(0)){
		 System.out.print(factor + " ");
		 }
		 System.out.println();
		// 计算权重
		Caculate_Weight cw = new Caculate_Weight();
		cw.caculate_weight(hosp);
		// System.out.println("权重： " + hosp.getWeights());
		// 计算概念漂移点
		Concept_drift_point cdp = new Concept_drift_point();
		cdp.detective_drift_point(hosp);
		
		// System.out.println("概念漂移点： " + hosp.getDrift_points());
		// 分段
		CutSegmentation cutsegment = new CutSegmentation();
		cutsegment.cutsegmente(hosp);
		// System.out.println("allsegments: " + hosp.getSegments().size());
	}
	
	public static List<ArrayList<Double>> split(List<ArrayList<Double>> originaldata){
		List<ArrayList<Double>> true_factors = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < originaldata.size(); i++){
			ArrayList<Double> list = new ArrayList<Double>();
			for(int j = 0; j < 12; j++){
				list.add(originaldata.get(i).get(originaldata.get(i).size() - 12 + j));
				originaldata.get(i).remove(originaldata.get(i).size() - 12 + j);
			}
			true_factors.add(list);
		}
		return true_factors;
	}
	
	//差分
	public static ArrayList<ArrayList<Double>> diff(List<ArrayList<Double>> data){
		assert data.size() > 0 : "没有数据！";
		ArrayList<ArrayList<Double>> data_diff = new ArrayList<ArrayList<Double>>();
		for(ArrayList<Double> list : data){
			assert list.size() > 2 : "数据量太小，无法做差分！";
			ArrayList<Double> factor_diff = new ArrayList<Double>();
			for(int j = 1; j < list.size(); j++){
				factor_diff.add(list.get(j) - list.get(j - 1));
			}
			data_diff.add(factor_diff);
		}
		return data_diff;
	}
	//归一化
	public static ArrayList<ArrayList<Double>> normalized(List<ArrayList<Double>> data){
		assert data.size() > 0 : "没有数据！";
		ArrayList<ArrayList<Double>> data_nor = new ArrayList<ArrayList<Double>>();
		for(ArrayList<Double> list : data){
			assert list.size() > 2 : "数据量太小，无法做归一化！";
			Normalization norma = new Normalization();
			ArrayList<Double> factor_nor = norma.qujianfa(list);
			data_nor.add(factor_nor);
		}
		return data_nor;
	}
	
	 /**
	  * 
	  * @Title: readExcel
	  * @Description: 
	  * @param: @param file
	  * @param: @param index
	  * @param: @return   
	  * @return: List<ArrayList<Double>>   
	  * @throws
	  */
		public static  ArrayList<ArrayList<Double>> readExcel(File file,int index, ArrayList<String> name) {  
			ArrayList<ArrayList<Double>> list_arr = new ArrayList<ArrayList<Double>>();
			//System.out.println("路径：" + file);
	     try {  
	         // 创建输入流，读取Excel  
	         InputStream is = new FileInputStream(file.getAbsolutePath());  
	         // jxl提供的Workbook类  
	         Workbook wb = Workbook.getWorkbook(is);  
	         Sheet sheet = wb.getSheet(index);  
	        // System.out.println(sheet.getName());
	        // System.out.println("sheet.getColumns(): " + sheet.getColumns() + " sheet.getRows(): " + sheet.getRows());
	             // sheet.getRows()返回该页的总行数  
	             for (int i = 1; i < sheet.getColumns(); i ++) {  
	             	ArrayList<Double> list = new ArrayList<Double>();
	             	name.add(sheet.getCell(i,0).getContents());
	                 // sheet.getColumns()返回该页的总列数  
	                 for (int j = 1; j < sheet.getRows(); j ++) {    
	                	 if(sheet.getCell(i, j).getContents().isEmpty()){
	                		 break;
	                	 }else{
	                		 list.add(Double.parseDouble(sheet.getCell(i, j).getContents()));
	                	 }
	                 	
	                 	//System.out.println(Double.parseDouble(sheet.getCell(i, j).getContents()));
	                 } 
	                 list_arr.add(list);
	             }                
	     } catch (FileNotFoundException e) {  
	         e.printStackTrace();  
	     } catch (BiffException e) {  
	         e.printStackTrace();  
	     } catch (IOException e) {  
	         e.printStackTrace();  
	     }  
	     return list_arr;
	 } 
}
