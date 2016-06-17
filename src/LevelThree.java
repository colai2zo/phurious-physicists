import java.awt.Cursor;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * In this level the user is able to set the compression of a spring after being given the angle that the object will
 * be launched at. After pressing the launch button, the spring will launch  the Einstein head toward the target.
 * A different amount of points will be awarded depending on the proximity to the center of the target.
 * 
 * To solve this use the formula x= sqrt(((distance)*(gravity)*(mass)) / (spring constant)*(sin(2 * theta)))
 * 
 * @author Joey Colaizzo, Dino Martinez, Lucas Carey
 *
 */

@SuppressWarnings("serial")//Removes "The serializable class Level does not declare a static final serialVersionUID field of type long" warning
public class LevelThree extends Level {

    //Text Labels for all necessary values and images specific to this level
    private JLabel currentAngle;
    private JLabel currentDistance;
    private JLabel currentCompression;
    private JLabel einsteinProp;
    private JLabel springProp;
    
    //private JSlider angleChooser; 
    //Slider to set compression, and compression value
    private JSlider compression;
    private double comp;
    
    //Angle of spring and initial location of projectile
    private int angle;
    private Point einsteinStart;
    
    public LevelThree()
    {
        super();
                
        //create an instance of the launcher
        launcher = new Launcher(randomAngle(), (int)screenSize.getWidth()/20, (int)screenSize.getHeight() - grass.getHeight());
        launcher.setSpringConstant(15000);
        
        //create an instance of einstein's head to launch
        einstein = new LaunchableObject(50.0, launcher.getX() + launcher.getWidth(), launcher.getY() - 30, new ImageIcon(getClass().getResource("/images/einstein.png") ));
        einsteinStart = new Point(einstein.getX(), einstein.getY());
        
        //create a label to display the mass of Einstein
        einsteinProp = new JLabel("Mass: " + einstein.getMass() + " kg");
        einsteinProp.setSize(200, 20);
        einsteinProp.setLocation(5, grass.getY());
        
        //create a label that hovers below the center of the target and displays the distance from the launcher
        //to the center of the target
        currentDistance = new JLabel();
        currentDistance.setText("Horizontal Distance: " + getDistance() + " meters");
        currentDistance.setSize(500,20);
        currentDistance.setLocation((int)screenSize.getWidth()/2, (int)screenSize.getHeight()- 65);
        
        //Display the spring constant on a label
        springProp = new JLabel("Spring Constant: " + (int)launcher.getSpringConstant() + " kg/m");
        springProp.setSize(200, 20);
        springProp.setLocation(einsteinProp.getX() , grass.getY() - springProp.getHeight());
        
        //create a label that displays the current angle of the launcher
        currentAngle = new JLabel();
        currentAngle.setText("Angle: " + launcher.getAngle() + " degrees");
        currentAngle.setSize(200,20);
        currentAngle.setLocation(einsteinProp.getX() , grass.getY() - (2 * springProp.getHeight()));
        
        //A slider that allows the user to modify the compression of the launcher
        compression = new JSlider(0, 680, 0);
        compression.setCursor(new Cursor(Cursor.HAND_CURSOR));
        compression.setOrientation(SwingConstants.HORIZONTAL);
        compression.setSize(300,20);
        compression.setLocation(20, 600);//(int)angleChooser.getY() - 40);
        compression.setOpaque(false);
        compression.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                comp = (int)source.getValue()/(double)1000;
                launcher.setCompression(comp);
                currentCompression.setText("Compression: " + comp + " meters");
            }
        });
        
        //create a label that displays the current compression of the launcher.
        //It will be located next to the compression slider.
        currentCompression = new JLabel();
        currentCompression.setText("Compression: " + comp + " meters");
        currentCompression.setSize(200, 20);
        currentCompression.setLocation(5, compression.getY() + compression.getHeight());
                
        //Set label text for instructions
        infoLabel.setText("<html> &emsp;Welcome to level three! This is the hardest level in the game!" +
                "<br><br>&emsp; In this level, you are given a horizontal distance to the center of the bulls-eye " +
                "(displayed at bottom of screen), the angle of the launcher relative to the ground " +
                "(displayed bottom left), the mass of Einstein (displayed bottom left), and the spring constant of " +
                "the spring that will launch Einstein (bottom left). You must set only the initial compression of the" +
                " spring so that Einstein will land on the center of the bulls-eye. Hint: You will need to combine" +
                " multiple aspects in physics (e.g. conservation of energy, conservation of momentum, dynamics," +
                " kinematics) in order to solve this problem. Remember that you can assume the acceleration due " +
                "to gravity is 10 meters per second squared downward. <br><br>&emsp;First, choose your compression using the slider." +
                " When you are ready, press ‘Launch’ to launch Einstein. Good luck! </html>");

        
        //Add ALL Contents from this class and super class to panel.
        //MUST BE DONE IN EACH SUBCLASS! This is to set order of precedence. The top-most layer painted to a frame
        //is the first thing added; and all components added later are placed beneath all components added first
        add(infoButton);
        add(infoLabel);
        add(launcher);
        add(einstein);
        add(einsteinProp);
        add(compression);
        add(currentAngle);
        add(currentDistance);
        add(currentCompression);
        for (JCheckBox box : graphBoxes)
        {
            box.setVisible(false);
            add(box);
        }
        add(target);
        add(grass);
        add(launch);
        add(score);
        add(resetButton);
        add(homeButton);
        add(springProp);
        add(background);
    }
    
    /**
     * Launch method, sets velocity to intial value, simulates spring animation, and starts timer
     */
    @Override
    public void launch() {
        einstein.setVelocity(new Vector((Math.abs((getVi(comp, launcher, einstein)) * Math.cos(Math.toRadians(launcher.getAngle())))), (-(getVi(comp, launcher, einstein) * Math.sin(Math.toRadians(launcher.getAngle()))))));
        launcher.setCompression(.08);
        launch.setVisible(false);
        compression.setValue(0);
        timer.start();
    }
    
    /**
     * Resets all variables to initial states 
     */
    public void restart()
    {
        super.restart();
        
        //Set location, compression, angle, label text to display angle, label text to display instructions, and label text to display compression
        einstein.setLocation(einsteinStart);
        angle = randomAngle();
        launcher.setAngle(angle);
        currentAngle.setText("Angle: " + launcher.getAngle() + " degrees");
        infoLabel.setText("<html> &emsp;Welcome to level three! This is the hardest level in the game!" +
                "<br><br>&emsp; In this level, you are given a horizontal distance to the center of the bulls-eye " +
                "(displayed at bottom of screen), the angle of the launcher relative to the ground " +
                "(displayed bottom left), the mass of Einstein (displayed bottom left), and the spring constant of " +
                "the spring that will launch Einstein (bottom left). You must set only the initial compression of the" +
                " spring so that Einstein will land on the center of the bulls-eye. Hint: You will need to combine" +
                " multiple aspects in physics (e.g. conservation of energy, conservation of momentum, dynamics," +
                " kinematics) in order to solve this problem. Remember that you can assume the acceleration due " +
                "to gravity is 10 meters per second squared downward. <br><br>&emsp;First, choose your compression using the slider." +
                " When you are ready, press ‘Launch’ to launch Einstein. Good luck! </html>");
        currentDistance.setText("Horizontal Distance: " + getDistance() + " meters");
        currentCompression.setText("Compression: " + comp + " meters");
        resetButton.setVisible(false);
    }

    /**
     * @return double distance in meters from target
     */
    private double getDistance()
    {
        return (((target.getX()+(target.getWidth()/2)) - (einstein.getX()+ (einstein.getWidth()/2)))/(double)100);
    }
    
    /**
     * 
     * @param comp compression in meters -- double precision
     * @param launcher launcher object
     * @param obj projectile
     * @return magnitude of velocity of the projectile
     */
    private double getVi(double comp, Launcher launcher, LaunchableObject obj)
    {
        double vi = Math.sqrt((launcher.getSpringConstant() * comp * comp) / obj.getMass());
        return vi;
    }
    
    /**
     * @return a random angle between 15 and 60
     */
    private int randomAngle()
    {
        return (int)(Math.random() * 45) + 15;
    }
    
    
    @Override
    public boolean isFlightOver(){
        return (einstein.getY() >= screenSize.getHeight() - grass.getHeight() - einstein.getHeight());
    }
}