package com.game.src.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	private double widthFactorFS;
	private double heightFactorFS;
	private double screenWidth;
	private double screenHeight;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		setFactors();
		
		//Play Button
		
		if (Game.state == Game.STATE.MENU && Game.menuTracker == "main") {
			if(mx >= ((Game.WIDTH / 2 + 120) * widthFactorFS) && mx < ((Game.WIDTH / 2 + 220) * widthFactorFS) ){
				if(my >= (150 * heightFactorFS) && my <= (200 * heightFactorFS)) {
					//Pressed Play Button
					Game.state = Game.STATE.GAME;
				}
			}
			
			if(mx >= (Game.WIDTH / 2 + 120) * widthFactorFS && mx <( Game.WIDTH / 2 + 220) * widthFactorFS) {
				if(my >= 250 * heightFactorFS && my <= 300 * heightFactorFS)
					Game.menuTracker = "help";
			}
			
			//Quit Button
			if(mx >= (Game.WIDTH / 2 + 120) * widthFactorFS && mx < (Game.WIDTH / 2 + 220) * widthFactorFS) {
				if(my >= 350 * heightFactorFS && my <= 400 * heightFactorFS) {
					//Pressed Quit Button
					System.exit(1);
				}
			}
		}
		
		if (Game.state == Game.STATE.MENU && Game.menuTracker == "help") {
			if(mx >= 20 * widthFactorFS && mx < 120 * widthFactorFS) {
				if(my >= 20 * heightFactorFS && my < 70 * heightFactorFS) {
					Game.menuTracker = "main";
				}
			}
		}
		
		if (Game.state == Game.STATE.DEAD) {
			if(mx >= (Game.WIDTH / 2 - 10) * widthFactorFS && mx < (Game.WIDTH / 2 + 90) * widthFactorFS) {
				if(my >= 200 * heightFactorFS && my <= 250 * heightFactorFS) {
					Game.retryYN = true;
				}
			}
			if(mx >= (Game.WIDTH + 80) * widthFactorFS && mx <= (Game.WIDTH + 180) * widthFactorFS) {
				if(my >= 200 * heightFactorFS && my <= 250 * heightFactorFS) {
					System.exit(1);
				}
			}
			if(mx >= (Game.WIDTH - 45) * widthFactorFS && mx < (Game.WIDTH + 55) * widthFactorFS) {
				if(my >= 300 * heightFactorFS && my <= 350 * heightFactorFS) {
					Game.menuTracker = "main";
					Game.mReset = true;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setFactors() {
		screenWidth = Game.frame.getWidth();
		screenHeight = Game.frame.getHeight();
		
		widthFactorFS = (screenWidth/Game.WIDTH) / 2;
		heightFactorFS = (screenHeight/Game.WIDTH) / 1.45;
	}
}
