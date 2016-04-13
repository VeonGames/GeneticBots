package geneticbots;

import java.awt.Color;
import java.util.List;

public abstract class Physics extends Actor
{
    protected double direction=0;
    protected double speed=0;
    public Physics(double x, double y, Color color, int shape)
    {
        super(x,y,color,shape);
    }
    
    abstract void move(List<Barrier> b);
    
    public Color getColor()
    {
        return color;
    }
}
