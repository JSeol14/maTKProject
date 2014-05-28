package com.maTK.DotO;

import java.awt.Point;

public class Creep {
	public int maxHp;//Starting HP of creep
	public int curHp;//Current HP of creep
	public int type;//corresponds to color/elemental attribute
	public double speed;//corresponds to the quickness of the creep (number of pixels moved per frame)
	public double aXpos;//actual xpos
	public double aYpos;//actual ypos
	public int xpos;//x position of the creep
	public int ypos;//y position of the creep
	public int width;//width of creep
	public int height;//height of creep
	public double dir;//corresponds to direction creep is facing (radians)
	public double dir2;//corresponds to direction creep is facing (in 10's of degrees) i.e 1 = 5-14 degrees, 2 = 15-24 degrees, etc.
	public double slope;//slope of line the creep is currently traveling on
	public int frameCount;//corresponds to total number of frames in animation
	public int frameNum;//corresponds to current frame number
	public boolean isAlive;//Creep's alive/dead status
	public int goldNum;//Amount of gold received for creep kill
	public int dmg;//dmg/lives taken away when creep attacks the Origin
	public Road path;//path of travel
	public int pathNum = 0;//number of which point the creep is on
	public Point nextPoint;//nextPoint on path
	public double slowPercent = 1;//multiplier for speed
	public boolean frozen = false;//stops movement when frozen
	public int slowTimer = 0;
	public int freezeTimer = 0;
	public int poisonDmg = 0;
	public int poisonTimer = 0;
	public int poisonCounter = 0;
	public boolean splash = false;
	public int splashRadius = 0;
	public int splashDamage = 0;
	
	public Creep(int hp, double spd, int damage, int gold, int t)
	{
		maxHp = hp;
		curHp = hp;
		speed = spd;
		dmg = damage;
		goldNum = gold;
		type = t;
		isAlive = true;
	}
	
	public void addPath(Road p)
	{
		path = p;//set Path
		Point temp = ((Point) path.points.get(0));//get first point
		xpos = temp.x;//set Location to start of path
		ypos = temp.y;//set Location to start of path
		aXpos = xpos;
		aYpos = ypos;
		reachPoint();//set next point
	}
	
	public boolean reachPoint()
	{
		pathNum++;
		if(pathNum<path.points.size())
		{
			nextPoint = ((Point) path.points.get(pathNum));
			int difX = nextPoint.x - xpos;
			int difY = nextPoint.y - ypos;
			dir = Math.atan2(difY, difX);
			dir2 = (int)(Math.toDegrees(dir)/10);
			slope = (double)(difY)/(double)(difX);
			//System.out.println("NEXT POINT dx: " + difX + " dy: " + difY + " dir: " + dir);
			return false;
		}
		else
		{
			return true;//reached last point
		}
	}
	
	public void move(int delay)
	{
		if(isAlive)
		{
			double difX = Math.cos(dir)*speed*slowPercent;
			double difY = Math.sin(dir)*speed*slowPercent;
			if(slowTimer>0)
			{
				slowTimer-=delay;
			}
			if(slowTimer<=0)
			{
				slowPercent = 1;
			}
			if(poisonTimer>0)
			{
				poisonTimer-=delay;
				if(poisonCounter == 0)
				{
					curHp-=poisonDmg;
					poisonCounter = 1000/delay;
				}
				poisonCounter--;
			}
			if(poisonTimer<=0)
			{
				poisonDmg = 0;
			}
			
			if(!frozen)
			{
				aXpos += difX;
				aYpos += difY;
			}
			else
			{
				freezeTimer-=delay;
				if(freezeTimer <=0)
				{
					frozen = false;
				}
			}
			
			if(difX>=0)
			{
				xpos = (int) Math.floor(aXpos);
			}
			else
			{
				xpos = (int) Math.ceil(aXpos);
			}
			if(difY>=0)
			{
				ypos = (int) Math.floor(aYpos);
			}
			else
			{
				ypos = (int) Math.ceil(aYpos);
			}
			//System.out.println("dx: " + difX + " dy: " + difY + " dir: " + dir);
			//System.out.println("CREEP x: " + aXpos + " y: " + aYpos);
		}
	}
}
