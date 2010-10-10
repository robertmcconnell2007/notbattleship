package game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A class for the actual ships until they are put onto the board.
 * After ship placement has occurred, this class will help in decrementing health of ships
 * in order for a player to win.
 * @author Sean
 *
 */

public class Ship 
{
	/**
	 * the health of the ship
	 */
	int health;
	/**
	 * if the ship is rotated
	 * false = horizontal
	 * true = vertical
	 */
	boolean rotated;
	
	/**
	 * the actual length of the ship
	 */
	int length;
	
	/**
	 * draw box size
	 */
	int dSize;
	
	/**
	 * where the ship draws X
	 */
	int dX;
	
	/** 
	 * where the ship draws Y
	 */
	int dY;
	
	Ship(int lth, int dsize)
	{
		length = lth;
		dSize = dsize;
		health = length;
		dX = 0;
		dY = 0;
	}
	public void draw(Graphics g, int bX, int bY)
	{
		g.setColor(new Color(139,137,137));
		if(!rotated)
		{
			for(int i = 0; i < length; i++)
			{
				g.fillRect((i*dSize+dX*dSize)+bX ,(dY*dSize)+bY ,dSize - 1 ,dSize - 1);
			}
		}
		else
		{
			for(int i = 0; i < length; i++)
			{
				g.fillRect((dX*dSize)+bX, (dY*dSize + i * dSize) + bY, dSize - 1, dSize - 1);
			}
		}
	}
	void rotate()
	{
		rotated = !rotated;
	}
	void setPosition(int nX, int nY)
	{
		dX = nX;
		dY = nY;
	}
	void decHealth()
	{
		health--;
	}
	
	
	
	
	
	
	
	
	
}
