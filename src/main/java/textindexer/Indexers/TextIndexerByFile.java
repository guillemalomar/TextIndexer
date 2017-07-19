/*
 *    Title: Words Search Application
 *    Author: Guillem Nicolau Alomar Sitjes
 *    Date: June 18th, 2017
 *    Code version: 0.1
 *    Availability: Public
 */
package textindexer.Indexers;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

public class TextIndexerByFile extends TextIndexer{

    private static Map<String, HashSet<Integer>> fileWords = new HashMap<>();

    /**
     * The text_indexer_by_file method stores a hashcode of every word
     * in our data structure.
     * Input:
     *     - File[] listOfFiles: list of the files in the files directory.
     *     - String folder_path: the path to the files directory.
     */
    public static void text_indexer_by_file(File[] listOfFiles, String folder_path) {
        System.out.println("----------------------------");
        System.out.println("Indexing by file ...");
        timer.startTime();
        LOGGER.setLevel(Level.WARNING);
        int words_in_file;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                TextIndexerByFile.fileWords.put(file.getName(), new HashSet<>());
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader("/" + folder_path + "/" + file.getName()));
                    String line = br.readLine();
                    words_in_file = 0;
                    while (line != null) {
                        String[] split = splitLine(line);
                        for (String word : split) {
                            word_indexer(file.getName(), word);
                            words_in_file++;
                        }
                        line = br.readLine();
                    }
                    LOGGER.info("Number of words in file " + file.getName() + ":" + words_in_file);
                } catch (FileNotFoundException e) {
                    LOGGER.warning("Error opening file:" + e);
                } catch (IOException e) {
                    LOGGER.warning("Error reading line:" + e);
                } finally {
                    try {
                        br.close();
                    } catch (IOException e1) {
                        LOGGER.warning("Error closing file:" + e1);
                    }
                }
            } else if (file.isDirectory()) {
                System.out.println(TextIndexerByFile.fileWords.size() + " files read in directory " + folder_path);
            }
        }
        timer.totalTime();
    }

    /**
     * The word_indexer method stores a hashcode of every word
     * in our data structure.
     * Input:
     *     - String file: the path to the file where the word comes from.
     *     - String word: the word that we want to index in our structure.
     */
    private static void word_indexer(String file, String word) {
        fileWords.get(file).add((word.hashCode()));
    }

    /**
     * The word_finder method implements the search in our data
     * structure, printing in the screen the most relevant
     * files to the search.
     * Input:
     *     - String[] search: user input line of words.
     */
    public static void word_finder(String[] search) {
        System.out.println("----------------------------");
        System.out.println("Searching by file ...");
        timer.startTime();
        HashMap<String, Double> words_found = new HashMap<>();
        double numWords = (double) search.length;
        Boolean found = false;
        for (Map.Entry<String, HashSet<Integer>> entry : fileWords.entrySet()) {
            for (String word : search) {
                if (entry.getValue().contains(word.hashCode())) {
                    found = true;
                    TextIndexer.increase_position(entry.getKey(), words_found);
                }
            }
        }
        if (found) {
            TextIndexer.visualize_results(words_found, numWords);
        } else {
            System.out.println("no matches found");
        }
        timer.totalTime();
    }
}
