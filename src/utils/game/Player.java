package utils.game;

import java.util.Vector;

/**
 * @author chenxi
 */
public class Player extends Tank{
    Vector<Bullet> bullets = new Vector<>();
    Bullet bullet =null;
    //玩家操纵的坦克
    public Player(int x, int y) {
        super(x, y);
    }
    public void shootBullet(){
        bullet = new Bullet(this.getX(), this.getY(), this.getDirection(), 1);
        Thread thread = new Thread(bullet);
        thread.start();
    }

}
