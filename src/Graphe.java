
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;

public class Graphe extends JFrame {
    
    public Graphe() {
        
        initUI();
    }

    private void initUI() {

        CategoryDataset dataset = createDataset();
        //PieDataset dataset2 = createDataset2();
        

        JFreeChart chart = createChart(dataset);
        //JFreeChart pieChart = createChart2(dataset2);
        ChartPanel chartPanel = new ChartPanel(chart);
        //ChartPanel pieChartPanel = new ChartPanel(pieChart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        //pieChartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        //pieChartPanel.setBackground(Color.white);
        add(chartPanel);
        //add(pieChartPanel);

        pack();
        setTitle("Bar chart");
        //setTitle("Pie chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset createDataset() {
    	Algo.algo();
    	
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(Algo.getInterfaceTurbines().get(0).getDebitFinal(), "Débit turbiné", "Turbine1");
        dataset.setValue(Algo.getInterfaceTurbines().get(0).getPuissanceFinale(), "Puissance turbine", "Turbine1");
        dataset.setValue(Algo.getInterfaceTurbines().get(1).getDebitFinal(), "Débit turbiné", "Turbine2");
        dataset.setValue(Algo.getInterfaceTurbines().get(1).getPuissanceFinale(), "Puissance turbine", "Turbine2");
        dataset.setValue(Algo.getInterfaceTurbines().get(2).getDebitFinal(), "Débit turbiné", "Turbine3");
        dataset.setValue(Algo.getInterfaceTurbines().get(2).getPuissanceFinale(), "Puissance turbine", "Turbine3");
        dataset.setValue(Algo.getInterfaceTurbines().get(3).getDebitFinal(), "Débit turbiné", "Turbine4");
        dataset.setValue(Algo.getInterfaceTurbines().get(3).getPuissanceFinale(), "Puissance turbine", "Turbine4");
        dataset.setValue(Algo.getInterfaceTurbines().get(4).getDebitFinal(), "Débit turbiné", "Turbine5");
        dataset.setValue(Algo.getInterfaceTurbines().get(4).getPuissanceFinale(), "Puissance turbine", "Turbine5");
        
 

        return dataset;
    }
    /*private PieDataset createDataset2() {
    	Algo.algo();
    	
        PieDataset dataset2 = (PieDataset) new DefaultCategoryDataset();
        
        dataset2.setValue(Algo.getInterfaceTurbines().get(0).getPuissanceFinale(), "Puissance turbine", "Turbine1");
        dataset2.setValue(Algo.getInterfaceTurbines().get(1).getPuissanceFinale(), "Puissance turbine", "Turbine1");
        dataset2.setValue(Algo.getInterfaceTurbines().get(2).getPuissanceFinale(), "Puissance turbine", "Turbine1");
        dataset2.setValue(Algo.getInterfaceTurbines().get(3).getPuissanceFinale(), "Puissance turbine", "Turbine1");
        dataset2.setValue(Algo.getInterfaceTurbines().get(4).getPuissanceFinale(), "Puissance turbine", "Turbine1");
        
 

        return dataset2;
    }*/

    private JFreeChart createChart(CategoryDataset dataset) {
    	
    	Algo.algo();

        JFreeChart barChart = ChartFactory.createBarChart(
                "Débit turbiné par chacune des turbines centrale CDD",
                
                "Débit turbiné en rouge","Puissance turbine en bleu",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
        
        
        
        return barChart;
    }
    
/*private JFreeChart createChart2(PieDataset dataset2) {
    	
    	Algo.algo();

        JFreeChart pieChart = ChartFactory.createPieChart("Distribution de la puissance", dataset2);
        
        
        
        return pieChart;
    }*/

    

}