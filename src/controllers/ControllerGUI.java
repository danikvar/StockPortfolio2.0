package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import models.Model;
import models.ModelStrategy;
import models.StockModel;
import models.StockModelStrategy;
import views.gui.Features;
import views.gui.GUIView;

/**
 * Class that controls the application when the user wants to see a GUI on the screen. This class
 * implements the methods of Features and StockControllerGUI interface.
 */
public class ControllerGUI implements Features, StockControllerGUI {


  Map<String, String> validStocksMap;
  String portfolioFilePath;

  /**
   * Constructor of the ControllerGUI class, that is used to set the values that are used.
   *
   * @param model Model object of StockModel.
   * @param path  path of the portfolio file.
   * @throws URISyntaxException   if there is a URI exception.
   * @throws IOException          when there is an I/O exception.
   * @throws InterruptedException if the thread is interrupted.
   */
  public ControllerGUI(StockModel model, String path) throws URISyntaxException, IOException,
          InterruptedException {
    InputStream is = getClass().getResourceAsStream("/config/config.properties");
    Properties prop = new Properties();
    prop.load(is);
    Object apiUsed = prop.getProperty("API");
    API api = null;
    if (Objects.equals(apiUsed, "AlphaVantageClient")) {
      api = new AlphaVantageClient();
    }
    api.getListingStatus();
    this.validStocksMap = model.createMapForValidStocks();
    this.portfolioFilePath = path;
  }

  @Override
  public String createPortfolioGUI(List<List<String>> stockList)
          throws IOException, org.json.simple.parser.ParseException, ParseException,
          URISyntaxException, InterruptedException {
    StockModel model = new Model(portfolioFilePath);
    Map<String, Map<String, String>> stockMap = new HashMap<>();
    for (List<String> strings : stockList) {
      String stockName = strings.get(0);
      Double stockNumber = Double.parseDouble(strings.get(1));
      Float commissionForTrade = Float.valueOf(strings.get(3));
      String date = strings.get(2);
      createHashMapForStocksProvided(stockMap, stockName, stockNumber, commissionForTrade, date);

    }

    return model.createPortfolio(stockMap, "Flexible");
  }

  /**
   * Create hash map for the stock provided.
   *
   * @param stockMap           map input.
   * @param stockName          stock name.
   * @param stockNumber        number of stocks.
   * @param commissionForTrade commission fee paid.
   * @param date               date of creation.
   */
  private void createHashMapForStocksProvided(Map<String, Map<String, String>> stockMap,
                                              String stockName, Double stockNumber,
                                              Float commissionForTrade, String date) {
    Controller.createHashMapForStocks(stockMap, stockName, stockNumber, commissionForTrade, date);
  }

  @Override
  public String buyStocks(String portfolioId, String stockName, String quantity, String date,
                          String commission)
          throws ParseException, URISyntaxException, IOException,
          org.json.simple.parser.ParseException, InterruptedException {
    StockModel model = new Model(portfolioFilePath);
    return model.buyStockOnDate(Integer.parseInt(portfolioId), stockName, Long.parseLong(quantity),
            date,
            Float.parseFloat(commission));
  }

  @Override
  public String sellStocks(String portfolioId, String stockName, String quantity, String date,
                           String commission)
          throws IOException, org.json.simple.parser.ParseException, ParseException,
          URISyntaxException, InterruptedException {
    StockModel model = new Model(portfolioFilePath);
    if (model.hasStock(Integer.parseInt(portfolioId), stockName.toUpperCase())) {
      if (!model.hasStockQuantity(Integer.parseInt(portfolioId), stockName.toUpperCase(),
              Long.valueOf(quantity))) {
        return
                "You don't have sufficient stocks to sell";
      }
      String latest_date = model.getLatestBuyDate(Integer.parseInt(portfolioId),
              stockName.toUpperCase());

      if (Controller.dateCompare(date, latest_date) < 0) {
        return "You cannot sell a stock before the last buy i.e "
                + latest_date;
      }
    } else {
      return "Provided stock doesn't exist in the portfolio.";
    }
    return model.sellStockOnDate(Integer.parseInt(portfolioId), stockName,
            Long.parseLong(quantity), date,
            Float.parseFloat(commission));

  }

  @Override
  public String rebalance(String portfolioId, String date,
                          Map<String, Double> weightData, String commission)
          throws IOException, org.json.simple.parser.ParseException {
    StockModel model = new Model(portfolioFilePath);
    int id;
    try {
      id = Integer.parseInt(portfolioId);
    } catch (Exception e) {
      return "Portfolio Unbalanced Cannot parse id of portfolio.";
    }
    for (String key : weightData.keySet()) {
      if (!model.hasStock(Integer.parseInt(portfolioId), key.toUpperCase())) {
        return "Portfolio Unbalanced Cannot balance stocks that are not in the portfolio";
      }
    }

    return model.rebalancePort(id, date, weightData, commission);
  }

  @Override
  public String costBasis(int portfolioId, String date)

          throws IOException, org.json.simple.parser.ParseException {
    StockModel model = new Model(portfolioFilePath);
    Double costBasis = model.getCostBasisPortfolio(portfolioId, date);
    DecimalFormat df = new DecimalFormat("0.00");
    String result = "\033[1;32m" + "Total cost basis of portfolio " + portfolioId
            + " for the date " + date + ": $" + df.format(costBasis);
    return result;
  }

  @Override
  public String addDollarAverageStrategyToExistingPortfolio(int portfolioId, Double nAmount,
                                                            Double commissionAmount, String date,
                                                            Map<String, Double> stockWeights)
          throws IOException, org.json.simple.parser.ParseException, ParseException,
          URISyntaxException, InterruptedException {
    StockModelStrategy model = new ModelStrategy(portfolioFilePath);
    String result = model.addNonRecurringDcaExistingPortfolio(portfolioId, nAmount,
            commissionAmount,
            date, stockWeights);
    return result;
  }

  @Override
  public String createPortfolioWithDollarAverageStrategy(Double nAmount, Double commissionAmount,
                                                         int nFrequencyInDays, String startDate,
                                                         String endDate,
                                                         Map<String, Double> stockWeights)
          throws IOException, org.json.simple.parser.ParseException, ParseException,
          URISyntaxException, InterruptedException {
    StockModelStrategy model = new ModelStrategy(portfolioFilePath);
    if (endDate.isEmpty()) {
      endDate = null;
    }
    int nStocks = stockWeights.size();
    String result = model.createStartToFinishDcaPortfolio(nStocks, nAmount, commissionAmount,
            nFrequencyInDays, startDate, endDate, stockWeights);
    return result;
  }

  @Override
  public String downloadPortfolio(String filepath, String portfolioId)
          throws IOException, org.json.simple.parser.ParseException {
    StockModel model = new Model(portfolioFilePath);
    Map<String, Double> portfolioComposition = model.examinePortfolio(
            Integer.parseInt(portfolioId));

    return model.downloadPortfolio(portfolioComposition, filepath, Integer.parseInt(portfolioId));

  }

  @Override
  public String uploadPortfolio(String filepath)
          throws IOException, org.json.simple.parser.ParseException, ParseException,
          URISyntaxException, InterruptedException {
    StockModel model = new Model(portfolioFilePath);
    String result = model.uploadFile(filepath, this.validStocksMap, "Flexible");

    if (Objects.equals(result, "File Error") || Objects.equals(result, "JSON Error")
            || result.contains("date")) {
      if (Objects.equals(result, "File Error")) {
        return "Invalid file path provided, please provide a valid file path: ";
      } else if (Objects.equals(result, "JSON Error")) {
        return "Invalid JSON file provided, please provide a valid file: ";
      } else {
        return
                result + ", please update the file and reupload it: ";
      }
    } else {
      return result;
    }

  }

  @Override
  public List<String> getListOfPortfolios()
          throws IOException, org.json.simple.parser.ParseException {
    StockModel model = new Model(portfolioFilePath);
    return model.listOfFlexiblePortfolios();
  }

  @Override
  public int getLatestPortfolio()
          throws IOException, org.json.simple.parser.ParseException {
    StockModel model = new Model(portfolioFilePath);
    return model.getLatestPortfolioId();
  }

  @Override
  public boolean getHasFlexiblePortfolio()
          throws IOException, org.json.simple.parser.ParseException {
    StockModel model = new Model(portfolioFilePath);
    return model.hasFlexiblePortfolio();
  }

  @Override
  public Map<String, String> getPortfolioAtAGlance(String portfolioID, String fromDate,
                                                   String toDate)
          throws IOException, org.json.simple.parser.ParseException, ParseException,
          URISyntaxException, InterruptedException {
    StockModel model = new Model(portfolioFilePath);
    Validations dateValidator = new Validations();
    return model.portfolioAtAGlance(Integer.parseInt(portfolioID), fromDate, toDate,
            dateValidator.checkIfToDateIsMoreThanFromDate(fromDate, toDate));
  }

  @Override
  public Map<String, Double> getValueOfPortfolio(int portfolioId, String date)
          throws IOException, org.json.simple.parser.ParseException, URISyntaxException,
          ParseException, InterruptedException {
    StockModel model = new Model(portfolioFilePath);
    return model.getValueOfPortfolio(portfolioId, date);

  }

  @Override
  public void setView(GUIView view) throws IOException, org.json.simple.parser.ParseException {
    StockModel model = new Model(portfolioFilePath);
    view.addFeatures(this, this.validStocksMap);
  }
}
