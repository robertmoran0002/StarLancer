package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
	
	public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 50);
	public Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
	public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);
	public Rectangle backButton = new Rectangle(20, 20, 100, 50);

	public void render(Graphics g) {
		
		if (Game.menuTracker == "main") {
			Graphics2D g2d = (Graphics2D) g;
			
			Font fnt0 = new Font("arial", Font.BOLD, 50);
			g.setFont(fnt0);
			g.setColor(Color.white);
			g.drawString("Star Lancer", Game.WIDTH - 120, 100);
			
			Font fnt1 = new Font("arial", Font.BOLD, 30);
			g.setFont(fnt1);
			g.drawString("Play", playButton.x + 20, playButton.y + 35);
			g.drawString("Help", helpButton.x + 20, helpButton.y + 35);
			g.drawString("Quit", quitButton.x + 20, quitButton.y + 35);
			g2d.draw(playButton);
			g2d.draw(helpButton);
			g2d.draw(quitButton);
		}
		
		if (Game.menuTracker == "help") {
			Graphics2D g2d = (Graphics2D) g;
			
			Font fnt1 = new Font("arial", Font.BOLD, 20);
			g.setFont(fnt1);
			Font fnt0 = new Font("arial", Font.BOLD, 30);
			g.setFont(fnt0);
			g.setColor(Color.white);
			g.drawString("Objective:", Game.WIDTH - 75, 100);
			g.setFont(fnt1);
			g.drawString("Destroy everything in sight!", Game.WIDTH - 140, 150);
			g.setFont(fnt0);
			g.drawString("Controls:", Game.WIDTH - 75, Game.HEIGHT);
			g.setFont(fnt1);
			g.drawString("Movement: Arrow Keys OR WASD", 145, 280);
			g.drawString("Shoot: Space Bar", Game.WIDTH - 90, 315);
			g.drawString("Pause: P", Game.WIDTH - 48, 350);
			g.drawString("Music on/off: T", Game.WIDTH - 80, 383);
			g.setFont(fnt0);
			g.drawString("Back", backButton.x + 15, backButton.y + 35);
			g2d.draw(backButton);
		}
	}
}
