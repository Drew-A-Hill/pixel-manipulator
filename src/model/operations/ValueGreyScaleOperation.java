package model.operations;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class sets the logic for the value operation.
 */
public class ValueGreyScaleOperation extends GreyScaleAbstract {

  /**
   * Empty constructor used to instantiate an operation.
   */
  public ValueGreyScaleOperation() {
    super();
  }

  /**
   * Determines the new rgb value that will be used.
   * @param r the red value of the pixel
   * @param g the green value of the pixel
   * @param b the blue value of the pixel
   * {@return returns the desired rgb value to be set}
   */
  @Override
  protected int getNewRGB(int r, int g, int b) {
    return Math.max(r, Math.max(g, b));
  }
}
