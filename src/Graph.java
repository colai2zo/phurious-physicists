/**
 * Graph class which displays a graph based on an array of points passed in the constructor\
 * @author Dino Martinez, Joey Colaizzo, Lucas Carey
 */
import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;

@SuppressWarnings("serial")
/** PRECONDITION FOR USE OF GRAPH CLASS: POINTS ARE IN TIME ORDER ON INSTANTIATION */
public class Graph extends JComponent {
    private ArrayList<Point2D.Double> points; //List of points, formatted as (x,y)
    private  final int BORDER_GAP = 10; //Number of pixels to be between the edge of the frame and the axes
    private double xLeft; //These are necessary to fix a loss of precision due to integer truncation
    private double yLeft;
    private int yScale; //Scaling factor of x and y values, to assure the grpah fits on the frame
    private int xScale;
    
    public Graph(ArrayList<Point2D.Double> values)
    {
        //Instantiate points to equal array list passed in
        points = new ArrayList<Point2D.Double>();
        for (int i = 0; i < values.size(); i++)
        {
            points.add(values.get(i));
        }
        xLeft = 0;
        yLeft = 0;
        getMaxY();
    }
    
    public Graph(int theXScale, int theYScale){
        points = new ArrayList<Point2D.Double>();
        xScale = theXScale;
        yScale = theYScale;
        repaint();
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g; //Convert graphics
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Set antialiasing to on, which smooths rough edges
        drawAxes(g2); //Draws a blank set of axes
        //System.out.println(xScale + " : " + yScale);
        
        //Set X and Y scales
        xScale = 10 * ((getWidth() - BORDER_GAP) - BORDER_GAP)/points.size();
        yScale = (int)(-((getHeight() - BORDER_GAP) - BORDER_GAP)/getMaxY());
        
        //Draw lines between each set of points
        for (int i = 0; i < points.size() - 1; i++)
        {
            int x0 = (int)(BORDER_GAP + (xScale * points.get(i).getX()));
            xLeft = (points.get(i).getX() - (int)points.get(i).getX())/xScale;
            int x1 = (int)(BORDER_GAP + (xScale * (points.get(i + 1).getX() + xLeft)));
            int y0 = (int)(getHeight() - BORDER_GAP + (yScale * points.get(i).getY()));
            yLeft = (points.get(i).getY() - (int)points.get(i).getY())/yScale;
            int y1 = (int)(getHeight() - BORDER_GAP + (yScale * (points.get(i + 1).getY() + yLeft)));
            g2.drawLine(x0,y0,x1,y1);
        }
    }
    
    
    /**
     * Helper method that draws axes and hatch marks.
     */
    public void drawAxes(Graphics2D g2){
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Create x and y axes 
        g2.setPaint(Color.BLACK);
        g2.setStroke(new BasicStroke(3.0f));
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
        g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);
        
        //Create hatch marks for y axis
        for (int i = 0; i < 10; i++)
        {
            int x0 = BORDER_GAP;
            int x1 = BORDER_GAP + 10;
            int y0 = getHeight() - BORDER_GAP -((i + 1) * 28);
            int y1 = y0;
                
            g2.drawLine(x0,y0,x1,y1);
        }
        
        //Create hatch marks for x axis
        for (int i = 0; i < 10; i++)
        {
            int x0 = getWidth() + BORDER_GAP - ((i + 1) * 28);
            int x1 = x0;
            int y0 = getHeight() - BORDER_GAP;
            int y1 = getHeight() - BORDER_GAP - 10;
            g2.drawLine(x0,y0,x1,y1);
        }
        
        //Create square border for Window
        g2.drawLine(0,0,getWidth(),0);
        g2.drawLine(0,0,0,getHeight());
        g2.drawLine(0,getHeight() - 1,getWidth(),getHeight() - 1);
        g2.drawLine(getWidth() - 1,0,getWidth() - 1,getHeight());
    }
    
    public double getMaxY()
    {
        double max = points.get(0).getY();
        
        for (Point2D.Double p : points)
        {
            if (p.getY() > max)
                max = p.getY();
        }
        return max;
    }
    
    public int getXScale()
    {
        return xScale;
    }
    
    public int getYScale()
    {
        return yScale;
    }
}