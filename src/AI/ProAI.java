package AI;

import Mod.Boss;
import Mod.Player;

import java.util.LinkedList;
import java.util.Queue;

import static Game.GameManager.mapH;
import static Game.GameManager.mapW;
import static Mod.Mod.*;
import static Game.GameManager.arr;
import java.util.Random;

public class ProAI extends AI {
    public static int maxHW = 20;
    public int[][] dirMap = new int[maxHW][maxHW];
    public boolean[][] checkMap = new boolean[maxHW][maxHW];
    public Queue<Pair> myQueue = new LinkedList<>();
    private static int sizeBoss = 35;
    private Random random= new Random();

    public ProAI(Player player, Boss Boss) {
        super(player, Boss);
    }

    public Pair dic(int x, int y, int t) {
        if (t == 0) {
            return new Pair(x-1, y);
        } else if (t == 1) {
            return new Pair(x + 1, y);
        } else if (t == 2) {
            return new Pair(x, y - 1);
        }
        return new Pair(x, y + 1);
    }

    public Pair round(int x, int y) {
        int ansX = x / SIZE;
        int ansY = y / SIZE;
        int xRaw;
        int yRaw;
        if (dirMap[ansY][ansX+1] == LEFT && dirMap[ansY][ansX] != LEFT && dirMap[ansY][ansX] != -1) {
            if (ansX * SIZE + SIZE - x <= sizeBoss) {
                int t = dirMap[ansY][ansX];
                int x1 = dic(ansX, ansY, t).getX();
                int x2 = dic(ansX, ansY, t).getY();
                dirMap[x2][x1] = t;
                return new Pair(ansX * SIZE + SIZE, ansY * SIZE);
            }
        }
        if (dirMap[ansY][ansX] == RIGHT && dirMap[ansY][ansX+1] != RIGHT && dirMap[ansY][ansX+1] != -1) {
            if (x <= ansX * SIZE + SIZE) {
                int t = dirMap[ansY][ansX];
                int x1 = dic(ansX, ansY, t).getX();
                int x2 = dic(ansX, ansY, t).getY();
                dirMap[x2][x1] = t;
                return new Pair(ansX * SIZE, ansY * SIZE);
            }
        }
        if (dirMap[ansY][ansX] == DOWN && dirMap[ansY+1][ansX] != DOWN && dirMap[ansY+1][ansX] != -1) {
            if (y <= ansY * SIZE + SIZE) {
                int t = dirMap[ansY][ansX];
                int x1 = dic(ansX, ansY, t).getX();
                int x2 = dic(ansX, ansY, t).getY();
                dirMap[x2][x1] = t;
                return new Pair(ansX * SIZE, ansY * SIZE);
            }
        }
        if (dirMap[ansY+1][ansX] == UP && dirMap[ansY][ansX] != UP && dirMap[ansY][ansX] != -1) {
            if (y + sizeBoss >= ansY * SIZE + SIZE) {
                int t = dirMap[ansY][ansX];
                int x1 = dic(ansX, ansY, t).getX();
                int x2 = dic(ansX, ansY, t).getY();
                dirMap[x2][x1] = t;
                return new Pair(ansX * SIZE, ansY * SIZE + SIZE);
            }
        }
        return new Pair(player.roundLocation(x), player.roundLocation(y));
    }

    public int changeMove(Player player, Boss boss){
        changeDirection(player, boss);
        /*for (int i = 0; i <= mapH; i++) {
            for (int j = 0; j <= mapW; j++) {
                System.out.print(dirMap[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();*/
        int x = boss.getX();
        int y = boss.getY();
        x = round(x,y).getX()/SIZE;
        y = round(x,y).getY()/SIZE;
        return dirMap[y][x];
    }

    public boolean checkOutsideMap(int x, int y) {
        if (arr[y][x] > 5 || y > mapH || y < 0 || x > mapW || x < 0) {
            return false;
        }
        return true;
    }

    public void changeDirection(Player player, Boss boss) {
        for (int i = 0; i <= mapH; i++) {
            for (int j = 0; j <= mapW; j++) {
                dirMap[i][j] = -1;
                checkMap[i][j] = false;
            }
        }
        int xID = player.roundLocation(player.getX())/SIZE;
        int yID = player.roundLocation(player.getY())/SIZE;
        checkMap[yID][xID] = true;
        myQueue.add(new Pair(xID, yID));
        while (!myQueue.isEmpty()) {
            xID = myQueue.peek().getX();
            yID = myQueue.peek().getY();
            myQueue.remove();
            if (checkOutsideMap(xID + 1, yID) && arr[yID][xID + 1] == 0 && checkMap[yID][xID + 1] == false) {
                dirMap[yID][xID + 1] = LEFT;
                checkMap[yID][xID + 1] = true;
                myQueue.add(new Pair(xID + 1, yID));
            }
            if (checkOutsideMap(xID - 1, yID) && arr[yID][xID - 1] == 0 && checkMap[yID][xID - 1] == false) {
                dirMap[yID][xID - 1] = RIGHT;
                checkMap[yID][xID - 1] = true;
                myQueue.add(new Pair(xID - 1, yID));
            }
            if (checkOutsideMap(xID, yID + 1) && arr[yID + 1][xID] == 0 && checkMap[yID + 1][xID] == false) {
                dirMap[yID + 1][xID] = UP;
                checkMap[yID + 1][xID] = true;
                myQueue.add(new Pair(xID, yID + 1));
            }
            if (checkOutsideMap(xID, yID - 1) && arr[yID - 1][xID] == 0 && checkMap[yID - 1][xID] == false) {
                dirMap[yID - 1][xID] = DOWN;
                checkMap[yID - 1][xID] = true;
                myQueue.add(new Pair(xID, yID - 1));
            }
        }
    }
}
