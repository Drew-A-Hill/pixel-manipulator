package commands.save;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import commands.CommandImpl;
import model.image.IImage;
import model.write.IImageWriter;
import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class acts as the save command and performs all the logic needed to save an image. Save the
 * desired image from storage to the specified directory with the given file name and extension.
 */
public class SaveCommand extends CommandImpl {
  private final Map<String, IImageWriter> saveMap;

  /**
   * Constructs a new instance of a save object that will be used to save the image files based on
   * extension.
   * @param scanner contains all the required arguments to be used when saving image files.
   * @param storage storage object that holds the images that have been loaded and altered.
   */
  public SaveCommand(Scanner scanner, IStorage storage) {
    super(scanner, storage);
    this.saveMap = new HashMap<>();

    this.saveMap.put("ppm", new SavePPMStrategy().loadStrategy());
    this.saveMap.put("jpeg", new SaveJPEGStrategy().loadStrategy());
    this.saveMap.put("png", new SavePNGStrategy().loadStrategy());
    this.saveMap.put("bmp", new SaveBMPStrategy().loadStrategy());
  }

  /**
   * Contains the logic that determines which save strategy is used based on the file extension.
   * @param filePath the filepath.
   * {@return an image reader}
   */
  private IImageWriter saveStrategy(String filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("Bad filepath!\n");
    }

    String extension = filePath.substring(
            filePath.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);

    IImageWriter iImageWriter = saveMap.get(extension);

    if (iImageWriter == null) {
      throw new IllegalArgumentException("Invalid extension!\n");
    }

    return iImageWriter;
  }

  /**
   * Saves desired image that is stored in the storage object based on desired filepath.
   */
  @Override
  public void runCommand() {
    String filepath = scanner.next();
    String imageName = scanner.next();

    IImageWriter write = this.saveStrategy(filepath);

    IImage image = storage.retrieveImage(imageName);

    if (image == null) {
      throw new IllegalArgumentException(
              String.format(storage.storedImages(imageName + " Not Found! Try: ")));
    }

    try {
      write.writeImage(image, filepath);
    } catch (IOException e) {
      throw new IllegalArgumentException(String.format(filepath + " Directory not found!\n"));
    }
    System.out.printf("Saving: %s to %s\n", imageName, filepath);
    storage.removeImage(imageName);
  }
}
