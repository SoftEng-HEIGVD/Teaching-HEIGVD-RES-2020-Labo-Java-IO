package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  /**
   * {@inheritDoc}
   */
  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {

    // Note : instead of using a recursive implementation of DFS traversal,
    // we use a stack to offer an iterative implementation and explicitly
    // manage the recursion by ourselves.
    Stack<File> deque = new Stack<>();
    deque.push(rootDirectory);

    while (!deque.isEmpty()) {
      File current = deque.pop();
      visitor.visit(current);
      File[] children = current.listFiles();

      if (children != null) {
        Arrays.sort(children, Comparator.comparing(File::getName).reversed());
        for (File child : children) {
          deque.push(child);
        }
      }
    }
  }

}
