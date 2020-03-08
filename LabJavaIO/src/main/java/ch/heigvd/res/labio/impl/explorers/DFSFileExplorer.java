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
      if (rootDirectory != null) {

        vistor.visit(rootDirectory);
        File[] childFiles = rootDirectory.listFiles();
        if (childFiles != null){
        Arrays.sort(childFiles);
        for (File f : childFiles) {
          if (f.isDirectory()) {
            explore(f, vistor);
          } else
            vistor.visit(f);
          }
        } else { return; }
      } else {
        return;
      }
    }
}
