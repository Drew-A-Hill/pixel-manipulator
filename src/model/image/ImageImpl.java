package model.image;

import java.awt.image.BufferedImage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class serves as the implementation for the ImageImpl class.
 */
public class ImageImpl implements IImage {
  private Pixel[][] image;
  private final int height;
  private final int width;
  private final int maxValue;

  /**
   * Constructs an instance of a new image.
   * @param height the height of the image.
   * @param width the width of the image.
   * @param maxValue the max value of the image.
   */
  public ImageImpl(int height, int width, int maxValue) {
    this.image = new Pixel[height][width];
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
  }

  /**
   * Constructs an instance of a new image for binary images which don't contain a max value.
   * @param height the height of the image.
   * @param width the width of the image.
   */
  public ImageImpl(int height, int width) {
    this.image = new Pixel[height][width];
    this.height = height;
    this.width = width;
    this.maxValue = 0;
  }

  /**
   * Sets the pixels of the image to the appropriate values. If the value of any pixels
   * rgb values are above 255, or below 0 the value is clamped at the respective max or min.
   *
   * @param row int: row to update
   * @param col int: col to update
   */
  @Override
  public void setPixel(int row, int col , Pixel pixel) {
    this.image[row][col] = pixel;
  }

  /**
   * Retrieves a pixel at a specific coordinate.
   * @param row row coordinate
   * @param col column coordinate
   * {@return a list that contains the rgb value of the pixel}
   */
  public Pixel getPixel(int row, int col) {
    return this.image[row][col];
  }

  /**
   * Retrieves the height of the image.
   * {@return the height of the image}
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Retrieves the width of the image.
   * {@return the width of the image}
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Retrieves the max value of the image.
   * {@return the max value of the image}
   */
  public int getMaxValue() {
    return this.maxValue;
  }

  /**
   * Converts IImage to a buffered image for GUI display.
   * {@return buffered image}
   */
  public BufferedImage convertToBufferedImage() {
    BufferedImage bufferedImage =
            new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        Pixel pixel = this.getPixel(i, j);
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();

        int convertedRGB = (r << 16) | (g << 8) | b;

        bufferedImage.setRGB(i, j, convertedRGB);
      }
    }
    return bufferedImage;
  }
}
