package model.write;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.image.IImage;
import model.image.Pixel;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class serves as the implementation for the writer when writing binary image files.
 */
public abstract class WriteBinaryImage implements IImageWriter {

  /**
   * Converts from rgb values for each pixel to a binary value.
   * @param pixel pixel whose values are being converted.
   * {@return the binary value of the pixel}
   */
  private int convertValue(Pixel pixel) {
    int red = pixel.getRed();
    int green = pixel.getGreen();
    int blue = pixel.getBlue();

    return (red << 16 | green << 8 | blue);
  }

  /**
   * Reformats the image with binary pixel values.
   * @param image being saved as a binary image file
   * {@return an image in the format standard for saving binary images}
   */
  private BufferedImage bufferedImageConverter(IImage image) {
    BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        bufferedImage.setRGB(col, row, this.convertValue(image.getPixel(row, col)));
      }
    }
    return bufferedImage;
  }

  /**
   * The extension used to write the file.
   * {@return the extension as a string.}
   */
  protected abstract String extension();

  /**
   * Writes the IImage object to the desired extension type.
   *
   * @param image IImage: the image in pixel integer form
   */
  @Override
  public void writeImage(IImage image, String filename) throws IOException {
    BufferedImage bufferedImage = bufferedImageConverter(image);
    File outFile = new File(filename);

    boolean fileWriten = ImageIO.write(bufferedImage, this.extension(), outFile);

    if (!fileWriten) {
      throw new IllegalArgumentException("File failed to save!");
    }
  }
}
