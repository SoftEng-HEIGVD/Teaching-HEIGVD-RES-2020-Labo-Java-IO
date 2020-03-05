package ch.heigvd.res.labio.impl.transformers;

import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This abstract class implements the IFileVisitor interface and has the responsibility
 * to open an input text file, to read its content, to apply a number of transformations
 * (via filters) and to write the result in an output text file.
 * <p>
 * The subclasses have to implement the decorateWithFilters method, which instantiates
 * a list of filters and decorates the output writer with them.
 *
 * @author Olivier Liechti
 * @author Ludovic Bonzon
 */
public abstract class FileTransformer implements IFileVisitor {

    private static final Logger LOG = Logger.getLogger(FileTransformer.class.getName());
    private final List<FilterWriter> filters = new ArrayList<>();
    private static final int BUFFERSIZE = 8192;

    /**
     * The subclasses implement this method to define what transformation(s) are
     * applied when writing characters to the output writer. The visit(File file)
     * method creates an output file and creates a corresponding writer. It then
     * calls decorateWithFilters and passes the writer as argument. The method
     * wraps 0, 1 or more filter writers around the original writer and returns
     * the result.
     *
     * @param writer the writer connected to the output file
     * @return the writer decorated by 0, 1 or more filter writers
     */
    public abstract Writer decorateWithFilters(Writer writer);

    @Override
    public void visit(File file) {
        if(!file.isFile()) {
            return;
        }
        try {
            Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            Writer writer = new OutputStreamWriter(new FileOutputStream(file.getPath() + ".out"), "UTF-8"); // the bug fix by teacher
            writer = decorateWithFilters(writer);

            /*
             * There is a missing piece here: you have an input reader and an ouput writer (notice how the
             * writer has been decorated by the concrete subclass!). You need to write a loop to read the
             * characters and write them to the writer.
             */

            // Defines a char buffer and a length value for the text to read and write
            char[] buffer = new char[BUFFERSIZE];
            int length;

            // While there's still something to read, we write it with the two filters applied
            while((length = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, length);
            }

            reader.close();
            writer.flush();
            writer.close();
        } catch(IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

}
