package utils.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author chenxi
 */
//为了监听键盘事件，此处需要实现接口KeyListener
public class GamePanel extends JPanel implements KeyListener, Runnable {
    Player player = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    int enemyTank_num = 3;
    String  str = "分数：\t";

    public GamePanel(Player player, Vector<EnemyTank> enemyTanks) {
        this.player = player;
        this.enemyTanks = enemyTanks;
    }

    public GamePanel() {
        player = new Player(500, 500);
        for (int i = 0; i < enemyTank_num; i++) {
            EnemyTank enemyTank = new EnemyTank(300 * (1 + i), 0);
            enemyTanks.add(enemyTank);
            Thread thread = new Thread(enemyTank);
            thread.start();
        }
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            Vector<Tank> tanks = new Vector<>();
            tanks.add(player);
            for (EnemyTank tank : enemyTanks) {
                if (tank != enemyTank) {
                    tanks.add(tank);
                }
            }
            enemyTank.setOtherTanks(tanks);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //画出背景板
        g.fillRect(0, 0, 1500, 900);
        //画出分数
        g.setColor(Color.white);
        g.drawString(str+Recorder.getScore(),10,10);
        //画出玩家
        if(player.isLive()){
            drawTank(g, player.getX(), player.getY(), 1, player.getDirection());
        }
        //画出玩家子弹
        for (int j = 0; j < player.bullets.size(); j++) {
            if (player.bullets.get((j)).isLive()) {
                g.fill3DRect(player.bullets.get(j).getX(), player.bullets.get(j).getY(), 3, 3, false);
            } else {
                player.bullets.remove(j);
            }
        }
        //销毁玩家
        if (!player.isLive() && player.exist){
            bombs.add(new Bomb(player.getX(), player.getY()));
            player.exist = false;
        }

        //画出敌方tank
        for (int i = 0; i < enemyTank_num; i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive()) {
                drawTank(g, enemyTank.getX(), enemyTank.getY(), 0, enemyTank.getDirection());
            }
            //画出地方全部子弹
            for (int j = 0; j < enemyTank.bullets.size(); j++) {
                Bullet bullet = enemyTank.bullets.get(j);
                if (bullet.isLive()) {
                    g.fill3DRect(bullet.getX(), bullet.getY(), 3, 3, false);
                } else {
                    enemyTank.bullets.remove(j);
                }
            }
            if (!enemyTank.isLive()) {
                bombs.add(new Bomb(enemyTank.getX(), enemyTank.getY()));
                enemyTanks.remove(i);
                Recorder.score+=1;
                enemyTank_num--;

            }
        }
        //判断Boom效果
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            g.setColor(Color.red);
            if (bomb.life >= 6) {
                g.fillOval(bomb.x, bomb.y, 80, 80);
            } else if (bomb.life >= 3) {
                g.fillOval(bomb.x + 10, bomb.y + 10, 60, 60);
            } else if (bomb.life >= 0) {
                g.fillOval(bomb.x + 25, bomb.y + 25, 30, 30);
            } else {
                bombs.remove(bomb);
                break;
            }
            bomb.lifeDown();
        }

    }
    //判断子弹是否击中
    public void hitJudgement(){
        for (EnemyTank enemyTank : enemyTanks) {
            for (int j = 0; j < player.bullets.size(); j++) {
                hitTank(player.bullets.get(j), enemyTank);
            }
            for (int j = 0; j < enemyTank.bullets.size(); j++) {
                hitTank(enemyTank.bullets.get(j), player);
            }

        }
    }
    //判断子弹击中对象
    public static void hitTank(Bullet bullet, Tank tank) {
        int direction = tank.getDirection();
        int xb = bullet.getX();
        int yb = bullet.getY();
        int xt = tank.getX();
        int yt = tank.getY();
        if (direction == 0 || direction == 2) {
            hitTankJudgement(bullet, tank, xb, yb, xt, yt);
        } else if (direction == 1 || direction == 3) {
            hitTankJudgement(bullet, tank, yb, xb, yt, xt);
        }

    }

    private static void hitTankJudgement(Bullet bullet, Tank tank, int xb, int yb, int xt, int yt) {
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
        if (player.isLive()) {
            if (e.getKeyChar() == KeyEvent.VK_S) { //KeyEvent.VK_S为S键对应的int值，java给每个按键赋予了对应的int数值；
                player.setDirection(2);
                if(collisionJudgement())player.movedown();
            } else if (e.getKeyChar() == KeyEvent.VK_W) {
                player.setDirection(0);
                if(collisionJudgement())player.moveup();
            } else if (e.getKeyChar() == KeyEvent.VK_D) {
                player.setDirection(1);
                if(collisionJudgement())player.moveright();
            } else if (e.getKeyChar() == KeyEvent.VK_A) {
                player.setDirection(3);
                if(collisionJudgement())player.moveleft();
            } else if (e.getKeyChar() == KeyEvent.VK_J) {
                player.shootBullet();
            }
        }
        this.repaint();
    }
    public boolean collisionJudgement(){
        for (EnemyTank enemyTank : enemyTanks) {
            if (!player.collisionVolume(enemyTank)) {
                return false;
            }
        }
        return true;
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
            hitJudgement();
            this.repaint();
        }
    }
}
