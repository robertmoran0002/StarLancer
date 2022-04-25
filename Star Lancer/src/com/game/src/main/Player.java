package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.Game.STATE;
import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.classes.EntityC;
import com.game.src.main.libs.Animation;
import com.game.src.main.libs.Explosion;

public class Player extends GameObject implements EntityA {
	
	private double velX = 0;
	private double velY = 0;
	boolean dead = false;
	
	Game game;
	Controller c;
	Animation anim;
	Explosion explosion;
	
	@SuppressWarnings("unused")
	private Textures tex;
	
	public Player(double x, double y, Textures tex, Game game, Controller c) {
		// Super refers to super class, which is the GameObject and that refers to the constructor
		super(x,y);
		this.tex = tex;
		this.game = game;
		this.c = c;
		
		explosion = new Explosion(this.x, this.y, tex, c, game);
		anim = new Animation(2, false, tex.player);

	}
	
	public void tick() {
		x+=velX;
		y+=velY;
		
		if (x <= 0)
			x = 0;
		if(x >= Game.WIDTH * Game.SCALE - 32)
			x = Game.WIDTH * Game.SCALE - 32;
		if (y <= 0)
			y = 0;
		if (y >= Game.HEIGHT * Game.SCALE - 50)
			y = Game.HEIGHT * Game.SCALE - 50;
		
		for(int i = 0; i < game.eb.size(); i++) {
			EntityB tempEnt = game.eb.get(i);
			
			if (Game.state == STATE.GAME) {
				if(Physics.Collision(this, tempEnt)) {
					c.removeEntity(tempEnt);
					Game.HEALTH -= 10;
					game.playSE(5);
					game.setEnemy_killed(game.getEnemy_killed() + 1);
					if (Game.HEALTH <= 0) {
						Game.HEALTH = 0;
						explosion.x = this.x;
						explosion.y = this.y;
						c.addEntity(explosion);
						game.playSE(3);
						dead = true;
					}
				}
			}
		}
		
		for(int i = 0; i < game.ec.size(); i++) {
			EntityC tempEnt = game.ec.get(i);
			
			if (Game.state == STATE.GAME) {
				if(Physics.Collision(this, tempEnt)) {
					c.removeEntity(tempEnt);
					game.playSE(4);
					Game.HEALTH -= 5;
					if (Game.HEALTH <= 0) {
						Game.HEALTH = 0;
						explosion.x = this.x;
						explosion.y = this.y;
						c.addEntity(explosion);
						game.playSE(3);
						dead = true;
					}
				}
			}
		}
		
		if(dead) {
			explosion.tick();
		}
		if(!dead) {
			anim.runAnimation();
		}
	}
	
	public void render(Graphics g) {
		if(!dead)
			anim.drawAnimation(g, x, y, 0);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x +11, (int)y + 11, 10, 10);
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}
}
