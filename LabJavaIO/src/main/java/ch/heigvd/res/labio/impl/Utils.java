package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti, Vitor Vaz Afonso
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

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

    String extractNextLine = "(?<=\r\n)|(?<=\r)(?!\n)|(?<=\n)";

    String[] splitLines = lines.split(extractNextLine, 2);

    // There are one or more line separators
    if(splitLines.length == 2){
      return splitLines;
    }else{ // There are no line separators
      return new String[]{"",splitLines[0]};
    }

  }

}
