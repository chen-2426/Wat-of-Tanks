package utils.game;

import java.util.Vector;

/**
 * @author chenxi
 */
public class EnemyTank extends Tank {
    Vector<Bullet> bullets = new Vector<>();
    public EnemyTank(int x, int y) {
        super(x, y);
        this.setDirection(2);
        shoot();
        shoot();
    }
    public void shoot(){
        Bullet bullet = new Bullet(this.getX(),this.getY(),this.getDirection(),0);
        Thread thread = new Thread(bullet);
        thread.start();
        bullets.add(bullet);
    }
}
