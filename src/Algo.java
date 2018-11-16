import java.util.ArrayList;

public class Algo {
	//Param�tres
	private static final double qtot = 15;
	private static final double elam = 172;
	
	//Coefficients de l'�quation de l'�l�vation aval
	private static double[] coeffElav = {-7.071e-07, 0.004118, 137.2};
	
	//Coefficients de l'�quation des puissance des 5 turbines
	private static double[] coeffP1 = {31.57, 1.796, 17.28, - 0.0078120, 0.8945, -2.914, -0.014010, 0.12623};
	private static double[] coeffP2 = {30.33, 1.715, 20.53, - 0.0064880, 1.0850, -3.356, 0.020120,  0.11620};
	private static double[] coeffP3 = {33.58, 1.891, 16.88, - 0.0007952, 1.0010, -3.611, -0.007118, 0.1408};
	private static double[] coeffP4 = {34.50, 1.893, 19.79, - 0.0061180, 1.2370, -4.030, 0.007646,  0.17570};
	private static double[] coeffP5 = {32.00, 2.030, 17.86, - 0.0282700, 1.2390, -2.710, 0.034180,  0.11840};
	

	public static void main(String[] args) {
		double qn = 155;
		double hn =  calculHauteurChuteNette(qn);
		for(int n = 1; n<6; n++) {
			double p = calculPuissance(qn, hn, n);
			System.out.println("Puissance turbine " + n + " : " + p);
		}
	}

	private static double calculHauteurChuteNette(double qn) {
		double perte = 5*0.00001*qn*qn;
		double elav = coeffElav[0] * qn * qn + coeffElav[1] * qn + coeffElav[2];
		double hn = elam - elav - perte;
		System.out.println("Hauteur de chutte nette: "+hn);
		return hn;
	}
	
	private static double calculPuissance(double qn, double hn, int n) {
		
		double puissance = 0;
		switch(n) {
			case 1: puissance = coeffP1[0] + (coeffP1[1] * hn) + (coeffP1[2] * qn) 
					+ (coeffP1[3] * hn * hn) + (coeffP1[4] * hn * qn) + (coeffP1[5] * qn * qn) 
					+ (coeffP1[6] * qn * hn * hn) + (coeffP1[7] * qn *qn * hn);
					break;
				
			case 2: puissance = coeffP2[0] + (coeffP2[1] * hn) + (coeffP2[2] * qn) 
					+ (coeffP2[3] * hn * hn) + (coeffP2[4] * hn * qn) + (coeffP2[5] * qn * qn) 
					+ (coeffP2[6] * qn * hn * hn) + (coeffP2[7] * qn *qn * hn) ;
					break;
				
			case 3: puissance = coeffP3[0] + (coeffP3[1] * hn) + (coeffP3[2] * qn) 
					+ (coeffP3[3] * hn * hn) + (coeffP3[4] * hn * qn) + (coeffP3[5] * qn * qn) 
					+ (coeffP3[6] * qn * hn * hn) + (coeffP3[7] * qn *qn * hn);
					break;
			case 4: puissance = coeffP4[0] + (coeffP4[1] * hn) + (coeffP4[2] * qn) 
					+ (coeffP4[3] * hn * hn) + (coeffP4[4] * hn * qn) + (coeffP4[5] * qn * qn) 
					+ (coeffP4[6] * qn * hn * hn) + (coeffP4[7] * qn *qn * hn);
					break;
					
			case 5: puissance = coeffP5[0] + (coeffP5[1] * hn) + (coeffP5[2] * qn) 
					+ (coeffP5[3] * hn * hn) + (coeffP5[4] * hn * qn) + (coeffP5[5] * qn * qn) 
					+ (coeffP5[6] * qn * hn * hn) + (coeffP5[7] * qn *qn * hn);
					break;
			default: puissance = 0;
		}

		
		//Convertion en MW
		return puissance *= 0.001;

	}
	
}