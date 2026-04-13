package view.panels.selector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 10
 * This class serves as the panel that displays all currently loaded images and allows
 * the user to select or delete one. It exposes a refresh method so the parent window
 * can update the list after any storage change.
 */
public class ImageSelectorPanel extends JPanel {

  private final IStorage storage;
  private final Runnable onDelete; // notifies the parent after an image is deleted

  private final DefaultListModel<String> listModel;
  private final JList<String> list;

  /**
   * Constructs the image selector panel.
   * @param storage the image storage model.
   * @param onDelete callback invoked after the selected image is deleted.
   */
  public ImageSelectorPanel(IStorage storage, Runnable onDelete) {
    this.storage = storage;
    this.onDelete = onDelete;

    listModel = new DefaultListModel<>();
    list = new JList<>(listModel);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    build();
  }

  /**
   * Builds and lays out all components of the selector panel.
   */
  private void build() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder("Select Image"));

    JScrollPane scroll = new JScrollPane(list);

    JButton deleteButton = new JButton("Delete Image");
    deleteButton.setToolTipText("Click to delete the selected image");
    deleteButton.addActionListener(this::handleDelete);

    JPanel deleteRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    deleteRow.add(deleteButton);

    add(scroll, BorderLayout.CENTER);
    add(deleteRow, BorderLayout.SOUTH);
  }

  /**
   * Handles the delete button press. Removes the selected image from storage and
   * notifies the parent via callback.
   * @param e the action event from the delete button.
   */
  private void handleDelete(ActionEvent e) {
    String selected = list.getSelectedValue();

    if (selected != null && storage.containsImage(selected)) {
      storage.removeImage(selected);
      refresh();
      onDelete.run();
    }
  }

  /**
   * Rebuilds the list from the current state of storage. Call this after any
   * operation that adds or removes an image.
   */
  public void refresh() {
    listModel.clear();
    Set<String> keys = storage.getImageMap().keySet();

    for (String key : keys) {
      listModel.addElement(key);
    }
  }

  /**
   * Registers a list selection listener so the parent window can react when the
   * user clicks a different image.
   * @param listener the listener to register.
   */
  public void addListSelectionListener(ListSelectionListener listener) {
    list.addListSelectionListener(listener);
  }

  /**
   * {@return the name of the currently selected image, or null if none is selected.}
   */
  public String getSelectedValue() {
    return list.getSelectedValue();
  }

  /**
   * Programmatically sets the selected image in the list.
   * @param name the image name to select.
   * @param shouldScroll true to scroll the list so the selection is visible.
   */
  public void setSelectedValue(String name, boolean shouldScroll) {
    list.setSelectedValue(name, shouldScroll);
  }

  /**
   * {@return the number of images currently in the list.}
   */
  public int getImageCount() {
    return listModel.getSize();
  }

  /**
   * {@return the image name at the given index in the list.}
   * @param index the position to read from.
   */
  public String getElementAt(int index) {
    return listModel.getElementAt(index);
  }
}