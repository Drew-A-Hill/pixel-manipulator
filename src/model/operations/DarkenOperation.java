package model.operations;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class sets the logic for the darken operation.
 */
public class DarkenOperation extends BrightenOperation {

  /**
   * Constructs a darken operation that modifies the image provided by the value. The constructor
   * sets the value field to a negative value to darken the image.
   * @param value the amount to brighten the image by
   */
  public DarkenOperation(int value) {
    super(- value);
  }
}
