package ch.heigvd.res.labio.quotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.logging.Logger;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * This is a technical class, used to transform JSON payloads received in 
 * HTTP responses into Java objects. You don't need to modify, nor to use
 * this file. It is used automatically when fetching quotes from a
 * web service.
 * 
 * @author Olivier Liechti
 */
@Provider
public class SimpleObjectMapperProvider implements ContextResolver<ObjectMapper> {

  private static final Logger LOG = Logger.getLogger(SimpleObjectMapperProvider.class.getName());

  final ObjectMapper defaultObjectMapper;

  public SimpleObjectMapperProvider() {
    defaultObjectMapper = createDefaultMapper();
  }

  @Override
  public ObjectMapper getContext(Class<?> type) {
    return defaultObjectMapper;
  }

  private static ObjectMapper createDefaultMapper() {
    final ObjectMapper result = new ObjectMapper();
    result.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    return result;
  }

}
