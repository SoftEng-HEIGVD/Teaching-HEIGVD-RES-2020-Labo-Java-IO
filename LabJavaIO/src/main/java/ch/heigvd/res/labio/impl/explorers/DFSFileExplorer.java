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

    // First I visit myself

    visitor.visit(rootDirectory);

    // Secondly I visit my child files

    File[] concreteFiles = rootDirectory.listFiles();

    // if there isn't any child files, come back in our exploration

    if(concreteFiles == null)
      return;

    Arrays.sort(concreteFiles);

    for(File childFile : concreteFiles) {
      if (childFile.isFile()) {
        visitor.visit(childFile);
      }else{
        explore(childFile, visitor);
      }
    }

  }

}
