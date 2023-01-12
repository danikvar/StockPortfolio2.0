package views.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.json.simple.parser.ParseException;

/**
 * Class for creating the Create portfolio frame.
 */
public class CreatePortfolioGUI extends JFrame {

  private final JButton create;
  private final JButton addStock;
  private final JButton back;
  private final JLabel resultDisplay = new JLabel();
  List<List<String>> stockList = new ArrayList<>();
  private String stockName;
  private String numberOfStocks;
  private String dateCreated;
  private JTable table = new JTable();
  private JScrollPane scrollPane = new JScrollPane();

  /**
   * Setter for stock name.
   *
   * @param stockName name of stock.
   */
  public void setStockName(String stockName) {
    this.stockName = stockName;
  }

  /**
   * Setter for number of stocks.
   *
   * @param numberOfStocks number of stocks.
   */
  public void setNumberOfStocks(String numberOfStocks) {
    this.numberOfStocks = numberOfStocks;
  }

  /**
   * Setter for date created.
   *
   * @param dateCreated creation date.
   */
  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  /**
   * Setter for commission.
   *
   * @param commissionPaid commission paid.
   */
  public void setCommissionPaid(String commissionPaid) {
    this.commissionPaid = commissionPaid;
  }

  private String commissionPaid;
  private GUIView previousView;

  /**
   * Constructor of the class, used to create a frame and display it.
   *
   * @param validStockMap available list of stocks.
   * @param features      features object.
   * @param previousView  frame of previous screen.
   */
  public CreatePortfolioGUI(Map<String, String> validStockMap, Features features,
      GUIViewImpl previousView)
      throws URISyntaxException, IOException, InterruptedException {
    this.previousView = previousView;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(600, 400);
    this.setTitle("Create Portfolio");
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    getContentPane().setLayout(
        new BorderLayout(8, 6)
    );
    JPanel subPanelNorth = new JPanel();
    addStock = new JButton("Add Stock");
    addStock.setActionCommand("Add Stock");
    subPanelNorth.add(addStock);
    create = new JButton("Create Portfolio");
    create.setActionCommand("Create Portfolio");
    create.setEnabled(false);
    back = new JButton("Back To Main Menu");
    back.setActionCommand("Back To Main Menu");
    subPanelNorth.add(create);
    subPanelNorth.add(table);
    this.add(subPanelNorth, BorderLayout.NORTH);
    this.add(back, BorderLayout.SOUTH);
    this.add(resultDisplay);
    resultDisplay.setVisible(false);
    actionListeners(validStockMap, this, features);

    setVisible(true);


  }

  /**
   * ActionListener for the frame.
   *
   * @param validStockMap valid list of stocks.
   * @param frame         frame object.
   * @param features      features object.
   */
  public void actionListeners(Map<String, String> validStockMap, CreatePortfolioGUI frame,
      Features features) {
    addStock.addActionListener(e -> {
      scrollPane.setVisible(true);
      resultDisplay.setVisible(false);
      new AddStocks(validStockMap, frame);
    });
    back.addActionListener(e -> {
      dispose();
      this.previousView.setVisibleFrame(true);
      create.setEnabled(false);
    });
    create.addActionListener(e -> {
      try {
        String result = features.createPortfolioGUI(stockList);
        if (result.contains("created")) {
          showOutput(result.substring(7), true);
          create.setEnabled(false);
        } else {
          showOutput(result.substring(7), false);
          create.setEnabled(false);
        }
        stockList = new ArrayList<>();
        table.setVisible(false);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      } catch (ParseException ex) {
        throw new RuntimeException(ex);
      } catch (java.text.ParseException ex) {
        throw new RuntimeException(ex);
      } catch (URISyntaxException ex) {
        throw new RuntimeException(ex);
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }

    });
  }


  private void showOutput(String result, boolean status) {
    scrollPane.setVisible(false);
    if (status) {
      JOptionPane.showMessageDialog(this, result,
          "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(this, result,
          "Error", JOptionPane.ERROR_MESSAGE);
    }


  }


  /**
   * Method to add stock to list.
   */
  public void addStockToList() {
    create.setEnabled(true);
    this.remove(table);
    this.remove(scrollPane);
    List<String> listOfItems = new ArrayList<String>();
    listOfItems.add(this.stockName);
    listOfItems.add(this.numberOfStocks);
    listOfItems.add(this.dateCreated);
    listOfItems.add(this.commissionPaid);

    stockList.add(listOfItems);
    String[][] data = new String[stockList.size()][];
    int i = 0;
    for (List<String> nestedList : stockList) {
      data[i++] = nestedList.toArray(new String[0]);
    }

    String[] columnNames = {"StockID", "Number Of Stocks", "Date", "Commission"};
    table = new JTable(data, columnNames);
    table.repaint();
    table.setEnabled(false);
    scrollPane = new JScrollPane(table);
    scrollPane.setBounds(36, 37, 200, 100);
    this.add(scrollPane);
    this.setVisible(true);

  }
}

