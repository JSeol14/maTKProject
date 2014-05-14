package com.maTK.DotO;

public class Tower {
	public int level;//corresponds to number of edges/power - used in spreadsheet to get tower img
	public int type;//corresponds to color/elemental attribute - used in spreadsheet to get tower img
	public int range;//radius of circle in which the tower can fire
	public int damage;//tower's damage
	public int xpos;//x position of the tower
	public int ypos;//y position of the tower
	public int width;//width of tower
	public int height;//height of tower
	public int upPrice;//cost to upgrade	
	public int sellPrice;//money from selling
	public boolean isAlive;//alive or not
	
	public Tower (int initX, int initY, int w, int h, int t, int l)
	{
		xpos = initX;
		ypos = initY;
		width = w;
		height = h;
		type = t;
		level = l;
	}
	
	public void upgrade()//Upgrades tower to next level
	{
		level++;
		upPrice = level;//Gives price of upgrade to next level
		sellPrice = level;//Gives the value of the tower if sold
	}
	
	public void fire(Creep target)
	{
		
	}
}