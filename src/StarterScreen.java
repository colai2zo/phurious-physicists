import javax.swing.*;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** This is a Class that extends JPanel which will display the logo, a general instructions button, a close button
 *         and a play button. This will appear immediately after the splash screen, and is equivalent to msot games' home screens
 * 
 * @author Dino Martinez, Joey Colaizzo, Lucas Carey
 */

@SuppressWarnings("serial")
public class StarterScreen extends JPanel {
    /** SCREEN LEVEL VARIABLES **/
private JButton infoButton; //Button displays rules of game
    private JButton exitButton; //Exit the screen.
    private JButton startButton; //Goes to level screen.
    private JLabel info; //rules of game
    private JLabel cloudBG; //cloud background picture.
    private JLabel logo; 
    private JLabel authorInfo;
    private boolean infoIsClicked = false; //helper boolean to allow us to see if info button is currently displayed or not.
    private Dimension screenSize; //size of screen
    public StarterScreen()
    {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //use java default toolkit to determine size of screen
        setSize(screenSize); //set panel size to screen size.
        setLayout(null); //remove default layout manager
        
        //Call helper method that specifies state of components and adds them to the panel
        createContents();
        
        //Listen to exit button. Exit program if clicked.
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        
        //Listen to info button. Display instructions when clicked and instructions aren’t already displayed. Remove instructions when they are already displayed.
        infoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(!infoIsClicked)
                {
                    info.setVisible(true);
                    infoIsClicked = true;
                }    
                else
                {
                    info.setVisible(false);
                    infoIsClicked = false;
                }
            }
        });
        setVisible(true);
    }


    /** CREATE CONTENTS METHOD specifies component state and adds components to panel **/
    private void createContents()
    {
        //Set properties for JLabels

        cloudBG = new JLabel();
        cloudBG.setSize(screenSize);
        cloudBG.setLocation(-6,0);
        cloudBG.setIcon(new ImageIcon(getClass().getResource("/images/clouds.png")));
        
        logo = new JLabel();
        logo.setSize(781, 383);
        logo.setLocation((getWidth()/2)-340, (getHeight()/2)-383);
        logo.setIcon(new ImageIcon(getClass().getResource("/images/PPLogo.png")));
        
        authorInfo = new JLabel("Phurious Physicists, Version 1.0 (Joey Colaizzo, Dino Martinez, Lucas Carey) June 2016");
        authorInfo.setSize(600,50);
        authorInfo.setLocation((int)(getWidth()*.55), (int)(getHeight()*.9));
        
        //Set properties for buttons.

        infoButton = new JButton();
        infoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        infoButton.setSize(48, 48);
        infoButton.setIcon(new ImageIcon(getClass().getResource("/images/qMark.png")));
        infoButton.setLocation(0, 0);
        infoButton.setOpaque(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setBorderPainted(false);
        
        exitButton = new JButton();
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setSize(48,48);
        exitButton.setIcon(new ImageIcon(getClass().getResource("/images/exitButton.png")));
        exitButton.setLocation(getWidth()-53, 0);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        
        startButton = new JButton(new ImageIcon(getClass().getResource("/images/playbutton.png")));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setSize(185, 130);
        startButton.setLocation((getWidth()/2)-92, (getHeight()/2)+ 65);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        
        
        //Set properties for information label.
        //For large amounts of text, we use HTML to format the label
        //<br> means break to new line, &emsp; means tab
        info = new JLabel();
        info.setSize(350,800);
        info.setLocation(20, 60);
        info.setText("<html>&emsp;Welcome to Phurious Physicists™! In this game, your goal will be to calculate specified " +
                "variables in order to launch Einstein’s head onto a target. Do not fret, for all levels are perfectly" +
                " solvable! In this game, you can assume that acceleration due to gravity is 10.0 meters per second " +
                "per second, that there is no air resistance, that there is no friction during launch, and that " +
                "Einstein is immediately stopped the instant he contacts the ground in a perfectly inelastic" +
                " collision. The levels increase in difficulty as you progress, so expect level one to be easier than" +
                " level three. A forewarning: you can not consistently succeed in this game by utilizing the ever" +
                " popular ‘guess and check’ strategy, so expect to calculate each answer before pressing ‘Launch.’<br><br> &emsp;" +
                " In level one, you are tasked with determining a height and velocity for a horizontally launched projectile," +
                " while being given the object's mass, and distance to the target, just like the most common of kinematics " +
                "problems.<br><br> &emsp;Level two asks you to determine the velocity of a projectile launched at an angle, while being" +
                " given the object’s mass, distance to the target, and angle from the horizontal. You may assume height is " +
                "zero.<br><br> &emsp;Level three is a jump in difficulty, and requires an understanding of energy transfer to solve. " +
                "You are asked to determine how far to compress the spring which is launching the projectile, in order to " +
                "make the projectile land on the target. You are given the spring constant, object mass, and distance to " +
                "the target, and you may assume initial height is zero.<br><br> &emsp;To begin the game, please press the play button in " +
                "the middle of the screen, then select a level. To play each level, you may adjust the sliders to set your values" +
                " for the variable in question, and then press the word “Launch” in the top left of the screen. Once the object lands," +
                " you will be prompted with five check boxes and three buttons. Each checkbox will display a corresponding graph." +
                " The home button will return you to this screen, the restart button will restart the current level," +
                " randomly placing the target, and randomly setting the angle in level two and three. The next level button will only " +
                "display once you have successfully launched the object onto the target, and will bring you directly to the level after" +
                " the one you succeeded on.</html>");
        info.setVisible(false);
        
        /** ADD COMPONENTS TO PANEL IN ORDER (background last)**/
        add(startButton);
        add(infoButton);
        add(exitButton);
        add(info);
        add(logo);
        add(authorInfo);
        add(cloudBG);
    }
    
    
    /**
     * @return the start button object.
     */
    public JButton getStartButton()
    {
        return startButton;
    }
    
}