package view.actions;

import java.util.Map;
import java.util.Scanner;

import commands.CommandMap;
import commands.ICommands;
import storage.IStorage;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 10
 * This class serves as is used to run the commands input into the fields when the apply, load or
 * save button is pressed.
 */
public class ActionRunner {

  /**
   * Runs the commands based on the inputs from the GUI.
   * @param instructions the information to be passed the command to perform actions.
   * @param storage the storage for the images.
   * @param key the key to call the command from the cmdMap
   */
  public void run(String instructions, IStorage storage, String key) {
    Scanner scanner = new Scanner(instructions);
    Map<String, ICommands> cmdMap = new CommandMap(scanner, storage).getCmdMap();

    try {
      cmdMap.get(key).runCommand();
    } catch (Exception e) {
      throw new IllegalArgumentException("Bad Instructions");
    }
  }
}
