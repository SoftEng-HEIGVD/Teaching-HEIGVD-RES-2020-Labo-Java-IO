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
  private int lineNumber;
  private boolean beginning;
  private boolean newline;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineNumber = 0;
    beginning = true;
    newline = false;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    // throw new UnsupportedOperationException("The student has not implemented this method yet.");
    // fait appel au write char par char
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // throw new UnsupportedOperationException("The student has not implemented this method yet.");
    // fait appel au write de char
    for (int i = 0; i < len; ++i) {
      write(cbuf[off + i]);
    }
    // ajout de la dernière ligne (assez artificiel)
    write(0);
  }

  /**
   * Écrit un numéro de ligne suivi d'un tab à chaque nouvelle ligne
   * @throws IOException si erreur à l'écriture sur le flux
   */
  public void newline() throws IOException {
    out.write(++lineNumber + "\t");
  }

  @Override
  public void write(int c) throws IOException {
    // throw new UnsupportedOperationException("The student has not implemented this method yet.");
    // commence par indiquer si on est au début du fichier (donc quand la
    // classe vient d'être instanciée
    if (beginning) {
      beginning = false; // on change le flag de début de fichier
      newline(); // ajout nouvelle ligne
      out.write(c);
    } else {
      // si notre char est une nouvelle ligne ou retour chariot
      if (c == '\r' || c == '\n') {
        // on indique qu'on a une nouvelle ligne
        if (!newline)
          newline = true;
        out.write(c);
      } else {
        // si on a une nouvelle ligne
        if (newline)
          newline(); // ajout nouvelle ligne
        newline = false; // on change le flag de nouvelle ligne
        // comme on doit rajouter une nouvelle ligne en fin de ligne, on
        // vérifie si le dernier char est le char NUL
        if (c != 0)
          out.write(c);
      }
    }
  }

}
