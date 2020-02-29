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

    // We can only have directories in the stack.
    deque.push(rootDirectory);

    while (!deque.isEmpty()) {

      File current = deque.pop();
      File[] children = current.listFiles();

      // Visit the current directory.
      visitor.visit(current);

      if (children != null) {

        // The ordering of the files read by the listFiles() method is not
        // deterministic, so we have to force it to be done in a sorted
        // order to pass the unit tests.
        Arrays.sort(children, Comparator.comparing(File::getName).reversed());

        // Visit all the direct children, and push the
        // subdirectories onto the stack.
        for (File child : children) {
          if (child.isDirectory()) {
            deque.push(child); // Defer visit to the next iteration.
          } else {
            visitor.visit(child);
          }
        }
      }
    }
  }

}
