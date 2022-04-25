package com.game.src.main.libs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	private int speed;
	private int frames;
	private int index = 0;
	private int count = 0;
	private boolean aniFinish;
	private BufferedImage currentImg;
	private BufferedImage[] imgs;
		
	public Animation(int speed, boolean finish, BufferedImage[] img) {
		aniFinish = finish;
		this.speed = speed;
		this.frames = img.length;
		this.imgs = new BufferedImage[img.length];
		this.imgs = img;
	}
		
		public void runAnimation() {
			index++;
			if(index > speed) {
				index = 0;
				nextFrame();
			}
		}
		
		public void nextFrame() {
			this.currentImg = this.imgs[count];
			count++;
			
			if(count >= frames - 1) {
				aniFinish = true;
				count = 0;
			}
		}
		
		public void drawAnimation(Graphics g, double x, double y, int offset) {
			g.drawImage(currentImg, (int)x - offset, (int)y, null);
		}
		
		public void setCount(int count) {
			this.count = count;
		}
		public int getCount() {
			return count;
		}
		public int getSpeed() {
			return speed;
		}
		public void setSpeed(int speed) {
			this.speed = speed;
		}
	
		public boolean getAniFinish() {
			return aniFinish;
		}
		
		public boolean setAniFinish(boolean finish) {
			return this.aniFinish = finish;
		}
}
