package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
/*
 * Game will create game boards in order to play
 * if playing single-player, game will create 2 GameBoards
 * if playing multi-player, game will set up networking
 */
public class Game extends JPanel implements MouseListener, Runnable, MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * I want to use this to represent what state we are in
	 * @author Robs
	 */
	enum States
	{
		titleScreen,
		askVersus,
		askSalvo,
		placingShipsPlayer1,
		placingShipsPlayer2,
		player1Turn,
		changingChairs,
		player2Turn,
		displayWinner,
		quitGame,
	}
	//static Message otherPlayer;
	static GameBoard board1;
	static GameBoard board2;
	
	/**
	 * playing versus an AI
	 * and playing with salvo rule
	 * num of shots remaining
	 */
	static boolean AIPlayer = false;
	static boolean salvo = false;
	static int shotsRemaining = 1;
	/**
	 * ready boolean that states the player is ready
	 */
	static boolean readyPressed = false;
	/**
	 * sets up the current state the game is in.
	 * Default: titleScreen
	 */

	static States currentState = States.titleScreen;
	
	/**
	 * holds the previous state just in case we need it
	 */
	static States previousState = currentState;
	/**
	 * this is to show whose turn it is.  
	 * Default: player 1
	 */
	static boolean player1 = true;
	/**
	 * user clicked, the clickedX and clickedY will store the location of the click
	 * user clicked rotate
	 */
	static boolean hasClicked;
	static boolean hasRotateClicked;
	/**
	 * this will hold the X position of where a player clicked.
	 */
	int clickedX = 0;
	/**
	 * this will hold the Y position of where a player clicked.
	 */
	int clickedY = 0;
	/**
	 * rectData for center button
	 */
	static Rect nextTurnButton;
	static Rect yesButton;
	static Rect noButton;
	/**
	 * rect for shots
	 */
	static Rect shotRect;
	
	static boolean winner;
	
	public static void main(String[] args)
	{
		Component g = new Game();
		JFrame jf = new JFrame("!BattleShip");
		jf.setSize(800,600);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(g);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		/**
		 * This is to load an image. 
		 * battleship.gif is my test image.  You can replace it with anything.  Eventually
		 * it will be our titleScreen
		 * as long as you put the image in the folder.
		 * http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Java/Chapter06/images.html
		 * I put in a bitmap image as well.  I read that java only uses gif, jpg.
		 * I tested with bitmap, but it doesn't show so everything needs to be either gif or jpg.
		 */

		if(currentState == States.titleScreen)
		{
			Image titleScreen = Toolkit.getDefaultToolkit().getImage("battleship.gif");
			g.drawImage(titleScreen, 0,0, this);
		}
		/**
		 * this statement checks to make sure we are at a spot we should be drawing the board
		 * IE: only draw the board during states: placingShips, player1Turn and player2Turn
		 */
		if(currentState == States.askVersus)
		{
			g.setColor(new Color(0,0,0));
			g.drawString("Would you like to play against a computer? Or player?", 0, 50);
			g.setColor(new Color(0,255,0));
			g.fillRoundRect(yesButton.x, yesButton.y, yesButton.w, yesButton.h, 25, 25);
			g.setColor(new Color(0,0,0));
			g.drawString("Player", yesButton.x + 25, yesButton.y + 25);
			g.setColor(new Color(255,0,0));
			g.fillRoundRect(noButton.x, noButton.y, noButton.w, noButton.h, 25, 25);
			g.setColor(new Color(0,0,0));
			g.drawString("Computer", noButton.x + 25, noButton.y + 25);
		}
		if(currentState == States.askSalvo)
		{
			g.setColor(new Color(0,0,0));
			g.drawString("Would you like to play with the salvo rule?", 0, 50);
			g.setColor(new Color(0,255,0));
			g.fillRoundRect(yesButton.x, yesButton.y, yesButton.w, yesButton.h, 25, 25);
			g.setColor(new Color(0,0,0));
			g.drawString("Yes", yesButton.x + 25, yesButton.y + 25);
			g.setColor(new Color(255,0,0));
			g.fillRoundRect(noButton.x, noButton.y, noButton.w, noButton.h, 25, 25);
			g.setColor(new Color(0,0,0));
			g.drawString("No", noButton.x + 25, noButton.y + 25);
		}
		if(currentState == States.placingShipsPlayer1 || currentState == States.placingShipsPlayer2)
		{
			if(currentState != States.changingChairs || AIPlayer)
			{
				boolean showShips = false;
				if(currentState == States.placingShipsPlayer1 || AIPlayer)
					showShips = true;
				board1.draw(g,showShips);
				board2.draw(g,!showShips);
			}
			else
			{
				board1.draw(g,false);
				board2.draw(g,false);
				g.setColor(new Color(255,0,0));
				g.fillRoundRect(nextTurnButton.x, nextTurnButton.y, nextTurnButton.w, nextTurnButton.h, 25, 25);
				g.setColor(new Color(0,0,0));
				g.drawString("Next Player", nextTurnButton.x + 25, nextTurnButton.y + 25);
			}
		}
		if(currentState == States.player1Turn || currentState == States.player2Turn || currentState == States.changingChairs)
		{
			if(currentState != States.changingChairs || AIPlayer)
			{
				boolean showShips = false;
				int xpos = 0, ypos = 0;
				if(salvo)
				{
					xpos = board1.boardX + 5;
					ypos = board1.boardY+(board1.boardH*board1.tileSize) + 5;
				}
				if(currentState == States.player1Turn || AIPlayer)
					showShips = true;
				g.setColor(new Color(255,0,0));
				if(currentState == States.player1Turn)
					g.fillRect(board2.boardX-5, board2.boardY-5, (board2.boardW*board2.tileSize)+10, (board2.boardH*board2.tileSize)+10);
				else
				{
					if(salvo)
					{
						xpos = board2.boardX;
						ypos = board2.boardY+(board2.boardH*board2.tileSize) + 5;
					}
					g.fillRect(board1.boardX-5, board1.boardY-5, (board1.boardW*board1.tileSize)+10, (board1.boardH*board1.tileSize)+10);
				}
				if(salvo)
				{
					for(int i = 0; i < shotsRemaining; ++i)
					{
						g.fillRect(xpos, ypos, shotRect.w, shotRect.h);
						xpos = xpos + 5 + shotRect.w; 
					}
				}
				board1.draw(g,showShips);
				board2.draw(g,!showShips);
			}
			else
			{
				board1.draw(g,false);
				board2.draw(g,false);
				g.setColor(new Color(255,0,0));
				g.fillRoundRect(nextTurnButton.x, nextTurnButton.y, nextTurnButton.w, nextTurnButton.h, 25, 25);
				g.setColor(new Color(0,0,0));
				g.drawString("Next Player", nextTurnButton.x + 25, nextTurnButton.y + 25);
			}
		}
		if(currentState == States.displayWinner)
		{
			//TODO: draw the winner's name (player1 or player2)
		}
		
	}
	/**
	 * this is the default constructor for the game.
	 * it sets up the boards for the player
	 * turns on the mouse listener
	 * allows thread use
	 */
	Game()
	{
		board1 = new GameBoard(10,10,0,0);
		board2 = new GameBoard(10,10,480,0);
		nextTurnButton = new Rect(340,125,100,50);
		yesButton = new Rect(10,60,100,50);
		noButton = new Rect(10,120,100,50);
		shotRect = new Rect(0,0,10,10);
		addMouseListener(this);
		addMouseMotionListener(this);
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * this is a boolean so we know when a player is placing their ships
	 */
	boolean done = true;
	/**
	 * This function is for a player to place their ships 1 at a time
	 */
	public void placePiecesOnBoard() 
	{
		while(!done)
		{
			//set all the ships of the current player, then return when done.
			//if(shipCount is greater than number of ships I can have, done = true;
			//draw
			done = true;
		}
	}
	/**
	 * this boolean is so we know the game is running
	 */
	boolean running = true;
	/**
	 * this will be the game loop
	 * @author Robs
	 */
	public void run() {
		while(running)
		{
			//update
			checkStates();
			//throttle
			try{
				Thread.sleep(100);
			}
			catch(Exception e){
			}
			//draw
			repaint();
			
		}
	}
	/**
	 * This function is working as our state machine.
	 * It will check the current state of the game, and based on the state
	 * It will do what that state requires the player to do. 
	 * It will also change state at the end of each action if needed.
	 */
	public void checkStates()
	{
		if(currentState == States.titleScreen)
		{
			previousState = currentState;
			//display the title
			//wait for a click, then change State to States.placingShips
			if(hasClicked)
				currentState = States.askVersus;
		}
		//TODO: add state for selecting versus AI or versus Player
		else if(currentState == States.askVersus)
		{
			if(hasClicked)
			{
				if(yesButton.isIn(clickedX, clickedY))
				{
					AIPlayer = false;
					currentState = States.askSalvo;
				}
				else if(noButton.isIn(clickedX, clickedY))
				{
					AIPlayer = true;
					currentState = States.askSalvo;
				}
			}
		}
		//TODO: add state for selecting salvo or regular shots
		else if(currentState == States.askSalvo)
		{
			if(hasClicked)
			{
				if(yesButton.isIn(clickedX, clickedY))
				{
					salvo = true;
					currentState = States.placingShipsPlayer1;
				}
				else if(noButton.isIn(clickedX, clickedY))
				{
					salvo = false;
					currentState = States.placingShipsPlayer1;
				}
			}
		}
		else if(currentState == States.placingShipsPlayer1)
		{
			if(hasRotateClicked)
				board1.shipArray[board1.shipCounter].rotate();
			if(hasClicked)
			{
				if(board1.transposeShip())
				{
					if(board1.shipCounter == -1)
					{
						previousState = currentState;
						currentState = States.changingChairs;
					}
				}
			}
			if(board1.shipCounter != -1)
				board1.shipArray[board1.shipCounter].setPosition((clickedX-board1.boardX)/board1.tileSize , (clickedY-board1.boardY)/board1.tileSize);
		}
		else if(currentState == States.placingShipsPlayer2)
		{
			if(!AIPlayer)
			{
				if(hasRotateClicked)
					board2.shipArray[board2.shipCounter].rotate();
				if(hasClicked)
				{
					if(board2.transposeShip())
					{
						if(board2.shipCounter == -1)
						{
							previousState = currentState;
							currentState = States.changingChairs;
						}
					}
				}
				if(board2.shipCounter != -1)
					board2.shipArray[board2.shipCounter].setPosition((clickedX-board2.boardX)/board2.tileSize , (clickedY-board2.boardY)/board2.tileSize);
			}
			else
			{
				while(board2.shipCounter != -1)
				{
					board2.shipArray[board2.shipCounter].setPosition((int)(Math.random()*1000) % board2.boardW, (int)(Math.random()*1000) % board2.boardH);
					for(int i = 0; i < (int)Math.random() * 1000 % 10; i++)
					{
						board2.shipArray[board2.shipCounter].rotate();
					}
					board2.transposeShip();
				}
				previousState = currentState;
				currentState = States.changingChairs;
				//TODO: put computer AI placement here
			}
		}
		else if(currentState == States.player1Turn)
		{
			//player1 take your shot
			//update based on what the shots detail
			//change state to States.player2Turn;
			//currentState = States.player2Turn;
			previousState = currentState;
			if(hasClicked)
			{
				if(board2.clickedIn(clickedX, clickedY))
				{
					if(board2.clickBox((clickedX-board2.boardX)/board2.tileSize, (clickedY-board2.boardY)/board2.tileSize))
					{
						if(--shotsRemaining <= 0)
							currentState = States.changingChairs;
					}					
				}
			}
		}
		else if(currentState == States.player2Turn)
		{
			//computer/player2 take your shot
			//update based on what the shots detail
			//change state to States.player1Turn;
			//currentState = States.player1Turn;
			previousState = currentState;
			if(AIPlayer)
			{
				while(!(board1.clickBox((int)(Math.random()*1000) % board1.boardW, (int)(Math.random() * 1000) % board1.boardH)));
				if(--shotsRemaining <= 0)
					currentState = States.changingChairs;
			}
			else
			{
				if(hasClicked)
				{
					if(board1.clickedIn(clickedX, clickedY))
					{
						if(board1.clickBox((clickedX-board1.boardX)/board1.tileSize, (clickedY-board1.boardY)/board1.tileSize))
						{
							if(--shotsRemaining <= 0)
								currentState = States.changingChairs;
						}
					}
				}
			}
			
			
		}
		else if(currentState == States.changingChairs)
		{
			States newState;
			if(previousState == States.placingShipsPlayer1)
			{
				newState = States.placingShipsPlayer2;
			}
			else if(previousState == States.placingShipsPlayer2)
			{
				newState = States.player1Turn;
			}
			else if(previousState == States.player1Turn)
			{
				newState = States.player2Turn;
			}
			else if(previousState == States.player2Turn)
			{
				newState = States.player1Turn;
			}
			else
			{
				System.out.println("did not enter changingChairs from the proper state.");
				newState = currentState;
			}
			if(hasClicked || AIPlayer)
			{
				if(nextTurnButton.isIn(clickedX, clickedY) || AIPlayer)
				{
					previousState = currentState;
					currentState = newState;
					if(currentState == States.player1Turn)
					{
						if(salvo)
							shotsRemaining = 5;//TODO: shotsRemaining = board1.countShips();
						else
							shotsRemaining = 1;
					}
					if(currentState == States.player2Turn)
					{
						if(salvo)
							shotsRemaining = 5;//TODO: shotsRemaining = board2.countShips();
						else
							shotsRemaining = 1;
					}
				}
			}
		}
		else if(currentState == States.displayWinner)
		{
			//TODO: determine whom is the winner
			if(!winner)
			{
				//player 1 won
			}
			else
			{
				//player 2 won
			}
		
			
			
			
			
			
			
			
		}
		//if all of one player's ships are destroyed
		//change state to States.displayWinner;
		//currentState = States.displayWinner;
		//display the Winner
		//after a mouse click or what not
		//currentState = States.quitGame;
		else if(currentState == States.quitGame)
		{
			
			
			
			
			
			
			running = false;
		}
		//TODO: check each board for a winner
		hasClicked = false;
		hasRotateClicked = false;
		if(board1.checkWin())
		{
			previousState = currentState;
			currentState = States.displayWinner;
			winner = false;
		}
		if(board2.checkWin())
		{
			previousState = currentState;
			currentState = States.displayWinner;
			winner = true;
		}
	}

	public void mouseClicked(MouseEvent e)
	{		
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	
	public void mouseExited(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			hasRotateClicked = true;
		}
		else
			hasClicked = true;
		clickedX = e.getX();
		clickedY = e.getY();
	}
	public void mouseReleased(MouseEvent e)
	{		
	}
	
	public void mouseDragged(MouseEvent e)
	{
	}
	
	public void mouseMoved(MouseEvent e) 
	{
		clickedX = e.getX();
		clickedY = e.getY();
	}
	
	
}
