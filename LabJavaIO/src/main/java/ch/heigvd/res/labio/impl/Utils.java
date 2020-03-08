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
    String[] result = new String[2];
    result[0] = "";
    result[1] = "";
    boolean isWindowsNewLine = false;
    boolean nextLineOk = false;
    for(int i = 0; i < lines.length();i++){
      if(nextLineOk) {
        result[1] += lines.charAt(i);
      }else{
        result[0] += lines.charAt(i);
        if(lines.charAt(i) == '\n'){
          nextLineOk = true;
        }
        if(lines.charAt(i) == '\r' && ((i+1 < lines.length())&&(lines.charAt(i+1)!= '\n')||(i == lines.length()-1))){
          nextLineOk = true;
        }
      }
    }
    if(nextLineOk == false){
      result[1] = result[0];
      result[0] = "";
    }
    return result;
  }
}
