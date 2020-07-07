import com.orsoncharts.util.json.parser.ParseException;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    private static ParseHTML parseHTML;
    private static Chart chart;
    private static String timePeriod[] = {"День", "Неделя", "Месяц", "Год"};
    private static JTextField jTextFieldBegin = new JTextField("01.01.2009");
    private static JTextField jTextFieldEnd = new JTextField("31.12.2019");
    private static JFrame jFrame;
    private static JPanel jPanel;
    private static JLabel jLabel = new JLabel("Данные введены неверно");
    private static JButton jButton = new JButton("Построить график");
    private static ArrayList<Double> YAxis = new ArrayList<Double>();
    private static ArrayList<String> XAxis = new ArrayList<String>();

    public static void main(String[] args) throws IOException, ParseException {
        jFrame = new JFrame();
        jPanel = new JPanel();
        jButton.setEnabled(false);
        for (int i = 2009; i <= 2017; i += 2) {
            String url = "http://pogoda-service.ru/archive_gsod_res.php?" +
                    "country=RS&station=276120&datepicker_beg=01.01." + i + "&datepicker_end=31.12." + (i + 2) +
                    "&bsubmit=%D0%9F%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%B5%D1%82%D1%8C";
            String elements = "table.table_res";
            String element = "td";
            parseHTML = new ParseHTML(url, elements, element);
            int itr = 3;
            for (int j = 0; j < parseHTML.getData().size(); j++) {
                if (j % 8 == 0 || j == 0) {
                    XAxis.add(parseHTML.getData().get(j).toString());
                }
                if (j == itr) {
                    YAxis.add(Double.parseDouble(parseHTML.getData().get(j).toString()));
                    itr += 8;
                }
            }
        }
        JComboBox jComboBox = new JComboBox(timePeriod);
        jPanel.add(jTextFieldBegin);
        jPanel.add(jTextFieldEnd);
        jPanel.add(jButton);
        jPanel.add(jComboBox);
        jLabel.setVisible(false);
        jPanel.add(jLabel);
        jComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int itrBegin = -1;
                int itrEnd = -1;
                for (int i = 0; i < XAxis.size(); i++) {
                    if (itrBegin == -1 && jTextFieldBegin.getText().equals(XAxis.get(i))) {
                        itrBegin = i;
                    }
                    if (itrBegin != -1 && jTextFieldEnd.getText().equals(XAxis.get(i))) {
                        itrEnd = i;
                    }
                }
                if (itrBegin == -1 || itrEnd == -1) {
                    jLabel.setVisible(true);
                } else {
                    jLabel.setVisible(false);
                    jButton.setEnabled(true);
                    switch (jComboBox.getSelectedItem().toString()) {
                        case "День":
                            chart = new Chart("График погоды на основании метеорологических данных в городе Москва",
                                    "Дата",
                                    "Средняя температура",
                                    getXAxisDay(itrBegin, itrEnd),
                                    getYAxisDay(itrBegin, itrEnd));
                            break;
                        case "Неделя":
                            ArrayList<String> XAxisWeek = new ArrayList<>();
                            ArrayList<Double> YAxisWeek = new ArrayList<>();
                            Double courseWeek = 0.0;
                            int countWeek = 0;
                            for (int i = 0; i < getXAxisDay(itrBegin, itrEnd).size(); i++) {
                                courseWeek += Double.parseDouble(getYAxisDay(itrBegin, itrEnd).get(i).toString());
                                countWeek++;
                                if (countWeek == 7) {
                                    XAxisWeek.add(getXAxisDay(itrBegin, itrEnd).get(i));
                                    YAxisWeek.add(courseWeek / 7);
                                    countWeek = 0;
                                    courseWeek = 0.0;
                                }
                            }
                            chart = new Chart("График погоды на основании метеорологических данных в городе Москва",
                                    "Дата",
                                    "Средняя температура",
                                    XAxisWeek,
                                    YAxisWeek);
                            break;
                        case "Месяц":
                            ArrayList<String> XAxisMonth = new ArrayList<>();
                            ArrayList<Double> YAxisMonth = new ArrayList<>();
                            Double courseMonth = 0.0;
                            int countMonth = 0;
                            for (int i = 0; i < getXAxisDay(itrBegin, itrEnd).size(); i++) {
                                courseMonth += Double.parseDouble(getYAxisDay(itrBegin, itrEnd).get(i).toString());
                                countMonth++;
                                if (countMonth == 30) {
                                    XAxisMonth.add(getXAxisDay(itrBegin, itrEnd).get(i));
                                    YAxisMonth.add(courseMonth / 30);
                                    countMonth = 0;
                                    courseMonth = 0.0;
                                }
                            }
                            chart = new Chart("График погоды на основании метеорологических данных в городе Москва",
                                    "Дата",
                                    "Средняя температура",
                                    XAxisMonth,
                                    YAxisMonth);
                            break;
                        case "Год":
                            ArrayList<String> XAxisYear = new ArrayList<>();
                            ArrayList<Double> YAxisYear = new ArrayList<>();
                            Double courseYear = 0.0;
                            int countYear = 0;
                            for (int i = 3; i < getXAxisDay(itrBegin, itrEnd).size(); i++) {
                                courseYear += Double.parseDouble(getYAxisDay(itrBegin, itrEnd).get(i).toString());
                                countYear++;
                                if (countYear == 364) {
                                    XAxisYear.add(getXAxisDay(itrBegin, itrEnd).get(i));
                                    YAxisYear.add(courseYear / 364);
                                    countYear = 0;
                                    courseYear = 0.0;
                                }
                            }
                            chart = new Chart("График погоды на основании метеорологических данных в городе Москва",
                                    "Дата",
                                    "Средняя температура",
                                    XAxisYear,
                                    YAxisYear);
                            break;
                    }
                }
            }
        });

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chart.maiWindow(chart);
            }
        });
        jFrame.add(jPanel);
        jFrame.pack();
        jFrame.setSize(700, 100);
        jFrame.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }


    private static ArrayList<Double> getYAxisDay(int itrBegin, int itrEnd) {
        ArrayList<Double> YAxisDay = new ArrayList<>();
        for (int i = itrBegin; i < itrEnd; i++) {
            YAxisDay.add(YAxis.get(i));
        }
        return YAxisDay;
    }

    private static ArrayList<String> getXAxisDay(int itrBegin, int itrEnd) {
        ArrayList<String> XAxisDay = new ArrayList<>();
        for (int i = itrBegin; i < itrEnd; i++) {
            XAxisDay.add(XAxis.get(i));
        }
        return XAxisDay;
    }
}


