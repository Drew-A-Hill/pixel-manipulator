import java.util.Scanner;

import controller.CommandController;
import controller.GUIController;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class acts as the main entry way into the program.
 */
public class ImageModifier {

  /**
   * This method enters the program.
   * @param args arguments provided by user.
   */
  public static void main(String[] args) {
    Appendable appendable = new StringBuilder();

    if (args.length == 0) {
      new GUIController(appendable).run();
    } else if (args.length > 2) {
      Scanner scanner = new Scanner(System.in);
      new CommandController(scanner, appendable);
    }
  }
}
