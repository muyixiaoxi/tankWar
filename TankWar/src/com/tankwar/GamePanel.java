package com.tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class GamePanel extends JFrame{
	boolean one=true;
	boolean run=true;
	//关卡
	private int level=1;
	//定义图片
	Image offScreenImage=null;
	/*
	 * //游戏状态
	 * 0 未开始，1 单人模式，
	 * 2 游戏暂停，3 游戏失败，4 游戏胜利
	 */
	public int state =0;
	//游戏是否开始
	private boolean start=false;
	//临时变量
	private int a=1;
	//窗口长宽
	private int width=1260;
	private int height=800;
	//指针初始纵坐标
	private int y=260;
	//基地
	private Base base=new Base("images	/base.gif",660,740,this);
	//重绘次数
	public int count=0;
	//敌人数量
	private int enemyCount=0;
	///随机生成的墙体数量
	private int wallCount=100;
	private boolean d=true;
	private int[]xrr=new int[wallCount];
	private int[]yrr=new int[wallCount];
	//物体集合
	public List<Bullet>bulletList=new ArrayList<>();
	public List<Tank>tankList=new ArrayList<>();
	public List<Bot>botList=new ArrayList<>();
	public List<Bullet>removeList=new ArrayList<>();
	public List<Wall>wallList=new ArrayList<>();
	public List<Grass>grassList=new ArrayList<>();
	public List<Fe>feList=new ArrayList<>();
	public List<Base> baseList = new ArrayList<>();
	public List<EnemyBullet>enemyBulletsList=new ArrayList<>();
	public List<EnemyBullet>removeList2=new ArrayList<>();
	public List<Explode>explodeList=new ArrayList<>();
	//玩家
	 private PlayerOne playerOne = new PlayerOne("images/player1/p1tankU.gif", 500, 700,
	            "images/player1/p1tankU.gif","images/player1/p1tankD.gif",
	            "images/player1/p1tankL.gif","images/player1/p1tankR.gif", this);
	 public void random() {
		Random r=new Random();	
		for(int i=0;i<wallCount;) {
			int x=r.nextInt(20);
			int y=r.nextInt(8)+3;
			if(i>0) {
				boolean repeat=false;
				for(int j=0;j<i;j++) {
					if(xrr[j]==x*60&&yrr[j]==y*60) {
						repeat=true;
					}
				}
				if(!repeat) {
					xrr[i]=x*60;
					yrr[i]=y*60;
					i++;
				}else {
					continue;
				}
			}else {
				xrr[i]=x*60;
				yrr[i]=y*60;
				i++;
			}
		}
	 }
	 
	 /*
	  * 重置
	  */
	 
	 public void reset() {
		    count=0;
		    enemyCount=0;
		    playerOne = new PlayerOne("images/player1/p1tankU.gif", 500, 700,
		            "images/player1/p1tankU.gif","images/player1/p1tankD.gif",
		            "images/player1/p1tankL.gif","images/player1/p1tankR.gif", this);
		    feList.clear();
		    bulletList.clear();
			tankList.clear();
			botList.clear();
			removeList.clear();
			wallList.clear();
			grassList.clear();
			baseList.clear();
			enemyBulletsList.clear();
			removeList2.clear();
			explodeList.clear();
	 }
	 /*
	  * 添加墙，基地
	  */
	 
	 public void add() {
		    random();
			//添加围墙
			for(int i=0;i<21;i++) {
				wallList.add(new Wall("images/walls.gif",i*60,120,this));
			}
			wallList.add(new Wall("images/walls.gif",600,740,this));
			wallList.add(new Wall("images/walls.gif",600,680,this));
			wallList.add(new Wall("images/walls.gif",660,680,this));
			wallList.add(new Wall("images/walls.gif",720,680,this));
			wallList.add(new Wall("images/walls.gif",720,740,this));
			for(int i=0;i<wallCount;i++) {
				Random a=new Random();
				int num=a.nextInt(4);
				if(num<2) {
					Wall wall=new Wall("images/walls.gif",xrr[i], yrr[i], null);
					wallList.add(wall);
				}else if(num<3){
					Grass g=new Grass("images/cao.gif",xrr[i], yrr[i], null);
					grassList.add(g);
				}else {
					Fe f=new Fe("images/fe.gif",xrr[i], yrr[i], null);
					feList.add(f);
				}
			}
			//添加基地
			baseList.add(base);
	 }
	  
	/*
	 * 窗口的启动方法
	 */
	public void launch() {
		//标题
		setTitle("坦克大战");
		//窗口初始化大小
		setSize(width,height);
		//使屏幕居中
		setLocationRelativeTo(null);
		//添加关闭事件
		setDefaultCloseOperation(3);
		//用户不能调整大小
		setResizable(false);
		//使窗口可见
		setVisible(true);
		add();
		//添加鼠标事件
		this.addKeyListener(new GamePanel.KeyMonitor());
		while(true) {
			if(botList.size()==0&&enemyCount==10) {
				state=4;
			}
			if(tankList.size()==0&&(state==1)) {
				state=3;
			}
			if(state==1) {
				if(count%50==1&&enemyCount<10) {
					boolean a=true;
					Random r=new Random();
					int rnum=r.nextInt(19)+1;
					if(enemyCount>0) {
						for(Bot b:botList) {
							if(Math.abs(rnum*60-b.getX())<120) {//不让重叠
								a=false;
							}
						}
						if(a) {
							botList.add(new Bot("images/enemy/enemy1U.gif", rnum*60, 60,
			                        "images/enemy/enemy1U.gif","images/enemy/enemy1D.gif",
			                        "images/enemy/enemy1L.gif","images/enemy/enemy1R.gif", this));
							enemyCount++;
						}
					}else {//生成的第一格坦克
						botList.add(new Bot("images/enemy/enemy1U.gif", rnum*60, 60,
		                        "images/enemy/enemy1U.gif","images/enemy/enemy1D.gif",
		                        "images/enemy/enemy1L.gif","images/enemy/enemy1R.gif", this));
						enemyCount++;
					}
				}
				
					if(!explodeList.isEmpty()) {//爆炸集合不为空，则删除集合中第一个元素
						explodeList.remove(0);
					
				}
			}
			if(run) {
				repaint();
			}
			try {
				Thread.sleep(25);
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	@Override
	public void paint(Graphics g) {
		//创建和容器一样大小的Image图片
		if(offScreenImage==null) {
			offScreenImage=this.createImage(width,height);
		}
		//获得该图片的画布
		Graphics gImage=offScreenImage.getGraphics();
		//填充整个画布
		gImage.fillRect(0,0,width,height);
		if(state==0) {
			//添加图片
			gImage.drawImage(Toolkit.getDefaultToolkit().getImage("images/interface.png"),0,0,this);
			//添加鼠标事件
			this.addMouseListener(new GamePanel.MouseMonitor());
		}else if(state==1) {
			//paint重绘游戏元素
			for(Tank player:tankList) {
				player.paintSelf(gImage);
			}
			for(Bullet bullet:bulletList) {
				bullet.paintSelf(gImage);
			}
			bulletList.removeAll(removeList);
			for(EnemyBullet enemyBullet:enemyBulletsList) {
				enemyBullet.paintSelf(gImage);
			}
			enemyBulletsList.removeAll(removeList2);
			for(Explode explode:explodeList) {
				explode.paintSelf(gImage);
			}
			for(Bot bot:botList) {
				bot.paintSelf(gImage);
			}
			for(Wall wall:wallList) {
				wall.paintSelf(gImage);
			}
			for(Fe fe:feList) {
				fe.paintSelf(gImage);
			}
			for(Grass grass:grassList) {
				grass.paintSelf(gImage);
			}
			for(Base base:baseList) {
				base.paintSelf(gImage);
			}
			//重绘次数+1
			count++;
		}else if(state==2) {
			
		}	
		else if(state==3) {
			gImage.drawImage(Toolkit.getDefaultToolkit().getImage("images/shibai.png"),315,200,this);
			//添加鼠标事件
			this.addMouseListener(new GamePanel.MouseMonitor());
		}else if(state==4) {
			gImage.drawImage(Toolkit.getDefaultToolkit().getImage("images/win.png"),(width-322)/2,(height-84)/2,this);
			this.addMouseListener(new GamePanel.MouseMonitor());
		}
		//将缓冲区绘制好的图形整个绘制到容器的画布中
		g.drawImage(offScreenImage,0,0,null);
		
	}
	
	/*
	 * 添加键盘监听
	 */
	private class KeyMonitor extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int key=e.getKeyCode();
			switch(key) {
			case KeyEvent.VK_ENTER:
				if(!start) {
					tankList.add(playerOne);//将坦克添加至坦克集合
					state=a;
					start=true;
				}
				break;
			case KeyEvent.VK_P:
				if(state!=2) {
					a=state;
					state=2;
					run=false;
				}else {
					state=a;
					run=true;
					if(a==0) {
						a=1;
					}
				}
				break;				
				default:
					playerOne.keyPressed(e);
					break;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			playerOne.keyReleased(e);
		}
	}
	
	//添加鼠标监听
	private class MouseMonitor extends MouseAdapter{
		@Override
	    public void mouseClicked(MouseEvent e) {
			Point p=new Point(e.getX(),e.getY());
			if(!start&&state==0) {
				if(p.x>450&&p.x<750&&p.y>400&&p.y<480) {
					System.out.print("1");
					tankList.add(playerOne);
					state=1;
					start=true;
					Music.startPlay();
				}
			}else if(state==3||state==4) {
				System.out.print("6");
				reset();
				start=false;
				state=0;
				add();
			}
		}
	}	
	public static void main(String[]args) {
		GamePanel gamePanel=new GamePanel();
		gamePanel.launch();
	}
}

