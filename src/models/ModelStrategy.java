package models;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This represents the class which has the methods to apply strategies to the existing portfolios.
 */

public class ModelStrategy extends ToFileWriter implements StockModelStrategy {

  private final String filePath;
  public StringBuilder log = new StringBuilder("");
  public JSONObject portfolio;
  public Map<String, String> stocksMap;
  private static final DecimalFormat df = new DecimalFormat("0.00");

  /**
   * Constructs the modelStrategy class by doing the following. - read the config file and set the
   * variables. - initial a global portfolio file if not existing,
   *
   * @param portfolioFilePath path to the all portfolios json file
   * @throws IOException                           a I/O error.
   * @throws org.json.simple.parser.ParseException if json parse error.
   */
  public ModelStrategy(String portfolioFilePath)
      throws IOException, org.json.simple.parser.ParseException {
    InputStream is = getClass().getResourceAsStream("/config/config.properties");

    Model model = new Model(portfolioFilePath);
    Properties prop = new Properties();
    prop.load(is);
    String csvPathStocksDataUnique = prop.getProperty("ALL_UNIQUE_STOCKS_CSV");
    String csvPathForStocksData = prop.getProperty("ALL_STOCKS_CSV");
    String apiUsed = prop.getProperty("API");
    this.filePath = portfolioFilePath;
    File f = new File(filePath);
    if (!f.exists()) {
      String portfolios_json = "{\"currentPortfolioId\":0,\"hasFlexiblePortfolio\":0}";
      writeToFile(portfolios_json, filePath);
    }
    this.portfolio = model.jsonFileReader(this.filePath);
  }

  @Override
  public String createStartToFinishDcaPortfolio(int nStocks, Double nAmount,
      Double commissionAmount, int nFrequencyInDays, String startDate, String endDate,
      Map<String, Double> stockWeights)
      throws IOException, org.json.simple.parser.ParseException, ParseException,
      URISyntaxException, InterruptedException {
    Model model = new Model(this.filePath);
    String portfolioType = "Flexible";
    JSONObject portfolioObject = model.jsonFileReader(this.filePath);
    Long currentPortfolioId = (Long) portfolioObject.get("currentPortfolioId");
    currentPortfolioId++;

    JSONObject stockObject = new JSONObject();
    JSONArray stockArray = new JSONArray();
    JSONObject eachStock = new JSONObject();
    JSONArray eachStockArray = new JSONArray();
    JSONObject stock = new JSONObject();
    String oEndDate = endDate;
    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    if (endDate == null) { // indefinite investing
      endDate = dtf.format(LocalDate.now());
    }
    LocalDate nStartDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    LocalDate nEndDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    List<String> dateRange = nStartDate.datesUntil(nEndDate, Period.ofDays(nFrequencyInDays))
        .map(obj -> dtf.format(obj))
        .collect(Collectors.toList());
    LocalDate date_today = LocalDate.now();
    for (Map.Entry<String, Double> set : stockWeights.entrySet()) {
      String stockName = set.getKey();
      Double stockWeight = set.getValue();
      Double valueBeforeCommission = (stockWeight) * (nAmount / 100);
      Double value =
          (stockWeight) * ((nAmount - commissionAmount) / 100); // deduct commission before buying
      eachStock = new JSONObject();
      eachStockArray = new JSONArray();
      double totalStocks = 0;
      for (String date : dateRange) {
        // dont query future dates -> no data from API
        if (LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).isBefore(date_today)) {
          double stockValue = model.getValueOfStockOnImmediateWorkingDate(stockName, date);
          double quantity = value / stockValue;
          totalStocks += quantity;

          eachStockArray = model.addStockToList("Buy", date,
              Double.parseDouble(df.format(quantity)), Double.parseDouble(df.format(stockValue)),
              eachStockArray, commissionAmount.floatValue(),
              "recurringDollarCostAveraging", false);
        }
      }
      stock = new JSONObject();
      stock.put("total", Double.parseDouble(df.format(totalStocks)));

      // strategy rules
      JSONArray strategyArray = new JSONArray();
      JSONObject recurringDollarCostObject = new JSONObject();
      recurringDollarCostObject.put("type", "recurringDollarCostAveraging");
      recurringDollarCostObject.put("value", Double.parseDouble(df.format(valueBeforeCommission)));
      recurringDollarCostObject.put("commission", commissionAmount);
      recurringDollarCostObject.put("frequencyDays", nFrequencyInDays);
      recurringDollarCostObject.put("weight", nFrequencyInDays);
      recurringDollarCostObject.put("startDate", startDate);
      recurringDollarCostObject.put("endDate", oEndDate);
      strategyArray.add(recurringDollarCostObject);

      stock.put("strategy_rules", strategyArray);
      stock.put("transactions", eachStockArray);
      eachStock.put(set.getKey(), stock);
      stockArray.add(eachStock);
    }

    stockObject.put("stocks", stockArray);
    stockObject.put("type", "Flexible");
    portfolioObject.put(currentPortfolioId, stockObject);
    portfolioObject.put("currentPortfolioId", currentPortfolioId);
    if (portfolioType.equals("Flexible")) {
      portfolioObject.put("hasFlexiblePortfolio", 1);
    }
    writeToFile(portfolioObject.toJSONString(), this.filePath);
    return "\033[1;32m" + "Strategy Portfolio with Dollar cost averaging created "
        + "with id: " + currentPortfolioId
        + " . Please use this ID for further reference of the portfolio.";
  }

  @Override
  public String addNonRecurringDcaExistingPortfolio(int portfolioId, Double nAmount,
      Double commissionAmount, String date, Map<String, Double> stockWeights)
      throws ParseException, URISyntaxException, IOException, org.json.simple.parser.ParseException,
      InterruptedException {
    Model model = new Model(this.filePath);
    for (Map.Entry<String, Double> set : stockWeights.entrySet()) {
      String stockName = set.getKey();
      Double stockWeight = set.getValue();
      double stockValue = model.getValueOfStockOnDate(stockName, date);
      if (stockValue == -1) {
        return "\033[0;31m" + "Stock " + stockName + " not available for given date "
            + date;
      }
      Double value = stockWeight * (nAmount / 100); // check if deduct commission before buying
      double quantity = value / stockValue;
      String buy_status = model.buyStockOnDateHelper(portfolioId, stockName,
          Double.parseDouble(df.format(quantity)), date, commissionAmount.floatValue(), false);
      if (buy_status == null) {
        return "\033[0;31m" + "Stock " + stockName + " not available for given date " + date;
      }
    }

    return "\033[1;32m" + "Stocks purchased successfully";
  }
}
