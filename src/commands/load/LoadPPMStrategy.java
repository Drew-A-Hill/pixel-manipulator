package commands.load;

import model.read.IImageReader;
import model.read.PPMImageReader;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class loads provide the PPM Load strategy to the {@link LoadCommand}.
 */
public class LoadPPMStrategy {

  /**
   * Provide the image reader used for loading ppm image files.
   * {@return a image reader}
   */
  public IImageReader imageReader() {
    return new PPMImageReader();
  }
}
