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

    String[] arrayReturn = new String[2];
    int index = -2;
    //Y'a certainement un meilleur moyen que cette vielle répétition de code.
    index = lines.indexOf("\r\n");
    if (index > -1){
      arrayReturn[0] = lines.substring(0, index);
      arrayReturn[1] =  lines.substring(index);
    }

    index = lines.indexOf('\r');
    if (index > -1){
      arrayReturn[0] = lines.substring(0, index);
      arrayReturn[1] =  lines.substring(index);
    }

    index = lines.indexOf('\n');
    if (index > -1){
      arrayReturn[0] = lines.substring(0, index);
      arrayReturn[1] =  lines.substring(index);
    }

    return arrayReturn;
  }

}
