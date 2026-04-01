package commands.operations;

import java.util.Scanner;

import commands.CommandImpl;
import model.image.IImage;
import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class assembles and runs the operations for all the strategies except brighten and darken.
 */
public abstract class AbstractOperationCommands extends CommandImpl {

  /**
   * Constructs an instance of Abstract Operations Command.
   * @param scanner contains all the required arguments to be used when altering an image.
   * @param storage storage object that holds the images that have been loaded or altered.
   */
  public AbstractOperationCommands(Scanner scanner, IStorage storage) {
    super(scanner, storage);
  }


  /**
   * Performs the alteration for each operation type that extends this class.
   * @param image the image to be altered
   * {@return the altered image}
   */
  protected abstract IImage commandType(IImage image);

  /**
   * Holds the logic that assembles and runs the operation called by the users input.
   */
  @Override
  public void runCommand() {
    String imageName = scanner.next();
    IImage image = storage.retrieveImage(imageName);
    String newImageName = scanner.next();

    if (image == null) {
      String message = storage.storedImages(imageName + " Not Found! Try: ");
      throw new IllegalArgumentException(String.format("%s", message));
    }

    IImage newImage = commandType(image);
    storage.addImage(newImage, newImageName);
    System.out.println("New Image: " + newImageName +  " is complete\n");

    storage.removeImage(imageName);

  }
}
