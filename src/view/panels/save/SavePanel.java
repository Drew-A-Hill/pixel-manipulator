package view.panels.save;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import storage.IStorage;
import view.actions.ActionRunner;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 10
 * This class serves as the self-contained save panel. It owns the file path input
 * field and save button, and notifies the parent window via a callback after a
 * successful save.
 */
public class SavePanel extends JPanel {

  private static final String DEFAULT_PATH = "Enter File Path";

  private final IStorage storage;
  private final Supplier<String> getSelectedImage; // reads selected image name from parent
  private final Runnable onSave;                   // called after a successful save

  private JTextField filePathField;

  /**
   * Constructs the save panel.
   * @param storage the image storage model.
   * @param getSelectedImage supplier that returns the currently selected image name.
   * @param onSave callback invoked after a successful save to refresh the image list.
   */
  public SavePanel(IStorage storage,
                   Supplier<String> getSelectedImage,
                   Runnable onSave) {
    this.storage = storage;
    this.getSelectedImage = getSelectedImage;
    this.onSave = onSave;
    build();
  }

  /**
   * Builds and lays out all components of the save panel.
   */
  private void build() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createTitledBorder("Save Image"));

    filePathField = makePlaceholderField();

    JButton saveButton = new JButton("save");
    saveButton.setToolTipText("Click to save Image");
    saveButton.addActionListener(this::handleSave);

    JPanel saveRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    saveRow.add(new JLabel("File Path:  "));
    saveRow.add(filePathField);
    saveRow.add(saveButton);

    add(saveRow);
  }

  /**
   * Handles the save button press. Builds the save instruction string, runs the
   * save command, and notifies the parent on success.
   * @param e the action event from the save button.
   */
  private void handleSave(ActionEvent e) {
    String filePath     = filePathField.getText().trim();
    String fileName     = getSelectedImage.get();
    String instructions = filePath + " " + fileName;

    try {
      new ActionRunner().run(instructions, storage, e.getActionCommand().toLowerCase());
      onSave.run();
      resetPrompt();
    } catch (Exception ex) {
      errorMessage("Bad File Path");
    }
  }

  /**
   * Resets the file path field to its default placeholder text.
   */
  public void resetPrompt() {
    filePathField.setText(DEFAULT_PATH);
    filePathField.setForeground(new Color(80, 77, 77, 210));
    filePathField.setHorizontalAlignment(JTextField.CENTER);
  }

  /**
   * Creates a popup error message with relevant information.
   * @param message message to be displayed in the pop up error box.
   */
  private void errorMessage(String message) {
    JOptionPane error = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
    JDialog dialog = error.createDialog(error, "Error");
    dialog.setSize(400, 200);
    dialog.setLocation(1000, 300);
    dialog.setVisible(true);
  }

  /**
   * Creates the file path text field with placeholder text and focus behaviour.
   * {@return the configured text field.}
   */
  private JTextField makePlaceholderField() {
    JTextField field = new JTextField(DEFAULT_PATH, 25);
    field.setForeground(new Color(80, 77, 77, 210));
    field.setHorizontalAlignment(JTextField.CENTER);

    field.addFocusListener(new FocusAdapter() {
      /**
       * Invoked when a component gains the keyboard focus.
       * @param e the click in text field.
       */
      @Override
      public void focusGained(FocusEvent e) {
        if (field.getText().equalsIgnoreCase(DEFAULT_PATH) || field.getText().isBlank()) {
          field.setText("");
          field.setForeground(new Color(0, 0, 0));
          field.setHorizontalAlignment(JTextField.LEFT);
        }
      }

      /**
       * Invoked when a component loses the keyboard focus.
       * @param e the click release in text field.
       */
      @Override
      public void focusLost(FocusEvent e) {
        if (field.getText().isBlank() || field.getText().equalsIgnoreCase(DEFAULT_PATH)) {
          field.setText(DEFAULT_PATH);
          field.setForeground(new Color(80, 77, 77, 210));
          field.setHorizontalAlignment(JTextField.CENTER);
        }
      }
    });

    return field;
  }
}