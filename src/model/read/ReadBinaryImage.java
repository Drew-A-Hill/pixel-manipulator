package model.read;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.image.IImage;
import model.image.ImageImpl;
import model.image.Pixel;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class serves as the implementation for the binary image reader.
 */
public class ReadBinaryImage implements IImageReader {

  /**
   * Reads the binary image input and returns an image object.
   * {@return IImage 2d array of pixels}
   */
  @Override
  public IImage readImage(String filePath) throws FileNotFoundException {
    BufferedImage bi = null;

    try {
      bi = ImageIO.read(new File(filePath));
    } catch (IOException e) {
      throw new FileNotFoundException("File can not be found");
    }

    int height = bi.getHeight();
    int width = bi.getWidth();

    IImage image = new ImageImpl(height, width);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int buffedPixel = bi.getRGB(col, row);
        int red = buffedPixel >> 16 & 0xFF;
        int green = buffedPixel >> 8 & 0xFF;
        int blue = buffedPixel & 0xFF;

        Pixel pixel = new Pixel(red, green, blue);

        image.setPixel(row, col, pixel);
      }
    }
    return  image;
  }
}
