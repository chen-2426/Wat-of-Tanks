package utils.game;

import java.util.Vector;

/**
 * @author chenxi
 */
public class EnemyTank extends Tank implements Runnable {
    Vector<Bullet> bullets = new Vector<>();
    Vector<Tank> otherTanks = new Vector<>();
    int i = 2;

    public Vector<Tank> getOtherTanks() {
        return otherTanks;
    }

    public void setOtherTanks(Vector<Tank> otherTanks) {
        this.otherTanks = otherTanks;
    }

    public EnemyTank(int x, int y, int direction, Vector<Bullet> bullets) {
        super(x, y, direction);
        this.bullets = bullets;
    }

    public EnemyTank(int x, int y) {
        super(x, y);
        this.setDirection(2);
//        Thread thread = new Thread(this);
//        thread.start();
    }

    public void shoot() {
        Bullet bullet = new Bullet(this.getX(), this.getY(), this.getDirection(), 0);
        Thread thread = new Thread(bullet);
        thread.start();
        bullets.add(bullet);
    }

    //敌方tank随机移动
    public void randomMove(int i) {
        if (i % 4 == 1) {
            this.setDirection(1);
            if (collisionJudgement()) moveright();
        } else if (i % 4 == 2) {
            this.setDirection(2);
            if (collisionJudgement()) movedown();
        } else if (i % 4 == 3) {
            this.setDirection(3);
            if (collisionJudgement()) moveleft();
        } else {
            this.setDirection(0);
            if (collisionJudgement()) moveup();
        }
    }

    private boolean collisionJudgement() {
        for (int i = 0; i < otherTanks.size(); i++) {
            if (otherTanks.get(i).isLive() && (!this.collisionVolume(otherTanks.get(i)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        while (true) {
            if (!this.isLive()) {
                break;
            }
            int step = 5;
            while (step > 0) {
                randomMove(i);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if ((int) (Math.random() * 10) + 1 > 8) {
                    shoot();
                }
                step--;
            }
            i = (int) (Math.random() * 4);
        }
    }
}
