package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());
  private static final String WINDOWS_ENDLINE = "\r\n";
  private static final String MAC_ENDLINE = "\r";
  private static final String LINUX_ENDLINE = "\n";

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
    int nextLineIndex = getNextLineIndex(lines);
    String[] division = new String[2];

    division[0] = lines.substring(0, nextLineIndex);
    division[1] = lines.substring(nextLineIndex);

    return division;
  }

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to get the index
   * of the next line.
   *
   * @param lines lines a string that may contain 0, 1 or more lines
   * @return the index at which the next line starts
   */
  private static int getNextLineIndex(String lines){
    int posWindowsEndLine = lines.indexOf(WINDOWS_ENDLINE);
    int posLinuxEndLine = lines.indexOf(LINUX_ENDLINE);
    int posMacEndLine = lines.indexOf(MAC_ENDLINE);

    if(posWindowsEndLine >= 0){
      return posWindowsEndLine + WINDOWS_ENDLINE.length();
    }
    if(posMacEndLine >= 0){
      return posMacEndLine + MAC_ENDLINE.length();
    }
    if(posLinuxEndLine>= 0){
      return posLinuxEndLine + LINUX_ENDLINE.length();
    }
    return  0;
  }



}
