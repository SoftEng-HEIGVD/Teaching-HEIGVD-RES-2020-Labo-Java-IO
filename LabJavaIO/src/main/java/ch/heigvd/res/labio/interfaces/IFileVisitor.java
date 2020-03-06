package ch.heigvd.res.labio.interfaces;

import java.io.File;

/**
 * This interface is used together with the IFileExplorer interface. It defines
 * a method that is called for each file and directory during the traversal performed
 * by an implementation of the IFileExplorer interface.
 * 
 * @author Olivier Liechti
 */
public interface IFileVisitor {
  
  /**
   * This method is called by an instance of IFileExplorer during the traversal
   * of a file system (it is called for each file and directory encountered during
   * the traversal).
   * 
   * @param file the current file or directory visited by the IFileExplorer instance
   */
  public void visit(File file);
  
}
