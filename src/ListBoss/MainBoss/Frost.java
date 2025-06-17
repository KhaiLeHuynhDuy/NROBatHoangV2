package ListBoss.MainBoss;

import BossMain.Boss;
import BossMain.BossID;
import BossMain.BossManager;
import BossMain.BossesData;
import Maps.ItemMap;
import Player.Player;
import Server.Manager;
import Services.EffectSkillService;
import Services.Service;
import Utils.Util;
import java.util.Random;

public class Frost extends Boss {
    
    public Frost() throws Exception {
        super(BossID.FROST, BossesData.FROST_FIRST_FORM, BossesData.FROST_SECOND_FORM,
                BossesData.FROST_FINAL_FORM);
    }
    
    @Override
    public void reward(Player plKill) {
        byte randomDo = (byte) new Random().nextInt(Manager.itemIds_TL.length - 1);
        byte randomNR = (byte) new Random().nextInt(Manager.itemIds_NR_SB.length);
        int[] itemDos = new int[]{233, 237, 241, 245, 249, 253, 257, 261, 265, 269, 273, 277, 281};
        int randomc12 = new Random().nextInt(itemDos.length);
        
        switch ((int)this.id) {
            case -9: // FROST_FIRST_FORM
                Service.gI().dropItemMap(this.zone, new ItemMap(zone, 15, 1, this.location.x, this.location.y, plKill.id));
                break;
            case -10: // FROST_SECOND_FORM
                if (Util.isTrue(BossManager.ratioReward, 100)) {
                    if (Util.isTrue(1, 5)) {
                        Service.gI().dropItemMap(this.zone,new ItemMap(zone, 874, 1, this.location.x, this.location.y, plKill.id));
                        return;
                    }
                    Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, Manager.itemIds_TL[randomDo], 1, this.location.x, this.location.y, plKill.id));
                } else if (Util.isTrue(2, 5)) {
                    Service.gI().dropItemMap(this.zone, Util.RaitiDoc12(zone, itemDos[randomc12], 1, this.location.x, this.location.y, plKill.id));
                } else {
                    Service.gI().dropItemMap(this.zone, new ItemMap(zone, Manager.itemIds_NR_SB[randomNR], 1, this.location.x, this.location.y, plKill.id));
                }
                break;
            case -11: // FROST_FINAL_FORM
                Service.gI().dropItemMap(this.zone, new ItemMap(zone, 723, 1, this.location.x, this.location.y, plKill.id));
                break;
            default:
                break;
        }
        this.id--;
    }
    
    @Override
    public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage/2);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage/2;
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
}
