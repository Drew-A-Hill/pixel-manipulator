package commands.operations;

import java.util.Scanner;

import model.image.IImage;
import model.operations.BrightenOperation;
import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class alters the image based on the Brighten strategy which is called in the
 * {@link AbstractBrightDarkCommands}, which is where the details for the operation are held.
 */
public class BrightenCommand extends AbstractBrightDarkCommands {

  /**
   * Constructs an instance of Brighten command.
   * @param scanner contains all the required arguments to be used when altering an image.
   * @param storage storage object that holds the images that have been loaded or altered.
   */
  public BrightenCommand(Scanner scanner, IStorage storage) {
    super(scanner, storage);
  }

  /**
   * Alters the provided image and provides a new brightened image.
   * @param image the image to be altered
   * {@return the altered image}
   */
  @Override
  protected IImage commandType(IImage image, int value) {
    System.out.println("Brightening");
    return new BrightenOperation(value).apply(image);
  }
}
