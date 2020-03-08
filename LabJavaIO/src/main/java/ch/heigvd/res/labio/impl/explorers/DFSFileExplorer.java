package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.sql.ClientInfoStatus;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {
  /**
   * hen this method is invoked, it traverses the file system under the
   *    * rootDirectory directory. For each encountered file or directory, it
   *    * calls visitor.visit(file).
   *    *
   */
  private boolean firstIteration = true; // Used to visit very first root

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {
      // Get all files and directories in rootDirectory
      File[] listOfFilesAndDirectories = rootDirectory.listFiles();

      // Visit the root
      if (firstIteration) {
          visitor.visit(rootDirectory);
          firstIteration = false;
      }

      // Visit all other directories in depth
      if (listOfFilesAndDirectories != null) {
          for (File f : listOfFilesAndDirectories) {
              visitor.visit(f);
              explore(f, visitor);
          }
      }
  }
}
