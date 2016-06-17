/** A launchable object will be an object, represented by an ImageIcon, which can be launched by a launcher.
 *  This will consist of velocity, acceleration, potential energy due to gravity, kinetic energy, and mass.
 *  This class will allow for a launchable object's variables to be set, as well as allowing for acceleration due to gravity,
 *  and movement of the object.
 *  @author Joey Colaizzo Dino Martinez, Lucas Carey
 *  @version 1.0
 */

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class LaunchableObject extends JLabel {
    /** INSTANCE VARIABLES */
    private Vector velocity;         //Speed in meters per second
    private Vector acceleration;     //acceleration in meters per second squared
    private double potentialEnergy;  //Gravitational potential energy in Joules
    private double kineticEnergy;    //Kinetic energy in Joules
    private double mass;             //Mass in kilograms
    private double xLeft;            // X Left anf Y Left are variables to account for integer truncation. 
    private double yLeft;            // Counts the decimal distance lost in truncation.
    
    /** DEFAULT CONSTRUCTOR */
    public LaunchableObject()
    {
        super();
        //Set all instance variables to generic value
        velocity = new Vector(0.1,0.0);
        acceleration = new Vector(0.0, 10.0);
        potentialEnergy = 0;
        kineticEnergy = 0;
        mass = 0;
    }
    
    /** CONSTRUCTOR TO CREATE JLABEL AND INSTANTIATE VARIABLES 
     *  @param the mass of the object
     *  @param the initial location (x,y) of the object
     *  @param the image that will represent the object
     */
    public LaunchableObject(double theMass, int xPos, int yPos, ImageIcon icon)
    {
        super(icon);
        //Set values to what the user passes in
        setSize(icon.getIconWidth(),icon.getIconHeight());
        setLocation(xPos, yPos);
        velocity = new Vector(0.0, 0.0);
        acceleration = new Vector(0.0,10);
        mass = theMass;
        potentialEnergy = (yPos-100)/100 * mass * acceleration.getYMagnitude();
        kineticEnergy = (mass * velocity.getMagnitude() * velocity.getMagnitude()) / 2;
    }
    
    
    /** ACCESSORS */
    
    
    /**
     * @return the velocity vector of the object.
     */
    public Vector getVelocity()
    {
        return velocity;
    }
    
    
    /**
     * @return the acceleration of the object
     */
    public Vector getAccel()
    {
        return acceleration;
    }
    
    
    /**
     * @param the height of the frame for which the launchable object is an actor
     * @return the potential energy in joules.
     */
    public double getPE(int theFrameHeight)
    {
        potentialEnergy = Math.abs((theFrameHeight - getY()) * mass * acceleration.getYMagnitude()) / 100;
        return potentialEnergy;
    }
    
    
    /**
     * @return the kinetic energy in joules.
     */
    public double getKE()
    {
        kineticEnergy = (mass * velocity.getYMagnitude() * velocity.getYMagnitude()) / 2;
        return kineticEnergy;
    }
    
    
    /**
     * @return the mass of the object
     */
    public double getMass()
    {
        return mass;
    }
    
    /** MUTATORS
     * Methods to accelerate object, and to set all instance variables except for mass, acceleration, and velocity
     */
    //@param Vector object representing velocity
    public void setVelocity(Vector v)
    {
        velocity = v;
    }
    
    //@param Vector object representing acceleration
    public void setAcceleration(Vector a)
    {
        acceleration = a;
    }
    
    //@param double representing potential energy of the launchable object 
    public void setPE(double thePE)
    {
        potentialEnergy = thePE;
    }
    
    //@param double representing kinetic energy
    public void setKE(double theKE)
    {
        kineticEnergy = theKE;
    }
    
    /**
     * Method to change the velocity of the launchable object based on the current acceleration and the amount of time
     * the object is being accelerated
     * @param time in seconds
     */
    public void accelerate(double time)
    {
        //System.out.println(velocity.getYMagnitude() + (acceleration.getMagnitude() * time));
        //velocity.setYMagnitude(velocity.getYMagnitude() + (acceleration.getMagnitude() * time));
        velocity.setYMagnitude(velocity.getYMagnitude() + (.1));
       }
    
    /**
     * Method to move object based on current velocity, and time
     * @param time in seconds
     */
    public void move(double time)
    {
        double xDistance = ((velocity.getXMagnitude() * time) + xLeft) * 100;
        xLeft = (xDistance - (int)xDistance)/100;
        double yDistance = ((velocity.getYMagnitude() * time) + yLeft) * 100;
        yLeft = (yDistance - (int)yDistance)/100;
        setLocation(getX() + (int)xDistance, getY() + (int)yDistance);
    }
}