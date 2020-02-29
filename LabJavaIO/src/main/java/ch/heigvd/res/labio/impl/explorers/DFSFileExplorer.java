package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.impl.Application;
import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.util.Collection;
import java.util.Objects;

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

    if (!rootDirectory.exists()) {
      vistor.visit(rootDirectory);
      return;
    }
    String[] extensions = {"utf8"};
    Collection<File> files = FileUtils.listFilesAndDirs(rootDirectory,new WildcardFileFilter("*.*"), new WildcardFileFilter("*"));

    for (File file : files) {
        vistor.visit(file);
    }

  }

}
