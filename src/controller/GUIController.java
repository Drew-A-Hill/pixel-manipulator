package controller;

import javax.swing.SwingUtilities;

import storage.IStorage;
import storage.StorageImpl;
import view.MainViewWindow;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 9
 * This class loads saves and alters images based on the input provided by the user. Used to
 * control the flow of the program.
 */
public class GUIController implements IController {
  private Appendable appendable;
  private IStorage storage;

  /**
   * Constructs an instance of the controller.

   * @param appendable used to hold responses to the user.
   */
  public GUIController(Appendable appendable) {
    this.storage = new StorageImpl();
    this.appendable = appendable;

  }

  /**
   * Runs the program.
   */
  @Override
  public void run() {
    IMessanger messanger = new MessangerImpl(this.appendable);

    try {
      SwingUtilities.invokeLater(() -> new MainViewWindow(this.storage));
    } catch (Exception e) {
      messanger.printMessage(e.getMessage());
    }
  }
}
