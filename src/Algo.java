import java.util.ArrayList;

public class Algo {
	
	public static ArrayList<Turbine> listeTurbines = new ArrayList<Turbine>(6);
	
	public static void main(String[] args) {		
		
		// Discrétisation de Sn
		int sizeSn = Constante.qtot / 5 + 1;
		int[] Sn = new int[sizeSn];
		int indexSn = 0;
		for (int i = 0; i <= Constante.qtot; i += 5) {
			// Débit restant
			Sn[indexSn] = i;
			indexSn++;
		}

		// Initialisation des turbines
		listeTurbines.add(null);
		Turbine turbine1 = new Turbine(sizeSn, Sn, 1, true);
		if(turbine1.isActive()) listeTurbines.add(turbine1);
		Turbine turbine2 = new Turbine(sizeSn, Sn, 2, true);
		if(turbine2.isActive()) listeTurbines.add(turbine2);
		Turbine turbine3 = new Turbine(sizeSn, Sn, 3, true);
		if(turbine3.isActive())listeTurbines.add(turbine3);
		Turbine turbine4 = new Turbine(sizeSn, Sn, 4, true);
		if(turbine4.isActive())listeTurbines.add(turbine4);
		Turbine turbine5 = new Turbine(sizeSn, Sn, 5, true);
		if(turbine5.isActive())listeTurbines.add(turbine5);

		// Backward pass
		// Turbine 5 (premiere passe):
		for (int sn = 1; sn < sizeSn; sn++) {
			if(Sn[sn] > Constante.debitMaxTurbine) {
				listeTurbines.get(listeTurbines.size() - 1).setFnmax(0,sn);
			}else {
				listeTurbines.get(listeTurbines.size() - 1).setFnmax(turbine5.getGn(sn),sn);
				listeTurbines.get(listeTurbines.size() - 1).setXn(Sn[sn], sn);
			}
			//System.out.println("turbine: " +listeTurbines.get(listeTurbines.size() - 1).getId() + " Fninter: " + listeTurbines.get(listeTurbines.size() - 1).getFnmax(sn) + " sn :" + sn);
		}
		
		// Turbines intermédiaires:
		// FnInter: Tableau contenant tous les cas possibles
		double[][] FnInter = new double[sizeSn][sizeSn];
		FnInter[0][0] = 0;
		
		// Pour toutes les turbines intermediaires: (ici de 4 à 2)
		for (int turbine = listeTurbines.size()-2; turbine > 1; turbine--) {
			listeTurbines.get(turbine).setFnmax(0, 0);
			listeTurbines.get(turbine).setXn(0, 0);
			for (int sn = 1; sn < sizeSn; sn++) {
				for (int xn = 0; xn < sizeSn; xn++) {
					if(Sn[sn] > Constante.debitMaxTurbine || Sn[xn] > Constante.debitMaxTurbine ) {
						FnInter[sn][xn] = 0;
					}else if (sn - xn < 0) {
						// zone non remplissable du tableau
						FnInter[sn][xn] = -1;
					} else {
						FnInter[sn][xn] = listeTurbines.get(turbine).getGn(xn) + listeTurbines.get(turbine+1).getFnmax(sn-xn);
					}
					System.out.println("turbine: "+ turbine + " Fninter: " + FnInter[sn][xn] + " sn :" + sn + " xn: " + xn +" Fnmax: " + listeTurbines.get(turbine).getFnmax(sn));
				

					// Trouver les maxi: reconstruction de Fnmax pour le calcul de la turbine
					// précédente et Recupération du débit pour le quel Fnmax est maximum (pour l'utiliser dans le
					// forward pass)
					if (listeTurbines.get(turbine).getFnmax(sn) < FnInter[sn][xn]) {
						// Récupération du maximum
						listeTurbines.get(turbine).setFnmax(FnInter[sn][xn], sn);
						// Récupération du débit correspondant au maximum
						listeTurbines.get(turbine).setXn(Sn[xn],sn);
					}
					//System.out.println("turbine: " + turbine +" Fnmax: " + turbineCourante.getFnmax(sn) + " qn: " + turbineCourante.getXn(sn));
				}
			}
		}

		// Turbine 1:
		FnInter[sizeSn - 1][0] = listeTurbines.get(1).getGn(0) + listeTurbines.get(2).getFnmax(sizeSn - 1);
		listeTurbines.get(1).setFnmax(FnInter[sizeSn - 1][0],sizeSn - 1);
		listeTurbines.get(1).setXn(Sn[0], sizeSn - 1);
		System.out.println("turbine: " + 1 +" Fnmax: " + listeTurbines.get(1).getFnmax(0) + " qn: " + listeTurbines.get(1).getXn(0));
		for (int xn = 1; xn < sizeSn; xn++) {
			if(Sn[xn] > Constante.debitMaxTurbine) {
				FnInter[sizeSn - 1][xn] = 0;
			}else {
				FnInter[sizeSn - 1][xn] = listeTurbines.get(1).getGn(xn) + listeTurbines.get(2).getFnmax(sizeSn - 1 - xn);
			}
			if (FnInter[sizeSn - 1][xn - 1] < FnInter[sizeSn - 1][xn]) {
				// Récupération du maximum
				listeTurbines.get(1).setFnmax(FnInter[sizeSn - 1][xn], sizeSn - 1);
				// Récupération du débit correspondant au maximum
				listeTurbines.get(1).setXn(Sn[xn], sizeSn - 1);
			}
			System.out.println("turbine: " + 1 +" Fnmax: " + listeTurbines.get(1).getFnmax(xn) + " qn: " + listeTurbines.get(1).getXn(xn));
		}


	/*
		for (int turbine = 1; turbine < listeTurbines.size(); turbine++) {
			Turbine tur = listeTurbines.get(turbine);
			for (int xn = 1; xn < sizeSn; xn++) {
				//System.out.println("turbine: "+tur.getId()+"  FnMax: "  + tur.getFnmax(xn) + " sn : " + xn+ " QnMax: " + tur.getXn(xn));
			}
		}*/
		
		
		// Forward pass

		int Q1 = turbine1.getXn(sizeSn-1);
		int index = Q1/5 > 0 ? Q1/5 : 0;
		double P1 = turbine1.getGn(index);
		int debitRestant = Constante.qtot - Q1;
		System.out.println("q1 = "  + Q1);
		System.out.println(" debit restant : " + debitRestant );
		System.out.println("P1 : " + P1);

		int debitInter = debitRestant>Constante.debitMaxTurbine ? Constante.debitMaxTurbine:debitRestant;
		index = debitInter/5 > 0 ? debitInter/5 : 0;
		
		int Q2 = turbine2.getXn(index);
		index =   Q2/5 > 0 ? Q2/5 : 0;
		double P2 = turbine2.getGn(index);
		debitRestant -= Q2;
		System.out.println("q2 = "  + Q2);
		System.out.println(" debit restant : " + debitRestant );
		System.out.println("P2 : " + P2);

		debitInter = debitRestant>Constante.debitMaxTurbine ? Constante.debitMaxTurbine:debitRestant;
		index = debitInter/5 > 0 ? debitInter/5 : 0;
		
		int Q3 = turbine3.getXn(index);
		index =   Q3/5 > 0 ? Q3/5 : 0;
		double P3 = turbine3.getGn(index);
		debitRestant -= Q3;
		System.out.println("q3 = "  + Q3);
		System.out.println(" debit restant : " + debitRestant );
		System.out.println("P3 : " + P3);
		
		debitInter = debitRestant>Constante.debitMaxTurbine ? Constante.debitMaxTurbine:debitRestant;
		index = debitInter/5 > 0 ? debitInter/5 : 0;
		
		int Q4 = turbine4.getXn(index);
		index =   Q4/5 > 0 ? Q4/5 : 0;
		double P4 = turbine4.getGn(index);
		debitRestant -= Q4;
		System.out.println("q4 = "  + Q4);
		System.out.println(" debit restant : " + debitRestant );
		System.out.println("P4 : " + P4);
		
		debitInter = debitRestant>Constante.debitMaxTurbine ? Constante.debitMaxTurbine:debitRestant;
		index = debitInter/5 > 0 ? debitInter/5 : 0;
		
		int Q5 = turbine5.getXn(index);
		index =   Q5/5 > 0 ? Q5/5 : 0;
		double P5 = turbine5.getGn(index);
		debitRestant -= Q5;
		System.out.println("q5 = "  + Q5);
		System.out.println(" debit restant : " + debitRestant );
		System.out.println("P5 : " + P5);
		
		double Ptot = P1 + P2 + P3 + P4 + P5;
		System.out.println("Puissance max opti:  " + Ptot);
	}



	
}