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
    String result[] = {"", ""};
    int tabIndex = 0;
    for(int i = 0; i < lines.length(); ++i){
      result[tabIndex] += lines.charAt(i) + "";
      if(tabIndex == 0 && lines.charAt(i) == '\r'){
        tabIndex++;
        if(lines.length() > i+1 && lines.charAt(i+1)  == '\n'){
          i++;
          result[0] += lines.charAt(i);
        }
      }else if(tabIndex == 0 && lines.charAt(i) == '\n'){
        tabIndex++;
      }
    }
    if(tabIndex == 0){
      result[1] = result[0];
      result[0] = "";
    }
    return result;
  }

}
