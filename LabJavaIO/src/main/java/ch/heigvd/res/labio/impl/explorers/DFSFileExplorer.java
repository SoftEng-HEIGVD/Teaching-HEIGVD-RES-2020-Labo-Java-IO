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
 * @author Olivier Liechti - Modified by Nicolas Müller on 07.03.2020
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {

    visitor.visit(rootDirectory);

    // No need to do anything more if it's not a directory
    if (!rootDirectory.isDirectory()) {

      return;
    }

    File[] files = rootDirectory.listFiles();

    // No need to do anything more if directory is empty
    if (files != null) {

      // Test sometimes fails without sorting
      Arrays.sort(files);

      for (File f : files) {

        if (f.isDirectory()) {

          explore(f, visitor);
        }
      }
    }
  }
}
