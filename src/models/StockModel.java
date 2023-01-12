package models;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Interface for Model class which holds method declarations that can be invoked by the controller
 * class.
 */
public interface StockModel {

  /**
   * Takes a hashmap from the controller and parses it as JSON to save it the global portfolios
   * file.
   *
   * @param stockList a Hashmap of type {A=1,GOOG=2}.
   * @return a String stating the status ofn creation and returns portfolio id if successfully
   *     created.
   * @throws IOException if the portfolios json file has some error.
   */
  String createPortfolio(Map<String, Map<String, String>> stockList, String portfolioType)
          throws IOException, org.json.simple.parser.ParseException,
          ParseException, URISyntaxException,
          InterruptedException;

  /**
   * Takes in a portfolio id and returns the composition of that portfolio.
   *
   * @param portfolioId integer, portfolio id
   * @return a hashmap of composition of stocks.
   * @throws IOException if the portfolios json file has any error.
   */
  Map<String, Double> examinePortfolio(int portfolioId) throws IOException;


  /**
   * Returns the latest portfolio id existing in the json file.
   *
   * @return int, the latest portfolio id existing in the json file.
   */
  int getLatestPortfolioId();

  /**
   * Takes in a path of the file and a hashmap with valid stocks. the file is read and validated if
   * the stocks are valid and if success. it pushes this portfolio in the global portfolios json
   * file.
   *
   * @param relativePath   path of the file that is being uploaded.
   * @param validStocksMap a Hashmap of valid stocks and its active status.
   * @return the status of upload
   * @throws IOException if any of the file errors occur.
   */
  String uploadFile(String relativePath, Map<String, String> validStocksMap, String portfolioType)
          throws IOException, org.json.simple.parser.ParseException,
          ParseException, URISyntaxException,
          InterruptedException;

  /**
   * A boolean check to see if a particular stock is existent in the map of all stocks.
   *
   * @param stockName     the name of stock to be checked.
   * @param validStockMap a hashmap of all valid stocks.
   * @return true/false if the stock exists in the map.
   * @throws IOException if file error.
   */
  boolean ifStockExists(String stockName, Map<String, String> validStockMap)
          throws IOException, URISyntaxException, InterruptedException;

  /**
   * Returns a hashmap of valid stocks and its validity status from a csv file.
   *
   * @return hashmap of valid stocks and its validity status.
   * @throws URISyntaxException   if file not found error.
   * @throws IOException          if file error.
   * @throws InterruptedException if file interruption occues.
   */
  Map<String, String> createMapForValidStocks()
          throws URISyntaxException, IOException, InterruptedException;

  /**
   * Takes in a directory and a portfolio to be downloaded.
   *
   * @param portfolioComposition a hashmap of stocks to be dpwnloaded as json
   * @param filePath             file to be downloaded to.
   * @param portfolioID          the portfolio id that is to be downloaded.
   * @return status of download.
   * @throws IOException if file error,
   */
  String downloadPortfolio(Map<String, Double> portfolioComposition, String filePath,
                           int portfolioID)
          throws IOException;

  /**
   * Check if the file exists on the system.
   *
   * @param filePath path of file.
   * @return boolean if the file exists on the system.
   */
  boolean checkIfValidDirectory(String filePath);

  /**
   * Takes in a portfolio composition and a date and return the total value of composition for that
   * particular date.
   *
   * @param portfolioId int value of portfolio.
   * @param date        date to be queried on.
   * @return a hashmap of the stock name and its value for that date.
   * @throws URISyntaxException   if string is not parsed as URI reference.
   * @throws IOException          a I/O error.
   * @throws InterruptedException if the thread is interrupted.
   * @throws ParseException       if any parse error.
   */
  Map<String, Double> getValueOfPortfolio(int portfolioId, String date)
          throws URISyntaxException, IOException, InterruptedException, ParseException;

  /**
   * Takes in a date and checks if it is of valid format yyyy-MM-DD.
   *
   * @param date a date string
   * @return status if the date is fine
   * @throws ParseException if any parse error.
   */
  String dateValidation(String date) throws ParseException;

  /**
   * Returns a hashmap of stock,date for a value A,2018-01-03 = 23.56.
   *
   * @return a hashmap of above type.
   * @throws IOException        a I/O error.
   * @throws URISyntaxException if string is not parsed as URI reference.
   */
  Map<String, String> createMapForPriceOfStock()
          throws IOException, URISyntaxException, InterruptedException;

  /**
   * Takes in a portfolio composition and returns a hashmap with its value and total composition
   * value.
   *
   * @param portfolioComposition a hashmap of stock and name and quantity.
   * @param date                 date for the value calculatio.
   * @param stockPricesMap       a hashmap of all stocks and value for dates
   * @return a new hashmap with composition value.
   * @throws URISyntaxException   if string is not parsed as URI reference.
   * @throws IOException          a I/O error.
   * @throws InterruptedException if the thread is interrupted.
   * @throws ParseException       if any parse error.
   */
  Map<String, Double> getValueOfPortfolioFromCsv(Map<String, Integer> portfolioComposition,
                                                 String date, Map<String, String> stockPricesMap)
          throws URISyntaxException, IOException, InterruptedException, ParseException;


  /**
   * Returns true if the portfolio has the particular stock.
   *
   * @param portfolioId portfolio to be checked.
   * @param stock       stock to be checked.
   * @return true/false if stock is present.
   */
  boolean hasStock(int portfolioId, String stock);

  /**
   * Check if the particular stock has sufficient quantity in the portfolio.
   *
   * @param portfolioId portfolio to be checked.
   * @param stock       stock to be checked.
   * @param quantity    quantity ot be checked.
   * @return true/false if quantity is present.
   */
  boolean hasStockQuantity(int portfolioId, String stock, Long quantity);

  /**
   * Get the last buy date among transactions in a portfolio.
   *
   * @param portfolioId portfolio to be checked.
   * @param stock       stock to be checked.
   * @return String of latest date.
   */
  String getLatestBuyDate(int portfolioId, String stock);

  /**
   * Purchase a stock onto a portfolio for a given date and commission.
   *
   * @param portfolioId portfolio where the stock is being purchased into.
   * @param stock       stock that is being purchased.
   * @param quantity    of stock being purchased.
   * @param date        date of purchase.
   * @param commission  commission of the buy trade.
   * @return Status of the purchase.
   * @throws ParseException                        if parse error.
   * @throws URISyntaxException                    if string is not parsed as URI reference.
   * @throws IOException                           if I/O error.
   * @throws InterruptedException                  if the thread is interrupted.
   * @throws org.json.simple.parser.ParseException if json parse error.
   */
  String buyStockOnDate(int portfolioId, String stock, Long quantity, String date,
                        float commission)

          throws ParseException, URISyntaxException, IOException, InterruptedException,
          org.json.simple.parser.ParseException;

  /**
   * Sell a stock onto a portfolio for a given date and commission.
   *
   * @param portfolioId portfolio where the stock is being sold from.
   * @param stock       stock that is being sold.
   * @param quantity    of stock being sold.
   * @param date        date of sell.
   * @param commission  commission of the sell trade.
   * @return Status of the sell.
   * @throws ParseException                        if parse error.
   * @throws URISyntaxException                    if string is not parsed as URI reference.
   * @throws IOException                           if I/O error.
   * @throws InterruptedException                  if the thread is interrupted.
   * @throws org.json.simple.parser.ParseException if json parse error.
   */
  String sellStockOnDate(int portfolioId, String stock, Long quantity, String date,
                         float commission)
          throws ParseException, URISyntaxException, IOException, InterruptedException,
          org.json.simple.parser.ParseException;

  /**
   * Returns if the portfolio is flexible or inflexible.
   *
   * @param portfolioId that is being checked.
   * @return String if the portfolio is flexible or inflexible.
   */
  String getTypeOfPortfolio(int portfolioId);

  /**
   * Checks if the global portfolio file has atleast one flexible portfolio or not.
   *
   * @return true/false if atleast one flexible portfolio or not.
   */
  boolean hasFlexiblePortfolio();

  /**
   * For a given date return the cost basis of the portfolio till that date.
   *
   * @param portfolioId portfolio where it is checked.
   * @param date        date till the cost basis to be calculated.
   * @return the cost basis in double.
   */
  double getCostBasisPortfolio(int portfolioId, String date);

  /**
   * Returns the map object for portfolio at a glance displaying a bar graph of trend between two
   * dates.
   *
   * @param portfolioId            to get the bar chart.
   * @param fromDate               begin date.
   * @param toDate                 end date.
   * @param differenceBetweenDates difference of dates between from end end.
   * @return a map of bar chart.
   * @throws ParseException       if parse error.
   * @throws URISyntaxException   if string is not parsed as URI reference.
   * @throws IOException          a I/O error.
   * @throws InterruptedException if the thread is interrupted.
   */
  Map<String, String> portfolioAtAGlance(int portfolioId, String fromDate, String toDate,
                                         long differenceBetweenDates)
          throws ParseException, URISyntaxException, IOException, InterruptedException;

  /**
   * Lists all flexible portfolios.
   *
   * @return list of flexible portfolios
   */
  List<String> listOfFlexiblePortfolios();

  /**
   * This method is used to rebalance a portfolio to the weights given by the user.
   *
   * @param portfolioId is the ID of the portfolio.
   * @param date        is the date for rebalancing.
   * @param weightData  is the hash map of stock names and new weights assigned by user.
   * @param commFee     is the commission fee charged for the transactions.
   * @return message stating the status of rebalancing.
   */

  String rebalancePort(int portfolioId, String date, Map<String, Double> weightData,
                       String commFee);
}
