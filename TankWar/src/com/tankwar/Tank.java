package com.tankwar;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public class Tank extends GameObject{
	
	//向上移动时的图片
	private String upImage;
	//向下移动时的图片
	private String downImage;
	//向右移动时的图片
	private String rightImage;
	//向左移动时的图片
	private String leftImage;
	
	//坦克size
	int width=50;
	int height=50;
	
	//坦克初始方向
	Direction direction=Direction.UP;
	//坦克速度
	private int speed=3;
	//攻击冷却
	private boolean attackCoolDown=true;
	//攻击冷却事件毫秒间隔1000ms发射子弹
	private int attackCoolDownTime=100;
	//坦克是否活着
	public boolean alive=true;
	/*
	 * 坦克坐标，方向，图片，方向，面板
	 */
	public Tank(String img,int x,int y,String upImage,String downImage,String leftImage,String rightImage,GamePanel gamePanel) {
		super(img,x,y,gamePanel);
		this.upImage=upImage;
		this.leftImage=leftImage;
		this.downImage=downImage;
		this.rightImage=rightImage;	
	}
	
	/*
	 * 坦克移动
	 */
	public void leftWard() {
		direction=Direction.LEFT;
		setImg(leftImage);
		if(!hitWall(x-speed,y)&&!moveToBorder(x-speed,y)&&alive&&!hitTank(x-speed,y)) {
			this.x-=speed;
		}
	}
	
	public void rightWard() {
		direction=Direction.RIGHT;
		setImg(rightImage);
		if(!hitWall(x+speed,y)&&!moveToBorder(x+speed,y)&&alive&&!hitTank(x+speed,y)) {
			this.x+=speed;
		}
	}
	public void upWard() {
		direction=Direction.UP;
		setImg(upImage);
		if(!hitWall(x,y-speed)&&!moveToBorder(x,y-speed)&&alive&&!hitTank(x,y-speed)) {
			this.y-=speed;
		}
	}
	public void downWard() {
		direction=Direction.DOWN;
		setImg(downImage);
		if(!hitWall(x,y+speed)&&!moveToBorder(x,y+speed)&&alive&&!hitTank(x-speed,y+speed)) {
			this.y+=speed;
		}
	}
	
	/*
	 * 射击
	 */
	public void attack() {
		Point p=getHeadPoint();
		if(attackCoolDown&&alive) {
			Bullet bullet=new Bullet("images/bullet/bulletGreen.gif",p.x-10,p.y-10, direction, this.gamePanel);
			this.gamePanel.bulletList.add(bullet);//将子弹添加至子弹集合
			attackCoolDown=false;
			new AttackCD().start();
		}
	}
	
	/*
	 * 坦克射击cd
	 */
	public class AttackCD extends Thread{
		public void run() {
			attackCoolDown=false;
			try {
				//休眠1秒
				Thread.sleep(attackCoolDownTime);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			//将攻击功能解除冷却状态
			attackCoolDown=true;
		}
	}
	/*
	 * 根据方向确定头部位置,x和y是左上角的点
	 */
	public Point getHeadPoint() {
		switch(direction) {
		case UP:
			return new Point(x+width/2,y);
		case LEFT:
			return new Point(x,y+height/2);
		case DOWN:
			return new Point(x+width/2,y+height);
		case RIGHT:
			return new Point(x+width,y+height/2);
			default:
				return null;
		}
	}
	/*
	 * 坦克与墙碰撞检测
	 */
	public boolean hitWall(int x,int y) {
		//假设玩家坦克前进，下一个位置形成的矩形
		Rectangle next=new Rectangle(x,y,width,height);
		//地图里所有的墙体
		List<Wall>walls=this.gamePanel.wallList;
		List<Fe>fes=this.gamePanel.feList;
		//判断两个矩形是否相交
		for(Wall w:walls) {
			if(w.getRec().intersects(next)) {
				return true;
			}
		}
		for(Fe f:fes) {
			if(f.getRec().intersects(next)) {
				return true;
			}
		}
		return false;
	}
	/*
	 * 人机碰撞检测
	 */
	public boolean hitTank(int x,int y) {
		int a=0;
		//假设人机坦克前进，下一个位置形成的矩形
		Rectangle next=new Rectangle(x,y,width,height);
		//地图里所有的人机
		List<Bot>bots=this.gamePanel.botList;
		List<Tank>tanks=this.gamePanel.tankList;
		//判断两个矩形是否相交
		for(Bot b:bots) {
			if(b.getRec().intersects(next)) {
				a++;
				if(a==2) {
					return true;
				}
			}
		}
		for( Tank t:tanks) {
			if(t.getRec().intersects(next)) {
				a++;
				if(a==2) {
					return true;
				}
			}
		}
		return false;
	}
	/*
	 * 边界与坦克碰撞检测
	 */
	public boolean moveToBorder(int x,int y) {
		if(x<10) {
			return true;
		}else if(x>this.gamePanel.getWidth()-width) {
			return true;
		}
		if(y<30) {
			return true;
		}else if(y>this.gamePanel.getHeight()-height) {
			return true;
		}
		return false;
	}
	@Override
	public Rectangle getRec(){
		return new Rectangle(x,y,width,height);
	}

	@Override
	public void paintSelf(Graphics g) {
		g.drawImage(img, x, y, null);
	}
}
