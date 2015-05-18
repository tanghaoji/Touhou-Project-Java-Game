package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.classes.EntityA;

public class Bullet extends GameObject implements EntityA {
	
	private Textures tex;
	private Game game;
		
	public Bullet(double x, double y, Textures tex, Game game){
		super(x, y);
		this.tex = tex;
		this.game = game;
	}
	
	public void tick(){
		y -= 10;  //speed of the bullet
		
		
		
	}
	
	public void render(Graphics g){
		g.drawImage(tex.bullet, (int) x, (int) y, null);
	} //display bullets
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 16, 16);
	}
	
	public double getY(){
		return y;
	}
	
	public double getX(){
		return x;
	}
	
}
