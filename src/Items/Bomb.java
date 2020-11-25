package Items;

import javax.swing.*;
import java.awt.*;

public class Bomb {
    private int x;
    private int y;
    public int checkBomb;
    private Image image;
    private int imageIndex = 0;
    private int lengthBomb = 1;
    private int SIZE = 45;
    public final Image[] IMAGE_BOOM = {
            new ImageIcon(getClass().getResource("/images/b1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/b1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/b2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/b2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/b3.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/b3.png")).getImage()
    };

    public void setCheckBomb(int checkBomb) {
        this.checkBomb = checkBomb;
    }

    public int getCheckBomb() {
        return checkBomb;
    }

    public Bomb(int x, int y, int lengthBomb) {
        this.x = x;
        this.y = y;
        this.lengthBomb = lengthBomb;
        this.checkBomb = 1;
        this.image = IMAGE_BOOM[0];
        booom();
    }

    public void draw(Graphics2D g2d) {
        imageIndex++;
        image = IMAGE_BOOM[imageIndex / 5 % IMAGE_BOOM.length];
        g2d.drawImage(image, x,y,SIZE,SIZE,null);
    }

    public Rectangle getRect(){
        Rectangle rectangle= new Rectangle(x,y+15,SIZE-10,SIZE-10);
        return  rectangle;
    }

    public Explosion booom() {
        int xRaw = x;
        int yRaw = y + 15;
        int len = this.lengthBomb;
        Explosion ex = new Explosion(x,y,len);
        return ex;
    }

}
