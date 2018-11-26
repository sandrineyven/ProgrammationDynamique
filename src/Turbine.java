
public class Turbine {

	// Coefficients de l'équation des puissance des 5 turbines
	private static double[] coeffP1 = {  0.08651, -0.0025430, -0.1976, 0.008183, 0.002892, 7.371 * 0.000001, -1.191 * 0.00001 };
	private static double[] coeffP2 = {  0.81220, -0.0237400, -0.2442, 0.006492, 0.003838, 2.207 * 0.000010, -1.665 * 0.00001 };
	private static double[] coeffP3 = { -0.02446,  0.0009464, -0.2157, 0.006353, 0.003541, 2.214 * 0.000010, -1.569 * 0.00001 };
	private static double[] coeffP4 = { -0.04632,  0.0017690, -0.1905, 0.004951, 0.003537, 3.487 * 0.000010, -1.689 * 0.00001 };
	private static double[] coeffP5 = {  0.29460, -0.0080740, -0.1834, 0.008090, 0.002706, 1.949 * 0.000010, -1.318 * 0.00001 };

	// Puissances de base
	private double[] Gn;

	// Fnmax: Solution optimal
	private double[] Fnmax;
	// Débit pour le quel la solution est optimale
	private int[] Xn;

	// propriétés de la turbine
	private int id;
	private boolean isActive = false;
	private int debitFinal = 0;
	private double puissanceFinale = 0;

	public Turbine(int sizeSn, int[] Sn, int id, boolean active) {
		this.id = id;
		this.Gn = initGn(sizeSn, Sn);
		this.Fnmax = new double[sizeSn];
		this.Xn = new int[sizeSn];
		if (active) {
			this.isActive = true;
		}
	}

	// Création de la matrice des puissances de base
	private double[] initGn(int sizeSn, int[] Sn) {
		double[] Gn = new double[sizeSn];
		for (int sn = 0; sn < sizeSn; sn++) {
			if (Sn[sn] > Constante.debitMaxTurbine) {
				Gn[sn] = 0;
			} else {
				Gn[sn] = calculPuissance(Sn[sn], calculHauteurChuteNette(Sn[sn]), this.id);
			}
		}
		return Gn;
	}

	private static double calculHauteurChuteNette(double qn) {
		double perte = 0.5 * 0.00001 * qn * qn;
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
			puissance = coeffP1[0] + (coeffP1[1] * hn) + (coeffP1[2] * qn) + (coeffP1[3] * hn * qn)
					+ (coeffP1[4] * qn * qn) + (coeffP1[5] * qn * qn * hn) + (coeffP1[6] * qn * qn * qn);
			break;
		case 2:
			puissance = coeffP2[0] + (coeffP2[1] * hn) + (coeffP2[2] * qn) + (coeffP2[3] * hn * qn)
					+ (coeffP2[4] * qn * qn) + (coeffP2[5] * qn * qn * hn) + (coeffP2[6] * qn * qn * qn);
			break;
		case 3:
			puissance = coeffP3[0] + (coeffP3[1] * hn) + (coeffP3[2] * qn) + (coeffP3[3] * hn * qn)
					+ (coeffP3[4] * qn * qn) + (coeffP3[5] * qn * qn * hn) + (coeffP3[6] * qn * qn * qn);
			break;
		case 4:
			puissance = coeffP4[0] + (coeffP4[1] * hn) + (coeffP4[2] * qn) + (coeffP4[3] * hn * qn)
					+ (coeffP4[4] * qn * qn) + (coeffP4[5] * qn * qn * hn) + (coeffP4[6] * qn * qn * qn);
			break;
		case 5:
			puissance = coeffP5[0] + (coeffP5[1] * hn) + (coeffP5[2] * qn) + (coeffP5[3] * hn * qn)
					+ (coeffP5[4] * qn * qn) + (coeffP5[5] * qn * qn * hn) + (coeffP5[6] * qn * qn * qn);
			break;
		default:
			puissance = 0;
		}
		return puissance;
	}

	//Getteurs et setteurs ---------------------------------------------------------------------
	
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

	public int getDebitFinal() {
		return debitFinal;
	}

	public void setDebitFinal(int debitFinal) {
		this.debitFinal = debitFinal;
	}

	public double getPuissanceFinale() {
		return puissanceFinale;
	}

	public void setPuissanceFinale(double puissanceFinale) {
		this.puissanceFinale = puissanceFinale;
	}

}
