package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class for API fetching from the official alpha vantage backend, that returns stock prices
 * for major market indices across the US.
 */
public class AlphaVantageClient implements API {

  private String api_key;
  private String base_url;
  private String filePath;

  /**
   * Constructs the Alphavantage class initializing the API key and the base url for the api.
   */
  public AlphaVantageClient() {
    this.api_key = "J6DIGO0PAQSMIQ5O";
    this.base_url = "https://www.alphavantage.co/query?";
    this.filePath = "stock_cache.csv";
  }

  @Override
  public void getListingStatus() throws URISyntaxException, IOException, InterruptedException {
    StringBuilder builder = new StringBuilder(this.base_url);
    builder
        .append("function=LISTING_STATUS")
        .append("&apikey=" + this.api_key);
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI(builder.toString()))
        .GET()
        .build();
    HttpResponse<Path> response = client.send(request,
        HttpResponse.BodyHandlers.ofFileDownload(
            Path.of(System.getProperty("user.dir")),
            StandardOpenOption.CREATE, StandardOpenOption.WRITE));

    Path path = response.body();
  }

  /**
   * Create a map from the csv file.
   *
   * @param filePath filepath of csv.
   * @return map created.
   * @throws IOException an I/O exception.
   */
  private Map<String, String> getMapFromCSV(final String filePath) throws IOException {
    Stream<String> lines = Files.lines(Paths.get(filePath));
    Map<String, String> resultMap =
        lines.map(line -> line.split(","))
            .collect(Collectors.toMap(line -> line[0], line -> line[1]));

    lines.close();

    return resultMap;
  }

  @Override
  public String getStockData(String ticker, String date)
      throws IOException {
    File f = new File(this.filePath);
    if (!f.exists()) {
      f.createNewFile();
    }
    Map<String, String> stockCache = getMapFromCSV(filePath);
    if (stockCache.containsKey(ticker + "@" + date)) {
      return stockCache.get(ticker + "@" + date);
    }
    StringBuilder builder = new StringBuilder(this.base_url);
    builder
        .append("function=TIME_SERIES_DAILY")
        .append("&symbol=" + ticker)
        .append("&outputsize=full")
        .append("&datatype=csv")
        .append("&apikey=" + this.api_key);

    InputStream in = null;
    StringBuilder output = new StringBuilder();
    URL url = new URL(builder.toString());
    in = url.openStream();
    int b;
    while ((b = in.read()) != -1) {
      output.append((char) b);
    }
    Stream<String> lines = output.toString().lines();
    Map<String, String> newStockCache = lines.map(line -> line.split(","))
        .filter(obj -> obj.length > 5)
        .collect(Collectors.toMap(line -> ticker + "@" + line[0], line -> line[4]));
    lines.close();

    stockCache.putAll(newStockCache);

    String eol = System.getProperty("line.separator");

    try (Writer writer = new FileWriter(this.filePath)) {
      for (Map.Entry<String, String> entry : stockCache.entrySet()) {
        writer.append(entry.getKey())
            .append(',')
            .append(entry.getValue())
            .append(eol);
      }
    } catch (IOException ex) {
      ex.printStackTrace(System.err);
    }

    return stockCache.get(ticker + "@" + date);
  }


  @Override
  public String getCurrentStockData(String ticker)
      throws URISyntaxException, IOException, InterruptedException {
    StringBuilder builder = new StringBuilder(this.base_url);
    builder
        .append("function=GLOBAL_QUOTE")
        .append("&symbol=" + ticker)
        .append("&datatype=json")
        .append("&apikey=" + this.api_key);
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI(builder.toString()))
        .GET()
        .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    String json = response.body().toString();
    json = json.replace("\n", "").replace("\r", "")
        .replace(" ", "");
    json = json.replace("\"", "");

    String pattern = "05.price:(.*?),";
    Pattern r = Pattern.compile(pattern);

    Matcher m = r.matcher(json);
    if (!m.find()) {
      return null;
    }
    String dateString = m.group(1);
    return m.group(1);
  }


}


