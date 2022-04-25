package com.game.src.main.sound;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	long clipTime = 0;
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sound/main_theme2.wav");
		soundURL[1] = getClass().getResource("/sound/player_bullet3.wav");
		soundURL[2] = getClass().getResource("/sound/enemy_bullet1.wav");
		soundURL[3] = getClass().getResource("/sound/explosion1.wav");
		soundURL[4] = getClass().getResource("/sound/eBulletHit1.wav");
		soundURL[5] = getClass().getResource("/sound/collision1.wav");
	}
	
	public void setFile(int i) {
		
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		}catch(Exception e) {
		}
	}
	public void play() {
		
		clip.start();
	}
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
		
	public void stop() {
		
		clip.stop();
	}
	
	public void pause() {
		clipTime = clip.getMicrosecondPosition();
		
		clip.stop();
	}
	
	public void resume() {
		clip.setMicrosecondPosition(clipTime);
		
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
