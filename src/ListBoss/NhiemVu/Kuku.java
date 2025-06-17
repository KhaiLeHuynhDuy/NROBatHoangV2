package ListBoss.NhiemVu;

import BossMain.Boss;
import BossMain.BossID;
import BossMain.BossesData;

public class Kuku extends Boss {

    public Kuku() throws Exception {
        super(BossID.KUKU, BossesData.KUKU);
    }

    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
//        if (Util.canDoWithTime(st, 900000)) {
//            this.changeStatus(BossStatus.LEAVE_MAP);
//        }
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;
}
