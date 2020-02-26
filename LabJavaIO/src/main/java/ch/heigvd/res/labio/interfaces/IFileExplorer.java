package ch.heigvd.res.labio.interfaces;

import java.io.File;

/**
 * This interface is used to perform one operation on each element (file and
 * directory) of a file system tree. The implementation of the interface decides
 * in which order the file system should be explored (depth-first, breadth-first,
 * etc). 
 * 
 * The implementation of this interface must call the visitor.visit(file) method 
 * for each file system node it encounters during the visit.
 * 
 * @author Olivier Liechti
 */
public interface IFileExplorer {
  
  /**
   * When this method is invoked, it traverses the file system under the
   * rootDirectory directory. For each encountered file or directory, it
   * calls visitor.visit(file).
   * 
   * @param rootDirectory the directory where to start the traversal
   * @param vistor defines the operation to be performed on each file
   */
  public void explore(File rootDirectory, IFileVisitor vistor);
  
}
