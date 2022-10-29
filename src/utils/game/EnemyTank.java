package utils.game;

import java.util.Vector;

/**
 * @author chenxi
 */
public class EnemyTank extends Tank implements Runnable{
    Vector<Bullet> bullets = new Vector<>();
    int i=2;

    public EnemyTank(int x, int y) {
        super(x, y);
        this.setDirection(2);
    }

    public void shoot() {
        Bullet bullet = new Bullet(this.getX(), this.getY(), this.getDirection(), 0);
        Thread thread = new Thread(bullet);
        thread.start();
        bullets.add(bullet);
    }

    public void randomMove(int i) {
        if (i % 4 == 1) {
            this.setDirection(1);
            moveright();
        } else if (i % 4 == 2) {
            this.setDirection(2);
            movedown();
        } else if (i % 4 == 3) {
            this.setDirection(3);
            moveleft();
        } else {
            this.setDirection(0);
            moveup();
        }
    }

    @Override
    public void run() {
        while(true){
            if(!this.isLive()){
                break;
            }
            int step =5;
            while(step>0){
                randomMove(i);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if((int)(Math.random()*10)+1>8){
                    shoot();
                }
                step--;
            }
            i=(int)(Math.random()*4);
        }
    }
}
