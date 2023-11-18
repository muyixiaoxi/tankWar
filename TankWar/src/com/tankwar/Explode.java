package com.tankwar;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Explode extends GameObject{
	
	public Explode(String img,int x,int y,GamePanel gamePanel) {
		super(img,x,y,gamePanel);
	}
	@Override
	public void paintSelf(Graphics g) {
		g.drawImage(img,x-25,y-30,null);
	}

	@Override
	public Rectangle getRec() {
		// TODO Auto-generated method stub
		return null;
	}
}

