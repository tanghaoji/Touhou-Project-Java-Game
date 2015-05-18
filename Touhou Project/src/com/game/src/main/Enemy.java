package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.main.classes.EntityB;

public class Enemy extends GameObject implements EntityB {
	
	Random r = new Random();
	
	private Textures tex;
	private Game game;
	private Controller c;
	
	public Enemy(double x, double y, Textures tex, Controller c, Game game){
		super(x, y);
		this.tex = tex;
		this.c = c;
		this.game = game;
	}
	
	public void tick(){
		y += (r.nextInt(3) + 2); //speed of enemy
		
		if(y > (Game.HEIGHT * Game.SCALE)){
			y = -10;
			x = r.nextInt(Game.WIDTH * Game.SCALE);
		}
		
		if(Physics.Collision(this, game.ea)){
			c.removeEntity(this);
			game.setEnemy_killed(game.getEnemy_killed() + 1);
		}
	}
	
	public void render(Graphics g){
		g.drawImage(tex.enemy, (int)x, (int)y, null);
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	
	
}
