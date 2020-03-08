package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti, Dupont Maxime
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    vistor.visit(rootDirectory); // on visite le dossier actuel, donc DFS pre-ordre, nous assure que chaque fichier est visité
    File[] subFiles = rootDirectory.listFiles(); // on liste les sous-fichiers

    //On va procéder de manière récursive, donc tout d'abord le cas basique
    if(subFiles == null){
      return; //basique, si on a pas de sous fichiers, on s'arrete
    }
    Arrays.sort(subFiles);
    //ensuite la partie récursive
    for (File f :subFiles){
      explore(f,vistor);
    }
  }
}
