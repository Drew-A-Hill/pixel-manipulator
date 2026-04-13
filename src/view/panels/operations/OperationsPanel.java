package view.panels.operations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import storage.IStorage;
import view.actions.ActionRunner;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 10
 * This class serves as the operations panel. It houses the new file name input,
 * the brighten / darken sub-panel, and the grey scale sub-panel. It communicates
 * results back to the parent window via a callback after a successful operation.
 */
public class OperationsPanel extends JPanel {

  private static final String DEFAULT_NAME = " Enter New File Name";

  private final IStorage storage;
  private final Supplier<String> getSelectedImage; // reads selected image name from parent
  private final Runnable onApply;                  // called after a successful operation

  private JTextField newFileName;
  private JMenu brightenDarkenMenu;
  private JMenu greyScaleMenu;
  private JSpinner value;
  private String brightenDarkenCommand;
  private String greyScaleCommand;

  /**
   * Constructs the operations panel.
   * @param storage the image storage model.
   * @param getSelectedImage supplier that returns the currently selected image name.
   * @param onApply callback invoked after a successful operation to refresh the image list.
   */
  public OperationsPanel(IStorage storage,
                         Supplier<String> getSelectedImage,
                         Runnable onApply) {
    this.storage = storage;
    this.getSelectedImage = getSelectedImage;
    this.onApply = onApply;
    build();
  }

  /**
   * Builds and lays out the new file name field and both operation sub-panels.
   */
  private void build() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createTitledBorder("Operations"));

    add(setUpNewImageNamePanel());
    add(Box.createVerticalStrut(10));
    add(setUpBrightenDarkenPanel());
    add(Box.createVerticalStrut(10));
    add(setUpGreyScaleOperationsPanel());
  }

  // ── New File Name ─────────────────────────────────────────────────────

  /**
   * Creates a panel that holds the text field input for the new file name.
   * {@return the new file name panel.}
   */
  private JPanel setUpNewImageNamePanel() {
    JPanel newName = new JPanel();
    newName.setLayout(new BoxLayout(newName, BoxLayout.Y_AXIS));

    JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel fileNameLabel = new JLabel("File Name:  ");

    this.newFileName = new JTextField(DEFAULT_NAME, 20);
    this.newFileName.setHorizontalAlignment(JTextField.CENTER);
    this.newFileName.setForeground(new Color(80, 77, 77, 210));

    this.newFileName.addFocusListener(new FocusAdapter() {
      /**
       * Invoked when a component gains the keyboard focus.
       * @param e the click in text field.
       */
      @Override
      public void focusGained(FocusEvent e) {
        if (newFileName.getText().equalsIgnoreCase(DEFAULT_NAME)) {
          newFileName.setText("");
          newFileName.setHorizontalAlignment(JTextField.LEFT);
          newFileName.setForeground(new Color(0, 0, 0));
        }
      }

      /**
       * Invoked when a component loses the keyboard focus.
       * @param e the click release in text field.
       */
      @Override
      public void focusLost(FocusEvent e) {
        if (newFileName.getText().trim().equalsIgnoreCase("")
                || newFileName.getText().equalsIgnoreCase(DEFAULT_NAME)) {
          newFileName.setText(DEFAULT_NAME);
          newFileName.setForeground(new Color(80, 77, 77, 210));
          newFileName.setHorizontalAlignment(JTextField.CENTER);
        }
      }
    });

    nameRow.add(fileNameLabel);
    nameRow.add(this.newFileName);
    newName.add(nameRow);

    return newName;
  }

  // ── Brighten / Darken ─────────────────────────────────────────────────

  /**
   * Sets up and adds all needed elements of the brighten darken panel.
   * {@return the brighten darken panel.}
   */
  private JPanel setUpBrightenDarkenPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.CENTER));
    panel.setBorder(BorderFactory.createTitledBorder("Brighten / Darken"));

    panel.add(dropDownBrightenDarkenSelector());
    panel.add(valueIncrementer());
    panel.add(applyButton());

    return panel;
  }

  /**
   * Creates the panel for the brighten / darken menu. This holds the logic for the menu
   * drop down functionality.
   * {@return the brighten / darken menu panel.}
   */
  private JPanel dropDownBrightenDarkenSelector() {
    JPanel brightenDarkenRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JMenuBar jMenuBar = new JMenuBar();

    this.brightenDarkenMenu = new JMenu("Select Operation ▼");

    Dimension dimension = new Dimension(150, 20);
    JMenuItem brighten = new JMenuItem("Brighten");
    JMenuItem darken   = new JMenuItem("Darken");

    jMenuBar.setPreferredSize(dimension);
    brighten.setPreferredSize(dimension);
    darken.setPreferredSize(dimension);

    brighten.addActionListener(e -> updateDropdownCommand("Brighten  "));
    darken.addActionListener(  e -> updateDropdownCommand("Darken  "));

    this.brightenDarkenMenu.add(brighten);
    this.brightenDarkenMenu.add(darken);
    jMenuBar.add(this.brightenDarkenMenu);
    brightenDarkenRow.add(jMenuBar);

    return brightenDarkenRow;
  }

  /**
   * Allows for incrementation of the brighten / darken values.
   * {@return the incrementer panel.}
   */
  private JPanel valueIncrementer() {
    JPanel valRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel valLabel = new JLabel("Value: ");
    this.value = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));

    valRow.add(valLabel);
    valRow.add(this.value);

    return valRow;
  }

  /**
   * Creates the apply button for the brighten / darken panel. Holds the logic for calling the
   * brighten / darken command.
   * {@return the apply button.}
   */
  private JButton applyButton() {
    JButton apply = new JButton("Apply");
    apply.setToolTipText("Click to apply brighten/darken to image");

    apply.addActionListener((ActionEvent e) -> {
      String selected = getSelectedImage.get();
      String dest     = resolvedName();

      // guard: need a selected image and a chosen operation before running
      if (brightenDarkenCommand == null || selected == null) {
        errorMessage(operationsErrorString(selected));
        return;
      }
      if (storage.containsImage(dest)) {
        return;
      }

      StringBuilder instructions = new StringBuilder();
      instructions.append(value.getValue()).append(" ");
      instructions.append(selected).append(" ");
      instructions.append(dest);

      try {
        new ActionRunner().run(instructions.toString(), storage,
                brightenDarkenCommand.toLowerCase().trim());
        onApply.run();
        dropDownReset();
      } catch (Exception ex) {
        errorMessage(operationsErrorString(selected));
      }
    });

    return apply;
  }

  /**
   * Updates the menu bar to display the operation being used.
   * @param newCommand string representation of the operation being used.
   */
  private void updateDropdownCommand(String newCommand) {
    this.brightenDarkenCommand = newCommand;

    int commandSize = newCommand.length();
    StringBuilder sb = new StringBuilder();
    sb.append(newCommand);

    while (commandSize < 20) {
      sb.append(" ");
      commandSize++;
    }

    sb.append("▼");
    this.brightenDarkenMenu.setText(sb.toString());
  }

  // ── Grey Scale ────────────────────────────────────────────────────────

  /**
   * Sets up and adds all needed elements of the grey scale panel.
   * {@return the grey scale panel.}
   */
  private JPanel setUpGreyScaleOperationsPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.CENTER));
    panel.setBorder(BorderFactory.createTitledBorder("Grey Scale"));

    panel.add(dropDownGreyScaleSelector());
    panel.add(greyScaleApplyButton());

    return panel;
  }

  /**
   * Creates the panel for the grey scale menu. This holds the logic for the menu drop
   * down functionality.
   * {@return the grey scale menu panel.}
   */
  private JPanel dropDownGreyScaleSelector() {
    JPanel greyScaleRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JMenuBar jMenuBar = new JMenuBar();

    this.greyScaleMenu = new JMenu("Select Operation ▼");

    String[] greyScaleOps = {"Red", "Green", "Blue", "Value", "Intensity", "Luma"};

    for (String ops : greyScaleOps) {
      JMenuItem item = new JMenuItem(ops);
      item.addActionListener(e -> updateDropdownCommandGreyScale(e.getActionCommand()));
      this.greyScaleMenu.add(item);
    }

    jMenuBar.add(this.greyScaleMenu);
    greyScaleRow.add(jMenuBar);

    return greyScaleRow;
  }

  /**
   * Creates the apply button for the grey scale panel. Holds the logic for calling the
   * grey scale commands.
   * {@return the apply button.}
   */
  private JButton greyScaleApplyButton() {
    JButton apply = new JButton("Apply");
    apply.setToolTipText("Click to apply greyscale to the image");

    apply.addActionListener((ActionEvent e) -> {
      String selected = getSelectedImage.get();
      String dest     = resolvedName();

      // guard: need a selected image and a chosen operation before running
      if (greyScaleCommand == null || selected == null) {
        errorMessage(operationsErrorString(selected));
        return;
      }
      if (storage.containsImage(dest)) {
        return;
      }

      StringBuilder instructions = new StringBuilder();
      instructions.append(selected).append(" ");
      instructions.append(dest);

      try {
        new ActionRunner().run(instructions.toString(), storage,
                greyScaleCommand.toLowerCase());
        onApply.run();
        dropDownReset();
      } catch (Exception ex) {
        errorMessage(operationsErrorString(selected));
      }
    });

    return apply;
  }

  /**
   * Updates the menu bar to display the grey scale operation being used.
   * @param newCommand string representation of the operation being used.
   */
  private void updateDropdownCommandGreyScale(String newCommand) {
    this.greyScaleCommand = newCommand;

    int commandSize = newCommand.length();
    StringBuilder sb = new StringBuilder();
    sb.append(newCommand);

    while (commandSize < 20) {
      sb.append(" ");
      commandSize++;
    }

    sb.append("▼");
    this.greyScaleMenu.setText(sb.toString());
  }

  // ── Shared Helpers ────────────────────────────────────────────────────

  /**
   * Resets all dropdown menus and the new file name field to their original state.
   */
  public void dropDownReset() {
    if (this.brightenDarkenMenu != null) {
      updateDropdownCommand("Select Operation");
    }
    if (this.greyScaleMenu != null) {
      updateDropdownCommandGreyScale("Select Operation");
    }

    this.newFileName.setText(DEFAULT_NAME);
    this.newFileName.setForeground(new Color(80, 70, 70, 210));
    this.newFileName.setHorizontalAlignment(JTextField.CENTER);
    this.value.setValue(0);
  }

  /**
   * Reads the new file name field and returns its value, substituting a default
   * name if the placeholder text is still showing.
   * {@return the resolved destination file name.}
   */
  private String resolvedName() {
    String text = newFileName.getText();
    return text.equalsIgnoreCase(DEFAULT_NAME) ? "Unnamed File" : text;
  }

  /**
   * Creates a string that holds all the error messages for a particular operation.
   * @param selected the currently selected image name, or null if none.
   * {@return the string consisting of the error output.}
   */
  private String operationsErrorString(String selected) {
    StringBuilder errorSB = new StringBuilder();

    if (newFileName.getText().equalsIgnoreCase(DEFAULT_NAME)) {
      errorSB.append("Enter New File Name!!").append(System.lineSeparator());
    }
    if (selected == null) {
      errorSB.append("Select Image!!").append(System.lineSeparator());
    }

    errorSB.append("Select An Operation!!").append(System.lineSeparator());

    return errorSB.toString();
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
}