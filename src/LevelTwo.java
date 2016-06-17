import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * In this level the user is able to set the initial velocity of a projectile after being given the angle that it                                             will
 * be launched at. After pressing the launch button, the spring will launch the Einstein head toward the target.
 * A different amount of points will be awarded depending on the proximity to the center of the target.
 * 
 * Solve this level by using the range formula:
 *         t = (v*v*sin(2 * angle)) / 10.0
 * @author Joey Colaizzo, Dino Martinez, Lucas Carey
 */

@SuppressWarnings("serial")//Removes "The serializable class Level does not declare a static final serialVersionUID field of type long" warning
public class LevelTwo extends Level {
    private JSlider viChooser;
    private double vMag;    //Magnitude of initial velocity
    private int vDir;    //Angle of launch
    private Vector currentV;    //Vector reference object representing projectile's current velocity
    
    //Labels to display necessary information to user
    private JLabel degreeLabel;    
    private JLabel speedLabel;
    private JLabel massLabel;
    private JLabel distanceLabel;
    
    public LevelTwo()
    {
        super();

        //Instantiate launcher, launchableObject, and sliders
        launcher = new Launcher((int)(Math.random() * 45) + 15, (int)screenSize.getWidth() / 20, (int)screenSize.getHeight() - grass.getHeight());
        launcher.setSpringConstant(150000);
        einstein = new LaunchableObject(100, launcher.getX() + launcher.getWidth() + 5, launcher.getY() - 30, new ImageIcon(getClass().getResource("/images/einstein.png")));
        
        vMag = 1;
        vDir = launcher.getAngle();
        
        viChooser = new JSlider(JSlider.HORIZONTAL, 100, 2000, 100);
        viChooser.setSize(300, 50);
        viChooser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viChooser.setLocation(20,600);
        viChooser.setOpaque(false);
        viChooser.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                vMag = source.getValue()/(double)100;
                speedLabel.setText("Initial Velocity: " + vMag + "meters per second");
                launcher.setCompression(Math.sqrt((einstein.getMass() * Math.pow(vMag, 2)) /launcher.getSpringConstant()));
            }
        });
        
        launch.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.out.print("launch");
                launch();
            }
        });
        
        //Declare all property labels
        degreeLabel = new JLabel("Angle: " + launcher.getAngle() + " degrees");
        degreeLabel.setLocation(5, grass.getY()-20);
        degreeLabel.setSize(500, 20);
        
        speedLabel = new JLabel("Initial Velocity: 1.0 meters per second");
        speedLabel.setLocation(viChooser.getX(), viChooser.getY() - 10);
        speedLabel.setSize(500, 100);
    
        massLabel = new JLabel("Mass: 100 kg");
        massLabel.setLocation(5, grass.getY());
        massLabel.setSize(500, 20);
        
        distanceLabel = new JLabel("Distance: " + getDistance() + " meters");
        distanceLabel.setLocation((int)screenSize.getWidth() / 2, target.getY() + target.getHeight() + 10);
        distanceLabel.setSize(500, 20);
        
        infoLabel.setText("<html>&emsp;Welcome to level two! This is the easier than level three, " +
                "but harder than level one.<br><br>&emsp; In this level, you are given a horizontal distance to the" +
                " center of the bulls-eye (displayed at bottom of screen), the angle of the launcher relative to the " +
                "ground (displayed bottom left), and the mass of Einstein (displayed bottom left). You must set only" +
                " the initial velocity of Einstein so that he will land on the center of the bulls-eye. Remember" +
                " that you can assume the acceleration due to gravity is 10 meters per second squared downward. <br><br>&emsp;First," +
                " choose your velocity using the slider. When you are ready, press ‘Launch’ to launch Einstein." +
                " Good luck!</html>");

                
         //Add ALL Contents from this class and super class to panel.
        //MUST BE DONE IN EACH SUBCLASS! This is to set order of precedence. The top-most layer painted to a frame
            //is the first thing added; and all components added later are placed beneath all components added first
        add(viChooser);
        add(launcher);
        add(infoButton);
        add(infoLabel);
        add(einstein);
        add(launch);
        add(target);
        add(massLabel);
        add(distanceLabel);
        for (JCheckBox box : graphBoxes)
        {
            box.setVisible(false);
            add(box);
        }
        add(speedLabel);
        add(degreeLabel);
        add(score);
        add(resetButton);
        add(nextLevelButton);
        add(homeButton);
        add(grass);
        add(background);
    }
    
    /**
     * Overridden Launch method, sets intial velocity, removes launch button, simulates spring animation, and starts level timer
     */
    @Override
    public void launch() 
    {
        currentV = new Vector(vMag * Math.abs(Math.cos(Math.toRadians(vDir))),  -(vMag * (Math.sin(Math.toRadians(vDir)))));
        einstein.setVelocity(currentV);
        launch.setVisible(false);
        launcher.setCompression(0);
        timer.start();
    }
    
    /**
     * Restart method to reset all variables to intial conditions
     */
    public void restart()
    {
        super.restart();
        
        //Set properties of launcher, projectile, and all labels to their initial states
        launcher.setAngle((int)(Math.random() * 45) + 15);
        speedLabel.setText("Initial Velocity: 1.0 meters per second");
        einstein.setLocation(launcher.getX() + launcher.getWidth() + 5, (int)screenSize.getHeight() - grass.getHeight() - 26);
        distanceLabel.setText("Distance: " + getDistance() +  " meters");
        massLabel.setText("Mass: 100 kg");
        degreeLabel.setText("Angle: " + launcher.getAngle() + " degrees");
        //angleChooser.setValue(15);
        infoLabel.setText("<html>&emsp;Welcome to level two! This is the easier than level three, " +
                "but harder than level one.<br><br>&emsp; In this level, you are given a horizontal distance to the" +
                " center of the bulls-eye (displayed at bottom of screen), the angle of the launcher relative to the " +
                "ground (displayed bottom left), and the mass of Einstein (displayed bottom left). You must set only" +
                " the initial velocity of Einstein so that he will land on the center of the bulls-eye. Remember" +
                " that you can assume the acceleration due to gravity is 10 meters per second squared downward. <br><br>&emsp;First," +
                " choose your velocity using the slider. When you are ready, press ‘Launch’ to launch Einstein." +
                " Good luck!</html>");
        
        //Sets slider value to its intial state, as well as resetting velocity and launch angle values
        viChooser.setValue(100);
        vDir = launcher.getAngle();
        vMag = 1;
    }
    
    
    /**
     * Method to fix rounding error on distance label (was displaying things like "8.000000000000000001 meters")
     */
    private BigDecimal getDistance()
    {
        BigDecimal roundedVal = BigDecimal.valueOf(((target.getX()+(target.getWidth()/2)) - (einstein.getX()+ (einstein.getWidth()/2)))/(double)100);
        return roundedVal;
    }
}