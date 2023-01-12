package controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

/**
 * This interface is a Controller interface which is responsible for designating the method calls to
 * the Model class and View class respectively.
 */
public interface StockController {

  /**
   * This method is called in the main class, this method marks the start of the execution cycle.
   *
   * @throws IOException catches I/O error.
   */
  void goController()
      throws IOException, URISyntaxException, InterruptedException, ParseException,
      org.json.simple.parser.ParseException;
}
