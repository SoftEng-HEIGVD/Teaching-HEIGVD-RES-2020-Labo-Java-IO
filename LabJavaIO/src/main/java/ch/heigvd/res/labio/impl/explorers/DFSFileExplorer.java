package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.ArrayList;
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
    // throw new UnsupportedOperationException("The student has not implemented this method yet.");
    // vérification si rootDirectory pointe bien sur quelque chose
    if (rootDirectory == null)
      return;

    // on visite notre root
    vistor.visit(rootDirectory);

    // on stock le listFiles dans un tableau de fichiers
    File[] childFiles = rootDirectory.listFiles();

    // si ce tableau est vide, on finit la récursion
    if(childFiles == null)
      return;

    // on trie le tableau (parce que sinon, le test ne passe pas)
    Arrays.sort(childFiles);

    // on fait le parcours en fonction de si c'est un fichier ou un répertoire
    for (File f : childFiles) {
      if (f.isDirectory())
        explore(f, vistor);
      else
        vistor.visit(f);
    }
  }

}
