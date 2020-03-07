package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {

    public UpperCaseFilterWriter(Writer wrappedWriter) {
        super(wrappedWriter);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append(Character.toUpperCase(str.charAt(i)));
        }
        //we let super handle the off and len
        super.write(sb.toString(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        char[] cString = new char[cbuf.length];
        for (int i = 0; i < cbuf.length; i++) {
            cString[i] = Character.toUpperCase(cbuf[i]);
        }
        //we let super handle the off and len
        super.write(cString, off, len);
    }

    @Override
    public void write(int c) throws IOException {
        super.write(Character.toUpperCase(c));
    }


}
