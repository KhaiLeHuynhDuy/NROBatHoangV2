package ListBoss.MainBoss;

import BossMain.Boss;
import BossMain.BossesData;
import Player.Player;
import Services.EffectSkillService;
import Services.Service;
import Utils.Util;
import java.util.Random;

public class ThienSuVados extends Boss {

    public ThienSuVados() throws Exception {
        super(Util.randomBossId(), BossesData.THIEN_SU_VADOS);
    }

    @Override
    public void reward(Player plKill) {
        int[] manhthuong = new int[]{1066,1067,1068,1069,1070};
        int[] manhhiem = new int[]{561};

        int randomAWJ = new Random().nextInt(manhthuong.length);
        int randomGR = new Random().nextInt(manhhiem.length);
        if (Util.isTrue(99, 100)) {
            Service.gI().dropItemMap(this.zone, Util.manhTS(zone, manhthuong[randomAWJ], 1, this.location.x, this.location.y, plKill.id));
        } else {
            Service.gI().dropItemMap(this.zone, Util.manhTS(zone, manhhiem[randomGR], 1, this.location.x, this.location.y, plKill.id));
        }
    }

    @Override
    public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (Util.isTrue(95, 100) && plAtt != null) {//tỉ lệ hụt của thiên sứ
            Util.isTrue(this.nPoint.tlNeDon, 100000);
            if (Util.isTrue(1, 100)) {
                this.chat("Hãy để bản năng tự vận động");
                this.chat("Tránh các động tác thừa");
            } else if (Util.isTrue(1, 100)) {
                this.chat("Chậm lại,các ngươi quá nhanh rồi");
                this.chat("Chỉ cần hoàn thiện nó!");
                this.chat("Các ngươi sẽ tránh được mọi nguy hiểm");
            } else if (Util.isTrue(1, 100)) {
                this.chat("Đây chính là bản năng vô cực");
            }
            damage = 0;

        }
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            if (damage >= 1) {
                damage = 1;
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }

//    @Override
//    public void active() {
//        super.active(); //To change body of generated methods, choose Tools | Templates.
//        if (Util.canDoWithTime(st, 1000000)) {
//            this.changeStatus(BossStatus.LEAVE_MAP);
//        }
//    }
//
//    @Override
//    public void joinMap() {
//        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
//        st = System.currentTimeMillis();
//    }
//    private long st;
}
