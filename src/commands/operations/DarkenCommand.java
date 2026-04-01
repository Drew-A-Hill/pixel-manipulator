package commands.operations;

import java.util.Scanner;

import model.image.IImage;
import model.operations.DarkenOperation;
import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class alters the image based on the Darken strategy which is called in the
 * {@link AbstractBrightDarkCommands}, which is where the details for the operation are held.
 */
public class DarkenCommand extends AbstractBrightDarkCommands {

  /**
   * Constructs an instance of Darken command.
   * @param scanner contains all the required arguments to be used when altering an image.
   * @param storage storage object that holds the images that have been loaded or altered.
   */
  public DarkenCommand(Scanner scanner, IStorage storage) {
    super(scanner, storage);
  }

  /**
   * Alters the provided image and provides a darken image.
   * @param image the image to be altered
   * {@return the altered image}
   */
  @Override
  protected IImage commandType(IImage image, int value) {
    System.out.println("Darkening");
    return new DarkenOperation(value).apply(image);
  }
}
