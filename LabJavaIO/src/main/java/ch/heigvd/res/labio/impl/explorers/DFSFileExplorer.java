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
    public void explore(File rootDirectory, IFileVisitor vistor) {
            vistor.visit(rootDirectory);
            if (rootDirectory.isDirectory()) {
                if (rootDirectory.list().length > 0) {
                    dfs(rootDirectory, vistor);
                }
            }
        }

        public void dfs(File rootDirectory, IFileVisitor vistor) {
            File[] directories = rootDirectory.listFiles(File::isDirectory);
            File[] files = rootDirectory.listFiles(File::isFile);

            if (directories != null) {
                Arrays.sort(directories);
            }
            if (files != null) {
                Arrays.sort(files);
            }

            for (File directory : directories) {
                vistor.visit(directory);
                dfs(directory, vistor);
            }
            for (File file : files) {
                vistor.visit(file);
            }
        }
    }
