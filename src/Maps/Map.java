package Maps;

import javax.swing.*;
import java.awt.*;

public class Map {
    private int x;
    private int y;
    public int bit;
    public static final int SIZE = 45;
    public final Image[] MY_IMAGE = {
            new ImageIcon(getClass().getResource("/images/goccay1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/SandBlockYellow.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/da1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/TownBlockRed.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/TownBlockYellow.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/vienDuoi.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/vienTren.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/vienPhai.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/vienTrai.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/background.jpg")).getImage(),
            new ImageIcon(getClass().getResource("/images/gocTrenTrai.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/gocTrenPhai.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/gocDuoiTrai.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/gocDuoiPhai.png")).getImage(),
    };

    public Map(int x, int y, int bit) {
        this.x = x;
        this.y = y;
        this.bit = bit;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBit() {
        return bit;
    }

    public void draw(Graphics2D g2d) {
        if (bit !=0) {
            g2d.drawImage(MY_IMAGE[bit - 1], x,y,SIZE,SIZE,null);
        }
        if (x== 0&& y==0){
            g2d.drawImage(MY_IMAGE[10],x,y,null);
        }
        if (x== 16*SIZE && y==0){
            g2d.drawImage(MY_IMAGE[11],x,y,null);
        }
        if (x== 0 && y==14*SIZE){
            g2d.drawImage(MY_IMAGE[12],x,y,null);
        }
        if (x== 16*SIZE && y== 14*SIZE){
            g2d.drawImage(MY_IMAGE[13],x,y,null);
        }
    }

    public Rectangle getRect() {
        Rectangle rectangle = new Rectangle(x, y+15, SIZE-10, SIZE-10);
        return rectangle;
    }
}
