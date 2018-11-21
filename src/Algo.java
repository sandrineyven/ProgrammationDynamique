public class Algo {
	//Param�tres
	private static final int qtot = 15;
	private static final double elam = 175;
	
	//Coefficients de l'�quation de l'�l�vation aval
	private static double[] coeffElav = {-7.071e-07, 0.004118, 137.2};
	
	//Coefficients de l'�quation des puissance des 5 turbines
	private static double[] coeffP1 = {31.57, 1.796, 17.28, - 0.0078120, 0.8945, -2.914, -0.014010, 0.12623};
	private static double[] coeffP2 = {30.33, 1.715, 20.53, - 0.0064880, 1.0850, -3.356, 0.020120,  0.11620};
	private static double[] coeffP3 = {33.58, 1.891, 16.88, - 0.0007952, 1.0010, -3.611, -0.007118, 0.14080};
	private static double[] coeffP4 = {34.50, 1.893, 19.79, - 0.0061180, 1.2370, -4.030, 0.007646,  0.15470};
	private static double[] coeffP5 = {32.00, 2.030, 17.86, - 0.0282700, 1.2390, -2.710, 0.034180,  0.11840};
	

	public static void main(String[] args) {
		
		//Discr�tisation de Sn
		int sizeSn = qtot/5 +1;
		int[] Sn = new int[sizeSn];
		//D�bit attribu�
		int[][] Qn = new int[6][sizeSn];
		int indexSn = 0;
		for(int i= 0; i<=qtot; i +=5) {
			//D�bit restant
			Sn[indexSn] = i;
			indexSn++;
		}
		
			
		//Initialisation de Gn
		double[][] Gn = initGn(sizeSn,Sn);
		
		//Backward pass
		//Turbine 5 (premiere passe):
		
		//Fnmax: Solution optimal 
		double[][] Fnmax = new double[6][sizeSn];
		for(int sn = 0; sn<sizeSn; sn++) {
			Fnmax[5][sn] = Gn[5][sn];		
		}
		
		//Turbines interm�diaires:
		//FnInter: Tableau contenant tous les cas possibles
		double[][] FnInter = new double[sizeSn][sizeSn];
		
		//Pour toutes les turbines intermediaires: (ici de 4 � 2)
		for(int turbine = 4; turbine > 1; turbine --) {
			Fnmax[turbine][0] = 0;
			for(int xn = 0; xn<sizeSn; xn++) {
				for(int sn = 0; sn<sizeSn; sn++) {
					if(sn-xn < 0) {
						//zone non remplissable du tableau
						FnInter[sn][xn]= -1 ;
					}else {
						FnInter[sn][xn]= Gn[turbine][xn] + Fnmax[turbine+1][sn-xn]  ;
						
						//System.out.println( "sn :"  +sn+ " xn : " +xn +" turbine: " + turbine + " Gn[turbine][xn] " + Gn[turbine][xn] + " Fnmax[turbine+1][sn-xn] " + Fnmax[turbine+1][sn-xn]);
					}
					//System.out.println(" Fninter: " + FnInter[sn][xn]);
				}
			}
		
			//Trouver les maxi: reconstruction de Fnmax pour le calcul de la turbine pr�c�dente
			
			//Recup�ration du d�bit pour le quel Fnmax est maximum (pour l'utiliser dans le forward pass)
			Qn[turbine][0] = 0;
			for(int sn = 1; sn<sizeSn; sn++) {
				for(int xn = 0; xn<sizeSn; xn++) {
					if(Fnmax[turbine][sn] < FnInter[xn][sn]) {
						//R�cup�ration du maximum
						Fnmax[turbine][sn] = FnInter[xn][sn];
						//R�cup�ration du d�bit correspondant au maximum
						Qn[turbine][sn] = Sn[sn];
						
					}
				}
			//	System.out.println("turbine: " + turbine +" Fnmax: " + Fnmax[turbine][sn] + " qn: " + Qn[turbine][sn]);
			}
			for(int sn = 0; sn<sizeSn; sn++) {
				System.out.println("turbine: " + turbine +" Fnmax: " + Fnmax[turbine][sn] + " qn: " + Qn[turbine][sn]);
			}
		}
		
		//Turbine 1:
		FnInter[sizeSn-1][0] = 0;
		for(int xn = 1; xn<sizeSn; xn++) {
				FnInter[sizeSn-1][xn]= Gn[1][xn] + Fnmax[2][sizeSn-1-xn]  ;
				System.out.println("turbine: 1  FnInter: " + FnInter[sizeSn-1][xn] );
		}
		
		for(int xn = 1; xn<sizeSn; xn++) {
			if(FnInter[sizeSn-1][xn-1] < FnInter[sizeSn-1][xn]) {
				//R�cup�ration du maximum
				Fnmax[1][sizeSn-1] = FnInter[sizeSn-1][xn];
				//R�cup�ration du d�bit correspondant au maximum
				Qn[1][sizeSn-1] = Sn[xn];
			}
		}
		System.out.println("turbine: 1  FnMax: " + Fnmax[1][sizeSn-1] +  "turbine: 1  QnMax: " + Qn[1][sizeSn-1]);
		
		//Forward pass
		
		
		
	}

	private static double calculHauteurChuteNette(double qn) {
		double perte = 5*0.00001*qn*qn;
		double elav = coeffElav[0] * qn * qn + coeffElav[1] * qn + coeffElav[2];
		double hn = elam - elav - perte;
		//System.out.println("Hauteur de chutte nette: "+hn);
		return hn;
	}
	
	private static double calculPuissance(double qn, double hn, int n) {
		
		double puissance = 0;
		if(qn == 0) {
			return puissance;
		}
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

	private static double[][] initGn(int sizeSn, int[] Sn){
		double[][] Gn = new double[6][sizeSn];
		for(int turbine=1;turbine<=5;turbine++) {
			for(int sn = 0; sn<sizeSn; sn++) {
				double hn =  calculHauteurChuteNette(Sn[sn]);
				double p = calculPuissance(Sn[sn], hn, turbine);
				Gn[turbine][sn] = p;
				//System.out.println("turbine " + turbine + " Gn[turbine][sn]: " + Gn[turbine][sn]);
			}
		}
		return Gn;
	}
}