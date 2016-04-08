package geneticbots;

import java.awt.Color;

public class Kid extends Physics
{
    //0-angle
    //1-rate of consumption
    //2-fuel == 1000
    
    private static final int totalFuel = 100;
    private double[][] thrusters = new double[3][3];
    private double[] fuel = new double[3];
    private double fitnes;
    
    public Kid(int x, int y,double[][] thrusters)
    {
        super(x,y,Color.BLUE,7);
        for(int i=0;i<fuel.length;i++)
        {
            fuel[i]=thrusters[2][i]*totalFuel;
        }
        this.thrusters=thrusters;
    }
    
    public void move(Barrier[] b)
    {
        double xAngle,yAngle;
        for(int i=0;i<fuel.length;i++)
        {
            if(fuel[i]>0)
            {
                xAngle = speed*Math.cos(direction)+thrusters[1][i]*Math.cos(thrusters[0][i]);
                yAngle = speed*Math.sin(direction)+thrusters[1][i]*Math.sin(thrusters[0][i]);
                direction=Math.atan(yAngle/xAngle);
                if(xAngle<0)
                    direction+=Math.PI;
                speed+=Math.sqrt(Math.pow(xAngle, 2)+Math.pow(yAngle, 2)) / 100;
                fuel[i]-=thrusters[1][i];
            }
        }
        double tx = x + speed * Math.cos(direction);
        double ty = y + speed * Math.sin(direction);
        for(int i=0;i<b.length;i++)
        {
            //System.out.println(b[i]);
            if(tx + width > b[i].x && tx < b[i].x + b[i].width)
            {
                if(ty + length > b[i].y && ty < b[i].y + b[i].length)
                {
                   // x-=speed*Math.cos(direction);
                    //direction = -direction;
                    if (speed*Math.cos(direction) < 0)
                    {
                        direction = Math.PI -Math.PI -direction;
                    }
                    else
                    {
                        direction = -1 * Math.PI - direction;
                    }
                    if (Math.sin(direction) < 0)
                    {
                        direction = -.5 * Math.PI - direction;
                       
                    }
                    else
                    {
                        direction = .5 * Math.PI - direction;
                        
                    }
                }
            }
        }
        x += speed * Math.cos(direction);
        y += speed * Math.sin(direction);
        //y+=speed*Math.sin(direction);
//        for(int i=0;i<b.length;i++)
//        {
//            if(x+width>b[i].x&&x<b[i].x+b[i].width)
//            {
//                if(y+length>b[i].y&&y<b[i].y+b[i].length)
//                {
//                    y-=speed*Math.sin(direction);
//                    if (Math.sin(direction) < 0)
//                    {
//                        direction = -.5 * Math.PI - direction;
//                       
//                    }
//                    else
//                    {
//                        direction = .5 * Math.PI - direction;
//                        
//                    }
//                    //x+=speed*Math.cos(direction);
//                    y+=speed*Math.sin(direction);
//                }
//            }
//        }
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