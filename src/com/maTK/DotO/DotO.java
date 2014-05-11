package com.maTK.DotO;

import javax.imageio.*;
import javax.swing.*;

import java.awt.Graphics;

import java.awt.*;
import java.io.*;
import java.net.*;

public class DotO extends JPanel {

	public int score;//In-game score
	public int gold;//Amount of gold currently in bank
	public int waveNum;//Counter of which wave the player is facing
	public int originHP;//HP of the origin
	public int sizeX = 1280;//X size of screen
	public int sizeY = 720; //Y size of screen
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		new DotO();
	}
	public DotO()
	{
		// TODO Auto-generated method stub
		//1. Create the frame.
		JFrame frame = new JFrame("Defense Of The Origin");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//3. Add Background Panel
	    frame.getContentPane().add(new ImagePanel());

		//4. Size the frame.
		frame.setSize(sizeX,sizeY);
		
		//6. Show it.
		frame.setVisible(true);
		

	}
	
  


	public void update(Graphics g)
	{
		paint(g);
	}
 
}
class ImagePanel extends JPanel 
{
	  private Image img;

	 
	  public ImagePanel() {
	        loadImage();  
	        setBackground(Color.white);  
	  }

	   private void loadImage()  
	    {  
	        String fileName = "Background.jpg";  
	        try  
	        {  
	            URL url = getClass().getResource(fileName);  
	            img = ImageIO.read(url);  
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
		protected void paintComponent(Graphics g)  
		{  
		    super.paintComponent(g);  
		    int w = getWidth();  
		    int h = getHeight();  
		    g.drawImage(img, 0, 0, w, h, this);  
		}

}