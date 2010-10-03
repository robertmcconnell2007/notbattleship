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
public class Game extends JPanel implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static GameBoard board1;
	static GameBoard board2;
	static boolean salvo = false;
	static int shots = 5;
	
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
		board1.draw(g);
		board2.draw(g);	
	}
	Game()
	{
		board1 = new GameBoard(10,10,0,0);
		board2 = new GameBoard(10,10,480,0);
		addMouseListener(this);
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
		System.out.println(e.getX() + " " + e.getY());
		repaint();
	}
	public void mouseReleased(MouseEvent e)
	{		
	}
}
