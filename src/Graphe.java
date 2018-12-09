
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleEdge;

public class Graphe extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	NumberFormat nf = new DecimalFormat("0.##");

	public Graphe(boolean pie) {

		if (pie) {
			PieDataset dataset2 = createDataset2();
			JFreeChart pieChart = createChart2(dataset2);
			ChartPanel pieChartPanel = new ChartPanel(pieChart);
			pieChart.getLegend().setPosition(RectangleEdge.RIGHT);
			pieChartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			add(pieChartPanel);
			setTitle("Pie chart");
			setLocation(50, 50);

		} else {
			CategoryDataset dataset = createDataset();
			JFreeChart chart = createChart(dataset);
			ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			chartPanel.setBackground(Color.white);
			add(chartPanel);
			setTitle("Bar chart");
			setLocation(600, 200);

		}

		pack();
		setVisible(true);

	}

	private CategoryDataset createDataset() {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(Algo.getInterfaceTurbines().get(0).getDebitFinal(), "D�bit turbin�", "Turbine1");
		dataset.setValue(Algo.getInterfaceTurbines().get(1).getDebitFinal(), "D�bit turbin�", "Turbine2");
		dataset.setValue(Algo.getInterfaceTurbines().get(2).getDebitFinal(), "D�bit turbin�", "Turbine3");
		dataset.setValue(Algo.getInterfaceTurbines().get(3).getDebitFinal(), "D�bit turbin�", "Turbine4");
		dataset.setValue(Algo.getInterfaceTurbines().get(4).getDebitFinal(), "D�bit turbin�", "Turbine5");

		return dataset;
	}

	private PieDataset createDataset2() {
		Algo.algo();

		DefaultPieDataset pieDataset = new DefaultPieDataset();


		String p1 = nf.format(Algo.getInterfaceTurbines().get(0).getPuissanceFinale());
		String p2 = nf.format(Algo.getInterfaceTurbines().get(1).getPuissanceFinale());
		String p3 = nf.format(Algo.getInterfaceTurbines().get(2).getPuissanceFinale());
		String p4 = nf.format(Algo.getInterfaceTurbines().get(3).getPuissanceFinale());
		String p5 = nf.format(Algo.getInterfaceTurbines().get(4).getPuissanceFinale());

		String percent1 = nf.format(percent(Algo.getInterfaceTurbines().get(0).getPuissanceFinale()));
		String percent2 = nf.format(percent(Algo.getInterfaceTurbines().get(1).getPuissanceFinale()));
		String percent3 = nf.format(percent(Algo.getInterfaceTurbines().get(2).getPuissanceFinale()));
		String percent4 = nf.format(percent(Algo.getInterfaceTurbines().get(3).getPuissanceFinale()));
		String percent5 = nf.format(percent(Algo.getInterfaceTurbines().get(4).getPuissanceFinale()));

		String t1 = "Puissance turbine 1 :\n " + p1 + " MW" + "\n " + percent1 + " %";
		String t2 = "Puissance turbine 2 :\n " + p2 + " MW" + "\n " + percent2 + " %";
		String t3 = "Puissance turbine 3 :\n " + p3 + " MW" + "\n " + percent3 + " %";
		String t4 = "Puissance turbine 4 :\n " + p4 + " MW" + "\n " + percent4 + " %";
		String t5 = "Puissance turbine 5 :\n " + p5 + " MW" + "\n " + percent5 + " %";

		pieDataset.setValue(t1, Algo.getInterfaceTurbines().get(0).getPuissanceFinale());

		pieDataset.setValue(t2, Algo.getInterfaceTurbines().get(1).getPuissanceFinale());

		pieDataset.setValue(t3, Algo.getInterfaceTurbines().get(2).getPuissanceFinale());

		pieDataset.setValue(t4, Algo.getInterfaceTurbines().get(3).getPuissanceFinale());

		pieDataset.setValue(t5, Algo.getInterfaceTurbines().get(4).getPuissanceFinale());

		return pieDataset;
	}

	private JFreeChart createChart(CategoryDataset dataset) {

		JFreeChart barChart = ChartFactory.createBarChart("R�partition du d�bit pour chacune des turbines centrale CDD",

				" ", "D�bit turbin� en m^3/s", dataset, PlotOrientation.VERTICAL, false, true, false);

		return barChart;
	}

	private JFreeChart createChart2(PieDataset dataset2) {

		JFreeChart pieChart = ChartFactory.createPieChart("Distribution de la puissance en MW\nPuissance totale: "+nf.format(Algo.getPuissancetotale())+" MW", dataset2);

		return pieChart;
	}

	private double percent(double p) {

		return p * 100 / Algo.getPuissancetotale();

	}

}