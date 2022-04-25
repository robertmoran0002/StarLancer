package com.game.src.main.libs;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.Controller;
import com.game.src.main.Game;
import com.game.src.main.GameObject;
import com.game.src.main.Textures;
import com.game.src.main.classes.EntityB;

public class Explosion extends GameObject implements EntityB {

	@SuppressWarnings("unused")
	private Textures tex;
	@SuppressWarnings("unused")
	private Game game;
	private Controller c;
	
	Animation explosion;
	
	public Explosion(double x, double y, Textures tex, Controller c, Game game) {
		super(x,y);
		this.tex = tex;
		this.c = c;
		this.game = game;
		
		explosion = new Animation(2, false, tex.explosion);
	}
	
	public void tick() {
		
		if(!explosion.getAniFinish()) {
				explosion.runAnimation();
		}
		if(explosion.getAniFinish()) {
			explosion.setAniFinish(false);
			c.removeEntity(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		explosion.drawAnimation(g, x, y, 0);
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
