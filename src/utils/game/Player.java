package utils.game;

import java.util.Vector;

/**
 * @author chenxi
 */
public class Player extends Tank {
    //销毁标识
    boolean exist = true;
    //弹夹
    Vector<Bullet> bullets = new Vector<>();

    public Player(int x, int y, int direction) {
        super(x, y, direction);
    }

    //玩家操纵的坦克
    public Player(int x, int y) {
        super(x, y);
    }
    public void shootBullet(){
        Bullet bullet = new Bullet(this.getX(), this.getY(), this.getDirection(), 1);
        Thread thread = new Thread(bullet);
        thread.start();
        bullets.add(bullet);
    }
}
