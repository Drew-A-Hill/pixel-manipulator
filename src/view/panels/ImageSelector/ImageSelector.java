package view.panels.ImageSelector;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.*;

public class ImageSelector {
  /**
   * Sets up panel that contains the list of images being worked on as well as the logic for
   * deleting image.
   */
  private void setUpSelectImagePanel() {
    this.images = new DefaultListModel<>();
    Set<String> imageSet = this.storage.getImageMap().keySet();

    for (String key : imageSet) {
      this.images.addElement(key);
    }

    this.listOfImages = new JList<>(images);
    this.listOfImages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.listOfImages.addListSelectionListener(this);

    JPanel imageSelectorPanel = new JPanel(new BorderLayout());
    Dimension panelDimension = new Dimension();
    panelDimension.setSize(this.windowWidth, this.windowHeight * .55);
    imageSelectorPanel.setPreferredSize(panelDimension);
    imageSelectorPanel.setBorder(BorderFactory.createTitledBorder("Select Image"));


    JScrollPane imageSelector = new JScrollPane(this.listOfImages);


    JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton deleteButton = new JButton("Delete Image");
    deleteButton.setToolTipText("Click to delete the selected image");
    deletePanel.add(deleteButton);
    deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Delete Image")) {
          if (storage.containsImage(selectedImage)) {
            storage.removeImage(selectedImage);
            refreshImageList(storage);

            if (listOfImages.getModel().getSize() > 0) {
              selectedImage = listOfImages.getModel().getElementAt(0);
              listOfImages.setSelectedValue(selectedImage, true);
            } else {
              imagePreviewLabel.setIcon(null);
            }
          }
        }
      }
    });

    imageSelectorPanel.add(imageSelector, BorderLayout.CENTER);
    imageSelectorPanel.add(deletePanel, BorderLayout.SOUTH);

    this.actionPanel.add(imageSelectorPanel);
  }

  /**
   * Sets the selected image for operations and save commands.
   */
  private void setSelectedImage() {
    if (listOfImages.getModel().getSize() > 0) {
      selectedImage = listOfImages.getModel().getElementAt(0);
      listOfImages.setSelectedValue(selectedImage, true);
    } else {
      imagePreviewLabel.setIcon(null);
    }
  }
}
