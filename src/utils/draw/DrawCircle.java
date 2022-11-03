package utils.draw;


import javax.swing.*;
import java.awt.*;

/**
 * @author chenxi
 * 面板画圆
 */
public class DrawCircle extends JFrame {//JFrame相当于一个面板窗口，画框；
    //定义一个面板
    private myPanel mp = null;

    public static void main(String[] args) {
        new DrawCircle();
    }

    public DrawCircle() {//构造器
        mp = new myPanel();
        //将面板放入窗口；
        this.add(mp);
        //设置窗口大小
        this.setSize(1920, 1050);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口的x则完全退出
        this.setVisible(true);
    }

}

class myPanel extends JPanel {
    //myPanel一个画本
    //Graphics 一个画笔

    @Override
    public void paint(Graphics g) {
        //以下情况paint会被调用(监听机制自动调用）:
        //窗口最小化在最大化；
        //窗口大小发生变化;
        //repaint函数被调用;
        super.paint(g);

//        g.setColor(Color.CYAN.brighter());
//        g.drawRect(520, 515, 40, 50);
//        g.fillRect(520, 515, 40, 50);
//        g.drawRect(500, 500, 20, 80);
//        g.fillRect(500, 500, 20, 80);
//        g.drawRect(560, 500, 20, 80);
//        g.fillRect(560, 500, 20, 80);
//        g.drawRect(538, 485, 4, 40);
//        g.fillRect(538, 485, 4, 40);
//        g.setColor(Color.cyan);
//        g.drawOval(525, 525, 30, 30);
//        g.fillOval(525, 525, 30, 30);
        g.drawString("aaaaa",500,500);
    }
}
