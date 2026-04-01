package commands;

import java.util.Scanner;

import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class is used as the primary constructor for all command classes.
 */
public abstract class CommandImpl implements ICommands {
  protected Scanner scanner;
  protected IStorage storage;

  /**
   * Constructs a new instance of command object that will be used to perform the command on the
   * image files.
   * @param scanner contains all the required arguments to determine the command to run.
   * @param storage storage object that holds the images that have been loaded.
   */
  public CommandImpl(Scanner scanner, IStorage storage) {
    if (scanner == null || storage == null) {
      throw new IllegalArgumentException("Storage/ Import error\n");
    }

    this.storage = storage;
    this.scanner = scanner;
  }
}
