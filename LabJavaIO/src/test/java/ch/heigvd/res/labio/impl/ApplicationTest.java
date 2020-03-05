package ch.heigvd.res.labio.impl;

import ch.heigvd.res.labio.interfaces.IApplication;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * *** IMPORTANT WARNING : DO NOT EDIT THIS FILE ***
 * <p>
 * This file is used to specify what you have to implement. To check your work,
 * we will run our own copy of the automated tests. If you change this file,
 * then you will introduce a change of specification!!!
 *
 * @author Olivier Liechti
 */
public class ApplicationTest {

    private static final Logger LOG = Logger.getLogger(ApplicationTest.class.getName());

    private static int NUMBER_OF_QUOTES = 20;

    @BeforeAll
    public static void invokeApplication() throws IOException {
        IApplication application = new Application();
        application.fetchAndStoreQuotes(NUMBER_OF_QUOTES);
        application.processQuoteFiles();
    }

    @Test
    public void theApplicationShouldGenerateTheCorrectNumberOfQuoteFiles() {
        String[] extensions = {"utf8"};
        Collection<File> files = FileUtils.listFiles(new File(Application.WORKSPACE_DIRECTORY), extensions, true);
        assertEquals(NUMBER_OF_QUOTES, files.size());
    }

    @Test
    public void theApplicationShouldUseTheCorrectSyntaxToNameTheQuoteFiles() {
        String[] extensions = {"utf8"};
        Collection<File> files = FileUtils.listFiles(new File(Application.WORKSPACE_DIRECTORY), extensions, true);
        for (File file : files) {
            String filename = file.getName();
            Pattern pattern = Pattern.compile("quote-\\d*.utf8");
            Matcher matcher = pattern.matcher(filename);
            assertTrue(matcher.matches());
        }
    }

    @Test
    public void theApplicationShouldGenerateTheCorrectNumberOfOutputFiles() {
        String[] extensions = {"out"};
        Collection<File> files = FileUtils.listFiles(new File(Application.WORKSPACE_DIRECTORY), new IOFileFilter() {

            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".utf8.out");
            }

            @Override
            public boolean accept(File dir, String file) {
                return file.endsWith(".utf8.out");
            }
        }, TrueFileFilter.INSTANCE);
        assertEquals(NUMBER_OF_QUOTES, files.size());
    }

    @Test
    public void theApplicationShouldBeAbleToGenerateTheListOfFileNames() throws IOException {
        Application app = new Application();
        app.clearOutputDirectory();

        String rootDirectory = Application.WORKSPACE_DIRECTORY;
        FileUtils.deleteDirectory(new File(rootDirectory));
        new File(rootDirectory).mkdir();

        String dirA = rootDirectory + "/A";
        String fileA1 = dirA + "/fileA1.txt";
        String fileA2 = dirA + "/fileA2.txt";
        String dirB = rootDirectory + "/A/B";
        String dirC = rootDirectory + "/A/C";
        String fileC1 = dirC + "/fileC1.txt";
        String dirD = rootDirectory + "/A/C/D";
        String fileD1 = dirD + "/fileD1.txt";
        String fileD2 = dirD + "/fileD2.txt";
        new File(dirA).mkdirs();
        new File(dirB).mkdirs();
        new File(dirC).mkdirs();
        new File(dirD).mkdirs();
        new File(fileA1).createNewFile();
        new File(fileA2).createNewFile();
        new File(fileC1).createNewFile();
        new File(fileD1).createNewFile();
        new File(fileD2).createNewFile();

        StringWriter writer = new StringWriter();
        app.printFileNames(writer);
        String output = writer.toString();
        StringBuilder sb = new StringBuilder();

        sb.append(rootDirectory);
        sb.append("\n");
        sb.append(dirA);
        sb.append("\n");
        sb.append(dirB);
        sb.append("\n");
        sb.append(dirC);
        sb.append("\n");
        sb.append(dirD);
        sb.append("\n");
        sb.append(fileD1);
        sb.append("\n");
        sb.append(fileD2);
        sb.append("\n");
        sb.append(fileC1);
        sb.append("\n");
        sb.append(fileA1);
        sb.append("\n");
        sb.append(fileA2);
        sb.append("\n");

        // Bug fix in the original unit test by teacher
        String fileSeparator = File.separator;
        if (fileSeparator.equals("\\")) {
            fileSeparator = "\\\\";
        }
        Pattern p = Pattern.compile(fileSeparator);
        Matcher m = p.matcher(output);
        String outputWithUnixSeparator = m.replaceAll("/");

        String reference = sb.toString();
        System.out.println("REFERENCE : ");
        System.out.println(sb.toString());
        System.out.println("ACTUAL : ");
        System.out.println(output);
        boolean applicationReturnsValidPlatformSpecificFilePaths = (reference.equals(output));
        boolean applicationReturnsValidUnixFilePaths = (reference.equals(outputWithUnixSeparator));
        boolean applicationReturnsValidFilePaths = (applicationReturnsValidPlatformSpecificFilePaths || applicationReturnsValidUnixFilePaths);

        assertTrue(applicationReturnsValidFilePaths);

    }

}
