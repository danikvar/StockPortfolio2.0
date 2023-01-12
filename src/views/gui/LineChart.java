package views.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import views.View;

/**
 * Class for creating a frame to display line chart.
 */
public class LineChart extends JFrame {

  /**
   * Constructor to display line chart frame.
   *
   * @param chartTitle title of chart.
   * @param data       data to be dosplayed.
   */
  public LineChart(String chartTitle, Map<String, String> data) {

    JFreeChart lineChart = ChartFactory.createLineChart(
        chartTitle,
        "Time Stamp", "Value of Portfolio",
        createDataset(data),
        PlotOrientation.VERTICAL,
        true, true, false);
    CategoryAxis axis = lineChart.getCategoryPlot().getDomainAxis();
    axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

    ChartPanel chartPanel = new ChartPanel(lineChart);
    chartPanel.setPreferredSize(new java.awt.Dimension(600, 200));
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(600, 400);
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.add(chartPanel);
    this.setResizable(false);
    this.setVisible(true);
  }

  /**
   * Method to create data set based on the data received.
   *
   * @param data data to be displayed.
   * @return Modified form of data.
   */
  private DefaultCategoryDataset createDataset(Map<String, String> data) {
    View viewObject = new View(null);
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (Map.Entry<String, String> mapElement : data.entrySet()) {
      String key = mapElement.getKey();
      if (!key.equals("Maximum") && !key.equals("Minimum") && !key.equals("TimeStampType")) {
        if (!mapElement.getValue().equals("NA")) {
          String timeStampFormat = data.get("TimeStampType");
          if (timeStampFormat.equals("Month") || timeStampFormat.equals("Year")) {
            dataset.addValue(Double.parseDouble(mapElement.getValue()), "Value",
                viewObject.convertDateFormat(key, timeStampFormat));
          } else {
            dataset.addValue(Double.parseDouble(mapElement.getValue()), "Value", key);
          }
        }
      }

    }

    return dataset;
  }

}
