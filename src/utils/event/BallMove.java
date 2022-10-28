package utils.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author chenxi
 */
public class BallMove extends JFrame {
    GamePanel gamePanel = null;

    public static void main(String[] args) {
        BallMove ballMove = new BallMove();

    }

    public BallMove() {
        gamePanel = new GamePanel();
        //将面板放入窗口；
        this.add(gamePanel);
        //设置窗口大小
        this.setSize(1920, 1050);
        //JFrame 可以监听键盘输入输出；
        this.addKeyListener(gamePanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口的x则完全退出
        this.setVisible(true);
    }
}

class GamePanel extends JPanel implements KeyListener {
    //实现小球动态移动
    int x = 10;
    int y = 10;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.cyan);
        g.fillOval(x, y, 10, 10);
    }
    //字符输入时。该方法就会触发
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //按键按下时该方法触发
    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println((char) e.getKeyChar()+"被按下");
        //根据用户按不同按键，处理小球移动
        if (e.getKeyChar() == KeyEvent.VK_S){//KeyEvent.VK_S为S键对应的int值，java给每个按键赋予了对应的int数值；
            y++;
        }else if(e.getKeyChar() == KeyEvent.VK_W){
            y--;
        }else if(e.getKeyChar() == KeyEvent.VK_D) {
            x++;
        } else if (e.getKeyChar() == KeyEvent.VK_A) {
            x--;
        }
        this.repaint();
    }
    //按键松开时该方法触发
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
