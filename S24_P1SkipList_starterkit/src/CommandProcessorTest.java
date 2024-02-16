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


    /**
     * Test the insert method
     */
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


    /**
     * Test the dump method
     */
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


    /**
     * Test the search method
     */
    public void testSearch() {
        // Reset random
        TestableRandom.setNextBooleans(null);

        cmdProc.processor("insert a 1 0 2 4");
        cmdProc.processor("insert b 2 0 4 8");
        cmdProc.processor("insert c 4 0 8 16");

        // Search a
        String expectedOutput = "Rectangles found:\n" + "(a, 1, 0, 2, 4)\n";

        systemOut().clearHistory();
        cmdProc.processor("search a");

        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);

        // Search b
        expectedOutput = "Rectangles found:\n" + "(b, 2, 0, 4, 8)\n";

        systemOut().clearHistory();
        cmdProc.processor("search b");

        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);

        // Search c
        expectedOutput = "Rectangles found:\n" + "(c, 4, 0, 8, 16)\n";

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


    /**
     * Test the search method
     * assuming multiple records found
     */
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


    /**
     * Test the remove method
     */
    public void testRemove() {
        // Reset random
        TestableRandom.setNextBooleans(null);

        cmdProc.processor("insert a 1 0 2 4");
        cmdProc.processor("insert b 2 0 4 8");
        cmdProc.processor("insert c 4 0 8 16");
        cmdProc.processor("insert d 8 0 16 32");

        String expectedOutput;

        expectedOutput = "Rectangle removed: (a, 1, 0, 2, 4)\n";

        systemOut().clearHistory();
        cmdProc.processor("remove a");

        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);

        expectedOutput = "Rectangle removed: (c, 4, 0, 8, 16)\n";

        systemOut().clearHistory();
        cmdProc.processor("remove c");

        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);

        // Try to remove e (not found)
        expectedOutput = "Rectangle not removed: e\n";

        systemOut().clearHistory();
        cmdProc.processor("remove e");

        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
    }


    /**
     * Test remove by value method
     */
    public void testRemoveByValue() {
        // Set to 1, 1, 2, 2
        TestableRandom.setNextBooleans(false, false, true, false, true, false);

        cmdProc.processor("insert a 1 0 2 4");
        cmdProc.processor("insert b 2 0 4 8");
        cmdProc.processor("insert c 4 0 8 16");
        cmdProc.processor("insert d 8 0 16 32");

        String expectedOutput;

        expectedOutput = "Rectangle removed: (a, 1, 0, 2, 4)\n";

        systemOut().clearHistory();
        cmdProc.processor("remove 1 0 2 4");

        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);

        expectedOutput = "Rectangle removed: (c, 4, 0, 8, 16)\n";

        systemOut().clearHistory();
        cmdProc.processor("remove 4 0 8 16");

        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);

        // Not found remove
        expectedOutput = "Rectangle not found: (1, 1, 1, 1)\n";

        systemOut().clearHistory();
        cmdProc.processor("remove 1 1 1 1");

        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);

        // Invalid remove
        expectedOutput = "Rectangle rejected: (-1, 1, 1, 1)\n";

        systemOut().clearHistory();
        cmdProc.processor("remove -1 1 1 1");

        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);

        // Use dump to verify that they were actually removed
        expectedOutput = "SkipList dump:\n" + "Node with depth 2, Value null\n"
            + "Node with depth 1, Value (b, 2, 0, 4, 8)\n"
            + "Node with depth 2, Value (d, 8, 0, 16, 32)\n"
            + "SkipList size is: 2";

        systemOut().clearHistory();
        cmdProc.processor("dump");

        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
    }


    /**
     * Test removing all the elements
     */
    public void testRemoveAll() {
        // Set to 1, 1, 2, 2
        TestableRandom.setNextBooleans(false, false, true, false, true, false);

        cmdProc.processor("insert a 1 0 2 4");
        cmdProc.processor("insert b 2 0 4 8");
        cmdProc.processor("insert c 4 0 8 16");
        cmdProc.processor("insert d 8 0 16 32");

        cmdProc.processor("remove d");
        cmdProc.processor("remove a");
        cmdProc.processor("remove 4 0 8 16");
        cmdProc.processor("remove 2 0 4 8");

        // Use dump to verify that they were actually removed
        String expectedOutput = "SkipList dump:\n"
            + "Node with depth 1, Value null\n" + "SkipList size is: 0";

        systemOut().clearHistory();
        cmdProc.processor("dump");

        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
    }


    /**
     * Test removing a simple key
     */
    public void testSimpleRemoveKey() {

        cmdProc.processor("insert a 1 0 2 4");

        cmdProc.processor("remove a");

        // Use dump to verify that they were actually removed
        String expectedOutput = "SkipList dump:\n"
            + "Node with depth 1, Value null\n" + "SkipList size is: 0";

        systemOut().clearHistory();
        cmdProc.processor("dump");

        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
    }


    /**
     * Test removing a simple value
     */
    public void testSimpleRemoveValue() {
        cmdProc.processor("insert a 1 0 2 4");

        cmdProc.processor("remove 1 0 2 4");

        // Use dump to verify that they were actually removed
        String expectedOutput = "SkipList dump:\n"
            + "Node with depth 1, Value null\n" + "SkipList size is: 0";

        systemOut().clearHistory();
        cmdProc.processor("dump");

        String actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
    }


    /**
     * Test an invalid command.
     */
    public void testInvalidCommand() {
        String expectedOutput;
        String actualOutput;

        expectedOutput = "Invalid command.\n";

        systemOut().clearHistory();
        cmdProc.processor("remove 1 1 1 1 1");

        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);

        expectedOutput = "Unrecognized command.\n";

        systemOut().clearHistory();
        cmdProc.processor("failure");

        actualOutput = systemOut().getHistory();
        assertFuzzyEquals(expectedOutput, actualOutput);
    }

}
