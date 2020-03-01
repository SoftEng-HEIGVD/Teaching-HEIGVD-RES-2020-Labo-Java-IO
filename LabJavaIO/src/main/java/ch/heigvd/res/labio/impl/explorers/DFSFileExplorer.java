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
    public void explore(File rootDirectory, IFileVisitor visitor) {
      visitor.visit(rootDirectory);
      this.exploreRecursively(rootDirectory, visitor);
    }

    private void exploreRecursively(File rootDirectory, IFileVisitor visitor) {
        // Fetch all files in the current dir
        File[] filesInDir = rootDirectory.listFiles();
        if(filesInDir != null){
            // Sort the array to prevent test bug in some OS
            Arrays.sort(filesInDir);
            for(File file : filesInDir) {
                visitor.visit(file);
                // Recursive call on directory
                if(file.isDirectory()){
                    this.exploreRecursively(file, visitor);
                }
            }
        }
    }

}
