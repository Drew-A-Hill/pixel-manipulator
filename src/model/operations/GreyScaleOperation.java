package model.operations;

import model.image.IImage;
import model.image.Pixel;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class sets the logic for the rgb grey scale operation.
 */
public class GreyScaleOperation extends AbstractOperations {
  private final String greyScaleType;

  /**
   * Constructs a rgb greyscale operation based on the r,g or b.
   * @param greyScaleType the desired grey scale type
   */
  public GreyScaleOperation(String greyScaleType) {
    super();
    this.greyScaleType = greyScaleType;
  }


  /**
   * Serves as the logic behind the rgb grey scale operation. This creates a grey scale based on
   * the r, g, or b value of the pixels.
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
    int newRGB;

    if (this.greyScaleType.equalsIgnoreCase("red")) {
      newRGB = pixel.getRed();
    } else if (this.greyScaleType.equalsIgnoreCase("green")) {
      newRGB = pixel.getGreen();
    } else if (this.greyScaleType.equalsIgnoreCase("blue")) {
      newRGB = pixel.getBlue();
    } else {
      throw new IllegalArgumentException("Must select Red, Green, or Blue");
    }

    Pixel updatedpPixel = new Pixel(newRGB, newRGB, newRGB);

    image.setPixel(row, col, updatedpPixel);
  }
}
