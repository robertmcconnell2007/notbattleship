package game;
/*
 * the game board will be created to store the location of pieces
 * and the location of shots taken by the player
 */
import java.awt.Color;
import java.awt.Graphics;





/**
 * Actual data for the game board.
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
	 * size of drawn tile
	 */
	int tileSize;
	/**
	 * draws the squares of the board according to color
	 * @param g is the graphics handler
	 * @param showShips determines whether or not the ships will show up on the board
	 */
	public void draw(Graphics g, Boolean showShips)
	{
		for(int y = 0; y < boardH; y++)
		{
			for(int x = 0; x < boardW; x++)
			{
				switch(playBoard[y][x])
				{
				case 0://empty
					{
						g.setColor(new Color(0,0,255));
						break;
					}
				case 1://has ship
					{
						if(showShips)
						{
							g.setColor(new Color(139,137,137));
						}
						else
						{
							g.setColor(new Color(0,0,255));
						}
						break;
					}
				case 2://no hit
					{
						g.setColor(new Color(0,255,255));
						break;
					}
				case 3://hit
					{
						g.setColor(new Color(255,0,0));
						break;
					}
				}
				g.fillRect(y*tileSize+boardX,x*tileSize+boardY,29,29);
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
		tileSize = 30;
		//TODO: Take out after testing
		for(int i = 0; i < width; i++)
		{
			playBoard[i][0] = 1;
		}
		
	}
	/**
	 * takes input from mouse click and is able to edit the clicked box underneath
	 * is the main attack function for the game at the moment
	 * @param cX clicked x
	 * @param cY clicked y
	 */
	public Boolean clickBox(int newX, int newY)
	{
		
		if(newX >= this.boardW || newY >= this.boardH)//if out of bounds, attack hasn't been made
		{
			return false;
		}
		if(playBoard[newX][newY] == 1)//attack made on enemy ship
		{
			playBoard[newX][newY] = 3;
			return true;
		}
		else if(playBoard[newX][newY] == 0)//attack made on open water GJ.
		{
			playBoard[newX][newY] = 2;
			return true;
		}
		else//nothing happened with that last shot so you get another one :)
		{
			return false;
		}
	}
	public boolean clickedIn(int x, int y)
	{
		if(x > boardX && x < boardX + (boardW*tileSize) && y > boardY && y < boardY + (boardH*tileSize))
		{
			return true;
		}
		return false;
	}
}

