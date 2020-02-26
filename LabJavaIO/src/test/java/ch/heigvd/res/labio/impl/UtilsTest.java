package ch.heigvd.res.labio.impl;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 *
 * @author Olivier Liechti
 */
public class UtilsTest {

  private static final Logger LOG = Logger.getLogger(UtilsTest.class.getName());

  @Test
  public void itShouldBePossibleToGetANewLineOnWindows() {
    String lines = "hello\r\nworld\r\n";
    String[] r1 = Utils.getNextLine(lines);
    String[] e1 = {"hello\r\n", "world\r\n"};
    assertArrayEquals(e1, r1);
    String[] r2 = Utils.getNextLine(r1[1]);
    String[] e2 = {"world\r\n", ""};
    assertArrayEquals(e2, r2);
  }

  @Test
  public void itShouldBePossibleToGetANewLineOnMacOS9() {
    String lines = "hello\rworld\r";
    String[] r1 = Utils.getNextLine(lines);
    String[] e1 = {"hello\r", "world\r"};
    assertArrayEquals(e1, r1);
    String[] r2 = Utils.getNextLine(r1[1]);
    String[] e2 = {"world\r", ""};
    assertArrayEquals(e2, r2);
  }

  @Test
  public void itShouldBePossibleToGetANewLineOnUnix() {
    String lines = "hello\nworld\n";
    String[] r1 = Utils.getNextLine(lines);
    String[] e1 = {"hello\n", "world\n"};
    assertArrayEquals(e1, r1);
    String[] r2 = Utils.getNextLine(r1[1]);
    String[] e2 = {"world\n", ""};
    assertArrayEquals(e2, r2);
  }

  @Test
  public void itShouldReturnALineOnlyIfThereIsANewLineCharacterAtTheEnd() {
    String line = "this is a line without a new line character at the end";
    String[] r = Utils.getNextLine(line);
    String[] e = {"", line};
    assertArrayEquals(e, r);
  }

}
