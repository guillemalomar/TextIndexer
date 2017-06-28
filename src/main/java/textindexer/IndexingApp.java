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

public class IndexingApp{

	/** 
	 * Main application method
	 */
    public static void​ main(String[] args) {
        clearScreen();
        if(args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }

        startingApplication();

        final File indexableDirectory = new File(args[0]);
        System.out.println("Indexing files...");
        textindexer.TextIndexer.text_indexer(indexableDirectory, args);
        System.out.println("Done!");
        System.out.println("----------------------------");
        
        Scanner keyboard = new Scanner(System.in);
        while(true) {
            System.out.print("search> ");
            final String line = keyboard.nextLine();
            exitCondition(line);
            String[] search = textindexer.TextIndexer.splitLine(line);
            exitCondition(line);
            textindexer.TextIndexer.word_finder(search);
            System.out.println("----------------------------");
        }
    }

	/** 
	 * Just for visualizing purposes, cleans the terminal at the 
     * beginning of the execution.
	 */
	private static void clearScreen() {  
		System.out.print("\033[H\033[2J");  
		System.out.flush();  
    }

	/** 
	 * This prints the application information at the
     * beginning of the execution.
	 */
    private static void startingApplication(){
        System.out.println("****************************\n* Words Search Application *\n****************************");
        System.out.println("This application lets you search for specific words in all files in");
        System.out.println("a given directory, showing the file/files where most of the words");
        System.out.println("can be found (in case that the words are found).\nWrite ':quit' to exit.");
    }

	/** 
	 * This checks if the user wants to finish the execution.
     * Input:
     *    String line: user input line of words
	 */
    private static void exitCondition(String line){
        if(line.equals(":quit")){
            System.out.println("Exiting application");
            System.out.println("****************************");
            clearScreen();
            System.exit(0);
        }
    }
}
