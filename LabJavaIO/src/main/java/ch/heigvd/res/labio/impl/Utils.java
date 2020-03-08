package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * découverte des lookahead et lookbehind grâce à :
   * - https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
   *   pour ce qui est de la structure d'une regex
   * - https://www.regular-expressions.info/lookaround.html
   *   pour l'utilisation des lookahead et lookbehind
   *   - (?<=X)      : si X se trouve bien avant le caractère
   *   - (?!X)       : si X ne se trouve pas après le caractère
   *   - (?<=X(?!Y)) : si X se trouve avant le caractère mais qu'il n'est pas
   *                   suivi par Y
   *   - lookahead et lookbehind permettent de faire des comparaisons sur les
   *     String sans que ceux-ci ne soient consommés : ils permettent de dire
   *     si le String match ou non la regexe
   */
  private static final String REGEX = "(?<=(\r\n))|(?<=\n)|(?<=\r(?!\n))";

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
    // throw new UnsupportedOperationException("The student has not implemented this method yet.");
    String[] result = lines.split(REGEX, 2);

    if (result.length == 2)
      return result;
    else
      return new String[]{"", result[0]};
  }

}
