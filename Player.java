import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player extends Mod {
    private int timeMove;
    public static final int LEFT=0;
    public static final int RIGHT=1;
    public static final int UP=2;
    public static final int DOWN=3;
    private boolean isPlayerRun = false;
    private int imageIndex = 0;
    private int lenghtBoomBang = 2;

    public final Image[] IMAGES_PLAYER_LEFT= {
            new ImageIcon(getClass().getResource("/images/player_left_1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_left_2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_left_3.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_left_4.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_left_5.png")).getImage(),
    };
    public final Image[] IMAGES_PLAYER_RIGHT= {
            new ImageIcon(getClass().getResource("/images/player_right_1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_right_2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_right_3.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_right_4.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_right_5.png")).getImage(),
    };
    public final Image[] IMAGES_PLAYER_UP= {
            new ImageIcon(getClass().getResource("/images/player_up_1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_up_2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_up_3.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_up_4.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_up_5.png")).getImage(),
    };
    public final Image[] IMAGES_PLAYER_DOWN= {
            new ImageIcon(getClass().getResource("/images/player_down_1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_down_2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_down_3.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_down_4.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/player_down_5.png")).getImage(),
    };

    public Player(int x, int y, int orient, int timeMove) {
        super(x, y, orient);
        this.timeMove = timeMove;
    }

    public void setOrient(int orient) {
        super.orient = orient;
        isPlayerRun = true;
    }

    public boolean isIrun() {
        return isPlayerRun;
    }

    public void draw(Graphics2D g2d) {
        switch (orient){
            case LEFT:{
                if (!isIrun()){
                    g2d.drawImage(IMAGES_PLAYER_LEFT[0],x,y,SIZE+5,SIZE+5,null);
                }
                else {
                    imageIndex++;
                    g2d.drawImage(IMAGES_PLAYER_LEFT[imageIndex / 10 % IMAGES_PLAYER_LEFT.length], x, y,SIZE+5,SIZE+5, null);
                }
                break;
            }
            case RIGHT:{
                if (!isIrun()){
                    g2d.drawImage(IMAGES_PLAYER_RIGHT[0],x,y,SIZE+5,SIZE+5,null);
                }
                else {
                    imageIndex++;
                    g2d.drawImage(IMAGES_PLAYER_RIGHT[imageIndex / 10 % IMAGES_PLAYER_RIGHT.length], x, y, SIZE+5,SIZE+5,null);
                }
                break;
            }
            case UP:{
                if (!isIrun()){
                    g2d.drawImage(IMAGES_PLAYER_UP[0],x,y,SIZE+5,SIZE+5,null);
                }
                else {
                    imageIndex++;
                    g2d.drawImage(IMAGES_PLAYER_UP[imageIndex / 10 % IMAGES_PLAYER_UP.length], x, y,SIZE+5,SIZE+5, null);
                }
                break;
            }
            case DOWN:{
                if (!isIrun()){
                    g2d.drawImage(IMAGES_PLAYER_DOWN[0],x,y,SIZE+5,SIZE+5,null);
                }
                else {
                    imageIndex++;
                    g2d.drawImage(IMAGES_PLAYER_DOWN[imageIndex / 10 % IMAGES_PLAYER_LEFT.length], x, y,SIZE+5,SIZE+5,null);
                }
            }
            break;
            default:
        }
        isPlayerRun=false;
        imageIndex++;
    }

    public void setMoveBomb(ArrayList<Bomb> arrBoom){
        for (int i=0;i<arrBoom.size();i++){
            Rectangle rectangle= getRect().intersection(arrBoom.get(i).getRect());
            if (rectangle.isEmpty() == true){
                arrBoom.get(i).setCheckBomb(0);
            }
        }
    }

    public boolean checkMoveBomb(ArrayList<Bomb> arrBoom){
        setMoveBomb(arrBoom);
        for (int i=0;i<arrBoom.size();i++){
            Rectangle rectangle= getRect().intersection(arrBoom.get(i).getRect());
            if (rectangle.isEmpty()==false && arrBoom.get(i).getCheckBomb()==0){
                return false;
            }
        }
        return true;
    }

    public void move(ArrayList <Map> arrMap, ArrayList <Bomb> arrBomb) {
        int xRaw = x;
        int yRaw = y;
        switch (orient) {
            case LEFT:
                xRaw -= speed;
                break;
            case RIGHT:
                xRaw += speed;
                break;
            case DOWN:
                yRaw += speed;
                break;
            case UP:
                yRaw -= speed;
                break;
            default:
        }
        int xRaw1=x;
        int yRaw1=y;
        x=xRaw;
        y=yRaw;
        boolean checkMovePlayer = checkMoveMap(arrMap);
        boolean checkMovetoBomb = checkMoveBomb(arrBomb);
        if (checkMovePlayer==true ){
            x = xRaw1;
            y = yRaw1;
        }

        if (!checkMovetoBomb) {
            x = xRaw1;
            y = yRaw1;
        }
    }

    public int roundLocation(int x) {
        int ans = x / SIZE;
        ans *= SIZE;
        if (x - ans < ans + SIZE - x) {
            return ans;
        }
        else return ans + SIZE;
    }

    public Bomb plantBomb() {
        int xBoom= roundLocation(this.x);
        int yBoom= roundLocation(this.y);
        int lengBoom =this.lenghtBoomBang;
        Bomb bomb = new Bomb(xBoom, yBoom,lengBoom);
        return bomb;
    }

    public Rectangle getRect() {
        Rectangle rectangle = new Rectangle(x, y+25, SIZE-10, SIZE-10);
        return rectangle;
    }

    public boolean checkMoveMap(ArrayList<Map> arrMap) {
        for (Map map : arrMap) {
            if (map.bit >= 1 && map.bit <= 9) {
                Rectangle rectangle = getRect().intersection(map.getRect());
                if (!rectangle.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
