package views.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * This class is used to create the frame in which the user adds weights for stocks.
 */

public class AddStockWeightsGUI extends JFrame {

  private NonDcaGUI frame;

  /**
   * Constructor of the class, used to create screen and display it.
   * @param validStockMap map of valid stocks.
   * @param setter object.
   */
  public AddStockWeightsGUI(Map<String, String> validStockMap, StockWeightsSetter setter) {

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(600, 400);
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);
    this.setTitle("Add Stocks and their weights");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    JTable table = new JTable();

    Object[] columns = {"Stock Name", "Stock Weight",};
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(columns);

    table.setModel(model);

    table.setBackground(Color.LIGHT_GRAY);
    table.setForeground(Color.black);
    Font font = new Font("", 1, 22);
    table.setFont(font);
    table.setRowHeight(30);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.getColumnModel().getColumn(0).setPreferredWidth(100);
    table.getColumnModel().getColumn(1).setPreferredWidth(100);

    JLabel stockNameLabel = new JLabel("Stock Name");
    JLabel stockWeightLabel = new JLabel("Stock Weight (%)");
    JTextField stockName = new JTextField();
    JTextField stockWeight = new JTextField();

    JButton btnAdd = new JButton("Add");
    JButton btnDelete = new JButton("Delete");
    JButton btnUpdate = new JButton("Update");
    JButton validateAndCreate = new JButton("Validate and Create");

    stockNameLabel.setBounds(20, 220, 100, 25);
    stockWeightLabel.setBounds(20, 250, 100, 25);

    stockName.setBounds(125, 220, 100, 25);
    stockWeight.setBounds(125, 250, 100, 25);

    btnAdd.setBounds(275, 220, 100, 25);
    btnUpdate.setBounds(275, 265, 100, 25);
    btnDelete.setBounds(275, 310, 100, 25);
    validateAndCreate.setBounds(400, 310, 150, 25);

    JScrollPane pane = new JScrollPane(table);
    pane.setBounds(0, 0, 220, 150);

    this.setLayout(null);
    this.add(pane);

    // add JTextFields to the jframe
    this.add(stockName);
    this.add(stockWeight);
    this.add(stockNameLabel);
    this.add(stockWeightLabel);

    // add JButtons to the jframe
    this.add(btnAdd);
    this.add(btnDelete);
    this.add(btnUpdate);
    this.add(validateAndCreate);

    Object[] row = new Object[2];

    // button add row
    btnAdd.addActionListener(e -> {

      row[0] = stockName.getText().toUpperCase();
      row[1] = stockWeight.getText();
      if (!(validStockMap.containsKey(row[0]) && Objects.equals(validStockMap.get(row[0]),
          "Active"))) {
        showDialogueBox("Please enter a valid Stock Name");
      } else {
        boolean flag = true;
        for (int i = 0; i < model.getRowCount(); i++) {
          String stock = (String) model.getValueAt(i, 0);
          if (stock.equals(row[0])) {
            showDialogueBox("Please enter a new Stock, or edit the previously added.");
            flag = false;
          }
        }
        if (flag) {
          try {
            Double.parseDouble((String) row[1]);
            model.addRow(row);
            stockWeight.setText("");
            stockName.setText("");
          } catch (Exception ex) {
            showDialogueBox("Please enter a valid Stock Weight");
          }
        }

      }


    });
    btnDelete.addActionListener(e -> {

      // i = the index of the selected row
      int i = table.getSelectedRow();
      if (i >= 0) {
        // remove a row from jtable
        model.removeRow(i);
      }
    });

    table.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {

        // i = the index of the selected row
        int i = table.getSelectedRow();

        stockName.setText(model.getValueAt(i, 0).toString());
        stockWeight.setText(model.getValueAt(i, 1).toString());
      }
    });

    btnUpdate.addActionListener(e -> {

      // i = the index of the selected row
      int i = table.getSelectedRow();

      if (i >= 0) {
        model.setValueAt(stockName.getText(), i, 0);
        model.setValueAt(stockWeight.getText(), i, 1);
      }
    });

    setVisible(true);

    validateAndCreate.addActionListener(e -> {
      Map<String, Double> stockMap = new HashMap<>();
      for (int i = 0; i < model.getRowCount(); i++) {
        String stock = (String) model.getValueAt(i, 0);
        Double weight = Double.parseDouble((String) model.getValueAt(i, 1));
        stockMap.put(stock, weight);
      }
      Double sumWeights = stockMap.values().stream().mapToDouble(d -> d).sum();
      if (sumWeights.equals(100.0)) {
        setter.setStockWeights(stockMap);
        setter.addStockToList();
        dispose();
      } else {
        showDialogueBox("Stock Weights needs to add upto 100, Please Update");
      }
    });
  }

  /**
   * Shows a dialogue box with a message.
   *
   * @param result message to be displayed.
   */
  public void showDialogueBox(String result) {
    JOptionPane.showMessageDialog(this, result,
        "Invalid Input", JOptionPane.ERROR_MESSAGE);
  }
}
