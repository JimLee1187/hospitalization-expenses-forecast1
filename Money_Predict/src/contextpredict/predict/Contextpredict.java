package contextpredict.predict;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import contextpredict.concept_drift.Concept_drift_point;
import contextpredict.concept_drift.Normalization;
import contextpredict.curvesegmentation.CutSegmentation;
import contextpredict.hospital.Caculate_Weight;
import contextpredict.hospital.HospInfo;
import contextpredict.match_hiscontext.ContextPredict;
import contextpredict.seasonal_adjustment_factor.Seasonal_Adjustment;

/**
 *
 * @author  LiJing 
 * @date    2018��6��15�� ����9:25:26
 * @Version 1.0
 *
 */
public class Contextpredict {
	/**
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * 
	 * @Title: contextpredict
	 * @Description: 
	 * @param: @param yybm
	 * @param: @param rqlb   
	 * @return: void   
	 * @throws
	 */
	private int predict_year = HospInfo.predict_year;
	private int factor_num = HospInfo.factor_num;
	private boolean sea_ajust_factor = HospInfo.sea_ajust_factor;
	
	public HospInfo contextpredict(String yybm, String rqlb, int display) throws RowsExceededException, WriteException, IOException{
		//ArrayList<HospInfo> hosps = new ArrayList<HospInfo>();
		//����ҽԺ����
		HospInfo hosp = new HospInfo();
		//����һ����ά������ҽԺ������������
		ArrayList<ArrayList<Double>> content = new ArrayList<ArrayList<Double>>();
		//���ҽԺ����
		ArrayList<String> cname = new ArrayList<String>();
		String path = "H:\\DW\\data\\HCpredict\\hospital_data\\HDATA\\" + predict_year + "\\"+ yybm + "data_"+ rqlb +"result.xls";
		File file = new File(path);
		content = readExcel(file, 0, cname);
		List<ArrayList<Double>> originaldata = new ArrayList<ArrayList<Double>>();
		//�����ȡ������
//		System.out.println("content: "+content.size());
//		for(int i = 0; i < content.size(); i++){
//			System.out.println(content.get(i));
//		}
		
		ArrayList<Double> true_data = new ArrayList<Double>(); 
		for(int i = 0; i < factor_num; i++){
			if(i == 0){
				for(int j = 0; j < 12; j++){
					true_data.add(content.get(i).get(content.get(i).size() - 12 + j));
				}
			}
			originaldata.add(content.get(i));
		}
		List<ArrayList<Double>> true_factors = split(originaldata);
		//ҽԺ���롢���ԭʼ���ݡ���������,��ʵ��������,��ʵ������������
		hosp.setYybm(yybm);
		hosp.setRqlb(rqlb); 	
		hosp.setOriginaldata(originaldata);
		hosp.setFactor_name(cname);
		hosp.setTrue_data(true_data);
		hosp.setTrue_factors(true_factors);
		//System.out.println("true: " + true_data);
		//���㼾���Ե�������
		if(sea_ajust_factor){
			Seasonal_Adjustment sea_adjust = new Seasonal_Adjustment();
			sea_adjust.seasonal_adjustment(hosp);
			List<Double> adjust_true_data = new ArrayList<Double>();
			for(int i = 0; i < hosp.getSeasonal_factors().get(0).length; i++){
				double factor = hosp.getSeasonal_factors().get(0)[i];
				adjust_true_data.add(true_data.get(i) / factor);
			}
			hosp.setAdjust_true_data(adjust_true_data);
		}else{
			List<ArrayList<Double>> adjust_originaldata = hosp.getOriginaldata();
			List<Double> adjust_true_data = true_data;
			hosp.setAdjust_originaldata(adjust_originaldata);
			hosp.setAdjust_true_data(adjust_true_data);
		}	
		
//		System.out.println("����ǰ�����ݣ� "+ hosp.getOriginaldata().get(0));
//		System.out.println("����������ݣ� "+ hosp.getAdjust_originaldata().get(0));
//		System.out.print("�������ӣ� ");
//		for(double factor : hosp.getSeasonal_factors().get(0)){
//			System.out.print(factor + " ");
//		}
//		System.out.println();
		//����Ȩ��
		Caculate_Weight cw = new Caculate_Weight();
		cw.caculate_weight(hosp);
		//System.out.println("Ȩ�أ� " + hosp.getWeights());
		//�������Ư�Ƶ�
		Concept_drift_point cdp = new Concept_drift_point();
		cdp.detective_drift_point(hosp);
		//System.out.println("����Ư�Ƶ㣺 " + hosp.getDrift_points());
		//�ֶ�
		CutSegmentation cutsegment = new CutSegmentation();
		cutsegment.cutsegmente(hosp);
		//System.out.println("allsegments: " + hosp.getSegments().size());

		/*
		 * �޸Ľ�ֹ�ĵط����Ѽ����Ի�ԭ����Ԥ��֮��ԭ��
		 */
		ContextPredict context_predict = new ContextPredict(); 
		context_predict.Cost_Context_predict(hosp);
		return hosp;
	}
	
	//���
	public static ArrayList<ArrayList<Double>> diff(List<ArrayList<Double>> data){
		assert data.size() > 0 : "û�����ݣ�";
		ArrayList<ArrayList<Double>> data_diff = new ArrayList<ArrayList<Double>>();
		for(ArrayList<Double> list : data){
			assert list.size() > 2 : "������̫С���޷�����֣�";
			ArrayList<Double> factor_diff = new ArrayList<Double>();
			for(int j = 1; j < list.size(); j++){
				factor_diff.add(list.get(j) - list.get(j - 1));
			}
			data_diff.add(factor_diff);
		}
		return data_diff;
	}
	//��һ��
	public static ArrayList<ArrayList<Double>> normalized(List<ArrayList<Double>> data){
		assert data.size() > 0 : "û�����ݣ�";
		ArrayList<ArrayList<Double>> data_nor = new ArrayList<ArrayList<Double>>();
		for(ArrayList<Double> list : data){
			assert list.size() > 2 : "������̫С���޷�����һ����";
			Normalization norma = new Normalization();
			ArrayList<Double> factor_nor = norma.qujianfa(list);
			data_nor.add(factor_nor);
		}
		return data_nor;
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
	
	public static double sum(List<Double> data){
		double sum = 0.0;
		for(double d : data){
			sum += d;
		}
		return sum;
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
			//System.out.println("·����" + file);
	     try {  
	         // ��������������ȡExcel  
	         InputStream is = new FileInputStream(file.getAbsolutePath());  
	         // jxl�ṩ��Workbook��  
	         Workbook wb = Workbook.getWorkbook(is);  
	         Sheet sheet = wb.getSheet(index);  
	        // System.out.println(sheet.getName());
	        // System.out.println("sheet.getColumns(): " + sheet.getColumns() + " sheet.getRows(): " + sheet.getRows());
	             // sheet.getRows()���ظ�ҳ��������  
	             for (int i = 1; i < sheet.getColumns(); i ++) {  
	             	ArrayList<Double> list = new ArrayList<Double>();
	             	name.add(sheet.getCell(i,0).getContents());
	                 // sheet.getColumns()���ظ�ҳ��������  
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
