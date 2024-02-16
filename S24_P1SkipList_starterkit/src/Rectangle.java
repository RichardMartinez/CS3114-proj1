
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
    private int x;
    // the y coordinate of the rectangle
    private int y;
    // the distance from the x coordinate the rectangle spans
    private int w;
    // the distance from the y coordinate the rectangle spans
    private int h;

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
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }


    /**
     * Getter for the x coordinate
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }


    /**
     * Getter for the y coordinate
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }


    /**
     * Getter for the width
     *
     * @return the width
     */
    public int getW() {
        return w;
    }


    /**
     * Getter for the height
     *
     * @return the height
     */
    public int getH() {
        return h;
    }


    /**
     * Checks if the invoking rectangle intersects with rec.
     * 
     * @param r2
     *            Rectangle parameter
     * @return true if the rectangle intersects with rec, false if not
     */
    public boolean intersect(Rectangle r2) {
        // One is invalid
        if (this.isInvalid()) {
            return false;
        }

        if (r2.isInvalid()) {
            return false;
        }

        // One is too far left
        if (this.right() <= r2.left()) {
            return false;
        }

        if (r2.right() <= this.left()) {
            return false;
        }

        // One is too far down
        if (r2.bottom() <= this.top()) {
            return false;
        }

        return !(this.bottom() <= r2.top());

    }


    /**
     * Checks if the invoking rectangle intersects with region.
     * 
     * @param region
     *            Rectangle parameter
     * @return true if the rectangle intersects with region, false if not
     */
    public boolean intersectRegion(Rectangle region) {
        // One is invalid
        if (this.isInvalid()) {
            return false;
        }

        // It does not matter if region is invalid

        // One is too far left
        if (this.right() <= region.left()) {
            return false;
        }

        if (region.right() <= this.left()) {
            return false;
        }

        // One is too far down
        if (region.bottom() <= this.top()) {
            return false;
        }

        return !(this.bottom() <= region.top());

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
        boolean sameX = this.getX() == rec.getX();
        boolean sameY = this.getY() == rec.getY();
        boolean sameW = this.getW() == rec.getW();
        boolean sameH = this.getH() == rec.getH();

        return (sameX && sameY && sameW && sameH);
    }


    /**
     * Checks, if the invoking rectangle has the same coordinates as rec.
     * 
     * @param rec
     *            the rectangle parameter
     * @return true if the rectangle has the same coordinates as rec, false if
     *         not
     */
    public boolean equals(Object rec) {
        return false;
    }


    /**
     * Outputs a human readable string with information about the rectangle
     * which includes the x and y coordinate and its height and width
     * 
     * @return a human readable string containing information about the
     *         rectangle
     */
    public String toString() {
        return String.format("%d, %d, %d, %d", x, y, w, h);
    }


    /**
     * Checks if the rectangle has invalid parameters
     * 
     * @return true if the rectangle has invalid parameters, false if not
     */
    public boolean isInvalid() {
        // Valid Rectangles sit entirely in the 1024 by 1024 "world box"
        // A rectangle is invalid if any portion
        // of it is outside this box
        // Must be all positive
        if (x < 0) {
            return true;
        }

        if (y < 0) {
            return true;
        }

        if (w <= 0) {
            return true;
        }

        if (h <= 0) {
            return true;
        }

        boolean leftValid = insideWorldBox(left());
        boolean topValid = insideWorldBox(top());
        boolean rightValid = insideWorldBox(right());
        boolean bottomValid = insideWorldBox(bottom());

        boolean valid = leftValid && topValid && rightValid && bottomValid;

        return !valid;
    }


    private boolean insideWorldBox(int a) {
        return (a >= 0) && (a <= 1024);
    }


    private int left() {
        return x;
    }


    private int top() {
        return y;
    }


    private int right() {
        return x + w;
    }


    private int bottom() {
        return y + h;
    }

}
