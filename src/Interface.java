
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
		new Interface().setVisible(true);
		
	}

	// Instanciation d'un objet JPanel
	JPanel panel = new JPanel();
	// ImageIcon background = new ImageIcon("latuque-01.jpg");
	// JLabel label = new JLabel();

//	ImageIcon img = new ImageIcon("latuque-01.jpg");
//	JLabel background = new JLabel("", img, JLabel.CENTER);

	JButton boutonCalculer = new JButton("Calculer");
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
		this.setSize(800, 548);
		// Termine le processus lorsqu'on clique sur la croix rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// Disposition !!
		this.setLayout(new GridLayout(2, 4));

		boutonCalculer.addActionListener(new BoutonListener());
		panel.add(boutonCalculer);
//		background.setBounds(0, 0, 800, 548);
//		add(background);
		// label.setIcon(background);
		// panel.add(label);

		// Paramètres
		// Débit total
		JPanel panelDebit = new JPanel();
		JLabel debitTotal = new JLabel("Débit total : ");
		jtf.setPreferredSize(new Dimension(100, 20));
		panelDebit.add(debitTotal);
		panelDebit.add(jtf);

		// Elévation avale

		JPanel panelElav = new JPanel();
		JLabel elav = new JLabel("Elévation amont : ");
		jtf2.setPreferredSize(new Dimension(100, 20));
		panelElav.add(elav);
		panelElav.add(jtf2);

		// Débit maximal par turbine à définir par l'utilisateur

		JPanel DebitMax = new JPanel();
		JLabel DbMax = new JLabel("Débit maximale turbine : ");
		jtf3.setPreferredSize(new Dimension(100, 20));
		DebitMax.add(DbMax);
		DebitMax.add(jtf3);
		
		//CheckBox
		JPanel top = new JPanel();
		JLabel checkLabel = new JLabel("Turbine désactivée : ");
		top.add(checkLabel);
	    
	    top.add(check1);
	    top.add(check2);
	    top.add(check3);
	    top.add(check4);
	    top.add(check5);
		
		// Dans l'ordre pour remplir la GridLayout
		/* (0,0) */ this.getContentPane().add(panelDebit);
		/* (1,0) */ this.getContentPane().add(panelElav);
		/* (1,1) */ this.getContentPane().add(DebitMax);
		/* (0,1) */ this.getContentPane().add(panel);
		this.getContentPane().add(top);

	}

	class BoutonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Param
			if(jtf.getText().length() != 0 && jtf.getText().length() != 0 && jtf.getText().length() != 0) {
			Constante.qtot = Integer.parseInt(jtf.getText());
			Constante.elam = Double.parseDouble(jtf2.getText());
			Constante.debitMaxTurbine = Integer.parseInt(jtf3.getText());
			SwingUtilities.invokeLater(() -> {
	            Graphe ex = new Graphe();
	            ex.setVisible(true);
	            //Algo.algo();
	            
	            });
			}else {
				System.out.println("wallah c pas bon");
				SwingUtilities.invokeLater(() -> {
		            Graphe ex = new Graphe();
		            ex.setVisible(true);
		            //Algo.algo();
		            
		            });
				return;
			}
			
			if(Constante.qtot < Constante.debitMaxTurbine) {
				System.out.println("wallah c pas bon");
				SwingUtilities.invokeLater(() -> {
		            Graphe ex = new Graphe();
		            ex.setVisible(true);
		            //Algo.algo();
		            
		            });
				return;
			}
			//Turbine desactivee
			Constante.turbine1 = check1.isSelected();
			Constante.turbine2 = check2.isSelected();
			Constante.turbine3 = check3.isSelected();
			Constante.turbine4 = check4.isSelected();
			Constante.turbine5 = check5.isSelected();
			System.out.print(check1.isSelected());
			
			//Lancement algo
			Algo.algo();
			 ArrayList<Turbine> turbines = Algo.getInterfaceTurbines();
			 Turbine turbine1 = turbines.get(0);
			 Turbine turbine2 = turbines.get(1);
			 Turbine turbine3 = turbines.get(2);
			 Turbine turbine4 = turbines.get(3);
			 Turbine turbine5 = turbines.get(4);
		}
	}

}