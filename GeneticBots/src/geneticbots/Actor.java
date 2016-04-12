package geneticbots;

import java.awt.Color;

public class Actor
{
    protected int x;
    protected int y;
    private Color color = Color.BLUE;
    private int shape;
    protected int length;
    protected int width;
    protected int radius;
    
    public Actor(int x, int y, Color color, int radius)
    {
        this.x=x;
        this.y=y;
        this.color=color;
        this.shape=1;
        this.radius=radius;
        length=radius*2;
        width=radius*2;
    }
    
    public Actor(int x, int y, Color color, int width, int length)
    {
        this.x=x;
        this.y=y;
        this.color=color;
        this.shape=0;
        this.width=width;
        this.length=length;
        radius=-1;
    }
    
    public int getRadius()
    {
        return radius;
    }
    
    public int getLength()
    {
        return length;
    }
    
    public int getWidth()
    {
        return width;
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
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
}
