package game;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.prefs.BackingStoreException;
/*
 * Game will create game boards in order to play
 * if playing single-player, game will create 2 GameBoards
 * if playing multi-player, game will set up networking
 */
public class Game extends JPanel implements MouseListener, Runnable
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
		placingShips,	
		player1Turn,
		player2Turn,
		handleClick,
		displayWinner,
		quitGame,
	}
	static GameBoard board1;
	static GameBoard board2;
	/**
	 * sets up the current state the game is in.
	 * Default: titleScreen
	 */
	static States currentState = States.titleScreen;
	/**
	 * this is to show whose turn it is.  
	 * Default: player 1
	 */
	static boolean player1 = true;
	/**
	 * this will hold the X position of where a player clicked.
	 */
	int clickedX = 0;
	/**
	 * this will hold the Y position of where a player clicked.
	 */
	int clickedY = 0;
	
	public static void main(String[] args)
	{
		
		System.out.println("Hello world!");
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
		 * this statement checks to make sure we are at a spot we should be drawing the board
		 * IE: only draw the board during states: placingShips, player1Turn and player2Turn
		 */
		if(currentState == States.placingShips || currentState == States.player1Turn || currentState == States.player2Turn)
		{
			board1.draw(g);
			board2.draw(g);	
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
		addMouseListener(this);
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
			System.out.print("Placing Ships");
			repaint();
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
			if(currentState == States.titleScreen)
			{
				//display the title
				//wait for a click, then change State to States.placingShips
			}
			if(currentState == States.handleClick)
			{
				currentState = States.placingShips;
			}
			if(currentState == States.placingShips)
			{
				//player1 placeShipsOnBoard();
				//computer/player2 placeShipsonBoard();
				//change state to States.player1Turn
				currentState = States.player1Turn;
			}
			if(currentState == States.player1Turn)
			{
				//player1 take your shot
				//update based on what the shots detail
				//change state to States.player2Turn;
				currentState = States.player2Turn;
			}
			if(currentState == States.player2Turn)
			{
				//computer/player2 take your shot
				//update based on what the shots detail
				//change state to States.player1Turn;
				currentState = States.player1Turn;
			}
			
			//if all of one player's ships are destroyed
			//change state to States.displayWinner;
			//currentState = States.displayWinner;
			//display the Winner
			//after a mouse click or what not
			//currentState = States.quitGame;
			if(currentState == States.quitGame)
			{
				running = false;
			}
			//throttle
			try{
				Thread.sleep(100);
			}
			catch(Exception e){
			}
			//draw
			//this is just testing.  It will print out the current State
			System.out.println(currentState);
			repaint();
			
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
		currentState = States.handleClick;
		clickedX = e.getX();
		clickedY = e.getY();
		System.out.println(e.getX() + " " + e.getY());
		repaint();
	}
	public void mouseReleased(MouseEvent e)
	{		
	}
	
	
}
