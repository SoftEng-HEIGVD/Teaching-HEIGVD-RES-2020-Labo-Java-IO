package ch.heigvd.res.labio.quotes;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a "quote", with tags, the main text, a link and a
 * source. When using the QuoteClient, you retrieve instances of this class.
 *
 * @author Olivier Liechti
 */

public class Quote implements Serializable {

  /**
   * This is a class that describes the structure of the JSON payload that is
   * sent by the Chuck Norris API. The deserialization from JSON into Java is
   * handled automatically for us. This is something that we will see in the
   * AMT course next year.
   */
  public class Value {

    private long id;
    private String joke;
    private String[] categories;

    public long getId() {
      return id;
    }

    public void setId(long id) {
      this.id = id;
    }

    public String getJoke() {
      return joke;
    }

    public void setJoke(String joke) {
      this.joke = joke;
    }

    public String[] getCategories() {
      return categories;
    }

    public void setCategories(String[] categories) {
      this.categories = categories;
    }

  }

  /**
   * In the 2015 version of this lab, we were using a service called
   * "iheartquotes". The API was sending us a list of tags with every quote. We
   * don't have this from the Chuck Norris facts API, so we generate random tags
   * to have the data available.
   */
  private String[] tags;

  private String type;

  private Value value;

  /**
   * In the 2015 version of the lab, we were getting quotes with multiple lines. This
   * is important if we want to see one feature of the lab in action (line numbering).
   * The Chuck Norris API sends us one-line jokes, so we have added some code to
   * split the jokes in multiple lines (one space out of three is replaced by a new line
   * character)
   */
  private String quoteSplitInLines = null;

  public Quote() {
    this.tags = TagsGenerator.pickRandomTags();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Value getValue() {
    return value;
  }

  public void setValue(Value value) {
    this.value = value;
  }

  public List<String> getTags() {
    return Arrays.asList(tags);
  }

  
  public String getQuote() {

    /*
     * If it is the first time that a client asks us for the quote, then we
     * split the original joke in multiple likes and keep the result in the
     * instance variable "quotesSplitInLines".
     */
    if (quoteSplitInLines == null) {
      StringBuilder quote = new StringBuilder(value.getJoke());
      int nextSpace = quote.indexOf(" ");
      int counter = 0;
      while (nextSpace != -1) {
        counter = (counter + 1) % 3;
        if (counter == 0) {
          quote.setCharAt(nextSpace, '\n');
        }
        nextSpace = quote.indexOf(" ", nextSpace+1);
      }
      quoteSplitInLines = quote.toString();
    }
    return quoteSplitInLines;
  }

}
