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

    private static final int TIME_BANG = 120;
    private static final int TIME_WAVE = 15;
    private final int SIZE = 45;
    private Random random=new Random();
    public final Image backGround=
            new ImageIcon(getClass().getResource("/images/background.jpg")).getImage();

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
        player = new Player(45, 45, Player.DOWN, 1);
        arrBomb = new ArrayList<>();
        timeBomb = new ArrayList<>();
        timeWave = new ArrayList<>();
        arrWaveBoom= new ArrayList<>();
        arrItem=new ArrayList<>();
        arrMap = new ArrayList<>();
        readTxtMap();
        initItem();
    }

    public void movePlayer(int orient) {
        player.setOrient(orient);
        player.move(arrMap, arrBomb);
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
            waveBoom.draw(g2d,arrMap);
        }
        player.draw(g2d);
    }

    public void MyBomb(int t) {
        Bomb bomb = player.plantBomb();
        arrBomb.add(bomb);
        timeBomb.add(t);
    }

    public boolean AI(int t){
        for (int i=0;i<arrBomb.size();i++){
            if (t-timeBomb.get(i) >=TIME_BANG){
                Explosion ex = arrBomb.get(i).booom();
                arrBomb.remove(i);
                /*Clip clip=Sound.getSound(getClass().getResource("/sounds/boom_bang.wav"));
                clip.start();*/
                arrWaveBoom.add(ex);
                timeBomb.remove(i);
                /*try {
                    ex.checkBoomToBoom(arrBoom, timeBoom);
                }catch (IndexOutOfBoundsException e){
                }
                 */
                timeWave.add(t);
            }
        }
        for (int i=0;i<arrWaveBoom.size();i++){
            //arrWaveBoom.get(i).checkBoomToBoss(arrBoss);
            if (t-timeWave.get(i)>=TIME_WAVE){
                arrWaveBoom.remove(i);
                timeWave.remove(i);
            }
        }
        /*
        if (player.checkDieToBoss(arrBoss) == true){
            clip1.stop();
            setCheckDieWin(false);
            return false;
        }
        */
        for (int i=0;i<arrWaveBoom.size();i++){
            if(arrWaveBoom.get(i).checkBoomToPlayer(arrWaveBoom,arrMap,player)){
                //clip1.stop();
                //timeDie=t;
                //setCheckDieWin(false);
                return false;
            }
        }
        return true;
    }

    public void readTxtMap() {
        File file = new File("src/map.txt");
        int intLine=0;
        try {
            FileInputStream inputStream=new FileInputStream(file);
            BufferedReader reader;
            reader=new BufferedReader(new InputStreamReader(inputStream));
            String line= reader.readLine();
            while (line!=null){
                for (int i=0;i<line.length();i++){
                    arrMap.add(new Map(i*SIZE,intLine*SIZE,Integer.parseInt(String.valueOf(line.charAt(i)))));
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
