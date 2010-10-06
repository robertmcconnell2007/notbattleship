package game;
/*
 * used by the game board to place pieces onto the board
 * @author Ernest Capulong
 */
public class Piece
{
	//stores the piece in a 2d array for rotation
	int pieceArray [] [];
	//for the piece type
	int type;
	int shipSize;
	
	/*
	 * Function to generate a piece. Does not read from a file
	 * Rather based on the type passed creates that type of piece
	 * original enums made pieces as...
	 * 0 = carrier
	 * 1 = battleship
	 * 2 = destroyer
	 * 3 = sub
	 * 4 = scout
	 */
	public void generatePiece(int t)
	{
		type = t;
		
		switch (type)
		{
		//carrier
		case 0:
			shipSize = 5;
			break;
		//battleship
		case 1:
			shipSize = 4;
			break;
		//destroyer
		case 2:
			shipSize = 3;
			break;
		//sub
		case 3:
			shipSize = 3;
			break;
		//scout
		case 4:
			shipSize = 2;
			break;
		}
		pieceArray = new int [shipSize][shipSize];
		//places the number representing the piece at starting at row 0
		//then moves along the column, based off of Lorin's implementation
		for(int i = 0; i < shipSize; i++)
		{	
			pieceArray[0][i] = t;
		}
	}
	
	/*
	 * Please call generatePiece before calling this...
	 * I do not have code that can check if the piece to be rotated exists...
	 * I think Java might give a runtime error, not sure.
	 * -Ernest
	 */
	public void rotatePiece()
	{
		int tempArray[][];
		/*
		 * Yes I know I could use type to generate the array size
		 * But eclipse is so cool that it keeps track of array sizes anyway
		 */
		tempArray = new int [pieceArray.length][pieceArray.length];
		//double for loop to copy piece into tempArray
		for(int r = 0; r < pieceArray.length; r++)
		{
			for(int c = 0; c < pieceArray.length; c++)
			{
				tempArray[r][c] = pieceArray[r][c];
			}
		}
		//then the actual flip
		for(int r = 0; r < pieceArray.length; r++)
		{
			for(int c = 0; c < pieceArray.length; c++)
			{
				pieceArray[c][shipSize-1-r] = tempArray[r][c];
			}
		}
	}
	
	//still transcribing code from C++
	public int getPart(int x, int y)
	{
		return pieceArray[y][x];
	}
	
	//draw function for a piece
	//TODO: This
	public void drawPieceAt(int x, int y)
	{
		
	}
	
	//draw and clear
	//TODO: this
	public void drawPieceAtAndClear(int x, int y)
	{
		
	}
	

	/*
	 * copy function for a piece takes in another piece as an argument
	 * then takes the argument and copies it into this piece
	 * if the piece does not exist then it will make a new piece. I think.
	 * Java does things.
	 * It might even delete the old piece if one already exists...
	 */
	public void copyPiece(Piece other)
	{
		type = other.type;
		switch (type)
		{
		//carrier
		case 0:
			shipSize = 5;
			break;
		//battleship
		case 1:
			shipSize = 4;
			break;
		//destroyer
		case 2:
			shipSize = 3;
			break;
		//sub
		case 3:
			shipSize = 3;
			break;
		//scout
		case 4:
			shipSize = 2;
			break;
		}
		pieceArray = new int [shipSize][shipSize];
		for(int r = 0; r < shipSize; r++)
		{
			for(int c = 0; c < shipSize; c++)
			{
				pieceArray[r][c] = other.pieceArray[r][c];
			}
		}
	}
	
	public int getPieceType()
	{
		return type;
	}
}
