package controllers;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Interface for the API class that decides the basic functionality of the data source. It holds
 * methods such as to get a dump of all stocks data and functions to get get data for a ticker
 * for a particular date.
 */
public interface API {

  /**
   * Downloads the CSV file of all stocks available on API.
   *
   * @throws URISyntaxException   if string is not parsed as URI reference.
   * @throws IOException          a I/O error.
   * @throws InterruptedException if the thread is interrupted.
   */
  void getListingStatus() throws URISyntaxException, IOException, InterruptedException;

  /**
   * Returns the stock price for a particular ticker on a particular date.
   *
   * @param ticker the stock to fetch the price of.
   * @param date   the date to be fetched on.
   * @return a String of the value of the stock.
   * @throws IOException          a I/O error.
   */
  String getStockData(String ticker, String date)
      throws  IOException;

  /**
   * Returns the current price of stock if the date is current date.
   *
   * @param ticker the stock ticker name.
   * @return the current price of stock.
   * @throws URISyntaxException   if string is not parsed as URI reference.
   * @throws IOException          a I/O error.
   * @throws InterruptedException if the thread is interrupted.
   */
  String getCurrentStockData(String ticker)
      throws URISyntaxException, IOException, InterruptedException;
}
