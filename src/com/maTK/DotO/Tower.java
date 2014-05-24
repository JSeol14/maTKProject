package com.maTK.DotO;

public class Tower {
	public int level;//corresponds to number of edges/power - used in spreadsheet to get tower img
	public int type;//corresponds to color/elemental attribute - used in spreadsheet to get tower img
	public int range;//radius of circle in which the tower can fire
	public int damage;//tower's damage
	public int xpos;//x position of the tower
	public int ypos;//y position of the tower
	public int upPrice;//cost to upgrade	
	public int sellPrice;//money from selling
	public boolean isAlive;//alive or not
	public int reloadTime;//number of frames between bullets
	public int reloadCount;//counter of frames between bullets
	
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