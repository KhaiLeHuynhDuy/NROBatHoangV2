package ListBoss.MainBoss;

import BossMain.Boss;
import BossMain.BossID;
import BossMain.BossesData;
import Maps.ItemMap;
import Player.Player;
import Services.Service;
import Services.TaskService;
import Utils.Util;
import java.util.Random;

/**
 *
 * @author Administrator
 */
public class QuyLaoKame extends Boss {

    public QuyLaoKame() throws Exception {
        super(BossID.QUY_LAO, BossesData.QUY_LAO);
    }

    @Override
    public void reward(Player plKill) {
        if (Util.isTrue(1, 1)) {
            Service.gI().dropItemMap(this.zone, Util.cTrangQuyLao(zone, 710, 1, this.location.x, this.location.y, plKill.id));
        }
    }

    @Override
    public void wakeupAnotherBossWhenDisappear() {
        if (this.parentBoss == null) {
            return;
        }
        for (Boss boss : this.parentBoss.bossAppearTogether[this.parentBoss.currentLevel]) {
            if (boss.id == BossID.XEKO || boss.id == BossID.CHAIEN) {
                boss.changeToTypePK();
            }
        }
    }

    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        if (Util.canDoWithTime(st, 900000)) {
//            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;

}
