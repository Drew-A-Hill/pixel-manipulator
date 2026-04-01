
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import commands.CommandMap;
import commands.ICommands;
import commands.operations.DarkenCommand;
import storage.IStorage;
import storage.StorageImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class serves as the test suite for the operations commands. This only loads from jpeg and
 * ppm given that the loading strategy for jpeg, bmp, and png operate the same way, therefore there
 * is no need for redundant testing. All files are saved as jpeg for space-saving purposes. This
 * class also tests the storage class.
 */
public class TestOperationsCommands {
  private String imageFileDir;
  private Scanner scanner;
  private IStorage storage;
  private Map<String, ICommands> commandMap;

  @Before
  public void setup() {
    this.imageFileDir = "./src/ImageFiles/testImages/";
    this.storage = new StorageImpl();
  }

  /**
   * This is a helper method for loading image. This is used to reduce repeat code. 
   * Assertions occur in this helper method therefore all relevant test methods use assetNull 
   * for style check purposes.
   * @param loadImageFileName file path as a string.
   * @param imageName name to save the file as.
   */
  private void loadHelper(String loadImageFileName, String imageName) {
    String cmdDetails = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(cmdDetails);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();
    assertTrue(this.storage.containsImage(imageName));
  }

  /**
   * This is a helper method for performing the operation. This is used to reduce repeat code. 
   * Assertions occur in this helper method therefore all relevant test methods use assetNull 
   * for style check purposes.
   * @param cmd command being used.
   * @param cmdDetails instructions for the command.
   * @param imgName name of image to perform operation on.
   * @param newImgName new name for the new image.
   */
  private void commandHelper(String cmd, String cmdDetails, String imgName, String newImgName) {
    this.scanner = new Scanner(cmdDetails);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get(cmd).runCommand();
    
    assertTrue(this.storage.containsImage(newImgName));
    assertFalse(this.storage.containsImage(imgName));
  }

  /**
   * This is a helper method for saving image. This is used to reduce repeat code. 
   * Assertions occur in this helper method therefore all relevant test methods use assetNull 
   * for style check purposes.
   * @param saveImageFileName file path to save image to.
   * @param newImageName name of image to be saved.
   */
  private void saveHelper(String saveImageFileName, String newImageName) {
    String cmdDetails = this.imageFileDir + saveImageFileName + " " + newImageName;
    this.scanner = new Scanner(cmdDetails);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(newImageName));
  }

  /**
   * Tests constructor for commands. All command classes are inherit the constructor from the
   * {@link commands.CommandImpl} class which implements the {@link ICommands} therefore only one
   * constructor is tested.
   */
  @Test
  public void testConstructor() {
    this.scanner = new Scanner("");
    ICommands commandsImpl = new DarkenCommand(this.scanner, this.storage);
    assertNotNull(commandsImpl);
  }

  /**
   * Tests constructor for commands using invalid arguments. All command classes are inherit the
   * constructor from the {@link commands.CommandImpl} class which implements the {@link ICommands}
   * therefore only one constructor is tested.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorFalse() {
    this.scanner = null;
    ICommands commandsImpl = new DarkenCommand(this.scanner, this.storage);
    assertNotNull(commandsImpl);
  }

  /**
   * Tests brighten commands for a brightened value of 100 while loading ppm and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testBrightenCommandPPM() {
    String loadImageFileName = "myImage.ppm";
    String imageName = "myImage";
    String newImageName = "brightened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "brighten";
    String commandDetails = 100 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);
  
    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests brighten commands for a brightened value of 100 while loading jpeg and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testBrightenCommandJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "brightened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "brighten";
    String commandDetails = 100 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests brighten commands for a brightened zero value. Image is
   * loaded as jpeg and then saved as jpeg for space purposes.
   */
  @Test
  public void testBrightenCommandZeroBrighten() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "brightened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "brighten";
    String commandDetails = 0 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests brighten commands for a brightened value of max value to test clamp. Image is
   * loaded as jpeg and then saved as jpeg for space purposes.
   */
  @Test
  public void testBrightenCommandMaxBrighten() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "max-brightened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "brighten";
    String commandDetails = 255 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests brighten commands for a brightened that exceeds max value to test clamp. Image is
   * loaded as jpeg and then saved as jpeg for space purposes.
   */
  @Test
  public void testBrightenCommandExceedMaxBrighten() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "max-brightened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "brighten";
    String commandDetails = 500 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests brighten commands for a negative brightened value. Image is loaded as jpeg and then
   * saved as jpeg for space purposes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBrightenCommandNegativeBrighten() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "brightened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "brighten";
    String commandDetails = -100 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);
  }

  /**
   * Tests brighten commands for a small negative decimal brightened value. Image is loaded as
   * jpeg and then saved as jpeg for space purposes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBrightenCommandSmallNegativeDecimalBrighten() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "brightened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "brighten";
    String commandDetails = -.01 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);
  }

  /**
   * Tests brighten commands for a small decimal brightened value. Image is loaded as
   * jpeg and then saved as jpeg for space purposes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBrightenCommandSmallDecimalBrighten() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "brightened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "brighten";
    String commandDetails = .01 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);
  }

  /**
   * Tests brighten commands for a large decimal brightened value. Image is loaded as
   * jpeg and then saved as jpeg for space purposes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBrightenCommandLargeDecimalBrighten() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "brightened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "brighten";
    String commandDetails = 100.01 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);
  }

  /**
   * Tests darken commands for a darkened value of 100 while loading ppm and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testDarkenCommandPPM() {
    String loadImageFileName = "myImage.ppm";
    String imageName = "myImage";
    String newImageName = "darkened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "darken";
    String commandDetails = 100 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests darken commands for a darkened value of 100 while loading jpeg and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testDarkenCommandJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "darkened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "darken";
    String commandDetails = 100 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests darken commands for a darkened zero value. Image is
   * loaded as jpeg and then saved as jpeg for space purposes.
   */
  @Test
  public void testDarkenCommandZeroDarken() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "darkened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "darken";
    String commandDetails = 0 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests darken commands for a darkened value of max value to test clamp. Image is
   * loaded as jpeg and then saved as jpeg for space purposes.
   */
  @Test
  public void testDarkenCommandMaxDarken() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "max-darkened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "darken";
    String commandDetails = 255 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests darken commands for a darkened value that exceeds max value to test clamp. Image is
   * loaded as jpeg and then saved as jpeg for space purposes.
   */
  @Test
  public void testDarkenCommandExceedMaxDarken() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "max-darkened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "darken";
    String commandDetails = 500 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests darken commands for a darkened negative value. Image is
   * loaded as jpeg and then saved as jpeg for space purposes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDarkenCommandNegativeDarken() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "darkened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "darken";
    String commandDetails = -5 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);
  }

  /**
   * Tests darken commands for a darkened small decimal value. Image is
   * loaded as jpeg and then saved as jpeg for space purposes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDarkenCommandSmallDecimalDarken() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "darkened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "darken";
    String commandDetails = .001 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);
  }

  /**
   * Tests darken commands for a darkened small negative decimal value. Image is
   * loaded as jpeg and then saved as jpeg for space purposes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDarkenCommandSmallNegativeDecimalDarken() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "darkened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "darken";
    String commandDetails = -.01 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);
  }

  /**
   * Tests darken commands for a darkened large decimal value. Image is
   * loaded as jpeg and then saved as jpeg for space purposes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDarkenCommandLargeDecimalDarken() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "darkened-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "darken";
    String commandDetails = 100.001 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);
  }

  /**
   * Tests red grey scale command while loading ppm and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testRedGreyScaleCommandPPM() {
    String loadImageFileName = "myImage.ppm";
    String imageName = "myImage";
    String newImageName = "red-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "red";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests green grey scale command while loading ppm and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testGreenGreyScaleCommandPPM() {
    String loadImageFileName = "myImage.ppm";
    String imageName = "myImage";
    String newImageName = "green-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "green";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests blue grey scale command while loading ppm and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testBlueGreyScaleCommandPPM() {
    String loadImageFileName = "myImage.ppm";
    String imageName = "myImage";
    String newImageName = "blue-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "blue";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests luma grey scale command while loading ppm and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testLumaGreyScaleCommandPPM() {
    String loadImageFileName = "myImage.ppm";
    String imageName = "myImage";
    String newImageName = "luma-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "luma";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests value grey scale command while loading ppm and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testValueGreyScaleCommandPPM() {
    String loadImageFileName = "myImage.ppm";
    String imageName = "myImage";
    String newImageName = "value-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "value";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests intensity grey scale command while loading ppm and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testIntensityGreyScaleCommandPPM() {
    String loadImageFileName = "myImage.ppm";
    String imageName = "myImage";
    String newImageName = "intensity-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "intensity";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests multiple operation commands while loading ppm and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testMultiOperationCommandPPM() {
    String loadImageFileName = "myImage.ppm";
    String imageName = "myImage";
    String newImageName = "value-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    //Operation 1

    // Sets details and performs command action for test
    String command = "value";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    //operation 2
    imageName = newImageName;
    newImageName = "blue-grey-scale-image";

    // Sets details and performs command action for test
    command = "blue";
    commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    //operation 3

    imageName = newImageName;
    newImageName = "luma-grey-scale-image";

    // Sets details and performs command action for test
    command = "luma";
    commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    //operation 4

    imageName = newImageName;
    newImageName = "darken-image";

    // Sets details and performs command action for test
    command = "darken";
    commandDetails = 100 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests that a storage object can be constructed.
   */
  @Test
  public void testStorageConstructor() {
    IStorage s = new StorageImpl();
    assertNotNull(s);
  }

  /**
   * Tests multiple loads and operation commands and the storage class while loading each from ppm
   * and saving as jpeg. Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testMultiLoadAndOperationCommandPPM() {
    // Image Details

    // Image 1

    String loadImageFileName1 = "myImage.ppm";
    String imageName1 = "myImage1";
    String saveName1 = "myImage";
    String saveImageFileName1 = saveName1 + ".jpeg";

    // Image 2

    String loadImageFileName2 = "myImage.ppm";
    String imageName2 = "myImage2";
    String saveName2 = "myImage";
    String saveImageFileName2 = saveName2 + ".jpeg";

    // Image 3

    String loadImageFileName3 = "myImage.ppm";
    String imageName3 = "myImage3";
    String saveName3 = "myImage";
    String saveImageFileName3 = saveName3 + ".jpeg";

    // Checks storage starts at size 0

    assertEquals(0, this.storage.getImageMap().size());

    // Load Image 1

    // Performs load action for test 
    loadHelper(loadImageFileName1, imageName1);
    
    // Checks that there is only one element in the storage
    assertFalse(this.storage.containsImage(imageName2));
    assertFalse(this.storage.containsImage(imageName3));
    assertEquals(1, this.storage.getImageMap().size());

    // Load Image 2

    // Performs load action for test 
    loadHelper(loadImageFileName2, imageName2);

    // Checks that there is only two element in the storage
    assertFalse(this.storage.containsImage(imageName3));
    assertEquals(2, this.storage.getImageMap().size());

    // Load Image 3

    // Performs load action for test 
    loadHelper(loadImageFileName3, imageName3);

    // Checks that there is three element in the storage
    assertEquals(3, this.storage.getImageMap().size());

    // Save Image 1

    // Performs save action for test
    saveHelper(saveImageFileName1, imageName1);

    // Checks that there is two element in the storage
    assertFalse(this.storage.containsImage(imageName1));
    assertEquals(2, this.storage.getImageMap().size());

    // Save image 2

    // Performs save action for test
    saveHelper(saveImageFileName2, imageName2);

    // Checks that there is one element in the storage
    assertFalse(this.storage.containsImage(imageName2));
    assertEquals(1, this.storage.getImageMap().size());

    // Save image 3

    // Performs save action for test
    saveHelper(saveImageFileName3, imageName3);

    // Checks that there is zero element in the storage
    assertFalse(this.storage.containsImage(imageName3));
    assertEquals(0, this.storage.getImageMap().size());

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName1);
  }

  /**
   * Tests red grey scale command while loading jpeg and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testRedGreyScaleCommandJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "red-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "red";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests green grey scale command while loading jpeg and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testGreenGreyScaleCommandJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "green-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "green";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests blue grey scale command while loading ppm and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testBlueGreyScaleCommandJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "blue-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "blue";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests luma grey scale command while loading jpeg and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testLumaGreyScaleCommandJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "luma-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "luma";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests value grey scale command while loading jpeg and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testValueGreyScaleCommandJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "value-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "value";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests intensity grey scale command while loading jpeg and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testIntensityGreyScaleCommandJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "intensity-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    // Sets details and performs command action for test
    String command = "intensity";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests multiple operation commands while loading jpeg and saving as jpeg.
   * Image was loaded and then saved as jpeg for space purposes.
   */
  @Test
  public void testMultiOperationCommandJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "value-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    //Operation 1

    // Sets details and performs command action for test
    String command = "value";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    //operation 2
    imageName = newImageName;
    newImageName = "blue-grey-scale-image";

    // Sets details and performs command action for test
    command = "blue";
    commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);
    assertFalse(this.storage.containsImage(imageName));

    //operation 3

    imageName = newImageName;
    newImageName = "luma-grey-scale-image";

    // Sets details and performs command action for test
    command = "luma";
    commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);
    assertFalse(this.storage.containsImage(imageName));

    //operation 4

    imageName = newImageName;
    newImageName = "darken-image";

    command = "darken";
    commandDetails = 100 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);
    assertFalse(this.storage.containsImage(imageName));

    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);

    // For style checker purposes. Assertion for test occurs in helper methods.
    assertNotNull(imageName);
  }

  /**
   * Tests for fail due to no image exiting in storage when trying multiple operation commands
   * while loading jpeg and saving as jpeg. Image was loaded and then saved as jpeg for space
   * purposes.
   */
  @Test(expected = NoSuchElementException.class)
  public void testMultiOperationCommandNoImage() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "value-grey-scale-image";
    String saveImageFileName = newImageName + ".jpeg";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    //Operation 1

    // Sets details and performs command action for test
    String command = "value";
    String commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    //operation 2
    imageName = "";
    newImageName = "blue-grey-scale-image";

    // Sets details and performs command action for test
    command = "blue";
    commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    assertFalse(this.storage.containsImage(imageName));

    //operation 3

    imageName = newImageName;
    newImageName = "luma-grey-scale-image";

    // Sets details and performs command action for test
    command = "luma";
    commandDetails = imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    assertFalse(this.storage.containsImage(imageName));


    //operation 4

    imageName = newImageName;
    newImageName = "darken-image";

    // Sets details and performs command action for test
    command = "darken";
    commandDetails = 100 + " " + imageName + " " + newImageName;
    commandHelper(command, commandDetails, imageName, newImageName);

    assertFalse(this.storage.containsImage(imageName));


    // Performs save action for test
    saveHelper(saveImageFileName, newImageName);
  }

  /**
   * Tests invalid commands after loading file.
   */
  @Test(expected = NullPointerException.class)
  public void testInvalidCommands() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";
    String newImageName = "value-grey-scale-image";

    // Performs load action for test 
    loadHelper(loadImageFileName, imageName);

    assertTrue(this.storage.containsImage(imageName));

    String commandDetails = imageName + " " + newImageName;
    String command = "valu-component";
    commandHelper(command, commandDetails, imageName, newImageName);
    command = "bl-component";
    commandHelper(command, commandDetails, imageName, newImageName);
    command = "r-component";
    commandHelper(command, commandDetails, imageName, newImageName);
    command = "red-c";
    commandHelper(command, commandDetails, imageName, newImageName);
  }
}