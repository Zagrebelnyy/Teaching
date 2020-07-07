import com.orsoncharts.util.json.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    private static String chartTitle = "График ретроспективной динамики курса доллара к рублю";
    private static ParseHTML parseHTML;
    private static Chart chart;
    private static String timePeriod[] = {"День", "Неделя", "Месяц", "Год"};
    private static JTextField jTextFieldBegin;
    private static JTextField jTextFieldEnd;
    private static JFrame jFrame;
    private static JPanel jPanel;
    private static JLabel jLabel = new JLabel("Данные введены неверно");
    private static ArrayList<Double> YAxis = new ArrayList<Double>();
    private static ArrayList<String> XAxis = new ArrayList<String>();

    public static void main(String[] args) throws IOException, ParseException {
        jFrame = new JFrame();
        jPanel = new JPanel();
        JButton jButton = new JButton("Построить график");
        String url = "https://zayman.ru/currency/usd?range=10year";
        String elements = "table.currency-table";
        String element = "td";
        parseHTML = new ParseHTML(url, elements, element);
        JComboBox jComboBox = new JComboBox(timePeriod);
        for (int i = 3; i < parseHTML.getData().size(); i++) {
            if ((i % 3) == 0) {
                XAxis.add(parseHTML.getData().get(i).toString());
            } else {
                YAxis.add(Double.parseDouble(parseHTML.getData().get(i).toString()));
                i++;
            }
        }
        for(int i = 0; i < XAxis.size();i++){
            System.out.println(XAxis.get(i));
        }
        for(int i = 0; i < XAxis.size();i++){
            System.out.println(YAxis.get(i));
        }
        jTextFieldBegin = new JTextField(XAxis.get(XAxis.size() - 1));
        jTextFieldEnd = new JTextField(XAxis.get(0));
        jComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int itrBegin = -1;
                int itrEnd = -1;
                for (int i = 0; i < XAxis.size(); i++) {
                    if (itrEnd == -1 && jTextFieldEnd.getText().equals(XAxis.get(i))) {
                        itrEnd = i;
                    }
                    if (itrEnd != -1 && jTextFieldBegin.getText().equals(XAxis.get(i))) {
                        itrBegin = i;
                    }

                }
                if (itrBegin == -1 || itrEnd == -1) {
                    jLabel.setVisible(true);
                } else {
                    jLabel.setVisible(false);
                    jButton.setEnabled(true);
                    switch (jComboBox.getSelectedItem().toString()) {
                        case "День":
                            chart = new Chart(chartTitle,
                                    "Дата",
                                    "Курс доллара в рублях",
                                    getXAxisDay(itrBegin, itrEnd),
                                    getYAxisDay(itrBegin, itrEnd));
                            break;
                        case "Неделя":
                            ArrayList<String> XAxisWeek = new ArrayList<>();
                            ArrayList<Double> YAxisWeek = new ArrayList<>();
                            Double courseWeek = 0.0;
                            int countWeek = 0;
                            for (int i = 0; i < getXAxisDay(itrBegin, itrEnd).size(); i++) {
                                countWeek++;
                                courseWeek += Double.parseDouble(getYAxisDay(itrBegin, itrEnd).get(i).toString());
                                if (countWeek == 7) {
                                    XAxisWeek.add(getXAxisDay(itrBegin, itrEnd).get(i));
                                    YAxisWeek.add(courseWeek / 7);
                                    countWeek = 0;
                                    courseWeek = 0.0;
                                }
                                chart = new Chart(chartTitle,
                                        "Дата",
                                        "Курс доллара в рублях",
                                        XAxisWeek,
                                        YAxisWeek);
                            }
                            break;
                        case "Месяц":
                            ArrayList<String> XAxisMonth = new ArrayList<>();
                            ArrayList<Double> YAxisMonth = new ArrayList<>();
                            Double courseMonth = 0.0;
                            int countMonth = 0;
                            for (int i = 0; i < getXAxisDay(itrBegin, itrEnd).size(); i++) {
                                countMonth++;
                                courseMonth += Double.parseDouble(getYAxisDay(itrBegin, itrEnd).get(i).toString());
                                if (countMonth == 30) {
                                    XAxisMonth.add(parseHTML.getData().get(i).toString());
                                    YAxisMonth.add(courseMonth / 30);
                                    countMonth = 0;
                                    courseMonth = 0.0;
                                }
                            }
                            chart = new Chart(chartTitle,
                                    "Дата",
                                    "Курс доллара в рублях",
                                    XAxisMonth,
                                    YAxisMonth);
                            break;
                        case "Год":
                            ArrayList<String> XAxisYear = new ArrayList<>();
                            ArrayList<Double> YAxisYear = new ArrayList<>();
                            Double courseYear = 0.0;
                            int countYear = 0;
                            for (int i = 0; i < getXAxisDay(itrBegin, itrEnd).size(); i++) {
                                countYear++;
                                courseYear += Double.parseDouble(getYAxisDay(itrBegin, itrEnd).get(i).toString());
                                if (countYear == 247) {
                                    XAxisYear.add(getXAxisDay(itrBegin, itrEnd).get(i));
                                    YAxisYear.add(courseYear / 364);
                                    countYear = 0;
                                    courseYear = 0.0;

                                }
                            }
                            chart = new Chart(chartTitle,
                                    "Дата",
                                    "Курс доллара в рублях",
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
        jPanel.add(jButton);
        jPanel.add(jComboBox);
        jPanel.add(jTextFieldBegin);
        jPanel.add(jTextFieldEnd);
        jFrame.add(jPanel);
        jFrame.pack();
        jFrame.setSize(600, 400);
        jFrame.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    private static ArrayList<Double> getYAxisDay(int itrBegin, int itrEnd) {
        ArrayList<Double> YAxisDay = new ArrayList<>();
        for (int i = itrEnd; i < itrBegin; i++) {
            YAxisDay.add(YAxis.get(i));
        }
        return YAxisDay;
    }

    private static ArrayList<String> getXAxisDay(int itrBegin, int itrEnd) {
        ArrayList<String> XAxisDay = new ArrayList<>();
        for (int i = itrEnd; i < itrBegin; i++) {
            XAxisDay.add(XAxis.get(i));
        }
        return XAxisDay;
    }
}