package ch.heigvd.res.labio.impl.transformers;

import ch.heigvd.res.labio.impl.filters.FileNumberingFilterWriter;
import ch.heigvd.res.labio.impl.filters.UpperCaseFilterWriter;

import java.io.Writer;

/**
 * This class returns a writer decorated with two filters: an instance of
 * the UpperCaseFilterWriter and an instance of the FileNumberingFilterWriter.
 * When an instance of this class is passed to a file system explorer, it will
 * generate an output file with 1) uppercase letters and 2) line numbers at the
 * beginning of each line.
 * 
 * @author Olivier Liechti
 */
public class CompleteFileTransformer extends FileTransformer {

  @Override
  public Writer decorateWithFilters(Writer writer) {
    /*
     * If you uncomment the following line (and get rid of th 3 previous lines...), you will restore the decoration 
     * of the writer (connected to the file. You can see that you first decorate the writer with an UpperCaseFilterWriter, which you then
     * decorate with a FileNumberingFilterWriter. The resulting writer is used by the abstract class to write the characters read from the
     * input files. So, the input is first prefixed with line numbers, then transformed to uppercase, then sent to the output file.f
     */
    writer = new FileNumberingFilterWriter(new UpperCaseFilterWriter(writer));
    return writer;
  }

}
