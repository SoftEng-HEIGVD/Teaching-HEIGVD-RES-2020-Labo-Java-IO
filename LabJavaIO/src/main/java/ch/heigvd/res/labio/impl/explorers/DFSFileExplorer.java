package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.util.*;
import java.io.File;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti, Florian Mulhauser
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {
   // throw new UnsupportedOperationException("The student has not implemented this method yet.");

    // first visit
    visitor.visit(rootDirectory);
    // then solve recursively if it's a directory
    if (rootDirectory.isDirectory()) exploreDFS(rootDirectory,visitor);

  }

public void exploreDFS(File rD, IFileVisitor visitor){
    File[] files = rD.listFiles();

    if(files == null){
      return;
    } else {
      for (File f:
           files) {
        visitor.visit(f);
        if(f.isDirectory()){ //if it's a directory, go recursively

          exploreDFS(f, visitor); // recursive call
        }



      }
    }
}


}
