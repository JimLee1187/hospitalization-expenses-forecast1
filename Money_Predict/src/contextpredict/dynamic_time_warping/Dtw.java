package contextpredict.dynamic_time_warping;
/**
 *
 * @author  LiJing 
 * @date    2018年2月5日 上午10:42:01
 * @Version 1.0
 *
 */
public class Dtw {
	public double getMin(double a, double b, double c) {
		double min = a;
		if (b > a)
		min = a;
		else if (c > b) {
		min = b;
		} else {
		min = c;
		}
		return min;
		}

		public double getDistance(double[] seqa, double[] seqb) {
		double distance = 0;
		int lena = seqa.length;
		int lenb = seqb.length;
		double[][] c = new double[lena][lenb];
		for (int i = 0; i < lena; i++) {
		for (int j = 0; j < lenb; j++) {
		c[i][j] = 1;
		}
		}
		for (int i = 0; i < lena; i++) {
		for (int j = 0; j < lenb; j++) {
		double tmp = (seqa[i] - seqb[j]) * (seqa[i] - seqb[j]);
		if (j == 0 && i == 0)
		c[i][j] = tmp;
		else if (j > 0)
		c[i][j] = c[i][j - 1] + tmp;
		if (i > 0) {
		if (j == 0)
		c[i][j] = tmp + c[i - 1][j];
		else
		c[i][j] = tmp + getMin(c[i][j - 1], c[i - 1][j - 1], c[i - 1][j]);
		}
		}
		}
		distance = c[lena - 1][lenb - 1];
		return distance;
		}
}
