package view.panels.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.image.IImage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 10
 * This class serves as the right-hand display panel. It contains the image preview
 * and the histogram panel and exposes simple show / clear methods so the parent
 * window never needs to touch Swing components directly.
 */
public class DisplayPanel extends JPanel {

  private final JLabel previewLabel;
  private final HistogramPanel histogramPanel;

  /**
   * Constructs the display panel, sets up the image preview scroll pane, and
   * attaches the histogram below it.
   */
  public DisplayPanel() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder("Image Preview"));

    previewLabel = new JLabel("", JLabel.CENTER);

    JScrollPane scrollPane = new JScrollPane(previewLabel);
    scrollPane.setPreferredSize(new Dimension(800, 700));

    histogramPanel = new HistogramPanel();

    add(scrollPane,     BorderLayout.CENTER);
    add(histogramPanel, BorderLayout.SOUTH);
  }

  /**
   * Updates the image preview and histogram to reflect the given image.
   * @param image the image to display.
   */
  public void show(IImage image) {
    BufferedImage buffered = image.convertToBufferedImage();

    previewLabel.setIcon(
            new ImageIcon(buffered.getScaledInstance(700, -1, Image.SCALE_SMOOTH)));

    histogramPanel.update(image);
  }

  /**
   * Clears the image preview and histogram. Called when no image is selected.
   */
  public void clear() {
    previewLabel.setIcon(null);
    histogramPanel.clear();
  }
}