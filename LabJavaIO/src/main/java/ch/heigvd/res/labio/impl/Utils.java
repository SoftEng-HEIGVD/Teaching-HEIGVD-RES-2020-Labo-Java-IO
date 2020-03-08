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
    int i1 = lines.indexOf("\n");
    int i2 = lines.indexOf("\r");
    int i3 = lines.indexOf("\r\n");
    if (i1 == -1 && i2==-1 && i3==-1) { //Aucune occurence
      result[0] = "";
      result[1] = lines;
    }
    else if(i3>i1 && i3 >=i2) { // pour \r\n
      result[0] = lines.substring(0, i3 + 3);
      result[1] = lines.substring(i3 + 3);
    }
    else if (i2>i1) { // pour \r
      result[0] = lines.substring(0, i2+1);
      result[1] = lines.substring(i2+1);
    } else { // pour \n
      result[0] = lines.substring(0, i1+1);
      result[1] = lines.substring(i1+1);
    }
    return result;
  }


}
