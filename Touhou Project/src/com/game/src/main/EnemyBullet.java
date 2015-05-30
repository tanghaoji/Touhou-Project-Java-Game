package com.game.src.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.game.src.main.classes.EntityB;

public class EnemyBullet extends Bullet implements EntityB {
	
	private double nx, ny;

	public EnemyBullet(double x, double y, Textures tex, Controller c, double dir, Game game) {
		super(x, y, tex, c, game);
		speed = 2;
		nx = speed * Math.cos(dir);
		ny = speed * Math.sin(dir);
	}
	
	public void tick(){
		x -= nx;
		y -= ny;  
		if (y < 0 || y > 480 || x < 0 || x > 640) {
			c.removeEntity(this);
			game.score.addScore(1);
		}
		
		if (Physics.Collision(this, game.p)) {
			Game.State = Game.STATE.FAIL;
		}
	}
	
	public void render(Graphics g){
		g.drawImage(tex.ebullet, (int) x, (int) y, null);
		//Graphics2D g2d = (Graphics2D) g;
		//g2d.draw(this.getBounds());
	} 
	
	public Rectangle getBounds(){
		return new Rectangle((int)x + 13, (int)y + 14, 5, 5);
	}

}
