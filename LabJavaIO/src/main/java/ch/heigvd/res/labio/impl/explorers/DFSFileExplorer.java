package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits
 * all files in the directory and then moves into the subdirectories.
 *
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  /**
   * Explores the given {@code rootDirectory} in DFS, browsing its child folders
   * first and then its child files, in ascending alphabetical order.
   * <p>
   * Each leaf file is eventually visited by the {@code visitor}.
   *
   * @author Moïn DANAI
   */
  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    File[] child_dirs = rootDirectory.listFiles((File node) -> node.isDirectory()),
        child_files = rootDirectory.listFiles((File node) -> !node.isDirectory());

    for (File dir : child_dirs) {
      explore(dir, vistor);
    }
    for (File file : child_files) {
      vistor.visit(file);
    }
  }
}
