package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.Objects;

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
    // time to fixe this
    // throw new UnsupportedOperationException("The student has not implemented this method yet.");

    // the DFS algorithm recursive version
    // TODO implement iterative version of DFS

    for (File childFile : Objects.requireNonNull(rootDirectory.listFiles())) {
      if (!childFile.isFile())
        explore(childFile, visitor);
      visitor.visit(childFile);
    }


  }

}
