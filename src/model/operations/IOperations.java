package model.operations;

import model.image.IImage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class serves as the interface for all operations classes.
 */
public interface IOperations {

  /**
   * Applies the Operation to the immage.
   * @param image the image being modified
   * {@return a new image}
   */
  IImage apply(IImage image);
}
