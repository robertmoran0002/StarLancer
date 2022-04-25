package com.game.src.main;

import java.awt.image.BufferedImage;

import com.game.src.main.libs.SpriteSheet;

public class Textures {
	
	public BufferedImage[] player = new BufferedImage[3];
	public BufferedImage[] missile = new BufferedImage[4];
	public BufferedImage[] enemy = new BufferedImage[7];
	public BufferedImage[] enemy2 = new BufferedImage[4];
	public BufferedImage[] enemy3 = new BufferedImage[14];
	public BufferedImage[] enemyBullet = new BufferedImage[10];
	public BufferedImage[] explosion = new BufferedImage[10];

	private SpriteSheet ss;
	
	public Textures(Game game) {
		ss = new SpriteSheet(game.getSpriteSheet());
		
		getTextures();
	}
	
	private void getTextures() {
		
		grabTextures();

		enemy2[0] = ss.grabImage(4, 1, 32, 32);
		enemy2[1] = ss.grabImage(4, 4, 32, 32);
		enemy2[2] = ss.grabImage(4, 5, 32, 32);
		enemy2[3] = ss.grabImage(4, 6, 32, 32);
		
		enemy3[0] = ss.grabImage(5, 1, 32, 32);
		enemy3[1] = ss.grabImage(5, 2, 32, 32);
		enemy3[2] = ss.grabImage(5, 3, 32, 32);
		enemy3[3] = ss.grabImage(5, 4, 32, 32);
		enemy3[4] = ss.grabImage(5, 5, 32, 32);
		enemy3[5] = ss.grabImage(5, 6, 32, 32);
		enemy3[6] = ss.grabImage(5, 7, 32, 32);
		enemy3[7] = ss.grabImage(5, 8, 32, 32);
		enemy3[8] = ss.grabImage(5, 7, 32, 32);
		enemy3[9] = ss.grabImage(5, 6, 32, 32);
		enemy3[10] = ss.grabImage(5, 5, 32, 32);
		enemy3[11] = ss.grabImage(5, 4, 32, 32);
		enemy3[12] = ss.grabImage(5, 3, 32, 32);
		enemy3[13] = ss.grabImage(5, 2, 32, 32);
		
		enemyBullet[0] = ss.grabImage(6, 1, 32, 32);
		enemyBullet[1] = ss.grabImage(6, 2, 32, 32);
		enemyBullet[2] = ss.grabImage(6, 3, 32, 32);
		enemyBullet[3] = ss.grabImage(6, 4, 32, 32);
		enemyBullet[4] = ss.grabImage(6, 5, 32, 32);
		enemyBullet[5] = ss.grabImage(6, 6, 32, 32);
		enemyBullet[6] = ss.grabImage(6, 5, 32, 32);
		enemyBullet[7] = ss.grabImage(6, 4, 32, 32);
		enemyBullet[8] = ss.grabImage(6, 3, 32, 32);
		enemyBullet[9] = ss.grabImage(6, 2, 32, 32);
	}
	
	private void grabTextures() {
		
		for (int i = 0; i < player.length; i++) {
			player[i] = ss.grabImage(1, i+1, 32, 32);
		}
		
		for (int i = 0; i < missile.length; i++) {
			missile[i] = ss.grabImage(2, i+1, 32, 32);
		}
		
		for (int i = 0; i < enemy.length; i++) {
			enemy[i] = ss.grabImage(3, i+1, 32, 32);
		}
		
		for (int i = 0; i < explosion.length; i++) {
			explosion[i] = ss.grabImage(7, i+1, 32, 32);
		}
	}
	
}
