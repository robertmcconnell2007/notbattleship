package game;

import java.awt.Component;
import java.awt.Graphics;


import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Game will create game boards in order to play
 * if playing single-player, game will create 2 GameBoards
 * if playing multi-player, game will set up networking
 */
public class Game extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static GameBoard board1;
	static GameBoard board2;
	
	public static void main(String[] args)
	{
		
		System.out.println("Hello world!");
		Component g = new Game();
		JFrame jf = new JFrame("gameBoard window");
		jf.setSize(800,600);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(g);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		board1.draw(g);
		board2.draw(g);	
	}
	Game()
	{
		board1 = new GameBoard(10,10,0,0);
		board2 = new GameBoard(10,10,480,0);
	}
}
