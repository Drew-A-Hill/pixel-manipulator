
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Scanner;

import commands.CommandMap;
import commands.ICommands;
import commands.load.LoadCommand;
import commands.save.SaveCommand;
import storage.IStorage;
import storage.StorageImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class serves as the test suite for the load and save commands. This tests loading from
 * each image file type and saving to each image file type with no operations performed.
 */
public class TestLoadAndSaveCommands {
  private String imageFileDir;
  private String commands;
  private Scanner scanner;
  private IStorage storage;
  private Map<String, ICommands> commandMap;

  @Before
  public void setup() {
    this.imageFileDir = "./src/ImageFiles/testImages/";
    this.storage = new StorageImpl();
  }

  /**
   * Tests constructor for load command.
   */
  @Test
  public void testLoadConstructor() {
    this.scanner = new Scanner("");
    ICommands commandsImpl = new LoadCommand(this.scanner, this.storage);
    assertNotNull(commandsImpl);
  }

  /**
   * Tests constructor with illegal arguments for load command.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLoadConstructorFalse() {
    this.scanner = null;
    ICommands commandsImpl = new LoadCommand(this.scanner, this.storage);
    assertNotNull(commandsImpl);
  }

  /**
   * Tests constructor for save command.
   */
  @Test
  public void testSaveConstructor() {
    this.scanner = new Scanner("");
    ICommands commandsImpl = new SaveCommand(this.scanner, this.storage);
    assertNotNull(commandsImpl);
  }

  /**
   * Tests constructor with illegal arguments for save command.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSaveConstructorFalse() {
    this.scanner = null;
    ICommands commandsImpl = new SaveCommand(this.scanner, this.storage);
    assertNotNull(commandsImpl);
  }

  /**
   * Tests the load and save commands by loading ppm and saving as ppm.
   */
  @Test
  public void testLoadPPMSavePPM() {
    String loadImageFileName = "myImage.ppm";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading ppm and saving as jpeg.
   */
  @Test
  public void testLoadPPMSaveJPEG() {
    String loadImageFileName = "myImage.ppm";
    String saveImageFileName = "myImage.jpeg";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading ppm and saving as png.
   */
  @Test
  public void testLoadPPMSavePNG() {
    String loadImageFileName = "myImage.ppm";
    String saveImageFileName = "myImage.png";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading ppm and saving as bmp.
   */
  @Test
  public void testLoadPPMSaveBNP() {
    String loadImageFileName = "myImage.ppm";
    String saveImageFileName = "myImage.bmp";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading jpeg and saving as ppm.
   */
  @Test
  public void testLoadJPEGSavePPM() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "myImage.ppm";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading jpeg and saving as jpeg.
   */
  @Test
  public void testLoadJPEGSaveJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "myImage.jpeg";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading jpeg and saving as png.
   */
  @Test
  public void testLoadJPEGSavePNG() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "myImage.png";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading jpeg and saving as bmp.
   */
  @Test
  public void testLoadJPEGSaveBMP() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "myImage.bmp";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading png and saving as ppm.
   */
  @Test
  public void testLoadPNGSavePPM() {
    String loadImageFileName = "myImage.png";
    String saveImageFileName = "myImage.ppm";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading png and saving as jpeg.
   */
  @Test
  public void testLoadPNGSaveJPEG() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "myImage.png";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading png and saving as png.
   */
  @Test
  public void testLoadPNGSavePNG() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "myImage.png";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading png and saving as bmp.
   */
  @Test
  public void testLoadPNGSaveBMP() {
    String loadImageFileName = "myImage.png";
    String saveImageFileName = "myImage.bmp";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading bmp and saving as ppm.
   */
  @Test
  public void testLoadBMPSavePPM() {
    String loadImageFileName = "myImage.bmp";
    String saveImageFileName = "myImage.ppm";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading bmp and saving as jpeg.
   */
  @Test
  public void testLoadBMPSaveJPEG() {
    String loadImageFileName = "myImage.bmp";
    String saveImageFileName = "myImage.png";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading bmp and saving as png.
   */
  @Test
  public void testLoadBMPSavePNG() {
    String loadImageFileName = "myImage.bmp";
    String saveImageFileName = "myImage.png";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands by loading bmp and saving as bmp.
   */
  @Test
  public void testLoadBMPSaveBMP() {
    String loadImageFileName = "myImage.bmp";
    String saveImageFileName = "myImage.bmp";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests the load and save commands ability to take upper case file path names by loading JPEG
   * and saving as jpeg instead of JPEG.
   */
  @Test
  public void testLoadSaveUpperCaseExtensionJPEG() {
    String loadImageFileName = "myImage.JPEG";
    String saveImageFileName = "myImage.JPEG";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests a failed load command for ppm when providing no extension.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedLoadPPMNoExtension() {
    String loadImageFileName = "myImage";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();
  }

  /**
   * Tests a failed load command for ppm when providing invalid extension.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedLoadPPMInvalidExtension() {
    String loadImageFileName = "myImage.pm";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();
  }

  /**
   * Tests a failed load command for ppm when providing non-existent file path.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedLoadPPMNoFile() {
    String loadImageFileName = "image.ppm";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();
  }

  /**
   * Tests a failed load command for jpeg when providing no extension.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedLoadJPEGNoExtension() {
    String loadImageFileName = "myImage";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();
  }

  /**
   * Tests a failed load command for jpeg when providing invalid extension.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedLoadJPEGInvalidExtension() {
    String loadImageFileName = "myImage.jp";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();
  }

  /**
   * Tests a failed load command for jpeg when providing non-existent file path.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedLoadJPEGNoFile() {
    String loadImageFileName = "image.jpeg";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();
  }

  /**
   * Tests a failed save command for ppm when providing invalid extension.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSavePPMInvalidSaveExtension() {
    String loadImageFileName = "myImage.ppm";
    String saveImageFileName = "myImage.pm";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests a failed save command for ppm when providing no extension.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSavePPMNoSaveExtension() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "myImage";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests a failed save command for ppm when providing no file name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSavePPMNoSaveFileName() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests a failed save command for ppm when providing no file was loaded.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSavePPMNoLoad() {
    String saveImageFileName = "myImage.ppm";
    String imageName = "myImage";

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests a failed save command for ppm when there is no image by that name in storage.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSavePPMNoImage() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "myImage.ppm";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + "image";
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests a failed save command for jpeg when providing invalid extension.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSaveJPEGInvalidSaveExtension() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "myImage.jg";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests a failed save command for jpeg when providing no extension.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSaveJPEGNoSaveExtension() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "myImage";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests a failed save command for jpeg when providing no file name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSaveJPEGNoSaveFileName() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests a failed save command for jpeg when providing no file was loaded.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSaveJPEGNoLoad() {
    String saveImageFileName = "";
    String imageName = "myImage";

    this.commands = this.imageFileDir + saveImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests a failed save command for jpeg when there is no image by that name in storage.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailedSaveJPEGNoImage() {
    String loadImageFileName = "myImage.jpeg";
    String saveImageFileName = "";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("load").runCommand();

    assertTrue(this.storage.containsImage(imageName));

    this.commands = this.imageFileDir + saveImageFileName + " " + "image";
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("save").runCommand();

    assertFalse(this.storage.containsImage(imageName));
  }

  /**
   * Tests for correct exception for bad load command input.
   */
  @Test(expected = NullPointerException.class)
  public void testBadLoadCommand() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("lod").runCommand();
  }

  /**
   * Tests for correct exception for bad load command input due to case.
   */
  @Test(expected = NullPointerException.class)
  public void testBadLoadCommandCase() {
    String loadImageFileName = "myImage.jpeg";
    String imageName = "myImage";

    this.commands = this.imageFileDir + loadImageFileName + " " + imageName;
    this.scanner = new Scanner(this.commands);
    this.commandMap = new CommandMap(scanner, storage).getCmdMap();
    this.commandMap.get("Load").runCommand();
  }
}