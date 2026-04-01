package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Drew Hill
 * CS5004
 * Summer 2025
 * Assignment 8
 * This class serves as the implementation of the IMessanger. This class formats and prints
 * error messages for users to see.
 */
public class MessangerImpl implements IMessanger {
  private Appendable appendable;
  private List<String> messageList;

  public MessangerImpl(Appendable appendable) {
    this.appendable = appendable;
    this.messageList = new ArrayList<>();
  }

  /**
   * Prints response messages displayed to the user.
   * @param message the message to be formatted.
   */
  @Override
  public void printMessage(String message) {
    String messageOut = message.substring(message.lastIndexOf("n:") + 1);

    try {
      this.appendable.append(messageOut);
      formatMessage();
    } catch (IOException e) {
      System.out.println("System Error!");
    }
  }

  /**
   * Formats the message shown to user.
   */
  @Override
  public void formatMessage() {
    String messages = this.appendable.toString();
    if (messageList.isEmpty()) {
      messageList.add(messages);
      System.out.println(messages);
    } else {

      String currentMessage = messages.substring(
              messages.lastIndexOf(messageList.get(messageList.size() - 1)));
      messageList.add(currentMessage);

      System.out.println(currentMessage + System.lineSeparator());
    }
  }
}
