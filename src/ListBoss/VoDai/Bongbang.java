package ListBoss.VoDai;

import BossMain.BossID;
import BossMain.BossesData;
import Player.Player;

public class Bongbang extends BossVD {

    public Bongbang(Player player) throws Exception {
        super(BossID.BONGBANG, BossesData.BONGBANG);
        this.playerAtt = player;
    }
}