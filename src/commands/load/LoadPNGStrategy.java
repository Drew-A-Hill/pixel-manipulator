package commands.load;

import model.read.IImageReader;
import model.read.ReadBinaryImage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class loads provide the PNG Load strategy to the {@link LoadCommand}.
 */
public class LoadPNGStrategy {

  /**
   * Provide the image reader used for loading png image files.
   * {@return a image reader}
   */
  public IImageReader imageReader() {
    return new ReadBinaryImage();
  }
}
