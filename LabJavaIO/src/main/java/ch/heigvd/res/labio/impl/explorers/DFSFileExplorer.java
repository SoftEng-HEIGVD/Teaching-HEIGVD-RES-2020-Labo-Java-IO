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
 * @author Olivier Liechti, Ludovic Bonzon
 */
public class DFSFileExplorer implements IFileExplorer {

    @Override
    public void explore(File rootDirectory, IFileVisitor visitor) {
        // Visit the root directory
        visitor.visit(rootDirectory);

        // Obtain a list of all files and directories in the current root directory
        File[] filesAndDirsList = rootDirectory.listFiles();

        // Assert that the list of files isn't empty
        if(filesAndDirsList != null) {
            // Sort the list of files and dirs to match the tests
            Arrays.sort(filesAndDirsList);
            // Go through all files and dirs
            for(File f : filesAndDirsList) {
                // Explore them if they're dirs
                if(f.isDirectory())
                    explore(f, visitor);
                // Or visit them if they're files
                else
                    visitor.visit(f);
            }
        }
    }

}
