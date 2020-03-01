package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.IOException;
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
        visitor.visit(rootDirectory);
       if (!rootDirectory.exists()) {
            return;
        }

        //File[] listOfFilesAndDirs = rootDirectory.listFiles();
        for (File file : rootDirectory.listFiles()) {
            //If file is file
            if (file.isFile()) {
                visitor.visit(file);
            }
        }
        for (File dir : rootDirectory.listFiles()) {

            //If file is directory
            if (dir.isDirectory()) {
                explore(dir, visitor);
            }
        }
    }
}
