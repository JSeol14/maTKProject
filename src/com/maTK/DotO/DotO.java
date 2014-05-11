package com.maTK.DotO;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;

import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class DotO extends JFrame {

	public int score;//In-game score
	public int gold;//Amount of gold currently in bank
	public int waveNum;//Counter of which wave the player is facing
	public int originHP;//HP of the origin
	
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
		System.out.println("It works!"); 
		System.out.println("Josh Does MAD werk");
		
		//1. Create the frame.
		JFrame frame = new JFrame("Defense Of The Origin");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//4. Size the frame.
		frame.setSize(1280,720);

		//5. Add in Panel
		frame.add(new GPanel());
		
		//6. Show it.
		frame.setVisible(true);
	
	}
	


}

class GPanel extends JPanel {
	public Image background;
	
    public GPanel() {
		background = Toolkit.getDefaultToolkit().getImage("background.png");
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(1280,720);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Text
        g.drawString("JOSHUA PLEASE TELL ME THIS WORKS!",10,20);
        g.drawImage(background,0,0,this);
    }  
}
