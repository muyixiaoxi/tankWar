package com.tankwar;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/*
 * 人机子弹
 */
public class EnemyBullet extends Bullet{
	public EnemyBullet(String img, int x, int y, Direction direction, GamePanel gamePanel) {
		super(img, x, y, direction, gamePanel);
	}
	/*
	 * 击中玩家
	 */
	public void hitPlayer() {
		Rectangle next=this.getRec();
		List<Tank>tanks=this.gamePanel.tankList;
		//子弹和坦克
		for(Tank tank:tanks) {
			if(tank.getRec().intersects(next)) {
				tank.alive=false;
				this.gamePanel.tankList.remove(tank);
				this.gamePanel.removeList.add(this);
				break;
			}
		}
	}
	@Override
	public void hitBase() {
		Rectangle next=this.getRec();
		for(Base base:gamePanel.baseList) {
			if(base.getRec().intersects(next)) {
				this.gamePanel.baseList.remove(base);
				this.gamePanel.removeList2.add(this);
				this.gamePanel.state=3;
				break;
			}
		}
	}
	public void hitWall() {
		Rectangle next=this.getRec();
		List<Wall>walls=this.gamePanel.wallList;
		for(Wall w:walls) {
			if(w.getRec().intersects(next)) {
				this.gamePanel.wallList.remove(w);
				this.gamePanel.removeList2.add(this);
				break;
			}
		}
	}
	public void hitFe() {
		Rectangle next=this.getRec();
		List<Fe>fes=this.gamePanel.feList;
		for(Fe f:fes) {
			if(f.getRec().intersects(next)) {
				this.gamePanel.removeList2.add(this);
				break;
			}
		}
	}
	public void paintSelf(Graphics g) {
		g.drawImage(img,x,y,null);
		go();
		hitBase();
		hitWall();
		hitFe();
		hitPlayer();
	}
}
