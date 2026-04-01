package controller;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class serves as the interface for the MessangerImpl which shows users that utilize the
 * command line interface the error messages.
 */
public interface IMessanger {

  /**
   * Prints response messages displayed to the user.
   * @param message the message to be formatted.
   */
  void printMessage(String message);

  /**
   * Formats the message shown to user.
   */
  void formatMessage();


}
