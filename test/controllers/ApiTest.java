package controllers;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * Test class for testing the API methods to check all the null and normal values handling.
 */
public class ApiTest {

  @Test
  public void testObjectCreation() {
    AlphaVantageClient apiMock = new AlphaVantageClient();
    assertNotSame(null, apiMock);
  }

  @Test
  public void testInitialFileCreationListingStatus()
      throws URISyntaxException, IOException, InterruptedException {
    AlphaVantageClient apiMock = new AlphaVantageClient();
    apiMock.getListingStatus();
    Path path = Paths.get(System.getProperty("user.dir") + "/listing_status.csv");
    assertTrue(Files.exists(path));
  }

  @Test
  public void testInvalidTicker()
      throws URISyntaxException, IOException, InterruptedException {
    AlphaVantageClient apiMock = new AlphaVantageClient();
    String price = apiMock.getStockData("XXX", "2022-01-05");
    assertEquals(null, price);
  }

  @Test
  public void testValidTickerAndDate()
      throws URISyntaxException, IOException, InterruptedException {
    AlphaVantageClient apiMock = new AlphaVantageClient();
    String price = apiMock.getStockData("IBM", "2022-01-05");
    assertEquals("138.2200", price);
  }

  @Test
  public void testInValidDate()
      throws URISyntaxException, IOException, InterruptedException {
    AlphaVantageClient apiMock = new AlphaVantageClient();
    String price = apiMock.getStockData("IBM", "222222");
    assertEquals(null, price);
  }

  @Test
  public void testInvalidTickerCurrentDay()
      throws URISyntaxException, IOException, InterruptedException {
    AlphaVantageClient apiMock = new AlphaVantageClient();
    String price = apiMock.getCurrentStockData("XXX");
    assertEquals(null, price);
  }

  @Test
  public void testValidTickerAndDateCurrentDay()
      throws URISyntaxException, IOException, InterruptedException {
    AlphaVantageClient apiMock = new AlphaVantageClient();
    String price = apiMock.getCurrentStockData("IBM");
    System.out.println(price);
    assertNotSame(null, price);
  }

  @Test
  public void rebalanceTest() throws URISyntaxException, IOException, InterruptedException {

    AlphaVantageClient apiMock = new AlphaVantageClient();
    String price = apiMock.getCurrentStockData("IBM");
    Assert.assertEquals("147.0500",price);

  }
}
