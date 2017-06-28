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

public class TextIndexer {

    private static Map<String, HashSet<Integer>> fileWords = new HashMap<String, HashSet<Integer>>();

	/** 
	 * This method returns a string array with the words found
     * in a given line, splitted by the most common word delimiters.
     * Input:
     *    String line: a text line containing words.
	 */
    public static String[] splitLine(String line){
        return line.split(";|,|\\:|-|\\.|\\ ");
    }

	/** 
	 * The text_indexer method stores a hashcode of every word
     * in our data structure.
     * Input:
     *    File indexableDirectory: the path to the files directory.
     *    String[] args: the application parameters.
	 */
    public static void text_indexer(File indexableDirectory, String[] args){

        File[] listOfFiles = indexableDirectory.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                textindexer.TextIndexer.fileWords.put(listOfFiles[i].getName(), new HashSet<>());
                BufferedReader br = null;
				try {
				    br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + args[0] + "/" + listOfFiles[i].getName()));
					String line = br.readLine();

					while (line != null) {
						String[] splitted = splitLine(line);
						for (String word: splitted) {
                            word_indexer(listOfFiles[i].getName(), word);
						}
						line = br.readLine();
					}
				} catch(FileNotFoundException e) {
                    System.out.println("Error opening file:" + e);
                } catch(IOException e) {
                    System.out.println("Error reading line");
                }
                finally {
                    try{
					    br.close();
                    } catch(IOException e2) {
                        System.out.println("Error closing file");
                    }
				}
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println(textindexer.TextIndexer.fileWords.size() + " files read in directory " + args[0]);
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
