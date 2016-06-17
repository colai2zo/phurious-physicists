/**
 * This is a launcher class.
 * A launcher is a JLabel containing an ImageIcon with a picture of a spring.
 * The Launcher is set at a certain angle (15 degrees to 60 degrees relative to the horizontal)
 * And has a certain compression (up to 1.36 meters)
 */
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

@SuppressWarnings("serial")
public class Launcher extends JLabel {
    
    /** INSTANCE VARIABLES**/
    private int angle;    //The angle, in degrees, of the spring image
    private double compression; //The compression of the spring in meters.
    private static Icon[] springImages;    //Array of spring images to simulate animation
    private Icon currentIcon;    //Current icon variable to assist the animating process
    private double springConstant;    //Spring constant -- double precision -- necessary for calculations
    private final int LAUNCHER_WIDTH;    //Image width and height
    private final int LAUNCHER_HEIGHT;

    /**
     * CONSTRUCTOR: constructs a launcher with an imageIcon and a default angle theta
     * @param theta the angle
     * @param x and y the coordinates of the location of the launcher
     */
    public Launcher(int theta, int x, int y)
    {
        //Instantiate spring image array
        springImages = new ImageIcon[8];
        
        /** * Fill Icon array with spring pictures. */
        for(int i = 0; i < springImages.length; i++)
        {
            springImages[i] = new ImageIcon(getClass().getResource("/images/spring" + i + ".png"));
        }
        
        //Set instance variables accordingly
        angle = theta;
        currentIcon = springImages[0];
        setIcon(springImages[0]);
        setSize(currentIcon.getIconWidth(), currentIcon.getIconHeight());
        LAUNCHER_WIDTH = getWidth();
        LAUNCHER_HEIGHT = getHeight();
        setLocation(x,y);
    }
    
    /**
     * Paints the launcher at given location with the given angle.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g.create();  
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform aT = g2.getTransform();         //Create an AffineTransform to rotate the image.
        Shape oldshape = g2.getClip();                  
        double x = LAUNCHER_WIDTH;
        double y = LAUNCHER_HEIGHT / 2;
        
        
        aT.rotate(Math.toRadians(-angle), x, y);         //Rotate accordingly
        
        g2.setTransform(aT);
        g2.setClip(oldshape);
        super.paintComponent(g2);
    }
    
    /**MUTATORS**/
    
    /**
     * @param a new spring constant for the spring
     */
    public void setSpringConstant(double k){
        springConstant = k;
    }
    
    
    /**
     * Set a new angle for the launcher and rotate it accordingly.
     * @param an integer angle relative to the horizontal between 15 and 60 degrees.
     */
    public void setAngle(int theta)
    {
        angle = theta;
        getParent().repaint();
    }
   
    
    /**
     * @param a new compression for the spring in meters.
     * Compression must be a valid double value from 0 - .68 meters.
     * POST CONDITION: The value for compression is set and the spring is repainted with an appropriately compressed image.
     */
    public void setCompression(double newCompression)
    {
        if(newCompression < 0 || newCompression > .68)
            return;
        else{
            if(newCompression <= .08){
                currentIcon = springImages[0];
                setIcon(currentIcon);
                }else if(newCompression > .08 && newCompression <= .17){
                    currentIcon = springImages[1];
                    setIcon(currentIcon);
                }else if(newCompression > .17 && newCompression <= .25){
                    currentIcon = springImages[2];
                    setIcon(currentIcon);
                }else if(newCompression > .25 && newCompression <= .34){
                    currentIcon = springImages[3];
                    setIcon(currentIcon);
                }else if(newCompression > .34 && newCompression <= .42){
                    currentIcon = springImages[4];
                    setIcon(currentIcon);
                }else if(newCompression > .42 && newCompression <= .51){
                    currentIcon = springImages[5];
                    setIcon(currentIcon);
                }else if(newCompression > .51 && newCompression <= .59){
                    currentIcon = springImages[6];
                    setIcon(currentIcon);
                }else if(newCompression > .59 && newCompression <= .68){
                    currentIcon = springImages[7];
                    setIcon(currentIcon);
                }
            setSize(currentIcon.getIconWidth(), currentIcon.getIconHeight());
            compression = newCompression;
            repaint();
            getParent().repaint();
        }
         
    }
    
    /** ACCESSORS **/
    
    /*
     * @return the compression of the spring in meters
     */
    public double getCompression(){
        return compression;
    }
    
    
    /*
     * @return the angle relative to the horizontal in degrees
     */
    public int getAngle(){
        return angle;
    }
    
    
    /*
     * @return the spring constant of the spring
     */
    public double getSpringConstant()
    {
        return springConstant;
    }
}


