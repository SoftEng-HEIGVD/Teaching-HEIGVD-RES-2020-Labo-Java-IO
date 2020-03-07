package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

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

    if(rootDirectory == null){
      return ;
    }

    vistor.visit(rootDirectory);

    if(rootDirectory.listFiles()==null){
      return;
    }
    
    ArrayList<File> dir = new ArrayList<>();
    ArrayList<File> notDir = new ArrayList<>();

    for(File f: rootDirectory.listFiles()){
      if(f.isDirectory()){
        dir.add(f);
      }
      else{
        notDir.add(f);
      }
    }

    for(File d: dir){
      explore(d,vistor);
    }

    for(File f: notDir){
      vistor.visit(f);
    }


  }

}
