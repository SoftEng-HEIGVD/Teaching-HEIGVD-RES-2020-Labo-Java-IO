package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
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

    // DFS iterative Version

    Set<File> visited = new HashSet<>();

    // We use a PQ for sorting the file by their pathName
    PriorityQueue<File> stack = new PriorityQueue<>(new Comparator<File>() {
      @Override
      public int compare(File file, File t1) {
        return file.getPath().compareTo(t1.getPath());
      }
    });

    //source Directory
    stack.add(rootDirectory);

    while (stack.size() != 0){

      rootDirectory = stack.poll();

      if(!visited.contains(rootDirectory)){
        visited.add(rootDirectory);
        visitor.visit(rootDirectory);
      }

      try {
        for (File childFile : Objects.requireNonNull(rootDirectory.listFiles())) {
          if (!visited.contains(childFile)) {
            stack.add(childFile);
          }
        }
      }catch (NullPointerException ex){
        System.out.println(ex.getMessage());
      }
    }

  }

}
