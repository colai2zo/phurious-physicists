/** This is the abstract level class, which will have three subclasses, each being a level. This will contain all objects and 
 *  methods required for a level, with or without implementation. 
 *  Each Level will have the same basic components such as a target, background, grass, performance label, launcher, and launchable object.
 *  Other components will be specifically defined in the 3 level subclasses.
 *  Each level will hold components in a JPanel.
 *  @author Dino Martinez, Joey Colaizzo, Lucas Carey
 *  @Version 1.0
 */
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;
import java.util.ArrayList;

@SuppressWarnings("serial")    //Removes "The serializable class Level does not declare a static final serialVersionUID field of type long" warning
public abstract class Level extends JPanel {
    /**GAME LEVEL COMPONENTS**/
    protected boolean infoIsClicked;    //Stores whether to display the instructions button
    protected boolean completed;    //Stores whether a level is completed or not
    protected Launcher launcher;    //Reference object extending JLabel which represents our Spring Launcher
    protected Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());  //size of screen
    protected LaunchableObject einstein; //Reference Object extending JLabel which represents the projectile
    protected int einstein_x;    //Original X position of the projectile
    protected int einstein_y;    //Original Y position of the projectile
    protected ActionListener timerListener;    //Listener for game timer
    protected Timer timer;    //Game timer
    protected Point targetPlace;  //location of target
    
    /* Game level Images */
    protected JLabel background;
    protected JLabel grass;    
    protected JLabel target;
    protected JLabel score; 
    protected JLabel infoLabel;  
    protected JLabel distanceLabel;
    
    /* Game level Buttons */
    
    //Buttons to always be displayed when a level is running 
    protected JButton infoButton;    //When pressed, will display instructions
    
    //Buttons to be displayed after flight is over, or to be set invisible during flight
    protected JButton nextLevelButton;    //When pressed, will immediately switch to the level succeeding this one
    protected JButton homeButton;    //When pressed, will return to startScreen
    protected JButton resetButton;    //When pressed, will reset current level
    protected JButton launch; //when pressed, will launch the object
    
     
    /** GRAPH LEVEL VARIABLES */
    protected double accumulatedTime;    //total time passed during flight
    protected ArrayList<Point2D.Double> vPoints;    //Point lists(stored as two double-precision values in the
    protected ArrayList<Point2D.Double> accelPoints;   //format (x,y)) for all relevant variables
    protected ArrayList<Point2D.Double> kePoints;
    protected ArrayList<Point2D.Double> pePoints;
    protected ArrayList<Point2D.Double> positionPoints;
    protected Graph[] graphs;    //Reference objects which represent Graphs for each variable
    protected JCheckBox[] graphBoxes;    //Check boxes to be displayed after flight is complete, will display corresponding graph
    protected ItemListener checkBoxListener;    //Listener to be added to each checkBox
    protected JWindow[] graphFrames;    //Frame to store Graph, and be displayed upon click of corresponding checkBox
    
    //Launch method to be implemented in each subclass, based on a sub-specific state
    public abstract void launch();
    
    
    /**
     * DEFAULT CONSTRUCTOR
     */
    public Level()
    {
        //Tell JPanel to not use Layout Manager
        setLayout(null);
        
        /**Instantiate all objects which are identical across levels*/
        
        //Set properties for background Image
        background = new JLabel();
        background.setLocation(-10,0);
        background.setSize(screenSize);
        background.setIcon(new ImageIcon(getClass().getResource("/images/clouds.png"))); 
        
        //Set properties for target and place at random location
        target = new JLabel();
        targetPlace = new Point((((int)(Math.random() * screenSize.getWidth() * .5) + (int)screenSize.getWidth() / 2)-target.getWidth())-200 ,(int)screenSize.getHeight() - 100);
        target.setSize(200, 25);
        target.setLocation(targetPlace);
        target.setIcon(new ImageIcon(getClass().getResource("/images/Target.png")));
        
        //Set properties for launch button, including calling the launch() method when it is clicked
        launch = new JButton();
        launch.setSize(359,141);
        launch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        launch.setLocation(10, 10);
        launch.setIcon(new ImageIcon(getClass().getResource("/images/LaunchButton.gif")));
        launch.setOpaque(false);
        launch.setContentAreaFilled(false);
        launch.setBorderPainted(false);
        launch.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                launch();
            }
            
        });
        
        //Set properties for next level button
        nextLevelButton = new JButton(new ImageIcon(getClass().getResource("/images/NextLevelButton.png")));
        nextLevelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextLevelButton.setOpaque(false);
        nextLevelButton.setContentAreaFilled(false);
        nextLevelButton.setBorderPainted(false);
        nextLevelButton.setSize(nextLevelButton.getIcon().getIconWidth(), nextLevelButton.getIcon().getIconHeight());
        nextLevelButton.setLocation((int)(screenSize.getWidth()*.75 + 150), (int)screenSize.getHeight()/8);
        nextLevelButton.setVisible(false);
        
        //Set properties for home button
        homeButton = new JButton(new ImageIcon(getClass().getResource("/images/home.png")));
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.setOpaque(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorderPainted(false);
        homeButton.setSize(homeButton.getIcon().getIconWidth(), homeButton.getIcon().getIconHeight());
        homeButton.setLocation((int)(screenSize.getWidth()*.75 - 150), (int)screenSize.getHeight()/8);
        homeButton.setVisible(false);

        //Set properties for grass
        grass = new JLabel(new ImageIcon(getClass().getResource("/images/grass.png")));
        grass.setSize(2002,102);
        grass.setLocation(0,(int)screenSize.getHeight()-100);
        
        //Set properties for score label
        score = new JLabel("Performance: ");
        score.setLocation((int)(screenSize.getWidth()*.75), (int)screenSize.getHeight()/32);
        score.setFont(new Font("Angry Birds", Font.PLAIN , 24));
        score.setSize(300,100);
        
        //Button to display the info for each level
        infoButton = new JButton();
        infoButton.setSize(48, 48);
        infoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        infoButton.setIcon(new ImageIcon(getClass().getResource("/images/qMark.png")));
        infoButton.setLocation((int)screenSize.getWidth() - infoButton.getWidth() - 5 , 0);
        infoButton.setOpaque(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setBorderPainted(false);
        infoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(!infoIsClicked)
                {
                    infoLabel.setVisible(true);
                    infoIsClicked = true;
                }    
                else
                {
                    infoLabel.setVisible(false);
                    infoIsClicked = false;
                }
            }
        });
        
        //Label to display the level info
        infoLabel = new JLabel();
        infoLabel.setSize(500, 300);
        infoLabel.setLocation(launch.getX() + launch.getWidth() + 50, launch.getY());
        infoLabel.setText("<html>If this message is still showing up you need to rewrite the info for this level.</html>");
        infoLabel.setVisible(false);
        


        infoIsClicked = false;
        completed = false;



        //Instantiate timer and add action listener, which is defined in the inner class below.
        timerListener = new Listener();
        timer = new Timer(10, timerListener);
        
        //Set properties for reset button, and make it so that it calls restart() method when clicked.
        resetButton = new JButton(new ImageIcon(getClass().getResource("/images/reset.png")));
        resetButton.setSize(128,128);
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.setVisible(false);
        resetButton.setOpaque(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setBorderPainted(false);
        resetButton.setLocation((int)(screenSize.getWidth()*.75), (int)screenSize.getHeight()/8);
        resetButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                restart();
            }
        });
        
        //Instantiate each set of points for graphs
        vPoints = new ArrayList<Point2D.Double>();
        accelPoints = new ArrayList<Point2D.Double>();
        kePoints = new ArrayList<Point2D.Double>();
        pePoints = new ArrayList<Point2D.Double>();
        positionPoints = new ArrayList<Point2D.Double>();
        
        //Instantiate array of frames to display graphs
        graphFrames = new JWindow[5];
        for (int i = 0; i < 5; i++)
        {
            //Set properties for current frame
            graphFrames[i] = new JWindow();
            graphFrames[i].setLocation(250,100);
            graphFrames[i].setVisible(false);
            graphFrames[i].setSize(300, 300);
        }
        
        //Instantiate checkboxes
        int xBox = launch.getX();    //Local level variables to set the x and y position of each checkBox
        int yBox = launch.getY() + 150;
        graphBoxes = new JCheckBox[5];
        graphBoxes[0] = new JCheckBox("Display velocity / time graph");
        graphBoxes[1] = new JCheckBox("Display acceleration / time graph");
        graphBoxes[2] = new JCheckBox("Display Kinetic energy / time graph");
        graphBoxes[3] = new JCheckBox("Display Gravitational potential / time graph");
        graphBoxes[4] = new JCheckBox("Display Height / time graph");
        for (int i = 0; i < 5; i++)
        {
            graphBoxes[i].setOpaque(false);
            graphBoxes[i].setSize(350, 30);
            graphBoxes[i].setLocation(xBox, yBox + (i * 20));
            graphBoxes[i].setVisible(false);
            graphBoxes[i].addItemListener(new CheckBoxListener());
        }
        
        //Instantiate array of graphs
        graphs = new Graph[5];
        
    }

    
    /**
     * INNER CLASS - ACTION LISTENER
     * Attached to timer
     * At each tick when the object has been launched and its flight is not over, it will accelerate and move the projectile.
     *         It will also instantiate a new point to be drawn onto each graph at each timer tick.
     * Otherwise it will set the score and make the reset button available.
     */
    class Listener implements ActionListener
        {
             public void actionPerformed(ActionEvent e)
            {
                 if (!isFlightOver())
                    {
                         //Call method to appropriately draw projectile's path
                        einstein.accelerate(.01);
                        einstein.move(.01);
                        
                        //Increment the accumulated time in order to set each graph's X axis
                        accumulatedTime += .01;                    
                        // DEBUGGING HELP System.out.println(new Point2D.Double( (10 * accumulatedTime),  grass.getY() - einstein.getY() - einstein.getHeight()));
                        //Add a new point to each graph based on the projectile's current state, and the time passed
                        vPoints.add(new Point2D.Double((10 * accumulatedTime), einstein.getVelocity().getYMagnitude()));
                        accelPoints.add(new Point2D.Double((10 * accumulatedTime),  einstein.getAccel().getYMagnitude()));
                        pePoints.add(new Point2D.Double((10 * accumulatedTime),  einstein.getPE(getHeight() - grass.getHeight())/50));
                        kePoints.add(new Point2D.Double((10 * accumulatedTime),  einstein.getKE()/50));
                        positionPoints.add(new Point2D.Double( (10 * accumulatedTime),  (grass.getY() - einstein.getY() - einstein.getHeight()) /(double)50 * .1));
                       } else {
                        //Instantiate each graph in graph array, adding to it a corresponding array of points
                        graphs[0] = new Graph(vPoints);
                        graphs[1] = new Graph(accelPoints);
                        graphs[2] = new Graph(kePoints);
                        graphs[3] = new Graph(pePoints);
                        graphs[4] = new Graph(positionPoints);
                        
                        //Add each graph to its corresponding frame
                        for (int i = 0; i < 5; i++)
                        {
                            graphFrames[i].add(graphs[i]);
                        }
                        //Set score, show reset and home button, and edit instructions label based on success or failure
                        setScore();
                        resetButton.setVisible(true);
                        accumulatedTime = 0;
                        homeButton.setVisible(true);
                        if(isSuccess()){
                            infoLabel.setText("<html>&emsp;Congratulations on beating this level!<br><br> &emsp;If you are having fun and would like to replay " +
                                    "this level, feel free to click the restart button at the top right. If you would like to look at graphs of physical" +
                                    " quantities for Einstein’s height, check the box to the left corresponding to the graph you would like to see. " +
                                    "When you are done with that graph, uncheck the box. <br><br> &emsp; When you are ready to move onto the next level, click on " +
                                    "the rightward arrow at the top right. Otherwise, if you would like to go back to the starting screen, click the" +
                                    " home button in the top right.</html>");

                        } else {
                            infoLabel.setText("<html>It looks like you were a little bit off! " +
                                    "Maybe you should re-calculate and try again. <br><br>&emsp;Press the reset" +
                                    " button in the top right corner when you are ready to give it another go." +
                                    " Otherwise, if you would like to go back to the starting screen," +
                                    " click the home button in the top right.");
                        }
                            
                        timer.stop();
                    }
            }
        }

    //JCheckBox ItemListener, determines which box is being checked 
    class CheckBoxListener implements ItemListener
    {
        public void itemStateChanged(ItemEvent e)
        {
            //Sets corresponding graph to be visible, and simulates an unclick for any other checkBoxes,
            //assuring there will be no more than one graph visible at a time.
            if (e.getSource() == graphBoxes[0])
            {
                if (graphBoxes[0].isSelected())
                {
                    graphFrames[0].setVisible(true);
                    for (JCheckBox box : graphBoxes)
                           if (box != graphBoxes[0] && box.isSelected())
                           {
                               box.doClick(0);
                           }
                }
                else
                    graphFrames[0].setVisible(false);
            }
            else if (e.getSource() == graphBoxes[1])
            {
                if (graphBoxes[1].isSelected())
                {
                    graphFrames[1].setVisible(true);
                    for (JCheckBox box : graphBoxes)
                           if (box != graphBoxes[1] && box.isSelected())
                           {
                               box.doClick(0);
                           }
                }
                else
                    graphFrames[1].setVisible(false);
            }
            else if (e.getSource() == graphBoxes[2])
            {
                if (graphBoxes[2].isSelected())
                {
                    graphFrames[2].setVisible(true);
                    for (JCheckBox box : graphBoxes)
                           if (box != graphBoxes[2] && box.isSelected())
                           {
                               box.doClick(0);
                           }
                }
                else
                    graphFrames[2].setVisible(false);
            }
            else if (e.getSource() == graphBoxes[3])
            {
                if (graphBoxes[3].isSelected())
                {
                    graphFrames[3].setVisible(true);
                    for (JCheckBox box : graphBoxes)
                           if (box != graphBoxes[3] && box.isSelected())
                           {
                               box.doClick(0);
                           }
                }
                else
                    graphFrames[3].setVisible(false);
            }
               else if (e.getSource() == graphBoxes[4])
               {
                   if (graphBoxes[4].isSelected())
                   {
                       graphFrames[4].setVisible(true);
                       for (JCheckBox box : graphBoxes)
                           if (box != graphBoxes[4] && box.isSelected())
                           {
                               box.doClick(0);
                           }
                   }
                else
                    graphFrames[4].setVisible(false);
               }
        }
    }
    
    //Updates score label based on distance from target
    public void setScore()
    {
        double precision = Math.abs((target.getX() + (target.getWidth() /2)) - (einstein.getX() + (einstein.getWidth()/2)));
        if (isFlightOver())
        {
            if (precision <= 10)
                score.setText("Performance: 100");
            else if (precision <= 20)
                score.setText("Performance: 90");
            else if (precision <= 30)
                score.setText("Performance: 80");
            else if (precision <= 40)
                score.setText("Performance: 70");
            else if (precision <= 50)
                score.setText("Performance: 60");
            else if (precision <= 60)
                score.setText("Performance: 50");
            else if (precision <= 70)
                score.setText("Performance: 40");
            else if (precision <= 80)
                score.setText("Performance: 30");
            else if (precision <= 90)
                score.setText("Performance: 20");
            else if (precision <= 100)
                score.setText("Performance: 10");
            else
                score.setText("Performance: 0");
        }
    }

    
    //Set all global variables to their default values
    //Should be overridden in each subclass to reset everything to its original location and property.
    public void restart()
    {
        timer.stop();
        launch.setVisible(true);
        
       //Remove all current graphs from screen
        for (JWindow w : graphFrames)
        {
            w.setVisible(false);
        }
        
        //Re-instantiate array of frames to display blank graphs, allows for graphs to look different across multiple trials
        graphFrames = new JWindow[5];
        for (int i = 0; i < 5; i++)
        {
            //Set properties for current frame
            graphFrames[i] = new JWindow();
            graphFrames[i].setLocation(250,100);
            graphFrames[i].setSize(300, 300);
        }
        
        //Removes and re-instantiates each graph, allows for difference between graphs along multiple trials,
            //as well as assuring the graph is entirely displayed on the axes
        for (int i = 0; i < 5; i++)
        {
            graphFrames[i].remove(graphs[i]);
            int xScale = graphs[i].getXScale();
            double yScale = graphs[i].getYScale();
            graphs[i] = new Graph(xScale,(int)yScale);
        }
        
        //Sets all checkboxes to unclicked and invisible, removes all points from the point lists, sets a new target location
        for (JCheckBox box : graphBoxes)
                if (box.isSelected())
                {
                    box.doClick(0);
                }
        for(JCheckBox box: graphBoxes)
            box.setVisible(false);
        vPoints.removeAll(vPoints);
        accelPoints.removeAll(accelPoints);
        kePoints.removeAll(kePoints);
        pePoints.removeAll(pePoints);
        positionPoints.removeAll(positionPoints);
        targetPlace = new Point((((int)(Math.random() * screenSize.getWidth() * .5) + (int)screenSize.getWidth() / 2)-target.getWidth()) ,(int)screenSize.getHeight() - 100);
        target.setLocation(targetPlace);
        
        //Resets various states
        score.setText("Performance: ");
        resetButton.setVisible(false);
        nextLevelButton.setVisible(false);
        homeButton.setVisible(false);
    }
    
    
    /**
     * @return true if einstein has landed and the flight is over
     * @return false if einstein has not been launched or is still in mid air
     */
    public boolean isFlightOver()
    {
        if (einstein.getY() >= screenSize.getHeight() - grass.getHeight() - einstein.getHeight()){
            return true;
        }
        else
            return false;
    }
    
    
    /**
     * @return true if the flight is over and einstein landed on the target
     * @retrun false otherwise
     */
    public boolean isSuccess()
    {
        if(isFlightOver() && (einstein.getX() + einstein.getWidth()/2) < target.getX() + target.getWidth() && (einstein.getX() + einstein.getWidth()/2) > target.getX()){
            nextLevelButton.setVisible(true);
            completed = true;
            for(JCheckBox box: graphBoxes)
                box.setVisible(true);
            return true;
        }
        else
            return false;
    }
    
    /**
     * @return the home button object
     */
    public JButton getHomeButton(){
        return homeButton;
    }
    
    
    /**
     * @return the next level button object
     */
    public JButton getNextLevelButton(){
        return nextLevelButton;
    }

    /**
     * @return true if a successful flight has occurred, false otherwise
     */
    public boolean hasBeenCompleted(){
        return completed;
    }

}