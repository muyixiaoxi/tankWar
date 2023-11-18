package com.tankwar;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Music{
       private static Clip start;
       private static Clip move;
       private static Clip attack;
       private static Clip explode;
       private static Clip wall;
       static {
           File bgMusicStartFile = new File("D:\\JAVA\\eclipse\\TankWar\\musics\\bgm.wav");
           File bgMusicAttackFile = new File("D:\\JAVA\\eclipse\\TankWar\\musics\\attack.wav");
           File bgMusicMoveFile = new File("D:\\JAVA\\eclipse\\TankWar\\musics\\move.wav");
           File bgMusicExplodeFile = new File("D:\\JAVA\\eclipse\\TankWar\\musics\\explode.wav");
           File bgMusicWallFile = new File("D:\\JAVA\\eclipse\\TankWar\\musics\\wall.wav");
           try {
               AudioInputStream audioInputStreamStart = AudioSystem.getAudioInputStream(bgMusicStartFile);
               start = AudioSystem.getClip();
               start.open(audioInputStreamStart);
               AudioInputStream audioInputStreamAttack = AudioSystem.getAudioInputStream(bgMusicAttackFile);
               attack = AudioSystem.getClip();
               attack.open(audioInputStreamAttack);
               AudioInputStream audioInputStreamStartMove = AudioSystem.getAudioInputStream(bgMusicMoveFile);
               move = AudioSystem.getClip();
               move.open(audioInputStreamStartMove);
               AudioInputStream audioInputStreamStartExplode = AudioSystem.getAudioInputStream(bgMusicExplodeFile);
               explode = AudioSystem.getClip();
               explode.open(audioInputStreamStartExplode); 
               AudioInputStream audioInputStreamStartWall = AudioSystem.getAudioInputStream(bgMusicWallFile);
               wall = AudioSystem.getClip();
               wall.open(audioInputStreamStartWall); 
           } catch (Exception e) {
               e.printStackTrace();
           }
     }
     public static void playBackground(){
           //循环播放
           move.setFramePosition(0);
           move.loop(Clip.LOOP_CONTINUOUSLY);       
       }
     public static void startPlay(){
       start.start();
       start.setFramePosition(0);
     }
     public static void attackPlay(){
	       attack.start();
	       //将进度条调为0
	       attack.setFramePosition(0);
     }
     public static void movePlay(){
	       move.start();
	       move.setFramePosition(0);
     }
     public static void moveStop(){
	       move.stop();
     }
     public static void explodePlay(){
	       explode.start();
	       explode.setFramePosition(0);
     }
     public static void wallPlay() {
    	 wall.start();
    	 wall.setFramePosition(0);
     }
}