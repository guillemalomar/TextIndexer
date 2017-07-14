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

public class IndexingApp{

    private final static Timer timer = new Timer();
    private final static Logger LOGGER = Logger.getLogger(IndexingApp.class.getName());

    /** 
     * Main application method
     */
    public static void main(String[] args) {
        LOGGER.setLevel(Level.WARNING);
        clearScreen();
        String folder_path;
        if(args.length == 0) {
            LOGGER.info("Using default indexable files directory: IndexableDirectory");
            folder_path = System.getProperty("user.dir") + "/IndexableDirectory";
        }else{
            LOGGER.info("Using specified indexable files directory: " + args[0]);
            folder_path = System.getProperty("user.dir") + "/" + args[0];
        }

        startingApplication();
        System.out.println("----------------------------");
        System.out.println("Indexing files...");
        timer.startTime();
        final File indexableDirectory = new File(folder_path);
        File[] listOfFiles = indexableDirectory.listFiles();
        LOGGER.info("Number of files found: " + listOfFiles.length);
        textindexer.TextIndexer.text_indexer(listOfFiles, folder_path);
        timer.finishTime();
        System.out.println("Done in " + timer.getTime() + "ms!");
        System.out.println("----------------------------");
        
        Scanner keyboard = new Scanner(System.in);
        while(true) {
            System.out.print("search> ");
            final String line = keyboard.nextLine();
            exitCondition(line);
            timer.startTime();
            String[] search = textindexer.TextIndexer.splitLine(line);
            textindexer.TextIndexer.word_finder(search);
            timer.finishTime();
            System.out.println("Done in " + timer.getTime() + "ms!");
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
