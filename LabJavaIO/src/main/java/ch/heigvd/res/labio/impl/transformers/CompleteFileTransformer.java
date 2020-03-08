package ch.heigvd.res.labio.impl.transformers;

import java.io.Writer;

import ch.heigvd.res.labio.impl.filters.FileNumberingFilterWriter;
import ch.heigvd.res.labio.impl.filters.UpperCaseFilterWriter;

/**
 * This class returns a writer decorated with two filters: an instance of the
 * UpperCaseFilterWriter and an instance of the FileNumberingFilterWriter. When
 * an instance of this class is passed to a file system explorer, it will
 * generate an output file with 1) uppercase letters and 2) line numbers at the
 * beginning of each line.
 *
 * @author Olivier Liechti & Moïn DANAI
 */
public class CompleteFileTransformer extends FileTransformer {

  /**
   * Decorates the given {@code writer} with {@link UpperCaseFilterWriter} first
   * and then {@link FileNumberingFilterWriter}.
   *
   * @param writer writer to decorate
   * @return Decorater writer
   */
  @Override
  public Writer decorateWithFilters(Writer writer) {
    writer = new FileNumberingFilterWriter(new UpperCaseFilterWriter(writer));
    return writer;
  }
}
