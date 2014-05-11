package com.maTK.DotO;

public class Tower {
	public int level;//corresponds to number of edges/power - used in spreadsheet to get tower img
	public int type;//corresponds to color/elemental attribute - used in spreadsheet to get tower img
	public int range;//radius of circle in which the tower can fire
	public int xpos;//x position of the tower
	public int ypos;//y position of the tower
	public int width;//width of tower
	public int height;//height of tower
	public int upPrice;//cost to upgrade	
	public int sellPrice;//money from selling
	public boolean isAlive;
	
	public Tower (int initX, int initY, int w, int h, int t, int l)
	{
		xpos = initX;
		ypos = initY;
		width = w;
		height = h;
		type = t;
		level = l;
	}
	
	public void fire(Creep target)
	{
		
	}
}