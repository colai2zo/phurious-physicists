/**
 * A vector is anything which has both a magnitude and a direction.
 * This is the vector class, which will be implemented in other classes to use for any vector values, such as velocity.
 * This class will store the magnitude and the direction of the vector in question, with double precision
 * @author Joey Colaizzo, Lucas Carey, Dino Martinez
 * @version 1.0
 */
import java.lang.Math;


public class Vector 
{
    /** INSTANCE VARIABLES */
    double magnitude; //Magnitude of the Vector
    double direction; //Direction of the object, in degrees from the horizontal
    double yMag;        //The magnitude of the y-component of the vector.
    double xMag;        //The magnitude of the x-component of the vector.
    
    /** DEFAULT CONSTRUCTOR */
    public Vector()
    {
        xMag = 0;
        yMag = 0;
        magnitude = 0;
        direction = 0;
    }
    
    
    /**
     * Constructor to set magnitudes
     * @param m, double equal to magnitude
     * @param d, double equal to direction in degrees from horizontal
     */
    public Vector(double x, double y)
    {
        yMag = y;
        xMag = x;
        magnitude = getMagnitude();
        direction = getDirection();
    }
    
    /** ACCESSORS */
    
    /**
     * methods to return x magnitude, y magnitude, or total magnitude
     * @return x value or y value
     */
    public double getXMagnitude()
    {
        return xMag;
    }
    
    
    public double getYMagnitude()
    {
        return yMag;
    }
    
    
    public double getMagnitude()
    {
        magnitude = Math.sqrt(Math.pow(xMag,2)+Math.pow(yMag,2));
        return magnitude;
    }
    
    /** method to return direction, in degrees from the horizontal
     * @return direction with double precision
     */
    public double getDirection()
    {
        direction = Math.atan(yMag/xMag);
        return direction;
    }
    
    /** MUTATORS */
    
    /** Methods to set x magnitude, y magnitude, or resultant magnitude */
    
    /** Method to set x magnitude, keeping y magnitude constant.
     * @param double x magnitude
     * POST CONDITION: magnitude of resultant is edited such that y magnitude is constant, but x magnitude has changed;
     *  direction has been edited accordingly
     */
    public void setXMagnitude(double x)
    {
        xMag = x;
        setMagnitude();    //Update magnitude and direction
        setDirection();
    }
    
    
    /** Method to set y magnitude, keeping x magnitude constant.
     * @param double y magnitude
     * POST CONDITION: magnitude of resultant is edited such that x magnitude is constant, but y magnitude has changed;
     *  direction has been edited accordingly
     */
    public void setYMagnitude(double y)
    {
        yMag = y;
        setMagnitude();    //Update magnitude and direction
        setDirection();
    }
    
    
    /**
     * Helper method that sets the magnitude according to the components using trigonometry.
     */
    private void setMagnitude()
    {
        magnitude = Math.sqrt(Math.pow(xMag,2)+Math.pow(yMag,2));
    }
    
    
    /**
     * Helper method that sets the direction according to the components, using trigonometry.
     */
    private void setDirection()
    {
        direction = Math.atan(yMag/xMag);
    }
}