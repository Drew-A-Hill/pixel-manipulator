package controller;

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import commands.CommandMap;
import commands.ICommands;
import storage.IStorage;
import storage.StorageImpl;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class loads saves and alters images based on the input provided by the user. Used to
 * control the flow of the program.
 */
public class CommandController implements IController {
  private final Scanner scanner;
  private Appendable appendable;
  private final Map<String, ICommands> cmdMap;

  /**
   * Constructs an instance of the controller.
   *
   * @param scanner contains all the required arguments to be used when altering an image.
   * @param appendable used to hold responses to the user.
   */
  public CommandController(Scanner scanner, Appendable appendable) {
    this.scanner = scanner;
    IStorage storage = new StorageImpl();
    this.cmdMap = new CommandMap(scanner, storage).getCmdMap();
    this.appendable = appendable;
  }

  /**
   * Runs the program.
   */
  @Override
  public void run() {
    while (this.scanner.hasNext()) {

      IMessanger messanger = new MessangerImpl(this.appendable);

      String firstCommand  = scanner.next().toLowerCase(Locale.ROOT);

      if (firstCommand.equalsIgnoreCase("quit")) {
        break;
      }

      ICommands command = cmdMap.get(firstCommand);
      if (command == null) {
        messanger.printMessage(firstCommand + " Is not a valid command!\n");
      } else {
        try {
          command.runCommand();
        } catch (Exception e) {
          messanger.printMessage(e.getMessage());
        }
      }
    }
  }
}
