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
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    vistor.visit(rootDirectory);
    File[] directories = rootDirectory.listFiles(File::isDirectory);
    File[] files = rootDirectory.listFiles(File::isFile);

    /* chek if root directory exist*/
    if (!rootDirectory.exists())
      return;

    if (directories.length != 0) {
      /* Directories*/
      Arrays.sort(directories);
      /* Files */
      Arrays.sort(files);
    }
   /*Dfs vist Directories */
    for (File dir : directories)
      explore(dir, vistor);
    /* DFS vist files*/
    for (File file : files)
      vistor.visit(file);
  }

}
