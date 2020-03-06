package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 *
 * @author Olivier Liechti
 */
public class DFSFileExplorerTest {

  private static final Logger LOG = Logger.getLogger(DFSFileExplorerTest.class.getName());

  @Test
  public void dfsExplorerShouldWork() {
    List<String> dfsNodes = generateTestTree(5, 5, 5);

    final List<String> directories = new ArrayList<>();
    IFileExplorer explorer = new DFSFileExplorer();

    explorer.explore(new File("./fs-test"), new IFileVisitor() {
      @Override
      public void visit(File file) {
        directories.add(file.getName());
      }
    });
    assertArrayEquals(dfsNodes.toArray(), directories.toArray());
  }

  @Test
  public void dfsExplorerShouldWorkWhenThereIsNoFile() {
    List<String> dfsNodes = generateTestTree(0, 0, 0);

    final List<String> directories = new ArrayList<>();
    IFileExplorer explorer = new DFSFileExplorer();

    explorer.explore(new File("./fs-test"), new IFileVisitor() {
      @Override
      public void visit(File file) {
        directories.add(file.getName());
      }
    });
    assertArrayEquals(dfsNodes.toArray(), directories.toArray());
  }
  
  private List<String> generateTestTree(int levels, int maxChildrenFolders, int maxChildrenFiles) {
    List<String> dfsNodes = new ArrayList<>();  
    File dir = new File("./fs-test");
    try {
      FileUtils.deleteDirectory(dir);
    } catch (IOException ex) {
      LOG.log(Level.SEVERE, "Could not delete {0} : {1}", new Object[]{dir, ex.getMessage()});
    }
    dfsNodes.add(dir.getName());
    generateLevel(dir, 0, levels, maxChildrenFolders, maxChildrenFiles, dfsNodes);
    return dfsNodes;
  }

  private void generateLevel(File dir, int level, int maxLevels, int maxChildrenFolders, int maxChildrenFiles, List<String> dfsNodes) {
    int childrenFolders = (int) (Math.random() * maxChildrenFolders);
    int childrenFiles = (int) (Math.random() * maxChildrenFiles);

    for (int i = 0; i < childrenFolders; i++) {
      String dirName = dir.getName() + "." + (i + 1);
      dfsNodes.add(dirName);
      File newDir = new File(dir, dirName);
      newDir.mkdirs();
      if (level < maxLevels) {
        generateLevel(newDir, level + 1, maxLevels, maxChildrenFolders, maxChildrenFiles, dfsNodes);
      }
    }
  }
}
