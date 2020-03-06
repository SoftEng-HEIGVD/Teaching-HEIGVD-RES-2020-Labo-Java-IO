package ch.heigvd.res.labio.impl.filters;
import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
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
 * @author Stéphane Teixeira Carvalho
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int nbLines = 0; //Permet de garder le nombre de ligne écrite dans le fichier
  private char lastChar = ' ';
  private static final char TAB = '\t';

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

    /**
     * Fonction permettant d'écrire dans la sortie out une nouvelle ligne de type
     * NuméroDeLigne\t
     * @throws IOException
     */
  private void writeNewLine() throws IOException{
      out.write(Integer.toString(++nbLines) + TAB);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      str = str.substring(off, off + len);
      String[] elements = Utils.getNextLine(str);
      if(nbLines == 0){
          writeNewLine();
      }
      while(!elements[0].equals("")) {
          out.write(elements[0]);
          writeNewLine();
          elements = Utils.getNextLine(elements[1]);
      }
      out.write(elements[1]);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    this.write(new String(cbuf, off,len), 0, len);
  }

  @Override
  public void write(int c) throws IOException {
    //Si le dernier char est \r et que le char actuelle n'est pas un \n cela veut dire que c'est un retour à la ligne
    //Mac qui a été effectué
    if(nbLines == 0 || (lastChar == Utils.MACOS_ENDLINE && (char)c != Utils.LINUX_ENDLINE)){
        writeNewLine();
    }
    out.write(c);
    //Si le char actuel est un \n une nouvelle ligne doit être écrite
    if(c == Utils.LINUX_ENDLINE){
        writeNewLine();
    }
    lastChar = (char)c;
  }

}
