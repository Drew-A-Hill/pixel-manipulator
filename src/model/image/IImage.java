package model.image;

import java.awt.image.BufferedImage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class serves as the interface for all image classes.
 */
public interface IImage {

  /**
   * Sets the pixels of the image to the appropriate values. If the value of any pixels
   * rgb values are above 255, or below 0 the value is clamped at the respective max or min.
   * @param row row location of the pixel
   * @param col col location of the pixel
   * @param pixel updated pixel
   */
  void setPixel(int row, int col, Pixel pixel);

  /**
   * Retrieves a pixel at a specific coordinate.
   * @param row row location of the pixel
   * @param col col location of the pixel
   * {@return the pixel at a location}
   */
  Pixel getPixel(int row, int col);

  /**
   * Retrieves the height of the image.
   * {@return the height of the image}
   */
  int getHeight();

  /**
   * Retrieves the width of the image.
   * {@return the width of the image}
   */
  int getWidth();

  /**
   * Retrieves the max value of the image.
   * {@return the max value of the image}
   */
  int getMaxValue();

  /**
   * Converts IImage to a buffered image for GUI display.
   * {@return buffered image}
   */
  BufferedImage convertToBufferedImage();
}
