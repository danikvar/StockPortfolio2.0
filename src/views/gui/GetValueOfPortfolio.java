package views.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
 * This class is used to create the frame for getting the value of portfolio.
 */
public class GetValueOfPortfolio extends JFrame {

  private final JButton generate;
  private final GUIView previousView;
  private JTable table = new JTable();
  private JScrollPane scrollPane = new JScrollPane();


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

  final boolean[] isValidDate = {false};

  /**
   * Constructor of the class, used to create a frame and display it.
   *
   * @param listPortfolios available list of portfolios.
   * @param features       features object.
   * @param previousView   frame of previous screen.
   */
  public GetValueOfPortfolio(List<String> listPortfolios, Features features,
      GUIViewImpl previousView) {
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.previousView = previousView;
    this.setSize(600, 400);
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    this.setTitle("Get Value of Portfolio");
    getContentPane().setLayout(
        new BorderLayout(8, 6)
    );
    JPanel subPanelNorth = new JPanel();
    JPanel portfolioId = new JPanel(new FlowLayout(5));
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
    portfolioId.add(cb);
    subPanelNorth.add(portfolioId);

    JPanel sDate = new JPanel(new FlowLayout(5));
    JLabel date = new JLabel("Date");
    sDate.add(date);
    JTextArea dateInput = new JTextArea(1, 10);
    sDate.add(dateInput);
    subPanelNorth.add(sDate);

    JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
    generate = new JButton("Get Value");
    generate.setActionCommand("Get Value");
    generate.setEnabled(false);
    button.add(generate);
    subPanelNorth.add(button);
    this.add(subPanelNorth, BorderLayout.NORTH);

    JButton back = new JButton("Back To Main Menu");
    back.setActionCommand("Back To Main Menu");
    this.add(back, BorderLayout.SOUTH);

    generate.setEnabled(false);

    JPanel resField = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel resultField = new JLabel();
    resField.add(resultField);
    this.add(resField);
    resField.setVisible(false);

    Validations dateValidator = new Validations();

    dateInput.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {

        try {
          String result = dateValidator.dateValidation(((JTextArea) input).getText());
          if (!result.equals("Valid")) {
            generate.setEnabled(false);
            isValidDate[0] = false;
            showOutput(result.substring(7));
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
          generate.setEnabled(true);
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

      }
    });

    setVisible(true);
    back.addActionListener(e -> {
      dispose();
      this.previousView.setVisibleFrame(true);
      generate.setEnabled(false);
    });
    generate.addActionListener(e -> {
      try {
        this.remove(table);
        this.remove(scrollPane);
        final DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Double> result = features.getValueOfPortfolio(
            Integer.parseInt((String) cb.getSelectedItem()),
            dateInput.getText());

        List<List<String>> stockList;

        if (result != null) {
          stockList = new ArrayList<>();

          for (Map.Entry<String, Double> entry : result.entrySet()) {
            List<String> listOfItems = new ArrayList<>();
            listOfItems.add(entry.getKey());
            listOfItems.add(df.format((entry.getValue())));
            stockList.add(listOfItems);

          }
          String[][] data = new String[stockList.size()][];
          int i = 0;
          for (List<String> nestedList : stockList) {
            data[i++] = nestedList.toArray(new String[0]);
          }
          String[] columnNames = {"Name", "Value",};
          table = new JTable(data, columnNames);
          scrollPane = new JScrollPane(table);
          table.repaint();
          table.setEnabled(false);
          scrollPane.setBounds(36, 37, 200, 100);
          add(scrollPane);
          setVisible(true);
          resultField.setText(String.valueOf(result.get("Total")));
          resField.setVisible(true);
          generate.setEnabled(false);
          cb.requestFocusInWindow();

        } else {
          showOutput("Provided date is a holiday, please provide another date");
          generate.setEnabled(false);
        }
        dateInput.setText("");
        isValidDate[0] = false;
      } catch (Exception ignored) {
        showOutput("Error has occurred, please try again");
      }


    });

  }

  /**
   * Shows a dialogue box with a message.
   *
   * @param result message to be displayed.
   */
  private void showOutput(String result) {
    JOptionPane.showMessageDialog(this, result,
        "Error", JOptionPane.ERROR_MESSAGE);


  }

}
