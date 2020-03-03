package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    /**
     * https://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
     * https://www.rexegg.com/regex-lookarounds.html
     * I'm using lookbehind patterns to assert the content coming immediately before
     * the end of line delimiters in the string.
     * <p>
     * This method looks for the next new line separators (\r, \n, \r\n) to extract
     * the next line in the string passed in arguments.
     *
     * @param lines a string that may contain 0, 1 or more lines
     * @return an array with 2 elements; the first element is the next line with
     * the line separator, the second element is the remaining text. If the argument does not
     * contain any line separator, then the first element is an empty string.
     */
    public static String[] getNextLine(String lines) {
        String LINE_SEP_REGEX = "(?<=\r\n)|(?<=\n)|(?<=\r)(?!\n)";

        // Splitting using the regex for line separators
        final String[] splittedLines = lines.split(LINE_SEP_REGEX, 2);
        // If there are new line chars at the end of the lines
        if(splittedLines.length == 2)
            return splittedLines;
            // If there's no new line char at the end of a line return the first line as empty
        else
            return new String[]{"", splittedLines[0]};
    }
}
