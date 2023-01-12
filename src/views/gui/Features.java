package views.gui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.json.simple.parser.ParseException;

/**
 * This interface has all the features that the app contains.
 */
public interface Features {

  /**
   * Method used to create a frame for creating a portfolio.
   *
   * @param stockList list of stocks.
   * @return message.
   * @throws IOException              I/O exception.
   * @throws ParseException           file parse error.
   * @throws java.text.ParseException java text parse error.
   * @throws URISyntaxException       uri exception.
   * @throws InterruptedException     if thread is interrupted.
   */
  String createPortfolioGUI(List<List<String>> stockList)
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException;

  /**
   * Method used to create a frame for buying stocks in  a portfolio.
   *
   * @param portfolioId portfolio id in which the stock has to be bought.
   * @param stockName   name of stock.
   * @param quantity    quantity of stock.
   * @param date        date of trade.
   * @param commission  commission paid.
   * @return message.
   * @throws IOException              I/O exception.
   * @throws ParseException           file parse error.
   * @throws java.text.ParseException java text parse error.
   * @throws URISyntaxException       uri exception.
   * @throws InterruptedException     if thread is interrupted.
   */
  String buyStocks(String portfolioId, String stockName, String quantity, String date,
      String commission)
      throws java.text.ParseException, URISyntaxException, IOException, ParseException,
      InterruptedException;

  /**
   * Method used to create a frame for selling stocks in  a portfolio.
   *
   * @param portfolioId portfolio id in which the stock has to be sold.
   * @param stockName   name of stock.
   * @param quantity    quantity of stock.
   * @param date        date of trade.
   * @param commission  commission paid.
   * @return message.
   * @throws IOException              I/O exception.
   * @throws ParseException           file parse error.
   * @throws java.text.ParseException java text parse error.
   * @throws URISyntaxException       uri exception.
   * @throws InterruptedException     if thread is interrupted.
   */
  String sellStocks(String portfolioId, String stockName, String quantity, String date,
      String commission)
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException;

  /**
   * Rebalances a portfolio at a given id for a certain date, matching the
   * proportions inputted by the user.
   * @param date The date to rebalance.
   * @param commission The commission for this transaction.
   * @param portfolioId The id of the portfolio doing the rebalancing.
   * @param weightData A map of stocks to weights for rebalancing.
   *
   * @return A string indicating the success of rebalancing a portofolio.
   */
  String rebalance(String portfolioId, String date,
                   Map<String, Double> weightData, String commission)
          throws IOException, ParseException, java.text.ParseException, URISyntaxException,
          InterruptedException;

  /**
   * Method to create a frame for getting the cost basis of portfolio.
   *
   * @param portfolioId id of portfolio.
   * @param date        date on which the cost basis has to be performed.
   * @return message.
   * @throws IOException    I/O exception.
   * @throws ParseException file parse error.
   */
  String costBasis(int portfolioId, String date) throws IOException, ParseException;

  /**
   * Method to create frame for dollar cost average strategy.
   *
   * @param portfolioId      id of portfolio.
   * @param nAmount          amount to be invested.
   * @param commissionAmount commission to be paid.
   * @param date             date of purchase.
   * @param stockWeights     weights stocks.
   * @return message.
   * @throws IOException              I/O exception.
   * @throws ParseException           file parse error.
   * @throws java.text.ParseException java text parse error.
   * @throws URISyntaxException       uri exception.
   * @throws InterruptedException     if thread is interrupted.
   */
  String addDollarAverageStrategyToExistingPortfolio(int portfolioId, Double nAmount,
      Double commissionAmount, String date, Map<String, Double> stockWeights)
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException;

  /**
   * Method to create frame for creating portfolio with dollar cost average strategy.
   *
   * @param nAmount          amount to be invested.
   * @param commissionAmount commission to be paid.
   * @param nFrequencyInDays frequency of investment.
   * @param startDate        start date.
   * @param endDate          end date.
   * @param stockWeights     weights of stocks.
   * @return message.
   * @throws IOException              I/O exception.
   * @throws ParseException           file parse error.
   * @throws java.text.ParseException java text parse error.
   * @throws URISyntaxException       uri exception.
   * @throws InterruptedException     if thread is interrupted.
   */
  String createPortfolioWithDollarAverageStrategy(Double nAmount, Double commissionAmount,
      int nFrequencyInDays, String startDate, String endDate, Map<String, Double> stockWeights)
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException;

  /**
   * Method used to download a portfolio using GUI.
   *
   * @param filepath    path of file.
   * @param portfolioId portfolio ID.
   * @return message.
   * @throws IOException    I/O error.
   * @throws ParseException parse error.
   */
  String downloadPortfolio(String filepath, String portfolioId) throws IOException, ParseException;

  /**
   * Method used to upload a portfolio.
   *
   * @param filepath path of file.
   * @return message.
   * @throws IOException              I/O exception.
   * @throws ParseException           file parse error.
   * @throws java.text.ParseException java text parse error.
   * @throws URISyntaxException       uri exception.
   * @throws InterruptedException     if thread is interrupted.
   */
  String uploadPortfolio(String filepath)
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException;

  /**
   * Method to get list of available portfolios.
   *
   * @return list of portfolios.
   * @throws IOException    I/O error.
   * @throws ParseException parse error.
   */
  List<String> getListOfPortfolios() throws IOException, ParseException;

  /**
   * Method to get the latest portfolio ID.
   *
   * @return latest portfolio ID.
   * @throws IOException    I/O error.
   * @throws ParseException parse error.
   */
  int getLatestPortfolio()
      throws IOException, ParseException;

  /**
   * Method to check if there are any flexible portfolios.
   *
   * @return true/false.
   * @throws IOException    I/O error.
   * @throws ParseException parse error.
   */
  boolean getHasFlexiblePortfolio()
      throws IOException, ParseException;


  /**
   * Method to create the frame to show the performance of the portfolio.
   *
   * @param portfolioID ID of portfolio.
   * @param fromDate    start date.
   * @param toDate      end date.
   * @return map of data that has to be displayed.
   * @throws IOException              I/O exception.
   * @throws ParseException           file parse error.
   * @throws java.text.ParseException java text parse error.
   * @throws URISyntaxException       uri exception.
   * @throws InterruptedException     if thread is interrupted.
   */
  Map<String, String> getPortfolioAtAGlance(String portfolioID, String fromDate, String toDate)
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException;


  /**
   * Method to create a frame for getting the value of portfolio.
   *
   * @param portfolioId id of portfolio.
   * @param date        date on which the cost basis has to be performed.
   * @return message.
   * @throws IOException    I/O exception.
   * @throws ParseException file parse error.
   */
  Map<String, Double> getValueOfPortfolio(int portfolioId, String date)
      throws IOException, ParseException, URISyntaxException, java.text.ParseException,
      InterruptedException;
}
