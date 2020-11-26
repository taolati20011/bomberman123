package Game;

import AI.ProAI;
import Items.Bomb;
import Items.Explosion;
import Items.Item;
import Mod.Boss;
import Mod.Player;
import Maps.Map;
import Sound.Sound;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    private Player player;
    private ArrayList<Bomb> arrBomb;
    private ArrayList<Map> arrMap;
    private ArrayList<Explosion> arrWaveBoom;
    private ArrayList<Integer> timeBomb;
    private ArrayList<Integer> timeWave;
    private ArrayList<Item> arrItem;
    private ArrayList<Boss> arrBoss;
    public static int mapH = 14;
    public static int mapW = 16;
    public static int[][] arr = new int[20][20];

    private boolean checkDieWin;
    private static final int TIME_BANG = 120;
    private static final int TIME_WAVE = 15;
    private final int SIZE = 45;
    private Random random=new Random();
    public final Image backGround=
            new ImageIcon(getClass().getResource("/images/background.jpg")).getImage();

    public Clip soundGame;

    public void setCheckDieWin(boolean checkDieWin) {
        this.checkDieWin = checkDieWin;
    }

    public void initItem(){
        for (int i = 0; i < arrMap.size(); i++) {
            int show = random.nextInt(100) + 1;
            if (show > 80 && (arrMap.get(i).bit == 2
                    || arrMap.get(i).bit == 4 || arrMap.get(i).bit == 5)) {
                int xRaw = arrMap.get(i).getX();
                int yRaw = arrMap.get(i).getY();
                Item item = new Item(xRaw, yRaw);
                arrItem.add(item);
            }
        }
    }

    public void initGame() {
        arrMap = new ArrayList<>();
        readTxtMap();
        arrBoss = new ArrayList<>();
        initBoss();
        player = new Player(45, 45, Player.DOWN, 1);
        arrItem=new ArrayList<>();
        initItem();
        arrBomb = new ArrayList<>();
        timeBomb = new ArrayList<>();
        timeWave = new ArrayList<>();
        arrWaveBoom= new ArrayList<>();
        Clip start = Sound.getSound(getClass().getResource("/Sound/start.wav"));
        start.start();
        soundGame = Sound.getSound(getClass().getResource("/Sound/soundGame.wav"));
        soundGame.start();
        soundGame.loop(100);
        System.out.println("Số Boom: " + player.getBombCount());
        System.out.println("Độ dài Boom: " + player.getLenghtBoomBang());
        System.out.println("Tốc độ: " + player.getSpeed());
    }

    public void initBoss() {
        int orient = random.nextInt(4);
        Boss boss0 = new Boss(45, 630-45,orient);
        arrBoss.add(boss0);
        int orient1 = random.nextInt(4);
        Boss boss1 = new Boss(675,45,orient1);
        arrBoss.add(boss1);
        int orient2 = random.nextInt(4);
        Boss boss2 = new Boss(675, 585, orient2);
        arrBoss.add(boss2);
    }

    public void movePlayer(int orient) {
        player.setOrient(orient);
        player.move(arrMap, arrBomb);
        player.moveItem(arrItem);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(backGround, 0 , 0, 765, 705, null);
        for (Bomb bomb : arrBomb) {
            bomb.draw(g2d);
        }
        for (Item item:arrItem){
            item.draw(g2d);
        }
        for (Map map : arrMap) {
            map.draw(g2d);
        }
        for (Explosion waveBoom : arrWaveBoom) {
            waveBoom.draw(g2d, arrMap);
        }
        for (Boss boss : arrBoss) {
            boss.draw(g2d);
        }
        player.draw(g2d);
    }

    public boolean isCheckDieWin() {
        return checkDieWin;
    }

    public void MyBomb(int t) {
        Bomb bomb = player.plantBomb();
        if (arrBomb.size() < player.getBombCount()) {
            Clip plantBomb = Sound.getSound(getClass().getResource("/Sound/set_boom.wav"));
            plantBomb.start();
            arrBomb.add(bomb);
            int x = player.roundLocation(player.getX())/SIZE;
            int y = player.roundLocation(player.getY())/SIZE;
            arr[y][x] = 10;
            timeBomb.add(t);
        }
    }

    public boolean AI(int t) {
        for (int i = 0; i < arrBoss.size(); i++){
            arrBoss.get(i).moveBoss(arrMap,arrBomb,player,1);
        }
        if (arrBoss.size() == 2) {
            for (Boss boss : arrBoss) {
                boss.setSpeed(2);
            }
        }

        for (int i=0;i<arrBomb.size();i++){
            if (t-timeBomb.get(i) >=TIME_BANG){
                Explosion ex = arrBomb.get(i).booom();
                arrBomb.remove(i);
                Clip boomBang = Sound.getSound(getClass().getResource("/Sound/boom_bang.wav"));
                boomBang.start();
                arrWaveBoom.add(ex);
                timeBomb.remove(i);
                try {
                    ex.checkBoomToBomb(arrBomb, timeBomb, arrMap);
                }catch (IndexOutOfBoundsException e){
                }
                timeWave.add(t);
            }
        }
        for (int i=0;i<arrWaveBoom.size();i++){
            arrWaveBoom.get(i).checkBoomToBoss(arrBoss, arrMap);
            if (t-timeWave.get(i)>=TIME_WAVE){
                int x = player.roundLocation(arrWaveBoom.get(i).getX())/SIZE;
                int y = player.roundLocation(arrWaveBoom.get(i).getY())/SIZE;
                arr[y][x] = 0;
                arrWaveBoom.remove(i);
                timeWave.remove(i);
            }
        }
        if (!player.checkPlayerToBoss(arrBoss)){
            soundGame.stop();
            setCheckDieWin(false);
            return false;
        }
        for (int i = 0; i < arrWaveBoom.size(); i++) {
            for (int j = 0; j < arrItem.size(); j++) {
                if (!arrWaveBoom.get(i).checkBoomToItem(arrWaveBoom.get(i), arrItem.get(j), arrMap)) {
                    arrItem.remove(j);
                }
            }
        }
        for (int i=0;i<arrWaveBoom.size();i++){
            if(arrWaveBoom.get(i).checkBoomToPlayer(arrWaveBoom.get(i),arrMap,player)){
                soundGame.stop();
                //timeDie=t;
                setCheckDieWin(false);
                return false;
            }
        }
        if (arrBoss.isEmpty()) {
            setCheckDieWin(true);
            soundGame.stop();
            return false;
        }
        return true;
    }

    public void readTxtMap() {
        File file = new File("src/Maps/map.txt");
        int intLine=0;
        try {
            FileInputStream inputStream=new FileInputStream(file);
            BufferedReader reader;
            reader=new BufferedReader(new InputStreamReader(inputStream));
            String line= reader.readLine();
            while (line!=null){
                for (int i=0;i<line.length();i++){
                    arrMap.add(new Map(i*SIZE,intLine*SIZE,Integer.parseInt(String.valueOf(line.charAt(i)))));
                    arr[intLine][i] = Integer.parseInt(String.valueOf(line.charAt(i)));
                }
                line= reader.readLine();
                intLine++;
            }
            reader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
