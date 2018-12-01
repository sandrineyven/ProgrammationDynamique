import java.util.ArrayList;
import javax.swing.JFrame;
public class Algo {

	public static ArrayList<Turbine> listeTurbines = new ArrayList<Turbine>(6);

	public static void main(String[] args) {
		
		Interface fen = new Interface();

		// Discrétisation de Sn
		int sizeSn = Constante.qtot / 5 + 1;
		int sizeXn = Constante.debitMaxTurbine / 5 + 1;
		int[] Sn = new int[sizeSn];
		int indexSn = 0;
		for (int i = 0; i <= Constante.qtot; i += 5) {
			// Débit restant
			Sn[indexSn] = i;
			indexSn++;
		}

		// Initialisation des turbines - TRUE: ACTIVATION - FALSE: DESACTIVATION
		listeTurbines.add(null);
		Turbine turbine1 = new Turbine(sizeSn, Sn, 1, true);
		if (turbine1.isActive())
			listeTurbines.add(turbine1);
		Turbine turbine2 = new Turbine(sizeSn, Sn, 2, false);
		if (turbine2.isActive())
			listeTurbines.add(turbine2);
		Turbine turbine3 = new Turbine(sizeSn, Sn, 3, true);
		if (turbine3.isActive())
			listeTurbines.add(turbine3);
		Turbine turbine4 = new Turbine(sizeSn, Sn, 4, true);
		if (turbine4.isActive())
			listeTurbines.add(turbine4);
		Turbine turbine5 = new Turbine(sizeSn, Sn, 5, true);
		if (turbine5.isActive())
			listeTurbines.add(turbine5);

		// Backward pass------------------------------------------------------

		// Première passe:
		for (int sn = 1; sn < sizeSn; sn++) {
			if (Sn[sn] > Constante.debitMaxTurbine) {
				listeTurbines.get(listeTurbines.size() - 1)
						.setFnmax(listeTurbines.get(listeTurbines.size() - 1).getGn(Constante.debitMaxTurbine / 5), sn);
				listeTurbines.get(listeTurbines.size() - 1).setXn(Constante.debitMaxTurbine, sn);
			} else {
				listeTurbines.get(listeTurbines.size() - 1)
						.setFnmax(listeTurbines.get(listeTurbines.size() - 1).getGn(sn), sn);
				listeTurbines.get(listeTurbines.size() - 1).setXn(Sn[sn], sn);
			}
		}

		// Turbines intermédiaires:
		// FnInter: Tableau contenant tous les cas possibles
		double[][] FnInter = new double[sizeSn][sizeSn];
		FnInter[0][0] = 0;

		// Pour toutes les turbines intermediaires: (ici de 4 à 2 si toutes actives)
		for (int turbine = listeTurbines.size() - 2; turbine > 1; turbine--) {
			// Case sn =0, xn =0 initialisée à 0:
			listeTurbines.get(turbine).setFnmax(0, 0);
			listeTurbines.get(turbine).setXn(0, 0);
			for (int sn = 1; sn < sizeSn; sn++) {
				for (int xn = 0; xn < sizeXn; xn++) {
					if (sn - xn < 0) {
						// zone non remplissable du tableau
						FnInter[sn][xn] = -1;
					} else {
						FnInter[sn][xn] = listeTurbines.get(turbine).getGn(xn)
								+ listeTurbines.get(turbine + 1).getFnmax(sn - xn);
					}
				}
			}

			// Trouver les maxi + Recupération du débit pour le quel Fnmax est maximum (pour
			// l'utiliser dans le forward pass)
			for (int sn = 1; sn < sizeSn; sn++) {
				for (int xn = 0; xn < sizeXn; xn++) {
					if (listeTurbines.get(turbine).getFnmax(sn) <= FnInter[sn][xn]) {
						// Récupération du maximum
						listeTurbines.get(turbine).setFnmax(FnInter[sn][xn], sn);
						// Récupération du débit correspondant au maximum
						listeTurbines.get(turbine).setXn(Sn[xn], sn);
					}
				}
			}
		}

		// Dernière passe:
		double temp = 0;
		listeTurbines.get(1).setFnmax(0, 0);
		for (int xn = 1; xn < sizeXn; xn++) {
			temp = listeTurbines.get(1).getGn(xn) + listeTurbines.get(2).getFnmax(sizeSn - 1 - xn);
			if (temp >= listeTurbines.get(1).getFnmax(sizeSn - 1)) {
				// Récupération du maximum
				listeTurbines.get(1).setFnmax(temp, sizeSn - 1);
				// Récupération du débit correspondant au maximum
				listeTurbines.get(1).setXn(Sn[xn], sizeSn - 1);
			}
		}

		// Forward pass-----------------------------------------------

		
		//Turbine 1:
		int Q1 = turbine1.getXn(sizeSn - 1);
		int index = Q1 / 5 > 0 ? Q1 / 5 : 0;
		double P1 = turbine1.getGn(index);
		int debitRestant = Constante.qtot - Q1;
		turbine1.setDebitFinal(Q1);
		turbine1.setPuissanceFinale(P1);
		
		index = debitRestant / 5 > 0 ? debitRestant / 5 : 0;

		//Turbine 2:
		int Q2 = turbine2.getXn(index);
		index = Q2 / 5 > 0 ? Q2 / 5 : 0;
		double P2 = turbine2.getGn(index);
		debitRestant -= Q2;
		turbine2.setDebitFinal(Q2);
		turbine2.setPuissanceFinale(P2);

		index = debitRestant / 5 > 0 ? debitRestant / 5 : 0;

		//Turbine 3:
		int Q3 = turbine3.getXn(index);
		index = Q3 / 5 > 0 ? Q3 / 5 : 0;
		double P3 = turbine3.getGn(index);
		debitRestant -= Q3;
		turbine3.setDebitFinal(Q3);
		turbine3.setPuissanceFinale(P3);

		index = debitRestant / 5 > 0 ? debitRestant / 5 : 0;

		//Turbine 4:
		int Q4 = turbine4.getXn(index);
		index = Q4 / 5 > 0 ? Q4 / 5 : 0;
		double P4 = turbine4.getGn(index);
		debitRestant -= Q4;
		turbine4.setDebitFinal(Q4);
		turbine4.setPuissanceFinale(P4);

		index = debitRestant / 5 > 0 ? debitRestant / 5 : 0;

		//Turbine 5:
		int Q5 = turbine5.getXn(index);
		index = Q5 / 5 > 0 ? Q5 / 5 : 0;
		double P5 = turbine5.getGn(index);
		debitRestant -= Q5;
		turbine5.setDebitFinal(Q5);
		turbine5.setPuissanceFinale(P5);

		double Ptot = P1 + P2 + P3 + P4 + P5;
		
		//Affichage des résultats:
		
		System.out.println("Turbine n°1:");
		System.out.println("Q1:  " + turbine1.getDebitFinal());
		System.out.println("P1:  " + turbine1.getPuissanceFinale());
		
		System.out.println("Turbine n°2:");
		System.out.println("Q2:  " + turbine2.getDebitFinal());
		System.out.println("P2:  " + turbine2.getPuissanceFinale());
		
		System.out.println("Turbine n°3:");
		System.out.println("Q3:  " + turbine3.getDebitFinal());
		System.out.println("P3:  " + turbine3.getPuissanceFinale());
		
		System.out.println("Turbine n°4:");
		System.out.println("Q4:  " + turbine4.getDebitFinal());
		System.out.println("P4:  " + turbine4.getPuissanceFinale());
		
		System.out.println("Turbine n°5:");
		System.out.println("Q5:  " + turbine5.getDebitFinal());
		System.out.println("P5:  " + turbine5.getPuissanceFinale());
		
		
		System.out.println("Puissance max opti:  " + Ptot);
		System.out.println("Débit restant:  " + debitRestant);
	}

}