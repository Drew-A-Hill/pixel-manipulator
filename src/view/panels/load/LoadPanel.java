package view.panels.load;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.*;

import view.actions.ActionRunner;

public class LoadPanel {
  /**
   * Creates and sets up load panel and holds the logic for all related actions.
   */
  private void setUpLoadPanel() {

    String loadFileNameFieldPrompt = "Enter File Name";
    String loadFilePathFieldPrompt = "Enter File Path";

    JPanel load = new JPanel();
    load.setLayout(new BoxLayout(load, BoxLayout.Y_AXIS));
    load.setBorder(BorderFactory.createTitledBorder("Load Image"));
    Dimension panelDimension = new Dimension();
    panelDimension.setSize(this.windowWidth, this.windowHeight * .15);
    load.setPreferredSize(panelDimension);

    JPanel pathRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel filePathLabel = new JLabel("File Path:    ");
    this.filePathField = new JTextField(loadFilePathFieldPrompt, 30);
    this.filePathField.setForeground(new Color(80, 77, 77, 210));
    this.filePathField.setHorizontalAlignment(JTextField.CENTER);

    this.filePathField.addFocusListener(new FocusAdapter() {

      /**
       * Invoked when a component gains the keyboard focus.
       *
       * @param e the click in text field.
       */
      @Override
      public void focusGained(FocusEvent e) {
        filePathField.setText("");
        filePathField.setHorizontalAlignment(JTextField.LEFT);
        filePathField.setForeground(new Color(0,0,0));
      }

      /**
       * Invoked when a component loses the keyboard focus.
       *
       * @param e the click release in text field.
       */
      @Override
      public void focusLost(FocusEvent e) {
        if (filePathField.getText().equalsIgnoreCase(loadFilePathFieldPrompt)
                || filePathField.getText().equalsIgnoreCase("")) {

          filePathField.setText(loadFilePathFieldPrompt);
          filePathField.setForeground(new Color(80, 77, 77, 210));
          filePathField.setHorizontalAlignment(JTextField.CENTER);
        }
      }

    });

    pathRow.add(filePathLabel);
    pathRow.add(filePathField);

    JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel fileNameLabel = new JLabel("File Name:  ");
    this.fileNameField = new JTextField(loadFileNameFieldPrompt, 30);
    this.fileNameField.setForeground(new Color(80, 77, 77, 210));
    this.fileNameField.setHorizontalAlignment(JTextField.CENTER);

    this.fileNameField.addFocusListener(new FocusAdapter() {

      /**
       * Invoked when a component gains the keyboard focus.
       *
       * @param e the click in text field.
       */
      @Override
      public void focusGained(FocusEvent e) {
        fileNameField.setText("");
        fileNameField.setHorizontalAlignment(JTextField.LEFT);
        fileNameField.setForeground(new Color(0,0,0));


      }

      /**
       * Invoked when a component loses the keyboard focus.
       *
       * @param e the click release in text field.
       */
      @Override
      public void focusLost(FocusEvent e) {
        if (fileNameField.getText().equalsIgnoreCase(loadFileNameFieldPrompt)
                || fileNameField.getText().equalsIgnoreCase("")) {
          fileNameField.setText(loadFileNameFieldPrompt);
          fileNameField.setHorizontalAlignment(JTextField.CENTER);
          fileNameField.setForeground(new Color(80, 77, 77, 210));
        }
      }
    });

    nameRow.add(fileNameLabel);
    nameRow.add(fileNameField);

    JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT,380, 0));
    JButton loadButton = new JButton("Load");
    loadButton.setToolTipText("Click to Load Image");

    loadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        StringBuilder loadInstructions = new StringBuilder();
        String filePath = filePathField.getText().trim();
        String fileName = fileNameField.getText().trim();
        selectedImage = fileName;
        String command = e.getActionCommand().toLowerCase();
        loadInstructions.append(filePath);
        loadInstructions.append(" ");
        loadInstructions.append(fileName);

        if (!storage.containsImage(fileName)) {
          try {
            new ActionRunner().run(loadInstructions.toString().stripTrailing(), storage, command);
            refreshImageList(storage);

            displayImage();

            listOfImages.setSelectedValue(fileName, true);

            resetLoadPrompt();

          } catch (IllegalArgumentException ex) {
            errorMessage(loadErrorString());
          }
        }
        resetLoadPrompt();
      }
    });

    buttonRow.add(loadButton);
    load.add(pathRow);
    load.add(nameRow);
    load.add(buttonRow);

    this.actionPanel.add(load);
  }

  /**
   * Resets all prompts for text fields in the load panel.
   */
  private void resetLoadPrompt() {
    String loadFileNameFieldPrompt = "Enter File Name";
    String loadFilePathFieldPrompt = "Enter File Path";

    Color color = new Color(80,77,77, 210);

    this.filePathField.setText(loadFilePathFieldPrompt);
    this.filePathField.setHorizontalAlignment(JTextField.CENTER);
    this.filePathField.setForeground(color);

    this.fileNameField.setText(loadFileNameFieldPrompt);
    this.fileNameField.setHorizontalAlignment(JTextField.CENTER);
    this.fileNameField.setForeground(color);
  }
}
