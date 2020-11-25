package Mod;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import Items.Bomb;
import Maps.Map;

public class Boss extends Mod {
    private int speed;
    private Image image;
    private int imageIndex=0;
    private Random random= new Random();

    public final Image[] MY_BOSS={
            new ImageIcon(getClass().getResource("/images/left_monster.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/right_monster.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/up_monster.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/down_monster.png")).getImage(),
    };

    public Boss(int x, int y, int orient) {
        super(x, y, orient);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void changeOrient(int newOrient){
        orient=newOrient;
    }

    public void creatOrient(){
        int percent= random.nextInt(100);
        if (percent>95){
            int newOrient=random.nextInt(4);
            changeOrient(newOrient);
            image = MY_BOSS[newOrient];
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image,x,y,SIZE,SIZE,null);
    }

    public boolean checkMoveBoom(ArrayList<Bomb> arrBoom){
        for (int i=0;i<arrBoom.size();i++){
            Rectangle rectangle= getRect().intersection(arrBoom.get(i).getRect());
            if (rectangle.isEmpty()==false){
                return false;
            }
        }
        return true;
    }

    public void moveBoss(ArrayList<Map> arrMap, ArrayList<Bomb> arrBomb, int t) {
        int speed = 2;
        int xRaw = x;
        int yRaw = y;
        switch (orient) {
            case LEFT:
                xRaw -= speed;
                break;
            case RIGHT:
                xRaw += speed;
                break;
            case UP:
                yRaw -= speed;
                break;
            case DOWN:
                yRaw += speed;
            default:
        }
        int xRaw1 = x;
        int yRaw1 = y;
        x = xRaw;
        y = yRaw;
        boolean checkMoveBoss = checkMove(arrMap);
        boolean checkMoveBossBoom= checkMoveBoom(arrBomb);
        if (checkMoveBoss==true){
            x=xRaw1;
            y=yRaw1;
        }
        if (checkMoveBossBoom==false){
            x=xRaw1;
            y=yRaw1;
        }
        creatOrient();
    }

    public boolean checkMove(ArrayList<Map> arrBitMap) {
        for (Map bitMap: arrBitMap){
            if (bitMap.bit == 5 || bitMap.bit == 1 ||bitMap.bit == 2 ||bitMap.bit == 3 ||
                    bitMap.bit == 4 || bitMap.bit== 6 ||  bitMap.bit== 7 || bitMap.bit== 8
                    || bitMap.bit== 9) {
                Rectangle rectangle = getRect().intersection(bitMap.getRect());
                if (rectangle.isEmpty() == false) {
                    return true;
                }
            }
        }
        return  false;
    }

    public Rectangle getRect() {
        Rectangle rectangle= new Rectangle(x,y+25,SIZE-10,SIZE-10);
        return rectangle;
    }

}
