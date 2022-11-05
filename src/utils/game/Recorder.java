package utils.game;

import java.io.*;
import java.util.Vector;

/**
 * @author chenxi
 */
public class Recorder {
    public static int score = 0;
    static Vector<EnemyTank> enemyTanks = null;
    static Player player = null;

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static void setPlayer(Player player) {
        Recorder.player = player;
    }


    public Recorder(int score) {
        this.score = score;
    }


    public static int getScore() {
        return score;
    }

    public static int readScore() {
        return score;
    }

    public static void saveGame() {
        ObjectOutputStream bw = null;
        try {
            bw = new ObjectOutputStream(new FileOutputStream(""));
            bw.writeObject(player);
            bw.writeObject(enemyTanks);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void readGame() {
        ObjectInputStream oI = null;
        try {
            oI = new ObjectInputStream(new FileInputStream(""));
            player=(Player) oI.readObject();
            enemyTanks =(Vector<EnemyTank>) oI.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                oI.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
