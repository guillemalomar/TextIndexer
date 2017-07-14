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

public class Timer {

    private static long startTime = 9999999999L;
    private static long stopTime = 9999999999L;

    /**
     * Saves the starting timer
     */
    static void startTime(){
        startTime = System.currentTimeMillis();
    }

    /**
     * Saves the finish timer
     */
    static void finishTime(){
        stopTime = System.currentTimeMillis();
    }

    /**
     * Returns the elapsed time
     * Output:
     *    long : elapsed time between start and finish
     */
    static long getTime(){
        return stopTime - startTime;
    }
}
