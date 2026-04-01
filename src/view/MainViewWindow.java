package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;
import javax.swing.Box;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import commands.ICommands;
import model.image.IImage;
import model.image.Pixel;
import storage.IStorage;
import view.actions.ActionRunner;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 10
 * This class serves as logic and setup for the GUI and all of its functionalities.
 */
public class MainViewWindow extends JFrame implements ActionListener,
        ListSelectionListener {
  private IStorage storage;
  private JPanel actionPanel;
  private String brightenDarkenCommand;
  private String greyScaleCommand;
  private final int windowHeight;
  private final int windowWidth;
  private JMenu brightenDarkenMenu;
  private JMenu greyScaleMenu;
  private JLabel imagePreviewLabel;
  private JPanel imagePanel;
  private JList<String> listOfImages;
  private Map<String, ICommands> cmdMap;
  private String newFileNameLabel;
  private String selectedImage;
  private DefaultListModel<String> images;
  private JSpinner value;
  private JTextField newFileName;
  private JTextField filePathField;
  private JTextField fileNameField;
  private JPanel histogramPanel;
  private int[] red;
  private int[] green;
  private int[] blue;

  /**
   * Constructs the view for use of the GUI.
   * @param storage storage model where images get stored.
   */
  public MainViewWindow(IStorage storage) {
    super();
    this.storage = storage;
    this.windowHeight = 1000;
    this.windowWidth = 2000;

    setTitle("Image Editor");
    setSize(this.windowWidth, this.windowHeight);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    createMainPanel();
  }

  /**
   * Creates the main panel for the GUI. This panel calls all other panels into it.
   */
  private void createMainPanel() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    this.actionPanel = new JPanel();
    this.actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
    mainPanel.add(actionPanel);

    setUpSelectImagePanel();
    setUpLoadPanel();
    setUpOperationsPanel();
    setUpSavePanel();
    setUpDisplayImagePanel();

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, imagePanel);
    splitPane.setDividerLocation(500);
    this.add(splitPane);

    setVisible(true);
  }

  /**
   * Refreshes the list of images being worked on as displayed in the select image panel.
   * @param storage the updated storage model.
   */
  private void refreshImageList(IStorage storage) {
    this.images.clear();
    Set<String> updatedImageSet = storage.getImageMap().keySet();

    for (String image : updatedImageSet) {
      this.images.addElement(image);
    }
  }

  /**
   * Resets all dropdown menus to original state.
   */
  private void dropDownReset() {
    if (this.brightenDarkenMenu != null) {
      updateDropdownCommand("Select Operation");
    }

    if (this.greyScaleMenu != null) {
      updateDropdownCommandGreyScale("Select Operation");
    }

    this.newFileName.setText(this.newFileNameLabel);
    this.newFileName.setForeground(new Color(80, 70, 70, 210));
    this.newFileName.setHorizontalAlignment(JTextField.CENTER);
    this.value.setValue(0);
  }

  /**
   * Updates the menu bar to display the operation being used.
   * @param newCommand String representation of operation being used.
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

  /**
   * Creates the panel for the brighten/darken menu. This holds the logic for the menu drop
   * down functionality.
   * {@return the brighten/darken menu panel}
   */
  private JPanel dropDownBrightenDarkenSelector() {
    JPanel brightenDarkenRow = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JMenuBar jMenuBar = new JMenuBar();

    this.brightenDarkenMenu = new JMenu("Select Operation ▼");

    Dimension dimension = new Dimension(150, 20);

    JMenuItem brighten = new JMenuItem("Brighten");
    JMenuItem darken = new JMenuItem("Darken");

    jMenuBar.setPreferredSize(dimension);
    brighten.setPreferredSize(dimension);
    darken.setPreferredSize(dimension);

    brighten.addActionListener(e -> updateDropdownCommand("Brighten  "));
    darken.addActionListener(e -> updateDropdownCommand("Darken  "));

    this.brightenDarkenMenu.add(brighten);
    this.brightenDarkenMenu.add(darken);


    jMenuBar.add(this.brightenDarkenMenu);

    brightenDarkenRow.add(jMenuBar);

    return brightenDarkenRow;
  }

  /**
   * Allows for incrementation of the brighten darken values.
   * {@return the incrementer panel}
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
   * {@return the apply button panel}
   */
  private JButton applyButton() {
    JButton apply = new JButton("Apply");
    apply.setToolTipText("Click to apply brighten/darken to image");

    apply.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

          StringBuilder brightenDarkenInstructions = new StringBuilder();
          brightenDarkenInstructions.append((int) value.getValue());
          brightenDarkenInstructions.append(" ");
          brightenDarkenInstructions.append(selectedImage);
          brightenDarkenInstructions.append(" ");
          if (newFileName.getText().equalsIgnoreCase(" Enter New File Name")) {
            brightenDarkenInstructions.append("Unnamed File");
          } else {
            brightenDarkenInstructions.append(newFileName.getText());
          }

          if (!storage.containsImage(newFileName.getText())) {

            try {
              new ActionRunner().run(brightenDarkenInstructions.toString(), storage,
                      brightenDarkenCommand.toLowerCase().trim());
              refreshImageList(storage);
              dropDownReset();
              displayImage();
              setSelectedImage();

            } catch (Exception ex) {

              errorMessage(operationsErrorString());
            }
          }
        }
    });

    return apply;
  }

  /**
   * Creates a popup error message with relevant information.
   * @param message message to be displayed in pop up error box.
   */
  private void errorMessage(String message) {
    JOptionPane error = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
    JDialog dialog = error.createDialog(error, "Error");

    dialog.setSize(400, 200);
    dialog.setLocation(1000, 300);

    dialog.setVisible(true);
  }

  /**
   * Creates a string that holds all the error messages for a particular operation.
   * {@return the string consisting of the error output}
   */
  private String operationsErrorString() {
    StringBuilder errorSB = new StringBuilder();

    if (newFileName.getText().equalsIgnoreCase(newFileNameLabel)) {
      errorSB.append("Enter New File Name!!");
      errorSB.append(System.lineSeparator());
    }

    if (selectedImage == null) {
      errorSB.append("Select Image!!");
      errorSB.append(System.lineSeparator());
    }
    if (listOfImages.getModel().getSize() == 0) {
      errorSB.append("You Must Load New Image!!");
      errorSB.append(System.lineSeparator());

    }
    errorSB.append("Select A Operation!!");
    errorSB.append(System.lineSeparator());

    return errorSB.toString();
  }

  /**
   * Sets up and adds all needed elements of the brighten darken panel.
   * {@return the brighten darken panel}
   */
  private JPanel setUpBrightenDarkenPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.CENTER));
    panel.setBorder(BorderFactory.createTitledBorder("Brighten / Darken"));

    JPanel dropDown = this.dropDownBrightenDarkenSelector();

    JPanel valueIterator = this.valueIncrementer();
    JButton applyButton = this.applyButton();

    panel.add(dropDown);
    panel.add(valueIterator);
    panel.add(applyButton);

    return panel;
  }

  /**
   * Updates the menu bar to display the grey scale operation being used.
   * @param newCommand String representation of operation being used.
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

  /**
   * Creates the panel for the grey scale menu. This holds the logic for the menu drop
   * down functionality.
   * {@return the grey scale menu panel}
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
   * {@return the apply button panel}
   */
  private JButton greyScaleApplyButton() {
    JButton apply = new JButton("Apply");

    String toolTipString = "Click to apply greyscale to the image";

    apply.setToolTipText(toolTipString);

    apply.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        StringBuilder greyScaleInstructions = new StringBuilder();
        greyScaleInstructions.append(selectedImage);
        greyScaleInstructions.append(" ");

        if (newFileName.getText().equalsIgnoreCase(" Enter New File Name")) {
          greyScaleInstructions.append("Unnamed File");
        } else {
          greyScaleInstructions.append(newFileName.getText());
        }

        if (!storage.containsImage(newFileName.getText())) {

          try {
            new ActionRunner().run(greyScaleInstructions.toString(), storage,
                    greyScaleCommand.toLowerCase());
            refreshImageList(storage);
            dropDownReset();
            displayImage();
            setSelectedImage();

          } catch (Exception ex) {
            errorMessage(operationsErrorString());
          }
        }
      }
    });

    return apply;
  }

  /**
   * Sets up and adds all needed elements of the grey scale panel.
   * {@return the grey scale panel}
   */
  private JPanel setUpGreyScaleOperationsPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.CENTER));
    panel.setBorder(BorderFactory.createTitledBorder("Grey Scale"));

    panel.add(this.dropDownGreyScaleSelector());
    panel.add(this.greyScaleApplyButton());

    return panel;
  }

  /**
   * Creates a panel that holds the text field input for the new file name.
   * {@return the new file name panel}
   */
  private JPanel setUpNewImageNamePanel() {
    this.newFileNameLabel = " Enter New File Name";

    JPanel newName = new JPanel();
    newName.setLayout(new BoxLayout(newName, BoxLayout.Y_AXIS));

    JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel fileNameLabel = new JLabel("File Name:  ");
    this.newFileName = new JTextField(this.newFileNameLabel, 20);
    this.newFileName.setHorizontalAlignment(JTextField.CENTER);
    this.newFileName.setForeground(new Color(80,77,77,210));

    this.newFileName.addFocusListener(new FocusAdapter() {
      /**
       * Invoked when a component gains the keyboard focus.
       *
       * @param e the click in text field.
       */
      @Override
      public void focusGained(FocusEvent e) {
        if (newFileName.getText().equalsIgnoreCase(newFileNameLabel)) {
          newFileName.setText("");
          newFileName.setHorizontalAlignment(JTextField.LEFT);
          newFileName.setForeground(new Color(0, 0, 0));
        }
      }

      /**
       * Invoked when a component loses the keyboard focus.
       *
       * @param e the click release in text field.
       */
      @Override
      public void focusLost(FocusEvent e) {
        if (newFileName.getText().trim().equalsIgnoreCase("")
                || newFileName.getText().equalsIgnoreCase(newFileNameLabel)) {

          newFileName.setText(newFileNameLabel);
          newFileName.setForeground(new Color(80,77,77,210));
          newFileName.setHorizontalAlignment(JTextField.CENTER);
        }
      }
    });

    nameRow.add(fileNameLabel);
    nameRow.add(this.newFileName);

    newName.add(nameRow);

    return newName;
  }

  /**
   * Sets up and adds all needed elements of the operations panel. This adds brighten/ darken,
   * grey scale and the new file name panels and their relevant apply buttons
   * {@return the brighten darken panel}
   */
  private void setUpOperationsPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createTitledBorder("Operations"));
    Dimension dimension = new Dimension();
    dimension.setSize(this.windowWidth,this.windowHeight * .25);
    panel.setPreferredSize(dimension);


    panel.add(this.setUpNewImageNamePanel());
    panel.add(Box.createVerticalStrut(10));
    panel.add(this.setUpBrightenDarkenPanel());
    panel.add(Box.createVerticalStrut(10));
    panel.add(this.setUpGreyScaleOperationsPanel());

    this.actionPanel.add(panel);
  }

  /**
   * Creates and sets up save panel and holds the logic for all related actions.
   */
  private void setUpSavePanel() {
    JPanel save = new JPanel();
    save.setLayout(new BoxLayout(save, BoxLayout.Y_AXIS));
    save.setBorder(BorderFactory.createTitledBorder("Save Image"));

    JPanel saveRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel filePathLabel = new JLabel("File Path:  ");
    JTextField filePathField = new JTextField("Enter File Path", 25);
    filePathField.setForeground(new Color(80, 77, 77, 210));
    filePathField.setHorizontalAlignment(JTextField.CENTER);

    filePathField.addFocusListener(new FocusAdapter() {
      /**
       * Invoked when a component gains the keyboard focus.
       *
       * @param e the click in text field.
       */
      @Override
      public void focusGained(FocusEvent e) {
        if (filePathField.getText().equalsIgnoreCase("")
                || filePathField.getText().equalsIgnoreCase("Enter File Path")) {
          filePathField.setText("");
          filePathField.setForeground(new Color(0,0,0));
          filePathField.setHorizontalAlignment(JTextField.LEFT);
        }
      }

      /**
       * Invoked when a component loses the keyboard focus.
       *
       * @param e the click release in text field.
       */
      @Override
      public void focusLost(FocusEvent e) {
        if (filePathField.getText().equalsIgnoreCase("")
                || filePathField.getText().equalsIgnoreCase("Enter File Path")) {
          filePathField.setText("Enter File Path");
          filePathField.setForeground(new Color(80, 77, 77, 210));
          filePathField.setHorizontalAlignment(JTextField.CENTER);
        }
      }

    });

    saveRow.add(filePathLabel);
    saveRow.add(filePathField);

    JButton saveButton = new JButton("save");
    saveButton.setToolTipText("Click to save Image");
    saveButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        StringBuilder saveInstructions = new StringBuilder();
        String filePath = filePathField.getText().trim();
        String fileName = selectedImage;
        String command = e.getActionCommand().toLowerCase();

        saveInstructions.append(filePath);
        saveInstructions.append(" ");
        saveInstructions.append(fileName);

        try {
          new ActionRunner().run(saveInstructions.toString(), storage, command);
          refreshImageList(storage);
          filePathField.setText("Enter File Path");
          filePathField.setForeground(new Color(80, 77, 77, 210));
          filePathField.setHorizontalAlignment(JTextField.CENTER);
          filePathField.setText("Enter File Path");

          if (storage.getImageMap().isEmpty()) {
            imagePreviewLabel.setIcon(null);
          }

        } catch (Exception ex) {
          errorMessage("Bad File Path");
        }
      }
    });

    saveRow.add(saveButton);

    save.add(saveRow);

    this.actionPanel.add(save);
  }

  /**
   * Creates the panel that displays the image to the user.
   */
  private void setUpDisplayImagePanel() {
    imagePreviewLabel = new JLabel("", JLabel.CENTER);
    JScrollPane scrollPane = new JScrollPane(imagePreviewLabel);
    scrollPane.setPreferredSize(new Dimension(800, 700));

    imagePanel = new JPanel(new BorderLayout());
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image Preview"));
    imagePanel.add(scrollPane, BorderLayout.CENTER);

    createHistogramPanel();
    imagePanel.add(histogramPanel, BorderLayout.SOUTH);
  }

  /**
   * Creates the panel for the histogram.
   */
  private void createHistogramPanel() {
    histogramPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        displayHistogram(g);
      }
    };

    histogramPanel.setPreferredSize(new Dimension(800, 200));
    histogramPanel.setBorder(BorderFactory.createTitledBorder("RGB Frequency"));
  }

  /**
   * Creates the array for the histogram by setting values for red, green, blue.
   */
  private void createHistogram() {
    if (this.selectedImage == null || !this.storage.containsImage(this.selectedImage)) {
      this.red = new int[256];
      this.green = new int[256];
      this.blue = new int [256];
      return;
    }
    IImage image = this.storage.retrieveImage(this.selectedImage);

    this.red = new int[256];
    this.green = new int[256];
    this.blue = new int [256];

    int height = image.getHeight();
    int width = image.getWidth();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel pixel = image.getPixel(i, j);
        red[pixel.getRed()]++;
        green[pixel.getGreen()]++;
        blue[pixel.getBlue()]++;
      }
    }
  }

  /**
   * Sets and displays the graph containing the chart and the r, g, b values within the histogram
   * panel.
   * @param g graphic component being painted
   */
  private void displayHistogram(Graphics g) {
    if (red == null || green == null || blue == null) {
      return;
    }

    Graphics2D graphics2D = (Graphics2D) g;

    int panelWidth = histogramPanel.getWidth();
    int panelHeight = histogramPanel.getHeight();

    int left = 40;
    int right = 20;
    int top = 10;
    int bottom = 30;

    int xCord = Math.max(1, panelWidth - left - right);
    int yCord = Math.max(1, panelHeight - top - bottom);

    graphics2D.setColor(new Color(0,0,0));
    graphics2D.drawLine(left, top + 10, left, top + yCord);
    graphics2D.drawLine(left, top + yCord, left + xCord + 10, top + yCord);
    graphics2D.drawString("0", left - 15, top + yCord + 14);
    graphics2D.drawString("255", left + xCord - 20, top + yCord + 14);

    int max = 0;
    for (int i = 0; i < 256; i++) {
      if (red[i] > max) {
        max = red[i];
      }
      if (green[i] > max) {
        max = green[i];
      }
      if (blue[i]  > max) {
        max = blue[i];
      }
    }

    if (max <= 0) {
      return;
    }

    double relativeX = xCord / 255.0;
    double relativeY = yCord / (double) max;

    drawChannelLine(graphics2D, red,   new Color(255,0,0),
            left, top, yCord, relativeX, relativeY);
    drawChannelLine(graphics2D, green, new Color(0,255,0),
            left, top, yCord, relativeX, relativeY);
    drawChannelLine(graphics2D, blue,  new Color(0,0,255),
            left, top, yCord, relativeX, relativeY);
  }

  /**
   * Sets the point for each of the 0 - 255 values and draws lines between them for each of the
   * r,g,b channels.
   * @param graphics2D the graphic for the lines.
   * @param channel the color chanel being drawn.
   * @param color color to paint the line.
   * @param left left bound.
   * @param top top bound.
   * @param yCord y location.
   * @param relativeX relative location of x.
   * @param relativeY relative location of y.
   */
  private void drawChannelLine(Graphics2D graphics2D, int[] channel, Color color,
                               int left, int top, int yCord, double relativeX, double relativeY) {

    graphics2D.setStroke(new BasicStroke(2));
    graphics2D.setColor(color);
    int prevX = left;
    int prevY = top + yCord - (int) Math.round(channel[0] * relativeY) - 5;

    for (int i = 1; i < 256; i++) {
      int x = left + (int) Math.round(i * relativeX) + 10;
      int y = top + yCord - (int) Math.round(channel[i] * relativeY) - 5;

      graphics2D.drawLine(prevX, prevY, x, y);
      prevX = x;
      prevY = y;
    }
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    // all actions performed currently exist in lamda form
  }

  /**
   * Called whenever the value of the selection changes.
   *
   * @param e the event that characterizes the change.
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      this.displayImage();
      this.listOfImages.setSelectedValue(this.selectedImage, true);

      this.createHistogram();
      this.histogramPanel.repaint();
    }
  }
}
