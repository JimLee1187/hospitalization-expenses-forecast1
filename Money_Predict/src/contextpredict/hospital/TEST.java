package contextpredict.hospital;

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
import contextpredict.concept_drift.Drift_Detection;
import contextpredict.concept_drift.Feature_Extraction;
import contextpredict.concept_drift.Normalization;

public class TEST {
	public static void main(String[] args) {
		int window = 6;
		double threshold = 0.3;
		Feature_Extraction fe = new Feature_Extraction();
		//����һ����ά������ҽԺ������������
		ArrayList<ArrayList<Double>> content = new ArrayList<ArrayList<Double>>();
		//���ҽԺ����
		ArrayList<String> cname = new ArrayList<String>();
		String path = "F:\\data\\HistoryContenPredict\\000008data_zgfactor.xls";
		File file = new File(path);
		content = readExcel(file, 0, cname);
		ArrayList<Double> predict_data = content.get(0); 
		ArrayList<Double> factor_data1 = content.get(1); 
		ArrayList<Double> factor_data2 = content.get(2);
		List<ArrayList<Double>> originaldata = new ArrayList<ArrayList<Double>>();
		originaldata.add(predict_data);
		originaldata.add(factor_data1);
		originaldata.add(factor_data2);
		double[] season_factor = new double[12];
		int count = 0;
		while (predict_data.size() - (count + 1) * 12 >= 0) {
			for (int i = 0; i < 12; i++) {
				season_factor[i] += predict_data.get(predict_data.size() - 12 + i - count * 12);
//				if(i == 10){
//					System.out.println(predict_data.get(predict_data.size() - 12 + i - count * 12));
//				}
			}
			count++;
		}
		System.out.println("count: " + count);
		for(int i = 0; i < season_factor.length; i++){
			System.out.print(season_factor[i] + " ");
		}
		System.out.println();
		System.out.println("/count");
		for(int i = 0; i < season_factor.length; i++){
			season_factor[i] = season_factor[i] / count;
		}
		
		for(int i = 0; i < season_factor.length; i++){
			System.out.print(season_factor[i] + " ");
		}
		System.out.println("length: " + season_factor.length);
		List<Double> predict_data_sea = new ArrayList<Double>();
		for(int i = 0; i < predict_data.size(); i++){
			int index = i % 12;
			predict_data_sea.add(predict_data.get(i) / season_factor[index]);
		}
		System.out.println(predict_data);
		for(int i = 0; i < 12; i++){
			System.out.print(predict_data_sea.get(i) + " ");
		}
		
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
