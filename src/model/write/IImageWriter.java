package model.write;

import java.io.IOException;
import model.image.IImage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class serves as the interface for all image writer classes.
 */
public interface IImageWriter {

  /**
   * Writes the IImage object to the desired extension type.
   * @param image IImage: the image in pixel integer form
   */
  void writeImage(IImage image, String filename) throws IOException;
}
