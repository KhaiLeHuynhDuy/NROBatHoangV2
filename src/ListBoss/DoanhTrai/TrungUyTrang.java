/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListBoss.DoanhTrai;

import BossMain.Boss;
import BossMain.BossData;
import BossMain.BossID;
import BossMain.BossManager;
import BossMain.BossStatus;
import Consts.ConstPlayer;
import Consts.ConstRatio;
import Services.func.ChangeMapService;
import Maps.ItemMap;
import Maps.Zone;
import Player.Player;
import Services.EffectSkillService;
import Services.PlayerService;
import Services.Service;
import Services.SkillService;
import Skill.Skill;
import Utils.SkillUtil;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class TrungUyTrang extends Boss {
    protected Player playerAtt;
    
    
    public TrungUyTrang(Zone zone, long dame, long hp ) throws Exception {
        super(BossID.TRUNG_UY_TRANG, new BossData(
                "Trung Uý Trắng", //name
                ConstPlayer.TRAI_DAT, //gender
                new short[]{141, 142, 143, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
                ((hp/10)), //dame
                new long[]{((20 * dame ))}, //hp
                new int[]{62}, //map join
                new int[][]{
                {Skill.DEMON, 3, 1}, {Skill.DEMON, 6, 2}, {Skill.DRAGON, 7, 3}, {Skill.DRAGON, 1, 4}, {Skill.GALICK, 5, 5},
                {Skill.KAMEJOKO, 7, 6}, {Skill.KAMEJOKO, 6, 7}, {Skill.KAMEJOKO, 5, 8}, {Skill.KAMEJOKO, 4, 9}, {Skill.KAMEJOKO, 3, 10}, {Skill.KAMEJOKO, 2, 11},{Skill.KAMEJOKO, 1, 12},
              {Skill.ANTOMIC, 1, 13},  {Skill.ANTOMIC, 2, 14},  {Skill.ANTOMIC, 3, 15},{Skill.ANTOMIC, 4, 16},  {Skill.ANTOMIC, 5, 17},{Skill.ANTOMIC, 6, 19},  {Skill.ANTOMIC, 7, 20},
                {Skill.MASENKO, 1, 21}, {Skill.MASENKO, 5, 22}, {Skill.MASENKO, 6, 23},
                    {Skill.KAMEJOKO, 7, 1000},},
                new String[]{}, //text chat 1
                new String[]{"|-1|Nhóc con"}, //text chat 2
                new String[]{}, //text chat 3
                1//respawn
        ));
        
        
        this.zone = zone;
      
    }
    @Override
    public void reward(Player plKill) {
        if (Util.isTrue(100, 100)) {
            ItemMap it = new ItemMap(this.zone, 18, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), plKill.id);
            Service.getInstance().dropItemMap(this.zone, it);
        }
    }
    
    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        if (Util.canDoWithTime(st, 1800000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        } 
    }
    @Override
    public void leaveMap() {
        super.leaveMap();
        if (Util.canDoWithTime(st, 1800000) || this.isDie() == true) {
            BossManager.gI().removeBoss(this);
        }
    }
    
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;


    
 
    public synchronized long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie() && !this.zone.isbulon1Alive && !this.zone.isbulon2Alive) {
            if (!piercing && Util.isTrue(20, 100)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage / 2);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage / 2;
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
