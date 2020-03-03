package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());
    private static final String WINDOWS_RETURN_LINE = "\r\n";
    private static final String UNIX_RETURN_LINE = "\n";
    private static final String MAC_RETURN_LINE = "\r";

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
        String res[] = new String[2];
        res[0] = res[1] = "";

        // Special case if no return line found
        if(getNbOfReturnLine(lines) == 0){
            res[0] = "";
            res[1] = lines;
        }
        // 1 or more return lines founds
        else{
            // Split lines by the right divider and KEEP the divider with (?<=)
            String[] splitLines = new String[getNbOfReturnLine(lines)];
            if(lines.contains(WINDOWS_RETURN_LINE)){
                splitLines = lines.split("(?<="+WINDOWS_RETURN_LINE+")");
            }else if (lines.contains(UNIX_RETURN_LINE)){
                splitLines = lines.split("(?<="+UNIX_RETURN_LINE+")");
            }else if(lines.contains(MAC_RETURN_LINE)){
                splitLines = lines.split("(?<="+MAC_RETURN_LINE+")");
            }

            // Loop through all our split lines
            boolean first = true;
            for(String splitLine : splitLines){
                // First cell of the res array is the first split line
                if(first){
                    res[0] = splitLine;
                    first = false;
                }
                // Second cell is all the other splitLines
                else{
                    res[1] += splitLine;
                }
            }

        }

        return res;
    }

    /**
     * Return the count of return line present in the String in parameter
     * @param str The string for which we want to count how many return line are present
     * @return The count of return line
     */
    private static int getNbOfReturnLine(String str){
        int count = 0;
        Pattern pattern = Pattern.compile(WINDOWS_RETURN_LINE + "|" + UNIX_RETURN_LINE + "|" + MAC_RETURN_LINE);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    /**
     * Check if the String in parameter ends with a return line
     * @param str The String we want to check
     * @return True if the String ends with a return line
     */
    private static boolean strEndWithReturnLine(String str){
        return str.endsWith(WINDOWS_RETURN_LINE) || str.endsWith(UNIX_RETURN_LINE) || str.endsWith(MAC_RETURN_LINE);
    }

}
