package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 * @author Stéphane Teixeira Carvalho
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());
  public static final char MACOS_ENDLINE = '\r';
  public static final char LINUX_ENDLINE = '\n';

    /**
     * Fonction permettant de casser une string en 2 parties depuis un offset
     * @param lines String étant la string à partitionner
     * @param off entier étant le offset ou la partition doit avoir lieu
     * @return un tableau de String contenant les 2 parties
     */
  private static String[] breakLine(String lines, int off) {
      String[] elements = new String[2];
      elements[0] = lines.substring(0, off + 1);
      elements[1] = lines.substring(off + 1);
      return elements;
  }

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
    for(int i = 0; i < lines.length(); i++){
        if(i+1 == lines.length() && lines.charAt(i) == Utils.MACOS_ENDLINE){
            return breakLine(lines, i);
        }
        if(lines.charAt(i) == Utils.MACOS_ENDLINE && lines.charAt(i + 1) != Utils.LINUX_ENDLINE){
            return breakLine(lines, i);
        }
        else if(lines.charAt(i) == Utils.LINUX_ENDLINE){
            return breakLine(lines, i);
        }
    }
    return new String[]{"",lines};
  }

}
