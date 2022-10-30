package utils.game;

/**
 * @author chenxi
 */
public class Bomb {
    int x,y;
    int life = 9;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //控制动画生命周期
    public void lifeDown(){
        life -- ;
    }

}
