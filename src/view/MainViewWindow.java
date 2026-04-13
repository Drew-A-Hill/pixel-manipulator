package view;

import javax.swing.BoxLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import storage.IStorage;
import view.panels.display.DisplayPanel;
import view.panels.load.LoadPanel;
import view.panels.operations.OperationsPanel;
import view.panels.save.SavePanel;
import view.panels.selector.ImageSelectorPanel;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 10
 * This class serves as the main window. It assembles all panels and wires their
 * callbacks together. It contains no panel-building logic of its own.
 */
public class MainViewWindow extends JFrame implements IView, ListSelectionListener {

  private static final int WINDOW_HEIGHT = 1000;
  private static final int WINDOW_WIDTH  = 2000;

  private final IStorage storage;

  private ImageSelectorPanel selectorPanel;
  private DisplayPanel displayPanel;
  private String selectedImage;

  /**
   * Constructs the view for use of the GUI.
   * @param storage storage model where images get stored.
   */
  public MainViewWindow(IStorage storage) {
    super("Image Editor");
    this.storage = storage;
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    buildLayout();
    setVisible(true);
  }

  /**
   * Makes the window visible. Called by the controller after construction.
   */
  @Override
  public void renderView() {
    setVisible(true);
  }

  /**
   * Creates the main panel for the GUI. This panel assembles all sub-panels into a
   * split layout with controls on the left and the image preview on the right.
   */
  private void buildLayout() {
    JPanel left = new JPanel();
    left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

    // image selector — owns the list and delete button
    selectorPanel = new ImageSelectorPanel(storage, this::onDelete);
    selectorPanel.addListSelectionListener(this);

    // load panel — notifies this window when a new image is ready
    LoadPanel loadPanel = new LoadPanel(
            storage,
            this::onImageListChanged,
            name -> {
              selectedImage = name;
              selectorPanel.setSelectedValue(name, true);
            });

    // operations panel — reads selectedImage via supplier, notifies on apply
    OperationsPanel operationsPanel = new OperationsPanel(
            storage,
            () -> selectedImage,
            this::onImageListChanged);

    // save panel — reads selectedImage via supplier, notifies on save
    SavePanel savePanel = new SavePanel(
            storage,
            () -> selectedImage,
            this::onImageListChanged);

    left.add(selectorPanel);
    left.add(loadPanel);
    left.add(operationsPanel);
    left.add(savePanel);

    // display panel — right side, owns the preview label and histogram
    displayPanel = new DisplayPanel();

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, displayPanel);
    splitPane.setDividerLocation(500);
    this.add(splitPane);
  }

  // ── Cross-panel callbacks ─────────────────────────────────────────────

  /**
   * Refreshes the image list and redraws the display after any storage change.
   * Used as the onLoad, onApply, and onSave callback for the child panels.
   */
  private void onImageListChanged() {
    selectorPanel.refresh();
    refreshDisplay();
  }

  /**
   * Handles image deletion from the selector panel. Selects the next available
   * image or clears the display if the list is now empty.
   */
  private void onDelete() {
    selectorPanel.refresh();

    if (selectorPanel.getImageCount() > 0) {
      // auto-select the first remaining image after deletion
      selectedImage = selectorPanel.getElementAt(0);
      selectorPanel.setSelectedValue(selectedImage, true);
      refreshDisplay();
    } else {
      selectedImage = null;
      displayPanel.clear();
    }
  }

  /**
   * Redraws the preview panel with the currently selected image if it exists in storage.
   */
  private void refreshDisplay() {
    if (selectedImage != null && storage.containsImage(selectedImage)) {
      displayPanel.show(storage.retrieveImage(selectedImage));
    }
  }

  /**
   * Called whenever the value of the selection changes in the image list.
   * @param e the event that characterizes the change.
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      selectedImage = selectorPanel.getSelectedValue();
      refreshDisplay();
    }
  }
}