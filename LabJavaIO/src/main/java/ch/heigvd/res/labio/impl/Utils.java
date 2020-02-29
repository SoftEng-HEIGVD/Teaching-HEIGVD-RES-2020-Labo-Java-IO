package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
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
        String[] result = new String[2];
        result[0] = "";
        result[1] = "";
        char lastChar = '\0';
        boolean foundEndChar = false;

        for (int i = 0; i < lines.length(); i++) {
            char current = lines.charAt(i);

            if (current == '\n' || current == '\r') {
                foundEndChar = true;
            }
            
            if (lastChar == '\r' || current == '\n') {
                if (!(lastChar == '\r' && current != '\n')) {
                    i += 1;
                }
                if (current == '\n') {
                    result[0] += '\n';
                }
                result[1] = lines.substring(i);
                break;
            } else {
                result[0] += current;
            }
            lastChar = current;
        }

        if (!foundEndChar) {
            result[1] = result[0];
            result[0] = "";
        }

        return result;
    }

}
