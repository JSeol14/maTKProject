package com.maTK.DotO;

import java.awt.Rectangle;

public class Tower {
	public int level;//corresponds to number of edges/power - used in spreadsheet to get tower img
	public int type;//corresponds to color/elemental attribute - used in spreadsheet to get tower img
	public String typeString;//Name of type
	public int range;//radius of circle in which the tower can fire
	public int damage;//tower's damage
	public int xpos;//x position of the tower
	public int ypos;//y position of the tower
	public int upPrice;//cost to upgrade	
	public int sellPrice;//money from selling
	public boolean isAlive;//alive or not
	public int reloadTime;//number of frames between bullets
	public int reloadCount;//counter of frames between bullets
	public boolean selected = false;
	public Rectangle rec;
	
	public Tower (int initX, int initY, int t, int l, int r, int dmg, int rT)
	{
		xpos = initX;
		ypos = initY;
		type = t;
		level = l;
		range = r;
		isAlive = true;
		damage = dmg;
		reloadTime = rT;
		reloadCount = reloadTime;
		switch(type)
		{
			case 0: typeString = "Splash";
				break;
			case 1: typeString = "Gold";
				break;
			case 2: typeString = "Range";
				break;
			case 3: typeString = "Poison";
				break;
			case 4: typeString = "Slow";
				break;
			case 5: typeString = "Freeze";
				break;
			case 6: typeString = "Chain";
				break;
			case 7: typeString = "Sight";
				break;
			case 8: typeString = "Conf.";
				break;
		}
	}
	
	public void setRec(int x, int y, int w, int h)
	{
		rec = new Rectangle(x-w/2, y-h/2, w, h);
	}
	
	public void upgrade()//Upgrades tower to next level
	{
		level++;
		upPrice = level;//Gives price of upgrade to next level
		sellPrice = level;//Gives the value of the tower if sold
	}
	
	public Projectile fire(Creep target)
	{
		Projectile temp = new Projectile(xpos, ypos, damage, target);
		return(temp);
	}
}