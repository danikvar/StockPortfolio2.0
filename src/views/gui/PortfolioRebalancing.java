package views.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import controllers.Validations;

/**
 * Class to create a frame for the non-recurring dollar cost average.
 */
public class PortfolioRebalancing extends JFrame implements StockWeightsSetter {

  private final JButton addStockWeights;
  private final JButton createPortfolio;
  private final JButton back;
  private Map<String, Double> stockMap;
  private final GUIView previousView;
  private JTable table = new JTable();
  private JScrollPane scrollPane = new JScrollPane();
  List<List<String>> stockList = new ArrayList<>();
  final boolean[] isValidAmount = {false};
  final boolean[] isValidDate = {false};
  final boolean[] isValidCommission = {false};

  /**
   * Method to convert string list to int list.
   *
   * @param listOfString list of strings.
   * @param function     function.
   * @param <T>          datatype.
   * @param <U>          datatype.
   * @return list of ints.
   */
  public static <T, U> List<U> convertStringListToIntList(List<T> listOfString,
                                                          Function<T, U> function) {
    return listOfString.stream()
            .map(function)
            .collect(Collectors.toList());
  }

  @Override
  public void setStockWeights(Map<String, Double> stockMap) {
    this.stockMap = stockMap;
  }

  /**
   * Constructor for creating the screen.
   *
   * @param listPortfolios list of portfolios.
   * @param validStockMap  valid stocks map.
   * @param features       features object.
   * @param previousView   object of previous view.
   */
  public PortfolioRebalancing(List<String> listPortfolios, Map<String, String> validStockMap,
                   Features features,
                   GUIViewImpl previousView) {
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.previousView = previousView;
    this.setSize(600, 400);
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
            dim.height / 2 - this.getSize().height / 2);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    this.setTitle("Portfolio Rebalancing");
    getContentPane().setLayout(
            new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS)
    );
    JPanel portfolioId = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel portfolio = new JLabel("Portfolio ID");
    portfolio.setVisible(true);
    List<Integer> listOfInteger = convertStringListToIntList(
            listPortfolios, Integer::parseInt);
    Collections.sort(listOfInteger);
    List<String> strings = listOfInteger.stream().map(Object::toString)
            .collect(Collectors.toUnmodifiableList());

    FilterComboBox cb = new FilterComboBox(strings);
    cb.setVisible(true);
    portfolioId.add(portfolio);
    portfolioId.add(new JLabel("             "));
    portfolioId.add(cb);
    this.add(portfolioId);

    JPanel sDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel date = new JLabel("Date (yyyy-MM-dd)");

    sDate.add(date);
    sDate.add(new JLabel(" "));

    JTextArea dateInput = new JTextArea(1, 10);
    sDate.add(dateInput);
    this.add(sDate);


    JPanel nAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel amount = new JLabel("Amount");



    JTextArea amountInput = new JTextArea(1, 10);



    Validations dateValidator = new Validations();


    dateInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {

        try {
          String result = dateValidator.dateValidation(((JTextArea) input).getText());
          if (!result.equals("Valid")) {
            addStockWeights.setEnabled(true);
            isValidDate[0] = false;
            showOutput(result.substring(7), false);
            return false;
          }
          return true;
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }

      public boolean shouldYieldFocus(JComponent source,
                                      JComponent target) {
        if (verify(source)) {
          isValidDate[0] = true;
          if (isValidCommission[0] && isValidAmount[0]) {
            addStockWeights.setEnabled(true);
          }
          return true;
        } else {
          return false;
        }
      }
    });


    JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
    addStockWeights = new JButton("Add Stock Weights");
    addStockWeights.setActionCommand("Add Stock Weights");
    addStockWeights.setVisible(true);
    addStockWeights.setEnabled(true);
    button.add(addStockWeights);
    this.add(button);

    createPortfolio = new JButton("Create");
    createPortfolio.setActionCommand("Create");
    createPortfolio.setVisible(true);
    createPortfolio.setEnabled(false);
    button.add(createPortfolio);

    back = new JButton("Back To Main Menu");
    back.setActionCommand("Back To Main Menu");
    button.add(back);
    this.add(button);
    this.add(table);
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        dateInput.setFocusable(false);
        dateInput.setFocusable(true);



      }


    });
    actionListeners(validStockMap, this);
    setVisible(true);
    createPortfolio.addActionListener(e -> {
      try {

        String commission = "0";
        String result = features.rebalance((String) cb.getSelectedItem(), dateInput.getText(),
                stockMap, commission) ;
        
        if (result.contains("Portfolio Rebalanced")) {
          dateInput.setText("");
          cb.setSelectedIndex(0);
          showOutput(result.substring(20), true);
        } else {
          showOutput(result.substring(20), false);
        }
        createPortfolio.setEnabled(false);
        addStockWeights.setEnabled(true);
        isValidCommission[0] = false;
        isValidDate[0] = false;
        isValidAmount[0] = false;
        stockList = new ArrayList<>();
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }

    });

  }

  /**
   * ActionListener for the frame.
   *
   * @param validStockMap list of valid stocks.
   * @param frame         frame object.
   */
  public void actionListeners(Map<String, String> validStockMap, PortfolioRebalancing frame) {
    addStockWeights.addActionListener(e -> {
      scrollPane.setVisible(true);
      new RebalancingWeights(validStockMap, frame);

    });
    back.addActionListener(e -> {
      dispose();
      this.previousView.setVisibleFrame(true);
      createPortfolio.setEnabled(false);
    });

  }

  /**
   * Display dialogue box.
   *
   * @param result message to be diaplsyed.
   * @param status true/false.
   */
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

  @Override
  public void addStockToList() {
    createPortfolio.setEnabled(true);
    this.remove(table);
    this.remove(scrollPane);
    List<String> listOfItems = new ArrayList<String>();
    stockList = new ArrayList<>();
    Map<String, Double> x = this.stockMap;
    for (Map.Entry<String, Double> set : this.stockMap.entrySet()) {
      listOfItems = new ArrayList<String>();
      listOfItems.add(set.getKey());
      listOfItems.add(String.valueOf(set.getValue()));
      stockList.add(listOfItems);
    }
    String[][] data = new String[stockList.size()][];
    int i = 0;
    for (List<String> nestedList : stockList) {
      data[i++] = nestedList.toArray(new String[0]);
    }

    String[] columnNames = {"Stock Name", "Rebalanced Weight"};
    table = new JTable(data, columnNames);
    table.repaint();
    table.setEnabled(false);
    scrollPane = new JScrollPane(table);
    scrollPane.setBounds(36, 37, 200, 100);
    this.add(scrollPane);
    this.setVisible(true);


  }

}
