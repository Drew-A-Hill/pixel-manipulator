package commands.load;

import model.read.IImageReader;
import model.read.ReadBinaryImage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class loads provide the JPEG Load strategy to the {@link LoadCommand}.
 */
public class LoadJPEGStrategy {

  /**
   * Provide the image reader used for loading jpeg image files.
   * {@return a image reader}
   */
  public IImageReader imageReader() {
    return new ReadBinaryImage();
  }
}
