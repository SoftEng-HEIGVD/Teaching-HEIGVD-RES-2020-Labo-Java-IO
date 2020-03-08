package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());
  private static final int NB_STRINGS_RESULT=2;
  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines)
  {
      String[] result=new String[NB_STRINGS_RESULT];

      for(int x=0;x<lines.length();++x)
      {
          char c=lines.charAt(x);

          if(c=='\r' && x<=(lines.length()-2) && lines.charAt(x+1)=='\n')
          {
              result=lines.split("\r\n", NB_STRINGS_RESULT);
              result[0]+=("\r\n");
              break;
          }
          else if(c=='\r')
          {
              result=lines.split(String.valueOf(c), NB_STRINGS_RESULT);
              result[0]+=c;
              break;
          }
          else if(c=='\n')
          {
              result=lines.split(String.valueOf(c), NB_STRINGS_RESULT);
              result[0]+=c;
              break;
          }
          else
          {
              result=new String[] {"", lines};
          }
      }

      return result;
  }

}
