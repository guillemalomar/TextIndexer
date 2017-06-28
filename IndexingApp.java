package textindexer;
import textindexer.TextIndexer;

import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class IndexingApp{
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
                TextIndexer.fileWords.put(listOfFiles[i].getName(), new HashSet<>());
                BufferedReader br = null;
				try {
				    br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + args[0] + "/" + listOfFiles[i].getName()));
					String line = br.readLine();

					while (line != null) {
						String[] splitted = line.split(",|\\:|-|\\.|\\ "); //Here we split the lines by the most common word delimiters.
						for (String word: splitted) {
                            TextIndexer.word_indexer(listOfFiles[i].getName(), word); //Here we index every word from every file in our structure.
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
                System.out.println(TextIndexer.fileWords.size() + " files read in directory " + args[0]);
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
            TextIndexer.word_finder(search); //The search is processed.
            System.out.println("---------------------------------------");
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
}
