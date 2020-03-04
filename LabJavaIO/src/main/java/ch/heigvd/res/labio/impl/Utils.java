package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils
{
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
    public static String[] getNextLine(String lines)
    {
        int pos;

        for (pos = 0; pos < lines.length(); ++pos)
        {
            if (lines.charAt(pos) == '\n') { break;} // pos is the end of the first line

            if (lines.charAt(pos) == '\r')
            {
                if (pos < lines.length()-1 && lines.charAt(pos+1) == '\n')
                {
                    pos += 1;
                    break;  // In case of a carriage return (\r\n) then we must advance pos by 1 to find the end of the line
                }
                else
                {
                    break;  // Either ends by \r or isn't followed by \n so we found pos
                }

            }
        }

        if (pos == lines.length())
        {
            return new String[] {"", lines};
        }

        return new String[] {lines.substring(0, pos+1), lines.substring(pos+1, lines.length())};
    }

}
