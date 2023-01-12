package models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import controllers.API;
import controllers.AlphaVantageClient;
import controllers.Validations;

/**
 * Model class which the controller invokes, this takes in the input from the controller and only
 * does the job of data processing and returns back to the controller.
 */
public class Model extends ToFileWriter implements StockModel {

  private String filePath;
  public StringBuilder log = new StringBuilder("");
  private final String csvPathForStocksData;
  private final String csvPathStocksDataUnique;
  public JSONObject portfolio;
  public Map<String, String> stocksMap;
  private String apiUsed;
  private static final DecimalFormat df = new DecimalFormat("0.00");


  /**
   * Constructs the model class by doing the following. - read the config file and set the
   * variables. - initial a global portfolio file if not existing,
   *
   * @param portfolioFilePath path to the all portfolios json file
   * @throws IOException                           a I/O error.
   * @throws org.json.simple.parser.ParseException if json parse error.
   */
  public Model(String portfolioFilePath)
          throws IOException, org.json.simple.parser.ParseException {
    InputStream is = getClass().getResourceAsStream("/config/config.properties");

    Properties prop = new Properties();
    prop.load(is);
    this.csvPathStocksDataUnique = prop.getProperty("ALL_UNIQUE_STOCKS_CSV");
    this.csvPathForStocksData = prop.getProperty("ALL_STOCKS_CSV");
    this.apiUsed = prop.getProperty("API");
    this.filePath = portfolioFilePath;
    File f = new File(filePath);
    if (!f.exists()) {
      String portfolios_json = "{\"currentPortfolioId\":0,\"hasFlexiblePortfolio\":0}";
      writeToFile(portfolios_json, filePath);
    }
    this.portfolio = jsonFileReader(this.filePath);
  }

  @Override
  public String createPortfolio(Map<String, Map<String, String>> stockHash, String portfolioType)
          throws IOException, org.json.simple.parser.ParseException, ParseException,
          URISyntaxException, InterruptedException {

    JSONObject portfolioObject = jsonFileReader(this.filePath);
    Long currentPortfolioId = (Long) portfolioObject.get("currentPortfolioId");
    currentPortfolioId++;

    JSONObject newPortfolio = new JSONObject();
    JSONObject stockObject = new JSONObject();
    JSONArray stockArray = new JSONArray();
    JSONObject eachStock = new JSONObject();
    JSONObject eachStockObject = new JSONObject();
    JSONArray eachStockArray = new JSONArray();
    JSONObject stock = new JSONObject();

    for (Map.Entry<String, Map<String, String>> set : stockHash.entrySet()) {
      eachStock = new JSONObject();
      eachStockArray = new JSONArray();
      Double totalStocks = 0.0;
      for (Map.Entry<String, String> dateSet : set.getValue().entrySet()) {
        String value = dateSet.getValue();
        String[] valueArray = value.split(",");
        totalStocks += Double.parseDouble(valueArray[0]);
        double stockValue = getValueOfStockOnDate(set.getKey(), dateSet.getKey());
        if (stockValue == -1) {
          eachStockArray = null;
        } else {
          eachStockArray = addStockToList("Buy", dateSet.getKey(),
                  Double.parseDouble(valueArray[0]), stockValue,
                  eachStockArray, Float.parseFloat(valueArray[1]), "basic", false);
        }

        if (eachStockArray == null) {
          return "\033[0;31m" + "Stock " + set.getKey() + " not available for given date "
                  + dateSet.getKey();
        }
      }

      stock = new JSONObject();
      stock.put("total", Double.parseDouble(df.format(totalStocks)));
      stock.put("transactions", eachStockArray);
      stock.put("strategy_rules", new JSONArray());
      eachStock.put(set.getKey(), stock);
      stockArray.add(eachStock);
    }

    stockObject.put("stocks", stockArray);
    stockObject.put("type", portfolioType);
    portfolioObject.put(currentPortfolioId, stockObject);
    portfolioObject.put("currentPortfolioId", currentPortfolioId);
    if (portfolioType.equals("Flexible")) {
      portfolioObject.put("hasFlexiblePortfolio", 1);
    }
    writeToFile(portfolioObject.toJSONString(), this.filePath);
    return "\033[1;32m" + "Portfolio created with id: " + currentPortfolioId
            + " . Please use this ID for further reference of the portfolio.";
  }


  /**
   * Reads a json file into json object using the given path.
   *
   * @param filePath the path of json file.
   * @return a JSONObject of the json file
   * @throws IOException                           a I/O error.
   * @throws org.json.simple.parser.ParseException if json parse error.
   */
  protected JSONObject jsonFileReader(String filePath)
          throws IOException, org.json.simple.parser.ParseException {
    JSONParser parser = new JSONParser();
    FileReader file = new FileReader(filePath);
    Object obj = parser.parse(file);
    file.close();
    JSONObject jsonObject = (JSONObject) obj;
    return jsonObject;
  }

  /**
   * Given a date and a stock return its closing price on stock market.
   *
   * @param stock stock to be fetched
   * @param date  date to be fetched on.
   * @return total price*quantity for that date.
   * @throws ParseException       if parse error.
   * @throws URISyntaxException   if string is not parsed as URI reference.
   * @throws IOException          a I/O error.
   * @throws InterruptedException if the thread is interrupted.
   */
  protected double getValueOfStockOnDate(String stock, String date)
          throws ParseException, URISyntaxException, IOException, InterruptedException {
    API getValueOfStock = null;
    if (Objects.equals(this.apiUsed, "AlphaVantageClient")) {
      getValueOfStock = new AlphaVantageClient();
    }
    boolean isDateToday = checkIfGivenDateIsToday(date);
    String stockValue;
    try {
      if (isDateToday) {
        stockValue = getValueOfStock.getCurrentStockData(stock);
      } else {
        stockValue = getValueOfStock.getStockData(stock, date);
      }
      double stockPrice = Double.parseDouble(stockValue);
      return Double.parseDouble(df.format(stockPrice));
    } catch (Exception e) {
      return -1;
    }


  }

  /**
   * Method that returns the value of stock if that particular day is a weekend/holiday.
   *
   * @param stock stock name.
   * @param date  date of request.
   * @return value of the stock on a particular date.
   * @throws ParseException       if parse error.
   * @throws URISyntaxException   uri error.
   * @throws IOException          I/O exception.
   * @throws InterruptedException thread interrupted.
   */
  protected double getValueOfStockOnImmediateWorkingDate(String stock, String date)
          throws ParseException, URISyntaxException, IOException, InterruptedException {
    API getValueOfStock = null;
    if (Objects.equals(this.apiUsed, "AlphaVantageClient")) {
      getValueOfStock = new AlphaVantageClient();
    }
    LocalDate nDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    String stockValue;
    boolean hasFoundValue = false;
    while (!hasFoundValue) {
      try {
        String newDate = dtf.format(nDate);
        stockValue = getValueOfStock.getStockData(stock, newDate);

        double stockPrice = Float.parseFloat(stockValue);
        double individualStockValue = stockPrice;
        return Float.parseFloat(df.format(individualStockValue));
      } catch (Exception e) {
        nDate = nDate.plusDays(1);
      }
    }

    return -1;

  }

  @Override
  public Map<String, Double> examinePortfolio(int portfolioId) throws IOException {
    try {
      Map<String, Double> compositionMap = new HashMap<>();
      JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
      JSONArray stocks = (JSONArray) portfolioStock.get("stocks");
      Iterator iterator = stocks.iterator();
      while (iterator.hasNext()) {
        JSONObject jsonObj = (JSONObject) iterator.next();
        String stock = (String) jsonObj.keySet().toArray()[0];
        JSONObject stockObject = (JSONObject) jsonObj.get(stock);
        Double value = (Double) stockObject.get("total");

        compositionMap.put(stock, Double.valueOf(df.format(value)));
      }
      return compositionMap;
    } catch (Exception e) {
      return null;
    }

  }

  @Override
  public double getCostBasisPortfolio(int portfolioId, String date) {
    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    JSONArray stocks = (JSONArray) portfolioStock.get("stocks");
    Iterator iterator = stocks.iterator();
    double totalCostBasis = 0F;
    while (iterator.hasNext()) {
      JSONObject jsonObj = (JSONObject) iterator.next();
      String stock = (String) jsonObj.keySet().toArray()[0];
      JSONObject stockObject = (JSONObject) jsonObj.get(stock);
      JSONArray transactions = (JSONArray) stockObject.get("transactions");
      LocalDate givenDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
              .plusDays(1);

      // BASIC STRATEGY COSTS
      // buy commission + buy value
      totalCostBasis += transactions.stream()
              .filter(obj -> ((JSONObject) obj).get("trade").equals("Buy"))
              .filter(obj -> ((JSONObject) obj).get("strategy_type").equals("basic"))
              .filter(obj -> ((JSONObject) obj).get("rebalance").equals(false))
              .filter(obj -> LocalDate.parse((String) ((JSONObject) obj).get("date"),
                      DateTimeFormatter.ofPattern("yyyy-MM-dd")).isBefore(givenDate))
              .mapToDouble(obj -> ((double) ((JSONObject) obj).get("commission"))
                      + (double) ((JSONObject) obj).get("valueDate")).sum();

      // sell commission
      totalCostBasis += transactions.stream()
              .filter(obj -> ((JSONObject) obj).get("trade").equals("Sell"))
              .filter(obj -> ((JSONObject) obj).get("strategy_type").equals("basic"))
              .filter(obj -> ((JSONObject) obj).get("rebalance").equals(false))
              .filter(obj -> LocalDate.parse((String) ((JSONObject) obj).get("date"),
                      DateTimeFormatter.ofPattern("yyyy-MM-dd")).isBefore(givenDate))
              .mapToDouble(obj -> ((double) ((JSONObject) obj).get("commission"))).sum();

      // OTHER STRATEGY COSTS
      JSONArray strategy_transactions = (JSONArray) stockObject.get("strategy_rules");
      Iterator strat_iterator = strategy_transactions.iterator();
      while (strat_iterator.hasNext()) {
        JSONObject strat_obj = (JSONObject) strat_iterator.next();
        String strategy_type = (String) strat_obj.get("type");
        if (strategy_type.equals("recurringDollarCostAveraging")) {
          LocalDate endDate;
          LocalDate startDate = LocalDate.parse((String) strat_obj.get("startDate"),
                  DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          if (strat_obj.get("endDate") == null) {
            endDate = givenDate;
          } else { // check if end date > cost basis date
            endDate = LocalDate.parse((String) strat_obj.get("endDate"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (givenDate.isBefore(endDate)) {
              endDate = givenDate;
            }

            if (givenDate.isBefore(startDate)) {
              return 0.0;
            }
          }
          long dateRange = startDate.datesUntil(endDate, Period.ofDays(
                          Math.toIntExact((Long) strat_obj.get("frequencyDays"))))
                  .count();

          // commission included in value
          totalCostBasis +=
                  (double) dateRange * (Double) strat_obj.get("value");
        }
      }
    }
    return totalCostBasis;
  }


  @Override
  public boolean hasStock(int portfolioId, String stock) {
    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    JSONArray stocks = (JSONArray) portfolioStock.get("stocks");
    Iterator iterator = stocks.iterator();
    while (iterator.hasNext()) {
      JSONObject jsonObj = (JSONObject) iterator.next();
      if (stock.equals(jsonObj.keySet().toArray()[0])) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean hasStockQuantity(int portfolioId, String stock, Long quantity) {
    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    JSONArray stocks = (JSONArray) portfolioStock.get("stocks");
    Iterator iterator = stocks.iterator();
    while (iterator.hasNext()) {
      JSONObject jsonObj = (JSONObject) iterator.next();
      if (stock.equals(jsonObj.keySet().toArray()[0])) {
        JSONObject stockObject = (JSONObject) jsonObj.get(stock);
        Double total = (Double) stockObject.get("total");
        return total >= quantity;
      }
    }
    return false;
  }

  @Override
  public String getLatestBuyDate(int portfolioId, String stock) {
    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    JSONArray stocks = (JSONArray) portfolioStock.get("stocks");
    Iterator iterator = stocks.iterator();
    while (iterator.hasNext()) {
      JSONObject jsonObj = (JSONObject) iterator.next();
      if (stock.equals(jsonObj.keySet().toArray()[0])) {
        JSONObject stockObject = (JSONObject) jsonObj.get(stock);
        JSONArray transactions = (JSONArray) stockObject.get("transactions");
        List<String> dates = (List<String>) transactions.stream()
                .filter(obj -> ((JSONObject) obj).get("trade").equals("Buy"))
                .map(obj -> ((JSONObject) obj).get("date").toString())
                .collect(Collectors.toList());
        String maxDate = dates.stream()
                .map(s -> LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .max(LocalDate::compareTo)
                .get()
                .toString();
        return maxDate;
      }
    }
    return null;
  }


  /**
   * Given a portfolio id return the most past date in the transactions.
   *
   * @param portfolioId id of the portfolio to be looked on.
   * @return a string of the min date.
   */
  private String getMinimumDate(int portfolioId) {
    List<String> dates = null;
    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    JSONArray stocks = (JSONArray) portfolioStock.get("stocks");
    Iterator iterator = stocks.iterator();
    while (iterator.hasNext()) {
      JSONObject jsonObj = (JSONObject) iterator.next();
      String stock = (String) jsonObj.keySet().toArray()[0];
      JSONObject stockObject = (JSONObject) jsonObj.get(stock);
      JSONArray transactions = (JSONArray) stockObject.get("transactions");
      dates = (List<String>) transactions.stream()
              .filter(obj -> ((JSONObject) obj).get("trade").equals("Buy"))
              .map(obj -> ((JSONObject) obj).get("date").toString())
              .collect(Collectors.toList());

    }
    Collections.sort(dates);
    return dates.get(0);
  }

  /**
   * Return a stock json object if exists.
   *
   * @param stocks a JSONArray of stocks
   * @param stock  stock to be searched for.
   * @return A JSONObject of stock else null.
   */
  private JSONObject getStock(JSONArray stocks, String stock) {
    Iterator iterator = stocks.iterator();
    while (iterator.hasNext()) {
      JSONObject jsonObj = (JSONObject) iterator.next();
      if (stock.equals(jsonObj.keySet().toArray()[0])) {
        return jsonObj;
      }
    }
    return null;
  }

  /**
   * Add a particular stock to a list of transactions.
   *
   * @param trade      if buy/sell.
   * @param date       date of the trade.
   * @param quantity   quantity of the trade.
   * @param stocks     Stocks array where the transaction is to be added.
   * @param commission commission of the trade.
   * @return updated JSONArray with updated transactions.
   */
  protected JSONArray addStockToList(String trade, String date, Double quantity,
                                     Double stockValue,
                                     JSONArray stocks, float commission,
                                     String strategyType, boolean rebalance) {
    JSONObject eachStockObject = new JSONObject();
    eachStockObject.put("trade", trade);
    eachStockObject.put("date", date);
    eachStockObject.put("quantity", Double.parseDouble(df.format(quantity)));
    eachStockObject.put("commission", Float.valueOf(commission).doubleValue());
    eachStockObject.put("strategy_type", strategyType);
    eachStockObject.put("valueDate", Double.parseDouble(df.format(quantity * stockValue)));
    eachStockObject.put("rebalance", rebalance);
    stocks.add(eachStockObject);
    return stocks;
  }

  @Override
  public String buyStockOnDate(int portfolioId, String stock, Long quantity, String date,
                               float commission)
          throws ParseException, URISyntaxException, IOException, InterruptedException,
          org.json.simple.parser.ParseException {
    String buy_status = buyStockOnDateHelper(portfolioId, stock, Double.valueOf(quantity), date,
            commission, false);
    if (buy_status == null) {
      return "Stock " + stock + " not available for given date " + date;
    } else if (buy_status.equals("Success")) {
      return "Stock: " + stock + " purchased successfully";
    } else {
      return "Some error while purchasing, please retry";
    }
  }

  /**
   * Helper method of buying stocks.
   *
   * @param portfolioId ID of portfolio.
   * @param stock       stock name.
   * @param quantity    quantity of stock.
   * @param date        date of purchase.
   * @param commission  commission paid.
   * @return Success message if valid.
   * @throws ParseException                        parse error.
   * @throws URISyntaxException                    uri error.
   * @throws IOException                           I/O error.
   * @throws InterruptedException                  if thread is interrupted.
   * @throws org.json.simple.parser.ParseException json parse error.
   */
  public String buyStockOnDateHelper(int portfolioId, String stock, Double quantity,
                                     String date, float commission, boolean rebalance)
          throws ParseException, URISyntaxException, IOException, InterruptedException,
          org.json.simple.parser.ParseException {
    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    JSONArray stocks = (JSONArray) portfolioStock.get("stocks");
    JSONObject stockData = getStock(stocks, stock);
    JSONObject eachStockObject;
    if (stockData == null) {
      JSONObject newStock = new JSONObject();
      JSONObject eachStock = new JSONObject();
      JSONArray transactions = new JSONArray();

      transactions = getJsonArray(stock, quantity, date, commission, transactions, rebalance);
      if (transactions == null) {
        return null;
      }
      newStock.put("total", quantity);
      newStock.put("transactions", transactions);
      newStock.put("strategy_rules", new JSONArray());
      eachStock.put(stock, newStock);
      stocks.add(eachStock);
    } else {
      JSONObject stockObject = (JSONObject) stockData.get(stock);
      Double total = (Double) stockObject.get("total");
      total += quantity;
      JSONArray transactions = (JSONArray) stockObject.get("transactions");
      JSONArray strategy_rules = (JSONArray) stockObject.get("strategy_rules");
      List<JSONObject> date_transactions = (List<JSONObject>) transactions.stream()
              .filter(obj -> ((JSONObject) obj).get("trade").equals("Buy"))
              .filter(obj -> ((JSONObject) obj).get("rebalance").equals(false))
              .filter(jsonObj -> ((JSONObject) jsonObj).get("date").equals(date))
              .collect(Collectors.toList());
      if (date_transactions.size() == 0) {

        transactions = getJsonArray(stock, quantity, date, commission, transactions, rebalance);
        if (transactions == null) {
          return null;
        }
      } else {
        eachStockObject = date_transactions.get(0);
        Double existing_quantity = (Double) eachStockObject.get("quantity");
        eachStockObject.put("quantity", existing_quantity + quantity);
        eachStockObject.put("commission", commission);
        eachStockObject.put("rebalance", rebalance);
      }

      stockObject.put("total", total);
      stockObject.put("transactions", transactions);
      stockObject.put("strategy_rules", strategy_rules);

    }
    updatePortfolioAfterTrade(portfolioId, portfolioStock, stocks);
    return "Success";
  }

  /**
   * Method to get the JSON array from the API.
   *
   * @param stock        name of stock.
   * @param quantity     quantity of stock.
   * @param date         date of purchase.
   * @param commission   commission paid.
   * @param transactions transaction list.
   * @return the created JSON array.
   * @throws ParseException       parse error.
   * @throws URISyntaxException   uri error.
   * @throws IOException          I/O error.
   * @throws InterruptedException if thread is interrupted.
   */
  private JSONArray getJsonArray(String stock, Double quantity, String date,
                                 float commission,
                                 JSONArray transactions, boolean rebalance)
          throws ParseException, URISyntaxException, IOException, InterruptedException {
    double stockValue = getValueOfStockOnDate(stock, date);
    if (stockValue == -1) {
      transactions = null;
    } else {
      transactions = addStockToList("Buy", date, quantity, stockValue, transactions,
              commission,
              "basic", rebalance);
    }
    return transactions;
  }

  /**
   * Update a portfolio post adding the transaction.
   *
   * @param portfolioId    portfolio to be affected.
   * @param portfolioStock the stock JSONObject on the portfolio.
   * @param stocks         stock array where the transaction is to be added.
   * @throws IOException                           a I/O error,
   * @throws org.json.simple.parser.ParseException if json parse error.
   */
  private void updatePortfolioAfterTrade(int portfolioId, JSONObject portfolioStock,
                                         JSONArray stocks)
          throws IOException, org.json.simple.parser.ParseException {
    portfolioStock.put("stocks", stocks);
    JSONObject portfolioObject = jsonFileReader(this.filePath);
    portfolioObject.replace(Integer.toString(portfolioId), portfolioStock);
    writeToFile(portfolioObject.toJSONString(), this.filePath);
  }

  @Override
  public String sellStockOnDate(int portfolioId, String stock, Long quantity, String date,
                                float commission)
          throws ParseException, URISyntaxException, IOException, InterruptedException,
          org.json.simple.parser.ParseException {

    String sell_status = sellStockOnDateHelper(portfolioId, stock, Double.valueOf(quantity), date,
            commission, false);
    if (sell_status == null) {
      return "\033[0;31m" + "Stock " + stock + " not available for given date " + date;
    } else if (sell_status.equals("Success")) {
      return "\033[1;32m" + "Stock: " + stock + " sold successfully in the portfolio with id "
              + portfolioId;
    } else {
      return "Some error while selling stock, please retry";
    }
  }

  /**
   * Helper method of buying stocks.
   *
   * @param portfolioId ID of portfolio.
   * @param stock       stock name.
   * @param quantity    quantity of stock.
   * @param date        date of purchase.
   * @param commission  commission paid.
   * @param rebalance   if this transaction should be included in CB
   * @return Success message if valid.
   * @throws ParseException                        parse error.
   * @throws URISyntaxException                    uri error.
   * @throws IOException                           I/O error.
   * @throws InterruptedException                  if thread is interrupted.
   * @throws org.json.simple.parser.ParseException json parse error.
   */
  public String sellStockOnDateHelper(int portfolioId, String stock, Double quantity,
                                      String date, float commission, boolean rebalance)
          throws ParseException, URISyntaxException, IOException, InterruptedException,
          org.json.simple.parser.ParseException {
    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    JSONArray stocks = (JSONArray) portfolioStock.get("stocks");
    JSONObject stockData = getStock(stocks, stock);
    JSONObject stockObject = (JSONObject) stockData.get(stock);
    Double total = (Double) stockObject.get("total");
    total -= quantity;

    if (total < 0) {
      return "Error: Cant have sell more than currently have.";
    }
    JSONArray transactions = (JSONArray) stockObject.get("transactions");

    double stockValue = getValueOfStockOnDate(stock, date);
    if (stockValue == -1) {
      transactions = null;
    } else {
      transactions = addStockToList("Sell", date, quantity, stockValue,
              transactions,
              commission, "basic", rebalance);

    }
    if (transactions == null) {
      return null;
    }
    //update
    stockObject.put("total", total);
    stockObject.put("transactions", transactions);

    updatePortfolioAfterTrade(portfolioId, portfolioStock, stocks);

    return "success";
  }


  @Override
  public int getLatestPortfolioId() {
    return (int) (long) this.portfolio.get("currentPortfolioId");
  }

  @Override
  public String getTypeOfPortfolio(int portfolioId) {
    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    return (String) portfolioStock.get("type");
  }

  @Override
  public boolean hasFlexiblePortfolio() {
    return (int) (long) this.portfolio.get("hasFlexiblePortfolio") == 1;
  }

  @Override
  public List<String> listOfFlexiblePortfolios() {
    JSONObject allPortfolios = (JSONObject) this.portfolio;
    ArrayList<String> portfoliosList = new ArrayList<String>(allPortfolios.keySet());
    List<String> listOfPortfolios;

    listOfPortfolios = portfoliosList.stream()
            .filter(obj -> {
              try {
                int portfolioId = Integer.parseInt(obj);
                JSONObject portfolioStock = (JSONObject) this.portfolio.get(
                        Integer.toString(portfolioId));
                String portfolioType = (String) portfolioStock.get("type");
                if (portfolioType.equals("Flexible")) {
                  return true;
                }
              } catch (Exception ignored) {
                // we ignore this exception
              }
              return false;
            }).collect(Collectors.toList());

    return listOfPortfolios;
  }

  @Override
  public Map<String, String> portfolioAtAGlance(int portfolioId, String fromDate, String toDate,
                                                long differenceBetweenDates)
          throws ParseException, URISyntaxException, IOException, InterruptedException {
    int timeStamp;
    Validations dateObj = new Validations();
    Map<String, String> result = new TreeMap<>();
    Double minimum = Double.MAX_VALUE;
    Double maximum = Double.MIN_VALUE;
    if (differenceBetweenDates < 365) {
      timeStamp = (int) ((differenceBetweenDates / 30) + 1);
    } else if (differenceBetweenDates <= 730) { // 1 to 2 years
      timeStamp = 1;
    } else if (differenceBetweenDates <= 1825) { // 2 to 5years
      timeStamp = 2;
    } else if (differenceBetweenDates <= 2555) { // 5 to 7 years
      timeStamp = 3;
    } else if (differenceBetweenDates <= 4380) { // 7 to 12 years
      timeStamp = 6;
    } else { // more than 12 years
      timeStamp = 12;
    }
    int count = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    Date firstDate = sdf.parse(fromDate);
    Date secondDate = sdf.parse(toDate);
    Calendar cal = Calendar.getInstance();
    cal.setTime(firstDate);

    if (differenceBetweenDates >= 365 && differenceBetweenDates < 4380) {
      getPerformanceOfPortfolio(portfolioId, timeStamp, dateObj, result, sdf, firstDate, secondDate,
              cal, 1, "Month", minimum, maximum);
    } else if (differenceBetweenDates > 4380) {
      String[] fromDateString = fromDate.split("-");
      String fromYear = fromDateString[0] + "-12-31";
      String[] toDateString = toDate.split("-");
      if (!toDateString[0].equals("2022")) {
        String toYear = toDateString[0] + "-12-31";
        secondDate = sdf.parse(toYear);
      }
      firstDate = sdf.parse(fromYear);
      cal.setTime(firstDate);
      getPerformanceOfPortfolio(portfolioId, timeStamp, dateObj, result, sdf, firstDate, secondDate,
              cal, 1, "Year", minimum, maximum);
    } else {
      getPerformanceOfPortfolio(portfolioId, timeStamp, dateObj, result, sdf, firstDate, secondDate,
              cal, 0, "Day", minimum, maximum);
    }
    return result;

  }


  /**
   * Updates the map of performance calculated for a portfolio between dates.
   *
   * @param portfolioId   portfolio that is being checked on.
   * @param timeStamp     calculated based on difference in dates.
   * @param dateObj       a datevalidation object to check dates.
   * @param result        map where the values are to be updated.
   * @param sdf           simpledateformat object with date format.
   * @param firstDate     begin date.
   * @param secondDate    end date.
   * @param cal           calendar object.
   * @param flag          flag to decide if the periods are day wise or month wise.
   * @param timeStampType if monthly,daily or yearly.
   * @param minimum       min trade value between dates.
   * @param maximum       max trade value bteween dates.
   * @throws ParseException       if parse error.
   * @throws URISyntaxException   if string is not parsed as URI reference.
   * @throws IOException          if I/O error.
   * @throws InterruptedException if the thread is interrupted.
   */
  private void getPerformanceOfPortfolio(int portfolioId, int timeStamp, Validations dateObj,
                                         Map<String, String> result, SimpleDateFormat sdf,
                                         Date firstDate, Date secondDate,
                                         Calendar cal, int flag, String timeStampType,
                                         Double minimum, Double maximum)
          throws ParseException, URISyntaxException, IOException, InterruptedException {
    String dateAfter;
    while (firstDate.before(secondDate) || firstDate.equals(secondDate)) {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      int countNumberOfDaysAdded = 0;
      if (flag == 1) {
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        dateAfter = sdf.format(cal.getTime());
        firstDate = sdf.parse(dateAfter);
      }
      while (dateObj.checkIsWeekend(firstDate)) {
        cal.add(Calendar.DAY_OF_MONTH, -1);
        dateAfter = sdf.format(cal.getTime());
        firstDate = sdf.parse(dateAfter);
        countNumberOfDaysAdded++;
      }
      String strDate = dateFormat.format(firstDate);
      Map<String, Double> portfolio = null;
      String minDate = getMinimumDate(portfolioId);
      Date minimumDateInPortfolio = sdf.parse(minDate);
      if (firstDate.after(minimumDateInPortfolio) || firstDate.equals(minimumDateInPortfolio)) {
        portfolio = getValueOfPortfolio(portfolioId, strDate);
      }
      cal.add(Calendar.DAY_OF_MONTH, countNumberOfDaysAdded);
      dateAfter = sdf.format(cal.getTime());
      firstDate = sdf.parse(dateAfter);
      strDate = dateFormat.format(firstDate);
      if (portfolio != null) {
        result.put(strDate, String.valueOf(portfolio.get("Total")));
        minimum = Math.min(minimum, portfolio.get("Total"));
        maximum = Math.max(maximum, portfolio.get("Total"));
      } else {
        result.put(strDate, "NA");
      }
      if (flag == 0) {
        cal.add(Calendar.DAY_OF_MONTH, timeStamp);
      } else {
        cal.add(Calendar.MONTH, timeStamp);
      }
      dateAfter = sdf.format(cal.getTime());
      firstDate = sdf.parse(dateAfter);
    }
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = dateFormat.format(secondDate);
    Map<String, Double> portfolio;
    portfolio = getValueOfPortfolio(portfolioId, strDate);
    if (portfolio != null) {
      result.put(strDate, String.valueOf(portfolio.get("Total")));
      minimum = Math.min(minimum, portfolio.get("Total"));
      maximum = Math.max(maximum, portfolio.get("Total"));
    } else {
      result.put(strDate, "NA");
    }
    result.put("TimeStampType", timeStampType);
    result.put("Minimum", String.valueOf(minimum));
    result.put("Maximum", String.valueOf(maximum));
  }

  @Override
  public String uploadFile(String relativePath, Map<String, String> validStocksMap,
                           String portfolioType)
          throws IOException, org.json.simple.parser.ParseException,
          ParseException, URISyntaxException, InterruptedException {
    JSONParser parser = new JSONParser();
    Map<String, Map<String, String>> map = new HashMap<>();
    String date;
    try {

      FileReader file = new FileReader(relativePath);
      Object obj = parser.parse(file);
      file.close();
      JSONObject jsonObject = (JSONObject) obj;
      if (jsonObject.get("stocks") == null) {
        return "JSON Error";
      }
      JSONArray subjects = (JSONArray) jsonObject.get("stocks");

      Iterator iterator = subjects.iterator();
      while (iterator.hasNext()) {
        JSONObject currentObject = (JSONObject) iterator.next();
        if (currentObject.get("date") == null && portfolioType.equals("Flexible")) {
          return "JSON Error";
        }
        if (currentObject.get("date") != null && portfolioType.equals("InFlexible")) {
          return "JSON Error";
        } else {
          if (portfolioType.equals("InFlexible")) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            date = dtf.format(now);
          } else {
            date = (String) currentObject.get("date");
            Validations dateValidate = new Validations();
            if (!Objects.equals(dateValidate.dateValidation(date), "Valid")) {
              return dateValidate.dateValidation(date);
            }
          }
          String name = (String) currentObject.get("name");
          if (!ifStockExists(name, validStocksMap)) {
            return "\033[0;31m" + name
                    + " is not a valid Stock ID, please update the file and upload it again";
          }
          Double stockNumber = (Double) currentObject.get("quantity");
          float commissionForTrade = ((Double) currentObject.get("commission")).floatValue();
          if (map.containsKey(date)) {
            Map<String, String> subMap;
            subMap = map.get(date);
            createMapForStockList(map, date, name, stockNumber, commissionForTrade, subMap);
          } else {
            HashMap<String, String> subMap = new HashMap<>();
            subMap.put(date, stockNumber + "," + commissionForTrade);
            map.put(name.toUpperCase(), subMap);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "File Error";
    }

    return createPortfolio(map, portfolioType);
  }

  /**
   * Takes the user input and converts it into a hashmap {AA={2021-01-01=126,56}}.
   *
   * @param map                map to be updated
   * @param date               date of trade.
   * @param name               stock that is being traded.
   * @param stockNumber        quantity of stocks.
   * @param commis             commission of trade.
   * @param subMap             map of date and quantity and commission value.
   */
  public static void createMapForStockList(Map<String, Map<String, String>> map, String date,
                                           String name,
                                           Double stockNumber,
                                           float commis, Map<String, String> subMap) {
    if (subMap.containsKey(date)) {
      String hashValue = subMap.get(date);
      String[] list = hashValue.split(",");
      Double stockValue = Double.parseDouble(list[0]);
      double commissionForTrade = Float.valueOf(commis).doubleValue();

      stockValue += stockNumber;
      subMap.put(date, stockValue + "," + commissionForTrade);

    } else {
      double commissionForTrade = Float.valueOf(commis).doubleValue();
      subMap.put(date, stockNumber + "," + commissionForTrade);
    }
    map.put(name.toUpperCase(), subMap);
  }

  @Override
  public Map<String, String> createMapForValidStocks()
          throws URISyntaxException, IOException, InterruptedException {

    Stream<String> lines = Files.lines(Paths.get(this.csvPathStocksDataUnique));
    this.stocksMap =
            lines.map(line -> line.split(","))
                    .filter(obj -> obj.length > 6)
                    .collect(Collectors.toMap(line -> line[0], line -> line[6]));
    lines.close();
    return stocksMap;


  }

  /**
   * Takes in a inputstream and returns the consolidated stream.
   *
   * @param is Imnputsteam as input.
   * @return a new Stream object.
   * @throws IOException a I/O error.
   */
  private Stream getInputStream(InputStream is) throws IOException {
    InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
    BufferedReader reader = new BufferedReader(streamReader);
    String l;
    ArrayList<String> s = new ArrayList<>();
    while ((l = reader.readLine()) != null) {
      s.add(l);
    }
    Stream<String> lines = convertListToStream(s);
    return lines;
  }

  /**
   * Helper function the convert list to a stream for faster processing.
   *
   * @param list list to be converted to stream.
   * @param <T>  type of list.
   * @return Stream object of the list.
   */
  private static <T> Stream<T> convertListToStream(List<T> list) {
    return list.stream();
  }

  @Override
  public boolean ifStockExists(String stockName, Map<String, String> validStockMap)
          throws IOException {
    return validStockMap.containsKey(stockName) && Objects.equals(validStockMap.get(stockName),
            "Active");

  }

  @Override
  public String downloadPortfolio(Map<String, Double> portfolioComposition, String directory,
                                  int portfolioId)
          throws IOException {
    String filePath = directory + "portfolio_" + portfolioId + ".json";
    if (!directory.contains(".json")) {
      directory += ".json";
    }
    log.append("Received " + portfolioComposition + " " + filePath + " " + portfolioId + " ");
    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    writeToFile(portfolioStock.toJSONString(), directory);
    return "Portfolio downloaded successfully. You can find the portfolio at: " + directory;
  }

  @Override
  public boolean checkIfValidDirectory(String filePath) {
    File file = new File(filePath);
    return file.exists();
  }

  @Override
  public Map<String, Double> getValueOfPortfolio(int portfolioId,
                                                 String date)
          throws URISyntaxException, IOException, InterruptedException, ParseException {
    Map<String, Double> result = new HashMap<>();
    API getValueOfStock = null;
    if (Objects.equals(this.apiUsed, "AlphaVantageClient")) {
      getValueOfStock = new AlphaVantageClient();
    }
    double totalStockValue = 0;
    boolean isDateToday = checkIfGivenDateIsToday(date);
    Map<String, Integer> compositionMap = new HashMap<>();
    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    JSONArray stocks = (JSONArray) portfolioStock.get("stocks");
    String portfolioType = (String) portfolioStock.get("type");
    Iterator iterator = stocks.iterator();
    Double numberOfStocks = 0.0;
    while (iterator.hasNext()) {
      String stockValue;
      JSONObject jsonObj = (JSONObject) iterator.next();
      String stock = (String) jsonObj.keySet().toArray()[0];
      JSONObject stockObject = (JSONObject) jsonObj.get(stock);
      JSONArray transactions = (JSONArray) stockObject.get("transactions");
      Iterator transactionIterator = transactions.iterator();
      while (transactionIterator.hasNext()) {
        JSONObject transactionObj = (JSONObject) transactionIterator.next();
        if (portfolioType.equals("Flexible")) {
          String transactionDate = (String) transactionObj.get("date");
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date enteredDate = sdf.parse(date);
          Date transactionDateObj = sdf.parse(transactionDate);
          if (transactionDateObj.before(enteredDate) || transactionDateObj.equals(enteredDate)) {
            Double currentNumberOfStocks = (Double) transactionObj.get("quantity");
            String trade = (String) transactionObj.get("trade");
            if (trade.equals("Buy")) {
              numberOfStocks += currentNumberOfStocks;
            } else {
              numberOfStocks -= currentNumberOfStocks;
            }
          }
        } else {
          Double currentNumberOfStocks = (Double) transactionObj.get("quantity");
          numberOfStocks += currentNumberOfStocks;

        }
      }
      try {
        if (isDateToday) {
          stockValue = getValueOfStock.getCurrentStockData(stock);
        } else {
          stockValue = getValueOfStock.getStockData(stock, date);
        }
        double stockPrice = Float.parseFloat(stockValue);
        double individualStockValue = stockPrice * numberOfStocks;
        individualStockValue = Float.parseFloat(df.format(individualStockValue));
        totalStockValue = totalStockValue + individualStockValue;
        result.put(stock, individualStockValue);
      } catch (Exception e) {
        return null;
      }
      numberOfStocks = 0.0;
    }
    result.put("Total", totalStockValue);
    return result;
  }


  @Override
  public String dateValidation(String date) throws ParseException {
    Validations dateObject = new Validations();

    return dateObject.dateValidation(date);
  }

  @Override
  public Map<String, String> createMapForPriceOfStock() throws IOException, URISyntaxException {
    Map<String, String> stockPricesMap;

    InputStream is = getClass().getClassLoader().getResourceAsStream(csvPathForStocksData);
    Stream<String> lines = getInputStream(is);

    stockPricesMap =
            lines.map(line -> line.split(","))
                    .collect(Collectors.toMap(line -> line[2] + "," + line[0], line -> line[1]));

    lines.close();
    return stockPricesMap;
  }

  /**
   * check if the passed date object falls today.
   *
   * @param date Date object to be checked.
   * @return true/false if date falls today.
   * @throws ParseException if any parse error.
   */
  private boolean checkIfGivenDateIsToday(String date) throws ParseException {
    Calendar today = Calendar.getInstance();
    Calendar specifiedDate = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date enteredDate = sdf.parse(date);
    specifiedDate.setTime(enteredDate);
    return today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH)
            && today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
            && today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
  }

  @Override
  public Map<String, Double> getValueOfPortfolioFromCsv(Map<String, Integer> portfolioComposition,
                                                        String date,
                                                        Map<String, String> stockPricesMap) {
    Map<String, Double> result = new HashMap<>();
    double totalStockValue = 0;
    String stockValue;

    for (Map.Entry<String, Integer> mapElement : portfolioComposition.entrySet()) {
      String key = mapElement.getKey() + "," + date;
      int value = mapElement.getValue();
      try {
        stockValue = stockPricesMap.get(key);
        double stockPrice = Float.parseFloat(stockValue);
        double individualStockValue = stockPrice * value;
        individualStockValue = Float.parseFloat(df.format(individualStockValue));
        totalStockValue = totalStockValue + individualStockValue;
        result.put(key, individualStockValue);
      } catch (Exception e) {
        return null;
      }

    }
    result.put("Total", totalStockValue);
    return result;
  }

  /**
   * This method is used to rebalance a portfolio to the weights given by the user.
   *
   * @param portfolioId is the ID of the portfolio.
   * @param date        is the date for rebalancing.
   * @param weightData  is the hash map of stock names and new weights assigned by user.
   * @param commFeeStr  is the commission fee charged for the transactions.
   * @return message stating the status of rebalancing.
   */

  @Override
  public String rebalancePort(int portfolioId, String date,
                              Map<String, Double> weightData,
                              String commFeeStr) {
    float commFee;
    try {
      commFee = Float.parseFloat(commFeeStr);
      if (commFee < 0) {
        return "Portfolio Unbalanced Commission fee cannot be < 0";
      }
    } catch (Exception e) {
      return "Portfolio Unbalanced Could not parse commission fee. Try again.";
    }

    Map<String, Double[]> currentProps;

    try {
      currentProps = this.rebalancePortMap(portfolioId, date, weightData);
    } catch (Exception e) {
      return "Portfolio Unbalanced " + e.getMessage();
    }

    Map<String, Double[]> translatedMap = this.translateBalanceMap(currentProps);
    StringBuilder outBuild = new StringBuilder()
            .append("Portfolio Rebalanced Successfully Rebalanced with transactions: \n");

    for (String key : translatedMap.keySet()) {

      Double[] transactionData = translatedMap.get(key);
      double shares = transactionData[0];
      boolean toBuy = true;

      if (transactionData[1] < 0.5) {
        toBuy = false;
      }


      if (toBuy) {

        String buyStatus;
        try {
          buyStatus = buyStockOnDateHelper(portfolioId, key, shares, date, commFee,true);
        } catch (ParseException | URISyntaxException | org.json.simple.parser.ParseException
                 | InterruptedException | IOException e) {
          return "Portfolio not balanced Found an exception buying shares of stock " + key;
        }
        if (buyStatus == null) {
          return "Portfolio not balanced Stock " + key + " not available for given date " + date;
        } else if (buyStatus.equals("Success")) {
          outBuild.append("Buying ").append(shares).append(" of ").append(key).append("\n");
        } else {
          return "Portfolio Unbalanced Error while purchasing, please retry";
        }
      } else {

        String sellStatus;
        try {
          sellStatus = sellStockOnDateHelper(portfolioId, key, shares, date, commFee, true);
        } catch (ParseException | URISyntaxException | org.json.simple.parser.ParseException
                 | InterruptedException | IOException e) {
          return "Portfolio Unbalanced Found an exception buying shares of stock " + key;
        }
        if (sellStatus == null) {
          return "Portfolio Unbalanced Stock " + key + " not available for given date " + date;
        } else if (sellStatus.contains("Error")) {
          return "Portfolio Unbalanced Tried selling more stocks than possible with "
                  + shares + " shares for stock" + key + " with weight " + weightData.get(key);
        } else if (sellStatus.equals("success")) {
          outBuild.append("Selling ").append(shares).append(" of ").append(key).append("\n");
        } else {
          return "Portfolio Unbalanced Some error while selling, please retry";
        }


      }


    }


    return outBuild.toString();
  }

  /**
   * Translates the map of stocks: [shares, commission, true_weight] to a new map
   * of stocks: [shares_for_transaction, buy_or_sell] to use in
   * buying or selling shares for rebalancing. The array value [0] is the number of
   * shares to transact, [1] is a double value 0.0 or 1.0 where 0.0 means to sell and
   * 1.0 means to buy.
   *
   * @return The translated map
   */
  // TODO: Use total value function to get total value.
  // For each stock get the current value --> get the true proportion
  // return map for each transaction
  private Map<String, Double[]> translateBalanceMap(Map<String, Double[]> sharesData) {

    Map<String, Double[]> translation = new HashMap<String, Double[]>();

    for (String key : sharesData.keySet()) {

      // {price, shares, trueProp, targetProp, totalValue};
      Double[] dataForStock = sharesData.get(key);
      double price = dataForStock[0];
      double shares = dataForStock[1];
      double targetProp = dataForStock[2];
      double totVal = dataForStock[3];
      double newShares = (targetProp * totVal) / price;
      newShares -= shares;

      double buyOrSell = 1.0;
      if (newShares < 0) {
        buyOrSell = 0.0;
        newShares *= -1;
      }


      newShares = Math.round(newShares * 1000.0) / 1000.0;

      Double[] translatedData = {newShares, buyOrSell};

      translation.put(key, translatedData);


    }

    return translation;
  }


  /**
   * Returns a map of current proportions for each stock in weightData.
   * The map has the format ticker:[current_price, shares, true_proportion,
   * newProportion, Total_Value_stocks_used].
   *
   * @param weightData The weight map given by the user.
   * @return a map of ticker to current_price, shares, true_proportion,
   *      Total_Value_stocks_used.
   */
  private Map<String, Double[]> rebalancePortMap(int portfolioId, String date,
                                                 Map<String, Double> weightData)
          throws IllegalArgumentException {

    JSONObject portfolioStock = (JSONObject) this.portfolio.get(Integer.toString(portfolioId));
    JSONArray stocks = (JSONArray) portfolioStock.get("stocks");
    Iterator iterator = stocks.iterator();
    Map<String, Double[]> stockSharesTemp = new HashMap<>();

    double totalValue = 0.0;
    while (iterator.hasNext()) {
      JSONObject jsonObj = (JSONObject) iterator.next();
      String stock = (String) jsonObj.keySet().toArray()[0];

      // Only want the stocks we chose.
      if (weightData.containsKey(stock)) {

        JSONObject stockObject = (JSONObject) jsonObj.get(stock);
        double curShares = (double) stockObject.get("total");

        double value = -1;
        try {
          value = this.getValueOfStockOnDate(stock, date);
        } catch (ParseException | URISyntaxException | IOException | InterruptedException e) {

          try {
            value = this.getValueOfStockOnImmediateWorkingDate(stock, date);
          } catch (ParseException | InterruptedException | IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
          }
        }
        //
        if (value == -1) {

          try {
            value = this.getValueOfStockOnImmediateWorkingDate(stock, date);
          } catch (ParseException | InterruptedException | IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
          }

          if (value == -1) {
            throw new RuntimeException("Cannot get the value of stock " + stock + "on " + date);
          }
        }

        double stockValue = Math.round(curShares * value * 1000.0) / 1000.0;

        totalValue += stockValue;
        Double[] stockProps = {curShares, stockValue, value};
        stockSharesTemp.put(stock, stockProps);
      }


    }

    // Since there are no weights saved we have to get them by dividing the value by TotalValue
    double totProp = 0.0;
    Map<String, Double[]> stockShares = new HashMap<>();
    for (String key : weightData.keySet()) {

      if (weightData.get(key) > 0) {
        Double[] tempValues;
        if (stockSharesTemp.containsKey(key)) {
          tempValues = stockSharesTemp.get(key);
        } else {
          throw new IllegalArgumentException("Ticker inputted that was not in portfolio."
                  + "Cannot rebalance stocks not contained in portfolio");
        }
        double targetProp = weightData.get(key);
        if (targetProp > 1) {
          targetProp /= 100;
        }

        totProp += targetProp;
        Double[] newVals = {tempValues[2], tempValues[0], targetProp, totalValue};
        stockShares.put(key, newVals);
      } else {
        throw new IllegalArgumentException("Cannot have weights < 0.");
      }

    }
    
    if (Math.round(totProp) != 1.0) {
      throw new IllegalArgumentException("All stock weights must add up to 100 percent");
    }

    return stockShares;

  }
}
