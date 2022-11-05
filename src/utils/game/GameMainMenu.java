package utils.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;

/**
 * @author chenxi
 */
public class GameMainMenu extends JFrame {
    GamePanel gamePanel = null;

    public static void main(String[] args) {
        GameMainMenu gameMainMenu = new GameMainMenu();
    }

    public GameMainMenu() {
//        if(????) {
        gamePanel = new GamePanel();
//        }else {
        Recorder.readGame();
//            gamePanel = new GamePanel(Recorder.player,Recorder.enemyTanks);
//        }
        //为了方便刷新采取线程模式重复绘图
        Thread thread = new Thread(gamePanel);
        thread.start();
        //将面板放入窗口；
        this.add(gamePanel);
        //设置窗口大小
        this.setSize(1500, 900);
        this.addKeyListener(gamePanel);//监听键盘
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口的x则完全退出
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.setPlayer(gamePanel.player);
                Recorder.setEnemyTanks(gamePanel.enemyTanks);
                Recorder.saveGame();
                System.exit(0);
            }
        });
    }
}
