package view.panels.display;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class DisplayImage {
  /**
   * Displays the image selected in the image preview panel.
   */
  private void displayImage() {
    this.selectedImage = this.listOfImages.getSelectedValue();
    if (this.selectedImage != null && this.storage.containsImage(this.selectedImage)) {
      BufferedImage image = this.storage.retrieveImage(selectedImage).convertToBufferedImage();

      imagePreviewLabel.setIcon(
              new ImageIcon(image.getScaledInstance(700, -1, Image.SCALE_SMOOTH)));

      this.createHistogram();
      this.histogramPanel.repaint();
    }
  }

}
