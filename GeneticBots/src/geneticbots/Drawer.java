package geneticbots;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Nicno90
 */
public class Drawer extends JComponent
{    
    public void paint(Graphics g)
    {
        List<Actor> actors = GeneticBots.getActors();
        g.setColor(Color.YELLOW);
        for(int i=0;i<actors.size();i++)
        {
            try{
                g.setColor(actors.get(i).getColor());
                //System.out.println(actors.get(i));
                actors.get(i).reXY();
                switch(actors.get(i).getShape())
                {
                    case 0: g.fillRect((int)actors.get(i).dx,(int) actors.get(i).dy, actors.get(i).width, actors.get(i).height);
                            g.setColor(Color.BLACK);
                            g.drawRect((int)actors.get(i).dx,(int) actors.get(i).dy, actors.get(i).width, actors.get(i).height);
                        break;
                    case 1: g.fillOval((int) actors.get(i).dx, (int)actors.get(i).dy, actors.get(i).width, actors.get(i).height);
                            g.setColor(Color.BLACK);
                            g.drawOval((int) actors.get(i).dx, (int) actors.get(i).dy, actors.get(i).width, actors.get(i).height);
                        break;
            }
            } catch(Exception e)
            {
                System.out.println(actors.get(i));
                actors.get(i).setColor(Color.YELLOW);
                
                i--;
            }
        }
        g.setColor(Color.black);
        g.drawString(GeneticBots.getStats(0), 5, 10);
        g.drawString(GeneticBots.getStats(1), 5, 25);
        g.drawString(GeneticBots.getStats(2), 5, 40);
        g.drawString(GeneticBots.getStats(3), 5, 55);
        g.drawString(GeneticBots.getStats(4), 5, 70);
        g.drawString(GeneticBots.getStats(5), 5, 85);
    }
}
