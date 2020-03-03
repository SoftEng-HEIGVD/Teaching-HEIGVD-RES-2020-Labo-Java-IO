package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

        // stock all the sub directory
        File[] subdirectoryArray = rootDirectory.listFiles();
        List<File> listFiles;

        // if the root directory had children
        if (subdirectoryArray != null) {
            // Stock as a array and ordered them lexicographically
            listFiles = Arrays.asList(subdirectoryArray);
            Collections.sort(listFiles);

            for (File file : listFiles) {
                if (file.isDirectory()) {
                    explore(file, vistor);
                } else {
                    vistor.visit(file);
                }
            }
        }

    }

}
