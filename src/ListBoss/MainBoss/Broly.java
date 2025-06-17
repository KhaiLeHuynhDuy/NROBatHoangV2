package ListBoss.MainBoss;

import BossMain.Boss;
import BossMain.BossData;
import BossMain.BossID;
import BossMain.BossesData;
import BossMain.BossSuper;
import Player.Player;
import Skill.Skill;
import Services.EffectSkillService;
import Services.Service;
import Utils.Util;

public class Broly extends Boss {

    public Broly() throws Exception {
        super(BossID.BROLY, BossesData.BROLY_1);
    }

    private long HpBossSupperActive = Util.nextInt(15000000, 20000000);

    @Override
    public void reward(Player plKill) {
        if (this.nPoint.hpMax >= HpBossSupperActive) {
            BossData SuperBroly = new BossData(
                    "Super Broly " + Util.nextInt(100),
                    this.gender,
                    new short[]{294, 295, 296, -1, -1, -1},
                    Util.nextInt(15000, 25000),
                    new long[]{HpBossSupperActive + Util.nextInt(500000, 2000000)},
                    new int[]{-1},
                    new int[][]{
                        {Skill.KAMEJOKO, 7, 100},
                        {Skill.TAI_TAO_NANG_LUONG, 5, 15000}
                    },
                    new String[]{"|-2|SuperBroly"},
                    new String[]{"|-1|Ọc Ọc"},
                    new String[]{"|-1|Lần khác ta sẽ xử đẹp ngươi"},
                    60
            );
            try {
                new BossSuper(Util.createIdBossClone((int) this.id), SuperBroly, this.zone);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void active() {
        super.active();
        this.nPoint.dame = this.nPoint.hpMax / 100;
    }

    @Override
    public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        damage = this.nPoint.subDameInjureWithDeff(damage);

        if (this.nPoint.hp >= 1600000 && this.nPoint.hp > 0) {
            BossData SuperBroly = new BossData(
                    "Super Broly " + Util.nextInt(100),
                    this.gender,
                    new short[]{294, 295, 296, -1, -1, -1},
                    Util.nextInt(15000, 25000),
                    new long[]{HpBossSupperActive + Util.nextInt(500000, 2000000)},
                    new int[]{-1},
                    new int[][]{
                        {Skill.KAMEJOKO, 7, 100},
                        {Skill.TAI_TAO_NANG_LUONG, 5, 15000}
                    },
                    new String[]{"|-2|SuperBroly"},
                    new String[]{"|-1|Ọc Ọc"},
                    new String[]{"|-1|Lần khác ta sẽ xử đẹp ngươi"},
                    60
            );
            try {
                new BossSuper(Util.createIdBossClone((int) this.id), SuperBroly, this.zone);
                Service.gI().sendThongBao(plAtt, "Super Broly xuất hiện!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.nPoint.hp = 0; // Đặt HP về 0 để boss này chết
        }

        if (this.nPoint.hpMax < 16000000) {
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = this.nPoint.hpMax / 100;
                if (damage > nPoint.mpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = this.nPoint.hpMax / 100;
                if (damage > nPoint.tlNeDon) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = this.nPoint.hpMax / 100;
            }
            if (damage > this.nPoint.hpMax / 100) {
                damage = this.nPoint.hpMax / 100;
            }
        }
        if (this.nPoint.hpMax < 20000000) {
            this.nPoint.hpMax += this.nPoint.hpMax / 100;
        }

        this.nPoint.subHP(damage);
        if (isDie()) {
            this.setDie(plAtt);
            die(plAtt);
        }
        return damage;
    }
}
