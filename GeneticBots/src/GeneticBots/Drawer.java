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
            
            g.setColor(actors[i].getColor());
            switch(actors[i].getShape())
            {
                case 0: g.fillRect(actors[i].getX(), actors[i].getY(), actors[i].getWidth(), actors[i].getLength());
                    break;
                case 1: g.fillOval(actors[i].getX(), actors[i].getY(), actors[i].getRadius()*2, actors[i].getRadius()*2);
                    break;
            }
        }
        g.setColor(Color.black);
        g.drawString("Generation: " + GeneticBots.getGenerations(), 100, 100);
    }
}
