package ch.heigvd.res.labio.impl;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {
  public static final String WINDOWS = "\r\n";
  public static final String UNXSUBSYS = "\n";
  public static final String MACOS = "\r";

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments.
   *
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    String[] newLines = {"", ""};

    if (lines.contains(UNXSUBSYS) && !lines.contains(WINDOWS)) { //UNIX
      newLines[0] = lines.substring(0, lines.indexOf(UNXSUBSYS) + UNXSUBSYS.length());
      newLines[1] = lines.substring(lines.indexOf(UNXSUBSYS) + UNXSUBSYS.length());
    } else if (lines.contains(MACOS) && !lines.contains(WINDOWS)) { //MACOS
      newLines[0] = lines.substring(0, lines.indexOf(MACOS) + MACOS.length());
      newLines[1] = lines.substring(lines.indexOf(MACOS) + MACOS.length());
    } else if (lines.contains(WINDOWS)) { //Windows
      newLines[0] = lines.substring(0, lines.indexOf(WINDOWS) + WINDOWS.length());
      newLines[1] = lines.substring(lines.indexOf(WINDOWS) + WINDOWS.length());
    } else {
      newLines[1] = lines;
    }

    return newLines;
  }

}
