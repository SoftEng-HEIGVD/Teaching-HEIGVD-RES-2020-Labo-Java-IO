package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 *
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {

    visitor.visit(rootDirectory);

    if (!rootDirectory.exists()) {
      return; // recursion end
    }

    File[] files = rootDirectory.listFiles(); // all files and folders in root directory

    Arrays.sort(files);

    for (File file : files) {
      if (file.isFile()) {
        visitor.visit(file);
      } else if (file.isDirectory()) { // we don't just use the else, because maybe there is something other than file and directory, just to be sure
        explore(file, visitor);
      }
    }
  }
}
