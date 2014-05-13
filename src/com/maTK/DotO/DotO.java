package com.maTK.DotO;

import javax.imageio.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;

public class DotO extends JPanel {

	private final static int SIZEX = 1280;//X size of screen
	private final static int SIZEY = 720; //Y size of screen
	private String backgroundPath = "resources/Background.png";//Path to the background picture
	public int score;//In-game score
	public int gold;//Amount of gold currently in bank
	public int waveNum;//Counter of which wave the player is facing
	public int originHP;//HP of the origin
	public ArrayList creepWave = new ArrayList();//The Array List of all the arrays of different creeps there will be per wave
	public Vector towers = new Vector();//The vector of all the towers active on the map
	public Tower tempTower;//A place holder for the towers we create for the vector "tower"
	public Image backgroundImage;

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
		backgroundImage = loadImage(backgroundPath);  
		setBackground(Color.white);
	}

    private static void createAndShowGUI() {
    	    	
		//1. Create the frame.
		JFrame frame = new JFrame("Defense Of The Origin");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//4. Size the frame.
		frame.setSize(SIZEX, SIZEY);
		
		//5. Show it.
		frame.setVisible(true);
		
		//3. Add Background Panel
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
		System.out.println("painted");
		super.paintComponent(g);  
		int w = getWidth();  
		int h = getHeight();
		g.drawImage(backgroundImage, 0, 0, w, h, this);	
	}

}