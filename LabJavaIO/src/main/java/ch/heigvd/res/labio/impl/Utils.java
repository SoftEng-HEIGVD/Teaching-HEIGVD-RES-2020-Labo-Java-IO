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
    String[] gLines = new String[2];
    gLines = lines.split("\\R",2);
    int pos = gLines[0].length();
    if(gLines.length > 1){
      if(lines.regionMatches(pos,"\r\n",0,2)){
        gLines[0] += "\r\n";
      }else if(lines.regionMatches(pos,"\r",0,1)){
        gLines[0] += "\r";
      }else if(lines.regionMatches(pos,"\n",0,1)){
        gLines[0] += "\n";
      }
    }
    else{
      String[] ret = new String[]{"",gLines[0]};
      return ret;
    }
    return gLines;
  }

}
