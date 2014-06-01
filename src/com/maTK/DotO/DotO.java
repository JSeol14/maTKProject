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

public class DotO extends JPanel implements MouseListener, MouseMotionListener, Runnable {

	private final static int SIZEX = 1280;//X size of screen
	private final static int SIZEY = 720; //Y size of screen
	private final static double TOWERX = (932/(double)SIZEX);
	private final static double TOWERY = (55/(double)SIZEY);
	private final static int SPRITEX = 100;
	private final static int SPRITEY = 100;
	private final static int DELAY = 5;
	
	private String backgroundPath = "resources/Background2.png";//Path to the background picture (the distance from the far left to the tower menu is 924 pixels.)
	private String sideImagePath = "resources/SideBar.png";
	private String towerSpriteImagePath = "resources/TowerSprite.png";
	private String towerSpriteImagePath2 = "resources/TowerSheetTransparent.png";
	private String openingScreenPath = "resources/OpeningScreen.png";
	private String animationSpritePath = "resources/AnimationSprite.png";
	private String animationSpritePath2 = "resources/FinalBugSheet.png";
	private String rangeSpritePath = "resources/RangeIndicator.png";
	private String sellImagePath = "resources/SellButton.png";
	private String upImagePath = "resources/UpButton.png";
	
	private boolean gameStarted = false;
	
	private static int towerSizeX; 
	private static int towerSizeY;
	
	private int score = 0;//In-game score
	private int gold = 1000000000;//Amount of gold currently in bank
	private int waveNum;//Counter of which wave the player is facing
	private int originHP = 100;//HP of the origin
	private int recSelected = -1;
	private ArrayList<Creep> creepWave = new ArrayList<Creep>();//The Array List of all the arrays of different creeps there will be per wave
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();//The Array List of all the arrays of different projectiles
	private ArrayList<Tower> towers = new ArrayList<Tower>();//The Array List of all the towers active on the map
	private ArrayList<Road> roads = new ArrayList<Road>();//The Array List  of all the paths on the map
	private Tower tempTower;//A place holder for the towers we create for the vector "tower"
	private Tower placeTower;//Potential tower during placing selection
	
	private Image openingScreenImage;
	private Image backgroundImage;
	private Image sideImage;
	private Image towerSpriteImage;
	private Image towerSpriteImage2;
	private Image creepSpriteImage;
	private Image creepSpriteImage2;//with direction
	private Image rangeIndicatorImage;
	private Image sellImage;
	private Image upImage;

	private Font customFont18;
	private Font customFont36;
	private Font customFont28;
	private Font customFont72;
	
	private int counter=0;
	
	private Rectangle[] towerRec = new Rectangle[9];
	
	private Tower selectTower;
	private boolean selected = false;
	private int selNum = 0;
	private Rectangle sellRec;// = new Rectangle(0,0,0,0);
	private Rectangle upgradeRec;// = new Rectangle(0,0,0,0);
	
	Thread thread;

	
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
            public void run() 
            {
            	createAndShowGUI();

            }
        });
	}
	public DotO()
	{
        setBorder(BorderFactory.createLineBorder(Color.black));
        openingScreenImage = loadImage(openingScreenPath);
		backgroundImage = loadImage(backgroundPath);  
		sideImage = loadImage(sideImagePath);  
		towerSpriteImage = loadImage(towerSpriteImagePath);
		towerSpriteImage2 = loadImage(towerSpriteImagePath2);
		creepSpriteImage = loadImage(animationSpritePath);
		creepSpriteImage2 = loadImage(animationSpritePath2);
		rangeIndicatorImage = loadImage(rangeSpritePath);
		sellImage = loadImage(sellImagePath);
		upImage = loadImage(upImagePath);
		setBackground(Color.white);
	    addMouseListener(this);
	    addMouseMotionListener(this);
	    initRoads();
	    thread = new Thread(this);
	    thread.start();
	}
	public void run()
	{                    
		try {
			//create the font to use. Specify the size!
			customFont18 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("resources/ARDESTINE.ttf")).deriveFont(18f);
			customFont36 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("resources/ARDESTINE.ttf")).deriveFont(36f);
			customFont28 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("resources/ARDESTINE.ttf")).deriveFont(28f);
			customFont72 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("resources/ARDESTINE.ttf")).deriveFont(72f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			//register the font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("resources/ARDESTINE.ttf")));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	    catch(FontFormatException e)
	    {
	        e.printStackTrace();
	    }
		while(true)
		{
			try {
				repaint();
				doStuff();
				thread.sleep(DELAY);
				//System.out.println(originHP);
			} catch (InterruptedException e) {
			
			}
		}
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
		Rectangle r = frame.getBounds();
		int w = r.width;  
		int h = r.height;
		towerSizeX = (int)((TOWERX*w)+((w-(TOWERX*w))/10)*(3)) - (int)((TOWERX*w)+((w-(TOWERX*w))/10)*(1));
		towerSizeY = (int)((TOWERY*h)+((h-(TOWERY*h))/18)*(2)+((h-(TOWERY*h))/36)) - (int)((TOWERY*h)+((h-(TOWERY*h))/36));
		if(gameStarted)
		{
			tempTower = new Tower(xpos,ypos,type,1, 300, 5, 100);//xpos, ypos, type, level, range, damage, reload Time
			tempTower.setRec(xpos*w/SIZEX, ypos*h/SIZEY, towerSizeX, towerSizeY);
			towers.add(tempTower);
			//System.out.println("Tower added at x: "+xpos+", y:"+ypos);
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

			double tempXpos2 = 0;
			double tempYpos2 = 0;
			boolean drawSelected = false;
			
			for(int i=0; i<towers.size();i++)
			{
				tempTower = towers.get(i);
				tempTower.setRec(tempTower.xpos*w/SIZEX, tempTower.ypos*h/SIZEY, towerSizeX, towerSizeY);
				if(tempTower.isAlive)
				{
					double tempXpos = tempTower.xpos/(double)SIZEX;
					double tempYpos = tempTower.ypos/(double)SIZEY;
					g.drawImage(towerSpriteImage, (int)(tempXpos*w)-towerSizeX/2, (int)(tempYpos*h)-towerSizeY/2, (int)((tempXpos)*w)+towerSizeX/2,(int)((tempYpos)*h)+towerSizeY/2, SPRITEX*(tempTower.level-1), tempTower.type*SPRITEY, SPRITEX*tempTower.level, (tempTower.type+1)*SPRITEY,this);
					if(tempTower.selected)
					{
						tempXpos2 = tempXpos;
						tempYpos2 = tempYpos;
						drawSelected = true;
					}
				}
			}
			
			if(drawSelected)
			{
				g.drawImage(rangeIndicatorImage, (int)(tempXpos2*w)-(selectTower.range*w/SIZEX)/2, (int)(tempYpos2*h)-(selectTower.range*h/SIZEY)/2, (int)(tempXpos2*w)+(selectTower.range*w/SIZEX)/2,(int)(tempYpos2*h)+(selectTower.range*h/SIZEY)/2, 0, 0, 1000, 1000,this);
			}
			
			//Paints Dots to show path
			/*
			for(int i=0; i<roads.size(); i++)
			{
				Road tRoad = roads.get(i);
				for(int a=0; a<tRoad.points.size(); a++)
				{
					Point p = tRoad.points.get(a);
					g.setColor(Color.red);
					g.fillRect((int)((double)p.x/(double)SIZEX*(double)w), (int)((double)p.y/(double)SIZEY*(double)h), 3, 3);
				}
			}
			*/
			
			for(int i=0; i<projectiles.size(); i++)
			{
				Projectile tempProjectile = projectiles.get(i);
				double tempXpos = tempProjectile.xpos/(double)SIZEX;
				double tempYpos = tempProjectile.ypos/(double)SIZEY;
				double projectileSizeX = 30/(double)SIZEX;
				double projectileSizeY = 30/(double)SIZEY;
				g.drawImage(towerSpriteImage, (int)(tempXpos*w)-(int)(projectileSizeX*w/2), (int)(tempYpos*h)-(int)(projectileSizeY*h/2), (int)((tempXpos)*w)+(int)(projectileSizeX*w/2),(int)((tempYpos)*h)+(int)(projectileSizeY*h/2), SPRITEX*(tempProjectile.level-1), tempProjectile.type*SPRITEY, SPRITEX*tempProjectile.level, (tempProjectile.type+1)*SPRITEY,this);				
				//g.setColor(Color.red);
				//g.fillRect((int)(tempXpos*w), (int)(tempYpos*h), 2, 2);
			}
			
		    for(int i=0; i<creepWave.size(); i++)
		    {
		    	Creep tempCreep = creepWave.get(i);
		    	
		    	//System.out.println(tempCreep.confuseTimer);
		    	/*
		    	if(tempCreep.confused)
		    	{
					for(int a=0; i<tempCreep.confPath.points.size(); i++)
					{
						for(int z=0; z<tempCreep.confPath.points.size(); z++)
						{
							Point p = tempCreep.confPath.points.get(z);
							g.setColor(Color.red);
							g.fillRect((int)((double)p.x/(double)SIZEX*(double)w), (int)((double)p.y/(double)SIZEY*(double)h), 3, 3);
						}
					}
		    	}
				g.setColor(Color.blue);
				g.fillRect((int)((double)tempCreep.nextPoint.x/(double)SIZEX*(double)w), (int)((double)tempCreep.nextPoint.y/(double)SIZEY*(double)h), 3, 3);
				*/
		    	
		    	int tempInt = counter%26;
		    	int animTemp;
		    	if(tempInt<13)
		    	{
		    		animTemp = 0;
		    	}
		    	else
		    	{
		    		animTemp = 100;
		    	}
		    	/*
				g.setColor(Color.blue);
				g.fillRect((int)((double)testCreep[i].xpos/(double)SIZEX*(double)w), (int)((double)testCreep[i].ypos/(double)SIZEY*(double)h), 3, 3);
				*/
				double tempXpos = tempCreep.xpos/(double)SIZEX;
				double tempYpos = tempCreep.ypos/(double)SIZEY;
				//g.drawImage(creepSpriteImage, (int)(tempXpos*w) - towerSizeX/2, (int)(tempYpos*h) - towerSizeY/2, (int)((tempXpos)*w)+towerSizeX/2,(int)((tempYpos)*h)+towerSizeY/2, animTemp, (tempCreep.type)*SPRITEY, animTemp + SPRITEX, (tempCreep.type + 1)*SPRITEY,this);
				
				if(tempCreep.dir2>=-17 && tempCreep.dir2<=-10)
				{
					g.drawImage(creepSpriteImage2, (int)(tempXpos*w) - towerSizeX/2, (int)(tempYpos*h) - towerSizeY/2, (int)((tempXpos)*w)+towerSizeX/2,(int)((tempYpos)*h)+towerSizeY/2, (int)(animTemp*10 + (tempCreep.dir2+18)*100 + SPRITEX), (tempCreep.type + 1)*SPRITEY, (int)(animTemp*10 + (tempCreep.dir2+18)*100), (tempCreep.type)*SPRITEY,this);
				}	
				else if(tempCreep.dir2<=-1 && tempCreep.dir2>=-9)
				{
					g.drawImage(creepSpriteImage2, (int)(tempXpos*w) - towerSizeX/2, (int)(tempYpos*h) - towerSizeY/2, (int)((tempXpos)*w)+towerSizeX/2,(int)((tempYpos)*h)+towerSizeY/2, (int)(animTemp*10 + (-tempCreep.dir2)*100), (tempCreep.type + 1)*SPRITEY, (int)(animTemp*10 + (-tempCreep.dir2)*100 + SPRITEX), (tempCreep.type)*SPRITEY,this);
				}
				else if(tempCreep.dir2>=0 && tempCreep.dir2<=9)
				{
					g.drawImage(creepSpriteImage2, (int)(tempXpos*w) - towerSizeX/2, (int)(tempYpos*h) - towerSizeY/2, (int)((tempXpos)*w)+towerSizeX/2,(int)((tempYpos)*h)+towerSizeY/2, (int)(animTemp*10 + tempCreep.dir2*100), (tempCreep.type)*SPRITEY, (int)(animTemp*10 + tempCreep.dir2*100 + SPRITEX), (tempCreep.type + 1)*SPRITEY,this);
				}				
				else if(tempCreep.dir2>=10 && tempCreep.dir2<=18)
				{
					g.drawImage(creepSpriteImage2, (int)(tempXpos*w) - towerSizeX/2, (int)(tempYpos*h) - towerSizeY/2, (int)((tempXpos)*w)+towerSizeX/2,(int)((tempYpos)*h)+towerSizeY/2, (int)(animTemp*10 + (18-tempCreep.dir2)*100 + SPRITEX), (tempCreep.type)*SPRITEY, (int)(animTemp*10 + (18-tempCreep.dir2)*100), (tempCreep.type + 1)*SPRITEY,this);
				}
			}
		    for(int i=0; i<creepWave.size(); i++)
		    {
		    	Creep tempCreep = creepWave.get(i);
				double tempXpos = tempCreep.xpos/(double)SIZEX;
				double tempYpos = tempCreep.ypos/(double)SIZEY;
				
				g.setColor(Color.black);
				g.fillRect((int)(tempXpos*w)-(int)((double)40/SIZEX*w)-1, (int)(tempYpos*h)-(int)((double)50/SIZEY*h)-1, (int)((double)80/SIZEX*w)+2, (int)((double)10/SIZEY*h)+2);
				g.setColor(Color.red);
				g.fillRect((int)(tempXpos*w)-(int)((double)40/SIZEX*w), (int)(tempYpos*h)-(int)((double)50/SIZEY*h), (int)((double)80/SIZEX*w), (int)((double)10/SIZEY*h));
				g.setColor(Color.green);
				g.fillRect((int)(tempXpos*w)-(int)((double)40/SIZEX*w), (int)(tempYpos*h)-(int)((double)50/SIZEY*h), (int)((double)80/SIZEX*w*(double)tempCreep.curHp/(double)tempCreep.maxHp), (int)((double)10/SIZEY*h));
		    	
		    }
		    
		    double sXpos = 936/(double)SIZEX*(double)w;
		    double sYpos = 0/(double)SIZEY*(double)h;
		    double sWidth = 344/(double)SIZEX*(double)w;
		    double sHeight = 720/(double)SIZEY*(double)h;
		    
		    g.drawImage(sideImage, (int)sXpos, (int)sYpos, (int)sWidth, (int)sHeight, this);

			
			for(int j=0; j<3; j++)//Tower Select Panel
			{
				for(int k=0; k<3; k++)
				{
					g.drawImage(towerSpriteImage, (int)((TOWERX*w)+((w-(TOWERX*w))/10)*((3*k)+1)),(int)((TOWERY*h)+((h-(TOWERY*h))/18)*(2*j)+((h-(TOWERY*h))/36)),(int)((TOWERX*w)+((w-(TOWERX*w))/10)*((3*k)+3)),(int)((TOWERY*h)+((h-(TOWERY*h))/18)*(2*j+2)+((h-(TOWERY*h))/36)),SPRITEX*0,(SPRITEY*(3*j))+(SPRITEY*k),SPRITEX*1,(SPRITEY*(3*j))+(SPRITEY*(k+1)),this);
					
					towerRec[3*j+k] = new Rectangle ((int)((TOWERX*w)+((w-(TOWERX*w))/10)*((3*k)+1)),(int)((TOWERY*h)+((h-(TOWERY*h))/18)*(2*j)+((h-(TOWERY*h))/36)),towerSizeX,towerSizeY);
				}
			}

			if(recSelected!=-1)//ghost Image
			{		
				double tempXpos = placeTower.xpos;
				double tempYpos = placeTower.ypos;
				g.drawImage(towerSpriteImage2, (int)(tempXpos)-towerSizeX/2, (int)(tempYpos)-towerSizeY/2, (int)(tempXpos)+towerSizeX/2,(int)(tempYpos)+towerSizeY/2, 0, placeTower.type*SPRITEY, SPRITEX, (placeTower.type+1)*SPRITEY,this);
				g.drawImage(rangeIndicatorImage, (int)(tempXpos)-(placeTower.range*w/SIZEX)/2, (int)(tempYpos)-(placeTower.range*h/SIZEY)/2, (int)(tempXpos)+(placeTower.range*w/SIZEX)/2,(int)(tempYpos)+(placeTower.range*h/SIZEY)/2, 0, 0, 1000, 1000,this);
			}
			
			if(selected)//Selected Tower Stats
			{
				double scaleX = (double)w/(double)SIZEX;
				double scaleY = (double)h/(double)SIZEY;
				double tempXpos = 980;
				double tempYpos = 350;
				g.drawImage(towerSpriteImage, (int)(tempXpos*scaleX)-towerSizeX/2, (int)(tempYpos*scaleY)-towerSizeY/2, (int)(tempXpos*scaleX)+towerSizeX/2,(int)(tempYpos*scaleY)+towerSizeY/2, SPRITEX*(selectTower.level-1), selectTower.type*SPRITEY, SPRITEX*selectTower.level, (selectTower.type+1)*SPRITEY,this);
				tempXpos = 1020;
				tempYpos = 360;
				g.setFont(customFont36);
				g.setColor(Color.black);
				g.drawString(selectTower.typeString + " Tower", (int)(tempXpos*scaleX), (int) (tempYpos*scaleY));
				g.setFont(customFont18);
				g.drawString("Level: " + selectTower.level, (int)(950*scaleX), (int) (395*scaleY));
				g.drawString("Damage: " + selectTower.damage, (int)(950*scaleX), (int) (415*scaleY));
				g.drawString("Range: " + selectTower.range, (int)(950*scaleX), (int) (435*scaleY));
				g.drawString(selectTower.phrase1, (int)(1070*scaleX), (int) (395*scaleY));
				if(selectTower.phrase2 != null)
				{
					g.drawString(selectTower.phrase2, (int)(1070*scaleX), (int) (415*scaleY));
				}
				tempXpos = 1100;
				tempYpos = 465;
				sellRec = new Rectangle((int)(tempXpos*scaleX)-(int)(148/2*scaleX), (int)(tempYpos*scaleY)-(int)(51/2*scaleY),(int)(148*scaleX), (int)(51*scaleY));
				g.drawImage(sellImage, (int)(tempXpos*scaleX)-(int)(148/2*scaleX), (int)(tempYpos*scaleY)-(int)(51/2*scaleY), (int)(tempXpos*scaleX)+(int)(148/2*scaleX),(int)(tempYpos*scaleY)+(int)(51/2*scaleY), 0, 0, 308, 106,this);
				g.setFont(customFont28);
				g.setColor(Color.white);
				int adjust = (int)Math.log10(selectTower.sellPrice + 1);
				g.drawString("Sell " + selectTower.sellPrice + "g", (int)((1054 - (int)((double)(adjust)*7.5))*scaleX), (int)(473*scaleY));
				
				upgradeRec = new Rectangle(0,0,0,0);
				if(selectTower.level<5)
				{
					tempXpos = 980;
					tempYpos = 520;
					g.drawImage(towerSpriteImage, (int)(tempXpos*scaleX)-towerSizeX/2, (int)(tempYpos*scaleY)-towerSizeY/2, (int)(tempXpos*scaleX)+towerSizeX/2,(int)(tempYpos*scaleY)+towerSizeY/2, SPRITEX*selectTower.level, selectTower.type*SPRITEY, SPRITEX*(selectTower.level+1), (selectTower.type+1)*SPRITEY,this);
					g.setFont(customFont36);
					g.setColor(Color.black);
					tempXpos = 1020;
					tempYpos = 530;
					g.drawString("Next Level", (int)(tempXpos*scaleX), (int) (tempYpos*scaleY));
					g.setFont(customFont18);
					g.drawString("Level: " + selectTower.level, (int)(950*scaleX), (int) (565*scaleY));
					g.drawString("Damage: " + selectTower.damage, (int)(950*scaleX), (int) (585*scaleY));
					g.drawString("Range: " + selectTower.range, (int)(950*scaleX), (int) (605*scaleY));
					g.drawString(selectTower.phrase1, (int)(1070*scaleX), (int) (565*scaleY));
					if(selectTower.phrase2 != null)
					{
						g.drawString(selectTower.phrase2, (int)(1070*scaleX), (int) (585*scaleY));
					}				
					tempXpos = 1100;
					tempYpos = 635;
					upgradeRec = new Rectangle((int)(tempXpos*scaleX)-(int)(210/2*scaleX), (int)(tempYpos*scaleY)-(int)(51/2*scaleY), (int)(210*scaleX), (int)(51*scaleY));
					g.drawImage(upImage, (int)(tempXpos*scaleX)-(int)(210/2*scaleX), (int)(tempYpos*scaleY)-(int)(51/2*scaleY), (int)(tempXpos*scaleX)+(int)(210/2*scaleX),(int)(tempYpos*scaleY)+(int)(51/2*scaleY), 0, 0, 438, 106,this);
					g.setFont(customFont28);
					g.setColor(Color.white);
					adjust = (int)Math.log10(selectTower.upPrice + 1);
					g.drawString("Upgrade " + selectTower.upPrice + "g", (int)((1025 - (int)((double)(adjust)*7.5))*scaleX), (int)(643*scaleY));
				}
			}
		    counter++;	
		}
	}
	
	public void doStuff()
	{
		Rectangle r = frame.getBounds();
		int w = r.width;  
		int h = r.height;
	    for(int i=0; i<creepWave.size(); i++)
	    {
	    	Creep tempCreep = creepWave.get(i);
	    	if(tempCreep.confuseHit && !tempCreep.confused)
	    	{
	    		tempCreep.confuseHit = false;
	    		tempCreep.confused = true;
	    		tempCreep.confusePath();
	    	}
	    	tempCreep.move(DELAY);
	    	if(tempCreep.splash)
	    	{
	    	    for(int a=0; a<creepWave.size(); a++)
	    	    {
	    	    	Creep tempCreep2 = creepWave.get(a);
		    		double distance = Math.sqrt(Math.pow((tempCreep2.xpos - tempCreep.xpos),2) + Math.pow((tempCreep2.ypos - tempCreep.ypos),2));
	    	    	if(a!=i && distance<= tempCreep.splashRadius)
	    	    	{
	    	    		tempCreep2.curHp-=tempCreep.splashDamage;
	    				if(tempCreep2.curHp <=0)
	    				{
	    					tempCreep2.isAlive = false;
	    				}
	    	    	}
	    	    }
	    	    tempCreep.splash = false;
	    	}
			if(tempCreep.isAlive)
			{
				if(Math.abs(tempCreep.xpos - tempCreep.nextPoint.x)<=1 && Math.abs(tempCreep.ypos - tempCreep.nextPoint.y)<=1)
				{
					if(tempCreep.reachPoint())
					{
						tempCreep.isAlive = false;
						originHP -= tempCreep.dmg;
					}
				}
			}
	    }
	    
	    for(int i=0; i<towers.size(); i++)
	    {
	    	Tower tempTower = towers.get(i);
	    	if(tempTower.reloadTime>0)
	    	{
	    		tempTower.reloadTime--;
	    	}
    		//System.out.println(tempTower.reloadTime);
	    	for(int a=0; a<creepWave.size(); a++)
	    	{
	    		Creep tempCreep = creepWave.get(a);
	    		double distance = Math.sqrt(Math.pow((tempTower.xpos - tempCreep.xpos),2) + Math.pow((tempTower.ypos - tempCreep.ypos),2));
	    		if(distance<=tempTower.range/2 && tempTower.reloadTime == 0)
	    		{
	    			//System.out.println("FIRE");
	    			projectiles.add(tempTower.fire(tempCreep));
	    			a=creepWave.size();//break loop to ensure that it only fires once
	    			tempTower.reloadTime = tempTower.reloadCount;
	    		}
	    	}
	    }
	    
	    for(int i=0; i<projectiles.size(); i++)
	    {
	    	Projectile tempProjectile = projectiles.get(i);
	    	tempProjectile.move((double)((double)w/(double)SIZEX));
	    }
	    for(int i=projectiles.size()-1; i>=0; i--)
	    {
	    	Projectile tempProjectile = projectiles.get(i);
	    	if(tempProjectile.isAlive == false)
	    	{
	    		projectiles.remove(i);
	    	}
	    }
	    
	    for(int i=creepWave.size()-1; i>=0; i--)
	    {
	    	Creep tempCreep = creepWave.get(i);
	    	if(tempCreep.isAlive == false)
	    	{
	    		creepWave.remove(i);
		    	Creep testCreep = new Creep(1);
		    	testCreep.addPath(roads.get((int)(Math.random()*4)));
		    	creepWave.add(testCreep);
	    	}
	    }
	}
	public int randomCreepType()
	{
		int typeNumber = -1;
		
		
		
		return typeNumber;
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		Rectangle r = frame.getBounds();
		int w = r.width;  
		int h = r.height;
		int xvar = arg0.getX();
        int yvar = arg0.getY();
		if(recSelected!=-1)
		{
			placeTower.xpos = xvar;
			placeTower.ypos = yvar;
		}
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
		int xvar;
		int yvar;  
		Rectangle r = frame.getBounds();
		int w = r.width;  
		int h = r.height;
		Point pointClicked;
        
		xvar = arg0.getX();
        yvar = arg0.getY();
        yvar = arg0.getY();
        //System.out.println("x "+xvar+" y "+yvar);
        //System.out.println("x: " + xvar + ", y: " + yvar);
        pointClicked = new Point(xvar,yvar);
        
        
        if(gameStarted)
        {
    		if(recSelected!=-1&&(xvar<TOWERX*w))
    		{
    			createTower(recSelected,(int)(((double)pointClicked.x/(double)w)*(double)SIZEX),(int)(((double)pointClicked.y/(double)h)*(double)SIZEY));
    			recSelected = -1;
    		}
    		else if(recSelected!=-1&&!(xvar<TOWERX*w - towerSizeX/2))
    		{
    			recSelected = -1;
    		}
        	for(int i=0; i<towerRec.length; i++)
        	{
        		if(towerRec[i].contains(pointClicked))
        		{
        			placeTower = new Tower(xvar,yvar,i,1, 300, 5, 100);
                	recSelected = i;
        		}
        	}
        	boolean clickTower = false;
        	boolean clickIn = false;
        	if(xvar>(int)(936*(double)w/(double)SIZEX) && yvar>(int)(320*(double)h/(double)SIZEY))
        	{
    			clickIn = true;
            	if(selected)
            	{
            		if(sellRec.contains(pointClicked))
            		{
            			gold += selectTower.sellPrice;
            			towers.remove(selNum);
            			selected = false;
            		} 
            		if(upgradeRec.contains(pointClicked))
            		{
            			gold -= selectTower.upPrice;
            			selectTower.upgrade();
            		}
            	}
        	}
        	for(int i=0; i<towers.size(); i++)
        	{
        		Tower tempTower = towers.get(i);
        		if(!clickIn)
        		{
        			tempTower.selected = false;
        		}
        		if(tempTower.rec.contains(pointClicked))
        		{
        			selNum = i;
        			tempTower.selected = true;
        			selected = true;
        			selectTower = tempTower;
        			clickTower = true;
        		}
        	}
        }       
        if(!gameStarted)
        {
        	gameStarted = true;
        }
		
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
	    for(int i=0; i<4; i++)
	    {
	    	int tempInt = (int)(Math.random()*10);
	    	creepWave.add(new Creep(1));
		    creepWave.get(i).addPath(roads.get(i));

		    /*
	    	tempInt = (int)(Math.random()*4);
		    creepWave.get(i).addPath(roads.get(tempInt));
		    */
	    }
	}

}