package models;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Map;

/**
 * This interface handles all the business logic for creating portfolios based on some strategy.
 */
public interface StockModelStrategy {

  /**
   * A method to create a portfolio based on the dollar cost average strategy.
   *
   * @param nStocks          number of stocks to be added.
   * @param nAmount          amount of money to be invested.
   * @param commissionAmount commission paid.
   * @param nFrequencyInDays frequency of buying of stocks.
   * @param startDate        start date of the strategy.
   * @param endDate          end date of the strategy.
   * @param stockWeights     weights of stocks in strategy.
   * @return success or error message.
   * @throws IOException                           I/O exception.
   * @throws org.json.simple.parser.ParseException json parse error.
   * @throws ParseException                        file parse error.
   * @throws URISyntaxException                    URI exception.
   * @throws InterruptedException                  if thread is interrupted.
   */
  String createStartToFinishDcaPortfolio(int nStocks, Double nAmount, Double commissionAmount,
      int nFrequencyInDays, String startDate, String endDate,
      Map<String, Double> stockWeights)
      throws IOException, org.json.simple.parser.ParseException, ParseException, URISyntaxException,
      InterruptedException;

  /**
   * Add the dollar cost average strategy for the existing portfolio.
   *
   * @param portfolioId      portfolio ID to which the strategy has to be added.
   * @param nAmount          amount to be invested.
   * @param commissionAmount commission that has been paid.
   * @param date             start date of strategy.
   * @param stockWeights     weights of stocks in strategy.
   * @return message.
   * @throws IOException                           I/O exception.
   * @throws org.json.simple.parser.ParseException json parse error.
   * @throws ParseException                        file parse error.
   * @throws URISyntaxException                    URI exception.
   * @throws InterruptedException                  if thread is interrupted.
   */
  String addNonRecurringDcaExistingPortfolio(int portfolioId, Double nAmount,
      Double commissionAmount, String date, Map<String, Double> stockWeights)
      throws ParseException, URISyntaxException, IOException, org.json.simple.parser.ParseException,
      InterruptedException;

}
