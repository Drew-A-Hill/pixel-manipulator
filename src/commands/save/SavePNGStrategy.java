package commands.save;

import model.write.IImageWriter;
import model.write.PNGWriter;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class loads provide the PNG save strategy to the {@link SaveCommand}.
 */
public class SavePNGStrategy {

  /**
   * Provide the image writer used for saving png image files.
   * {@return a image writer}
   */
  public IImageWriter loadStrategy() {
    return new PNGWriter();
  }
}
