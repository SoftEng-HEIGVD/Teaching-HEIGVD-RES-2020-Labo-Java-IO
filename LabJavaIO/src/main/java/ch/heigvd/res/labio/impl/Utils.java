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
    String[] division = new String[2];
    division[1] = "";
    int offset;

    int posWindowsEndLine = lines.indexOf("\r\n");
    int posLinuxEndLine = lines.indexOf("\n");
    int posMacEndLine = lines.indexOf("\r");
    int posEndLine;

    if(posWindowsEndLine >= 0){  //TODO methode auxiliaire
      posEndLine = posWindowsEndLine;
      offset = 2;
    }else if(posMacEndLine >= 0){
      posEndLine = posMacEndLine;
      offset = 1;
    }else if(posLinuxEndLine>= 0){
      posEndLine = posLinuxEndLine;
      offset = 1;
    }else{
      division[0] = "";
      posEndLine = 0;
      offset = 0;
    }
    division[0] = lines.substring(0, posEndLine + offset);
    division[1] = lines.substring(posEndLine + offset);

    return division;
   // throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
