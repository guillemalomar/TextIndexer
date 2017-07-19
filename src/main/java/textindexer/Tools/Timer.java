/*
 *    Title: Words Search Application
 *    Author: Guillem Nicolau Alomar Sitjes
 *    Date: June 18th, 2017
 *    Code version: 0.1
 *    Availability: Public
 */
package textindexer.Tools;

public class Timer {

    private static long startTime = 9999999999L;  // Value just to initialize
    private static long stopTime = 9999999999L;   // Value just to initialize

    /**
     * Saves the starting timer
     */
    public static void startTime(){
        startTime = System.currentTimeMillis();
    }

    /**
     * Saves the finish timer
     */
    private static void finishTime(){
        stopTime = System.currentTimeMillis();
    }

    /**
     * Returns the elapsed time
     * Output:
     *    long : elapsed time between start and finish in seconds
     */
    private static double getTime(){
        long elapsedTime = stopTime - startTime;
        return (double)elapsedTime / 1000.0;
    }

    public static void totalTime(){
        finishTime();
        System.out.println("Done in " + getTime() + "s!");
        System.out.println("----------------------------");
    }
}
