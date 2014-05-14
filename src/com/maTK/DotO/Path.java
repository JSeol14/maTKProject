package com.maTK.DotO;

import java.awt.Point;
import java.util.ArrayList;

public class Path {
	public ArrayList points;
	
	public Path()
	{
		
	}
	public void addPoint(int x, int y)
	{
		Point temp = new Point(x,y);
		points.add(temp);
	}
}
