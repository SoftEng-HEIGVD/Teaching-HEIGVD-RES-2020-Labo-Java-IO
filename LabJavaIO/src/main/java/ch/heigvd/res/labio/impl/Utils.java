package ch.heigvd.res.labio.impl;

import java.util.ArrayList;
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
    String[] tmp = new String[2];
    boolean windowsRetLin = false;
    for (int i = 0; i < lines.length(); i++) {
      if (lines.charAt(i) != '\n' && windowsRetLin){
        tmp[0] = lines.substring(0,i);
        tmp[1] = lines.substring(i);
        return tmp;
      }
      if(lines.charAt(i) == '\r'){
        if (i+1 < lines.length())
          windowsRetLin = true;
        else {
          tmp[0] = lines.substring(0, i + 1);
          tmp[1] = lines.substring(i + 1);
          return tmp;
        }
      }else if(lines.charAt(i) == '\n'){
        tmp[0] = lines.substring(0,i+1);
        tmp[1] = lines.substring(i+1);
        return tmp;
      }
    }
    // si pas de retour de ligne
    tmp[0] = new String();
    tmp[1] = lines;
    return tmp;
  }
}
