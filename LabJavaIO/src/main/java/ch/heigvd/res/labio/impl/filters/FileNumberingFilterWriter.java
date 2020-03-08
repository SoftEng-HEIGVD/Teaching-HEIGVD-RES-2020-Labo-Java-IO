package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti, Dupont Maxime
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int lineCount = 0;
  private char lastChar = '\0';
  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    this.write(str.toCharArray(), off , len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    for(int i = off; i < len + off; ++i){
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    //Tout le traitement se fait ici, les deux autres méthodes se contentent de rediriger
    String result = "";

    //On commence :
    if(lastChar == '\0'){
      result += ++lineCount + "\t";
      result +=((char) c);
    }else if (c == '\n'){
      result += ((char) c);
      result += ++lineCount + "\t";
    }else if (c == '\r'){
      lastChar = (char) c;
      return;
    }else if( lastChar == '\r'){
      result +=lastChar;
      if( c == '\n'){
        result +=((char) c) ;
        result +=++lineCount + "\t";
      }else{
        result += ++lineCount + "\t";
        result +=  ((char) c);
      }
    }else {
      result +=((char) c);
    }
    out.write(result);
    lastChar = (char) c;
  }

}
