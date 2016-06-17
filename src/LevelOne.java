/**
 * This is the code for the level one class.
 * In level one of the game, the user will select a height above the ground and an initial velocity in order to try to get it on target.
 * 
 * To solve this level, choose an arbitrary value for height (preferrably a large value), and then solve for
 *     the amount of time it will take for the projectile to hit the ground with sqrt((2d)/a) where a is 10.0 and d is 
 *         the height of the projectile. Then, solve for intial velocity by using the formula v = d/t where d is height and
 *             t is the time you just solved for.
 * 
 * @author Joey Colaizzo, Dino Martinez, Lucas Carey
 * @version 1.0
 */

import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")//Removes "The serializable class Level does not declare a static final serialVersionUID field of type long" warning
public class LevelOne extends Level {

    /** GAME LEVEL VARIBALES */
    private JSlider heightChooser;    //Slider which the user changes to edit the initial height of the projectile
    private JSlider velocityChooser;    //Slider which the user changes to edit the initial velocity of the projectile
   
    //Sub-level images
    private JLabel platform;
    private JLabel heightLabel;
    private JLabel velocityLabel;
    
    //Projectile's initial velocity
    private double initialVelocity;
    
    //These variables are necessary in order to keep track of the original location of the three JLabels
    private int platform_x;
    private int platform_y;
    private int launcher_x;
    private int launcher_y;
 
    
    /**
     * DEFAULT CONSTRUCTOR 
     */
    public LevelOne(){
        super();
        
        //Instantiate and set properties for platform, einstein, and launcher.
        platform = new JLabel(new ImageIcon(getClass().getResource("/images/platform.png")));
        platform.setLocation((int)screenSize.getWidth()/8,(int)screenSize.getHeight()-grass.getHeight()-100);
        platform.setSize(200,1000);
        platform_x = platform.getX();
        platform_y = platform.getY();
        
        einstein = new LaunchableObject(25,platform.getX()+platform.getWidth()-25,platform.getY()-new ImageIcon(getClass().getResource("/images/einstein.png")).getIconHeight(),new ImageIcon(getClass().getResource("/images/einstein.png")));
        einstein_x = einstein.getX();
        einstein_y = einstein.getY();
        
        launcher = new Launcher(0,einstein_x-125,einstein_y);
        launcher_x = launcher.getX();
        launcher_y = launcher.getY();
        launcher.setSpringConstant(5406);
        initialVelocity = 1;
        
        //Set infoLabel(Uses HTML in order to create a multi-line JLabel. <br> simulates a "return" key, "&emsp" simulates a TAB press).
        infoLabel.setText("<html>&emsp;Welcome to level one! This is the easiest level in the game." +
                " <br><br>&emsp;In this level, you are given a horizontal distance to the center of the bulls-eye" +
                " (displayed at bottom of screen), and must set the height and initial velocity of Einstein so " +
                "that he will land on the center of the bulls-eye. Remember that you can assume the acceleration due" +
                " to gravity is 10 meters per second squared downward. <br><br>&emsp;First, choose your velocity and" +
                " height using the specified sliders. When you are ready, press launch to launch Einstein." +
                " Good luck!</html>");

        
        //Set all properties  for the sliders.
        heightChooser = new JSlider(JSlider.VERTICAL,100,500,100);
        heightChooser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        heightChooser.setSize(20,300);
        heightChooser.setOpaque(false);
        heightChooser.setLocation(0,(int)screenSize.getHeight()-100-heightChooser.getHeight());

        velocityChooser = new JSlider(JSlider.HORIZONTAL,100,2000,100);
        velocityChooser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        velocityChooser.setSize(400,20);
        velocityChooser.setOpaque(false);
        velocityChooser.setLocation(0,(int)screenSize.getHeight()-100);

        //Instantiate and set all properties for labels
        heightLabel = new JLabel("Height: 1 Meter");
        heightLabel.setSize(300,20);
        heightLabel.setLocation(0,(int)screenSize.getHeight()-150-heightChooser.getHeight());
        
        velocityLabel = new JLabel("Initial Velocity: 1 meter per second");
        velocityLabel.setLocation(velocityChooser.getX()+20, velocityChooser.getY()+20);
        velocityLabel.setSize(300,50);
        
        distanceLabel = new JLabel("Horizontal Distance: " + (((target.getX() + (target.getWidth()/2)) - (einstein.getX() + (einstein.getWidth()/2)))/(double)100) + " meters");
        distanceLabel.setSize(300,50);
        distanceLabel.setLocation((int)screenSize.getWidth()/2, (int)screenSize.getHeight()-85);
        
        
        /**Add Change Listeners for sliders.**/
        
        //Adjusts velocity of einstein according to slider state.
        //Sets spring compression and velocity label accordingly.
        velocityChooser.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                if(!isFlightOver())
                {
                    JSlider source = (JSlider)e.getSource();    //This returns the JSlider whose state is changed
                    initialVelocity = (int)source.getValue()/(double)100; //getValue() method returns a double representing the slider's state
                    einstein.setVelocity(new Vector(initialVelocity,0));
                    velocityLabel.setText("Initial Velocity: " + initialVelocity + " meters per second");
                    launcher.setCompression(Math.sqrt((einstein.getMass()*Math.pow(initialVelocity, 2))/launcher.getSpringConstant()));
                }
            }
        });
        
        
        //Adjusts height of platform, einstein, and launcher acording to slider state.
        //Adjusts height label accordingly
        heightChooser.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                if(!isFlightOver())
                {
                    JSlider source = (JSlider)e.getSource();
                    int currentHeight = (int)source.getValue();         
                    platform.setLocation(platform_x,platform_y -(currentHeight)+ 100);
                    einstein.setLocation(einstein_x,einstein_y -(currentHeight)+ 100);
                    launcher.setLocation(launcher_x,launcher_y -(currentHeight)+ 100);
                    heightLabel.setText("Height: "+(double)currentHeight/100+" Meter(s)");
                }
            }
        });
        
        //Add ALL Contents from this class and super class to panel.
        //MUST BE DONE IN EACH SUBCLASS! This is to set order of precedence. The top-most layer painted to a frame
            //is the first thing added; and all components added later are placed beneath all components added first
        add(heightLabel);
        add(heightChooser);
        add(velocityLabel);
        add(infoButton);
        add(infoLabel);
        add(velocityChooser);
        add(distanceLabel);
        add(resetButton);
        add(nextLevelButton);
        add(homeButton);
        for (JCheckBox box : graphBoxes)
        {
            box.setVisible(false);
            add(box);
        }
        add(einstein);
        add(target);
        add(grass);
        add(platform);
        add(score);
        add(launch);
        add(launcher);
        add(background);
        
    }
    
    
    @Override
    /**
     * Specifies what will happen when the launch button is pressed.
     * Releases spring, sets velocity, and starts timer.
     */
    public void launch() {
        launcher.setCompression(0);
        launch.setVisible(false);
        einstein.setVelocity(new Vector(initialVelocity,0.0));
        timer.start();
    }
    
    
    @Override
    /**
     * Add Level One Specific Elements to the reset.
     */
    public void restart()
    {
        super.restart();
        
        //Set properties of platform
        platform.setLocation((int)screenSize.getWidth()/8,(int)screenSize.getHeight()-grass.getHeight()-100);
        platform_x = platform.getX();
        platform_y = platform.getY();
        
        //Set properties of projectile
        einstein.setLocation(platform.getX()+platform.getWidth()-25,platform.getY()-new ImageIcon(getClass().getResource("/images/einstein.png")).getIconHeight());
        einstein_x = einstein.getX();
        einstein_y = einstein.getY();
        
        //Set properties of launcher
        launcher.setLocation(einstein_x-125,einstein_y);
        launcher_x = launcher.getX();
        launcher_y = launcher.getY();
        
        //resets instructions text
        infoLabel.setText("<html>&emsp;Welcome to level one! This is the easiest level in the game." +
                " <br><br>&emsp;In this level, you are given a horizontal distance to the center of the bulls-eye" +
                " (displayed at bottom of screen), and must set the height and initial velocity of Einstein so " +
                "that he will land on the center of the bulls-eye. Remember that you can assume the acceleration due" +
                " to gravity is 10 meters per second squared downward. <br><br>&emsp;First, choose your velocity and" +
                " height using the specified sliders. When you are ready, press launch to launch Einstein." +
                " Good luck!</html>");
        
        //Reste value of each slider, set text of the distance label, and set reset button to invisible
        heightChooser.setValue(100);
        velocityChooser.setValue(100);
        distanceLabel.setText("Horizonatal Distance: " + (((target.getX() + (target.getWidth()/2)) - (einstein.getX() + (einstein.getWidth()/2)))/(double)100) + " meters");
        
        resetButton.setVisible(false); 
    }
    

}


