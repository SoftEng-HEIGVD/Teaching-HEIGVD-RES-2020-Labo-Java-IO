package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits
 * all files in the directory and then moves into the subdirectories.
 *
 * @author Olivier Liechti & Moïn DANAI
 */
public class DFSFileExplorer implements IFileExplorer {

  /**
   * Explores the given {@code rootDirectory} in DFS, browsing its child
   * directories first and then its child files, in ascending alphabetical order.
   * <p>
   * Each leaf file is eventually visited by the {@code visitor}.
   *
   * @author Moïn DANAI
   *
   * @param rootDirectory root node
   * @param visitor       method to call upon node visitO
   */
  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    if (rootDirectory == null) {
      return;
    }

    // get children nodes
    File[] childrenDirs = rootDirectory.listFiles((File node) -> node.isDirectory()),
        childrenFiles = rootDirectory.listFiles((File node) -> !node.isDirectory());

    vistor.visit(rootDirectory);

    // visit directories
    if (childrenDirs != null) {
      Arrays.sort(childrenDirs);
      for (File dir : childrenDirs) {
        explore(dir, vistor);
      }
    }

    // visit files
    if (childrenFiles != null) {
      Arrays.sort(childrenFiles);
      for (File file : childrenFiles) {
        vistor.visit(file);
      }
    }
  }
}
