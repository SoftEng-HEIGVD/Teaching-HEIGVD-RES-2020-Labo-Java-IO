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
 * @author Olivier Liechti, Diluckshan Ravindranathan
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {

    /// visit the root directory
    vistor.visit(rootDirectory);
    // Array of directory files
    File[] listOfFiles = rootDirectory.listFiles();

    if (listOfFiles == null){
      return;
    }
    Arrays.sort(listOfFiles);

    for(File file : listOfFiles){
      if(file.isFile()){
        vistor.visit(file);
      }
      else{
        explore(file, vistor);
      }
    }
  }

}
