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
        if (!rootDirectory.exists()) {
            return;
        }

        //Sort is necessary for DFS to work as expected.
        //List of directories
        File[] dirs = rootDirectory.listFiles(File::isDirectory);
        if (dirs.length != 0) {
            Arrays.sort(dirs);
        }
        //List of files
        File[] files = rootDirectory.listFiles(File::isFile);
        if (dirs.length != 0) {
            Arrays.sort(files);
        }
        for (File dir : dirs) {
            explore(dir, vistor);
        }
        for (File file : files) {
            vistor.visit(file);
        }
    }
}