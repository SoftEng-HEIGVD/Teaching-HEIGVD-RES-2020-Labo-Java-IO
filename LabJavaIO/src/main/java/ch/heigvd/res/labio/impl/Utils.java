package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  // Definition des séparateurs pour les OS MAC, UNIX et WINDOWS
  private static final String SEPARTOR_MAC = "\r";
  private static final String SEPARTOR_UNI = "\n";
  private static final String SEPARTOR_WIN = "\r\n";
  private static String[] allSeparators = {SEPARTOR_MAC, SEPARTOR_UNI, SEPARTOR_WIN};
  private static int NB_LINES = 2;


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
    String[] tabResults = new String[NB_LINES];
    boolean isSeparator = false;

    //Recherche du système utilisé (séparateur)
    for(String s : allSeparators){
      //Identifiquation du séparateur utilisé
      if(lines.contains(s)){
        tabResults = lines.split(s,NB_LINES);
        tabResults[0] += s;
        isSeparator = true;
      }

    }
    //Gestion des lignes sans séparateurs
    if(!isSeparator){
      tabResults[0] = "";
      tabResults[1] = lines;
    }
    return  tabResults;
  }
}
