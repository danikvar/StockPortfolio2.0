package views.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * A class for filtered combo box.
 */
public class FilterComboBox
    extends JComboBox {


  private final List<String> entries;

  public List<String> getEntries() {
    return entries;
  }

  /**
   * Constructor for the filter combox.
   *
   * @param entries list of entries.
   */
  public FilterComboBox(List<String> entries) {
    super(entries.toArray());
    this.entries = entries;
    this.setEditable(true);

    final JTextField textfield =
        (JTextField) this.getEditor().getEditorComponent();

    textfield.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent ke) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            comboFilter(textfield.getText());
          }
        });
      }
    });

  }

  /**
   * Build a list of entries that match the given filter.
   */
  public void comboFilter(String enteredText) {
    List<String> entriesFiltered = new ArrayList<String>();

    for (String entry : getEntries()) {
      if (entry.toLowerCase().contains(enteredText.toLowerCase())) {
        entriesFiltered.add(entry);
      }
    }

    if (entriesFiltered.size() > 0) {
      this.setModel(
          new DefaultComboBoxModel(
              entriesFiltered.toArray()));
      this.setSelectedItem(enteredText);
      this.showPopup();
    } else {
      this.hidePopup();
    }
  }
}
