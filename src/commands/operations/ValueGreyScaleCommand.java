package commands.operations;

import java.util.Scanner;

import model.image.IImage;
import model.operations.ValueGreyScaleOperation;
import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class alters the image based on the Value Grey Scale strategy which is called in the
 * {@link AbstractOperationCommands}, which is where the details for the operation are held.
 */
public class ValueGreyScaleCommand extends AbstractOperationCommands {

  /**
   * Constructs an instance of value Grey Scale command.
   * @param scanner contains all the required arguments to be used when altering an image.
   * @param storage storage object that holds the images that have been loaded or altered.
   */
  public ValueGreyScaleCommand(Scanner scanner, IStorage storage) {
    super(scanner, storage);
  }

  /**
   * Alters the provided image and provides a new value grey scale image.
   * @param image the image to be altered
   * {@return the altered image}
   */
  @Override
  protected IImage commandType(IImage image) {
    System.out.println("Adding value grey scale to");
    return new ValueGreyScaleOperation().apply(image);
  }
}
