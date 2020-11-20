package AI;

import Mod.Boss;
import Mod.Player;

import java.util.Random;

public class noobAI extends AI {
    private Random random= new Random();

    public noobAI(Player player, Boss Boss) {
        super(player, Boss);
    }

    @Override
    public int changeMove() {
        return random.nextInt(4);
    }
}
