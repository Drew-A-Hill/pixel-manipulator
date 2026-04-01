package commands.operations;

import java.util.Scanner;

import model.image.IImage;
import model.operations.LumaOperation;
import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class alters the image based on the Luma Grey Scale strategy which is called in the
 * {@link AbstractOperationCommands}, which is where the details for the operation are held.
 */
public class LumaGreyScaleCommand extends AbstractOperationCommands {

  /**
   * Constructs an instance of luma Grey Scale command.
   * @param scanner contains all the required arguments to be used when altering an image.
   * @param storage storage object that holds the images that have been loaded or altered.
   */
  public LumaGreyScaleCommand(Scanner scanner, IStorage storage) {
    super(scanner, storage);
  }

  /**
   * Alters the provided image and provides a new luma grey scale image.
   * @param image the image to be altered
   * {@return the altered image}
   */
  @Override
  protected IImage commandType(IImage image) {
    System.out.println("Adding luma grey scale to");
    return new LumaOperation().apply(image);
  }
}
