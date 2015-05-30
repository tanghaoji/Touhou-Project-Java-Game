package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class FailScreen {
	
	public Rectangle backButton = new Rectangle(210, 320, 250, 40);
	
	public void render(Graphics g){
		//Graphics2D g2d = (Graphics2D) g;
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("You Failed", 220, 200);
		
		Font fnt1 = new Font("Arial Bold", Font.ITALIC, 25);
		g.setFont(fnt1);
		g.drawString("Back to Menu (ESC)", backButton.x + 12, backButton.y + 27);
		//g2d.draw(backButton);
	}

}
