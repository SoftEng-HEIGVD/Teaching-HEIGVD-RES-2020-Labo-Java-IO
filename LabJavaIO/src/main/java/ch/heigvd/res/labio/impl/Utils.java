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
    String[] elements = new String[2];
    boolean endFound = false;
    int i = 0;
    while(!endFound && i < lines.length()){
      if(lines.charAt(i) == '\r' || lines.charAt(i) == '\n'){
          endFound = true;
          if(i + 1 >= lines.length()) {
            elements[0] = lines.substring(0, i + 1);
            elements[1] = "";
          }
          else{
            //Comportement différent pour Windows
            if(lines.charAt(i + 1) == '\n') {
              elements[0] = lines.substring(0, i + 2);
              if(i + 2 >= lines.length()) {
                elements[1] = "";
              }
              else {
                elements[1] = lines.substring(i + 2);
              }
            } else {
              elements[0] = lines.substring(0, i + 1);
              elements[1] = lines.substring(i + 1);
            }
          }
      }
      i++;
    }
    if(elements[0] == null){
      elements[0] = "";
      elements[1] = lines;
    }
    return elements;
  }

}
