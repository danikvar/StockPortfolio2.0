package models;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Class to output the file to a particular extension on local.
 */
public class ToFileWriter {

  /**
   * Takes in the data string and the path to save the data into.
   *
   * @param fileData data string.
   * @param filePath path to be saved to.
   */
  public static void writeToFile(String fileData, String filePath) {
    try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
      out.write(fileData);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
