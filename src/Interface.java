
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Interface extends JFrame {
	//Instanciation d'un objet JPanel
    JPanel p = new JPanel();
    //Ajout de l'image de fond
    ImageIcon i = new ImageIcon("latuque-01.jpg");
    JLabel l = new JLabel();
  //instanciation puis d�finition du libell�
    JButton bouton = new JButton("First try");
    
	public Interface(){
	//D�finit un titre pour notre fen�tre
    this.setTitle("Outil optimisation");
    //D�finit sa taille : 800 pixels de large et 548 pixels de haut
    this.setSize(800, 548);
    
    p.add(bouton);
    l.setIcon(i);
    p.add(l);
  //Ajout du bouton � notre content pane
    
    this.add(p);
    
    //Nous demandons maintenant � notre objet de se positionner au centre
    this.setLocationRelativeTo(null);
    //Termine le processus lorsqu'on clique sur la croix rouge
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Et enfin, la rendre visible        
    this.setVisible(true);

    //On pr�vient notre JFrame que notre JPanel sera son content pane
    this.setContentPane(p);               
    this.setVisible(true);
    
    
    
    
	}
            
}
