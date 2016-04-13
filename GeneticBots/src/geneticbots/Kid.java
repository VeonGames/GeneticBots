package geneticbots;

import java.awt.Color;
import java.util.List;

public class Kid extends Physics
{
    //0-angle
    //1-rate of consumption
    //2-fuel == 1000

    private static final int totalFuel = 100;
    private double[][] thrusters = new double[3][3];
    private double[] fuel = new double[3];
    private double fitnes;
    
    public Kid(int x, int y, double[][] thrusters, Color c)
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
                speed += Math.sqrt(Math.pow(xAngle, 2) + Math.pow(yAngle, 2)) / 100;
                fuel[i] -= thrusters[1][i];
            }
        }
        double tx = x + speed * Math.cos(direction);
        double ty = y + speed * Math.sin(direction);
        for (int i = 0; i < b.size(); i++)
        {
            //System.out.println(b[i]);
            while (intersects(b.get(i)))              //(tx + radius > b[i].x && tx - radius < b[i].x + b[i].width) && (ty + radius > b[i].y && ty - radius < b[i].y + b[i].length))
            {
                tx -= Math.cos(direction);
                ty -= Math.sin(direction);
                if (!(tx + radius > b.get(i).x && tx - radius < b.get(i).x + b.get(i).width))
                {
                    if (Math.cos(direction) < 0)
                    {
                        direction = direction - Math.PI;
                    } else
                    {
                        direction = Math.PI - direction;
                    }
                    break;
                } else if (!(ty + radius > b.get(i).y && ty - radius < b.get(i).y + b.get(i).height))
                {
                    if (Math.sin(direction) < 0)
                    {
                        direction = - direction;
                    } else
                    {
                        direction = - direction;
                    }
                    break;
                } 
            }
        }
        
       /** while (GeneticBots.screen.contains(this))             
        {
            //System.out.println("(" +x + ", " + y + ")\t" + "(" +tx + ", " + ty + ")");
            tx -= Math.cos(direction);
            ty -= Math.sin(direction);
            if ((tx + radius < GeneticBots.screen.width && tx - radius > 0))
            {
                if (Math.cos(direction) > 0)
                {
                    direction = direction - Math.PI; 
                } else
                {
                    direction = Math.PI - direction;
                }
                break;
            } else if ((ty + radius < GeneticBots.screen.height && ty - radius > 0))
            {
                if (Math.sin(direction) < 0)
                {
                    direction = -direction;
                } else
                {
                    direction = -direction;
                }
                break;
            }
        }   
        x =  (int) (tx + speed * Math.cos(direction));
        y =  (int) (ty + speed * Math.sin(direction));
        */
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
