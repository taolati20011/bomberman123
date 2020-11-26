package Mod;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import Items.Bomb;
import Items.Item;
import Maps.Map;
import AI.noobAI;
import AI.ProAI;
import Sound.Sound;

public class Boss extends Mod {
    private Image image;
    private int imageIndex=0;
    private int number;
    private Random random= new Random();
    private boolean proToNoob = false;

    public final Image[] Monster = {
            new ImageIcon(getClass().getResource("/images/left_monster.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/right_monster.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/up_monster.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/down_monster.png")).getImage(),
    };

    public final Image[] iBoss = {
            new ImageIcon(getClass().getResource("/images/boss_left.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/boss_right.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/boss_up.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/boss_down.png")).getImage(),
    };

    public Boss(int x, int y, int orient) {
        super(x, y, orient);
        this.speed = 1;
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

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public void selectAI(int t, Player player) {
        if (t == 0) {
            noobAI ai = new noobAI();
            ai.changeOrient(this);
        } else {
            ProAI ai = new ProAI(player, this);
            if (ai.changeMove(player, this) == -1) {
                selectAI(0, player);
                proToNoob = true;
                return;
            } else {
                proToNoob = false;
            }
            changeOrient(ai.changeMove(player, this));
        }
    }

    public void moveBoss(ArrayList<Map> arrMap, ArrayList<Bomb> arrBomb, Player player, int numAI) {
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
        if (checkMoveBoss){
            x = xRaw1;
            y = yRaw1;
        }
        if (!checkMoveBossBoom) {
            x = xRaw1;
            y = yRaw1;
        }
        selectAI(numAI, player);
        image = iBoss[orient];
    }

    public boolean checkMove(ArrayList<Map> arrBitMap) {
        for (Map bitMap: arrBitMap){
            if (bitMap.bit == 5 || bitMap.bit == 1 ||bitMap.bit == 2 ||bitMap.bit == 3 ||
                    bitMap.bit == 4 || bitMap.bit== 6 ||  bitMap.bit== 7 || bitMap.bit== 8
                    || bitMap.bit== 9) {
                Rectangle rectangle = getRect().intersection(bitMap.getRect());
                if (!rectangle.isEmpty()) {
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

    public void moveItem(ArrayList<Item> arrItem) {
        for (int i = 0; i < arrItem.size(); i++){
            Rectangle rectangle = getRect().intersection(arrItem.get(i).getRect());
            if (!rectangle.isEmpty()){
                if (arrItem.get(i).getBitItem() == 0){
                    arrItem.remove(i);
                }
                else if (arrItem.get(i).getBitItem() == 1){
                    arrItem.remove(i);
                }
                else if (arrItem.get(i).getBitItem() == 2){
                    arrItem.remove(i);
                }
            }
        }
    }

}
