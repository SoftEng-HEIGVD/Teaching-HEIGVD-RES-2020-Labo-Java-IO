package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());
    public static final String WINDOWS_SEPARATOR = "\r\n";
    public static final String UNIX_SEPARATOR = "\n";
    public static final String MACOS_SEPARATOR = "\r";

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
        String[] result = {"", ""};

        if (lines.contains(WINDOWS_SEPARATOR)) { // Contains /r/n
            result[0] = lines.substring(0, lines.indexOf(WINDOWS_SEPARATOR) + WINDOWS_SEPARATOR.length());
            result[1] = lines.substring(lines.indexOf(WINDOWS_SEPARATOR) + WINDOWS_SEPARATOR.length());
        } else if (lines.contains(UNIX_SEPARATOR) ) { // Contains /n
            result[0] = lines.substring(0, lines.indexOf(UNIX_SEPARATOR) + UNIX_SEPARATOR.length());
            result[1] = lines.substring(lines.indexOf(UNIX_SEPARATOR) + UNIX_SEPARATOR.length());
        } else if (lines.contains(MACOS_SEPARATOR)) { // Contains /r
            result[0] = lines.substring(0, lines.indexOf(MACOS_SEPARATOR) + MACOS_SEPARATOR.length());
            result[1] = lines.substring(lines.indexOf(MACOS_SEPARATOR) + MACOS_SEPARATOR.length());
        } else {
            result[1] = lines;
        }

        return result;
    }

}
