package commands.save;

import model.write.BMPImageWriter;
import model.write.IImageWriter;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class loads provide the BMP save strategy to the {@link SaveCommand}.
 */
public class SaveBMPStrategy {

  /**
   * Provide the image writer used for saving bmp image files.
   * {@return a image writer}
   */
  public IImageWriter loadStrategy() {
    return new BMPImageWriter();
  }
}
