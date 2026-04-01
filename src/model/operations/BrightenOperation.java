package model.operations;

import model.image.IImage;
import model.image.Pixel;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class sets the logic for the brighten operation.
 */
public class BrightenOperation extends AbstractOperations {
  private final int value;

  /**
   * Constructs a brighten operation that modifies the image provided by the value.
   * @param value the amount to brighten the image by
   */
  public BrightenOperation(int value) {
    this.value = value;
  }

  /**
   * Serves as the logic behind the brighten operation.
   * @param image the image being modified
   * @param pixel a pixel in the image being modified
   * @param row the row of the pixel
   * @param col the col of the pixel
   */
  @Override
  protected void strategy(IImage image, Pixel pixel, int row, int col) {
    if (image == null || pixel == null) {
      throw new IllegalArgumentException("Invalid Image or Pixel");
    }

    int newR = pixel.getRed() + this.value;
    int newG = pixel.getGreen() + this.value;
    int newB = pixel.getBlue() + this.value;

    Pixel updatedPixel = new Pixel(newR, newG, newB);

    image.setPixel(row, col, updatedPixel);
  }
}
