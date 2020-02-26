package ch.heigvd.res.labio.interfaces;

import java.io.IOException;

/**
 *
 * @author Olivier Liechti
 */
public interface IApplication {

  /**
   * This method uses the QuoteClient and invokes it method several times to retrieve
   * instances of the Quote class. For each quote returned by the client, this class
   * stores its text in a new file. The algorithm is the following:
   * 
   * 1. Iterate numberOfQuotes times
   * 1.1. Invoke the QuoteClient to retrieve a Quote instance (q)
   * 1.2. q.getTags returns a list of tags; iterate over this list
   * 1.2.1. For each tag, create a new directory if it does not exist yet
   * 1.3. create a file named "quote-n.utf8" (n is the counter for the main loop)
   * 1.4. store the string returned by quote.getQuote() in this file
   * 
   * You will end up with a file system like this one:
   * ./workspace/quotes/codehappy
   * ./workspace/quotes/codehappy/quote-1.utf8
   * ./workspace/quotes/codehappy/quote-10.utf8
   * ./workspace/quotes/codehappy/quote-3.utf8
   * ./workspace/quotes/codehappy/quote-5.utf8
   * ./workspace/quotes/codehappy/quote-8.utf8
   * ./workspace/quotes/codehappy/quote-9.utf8
   * ./workspace/quotes/fortunes
   * ./workspace/quotes/fortunes/bryce_nesbitt_84
   * ./workspace/quotes/fortunes/bryce_nesbitt_84/quote-6.utf8
   * 
   * @param numberOfQuotes
   * @throws IOException
   */
  public void fetchAndStoreQuotes(int numberOfQuotes) throws IOException;

  public void processQuoteFiles() throws IOException;
}
