package commands.operations;

import java.util.InputMismatchException;
import java.util.Scanner;

import commands.CommandImpl;
import model.image.IImage;
import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class assembles and runs the operations for all brighten and darken commands.
 */
public abstract class AbstractBrightDarkCommands extends CommandImpl {

  /**
   * Constructs an instance of Abstract Brighten Darken Command.
   * @param scanner contains all the required arguments to be used when altering an image.
   * @param storage storage object that holds the images that have been loaded or altered.
   */
  public AbstractBrightDarkCommands(Scanner scanner, IStorage storage) {
    super(scanner, storage);
  }

  /**
   * Performs the alteration for each operation type that extends this class.
   * @param image the image to be altered
   * {@return the altered image}
   */
  protected abstract IImage commandType(IImage image, int value);

  /**
   * Holds the logic that assembles and runs the operation called by the users input.
   */
  @Override
  public void runCommand() {
    int value;

    try {
      value = scanner.nextInt();
    } catch (InputMismatchException e) {
      throw new IllegalArgumentException(e);
    }

    if (value < 0) {
      throw new IllegalArgumentException("Provide a positive integer value");
    }

    String imageName = scanner.next();

    IImage image = storage.retrieveImage(imageName);

    if (image == null) {
      String message = storage.storedImages(imageName + " Not Found! Try: ");
      throw new IllegalArgumentException(String.format("%s", message));
    }

    IImage newImage = commandType(image, value);

    String newImageName = scanner.next();

    storage.addImage(newImage, newImageName);
    System.out.println("New Image: " + newImageName +  " is complete\n");

    storage.removeImage(imageName);
  }
}
