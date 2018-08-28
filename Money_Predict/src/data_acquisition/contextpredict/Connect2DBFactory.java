package data_acquisition.contextpredict;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author  LiJing 
 * @date    2018年8月8日 上午9:22:02
 * @Version 1.0
 *
 */
public class Connect2DBFactory {

	
	public static Connection getDBConnection() {
        Connection conn = null;
        try {
           // Class.forName("oracle.jdbc.Driver");
            //System.out.println("开始尝试连接数据库！");
            String url = "jdbc:oracle:" + "thin:@localhost:1521:orcl";
            String user = "lj";
            String password = "123456";
            conn = DriverManager.getConnection(url, user, password);
            //System.out.println("连接成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return conn;
    }
}
