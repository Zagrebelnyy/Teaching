import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


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

    private  DefaultCategoryDataset createDataset(ArrayList<Double> courseParse, ArrayList<String> dateParse) {
        String series1 = "Series1";
        String series2 = "Series2";
        String series3 = "Series3";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        double avg10days = 0;
        double avg100days = 0;
        for (int i = 0; i < courseParse.size(); i++) {
            dataset.addValue(courseParse.get(i), series1, dateParse.get(i));
            avg10days += courseParse.get(i);
            avg100days += courseParse.get(i);
            if(i%10==0 && i != 0){
                dataset.addValue(avg10days/10, series2, dateParse.get(i));
                avg10days = 0;
            } if(i%100==0 && i != 0){
                dataset.addValue(avg100days/100, series3, dateParse.get(i));
                avg100days = 0;
            }

        }

        return dataset;
    }

    public void maiWindow(Chart chart) {
        chart.setAlwaysOnTop(true);
        chart.setSize(600, 400);
        chart.setVisible(true);
    }
}