
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Interface extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new Interface();

	}

	// Instanciation d'un objet JPanel
	JPanel panel = new JPanel();

	JButton boutonCalculer = new JButton("Calculer");
	JButton boutonExcel = new JButton("Excel");
	JTextField jtf = new JTextField("");
	JTextField jtf2 = new JTextField("");
	JTextField jtf3 = new JTextField("");
	JCheckBox check1 = new JCheckBox("Turbine 1");
	JCheckBox check2 = new JCheckBox("Turbine 2");
	JCheckBox check3 = new JCheckBox("Turbine 3");
	JCheckBox check4 = new JCheckBox("Turbine 4");
	JCheckBox check5 = new JCheckBox("Turbine 5");

	public Interface() {

		// Définit un titre pour notre fenêtre
		this.setTitle("Outil optimisation");
		// Définit sa taille : 800 pixels de large et 548 pixels de haut
		this.setSize(320, 400);
		// Termine le processus lorsqu'on clique sur la croix rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// Disposition !!
		this.setLayout(new GridLayout(6, 2));

		boutonCalculer.addActionListener(new BoutonListener());
		panel.add(boutonCalculer);

		// Paramètres
		// Débit total
		JPanel panelDebit = new JPanel();
		JPanel panelDebitTexte = new JPanel();
		JLabel debitTotal = new JLabel("Débit total : ");
		jtf.setPreferredSize(new Dimension(100, 20));
		panelDebit.add(debitTotal);
		panelDebitTexte.add(jtf);

		// Elévation avale

		JPanel panelElav = new JPanel();
		JPanel panelElavTexte = new JPanel();
		JLabel elav = new JLabel("Elévation amont : ");
		jtf2.setPreferredSize(new Dimension(100, 20));
		panelElav.add(elav);
		panelElavTexte.add(jtf2);

		// Débit maximal par turbine à définir par l'utilisateur

		JPanel DebitMax = new JPanel();
		JPanel DebitMaxTexte = new JPanel();
		JLabel DbMax = new JLabel("Débit maximale turbine : ");
		jtf3.setPreferredSize(new Dimension(100, 20));
		DebitMax.add(DbMax);
		DebitMaxTexte.add(jtf3);

		// CheckBox
		JPanel top = new JPanel();
		JLabel checkLabel = new JLabel("Turbine désactivée : ");
		top.add(checkLabel);

		JPanel blanc = new JPanel();

		JPanel top2 = new JPanel();
		top2.add(check1);
		top2.add(check2);
		JPanel top3 = new JPanel();
		top3.add(check3);
		top3.add(check4);
		JPanel top4 = new JPanel();
		top4.add(check5);

		this.getContentPane().add(panelDebit);
		this.getContentPane().add(panelDebitTexte);
		this.getContentPane().add(panelElav);
		this.getContentPane().add(panelElavTexte);
		this.getContentPane().add(DebitMax);
		this.getContentPane().add(DebitMaxTexte);

		this.getContentPane().add(top);
		this.getContentPane().add(blanc);
		this.getContentPane().add(top2);
		this.getContentPane().add(top3);
		this.getContentPane().add(top4);
		this.getContentPane().add(blanc);
		this.getContentPane().add(panel, BorderLayout.CENTER);

		setVisible(true);

	}

	class BoutonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Récupération des Parametres
			if (jtf.getText().length() > 0 && jtf.getText().length() > 0 && jtf.getText().length() > 0) {
				// Débit total
				Constante.qtot = Integer.parseInt(jtf.getText());
				// Elevation amont
				Constante.elam = Double.parseDouble(jtf2.getText());
				// Débit maximal pour une turbine
				Constante.debitMaxTurbine = Integer.parseInt(jtf3.getText());
				// Turbine desactivee
				Constante.turbine1 = check1.isSelected();
				Constante.turbine2 = check2.isSelected();
				Constante.turbine3 = check3.isSelected();
				Constante.turbine4 = check4.isSelected();
				Constante.turbine5 = check5.isSelected();
				if (Constante.qtot < Constante.debitMaxTurbine) {
					// Si le débit total est plus petit que le débit maximal d'une turbine
					System.out.println("Le débit total est trop peu élevé.");
					return;
				}

				// Tout est ok on peut lancer l'algo

				SwingUtilities.invokeLater(() -> {
					Algo.algo();
					new Graphe(false);
					new Graphe(true);
				});

			} else {
				// Si les paramètres sont incorrects
				System.out.println("Veuillez remplir les champs demandés.");
				return;
			}

		}
	}

}