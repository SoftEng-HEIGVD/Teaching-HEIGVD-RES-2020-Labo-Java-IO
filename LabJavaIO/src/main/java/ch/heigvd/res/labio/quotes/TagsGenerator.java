package ch.heigvd.res.labio.quotes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This utility class was added for the 2016 version of the lab. Last year, the "iheartquotes" API was providing us
 * with a list of tags for every quote. This is not the case for the Chuck Norris API, so we have added some code
 * to generate random tags. That is important because the tags are used to create the file system hierarchy in the
 * application code.
 * 
 * @author Olivier Liechti
 */
public class TagsGenerator {

  private final static String[] availableTags = {"funny", "popular", "movie", "internet", "joke", "hilarious", "geek"};

  public static String[] pickRandomTags() {
    int numberToPick = (int) (Math.random() * (availableTags.length + 1)); // we will add between 0 and availableTags.length tags for this quote
    String[] result = new String[numberToPick];
    List<String> candidates = new ArrayList<>(Arrays.asList(availableTags));
    for (int i = 0; i < numberToPick; i++) {
      Collections.shuffle(candidates);
      result[i] = candidates.remove(0);
    }
    return result;
  }

}
