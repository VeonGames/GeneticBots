package geneticbots;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Nicno90
 */
public class Drawer extends JComponent
{    
    public void paint(Graphics g)
    {
        Actor[] actors = GeneticBots.getActors();
        g.setColor(Color.RED);
        for(int i=0;i<actors.length;i++)
        {
            try{
                g.setColor(actors[i].getColor());

                switch(actors[i].getShape())
                {
                    case 0: g.fillRect(actors[i].x, actors[i].y, actors[i].width, actors[i].height);
                        break;
                    case 1: g.fillOval(actors[i].x, actors[i].y, actors[i].width, actors[i].height);
                        break;
            }
            } catch(Exception e)
            {
                g.setColor(Color.yellow);
            }
        }
        g.setColor(Color.black);
        g.drawString(GeneticBots.getStats(0), 5, 10);
        g.drawString(GeneticBots.getStats(1), 5, 25);
        g.drawString(GeneticBots.getStats(2), 5, 40);
        g.drawString(GeneticBots.getStats(3), 5, 55);
    }
}
