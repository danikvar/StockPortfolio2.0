package views.gui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import controllers.Validations;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * Class for creating the frame for recurring dollar cost average.
 */
public class RecurringDcaGUI extends JFrame implements StockWeightsSetter {

  private final GUIView previousView;
  private final JRadioButton isEndDateAvailable;
  private final JRadioButton isEndDateNotAvailable;
  private final ButtonGroup buttonGroup;
  private JScrollPane scrollPane = new JScrollPane();
  final boolean[] isValidAmount = {false};
  final boolean[] isValidStartDate = {false};
  final boolean[] isValidEndDate = {false};
  final boolean[] isValidFrequency = {false};
  final boolean[] isValidCommission = {false};
  private final JButton addStockWeights;
  private final JButton createPortfolio;
  private final JButton back;
  List<List<String>> stockList = new ArrayList<>();
  private Map<String, Double> stockMap;
  private JTable table = new JTable();

  @Override
  public void setStockWeights(Map<String, Double> stockMap) {
    this.stockMap = stockMap;
  }

  /**
   * Constructor for displaying the frame.
   *
   * @param validStockMap map of valid stocks.
   * @param features      features object.
   * @param previousView  previous frame object.
   */
  public RecurringDcaGUI(Map<String, String> validStockMap, Features features,
      GUIViewImpl previousView) {
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.previousView = previousView;
    this.setSize(600, 400);
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    this.setTitle("Recurring Dollar Cost Average");
    getContentPane().setLayout(
        new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS)
    );

    JPanel nAmount = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel amount = new JLabel("Amount");
    amount.setVisible(true);
    nAmount.add(amount);
    nAmount.add(new JLabel("                             "));
    JTextArea amountInput = new JTextArea(1, 10);
    nAmount.add(amountInput);
    this.add(nAmount);

    JPanel sCommission = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel commission = new JLabel("Commission");
    commission.setVisible(true);
    sCommission.add(commission);
    sCommission.add(new JLabel("                     "));
    JTextArea commissionInput = new JTextArea(1, 10);
    sCommission.add(commissionInput);
    this.add(sCommission);

    JPanel sFrequency = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel frequency = new JLabel("Frequency (In days)");
    frequency.setVisible(true);
    sFrequency.add(frequency);
    sFrequency.add(new JLabel("        "));
    JTextArea frequencyInput = new JTextArea(1, 10);
    sFrequency.add(frequencyInput);
    this.add(sFrequency);

    JPanel sStartDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel startDate = new JLabel("Start Date (yyyy-MM-dd)");
    sStartDate.add(startDate);
    sStartDate.add(new JLabel(" "));
    JTextArea startDateInput = new JTextArea(1, 10);
    sStartDate.add(startDateInput);
    this.add(sStartDate);

    JPanel sEndDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel isEndDate = new JLabel("Do you want an end date for this strategy");
    JLabel yes = new JLabel("Yes");
    JLabel no = new JLabel("No");
    isEndDateAvailable = new JRadioButton();
    isEndDateNotAvailable = new JRadioButton();
    sEndDate.add(isEndDate);
    sEndDate.add(yes);
    sEndDate.add(isEndDateAvailable);
    sEndDate.add(no);
    sEndDate.add(isEndDateNotAvailable);
    buttonGroup = new ButtonGroup();
    buttonGroup.add(isEndDateAvailable);
    buttonGroup.add(isEndDateNotAvailable);
    this.add(sEndDate);

    JPanel sEndDateVal = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel endDate = new JLabel("End Date (yyyy-MM-dd)");
    sEndDateVal.setVisible(true);
    sEndDateVal.add(endDate);
    sEndDateVal.add(new JLabel("   "));
    JTextArea endDateInput = new JTextArea(1, 10);
    sEndDateVal.add(endDateInput);
    this.add(sEndDateVal);
    sEndDateVal.setVisible(false);
    JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
    addStockWeights = new JButton("Add Stock Weights");
    addStockWeights.setActionCommand("Add Stock Weights");
    addStockWeights.setVisible(true);
    addStockWeights.setEnabled(false);
    button.add(addStockWeights);

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

    actionListeners(validStockMap, this);
    setVisible(true);
    back.requestFocusInWindow();

    Validations dateValidator = new Validations();

    amountInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        String result = dateValidator.validateAmount(((JTextArea) input).getText());
        if (!result.equals("Valid")) {
          addStockWeights.setEnabled(false);
          isValidAmount[0] = false;
          showDialogueBox(result);
          return false;
        }
        return true;
      }

      public boolean shouldYieldFocus(JComponent source,
          JComponent target) {
        if (verify(source)) {
          isValidAmount[0] = true;
          if (isEndDateAvailable.isSelected()) {
            if (isValidStartDate[0] && isValidCommission[0] && isValidFrequency[0]
                && isValidEndDate[0]) {
              addStockWeights.setEnabled(true);
            }
          } else {
            if (isValidStartDate[0] && isValidCommission[0] && isValidFrequency[0]
                && isEndDateNotAvailable.isSelected()) {
              addStockWeights.setEnabled(true);
            }
          }

          return true;
        } else {
          return false;
        }
      }
    });

    frequencyInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        String result = dateValidator.validateFrequency(((JTextArea) input).getText());
        if (!result.equals("Valid")) {
          addStockWeights.setEnabled(false);
          isValidFrequency[0] = false;
          showDialogueBox(result);
          return false;
        }
        return true;
      }

      public boolean shouldYieldFocus(JComponent source,
          JComponent target) {
        if (verify(source)) {
          isValidFrequency[0] = true;
          if (isEndDateAvailable.isSelected()) {
            if (isValidStartDate[0] && isValidCommission[0] && isValidAmount[0]
                && isValidEndDate[0]) {
              addStockWeights.setEnabled(true);
            }
          } else {
            if (isValidStartDate[0] && isValidCommission[0] && isValidAmount[0]
                && isEndDateNotAvailable.isSelected()) {
              addStockWeights.setEnabled(true);
            }
          }

          return true;
        } else {
          return false;
        }
      }
    });

    startDateInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        try {
          boolean result = dateValidator.validateDateFormat(((JTextArea) input).getText());
          if (!result) {
            addStockWeights.setEnabled(false);
            isValidStartDate[0] = false;
            showDialogueBox("Invalid Start Date provided.");
            return false;
          }
          return true;
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }

      public boolean shouldYieldFocus(JComponent source,
          JComponent target) {
        if (verify(source)) {
          isValidStartDate[0] = true;
          if (isEndDateAvailable.isSelected()) {
            if (isValidAmount[0] && isValidCommission[0] && isValidFrequency[0]
                && isValidEndDate[0]) {
              addStockWeights.setEnabled(true);
            }
          } else {
            if (isValidAmount[0] && isValidCommission[0] && isValidFrequency[0]
                && isEndDateNotAvailable.isSelected()) {
              addStockWeights.setEnabled(true);
            }
          }

          return true;
        } else {
          return false;
        }
      }
    });

    endDateInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        try {
          boolean result = dateValidator.validateDateFormat(((JTextArea) input).getText());
          if (!result) {
            addStockWeights.setEnabled(false);
            isValidEndDate[0] = false;
            showDialogueBox("Invalid End Date provided!");
            return false;
          }
          return true;
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }

      public boolean shouldYieldFocus(JComponent source,
          JComponent target) {
        if (verify(source)) {
          isValidStartDate[0] = true;
          if (isValidCommission[0] && isValidAmount[0]) {
            addStockWeights.setEnabled(true);
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
          addStockWeights.setEnabled(false);
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
          if (isEndDateAvailable.isSelected()) {
            if (isValidAmount[0] && isValidStartDate[0] && isValidFrequency[0]
                && isValidEndDate[0]) {
              addStockWeights.setEnabled(true);
            }
          } else {
            if (isValidAmount[0] && isValidStartDate[0] && isValidFrequency[0]
                && isEndDateNotAvailable.isSelected()) {
              addStockWeights.setEnabled(true);
            }
          }

          return true;
        } else {
          return false;
        }
      }
    });

    isEndDateAvailable.addActionListener(e -> {
      if (e.getSource() == isEndDateAvailable) {
        addStockWeights.setEnabled(false);
        sEndDateVal.setVisible(true);
      }
    });

    isEndDateNotAvailable.addActionListener(e -> {
      if (e.getSource() == isEndDateNotAvailable) {
        if (isValidAmount[0] && isValidStartDate[0] && isValidCommission[0]) {
          addStockWeights.setEnabled(true);
        }
        endDateInput.setText("");
        sEndDateVal.setVisible(false);
      }
    });

    createPortfolio.addActionListener(e -> {
      try {
        //check end date > start date
        boolean passThrough = true;
        if (!endDateInput.getText().isEmpty()) {
          long diff = dateValidator.checkIfToDateIsMoreThanFromDate(startDateInput.getText(),
              endDateInput.getText());
          if (diff <= 0) {
            passThrough = false;
            showOutput("End date cannot be before or equal to Start Date", false);
          } else if (diff < Integer.parseInt(frequencyInput.getText())) {
            passThrough = false;
            showOutput(
                "Difference between end date and start date not sufficient "
                    + "for the given frequency",
                false);
          }

        }
        if (passThrough) {
          SwingWorker<String, Void> mySwingWorker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {

              String result = features.createPortfolioWithDollarAverageStrategy(
                  Double.parseDouble(amountInput.getText()),
                  Double.parseDouble(commissionInput.getText()),
                  Integer.parseInt(frequencyInput.getText()), startDateInput.getText(),
                  endDateInput.getText(), RecurringDcaGUI.this.stockMap);
              return result;
            }

          };

          Window win = SwingUtilities.getWindowAncestor((AbstractButton) e.getSource());
          final JDialog dialog = new JDialog(win, "Dialog", ModalityType.APPLICATION_MODAL);

          mySwingWorker.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("state")) {
              if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                dialog.dispose();
              }
            }
          });

          mySwingWorker.execute();

          JProgressBar progressBar = new JProgressBar();
          progressBar.setIndeterminate(true);
          JPanel panel = new JPanel(new BorderLayout());
          panel.add(progressBar, BorderLayout.CENTER);
          panel.add(new JLabel("Creating Portfolio, Please wait......."), BorderLayout.PAGE_START);
          dialog.add(panel);
          dialog.setUndecorated(true);
          dialog.pack();
          dialog.setLocationRelativeTo(win);
          dialog.setVisible(true);
          String result;
          try {
            result = mySwingWorker.get();
          } catch (Exception ex) {
            result = "error";
            ex.printStackTrace();
          }

          if (result.contains("created")) {
            amountInput.setText("");
            startDateInput.setText("");
            endDateInput.setText("");
            commissionInput.setText("");
            frequencyInput.setText("");
            showOutput(result.substring(7), true);
          } else {
            showOutput(result.substring(7), false);
          }
          stockList = new ArrayList<>();
          createPortfolio.setEnabled(false);
          addStockWeights.setEnabled(false);
          isValidCommission[0] = false;
          isValidStartDate[0] = false;
          isValidEndDate[0] = false;
          isValidAmount[0] = false;
          isValidFrequency[0] = false;
          back.requestFocusInWindow();
          buttonGroup.clearSelection();
        }

      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }

    });
  }

  /**
   * Show dialogue box.
   *
   * @param result message.
   */
  public void showDialogueBox(String result) {
    JOptionPane.showMessageDialog(this, result,
        "Invalid Input", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * ActionListener for the frame.
   *
   * @param validStockMap list of valid stocks.
   * @param frame         frame object.
   */
  public void actionListeners(Map<String, String> validStockMap, RecurringDcaGUI frame) {
    addStockWeights.addActionListener(e -> {
      scrollPane.setVisible(true);
      new AddStockWeightsGUI(validStockMap, frame);
    });
    back.addActionListener(e -> {
      dispose();
      this.previousView.setVisibleFrame(true);
      createPortfolio.setEnabled(false);
    });

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

    String[] columnNames = {"Stock Name", "Stock Weight"};
    table = new JTable(data, columnNames);
    table.repaint();
    table.setEnabled(false);
    scrollPane = new JScrollPane(table);
    scrollPane.setBounds(36, 37, 200, 100);
    this.add(scrollPane);
    this.setVisible(true);

  }

  /**
   * Show dialogue box.
   *
   * @param result message to be shown.
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
}
