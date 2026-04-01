package model.operations;

import model.image.IImage;
import model.image.Pixel;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class sets the abstract for the collection of grey scale operations.
 */
public abstract class GreyScaleAbstract extends AbstractOperations {

  /**
   * Empty constructor used to instantiate an operation.
   */
  public GreyScaleAbstract() {
    super();
  }

  /**
   * Determines the new rgb value that will be used.
   * @param r the red value of the pixel
   * @param g the green value of the pixel
   * @param b the blue value of the pixel
   * {@return returns the desired rgb value to be set}
   */
  protected abstract int getNewRGB(int r, int g, int b);

  /**
   * Serves as the logic behind the grey scale operations.
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

    int newRGB = this.getNewRGB(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    Pixel updatedPixel = new Pixel(newRGB, newRGB, newRGB);
    image.setPixel(row, col, updatedPixel);
  }
}
