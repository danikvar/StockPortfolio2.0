package views.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import org.json.simple.parser.ParseException;

/**
 * Class to create the initial frame of the application.
 */
public class GUIViewImpl extends JFrame implements GUIView {

  private final JButton create;
  private final JButton costBasis;
  private final JButton getValue;
  private final JButton uploadButton;
  private final JButton buyStocks;
  private final JButton sellStocks;
  private final JButton nonRecurringDcaButton;
  private final JButton portfolioAtAGlance;
  private final JButton downloadButton;
  private final JButton recurringDcaButton;
  private JButton portfolioRebalancing;


  /**
   * Constructor for creating the frame.
   */
  public GUIViewImpl() {
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(700, 400);
    this.setLocation(dim.width / 2 - this.getSize().width / 2,
        dim.height / 2 - this.getSize().height / 2);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setTitle("Portfolio Management");

    JPanel title = new JPanel();
    JLabel titleDisplay = new JLabel("<html>"
        + "<h3 style=\"margin-top: 15px; width:250px; text-align: center\">Welcome to "
        + "Portfolio Management Application"
        + "</h3> "
        + "<h4 style=\"text-align: center; width:200px\">This "
        + "application offers wide range of operations that can be performed on a portfolio. "
        + "You can create, upload and download a portfolio,"
        + "purchase stocks based on dollar cost average strategy and "
        + "also look at the performance of your portfolio over a period of time.</h4>"
        + "</html");
    title.add(titleDisplay);

    JPanel buttons = new JPanel(new GridLayout(11, 1, 5, 5));
    create = new JButton("Create Portfolio");
    create.setActionCommand("Create Portfolio");
    buttons.add(create);

    getValue = new JButton("Get Value of Portfolio");
    getValue.setActionCommand("Get Value of Portfolio");
    buttons.add(getValue);

    costBasis = new JButton("Cost Basis");
    costBasis.setActionCommand("Cost Basis");
    buttons.add(costBasis);

    uploadButton = new JButton("Upload Portfolio");
    uploadButton.setActionCommand("Upload Portfolio");
    buttons.add(uploadButton);

    downloadButton = new JButton("Download Portfolio");
    downloadButton.setActionCommand("Download Portfolio");
    buttons.add(downloadButton);

    buyStocks = new JButton("Purchase Stocks");
    buyStocks.setActionCommand("Purchase Stocks");
    buttons.add(buyStocks);

    sellStocks = new JButton("Sell Stocks");
    sellStocks.setActionCommand("Sell Stocks");
    buttons.add(sellStocks);

    nonRecurringDcaButton = new JButton("Non Recurring Dollar Cost Averaging");
    nonRecurringDcaButton.setActionCommand("Non Recurring Dollar Cost Averaging");
    buttons.add(nonRecurringDcaButton);

    recurringDcaButton = new JButton("Recurring Dollar Cost Averaging New Portfolio");
    recurringDcaButton.setActionCommand("Recurring Dollar Cost Averaging New Portfolio");
    buttons.add(recurringDcaButton);

    portfolioRebalancing = new JButton("Portfolio Rebalancing");
    portfolioRebalancing.setActionCommand("Portfolio Rebalancing");
    buttons.add(portfolioRebalancing);

    portfolioAtAGlance = new JButton("Portfolio at a Glance");
    portfolioAtAGlance.setActionCommand("Portfolio at a Glance");
    buttons.add(portfolioAtAGlance);

    buttons.setVisible(true);

    JSplitPane splitPane = new JSplitPane();
    splitPane.setSize(600, 400);
    splitPane.setDividerSize(0);
    splitPane.setDividerLocation(350);
    splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setLeftComponent(title);
    splitPane.setRightComponent(buttons);

    this.add(splitPane);

    setVisible(true);
    title.requestFocusInWindow();
  }

  @Override
  public void setVisibleFrame(boolean value) {
    setVisible(value);
  }

  @Override
  public void addFeatures(Features features, Map<String, String> validStocks) {

    create.addActionListener(e -> {
      setVisible(false);
      try {
        GUIViewImpl view = getView();
        new CreatePortfolioGUI(validStocks, features, view);
      } catch (URISyntaxException | IOException | InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    });
    costBasis.addActionListener(e -> {
      try {
        if (features.getLatestPortfolio() == 0) {
          showDialogueBox("No portfolios created Yet! Please create one.", false);
        } else if (!features.getHasFlexiblePortfolio()) {
          showDialogueBox("There are no flexible portfolios created Yet! Please create one.",
              false);
        } else {
          setVisible(false);
          try {
            GUIViewImpl view = getView();
            new CostBasisGUI(features.getListOfPortfolios(), features, view);
          } catch (Exception ex) {
            throw new RuntimeException(ex);
          }

        }
      } catch (IOException | ParseException ex) {
        throw new RuntimeException(ex);
      }

    });

    getValue.addActionListener(e -> {
      try {
        if (features.getLatestPortfolio() == 0) {
          showDialogueBox("No portfolios created Yet! Please create one.", false);
        } else if (!features.getHasFlexiblePortfolio()) {
          showDialogueBox("There are no flexible portfolios created Yet! Please create one.",
              false);
        } else {
          setVisible(false);
          try {
            GUIViewImpl view = getView();
            new GetValueOfPortfolio(features.getListOfPortfolios(), features, view);
          } catch (Exception ex) {
            throw new RuntimeException(ex);
          }

        }
      } catch (IOException | ParseException ex) {
        throw new RuntimeException(ex);
      }

    });


    uploadButton.addActionListener(ae -> {
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        String filepath = selectedFile.getAbsolutePath();
        try {
          String result = features.uploadPortfolio(filepath);
          if (result.contains("Invalid") || result.contains("reupload")) {
            showDialogueBox(result, false);
          } else {
            showDialogueBox(result.substring(7), true);
          }
        } catch (IOException | ParseException | java.text.ParseException | URISyntaxException |
                 InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });

    nonRecurringDcaButton.addActionListener(e -> {
      try {
        if (features.getLatestPortfolio() == 0) {
          showDialogueBox("No portfolios created Yet! Please create one.", false);
        } else if (!features.getHasFlexiblePortfolio()) {
          showDialogueBox("There are no flexible portfolios created Yet! Please create one.",
              false);
        } else {
          try {
            setVisible(false);
            try {
              GUIViewImpl view = getView();
              new NonDcaGUI(features.getListOfPortfolios(), validStocks, features, view);
            } catch (Exception ex) {
              throw new RuntimeException(ex);
            }
          } catch (Exception ex) {
            throw new RuntimeException(ex);
          }
        }

      } catch (IOException ex) {
        throw new RuntimeException(ex);
      } catch (ParseException ex) {
        throw new RuntimeException(ex);
      }
    });

    portfolioRebalancing.addActionListener(e -> {
      try {
        if (features.getLatestPortfolio() == 0) {
          showDialogueBox("No portfolios created Yet! Please create one.", false);
        } else if (!features.getHasFlexiblePortfolio()) {
          showDialogueBox("There are no flexible portfolios created Yet! Please create one.",
                  false);
        } else {
          try {
            setVisible(false);
            try {
              GUIViewImpl view = getView();
              new PortfolioRebalancing(features.getListOfPortfolios(), validStocks, features, view);
            } catch (Exception ex) {
              throw new RuntimeException(ex);
            }
          } catch (Exception ex) {
            throw new RuntimeException(ex);
          }
        }

      } catch (IOException ex) {
        throw new RuntimeException(ex);
      } catch (ParseException ex) {
        throw new RuntimeException(ex);
      }
    });

    downloadButton.addActionListener(ae -> {
      try {
        if (features.getLatestPortfolio() == 0) {
          showDialogueBox("No portfolios created Yet! Please create one.", false);
        } else if (!features.getHasFlexiblePortfolio()) {
          showDialogueBox("There are no flexible portfolios created Yet! Please create one.",
              false);
        } else {
          setVisible(false);
          try {
            GUIViewImpl view = getView();
            new DownloadPortfolio(view, features);
          } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
          }

        }
      } catch (IOException | ParseException e) {
        throw new RuntimeException(e);
      }

    });

    recurringDcaButton.addActionListener(e -> {

      setVisible(false);
      try {
        GUIViewImpl view = getView();
        new RecurringDcaGUI(validStocks, features, view);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }


    });

    buyStocks.addActionListener(e -> {
      try {
        if (features.getLatestPortfolio() == 0) {
          showDialogueBox("No portfolios created Yet! Please create one.", false);
        } else {
          try {
            if (!features.getHasFlexiblePortfolio()) {
              showDialogueBox("There are no flexible portfolios created Yet! Please create "
                      + "one."
                      + "",
                  false);
            } else {
              setVisible(false);
              GUIViewImpl view = getView();
              try {
                new TradingOfStocks(validStocks, features, view, "buy");
              } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
              }
            }
          } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
          }
        }
      } catch (IOException | ParseException ex) {
        throw new RuntimeException(ex);
      }
    });
    sellStocks.addActionListener(e -> {
      try {
        if (features.getLatestPortfolio() == 0) {
          showDialogueBox("No portfolios created Yet! Please create one.", false);
        } else if (!features.getHasFlexiblePortfolio()) {
          showDialogueBox("There are no flexible portfolios created Yet! Please create one.",
              false);
        } else {
          setVisible(false);
          GUIViewImpl view = getView();
          try {
            new TradingOfStocks(validStocks, features, view, "sell");
          } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
          }
        }
      } catch (IOException | ParseException ex) {
        throw new RuntimeException(ex);
      }

    });

    portfolioAtAGlance.addActionListener(e -> {
      try {
        if (features.getLatestPortfolio() == 0) {
          showDialogueBox("No portfolios created Yet! Please create one.", false);
        } else {
          try {
            if (!features.getHasFlexiblePortfolio()) {
              showDialogueBox("There are no flexible portfolios created Yet! "
                      + "Please create one.",
                  false);
            } else {
              setVisible(false);
              GUIViewImpl view = getView();
              try {
                new PortfolioAtAGlance(this, features);
              } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
              }
            }
          } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
          }
        }
      } catch (IOException | ParseException ex) {
        throw new RuntimeException(ex);
      }
    });
  }


  /**
   * Getter of the object.
   *
   * @return object.
   */
  private GUIViewImpl getView() {
    return this;
  }

  /**
   * Show dialogue box.
   *
   * @param result message to be shown.
   * @param valid  error/success
   */
  public void showDialogueBox(String result, boolean valid) {
    if (!valid) {
      JOptionPane.showMessageDialog(this, result,
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(this, result,
          "Success", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
