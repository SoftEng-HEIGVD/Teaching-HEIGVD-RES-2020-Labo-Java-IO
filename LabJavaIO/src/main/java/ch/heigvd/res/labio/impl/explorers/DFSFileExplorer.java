package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.io.FileFilter;
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

      // let children = les dossiers enfants
      File[] childrenDirectories = rootDirectory.listFiles(new FileFilter() {
          @Override
          public boolean accept(File pathname) {
              return pathname.isDirectory();
          }

      });

      //Alphabetical order
      if(childrenDirectories != null) {
          Arrays.sort(childrenDirectories);


          // for (children)
          //    explore(dossier enfant)
          for (int i = 0; i < childrenDirectories.length; i++) {
              explore(childrenDirectories[i], visitor);
          }
      }

          // let childrenFiles = les fichiers enfants
          File[] childrenFiles = rootDirectory.listFiles(new FileFilter() {
              @Override
              public boolean accept(File pathname) {
                  return pathname.isFile();
              }
          });


      // Aplhabetical order
      if(childrenFiles != null) {
          Arrays.sort(childrenFiles);


          // for (childrenFiles)
          //    visit(childrenFile)
          for (int i = 0; i < childrenFiles.length; i++) {
              visitor.visit(childrenFiles[i]);
          }
      }
   }
}
