package view.error;

public class ErrorHandler {
  /**
   * Creates a string that holds all the error messages for a particular action.
   * {@return the string consisting of the error output}
   */
  private String loadErrorString() {
    StringBuilder sb = new StringBuilder();
    if (fileNameField.getText().equalsIgnoreCase("Enter File Name")) {
      sb.append("Enter File Name!!");
      sb.append(System.lineSeparator());
    }

    if (filePathField.getText().equalsIgnoreCase("Enter File Path")) {
      sb.append("Enter File Path!!");
      sb.append(System.lineSeparator());
    } else {
      sb.append("Bad File Path");
    }

    return sb.toString();
  }
}
