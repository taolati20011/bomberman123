package AI;

import Mod.Boss;
import Mod.Player;

import java.util.Random;

public class noobAI extends AI {
    private Random random= new Random();

    public noobAI() {}

    public noobAI(Player player, Boss Boss) {
        super(player, Boss);
    }

    public void changeOrient(Boss boss) {
        int percent= random.nextInt(100);
        if (percent>95){
            int newOrient=random.nextInt(4);
            boss.changeOrient(newOrient);
        }
    }
}
