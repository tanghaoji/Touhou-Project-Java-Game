package com.game.src.main;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		/**
		  	public Rectangle startButton = new Rectangle(100, 180, 80, 40);
			public Rectangle exstartButton = new Rectangle(65, 250, 150, 40);
			public Rectangle quitButton = new Rectangle(105, 320, 75, 40);
		 */
		
		//Start button
		if(mx >= 100 && mx <= 180){
			if(my >= 180 && my <= 220){
				//pressed Start button
				Game.State = Game.STATE.GAME;
			}
		}
		
		//Quit button
		if(mx >= 105 && mx <= 180){
			if(my >= 320 && my <= 360){
				//pressed Quit button
				System.exit(1);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
