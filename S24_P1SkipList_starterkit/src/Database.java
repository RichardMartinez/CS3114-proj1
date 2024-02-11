import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 * 
 * Also note that the Database class will have a clearer role in Project2, 
 * where we will have two data structures.
 * The Database class will then determine 
 * which command should be directed to which data structure.  
 * 
 * @author CS Staff
 * 
 * @version 2024-01-22
 */
public class Database {

    // this is the SkipList object that we are using
    // a string for the name of the rectangle and then
    // a rectangle object, these are stored in a KVPair,
    // see the KVPair class for more information
    private SkipList<String, Rectangle> list;
    
    //This is an Iterator object over the SkipList
    //to loop through it from outside the class.
    //You will need to define an extra Iterator for the intersections method.
    private Iterator<KVPair<String, Rectangle>> itr1;
    

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, Rectangle>();
    }


    /**
     * Inserts the KVPair in the SkipList if the rectangle has valid coordinates
     * and dimensions, that is that the coordinates are non-negative and that
     * the rectangle object has some area (not 0, 0, 0, 0). This insert will
     * add the KVPair specified into the sorted SkipList appropriately
     * 
     * @param pair
     *            the KVPair to be inserted
     */
    public void insert(KVPair<String, Rectangle> pair) {
        // Delegates the decision mostly to SkipList, only
        // writing the correct message to the console from
        // that
        
        Rectangle rec = pair.getValue();
        String name = pair.getKey();
        if (rec.isInvalid()) {
            //  Rectangle rejected: (a, -1, -1, 2, 4)
            String out =
                String.format("Rectangle rejected: (%s, %s)", name, rec);
            System.out.println(out);
            return;
        }
        
        // Insert it
        list.insert(pair);
        String out = String.format("Rectangle inserted: (%s, %s)", name, rec);
        System.out.println(out);
    }


    /**
     * Removes a rectangle with the name "name" if available. If not an error
     * message is printed to the console.
     * 
     * @param name
     *            the name of the rectangle to be removed
     */
    public void remove(String name) {
        KVPair<String, Rectangle> pair = list.remove(name);
        if (pair == null) {
          // Rectangle not removed: b
          String out = String.format("Rectangle not removed: %s", name);
          System.out.println(out);
          return;
        }
        
        // Successful remove
        Rectangle rec = pair.getValue();
        
        String out = String.format("Rectangle removed: (%s, %s)",
            name, rec);
        System.out.println(out);

    }


    /**
     * Removes a rectangle with the specified coordinates if available. If not
     * an error message is printed to the console.
     * 
     * @param x
     *            x-coordinate of the rectangle to be removed
     * @param y
     *            x-coordinate of the rectangle to be removed
     * @param w
     *            width of the rectangle to be removed
     * @param h
     *            height of the rectangle to be removed
     */
    public void remove(int x, int y, int w, int h) {
        // Generate Test Rectangle
        Rectangle testRec = new Rectangle(x, y, w, h);
        
        if (testRec.isInvalid()) {
            // Rectangle rejected: (-1, -1, 2, 4)
            String out = String.format("Rectangle rejected: (%s)", testRec);
            System.out.println(out);
            return;
        }
        
        Iterator<KVPair<String, Rectangle>> it = list.iterator();
        KVPair<String, Rectangle> elem;
        
        while (it.hasNext()) {
            elem = it.next();
            Rectangle rec = elem.getValue();
            
            if (rec != null) {
                if (testRec.equals(rec)) {
                    String name = elem.getKey();
                    list.remove(name);
                    
                    String out = String.format("Rectangle removed: (%s, %s)",
                        name, rec);
                    System.out.println(out);
                    return;
                }
            }
            
            // elem = it.next();
        }
        
        // Not found
        // Rectangle not found: (2, 0, 4, 8)
        String out = String.format("Rectangle not found: (%s)", testRec);
        System.out.println(out);
    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region.
     * 
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {
    
    }

    /**
     * Prints out all the rectangles that intersect each other. Note that 
     * it is better not to implement an intersections
     * method in the SkipList class
     * as the SkipList needs to be agnostic about
     * the fact that it is storing Rectangles. 
     */
    public void intersections() {
        
    }


    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Rectangle to be searched for
     */
    public void search(String name) {
        ArrayList<KVPair<String, Rectangle>> found = list.search(name);
        if (found.size() == 0) {
            // Nothing found
            // Rectangle not found: (a)
            String out = String.format("Rectangle not found: (%s)", name);
            System.out.println(out);
            return;
        }
                
        // Something found
        System.out.println("Rectangles found:");
        
        // Iterate and print all
        for (KVPair<String, Rectangle> elem : found) {
            String out = String.format("(%s, %s)",
                elem.getKey(), elem.getValue());
            System.out.println(out);
        }
    }


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
        list.dump();
    }

}
