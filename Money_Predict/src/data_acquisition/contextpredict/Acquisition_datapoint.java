package data_acquisition.contextpredict;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author  LiJing 
 * @date    2018年8月8日 上午10:03:38
 * @Version 1.0
 *
 */
public class Acquisition_datapoint {

	public double acq_datapoint(String sql, String hosp, String rqlb, String timepoint, String attribute){
		double data = 0;
		Connect2DBFactory conn = new Connect2DBFactory();
		Connection con = conn.getDBConnection();
		try{
			PreparedStatement pre = con.prepareStatement(sql);
			if( attribute.equals("xb") || attribute.equals("qy")){
				pre.setString(1, hosp);
				pre.setString(2, rqlb);
				pre.setString(3, timepoint);
				pre.setString(4, hosp);
				pre.setString(5, rqlb);
				pre.setString(6, timepoint);
			}else{
				if(attribute.equals("jbbm")){
					pre.setString(1, hosp);
					pre.setString(2, rqlb);
					pre.setString(3, timepoint);
					pre.setString(4, hosp);
					pre.setString(5, rqlb);
				}else{
					pre.setString(1, hosp);
					pre.setString(2, rqlb);
					pre.setString(3, timepoint);
				}
			}
			ResultSet result = pre.executeQuery();
			while(result.next()){
				data = result.getDouble(attribute);
			}
			result.close();
			pre.close();
			con.close();
		}catch(Exception e){
			e.getStackTrace();
		}
		return data;
	}
}
