package ListBoss.MainBoss;

import Consts.ConstPlayer;
import BossMain.Boss;
import BossMain.BossID;
import BossMain.BossManager;
import BossMain.BossesData;
import Maps.ItemMap;
import Player.Player;
import Services.EffectSkillService;
import Services.PlayerService;
import Services.Service;
import Services.TaskService;
import Utils.Util;

public class SuperXen extends Boss {
    
    private long lastTimeHapThu;
    private int timeHapThu;
    
    public SuperXen() throws Exception {
        super(BossID.SUPER_XEN, BossesData.SUPER_XEN);
    }

    @Override
    public void reward(Player plKill) {
        if(Util.isTrue(30,100)){
        ItemMap it = new ItemMap(this.zone, 14, 1, this.location.x, this.location.y, plKill.id);
        Service.gI().dropItemMap(this.zone, it);
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }else  if(Util.isTrue(10,100)){
         ItemMap it = new ItemMap(this.zone, 15, 1, this.location.x, this.location.y, plKill.id);
        Service.gI().dropItemMap(this.zone, it);
        }
         ItemMap it = new ItemMap(this.zone, 15, 1, this.location.x, this.location.y, plKill.id);
         Service.gI().dropItemMap(this.zone, it);
    }
    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.hapThu();
        this.attack();
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
        this.nPoint.dameg += (pl.nPoint.dame * 5 / 100);
        this.nPoint.hpg += (pl.nPoint.hp * 2 / 100);
        this.nPoint.critg++;
        this.nPoint.calPoint();
        PlayerService.gI().hoiPhuc(this, pl.nPoint.hp, 0);
        pl.injured(null, pl.nPoint.hpMax, true, false);
        Service.gI().sendThongBao(pl, "Bạn vừa bị " + this.name + " hấp thu!");
        this.chat(2, "Ui cha cha, kinh dị quá. " + pl.name + " vừa bị tên " + this.name + " nuốt chửng kìa!!!");
        this.chat("Haha, ngọt lắm đấy " + pl.name + "..");
        this.lastTimeHapThu = System.currentTimeMillis();
        this.timeHapThu = Util.nextInt(30000, 70000);
    }
   @Override
    public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (Util.isTrue(70, 100) && plAtt != null) {//tỉ lệ hụt của thiên sứ
            Util.isTrue(this.nPoint.tlNeDon, 100000);
            
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
                damage = damage;
                 if (damage > nPoint.mpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage; 
                 if (damage > nPoint.tlNeDon) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage; 
            }
            if (damage >= 1000000) {
                damage = 10000000;
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

     
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;
    
    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        super.dispose();
        
    }
}
