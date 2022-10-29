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
    public void lifeDown(){
        life -- ;
    }

}
