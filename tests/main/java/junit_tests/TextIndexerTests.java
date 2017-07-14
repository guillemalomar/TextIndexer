/***********************************************
*    Title: Words Search Application           *
*    Author: Guillem Nicolau Alomar Sitjes     *
*    Date: July 14th, 2017                     *
*    Code version: 0.1                         *
*    Availability: Public                      *
***********************************************/
package textindexer;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TextIndexerTests {

    @Tests
    public void splitLineTests(){
        myTextIndexer = new TextIndexer();

        assertEquals(["bla", "bla2"], myTextIndexer.splitLine("bla.bla2"));
    }
}