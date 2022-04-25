package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.classes.EntityA;
import com.game.src.main.libs.Animation;

public class Bullet extends GameObject implements EntityA {
	
	@SuppressWarnings("unused")
	private Textures tex;
	@SuppressWarnings("unused")
	private Game game;
	private Controller c;
	
	Animation anim;
	
	public Bullet(double x, double y, Textures tex, Game game, Controller c) {
		super(x,y);
		this.tex = tex;
		this.game = game;
		this.c = c;
		
		anim = new Animation(1, false, tex.missile);
	}
	
	public void tick() {
		y -= 5;
		
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
