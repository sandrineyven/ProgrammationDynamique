
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Interface extends JFrame {
	// Instanciation d'un objet JPanel
	JPanel panel = new JPanel();
	//ImageIcon background = new ImageIcon("latuque-01.jpg");
	//JLabel label = new JLabel();
	
	ImageIcon img = new ImageIcon("latuque-01.jpg");
	JLabel background = new JLabel("", img, JLabel.CENTER);
	
	
	JPanel container = new JPanel();
	GridLayout gl = new GridLayout(3, 3, 5, 5);

	public Interface() {

		// Définit un titre pour notre fenêtre
		this.setTitle("Outil optimisation");
		// Définit sa taille : 800 pixels de large et 548 pixels de haut
		this.setSize(800, 548);
		// Termine le processus lorsqu'on clique sur la croix rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(2, 2));

		panel.add(new JButton("Calculer"));
		background.setBounds(0,0,800,548);
		add(background);
		//label.setIcon(background);
		//panel.add(label);

		// Paramètres
		// Débit total
		JPanel panelDebit = new JPanel();
		JLabel debitTotal = new JLabel("Débit total : ");
		JTextField jtf = new JTextField("");
		jtf.setPreferredSize(new Dimension(100, 20));
		panelDebit.add(debitTotal);
		panelDebit.add(jtf);

		// Elévation avale

		JPanel panelElav = new JPanel();
		JLabel elav = new JLabel("Elévation aval : ");
		JTextField jtf2 = new JTextField("");
		jtf2.setPreferredSize(new Dimension(100, 20));
		panelElav.add(elav);
		panelElav.add(jtf2);
		
		// Débit maximal par turbine à définir par l'utilisateur

		JPanel DebitMax = new JPanel();
		JLabel DbMax = new JLabel("Débit maximale turbine : ");
		JTextField jtf3 = new JTextField("");
		jtf3.setPreferredSize(new Dimension(100, 20));
		DebitMax.add(DbMax);
		DebitMax.add(jtf3);

		// On prévient notre JFrame que notre JPanel sera son content pane

		// Dans l'ordre pour remplir la GridLayout
		/* (0,0) */ this.getContentPane().add(panelDebit);
		/* (0,1) */ this.getContentPane().add(panel);
		/* (1,0) */ this.getContentPane().add(panelElav);
		/* (1,1) */ this.getContentPane().add(DebitMax);

		this.setVisible(true);

	}

}
