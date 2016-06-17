/**
 * This is a class representing a JPanel that will hold the components for the level selection screen.
 * This screen launches when the user clicks play, and will allow the user to select any level they have unlocked to play.
 * @author Joey Colaizzo, Dino Martinez, Lucas Carey
 * @version 1.0
 */

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LevelScreen extends JPanel {
    private JButton[] lvlButtons;    //Buttons for each level
    private JButton backButton;    //Button to return to home screen
    private JButton[] lvlThumbs;    //Thumbnails for each level
    private JLabel background;
    private JLabel logo;
    
    /**
     * CONSTRUCTOR
     * @param testLevels an array of levels from the view class used to see which has been completed.
     * If a level has not been unlocked (meaning the level before it is complete), it will not be accessible.
     */
    public LevelScreen(Level[] testLevels){
        
        //Set Panel Related Properties
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setLayout(null);
        
        //Fill array of level buttons and set properties.
        lvlButtons = new JButton[3];
        for(int i = 0; i < lvlButtons.length; i++){
            lvlButtons[i] = new JButton(new ImageIcon(getClass().getResource("/images/level" + (i+1) + ".png")));
            lvlButtons[i].setSize(250,100);
            lvlButtons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            lvlButtons[i].setLocation((int)((i) * screenSize.getWidth())/3 + 100,(int) (screenSize.getHeight() * .75));
            lvlButtons[i].setOpaque(false);
            lvlButtons[i].setContentAreaFilled(false);
            lvlButtons[i].setBorderPainted(false);
            add(lvlButtons[i]);
            
            //Set all buttons that lead to a locked level to gray scale and disables.
            if(i>0){
                if(!testLevels[i-1].hasBeenCompleted()){
                    lvlButtons[i].setEnabled(false);
                    setGrayScale(lvlButtons[i].getIcon());
                }
            }
        }
        
        //Fill array of Level thumb nail buttons and set properties.
        lvlThumbs = new JButton[3];
        for(int i = 0; i < lvlThumbs.length; i++){
            lvlThumbs[i] = new JButton(new ImageIcon(getClass().getResource("/images/level" + (i+1) + "Thumb.png")));
            lvlThumbs[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            lvlThumbs[i].setSize(lvlThumbs[i].getIcon().getIconWidth(), lvlThumbs[i].getIcon().getIconHeight());
            lvlThumbs[i].setLocation(lvlButtons[i].getX() - (lvlButtons[i].getWidth()/4), lvlButtons[i].getY()-lvlThumbs[i].getHeight()-50);
            add(lvlThumbs[i]);
            
            //Set all buttons that lead to a locked level to gray scale and disables.
            if(i>0){
                if(!testLevels[i-1].hasBeenCompleted()){
                    lvlThumbs[i].setEnabled(false);
                    setGrayScale(lvlThumbs[i].getIcon());
                }
            }
        }
        
        //Set properties for background Image
        background = new JLabel(new ImageIcon(getClass().getResource("/images/redbackground.png")));
        background.setLocation(-6,0);
        background.setSize(screenSize);
        
        //Set Properties for back button
        backButton = new JButton(new ImageIcon(getClass().getResource("/images/BackButton.png")));
        backButton.setSize(backButton.getIcon().getIconWidth(), backButton.getIcon().getIconHeight());
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setLocation(0,0);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        
        //Set properties for title logo.
        logo = new JLabel(new ImageIcon(getClass().getResource("/images/levelselector.png")));
        logo.setSize(logo.getIcon().getIconWidth(), logo.getIcon().getIconHeight());
        logo.setLocation((int)(screenSize.getWidth()/2) - (logo.getWidth()/2), (int)screenSize.getHeight()/18);
        
        /* Add contents to panel  */
        add(backButton);
        add(logo);
        add(background);
        
    }
    
    
    /**
     * A helper method to update the state of the level buttons. If a level has become unlocked,
     * it will become non gray scale and be available for use.
     * @param an updated level array to check for level completion.
     */
    public void updateButtons(Level[] testLevels){
        for(int i = 1; i < lvlButtons.length; i++){
            if(((Level) testLevels[i-1]).hasBeenCompleted()){
                lvlButtons[i].setEnabled(true);
                lvlButtons[i].setIcon(new ImageIcon(getClass().getResource("/images/level" + (i+1) + ".png")));
                lvlThumbs[i].setEnabled(true);
                lvlThumbs[i].setIcon(new ImageIcon(getClass().getResource("/images/level" + (i+1) + "Thumb.png")));
            }
        }
    }
    
    /**
     * @return the array of buttons used to select levels.
     */
    public JButton[] getLevelButtons(){
        return lvlButtons;
    }
    
    /**
     * @return the array of buttons used to select levels that contain the thumbnail of that level.
     */
    public JButton[] getLevelThumbs(){
        return lvlThumbs;
    }
    
    /**
     * @return the back button object
     */
    public JButton getBackButton(){
        return backButton;
    }
    /**
     * A helper method that will change the icon of a JComponent and make it gray scale.
     */
    public void setGrayScale(Icon img){
        Image img2 = ((ImageIcon)img).getImage();
        img = new ImageIcon(GrayFilter.createDisabledImage(img2));
    }
}