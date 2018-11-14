import java.util.ArrayList;

public class Algo {
	
	private static final double qtot = 15;
	private static final double elam = 140;
	
	private static double[] coeffElav = {-7.071e-07,0.004118,137.2};
	private static double[] coeffP5 = {32,2.03,17.86,0.02827,1.239,-2.71,0.03418,0.1184,-1.904};

	public static void main(String[] args) {
		double p = calculPuissance(5, 0);
		System.out.println(p);
	}

	private static double calculHauteurChuteNette(double qn) {
		double perte = 5*0.00001*qn*qn;
		double elav = coeffElav[0] * qn * qn + coeffElav[1] * qn + coeffElav[2];
		double hn = elam - elav - perte;
		System.out.println("Hauteur de chutte nette: "+hn);
		return hn;
	}
	
	private static double calculPuissance(double qn, int n) {
		double hn =  calculHauteurChuteNette(qn);
		double puissance = coeffP5[0] + coeffP5[1]* hn + coeffP5[2] * qn + coeffP5[3] * hn *hn + coeffP5[4] * hn * qn +
				coeffP5[5] * qn*qn + coeffP5[6] * qn * hn * hn + coeffP5[7] * qn *qn * hn + coeffP5[8] * qn *qn *qn;
		
		return puissance;
	}
	
}