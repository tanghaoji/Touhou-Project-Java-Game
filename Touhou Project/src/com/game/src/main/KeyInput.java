package com.game.src.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	Game game;
	
	public KeyInput(Game game){
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e){
		game.keyPressed(e);
	} //when press the key, it goes to the method
	
	public void keyReleased(KeyEvent e){
		game.keyReleased(e);
	}
}
