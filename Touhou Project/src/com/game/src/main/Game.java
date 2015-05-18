package com.game.src.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public final String TITLE = "Touhou Project ~ Imperishable Night";
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private boolean is_shooting = false;
	
	private int enemy_count = 2; //initial quantity of enemies
	private int enemy_killed = 0;
	
	private Player p;
	private Controller c;
	private Textures tex;
	private Menu menu;
	
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
	
	public static enum STATE{
		MENU,
		GAME
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
		
		this.addKeyListener(new KeyInput(this)); //call the KeyInput class
		this.addMouseListener(new MouseInput()); //call the MouseInput class
		
		tex = new Textures(this); //load textures
		
		p = new Player(WIDTH * SCALE / 2, HEIGHT * SCALE / 6 * 5, tex); //add player and set coordinates
		c = new Controller(tex, this);
		menu = new Menu();
		
		ea = c.getEntityA();
		eb = c.getEntityB();
		
		c.createEnemy(enemy_count); //create enemies
	}
	
	private synchronized void start(){ //dealing with thread
		if (running)
			return; //get out of this method if the game is already running
		
		running = true; //make the game running
		thread = new Thread(this); //initializing the Thread
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
				System.out.println(updates + "Ticks, Fps" + frames);
				updates = 0; //reset it to 0
				frames = 0; //reset it to 0
			} //display the game updates and renders per second
			
		}
		stop();
	}
	
	private void tick(){
			//everything that the game updates
		if(State == STATE.GAME){
			p.tick();
			c.tick();
		}
		
		if(enemy_killed >= enemy_count){
			enemy_count += 2;
			enemy_killed = 0;
			c.createEnemy(enemy_count); //continue adding enemies after killed
		}
	}
	
	private void render(){
			//everything that the game renders
		BufferStrategy bs = this.getBufferStrategy(); //initializing the buffer strategy
		if (bs  == null){
			createBufferStrategy(3);  //how many images loading up, cost CPU usage
			return;
		} //creating buffer strategy
		
		Graphics g = bs.getDrawGraphics(); //apply buffer strategy to graphics
		/////////////////////////////
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this); //black background
		
		g.drawImage(background, 0, 0, null);
		
		if(State == STATE.GAME){
			p.render(g);
			c.render(g);
		}else if(State == STATE.MENU){
			//MENU
			menu.render(g);
		}
		
		/////////////////////////////
		g.dispose();
		bs.show(); //show the buffer strategy
		
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(State == STATE.GAME){ 
		if(key == KeyEvent.VK_RIGHT){
			p.setVelX(5);
		} else if(key == KeyEvent.VK_LEFT){
			p.setVelX(-5);
		} else if(key == KeyEvent.VK_DOWN){
			p.setVelY(5);
		} else if(key == KeyEvent.VK_UP){
			p.setVelY(-5);
		} else if(key == KeyEvent.VK_Z || key == KeyEvent.VK_X && !is_shooting){
			is_shooting = true;
			c.addEntity(new Bullet(p.getX(), p.getY(), tex, this)); //add bullets
		}
		}
		
		
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			p.setVelX(0);
		} else if(key == KeyEvent.VK_LEFT){
			p.setVelX(0);
		} else if(key == KeyEvent.VK_DOWN){
			p.setVelY(0);
		} else if(key == KeyEvent.VK_UP){
			p.setVelY(0);
		} else if(key == KeyEvent.VK_Z || key == KeyEvent.VK_X){
			is_shooting = false; //you have to release the key every time
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE)); //set dimension
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		JFrame frame = new JFrame(game.TITLE); //Initializing JFrame
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button works
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); //not to set the location
		frame.setVisible(true);
		
		game.start(); //call the start method
		
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
