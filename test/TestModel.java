
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import commands.load.LoadJPEGStrategy;
import model.image.IImage;
import model.image.Pixel;
import model.operations.BrightenOperation;
import model.operations.DarkenOperation;
import model.operations.GreyScaleOperation;
import model.operations.IntensityGreyScale;
import model.operations.LumaOperation;
import model.operations.ValueGreyScaleOperation;
import model.read.PPMImageReader;
import model.read.ReadBinaryImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class serves as the test suite for the model. All the operations are tested for accuracy,
 * and the read model is tested for accuracy. The write model is not tested as it is tested
 * in the {@link TestOperationsCommands} test file.
 */
public class TestModel {
  private IImage image;

  @Before
  public void setup() {
    String imageFileDir = "./src/ImageFiles/testImages/myImage.jpeg";

    try {
      this.image = new LoadJPEGStrategy().imageReader().readImage(imageFileDir);
    } catch (FileNotFoundException e) {
      System.out.println("Setup Fail");
    }
  }

  /**
   * Tests brighten constructor.
   */
  @Test
  public void testBrightenConstructor() {
    BrightenOperation operation = new BrightenOperation(5);
    assertNotNull(operation);
  }

  /**
   * Tests darken constructor.
   */
  @Test
  public void testDarkenConstructor() {
    DarkenOperation operation = new DarkenOperation(5);
    assertNotNull(operation);
  }

  /**
   * Tests grey scale constructor.
   */
  @Test
  public void testGreyScaleConstructor() {
    GreyScaleOperation operation = new GreyScaleOperation("red");
    assertNotNull(operation);
  }

  /**
   * Tests value grey scale constructor.
   */
  @Test
  public void testValueGreyScaleConstructor() {
    ValueGreyScaleOperation operation = new ValueGreyScaleOperation();
    assertNotNull(operation);
  }

  /**
   * Tests luma grey scale constructor.
   */
  @Test
  public void testLumaGreyScaleConstructor() {
    LumaOperation operation = new LumaOperation();
    assertNotNull(operation);
  }

  /**
   * Tests luma grey scale constructor.
   */
  @Test
  public void testIntensityGreyScaleConstructor() {
    LumaOperation operation = new LumaOperation();
    assertNotNull(operation);
  }

  /** Helper method for adjusting color value.
   * @param red value to be adjusted.
   * @param value value to adjust by.
   * {@return adjusted value}
   */
  private int adjRedHelper(int red, int value) {
    //Adjusted values for non brightened image
    int adjRed;

    //Ensures clamping for control
    if (red + value > 255) {
      adjRed = 255;
    } else if (red + value < 0) {
      adjRed = 0;
    } else {
      adjRed = red + value;
    }
    return adjRed;
  }

  /** Helper method for adjusting color value.
   * @param green value to be adjusted.
   * @param value value to adjust by.
   * {@return adjusted value}
   */
  private int adjGreenHelper(int green, int value) {
    //Adjusted values for non brightened image
    int adjGreen;

    //Ensures clamping for control
    if (green + value > 255) {
      adjGreen = 255;
    } else if (green + value < 0) {
      adjGreen = 0;
    } else {
      adjGreen = green + value;
    }
    return adjGreen;
  }

  /** Helper method for adjusting color value.
   * @param blue value to be adjusted.
   * @param value value to adjust by.
   * {@return adjusted value}
   */
  private int adjBlueHelper(int blue, int value) {
    //Adjusted values for non brightened image
    int adjBlue;

    //Ensures clamping for control
    if (blue + value > 255) {
      adjBlue = 255;
    } else if (blue + value < 0) {
      adjBlue = 0;
    } else {
      adjBlue = blue + value;
    }
    return adjBlue;
  }

  /**
   * Helper method used in testing the pixels of the brightened and darkened images.
   * @param original the original image.
   * @param newImage altered image.
   * @param value amount to alter each pixel by.
   */
  private void pixelBrightDarkCompareTestHelper(IImage original, IImage newImage, int value) {
    for (int row = 0; row < original.getHeight(); row++) {
      for (int col = 0; col < original.getWidth(); col++) {
        //Pixel values after altered image
        int brightRed = newImage.getPixel(row, col).getRed();
        int brightGreen = newImage.getPixel(row, col).getGreen();
        int brightBlue = newImage.getPixel(row, col).getBlue();

        //Pixel values before altered image
        int red = original.getPixel(row, col).getRed();
        int green = original.getPixel(row, col).getGreen();
        int blue = original.getPixel(row, col).getBlue();

        //Adjusted values for non altered image
        int adjRed = adjRedHelper(red, value);
        int adjGreen = adjGreenHelper(green, value);
        int adjBlue = adjBlueHelper(blue, value);

        assertEquals(adjRed, brightRed);
        assertEquals(adjGreen, brightGreen);
        assertEquals(adjBlue, brightBlue);
      }
    }
  }

  /**
   * Helper method for testing the pixels of non brighten or darken images.
   * @param type type of operation.
   * @param pixel pixel.
   * {@return altered pixel value}
   */
  int getValue(String type, Pixel pixel) {
    int red = pixel.getRed();
    int green = pixel.getGreen();
    int blue = pixel.getBlue();

    if (type.equalsIgnoreCase("red")) {
      return red;
    }

    if (type.equalsIgnoreCase("green")) {
      return green;
    }

    if (type.equalsIgnoreCase("blue")) {
      return blue;
    }

    if (type.equalsIgnoreCase("luma")) {
      return Math.toIntExact(Math.round(0.2126 * red + 0.7152 * green + 0.0722 * blue));
    }

    if (type.equalsIgnoreCase("value")) {
      return Math.max(red, Math.max(green, blue));
    }

    if (type.equalsIgnoreCase("intensity")) {
      return (red + green + blue) / 3;
    }

    //Returns if none of the above are true
    return Integer.MAX_VALUE;
  }

  /**
   * Helper method used in testing the pixels of the image.
   * @param original the original image.
   * @param newImage altered image.
   * @param type type of operation.
   */
  private void pixelGeneralCompareTestHelper(IImage original, IImage newImage, String type) {
    for (int row = 0; row < original.getHeight(); row++) {
      for (int col = 0; col < original.getWidth(); col++) {
        //Pixel values after altered image
        int brightRed = newImage.getPixel(row, col).getRed();
        int brightGreen = newImage.getPixel(row, col).getGreen();
        int brightBlue = newImage.getPixel(row, col).getBlue();

        //Pixel values before altered image
        int red = original.getPixel(row, col).getRed();
        int green = original.getPixel(row, col).getGreen();
        int blue = original.getPixel(row, col).getBlue();

        //Adjusted values for non altered image
        int adjRed = getValue(type, this.image.getPixel(row, col));
        int adjGreen = getValue(type, this.image.getPixel(row, col));
        int adjBlue = getValue(type, this.image.getPixel(row, col));

        assertEquals(adjRed, brightRed);
        assertEquals(adjGreen, brightGreen);
        assertEquals(adjBlue, brightBlue);
      }
    }
  }

  /**
   * This test the brightened operation by checking that the pixels have the correct value after
   * operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testBrighten() {
    int value = 120;
    IImage operation = new BrightenOperation(value).apply(this.image);

    pixelBrightDarkCompareTestHelper(this.image, operation, value);
    assertNotNull(operation);
  }

  /**
   * This test the brightened operation at its max pixel value by checking that the pixels have the
   * correct value after operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testBrightenMaxValue() {
    int value = 255;
    IImage operation = new BrightenOperation(value).apply(this.image);

    pixelBrightDarkCompareTestHelper(this.image, operation, value);
    assertNotNull(operation);
  }

  /**
   * This test the brightened operation when the value exceeds 255 by checking that the pixels have
   * the correct value after operation. This test uses helper methods to test for accurate
   * operation.
   */
  @Test
  public void testBrightenExceedsValue() {
    int value = 300;
    IImage operation = new BrightenOperation(value).apply(this.image);

    pixelBrightDarkCompareTestHelper(this.image, operation, value);
    assertNotNull(operation);
  }

  /**
   * This test the brightened operation for zero value by checking that the pixels have the
   * correct value after operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testBrightenZeroValue() {
    int value = 0;
    IImage operation = new BrightenOperation(value).apply(this.image);

    pixelBrightDarkCompareTestHelper(this.image, operation, value);
    assertNotNull(operation);
  }

  /**
   * This test the darken operation by checking that the pixels have the correct value after
   * operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testDarken() {
    int value = 120;
    IImage operation = new DarkenOperation(value).apply(this.image);

    pixelBrightDarkCompareTestHelper(this.image, operation, -value);
    assertNotNull(operation);
  }

  /**
   * This test the darken operation at its max pixel value by checking that the pixels have the
   * correct value after operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testDarkenMaxValue() {
    int value = 255;
    IImage operation = new DarkenOperation(value).apply(this.image);

    pixelBrightDarkCompareTestHelper(this.image, operation, -value);
    assertNotNull(operation);
  }

  /**
   * This test the darken operation when the value exceeds 255 by checking that the pixels have
   * the correct value after operation. This test uses helper methods to test for accurate
   * operation.
   */
  @Test
  public void testDarkenExceedsValue() {
    int value = 300;
    IImage operation = new DarkenOperation(value).apply(this.image);

    pixelBrightDarkCompareTestHelper(this.image, operation, -value);
    assertNotNull(operation);
  }

  /**
   * This test the darken operation for zero value by checking that the pixels have the
   * correct value after operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testDarkenZeroValue() {
    int value = 0;
    IImage operation = new DarkenOperation(value).apply(this.image);

    pixelBrightDarkCompareTestHelper(this.image, operation, -value);
    assertNotNull(operation);
  }

  /**
   * This test the red grey scale operation by checking that the pixels have the correct value
   * after operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testRedGreyScale() {
    String type = "red";

    IImage operation = new GreyScaleOperation(type).apply(this.image);

    pixelGeneralCompareTestHelper(this.image,operation, type);
    assertNotNull(operation);
  }

  /**
   * This test the green grey scale operation by checking that the pixels have the correct value
   * after operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testGreenGreyScale() {
    String type = "green";

    IImage operation = new GreyScaleOperation(type).apply(this.image);

    pixelGeneralCompareTestHelper(this.image,operation, type);
    assertNotNull(operation);
  }

  /**
   * This test the blue grey scale operation by checking that the pixels have the correct value
   * after operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testBlueGreyScale() {
    String type = "blue";

    IImage operation = new GreyScaleOperation(type).apply(this.image);

    pixelGeneralCompareTestHelper(this.image,operation, type);
    assertNotNull(operation);
  }

  /**
   * This test the luma grey scale operation by checking that the pixels have the correct value
   * after operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testLumaGreyScale() {
    String type = "luma";

    IImage operation = new LumaOperation().apply(this.image);

    pixelGeneralCompareTestHelper(this.image,operation, type);
    assertNotNull(operation);
  }

  /**
   * This test the value grey scale operation by checking that the pixels have the correct value
   * after operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testValueGreyScale() {
    String type = "value";

    IImage operation = new ValueGreyScaleOperation().apply(this.image);

    pixelGeneralCompareTestHelper(this.image,operation, type);
    assertNotNull(operation);
  }

  /**
   * This test the intensity grey scale operation by checking that the pixels have the correct
   * value after operation. This test uses helper methods to test for accurate operation.
   */
  @Test
  public void testIntensityGreyScale() {
    String type = "intensity";

    IImage operation = new IntensityGreyScale().apply(this.image);

    pixelGeneralCompareTestHelper(this.image,operation, type);
    assertNotNull(operation);
  }

  /**
   * Tests that the read  for ppm  read image model operates correctly.
   */
  @Test
  public void testReadPPM() {
    IImage testImage = null;
    String imageFileDir = "./src/ImageFiles/testImages/myImage.ppm";

    assertNull(testImage);

    try {
      testImage = new PPMImageReader().readImage(imageFileDir);
    } catch (FileNotFoundException e) {
      System.out.println("Setup Fail");
    }

    assertNotNull(testImage);
  }

  /**
   * Tests that the read for the binary read image model operates correctly.
   */
  @Test
  public void testReadBinary() {
    IImage testImage = null;
    String imageFileDir = "./src/ImageFiles/testImages/myImage.jpeg";

    assertNull(testImage);

    try {
      testImage = new ReadBinaryImage().readImage(imageFileDir);
    } catch (FileNotFoundException e) {
      System.out.println("Setup Fail");
    }

    assertNotNull(testImage);
  }

  /**
   * Tests that the read for ppm read image model operates correctly when provided bad path.
   */
  @Test
  public void testReadPPMFalse() {
    IImage testImage = null;
    String imageFileDir = "./src/ImageFiles/testImages/myImage.";

    assertNull(testImage);

    try {
      testImage = new PPMImageReader().readImage(imageFileDir);
    } catch (FileNotFoundException e) {
      assertNull(testImage);
    }

    assertNull(testImage);
  }

  /**
   * Tests that the read for binary read image model operates correctly when provided bad path.
   */
  @Test
  public void testReadBinaryFalse() {
    IImage testImage = null;
    String imageFileDir = "./src/ImageFiles/testImages/myImage.";

    assertNull(testImage);

    try {
      testImage = new ReadBinaryImage().readImage(imageFileDir);
    } catch (FileNotFoundException e) {
      assertNull(testImage);
    }
  }
}
