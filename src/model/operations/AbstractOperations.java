package model.operations;

import model.image.IImage;
import model.image.ImageImpl;
import model.image.Pixel;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class serves as the abstract class for all operations being performed on the image.
 */
public abstract class AbstractOperations implements IOperations {

  /**
   * Serves as the logic behind the different operations.
   * @param image the image being modified
   * @param pixel a pixel in the image being modified
   * @param row the row of the pixel
   * @param col the col of the pixel
   */
  protected abstract void strategy(IImage image, Pixel pixel, int row, int col);

  /**
   * Applies the Operation to the immage.
   * @param image the image being modified
   * {@return a new image}
   */
  @Override
  public IImage apply(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Invalid Image");
    }

    int height = image.getHeight();
    int width = image.getWidth();
    int maxValue = image.getMaxValue();

    IImage alteredImage = new ImageImpl(height, width, maxValue);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel pixel = image.getPixel(row, col);
        strategy(alteredImage, pixel, row, col);
      }
    }
    return alteredImage;
  }
}
