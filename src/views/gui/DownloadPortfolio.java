package views.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.simple.parser.ParseException;

/**`
 * Class for creating the download portfolio frame.
 */
public class DownloadPortfolio extends JFrame {

  /**
   * Convert list to string.
   *
   * @param listOfString available list of strings.
   * @param function     function.
   * @param <T>          datatype.
   * @param <U>          datatype.
   * @return converted list.
   */
  public static <T, U> List<U> convertStringListToIntList(List<T> listOfString,
      Function<T, U> function) {
    return listOfString.stream()
        .map(function)
        .collect(Collectors.toList());
  }

  JButton create;
  JButton cancel;


  /**
   * Constructor of the class, used to create a frame and display it.
   *
   * @param features features object.
   * @param frame    frame of previous screen.
   */
  public DownloadPortfolio(GUIViewImpl frame, Features features)
      throws IOException, ParseException {
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(600, 400);
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setResizable(false);
    this.setTitle("Download Portfolio");
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
    portfolioId.setVisible(true);
    portfolioId.add(portfolio);
    portfolioId.add(cb1);
    subPanelNorth.add(portfolioId);

    JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
    create = new JButton("Save Portfolio");
    create.setActionCommand("Save Portfolio");
    cancel = new JButton("Back To Main Menu");
    cancel.setActionCommand("Back To Main Menu");
    button.add(create);
    subPanelNorth.add(button);
    create.setEnabled(true);
    this.add(subPanelNorth);
    this.add(cancel, BorderLayout.SOUTH);

    this.setVisible(true);

    create.addActionListener(ae -> {
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showSaveDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        String filepath = selectedFile.getAbsolutePath();
        String result = null;
        try {
          result = features.downloadPortfolio(filepath, cb1.getSelectedItem().toString());

          if (result.contains("successfully")) {
            showDialogueBox("Portfolio downloaded successfully");
            cb1.setSelectedIndex(0);
          }
        } catch (IOException | ParseException e) {
          throw new RuntimeException(e);
        }
      }
    });

    cancel.addActionListener(e -> {
      dispose();
      frame.setVisibleFrame(true);
    });
  }

  /**
   * Display Dialogue box.
   *
   * @param result message to be displayed.
   */
  public void showDialogueBox(String result) {

    JOptionPane.showMessageDialog(this, result,
        "Success", JOptionPane.INFORMATION_MESSAGE);


  }
}


