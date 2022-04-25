package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Retry {

	public Rectangle yesButton = new Rectangle(Game.WIDTH / 2 - 10, 200, 100, 50);
	public Rectangle noButton = new Rectangle(Game.WIDTH + 80, 200, 100, 50);
	public Rectangle mainButton = new Rectangle(Game.WIDTH - 45, 300, 100, 50);
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("RETRY?", 270,100);
		g.drawString("YES", yesButton.x + 20, yesButton.y + 35);
		g.drawString("NO", noButton.x + 30, noButton.y + 35);
		g.drawString("MAIN", mainButton.x + 13, mainButton.y + 35);
		g2d.draw(yesButton);
		g2d.draw(noButton);
		g2d.draw(mainButton);
	}
	
}
