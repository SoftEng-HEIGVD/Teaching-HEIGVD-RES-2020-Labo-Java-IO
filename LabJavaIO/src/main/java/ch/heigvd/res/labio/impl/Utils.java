package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti, modified by Christian Zaccaria
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  //Systems to check
  private static final String MAC = "\r";
  private static final String UNIX = "\n";
  private static final String WINDOWS = "\r\n";

  private static final int NB_LINES = 2;
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
    //Line splitted in two
    String[] result = new String[NB_LINES];
    //Tab of all type of systems (separators)
    String[] separators = {MAC, UNIX, WINDOWS};
    boolean separatorFind = false;

    //Loop to try to find if one of the system is used
    for(int i = 0; i < separators.length; ++i){
      //Try to check if is the good system
      if(lines.contains(separators[i])){
          //Split line --> 2
          result = lines.split(separators[i], NB_LINES);
          result[0] += separators[i];
          separatorFind = true;
      }
    }
    //Case when is not a separator in a line
    if(!separatorFind){
      result[0] = "";
      result[1] = lines;
    }

    return result;
  }
}
