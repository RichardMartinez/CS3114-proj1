import java.util.ArrayList;
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

    private SkipList<String, Rectangle> sl;
	
	/**
	 * Set up new SkipList for each test
	 */
	public void setUp() {
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
	
	/**
	 * Test inserting two rectangles
	 * and verify they are there
	 */
	public void testInsert() {
	    // Reset Testable Random
	    TestableRandom.setNextBooleans(null);
	    
	    // sl is a default constructed SkipList
	    Rectangle rec = new Rectangle(10, 10, 5, 5);
	    String name = "HelloWorld";
	    KVPair<String, Rectangle> pair =
	        new KVPair<String, Rectangle>(name, rec);
	    sl.insert(pair);
	    
	    rec = new Rectangle(20, 20, 5, 5);
        name = "HelloWorld2";
        pair = new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
	       
	    // Iterator and Test KVPairs
        Iterator<KVPair<String, Rectangle>> it = sl.iterator();
        
        KVPair<String, Rectangle> elem;
        elem = it.next();
        assertEquals(elem.getKey(), "HelloWorld");
        elem = it.next();
        assertEquals(elem.getKey(), "HelloWorld2");
	}
	
	/**
	 * Test the dump method prints
	 * as expected
	 */
	public void testDump() {
	    // Make next levels: 3, 2
        TestableRandom.setNextBooleans(true, true, false, true, false);
        
        // sl is a default constructed SkipList
        Rectangle rec = new Rectangle(1, 0, 2, 4);
        String name = "a";
        KVPair<String, Rectangle> pair =
            new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
        
        rec = new Rectangle(2, 0, 4, 8);
        name = "b";
        pair = new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
        
        String expectedOutput = "SkipList dump:\n"
            + "Node with depth 3, Value null\n"
            + "Node with depth 3, Value (a, 1, 0, 2, 4)\n"
            + "Node with depth 2, Value (b, 2, 0, 4, 8)\n"
            + "SkipList size is: 2\n";
        
        systemOut().clearHistory();
        sl.dump();
        
        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
	}
	
	/**
	 * Test the dump method works
	 * as expected with an empty list
	 */
	public void testEmptyListDump() {
	    String expectedOutput = "SkipList dump:\n"
            + "Node with depth 1, Value null\n"
            + "SkipList size is: 0\n";
        
        systemOut().clearHistory();
        sl.dump();
        
        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
	}
	
	/**
	 * Test inserting null values
	 * as last value in list
	 */
	public void testInsertNullValues() {
	 // Make next levels: 3, 2
        TestableRandom.setNextBooleans(null);
        
        // sl is a default constructed SkipList
        Rectangle rec = new Rectangle(1, 0, 2, 4);
        String name = "a";
        KVPair<String, Rectangle> pair =
            new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
        
        rec = null;
        name = "b";
        pair = new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
        
     // Iterator and Test KVPairs
        Iterator<KVPair<String, Rectangle>> it = sl.iterator();
        
        KVPair<String, Rectangle> elem;
        elem = it.next();
        assertEquals(elem.getKey(), "a");
        assertNotNull(elem.getValue());
        elem = it.next();
        assertEquals(elem.getKey(), "b");
        assertNull(elem.getValue());
	}
	
	/**
	 * Test the hasNext method of the iterator
	 */
	public void testIteratorHasNext() {
	    // Make next levels: 3, 2
        TestableRandom.setNextBooleans(true, true, false, true, false);
        
        // sl is a default constructed SkipList
        Rectangle rec = new Rectangle(1, 0, 2, 4);
        String name = "a";
        KVPair<String, Rectangle> pair =
            new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
        
        rec = new Rectangle(2, 0, 4, 8);
        name = "b";
        pair = new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
        
        // Iterator and Test KVPairs
        Iterator<KVPair<String, Rectangle>> it = sl.iterator();
        
        KVPair<String, Rectangle> elem;
        elem = it.next();
        assertEquals(elem.getKey(), "a");
        
        boolean hasNext = it.hasNext();
        assertTrue(hasNext);
        
        elem = it.next();
        assertEquals(elem.getKey(), "b");
        
        hasNext = it.hasNext();
        assertFalse(hasNext);
	}
    
	/**
	 * Test the search method
	 */
	public void testSearch() {
	    // Reset Random
	    TestableRandom.setNextBooleans(null);
	    
	    Rectangle rec;
	    String name;
	    KVPair<String, Rectangle> pair;
	    
	    rec = new Rectangle(10, 10, 15, 15);
        name = "a";
        pair = new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
        
        rec = new Rectangle(1, 2, 3, 4);
        name = "b";
        pair = new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
        
        rec = new Rectangle(50, 21, 52, 1);
        name = "a";
        pair = new KVPair<String, Rectangle>(name, rec);
        sl.insert(pair);
        
        ArrayList<KVPair<String, Rectangle>> list = sl.search("a");
        
        assertEquals(list.size(), 2);
        
        Iterator<KVPair<String, Rectangle>> it = list.iterator();
        KVPair<String, Rectangle> elem;
        elem = it.next();
        assertEquals(elem.getKey(), "a");
        elem = it.next();
        assertEquals(elem.getKey(), "a");
        
        assertFalse(it.hasNext());
        
        // Test search not found
        list = sl.search("c");
        
        assertEquals(list.size(), 0);
        it = list.iterator();
        assertFalse(it.hasNext());
	}

}
