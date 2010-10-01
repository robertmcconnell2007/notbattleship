package game;

import java.awt.Color;
import java.awt.Graphics;





/**
 * Actual data for the game board.
 * TODO: click to board coordinates?
 * TODO: do I have enough color types, more can be added
 * TODO: gasp in awe of eclipse
 * @author Sean Cox
 */
public class GameBoard
{
	/**
	 * the actual playing board
	 */
	int playBoard[][];
	/**
	 * the height of the board. read in by constructor
	 */
	int boardH;
	/**
	 * the width of the board. read in by constructor
	 */
	int boardW;
	/**
	 * where the board draws x
	 */
	int boardX;
	/**
	 * where the board draws y
	 */
	int boardY;
	/**
	 * draws the squares of the board according to color
	 * @param g is the graphics handler
	 */
	public void draw(Graphics g)
	{
		

		for(int y = 0; y < boardH; y++)
		{
			for(int x = 0; x < boardW; x++)
			{
				switch(playBoard[y][x])
				{
				case 0:
					{
						g.setColor(new Color(255,0,0));
						break;
					}
				case 1:
					{
						g.setColor(new Color(0,255,0));
						break;
					}
				case 2:
					{
						g.setColor(new Color(0,0,255));
						break;
					}
				}
				g.fillRect(y*30+boardX,x*30+boardY,29,29);
			}
		}
		
		
	}
	/**
	 * simple variable initializer and class constructor
	 * @param width (of the board by squares)
	 * @param height (height of the board by squares)
	 * @param bX (where board draws at x)
	 * @param bY (where board draws at y)
	 */
	GameBoard(int width, int height, int bX, int bY)
	{
		boardW = width;
		boardH = height;
		playBoard = new int[height][width];
		boardX = bX;
		boardY = bY;
		
	}
}

