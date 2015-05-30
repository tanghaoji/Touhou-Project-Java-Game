package com.game.src.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Enemy extends GameObject implements EntityB {
	
	Random r = new Random();
	
	private Textures tex;
	private Game game;
	private Controller c;
	private int speed = (r.nextInt(2) + 1);
	private int fireRate = 0;
	private final int FIRE_RATE; // the time b/w the bullets, higher is slower
	
	public Enemy(double x, double y, Textures tex, Controller c, Game game, int fr){
		super(x, y);
		this.tex = tex;
		this.c = c;
		this.game = game;
		this.FIRE_RATE = fr;
		fireRate = FIRE_RATE;
	}
	
	public void tick(){
		if (fireRate > 0) fireRate--;
		y += speed;
		
		if (y > (Game.HEIGHT * Game.SCALE)) {
			y = -10;
			x = r.nextInt(Game.WIDTH * Game.SCALE - 20);
		}
		
		for (int i = 0; i < game.ea.size(); i++) {
			EntityA tempEnt = game.ea.get(i);
			
			if(Physics.Collision(this, tempEnt)){
				c.removeEntity(tempEnt);
				c.removeEntity(this);
				game.setEnemy_killed(game.getEnemy_killed() + 1);
				game.score.addScore(5);
			}
		}
		
		if (Physics.Collision(this, game.p)) {
			Game.State = Game.STATE.FAIL;
		}
		
		if (fireRate <= 0) {
			double dx = x - game.p.getX();
			double dy = y - game.p.getY();
			double dir = Math.atan2(dy, dx); // aim to the player
			c.addEntity(new EnemyBullet(x, y, tex, c, dir, game)); //add bullets
			fireRate = FIRE_RATE;
		}
		
	}
	
	public void render(Graphics g){
		g.drawImage(tex.enemy, (int)x, (int)y, null);
		//Graphics2D g2d = (Graphics2D) g;
		//g2d.draw(this.getBounds());
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x + 1, (int)y, 29, 31);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	
	
}
