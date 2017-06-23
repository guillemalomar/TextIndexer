/***********************************************
*    Title: Words Search Application           *
*    Author: Guillem Nicolau Alomar Sitjes     * 
*    Date: June 16th, 2017                     *
*    Code version: 0.1                         *
*    Availability: Private                     *
***********************************************/

import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Main {
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

	/** 
	 * Just for visualizing purposes, cleans the terminal at the 
     * beginning of the execution.
	 */
	public static void clearScreen() {  
		System.out.print("\033[H\033[2J");  
		System.out.flush();  
    }

    public static void​ main(String[] args) {
        clearScreen();
        System.out.println("****************************\n* Words Search Application *\n****************************");
        System.out.println("This application lets you search for specific words in all files in");
        System.out.println("a given directory, showing the file/files where most of the words");
        System.out.println("can be found (in case that the words are found).\nWrite ':quit' to exit.");
        if(args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }
        final File indexableDirectory = new File(args[0]);
        
        File[] listOfFiles = indexableDirectory.listFiles();

        System.out.println("Indexing files...");
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileWords.put(listOfFiles[i].getName(), new HashSet<>());
                BufferedReader br = null;
				try {
				    br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + args[0] + "/" + listOfFiles[i].getName()));
					String line = br.readLine();

					while (line != null) {
						String[] splitted = line.split(",|\\:|-|\\.|\\ "); //Here we split the lines by the most common word delimiters.
						for (String word: splitted) {
                            word_indexer(listOfFiles[i].getName(), word); //Here we index every word from every file in our structure.
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
                System.out.println(fileWords.size() + " files read in directory " + args[0]);
            }
        }
        System.out.println("Done!");
        System.out.println("---------------------------------------");
        
        Scanner keyboard = new Scanner(System.in);
        while(true) {
            System.out.print("search> ");
            final String line = keyboard.nextLine();
            if(line.equals(":quit")){
                System.out.println("Exiting application");
                System.exit(0);
            }
            String[] search = line.split(",|\\:|-|\\.|\\ "); //This splits the input line by the most common word delimiters.
            word_finder(search); //The search is processed.
            System.out.println("---------------------------------------");
        }
    }
}
