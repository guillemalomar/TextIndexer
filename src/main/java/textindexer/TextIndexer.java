/***********************************************
*    Title: Words Search Application           *
*    Author: Guillem Nicolau Alomar Sitjes     * 
*    Date: June 16th, 2017                     *
*    Code version: 0.1                         *
*    Availability: Public                      *
***********************************************/
package textindexer;

import java.util.*;
import java.io.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TextIndexer {

    private static Map<String, HashSet<Integer>> fileWords = new HashMap<>();

    private final static Logger LOGGER = Logger.getLogger(IndexingApp.class.getName());

    /** 
     * This method returns a string array with the words found
     * in a given line, splitted by the most common word delimiters.
     * Input:
     *    String line: a text line containing words.
     */
    static String[] splitLine(String line){
        return line.split(";|,|\\:|-|\\.|\\ ");
    }

    /** 
     * The text_indexer method stores a hashcode of every word
     * in our data structure.
     * Input:
     *    File[] listOfFiles: list of the files in the files directory.
     *    String folder_path: the path to the files directory.
     */
     static void text_indexer(File[] listOfFiles, String folder_path){
        LOGGER.setLevel(Level.WARNING);
        int words_in_file;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                textindexer.TextIndexer.fileWords.put(file.getName(), new HashSet<>());
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader("/" + folder_path + "/" + file.getName()));
                    String line = br.readLine();
                    words_in_file = 0;
                    while (line != null) {
                        String[] split = splitLine(line);
                        for (String word: split) {
                            word_indexer(file.getName(), word);
                            words_in_file++;
                        }
                        line = br.readLine();
                    }
                    LOGGER.info("Number of words in file " + file.getName() + ":" + words_in_file);
                } catch(FileNotFoundException e) {
                    LOGGER.warning("Error opening file:" + e);
                } catch(IOException e) {
                    LOGGER.warning("Error reading line:" + e);
                }
                finally {
                    try{
                        br.close();
                    } catch(IOException e1) {
                        LOGGER.warning("Error closing file:" + e1);
                    }
                }
            } else if (file.isDirectory()) {
                System.out.println(textindexer.TextIndexer.fileWords.size() + " files read in directory " + folder_path);
            }
        }
    }
    
    /** 
     * The word_indexer method stores a hashcode of every word
     * in our data structure.
     * Input:
     *    String file: the path to the file where the word comes from.
     *    String word: the word that we want to index in our structure.
     */
    private static void word_indexer(String file, String word){
        fileWords.get(file).add((word.hashCode()));
    }

    /** 
     * The word_finder method implements the search in our data
     * structure, printing in the screen the most relevant
     * files to the search.
     * Input:
     *    String[] search: user input line of words.
     */
    static void word_finder(String[] search){
        HashMap<String, Double> words_found = new HashMap<>();
        double numWords = (double) search.length;
        Boolean found = false;
        for (Map.Entry<String, HashSet<Integer>> entry : fileWords.entrySet()){
            for (String word: search) {
                if(entry.getValue().contains(word.hashCode())){
                    found = true;
                    if(words_found.containsKey(entry.getKey())){
                        words_found.put(entry.getKey(), 1.00 + words_found.get(entry.getKey()));
                    }else{
                        words_found.put(entry.getKey(), 1.00);
                    }
                }
            }
            if(!words_found.containsKey(entry.getKey())){
                words_found.put(entry.getKey(), 0.00);
            }
        }
        if(found){
            int processed = 0;
            List<String> processedFiles = new ArrayList<>();
            Double max = 0.0;
            String maxFile = "";
            while((processed < words_found.size()) && (processed < 10)){ //until all relevant files have been processed, up to a maximum of 10
                for(Map.Entry<String, Double> entry : words_found.entrySet()){
                    if((entry.getValue() >= max) && (!processedFiles.contains(entry.getKey()))){
                        max = entry.getValue();   //This is done in order to show the results in descending order.
                        maxFile = entry.getKey(); //Current max value and it's file are saved.
                    }
                }
                System.out.println(maxFile + " : " + max/numWords*100 + "%");
                processedFiles.add(maxFile); //so that we just 
                processed++;
                max = 0.0;
                maxFile = "";
            }
        }else{
            System.out.println("no matches found");
        }
    }
}
