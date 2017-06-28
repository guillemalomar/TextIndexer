# TextIndexer

*    Title: Words Search Application          
*    Author: Guillem Nicolau Alomar Sitjes      
*    Date: June 16th, 2017                     
*    Code version: 0.1                         
*    Availability: Public                      


Just a text indexer for fast text searches. Similar to a grep,
but for several words at the same time, giving an ordered list
as a result, with a % of the words in the input that appear in
each file.

It has been tested with +1.25GB of data.
For a bigger dataset, it might give memory problems. In this
case, an implementation to save the temporal data in disk
would be better. A parallel implementation would also be better
in that case, for a better performance.

The 'IndexableDirectory' folder only contains some small files
used for testing.
