package Mod;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Maps.Map;
import Items.Bomb;
import Items.Item;
import Sound.Sound;

public class Player extends Mod {
    private int timeMove;
    private boolean isPlayerRun = false;
    private int imageIndex = 0;
    private int lenghtBoomBang = 1;
    private int bombCount = 1;
    private int bombLimit = 4;
    private int speedLimit = 3;

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

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setLenghtBoomBang(int lenghtBoomBang) {
        this.lenghtBoomBang = lenghtBoomBang;
    }

    public int getLenghtBoomBang() {
        return lenghtBoomBang;
    }

    public boolean isIrun() {
        return isPlayerRun;
    }

    @Override
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

    public boolean checkPlayerToBoss(ArrayList<Boss> arrBoss) {
        for (Boss boss : arrBoss) {
            Rectangle rectangle = getRect().intersection(boss.getRect());
            if (!rectangle.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void moveItem(ArrayList<Item> arrItem){
        for (int i = 0; i < arrItem.size(); i++){
            Rectangle rectangle = getRect().intersection(arrItem.get(i).getRect());
            if (!rectangle.isEmpty()){
                if (arrItem.get(i).getBitItem() == 0){
                    arrItem.remove(i);
                    if (getBombCount() < bombLimit) {
                        setBombCount(bombCount+1);
                        System.out.println("Số Boom: "+ getBombCount());
                    }
                }
                else if (arrItem.get(i).getBitItem() == 1){
                    setLenghtBoomBang(lenghtBoomBang+1);
                    System.out.println("Độ dài Boom: "+ getLenghtBoomBang());
                    arrItem.remove(i);
                }
                else if (arrItem.get(i).getBitItem() == 2){
                    arrItem.remove(i);
                    if (speed < speedLimit) {
                        setSpeed(speed+1);
                        System.out.println("Tốc độ: "+speed);
                    }
                }
                Clip item = Sound.getSound(getClass().getResource("/Sound/item.wav"));
                item.start();
            }
        }
    }
}
