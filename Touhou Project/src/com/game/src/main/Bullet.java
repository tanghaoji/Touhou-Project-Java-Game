package com.game.src.main;

import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Bullet extends GameObject {
	
	protected Textures tex;
	protected Controller c;
	protected Game game;
	
	protected int speed;
		
	public Bullet(double x, double y, Textures tex, Controller c, Game game){
		super(x, y);
		this.tex = tex;
		this.c = c;
		this.game = game;
	}
	
	public void tick(){
	}
	
	public void render(Graphics g){
	} 
	
	public double getY(){
		return y;
	}
	
	public double getX(){
		return x;
	}
	
}
