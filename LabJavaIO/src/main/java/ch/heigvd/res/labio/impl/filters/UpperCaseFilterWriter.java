package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter
{
    public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

    @Override
    public void write(String str, int off, int len) throws IOException
    {
      // Based on : https://www.tutorialspoint.com/java/java_string_touppercase.htm
      super.write(str.toUpperCase(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException
    {
        // Same as above but includes a conversion
        super.write(new String(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException
    {
        // Based on : https://www.javatpoint.com/post/java-character-touppercase-method
        super.write(Character.toUpperCase(c));
    }

}
