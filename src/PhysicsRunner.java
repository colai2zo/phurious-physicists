/**
 * VIEW CLASS
 * This class will pull everything together from the other classes.
 * It is a JFrame that will switch between holding different JPanels.
 * It will be responsible for adding buttons that switch between screens and listening to them.
 * @author Joey Colaizzo, Dino Martinez, Lucas Carey
 * @version 1.0
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PhysicsRunner extends JFrame {
    
    /** INSTANCE VARIABLES **/
    private Level[] levels;
    private JPanel currentScreen;
    private StarterScreen startScreen;
    private LevelScreen levelScreen;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    /**
     * DEFAULT CONSTRUCTOR
     * Adds start screen to frame and makes that the current screen.
     * Instantiate all other variables.
     * Set all properties for frame.
     */
    public PhysicsRunner(){
        //Change Frame related properties
        setSize(screenSize);
        setResizable(false);
        setTitle("Phurious Physicists");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        //Instantiate JPanel instance variables.
        levels = new Level[3];
        levels[0] = new LevelOne();
        levels[1] = new LevelTwo();
        levels[2] = new LevelThree();

        startScreen = new StarterScreen();
        levelScreen = new LevelScreen(levels);
        
        setButtonProperties();

        currentScreen = startScreen;
        
        /**ADD PANEL TO FRAME**/
        setContentPane(currentScreen);

        setVisible(true);
        
    }

    
    /**
     * A helper method that will add the appropriate listener for EVERY Button that controls switching screens.
     * Must be done in the view class because view class is in charge of screen switching.
     */
    private void setButtonProperties(){
        
        //Start button on start screen goes to level selection screen.
        startScreen.getStartButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switchScreen(levelScreen);
            }
        });
        
        //Next level button for first 2 levels will take them to the next level.
        for(Level l: levels){
            l.getNextLevelButton().addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(currentScreen.equals(levels[0])){
                        switchScreen(levels[1]);
                    }
                    else if(currentScreen.equals(levels[1])){
                        switchScreen(levels[2]);
                    }
                }
            });
            
            //Home button takes them back to start screen
            l.getHomeButton().addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    ((Level)currentScreen).restart();
                    switchScreen(startScreen);
                }
            });
        }
        
        
        /** Buttons on level selection screen will change screen to the specified level. **/
        JButton[] lvlButtons = levelScreen.getLevelButtons();
        JButton[] lvlThumbs = levelScreen.getLevelThumbs();
        
        lvlButtons[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switchScreen(levels[0]);
                }
            });
        
    
        lvlButtons[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switchScreen(levels[1]);
                }
            });
        
    
        lvlButtons[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switchScreen(levels[2]);
                }
            });
        
        lvlThumbs[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switchScreen(levels[0]);
                }
            });
        
    
        lvlThumbs[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switchScreen(levels[1]);
                }
            });
        
    
        lvlThumbs[2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switchScreen(levels[2]);
                }
            });
        
        //Back button on level selection screen takes us back to the home screen.
        levelScreen.getBackButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                switchScreen(startScreen);
            }
        });
    }    
    
    /**
     * A helper method that will help us to switch between the different screens.
     * @param The Panel that is being switched to.
     * POST CONDITION: The previous level is reset, and the screen switches to the specified screen.
     */
    private void switchScreen(JPanel pane){
        //Checks to see if the current screen is a level. If so, reset that level
        for(int i = 0; i < levels.length; i++)
            if(levels[i].equals(currentScreen))
                ((Level)currentScreen).restart();
        
        levelScreen.updateButtons(levels);
        currentScreen = pane;
        setContentPane(currentScreen);
        setVisible(true);        
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args){
        new PhysicsRunner();
    }
}