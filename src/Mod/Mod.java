package Mod;

import java.awt.*;

public abstract class Mod {
    protected int orient;
    protected int x;
    protected int y;
    protected int speed = 2;
    public static int SIZE = 45;

    public static final int LEFT=0;
    public static final int RIGHT=1;
    public static final int UP=2;
    public static final int DOWN=3;

    public Mod(int x, int y, int orient) {
        this.x = x;
        this.y = y;
        this.orient = orient;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void draw(Graphics2D g2d);

    public int roundLocation(int x) {
        int ans = x / SIZE;
        ans *= SIZE;
        if (x - ans < ans + SIZE - x) {
            return ans;
        }
        else return ans + SIZE;
    }

}
