public abstract class Mod {
    protected int orient;
    protected int x;
    protected int y;
    protected int speed = 2;
    public static int SIZE = 45;

    public Mod(int x, int y, int orient) {
        this.x = x;
        this.y = y;
        this.orient = orient;
    }

}
