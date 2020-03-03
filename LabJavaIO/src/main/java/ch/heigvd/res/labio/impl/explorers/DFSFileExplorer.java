package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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
    if(rootDirectory.isDirectory()){
      vistor.visit(rootDirectory);
      exploreDFS(rootDirectory, vistor);
    }
  }

  public void exploreDFS(File rootDirectory, IFileVisitor vistor) {
    if(rootDirectory.isDirectory()){
      File[] listFiles = rootDirectory.listFiles();
      if(listFiles != null){
        Arrays.sort(listFiles, NameFileComparator.NAME_COMPARATOR);
        for(File file : listFiles){
          if(file.isDirectory()){
            vistor.visit(file);
            exploreDFS(file, vistor);
          }else{
            vistor.visit(file);
          }
        }
      }
    }
  }
}
