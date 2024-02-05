
/**
 * This class holds the coordinates and dimensions of a rectangle and methods to
 * check if it intersects or has the same coordinates as an other rectangle.
 * 
 * @author CS Staff
 * 
 * @version 2024-01-22
 */
public class Rectangle {
    // the x coordinate of the rectangle
    private int xCoordinate;
    // the y coordinate of the rectangle
    private int yCoordinate;
    // the distance from the x coordinate the rectangle spans
    private int width;
    // the distance from the y coordinate the rectangle spans
    private int height;

    /**
     * Creates an object with the values to the parameters given in the
     * xCoordinate, yCoordinate, width, height
     * 
     * @param x
     *            x-coordinate of the rectangle
     * @param y
     *            y-coordinate of the rectangle
     * @param w
     *            width of the rectangle
     * @param h
     *            height of the rectangle
     */
    public Rectangle(int x, int y, int w, int h) {
        xCoordinate = x;
        yCoordinate = y;
        width = w;
        height = h;
    }


    /**
     * Getter for the x coordinate
     *
     * @return the x coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }


    /**
     * Getter for the y coordinate
     *
     * @return the y coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }


    /**
     * Getter for the width
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }


    /**
     * Getter for the height
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }


    /**
     * Checks if the invoking rectangle intersects with rec.
     * 
     * @param r2
     *            Rectangle parameter
     * @return true if the rectangle intersects with rec, false if not
     */
    public boolean intersect(Rectangle r2) {
        
        if (this.isInvalid() || r2.isInvalid()) {
            return false;
        }
              
        // One is too far left
        if (this.right() <= r2.left() || r2.right() <= this.left()) {
            return false;
        }
        
        // One is too far down
        if (r2.bottom() <= this.top() || this.bottom() <= r2.top()) {
            return false;
        }
        
        return true;

    }


    /**
     * Checks, if the invoking rectangle has the same coordinates as rec.
     * 
     * @param rec
     *            the rectangle parameter
     * @return true if the rectangle has the same coordinates as rec, false if
     *         not
     */
    public boolean equals(Rectangle rec) {
        // Need same x, same y, same w, same h
        boolean sameX = this.getxCoordinate() == rec.getxCoordinate();
        boolean sameY = this.getyCoordinate() == rec.getyCoordinate();
        boolean sameW = this.getWidth() == rec.getWidth();
        boolean sameH = this.getHeight() == rec.getHeight();
        
        return (sameX && sameY && sameW && sameH);
    }


    /**
     * Outputs a human readable string with information about the rectangle
     * which includes the x and y coordinate and its height and width
     * 
     * @return a human readable string containing information about the
     *         rectangle
     */
    public String toString() {
        int x = this.getxCoordinate();
        int y = this.getyCoordinate();
        int w = this.getWidth();
        int h = this.getHeight();
               
        return String.format("%d, %d, %d, %d", x, y, w, h);
    }


    /**
     * Checks if the rectangle has invalid parameters
     * 
     * @return true if the rectangle has invalid parameters, false if not
     */
    public boolean isInvalid() {
        // Valid Rectangles sit entirely in the 1024 by 1024 "world box"
        // A rectangle is invalid if any portion of it is outside this box
        int x = this.getxCoordinate();
        int y = this.getyCoordinate();
        int w = this.getWidth();
        int h = this.getHeight();
        
        // Must be all positive
        if (x < 0 || y < 0 || w <= 0 || h <= 0) {
            return true;
        }
        
        boolean leftValid = this.insideWorldBox(x);
        boolean topValid = this.insideWorldBox(y);
        boolean rightValid = this.insideWorldBox(x+w);
        boolean bottomValid = this.insideWorldBox(y+h);
        
        boolean valid = leftValid && topValid && rightValid && bottomValid;
        
        return !valid;
    }
    
    private boolean insideWorldBox(int a) {
        return (a >= 0) && (a <= 1024);
    }
    
    private int left() {
        return getxCoordinate();
    }
    
    private int top() {
        return getyCoordinate();
    }
    
    private int right() {
        return getxCoordinate() + getWidth();
    }
    
    private int bottom() {
        return getyCoordinate() + getHeight();
    }
    
}
