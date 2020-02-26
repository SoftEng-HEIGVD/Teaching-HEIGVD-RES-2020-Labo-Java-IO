package ch.heigvd.res.labio.quotes;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * This class provides a method to invoke a web service and to receive a quote.
 * The implementation uses the jersey framework, but you do not need to worry
 * about that. You do not need to modify this file.
 * 
 * You will use this class to fetch quotes, which you will then save on the
 * file system.
 * 
 * @author Olivier Liechti
 */
public class QuoteClient {

  /*
   * This has changed in the 2016 version of the lab. We were using the "itheardquotes" API, which is now down. We have
   * replaced it with another API that generates random jokes.
  */
    static String WEB_SERVICE_ENDPOINT = "http://api.icndb.com/jokes/random?firstName=Olivier&lastName=Liechti&escape=javascript";

  /**
   * Use this method to invoke the iheartquotes.com web service and receive
   * an instance of a Quote.
   * 
   * @return an instance of Quote, with values provided by the web service
   */
  public Quote fetchQuote() {
    Client client = ClientBuilder.newBuilder()
      .register(JacksonFeature.class)
      .register(SimpleObjectMapperProvider.class)
      .build();
    WebTarget target = client.target(WEB_SERVICE_ENDPOINT);
    Invocation.Builder invocationBuilder = target.request();
    Response response = invocationBuilder.get();
    Quote quote = response.readEntity(Quote.class);
    return quote;
  }

}
