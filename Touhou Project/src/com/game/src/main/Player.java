package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.classes.EntityA;

public class Player extends GameObject implements EntityA {
	
	private double velX = 0; //velocity x
	private double velY = 0; //velocity y
		
	private Textures tex;
	
	public Player(double x, double y, Textures tex){
		super(x, y);
		this.tex = tex;
	}
	
	public void tick(){
		x+=velX;
		y+=velY;
		
		//set borders
		if (x <= 0)
			x = 0;
		if (x >= 640 - 16)
			x = 640 - 16;
		if (y <= 0)
			y = 0;
		if (y >= 480 - 32)
			y = 480 - 32;
	} //how the player moves
	
	public void render(Graphics g){
		g.drawImage(tex.player, (int)x, (int)y,null);
	} //display the player
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public void setVelX(double velX){
		this.velX=velX;
	}
	public void setVelY(double velY){
		this.velY=velY;
	}
	
}
