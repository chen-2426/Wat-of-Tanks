package utils.game;

/**
 * @author chenxi
 */
public class Tank {
    //坦克坐标；
    private int x;
    private int y;
    private int direction;//坦克方向  0-向上，1-向右，2-向下，3-向左
    private final int speed = 5;
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

    public Tank(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
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

    public int getY() {
        return y;
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
    public boolean collisionVolume(Tank tank2){

        if(this.getDirection()==0){
            int x = this.getX();
            int y = this.getY()-15-speed;
            return !updownJudgement(tank2, x, y);
        } else if (this.getDirection()==1) {
            int x = this.getX()+95+speed;
            int y = this.getY();
            return !leftrightJudgement(tank2, x, y);
        } else if (this.getDirection()==2) {
            int x = this.getX();
            int y = this.getY()+95+speed;
            return !updownJudgement(tank2, x, y);
        } else if (this.getDirection()==3) {
            int x = this.getX()-15-speed;
            int y = this.getY();
            return !leftrightJudgement(tank2, x, y);
        }
        return true;
    }

    private boolean leftrightJudgement(Tank tank2, int x, int y) {
        if(x>tank2.getX() && x<tank2.getX()+95){
            return (y > tank2.getY() && y < tank2.getY() + 80) || (y + 80 > tank2.getY() && y + 80 < tank2.getY() + 80);
        }
        return false;
    }

    private boolean updownJudgement(Tank tank2, int x, int y) {
        if(y>tank2.getY() && y<tank2.getY()+95){
            return (x > tank2.getX() && x < tank2.getX() + 80) || (x + 80 > tank2.getX() && x + 80 < tank2.getX() + 80);
        }
        return false;
    }
}
