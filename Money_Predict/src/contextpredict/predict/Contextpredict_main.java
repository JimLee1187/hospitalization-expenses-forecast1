package contextpredict.predict;

import grouppredict.hospital.Hosp_info;
import grouppredict.limitpredict.GroupPredict_main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import contextpredict.hospital.HospInfo;
import contextpredict.hospital.PredictPointInfo;

/**
 *
 * @author  LiJing 
 * @date    2018年6月14日 下午2:13:25
 * @Version 1.0
 *
 */
public class Contextpredict_main {
	public static void main(String[] args) throws RowsExceededException, WriteException, IOException { 
		Contextpredict cpredict = new Contextpredict();
		//String[] yybms = {"000001"};
		int disp = 0;
		String[] yybms = {"000001", "000002", "000003", "000004", "000008", "000024", "000025", "000026", "001105",
				"001439",  "106001", "106002", "500001", "500002"};
		//有问题的医院：001110，000026，001439
		String[] yybm_arr = { "500001", "000003", "000024", "000025", "000004", "001110"};
		String[] rqlbs = {"A", "B"}; 
		ArrayList<HospInfo> hosps = new ArrayList<HospInfo>();
		ArrayList<HospInfo> context_predict_hosps = new ArrayList<HospInfo>();
		ArrayList<HospInfo> group_predict_hosps = new ArrayList<HospInfo>();
		Display_HospInfo display_hospinfo = new Display_HospInfo();
		System.out.println("上下文预测！");
		for(String yybm : yybms){
			HospInfo hosp = cpredict.contextpredict(yybm, rqlbs[0], 0);
			display_hospinfo.display_hospcontextpredictinfo(hosp, disp);
			double sum_similarity = 0;
			double max_simi = 0;
			for(int i = 0; i < hosp.getPredict_Points_info().size(); i++){
				PredictPointInfo pointinfo = hosp.getPredict_Points_info().get(i);
				sum_similarity += pointinfo.getSimilarity();
				if(pointinfo.getSimilarity() > max_simi){
					max_simi = pointinfo.getSimilarity();
				}
			}
			//System.out.println("weight: " + hosp.getWeights());
			//System.out.println("size: " + hosp.getWeights().size());
			//System.out.print("avg_simi: " + sum_similarity/hosp.getPredict_Points_info().size());
			//System.out.println("  max_simi: " + max_simi);
			hosps.add(hosp);
		}
		System.out.println();
		for(String yybm : yybms){
			HospInfo hosp = cpredict.contextpredict(yybm, rqlbs[1], 0);
			display_hospinfo.display_hospcontextpredictinfo(hosp, disp);
			double sum_similarity = 0;
			double max_simi = 0;
			for(int i = 0; i < hosp.getPredict_Points_info().size(); i++){
				PredictPointInfo pointinfo = hosp.getPredict_Points_info().get(i);
				sum_similarity += pointinfo.getSimilarity();
				if(pointinfo.getSimilarity() > max_simi){
					max_simi = pointinfo.getSimilarity();
				}
			}
			//System.out.print("avg_simi: " + sum_similarity/hosp.getPredict_Points_info().size());
			//System.out.println("  max_simi: " + max_simi);
			hosps.add(hosp);
		}
		
//		double zrs_weight = 0;
//		for(int i = 0; i < hosps.size(); i++){
//			System.out.println("weight: " + hosps.get(i).getWeights());
//			zrs_weight = zrs_weight + hosps.get(i).getWeights().get(0);
//			if(hosps.get(i).isHave_simihiscontext()){ 
//				context_predict_hosps.add(hosps.get(i));
//			}else{
//				group_predict_hosps.add(hosps.get(i));
//			}
//		}
//		System.out.println("avg_weight: " + zrs_weight/hosps.size());
////		for(HospInfo hosp : group_predict_hosps){
////			System.out.println(hosp.getYybm());
////		}
//
//		System.out.println("分组预测！");
//		List<Hosp_info> hosp_infos = new ArrayList<Hosp_info>();
//		GroupPredict_main gpm = new GroupPredict_main();
//		hosp_infos = gpm.grouppredict(group_predict_hosps);
		
		//输出预测好的医院
//		System.out.println("输出上下文预测结果！");
//		for(HospInfo hosp :context_predict_hosps){
//			display_hospinfo.display_hospcontextpredictinfo(hosp, 0);
//		}
//		System.out.println("输出分组预测结果！");
//		for(Hosp_info hosp : hosp_infos){
//			display_hospinfo.display_grouppredictinfo(hosp, 1);
//		}
//		for(Hosp_info hosp : hosp_infos){
//			display_hospinfo.display_grouppredictinfo(hosp, 0);
//		}
		
		//输出上下文详细预测结果
//		System.out.println("输出上下文预测结果！");
//		for(HospInfo hosp :context_predict_hosps){
//			if(adjust_in(hosp.getYybm(), yybm_arr)){
//				display_hospinfo.display_hospcontextpredictinfo(hosp, 1);
//			}
//		}
	}
	
	public static boolean adjust_in(String yybm, String[] yybm_arr){
		assert yybm_arr.length > 0 : "数组为空，无法判断字符是否在数组里！";
		boolean is_in = false;
		for(String str : yybm_arr){
			//System.out.println("yybm: " + yybm + " str" + str);
			if(yybm.equals(str)){
				//System.out.println("aaaaaaa");
				is_in = true;
			}
		}
		return is_in;
	}
}
