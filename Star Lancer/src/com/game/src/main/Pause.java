package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Pause {
	
	public void render(Graphics g) {
		@SuppressWarnings("unused")
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("PAUSED", Game.WIDTH - 95, Game.HEIGHT);
	}
}
