package com.game.src.main.hostiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.main.Controller;
import com.game.src.main.Game;
import com.game.src.main.GameObject;
import com.game.src.main.Physics;
import com.game.src.main.Textures;
import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.libs.Animation;
import com.game.src.main.libs.Explosion;

public class Enemy2 extends GameObject implements EntityB {
	
	//This enemy constantly moves towards the player's current position
	@SuppressWarnings("unused")
	private Textures tex;
	Random r = new Random();
	private int speed = 2;
	private Game game;
	private Controller c;
	private double playerX;
	private double playerY;
	private int points = 2;
	private boolean dead = false;
	
	Animation anim;
	Explosion explosion;
	
	public Enemy2(double x, double y, Textures tex, Controller c, Game game) {
		super(x,y);
		this.tex = tex;
		this.c = c;
		this.game = game;
		
		explosion = new Explosion(this.x, this.y, tex, c, game);
		anim = new Animation(1, false, tex.enemy2);
	}
	
	public void tick() {

		trackPlayer();
			
		if (y > (Game.HEIGHT * Game.SCALE)) {
			speed = r.nextInt(3) + 1;
			y = -10;
			x = r.nextInt((Game.WIDTH - 50) * Game.SCALE);
		}
		
		for(int i = 0; i < game.ea.size(); i++) {
			EntityA tempEnt = game.ea.get(i);
		
			if(Physics.Collision(this, tempEnt)) {
				explosion.x = this.x;
				explosion.y = this.y;
				c.addEntity(explosion);
				c.removeEntity(tempEnt);
				c.removeEntity(this);
				game.playSE(3);
				game.setEnemy_killed(game.getEnemy_killed() + 1);
				Game.addScore(points);
				dead = true;
			}

		}
		if(dead) {
			explosion.tick();
		}
		
		anim.runAnimation();
	}
	
	public void trackPlayer() { //Tracks player's position and adjusts enemy pursuit to follow player
		playerX = game.getPlayerPositionX();
		playerY = game.getPlayerPositionY();
		
		if (y < playerY) {
			y += speed;
			
			if (x > playerX) {
				x -= 1;
			}
			if (x <= playerX) {
				x += 1;
			}
		}

		if (y > playerY) {
			y -= 1;
			
			if (x > playerX) {
				x -= 1;
			}
			if (x <= playerX) {
				x += 1;
			}
		}
		
		if (y == playerY) {
			y += 0;
			
			if (x > playerX) {
				x -= 1;
			}
			if (x <= playerX) {
				x += 1;
			}
		}
	}
	
	public void render(Graphics g) {
		anim.drawAnimation(g, x, y, 0);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
}
