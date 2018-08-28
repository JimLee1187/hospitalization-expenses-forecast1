package data_acquisition.contextpredict;

import java.io.IOException;

import jxl.write.WriteException;

/**
 *
 * @author  LiJing 
 * @date    2018年8月8日 上午9:44:10
 * @Version 1.0
 *
 */
public class Acquisition_hosp {
	
	public static void main(String[] args) throws WriteException, IOException {
		Acquisition_hosp ah = new Acquisition_hosp();
		ah.Acquisition_h("002605", "A");
		ah.Acquisition_h("002605", "B");
	}
	
	public void Acquisition_h(String hosp, String rqlb) throws WriteException, IOException{
		//每家医院
		String[] attributes = {"age1", "age2", "age3", "zyts1", "zyts2", "qy"};
		Acquisition_attribute Aa = new Acquisition_attribute();
		Aa.acq_attribute(hosp, rqlb, attributes);
	}
}
