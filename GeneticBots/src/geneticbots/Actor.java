package geneticbots;

import java.awt.Color;
import java.awt.Rectangle;

public class Actor extends Rectangle
{
    protected Color color = Color.BLUE;
    private int shape = 0;
    protected int radius;
    protected double x,y;
    public Actor(double x, double y, Color color, int radius)
    {
        super((int) x, (int) y, radius*2, radius*2);
        this.color=color;
        this.shape=1;
        this.radius=radius;
    }
    
    public Actor(double x, double y, Color color, int width, int length)
    {
        super((int) x, (int) y,width,length);
        this.color=color;
        this.shape=0;
        radius=-1;
    }
    
    public int getRadius()
    {
        return radius;
    }
    
    
    public void setY(int y)
    {
        this.y=y;
    }
    
    public void setX(int x)
    {
        this.x=x;
    }
    
    public int getShape()
    {
        return shape;
    }
    
    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public String toString()
    {
        return "(" + x + ", " + y +")\t " + color;
    }
}
