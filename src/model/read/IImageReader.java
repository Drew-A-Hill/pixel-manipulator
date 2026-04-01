package model.read;

import model.image.IImage;
import java.io.FileNotFoundException;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class serves as the interface for all image reader classes.
 */
public interface IImageReader {

  /**
   * Reads the image input and returns an image.
   * {@return IImage 2d array of pixels}
   */
  IImage readImage(String filePath) throws FileNotFoundException;
}
