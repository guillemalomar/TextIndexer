/***********************************************
*    Title: Words Search Application           *
*    Author: Guillem Nicolau Alomar Sitjes     * 
*    Date: June 16th, 2017                     *
*    Code version: 0.1                         *
*    Availability: Private                     *
***********************************************/
package textindexer;

import java.util.*;

public class TextIndexer {
    public static Map<String, HashSet<Integer>> fileWords = new HashMap<String, HashSet<Integer>>();

	/** 
	 * The word_indexer method stores a hashcode of every word
     * in our data structure.
	 */
    public static void word_indexer(String file, String word){
        fileWords.get(file).add((word.hashCode()));
    }

	/** 
	 * The word_finder method implements the search in our data
	 * structure, printing in the screen the most relevant
     * files to the search.
	 */
    public static void word_finder(String[] search){
        HashMap<String, Double> words_found = new HashMap<String, Double>();
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
            if(words_found.containsKey(entry.getKey()) == false){
                words_found.put(entry.getKey(), 0.00);
            }
        }
        if(found == true){
            int processed = 0;
            List<String> processedFiles = new ArrayList<>();
            Double max = 0.0;
            String maxFile = "";
            while((processed < words_found.size()) && (processed < 10)){ //until all relevant files have been processed, up to a maximum of 10
		        for(Map.Entry<String, Double> entry : words_found.entrySet()){
                    if((entry.getValue() >= max) && (processedFiles.contains(entry.getKey()) == false)){
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
