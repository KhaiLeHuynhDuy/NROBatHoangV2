package ListBoss.VoDai;

import BossMain.BossID;
import BossMain.BossesData;
import Player.Player;
import Services.Service;
import Utils.Util;

public class Nguoivohinh extends BossVD {

    private long lastTimeTanHinh;
    private boolean goToPlayer;

    public Nguoivohinh(Player player) throws Exception {
        super(BossID.NGUOIVOHINH, BossesData.NGUOIVOHINH);
        this.playerAtt = player;
        lastTimeTanHinh = System.currentTimeMillis();
    }

    @Override
    public void tanHinh() {
        if (Util.canDoWithTime(lastTimeTanHinh, 15000)) {
            lastTimeTanHinh = System.currentTimeMillis();
        }

        if (!Util.canDoWithTime(this.lastTimeTanHinh, 5000)) {
            Service.gI().setPos2(this, playerAtt.location.x + (Util.getOne(-1, 1) * Util.nextInt(20, 200)),
                    10000);
            goToPlayer = false;
        } else {
            if (!goToPlayer) {
                goToPlayer = true;
                goToPlayer(playerAtt, false);
            }
        }

    }
}
