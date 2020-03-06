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

    String[] returnStrings = new String[2];
    returnStrings[0] = "";
    returnStrings[1] = "";

    int position = 0;
    int delimiterSize = 0;
    boolean keepGoing = true;
    while(position < lines.length() && keepGoing){

      switch(lines.charAt(position)){
        case '\r':
          //Case \r\n
          if((position + 1) < lines.length() && lines.charAt(position+1) == '\n'){
            delimiterSize++;
          }
          keepGoing = false;
          break;
        case '\n':
          keepGoing = false;
          break;
       }
       position++;
    }


    returnStrings[0] = keepGoing ? "" : lines.substring(0, position + delimiterSize);

    returnStrings[1] = keepGoing ? lines : lines.substring(returnStrings[0].length());

    return returnStrings;

  }

  public static void main(String[] args){
    String str = "this is a line1\nthis is a other line1";
    String str2 = "this is a line2\rthis is a other line2\r";
    String str21 = "this is a line2\nthis is a other line2\n";
    String str3 = "This is line 1\nThis is line 2\r\nThis is line 3\rThis is line 4";
    String str4 = "This is line 1";


    String[] res = getNextLine(str4);
    System.out.println("-------------------");
    System.out.println(res[0]);

    String[] res2 = getNextLine(res[1]);
    System.out.println(res2[0]);


    System.out.println("-------------------");



  }

}
