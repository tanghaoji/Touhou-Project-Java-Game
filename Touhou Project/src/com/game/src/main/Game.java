package com.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.classes.Keyboard;


public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public final String TITLE = "Touhou Project ~ Imperishable Night";
	
	private JFrame frame; // window
	private Keyboard key;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private int enemy_count = 0; //initial quantity of enemies
	private int enemy_killed = 0;
	private int enemy_bullet_speed = 1;
	private int enemy_firerate = 45;
	
	public Player p;
	private Controller c;
	private Textures tex;
	private Menu menu;
	private FailScreen fs;
	public Score score;
	
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
	
	public static enum STATE{
		MENU,
		GAME,
		EXTRA,
		FAIL
	};
	public static STATE State = STATE.MENU;
	
	public void init(){ //initialize
		requestFocus(); //get focus to the screen
		BufferedImageLoader loader = new BufferedImageLoader();
		try{ //load buffered image
			spriteSheet = loader.loadImage("/sprite_sheet.png");
			background = loader.loadImage("/background.png");
		}catch(IOException e){
			e.printStackTrace(); //error report
		}
		
		
		key = new Keyboard();
		this.addKeyListener(key); //call the KeyInput class
		this.addMouseListener(new MouseInput()); //call the MouseInput class
		
		tex = new Textures(this); //load textures
		
		c = new Controller(tex, this);
		p = new Player(WIDTH * SCALE / 2, HEIGHT * SCALE / 6 * 5, tex, this, c, key); //add player and set coordinates
		menu = new Menu();
		fs = new FailScreen();
		score = new Score();
		
		ea = c.getEntityA();
		eb = c.getEntityB();
		
		Sound sound = new Sound();

	}
	
	private synchronized void start(){ //dealing with thread
		if (running)
			return; //get out of this method if the game is already running
		
		running = true; 
		thread = new Thread(this); 
		thread.start();
	}
	
	private synchronized void stop(){
		if (!running)
			return; //get out of this method if there is no thread
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
		
	}
	
	public void run(){
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;  //set FPS
		double ns = 1000000000 / amountOfTicks; //in nanoseconds per tick
		double delta = 0; //calculate the time pass
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis(); //in milliseconds
		
		while(running){
				//the game loop
			long now = System.nanoTime();
			delta += (now - lastTime) / ns; //in ticks
			lastTime = now;
			if (delta >= 1){
				tick(); //update the frame
				updates++; //calculate the updates
				delta--;
			} //limit to 60 times per second to update the frames
			render(); //display
			frames++; //calculate how many times it goes through this code
			
			if (System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				frame.setTitle(TITLE + "  |  " + updates + "ticks, " + frames + " fps");
				updates = 0; //reset it to 0
				frames = 0; //reset it to 0
			} //display the game updates and renders per second
			
		}
		stop();
	}
	
	private void tick(){
			//everything that the game updates
		key.tick();
		if (State == STATE.GAME) {
			c.tick();
			p.tick();
			if(enemy_killed >= enemy_count){
				enemy_count += 2;
				enemy_killed = 0;
				enemy_bullet_speed++;
				if (enemy_firerate > 0) enemy_firerate -= 5; // increasing difficulty
				c.createEnemy(enemy_count, enemy_bullet_speed, enemy_firerate); //continue adding enemies after killed
			}
			score.tick();
		}
		
		if (key.esc) State = STATE.MENU;
		if (key.enter && State == STATE.MENU) State = STATE.GAME;
	}
	
	private void render(){
			//everything that the game renders
		BufferStrategy bs = this.getBufferStrategy();
		if (bs  == null){
			createBufferStrategy(3);  //how many images loading up, cost CPU usage
			return;
		} //creating buffer strategy
		
		Graphics g = bs.getDrawGraphics(); //apply buffer strategy to graphics
		/////////////////////////////
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this); //black background
		
		g.drawImage(background, 0, 0, null);
		
		if (State == STATE.GAME){
			c.render(g);
			p.render(g);
			score.render(g);
		} else if (State == STATE.MENU) {
			menu.render(g);
			// reset all
			for (int i = 0; i < ea.size(); i++) {
				c.removeEntity(ea.get(i));
			}
			for (int i = 0; i < eb.size(); i++) {
				c.removeEntity(eb.get(i));
			}
			enemy_killed = 0;
			enemy_count = 0;
			enemy_bullet_speed = 1;
			enemy_firerate = 45;
			p.setX(WIDTH * SCALE / 2);
			p.setY(HEIGHT * SCALE / 6 * 5);
			score.setZero();
		} else if (State == STATE.FAIL) {
			fs.render(g);
			score.render(g);
		} else if (State == STATE.EXTRA) {
			Font fnt1 = new Font("Arial Bold", Font.ITALIC, 25);
			g.setFont(fnt1);
			g.setColor(Color.gray);
			g.drawString("Boss has not awaken yet...", 200, 400); //Note: still under development of this mode
		}
		
		/////////////////////////////
		g.dispose();
		bs.show(); //show the buffer strategy
		
	}
	
	
	public static void main(String args[]){
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE)); //set dimension
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		game.frame = new JFrame(); 
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button works
		game.frame.setResizable(false);
		game.frame.setLocationRelativeTo(null); //not to set the location
		game.frame.setVisible(true);
		
		game.start();
	
		
	}
	
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}

	public int getEnemy_count() {
		return enemy_count;
	}

	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}

	public int getEnemy_killed() {
		return enemy_killed;
	}

	public void setEnemy_killed(int enemy_killed) {
		this.enemy_killed = enemy_killed;
	}
	


}
