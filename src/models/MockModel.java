package models;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * A Mock model that mimics the actual model which is used to test the inputs received by the model
 * from the controller.
 */
public class MockModel implements StockModel {

  StringBuilder log;

  StockModel model;

  /**
   * Constructs the mock model for testing purpose whether to check if the controller is passing the
   * right data to the model.
   *
   * @param portfolioFilePath path of the global portfolios json file.
   * @param log               String log where all the loggers are stored.
   * @throws IOException                           a I/O error.
   * @throws org.json.simple.parser.ParseException if json error.
   */
  public MockModel(String portfolioFilePath, StringBuilder log)
      throws IOException, org.json.simple.parser.ParseException {
    model = new Model(portfolioFilePath);
    this.log = log;
  }


  @Override
  public String createPortfolio(Map<String, Map<String, String>> stockList, String portfolioType)
      throws IOException, org.json.simple.parser.ParseException, ParseException,
      URISyntaxException, InterruptedException {
    log.append("Received :" + stockList + " Type: " + portfolioType);
    return model.createPortfolio(stockList, portfolioType);
  }

  @Override
  public Map<String, Double> examinePortfolio(int portfolioId) throws IOException {
    log.append(" Received :" + portfolioId);
    return model.examinePortfolio(portfolioId);
  }

  @Override
  public int getLatestPortfolioId() {
    return model.getLatestPortfolioId();
  }

  @Override
  public String uploadFile(String relativePath, Map<String, String> validStocksMap,
      String portfolioType)
      throws IOException, org.json.simple.parser.ParseException, ParseException,
      URISyntaxException, InterruptedException {
    return model.uploadFile(relativePath, validStocksMap, portfolioType);
  }

  @Override
  public boolean ifStockExists(String stockName, Map<String, String> validStockMap)
      throws IOException, URISyntaxException, InterruptedException {
    return model.ifStockExists(stockName, validStockMap);
  }

  @Override
  public Map<String, String> createMapForValidStocks()
      throws URISyntaxException, IOException, InterruptedException {
    return model.createMapForValidStocks();
  }

  @Override
  public String downloadPortfolio(Map<String, Double> portfolioComposition, String filePath,
      int portfolioID) throws IOException {
    return model.downloadPortfolio(portfolioComposition, filePath, portfolioID);
  }

  @Override
  public boolean checkIfValidDirectory(String filePath) {
    return model.checkIfValidDirectory(filePath);
  }

  @Override
  public Map<String, Double> getValueOfPortfolio(int portfolioId, String date)
      throws URISyntaxException, IOException, InterruptedException, ParseException {
    log.append(" Received :" + portfolioId + " Date : " + date);
    return model.getValueOfPortfolio(portfolioId, date);
  }

  @Override
  public String dateValidation(String date) throws ParseException {
    return model.dateValidation(date);
  }

  @Override
  public Map<String, String> createMapForPriceOfStock()
      throws IOException, URISyntaxException, InterruptedException {

    return model.createMapForPriceOfStock();
  }

  @Override
  public Map<String, Double> getValueOfPortfolioFromCsv(Map<String, Integer> portfolioComposition,
      String date, Map<String, String> stockPricesMap)
      throws URISyntaxException, IOException, InterruptedException, ParseException {

    return model.getValueOfPortfolioFromCsv(portfolioComposition, date, stockPricesMap);
  }

  @Override
  public boolean hasStock(int portfolioId, String stock) {
    return model.hasStock(portfolioId, stock);
  }

  @Override
  public boolean hasStockQuantity(int portfolioId, String stock, Long quantity) {
    return model.hasStockQuantity(portfolioId, stock, quantity);
  }

  @Override
  public String getLatestBuyDate(int portfolioId, String stock) {
    return model.getLatestBuyDate(portfolioId, stock);
  }

  @Override
  public String buyStockOnDate(int portfolioId, String stock, Long quantity, String date,
      float commission)
      throws ParseException, URISyntaxException, IOException, InterruptedException,
      org.json.simple.parser.ParseException {
    log.append(" Received :" + portfolioId + " Date : " + date + " Commission :" + commission
        + " Stock:" + stock + " Quantity:" + quantity);
    return model.buyStockOnDate(portfolioId, stock, quantity, date, commission);
  }

  @Override
  public String sellStockOnDate(int portfolioId, String stock, Long quantity, String date,
      float commission)
      throws ParseException, URISyntaxException, IOException, InterruptedException,
      org.json.simple.parser.ParseException {
    log.append(" Received :" + portfolioId + " Date : " + date + " Commission :" + commission
        + " Stock:" + stock + " Quantity:" + quantity);
    return model.sellStockOnDate(portfolioId, stock, quantity, date, commission);
  }

  @Override
  public String getTypeOfPortfolio(int portfolioId) {
    return model.getTypeOfPortfolio(portfolioId);
  }

  @Override
  public boolean hasFlexiblePortfolio() {
    return model.hasFlexiblePortfolio();
  }

  @Override
  public double getCostBasisPortfolio(int portfolioId, String date) {
    log.append(" Received :" + portfolioId + " Date : " + date);
    return model.getCostBasisPortfolio(portfolioId, date);
  }

  @Override
  public Map<String, String> portfolioAtAGlance(int portfolioId, String fromDate, String toDate,
      long differenceBetweenDates)
      throws ParseException, URISyntaxException, IOException, InterruptedException {
    log.append(" Received :" + portfolioId + " From : " + fromDate + " To :" + toDate
        + " Difference:" + differenceBetweenDates);
    return model.portfolioAtAGlance(portfolioId, fromDate, toDate, differenceBetweenDates);
  }

  @Override
  public List<String> listOfFlexiblePortfolios() {
    return null;
  }

  @Override
  public String rebalancePort(int portfolioId, String date, Map<String, Double> weightData,
                              String commFee) {
    return "";
  }


}
