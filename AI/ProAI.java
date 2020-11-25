package AI;

import Mod.Boss;
import Mod.Player;

public class ProAI extends AI {
    public ProAI(Player player, Boss Boss) {
        super(player, Boss);
    }

    @Override
    public int changeMove() {
        int px = player.getX();
        int py = player.getY();
        int bx = boss.getX();
        int by = boss.getY();

        return 0;

    }
}
