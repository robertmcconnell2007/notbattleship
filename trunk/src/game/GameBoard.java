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
	
	Ship shipArray[];
	
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
	int shipCounter;

	public void draw(Graphics g, Boolean showShips)
	{
		for(int y = 0; y < boardH; y++)
		{
			for(int x = 0; x < boardW; x++)
			{
				switch(playBoard[y][x])
				{
				case 0://shipArray0
					{
						g.setColor(new Color(0,255,0));
						break;
					}
				case 1://shipArray1
					{
						g.setColor(new Color(0,100,0));
						break;
					}
				case 2://shipArray2
					{
						g.setColor(new Color(0,255,255));
						break;
					}
				case 3://ShipArray3
					{
						g.setColor(new Color(255,255,0));
						break;
					}
				case 4:
					{
						g.setColor(new Color(255,0,255));
						break;
					}
				case 5:
					{
						g.setColor(new Color(0,0,255));
						break;
					}
				}
				g.fillRect(y*tileSize+boardX,x*tileSize+boardY,29,29);
			}
		}
		if(shipCounter != -1 && showShips)
		{
			shipArray[shipCounter].draw(g);
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
		//
		for(int i = 0; i < boardW; i++)
		{
			for(int k = 0; k < boardH; k++)
			{
				playBoard[i][k] = 5;
			}
		}
		
		shipArray = new Ship[5];
		shipArray[0] = new Ship(2,tileSize);
		shipArray[1] = new Ship(3,tileSize);
		shipArray[2] = new Ship(3,tileSize);
		shipArray[3] = new Ship(4,tileSize);
		shipArray[4] = new Ship(5,tileSize);
		shipCounter = 0;
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
		if(x > boardX && x < boardX+boardW && y > boardY && y < boardY+boardH)
			return true;
		return false;
	}
	public Boolean transposeShip()
	{
		//if the ship is out of bounds of the board
		if(!shipArray[shipCounter].rotated)
		{
			if(shipArray[shipCounter].dX + shipArray[shipCounter].length > boardW || shipArray[shipCounter].dX < 0
					|| shipArray[shipCounter].dY > boardH)
			{
				return false;
			}
		}
		else
		{
			if(shipArray[shipCounter].dY + shipArray[shipCounter].length  > boardH || shipArray[shipCounter].dY < 0
					|| shipArray[shipCounter].dX > boardW)
			{
				return false;
			}
		}
		//if it collides with another ship
		for(int i = 0; i < shipArray[shipCounter].length; i++)
		{
			if(!shipArray[shipCounter].rotated)
			{
				if(playBoard[shipArray[shipCounter].dX + i][shipArray[shipCounter].dY] != 5)
				{
					return false;
				}
			}
			else
			{
				if(playBoard[shipArray[shipCounter].dX][shipArray[shipCounter].dY + i] != 5)
				{
					return false;
				}
			}
		}
		//finally transpose the ship because its all good :)
		for(int i = 0; i < shipArray[shipCounter].length; i++)
		{
			if(!shipArray[shipCounter].rotated)
			{
				playBoard[shipArray[shipCounter].dX + i][shipArray[shipCounter].dY] = shipCounter;
			}
			else
			{
				playBoard[shipArray[shipCounter].dX][shipArray[shipCounter].dY + i] = shipCounter;
			}
		}
		//increase the ship counter, put it out of bounds if no more ships to place
		shipCounter++;
		if(shipCounter > 4)
		{
			shipCounter = -1;
		}
		return true;
	}
}
