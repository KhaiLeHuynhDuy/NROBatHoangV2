package ListBoss.VoDai;

import BossMain.BossID;
import BossMain.BossesData;
import Player.Player;

public class Thodaubac extends BossVD {

    public Thodaubac(Player player) throws Exception {
        super(BossID.THODAUBAC, BossesData.THODAUBAC);
        this.playerAtt = player;
    }
}