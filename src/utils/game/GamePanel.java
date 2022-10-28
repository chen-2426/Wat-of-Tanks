package utils.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author chenxi
 */
//为了监听键盘事件，此处需要实现接口KeyListener
public class GamePanel extends JPanel implements KeyListener {
    Player player =null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int x;
    int y;
    int enemyTank_num = 3;
    public GamePanel(){
        player =new Player(500,500);
        for (int i = 0; i < enemyTank_num; i++) {
            enemyTanks.add(new EnemyTank(300*(1+i),0));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //画出背景板
        g.fillRect(0,0,1960,1520);
        drawTank(g,player.getX(),player.getY(),1,player.getDirection());
        for (int i = 0; i < enemyTank_num; i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            drawTank(g,enemyTank.getX(),enemyTank.getY(),0,enemyTank.getDirection());
        }

    }

    /**
     *
     * @param g 画笔
     * @param x 横坐标
     * @param y 纵坐标
     * @param type 判断是否是玩家 0-敌人 1-玩家
     * @param direction 判断tank朝向 0-向上，1-向右，2-向下，3-向左
     */
    public void drawTank(Graphics g,int x,int y,int type,int direction){
        //区分敌我
        if (type == 1){
            g.setColor(Color.CYAN);
        } else if (type == 0) {
            g.setColor(Color.YELLOW);
        }
        if (direction == 0){
            //tank 主干
            g.fill3DRect(x+20,y+15,40,50,false);
            //tank左轮
            g.fill3DRect(x,y,20,80,false);
            //tank右轮
            g.fill3DRect(x+60,y,20,80,false);
            //炮管
            g.fill3DRect(x+38,y-15,4,40,false);
            //炮台圆心
            g.fillOval(x+25,y+25,30,30);
        } else if (direction == 1) {
            //tank 主干
            g.fill3DRect(x+15,y+20,50,40,false);
            //tank左轮
            g.fill3DRect(x,y,80,20,false);
            //tank右轮
            g.fill3DRect(x,y+60,80,20,false);
            //炮管
            g.fill3DRect(x+55,y+38,40,4,false);
            //炮台圆心
            g.fillOval(x+25,y+25,30,30);
        } else if (direction == 2) {
            //tank 主干
            g.fill3DRect(x+20,y+15,40,50,false);
            //tank左轮
            g.fill3DRect(x,y,20,80,false);
            //tank右轮
            g.fill3DRect(x+60,y,20,80,false);
            //炮管
            g.fill3DRect(x+38,y+55,4,40,false);
            //炮台圆心
            g.fillOval(x+25,y+25,30,30);
        } else if (direction == 3) {
            //tank 主干
            g.fill3DRect(x+15,y+20,50,40,false);
            //tank左轮
            g.fill3DRect(x,y,80,20,false);
            //tank右轮
            g.fill3DRect(x,y+60,80,20,false);
            //炮管
            g.fill3DRect(x-15,y+38,40,4,false);
            //炮台圆心
            g.fillOval(x+25,y+25,30,30);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_S){ //KeyEvent.VK_S为S键对应的int值，java给每个按键赋予了对应的int数值；
            player.setDirection(2);
            player.movedown();
        }else if(e.getKeyChar() == KeyEvent.VK_W){
            player.setDirection(0);
            player.moveup();
        }else if(e.getKeyChar() == KeyEvent.VK_D) {
            player.setDirection(1);
            player.moveright();
        } else if (e.getKeyChar() == KeyEvent.VK_A) {
            player.setDirection(3);
            player.moveleft();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
