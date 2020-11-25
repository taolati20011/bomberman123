package AI;

import Mod.Boss;
import Mod.Player;

public abstract class AI {
    protected Player player;
    protected Boss boss;

    public AI(Player player, Boss Boss) {
        this.player = player;
        this.boss = boss;
    }

    public abstract int changeMove();
}
