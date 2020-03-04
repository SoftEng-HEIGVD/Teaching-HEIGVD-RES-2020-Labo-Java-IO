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
 * @author Olivier Liechti
 * @author Quentin Saucy
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineCounter=1;
    prevChar = '\0';
  }
  private int lineCounter ;
  private char prevChar ;

  @Override
  public void write(String str, int off, int len) throws IOException {
      for (int i = off; i<len+off;i++)
        this.write(str.charAt(i));

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String str = new String(cbuf,off,len);
    this.write(str,0,len);
  }

  @Override
  public void write(int c) throws IOException {

    String tmp ="";
    if(prevChar=='\0'){
      tmp+=lineCounter+"\t";
      tmp +=(char)c;
      lineCounter++;

    }else if(c =='\r'){
      prevChar =(char)c;
      return;
    }
    else if (prevChar =='\r'){
      tmp += prevChar;
      if(c =='\n'){
        tmp += (char)c;
        tmp += lineCounter + "\t";
      }else{
        tmp += lineCounter + "\t";
        tmp += (char)c;
      }
      lineCounter++;

    }else if(c=='\n') {
      tmp += (char) c;
      tmp += lineCounter + "\t";
      lineCounter++;
    }
    else
      tmp+=(char)c;
    out.write(tmp);
    prevChar =(char)c;
  }

}
