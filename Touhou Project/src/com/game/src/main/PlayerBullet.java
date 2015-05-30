package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.classes.EntityA;

public class PlayerBullet extends Bullet implements EntityA {
	
	public static final int FIRE_RATE = 7; // the time b/w the bullets, higher is slower
	
	public PlayerBullet(double x, double y, Textures tex, Controller c, Game game) {
		super(x, y, tex, c, game);
		speed = 10;
	}		
	
	public void tick(){
		y -= speed;  
		if (y < 0 || y > 480) c.removeEntity(this);
	}
	
	public void render(Graphics g){
		g.drawImage(tex.bullet, (int) x, (int) y, null);
		//Graphics2D g2d = (Graphics2D) g;
		//g2d.draw(this.getBounds());
	} 
	
	public Rectangle getBounds(){
		return new Rectangle((int)x + 10, (int)y + 12, 10, 8);
	}
	

}
