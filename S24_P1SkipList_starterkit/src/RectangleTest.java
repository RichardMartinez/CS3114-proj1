
import student.TestCase;

/**
 * This class tests the methods of Rectangle class,
 * ensuring that they work as they should.
 * 
 * @author Richard Martinez
 * @version 1
 */
public class RectangleTest extends TestCase {

    private Rectangle rec;
    private Rectangle rec2;

    /**
     * Initializes a rectangle object to be used for the tests.
     */
    public void setUp() {
        rec = new Rectangle(10, 10, 5, 5);
        rec2 = new Rectangle(10, 10, 5, 5);
    }


    /**
     * Test the equals method
     */
    public void testEquals() {
        // Equal to self
        assertTrue(rec.equals(rec));
        assertTrue(rec2.equals(rec2));

        // Equal to each other
        assertTrue(rec.equals(rec2));
        assertTrue(rec2.equals(rec));

        // Different x
        rec2 = new Rectangle(11, 10, 5, 5);
        assertFalse(rec.equals(rec2));
        assertFalse(rec2.equals(rec));

        // Different y
        rec2 = new Rectangle(10, 11, 5, 5);
        assertFalse(rec.equals(rec2));
        assertFalse(rec2.equals(rec));

        // Different w
        rec2 = new Rectangle(10, 10, 6, 5);
        assertFalse(rec.equals(rec2));
        assertFalse(rec2.equals(rec));

        // Different h
        rec2 = new Rectangle(10, 10, 5, 6);
        assertFalse(rec.equals(rec2));
        assertFalse(rec2.equals(rec));

        // Make them equal again
        rec = new Rectangle(10, 10, 5, 6);
        assertTrue(rec.equals(rec2));
        assertTrue(rec2.equals(rec));
    }


    /**
     * Test the toString method
     */
    public void testToString() {
        assertEquals(rec.toString(), "10, 10, 5, 5");
    }


    /**
     * Test the invalid method
     */
    public void testIsInvalid() {
        // Default
        assertFalse(rec.isInvalid());
        rec = new Rectangle(0, 0, 5, 5);
        assertFalse(rec.isInvalid());

        // Invalid x
        rec = new Rectangle(-1, 10, 5, 5);
        assertTrue(rec.isInvalid());

        // Invalid y
        rec = new Rectangle(10, -1, 5, 5);
        assertTrue(rec.isInvalid());

        // Invalid w (zero or negative)
        rec = new Rectangle(10, 10, 0, 5);
        assertTrue(rec.isInvalid());
        rec = new Rectangle(10, 10, -1, 5);
        assertTrue(rec.isInvalid());

        // Invalid h (zero or negative)
        rec = new Rectangle(10, 10, 5, 0);
        assertTrue(rec.isInvalid());
        rec = new Rectangle(10, 10, 5, -1);
        assertTrue(rec.isInvalid());

        // Outside world box
        // Left
        rec = new Rectangle(-10, 10, 5, 5);
        assertTrue(rec.isInvalid());

        // Right
        rec = new Rectangle(1000, 10, 25, 5);
        assertTrue(rec.isInvalid());

        // Top
        rec = new Rectangle(10, -10, 5, 5);
        assertTrue(rec.isInvalid());

        // Bottom
        rec = new Rectangle(10, 1000, 5, 25);
        assertTrue(rec.isInvalid());

        // Coordinates are greater than 0, but outside world box
        rec = new Rectangle(2000, 2000, 5, 5);
        assertTrue(rec.isInvalid());
    }


    /**
     * Test the intersect method
     */
    public void testIntersect() {
        // Tests from Spec
        rec = new Rectangle(10, 10, 5, 5);
        rec2 = new Rectangle(15, 10, 5, 5);
        assertFalse(rec.intersect(rec2));
        assertFalse(rec2.intersect(rec));

        rec2 = new Rectangle(14, 10, 5, 5);
        assertTrue(rec.intersect(rec2));
        assertTrue(rec2.intersect(rec));

        rec2 = new Rectangle(11, 11, 1, 1);
        assertTrue(rec.intersect(rec2));
        assertTrue(rec2.intersect(rec));

        // Same Rectangle
        rec2 = new Rectangle(10, 10, 5, 5);
        assertTrue(rec.intersect(rec2));
        assertTrue(rec2.intersect(rec));

        // One is Invalid
        rec = new Rectangle(10, 10, 0, 5);
        rec2 = new Rectangle(10, 10, 5, 5);
        assertFalse(rec.intersect(rec2));
        assertFalse(rec2.intersect(rec));

        rec = new Rectangle(10, 10, 5, 5);
        rec2 = new Rectangle(10, 10, 0, 5);
        assertFalse(rec.intersect(rec2));
        assertFalse(rec2.intersect(rec));

        // One is too far left
        rec = new Rectangle(5, 10, 5, 5);
        rec2 = new Rectangle(10, 10, 5, 5);
        assertFalse(rec.intersect(rec2));
        assertFalse(rec2.intersect(rec));

        rec = new Rectangle(10, 10, 5, 5);
        rec2 = new Rectangle(5, 10, 5, 5);
        assertFalse(rec.intersect(rec2));
        assertFalse(rec2.intersect(rec));

        // One is too far down
        rec = new Rectangle(10, 10, 5, 5);
        rec2 = new Rectangle(10, 15, 5, 5);
        assertFalse(rec.intersect(rec2));
        assertFalse(rec2.intersect(rec));

        rec = new Rectangle(10, 15, 5, 5);
        rec2 = new Rectangle(10, 10, 5, 5);
        assertFalse(rec.intersect(rec2));
        assertFalse(rec2.intersect(rec));

        // rec is invalid, but they would otherwise intersect
        rec = new Rectangle(-5, -5, 10, 10);
        rec2 = new Rectangle(0, 0, 5, 5);
        assertFalse(rec.intersect(rec2));
        assertFalse(rec2.intersect(rec));
    }


    /**
     * Test the intersect method
     */
    public void testIntersectRegion() {
        // Take most of the tests from regular intersect
        // Tests from Spec
        rec = new Rectangle(10, 10, 5, 5);
        rec2 = new Rectangle(15, 10, 5, 5);
        assertFalse(rec.intersectRegion(rec2));
        assertFalse(rec2.intersectRegion(rec));

        rec2 = new Rectangle(14, 10, 5, 5);
        assertTrue(rec.intersectRegion(rec2));
        assertTrue(rec2.intersectRegion(rec));

        rec2 = new Rectangle(11, 11, 1, 1);
        assertTrue(rec.intersectRegion(rec2));
        assertTrue(rec2.intersectRegion(rec));

        // Same Rectangle
        rec2 = new Rectangle(10, 10, 5, 5);
        assertTrue(rec.intersectRegion(rec2));
        assertTrue(rec2.intersectRegion(rec));

        // Invalid this
        rec = new Rectangle(10, 10, 0, 5);
        rec2 = new Rectangle(10, 10, 5, 5);
        assertFalse(rec.intersectRegion(rec2));
        assertFalse(rec2.intersectRegion(rec));

        // One is too far left
        rec = new Rectangle(5, 10, 5, 5);
        rec2 = new Rectangle(10, 10, 5, 5);
        assertFalse(rec.intersectRegion(rec2));
        assertFalse(rec2.intersectRegion(rec));

        rec = new Rectangle(10, 10, 5, 5);
        rec2 = new Rectangle(5, 10, 5, 5);
        assertFalse(rec.intersectRegion(rec2));
        assertFalse(rec2.intersectRegion(rec));

        // One is too far down
        rec = new Rectangle(10, 10, 5, 5);
        rec2 = new Rectangle(10, 15, 5, 5);
        assertFalse(rec.intersectRegion(rec2));
        assertFalse(rec2.intersectRegion(rec));

        rec = new Rectangle(10, 15, 5, 5);
        rec2 = new Rectangle(10, 10, 5, 5);
        assertFalse(rec.intersectRegion(rec2));
        assertFalse(rec2.intersectRegion(rec));

        // Region can be invalid, but still pass
        Rectangle region;

        rec = new Rectangle(0, 0, 5, 5);
        region = new Rectangle(-1, 0, 10, 10);
        assertTrue(rec.intersectRegion(region));
        assertFalse(rec.intersect(region));

        rec = new Rectangle(0, 0, 5, 5);
        region = new Rectangle(0, -1, 10, 10);
        assertTrue(rec.intersectRegion(region));
        assertFalse(rec.intersect(region));

        // Rec is invalid, but they would have
        // intersected otherwise
        rec = new Rectangle(-1, 0, 5, 5);
        region = new Rectangle(0, -1, 10, 10);
        assertFalse(rec.intersectRegion(region));
        assertFalse(rec.intersect(region));

        rec = new Rectangle(0, -1, 5, 5);
        region = new Rectangle(0, -1, 10, 10);
        assertFalse(rec.intersectRegion(region));
        assertFalse(rec.intersect(region));
    }
}
