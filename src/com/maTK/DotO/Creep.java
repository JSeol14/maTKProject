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
	public double slope;//slope of line the creep is currently traveling on
	public int frameCount;//corresponds to total number of frames in animation
	public int frameNum;//corresponds to current frame number
	public boolean isAlive;//Creep's alive/dead status
	public int goldNum;//Amount of gold received for creep kill
	public int dmg;//dmg/lives taken away when creep attacks the Origin
	public Road path;//path of travel
	public int pathNum = 0;//number of which point the creep is on
	public Point nextPoint;//nextPoint on path
	
	public Creep(int hp, double spd, int damage, int gold)
	{
		maxHp = hp;
		curHp = hp;
		speed = spd;
		dmg = damage;
		goldNum = gold;
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
	
	public void reachPoint()
	{
		pathNum++;
		nextPoint = ((Point) path.points.get(pathNum));
		int difX = nextPoint.x - xpos;
		int difY = nextPoint.y - ypos;
		dir = Math.atan2(difX, difY);
		slope = (double)(difY)/(double)(difX);
	}
	
	public void move()
	{
		double difX = Math.cos(dir)*slope;
		double difY = Math.sin(dir)*slope;
		aXpos += difX;
		aYpos += difY;
		xpos = (int) Math.floor(aXpos);
		ypos = (int) Math.floor(aYpos);
	}
}
