package com.maTK.DotO;

import java.awt.Point;

public class Projectile {

	public int damage;//projectile's damage
	public int xpos;//projectile's xpos
	public int ypos;//projectile's ypos
	public double aXpos;//actual xpos
	public double aYpos;//actual ypos
	public double speed = 2;//speed of projectile
	public boolean isAlive = true;//tells whether projectile exists
	public double dir;//corresponds to direction projectile is facing (radians)
	public int type;//corresponds to type of projectile
	public int level;//corresponds to level of projectile
	
	public int splashRadius = 0;//radius of projectile splash, default hits only target, splash has much less dmg
	public int extraGold = 0;//extra gold from creep kill
	public int extraRange = 0;//extra range of tower
	public int poisonDmg = 0;//Poison dps
	public int poisonTime = 0;//duration of poison
	public double slowPercent = 1;//slow effect, multiplier for creep speed
	public int slowTime = 0;//duration of slow
	public int freezeChance = 0;//percent chance of freeze (0-100)
	public int freezeTime = 0;//duration of freeze
	public int trueSightRange = 0;//range of tower true sight
	public int confusionChance = 0;//chance of confusing creep
	public int confusionTime = 0;//duration of confusion
	public Creep Target;//target creep
	
	public Projectile (int initXpos, int initYpos, int dmg, int t, int l, Creep target)
	{
		Target = target;
		damage = dmg;
		type = t;
		level = l;
		xpos = initXpos;
		ypos = initYpos;
		aXpos = xpos;
		aYpos = ypos;
	}
	
	public void move(double scale)
	{
		if(isAlive)
		{
			int difX = Target.xpos - xpos;
			int difY = Target.ypos - ypos;
			dir = Math.atan2(difY, difX);
			double difX2 = Math.cos(dir)*speed*scale;
			double difY2 = Math.sin(dir)*speed*scale;
			aXpos += difX2;
			aYpos += difY2;
			if(difX2>=0)
			{
				xpos = (int) Math.floor(aXpos);
			}
			else
			{
				xpos = (int) Math.ceil(aXpos);
			}
			if(difY2>=0)
			{
				ypos = (int) Math.floor(aYpos);
			}
			else
			{
				ypos = (int) Math.ceil(aYpos);
			}
			if(Math.abs(Target.xpos - xpos)<=1 && Math.abs(Target.ypos - ypos)<=1)
			{
				Target.curHp -= damage;
				Target.slowPercent = slowPercent;
				Target.slowTimer = slowTime;
				Target.poisonDmg = poisonDmg;
				Target.poisonTimer = poisonTime;
				if(splashRadius>0)
				{
					Target.splash = true;
					Target.splashRadius = splashRadius;
					Target.splashDamage = damage;
				}
				int chance = (int)(Math.random()*100);
				if(chance<freezeChance)
				{
					Target.frozen = true;
					Target.freezeTimer = freezeTime;
				}
				if(Target.curHp <=0)
				{
					Target.isAlive = false;
				}
				isAlive = false;
			}
		}
	}
	
	public void setSplash(int radius)
	{
		splashRadius = radius;
	}
	
	public void setExtraGold(int gold)
	{
		extraGold = gold;
	}
	
	public void setRange(int range)
	{
		extraRange = range;
	}
	
	public void setPoision(int poison, int poisonDur)
	{
		poisonDmg = poison;
		poisonTime = poisonDur;
	}
	
	public void setSlow(double slow, int slowDur)
	{
		slowPercent = slow;
		slowTime = slowDur;
	}
	
	public void setFreeze(int freeze, int freezeDur)
	{
		freezeChance = freeze;
		freezeTime = freezeDur;
	}
	
	public void setTrueSight(int range)
	{
		trueSightRange = range;
	}
	
	public void setConfusion(int confusion, int confusionDur)
	{
		confusionChance = confusion;
		confusionTime = confusionDur;
	}
}
