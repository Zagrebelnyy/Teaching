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

    private DefaultCategoryDataset createDataset(ArrayList<Double> courseParse, ArrayList<String> dateParse) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String series = "Vistor";
        for (int i = 0; i < courseParse.size(); i++) {
            dataset.addValue(courseParse.get(i), series, dateParse.get(i));
        }

//        String series1 = "series1";
//        String series2 = "series2";
//        String series3 = "series3";
//        int itr1 = 0;
//        int itr2 = 0;
//        int container1 = 0;
//        for(int i = 0; i < courseParse.size(); i++){
//            if(itr1 == 10){
//                dataset.addValue(courseParse.get(itr1), series2, dateParse.get(i));
//                itr1++;
//                itr1 = 0;
//            }
//            if((i % 5) == 0){
//                dataset.addValue(courseParse.get(itr2), series3, dateParse.get(i));
//                itr2++;
//            }
//            dataset.addValue(courseParse.get(i), series1, dateParse.get(i));
//            itr1++;
//            itr2++;
//            container1 +=courseParse.get(i);
//
//        }

        return dataset;
    }

    public void maiWindow(Chart chart) {
        chart.setAlwaysOnTop(true);
        chart.setSize(600, 400);
        chart.setVisible(true);
    }
}