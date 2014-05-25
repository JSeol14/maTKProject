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
	public String phrase1;
	public String phrase2;
	
	public int splashRadius = 1;//radius of projectile splash, default hits only target, splash has much less dmg
	public int extraGold = 0;//extra gold from creep kill
	public int extraRange = 0;//extra range of tower
	public int poisonDmg = 0;//Poison dps
	public double poisonTime = 0;//duration of poison
	public double slowPercent = 1;//slow effect, multiplier for creep speed
	public double slowTime = 0;//duration of slow
	public int freezeChance = 0;//percent chance of freeze (0-100)
	public double freezeTime = 0;//duration of freeze
	public int chainChance = 0;//chance of chain to another creep
	public int chainRange = 0;//range of chain to another creep
	public int trueSightRange = 0;//range of tower true sight
	public int confusionChance = 0;//chance of confusing creep
	public double confusionTime = 0;//duration of confusion
	
	public Tower (int initX, int initY, int t, int l, int r, int dmg, int rT)
	{
		upPrice = 300;
		sellPrice = 30;
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
				phrase1 = "Splash Radius: " + splashRadius;
				break;
			case 1: typeString = "Gold";
				phrase1 = "Gold/Kill: " + extraGold;
				break;
			case 2: typeString = "Range";
				phrase1 = "Extra Range: " + extraRange;
				break;
			case 3: typeString = "Poison";
				phrase1 = "Poison Dmg: " + poisonDmg;
				phrase2 = "Poison Time: " + (int)poisonTime;
				break;
			case 4: typeString = "Slow";
				phrase1 = "Slow Amount: " + (int)((100-(slowPercent*100))) + "%";
				phrase2 = "Slow Time: " + slowTime;
				break;
			case 5: typeString = "Freeze";
				phrase1 = "Freeze Chance: " + freezeChance + "%";
				phrase2 = "Freeze Time: " + freezeTime;
				break;
			case 6: typeString = "Chain";
				phrase1 = "Chain Chance: " + chainChance + "%";
				phrase2 = "Chain Range: " + chainRange;
				break;
			case 7: typeString = "Sight";
				phrase1 = "Sight Range: " + trueSightRange;
				break;
			case 8: typeString = "Dizzy";
				phrase1 = "Dizzy Chance: " + confusionChance + "%";
				phrase2 = "Dizzy Time: " + confusionTime;
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