/*
 *    Title: Words Search Application
 *    Author: Guillem Nicolau Alomar Sitjes
 *    Date: June 18th, 2017
 *    Code version: 0.1
 *    Availability: Public
 */
package textindexer.Indexers;

import textindexer.IndexingApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TextIndexer {

    final static Logger LOGGER = Logger.getLogger(IndexingApp.class.getName());
    final static textindexer.Tools.Timer timer = new textindexer.Tools.Timer();

    /**
     * This method returns a string array with the words found
     * in a given line, splitted by the most common word delimiters.
     * Input:
     *     - String line: a text line containing words.
     */
    public static String[] splitLine(String line) {
        return line.split(";|,|\\:|-|\\.|\\ ");
    }

    /**
     * The increase_position checks if a key already has been stored in the words_found
     * map, and then increases the value by one (if yes) or sets it to 1 (if no).
     * Input:
     *     - String key: position that needs to be increased.
     *     - HashMap<String, Double> words_found: map with the current num of words
     *     found per file.
     */
    static void increase_position(String key, HashMap<String, Double> words_found){
        if (words_found.containsKey(key)) {
            words_found.put(key, 1.00 + words_found.get(key));
        } else {
            words_found.put(key, 1.00);
        }
    }

    /**
     * This method prints an ordered list of the top ten files that contain at
     * least one word of the search.
     * Input:
     *     - HashMap<String, Double> words_found: a map with all the files
     *     followed by the number of words of the search that contain.
     *     - double numWords: total words of the search
     */
    static void visualize_results(HashMap<String, Double> words_found, double numWords){
        int processed = 0;
        List<String> processedFiles = new ArrayList<>();
        Double max = 0.0;
        String maxFile = "";
        while ((processed < words_found.size()) && (processed < 10)) { //until all relevant files have been processed, up to a maximum of 10
            for (Map.Entry<String, Double> entry : words_found.entrySet()) {
                if ((entry.getValue() >= max) && (!processedFiles.contains(entry.getKey()))) {
                    max = entry.getValue();   //This is done in order to show the results in descending order.
                    maxFile = entry.getKey(); //Current max value and it's file are saved.
                }
            }
            System.out.println(maxFile + " : " + max / numWords * 100 + "%");
            processedFiles.add(maxFile);
            processed++;
            max = 0.0;
            maxFile = "";
        }
    }
}
