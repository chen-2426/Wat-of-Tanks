package utils.game;

/**
 * @author chenxi
 */
public class Tank {
    //坦克坐标；
    private int x;
    private int y;
    private int direction;//坦克方向  0-向上，1-向右，2-向下，3-向左
    private int speed = 3;
    private boolean isLive = true;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = 0;
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

    //TANK 移动，限制在图框的范围内
    public void moveup() {
        if(y>=speed+10)y -= speed;
    }

    public void movedown() {
        if(y<900-speed-140)y += speed;
    }


    public void moveleft() {
        if(x>=speed+10)x -= speed;
    }

    public void moveright() {
        if(x<1500-speed-140)x += speed;
    }
}
