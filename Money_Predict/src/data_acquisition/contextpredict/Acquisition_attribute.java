package data_acquisition.contextpredict;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 *
 * @author  LiJing 
 * @date    2018年8月8日 上午9:38:12
 * @Version 1.0
 *
 */
public class Acquisition_attribute {
	
	public void acq_attribute(String hosp, String rqlb, String[] attributes) throws IOException, WriteException{
		String  path_result = "H:\\小论文\\实验\\data\\"+ hosp + "data_"+ rqlb +"result.xls";
		File file_result = new File(path_result);
		WritableWorkbook writebook = Workbook.createWorkbook(file_result);
		String s = hosp + rqlb;
		WritableSheet sheet = writebook.createSheet(s, 0);
		for(int i = 0; i < attributes.length; i++){
			String attribute = attributes[i];
			ArrayList<Double> attribute_data = acq_attrib(hosp, rqlb, attribute);
			//output_attribute(sheet, attribute, attribute_data, i+1);
			sheet.addCell(new Label(i+1 , 0, attribute));
			for(int j = 0; j < attribute_data.size(); j++){
				double d = attribute_data.get(j);
				sheet.addCell(new jxl.write.Number(i+1, j+1, d));
			}
		}
		writebook.write();
		writebook.close();
	}
	
	public ArrayList<Double> acq_attrib(String hosp, String rqlb, String attribute){
		Acquisition_datapoint Ad = new Acquisition_datapoint();
		ArrayList<Double> attribute_arr = new ArrayList<Double>();
		//根据属性选取sql
		String sql = determine_sql(attribute);
		for(int i = 2010; i <= 2016; i++){
			String timepoint = new String(Integer.toString(i));
			for(int j = 1; j <= 12; j++){
				if(j <= 9){
					timepoint = timepoint.substring(0,4) + "0" + Integer.toString(j);
				}
				if(j >= 10){
					timepoint = timepoint.substring(0,4) + Integer.toString(j);
				}
				double data = Ad.acq_datapoint(sql, hosp, rqlb, timepoint, attribute);
				System.out.println(timepoint + "data:" + data);
				attribute_arr.add(data);
			}
		}
		return attribute_arr;
	}
	
	//根据属性确定sql语句
	public String determine_sql(String attribute){
		String sql = "";
		if(attribute.equals("zje")){
			sql = "select sum(a.sjfsfyze) as zje from info_Funding_forecast a "
					+ "where a.yltclb = '1' and a.yybm = ? and a.rqlb = ? and a.rq = ?";
		}
		
		if(attribute.equals("zrs")){
			sql = "select count(a.ryid) as zrs from info_Funding_forecast a "
					+ "where a.yltclb = '1' and a.yybm = ? and a.rqlb = ? and a.rq = ?";
		}
		
		if(attribute.equals("age1")){
			sql = "select count(a.ryid) as age1 from info_Funding_forecast a "
					+ "where a.yltclb = '1' and a.age < 25 and a.yybm = ? and a.rqlb = ? and a.rq = ?";
		}
		
		if(attribute.equals("age2")){
			sql = "select count(a.ryid) as age2 from info_Funding_forecast a "
					+ "where a.yltclb = '1' and a.age between 25 and 55 and a.yybm = ? and a.rqlb = ? and a.rq = ?";
		}
		
		if(attribute.equals("age3")){
			sql = "select count(a.ryid) as age3 from info_Funding_forecast a "
					+ "where a.yltclb = '1' and a.age > 55 and a.yybm = ? and a.rqlb = ? and a.rq = ?";
		}
		
		if(attribute.equals("zyts1")){
			sql = "select count(a.ryid) as zyts1 from info_Funding_forecast a "
					+ "where a.yltclb = '1' and a.zyts between 0 and 14 and a.yybm = ? and a.rqlb = ? and a.rq = ?";
		}
		
		if(attribute.equals("zyts2")){
			sql = "select count(a.ryid) as zyts2 from info_Funding_forecast a "
					+ "where a.yltclb = '1' and a.zyts > 14 and a.yybm = ? and a.rqlb = ? and a.rq = ?";
		}
		
		//-----------------------------------------------------------------------------------
		//疾病
		if(attribute.equals("jbbm")){
			sql = "select count(a.ryid) as jbbm from info_Funding_forecast a "
					+ "where a.yltclb = '1' and a.yybm = ? and a.rqlb = ? and a.rq = ? and a.jbbm in("
					+ "select jbbm_c from ("
					+ "select a.jbbm_c from info_Funding_forecast a where a.yltclb = '1' and a.yybm = ? and a.rqlb = ? "
					+ "group by a.jbbm_c order by sum(a.sjfsfyze) desc) where rownum < 201) ";
		}
		//---------------------------------------------------------------------------------------------
		//性别,迁移
		if(attribute.equals("xb")){
			sql = "select aa.rs/bb.rs as xb from ("
					+ "select count(a.ryid) rs from info_Funding_forecast a where a.yltclb = '1' and a.xb = '1' and a.yybm = ? and a.rqlb = ? and a.rq = ?) aa,"
					+ "(select count(a.ryid) rs from info_Funding_forecast a where a.yltclb = '1' and a.xb = '2' and a.yybm = ? and a.rqlb = ? and a.rq = ?) bb";
		}
		
		if(attribute.equals("qy")){
			sql = "select aa.rs/bb.rs as qy from ("
					+ "select count(a.ryid) rs from migrate_patient a where a.yltclb = '1' and a.yybm = ? and a.rqlb = ? and a.rq = ?) aa,"
					+ "(select count(a.ryid) rs from notmigrate_patient a where a.yltclb = '1' and a.yybm = ? and a.rqlb = ? and a.rq = ?) bb";
		}
		return sql;
	}
	
	public void output_attribute(WritableSheet sheet, String attribute, List<Double> attribute_data, int index) throws RowsExceededException, WriteException{
		sheet.addCell(new Label(index, 0, attribute));
		for(int i = 0; i < attribute_data.size(); i++){
			double d = attribute_data.get(i);
			sheet.addCell(new jxl.write.Number(index, i, d));
		}
	}
}
