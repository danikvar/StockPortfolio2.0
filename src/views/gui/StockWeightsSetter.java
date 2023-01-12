package views.gui;

import java.util.Map;

/**
 * Interface used to create frame for setting the weights for the stocks.
 */
public interface StockWeightsSetter {

  /**
   * Set the weights of the stocks provided.
   *
   * @param stockMap map of stocks and weights.
   */
  void setStockWeights(Map<String, Double> stockMap);

  /**
   * Method to add stock to the list.
   */
  void addStockToList();
}
