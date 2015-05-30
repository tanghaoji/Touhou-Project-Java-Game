package com.game.src.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.Keyboard;

public class Player extends GameObject implements EntityA {
	
	private final int SPEED = 5;
	private int cspeed = SPEED;
	private int fireRate = 0;
		
	private Textures tex;
	
	private Game game;
	private Controller c;
	private Keyboard input;
	
	public Player(double x, double y, Textures tex, Game game, Controller c, Keyboard input){
		super(x, y);
		this.tex = tex;
		this.game = game;
		this.c = c;
		this.input = input;
		fireRate = PlayerBullet.FIRE_RATE;
	}
	
	public void tick(){
		if (fireRate > 0) fireRate--;
		
		if (input.up) y -= cspeed;
		if (input.down) y += cspeed;
		if (input.left) x -= cspeed;
		if (input.right) x += cspeed;
		if (input.slow) cspeed = 2;
		if (!input.slow) cspeed = SPEED;
		if (input.shoot && fireRate <= 0) {
			c.addEntity(new PlayerBullet(x, y, tex, c, game)); //add bullets
			fireRate = PlayerBullet.FIRE_RATE;
		}
		
		//set borders
		if (x <= 0)
			x = 0;
		if (x >= 640 - 16)
			x = 640 - 16;
		if (y <= 0)
			y = 0;
		if (y >= 480 - 32)
			y = 480 - 32;
	}
	
	public void render(Graphics g){
		g.drawImage(tex.player, (int)x, (int)y,null);
		//Graphics2D g2d = (Graphics2D) g;
		//g2d.draw(this.getBounds());
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x + 7, (int)y + 2, 20, 26);
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

	
}
