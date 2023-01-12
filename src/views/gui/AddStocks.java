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
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import controllers.Validations;

/**
 * This class is used to create the frame in which the user adds stocks.
 */
public class AddStocks extends JFrame {

  private final JButton create;
  private JLabel pDisplay;

  /**
   * Constructor for the class, used to create and display the screen.
   * @param validStockMap list of stocks.
   * @param frame previous frame.
   */
  public AddStocks(Map<String, String> validStockMap, CreatePortfolioGUI frame) {

    final boolean[] isValidNumberOfStocks = {false};
    final boolean[] isValidDate = {false};
    final boolean[] isValidCommission = {false};
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(600, 400);
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);

    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    this.setTitle("Add Stocks");

    getContentPane().setLayout(
        new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS)
    );
    JPanel sName = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel stock = new JLabel("Stock Name");
    stock.setVisible(true);
    List<String> listOfStocks = new ArrayList<>(validStockMap.keySet());
    Collections.sort(listOfStocks);
    FilterComboBox cb = new FilterComboBox(listOfStocks);
    cb.setVisible(true);
    sName.add(stock);
    sName.add(new JLabel("             "));
    sName.add(cb);
    this.add(sName);

    JPanel sNumber = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel numberOfStocks = new JLabel("Number of Stocks");
    numberOfStocks.setVisible(true);
    JTextArea numberOfStocksInput = new JTextArea(1, 10);
    sNumber.add(numberOfStocks);
    sNumber.add(new JLabel("  "));
    sNumber.add(numberOfStocksInput);
    this.add(sNumber);

    JPanel sDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel date = new JLabel("Date (yyyy-MM-dd)");

    sDate.add(date);
    sDate.add(new JLabel(" "));

    JTextArea dateInput = new JTextArea(1, 10);
    sDate.add(dateInput);
    this.add(sDate);


    JPanel sCommission = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel commission = new JLabel("Commission");
    commission.setVisible(true);
    sCommission.add(commission);
    sCommission.add(new JLabel("           "));
    JTextArea commissionInput = new JTextArea(1, 10);
    sCommission.add(commissionInput);
    this.add(sCommission);

    JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
    create = new JButton("Add Stock To Portfolio");
    create.setActionCommand("Add Stock To Portfolio");
    JButton cancel = new JButton("Cancel");
    cancel.setActionCommand("Cancel");
    button.add(create);
    this.add(button);
    create.setEnabled(false);
    this.add(button);


    Validations dateValidator = new Validations();
    numberOfStocksInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        String result = dateValidator.validateInputType(((JTextArea) input).getText());
        if (!result.equals("Valid")) {
          create.setEnabled(false);
          isValidNumberOfStocks[0] = false;
          showDialogueBox(result);
          return false;
        }
        return true;
      }

      public boolean shouldYieldFocus(JComponent source,
          JComponent target) {
        if (verify(source)) {
          isValidNumberOfStocks[0] = true;
          if (isValidDate[0] && isValidCommission[0]) {
            create.setEnabled(true);
          }
          return true;
        } else {
          return false;
        }
      }
    });

    dateInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        try {
          String result = dateValidator.dateValidation(((JTextArea) input).getText());
          if (!result.equals("Valid")) {
            create.setEnabled(false);
            isValidDate[0] = false;
            showDialogueBox(result.substring(7));
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
          if (isValidCommission[0] && isValidNumberOfStocks[0]) {
            create.setEnabled(true);
          }
          return true;
        } else {
          return false;
        }
      }
    });

    commissionInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        String result = dateValidator.validateCommission(((JTextArea) input).getText());
        if (!result.equals("Valid")) {
          create.setEnabled(false);
          isValidCommission[0] = false;
          showDialogueBox(result);
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

      frame.setStockName((String) cb.getSelectedItem());
      frame.setNumberOfStocks(numberOfStocksInput.getText());
      frame.setDateCreated(dateInput.getText());
      frame.setCommissionPaid(commissionInput.getText());
      frame.addStockToList();
      dispose();

    });


  }

  /**
   * Shows the dialogue box with a message.
   *
   * @param result message to be displayed.
   */
  public void showDialogueBox(String result) {
    JOptionPane.showMessageDialog(this, result,
        "Invalid Input", JOptionPane.ERROR_MESSAGE);
  }


}
