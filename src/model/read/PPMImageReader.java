package model.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.image.IImage;
import model.image.ImageImpl;
import model.image.Pixel;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class serves as the implementation for the ppm image reader.
 */
public class PPMImageReader implements IImageReader {

  /**
   * Reads the image input and returns an image.
   * {@return IImage 2d array of pixels}
   */
  @Override
  public IImage readImage(String filePath) throws FileNotFoundException {
    File file = new File(filePath);

    Scanner scanner;
    try {
      scanner = new Scanner(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + filePath + " not found!");
    }

    // Read the file, stripping comments and blank lines
    StringBuilder sb = new StringBuilder();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (line.isEmpty() || line.startsWith("#")) {
        continue; // skip blank or comment lines
      }
      sb.append(line).append(System.lineSeparator());
    }
    scanner.close();

    // Re-scan the cleaned content
    scanner = new Scanner(sb.toString());

    // Verify header
    String token = scanner.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: must start with P3");
    }

    int width = scanner.nextInt();
    int height = scanner.nextInt();
    int maxValue = scanner.nextInt();

    ImageImpl image = new ImageImpl(height, width, maxValue);

    // Read pixel data (row-major order)
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (!scanner.hasNextInt()) {
          throw new IllegalStateException("Unexpected end of PPM pixel data");
        }
        int r = scanner.nextInt();
        int g = scanner.nextInt();
        int b = scanner.nextInt();
        image.setPixel(row, col, new Pixel(r, g, b));
      }
    }

    scanner.close();
    return image;
  }
}
