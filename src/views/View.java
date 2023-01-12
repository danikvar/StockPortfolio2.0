package views;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * View class that implements StockView interface interact with the controller and is responsible
 * only in displaying ouyput to the uswr by returning it back to the controller.
 */
public class View implements StockView {

  private final PrintStream output;
  private static final DecimalFormat df = new DecimalFormat("0.00");

  /**
   * Constructs the View model by getting the output from the controller and setting here.
   *
   * @param output information from the controller to be viewed.
   */
  public View(PrintStream output) {
    this.output = output;
  }


  @Override
  public void displayMenu() {
    this.output.printf("Select An Option from the Menu below \n");
    this.output.printf("1. Create a new portfolio \n");
    this.output.printf("2. Upload a new portfolio \n");
    this.output.printf("3. Examine an existing portfolio \n");
    this.output.printf("4. Download portfolio as a file \n");
    this.output.printf("5. Get total value \n");
    this.output.printf("6. Purchase Stocks \n");
    this.output.printf("7. Sell Stocks \n");
    this.output.printf("8. Cost Basis  \n");
    this.output.printf("9. Portfolio At a Glance \n");
    this.output.printf("10.Portfolio Rebalancing \n");
    this.output.printf("11.Exit \n");
  }

  @Override
  public void createMenuStockName(int value) {
    this.output.printf("Enter stock number " + value + "\n");
  }

  @Override
  public void createMenuStockNumber(String stockName) {
    this.output.printf("Enter the number of stocks for: " + stockName + "\n");
  }


  @Override
  public void examinePortfolio(Map<String, Double> portfolioComposition, int portfolioId,
      int currentPortfolio, int flag) {
    if (portfolioComposition == null) {
      if (currentPortfolio == 0) {
        this.output.printf(
            "\033[0;31m"
                + "No portfolio is created yet! Please create or upload"
                + " a portfolio before you look for one!! \n");
        this.output.printf("\033[0m");
      } else if (currentPortfolio == 1) {
        this.output.printf(
            "\033[0;31m"
                + "Invalid ID provided, there is only one portfolio present which has ID : 1 \n");
        this.output.printf("\033[0m");
      } else {
        this.output.printf(
            "\033[0;31m" + "Invalid ID provided, Provide portfolio id between 1 and "
                + currentPortfolio + "\n");
        this.output.printf("\033[0m");
      }
      return;
    }
    if (flag == 0) {
      this.output.printf("\033[1;32m" + "Composition for Portfolio ID: " + portfolioId + "\n");
      for (Entry entry : portfolioComposition.entrySet()) {
        this.output.printf(
            "\033[1;32m" + "Stock ID: \t" + entry.getKey() + ", Number of Stocks Owned: \t"
                + entry.getValue() + "\n");
      }
      this.output.printf("\033[0m");
    }
  }

  @Override
  public void display(String result) {
    this.output.printf(result + "\n");
    this.output.printf("\033[0m");
  }

  @Override
  public void downloadPortfolio(String result) {
    if (result == null) {
      this.output.printf(
          "\033[0;31m" + "Directory not found, please provide a valid directory: \n");
    } else {
      this.output.printf("\033[1;32m" + result + "\n");
    }
    this.output.printf("\033[0m");
  }

  @Override
  public void getValueOfPortfolio(Map<String, Double> valueOfPortfolio, String date) {
    if (valueOfPortfolio == null) {
      this.output.printf(
          "\033[0;31m" + "Provided date is a holiday, please provide another date:\n");
      this.output.printf("\033[0m");
      return;
    }

    this.output.printf("\033[1;32m" + "Value of your stocks on " + date + " are\n");
    for (Map.Entry<String, Double> entry : valueOfPortfolio.entrySet()) {
      String[] strArray = entry.getKey().split(",");
      if (!strArray[0].equals("Total")) {
        this.output.printf(
            "\033[1;32m" + strArray[0] + " ----> $" + df.format((entry.getValue())) + "\n");
      }
    }
    this.output.printf(
        "\033[1;32m" + "Total" + " ----> $" + df.format((valueOfPortfolio.get("Total"))) + "\n");
    this.output.printf("\033[0m");


  }

  @Override
  public void showSubMenu() {
    this.output.printf("Select An Option from the Menu below \n");
    this.output.printf("1. Flexible portfolio \n");
    this.output.printf("2. Inflexible portfolio \n");
  }

  @Override
  public void portfolioAtAGlance(Map<String, String> portfolioAtAGlance) {
    Double previous = 0.0;
    String timeStampType = portfolioAtAGlance.get("TimeStampType");
    Double min = Double.valueOf(portfolioAtAGlance.get("Minimum"));
    Double max = Double.valueOf(portfolioAtAGlance.get("Maximum"));
    Map<String, Integer> result = new HashMap<>();
    Map<String, String> scaleType = getScale(min, max);
    for (Map.Entry<String, String> entry : portfolioAtAGlance.entrySet()) {
      Double value;
      if (Objects.equals(entry.getValue(), "NA")) {
        value = previous;
      } else if (Objects.equals(entry.getValue(), "Day") || Objects.equals(entry.getValue(),
          "Month") || Objects.equals(entry.getValue(), "Year") ||
          Objects.equals(entry.getValue(),
              "Maximum") || Objects.equals(entry.getValue(), "Minimum")) {
        value = 0.0;
      } else {
        value = Double.parseDouble(entry.getValue());
      }
      if (!Objects.equals(entry.getKey(), "TimeStampType") &&
          !Objects.equals(entry.getKey(), "Maximum") &&
          !Objects.equals(entry.getKey(), "Minimum")) {
        this.output.printf(
            "\033[1;32m" + convertDateFormat(entry.getKey(), timeStampType) + " : ");

        int numberOfStars = getNumberOfStars(scaleType, value);
        if (numberOfStars == 0 && value == 0.0) {
          this.output.printf("NA");
        } else {
          for (int i = 0; i < numberOfStars; i++) {
            this.output.printf("*");
          }
        }
        this.output.printf("\n");
        previous = Double.valueOf(df.format(value));
      }


    }
    double scale = Double.parseDouble(scaleType.get("Scale"));
    double base = Double.parseDouble(scaleType.get("Base"));
    if (scaleType.get("Normalized").equals("1")) {
      scale = scale / 50;
      base = base / 50;
    }
    if (scaleType.get("Scale Type").equals("Relative")) {
      this.output.printf(
          "\033[1;32m" + "Scale : * = $" + df.format(scale) + " more than the base value. \n");
      this.output.printf(
          "\033[1;32m" + "Base Value : $" + df.format(base) + "\n");
      this.output.printf(
          "\033[1;32m" + "Scale Type : Relative" + "\n");
    } else {
      this.output.printf(
          "\033[1;32m" + "Scale : * = $" + df.format(scale) + "\n");
      this.output.printf(
          "\033[1;32m" + "Scale Type : Absolute" + "\n");
    }
    this.output.printf(
        "\033[1;32m" + "NA : You have not created a portfolio for that particular date." + "\n");
    this.output.printf("\033[0m");
  }

  /**
   * For the bar chart convert the date format accordingly, daily, monthly, yearly.
   *
   * @param date            date to be checked.
   * @param timeStampFormat format to be converted into.
   * @return new String format for the date.
   */
  public String convertDateFormat(String date, String timeStampFormat) {
    DateTimeFormatter dtfInput = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    LocalDate newDate = LocalDate.parse(date, dtfInput);
    if (Objects.equals(timeStampFormat, "Month") || Objects.equals(timeStampFormat, "Year")) {
      DateTimeFormatter dtfOutputEng = DateTimeFormatter.ofPattern("MMM uuuu",
          Locale.ENGLISH);
      date = dtfOutputEng.format(newDate);
    }
    return date;
  }

  /**
   * For the bar chart get the scale value for each start in the legend.
   *
   * @param min min trade value of the portfolio.
   * @param max max trade value of the portfolio.
   * @return A map containing values for scale, if normalized and scale type.
   */
  private Map<String, String> getScale(Double min, Double max) {
    double scale;
    double base = 0;
    String flag = "0";
    String type = "Absolute";
    if (min < 50 || max < 50) {
      min *= 50;
      max *= 50;
      flag = "1";
    }
    scale = max / 50;
    if ((max / min) <= 50) {
      scale = min;
    } else {
      base = min;
      type = "Relative";
    }
    Map<String, String> result = new HashMap<>();
    result.put("Scale", String.valueOf(scale));
    result.put("Base", String.valueOf(base));
    result.put("Scale Type", type);
    result.put("Normalized", flag);
    return result;
  }

  /**
   * Return the number of stars for a particular stock value in a date time.
   *
   * @param scaleMap map of bar chart infor regarding the scale.
   * @param value    value to be to converted into stars.
   * @return int for number of stars.
   */
  private int getNumberOfStars(Map<String, String> scaleMap, Double value) {
    int numberOfStars = 0;
    if (scaleMap.get("Scale Type").equals("Relative")) {
      value = value - Double.parseDouble(scaleMap.get("Base"));
    }
    return (int) (value / Double.parseDouble(scaleMap.get("Scale")));

  }


}
