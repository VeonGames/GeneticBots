package geneticbots;


import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class GeneticBots
{
    private static int fps;
    private static final int frames = 200;
    private static int population;
    private static int barriers;
    private static Target target;
    private static int length=600;
    private static int width=900;
    private static double mutation = .001;
    private static Barrier[] barrierArray;
    private static Physics[] physics;
    private static Kid[] kids;
    private static Actor[] actors;
    private static int generation;
    private static int sx = 100, sy = 550; //start x and y
    static boolean start = false;
    public static void main(String[] args)
    {
        Properties.main(args);
        while (!start)
        {
            System.out.print("");
        }
        JFrame holder = new JFrame("GeneticBots");
        Drawer theDrawer = new Drawer();
        holder.setVisible(false);
        JSlider jslide = new JSlider();
        jslide.setBounds(5, 60, 150, 20);
        jslide.setValue(fps);
        jslide.setMaximum(1000);
        jslide.setMinimum(1);
        jslide.setVisible(true);
        holder.add(jslide);
        holder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        holder.setBounds(0,0,width,length);
        final JPanel homePanel = new JPanel();
        holder.getContentPane().add(theDrawer);
        barrierArray = new Barrier[barriers];
        physics = new Physics[population];
        kids = new Kid[population];
        actors = new Actor[population + barriers + 1];
        
        target = new Target((width-60), (length / 2));
        actors[actors.length - 1] = target;
        for (int k = 0; k < barriers; k++)
        {
            actors[k] = new Barrier((int)(Math.random()*(width-15)),(int)(Math.random()*length)-20,(int)(Math.random()*90)+50,(int)(Math.random()*60)+30);
            barrierArray[k] = (Barrier) actors[k];
            if (actors[k].contains(new Rectangle(sx-50,sy-50, sx+50, sy+50)) || actors[k].contains(new Rectangle(0, 0, 70, 50)))
            {
                k--;
            }
        }
        
        double[][] temp;
        for (int k = 0; k < population; k++)
        {
            temp = new double[3][3];

            for (int i = 0; i < 3; i++)
            {
                temp[0][i] = (Math.random() * 2 * Math.PI);
                System.out.println();
            }
            for (int i = 0; i < 3; i++)
            {
                temp[1][i] =  Math.random() * 1;
            }
            double sum=0;
            for(int i=0;i<3;i++)
            {
                temp[2][i] = Math.random();
                sum+=temp[2][i];
            }
            for(int i=0;i<3;i++)
            {
                temp[2][i]/=sum;
            }
            
            kids[k] = new Kid(sx, sy, temp, Color.BLUE);
            actors[k+barriers]=kids[k];
            physics = kids;
        }
//        for (int k = 0; k < frames; k++)
//        {
//            move();
//        }
//        kids = nextGen();
//        for (int i = barriers; i < population + barriers; i++)
//        {
//            actors[i] = kids[i - barriers];
//        }
//        physics = kids;
//        generation++;
        holder.setVisible(true);
        holder.repaint();
        while(true)
        {
            for (int k = 0; k < frames; k++)
            {
                move();
                holder.repaint();
                //System.out.println(k);
                try
                {
                    Thread.sleep(1000/fps);
                }
                catch (InterruptedException ex)
                {
                    System.out.println(ex);
                }
                fps=jslide.getValue();
            }
            kids = nextGen();
            for(int i=barriers;i<population+barriers;i++)
            {
                actors[i]=kids[i-barriers];
            }
            physics = kids;
            generation++;
        }
        
    }
    
    public static int getGenerations()
    {
        return generation;
    }
    
    public static Actor[] getActors()
    {
        return actors;
    }
    
    public static Kid[] nextGen()
    {
        Kid[] newKids = new Kid[kids.length];
        int total=0;
        for(int i=0;i<kids.length;i++)
        {
            kids[i].setFit(Math.sqrt(Math.pow(kids[i].getX()-target.getX(), 2)+Math.pow(kids[i].getY()-target.getY(),2)));
            total += kids[i].getFit();
        }
        
        double sum = 0;
        
        for (int k = 0; k < kids.length; k++)
        {
            kids[k].setFit(total / kids[k].getFit());
            sum += kids[k].getFit();
        }
        
        double sum2=0;
        for (int k = 0; k < kids.length; k++)
        {
            sum2+=kids[k].getFit() / sum;
            kids[k].setFit(sum2);
        }
        
        double rand;
        Kid[] temp = new Kid[2];
        for(int i=0;i<population;i++)
        {
            for(int j=0;j<2;j++)
            {
                rand=Math.random();
                for(int k=0;k<population-1;k++)
                {
                    if(kids[k].getFit()>rand)
                    {
                        temp[j]=kids[k];
                        break;
                    }
                    temp[j]=kids[kids.length-1];
                }
            }
            newKids[i] = makeBaby(temp[0], temp[1]);
        }
        return newKids;
    }
    
    public static Kid makeBaby(Kid parent1, Kid parent2)
    {
        double[][] p1 = parent1.getGenes();
        double[][] p2 = parent2.getGenes();
        double[][] kid = new double[p1.length][p1[0].length];
       
        for (int j = 0; j < 3; j++)
        {
            kid[0][j] = (p1[0][j] + p2[0][j]) / 2;
            if (Math.random() < mutation)
            {
                kid[0][j] = Math.random() * 360;
            }
        }
        
        for (int j = 0; j < 3; j++)
        {
            kid[1][j] = (p1[1][j] + p2[1][j]) / 2;
            if (Math.random() < mutation)
            {
                kid[1][j] = Math.random();
            }
        }
        double sum = 0;
        double te;
        double[] ti = {-1, -1, -1};
        for (int j = 0; j < 3; j++)
        {
            if (Math.random() < mutation)
            {
                te = kid[1][j] = Math.random();
                ti[j] = te;
            }
            else
            {
                te = (p1[2][j] + p2[2][j]) / 2;
            }
            sum += te;
        }
        for (int j = 0; j < 3; j++)
        {
            if (ti[j] != -1)
            {
                te = ti[j];
            }
            else
            {
                te = (p1[2][j] + p2[2][j]) / sum;
            }
            kid[2][j] = te;
            
        }
        
        Color c;
        if (Math.random() < mutation)
        {
            c = new Color((int) Math.random() * 255 + 1, (int) Math.random() * 255 + 1, (int) Math.random() * 255 + 1);
        }
        else
        {
            int r, b, g;
            r = (int) (parent1.color.getRed() + parent2.color.getRed())/2;
            b = (int) (parent1.color.getBlue() + parent2.color.getBlue())/2;
            g = (int) (parent1.color.getGreen() + parent2.color.getGreen())/2;
            c = new Color(r, b, g);
        }
        
        return new Kid(sx, sy, kid, c);
    }
    
    public static void move()
    {
        for(int i=0;i<physics.length;i++)
        {
            physics[i].move(barrierArray);
        }
    }

    public static void setPopulation(int population)
    {
        GeneticBots.population = population;
    }

    public static void setBarriers(int barriers)
    {
        GeneticBots.barriers = barriers;
    }

    public static void setMutation(double mutation)
    {
        GeneticBots.mutation = mutation;
    }
    
    public static String getStats(int i)
    {
        
        String[] out = {"Generation:    " + GeneticBots.getGenerations(),
                        "Population:    " + population,
                        "Frames/Second: " + fps,
                        "Mutation Rate: " + mutation};
        return out[i];
    }

    public static void setFps(int fps)
    {
        GeneticBots.fps = fps;
    }
    
}
