
import student.TestCase;

/**
 * This class tests the KVPair class so that the member methods work properly
 * and that the expected behavior occurs.
 * 
 * @author CS Staff
 * 
 * @version 2024.1
 */
public class KVPairTest extends TestCase {
    
    private KVPair<String, Integer> pair;
    
    public void setUp() {
        pair = new KVPair<String, Integer>("a", 0);
    }
    
    public void testGetKey() {
        String key = pair.getKey();
        assertEquals(key, "a");
    }
    
    public void testGetValue() {
        int value = (int)pair.getValue();
        assertEquals(value, 0);
    }
    
    public void testToString() {
        String repr = pair.toString();
        assertFuzzyEquals(repr, "(a, 0)");
    }

}
