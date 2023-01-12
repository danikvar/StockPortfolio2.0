package views;

import java.util.Map;

/**
 * Interface for View class which has methods declared for displaying the output to the user when
 * invoked by the controller.
 */
public interface StockView {

  /**
   * It is used to display the menu.
   */
  void displayMenu();

  /**
   * Ask the user for stock that exists.
   */
  void createMenuStockName(int value);

  /**
   * Display the stock entered by the user.
   *
   * @param stockName name of the stock given by user.
   */
  void createMenuStockNumber(String stockName);


  /**
   * Give the composition of the portfolio give a hashmap of all stocks and the portfolio id
   * requested.
   *
   * @param portfolioComposition a hashmap of stocks.
   * @param portfolioId          the portfolio id.
   * @param currentPortfolio     the maximum portfolio id in the global file.
   * @param flag                 flag to determine if the view is called for examine or downloading
   *                             the portfolio,
   */
  void examinePortfolio(Map<String, Double> portfolioComposition, int portfolioId,
      int currentPortfolio, int flag);

  /**
   * Display the output whatever is passed.
   *
   * @param result to be displayed.
   */
  void display(String result);

  /**
   * Options when user wants to download a portfolio.
   *
   * @param result the result passed to check the portfolio status.
   */
  void downloadPortfolio(String result);

  /**
   * To display the composition of the portfolio for a particular date, if the date is invalid it is
   * handled as well.
   *
   * @param valueOfPortfolio a hashmap that is to be displayed.
   * @param date             the date on which the portfolio is queried.
   */
  void getValueOfPortfolio(Map<String, Double> valueOfPortfolio, String date);

  /**
   * Displays the menu to opt for flexible or inflexible portfolio.
   */
  void showSubMenu();

  /**
   * Displays the bar chart for a portfolio id between two dates adjusting the scale dynamically.
   *
   * @param portfolioAtAGlance map object containing the star and scale details.
   */
  void portfolioAtAGlance(Map<String, String> portfolioAtAGlance);
}
