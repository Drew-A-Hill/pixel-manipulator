package commands.load;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import commands.CommandImpl;
import model.image.IImage;
import model.read.IImageReader;
import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class acts as the load command and performs all the logic needed to load an image. This
 * class will load images from a folder and save the image in the storage object to be altered.
 */
public class LoadCommand extends CommandImpl {
  private final Map<String, IImageReader> loadMap;

  /**
   * Constructs a new instance of a load object that will be used to load the image files based on
   * extension.
   * @param scanner contains all the required arguments to be used when loading image file.
   * @param storage storage object that holds the images that have been loaded.
   */
  public LoadCommand(Scanner scanner, IStorage storage) {
    super(scanner, storage);
    this.loadMap = new HashMap<>();

    loadMap.put("ppm", new LoadPPMStrategy().imageReader());
    loadMap.put("jpeg", new LoadJPEGStrategy().imageReader());
    loadMap.put("bmp", new LoadBMPStrategy().imageReader());
    loadMap.put("png", new LoadPNGStrategy().imageReader());
  }

  /**
   * Contains the logic that determines which load strategy is used based on the file extension.
   * @param filePath the filepath.
   * {@return an image reader}
   */
  private IImageReader loadStrategy(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("Bad file path provided!\n");
    }

    String extension = filePath.substring(
            filePath.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);

    IImageReader imageReader = loadMap.get(extension);

    if (imageReader == null) {
      throw new IllegalArgumentException("Invalid File Extension!\n");
    }

    return imageReader;
  }

  /**
   * Loads the image and stores it in the storage object.
   */
  @Override
  public void runCommand() {
    String filepath = scanner.next();
    String imageName = scanner.next();

    IImageReader reader = loadStrategy(filepath);
    IImage readImage;

    try {
      readImage = reader.readImage(filepath);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found!\n");
    }

    if (!storage.containsImage(imageName)) {
      storage.addImage(readImage, imageName);
    } else {
      System.out.printf("%s already in use! Please select alternate image name.\n", imageName);
    }

    System.out.printf("Loading: %s as %s\n", filepath, imageName);
  }
}
