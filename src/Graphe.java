
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

public class Graphe extends JFrame {
    
    public Graphe() {
        
        initUI();
    }

    private void initUI() {

        CategoryDataset dataset = createDataset();

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset createDataset() {
    	Algo.algo();
    	
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(Algo.getInterfaceTurbines().get(0).getPuissanceFinale(), "Débit turbiné", "Turbine1");
        dataset.setValue(Algo.getInterfaceTurbines().get(1).getPuissanceFinale(), "Débit turbiné", "Turbine2");
        dataset.setValue(Algo.getInterfaceTurbines().get(2).getPuissanceFinale(), "Débit turbiné", "Turbine3");
        dataset.setValue(Algo.getInterfaceTurbines().get(3).getPuissanceFinale(), "Débit turbiné", "Turbine4");
        dataset.setValue(Algo.getInterfaceTurbines().get(4).getPuissanceFinale(), "Débit turbiné", "Turbine5");

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
    	
    	Algo.algo();

        JFreeChart barChart = ChartFactory.createBarChart(
                "Débit turbiné par chacune des turbines centrale CDD",
                "",
                "Débit turbiné",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }

    

}