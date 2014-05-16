package com.maTK.DotO;

import javax.imageio.*;
import javax.swing.*;

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
	private final static int SPRITEX = 100;
	private final static int SPRITEY = 100;
	
	
	private String backgroundPath = "resources/Background.png";//Path to the background picture (the distance from the far left to the tower menu is 924 pixels.)
	private String towerSpriteImagePath = "resources/TowerSprite.png";
	private String openingScreenPath = "resources/OpeningScreen.png";
	private String animationSpritePath = "resources/AnimationSprite.png";
	
	private boolean gameStarted = false;
	
	private static int towerSizeX; 
	private static int towerSizeY;
	
	private int score;//In-game score
	private int gold;//Amount of gold currently in bank
	private int waveNum;//Counter of which wave the player is facing
	private int originHP;//HP of the origin
	private int recSelected = -1;
	
	private ArrayList<Creep> creepWave = new ArrayList<Creep>();//The Array List of all the arrays of different creeps there will be per wave
	private ArrayList<Tower> towers = new ArrayList<Tower>();//The Array List of all the towers active on the map
	private ArrayList<Road> roads = new ArrayList<Road>();//The Array List  of all the paths on the map
	private Tower tempTower;//A place holder for the towers we create for the vector "tower"
	
	private Image openingScreenImage;
	private Image backgroundImage;
	private Image towerSpriteImage;
	private Image creepSpriteImage;
	
	private Creep[] testCreep = new Creep[4];
	private int counter=0;
	
	private Rectangle[] towerRec = new Rectangle[9];

	
	//1. Create the frame.
	private static JFrame frame = new JFrame("Defense Of The Origin");
	
	/*
	 * Tower.type is an integer symbolizing all the different colors
	 * 
	 * 0: Red, 1: Orange, 2: Yellow, 3: Green, 4: Blue, 5: Purple, 6: Pink, 7: Grey, 8: Black
	 */

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
		creepSpriteImage = loadImage(animationSpritePath);
		setBackground(Color.white);
	    addMouseListener(this);
	    addMouseMotionListener(this);
	    initRoads();
	}

    private static void createAndShowGUI() {

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

	private void createTower(int type, int xpos, int ypos)
	{
		if(gameStarted)
		{
			tempTower = new Tower(xpos,ypos,type,0);
			towers.add(tempTower);
			System.out.println("Tower added at x: "+xpos+", y:"+ypos);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)  
	{  
		super.paintComponent(g);  
		Rectangle r = frame.getBounds();
		int w = r.width;  
		int h = r.height;
		towerSizeX = (int)((TOWERX*w)+((w-(TOWERX*w))/10)*(3)) - (int)((TOWERX*w)+((w-(TOWERX*w))/10)*(1));
		towerSizeY = (int)((TOWERY*h)+((h-(TOWERY*h))/18)*(2)+((h-(TOWERY*h))/36)) - (int)((TOWERY*h)+((h-(TOWERY*h))/36));
		
		if(!gameStarted)
		{
			g.drawImage(openingScreenImage, 0,0,w,h,this);
		}
		
		if(gameStarted)
		{
			g.drawImage(backgroundImage, 0, 0, w, h, this);				
			for(int j=0; j<3; j++)
			{
				for(int k=0; k<3; k++)
				{
					g.drawImage(towerSpriteImage, (int)((TOWERX*w)+((w-(TOWERX*w))/10)*((3*k)+1)),(int)((TOWERY*h)+((h-(TOWERY*h))/18)*(2*j)+((h-(TOWERY*h))/36)),(int)((TOWERX*w)+((w-(TOWERX*w))/10)*((3*k)+3)),(int)((TOWERY*h)+((h-(TOWERY*h))/18)*(2*j+2)+((h-(TOWERY*h))/36)),SPRITEX*0,(SPRITEY*(3*j))+(SPRITEY*k),SPRITEX*1,(SPRITEY*(3*j))+(SPRITEY*(k+1)),this);
					
					towerRec[3*j+k] = new Rectangle ((int)((TOWERX*w)+((w-(TOWERX*w))/10)*((3*k)+1)),(int)((TOWERY*h)+((h-(TOWERY*h))/18)*(2*j)+((h-(TOWERY*h))/36)),towerSizeX,towerSizeY);
				}
			}
			
			for(int i=0; i<towers.size();i++)
			{
				tempTower = towers.get(i);
				if(tempTower.isAlive)
				{
					//g.drawImage(towerSpriteImage, 20, 20, 20+towerSizeX,20+towerSizeY, 0, tempTower.type*SPRITEY, SPRITEX, (tempTower.type+1)*SPRITEY,this);
					double tempXpos = tempTower.xpos/(double)SIZEX;
					double tempYpos = tempTower.ypos/(double)SIZEY;
					g.drawImage(towerSpriteImage, (int)(tempXpos*w)-towerSizeX/2, (int)(tempYpos*h)-towerSizeY/2, (int)((tempXpos)*w)+towerSizeX/2,(int)((tempYpos)*h)+towerSizeY/2, 0, tempTower.type*SPRITEY, SPRITEX, (tempTower.type+1)*SPRITEY,this);
				}
			}
			
			for(int i=0; i<roads.size(); i++)
			{
				Road tRoad = roads.get(i);
				for(int a=0; a<tRoad.points.size(); a++)
				{
					Point p = tRoad.points.get(a);
					g.setColor(Color.red);
					g.fillRect(p.x, p.y, 3, 3);
				}
			}
		    for(int i=0; i<testCreep.length; i++)
		    {
		    	int tempInt = counter%30;
		    	int animTemp;
		    	if(tempInt<15)
		    	{
		    		animTemp = 0;
		    	}
		    	else
		    	{
		    		animTemp = 100;
		    	}
				g.setColor(Color.blue);
				g.fillRect(testCreep[i].xpos, testCreep[i].ypos, 3, 3);
				testCreep[i].move();
				double tempXpos = testCreep[i].xpos/(double)SIZEX;
				double tempYpos = testCreep[i].ypos/(double)SIZEY;
				g.drawImage(creepSpriteImage, (int)(tempXpos*w) - towerSizeX/2, (int)(tempYpos*h) - towerSizeY/2, (int)((tempXpos)*w)+towerSizeX/2,(int)((tempYpos)*h)+towerSizeY/2, animTemp, i*SPRITEY, animTemp + SPRITEX, (i+1)*SPRITEY,this);
		    }
		    counter++;
		}
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		repaint();
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int xvar;
		int yvar;  
		Rectangle r = frame.getBounds();
		int w = r.width;  
		int h = r.height;
		Point pointClicked;
        
		xvar = arg0.getX();
        yvar = arg0.getY();
        //System.out.println("x: " + xvar + ", y: " + yvar);
        pointClicked = new Point(xvar,yvar);
        
        
        if(gameStarted)
        {
    		if(recSelected!=-1&&(xvar<TOWERX*w))
    		{
    			createTower(recSelected,(int)(((double)pointClicked.x/(double)w)*(double)SIZEX),(int)(((double)pointClicked.y/(double)h)*(double)SIZEY));
    			recSelected = -1;
    		}
        	for(int i=0; i<towerRec.length; i++)
        	{
        		if(towerRec[i].contains(pointClicked))
        		{
                	recSelected = i;
        		}
        	}
        }       
        if(!gameStarted)
        {
        	gameStarted = true;
        }

        repaint();
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
	
	private void initRoads()
	{
		//TOP
	    Road p = new Road();
	    p.addPoint(0, 196);
	    p.addPoint(41, 157);
	    p.addPoint(90, 105);
	    p.addPoint(146, 70);
	    p.addPoint(215, 53);
	    p.addPoint(279, 51);
	    p.addPoint(323, 73);
	    p.addPoint(348, 125);
	    p.addPoint(377, 162);
	    p.addPoint(414, 208);
	    p.addPoint(456, 244);
	    p.addPoint(468, 278);
	    p.addPoint(468, 288);
	    roads.add(p);

		//RIGHT
	    p = new Road();
	    p.addPoint(652, 0);
	    p.addPoint(648, 48);
	    p.addPoint(654, 113);
	    p.addPoint(664, 164);
	    p.addPoint(683, 216);
	    p.addPoint(724, 242);
	    p.addPoint(771, 270);
	    p.addPoint(802, 323);
	    p.addPoint(800, 386);
	    p.addPoint(765, 441);
	    p.addPoint(718, 469);
	    p.addPoint(672, 471);
	    p.addPoint(640, 445);
	    p.addPoint(610, 407);
	    p.addPoint(578, 373);
	    p.addPoint(550, 362);
	    p.addPoint(538, 362);
	    roads.add(p);

		//BOTTOM
	    p = new Road();
	    p.addPoint(935, 558);
	    p.addPoint(879, 562);
	    p.addPoint(831, 597);
	    p.addPoint(781, 634);
	    p.addPoint(727, 668);
	    p.addPoint(664, 670);
	    p.addPoint(579, 670);
	    p.addPoint(484, 669);
	    p.addPoint(412, 649);
	    p.addPoint(367, 609);
	    p.addPoint(371, 564);
	    p.addPoint(411, 527);
	    p.addPoint(451, 487);
	    p.addPoint(463, 453);
	    p.addPoint(465, 431);
	    roads.add(p);
	    
		//LEFT
	    p = new Road();
	    p.addPoint(176, 672);
	    p.addPoint(165, 628);
	    p.addPoint(140, 573);
	    p.addPoint(112, 505);
	    p.addPoint(93, 443);
	    p.addPoint(90, 398);
	    p.addPoint(112, 351);
	    p.addPoint(134, 315);
	    p.addPoint(175, 279);
	    p.addPoint(215, 260);
	    p.addPoint(262, 258);
	    p.addPoint(290, 290);
	    p.addPoint(321, 321);
	    p.addPoint(361, 354);
	    p.addPoint(397, 360);
	    roads.add(p);
	    for(int i=0; i<testCreep.length; i++)
	    {
	    	testCreep[i]= new Creep(100, 1, 100, 10);
		    testCreep[i].addPath(roads.get(i));
	    }
	}

}