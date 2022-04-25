package com.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.classes.EntityC;
import com.game.src.main.libs.BufferedImageLoader;
import com.game.src.main.sound.Sound;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7228164263489589306L;

	private boolean running = false;
	private Thread thread;
	
	// A buffer image loads the image before it gets projected
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	//private boolean is_shooting = false;
	private boolean is_paused = false;
	private boolean start_music = true;
	private boolean stop_music = false;
	static boolean mReset = false;
	
	private int enemy_count = 1;
	private int enemy_killed = 0;
	private static int score = 0;
	
	private static int seconds = 0;
	private static int minutes = 0;
	private static int gameClockCount = 0;
	private static int enemyTimer = 0;
	private static int shotTimer = 3;

	private Player p;
	private Controller c;
	private Textures tex;
	private Menu menu;
	private Retry retry;
	private Pause pause;
	private Sound music = new Sound();
	private Sound sound = new Sound();
	
	public static String menuTracker = "main";
	
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
	public LinkedList<EntityC> ec;
	
	public static int HEALTH = 100;
	public static boolean retryYN = false;
	
	enum STATE{
		MENU,
		GAME,
		DEAD,
		PAUSE
	};
	
	public static STATE state = STATE.MENU;
	
	public void init() {
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("/spriteSheetRPGgame2.png");
			background = loader.loadImage("/background1.png");
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		tempScreen = new BufferedImage(WIDTH * SCALE, HEIGHT * SCALE, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
		setFullScreen();
		
		this.addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput());
		
		tex = new Textures(this);

		c = new Controller(tex, this);
		p = new Player(WIDTH, HEIGHT * SCALE, tex, this, c);

		menu = new Menu();
		retry = new Retry();
		pause = new Pause();		

		
		ea = c.getEntityA();
		eb = c.getEntityB();
		ec = c.getEntityC();
		
		c.createEnemy(enemy_count);
	}
	
	public void setFullScreen() {
		
		// Get local screen device
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(frame);
		
		// Get full screen width and height
		screenWidth2 = frame.getWidth();
		screenHeight2 = frame.getHeight();
	}
	
	// Starts the thread. Synchronized deals with threads
	private synchronized void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		// Only called if there is a thread going on
		if(!running) 
			return;
			
			running = false;
			try {
			thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(1);
		
	}
	
	@Override
	// The game loop. The heart of the game.
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {
			// Limits ticks to 60 per second
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			drawToScreen();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, FPS " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	// For everything in the game that updates
	private void tick() {
		if(state == STATE.GAME) {
			
			if(start_music) {
				playMusic(0);
				start_music = false;
			}			
			screenY += 2;
			screenY2 += 2;
			p.tick();
			c.tick();
			
			gameClockCount += 1;

			if (gameClockCount >= 60) {
				seconds += 1;
				enemyTimer += 1;
				c.createEnemy(enemyTimer);
				gameClockCount = 0;
			}
			if (seconds >= 60) {
				minutes += 1;
				seconds = 0;
			}
		}
		
		if (stop_music) {
			stopMusic();
		}

		// ****************
		if(HEALTH <= 0) {
			p.tick();
			c.tick();
			state = STATE.DEAD;
			stop_music = true;
			p.setVelX(0);
			p.setVelY(0);
			if(retryYN) {
				playerRetry();
			}
			if(mReset) {
				mainReset();
			}
		}
		
		if (state == STATE.PAUSE) {
			return;
		}
	}
	
	public void render() {
		// Buffer strategy handles all the buffering behind the scenes
				BufferStrategy bs = this.getBufferStrategy(); // "this" accesses Canvas
				
				if(bs == null) {
					// Triple buffering (loads two images back, increasing speed over time
					// If computer is fast enough, loads a second buffer
					createBufferStrategy(3);
					return;
				}
				

				
				// Creates a graphics context for drawing buffers
				/////////////////
				
				g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
				
				if (screenY >= HEIGHT * SCALE)
					screenY = 0;
				if (screenY2 >= 0)
					screenY2 = -HEIGHT * SCALE;
				g2.drawImage(background, 0,(int) screenY2, this);
				g2.drawImage(background, 0, (int) screenY, this);
				
				if(state == STATE.GAME) {
					p.render(g2);
					c.render(g2);
					
					g2.setColor(Color.gray);
					g2.fillRect(10,18,100,10);
					
					g2.setColor(Color.green);
					
					if (HEALTH > 0) {
						g2.fillRect(10,18,HEALTH,10);
					}
					
					g2.setColor(Color.white);
					g2.drawRect(10,18,100,10);
					
					String scoreDisplay = String.valueOf(score);
					Font fnt0 = new Font("arial", Font.BOLD, 15);
					g2.setFont(fnt0);
					g2.setColor(Color.white);
					g2.drawString("SCORE: " + scoreDisplay, WIDTH * SCALE - 150, 15);
					g2.drawString("HEALTH", 10, 15);
					
					String clock = displayTime();
					g2.drawString("TIME  " + clock, WIDTH - 50, 15);
				}
				
				
				else if (state == STATE.DEAD) {
					retry.render(g2);
					
					p.render(g2);
					c.render(g2);
					
					g2.setColor(Color.gray);
					g2.fillRect(10,18,100,10);
					
					g2.setColor(Color.green);
					
					if (HEALTH > 0) {
						g2.fillRect(10,18,HEALTH,10);
					}
					
					g2.setColor(Color.white);
					g2.drawRect(10,18,100,10);
					
					String scoreDisplay = String.valueOf(score);
					Font fnt0 = new Font("arial", Font.BOLD, 15);
					g2.setFont(fnt0);
					g2.setColor(Color.white);
					g2.drawString("SCORE: " + scoreDisplay, WIDTH * SCALE - 150, 15);
					g2.drawString("HEALTH", 10, 15);
					
					String clock = displayTime();
					g2.drawString("TIME  " + clock, WIDTH - 50, 15);
				}
				
				else if(state == STATE.MENU) {
					menu.render(g2);
				}
				
				else if (state == STATE.PAUSE) {
					pause.render(g2);
					
					p.render(g2);
					c.render(g2);
					
					g2.setColor(Color.gray);
					g2.fillRect(10,18,100,10);
					
					g2.setColor(Color.green);
					
					if (HEALTH > 0) {
						g2.fillRect(10,18,HEALTH,10);
					}
					
					g2.setColor(Color.white);
					g2.drawRect(10,18,100,10);
					
					String scoreDisplay = String.valueOf(score);
					Font fnt0 = new Font("arial", Font.BOLD, 15);
					g2.setFont(fnt0);
					g2.setColor(Color.white);
					g2.drawString("SCORE: " + scoreDisplay, WIDTH * SCALE - 150, 15);
					g2.drawString("HEALTH", 10, 15);
					
					String clock = displayTime();
					g2.drawString("TIME  " + clock, WIDTH - 50, 15);
				}
				/////////////////
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(state == STATE.GAME) {
			
			if (key == KeyEvent.VK_SPACE && shotTimer <= 4) {
				shotTimer += 1;
			}
			else if(key == KeyEvent.VK_SPACE && shotTimer > 4) {
				playSE(1);
				c.addEntity(new Bullet(p.getX(), p.getY(), tex, this, c));
				shotTimer = 0;
			}
			else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				p.setVelX(5);
			} else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				p.setVelX(-5);
			} else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				p.setVelY(5);
			} else if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				p.setVelY(-5);
				
			}
		}
	}
	
	public void keyRelease(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(state == STATE.PAUSE) {
			if(key == KeyEvent.VK_P && is_paused) {
				resumeMusic();
				is_paused = false;
				state = STATE.GAME;
				return;
			}
		}
		
		if(state == STATE.GAME) {
			if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				p.setVelX(0);
			} else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				p.setVelX(0);
			} else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				p.setVelY(0);
			} else if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				p.setVelY(0);
			} else if(key == KeyEvent.VK_SPACE) {
				playSE(1);
				c.addEntity(new Bullet(p.getX(), p.getY(), tex, this, c));
			} else if (key == KeyEvent.VK_P && !is_paused) {
				pauseMusic();
				state = STATE.PAUSE;
				is_paused = true;
			} else if (key == KeyEvent.VK_T) {
				if(stop_music) {
					resumeMusic();
					stop_music = false;
					return;
				}
				if(!stop_music){
					pauseMusic();
					stop_music = true;
					return;
				}

			}
		}
	}
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public static double screenY = 0;
	public static double screenY2 = -HEIGHT * SCALE;
	public final String TITLE = "Star Lancer";
	//For full screen
	static int screenWidth2 = WIDTH * SCALE;
	static int screenHeight2 = HEIGHT * SCALE;
	BufferedImage tempScreen;
	Graphics2D g2;
	public static JFrame frame;
	public static BufferStrategy bs;

	
	public static void main(String args[]) {
		Game game = new Game();
		
		// Constructs a dimension and initializes it to a specified width and height
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
		
		game.start();
	}
	
	public void playerRetry() {
		valueReset();
		clearEntities();
		state = STATE.GAME;
		p.dead = false;
	}
	
	public void mainReset() {
		valueReset();
		clearEntities();
		state = STATE.MENU;
		p.dead = false;
		mReset = false;
	}
	
	public String displayTime() {
		return String.format("%02d:%02d", minutes, seconds);
	}
	
	public void valueReset() {
		seconds = 0;
		minutes = 0;
		gameClockCount = 0;
		score = 0;
		enemyTimer = 0;
		HEALTH = 100;
		enemy_killed = 0;
		enemy_count = 1;
		p.x = WIDTH;
		p.y = HEIGHT * SCALE;
		start_music = true;
		stop_music = false;
		is_paused = false;
		retryYN = false;
	}
	
	public void clearEntities() {
		while (ea.size() > 0 || eb.size() > 0 || ec.size() > 0) {
			for(int i = 0; i < ea.size(); i++) {
				EntityA tempEnt = ea.get(i);
				c.removeEntity(tempEnt);
			}
			for(int i = 0; i < eb.size(); i++) {
				EntityB tempEnt = eb.get(i);
				c.removeEntity(tempEnt);
			}
			for(int i = 0; i < ec.size(); i++) {
				EntityC tempEnt = ec.get(i);
				c.removeEntity(tempEnt);
			}
		}
	}
	
	public void drawToScreen() {
	
		BufferStrategy bs = this.getBufferStrategy(); // "this" accesses Canvas
		
		if(bs == null) {
			// Triple buffering (loads two images back, increasing speed over time
			// If computer is fast enough, loads a second buffer
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		bs.show();
		g.dispose();
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSE(int i) {
		
		sound.setFile(i);
		sound.play();
	}
	
	public void pauseMusic() {
		music.pause();
	}
	
	public void resumeMusic() {
		music.resume();
	}

	public BufferedImage getSpriteSheet() {
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
	
	public double getPlayerPositionX() {
		return p.x;
	}
	
	public double getPlayerPositionY() {
		return p.y;
	}
	
	public static void addScore(int points) {
		score += points;
	}
	
	public int getEnemyTimer() {
		return enemyTimer;
	}

	public double getScreenY() {
		return screenY;
	}

	public static void setScreenY(double screenY) {
		Game.screenY = screenY;
	}

}
