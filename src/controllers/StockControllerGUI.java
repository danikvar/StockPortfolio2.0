package controllers;

import java.io.IOException;
import views.gui.GUIView;

/**
 * Interface for the controller of GUI.
 */
public interface StockControllerGUI {

  /**
   * Method to create a GUI object and display the GUI on the screen.
   *
   * @param view view object of GUI.
   * @throws IOException                           I/O error.
   * @throws org.json.simple.parser.ParseException parse error.
   */
  void setView(GUIView view) throws IOException, org.json.simple.parser.ParseException;
}
