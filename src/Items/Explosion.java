package Items;

import Mod.Boss;
import Mod.Player;
import Maps.Map;
import Sound.Sound;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Explosion {
    private int x;
    private int y;
    public int length = 3;
    private int lenLeft = 3;
    private int lenRight = length;
    private int lenUp = length;
    private int lenDown = length;
    private int xBossDie;
    private int yBossDie;
    private int imageIndex = 0;
    private static final int SIZE = 45;

    public final Image[] Ex = {
            new ImageIcon(getClass().getResource("/images/bombbang_left_2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/bombbang_right_2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/bombbang_up_2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/bombbang_down_2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/bombbang_mid_2.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/bombbang_left_1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/bombbang_right_1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/bombbang_up_1.png")).getImage(),
            new ImageIcon(getClass().getResource("/images/bombbang_down_1.png")).getImage(),
    };

    public Explosion(int x, int y,int lenghWave) {
        this.x = x;
        this.y = y;
        this.lenLeft = lenghWave;
        this.lenRight = lenghWave;
        this.lenDown = lenghWave;
        this.lenUp = lenghWave;
    }

    public void draw(Graphics2D g2d, ArrayList<Map> arrBitMap){
        drawMid(g2d, arrBitMap);
        drawLeft(g2d, arrBitMap);
        drawRight(g2d, arrBitMap);
        drawUp(g2d, arrBitMap);
        drawDown(g2d, arrBitMap);
        /*if (xBossDie!=0 || yBossDie!=0) {
            imageIndex++;
            Image image= BOSS_DIE[imageIndex/50%BOSS_DIE.length];
            g2d.drawImage(image, xBossDie, yBossDie, null);
        }*/
    }

    public Rectangle getRect(int x,int y) {
        Rectangle rectangle = new Rectangle(x, y + 15, SIZE-10, SIZE-10);
        return rectangle;
    }

    public boolean checkBoomToMap(ArrayList<Map> arrMap, int xRaw, int yRaw) {
        for (int d = 0; d < arrMap.size(); d++) {
            if (arrMap.get(d).getBit() == 0) {
                continue;
            }
            Rectangle recMap = getRect(xRaw, yRaw).intersection(arrMap.get(d).getRect());
            if (!recMap.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoomToItem(Explosion waveBomb, Item anItem, ArrayList<Map> arrMap) {
        for (int j = 1; j <= lenLeft; j++) {
            boolean check = false;
            int xRaw = x - j * SIZE;
            int yRaw = y;
            Rectangle rectangle = getRect(xRaw, yRaw).intersection(anItem.getRect());
            if (!rectangle.isEmpty()) {
                System.out.println(anItem.timeItem);
                for (int i = 0; i < arrMap.size(); i++) {
                    Rectangle rec = arrMap.get(i).getRect().intersection(anItem.getRect());
                    if (!rec.isEmpty()) {
                        return true;
                    }
                }
                if (anItem.timeItem > 0) {
                    anItem.setTimeItem(anItem.timeItem-1);
                }
                else return false;
            }
        }
        for (int j = 1; j <= lenRight; j++) {
            boolean check = false;
            int xRaw = x + j * SIZE;
            int yRaw = y;
            Rectangle rectangle = getRect(xRaw, yRaw).intersection(anItem.getRect());
            if (!rectangle.isEmpty()) {
                System.out.println(anItem.timeItem);
                for (int i = 0; i < arrMap.size(); i++) {
                    Rectangle rec = arrMap.get(i).getRect().intersection(anItem.getRect());
                    if (!rec.isEmpty()) {
                        return true;
                    }
                }
                if (anItem.timeItem > 0) {
                    anItem.setTimeItem(anItem.timeItem-1);
                }
                else return false;
            }
        }
        for (int j = 1; j <= lenUp; j++) {
            boolean check = false;
            int xRaw = x;
            int yRaw = y - j * SIZE;
            Rectangle rectangle = getRect(xRaw, yRaw).intersection(anItem.getRect());
            if (!rectangle.isEmpty()) {
                System.out.println(anItem.timeItem);
                for (int i = 0; i < arrMap.size(); i++) {
                    Rectangle rec = arrMap.get(i).getRect().intersection(anItem.getRect());
                    if (!rec.isEmpty()) {
                        return true;
                    }
                }
                if (anItem.timeItem > 0) {
                    anItem.setTimeItem(anItem.timeItem-1);
                }
                else return false;
            }
        }
        for (int j = 1; j <= lenDown; j++) {
            boolean check = false;
            int xRaw = x;
            int yRaw = y + j * SIZE;
            Rectangle rectangle = getRect(xRaw, yRaw).intersection(anItem.getRect());
            if (!rectangle.isEmpty()) {
                System.out.println(anItem.timeItem);
                for (int i = 0; i < arrMap.size(); i++) {
                    Rectangle rec = arrMap.get(i).getRect().intersection(anItem.getRect());
                    if (!rec.isEmpty()) {
                        return true;
                    }
                }
                if (anItem.timeItem > 0) {
                    anItem.setTimeItem(anItem.timeItem-1);
                }
                else return false;
            }
        }
        return true;
    }

    public void checkBoomToBomb(ArrayList<Bomb> arrBomb, ArrayList<Integer> timeBomb, ArrayList<Map> arrMap) {
        for (int i = 0; i < arrBomb.size(); i++) {
            Rectangle rectangle = getRect(x,y).intersection(arrBomb.get(i).getRect());
            if (!rectangle.isEmpty()) {
                timeBomb.set(i,0);
            }
            for (int j = 1; j <= lenLeft; j++) {
                int xRaw = x - j * SIZE;
                int yRaw = y;
                if (checkBoomToMap(arrMap, xRaw, yRaw)) {
                    break;
                }
                Rectangle rectangle1 = getRect(xRaw, yRaw).intersection(arrBomb.get(i).getRect());
                if (!rectangle1.isEmpty()) {
                    timeBomb.set(i,0);
                }
            }
            for (int j = 1; j <= lenRight; j++) {
                int xRaw = x + j * SIZE;
                int yRaw = y;
                if (checkBoomToMap(arrMap, xRaw, yRaw)) {
                    break;
                }
                Rectangle rectangle1 = getRect(xRaw, yRaw).intersection(arrBomb.get(i).getRect());
                if (!rectangle1.isEmpty()) {
                    timeBomb.set(i,0);
                }
            }
            for (int j = 1; j <= lenUp; j++) {
                int xRaw = x;
                int yRaw = y - j * SIZE;
                if (checkBoomToMap(arrMap, xRaw, yRaw)) {
                    break;
                }
                Rectangle rectangle1 = getRect(xRaw, yRaw).intersection(arrBomb.get(i).getRect());
                if (!rectangle1.isEmpty()) {
                    timeBomb.set(i,0);
                }
            }
            for (int j = 1; j <= lenDown; j++) {
                int xRaw = x;
                int yRaw = y + j * SIZE;
                if (checkBoomToMap(arrMap, xRaw, yRaw)) {
                    break;
                }
                Rectangle rectangle1 = getRect(xRaw, yRaw).intersection(arrBomb.get(i).getRect());
                if (!rectangle1.isEmpty()) {
                    timeBomb.set(i,0);
                }
            }
        }
    }

    public boolean checkBoomToPlayer(Explosion waveBomb, ArrayList<Map> arrMap, Player player) {
        Rectangle rectangle = getRect(x, y).intersection(player.getRect());
        if (!rectangle.isEmpty()) {
            return true;
        }
        for (int j = 1; j <= lenLeft; j++){
            int xRaw = x - j * SIZE;
            int yRaw = y;
            if (checkBoomToMap(arrMap, xRaw, yRaw)) {
                break;
            }
            Rectangle rec = getRect(xRaw, yRaw).intersection(player.getRect());
            if (!rec.isEmpty()){
                return true;
            }
        }
        for (int j = 1; j <= lenRight; j++){
            int xRaw = x + j * SIZE;
            int yRaw = y;
            if (checkBoomToMap(arrMap, xRaw, yRaw)) {
                break;
            }
            Rectangle rec = getRect(xRaw, yRaw).intersection(player.getRect());
            if (!rec.isEmpty()){
                return true;
            }
        }
        for (int j = 1; j <= lenUp; j++){
            int xRaw = x;
            int yRaw = y - j * SIZE;
            if (checkBoomToMap(arrMap, xRaw, yRaw)) {
                break;
            }
            Rectangle rec = getRect(xRaw, yRaw).intersection(player.getRect());
            if (!rec.isEmpty()){
                return true;
            }
        }
        for (int j = 1; j <= lenDown; j++){
            int xRaw = x;
            int yRaw = y + j * SIZE;
            if (checkBoomToMap(arrMap, xRaw, yRaw)) {
                break;
            }
            Rectangle rec = getRect(xRaw, yRaw).intersection(player.getRect());
            if (rec.isEmpty()==false){
                return true;
            }
        }
    return false;
    }

    public void checkBoomToBoss(ArrayList<Boss> arrBoss) {
        for (int i = 0; i < arrBoss.size(); i++) {
            try {
                Rectangle rectangle1= getRect(x,y).intersection(arrBoss.get(i).getRect());
                if (rectangle1.isEmpty()== false){
                    xBossDie = arrBoss.get(i).getX();
                    yBossDie =arrBoss.get(i).getY();
                    arrBoss.remove(i);
                    Clip bangBang = Sound.getSound(getClass().getResource("/Sound/bang_bang.wav"));
                    bangBang.start();
                }
                for (int j = 1; j <= lenLeft; j++) {
                    int xRaw = x - j * SIZE;
                    int yRaw = y;
                    Rectangle rectangle0 = getRect(xRaw, yRaw).intersection(arrBoss.get(i).getRect());
                    if (rectangle0.isEmpty() == false) {
                        xBossDie= arrBoss.get(i).getX();
                        yBossDie=arrBoss.get(i).getY();
                        arrBoss.remove(i);
                        Clip bangBang = Sound.getSound(getClass().getResource("/Sound/bang_bang.wav"));
                        bangBang.start();
                    }
                }
                for (int j = 1; j <= lenRight; j++) {
                    int xRaw = x + j * SIZE;
                    int yRaw = y;
                    Rectangle rectangle = getRect(xRaw, yRaw).intersection(arrBoss.get(i).getRect());
                    if (rectangle.isEmpty() == false) {
                        xBossDie= arrBoss.get(i).getX();
                        yBossDie=arrBoss.get(i).getY();
                        arrBoss.remove(i);
                        Clip bangBang = Sound.getSound(getClass().getResource("/Sound/bang_bang.wav"));
                        bangBang.start();
                    }
                }
                for (int j = 1; j <= lenUp; j++) {
                    int xRaw = x;
                    int yRaw = y - j * SIZE;
                    Rectangle rectangle = getRect(xRaw, yRaw).intersection(arrBoss.get(i).getRect());
                    if (rectangle.isEmpty() == false) {
                        xBossDie= arrBoss.get(i).getX();
                        yBossDie=arrBoss.get(i).getY();
                        arrBoss.remove(i);
                        Clip bangBang = Sound.getSound(getClass().getResource("/Sound/bang_bang.wav"));
                        bangBang.start();

                    }
                }
                for (int j = 1; j <= lenDown; j++) {
                    int xRaw = x;
                    int yRaw = y + j * SIZE;
                    Rectangle rectangle = getRect(xRaw, yRaw).intersection(arrBoss.get(i).getRect());
                    if (rectangle.isEmpty() == false) {
                        xBossDie= arrBoss.get(i).getX();
                        yBossDie=arrBoss.get(i).getY();
                        arrBoss.remove(i);
                        Clip bangBang = Sound.getSound(getClass().getResource("/Sound/bang_bang.wav"));
                        bangBang.start();
                    }
                }
            }catch (IndexOutOfBoundsException e){
            }
        }
    }

    public void drawMid(Graphics2D g2d, ArrayList<Map> arrBitMap) {
        g2d.drawImage(Ex[4], x, y, null);
    }

    public void drawLeft(Graphics2D g2d, ArrayList<Map> arrBitMap) {
        boolean check = false;
        for (int i = 1; i <= lenLeft; i++) {
            int xRaw = x - i * SIZE;
            int yRaw = y;
            for (int j = 0; j < arrBitMap.size(); j++) {
                Rectangle rectangle = getRect(xRaw, yRaw).intersection(arrBitMap.get(j).getRect());
                if (!rectangle.isEmpty()) {
                    if (arrBitMap.get(j).bit == 2 || arrBitMap.get(j).bit == 4
                            || arrBitMap.get(j).bit == 5 ) {
                        arrBitMap.remove(j);
                        lenLeft = lenLeft - (lenLeft - i);
                    } else if (arrBitMap.get(j).bit != 0) {
                        check = true;
                    }
                }
            }
            if (check) {
                break;
            }
            if (i == lenLeft) {
                g2d.drawImage(Ex[0], xRaw, yRaw, null);
            } else {
                g2d.drawImage(Ex[5], xRaw, yRaw, null);
            }
        }
    }

    public void drawRight(Graphics2D g2d, ArrayList<Map> arrBitMap) {
        boolean check = false;
        for (int i = 1; i <= lenRight; i++) {
            int xRaw = x + i * SIZE;
            int yRaw = y;
            for (int j = 0; j < arrBitMap.size(); j++) {
                Rectangle rectangle = getRect(xRaw, yRaw).intersection(arrBitMap.get(j).getRect());
                if (!rectangle.isEmpty()) {
                    if (arrBitMap.get(j).bit == 2 || arrBitMap.get(j).bit == 4
                            || arrBitMap.get(j).bit == 5 ) {
                        arrBitMap.remove(j);
                        lenRight = lenRight - (lenRight - i);
                    } else if (arrBitMap.get(j).bit != 0) {
                        check = true;
                    }
                }
            }
            if (check) {
                break;
            }
            if (i == lenRight) {
                g2d.drawImage(Ex[1], xRaw, yRaw, null);
            } else {
                g2d.drawImage(Ex[6], xRaw, yRaw, null);
            }
        }
    }

    public void drawUp(Graphics2D g2d, ArrayList<Map> arrBitMap) {
        boolean check = false;
        for (int i = 1; i <= lenUp; i++) {
            int xRaw = x;
            int yRaw = y - i * SIZE;
            for (int j = 0; j < arrBitMap.size(); j++) {
                Rectangle rectangle = getRect(xRaw, yRaw).intersection(arrBitMap.get(j).getRect());
                if (!rectangle.isEmpty()) {
                    if (arrBitMap.get(j).bit == 2 || arrBitMap.get(j).bit == 4
                            || arrBitMap.get(j).bit == 5 ) {
                        arrBitMap.remove(j);
                        lenUp = lenUp - (lenUp - i);
                    } else if (arrBitMap.get(j).bit != 0) {
                        check = true;
                    }
                }
            }
            if (check) {
                break;
            }
            if (i == lenUp) {
                g2d.drawImage(Ex[2], xRaw, yRaw, null);
            } else {
                g2d.drawImage(Ex[7], xRaw, yRaw, null);
            }
        }
    }

    public void drawDown(Graphics2D g2d, ArrayList<Map> arrBitMap) {
        boolean check = false;
        for (int i = 1; i <= lenDown; i++) {
            int xRaw = x;
            int yRaw = y + i * SIZE;
            for (int j = 0; j < arrBitMap.size(); j++) {
                Rectangle rectangle = getRect(xRaw, yRaw).intersection(arrBitMap.get(j).getRect());
                if (!rectangle.isEmpty()) {
                    if (arrBitMap.get(j).bit == 2 || arrBitMap.get(j).bit == 4
                            || arrBitMap.get(j).bit == 5 ) {
                        arrBitMap.remove(j);
                        lenDown = lenDown - (lenDown - i);
                    } else if (arrBitMap.get(j).bit != 0) {
                        check = true;
                    }
                }
            }
            if (check) {
                break;
            }
            if (i == lenDown) {
                g2d.drawImage(Ex[3], xRaw, yRaw, null);
            } else {
                g2d.drawImage(Ex[8], xRaw, yRaw, null);
            }
        }
    }
}
