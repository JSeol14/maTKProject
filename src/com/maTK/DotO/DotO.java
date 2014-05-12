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
	public int score;//In-game score
	public int gold;//Amount of gold currently in bank
	public int waveNum;//Counter of which wave the player is facing
	public int originHP;//HP of the origin
	public ArrayList creepWave = new ArrayList();//The Array List of all the arrays of different creeps there will be per wave
	public Vector towers = new Vector();//The vector of all the towers active on the map
	public Tower tempTower;//A place holder for the towers we create for the vector "tower"
	public ImageIcon gTraingle;
	public JPanel backgroundPanel;
	public Image backgroundImage;

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	createAndShowGUI();
            	/*while (true)
                {
                	try
                	{
                		repaint();
                		Thread.sleep(100);
                	}
                	catch(InterruptedException e){}
                }*/
            }
        });
	}
	public DotO()
	{
        setBorder(BorderFactory.createLineBorder(Color.black));
		loadBackground();  
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
	private void loadBackground()  
	{  
		String fileName = "resources/Background.jpg";  
		try  
		{  
			URL url = getClass().getResource(fileName);  
			backgroundImage = ImageIO.read(url);  
		}  
		catch(MalformedURLException mue)  
		{  
			System.out.println("url: " + mue.getMessage());  
		}  
		catch(IOException ioe)  
		{  
			System.out.println("read: " + ioe.getMessage());  
		}
	}

    protected ImageIcon createImageIcon(String path, String description) 
    {
    	URL imgURL;
		imgURL = getClass().getResource(path);
		if (imgURL != null) 
		{
			return new ImageIcon(imgURL, description);
		} 
		else 
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
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