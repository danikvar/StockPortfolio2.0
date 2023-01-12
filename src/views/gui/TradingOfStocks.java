package views.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import controllers.Validations;


/**
 * Class for creating the frame for purchase/sell of stocks.
 */
public class TradingOfStocks extends JFrame {


  private final JButton create;
  private final JRadioButton isCommissionAvailable;
  private final JRadioButton isCommissionNotAvailable;
  private final ButtonGroup buttonGroup;
  private final GUIView previousView;

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

  /**
   * Constructor of the frame.
   *
   * @param validStockMap list of valid stocks.
   * @param features      features object.
   * @param frame         frame object.
   * @param tradeType     buy/sell.
   * @throws IOException                           I/O error.
   * @throws org.json.simple.parser.ParseException parse error.
   */
  public TradingOfStocks(Map<String, String> validStockMap, Features features, GUIView frame,
      String tradeType)
      throws IOException, org.json.simple.parser.ParseException {

    final boolean[] isValidNumberOfStocks = {false};
    final boolean[] isValidDate = {false};
    final boolean[] isValidCommission = {false};
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(600, 400);
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);
    this.previousView = frame;
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    if (tradeType.equals("buy")) {
      this.setTitle("Purchase Stocks");
    } else {
      this.setTitle("Sell Stocks");
    }

    getContentPane().setLayout(
        new BorderLayout(8, 6)
    );
    JPanel subPanelNorth = new JPanel();

    JPanel form = new JPanel();
    form.setLayout(
        new BoxLayout(form, BoxLayout.PAGE_AXIS)
    );

    JPanel portfolioId = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel portfolio = new JLabel("Portfolio ID");
    portfolioId.setVisible(true);
    List<String> listOfPortfolio = features.getListOfPortfolios();

    List<Integer> listOfInteger = convertStringListToIntList(
        listOfPortfolio, Integer::parseInt);
    Collections.sort(listOfInteger);
    List<String> strings = listOfInteger.stream().map(Object::toString)
        .collect(Collectors.toUnmodifiableList());

    FilterComboBox cb1 = new FilterComboBox(strings);
    cb1.setVisible(true);
    portfolioId.add(portfolio);
    portfolioId.add(new JLabel("            "));
    portfolioId.add(cb1);
    form.add(portfolioId);

    JPanel sName = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel stock = new JLabel("Stock Name");
    stock.setVisible(true);
    List<String> listOfStocks = new ArrayList<>(validStockMap.keySet());
    Collections.sort(listOfStocks);
    FilterComboBox cb = new FilterComboBox(listOfStocks);
    cb.setVisible(true);
    sName.add(stock);
    sName.add(new JLabel("            "));
    sName.add(cb);
    form.add(sName);

    JPanel sNumber = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel numberOfStocks = new JLabel("Number of Stocks");
    numberOfStocks.setVisible(true);
    JTextArea numberOfStocksInput = new JTextArea(1, 10);
    sNumber.add(numberOfStocks);
    sNumber.add(new JLabel(" "));
    sNumber.add(numberOfStocksInput);
    form.add(sNumber);

    JPanel sDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel date = new JLabel("Date (yyyy-MM-dd)");
    sDate.add(date);
    sDate.add(new JLabel(" "));
    JTextArea dateInput = new JTextArea(1, 10);
    sDate.add(dateInput);
    form.add(sDate);

    JPanel sCommission = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel isCommission;
    if (tradeType.equals("buy")) {
      isCommission = new JLabel("Did you pay any commission while purchasing this stock?");
    } else {
      isCommission = new JLabel("Did you pay any commission while selling this stock?");
    }
    JLabel yes = new JLabel("Yes");
    JLabel no = new JLabel("No");
    isCommissionAvailable = new JRadioButton();
    isCommissionNotAvailable = new JRadioButton();
    sCommission.add(isCommission);
    sCommission.add(yes);
    sCommission.add(isCommissionAvailable);
    sCommission.add(no);
    sCommission.add(isCommissionNotAvailable);
    buttonGroup = new ButtonGroup();
    buttonGroup.add(isCommissionAvailable);
    buttonGroup.add(isCommissionNotAvailable);
    form.add(sCommission);

    JPanel sCommissionVal = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel commission = new JLabel("Commission");
    sCommissionVal.setVisible(true);
    sCommissionVal.add(commission);
    sCommissionVal.add(new JLabel("            "));
    JTextArea commissionInput = new JTextArea(1, 10);
    sCommissionVal.add(commissionInput);
    form.add(sCommissionVal);
    sCommissionVal.setVisible(false);

    JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
    if (tradeType.equals("buy")) {
      create = new JButton("Purchase Stock");
      create.setActionCommand("Purchase Stock");
    } else {
      create = new JButton("Sell Stock");
      create.setActionCommand("Sell Stock");
    }
    JButton cancel = new JButton("Back To Main Menu");
    cancel.setActionCommand("Back To Main Menu");
    button.add(create);
    form.add(button);
    create.setEnabled(false);
    subPanelNorth.add(form);
    this.add(subPanelNorth, BorderLayout.CENTER);
    this.add(cancel, BorderLayout.SOUTH);

    Validations dateValidator = new Validations();
    numberOfStocksInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        String result = dateValidator.validateInputType(((JTextArea) input).getText());
        if (!result.equals("Valid")) {
          create.setEnabled(false);
          isValidNumberOfStocks[0] = false;
          showDialogueBox(result, false);
          return false;
        }
        return true;

      }

      public boolean shouldYieldFocus(JComponent source,
          JComponent target) {

        if (verify(source)) {
          isValidNumberOfStocks[0] = true;
          if (isCommissionAvailable.isSelected()) {
            if (isValidCommission[0] && isValidDate[0]) {
              create.setEnabled(true);

            }
          } else {
            if (isValidDate[0]) {
              create.setEnabled(true);
            }

          }
          return true;


        }
        return false;
      }
    });

    dateInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        try {
          String result = dateValidator.dateValidation(((JTextArea) input).getText());
          if (!result.equals("Valid")) {
            create.setEnabled(false);
            isValidDate[0] = false;
            showDialogueBox(result.substring(7), false);
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
          if (isCommissionAvailable.isSelected()) {
            if (isValidCommission[0] && isValidNumberOfStocks[0]) {
              create.setEnabled(true);

            }
          } else {
            if (isValidNumberOfStocks[0] && isCommissionNotAvailable.isSelected()) {
              create.setEnabled(true);

            }

          }
          return true;
        }
        return false;
      }

    });

    commissionInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        String result = dateValidator.validateCommission(((JTextArea) input).getText());
        if (!result.equals("Valid")) {
          create.setEnabled(false);
          isValidCommission[0] = false;
          showDialogueBox(result, false);
          return false;
        }
        return true;
      }

      public boolean shouldYieldFocus(JComponent source,
          JComponent target) {
        if (verify(source)) {
          isValidCommission[0] = true;
          if (isValidDate[0] && isValidNumberOfStocks[0]) {
            create.setEnabled(true);
          }
          return true;
        } else {
          return false;
        }
      }
    });

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        dateInput.setFocusable(false);
        dateInput.setFocusable(true);
        numberOfStocksInput.setFocusable(false);
        numberOfStocksInput.setFocusable(true);
        commissionInput.setFocusable(false);
        commissionInput.setFocusable(true);

      }
    });

    setVisible(true);
    create.addActionListener(e -> {
      try {
        String commissionValue;
        if (isCommissionNotAvailable.isSelected()) {
          commissionValue = "0";
        } else {
          commissionValue = commissionInput.getText();
        }
        String result;
        if (tradeType.equals("buy")) {
          result = features.buyStocks(cb1.getSelectedItem().toString(),
              cb.getSelectedItem().toString(), numberOfStocksInput.getText(), dateInput.getText(),
              commissionValue);

        } else {
          result = features.sellStocks(cb1.getSelectedItem().toString(),
              cb.getSelectedItem().toString(), numberOfStocksInput.getText(), dateInput.getText(),
              commissionValue);
        }
        if (result.contains("successfully")) {
          showDialogueBox(result.substring(7), true);
          numberOfStocksInput.setText("");
          dateInput.setText("");
          commissionInput.setText("");
          create.setEnabled(false);
          cb1.setSelectedIndex(0);
          cb.setSelectedIndex(0);
          buttonGroup.clearSelection();
          isValidCommission[0] = false;
          isValidDate[0] = false;
          isValidNumberOfStocks[0] = false;
        } else {
          showDialogueBox(result, false);
        }
      } catch (ParseException | URISyntaxException | IOException |
               org.json.simple.parser.ParseException | InterruptedException ex) {
        throw new RuntimeException(ex);
      }

    });
    cancel.addActionListener(e -> {
      dispose();
      this.previousView.setVisibleFrame(true);
      create.setEnabled(false);
    });

    isCommissionAvailable.addActionListener(e -> {
      if (e.getSource() == isCommissionAvailable) {
        create.setEnabled(false);
        sCommissionVal.setVisible(true);
      }
    });

    isCommissionNotAvailable.addActionListener(e -> {
      if (e.getSource() == isCommissionNotAvailable) {
        if (isValidNumberOfStocks[0] && isValidDate[0]) {
          create.setEnabled(true);
        }
        commissionInput.setText("");
        sCommissionVal.setVisible(false);
      }
    });


  }

  /**
   * Show the dialogue box.
   *
   * @param result  message to be displayed.
   * @param success true/false.
   */
  public void showDialogueBox(String result, boolean success) {
    if (success) {
      JOptionPane.showMessageDialog(this, result,
          "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(this, result,
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
  }


}
