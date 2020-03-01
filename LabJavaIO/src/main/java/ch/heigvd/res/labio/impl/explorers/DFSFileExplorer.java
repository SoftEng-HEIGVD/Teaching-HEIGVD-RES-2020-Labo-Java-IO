package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.io.FileInputStream;
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
    // time to fixe this

    // the DFS algorithm recursive version
    // TODO implement iterative version of DFS

    //iterative Version
    Set<File> visited = new HashSet<>();

    Stack<File> stack = new Stack<>();

    //source Directory
    stack.push(rootDirectory);

    while (!stack.empty()){

      rootDirectory = stack.peek();
      stack.pop();

      if(!visited.contains(rootDirectory)){
        visited.add(rootDirectory);
        visitor.visit(rootDirectory);
      }

      try {
        for (File childFile : Objects.requireNonNull(rootDirectory.listFiles())) {
          if (!visited.contains(childFile)) {
            stack.push(childFile);
          }
        }
      }catch (NullPointerException ex){
        System.out.println(ex.getMessage());
      }
    }

  }

}
