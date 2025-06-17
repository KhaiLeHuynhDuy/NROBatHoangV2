package ListBoss.VoDai;

import BossMain.BossID;
import BossMain.BossesData;
import Player.Player;
import Utils.Util;

public class Satan extends BossVD {
    
    private long lastTimeBay;

    public Satan(Player player) throws Exception {
        super(BossID.SATAN, BossesData.SATAN);
        this.playerAtt = player;
    }
    @Override
    public void bayLungTung() {
        if (Util.canDoWithTime(lastTimeBay, 1000)) {
            goToXY(playerAtt.location.x + (Util.getOne(-1, 1) * Util.nextInt(20, 80)), Util.nextInt(10) % 2 == 0 ? playerAtt.location.y : playerAtt.location.y - Util.nextInt(0, 200), false);
            lastTimeBay = System.currentTimeMillis();
        }
    }
}
