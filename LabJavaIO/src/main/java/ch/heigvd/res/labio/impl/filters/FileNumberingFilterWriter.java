package ch.heigvd.res.labio.impl.filters;
import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
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
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int nbLines = 0;
  private boolean newLine = true;
  private char lastChar = ' ';

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
      str = str.substring(off, off + len);
      String[] elements = Utils.getNextLine(str);
      while(!elements[0].equals("")) {
        //Si la dernière ligne est une fin de ligne commencement d'une nouvelle ligen
        if(newLine) {
          out.write(Integer.toString(++nbLines) + '\t' + elements[0]);
        }
        else {
          out.write(elements[0]);
          newLine = true;
        }
        //Utilisation de la fonction de partition de chaîne sur la deuxième partie de la chaîne
        elements = Utils.getNextLine(elements[1]);
      }
      //s'il n'y a pas eu de nouvelle ligne alors écriture de elements[1] seulement car la fonction termine
      //toujours par une ligne vide avec un numéro et une tabulation
      if(!newLine){
        out.write(elements[1]);
        newLine = true;
      }
      else {
        out.write(Integer.toString(++nbLines) + '\t' + elements[1]);
        newLine = false;
      }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    out.write(Arrays.toString(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    boolean newLine = false;
    //Permet de vérifier que la retour à la ligne Windows pour cela il faut vérifier le dernier caractère puis le
    //caractère actuel
    if(lastChar == '\r' && c == '\n') {
      //Enregistrement du caractère actuel comme carcatère de fin de ligne
      lastChar = (char)c;
    }
    else if(lastChar == '\n' || lastChar == '\r'){
      //Changement du lastChar pour ne pas rester avec à chaque fois une fin de ligne
      lastChar = ' ';
      newLine = true;
    }
    //Si une nouvelle ligne est desiré ajout d'une nouvelle ligne
    if(newLine || nbLines == 0){
      out.write(Integer.toString(++nbLines) + '\t');
    }
    //Si ce n'est pas une nouvelle ligne et que c est un caractère de fin de ligne stockage de sa valeur
    if(!newLine && (c == '\n' || c == '\r'))
      lastChar = (char)c;

    out.write(c);
  }

}
