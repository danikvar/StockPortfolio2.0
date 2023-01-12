package models;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test the model.
 */
public class ModelTest {


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

  String testConfigPath;

  @Before
  public void initializeTestConfigPath()
      throws IOException, URISyntaxException, InterruptedException {
    InputStream is = getClass().getResourceAsStream("/config/config.properties");
    Properties prop = new Properties();
    prop.load(is);
    testConfigPath = prop.getProperty("ALL_PORTFOLIOS_JSON_TEST");
  }

  @Test
  public void testCreateDcaScratchPastStrategyAndCostBasis()
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException {
    StockModelStrategy modelStrategy = new ModelStrategy(testConfigPath);
    int nStocks = 2;
    Double nAmount = 2000.0;
    Double commissionAmount = 20.0;
    int nFrequencyInDays = 30;
    String startDate = "2021-01-01";
    String endDate = "2021-05-01";
    Map<String, Double> stockWeights = new HashMap<>();
    stockWeights.put("GOOG", 50.0);
    stockWeights.put("AAPL", 50.0);
    String result = modelStrategy.createStartToFinishDcaPortfolio(nStocks, nAmount, commissionAmount
        , nFrequencyInDays, startDate, endDate, stockWeights);
    assertTrue(result.contains("Strategy Portfolio with Dollar cost averaging created with id: 1"
        + " . Please use this ID for further reference of the portfolio."));

    StockModel model = new Model(testConfigPath);
    Double cost_basis1 = model.getCostBasisPortfolio(1, "2020-05-05");
    assertEquals(0.0, cost_basis1, 0.1);

    Double cost_basis2 = model.getCostBasisPortfolio(1, "2021-02-02");
    assertEquals(4000.0, cost_basis2, 0.1);

    Double cost_basis3 = model.getCostBasisPortfolio(1, "2021-06-06");
    assertEquals(8000.0, cost_basis3, 0.1);
  }

  @Test
  public void testCreateDcaScratchPastAndFutureStrategyAndCostBasis()
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException {
    StockModelStrategy modelStrategy = new ModelStrategy(testConfigPath);
    int nStocks = 2;
    Double nAmount = 2000.0;
    Double commissionAmount = 20.0;
    int nFrequencyInDays = 30;
    String startDate = "2022-11-11";
    String endDate = "2023-02-01";
    Map<String, Double> stockWeights = new HashMap<>();
    stockWeights.put("GOOG", 50.0);
    stockWeights.put("AAPL", 50.0);
    String result = modelStrategy.createStartToFinishDcaPortfolio(nStocks, nAmount, commissionAmount
        , nFrequencyInDays, startDate, endDate, stockWeights);
    assertTrue(result.contains("Strategy Portfolio with Dollar cost averaging created with id: 1 ."
        + " Please use this ID for further reference of the portfolio."));

    StockModel model = new Model(testConfigPath);
    Double cost_basis1 = model.getCostBasisPortfolio(1, "2020-05-05");
    assertEquals(0.0, cost_basis1, 0.1);

    Double cost_basis2 = model.getCostBasisPortfolio(1, "2023-01-04");
    assertEquals(4000.0, cost_basis2, 0.1);

    Double cost_basis3 = model.getCostBasisPortfolio(1, "2023-06-06");
    assertEquals(6000.0, cost_basis3, 0.1);
  }

  @Test
  public void testCreateDcaScratchFutureStrategyAndCostBasis()
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException {
    StockModelStrategy modelStrategy = new ModelStrategy(testConfigPath);
    int nStocks = 2;
    Double nAmount = 2000.0;
    Double commissionAmount = 20.0;
    int nFrequencyInDays = 30;
    String startDate = "2023-01-01";
    String endDate = "2023-05-05";
    Map<String, Double> stockWeights = new HashMap<>();
    stockWeights.put("GOOG", 50.0);
    stockWeights.put("AAPL", 50.0);
    String result = modelStrategy.createStartToFinishDcaPortfolio(nStocks, nAmount, commissionAmount
        , nFrequencyInDays, startDate, endDate, stockWeights);
    assertTrue(result.contains("Strategy Portfolio with Dollar cost averaging created with id: 1 ."
        + " Please use this ID for further reference of the portfolio."));

    StockModel model = new Model(testConfigPath);
    Double cost_basis1 = model.getCostBasisPortfolio(1, "2020-05-05");
    assertEquals(0.0, cost_basis1, 0.1);

    Double cost_basis2 = model.getCostBasisPortfolio(1, "2023-03-04");
    assertEquals(6000.0, cost_basis2, 0.1);

    Double cost_basis3 = model.getCostBasisPortfolio(1, "2023-06-06");
    assertEquals(10000.0, cost_basis3, 0.1);
  }

  @Test
  public void testAddNonRecurringDcaToExistingAndCostBasis()
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException {
    StockModel model = new Model(testConfigPath);
    Map<String, Map<String, String>> stockHash = new HashMap<>();
    model.createPortfolio(stockHash, "Flexible");

    int portfolioId = 1;
    Double nAmount = 2000.0;
    Double commissionAmount = 20.0;
    String date = "2021-05-05";
    Map<String, Double> stockWeights = new HashMap<>();
    stockWeights.put("GOOG", 50.0);
    stockWeights.put("AAPL", 50.0);

    StockModelStrategy modelStrategy = new ModelStrategy(testConfigPath);
    String result = modelStrategy.addNonRecurringDcaExistingPortfolio(portfolioId, nAmount,
        commissionAmount, date, stockWeights);
    assertTrue(result.contains("Stocks purchased successfully"));

    model = new Model(testConfigPath);
    Double cost_basis1 = model.getCostBasisPortfolio(1, "2020-05-05");
    assertEquals(0.0, cost_basis1, 0.1);

    Double cost_basis2 = model.getCostBasisPortfolio(1, "2021-06-06");
    assertEquals(2030.29, cost_basis2, 0.1);
  }

  @Test
  public void testAddNonRecurringDcaToExistingAndValueOfPortfolio()
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException {
    StockModel model = new Model(testConfigPath);
    Map<String, Map<String, String>> stockHash = new HashMap<>();
    model.createPortfolio(stockHash, "Flexible");

    int portfolioId = 1;
    Double nAmount = 2000.0;
    Double commissionAmount = 20.0;
    String date = "2021-05-05";
    Map<String, Double> stockWeights = new HashMap<>();
    stockWeights.put("GOOG", 50.0);
    stockWeights.put("AAPL", 50.0);

    StockModelStrategy modelStrategy = new ModelStrategy(testConfigPath);
    String result = modelStrategy.addNonRecurringDcaExistingPortfolio(portfolioId, nAmount,
        commissionAmount, date, stockWeights);
    assertTrue(result.contains("Stocks purchased successfully"));

    model = new Model(testConfigPath);
    Map<String, Double> value1 = model.getValueOfPortfolio(1, "2020-05-05");
    Map<String, Double> expectedValue = new HashMap<>();
    expectedValue.put("GOOG", 0.0);
    expectedValue.put("AAPL", 0.0);
    expectedValue.put("Total", 0.0);
    assertEquals(expectedValue, value1);

    Map<String, Double> value2 = model.getValueOfPortfolio(1, "2021-11-11");
    expectedValue = new HashMap<>();
    expectedValue.put("GOOG", 1232.6800537109375);
    expectedValue.put("AAPL", 1154.8599853515625);
    expectedValue.put("Total", 2387.5400390625);
    assertEquals(expectedValue, value2);
  }

  @Test
  public void testAddRecurringDcaScratchFutureAndValueOfPortfolio()
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException {
    StockModelStrategy modelStrategy = new ModelStrategy(testConfigPath);
    int nStocks = 2;
    Double nAmount = 2000.0;
    Double commissionAmount = 20.0;
    int nFrequencyInDays = 30;
    String startDate = "2023-01-01";
    String endDate = "2023-05-05";
    Map<String, Double> stockWeights = new HashMap<>();
    stockWeights.put("GOOG", 50.0);
    stockWeights.put("AAPL", 50.0);
    String result = modelStrategy.createStartToFinishDcaPortfolio(nStocks, nAmount,
        commissionAmount,
        nFrequencyInDays, startDate, endDate, stockWeights);
    assertTrue(result.contains("Strategy Portfolio with Dollar cost averaging created with id: 1 ."
        + " Please use this ID for further reference of the portfolio."));

    StockModel model = new Model(testConfigPath);
    Map<String, Double> value1 = model.getValueOfPortfolio(1, "2020-05-05");
    Map<String, Double> expectedValue = new HashMap<>();
    expectedValue.put("GOOG", 0.0);
    expectedValue.put("AAPL", 0.0);
    expectedValue.put("Total", 0.0);
    assertEquals(expectedValue, value1);
  }

  @Test
  public void testCreateDcaScratchPastStrategyAndValueOfPortfolio()
      throws IOException, ParseException, java.text.ParseException, URISyntaxException,
      InterruptedException {
    StockModelStrategy modelStrategy = new ModelStrategy(testConfigPath);
    int nStocks = 2;
    Double nAmount = 2000.0;
    Double commissionAmount = 20.0;
    int nFrequencyInDays = 30;
    String startDate = "2021-01-01";
    String endDate = "2021-05-01";
    Map<String, Double> stockWeights = new HashMap<>();
    stockWeights.put("GOOG", 50.0);
    stockWeights.put("AAPL", 50.0);
    String result = modelStrategy.createStartToFinishDcaPortfolio(nStocks, nAmount,
        commissionAmount,
        nFrequencyInDays, startDate, endDate, stockWeights);
    assertTrue(result.contains("Strategy Portfolio with Dollar cost averaging created with id: 1 ."
        + " Please use this ID for further reference of the portfolio."));

    StockModel model = new Model(testConfigPath);
    Map<String, Double> value1 = model.getValueOfPortfolio(1, "2020-05-05");
    Map<String, Double> expectedValue = new HashMap<>();
    expectedValue.put("GOOG", 0.0);
    expectedValue.put("AAPL", 0.0);
    expectedValue.put("Total", 0.0);
    assertEquals(expectedValue, value1);

    Map<String, Double> value2 = model.getValueOfPortfolio(1, "2021-02-02");
    expectedValue = new HashMap<>();
    expectedValue.put("GOOG", 2100.989990234375);
    expectedValue.put("AAPL", 2028.9000244140625);
    expectedValue.put("Total", 4129.8900146484375);
    assertEquals(expectedValue, value2);

    Map<String, Double> value3 = model.getValueOfPortfolio(1, "2022-05-05");
    expectedValue = new HashMap<>();
    expectedValue.put("GOOG", 4739.91015625);
    expectedValue.put("AAPL", 4858.2998046875);
    expectedValue.put("Total", 9598.2099609375);
    assertEquals(expectedValue, value3);
  }

  @Test
  public void rebalanceTest()
          throws IOException, ParseException, java.text.ParseException, URISyntaxException,
          InterruptedException {
    StockModel model = new Model(testConfigPath);
    Map<String, Map<String, String>> stockHash = new HashMap<>();
    model.createPortfolio(stockHash, "Flexible");

    int portfolioId = 1;
    Double nAmount = 1000.0;
    Double commissionAmount = 20.0;
    String date = "2022-11-15";
    Map<String, Double> stockWeights = new HashMap<>();
    stockWeights.put("NVDA", 80.0);
    stockWeights.put("AAPL", 20.0);

    StockModelStrategy modelStrategy = new ModelStrategy(testConfigPath);
    String result = modelStrategy.addNonRecurringDcaExistingPortfolio(portfolioId, nAmount,
            commissionAmount, date, stockWeights);
    assertTrue(result.contains("Stocks purchased successfully"));

    model = new Model(testConfigPath);
    Map<String, Double> value1 = model.getValueOfPortfolio(1, "2020-05-05");
    Map<String, Double> expectedValue = new HashMap<>();
    expectedValue.put("NVDA", 0.0);
    expectedValue.put("AAPL", 0.0);
    expectedValue.put("Total", 0.0);
    assertEquals(expectedValue, value1);

    Map<String, Double> value2 = model.getValueOfPortfolio(1, "2022-12-02");
    expectedValue = new HashMap<>();
    expectedValue.put("NVDA", 810.0499877929688);
    expectedValue.put("AAPL", 196.58999633789062);
    expectedValue.put("Total", 1006.6399841308594);
    assertEquals(expectedValue, value2);


    Map<String, Double> newStockWeights = new HashMap<>();
    newStockWeights.put("NVDA", 50.0);
    newStockWeights.put("AAPL", 50.0);
    String date2 = "2022-12-08";
    String newComm = "0.0";

    String result2 = model.rebalancePort(portfolioId, date2, newStockWeights, newComm);
    System.out.println(result2);
    Assert.assertTrue(result2.contains("Portfolio Rebalanced"));

    Map<String, Double> value3 = model.getValueOfPortfolio(1, "2022-12-08");
    expectedValue = new HashMap<>();
    expectedValue.put("NVDA", 506.489990234375);
    expectedValue.put("AAPL", 506.4100036621094);
    expectedValue.put("Total", 1012.8999938964844);
    assertEquals(expectedValue, value3);

    double cb2 = model.getCostBasisPortfolio(1, "2022-12-06");
    double cb3 = model.getCostBasisPortfolio(1, "2022-12-09");
    Assert.assertEquals(cb2,cb3,20);
    Map<String, Double> newStockWeightsBad = new HashMap<>();
    newStockWeightsBad.put("NVDA", 150.0);
    newStockWeightsBad.put("AAPL", 50.0);

    Map<String, Double> newStockWeightsBad2 = new HashMap<>();
    newStockWeightsBad2.put("NVDA", 50.0);
    newStockWeightsBad2.put("IBM", 50.0);

    String dateBad = "202-12-08";
    String newCommBad = "-1.0";

    String result3 = model.rebalancePort(portfolioId, date2, newStockWeightsBad, newComm);
    Assert.assertTrue(result3.contains("Portfolio Unbalanced"));

    String result4 = model.rebalancePort(portfolioId, date2, newStockWeightsBad2, newComm);
    Assert.assertTrue(result4.contains("Portfolio Unbalanced"));

    String result5 = model.rebalancePort(portfolioId, dateBad, newStockWeights, newComm);
    Assert.assertTrue(result5.contains("Portfolio Unbalanced"));

    String result6 = model.rebalancePort(portfolioId, date2, newStockWeights, newCommBad);
    Assert.assertTrue(result6.contains("Portfolio Unbalanced"));


    Map<String, Double> newStockWeights2 = new HashMap<>();
    newStockWeights2.put("NVDA", 70.0);
    newStockWeights2.put("AAPL", 30.0);

    String result9 = model.rebalancePort(portfolioId, date2, newStockWeights2, newComm);
    System.out.println(result9);
    Assert.assertTrue(result9.contains("Portfolio Rebalanced"));

    Map<String, Double> value5 = model.getValueOfPortfolio(1, "2022-12-08");
    expectedValue = new HashMap<>();
    expectedValue.put("NVDA", 709.0800170898438);
    expectedValue.put("AAPL", 303.8399963378906);
    expectedValue.put("Total", 1012.9200134277344);
    assertEquals(expectedValue, value5);

  }
}
