import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import student.TestableRandom;

/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author CS Staff and OpenDSA 15.01
 * 
 * @version 2024-01-22
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipList<K extends Comparable<? super K>, V>
    implements Iterable<KVPair<K, V>> {
    private SkipNode head; // First element (Sentinel Node)
    private int size; // number of entries in the Skip List
    private Random rng;
    private int level;

    /**
     * Initializes the fields head, size and level
     */
    public SkipList() {
        head = new SkipNode(null, 0);
        size = 0;
        this.rng = new TestableRandom();
        level = -1;
    }

    /* returns a random level (using geometric distribution), minimum of 1 */
    // Ideally, you should call this method inside other methods
    // keep this method private. Since, we do not have any methods to call
    // this method at this time, we keep this
    // publicly accessible and testable.


    /**
     * Returns a random level (using a geometric distribution,
     * minimum of 1.
     * 
     * NOTE: Should this be a minimum of 0?
     * 
     * @return a random level
     */
    public int randomLevel() {
        int temp = 1;
        while (rng.nextBoolean())
            temp++;
        return temp;
    }


    /**
     * Searches for the KVPair using the key which is a Comparable object.
     * 
     * @param key
     *            key to be searched for
     * @return list
     *         an ArrayList of all records found
     */
    public ArrayList<KVPair<K, V>> search(K key) {
        // Return a list of all nodes with the
        // specified key

        ArrayList<KVPair<K, V>> list = new ArrayList<KVPair<K, V>>();

        if (size == 0) {
            return list;
        }

        // Start at the head
        SkipNode x = head;

        // SkipList search
        for (int i = level; i >= 0; i--) {
            while ((x.forward[i] != null) && (x.forward[i].element().getKey()
                .compareTo(key) < 0)) {
                x = x.forward[i];
            }
        }

        // Move to actual record if it exists
        x = x.forward[0];

        // If multiple matching records, they will be right next
        // to each other. So add all of them to the list
        while ((x != null) && (x.element().getKey().compareTo(key) == 0)) {
            // Add it to list
            list.add(x.element());

            // Advance by 1
            x = x.forward[0];
        }

        return list;
    }


    /**
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }


    /**
     * Inserts the KVPair in the SkipList at its appropriate spot as designated
     * by its lexicoragraphical order.
     * 
     * @param it
     *            the KVPair to be inserted
     */
    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
        int newLevel = randomLevel(); // New node's level
        if (newLevel > level) { // If new node is deeper
            adjustHead(newLevel); // extend header list
        }

        // Track the end of each level
        SkipNode[] update = (SkipNode[])Array.newInstance(
            SkipList.SkipNode.class, level + 1);

        // Start at header
        SkipNode x = head;
        for (int i = level; i >= 0; i--) {
            while ((x.forward[i] != null) && (x.forward[i].element().getKey()
                .compareTo(it.getKey()) < 0)) {
                x = x.forward[i];
            }
            update[i] = x; // Track end at level i
        }

        x = new SkipNode(it, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            x.forward[i] = update[i].forward[i]; // Who x points to
            update[i].forward[i] = x; // Who points to x
        }
        size++;
    }


    /**
     * Increases the number of levels in head so that no element has more
     * indices than the head.
     * 
     * @param newLevel
     *            the number of levels to be added to head
     */
    @SuppressWarnings("unchecked")
    public void adjustHead(int newLevel) {
        SkipNode oldHead = head;
        KVPair<K, V> pair = new KVPair<K, V>(null, null);
        head = new SkipNode(pair, newLevel);
        for (int i = 0; i <= level; i++) {
            head.forward[i] = oldHead.forward[i];
        }
        level = newLevel;
    }


    /**
     * Removes the KVPair that is passed in as a parameter and
     * returns the KVPair that was removed
     * 
     * @param key
     *            the key to be removed
     * @return returns KVPair that was removed
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
        // Build update array
        // Track the end of each level
        SkipNode[] update = (SkipNode[])Array.newInstance(
            SkipList.SkipNode.class, level + 1);

        // Start at header
        SkipNode x = head;
        for (int i = level; i >= 0; i--) {
            while ((x.forward[i] != null) && (x.forward[i].element().getKey()
                .compareTo(key) < 0)) {
                x = x.forward[i];
            }
            update[i] = x; // Track end at level i
        }

        // Move forward to actual record if exists
        x = x.forward[0];

        // If x is null, or key does not match, return null
        if (x == null) {
            return null;
        }
        if (x.element().getKey().compareTo(key) != 0) {
            return null;
        }

        // We know it exists at this point

        // For each node in update array
        // If it points to record, disconnect to null
        // Then re-point it around x
        for (int i = level; i >= 0; i--) {
            SkipNode node = update[i];

            // Ignore nodes that have null pointers,
            // or that do not point to x
            if (node.forward[i] == null) {
                continue;
            }
            if (node.forward[i].element().getKey().compareTo(key) != 0) {
                continue;
            }

            // We know this node points to x
            // Disconnect it
            node.forward[i] = null;

            // Reconnect pointers around x
            if (x.forward[0] == null) {
                continue;
            }
            if (x.forward[0].level >= i) {
                node.forward[i] = x.forward[0];
            }
        }

        size--;
        return x.element();
    }


    /**
     * Prints out the SkipList in a human readable
     * format to the console.
     */
    public void dump() {
        System.out.println("SkipList dump:");

        // Start at head
        SkipNode x = head;
        String out = "";

        if (size == 0) {
            // Print head
            out = String.format("Node with depth %d, Value null", 1);
            System.out.println(out);

            // Print size
            out = String.format("SkipList size is: %d", size);
            System.out.println(out);
            return;
        }

        // Only go at level 0
        while (x != null) {
            // Print x
            if (x.element().getValue() != null) {
                out = String.format("Node with depth %d, Value (%s, %s)",
                    x.level, x.element().getKey(), x.element().getValue());
            }
            else {
                out = String.format("Node with depth %d, Value null", x.level);
            }

            System.out.println(out);

            // Advance
            x = x.forward[0];
        }

        // Print size
        out = String.format("SkipList size is: %d", size);
        System.out.println(out);
    }

    /**
     * This class implements a SkipNode for the SkipList data structure.
     * 
     * @author CS Staff
     * 
     * @version 2016-01-30
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // An array of pointers to subsequent nodes
        private SkipNode[] forward;
        // the level of the node
        private int level;

        /**
         * Initializes the fields with the required KVPair and the number of
         * levels from the random level method in the SkipList.
         * 
         * @param tempPair
         *            the KVPair to be inserted
         * @param level
         *            the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,
                level + 1);
            this.level = level;
        }


        /**
         * Returns the KVPair stored in the SkipList.
         * 
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }

    }


    private class SkipListIterator implements Iterator<KVPair<K, V>> {
        private SkipNode current;

        public SkipListIterator() {
            current = head;
        }


        @Override
        public boolean hasNext() {
            return current.forward[0] != null;
        }


        @Override
        public KVPair<K, V> next() {
            KVPair<K, V> elem = current.forward[0].element();
            current = current.forward[0];
            return elem;
        }

    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new SkipListIterator();
    }

}
