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
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    //We need to visit all the fils in the directory and then explore all the sub-directory
    //We may want to do all in one loop and put the directory in a list but for now we just iterate twice the all directory
    File[] files = rootDirectory.listFiles();
    if(files == null){
        return;
    }
    //Visit (with the visitor) all the files in the directory
    for(File file : files){
        vistor.visit(file);
    }
    //For each subdirectory, explore them :
    for(File file : rootDirectory.listFiles()){
        if(file.isDirectory()){
            explore(file,vistor);
        }
    }
  }
}
