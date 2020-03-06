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
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int nbLines = 1; //Le numéro de ligne commence à 1
  private int prevChar = 0;
  private static final char NEW_LINE = '\n';
  private static final char TAB = '\t';
  private static final char RETURN = '\r';

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    //String to array of char
    write(str.toCharArray(),off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //Array of char to single char
    for(int i = off; i< off + len;i++){
      write(cbuf[i]);
    }
  }
  @Override
  public void write(int c) throws IOException {
    final String STR_LINE_NB = String.valueOf(nbLines);

    //Ici, si c'est la première ligne OU si le caractère ne correpsond pas au séparateur de ligne '\n'
    // Dans le cas de MAC, il est également nécessaire de vérifier si le caractère précédent est un return '\r'
    if(nbLines == 1 || prevChar == RETURN && c != NEW_LINE){
      // Ecritur du numéro de ligne au début du text traité
      super.write(STR_LINE_NB,0,String.valueOf(nbLines).length());
      // Ajout d'une tabulation après chaque numéro de ligne et incrémentation
      super.write(TAB);
      ++nbLines;
    }
    // Ecriture du caractère traité à la suite
    super.write(c);

    // Verification nouvelle ligne sous Win/Unix
    if(c == NEW_LINE){
      // Ecriture du numéro de ligne, d'un tabulation et incrémentation du nb de lignes
      super.write(STR_LINE_NB, 0, String.valueOf(nbLines).length());
      super.write(TAB);
      ++nbLines;
    }
    // enregistrement du car traité précédement pour le prochain appel de la fonction
    prevChar = c;
  }
}
