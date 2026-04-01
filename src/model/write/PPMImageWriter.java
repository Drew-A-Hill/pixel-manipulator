package model.write;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.image.IImage;
import model.image.Pixel;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment
 * This class serves as the implementation for the ppm writer.
 */
public class PPMImageWriter implements IImageWriter {

  /**
   * Builds the head  of the ppm file.
   * @param height height of the file
   * @param width width of the file
   * @param maxValue largest value in the file
   * {@return a list of strings containing the head of the file}
   */
  private List<String> buildFileStarter(int height, int width, int maxValue) {
    List<String> fileData = new ArrayList<>();
    fileData.add("P3");
    fileData.add(width + " " + height);
    fileData.add(String.valueOf(maxValue));
    return fileData;
  }

  /**
   * Builds each pixel into a string form.
   * @param pixel the pixels to be converted to string form.
   * {@return the pixels in string form}
   */
  private String fileBuildHelper(Pixel pixel) {
    return pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue();
  }

  /**
   * Builds the file that will be written.
   * @param image the image to be written
   * {@return the data that will be written to the file in a List of strings form}
   */
  private List<String> fileBuilder(IImage image) {
    List<String> data = buildFileStarter(image.getHeight(), image.getWidth(), image.getMaxValue());
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        Pixel pixel = image.getPixel(row, col);
        String pixelString = fileBuildHelper(pixel);
        data.add(pixelString);
      }
    }
    return data;
  }

  /**
   * Writes the IImage object to the desired extension type.
   *
   * @param image IImage: the image in pixel integer form
   */
  @Override
  public void writeImage(IImage image, String filename) throws IOException {
    List<String> fileData = this.fileBuilder(image);
    try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filename))) {
      for (String line : fileData) {
        fileWriter.write(line);
        fileWriter.newLine();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Could Not Write File");
    }
  }
}
