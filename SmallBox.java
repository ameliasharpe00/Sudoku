/* Sudoku HW9
 * 
 * Name: Amelia G L Sharpe
 * PennKey: amsharpe
 * Recitation: 206
 * Execution: java SmallBox
 * This is a class which contains the object SmallBox. SmallBox is what creates each
 * of the 36 small boxes in the 6x6 sudoku grid
 */

public class SmallBox {
    private static final double HALF_WIDTH = (double) 1 / 12;
    private double x;
    private double y;
    
    /*
     * Constructor: it initalizes values for instances of this class
     * */
    public SmallBox(double x, double  y) {
        this.x = x;
        this.y = y;
    }
    
    /*
     * Description: this draws one of the 36 sudoku boxes
     * no input
     * output: no output - just draws theboard
     * */
    public void draw() {
        PennDraw.setPenRadius(0.002);
        PennDraw.square(x, y, HALF_WIDTH);  
    }
    /*
     * Description: retrieves the x coordinate of the current box
     * no input
     * output: returns the current x coordinate of the small box
     *  */
    public double getX() {
        return this.x;
    }
    /*
     * Description: retrieves the y coordinate of the current box
     * no input
     * output: returns the current y coordinate of the small box
     *  */
    public double getY() {
        return this.y;
    }
    /*
     * Description: changes the x coordinate of the current box
     * input: the new x coordinate of the current box
     * no output
     *  */
    public void setX(double newX) {
        this.x = newX;
    }
    /*
     * Description: changes the y coordinate of the current box
     * the new y coordinate of the current box
     * no output
     *  */
    public void setY(double newY) {
        this.y = newY;
    }
     /*
     * Description: retrieves the half width of each box (all the same size)
     * no input
     * output: returns the current halfWidth of the small box
     *  */
    public static double getHalfWidth() {
        return HALF_WIDTH;
    }
}

