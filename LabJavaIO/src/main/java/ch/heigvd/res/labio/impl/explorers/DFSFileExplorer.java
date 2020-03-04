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

    // explore the content of the given root directory
    this._explore(rootDirectory, vistor);
  }

  private void _explore(File root, IFileVisitor vistor) {
    File[] content = root.listFiles();

    // if the given root doesn't have any children
    // we don't need to continue
    if (content == null)
      return;

    // sort the File array as it may cause some issues
    Arrays.sort(content);

    for (File file : content) {
      vistor.visit(file);

      // if the current file is a directory
      // explore the children
      if (file.isDirectory())
        this._explore(file, vistor);
    }
  }

}
