package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;

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
  public void explore(File rootDirectory, IFileVisitor vistor) {

    vistor.visit(rootDirectory); // visit the current directory

    // get list of all files and directories present in root
    File[] listOfFilesAndDirectory = rootDirectory.listFiles();

    // do nothing if the root directory is empty
    if (listOfFilesAndDirectory == null) {
      return;
    }

    for (File file : listOfFilesAndDirectory)
      {
        // explore a directory
        if (file.isDirectory()) {
          explore(file, vistor);
        }
        // visit a file
        else {
          vistor.visit(file);
        }
      }
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
