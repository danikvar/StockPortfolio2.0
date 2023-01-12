package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

import models.MockModel;
import models.Model;
import models.StockModel;
import views.StockView;
import views.View;
import views.gui.GUIView;

/**
 * This class is a Controller class which is responsible for designating the method calls to the
 * Model class and View class respectively. Controller class implements the StockController
 * interface.
 */
public class Controller implements StockController {

  Readable in = null;
  Appendable out = null;
  private Object apiUsed = "";
  Map<String, String> validStocksMap;
  private GUIView guiView;

  private PrintStream output;
  Scanner scan;
  private static final DecimalFormat df = new DecimalFormat("0.00");
  String portfolioFilePath;
  StringBuilder modelType;
  StringBuilder logger;


  /**
   * Constructs the controller class for the MVC architecture, that acts as a mediator between model
   * and view class.
   *
   * @param in     inputs received to the controller.
   * @param out    outputs received to the controller.
   * @param input  coupled input for test.
   * @param output coupled output for test.
   * @param path   path of the global portfolios json file.
   * @param log    distinguisher for model type if main or test.
   * @throws URISyntaxException                    if string is not parsed as URI reference.
   * @throws IOException                           a I/O error.
   * @throws InterruptedException                  if the thread is interrupted.
   * @throws org.json.simple.parser.ParseException if error in json parsing.
   */
  public Controller(Readable in, Appendable out, InputStream input, PrintStream output, String path,
                    StringBuilder log)
          throws URISyntaxException, IOException, InterruptedException,
          org.json.simple.parser.ParseException {
    this.in = in;
    this.out = out;
    InputStream is = getClass().getResourceAsStream("/config/config.properties");
    Properties prop = new Properties();
    prop.load(is);
    this.apiUsed = prop.getProperty("API");
    API api = null;
    if (Objects.equals(this.apiUsed, "AlphaVantageClient")) {
      api = new AlphaVantageClient();
    }
    api.getListingStatus();
    StockModel model = new Model(path);
    this.validStocksMap = model.createMapForValidStocks();
    this.output = output;
    this.scan = new Scanner(input);
    this.portfolioFilePath = path;
    this.modelType = log;
  }

  /**
   * Constructs the controller class for the MVC architecture, that acts as a mediator between model
   * and view class, this constructor is for the testing.
   *
   * @param in     inputs received to the controller.
   * @param out    outputs received to the controller.
   * @param input  coupled input for test.
   * @param output coupled output for test.
   * @param path   path of the global portfolios json file.
   * @param type   type of method main or test
   * @param logger log for capturing output
   * @throws URISyntaxException                    if string is not parsed as URI reference.
   * @throws IOException                           a I/O error.
   * @throws InterruptedException                  if the thread is interrupted.
   * @throws org.json.simple.parser.ParseException if error in json parsing.
   */
  public Controller(Readable in, Appendable out, InputStream input, PrintStream output, String path,
                    StringBuilder type, StringBuilder logger)
          throws URISyntaxException, IOException, InterruptedException,
          org.json.simple.parser.ParseException {
    this.in = in;
    this.out = out;
    InputStream is = getClass().getResourceAsStream("/config/config.properties");
    Properties prop = new Properties();
    prop.load(is);
    this.apiUsed = prop.getProperty("API");
    API api = null;
    if (Objects.equals(this.apiUsed, "AlphaVantageClient")) {
      api = new AlphaVantageClient();
    }
    api.getListingStatus();
    StockModel model = new Model(path);
    this.validStocksMap = model.createMapForValidStocks();
    this.output = output;
    this.scan = new Scanner(input);
    this.portfolioFilePath = path;
    this.modelType = type;
    this.logger = logger;
  }


  @Override
  public void goController() throws IOException,
          URISyntaxException, InterruptedException, ParseException,
          org.json.simple.parser.ParseException {
    switchCases();
  }

  /**
   * This method performs the switch operations based on the user input from the CLI.
   *
   * @throws IOException          when invalid input provided.
   * @throws URISyntaxException   when there is an exception in the URI Building.
   * @throws InterruptedException when there is an interrupted exception.
   * @throws ParseException       when there is a Parse error.
   */
  private void switchCases()
          throws IOException, URISyntaxException, InterruptedException, ParseException,
          org.json.simple.parser.ParseException {
    StockModel model;
    if ((this.modelType.toString().equals("test"))) {
      model = new MockModel(portfolioFilePath, logger);
    } else {
      model = new Model(portfolioFilePath);
    }
    StockView view = new View(this.output);
    view.display("Menu options");
    view.displayMenu();
    int value;
    while (true) {
      try {
        value = Integer.parseInt(scan.next());
        break;
      } catch (Exception e) {
        view.display("\033[0;31m" + "Invalid Input provided, "
                + "please provide a valid number between 1 and 10");
      }
    }
    switch (value) {
      case 1:
        while (true) {
          view.showSubMenu();
          int subMenuValue = subMenuHelper(view);
          int numStocks;
          view.display("Enter number of stocks to be added");
          while (true) {
            try {
              numStocks = Integer.parseInt(scan.next());
              break;
            } catch (Exception e) {
              view.display("\033[0;31m" + "Invalid Input provided, "
                      + "please provide a valid number");
            }

          }

          Map<String, Map<String, String>> stockList = buyNStocksHelper(numStocks, model, view,
                  subMenuValue);
          String result;
          if (subMenuValue == 1) {
            result = model.createPortfolio(stockList, "Flexible");
          } else {
            result = model.createPortfolio(stockList, "InFlexible");
          }
          view.display("\033[1;32m" + result);
          view.display("Do you want add another portfolio (y/n) : ");
          String option = scan.next();
          if (option.equals("n")) {
            switchCases();
          } else if (option.equals("y")) {
            continue;
          }
          return;
        }
      case 2:
        while (true) {
          view.showSubMenu();
          int subMenuValue = subMenuHelper(view);
          view.display("Provide the relative path of the json file "
                  + "that has to be uploaded:  ");
          String relativePath = scan.next();
          String result;
          if (subMenuValue == 1) {
            result = model.uploadFile(relativePath, this.validStocksMap, "Flexible");
          } else {
            result = model.uploadFile(relativePath, this.validStocksMap, "InFlexible");
          }

          while (Objects.equals(result, "File Error") || Objects.equals(result, "JSON Error")
                  || result.contains("date")) {
            if (Objects.equals(result, "File Error")) {
              view.display(
                      "\033[0;31m"
                              + "Invalid file path provided, please provide a valid file path: ");
            } else if (Objects.equals(result, "JSON Error")) {
              view.display(
                      "\033[0;31m"
                              + "Invalid JSON file provided, please provide a valid file: ");
            } else {
              view.display(
                      "\033[0;31m"
                              + result + ", please update the file and reupload it: ");
            }

            relativePath = scan.next();
            if (subMenuValue == 1) {
              result = model.uploadFile(relativePath, this.validStocksMap, "Flexible");
            } else {
              result = model.uploadFile(relativePath, this.validStocksMap, "InFlexible");
            }

          }
          view.display(result);
          view.display("Do you want to upload another portfolio (y/n) : ");
          String option = scan.next();
          if (option.equals("n")) {
            switchCases();
          } else if (option.equals("y")) {
            continue;
          }
          return;
        }

      case 3:
        while (true) {
          int latestPortfolioId = model.getLatestPortfolioId();
          int portfolioId;
          if (latestPortfolioId == 0) {
            view.display(
                    "\033[0;31m"
                            + "No portfolio is created yet! Please create or "
                            + "upload a portfolio before you look for one!!");
            view.display("");
            switchCases();
          } else {
            view.display("Enter portfolio ID to be displayed");
            try {
              portfolioId = Integer.parseInt(scan.next());
            } catch (Exception e) {
              view.display("\033[0;31m" + "Invalid ID provided, please provide a valid ID.");
              continue;
            }
            Map<String, Double> portfolioComposition = model.examinePortfolio(portfolioId);
            view.examinePortfolio(portfolioComposition, portfolioId, latestPortfolioId, 0);
            view.display("Do you want to examine another portfolio (y/n) : ");
            String option = scan.next();
            if (option.equals("n")) {
              switchCases();
            } else if (option.equals("y")) {
              continue;
            }
          }
          return;
        }
      case 4:
        while (true) {
          int latestPortfolioId = model.getLatestPortfolioId();
          int portfolioId;
          if (latestPortfolioId == 0) {
            view.display(
                    "\033[0;31m"
                            + "No portfolio is created yet! Please create or upload"
                            + " a portfolio before you look to download one!!");
            view.display("");
            switchCases();
          } else {
            view.display("Enter portfolio ID to be downloaded");
            try {
              portfolioId = Integer.parseInt(scan.next());
            } catch (Exception e) {
              view.display("\033[0;31m" + "Invalid ID provided, please provide a valid ID.");
              continue;
            }
            Map<String, Double> portfolioComposition = model.examinePortfolio(portfolioId);
            view.examinePortfolio(portfolioComposition, portfolioId, latestPortfolioId, 1);
            if (portfolioComposition != null) {
              view.display("Enter the directory in which the file has to be saved: ");
              String filePath = scan.next();
              while (!model.checkIfValidDirectory(filePath)) {
                view.downloadPortfolio(null);
                filePath = scan.next();
              }
              try {
                view.downloadPortfolio(
                        model.downloadPortfolio(portfolioComposition,
                                filePath + "/portfolio_" + portfolioId + ".json", portfolioId));
              } catch (Exception e) {
                view.display(e.toString());
              }
            }
            view.display("Do you want to download another portfolio (y/n) : ");
            String option = scan.next();
            if (option.equals("n")) {
              switchCases();
            } else if (option.equals("y")) {
              continue;
            }
          }
          return;
        }
      case 5:
        while (true) {
          view.display("Enter portfolio ID: ");
          int portfolioId;
          try {
            portfolioId = Integer.parseInt(scan.next());
          } catch (Exception e) {
            view.display("\033[0;31m" + "Invalid ID provided, please provide a valid ID.");
            continue;
          }
          Map<String, Double> portfolioComposition = model.examinePortfolio(portfolioId);
          int latestPortfolioId = model.getLatestPortfolioId();
          view.examinePortfolio(portfolioComposition, portfolioId, latestPortfolioId, 1);
          if (portfolioComposition != null) {
            view.display("Enter date in the format YYYY-MM-DD:  ");
            String date = scan.next();
            while (!Objects.equals(model.dateValidation(date), "Valid")) {
              view.display(model.dateValidation(date));
              date = scan.next();
            }
            Map<String, Double> portFolioValue = model.getValueOfPortfolio(
                    portfolioId,
                    date);
            while (portFolioValue == null) {
              view.display(
                      "\033[0;31m"
                              + "Provided date is a holiday, please provide "
                              + "a date which is not a holiday: ");
              date = scan.next();
              while (!Objects.equals(model.dateValidation(date), "Valid")) {
                view.display(model.dateValidation(date));
                date = scan.next();
              }
              portFolioValue = model.getValueOfPortfolio(portfolioId, date);
            }
            view.getValueOfPortfolio(portFolioValue, date);
          }
          view.display("Do you want to get value of another portfolio (y/n) : ");
          String option = scan.next();
          if (option.equals("n")) {
            switchCases();
          } else if (option.equals("y")) {
            continue;
          }
          return;
        }
      case 6:
        while (true) {
          int latestPortfolioId = model.getLatestPortfolioId();
          int portfolioId = portfolioIdHelper(model, view);
          Map<String, Double> portfolioComposition = model.examinePortfolio(portfolioId);
          view.examinePortfolio(portfolioComposition, portfolioId, latestPortfolioId, 1);
          if (portfolioComposition != null) {
            while (!Objects.equals(model.getTypeOfPortfolio(portfolioId), "Flexible")) {
              view.display("\033[0;31mPlease enter a flexible portfolio ID! Inflexible "
                      + "portfolios cannot be modified.");
              portfolioId = scan.nextInt();
            }
            Map<String, Map<String, String>> stockList = buyNStocksHelper(1, model, view,
                    1);

            for (Map.Entry<String, Map<String, String>> set : stockList.entrySet()) {
              for (Map.Entry<String, String> dateSet : set.getValue().entrySet()) {
                String valueOf = dateSet.getValue();
                String[] valueArray = valueOf.split(",");

                String date = dateSet.getKey();
                Double quantity = Double.parseDouble(valueArray[0]);
                String stock = set.getKey();
                String result = model.buyStockOnDate(portfolioId, stock, quantity.longValue(), date,
                        Float.parseFloat(valueArray[1]));

                view.display("\033[1;32m" + result);
              }
            }

          }
          view.display("Do you want purchase another stock (y/n) : ");
          String option = scan.next();
          if (option.equals("n")) {
            switchCases();
          } else if (option.equals("y")) {
            continue;
          }
          return;
        }
      case 7:
        while (true) {
          int portfolioId = portfolioIdHelper(model, view);
          int latestPortfolioId = model.getLatestPortfolioId();
          Map<String, Double> portfolioComposition = model.examinePortfolio(portfolioId);
          view.examinePortfolio(portfolioComposition, portfolioId, latestPortfolioId, 1);
          int numStocks = 1;
          if (portfolioComposition != null) {
            while (!Objects.equals(model.getTypeOfPortfolio(portfolioId), "Flexible")) {
              view.display("\033[0;31mPlease enter a flexible portfolio ID! Inflexible "
                      + "portfolios cannot be modified.");
              portfolioId = scan.nextInt();
            }

            while (numStocks > 0) {
              view.display("Enter the stock name you wish to sell:");
              String stockName = scan.next();
              if (model.hasStock(portfolioId, stockName.toUpperCase())) {
                view.createMenuStockNumber(stockName.toUpperCase());
                Long stockNumber;
                while (true) {
                  try {
                    stockNumber = Long.parseLong(scan.next());
                    if (stockNumber == 0) {
                      view.display("Zero Stocks not allowed, "
                              + "Please enter stock greater than zero:");
                      continue;
                    } else if (stockNumber < 0) {
                      view.display(
                              "Negative Stocks not valid, "
                                      + "please provide stock greater than zero:");
                      continue;
                    } else if (!model.hasStockQuantity(portfolioId, stockName.toUpperCase(),
                            stockNumber)) {
                      view.display(
                              "You don't have sufficient stocks to sell");
                    } else {
                      break;
                    }
                  } catch (Exception e) {
                    view.display("Invalid number of stocks provided, "
                            + "please provide a whole number:");
                  }
                }
                String date;
                view.display("Enter date on which you wish to sell the stock"
                        + ", in the format YYYY-MM-DD:  ");
                String latest_date = model.getLatestBuyDate(portfolioId, stockName.toUpperCase());
                while (true) {
                  date = dateValidationHelper(model, view);
                  if (dateCompare(date, latest_date) < 0) {
                    view.display("You cannot sell a stock before the last buy i.e "
                            + latest_date + ":");
                    continue;
                  } else {
                    break;
                  }
                }
                float commisionValue = getCommissionValue(view);
                String result = model.sellStockOnDate(portfolioId, stockName.toUpperCase(),
                        stockNumber, date, commisionValue);
                view.display("\033[1;32m" + result);
                numStocks--;
              } else {
                view.display(
                        "\033[0;31m"
                                + "Provided stock doesn't exist in the portfolio.");
              }
            }

          }

          view.display("Do you want to sell another stock (y/n) : ");
          String option = scan.next();
          if (option.equals("n")) {
            switchCases();
          } else if (option.equals("y")) {
            continue;
          }
          return;
        }
      case 8:
        while (true) {
          int portfolioId = portfolioIdHelper(model, view);
          int latestPortfolioId = model.getLatestPortfolioId();
          Map<String, Double> portfolioComposition = model.examinePortfolio(portfolioId);
          view.examinePortfolio(portfolioComposition, portfolioId, latestPortfolioId, 1);
          int numStocks = 1;
          if (portfolioComposition != null) {
            while (!Objects.equals(model.getTypeOfPortfolio(portfolioId), "Flexible")) {
              view.display("\033[0;31mPlease enter a flexible portfolio ID! Inflexible "
                      + "portfolios cannot be modified.");
              portfolioId = scan.nextInt();
            }

            view.display("Enter date in the format YYYY-MM-DD:  ");
            String date = scan.next();
            Validations dateObject = new Validations();
            while (!dateObject.validateDateFormat(date)) {
              view.display("Invalid date format, Please provide in correct format!");
              date = scan.next();
            }
            Double costBasis = model.getCostBasisPortfolio(portfolioId, date);
            DecimalFormat df = new DecimalFormat("0.00");
            view.display("\033[1;32m" + "Total cost basis of portfolio " + portfolioId
                    + " for the date " + date + ": $" + df.format(costBasis));
          }

          view.display("Do you want to check cost basis of a different portfolio (y/n) : ");
          String option = scan.next();
          if (option.equals("n")) {
            switchCases();
          } else if (option.equals("y")) {
            continue;
          }
          return;
        }

      case 9:
        while (true) {
          view.display("Enter portfolio ID: ");
          int portfolioId = scan.nextInt();
          Map<String, Double> portfolioComposition = model.examinePortfolio(portfolioId);
          int latestPortfolioId = model.getLatestPortfolioId();
          view.examinePortfolio(portfolioComposition, portfolioId, latestPortfolioId, 1);
          if (portfolioComposition != null) {
            while (!Objects.equals(model.getTypeOfPortfolio(portfolioId), "Flexible")) {
              view.display("\033[0;31mPlease enter a flexible portfolio ID! Inflexible "
                      + "portfolios cannot be looked up.");
              portfolioId = scan.nextInt();
            }

            view.display("Enter date from when you want to see the performance of "
                    + "your portfolio in the format YYYY-MM-DD:  ");
            String fromDate = scan.next();
            Validations dateObject = new Validations();
            while (!Objects.equals(model.dateValidation(fromDate), "Valid")) {
              view.display(model.dateValidation(fromDate));
              fromDate = scan.next();
            }
            view.display("Enter date upto when you want to see the performance of "
                    + "your portfolio in the format YYYY-MM-DD:  ");
            String toDate = scan.next();
            long difference = dateObject.checkIfToDateIsMoreThanFromDate(fromDate, toDate);
            while (!Objects.equals(model.dateValidation(toDate), "Valid") || difference < 5) {
              if (difference < 5) {
                view.display("Provided TO date should be latest more than 4 days after "
                        + "the provided FROM date, please enter the correct TO date: ");
              } else {
                view.display(model.dateValidation(toDate));
              }
              toDate = scan.next();
              difference = dateObject.checkIfToDateIsMoreThanFromDate(fromDate, toDate);
            }
            view.display("Getting Data, Please Wait.........");
            view.portfolioAtAGlance(
                    model.portfolioAtAGlance(portfolioId, fromDate, toDate, difference));
          }
          view.display("Do you want to get value of another portfolio (y/n) : ");
          String option = scan.next();
          if (option.equals("n")) {
            switchCases();
          } else if (option.equals("y")) {
            continue;
          }
          return;
        }
      case 10:
        while (true) {
          int latestPortfolioId = model.getLatestPortfolioId();
          int portfolioId;
          if (latestPortfolioId == 0) {
            view.display(
                    "\033[0;31m"
                            + "No portfolio is created yet! Please create or upload"
                            + " a portfolio before you look to download one!!");
            view.display("");
            switchCases();
          } else {
            view.display("Enter portfolio ID to be rebalanced");
            try {
              while (true) {
                portfolioId = Integer.parseInt(scan.next());
                if (!model.listOfFlexiblePortfolios().contains(String.valueOf(portfolioId))) {
                  view.display("\033[0;31m" + "Invalid ID provided, "
                          + "please provide a valid ID.");
                  continue;
                }
                break;
              }
            } catch (Exception e) {
              view.display("\033[0;31m" + "Invalid ID provided, please provide a valid ID.");
              continue;
            }

            view.display("Enter the date for rebalancing : ");
            String date = scan.next();
            Validations dateObject = new Validations();
            while (!Objects.equals(model.dateValidation(date), "Valid")) {
              view.display(model.dateValidation(date));
              date = scan.next();
            }

            String commissionForTrade = "0";

            view.display("Enter the number of stocks to be rebalanced");
            int numStocks;
            while (true) {
              try {
                numStocks = Integer.parseInt(scan.next());
                if (numStocks <= 0) {
                  view.display("Enter a number greater than zero");
                  continue;
                }
                break;


              } catch (Exception e) {
                view.display("\033[0;31m" + "Invalid Input provided, "
                        + "please provide a valid number");
              }
              continue;
            }

            Map<String, Double> weightData = new HashMap<>();

            while (true) {
              for (int i = 0; i < numStocks; i++) {
                view.display("Enter the ticker symbol of the stock ");
                String a = scan.next();
                String a1 = a.toUpperCase();
                view.display("Enter the new weight of the stock ");
                Double w = Double.valueOf(scan.next());
                weightData.put(a1, w);
              }
              Double sum = 0.0;
              for (Double f : weightData.values()) {
                sum += f;
              }
              if (sum != 100.0) {
                view.display("The weights should add up to 100 percent Please try again");
                weightData.clear();
                continue;
              }


              break;
            }


            String reabalanced = model.rebalancePort(portfolioId, date, weightData,
                    commissionForTrade);
            view.display(reabalanced);


            view.display("Do you want to rebalance another portfolio (y/n) : ");
            String option = scan.next();
            if (option.equals("n")) {
              switchCases();
            } else if (option.equals("y")) {
              continue;
            }
          }

          return;
        }


      case 11:
        break;
      default:
        view.display("\033[0;31m" + "Please provide a valid option i.e a "
                + "number between 1 and 10");
        switchCases();
        return;


    }


  }

  /**
   * Helper method to decide between flexible and inflexbible portfolios.
   *
   * @param view view object of the MVC architecture.
   * @return a integer of user inputted.
   */
  private int subMenuHelper(StockView view) {
    int subMenuValue;
    while (true) {
      try {
        subMenuValue = Integer.parseInt(scan.next());
        if (subMenuValue != 1 && subMenuValue != 2) {
          view.display("Invalid input provided, please provide either option 1 or 2");
          view.showSubMenu();
        } else {
          break;
        }
      } catch (Exception e) {
        view.display("Invalid input provided, please provide either option 1 or 2:");
        view.showSubMenu();
      }

    }
    return subMenuValue;
  }

  /**
   * Helper method to take and validate date if it is of the right format.
   *
   * @param model model object of the MVC architecture.
   * @param view  view object of the MVC architecture.
   * @return parsed date
   * @throws ParseException if any parse error.
   */
  private String dateValidationHelper(StockModel model, StockView view) throws ParseException {
    String date = scan.next();
    while (!Objects.equals(model.dateValidation(date), "Valid")) {
      view.display(model.dateValidation(date));
      date = scan.next();
    }
    return date;
  }

  /**
   * compares two dates and returns -1,0,1 accordingly.
   *
   * @param date1 first date to be compared.
   * @param date2 first date to be compared.
   * @return comparision values -1,0,1
   * @throws ParseException if any parse error.
   */
  static int dateCompare(String date1, String date2) throws ParseException {
    Date start = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
    Date end = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
    return start.compareTo(end);
  }

  /**
   * Helper method to ask user for a commission value for a trade buy/sell.
   *
   * @param view view object of the MVC architecture.
   * @return a commission value.
   */
  private float getCommissionValue(StockView view) {
    view.display("Please enter the commission fee paid while trading this stock: ");
    float commissionValue;
    while (true) {
      try {
        commissionValue = Float.parseFloat(scan.next());
        if (commissionValue < 0) {
          view.display("\033[0;31m" + "Invalid commission provided, "
                  + "please provide value greater than or equal to zero: ");
        } else {
          break;
        }
      } catch (NumberFormatException e) {
        view.display("\033[0;31m" + "Invalid commission provided, "
                + "please provide a valid value: ");
      }
    }
    return commissionValue;
  }

  /**
   * Helper method to buy N number of stocks from the user.
   *
   * @param numStocks    total N trades to be executed.
   * @param model        model object of the MVC architecture.
   * @param view         view object of the MVC architecture.
   * @param subMenuValue determine if flexible or inflexible portfolio.
   * @return a map of values of the format {AA={126,67}}
   * @throws IOException          a I/O error.
   * @throws URISyntaxException   if string is not parsed as URI reference.
   * @throws InterruptedException if the thread is interrupted.
   * @throws ParseException       if a parse error.
   */
  private Map<String, Map<String, String>> buyNStocksHelper(int numStocks, StockModel model,
                                                            StockView view, int subMenuValue)
          throws IOException, URISyntaxException, InterruptedException, ParseException {
    int i = numStocks;
    Map<String, Map<String, String>> stockList = new HashMap<>();
    while (numStocks > 0) {
      view.createMenuStockName(i - numStocks + 1);
      String stockName = scan.next();
      if (model.ifStockExists(stockName.toUpperCase(), this.validStocksMap)) {
        view.createMenuStockNumber(stockName.toUpperCase());
        int stockNumber;
        while (true) {
          try {
            stockNumber = Integer.parseInt(scan.next());
            if (stockNumber == 0) {
              view.display("Zero Stocks not allowed, Please enter stock greater than zero:");
              continue;
            } else if (stockNumber < 0) {
              view.display(
                      "Negative Stocks not valid, please provide stock greater than zero:");
              continue;
            } else {
              break;
            }
          } catch (Exception e) {
            view.display("Invalid number of stocks provided, please provide a whole number:");
          }
        }
        String date;
        if (subMenuValue == 1) {
          view.display("Enter date on which the stocks were purchased, "
                  + "in the format YYYY-MM-DD:  ");
          date = dateValidationHelper(model, view);
        } else {
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          LocalDateTime now = LocalDateTime.now();
          date = dtf.format(now);
        }
        float commissionForTrade = getCommissionValue(view);
        Double stockQuantity = (double) stockNumber;
        createHashMapForStocksProvided(stockList, stockName, stockQuantity, commissionForTrade,
                date);
        numStocks--;
      } else {
        view.display(
                "\033[0;31m"
                        + "Invalid Stock ID provided, please provide "
                        + "a valid Stock ID.");
      }
    }
    return stockList;
  }

  /**
   * Helper method to check if an entered portfolio id exists in the storage.
   *
   * @param model model object of the MVC architecture.
   * @param view  view object of the MVC architecture.
   * @return int of portfolio id
   * @throws IOException                           a I/O error.
   * @throws URISyntaxException                    if string is not parsed as URI reference.
   * @throws ParseException                        if parse error.
   * @throws org.json.simple.parser.ParseException if json parse error.
   * @throws InterruptedException                  if the thread is interrupted.
   */
  private int portfolioIdHelper(StockModel model, StockView view)
          throws IOException, URISyntaxException, ParseException,
          org.json.simple.parser.ParseException, InterruptedException {
    view.display("Enter portfolio ID: ");
    int latestPortfolioId = model.getLatestPortfolioId();
    boolean hasFlexiblePortfolio = model.hasFlexiblePortfolio();
    if (latestPortfolioId == 0) {
      view.display(
              "\033[0;31m"
                      + "No portfolio is created yet! Please create or "
                      + "upload a portfolio before you can try purchasing!!");
      view.display("");
      switchCases();
    } else if (!hasFlexiblePortfolio) {
      view.display(
              "\033[0;31m"
                      + "There are NO flexible portfolios existing! Please "
                      + "create or upload a flexible portfolio");
      view.display("");
      switchCases();
    }
    int portfolioId = 0;
    while (true) {
      try {
        portfolioId = Integer.parseInt(scan.next());
        break;
      } catch (Exception e) {
        view.display(
                "\033[0;31m"
                        + "Invalid ID provided, please provide a valid portfolio ID ");
      }
    }

    return portfolioId;
  }


  /**
   * Method to create a map for the given stocks.
   *
   * @param stockMap           map of stocks.
   * @param stockName          name of the stock.
   * @param stockNumber        number of stocks to be added.
   * @param commissionForTrade commission fee paid.
   * @param date               date of creation.
   */

  private void createHashMapForStocksProvided(Map<String, Map<String, String>> stockMap,
                                              String stockName, Double stockNumber,
                                              Float commissionForTrade, String date) {
    createHashMapForStocks(stockMap, stockName, stockNumber, commissionForTrade, date);
  }

  /**
   * Helper method for creating the map.
   *
   * @param stockMap           map of stocks.
   * @param stockName          name of the stock.
   * @param stockNumber        number of stocks to be added.
   * @param commissionForTrade commission fee paid.
   * @param date               date of creation.
   */

  static void createHashMapForStocks(Map<String, Map<String, String>> stockMap, String stockName,
                                     Double stockNumber, Float commissionForTrade, String date) {
    if (stockMap.containsKey(stockName.toUpperCase())) {
      Map<String, String> map;
      map = stockMap.get(stockName.toUpperCase());
      Model.createMapForStockList(stockMap, date, stockName, stockNumber,
              commissionForTrade, map);
    } else {
      HashMap<String, String> map = new HashMap<>();
      map.put(date, stockNumber + "," + commissionForTrade);
      stockMap.put(stockName.toUpperCase(), map);
    }
  }

}
