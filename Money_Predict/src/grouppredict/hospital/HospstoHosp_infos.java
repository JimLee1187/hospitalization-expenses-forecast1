package grouppredict.hospital;


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
import contextpredict.hospital.HospInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年6月21日 上午8:57:22
 * @Version 1.0
 *
 */
public class HospstoHosp_infos {
	
	public ArrayList<Hosp_info> hospstohosp_infos(List<HospInfo> hosps){
		ArrayList<Hosp_info> hosp_infos = new ArrayList<Hosp_info>();
		for(HospInfo hosp : hosps){
			String yybm = hosp.getYybm();
			String rqlb = hosp.getRqlb();
			Hosp_info hosp_info = new Hosp_info(yybm, rqlb);
			//hosp_info.setZje(sum_zje(hosp.getTrue_data()));
			//获取人数
			String hosppath_rs = "H:\\DW\\data\\Predict\\rs\\2016\\" + yybm + rqlb + "resultrs.xls";
			File file = new File(hosppath_rs);
			//分组名称
	    	List<String> columns_name = new ArrayList<String>();
	    	//分组名称
	    	List<String> columnname = new ArrayList<String>();
	    	//取出真实值，且判断分组数据长度
	    	//原始数据
	    	ArrayList<ArrayList<Double>> rs_data = new ArrayList<ArrayList<Double>>();
	    	//真实分组人数数据
	    	 ArrayList<Integer> zrs_group = new ArrayList<Integer>();
	    	ArrayList<Integer> true_values = new ArrayList<Integer>();
	    	
	    	//原始数据
	    	ArrayList<ArrayList<Double>> rs_original_data = readExcel(file, 0, columns_name);
	    	int max_duration = 0;
	    	for(ArrayList<Double> d : rs_original_data){
	    		if(d.size() > max_duration){
	    			max_duration = d.size();
	    		}
	    	}
	    	//计算总人数数组
	    	double[] zrs_arr = new double[max_duration];
	    	for(int i = 0; i < rs_original_data.size(); i++){
	    		ArrayList<Double> d = rs_original_data.get(i);
	    		for(int j = 0; j < d.size(); j++){
	    			zrs_arr[max_duration - d.size() + j] += d.get(j);
	    		}
	    		if(d.size() > 1){
	    			columnname.add(columns_name.get(i));
	    			true_values.add((int) Math.round(d.get(d.size()-1)));
	    			d.remove(d.size()-1);
	    			rs_data.add(d);
	    		}
	    	}
	    	for(double d : zrs_arr){
	    		zrs_group.add((int) Math.round(d));
	    	}
	    	int zrs = zrs_group.get(zrs_group.size() - 1);
	    	//把人数的信息加入
	    	hosp_info.setRs_original_data(rs_original_data);
	    	hosp_info.setRs_data(rs_data);
	    	hosp_info.setColumns_name(columns_name);
	    	hosp_info.setColumnname(columnname);
	    	hosp_info.setZrs_group(zrs_group);
	    	hosp_info.setRs_group(true_values);
	    	hosp_info.setZrs(zrs);
	    	hosp_infos.add(hosp_info);	
	    	
	    	//获取人均花费信息
			String hosppath_rjhf = "H:\\DW\\data\\Predict\\rjhf\\2016\\" + yybm + rqlb + "resultrjhf.xls";
	    	File file_rjhf = new File(hosppath_rjhf);
	    	List<String> columns_name1 = new ArrayList<String>();
	    	//原始数据
	    	ArrayList<ArrayList<Double>> rjhf_data = new ArrayList<ArrayList<Double>>();
	    	//真实分组人数数据
	    	ArrayList<Double> true_values_rjhf = new ArrayList<Double>();
	    	
	    	ArrayList<ArrayList<Double>> rjhf_original_data = readExcel(file_rjhf, 0, columns_name1);
	    	//取出真实值，且判断分组数据长度
	    	for(int i = 0; i < rjhf_original_data.size(); i++){
	    		ArrayList<Double> d = rjhf_original_data.get(i);
	    		if(d.size() > 1){
	    			true_values_rjhf.add(d.get(d.size()-1));
	    			d.remove(d.size()-1);
	    			rjhf_data.add(d);
	    		}
	    	}
	    	hosp_info.setRjhf_original_data(rjhf_original_data);
	    	hosp_info.setRjhf_data(rjhf_data);
	    	hosp_info.setRjhf_group(true_values_rjhf);
	    	
	    	//获取总金额信息
	    	String hosppath_zje = "H:\\DW\\data\\Predict\\zje\\2016\\" + yybm + rqlb + "resultzje.xls";
	  		File file_zje = new File(hosppath_zje);
    		List<String> columns_name2 = new ArrayList<String>();
        	ArrayList<ArrayList<Double>> zje_original_data = readExcel(file_zje, 0, columns_name2);
        	ArrayList<Double> zje_group = new ArrayList<Double>();
        	double zje = 0.0;
        	for(int i = 0; i < zje_original_data.size(); i++){
        		ArrayList<Double> current_group = zje_original_data.get(i);
        		if(current_group.size() > 0){
        			zje += current_group.get(current_group.size() - 1);
        		}
        		if(current_group.size() > 1){
        			zje_group.add(current_group.get(current_group.size() - 1));
        		}
        	}
        	hosp_info.setZje_original_data(zje_original_data);
        	hosp_info.setZje_group(zje_group);
        	hosp_info.setZje(zje);
		}
		return hosp_infos;
	}
	
	public static int sum(List<Integer> data){
		int sum = 0;
		for(int d : data){
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
	public static  ArrayList<ArrayList<Double>> readExcel(File file,int index, List<String> columns_name) {  
		ArrayList<ArrayList<Double>> list_arr = new ArrayList<ArrayList<Double>>();
		//System.out.println("路径：" + file);
       try {  
           // 创建输入流，读取Excel  
           InputStream is = new FileInputStream(file.getAbsolutePath());  
           // jxl提供的Workbook类  
           Workbook wb = Workbook.getWorkbook(is);  
           Sheet sheet = wb.getSheet(index);  
          // System.out.println("sheet.getColumns(): " + sheet.getColumns() + " sheet.getRows(): " + sheet.getRows());
               // sheet.getRows()返回该页的总行数  
               for (int i = 1; i < sheet.getColumns(); i ++) {  
               	columns_name.add(sheet.getCell(i, 0).getContents().toString());
               	if(sheet.getRows() == 0){
               		continue;
               	}
               	ArrayList<Double> list = new ArrayList<Double>();
                   // sheet.getColumns()返回该页的总列数  
                   for (int j = 1; j < sheet.getRows(); j ++) { 
                	   if(!sheet.getCell(i, j).getContents().isEmpty())
                		   list.add(Double.parseDouble(sheet.getCell(i, j).getContents()));
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
	
	public static double sum_zje(List<Double> data){
		double sum = 0.0;
		for(double d : data){
			sum += d;
		}
		return sum;
	}
}
