package com.maTK.DotO;

public class Projectile {

	public int damage;//projectile's damage
	public int xpos;//projectile's xpos
	public int ypos;//projectile's ypos
	public int splashRadius = 1;//radius of projectile splash, default hits only target, splash has much less dmg
	public int extraGold = 0;//extra gold from creep kill
	public int extraRange = 0;//extra range of tower
	public int poisonDmg = 0;//Poison dps
	public double slowPercent = 1;//slow effect, multiplier for creep speed
	public double slowTime = 0;//duration of slow
	public int freezeChance = 0;//percent chance of freeze (0-100)
	public double freezeTime = 0;//duration of freeze
	public int chainChance = 0;//chance of chain to another creep
	public int chainRange = 0;//range of chain to another creep
	public int trueSightRange = 0;//range of tower true sight
	public int confusionChance = 0;//chance of confusing creep
	public double confusionTime = 0;//duration of confusion
	public Creep Target;//target creep
	
	public Projectile (int initXpos, int initYpos, int dmg, Creep target)
	{
		Target = target;
		damage = dmg;
		xpos = initXpos;
		ypos = initYpos;
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
	
	public void setPoision(int poison)
	{
		poisonDmg = poison;
	}
	
	public void setSlow(double slow, double slowDur)
	{
		slowPercent = slow;
		slowTime = slowDur;
	}
	
	public void setFreeze(int freeze, double freezeDur)
	{
		freezeChance = freeze;
		freezeTime = freezeDur;
	}
	
	public void setChain(int chain, int range)
	{
		chainChance = chain;
		chainRange = range;
	}
	
	public void setTrueSight(int range)
	{
		trueSightRange = range;
	}
	
	public void setConfusion(int confusion, double confusionDur)
	{
		confusionChance = confusion;
		confusionTime = confusionDur;
	}
}
