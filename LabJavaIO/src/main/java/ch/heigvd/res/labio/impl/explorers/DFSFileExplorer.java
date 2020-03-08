package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
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
  public void explore(File rootDirectory, IFileVisitor vistor) throws IOException {

    vistor.visit(rootDirectory);

    if(!rootDirectory.isDirectory()){

      return;
    }

    File[] fileList = rootDirectory.listFiles();

    Arrays.sort(fileList);

    if(fileList != null){

      for(int i = 0; i < fileList.length; i++){

        if(fileList[i].isDirectory()){

          this.explore(fileList[i], vistor);
        }
      }
    }

//    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
