package view.panels.load;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.function.Consumer;

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
 * This class serves as the self-contained load panel. It owns the file path and file name
 * input fields and communicates results back to the parent window via callbacks.
 */
public class LoadPanel extends JPanel {

  private static final String DEFAULT_PATH = "Enter File Path";
  private static final String DEFAULT_NAME = "Enter File Name";

  private final IStorage storage;
  private final Runnable onLoad;                  // called after a successful load
  private final Consumer<String> onImageSelected; // passes the loaded image name to the parent

  private JTextField filePathField;
  private JTextField fileNameField;

  /**
   * Constructs the load panel.
   * @param storage the image storage model.
   * @param onLoad callback invoked after a successful load to refresh the image list.
   * @param onImageSelected callback invoked with the new image name so the parent can select it.
   */
  public LoadPanel(IStorage storage, Runnable onLoad, Consumer<String> onImageSelected) {
    this.storage = storage;
    this.onLoad = onLoad;
    this.onImageSelected = onImageSelected;
    build();
  }

  /**
   * Builds and lays out all components of the load panel.
   */
  private void build() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createTitledBorder("Load Image"));

    filePathField = makePlaceholderField(DEFAULT_PATH, 30);
    fileNameField = makePlaceholderField(DEFAULT_NAME, 30);

    JPanel pathRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    pathRow.add(new JLabel("File Path:    "));
    pathRow.add(filePathField);

    JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    nameRow.add(new JLabel("File Name:  "));
    nameRow.add(fileNameField);

    JButton loadButton = new JButton("Load");
    loadButton.setToolTipText("Click to Load Image");
    loadButton.addActionListener(this::handleLoad);

    JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 380, 0));
    buttonRow.add(loadButton);

    add(pathRow);
    add(nameRow);
    add(buttonRow);
  }

  /**
   * Handles the load button press. Reads the path and name fields, runs the load command,
   * and notifies the parent via callbacks on success.
   * @param e the action event from the load button.
   */
  private void handleLoad(ActionEvent e) {
    String filePath = filePathField.getText().trim();
    String fileName = fileNameField.getText().trim();

    // do not load if the image name already exists in storage
    if (storage.containsImage(fileName)) {
      resetPrompts();
      return;
    }

    String instructions = filePath + " " + fileName;
    try {
      new ActionRunner().run(instructions.stripTrailing(), storage,
              e.getActionCommand().toLowerCase());
      onImageSelected.accept(fileName);
      onLoad.run();
    } catch (IllegalArgumentException ex) {
      errorMessage(loadErrorString(filePath, fileName));
    } finally {
      resetPrompts();
    }
  }

  /**
   * Resets all text field prompts in the load panel to their default placeholder text.
   */
  public void resetPrompts() {
    Color dim = new Color(80, 77, 77, 210);

    filePathField.setText(DEFAULT_PATH);
    filePathField.setForeground(dim);
    filePathField.setHorizontalAlignment(JTextField.CENTER);

    fileNameField.setText(DEFAULT_NAME);
    fileNameField.setForeground(dim);
    fileNameField.setHorizontalAlignment(JTextField.CENTER);
  }

  /**
   * Builds the error string shown when a load attempt fails.
   * @param path the file path currently in the field.
   * @param name the file name currently in the field.
   * {@return the error message string.}
   */
  private String loadErrorString(String path, String name) {
    StringBuilder sb = new StringBuilder();

    if (name.equalsIgnoreCase(DEFAULT_NAME) || name.isBlank()) {
      sb.append("Enter File Name!!").append(System.lineSeparator());
    }
    if (path.equalsIgnoreCase(DEFAULT_PATH) || path.isBlank()) {
      sb.append("Enter File Path!!").append(System.lineSeparator());
    } else {
      sb.append("Bad File Path");
    }

    return sb.toString();
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
   * Creates a text field with placeholder text and focus behaviour that clears on click
   * and restores the placeholder when left empty.
   * @param placeholder the default placeholder string.
   * @param cols the column width of the text field.
   * {@return the configured text field.}
   */
  private JTextField makePlaceholderField(String placeholder, int cols) {
    JTextField field = new JTextField(placeholder, cols);
    field.setForeground(new Color(80, 77, 77, 210));
    field.setHorizontalAlignment(JTextField.CENTER);

    field.addFocusListener(new FocusAdapter() {
      /**
       * Invoked when a component gains the keyboard focus.
       * @param e the click in text field.
       */
      @Override
      public void focusGained(FocusEvent e) {
        if (field.getText().equalsIgnoreCase(placeholder)) {
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
        if (field.getText().isBlank() || field.getText().equalsIgnoreCase(placeholder)) {
          field.setText(placeholder);
          field.setForeground(new Color(80, 77, 77, 210));
          field.setHorizontalAlignment(JTextField.CENTER);
        }
      }
    });

    return field;
  }
}