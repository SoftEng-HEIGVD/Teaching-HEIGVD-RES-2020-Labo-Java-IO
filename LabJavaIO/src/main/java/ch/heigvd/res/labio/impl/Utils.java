package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
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

    String[] returnStrings = new String[2];
    returnStrings[0] = "";
    returnStrings[1] = "";

    String delimiter = "";

    if(lines.contains("\r\n")){ //Case Windows
      delimiter = "\r\n";
    }else if(lines.contains("\n")){ //Case MaxOs
      delimiter = "\n";
    }else if(lines.contains("\r")){ //Case Unix
      delimiter = "\r";
    }


    String[] splited = lines.split(String.format("(?<=%s)", delimiter),2);
    //If no delimiter
    if(delimiter == "")
      splited[0] = lines;

    //First part
    returnStrings[0] = splited[0];
    //Rest
    returnStrings[1] = lines.substring(returnStrings[0].length());

    return returnStrings;

  }

}
