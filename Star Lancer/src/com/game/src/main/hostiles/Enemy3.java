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

public class Enemy3 extends GameObject implements EntityB {

	// This enemy stays at the top of the screen and shoots at the player's current position
	private Textures tex;
	Random r = new Random();
	private int speed = 2;
	private Game game;
	private Controller c;
	private double playerX;
	private double playerY;
	private double adjacent;
	private double adjacentNorm;
	private double opposite;
	private double oppositeNorm;
	private double hypotenuse;
	private int points = 3;
	private int shotTimer = 0;
	private int speedFactor = 2;
	private boolean dead = false;
	
	Animation anim;
	Explosion explosion;
	
	public Enemy3(double x, double y, Textures tex, Controller c, Game game) {
		super(x,y);
		this.tex = tex;
		this.c = c;
		this.game = game;
		
		explosion = new Explosion(this.x, this.y, tex, c, game);
		anim = new Animation(3, false, tex.enemy3);
	}
	
	public void tick() {
		
		if (y < 50) {
			y += speed;
		}
		if (y >= 50) {
			y = 50;
		}
		shotTimer += 1;
		if (shotTimer == 60) {
			//Shoot at player
			//Determine the distance between Enemy3 and Player (hypotenuse)
			playerX = game.getPlayerPositionX();
			playerY = game.getPlayerPositionY();
			adjacent = playerX - x;
			opposite = playerY - y;
			hypotenuse = Math.sqrt(adjacent * adjacent + opposite * opposite);
			//Normalize x and y vectors
			adjacentNorm = adjacent / hypotenuse;
			oppositeNorm = opposite / hypotenuse;
			//Removed for now: too much noise
			//game.playSE(2); 
			c.addEntity(new EnemyBullet(x, y, tex, game, adjacentNorm * speedFactor, oppositeNorm * speedFactor, c));
			shotTimer = 0;
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
