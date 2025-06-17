package ListBoss.VoDai;

import BossMain.BossID;
import BossMain.BossesData;
import Player.Player;
import Services.Service;
import Utils.Util;

public class Dracula extends BossVD {
    
     private long lastTimeHutMau = System.currentTimeMillis();

    public Dracula(Player player) throws Exception {
        super(BossID.DRACULA, BossesData.DRACULA);
        this.playerAtt = player;
    }
    @Override
    public void hutMau() {
        try {
            if (Util.canDoWithTime(lastTimeHutMau, 15000) && this.nPoint.hp > this.nPoint.hpMax / 30) {
                long hp = playerAtt.nPoint.hpMax / 10;
                playerAtt.nPoint.subHP(hp);
                this.nPoint.addHp(hp);
                Service.gI().Send_Info_NV(this);
                Service.gI().Send_Info_NV_do_Injure(playerAtt);
                this.chat("Máu ngon quá hehe");
                lastTimeHutMau = System.currentTimeMillis();
            }
        } catch (Exception e) {
        }
    }
}