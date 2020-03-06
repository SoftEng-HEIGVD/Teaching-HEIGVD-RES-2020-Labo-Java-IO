package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 * @author Stéphane Teixeira Carvalho
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    //Met sous forme de tableau les fichiers ou dossiers contenu dans le rootDirectory
    File[] listOfFilesAndDirectory = rootDirectory.listFiles();
    vistor.visit(rootDirectory);
    //Si listFiles() renvoie un tableau null cela signifie que le dossier est vide ou que c'est un fichier.
    if (listOfFilesAndDirectory == null) {
      return;
    }
    //Parcours des fichiers du dossier
    for (File file : listOfFilesAndDirectory)
    {
        explore(file,vistor);
    }
  }

}
