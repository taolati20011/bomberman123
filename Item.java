import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Item {
    private int x;
    private int y;
    private int bitItem;
    private final int SIZE = 45;
    private Image image;
    private Random random=new Random();
    public final Image[] ITEM_IMAGE={
            new ImageIcon(getClass().getResource("/images/item_bomb.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/item_bombsize.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/item_shoe.png")).getImage(),
    };

    public Item(int x, int y) {
        int rd = random.nextInt(3);
        this.x = x;
        this.y = y;
        this.bitItem = rd;
        this.image = ITEM_IMAGE[rd];
    }

    public Rectangle getRect() {
        Rectangle rectangle = new Rectangle(x,y,SIZE, SIZE);
        return rectangle;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x+6, y, SIZE - 10, SIZE - 10, null);
    }
}
