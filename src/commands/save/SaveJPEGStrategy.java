package commands.save;

import model.write.IImageWriter;
import model.write.JPEGImageWriter;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class loads provide the JPEG save strategy to the {@link SaveCommand}.
 */
public class SaveJPEGStrategy {

  /**
   * Provide the image writer used for saving jpeg image files.
   * {@return a image writer}
   */
  public IImageWriter loadStrategy() {
    return new JPEGImageWriter();
  }
}
