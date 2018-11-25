
public class Turbine {

	// Coefficients de l'équation des puissance des 5 turbines
	private static double[] coeffP1 = { 31.57, 1.796, 17.28, -0.0078120, 0.8945, -2.914, -0.014010, 0.12623,-0.1* 1.918 };
	private static double[] coeffP2 = { 30.33, 1.715, 20.53, -0.0006488, 1.0850, -3.356, -0.020120, 0.10620,-0.1* 3.021 };
	private static double[] coeffP3 = { 33.58, 1.891, 16.88, -0.0007952, 1.0010, -3.611, -0.007118, 0.09408, -0.1* 2.002 };
	private static double[] coeffP4 = { 34.50, 1.893, 19.79, -0.0061180, 1.2370, -4.030, -0.007646, 0.15470, -0.1* 2.676 };
	private static double[] coeffP5 = { 32.00, 2.030, 17.86, -0.0028270, 1.2390, -2.710,  0.034180, 0.11840, -0.1* 1.904 };
	
	
	// Puissances de base
	private double[] Gn;

	// Fnmax: Solution optimal
	private double[] Fnmax;
	// Débit pour le quel la solution est optimale
	private int[] Xn;

	// Id de la turbine
	private int id;
	private boolean isActive = false;
	private int debitFinal = 0;
	private double puissanceFinale = 0;

	public Turbine(int sizeSn, int[] Sn, int id, boolean active) {
		this.id = id;
		this.Gn = initGn(sizeSn, Sn);
		this.Fnmax = new double[sizeSn];
		this.Xn = new int[sizeSn];
		if(active) {
			this.isActive = true;
		}
	}

	private double[] initGn(int sizeSn, int[] Sn) {
		double[] Gn = new double[sizeSn];
		for (int sn = 0; sn < sizeSn; sn++) {
			if (Sn[sn] > Constante.debitMaxTurbine) {
				Gn[sn] = 0;
			} else {
				double hn = calculHauteurChuteNette(Sn[sn]);
				double p = calculPuissance(Sn[sn], hn, this.id);
				Gn[sn] = p > 0 ? p:0;
				System.out.println("id : " + this.id + " gn: " + Gn[sn] +" sn : " + sn*5);
			}
		}
		
		return Gn;
	}

	private static double calculHauteurChuteNette(double qn) {
		double perte = 5 * 0.00001 * qn * qn;
		double elav = Constante.coeffElav[0] * Constante.qtot * Constante.qtot + Constante.coeffElav[1] * Constante.qtot
				+ Constante.coeffElav[2];
		double hn = Constante.elam - elav - perte;
		return hn;
	}

	private static double calculPuissance(double qn, double hn, int n) {

		double puissance = 0;
		if (qn == 0) {
			return puissance;
		}
		switch (n) {
		case 1:
			puissance = coeffP1[0] + (coeffP1[1] * hn) + (coeffP1[2] * qn) + (coeffP1[3] * hn * hn)
					+ (coeffP1[4] * hn * qn) + (coeffP1[5] * qn * qn) + (coeffP1[6] * qn * hn * hn)
					+ (coeffP1[7] * qn * qn * hn) + (coeffP1[8] * qn * qn * qn);
			break;

		case 2:
			puissance = coeffP2[0] + (coeffP2[1] * hn) + (coeffP2[2] * qn) + (coeffP2[3] * hn * hn)
					+ (coeffP2[4] * hn * qn) + (coeffP2[5] * qn * qn) + (coeffP2[6] * qn * hn * hn)
					+ (coeffP2[7] * qn * qn * hn)+ (coeffP2[8] * qn * qn * qn);
			break;

		case 3:
			puissance = coeffP3[0] + (coeffP3[1] * hn) + (coeffP3[2] * qn) + (coeffP3[3] * hn * hn)
					+ (coeffP3[4] * hn * qn) + (coeffP3[5] * qn * qn) + (coeffP3[6] * qn * hn * hn)
					+ (coeffP3[7] * qn * qn * hn)+ (coeffP3[8] * qn * qn * qn);
			break;
		case 4:
			puissance = coeffP4[0] + (coeffP4[1] * hn) + (coeffP4[2] * qn) + (coeffP4[3] * hn * hn)
					+ (coeffP4[4] * hn * qn) + (coeffP4[5] * qn * qn) + (coeffP4[6] * qn * hn * hn)
					+ (coeffP4[7] * qn * qn * hn)+ (coeffP4[8] * qn * qn * qn);
			break;

		case 5:
			puissance = coeffP5[0] + (coeffP5[1] * hn) + (coeffP5[2] * qn) + (coeffP5[3] * hn * hn)
					+ (coeffP5[4] * hn * qn) + (coeffP5[5] * qn * qn) + (coeffP5[6] * qn * hn * hn)
					+ (coeffP5[7] * qn * qn * hn)+ (coeffP5[8] * qn * qn * qn);
			break;
		default:
			puissance = 0;
		}

		// Convertion en MW
		return puissance *= 0.001;

	}

	public double getGn(int i) {
		return Gn[i];
	}

	public void setGn(double gn, int i) {
		Gn[i] = gn;
	}

	public double getFnmax(int i) {
		return Fnmax[i];
	}

	public void setFnmax(double fnmax, int i) {
		Fnmax[i] = fnmax;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getXn(int i) {
		return Xn[i];
	}

	public void setXn(int xn, int i) {
		Xn[i] = xn;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
}
