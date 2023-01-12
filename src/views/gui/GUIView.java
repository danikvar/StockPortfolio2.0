package views.gui;

import java.util.Map;

/**
 * This interface is used to create the landing screen of the application.
 */
public interface GUIView {


  /**
   * Set the visibility of the screen.
   *
   * @param b true/false.
   */
  void setVisibleFrame(boolean b);


  /**
   * Add the features that are available to the screen.
   *
   * @param features    object of Features.
   * @param validStocks list of valid stocks.
   */
  void addFeatures(Features features, Map<String, String> validStocks);

}
