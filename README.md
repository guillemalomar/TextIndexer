# Text Indexer

*    Title: Text Indexer        
*    Author: Guillem Nicolau Alomar Sitjes      
*    Initial release: June 16th, 2017                     
*    Code version: 0.1                         
*    Availability: Public                      

**Index**
* [Documentation](#documentation)
    * [Explanation](#explanation)
    * [Project Structure](#project-structure)
* [Requirements](#requirements)
* [Using the application](#using-the-application)
* [Notes](#notes)

## Documentation

### Explanation

Just a text indexer for fast text searches. Similar to a grep,
but for several words at the same time, giving an ordered list
as a result, with a % of the words in the input that appear in
each file.

### Project Structure

![alt text][logo2]

[logo2]: https://github.com/guillemalomar/TextIndexer/blob/master/Documentation/ProjectDiagram.png?raw=true "Folders and Files"

## Requirements

- Java

## Using the application

Currently, there are two available indexing modes to choose:
*    1) Indexing by file (Default mode)
     - Data is stored in a "file: words in file" shape
*    2) Indexing by word
     - Data is stored in a "word: files where it appears" shape
*    3) Indexing by file and Indexing by word

The mode can be passed as a parameter when executing the application. 

![alt text][logo2]

[logo2]: https://github.com/guillemalomar/TextIndexer/blob/master/Documentation/ExecutionExample.png?raw=true "Folders and Files"

## Notes

It has been tested with +1.25GB of data. For a bigger dataset, it might give memory problems. In this case, having the data in a DDBB would solve the memory problems (as long as the dataset fits in your laptop disk, otherwise you could store it in a server). The 'IndexableDirectory' folder only contains some small files used for testing (I have not put the +1GB files that I used for
GitHub storage reasons).

A good parallel implementation would improve the performance.

Several tests will be done in order to obtain some performance data to evaluate them. I guess that the proportion WordsRange/NumFiles will be the main way to choose between them. Some performance results have already been taken and can be seen in the PerformanceTests folder.
