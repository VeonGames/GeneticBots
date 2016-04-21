package geneticbots;

import java.awt.Color;
import java.util.List;

public class Kid extends Physics
{
    //0-angle
    //1-rate of consumption
    //2-fuel == 1000

    private static final int totalFuel = 10000;
    private double[][] thrusters = new double[3][3];
    private double[] fuel = new double[3];
    private double fitnes;
    
    public Kid(double x, double y, double[][] thrusters, Color c)
    {
        super(x, y, c, 7);
        for (int i = 0; i < fuel.length; i++)
        {
            fuel[i] = thrusters[2][i] * totalFuel;
        }
        this.thrusters = thrusters;
    }

    public void move(List<Barrier> b)
    {
        double xAngle, yAngle;
        boolean bounce;
        for (int i = 0; i < fuel.length; i++)
        {
            if (fuel[i] > 0)
            {
                xAngle = speed * Math.cos(direction) + thrusters[1][i] * Math.cos(thrusters[0][i]);
                yAngle = speed * Math.sin(direction) + thrusters[1][i] * Math.sin(thrusters[0][i]);
                direction = Math.atan(yAngle / xAngle);
                if (xAngle < 0)
                {
                    direction += Math.PI;
                }
                speed += Math.sqrt(Math.pow(xAngle, 2) + Math.pow(yAngle, 2)) / 200;
                fuel[i] -= thrusters[1][i];
            }
        }
        dx = dx + speed * Math.cos(direction);
        dy = dy + speed * Math.sin(direction);
        
        for (int i = 0; i < b.size(); i++)
        {
            bounce = false;
            //System.out.println(b[i]);
            while (this.intersects(b.get(i))) //((dx + radius > b.get(i).x && dx - radius < b.get(i).x + b.get(i).width) && (y + radius > b.get(i).y && y - radius < b.get(i).y + b.get(i).height))
            {
                dx -= Math.cos(direction);
                dy -= Math.sin(direction);
                bounce=true;
            }
            if(bounce)
            {
                if (!(dx + width > b.get(i).dx && dx < b.get(i).dx + b.get(i).width))//hitting the side
                {
                    direction = Math.PI - direction;
                    
                } else if (!(dy + height > b.get(i).dy && dy < b.get(i).dy + b.get(i).height))//hitting the top/botton
                {
                    direction = - direction;
                }
                else
                {
                    direction = Math.PI + direction;
                }
                //speed=0;
            }
        }
    }

    public void setFit(double fit)
    {
        fitnes = fit;
    }

    public double getFit()
    {
        return fitnes;
    }

    public double[][] getGenes()
    {
        return thrusters;
    }
    
    
}
