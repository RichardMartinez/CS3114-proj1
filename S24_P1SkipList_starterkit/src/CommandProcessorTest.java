import student.TestCase;
import student.TestableRandom;

/**
 * This class tests the CommandProcessor class.
 * Test each possible command on its bounds,
 * if applicable to ensure they work properly. 
 * Also test passing improper command to ensure 
 * all class functionalities work as intended.
 * 
 * @author <your_name>
 * @version <version_no>
 */
public class CommandProcessorTest extends TestCase {
    
    private CommandProcessor cmdProc;
    
    /**
     * The setUp() method will be called automatically before
     * each test and reset whatever the test modified. For this
     * test class, only a new database object is needed, so 
     * creat a database here for use in each test case.
     */
    public void setUp() {
        cmdProc = new CommandProcessor();
    }
    
    public void testInsert() {
        // Valid insert
        systemOut().clearHistory();
        cmdProc.processor("insert a 1 0 2 4");
        String expectedOutput = "Rectangle inserted: (a, 1, 0, 2, 4)";
        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
        
        // Invalid insert
        systemOut().clearHistory();
        cmdProc.processor("insert b -1 -1 -1 -1");
        expectedOutput = "Rectangle rejected: (b, -1, -1, -1, -1)";
        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
    }
    
    public void testDump() {
        // Make next levels: 3, 2
        TestableRandom.setNextBooleans(true, true, false, true, false);
        
        cmdProc.processor("insert a 1 0 2 4");
        cmdProc.processor("insert b 2 0 4 8");
               
        String expectedOutput = "SkipList dump:\n"
            + "Node with depth 3, Value null\n"
            + "Node with depth 3, Value (a, 1, 0, 2, 4)\n"
            + "Node with depth 2, Value (b, 2, 0, 4, 8)\n"
            + "SkipList size is: 2\n";
        
        systemOut().clearHistory();
        cmdProc.processor("dump");
        
        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
    }
    
    public void testSearch() {
        // Reset random
        TestableRandom.setNextBooleans(null);
        
        cmdProc.processor("insert a 1 0 2 4");
        cmdProc.processor("insert b 2 0 4 8");
        cmdProc.processor("insert c 4 0 8 16");
        
        // Search a
        String expectedOutput = "Rectangles found:\n"
            + "(a, 1, 0, 2, 4)\n";
        
        systemOut().clearHistory();
        cmdProc.processor("search a");
        
        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
        
        // Search b
        expectedOutput = "Rectangles found:\n"
            + "(b, 2, 0, 4, 8)\n";
        
        systemOut().clearHistory();
        cmdProc.processor("search b");
        
        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
        
        // Search c
        expectedOutput = "Rectangles found:\n"
            + "(c, 4, 0, 8, 16)\n";
        
        systemOut().clearHistory();
        cmdProc.processor("search c");
        
        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
        
        // Search d (not found)
        expectedOutput = "Rectangle not found (d)\n";
        
        systemOut().clearHistory();
        cmdProc.processor("search d");
        
        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
    }
    
    public void testSearchMultipleFound() {
        // Reset random
        TestableRandom.setNextBooleans(null);
        
        cmdProc.processor("insert a 10 10 15 15");
        cmdProc.processor("insert b 2 0 4 8");
        cmdProc.processor("insert a 50 21 52 1");
        cmdProc.processor("insert a 1 1 1 1");
        
        
        String recsFound = "Rectangles found:";
        String a1 = "(a, 10, 10, 15, 15)";
        String a2 = "(a, 50, 21, 52, 1)";
        String a3 = "(a, 1, 1, 1, 1)";
        
        systemOut().clearHistory();
        cmdProc.processor("search a");
        
        String actualOutput = systemOut().getHistory();
        assertTrue(actualOutput.contains(recsFound));
        assertTrue(actualOutput.contains(a1));
        assertTrue(actualOutput.contains(a2));
        assertTrue(actualOutput.contains(a3));
    }

}
