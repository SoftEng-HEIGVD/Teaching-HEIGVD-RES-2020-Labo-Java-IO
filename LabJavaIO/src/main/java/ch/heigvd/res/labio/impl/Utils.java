package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());
  public static final char MACOS_ENDLINE = '\r';
  public static final char LINUX_ENDLINE = '\n';
  public static final String WINDOWS_ENDLINE = "\r\n";

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
    //Regarde si le retour à la ligen windows est contenu dans la chaîne est sépare la chaîne si c'est le cas
    if(lines.contains(WINDOWS_ENDLINE)){
      elements[0] = lines.substring(0,lines.indexOf(WINDOWS_ENDLINE) + WINDOWS_ENDLINE.length());
      elements[1] = lines.substring(lines.indexOf(WINDOWS_ENDLINE) + WINDOWS_ENDLINE.length());
    }
    else if(lines.contains(String.valueOf(MACOS_ENDLINE))){
      elements[0] = lines.substring(0, lines.indexOf(MACOS_ENDLINE) + 1);//+ 1 car le caractère de fin de ligne doit être inclu
      elements[1] = lines.substring(lines.indexOf(MACOS_ENDLINE) + 1);
    }
    else if(lines.contains(String.valueOf(LINUX_ENDLINE))){
      elements[0] = lines.substring(0, lines.indexOf(LINUX_ENDLINE) + 1);
      elements[1] = lines.substring(lines.indexOf(LINUX_ENDLINE) + 1);
    }
    else{
      elements[1] = lines;
    }
    if(elements[0] == null){
      elements[0] = "";
    }
    return elements;
  }

}
