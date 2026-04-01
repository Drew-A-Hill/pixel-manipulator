package model.image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class serves as the implementation for the pixel class.
 */
public class Pixel implements IPixel {
  private final List<Integer> pixel;

  /**
   * Constructs an instance of a new pixel.
   * @param red the red value of the pixel.
   * @param green the green value of the pixel.
   * @param blue the blue value of the pixel.
   */
  public Pixel(int red , int green, int blue) {
    this.pixel = new ArrayList<>(Arrays.asList(clamp(red), clamp(green), clamp(blue)));
  }

  /**
   * Clamps the pixel values to a min of 0 and max of 255.
   * @param rgbValue the rgb value to be clamped
   * {@return the rgb value after clamping}
   */
  private int clamp(int rgbValue) {
    return Math.max(0, Math.min(255, rgbValue));
  }

  /**
   * Retrieves the value for the red pixel.
   * {@return the value for the red pixel.}
   */
  public int getRed() {
    return this.pixel.get(0);
  }

  /**
   * Retrieves the value for the green pixel.
   * {@return the value for the green pixel.}
   */
  public int getGreen() {
    return this.pixel.get(1);
  }

  /**
   * Retrieves the value for the blue pixel.
   * {@return the value for the blue pixel.}
   */
  public int getBlue() {
    return this.pixel.get(2);
  }
}
