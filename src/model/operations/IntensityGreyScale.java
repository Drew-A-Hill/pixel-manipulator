package model.operations;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class sets the logic for the intensity operation.
 */
public class IntensityGreyScale extends GreyScaleAbstract {

  /**
   * Empty constructor used to instantiate an operation.
   */
  public IntensityGreyScale() {
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
    return (r + g + b) / 3;
  }
}
