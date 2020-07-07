import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


import javax.swing.*;
import java.util.ArrayList;


public class Chart extends JFrame {
    public Chart(String name,
                 String categoryAxisLabel,
                 String valueAxisLabel,
                 ArrayList<String> dateParse,
                 ArrayList<Double> courseParse) {
        super("Chart");
        DefaultCategoryDataset dataset = createDataset(courseParse, dateParse);
        JFreeChart chart = ChartFactory.createLineChart(
                name, // Chart title
                categoryAxisLabel, // X-Axis Label
                valueAxisLabel, // Y-Axis Label
                dataset
        );
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private DefaultCategoryDataset createDataset(ArrayList<Double> courseParse,
                                                 ArrayList<String> dateParse) {
        final String series = "Series" ;

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < courseParse.size(); i++) {
            dataset.addValue(courseParse.get(i), series, dateParse.get(i));
        }
        return dataset;
    }

    public void maiWindow(Chart chart) {
        chart.setAlwaysOnTop(true);
        chart.setSize(600, 400);
        chart.setVisible(true);
    }
}