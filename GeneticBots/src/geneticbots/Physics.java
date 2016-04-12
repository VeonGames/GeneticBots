package geneticbots;

import java.awt.Color;

public abstract class Physics extends Actor
{
    protected double direction=0;
    protected double speed=0;
    public Physics(int x, int y, Color color, int shape)
    {
        super(x,y,color,shape);
    }
    
    abstract void move(Barrier[] b);
    
    public Color getColor()
    {
        return color;
    }
}
