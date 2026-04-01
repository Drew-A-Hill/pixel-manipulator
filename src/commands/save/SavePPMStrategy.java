package commands.save;

import model.write.IImageWriter;
import model.write.PPMImageWriter;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class loads provide the PPM save strategy to the {@link SaveCommand}.
 */
public class SavePPMStrategy {

  /**
   * Provide the image writer used for saving ppm image files.
   * {@return a image writer}
   */
  public IImageWriter loadStrategy() {
    return new PPMImageWriter();
  }
}
