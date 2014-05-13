package com.maTK.DotO;

import javax.imageio.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;

public class DotO extends JPanel implements MouseListener, MouseMotionListener {

	private final static int SIZEX = 1280;//X size of screen
	private final static int SIZEY = 720; //Y size of screen
	private final static double TOWERX = (932/(double)SIZEX);
	private final static double TOWERY = (68/(double)SIZEY);
	
	private String backgroundPath = "resources/Background.png";//Path to the background picture (the distance from the far left to the tower menu is 924 pixels.)
	private String towerSpriteImagePath = "resources/TowerSprite.png";
	private String openingScreenPath = "resources/OpeningScreen.png";
	private String animationSpritePath = "resources/AnimationSprite.png";
	
	private boolean gameStarted = false;
	
	private int score;//In-game score
	private int gold;//Amount of gold currently in bank
	private int waveNum;//Counter of which wave the player is facing
	private int originHP;//HP of the origin
	
	private ArrayList creepWave = new ArrayList();//The Array List of all the arrays of different creeps there will be per wave
	private Vector towers = new Vector();//The vector of all the towers active on the map
	private Tower tempTower;//A place holder for the towers we create for the vector "tower"
	
	private Image openingScreenImage;
	private Image backgroundImage;
	private Image towerSpriteImage;
	private Image animationSpriteImage;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	createAndShowGUI();
            }
        });
	}
	public DotO()
	{
        setBorder(BorderFactory.createLineBorder(Color.black));
        openingScreenImage = loadImage(openingScreenPath);
		backgroundImage = loadImage(backgroundPath);  
		towerSpriteImage = loadImage(towerSpriteImagePath);
		animationSpriteImage = loadImage(animationSpritePath);
		setBackground(Color.white);
	    addMouseListener(this);
	}

    private static void createAndShowGUI() {
    	    	
		//1. Create the frame.
		JFrame frame = new JFrame("Defense Of The Origin");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//3. Size the frame.
		frame.setSize(SIZEX, SIZEY);
		
		//4. Show it.
		frame.setVisible(true);
		
		//5. Add Background Panel
	    frame.add(new DotO());
        
    }
	private Image loadImage(String path)  
	{  
		String fileName = path;  
		Image tempImage = null;
		try  
		{  
			URL url = getClass().getResource(fileName);  
			tempImage = ImageIO.read(url);  
		}  
		catch(MalformedURLException mue)  
		{  
			System.out.println("url: " + mue.getMessage());  
		}  
		catch(IOException ioe)  
		{  
			System.out.println("read: " + ioe.getMessage());  
		}
		return tempImage;
	}


	@Override
	protected void paintComponent(Graphics g)  
	{  
		super.paintComponent(g);  
		int w = getWidth();  
		int h = getHeight();
		
		if(!gameStarted)
		{
			g.drawImage(openingScreenImage, 0,0,w,h,this);
		}
		
		if(gameStarted)
		{
			g.drawImage(backgroundImage, 0, 0, w, h, this);	
			g.drawImage(towerSpriteImage, (int)((TOWERX)*w),(int)((TOWERY)*h),(int)(w-((TOWERX)*w)),(int)(h-((TOWERY)*h)),this);
			
			while(true)
			{
				g.drawImage(backgroundImage, 0, 0, w, h, this);	
				g.drawImage(animationSpriteImage, 100,100,200,200,0,0,100,100,this);
				try {
					Thread.sleep(100); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.drawImage(backgroundImage, 0, 0, w, h, this);	
				g.drawImage(animationSpriteImage, 100,100,200,200,100,0,200,100,this);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int xvar;
		int yvar;
        xvar = arg0.getX();
        yvar = arg0.getY();
        System.out.println("x: "+xvar+" y: "+yvar);
        
        if(!gameStarted)
        {
        	gameStarted = true;
        	repaint();
        }
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}