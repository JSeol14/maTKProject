package com.maTK.DotO;

public class Creep {
	public int maxHp;//Starting HP of creep
	public int curHp;//Current HP of creep
	public int type;//corresponds to color/elemental attribute
	public int speed;//corresponds to the quickness of the creep
	public int xpos;//x position of the creep
	public int ypos;//y position of the creep
	public int width;//width of creep
	public int height;//height of creep
	public int dir;//corresponds to direction creep is facing
	public int frameCount;//corresponds to total number of frames in animation
	public int frameNum;//corresponds to current frame number
	public boolean isAlive;//Creep's alive/dead status
	public int goldNum;//Amount of gold received for creep kill
	public int dmg;//dmg/lives taken away when creep attacks the Origin
}
