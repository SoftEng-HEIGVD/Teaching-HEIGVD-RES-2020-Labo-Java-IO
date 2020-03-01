package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
    //In order to pass the first test we need to check if a root directory is existing or empty
    try {
      if (!rootDirectory.exists()) {
        visitor.visit(rootDirectory);
        throw new IOException("Directory is empty or not existing");
      }
    } catch (IOException exDFS) {
      exDFS.getMessage();
    }

    File[] listOfFilesAndDirs = rootDirectory.listFiles();
    if (listOfFilesAndDirs != null) { //List of files and dirs is not empty
      for (File file : listOfFilesAndDirs) {
        //If file is directory we need to go inside the hierarchy (Recursive call of explore)
        if (file.isDirectory()) {
          explore(file, visitor);
        }
        //If file is file
        else {
          visitor.visit(file);
        }
      }
    }
  }
}
