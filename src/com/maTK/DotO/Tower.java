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
	public int reloadTime;//counter of frames between bullets
	public int reloadCount;//number of frames between bullets
	public boolean selected = false;
	public Rectangle rec;
	public String phrase1;
	public String phrase2;
	
	public int splashRadius = 1;//radius of projectile splash, default hits only target, splash has much less dmg
	public int extraGold = 0;//extra gold from creep kill
	public int extraRange = 0;//extra range of tower
	public int poisonDmg = 0;//Poison dps
	public int poisonTime = 0;//duration of poison
	public double slowPercent = 1;//slow effect, multiplier for creep speed
	public int slowTime = 0;//duration of slow
	public int freezeChance = 0;//percent chance of freeze (0-100)
	public int freezeTime = 0;//duration of freeze
	//reloadTime serves for rapid tower
	public int trueSightRange = 0;//range of tower true sight
	public int confusionChance = 0;//chance of confusing creep
	public int confusionTime = 0;//duration of confusion
	
	public Tower (int initX, int initY, int t, int l, int r, int dmg, int rT)
	{
		upPrice = 1;
		sellPrice = 1;
		xpos = initX;
		ypos = initY;
		type = t;
		level = l;
		range = r;
		isAlive = true;
		damage = dmg;
		reloadTime = rT;
		reloadCount = reloadTime;
		setEffects();
	}
	
	public void setRec(int x, int y, int w, int h)
	{
		rec = new Rectangle(x-w/2, y-h/2, w, h);
	}
	
	public void upgrade()//Upgrades tower to next level
	{
		level++;
		damage = 5+level;
		upPrice = level;//Gives price of upgrade to next level
		sellPrice = level;//Gives the value of the tower if sold
		setEffects();
	}
	
	public void setEffects()
	{
		switch(type)
		{
			case 0: typeString = "Splash";
				splashRadius = 50*level;
				phrase1 = "Splash Radius: " + splashRadius;
				break;
			case 1: typeString = "Gold";
				extraGold = 50*level;
				phrase1 = "Gold/Kill: " + extraGold;
				break;
			case 2: typeString = "Range";
				extraRange = 70*level;
				range = 300 + extraRange;
				phrase1 = "Extra Range: " + extraRange;
				break;
			case 3: typeString = "Poison";
				poisonDmg = 1*level;//poison damage per second
				poisonTime = 5000;//duration in milliseconds
				phrase1 = "Poison Dmg: " + poisonDmg;
				phrase2 = "Poison Time: " + (int)(poisonTime/1000);
				break;
			case 4: typeString = "Slow";
				slowPercent = 1-0.12*level;
				slowTime = 1500 + 400*level;
				phrase1 = "Slow Amount: " + (int)((100-(slowPercent*100))) + "%";
				phrase2 = "Slow Time: " + (int)(slowTime/1000);
				break;
			case 5: typeString = "Freeze";
				freezeChance = 30;
				freezeTime = 500*level;
				phrase1 = "Freeze Chance: " + freezeChance + "%";
				phrase2 = "Freeze Time: " + (int)(freezeTime/1000);
				break;
			case 6: typeString = "Rapid"; 
				reloadCount = 100-10*level;
				phrase1 = "Reload Time: " + (double)reloadCount/1000;
				break;
			case 7: typeString = "Sight";
				trueSightRange = 300+100*level;
				phrase1 = "Sight Range: " + trueSightRange;
				break;
			case 8: typeString = "Dizzy";
				confusionChance = 30;
				confusionTime = 500*level;
				phrase1 = "Dizzy Chance: " + confusionChance + "%";
				phrase2 = "Dizzy Time: " + (double)(confusionTime/1000);
				break;
		}
	}
	
	public Projectile fire(Creep target)
	{
		Projectile temp = new Projectile(xpos, ypos, damage, type, level, target);
		switch(type)
		{
			case 0:
				temp.setSplash(splashRadius);
				break;
			case 1: 
				temp.setExtraGold(extraGold);
				break;
			case 2: 
				break;
			case 3:
				temp.setPoision(poisonDmg, poisonTime);
				break;
			case 4: 
				temp.setSlow(slowPercent, slowTime);
				break;
			case 5: 
				temp.setFreeze(freezeChance, freezeTime);
				break;
			case 6:
				break;
			case 7: 
				temp.setTrueSight(trueSightRange);
				break;
			case 8: 
				temp.setConfusion(confusionChance, confusionTime);
				break;
		}
		return(temp);
	}
}