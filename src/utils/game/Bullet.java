package utils.game;

/**
 * @author chenxi
 */
public class Bullet implements Runnable {
    private int x;
    private int y;
    private int direction;
    private int type;
    private int speed = 10;
    private boolean isLive = true;

    public Bullet(int x, int y, int direction, int type) {
        if (direction == 0) {
            this.x = x + 37;
            this.y = y - 40;
        } else if (direction == 1) {
            this.x = x + 110;
            this.y = y + 37;
        } else if (direction == 2) {
            this.x = x + 37;
            this.y = y + 120;
        } else if (direction == 3) {
            this.x = x - 30;
            this.y = y + 37;
        }

        this.direction = direction;
        this.type = type;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void shoot() {
        if (this.direction == 0) {
            shootUp();
        } else if (this.direction == 1) {
            shootRight();
        } else if (this.direction == 2) {
            shootDown();
        } else if (this.direction == 3) {
            shootLeft();
        }
    }

    public void shootUp() {
        y -= speed;
    }

    public void shootRight() {
        x += speed;
    }

    public void shootDown() {
        y += speed;
    }

    public void shootLeft() {
        x -= speed;
    }

    @Override
    public void run() {
        //子弹碰到边界后退出
        while (x > 0 && y > 0 && x <= 1500 && y <= 900 && isLive) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            shoot();
        }
        isLive = false;
    }
}
