package ListBoss.NhiemVu;

import Consts.ConstPlayer;
import BossMain.*;
import Maps.ItemMap;
import Player.Player;
import Services.Service;
import Services.TaskService;
import Utils.Util;
import java.util.Random;


public class SieuBoHung extends Boss {
    private long lastTimeHapThu;
    private int timeHapThu;
    public SieuBoHung() throws Exception {
        super(BossID.SIEU_BO_HUNG, BossesData.SIEU_BO_HUNG_1, BossesData.SIEU_BO_HUNG_2, BossesData.SIEU_BO_HUNG_3);
    }
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;

   @Override
    public void reward(Player plKill) {
        int[] itemDos = new int[]{555,556,557,558,559,560,561,562,563,564,565,566,567};
        int[] NRs = new int[]{16};
        int randomDo = new Random().nextInt(itemDos.length);
        int randomNR = new Random().nextInt(NRs.length);
        if (Util.isTrue(5, 100)) {
            if (Util.isTrue(30, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 16, 1, this.location.x, this.location.y, plKill.id));
                return;
            }
            Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, itemDos[randomDo], 1, this.location.x, this.location.y, plKill.id));
             } else if (Util.isTrue(70, 100)) {
            Service.gI().dropItemMap(this.zone, new ItemMap(zone, NRs[randomNR], 1, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
        }
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }
     @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.hapThu();
        this.attack();
         super.active(); //To change body of generated methods, choose Tools | Templates.
//         if (Util.canDoWithTime(st, 900000)) {
//             this.changeStatus(BossStatus.LEAVE_MAP);
//         }
    }

    private void hapThu() {
        if (!Util.canDoWithTime(this.lastTimeHapThu, this.timeHapThu) || !Util.isTrue(1, 100)) {
            return;
        }

        Player pl = this.zone.getRandomPlayerInMap();
        if (pl == null || pl.isDie()) {
            return;
        }
//        ChangeMapService.gI().changeMapYardrat(this, this.zone, pl.location.x, pl.location.y);
//        this.nPoint.dameg += (pl.nPoint.dame * 5 / 100);
//        this.nPoint.hpg += (pl.nPoint.hp * 2 / 100);
//        this.nPoint.critg++;
//        this.nPoint.calPoint();
//        PlayerService.gI().hoiPhuc(this, pl.nPoint.hp, 0);
//        pl.injured(null, pl.nPoint.hpMax, true, false);
//        Service.gI().sendThongBao(pl, "Bạn vừa bị " + this.name + " hấp thu!");
//        this.chat(2, "Ui cha cha, kinh dị quá. " + pl.name + " vừa bị tên " + this.name + " nuốt chửng kìa!!!");
//        this.chat("Haha, ngọt lắm đấy " + pl.name + "..");
//        this.lastTimeHapThu = System.currentTimeMillis();
//        this.timeHapThu = Util.nextInt(15000, 20000);
    }
 
//    @Override
//    public void moveTo(int x, int y) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.moveTo(x, y);
//    }
//
//    @Override
//    public void reward(Player plKill) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.reward(plKill);
//    }
//    
//    @Override
//    protected void notifyJoinMap() {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.notifyJoinMap();
//    }
}