/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticbots;

import java.awt.Color;
import java.util.List;

/**
 *
 * @author 16_deckhut_nicholas
 */
public class Dog extends Physics
{
    
    public Dog(double x, double y, int shape)
    {
        super(x, y, Color.GRAY, 0);
        
    }

    @Override
    public void move(List<Barrier> b)
    {
        
    }
}
