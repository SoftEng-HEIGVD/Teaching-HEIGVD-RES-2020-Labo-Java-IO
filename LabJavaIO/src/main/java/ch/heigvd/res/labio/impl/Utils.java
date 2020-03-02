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
    String[] elements = new String[2];
    boolean endFound = false;//Indique qu'un fin de ligne a été trouvé
    int i = 0;//Compteur pour parcourir la chaîne de caractère

    while(!endFound && i < lines.length()){
      //Si le caractère trouvé et un caractère de fin de ligne
      if(lines.charAt(i) == '\r' || lines.charAt(i) == '\n'){
          endFound = true;
          //Si i + 1 dépasse la longeur de la chaîne cela veut dire que le caractère de fin de ligne est le dernier caractère
          if(i + 1 >= lines.length()) {
            elements[0] = lines.substring(0, i + 1);
            elements[1] = "";
          }
          else{
            //Si le caractère i un \r et que i + 1 est un \n cela indique que c'est un retour à la ligne(Windows)
            if(lines.charAt(i) == '\r' && lines.charAt(i + 1) == '\n') {
              //Comme il est sur que c'est un retour à la ligne enregistrement de la substring
              elements[0] = lines.substring(0, i + 2);
              //Si i + 2 dépasse la taille de la chaîne cela indique que le caractère est la fin de la chaîne
              //pas de substring supplémentaire dans ce cas sinon enregistrement de la substring dans elements[1]
              if(i + 2 >= lines.length()) {
                elements[1] = "";
              }
              else {
                elements[1] = lines.substring(i + 2);
              }
            } else {
              elements[0] = lines.substring(0, i + 1);
              elements[1] = lines.substring(i + 1);
            }
          }
      }
      i++;
    }
    //Si aucun élément de nouvelle ligne est trouvé la ligne est mise à l'emplacement elements[1] et elements[0] est une chaîne vide
    if(elements[0] == null){
      elements[0] = "";
      elements[1] = lines;
    }
    return elements;
  }

}
