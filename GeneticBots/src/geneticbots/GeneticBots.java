package geneticbots;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class GeneticBots
{

    protected static int fps = 60;
    protected static final int frames = 300;
    protected static int population = 100;
    protected static int barriers = 6;
    protected static Target target;
    protected static int length = 600;
    protected static int width = 900;
    protected static double mutation = .001;
    protected static List<Barrier> barrierArray;
    protected static List<Physics> physics;
    protected static List<Kid> kids;
    protected static List<Actor> actors;
    protected static int generation;
    protected static int sx = 100, sy = 500; //start x and y
    static boolean start = false;
    protected static Rectangle screen;
    protected static double closest = 10000.0;
    protected static double lClosest;
    private static Actor newBarrier;
    private static Point prev;

    public static void main(String[] args)
    {
        Properties.main(args);
        while (!start)
        {
            System.out.print("");
        }
        JFrame holder = new JFrame("GeneticBots v0.8");
        screen = new Rectangle(0, 0, holder.getWidth(), holder.getHeight());
        Drawer theDrawer = new Drawer();
        holder.setVisible(false);
        JSlider slideMutation = new JSlider();
        slideMutation.setBounds(125, 40, 150, 20);
        slideMutation.setMaximum(1000);
        slideMutation.setMinimum(0);
        slideMutation.setVisible(true);
        slideMutation.setValue((int)(mutation * 1000));
        holder.add(slideMutation);
        JSlider slideFrames = new JSlider();
        slideFrames.setBounds(125, 25, 150, 20);
        slideFrames.setMaximum(300);
        slideFrames.setMinimum(1);
        slideFrames.setVisible(true);
        slideFrames.setValue(fps);
        holder.add(slideFrames);
        holder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        holder.setBounds(0, 0, width, length);
        final JPanel homePanel = new JPanel();
        holder.getContentPane().add(theDrawer);
        barrierArray = new ArrayList();
        physics = new ArrayList();
        kids = new ArrayList();
        actors = new ArrayList();

        target = new Target((width - 60.0), (length / 2.0));
        
        
        

        double[][] temp;
        for (int k = 0; k < population; k++)
        {
            double[][] kid = new double[3][3];

            for (int j = 0; j < 3; j++)
            {
                kid[0][j] = Math.random() * 360;
            }

            for (int j = 0; j < 3; j++)
            {
                kid[1][j] = Math.random()* .001;

            }
            double sum = 0;
            double te;
            double[] ti =
            {
                -1, -1, -1
            };
            for (int j = 0; j < 3; j++)
            {
                te = kid[1][j] = Math.random();
                ti[j] = te;

                sum += te;
            }
            for (int j = 0; j < 3; j++)
            {
                kid[2][j] = ti[j];

            }

            Color c= new Color((int) (Math.random() * 255) + 1,
                    (int) (Math.random() * 255) + 1,
                    (int) (Math.random() * 255) + 1);
            kids.add(new Kid(sx, sy, kid, c));
            actors.add(kids.get(k));
            for (Kid phy : kids)
            {
                physics.add(phy);
            }
        }
        actors.add(target);
        Rectangle startZone = new Rectangle(sx - 100, sy - 100, sx + 100, sy + 100);
        for (int k = 0; k < barriers; k++)
        {
            Barrier b = new Barrier((Math.random() * (width - 15)), (Math.random() * length) - 20, (int)(Math.random() * 90) + 50, (int) (Math.random() * 60) + 30);
            actors.add(b);
            barrierArray.add(b);
            if (b.intersects(startZone)
                    || actors.get(k).intersects(new Rectangle(0, 0, 300, 90))
                    || actors.get(k).intersects(target))
            {
                barrierArray.remove(barrierArray.size() - 1);
                actors.remove(actors.size()-1);
                k--;
            }
        }
        holder.addMouseListener(new MouseListener()
        {
            public void mouseClicked(MouseEvent me)
            {
                if(me.getButton()==3)
                {
                    for(int i=0;i<barrierArray.size();i++)
                    {
                        if(barrierArray.get(i).contains(me.getX()-7,me.getY()-30))
                        {
                            
                            barrierArray.remove(i);
                            actors.remove(i+population+1);
                        }
                    }
                }
            }

            public void mousePressed(MouseEvent me)
            {
                if(me.getButton()==1)
                {
                    if(actors.get(population).contains(me.getX()-7,me.getY()-30))
                    {
                        prev=me.getPoint();
                    }
                    else
                    {
                        newBarrier = new Actor(me.getX()-7,me.getY()-30,new Color(218,165,32),1,1);
                        actors.add(newBarrier);
                    }
                }
            }

            public void mouseReleased(MouseEvent me)
            {
                if(newBarrier!=null)
                {
                    Barrier temp = new Barrier(newBarrier.x,newBarrier.y,newBarrier.width,newBarrier.height);
                    barrierArray.add(temp);
                    actors.remove(actors.size()-1);
                    actors.add(temp);
                    newBarrier=null;
                }
                prev=null;
            }

            public void mouseEntered(MouseEvent me)
            {

            }

            public void mouseExited(MouseEvent me)
            {

            }
        });
        holder.addMouseMotionListener(new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent me)
            {
                if(newBarrier!=null)
                {
                    if(me.getX()-7>newBarrier.x)
                        newBarrier.width=me.getX()-7-newBarrier.x;
                    
                    if(me.getY()-30>newBarrier.y)
                        newBarrier.height=me.getY()-30-newBarrier.y;
                }
                else if(prev!=null)
                {
                    actors.get(population).dx+=me.getX()-prev.getX();
                    target.dy+=me.getY()-prev.getY();
                    prev=me.getPoint();
                }
            }

            public void mouseMoved(MouseEvent me)
            {

            }
        });

        holder.setVisible(true);
        holder.repaint();
        while (true)
        {
            for (int k = 0; k < frames; k++)
            {
                screen.setSize(holder.getWidth(), holder.getHeight());
                move();
                holder.repaint();
                //System.out.println(k);
                try
                {
                    Thread.sleep(1000 / fps);
                } catch (InterruptedException ex)
                {
                    System.out.println(ex);
                }
                fps = slideFrames.getValue();
                mutation = slideMutation.getValue() / 1000.0;

            }
            kids = nextGen();
            for (int i = 0; i < population; i++)
            {
                actors.set(i, kids.get(i));
            }
            physics.clear();
            for (Kid phy : kids)
            {
                physics.add(phy);
            }
            generation++;
        }

    }

    public static int getGenerations()
    {
        return generation;
    }

    public static List<Actor> getActors()
    {
        return actors;
    }

    public static List<Kid> nextGen()
    {
        List<Kid> newKids = new ArrayList();
        int total = 0;
        lClosest = 10000.0;
        for (int i = 0; i < kids.size(); i++)
        {
            kids.get(i).setFit(Math.sqrt(Math.pow(kids.get(i).getX() - (target.getX() + target.width/2), 2) + Math.pow(kids.get(i).getY() - target.getY() - target.height/2, 2)));
            if (kids.get(i).getFit() < closest)
            {
                closest = kids.get(i).getFit();
            }
            if (kids.get(i).getFit() < lClosest)
            {
                lClosest = kids.get(i).getFit();
            }
            total += kids.get(i).getFit();
        }

        double sum = 0;

        for (int k = 0; k < kids.size(); k++)
        {
            kids.get(k).setFit(total / kids.get(k).getFit());
            sum += kids.get(k).getFit();
        }

        double sum2 = 0;
        for (int k = 0; k < kids.size(); k++)
        {
            sum2 += kids.get(k).getFit() / sum;
            kids.get(k).setFit(sum2);
        }

        double rand;
        Kid[] temp = new Kid[2];
        for (int i = 0; i < population; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                rand = Math.random();
                for (int k = 0; k < population - 1; k++)
                {
                    if (kids.get(k).getFit() > rand)
                    {
                        temp[j] = kids.get(k);
                        break;
                    }
                    temp[j] = kids.get(kids.size() - 1);
                }
            }
            newKids.add(makeBaby(temp[0], temp[1]));
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
        double[] ti =
        {
            -1, -1, -1
        };
        for (int j = 0; j < 3; j++)
        {
            if (Math.random() < mutation)
            {
                te = kid[1][j] = Math.random();
                ti[j] = te;
            } else
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
            } else
            {
                te = (p1[2][j] + p2[2][j]) / sum;
            }
            kid[2][j] = te;

        }

        Color c;
        if (Math.random() < mutation)
        {
            c = new Color((int) (Math.random() * 255) + 1,
                    (int) (Math.random() * 255) + 1,
                    (int) (Math.random() * 255) + 1);
        } else if (Math.random() > .5)
        {
            c = parent1.color;
        } else
        {
            c = parent2.color;
        }

        return new Kid(sx, sy, kid, c);
    }

    public static void move()
    {
        for (int i = 0; i < physics.size(); i++)
        {
            physics.get(i).move(barrierArray);
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

        String[] out =
        {
            "Generation:    " + GeneticBots.getGenerations(),
            "Population:    " + (actors.size() - barriers - 1),
            "Frames/Second: " + fps,
            "Mutation Rate: " + mutation,
            "Closest:       " + closest,
            "Last Closest:  " + lClosest
        };
        return out[i];
    }

    public static void setFps(int fps)
    {
        GeneticBots.fps = fps;
    }

}
