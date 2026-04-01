package model.write;


/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class provides the png extension for the write type when called.
 */
public class PNGWriter extends WriteBinaryImage {

  /**
   * The extension used to write the file.
   * {@return the extension as a string.}
   */
  @Override
  protected String extension() {
    return "png";
  }
}
