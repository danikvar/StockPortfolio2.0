package controllers;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Properties;
import java.util.Scanner;
import models.StockModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the controller class to check all expected cases my mocking the user actions.
 */
public class ControllerTest {


  String testConfigPath;
  StockModel model;

  /**
   * Helper method to delete the file each time a test runs to create fresh scenarios each time.
   */
  private void deleteFile() throws IOException {
    File myObj = new File("all_portfolios_test.json");
    try {
      myObj.delete();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Before
  public void deleteTestFile() throws IOException {
    deleteFile();
  }

  @After
  public void deleteTestFile1() throws IOException, InterruptedException {
    deleteFile();
    sleep(200);
  }

  @Before
  public void initializeTestConfigPath()
      throws IOException, URISyntaxException, InterruptedException {
    InputStream is = getClass().getResourceAsStream("/config/config.properties");
    Properties prop = new Properties();
    prop.load(is);
    testConfigPath = prop.getProperty("ALL_PORTFOLIOS_JSON_TEST");

  }

  @Test
  public void testCreateFlexiblePortfolioWithOneStockCheckZeroAndNegativeCase()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 1 A 1.5 0 -52 100 2021-01-11 50 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Invalid number of stocks provided, please provide a whole number:\n"
        + "\u001B[0mZero Stocks not allowed, Please enter stock greater than zero:\n"
        + "\u001B[0mNegative Stocks not valid, please provide stock greater than zero:\n"
        + "\u001B[0mEnter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testCreateInFlexiblePortfolioWithOneStockCheckZeroAndNegativeCase()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 2 1 A 1.5 0 -52 100 50 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Invalid number of stocks provided, please provide a whole number:\n"
        + "\u001B[0mZero Stocks not allowed, Please enter stock greater than zero:\n"
        + "\u001B[0mNegative Stocks not valid, please provide stock greater than zero:\n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testCreateFlexiblePortfolioWithTwoStocks()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 2 A 1 2021-01-11 50 IBM 25478 2022-11-11 40 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testCreateInFlexiblePortfolioWithTwoStocks()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 2 2 A 1 50 IBM 25478 40 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testCreateFlexiblePortfolioInvalidStockID()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 1 WEQRT IBM 250479 2022-11-11 45 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "\u001B[0;31mInvalid Stock ID provided, please provide a valid Stock ID.\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testCreateInFlexiblePortfolioInvalidStockID()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 2 1 WEQRT IBM 250479 45 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "\u001B[0;31mInvalid Stock ID provided, please provide a valid Stock ID.\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testCreateInvalidStockIDMultipleStocks()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 2 2 WEQRT IBM 250479 50 Testyg A 5789452 40 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "\u001B[0;31mInvalid Stock ID provided, please provide a valid Stock ID.\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "\u001B[0;31mInvalid Stock ID provided, please provide a valid Stock ID.\n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: A\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testCreateInFlexibleSameStockMultipleTimes()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 2 3 IBM 250479 40 IBM 5789452 30 IBM 4578 10 n 3 1 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 3\n"
        + "Enter the number of stocks for: IBM\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tIBM, Number of Stocks Owned: \t6044509.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testCreateFlexibleSameStockMultipleTimes()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 3 IBM 250479 2022-11-11 40 IBM 5789452 2022-11-11 30 "
        + "IBM 4578 2021-05-05 10 n 3 1 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 3\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tIBM, Number of Stocks Owned: \t6044509.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testCreateMultipleInFlexiblePortfoliosInSingleTransactionAndExamine()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 2 3 IBM 250479 50 IBM 5789452 50 IBM 4578 40 y 2 1 NVR "
        + "4567895 50 n 3 1 y 2 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 3\n"
        + "Enter the number of stocks for: IBM\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: NVR\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 2 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tIBM, Number of Stocks Owned: \t6044509.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mEnter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 2\n"
        + "\u001B[1;32mStock ID: \tNVR, Number of Stocks Owned: \t4567895.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testCreateMultipleFlexiblePortfoliosInSingleTransactionAndExamine()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 3 IBM 250479 2022-11-11 50 IBM 5789452 2022-11-11 50 "
        + "IBM 4578 2022-11-11 40 y 1 1 NVR 4567895 2021-05-05 50 n 3 1 y 2 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 3\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: NVR\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 2 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tIBM, Number of Stocks Owned: \t6044509.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mEnter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 2\n"
        + "\u001B[1;32mStock ID: \tNVR, Number of Stocks Owned: \t4567895.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testCreateMultiplePortfoliosInSingleTransactionAndExamine()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 3 IBM 250479 2022-11-11 50 IBM 5789452 2022-11-11 50 IBM "
        + "4578 2022-11-11 40 y 2 1 NVR 4567895 50 n 3 1 y 2 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 3\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: NVR\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 2 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tIBM, Number of Stocks Owned: \t6044509.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mEnter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 2\n"
        + "\u001B[1;32mStock ID: \tNVR, Number of Stocks Owned: \t4567895.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }


  @Test
  public void testExamineInvalidID()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 3 IBM 250479 2022-11-11 50 IBM 5789452 2022-11-11 40 "
        + "IBM 4578 2022-11-11 30 y 2 1 NVR 4567895 20 n 3 1 y 3 y 2 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 3\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: NVR\n"
        + "Please enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 2 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tIBM, Number of Stocks Owned: \t6044509.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mEnter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[0;31mInvalid ID provided, Provide portfolio id between 1 and 2\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mEnter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 2\n"
        + "\u001B[1;32mStock ID: \tNVR, Number of Stocks Owned: \t4567895.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testExamineWhenNoPortfolioIsPresent()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "3 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "\u001B[0;31mNo portfolio is created yet! Please create or upload a portfolio "
        + "before you look for one!!\n"
        + "\u001B[0m\n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }


  @Test
  public void testDownloadWhenNoPortfolioIsPresent()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "4 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "\u001B[0;31mNo portfolio is created yet! Please create or upload a "
        + "portfolio before you look to download one!!\n"
        + "\u001B[0m\n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";

    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testDownloadPortfolioWithInvalidPortfolioID()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 2 A 1 2022-11-11 50 IBM 25478 2022-11-11 50 n 4 10 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this "
        + "ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be downloaded\n"
        + "\u001B[0m\u001B[0;31mInvalid ID provided, there is only one portfolio present "
        + "which has ID : 1 \n"
        + "\u001B[0mDo you want to download another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testDownloadPortfolioWithValidPortfolioID()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 2 A 1 2022-11-11 50 IBM 25478 2022-11-11 50 n 4 1 src/ n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be downloaded\n"
        + "\u001B[0mEnter the directory in which the file has to be saved: \n"
        + "\u001B[0m\u001B[1;32mPortfolio downloaded successfully. You can find the portfolio at: "
        + "src//portfolio_1.json\n"
        + "\u001B[0mDo you want to download another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    String data = null;
    try {
      File myObj = new File("src/portfolio_1.json");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        data = myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    String expected = "{\"type\":\"Flexible\",\"stocks\":[{\"A\":{\"total\":1.0,"
        + "\"strategy_rules\":[],"
        + "\"transactions\":[{\"date\":\"2022-11-11\",\"trade\":\"Buy\",\"quantity\":1.0,"
        + "\"commission\":50.0,\"strategy_type\":\"basic\",\"valueDate\":148.31}]}},"
        + "{\"IBM\":{\"total\":25478.0,\"strategy_rules\":[],"
        + "\"transactions\":[{\"date\":\"2022-11-11\",\"trade\":\"Buy\","
        + "\"quantity\":25478.0,\"commission\":50.0,"
        + "\"strategy_type\":\"basic\",\"valueDate\":3647685.26}]}}]}";
    assertEquals(expected, data);
    deleteFile();
    File myObj = new File("src/portfolio_1.json");
    myObj.delete();

  }

  @Test
  public void testDownloadPortfolioWithInvalidPath()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 2 A 1 2022-11-11 50 IBM 25478 2022-11-11 50 n 4 1 abc/ src/ n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be downloaded\n"
        + "\u001B[0mEnter the directory in which the file has to be saved: \n"
        + "\u001B[0m\u001B[0;31mDirectory not found, please provide a valid directory: \n"
        + "\u001B[0m\u001B[1;32mPortfolio downloaded successfully. You can find the portfolio at: "
        + "src//portfolio_1.json\n"
        + "\u001B[0mDo you want to download another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";

    assertEquals(expectedOutput, result);
    String data = null;
    try {
      File myObj = new File("src/portfolio_1.json");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        data = myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    String expected = "{\"type\":\"Flexible\",\"stocks\":[{\"A\":{\"total\":1.0,"
        + "\"strategy_rules\":[],\"transactions\":[{\"date\":\"2022-11-11\","
        + "\"trade\":\"Buy\",\"quantity\":1.0,"
        + "\"commission\":50.0,\"strategy_type\":\"basic\",\"valueDate\":148.31}]}},"
        + "{\"IBM\":{\"total\":25478.0,\"strategy_rules\":[],"
        + "\"transactions\":[{\"date\":\"2022-11-11\",\"trade\":\"Buy\","
        + "\"quantity\":25478.0,\"commission\":50.0,"
        + "\"strategy_type\":\"basic\",\"valueDate\":3647685.26}]}}]}";
    assertEquals(expected, data);
    deleteFile();

    File myObj = new File("src/portfolio_1.json");
    myObj.delete();
  }


  @Test
  public void testDownloadPortfolioSingleStock()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 1 A 1 2022-11-11 50 n 4 1 src/ n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be downloaded\n"
        + "\u001B[0mEnter the directory in which the file has to be saved: \n"
        + "\u001B[0m\u001B[1;32mPortfolio downloaded successfully. "
        + "You can find the portfolio at: src//portfolio_1.json\n"
        + "\u001B[0mDo you want to download another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    String data = null;
    try {
      File myObj = new File("src/portfolio_1.json");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        data = myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    String expected = "{\"type\":\"Flexible\",\"stocks\":[{\"A\":{\"total\":1.0,"
        + "\"strategy_rules\":[],\"transactions\":[{\"date\":\"2022-11-11\","
        + "\"trade\":\"Buy\",\"quantity\":1.0,\"commission\":50.0,\"strategy_type\":\"basic\","
        + "\"valueDate\":148.31}]}}]}";
    assertEquals(expected, data);
    deleteFile();

    File myObj = new File("src/portfolio_1.json");
    myObj.delete();

  }

  @Test
  public void testDownloadMultiplePortfolios()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "1 1 1 A 1 2022-11-11 50 y 1 1 IBM 25478 2022-11-11 50 n 4 1 "
        + "abc/ src/ y 2 src/ n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 2 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be downloaded\n"
        + "\u001B[0mEnter the directory in which the file has to be saved: \n"
        + "\u001B[0m\u001B[0;31mDirectory not found, please provide a valid directory: \n"
        + "\u001B[0m\u001B[1;32mPortfolio downloaded successfully. "
        + "You can find the portfolio at: src//portfolio_1.json\n"
        + "\u001B[0mDo you want to download another portfolio (y/n) : \n"
        + "\u001B[0mEnter portfolio ID to be downloaded\n"
        + "\u001B[0mEnter the directory in which the file has to be saved: \n"
        + "\u001B[0m\u001B[1;32mPortfolio downloaded successfully. "
        + "You can find the portfolio at: src//portfolio_2.json\n"
        + "\u001B[0mDo you want to download another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    String data = null;
    String data_2 = null;
    try {
      File myObj = new File("src/portfolio_1.json");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        data = myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    try {
      File myObj = new File("src/portfolio_2.json");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        data_2 = myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    String expected = "{\"type\":\"Flexible\",\"stocks\":[{\"A\":{\"total\":1.0,"
        + "\"strategy_rules\":[],\"transactions\":[{\"date\":\"2022-11-11\","
        + "\"trade\":\"Buy\",\"quantity\":1.0,\"commission\":50.0,"
        + "\"strategy_type\":\"basic\",\"valueDate\":148.31}]}}]}";
    String expected_2 = "{\"type\":\"Flexible\",\"stocks\":[{\"IBM\":{\"total\":25478.0,"
        + "\"strategy_rules\":[],"
        + "\"transactions\":[{\"date\":\"2022-11-11\",\"trade\":\"Buy\","
        + "\"quantity\":25478.0,\"commission\":50.0,\"strategy_type\":\"basic\","
        + "\"valueDate\":3647685.26}]}}]}";
    assertEquals(expected_2, data_2);
    assertEquals(expected, data);
    deleteFile();

    File myObj = new File("src/portfolio_1.json");
    myObj.delete();
    myObj = new File("src/portfolio_2.json");
    myObj.delete();


  }

  @Test
  public void testSequenceOfOperations()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString =
        "1 1 1 A 254 2021-01-11 50 n 3 1 n 5 1 2022-11-11 n 6 1 GOOG 30 2021-01-12 50 n"
            + " 7 1 GOOG 10 2021-01-13 20 n 8 1 2022-11-11 n 9 1 2020-01-01 2022-11-11 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t254.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[1;32mValue of your stocks on 2022-11-11 are\n"
        + "\u001B[1;32mA ----> $37670.74\n"
        + "\u001B[1;32mTotal ----> $37670.74\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: GOOG\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32mStock: GOOG purchased successfully\n"
        + "\u001B[0mDo you want purchase another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter the stock name you wish to sell:\n"
        + "\u001B[0mEnter the number of stocks for: GOOG\n"
        + "Enter date on which you wish to sell the stock, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mStock: GOOG sold successfully in the portfolio "
        + "with id 1\n"
        + "\u001B[0mDo you want to sell another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[1;32mTotal cost basis of portfolio 1 for the date 2022-11-11: "
        + "$102610.60\n"
        + "\u001B[0mDo you want to check cost basis of a different portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date from when you want to see the performance of your portfolio "
        + "in the format YYYY-MM-DD:  \n"
        + "\u001B[0mEnter date upto when you want to see the performance of your portfolio "
        + "in the format YYYY-MM-DD:  \n"
        + "\u001B[0mGetting Data, Please Wait.........\n"
        + "\u001B[0m\u001B[1;32mJan 2020 : NA\n"
        + "\u001B[1;32mMar 2020 : NA\n"
        + "\u001B[1;32mMay 2020 : NA\n"
        + "\u001B[1;32mJul 2020 : NA\n"
        + "\u001B[1;32mSep 2020 : NA\n"
        + "\u001B[1;32mNov 2020 : NA\n"
        + "\u001B[1;32mJan 2021 : **\n"
        + "\u001B[1;32mMar 2021 : ***\n"
        + "\u001B[1;32mMay 2021 : ***\n"
        + "\u001B[1;32mJul 2021 : ****\n"
        + "\u001B[1;32mSep 2021 : ****\n"
        + "\u001B[1;32mNov 2021 : ****\n"
        + "\u001B[1;32mJan 2022 : ****\n"
        + "\u001B[1;32mMar 2022 : ****\n"
        + "\u001B[1;32mMay 2022 : ***\n"
        + "\u001B[1;32mJul 2022 : *\n"
        + "\u001B[1;32mSep 2022 : *\n"
        + "\u001B[1;32mNov 2022 : *\n"
        + "\u001B[1;32mScale : * = $34719.70\n"
        + "\u001B[1;32mScale Type : Absolute\n"
        + "\u001B[1;32mNA : You have not created a portfolio for that particular date.\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }


  @Test
  public void testOneFileUploadValidMultipleStocks()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json n 10";

    FileWriter file = new FileWriter("test_dummy.json");
    try (PrintWriter out = new PrintWriter(file)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testOneFileUploadValidOneStocks()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json n 10";

    FileWriter file = new FileWriter("test_dummy.json");
    try (PrintWriter out = new PrintWriter(file)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "  ]\n"
          + "}");
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testOneFileUploadInValidThenValid()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy_invalid.json test_dummy.json n 10";

    FileWriter file = new FileWriter("test_dummy_invalid.json");
    try (PrintWriter out = new PrintWriter(file)) {
      out.write("{\n"
          + "  \"stoc\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100,\n"
          + "      \"commission\": 500.65 \n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    FileWriter file1 = new FileWriter("test_dummy.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[0;31mInvalid JSON file provided, please provide a valid file: \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy_invalid.json");
    myObj.delete();
    myObj = new File("test_dummy_valid.json");
    myObj.delete();
    myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testTwoFilesUploadBothValid()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy_1.json y 1 test_dummy_2.json n 10";

    FileWriter file1 = new FileWriter("test_dummy_1.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    file1 = new FileWriter("test_dummy_2.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"IBM\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 2 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy_1.json");
    myObj.delete();
    myObj = new File("test_dummy_2.json");
    myObj.delete();
  }

  @Test
  public void testOneFileUploadInValidThenValidWithExtension()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy_invalid.txt test_dummy_valid.json n 10";

    try (PrintWriter out = new PrintWriter(new FileWriter("test_dummy_invalid.txt"))) {
      out.write("{\n"
          + "  \"stoc\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
    } catch (Exception e) {
      e.printStackTrace();
    }

    FileWriter file1 = new FileWriter("test_dummy_valid.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[0;31mInvalid JSON file provided, please provide a valid file: \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy_invalid.txt");
    myObj.delete();
    myObj = new File("test_dummy_valid.json");
    myObj.delete();
  }


  @Test
  public void testOneFileUploadValidOneStockInvalidName()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json test_dummy_1.json n 10";

    FileWriter file1 = new FileWriter("test_dummy.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"ksdhskd\",\n"
          + "      \"quantity\": 100,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    file1 = new FileWriter("test_dummy_1.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[0;31m\u001B[0;31mksdhskd is not a valid Stock ID, "
        + "please update the file and upload it again, please update the file and reupload it: \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
    myObj = new File("test_dummy_1.json");
    myObj.delete();
  }

  @Test
  public void testOneFileUploadSameValidStockMultipleTimes()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json n 3 1 n 10";

    FileWriter file1 = new FileWriter("test_dummy.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"IBM\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"IBM\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-11-11\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tIBM, Number of Stocks Owned: \t600.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testOneFileUploadMultipleFilesOneAfterAnother()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy_1.json y 1 test_dummy_1.json n 10";

    FileWriter file1 = new FileWriter("test_dummy_1.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"IBM\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"AA\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-11-11\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    file1 = new FileWriter("test_dummy_2.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"IBM\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"IBM\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-11-11\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + ""
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 2 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy_1.json");
    myObj.delete();
    myObj = new File("test_dummy_2.json");
    myObj.delete();
  }

  @Test
  public void testOneFileUploadMultipleFilesOneAfterAnotherInFlexible()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 2 test_dummy_1.json y 1 test_dummy_2.json n 10";

    FileWriter file1 = new FileWriter("test_dummy_1.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"IBM\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"AA\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    file1 = new FileWriter("test_dummy_2.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"IBM\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"IBM\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-11-11\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 2 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy_1.json");
    myObj.delete();
    myObj = new File("test_dummy_2.json");
    myObj.delete();
  }

  @Test
  public void testOneFileUploadMultipleFilesOneAfterAnotherInFlexibleInvalid()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 2 test_dummy_2.json test_dummy_1.json y 1 test_dummy_1.json "
        + "test_dummy_2.json n 10";

    FileWriter file1 = new FileWriter("test_dummy_1.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"IBM\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"AA\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    file1 = new FileWriter("test_dummy_2.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"IBM\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"IBM\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-11-11\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[0;31mInvalid JSON file provided, please provide a valid file: \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[0;31mInvalid JSON file provided, please provide a valid file: \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 2 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy_1.json");
    myObj.delete();
    myObj = new File("test_dummy_2.json");
    myObj.delete();
  }

  // GET COMPOSITION TESTS
  @Test
  public void testGetCompositionInvalidPortfolioIdInitial()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "5 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0m\u001B[0;31mNo portfolio is created yet! "
        + "Please create or upload a portfolio before you look for one!! \n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testGetCompositionInvalidPortfolioIdAfterCertainCreates()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json n 5 5 n 10";

    try (PrintWriter out = new PrintWriter(new FileWriter("test_dummy.json"))) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0m\u001B[0;31mInvalid ID provided, "
        + "there is only one portfolio present which has ID : 1 \n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testGetCompositionInvalidDate()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json n 5 1 30-01-2022 2022-05-05 n 10";
    FileWriter file = new FileWriter("test_dummy.json");
    try (PrintWriter out = new PrintWriter(file)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mInvalid Date Format provided, please provide a "
        + "date in yyyy-MM-dd format\n"
        + "\u001B[0m\u001B[1;32mValue of your stocks on 2022-05-05 are\n"
        + "\u001B[1;32mAA ----> $6346.00\n"
        + "\u001B[1;32mGOOG ----> $1400958.00\n"
        + "\u001B[1;32mTotal ----> $1407304.00\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testGetCompositionInvalidPastDate()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json n 5 1 1992-01-03 2022-05-05 n 10";

    try (PrintWriter out = new PrintWriter(new FileWriter("test_dummy.json"))) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mProvided date is more than twenty years old, "
        + "please provide a date which is in the past twenty years\n"
        + "\u001B[0m\u001B[1;32mValue of your stocks on 2022-05-05 are\n"
        + "\u001B[1;32mAA ----> $6346.00\n"
        + "\u001B[1;32mGOOG ----> $1400958.00\n"
        + "\u001B[1;32mTotal ----> $1407304.00\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testGetCompositionValidPastDate()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json n 5 1 2022-05-05 n 10";

    FileWriter file = new FileWriter("test_dummy.json");
    try (PrintWriter out = new PrintWriter(file)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[1;32mValue of your stocks on 2022-05-05 are\n"
        + "\u001B[1;32mAA ----> $6346.00\n"
        + "\u001B[1;32mGOOG ----> $1400958.00\n"
        + "\u001B[1;32mTotal ----> $1407304.00\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testGetCompositionInFlexibleValidPastDate()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 2 test_dummy.json n 5 1 2022-05-05 n 10";

    FileWriter file = new FileWriter("test_dummy.json");
    try (PrintWriter out = new PrintWriter(file)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[1;32mValue of your stocks on 2022-05-05 are\n"
        + "\u001B[1;32mAA ----> $6346.00\n"
        + "\u001B[1;32mGOOG ----> $1400958.00\n"
        + "\u001B[1;32mTotal ----> $1407304.00\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testGetCompositionInvalidFutureDate()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json n 5 1 2050-01-01 2022-05-05 n 10";

    try (PrintWriter out = new PrintWriter(new FileWriter("test_dummy.json"))) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mProvided date is in future, "
        + "please provide current date or past date\n"
        + "\u001B[0m\u001B[1;32mValue of your stocks on 2022-05-05 are\n"
        + "\u001B[1;32mAA ----> $6346.00\n"
        + "\u001B[1;32mGOOG ----> $1400958.00\n"
        + "\u001B[1;32mTotal ----> $1407304.00\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testGetCompositionInvalidWeekendDate()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json n 5 1 2022-10-29 2022-05-05 n 10";

    FileWriter file = new FileWriter("test_dummy.json");
    try (PrintWriter out = new PrintWriter(file)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mProvided date falls on a weekend, "
        + "please provide a date which is a weekday\n"
        + "\u001B[0m\u001B[1;32mValue of your stocks on 2022-05-05 are\n"
        + "\u001B[1;32mAA ----> $6346.00\n"
        + "\u001B[1;32mGOOG ----> $1400958.00\n"
        + "\u001B[1;32mTotal ----> $1407304.00\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testGetCompositionMultipleStock()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy.json n 5 1 2022-05-05 n 10";

    FileWriter file = new FileWriter("test_dummy.json");
    try (PrintWriter out = new PrintWriter(file)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[1;32mValue of your stocks on 2022-05-05 are\n"
        + "\u001B[1;32mAA ----> $6346.00\n"
        + "\u001B[1;32mGOOG ----> $1400958.00\n"
        + "\u001B[1;32mTotal ----> $1407304.00\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy.json");
    myObj.delete();
  }

  @Test
  public void testGetCompositionOneAfterAnother()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "2 1 test_dummy_1.json y 1 test_dummy_2.json n 5 "
        + "1 2022-01-03 y 2 2022-11-11 n 10";

    FileWriter file = new FileWriter("test_dummy_1.json");
    try (PrintWriter out = new PrintWriter(file)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"AA\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-02-01\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    FileWriter file1 = new FileWriter("test_dummy_2.json");
    try (PrintWriter out = new PrintWriter(file1)) {
      out.write("{\n"
          + "  \"stocks\": [\n"
          + "    {\n"
          + "      \"name\": \"IBM\",\n"
          + "      \"quantity\": 100.0,\n"
          + "      \"commission\": 500.65,\n"
          + "      \"date\": \"2022-11-11\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"name\":\"GOOG\",\n"
          + "      \"quantity\": 600.0,\n"
          + "      \"commission\": 200.65,\n"
          + "      \"date\": \"2022-11-11\"\n"
          + "    }\n"
          + "\n"
          + "  ]\n"
          + "}");
      file1.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Provide the relative path of the json file that has to be uploaded:  \n"
        + "\u001B[0m\u001B[1;32mPortfolio created with id: 2 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want to upload another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[1;32mValue of your stocks on 2022-01-03 are\n"
        + "\u001B[1;32mAA ----> $0.00\n"
        + "\u001B[1;32mGOOG ----> $0.00\n"
        + "\u001B[1;32mTotal ----> $0.00\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mEnter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[1;32mValue of your stocks on 2022-11-11 are\n"
        + "\u001B[1;32mGOOG ----> $58038.00\n"
        + "\u001B[1;32mIBM ----> $14317.00\n"
        + "\u001B[1;32mTotal ----> $72355.00\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

    File myObj = new File("test_dummy_1.json");
    myObj.delete();
    myObj = new File("test_dummy_2.json");
    myObj.delete();
  }

  @Test
  public void testExitProgram()
      throws URISyntaxException, IOException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException {
    String inputString = "10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, log);
    controller.goController();
    String x = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, x);

  }

  @Test
  public void testControllerUsingMock()
      throws URISyntaxException, IOException, org.json.simple.parser.ParseException,
      InterruptedException, ParseException {
    String inputString =
        "1 1 1 A 254 2022-01-11 50 n 3 1 n 5 1 2022-11-11 n 6 1 GOOG 30 2021-01-12 50 n"
            + " 7 1 GOOG 10 2021-01-13 20 n 8 1 2022-11-11 n 9 1 2020-01-01 2022-11-11 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("test"), log);
    controller.goController();
    String x = bytes.toString();
    String expectedResult = "Received :{A={2022-01-11=254.0,50.0}} Type: Flexible "
        + "Received :1 Received :1 Received :1 Date : 2022-11-11 Received :1 "
        + "Received :1 Date : 2021-01-12 Commission :50.0 "
        + "Stock:GOOG Quantity:30 Received :1 Received :1 "
        + "Date : 2021-01-13 Commission :20.0 Stock:GOOG Quantity:10 Received :1 "
        + "Received :1 Date : 2022-11-11 Received :1 "
        + "Received :1 From : 2020-01-01 To :2022-11-11 Difference:1045";
    assertEquals(expectedResult, log.toString());
  }

  @Test
  public void testPurchaseInvalidPortfolio()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 6 10 n 3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0m\u001B[0;31mInvalid ID provided, there is only one portfolio present which has"
        + " ID : 1 \n"
        + "\u001B[0mDo you want purchase another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t100.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testPurchaseInvalidDate()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 6 1 A 100 2022-222-2 2022-01-03 "
        + "60 n 3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mInvalid Date Format provided, please provide a date in"
        + " yyyy-MM-dd format\n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32mStock: A purchased successfully\n"
        + "\u001B[0mDo you want purchase another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t200.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testPurchaseValidPastDate()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 6 1 A 100 2022-01-03 60 n 3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID "
        + "for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32mStock: A purchased successfully\n"
        + "\u001B[0mDo you want purchase another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t200.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testPurchaseValidFutureDate()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 6 1 A 100 2022-01-12 60 n 3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID "
        + "for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32mStock: A purchased successfully\n"
        + "\u001B[0mDo you want purchase another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t200.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testPurchaseWeekendDate()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 6 1 A 100 2021-01-10 2021-01-12 60 n 3 "
        + "1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID "
        + "for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mProvided date falls on a weekend, please provide a date which is "
        + "a weekday\n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32mStock: A purchased successfully\n"
        + "\u001B[0mDo you want purchase another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t200.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testPurchaseNewStock()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 6 1 IBM 100 2021-01-10 2021-01-12 60 n "
        + "3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mProvided date falls on a weekend, please provide a date which is a "
        + "weekday\n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32mStock: IBM purchased successfully\n"
        + "\u001B[0mDo you want purchase another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t100.0\n"
        + "\u001B[1;32mStock ID: \tIBM, Number of Stocks Owned: \t100.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  // SELL TESTS
  @Test
  public void testSellInvalidPortfolio()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 7 10 n 3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0m\u001B[0;31mInvalid ID provided, there is only one "
        + "portfolio present which has ID : 1 \n"
        + "\u001B[0mDo you want to sell another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t100.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testSellInvalidDateThenFuture()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 7 1 A 50 2022-222-2 2022-01-12 60 n 3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter the stock name you wish to sell:\n"
        + "\u001B[0mEnter the number of stocks for: A\n"
        + "Enter date on which you wish to sell the stock, in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mInvalid Date Format provided, please provide a date in "
        + "yyyy-MM-dd format\n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mStock: A sold successfully in the portfolio with id 1\n"
        + "\u001B[0mDo you want to sell another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t50.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testCannotSellInPast()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-12 50 n 7 1 A 50 2021-01-11 2021-01-13 60 n 3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter the stock name you wish to sell:\n"
        + "\u001B[0mEnter the number of stocks for: A\n"
        + "Enter date on which you wish to sell the stock, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mYou cannot sell a stock before the last buy i.e 2021-01-12:\n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mStock: A sold successfully in the portfolio with id 1\n"
        + "\u001B[0mDo you want to sell another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t50.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testSellWeekendDate()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 7 1 A 100 2021-01-10 2021-01-12 "
        + "60 n 3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter the stock name you wish to sell:\n"
        + "\u001B[0mEnter the number of stocks for: A\n"
        + "Enter date on which you wish to sell the stock, in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mProvided date falls on a weekend, "
        + "please provide a date which is a weekday\n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mStock: A sold successfully in the portfolio with id 1\n"
        + "\u001B[0mDo you want to sell another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t0.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testSellMissingStockName()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 7 1 IBM A 50 2021-01-10 2021-01-12 60 n "
        + "3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter the stock name you wish to sell:\n"
        + "\u001B[0m\u001B[0;31mProvided stock doesn't exist in the portfolio.\n"
        + "\u001B[0mEnter the stock name you wish to sell:\n"
        + "\u001B[0mEnter the number of stocks for: A\n"
        + "Enter date on which you wish to sell the stock, in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mProvided date falls on a weekend, please provide a date which "
        + "is a weekday\n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mStock: A sold successfully in the portfolio with id 1\n"
        + "\u001B[0mDo you want to sell another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t50.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testSellMissingStockQuantity()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 7 1 A 150 50 2021-01-10 2021-01-12 60 n "
        + "3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter the stock name you wish to sell:\n"
        + "\u001B[0mEnter the number of stocks for: A\n"
        + "You don't have sufficient stocks to sell\n"
        + "\u001B[0mEnter date on which you wish to sell the stock, in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mProvided date falls on a weekend, please provide a date which "
        + "is a weekday\n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mStock: A sold successfully in the portfolio with id 1\n"
        + "\u001B[0mDo you want to sell another stock (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t50.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  //COST BASIS
  @Test
  public void testCostBasisInvalidPortfolio()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 8 10 n 3 1 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0m\u001B[0;31mInvalid ID provided, "
        + "there is only one portfolio present which has ID : 1 \n"
        + "\u001B[0mDo you want to check cost basis of a different portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID to be displayed\n"
        + "\u001B[0m\u001B[1;32mComposition for Portfolio ID: 1\n"
        + "\u001B[1;32mStock ID: \tA, Number of Stocks Owned: \t100.0\n"
        + "\u001B[0mDo you want to examine another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testCostBasisZeroPastDate()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 8 1 2018-01-01 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . "
        + "Please use this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[1;32mTotal cost basis of portfolio 1 for the date 2018-01-01: $0.00\n"
        + "\u001B[0mDo you want to check cost basis of a different portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testCostBasisCorrectDate()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException
      , InterruptedException {
    String inputString = "1 1 1 A 100 2021-01-11 50 n 8 1 2021-01-12 n 10";

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use "
        + "this ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[1;32mTotal cost basis of portfolio 1 for the date 2021-01-12:"
        + " $12865.00\n"
        + "\u001B[0mDo you want to check cost basis of a different portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();
  }

  @Test
  public void testPortfolioAtAGlanceInvalidDatesProvidedAndAbsoluteScale()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException,
      InterruptedException {
    String inputString = "1 1 2 A 100 2021-01-11 50 IBM 2000 2021-05-05 60 n 9 1 "
        + "1992-05-05 2021-01-10 2021-01-08 2023-05-05 2021-11-11 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID "
        + "for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date from when you want to see the performance of your portfolio "
        + "in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mProvided date is more than twenty years old, please provide a "
        + "date which is in the past twenty years\n"
        + "\u001B[0m\u001B[0;31mProvided date falls on a weekend, please provide a date which "
        + "is a weekday\n"
        + "\u001B[0mEnter date upto when you want to see the performance of your portfolio "
        + "in the format YYYY-MM-DD:  \n"
        + "\u001B[0m\u001B[0;31mProvided date is in future, please provide current date or "
        + "past date\n"
        + "\u001B[0mGetting Data, Please Wait.........\n"
        + "\u001B[0m\u001B[1;32m2021-01-08 : NA\n"
        + "\u001B[1;32m2021-01-19 : NA\n"
        + "\u001B[1;32m2021-01-30 : NA\n"
        + "\u001B[1;32m2021-02-10 : NA\n"
        + "\u001B[1;32m2021-02-21 : NA\n"
        + "\u001B[1;32m2021-03-04 : NA\n"
        + "\u001B[1;32m2021-03-15 : NA\n"
        + "\u001B[1;32m2021-03-26 : NA\n"
        + "\u001B[1;32m2021-04-06 : NA\n"
        + "\u001B[1;32m2021-04-17 : NA\n"
        + "\u001B[1;32m2021-04-28 : NA\n"
        + "\u001B[1;32m2021-05-09 : *\n"
        + "\u001B[1;32m2021-05-20 : *\n"
        + "\u001B[1;32m2021-05-31 : *\n"
        + "\u001B[1;32m2021-06-11 : *\n"
        + "\u001B[1;32m2021-06-22 : *\n"
        + "\u001B[1;32m2021-07-03 : *\n"
        + "\u001B[1;32m2021-07-14 : *\n"
        + "\u001B[1;32m2021-07-25 : *\n"
        + "\u001B[1;32m2021-08-05 : *\n"
        + "\u001B[1;32m2021-08-16 : *\n"
        + "\u001B[1;32m2021-08-27 : *\n"
        + "\u001B[1;32m2021-09-07 : *\n"
        + "\u001B[1;32m2021-09-18 : *\n"
        + "\u001B[1;32m2021-09-29 : *\n"
        + "\u001B[1;32m2021-10-10 : *\n"
        + "\u001B[1;32m2021-10-21 : *\n"
        + "\u001B[1;32m2021-11-01 : *\n"
        + "\u001B[1;32m2021-11-11 : *\n"
        + "\u001B[1;32mScale : * = $256418.98\n"
        + "\u001B[1;32mScale Type : Absolute\n"
        + "\u001B[1;32mNA : You have not created a portfolio for that particular date.\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testPortfolioAtAGlanceGetDataDaily()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException,
      InterruptedException {
    String inputString = "1 1 2 A 1 2021-01-11 50 IBM 2000 2021-05-05 60 n 9 1 2021-10-11 "
        + "2021-01-08 "
        + "2023-05-05 2021-11-09 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this "
        + "ID for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date from when you want to see the performance of your portfolio in "
        + "the format YYYY-MM-DD:  \n"
        + "\u001B[0mEnter date upto when you want to see the performance of your portfolio in "
        + "the format YYYY-MM-DD:  \n"
        + "\u001B[0mProvided TO date should be latest more than 4 days after the provided "
        + "FROM date, please enter the correct TO date: \n"
        + "\u001B[0m\u001B[0;31mProvided date is in future, please provide current date or "
        + "past date\n"
        + "\u001B[0mGetting Data, Please Wait.........\n"
        + "\u001B[0m\u001B[1;32m2021-10-11 : *\n"
        + "\u001B[1;32m2021-10-12 : *\n"
        + "\u001B[1;32m2021-10-13 : *\n"
        + "\u001B[1;32m2021-10-14 : *\n"
        + "\u001B[1;32m2021-10-15 : *\n"
        + "\u001B[1;32m2021-10-16 : *\n"
        + "\u001B[1;32m2021-10-17 : *\n"
        + "\u001B[1;32m2021-10-18 : *\n"
        + "\u001B[1;32m2021-10-19 : *\n"
        + "\u001B[1;32m2021-10-20 : *\n"
        + "\u001B[1;32m2021-10-21 : *\n"
        + "\u001B[1;32m2021-10-22 : *\n"
        + "\u001B[1;32m2021-10-23 : *\n"
        + "\u001B[1;32m2021-10-24 : *\n"
        + "\u001B[1;32m2021-10-25 : *\n"
        + "\u001B[1;32m2021-10-26 : *\n"
        + "\u001B[1;32m2021-10-27 : *\n"
        + "\u001B[1;32m2021-10-28 : *\n"
        + "\u001B[1;32m2021-10-29 : *\n"
        + "\u001B[1;32m2021-10-30 : *\n"
        + "\u001B[1;32m2021-10-31 : *\n"
        + "\u001B[1;32m2021-11-01 : *\n"
        + "\u001B[1;32m2021-11-02 : *\n"
        + "\u001B[1;32m2021-11-03 : *\n"
        + "\u001B[1;32m2021-11-04 : *\n"
        + "\u001B[1;32m2021-11-05 : *\n"
        + "\u001B[1;32m2021-11-06 : *\n"
        + "\u001B[1;32m2021-11-07 : *\n"
        + "\u001B[1;32m2021-11-08 : *\n"
        + "\u001B[1;32m2021-11-09 : *\n"
        + "\u001B[1;32mScale : * = $241857.65\n"
        + "\u001B[1;32mScale Type : Absolute\n"
        + "\u001B[1;32mNA : You have not created a portfolio for that particular date.\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testPortfolioAtAGlanceGetDataMonthlyAndRelativeScale()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException,
      InterruptedException {
    String inputString = "1 1 2 A 1 2020-01-10 50 IBM 2000 2022-05-05 60 n 9 1 2021-10-11 "
        + "2021-01-08 2023-05-05 2022-10-11 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID for "
        + "further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date from when you want to see the performance of your portfolio in "
        + "the format YYYY-MM-DD:  \n"
        + "\u001B[0mEnter date upto when you want to see the performance of your portfolio in "
        + "the format YYYY-MM-DD:  \n"
        + "\u001B[0mProvided TO date should be latest more than 4 days after the provided FROM "
        + "date, please enter the correct TO date: \n"
        + "\u001B[0m\u001B[0;31mProvided date is in future, please provide current date or past "
        + "date\n"
        + "\u001B[0mGetting Data, Please Wait.........\n"
        + "\u001B[0m\u001B[1;32mOct 2021 : NA\n"
        + "\u001B[1;32mNov 2021 : NA\n"
        + "\u001B[1;32mDec 2021 : NA\n"
        + "\u001B[1;32mJan 2022 : NA\n"
        + "\u001B[1;32mFeb 2022 : NA\n"
        + "\u001B[1;32mMar 2022 : NA\n"
        + "\u001B[1;32mApr 2022 : NA\n"
        + "\u001B[1;32mMay 2022 : *\n"
        + "\u001B[1;32mJun 2022 : *\n"
        + "\u001B[1;32mJul 2022 : *\n"
        + "\u001B[1;32mAug 2022 : *\n"
        + "\u001B[1;32mSep 2022 : *\n"
        + "\u001B[1;32mOct 2022 : *\n"
        + "\u001B[1;32mScale : * = $235725.66\n"
        + "\u001B[1;32mScale Type : Absolute\n"
        + "\u001B[1;32mNA : You have not created a portfolio for that particular date.\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

  @Test
  public void testPortfolioAtAGlanceGetDataYearlyAndAbsoluteScale()
      throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException,
      InterruptedException {
    String inputString = "1 1 2 A 100 2012-01-10 50 IBM 200 2022-05-05 60 n 9 1 2010-10-11 "
        + "2022-10-11 n 10";
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    InputStream input = new ByteArrayInputStream(inputString.getBytes());
    StringBuilder log = new StringBuilder();
    StockController controller = new Controller(new InputStreamReader(System.in), System.out, input,
        out, testConfigPath, new StringBuilder("main"));
    controller.goController();
    String result = bytes.toString();
    String expectedOutput = "Menu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Select An Option from the Menu below \n"
        + "1. Flexible portfolio \n"
        + "2. Inflexible portfolio \n"
        + "Enter number of stocks to be added\n"
        + "\u001B[0mEnter stock number 1\n"
        + "Enter the number of stocks for: A\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0mEnter stock number 2\n"
        + "Enter the number of stocks for: IBM\n"
        + "Enter date on which the stocks were purchased, in the format YYYY-MM-DD:  \n"
        + "\u001B[0mPlease enter the commission fee paid while trading this stock: \n"
        + "\u001B[0m\u001B[1;32m\u001B[1;32mPortfolio created with id: 1 . Please use this ID "
        + "for further reference of the portfolio.\n"
        + "\u001B[0mDo you want add another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n"
        + "Enter portfolio ID: \n"
        + "\u001B[0mEnter date from when you want to see the performance of your "
        + "portfolio in the format YYYY-MM-DD:  \n"
        + "\u001B[0mEnter date upto when you want to see the performance of your "
        + "portfolio in the format YYYY-MM-DD:  \n"
        + "\u001B[0mGetting Data, Please Wait.........\n"
        + "\u001B[0m\u001B[1;32mDec 2010 : NA\n"
        + "\u001B[1;32mDec 2011 : NA\n"
        + "\u001B[1;32mDec 2012 : NA\n"
        + "\u001B[1;32mDec 2013 : NA\n"
        + "\u001B[1;32mDec 2014 : NA\n"
        + "\u001B[1;32mDec 2015 : NA\n"
        + "\u001B[1;32mDec 2016 : NA\n"
        + "\u001B[1;32mDec 2017 : NA\n"
        + "\u001B[1;32mDec 2018 : NA\n"
        + "\u001B[1;32mDec 2019 : NA\n"
        + "\u001B[1;32mDec 2020 : NA\n"
        + "\u001B[1;32mDec 2021 : NA\n"
        + "\u001B[1;32mOct 2022 : *\n"
        + "\u001B[1;32mScale : * = $36124.00\n"
        + "\u001B[1;32mScale Type : Absolute\n"
        + "\u001B[1;32mNA : You have not created a portfolio for that particular date.\n"
        + "\u001B[0mDo you want to get value of another portfolio (y/n) : \n"
        + "\u001B[0mMenu options\n"
        + "\u001B[0mSelect An Option from the Menu below \n"
        + "1. Create a new portfolio \n"
        + "2. Upload a new portfolio \n"
        + "3. Examine an existing portfolio \n"
        + "4. Download portfolio as a file \n"
        + "5. Get total value \n"
        + "6. Purchase Stocks \n"
        + "7. Sell Stocks \n"
        + "8. Cost Basis  \n"
        + "9. Portfolio At a Glance \n"
        + "10. Exit \n";
    assertEquals(expectedOutput, result);
    deleteFile();

  }

}
