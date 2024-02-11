import java.util.Iterator;

import org.junit.Test;

import student.TestCase;
import student.TestableRandom;

/**
 * This class tests the methods of SkipList class
 * 
 * @author CS Staff
 * 
 * @version 2024-01-22
 */

public class SkipListTest extends TestCase {
	
	SkipList<String, Rectangle> sl;
	
	public void setUp() {
		// TODO: implement setup
		sl = new SkipList<String, Rectangle>();
	}

	/***
	 * Example 1: Test `randomLevel` method with 
	 * predetermined random values using `TestableRandom`
	 */
	public void testRandomLevelOne() {
		TestableRandom.setNextBooleans(false);
		sl = new SkipList<String, Rectangle>();
		int randomLevelValue = sl.randomLevel();
		
		// This returns 1 because the first preset 
		// random boolean is `false` which breaks 
		// the `while condition inside the `randomLevel` method
		int expectedLevelValue = 1;  
		
		// Compare the values
		assertEquals(expectedLevelValue, randomLevelValue);
	}
	
	/***
	 * Example 2: Test `randomLevel` method with 
	 * predetermined random values using `TestableRandom`
	 */
	public void testRandomLevelFour() {
		TestableRandom.setNextBooleans(true, true, true, false, true, false);
		sl = new SkipList<String, Rectangle>();
		int randomLevelValue = sl.randomLevel();

		// This returns 4 because the fourth preset 
		// random boolean is `false` which breaks 
		// the `while condition inside the `randomLevel` method
		int expectedLevelValue = 4; 
		
		// Compare the values
		assertEquals(expectedLevelValue, randomLevelValue);
	}
	
    // TODO: implement more tests
	
	public void testInsert() {
	    // Reset Testable Random
	    //TestableRandom.setNextBooleans(null);
	    
	    // sl is a default constructed SkipList
	    Rectangle rec = new Rectangle(10, 10, 5, 5);
	    String name = "HelloWorld";
	    KVPair<String, Rectangle> pair = new KVPair<String, Rectangle>(name, rec);
	    sl.insert(pair);
	    
	    rec = new Rectangle(20, 20, 5, 5);
        name = "HelloWorld2";
        pair = new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
	       
	    sl.dump();
	}
	
	public void testEmptyList() {
	    sl.dump();
	}
    

}
