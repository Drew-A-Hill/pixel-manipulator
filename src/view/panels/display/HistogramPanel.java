package view.panels.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.image.IImage;
import model.image.Pixel;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 10
 * This class serves as the histogram panel. It computes and paints the RGB frequency
 * chart for whichever image is currently selected.
 */
public class HistogramPanel extends JPanel {

  private int[] red;
  private int[] green;
  private int[] blue;

  /**
   * Constructs the histogram panel with a fixed preferred size and border.
   */
  public HistogramPanel() {
    setPreferredSize(new Dimension(800, 200));
    setBorder(BorderFactory.createTitledBorder("RGB Frequency"));
  }

  /**
   * Recomputes the red, green, and blue frequency arrays from the given image
   * and triggers a repaint.
   * @param image the image to build the histogram from.
   */
  public void update(IImage image) {
    this.red   = new int[256];
    this.green = new int[256];
    this.blue  = new int[256];

    int height = image.getHeight();
    int width  = image.getWidth();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel p = image.getPixel(i, j);
        red[p.getRed()]++;
        green[p.getGreen()]++;
        blue[p.getBlue()]++;
      }
    }

    repaint();
  }

  /**
   * Clears the histogram data and repaints, used when no image is selected.
   */
  public void clear() {
    this.red   = null;
    this.green = null;
    this.blue  = null;
    repaint();
  }

  /**
   * Sets and displays the graph containing the chart and the r, g, b values within the histogram
   * panel.
   * @param g graphic component being painted.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // nothing to draw if no image has been loaded yet
    if (red == null || green == null || blue == null) {
      return;
    }

    Graphics2D graphics2D = (Graphics2D) g;

    int panelWidth  = getWidth();
    int panelHeight = getHeight();

    int left   = 40;
    int right  = 20;
    int top    = 10;
    int bottom = 30;

    int xCord = Math.max(1, panelWidth  - left - right);
    int yCord = Math.max(1, panelHeight - top  - bottom);

    // draw axes
    graphics2D.setColor(new Color(0, 0, 0));
    graphics2D.drawLine(left, top + 10, left, top + yCord);
    graphics2D.drawLine(left, top + yCord, left + xCord + 10, top + yCord);
    graphics2D.drawString("0",   left - 15,         top + yCord + 14);
    graphics2D.drawString("255", left + xCord - 20, top + yCord + 14);

    // find the tallest bar to scale all channels relative to it
    int max = 0;
    for (int i = 0; i < 256; i++) {
      if (red[i]   > max) max = red[i];
      if (green[i] > max) max = green[i];
      if (blue[i]  > max) max = blue[i];
    }

    if (max <= 0) {
      return;
    }

    double relativeX = xCord / 255.0;
    double relativeY = yCord / (double) max;

    drawChannelLine(graphics2D, red,   new Color(255, 0,   0),   left, top, yCord, relativeX, relativeY);
    drawChannelLine(graphics2D, green, new Color(0,   255, 0),   left, top, yCord, relativeX, relativeY);
    drawChannelLine(graphics2D, blue,  new Color(0,   0,   255), left, top, yCord, relativeX, relativeY);
  }

  /**
   * Sets the point for each of the 0 - 255 values and draws lines between them for each of the
   * r, g, b channels.
   * @param graphics2D the graphic for the lines.
   * @param channel the color channel being drawn.
   * @param color color to paint the line.
   * @param left left bound.
   * @param top top bound.
   * @param yCord y location.
   * @param relativeX relative location of x.
   * @param relativeY relative location of y.
   */
  private void drawChannelLine(Graphics2D graphics2D, int[] channel, Color color,
                               int left, int top, int yCord,
                               double relativeX, double relativeY) {
    graphics2D.setStroke(new BasicStroke(2));
    graphics2D.setColor(color);

    int prevX = left;
    int prevY = top + yCord - (int) Math.round(channel[0] * relativeY) - 5;

    for (int i = 1; i < 256; i++) {
      int x = left + (int) Math.round(i * relativeX) + 10;
      int y = top  + yCord - (int) Math.round(channel[i] * relativeY) - 5;

      graphics2D.drawLine(prevX, prevY, x, y);
      prevX = x;
      prevY = y;
    }
  }
}