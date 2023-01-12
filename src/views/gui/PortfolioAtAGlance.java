package views.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import controllers.Validations;
import org.json.simple.parser.ParseException;

/**
 * Class for creating a frame to show performance of portfolio.
 */
public class PortfolioAtAGlance extends JFrame {

  JButton cancel;
  JButton view;
  final boolean[] isValidToDate = {false};
  final boolean[] isValidFromDate = {false};

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
   * Constructor of the class, used to display screen.
   *
   * @param frame    frame object.
   * @param features features object.
   * @throws IOException    I/O exception.
   * @throws ParseException parse error.
   */
  public PortfolioAtAGlance(GUIViewImpl frame, Features features)
      throws IOException, ParseException {
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(600, 400);
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    this.setTitle("Portfolio at a glance");
    getContentPane().setLayout(
        new BorderLayout(8, 6)
    );
    JPanel subPanelNorth = new JPanel();
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
    portfolioId.add(cb1);
    subPanelNorth.add(portfolioId);
    JPanel sDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel fromDate = new JLabel("From Date");
    sDate.add(fromDate);
    JTextArea fromDateInput = new JTextArea(1, 10);
    sDate.add(fromDateInput);
    JLabel toDate = new JLabel("To Date");
    sDate.add(toDate);
    JTextArea toDateInput = new JTextArea(1, 10);
    sDate.add(toDateInput);
    toDateInput.setEnabled(false);
    view = new JButton("View Performance");
    view.setActionCommand("View Performance");
    sDate.add(view);
    view.setEnabled(false);
    subPanelNorth.add(sDate);
    cancel = new JButton("Back To Main Menu");
    cancel.setActionCommand("Back To Main Menu");
    this.add(subPanelNorth);
    this.add(cancel, BorderLayout.SOUTH);
    this.setVisible(true);

    cancel.addActionListener(e -> {
      dispose();
      frame.setVisibleFrame(true);
    });

    view.addActionListener(e -> {
      try {
        Map<String, String> data = features.getPortfolioAtAGlance(cb1.getSelectedItem().toString(),
            fromDateInput.getText(), toDateInput.getText());

        new LineChart("Performance of Portfolio", data);
        fromDateInput.setText("");
        toDateInput.setText("");
        toDateInput.setEnabled(false);
        view.setEnabled(false);
      } catch (IOException | ParseException | java.text.ParseException | URISyntaxException |
               InterruptedException ex) {
        throw new RuntimeException(ex);
      }


    });
    Validations dateValidator = new Validations();
    fromDateInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {

        try {
          boolean result = dateValidator.validateDateFormat(((JTextArea) input).getText());
          if (!result) {
            view.setEnabled(false);
            isValidFromDate[0] = false;
            showDialogueBox("Invalid Date format provided, please provide data in "
                + "yyyy-MM-dd.");
            return false;
          } else {
            if (!Objects.equals(toDateInput.getText(), "") || toDateInput.getText() == null) {
              long diff = dateValidator.checkIfToDateIsMoreThanFromDate(fromDateInput.getText(),
                  toDateInput.getText());
              if (diff <= 4) {
                view.setEnabled(false);
                showDialogueBox("Todate must be at least 5 days after the Fromdate");
                return false;
              }
            }
          }
          return true;
        } catch (java.text.ParseException e) {
          throw new RuntimeException(e);
        }
      }

      public boolean shouldYieldFocus(JComponent source,
          JComponent target) {
        if (verify(source)) {
          isValidFromDate[0] = true;
          toDateInput.setEnabled(true);
          return true;
        } else {
          return false;
        }
      }
    });

    toDateInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {

        try {
          boolean result = dateValidator.validateDateFormat(((JTextArea) input).getText());
          if (!result) {
            view.setEnabled(false);
            isValidToDate[0] = false;
            showDialogueBox("Invalid Date format provided, please provide data in "
                + "yyyy-MM-dd.");
            return false;
          } else {
            long diff = dateValidator.checkIfToDateIsMoreThanFromDate(fromDateInput.getText(),
                toDateInput.getText());
            if (diff <= 4) {
              view.setEnabled(false);
              showDialogueBox("Todate must be at least 5 days after the Fromdate");
              return false;
            }
          }
          return true;
        } catch (java.text.ParseException e) {
          throw new RuntimeException(e);
        }
      }

      public boolean shouldYieldFocus(JComponent source,
          JComponent target) {
        if (verify(source)) {
          isValidToDate[0] = true;
          view.setEnabled(true);
          return true;
        } else {
          return false;
        }
      }
    });

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        fromDateInput.setFocusable(false);
        fromDateInput.setFocusable(true);
        toDateInput.setFocusable(false);
        toDateInput.setFocusable(true);

      }
    });
  }

  /**
   * Show dialogue box.
   *
   * @param result message to be displayed.
   */
  public void showDialogueBox(String result) {
    JOptionPane.showMessageDialog(this, result,
        "Invalid Input", JOptionPane.ERROR_MESSAGE);
  }

}
