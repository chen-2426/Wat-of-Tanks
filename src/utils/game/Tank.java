package utils.game;

/**
 * @author chenxi
 */
public class Tank {
    //坦克坐标；
    private int x;
    private int y;
    private int direction;//坦克方向  0-向上，1-向右，2-向下，3-向左
    private int speed = 10;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = 0;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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

    public void setY(int y) {
        this.y = y;
    }

    //TANK 移动；
    public void moveup() {
        y -= speed;
    }

    public void movedown() {
        y += speed;
    }


    public void moveleft() {
        x -= speed;
    }

    public void moveright() {
        x += speed;
    }
}
