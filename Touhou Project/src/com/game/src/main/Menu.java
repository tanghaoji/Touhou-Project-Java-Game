package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
	
	public Rectangle startButton = new Rectangle(100, 180, 80, 40);
	public Rectangle exstartButton = new Rectangle(65, 250, 150, 40);
	public Rectangle quitButton = new Rectangle(105, 320, 75, 40);
	
	public void render(Graphics g){
		//Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("TOUHOU PROJECT", 100, 100);
		
		Font fnt1 = new Font("Arial Bold", Font.ITALIC, 25);
		g.setFont(fnt1);
		g.drawString("Start", startButton.x + 12, startButton.y + 27);
		//g2d.draw(startButton);
		g.drawString("Extra Start", exstartButton.x + 12, exstartButton.y + 27);
		//g2d.draw(exstartButton);
		g.drawString("Quit", quitButton.x + 12, quitButton.y + 27);
		//g2d.draw(quitButton);
	}

}
