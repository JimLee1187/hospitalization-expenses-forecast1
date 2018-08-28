package contextpredict.predict;

import grouppredict.hospital.Hosp_info;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import contextpredict.curvesegmentation.Segmentation;
import contextpredict.hospital.HospInfo;
import contextpredict.hospital.PredictPointInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年6月17日 下午8:24:06
 * @Version 1.0
 *
 */
public class Display_HospInfo {
	private int predict_year = HospInfo.predict_year;
	
	public void display_hospinfo(HospInfo hosp, int display) throws IOException, RowsExceededException, WriteException{
		if(hosp.isHave_simihiscontext()){
			this.display_hospcontextpredictinfo(hosp, display);
		}
		if(!hosp.isHave_simihiscontext()){
			//this.display_grouppredictinfo(hosp, display);
		}
	}
	
	public void display_hospcontextpredictinfo(HospInfo hosp, int display) throws IOException, RowsExceededException, WriteException{
		//输出预测过程
		if(display == 1){
			//输出预测结果
			String yybm = hosp.getYybm();
			String rqlb = hosp.getRqlb();
			String  path_result = "H:\\DW\\data\\HCpredict\\result\\Hresult\\" + predict_year + "\\"+ yybm + "data_"+ rqlb +"predict.xls";
			File file_result = new File(path_result);
			WritableWorkbook writebook = Workbook.createWorkbook(file_result);
			String s = yybm + rqlb;
			WritableSheet sheet = writebook.createSheet(s, 0);
			int column = 1;
			System.out.println("调整前的数据： "+ hosp.getOriginaldata().get(0));
			System.out.println("调整后的数据： "+ hosp.getAdjust_originaldata().get(0));
			sheet.addCell(new Label(1, 0, "原始数据"));
			sheet.addCell(new Label(2, 0, "调整后的数据"));
			for(int k = 0; k < hosp.getOriginaldata().get(0).size(); k++){
				sheet.addCell(new jxl.write.Number(1, k + 1, hosp.getOriginaldata().get(0).get(k)));
				sheet.addCell(new jxl.write.Number(2, k + 1, hosp.getAdjust_originaldata().get(0).get(k)));
			}
//			if(!hosp.getSeasonal_factors().isEmpty()){
//				System.out.print("调整因子： ");
//				for(double factor : hosp.getSeasonal_factors().get(0)){
//					System.out.print(factor + " ");
//				}
//				System.out.println();
//			}
			System.out.println("权重： " + hosp.getWeights());
			System.out.println("概念漂移点： " + hosp.getDrift_points());
			sheet.addCell(new Label(3, 0, "漂移点"));
			column += 3;
			for(int k = 0; k < hosp.getDrift_points().size(); k++){
				sheet.addCell(new jxl.write.Number(3, k + 1, hosp.getDrift_points().get(k)));
			}
			System.out.println("分段结果： ");
			for(int i = 0; i < hosp.getSegments().size(); i++){
				Segmentation se = hosp.getSegments().get(i);
				String segmentname = "分段" + (i + 1);
				sheet.addCell(new Label(column + i, 0, segmentname));
				for(int j = 0; j < se.getSegment().get(0).size(); j++){
					sheet.addCell(new jxl.write.Number(4 + i, j + 1, se.getSegment().get(0).get(j)));
				}
				System.out.println(se.getSegment().get(0));
			}
			column += hosp.getSegments().size();
			List<PredictPointInfo> Predict_Points_info = hosp.getPredict_Points_info();
			double similarity_sum = 0;
			double similarity_max = 0;
			List<Double> simi_arr = new ArrayList<Double>(); 
			for(int i = 0; i < Predict_Points_info.size(); i++){
				PredictPointInfo predictpointinfo = Predict_Points_info.get(i);
				double similarity = predictpointinfo.getSimilarity();
				int index = predictpointinfo.getIndex();
				Segmentation testedPcontext = predictpointinfo.getTestedPcontext();
				Segmentation historycontext = predictpointinfo.getHistorycontext();
				List<Double> predict_point = predictpointinfo.getPredict_point();
				if(similarity > similarity_max){
					similarity_max = similarity;
				}
				similarity_sum += similarity;
				simi_arr.add(similarity);
				//输出待测点上下文，历史上下文，相似度
				System.out.println("历史上下文： " + historycontext.getSegment().get(0));
				System.out.print("待测上下文： " + testedPcontext.getSegment().get(0));
				System.out.println("index: " + index);
				System.out.println(" 相似度" + similarity);
				System.out.println("预测结果：" + predict_point);
				System.out.println();
				String predict_count = "第" + (i+1) + "次预测！";
				sheet.addCell(new Label(column + 4 * i + 1, 0, predict_count));
				sheet.addCell(new Label(column + 4 * i + 2, 0, "历史上下文"));
				sheet.addCell(new Label(column + 4 * i + 3, 0, "待测点上下文"));
				sheet.addCell(new Label(column + 4 * i + 4, 0, "预测结果"));
				sheet.addCell(new Label(column + 4 * i + 1, 1, "相似度"));
				sheet.addCell(new jxl.write.Number(column + 4 * i + 1, 2, predictpointinfo.getSimilarity()));
				for(int j = index; j < historycontext.getSegment().get(0).size(); j++){
					sheet.addCell(new jxl.write.Number(column + 4 * i + 2, j - index + 1, historycontext.getSegment().get(0).get(j)));
				}
				for(int j = 0; j < testedPcontext.getSegment().get(0).size(); j++){
					sheet.addCell(new jxl.write.Number(column + 4 * i + 3, j + 1, testedPcontext.getSegment().get(0).get(j)));
				}
				for(int j = 0; j < predict_point.size(); j++){
					sheet.addCell(new jxl.write.Number(column + 4 * i + 4, j + 1, predict_point.get(j)));
				}
			}
			column += 4 * Predict_Points_info.size();
			double similarity_avg = similarity_sum/12;
			sheet.addCell(new Label(column + 1, 0, "Adjust真实值"));
			sheet.addCell(new Label(column + 2, 0, "Adjust预测值"));
			sheet.addCell(new Label(column + 3, 0, "误差"));
			sheet.addCell(new Label(column + 4, 0, "真实值"));
			sheet.addCell(new Label(column + 5, 0, "预测值"));
			sheet.addCell(new Label(column + 6, 0, "误差"));
			sheet.addCell(new Label(column + 7, 0, "相似度"));
			double avg_error = 0;
			for(int i = 1; i <= 12; i++){
				double adjusttruevalue = hosp.getAdjust_true_data().get(i - 1);
				double adjustpredictvalue = hosp.getAdjust_predict_data().get(i - 1);
				double adjust_error = Math.abs(adjustpredictvalue - adjusttruevalue) / adjusttruevalue;
				double truevalue = hosp.getTrue_data().get(i - 1);
				double predictvalue = hosp.getPredict_data().get(i - 1);
				double predit_error = Math.abs(predictvalue - truevalue) / truevalue;
				avg_error += predit_error;
				System.out.println(i + " truevalue: " + BigDecimal.valueOf(truevalue) + " predictvalue: " + predictvalue + " predit_error: " + predit_error);
				sheet.addCell(new jxl.write.Number(column + 1, i, adjusttruevalue));
				sheet.addCell(new jxl.write.Number(column + 2, i, adjustpredictvalue));
				sheet.addCell(new jxl.write.Number(column + 3, i, adjust_error));
				sheet.addCell(new jxl.write.Number(column + 4, i, truevalue));
				sheet.addCell(new jxl.write.Number(column + 5, i, predictvalue));
				sheet.addCell(new jxl.write.Number(column + 6, i, predit_error));
				sheet.addCell(new jxl.write.Number(column + 7, i, simi_arr.get(i - 1)));
			}
			avg_error = avg_error/12;
			sheet.addCell(new Label(column + 1, 14, "平均相似度"));
			sheet.addCell(new jxl.write.Number(column + 2, 14, similarity_avg));
			sheet.addCell(new Label(column + 3, 14, "最大相似度"));
			sheet.addCell(new jxl.write.Number(column + 4, 14, similarity_max));
			sheet.addCell(new Label(column + 5, 14, "平均误差"));
			sheet.addCell(new jxl.write.Number(column + 6, 14, avg_error));
			column += 7;
			System.out.println("predict: " + hosp.getPredict_data());
			System.out.println("true:    " + hosp.getTrue_data());
			System.out.println(hosp.getTrue_data());
			System.out.print(hosp.getYybm() + " " + hosp.getRqlb() + " ");
			System.out.print("预测费用：" + BigDecimal.valueOf(sum(hosp.getPredict_data())) + " ");
			System.out.print("真实费用：" + BigDecimal.valueOf(sum(hosp.getTrue_data())) + " ");
			double error = Math.abs(sum(hosp.getPredict_data()) - sum(hosp.getTrue_data())) / sum(hosp.getTrue_data());
			System.out.println("error: " + error);
			sheet.addCell(new Label(column + 1, 0, "总费用真实值"));
			sheet.addCell(new Label(column + 2, 0, "总费用预测值"));
			sheet.addCell(new Label(column + 3, 0, "误差"));
			sheet.addCell(new jxl.write.Number(column + 1, 1, sum(hosp.getTrue_data())));
			sheet.addCell(new jxl.write.Number(column + 2, 1, sum(hosp.getPredict_data())));
			sheet.addCell(new jxl.write.Number(column + 3, 1, error));
			writebook.write();
			writebook.close();
		}
		if(display != 1){
			System.out.print(hosp.getYybm() + " " + hosp.getRqlb() + " ");
			System.out.print("预测费用：" + BigDecimal.valueOf(sum(hosp.getPredict_data())) + " ");
			System.out.print("真实费用：" + BigDecimal.valueOf(sum(hosp.getTrue_data())) + " ");
			double error = Math.abs(sum(hosp.getPredict_data()) - sum(hosp.getTrue_data())) / sum(hosp.getTrue_data());
			System.out.println("error: " + error);
			//System.out.println(hosp.getPredict_data().size());
		}
	}
	
	public void display_grouppredictinfo(Hosp_info hosp, int display) throws IOException, RowsExceededException, WriteException{
		//输出预测过程
		if(display == 1){
			//输出预测结果
			String yybm = hosp.getYybm();
			String rqlb = hosp.getRqlb();
			String  path_result = "H:\\DW\\data\\predict\\groupresult\\temp\\"+ yybm + "data_"+ rqlb +"result.xls";
			File file_result = new File(path_result);
			WritableWorkbook writebook = Workbook.createWorkbook(file_result);
			String s = yybm + rqlb;
			WritableSheet sheet = writebook.createSheet(s, 0);
			int column = 0;
			//输出人数预测过程：分组名称、真实的数据，预测值。
			sheet.addCell(new Label(0, column, "分组"));
			sheet.addCell(new Label(0, column + 1, "分组人数"));
			sheet.addCell(new Label(0, column + 2, "预测值"));
			for(int i = 1; i <= hosp.getRs_group().size(); i++){
				sheet.addCell(new Label(i, column, hosp.getColumnname().get(i - 1)));
				sheet.addCell(new jxl.write.Number(i, column + 1, hosp.getRs_group().get(i - 1)));
				sheet.addCell(new jxl.write.Number(i, column + 2, hosp.getPredict_rs_group().get(i - 1)));
			}
			column += 3;
			//输出人均花费预测过程：真实的数据，预测值
			sheet.addCell(new Label(0, column, "分组人均花费"));
			sheet.addCell(new Label(0, column + 1, "预测值"));
			for(int i = 1; i <= hosp.getColumnname().size(); i++){
				sheet.addCell(new jxl.write.Number(i, column, hosp.getRjhf_group().get(i - 1)));
				sheet.addCell(new jxl.write.Number(i, column + 1, hosp.getPredict_rjhf_group().get(i - 1)));
			}
			column += 2;
			//分组预测结果：真实的数据，预测值
			sheet.addCell(new Label(0, column, "分组总金额"));
			sheet.addCell(new Label(0, column + 1, "预测值"));
			for(int i = 1; i <= hosp.getColumnname().size(); i++){
				sheet.addCell(new jxl.write.Number(i, column, hosp.getZje_group().get(i - 1)));
				//输出预测分组金额
				sheet.addCell(new jxl.write.Number(i, column + 1, hosp.getPredict_zje_group().get(i - 1)));
			}
			column += 2;
			//总费用展示：真实的数据，预测值
			sheet.addCell(new Label(0, column, "总金额"));
			sheet.addCell(new Label(2, column, "预测值"));
			sheet.addCell(new Label(4, column, "误差"));
			double error = Math.abs(hosp.getPredict_zje() - hosp.getZje()) / hosp.getZje();
			sheet.addCell(new jxl.write.Number(1, column, hosp.getZje()));
			sheet.addCell(new jxl.write.Number(3, column, hosp.getPredict_zje()));
			sheet.addCell(new jxl.write.Number(5, column, error));
			writebook.write();
			writebook.close();
		}
		if(display == 0){
			double error = Math.abs(hosp.getPredict_zje() - hosp.getZje()) / hosp.getZje();
			System.out.print(hosp.getYybm() + " " + hosp.getRqlb() + " ");
			System.out.print("预测费用：" + BigDecimal.valueOf(hosp.getPredict_zje()) + " ");
			System.out.print("真实费用：" + BigDecimal.valueOf(hosp.getZje()) + " ");
			System.out.println("error: " + error);
		}
	}
	
	public static double sum(List<Double> data){
		double sum = 0.0;
		for(double d : data){
			sum += d;
		}
		return sum;
	}
}
