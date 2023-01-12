import controllers.Controller;
import controllers.ControllerGUI;
import controllers.StockControllerGUI;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Properties;
import java.util.Scanner;
import models.Model;
import models.StockModel;
import views.gui.GUIView;
import views.gui.GUIViewImpl;

class Main {

  public static void main(String[] args)
      throws IOException, org.json.simple.parser.ParseException, URISyntaxException,
      InterruptedException {
    Scanner scan = new Scanner(System.in);

    System.out.println("Enter the type of view");
    System.out.println("1. Text Based UI");
    System.out.println("2. Graphical User Interface");
    int value;
    while (true) {
      try {
        value = Integer.parseInt(scan.next());
        if (value != 1 && value != 2) {
          System.out.println("Invalid Input provided, "
              + "please provide a valid number, i.e either 1 or 2");
        } else {
          break;
        }
      } catch (Exception e) {
        System.out.println("Invalid Input provided, "
            + "please provide a valid number, i.e either 1 or 2");
      }
    }
    InputStream is = Main.class.getResourceAsStream("/config/config.properties");
    Properties prop = new Properties();
    prop.load(is);
    if (value == 1) {
      try {
        new Controller(new InputStreamReader(System.in), System.out, System.in, System.out,
            prop.getProperty("ALL_PORTFOLIOS_JSON"), new StringBuilder("")).goController();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (URISyntaxException | InterruptedException | ParseException |
               org.json.simple.parser.ParseException e) {
        throw new RuntimeException(e);
      }
    } else {
      StockModel model = new Model(prop.getProperty("ALL_PORTFOLIOS_JSON"));
      StockControllerGUI controller = new ControllerGUI(
          model, prop.getProperty("ALL_PORTFOLIOS_JSON"));
      GUIView view = new GUIViewImpl();
      controller.setView(view);
    }

  }
}