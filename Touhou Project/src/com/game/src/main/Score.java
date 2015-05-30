package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class Score {
	private int currentScore = 0;
	private int highestScore = 0;
	private int displayCurrent = 0;
	private int displayHighest = 0;
	
	public String customFormat(String pattern, int value ) {
	      DecimalFormat myFormatter = new DecimalFormat(pattern);
	      String output = myFormatter.format(value);
	      return output;
	}
	
	public void render(Graphics g) {
		Font fnt1 = new Font("Arial Bold", Font.PLAIN, 25);
		g.setFont(fnt1);
		g.setColor(Color.green);
		g.drawString("Score:    " + customFormat("0000",displayCurrent), 480, 20);
		g.drawString("Highest: " + customFormat("0000",displayHighest), 480, 50);
	}
	
	public void tick(){
		displayCurrent = currentScore;
		if (currentScore > highestScore) {
			highestScore = currentScore;
			displayHighest = highestScore;
		}
	}
	
	public void addScore(int i) {
		currentScore += i;
	}
	
	// set the current score to zero
	public void setZero() {
		currentScore = 0;
	}
}
