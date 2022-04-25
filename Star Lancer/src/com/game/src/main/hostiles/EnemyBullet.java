package com.game.src.main.hostiles;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.Controller;
import com.game.src.main.Game;
import com.game.src.main.GameObject;
import com.game.src.main.Textures;
import com.game.src.main.classes.EntityC;
import com.game.src.main.libs.Animation;

public class EnemyBullet extends GameObject implements EntityC {
	
	@SuppressWarnings("unused")
	private Textures tex;
	@SuppressWarnings("unused")
	private Game game;
	private Controller c;
	
	//x and y velocity
	private double vx;
	private double vy;
	
	Animation anim;
	
	//This bullet will travel in a straight line towards the player's position
	//This bullet can not be destroyed by the player
	public EnemyBullet(double x, double y, Textures tex, Game game, double velocityX, double velocityY, Controller c) {
		super(x,y);
		this.tex = tex;
		this.game = game;
		this.c = c;
		vx = velocityX;
		vy = velocityY;
		anim = new Animation(1, false, tex.enemyBullet);
	}
	
	public void tick() {
		y += vy;
		x += vx;
		
		anim.runAnimation();
		
		if(y > (Game.HEIGHT * Game.SCALE + 50) || y < -50 || x > (Game.WIDTH * Game.SCALE + 50) || x < -50) {
			c.removeEntity(this);
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public void render(Graphics g) {
		anim.drawAnimation(g, x, y, 0);
	}
	
	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}
}
