package geneticbots;

import java.awt.Color;
import java.awt.Rectangle;

public class Actor extends Rectangle
{
    protected Color color = Color.BLUE;
    private int shape = 0;
    protected int radius;
    protected double dx,dy;
    public Actor(double x, double y, Color color, int radius)
    {
        super((int) x, (int) y, radius*2, radius*2);
        this.dx = x;
        this.dy = y;
        //System.out.println("(" + (int)x + ", " + (int)y +")");
        
        this.color=color;
        this.shape=1;
        this.radius=radius;
    }
    
    public Actor(double x, double y, Color color, int width, int length)
    {
        super((int) x, (int) y,width,length);
        this.dx = x;
        this.dy = y;
        this.color=color;
        this.shape=0;
        radius=-1;
    }
    
    public int getRadius()
    {
        return radius;
    }
    
    public double getX()
    {
        return this.x;
    }
    
    public double getY()
    {
        return this.y;
    }
    
    public void setY(int y)
    {
        this.dy=y;
    }
    
    public void setX(int x)
    {
        this.dx=x;
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
    
    public void reXY()
    {
        super.x = (int) dx;
        super.y = (int) dy;
    }

    public boolean intersects(Actor r)
    {
        if ((dx + width > r.dx && dx < r.dx + r.width) && (dy + height > r.dy && dy < r.dy + r.height))
        {
            return true;
        }
        return false;
    }
    
    
    
    public String toString()
    {
        return "(" + dx + ", " + dy +")\t " + color;
    }
}
