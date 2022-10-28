package utils.game;

import javax.swing.*;
import java.awt.*;

/**
 * @author chenxi
 */
public class GameMainMenu extends JFrame {
    GamePanel gamePanel = null;

    public static void main(String[] args) {
        GameMainMenu gameMainMenu = new GameMainMenu();
    }

    public GameMainMenu(){
        gamePanel = new GamePanel();
        //将面板放入窗口；
        this.add(gamePanel);
        //设置窗口大小
        this.setSize(1920,1050);
        this.addKeyListener(gamePanel);//监听键盘
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口的x则完全退出
        this.setVisible(true);
    }
}
