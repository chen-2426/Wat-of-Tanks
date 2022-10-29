package utils.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author chenxi
 */
//为了监听键盘事件，此处需要实现接口KeyListener
public class GamePanel extends JPanel implements KeyListener, Runnable {
    Player player = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    int x;
    int y;
    int enemyTank_num = 3;

    public GamePanel() {
        player = new Player(500, 500);
        for (int i = 0; i < enemyTank_num; i++) {
            EnemyTank enemyTank = new EnemyTank(300 * (1 + i), 0);
            enemyTanks.add(enemyTank);
            Thread thread = new Thread(enemyTank);
            thread.start();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //画出背景板
        g.fillRect(0, 0, 1960, 1520);
        //画出玩家
        drawTank(g, player.getX(), player.getY(), 1, player.getDirection());
        //画出玩家子弹
        if (player.bullet != null && player.bullet.isLive()) {
            g.fill3DRect(player.bullet.getX(), player.bullet.getY(), 3, 3, false);
        }
        //画出敌方tank
        for (int i = 0; i < enemyTank_num; i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive()) {
                drawTank(g, enemyTank.getX(), enemyTank.getY(), 0, enemyTank.getDirection());
            }
            for (int j = 0; j < enemyTank.bullets.size(); j++) {
                Bullet bullet = enemyTank.bullets.get(j);
                if (bullet.isLive()) {
                    g.fill3DRect(bullet.getX(), bullet.getY(), 3, 3, false);
                } else {
                    enemyTank.bullets.remove(j);
                }
            }
            if (!enemyTank.isLive()){
                bombs.add(new Bomb(enemyTank.getX(),enemyTank.getY()));
                enemyTanks.remove(i);
                enemyTank_num--;

            }
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
                g.setColor(Color.red);
            if(bomb.life>=6) {
                g.fillOval(bomb.x, bomb.y, 80, 80);
            }else  if (bomb.life>=3){
                g.fillOval(bomb.x+10, bomb.y+10, 60, 60);
            }else if(bomb.life>=0){
                g.fillOval(bomb.x+25, bomb.y+25, 30, 30);
            }else {
                bombs.remove(bomb);
                break;
            }
            bomb.lifeDown();
        }

    }

    public static void hitTank(Bullet bullet, Tank tank) {
        int direction = tank.getDirection();
        int xb = bullet.getX();
        int yb = bullet.getY();
        int xt = tank.getX();
        int yt = tank.getY();
        if (direction == 0 || direction == 2) {
            if ((xb < xt + 20 && xb > xt) || (xb < xt + 80 && xb > xt + 60)) {
                if (yb > yt && yb < yt + 80) {
                    bullet.setLive(false);
                    tank.setLive(false);
                }
            } else if (xb > xt + 20 && xb < xt + 60) {
                if (yb > yt + 15 && yb < yt + 65) {
                    bullet.setLive(false);
                    tank.setLive(false);
                }
            }
        } else if (direction == 1 || direction == 3) {
            if ((yb < yt + 20 && yb > yt) || (yb < yt + 80 && yb > yt + 60)) {
                if (xb > xt && xb < xt + 80) {
                    bullet.setLive(false);
                    tank.setLive(false);
                }
            } else if (yb > yt + 20 && yb < yt + 60) {
                if (xb > xt + 15 && xb < xt + 65) {
                    bullet.setLive(false);
                    tank.setLive(false);
                }
            }
        }

    }

    /**
     * @param g         画笔
     * @param x         横坐标
     * @param y         纵坐标
     * @param type      判断是否是玩家 0-敌人 1-玩家
     * @param direction 判断tank朝向 0-向上，1-向右，2-向下，3-向左
     */
    public void drawTank(Graphics g, int x, int y, int type, int direction) {
        //区分敌我
        if (type == 1) {
            g.setColor(Color.CYAN);
        } else if (type == 0) {
            g.setColor(Color.YELLOW);
        }
        if (direction == 0) {
            //tank 主干
            g.fill3DRect(x + 20, y + 15, 40, 50, false);
            //tank左轮
            g.fill3DRect(x, y, 20, 80, false);
            //tank右轮
            g.fill3DRect(x + 60, y, 20, 80, false);
            //炮管
            g.fill3DRect(x + 38, y - 15, 4, 40, false);
            //炮台圆心
            g.fillOval(x + 25, y + 25, 30, 30);
        } else if (direction == 1) {
            //tank 主干
            g.fill3DRect(x + 15, y + 20, 50, 40, false);
            //tank左轮
            g.fill3DRect(x, y, 80, 20, false);
            //tank右轮
            g.fill3DRect(x, y + 60, 80, 20, false);
            //炮管
            g.fill3DRect(x + 55, y + 40, 40, 4, false);
            //炮台圆心
            g.fillOval(x + 25, y + 25, 30, 30);
        } else if (direction == 2) {
            //tank 主干
            g.fill3DRect(x + 20, y + 15, 40, 50, false);
            //tank左轮
            g.fill3DRect(x, y, 20, 80, false);
            //tank右轮
            g.fill3DRect(x + 60, y, 20, 80, false);
            //炮管
            g.fill3DRect(x + 38, y + 55, 4, 40, false);
            //炮台圆心
            g.fillOval(x + 25, y + 25, 30, 30);
        } else if (direction == 3) {
            //tank 主干
            g.fill3DRect(x + 15, y + 20, 50, 40, false);
            //tank左轮
            g.fill3DRect(x, y, 80, 20, false);
            //tank右轮
            g.fill3DRect(x, y + 60, 80, 20, false);
            //炮管
            g.fill3DRect(x - 15, y + 38, 40, 4, false);
            //炮台圆心
            g.fillOval(x + 25, y + 25, 30, 30);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_S) { //KeyEvent.VK_S为S键对应的int值，java给每个按键赋予了对应的int数值；
            player.setDirection(2);
            player.movedown();
        } else if (e.getKeyChar() == KeyEvent.VK_W) {
            player.setDirection(0);
            player.moveup();
        } else if (e.getKeyChar() == KeyEvent.VK_D) {
            player.setDirection(1);
            player.moveright();
        } else if (e.getKeyChar() == KeyEvent.VK_A) {
            player.setDirection(3);
            player.moveleft();
        } else if (e.getKeyChar() == KeyEvent.VK_J) {
            player.shootBullet();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (player.bullet != null && player.bullet.isLive()) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    Tank enemyTank = enemyTanks.get(i);
                    hitTank(player.bullet, enemyTank);
                }
            }
            this.repaint();
        }
    }
}
