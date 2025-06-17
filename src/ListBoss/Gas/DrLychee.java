/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListBoss.Gas;

import BossMain.Boss;
import BossMain.BossData;
import BossMain.BossID;
import BossMain.BossManager;
import BossMain.BossStatus;
import Clan.Clan;
import Consts.ConstPlayer;
import Services.func.ChangeMapService;
import Item.Item.ItemOption;
import Maps.ItemMap;
import Maps.Zone;
import Player.Player;
import Services.EffectSkillService;
import Services.Service;
import Skill.Skill;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class DrLychee extends Boss {

    private final int level;
    private Clan clan;

    private static final int[][] FULL_DEMON = new int[][]{{Skill.DEMON, 1}, {Skill.DEMON, 2}, {Skill.DEMON, 3}, {Skill.DEMON, 4}, {Skill.DEMON, 5}, {Skill.DEMON, 6}, {Skill.DEMON, 7}};

    public DrLychee(Zone zone, Clan clan, int level, long dame, long hp) throws Exception {
        super( BossID.DR_LYCHEE, new BossData(
                "Dr Lychee",
                ConstPlayer.TRAI_DAT,
                new short[]{742, 743, 744, -1, -1, -1},
                ((10000 + dame)),
                new long[]{((1000000 + hp))},
                new int[]{148},
                (int[][]) Util.addArray(FULL_DEMON),
                new String[]{"|-1|Ta đợi các ngươi mãi",
                    "|-1|Bọn xayda các ngươi mau đền tội đi"},
                new String[]{"|-1|Đại bác báo thù...",
                    "|-1|Heyyyyyyyy Yaaaaa"},
                new String[]{"|-1|Các ngươi khá lắm",
                    "|-1|Hatchiyack sẽ báo thù cho ta"},
                60
        ));
        this.zone = zone;
        this.level = level;
        this.clan = clan;
    }

    @Override
    public synchronized long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.level, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }

            if (plAtt != null && plAtt.idNRNM != -1) {
                return 1;
            }

            damage = this.nPoint.subDameInjureWithDeff(damage + Util.nextInt(-100 * this.level, 0));

            damage -= damage / 100 * (this.level / 10);

            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
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

    @Override
    public void reward(Player plKill) {
        dropCt(0);
        for (int i = 0; i < this.zone.getNumOfPlayers(); i++) {
            int x = (i + 1) * 50;
            dropCt(x);
            dropCt(-x);
        }
    }

    private void dropCt(int x) {
        ItemMap it = new ItemMap(zone, 738, 1, this.location.x + x, this.zone.map.yPhysicInTop(this.location.x,
                this.location.y - 24), -1);
        it.options.clear();
        int ParamMax = (int) 5 + (level / 3) - (level > 55 ? Util.nextInt(level / 10) : 0);
        if (ParamMax < 3) {
            ParamMax = 3;
        }
        int ParamMin = ParamMax - 3;
        if (ParamMin < 3) {
            ParamMin = 3;
        }
        int hsd = Util.nextInt(ParamMin, ParamMax);
        it.options.add(new ItemOption(50, Util.nextInt(ParamMin, ParamMax)));
        it.options.add(new ItemOption(77, Util.nextInt(ParamMin, ParamMax)));
        it.options.add(new ItemOption(103, Util.nextInt(ParamMin, ParamMax)));
        it.options.add(new ItemOption(94, Util.nextInt(ParamMin, ParamMax)));
        it.options.add(new ItemOption(93, hsd > 21 ? 21 : hsd));
        it.options.add(new ItemOption(30, 0));
        Service.gI().dropItemMap(this.zone, it);
    }

    @Override
    public void joinMap() {
        ChangeMapService.gI().changeMap(this, this.zone, 480, 295);
        this.moveTo(480, 480);
        this.changeStatus(BossStatus.CHAT_S);
    }

    @Override
    public void die(Player plKill) {
        if (plKill != null) {
            reward(plKill);
        }
        this.changeStatus(BossStatus.DIE);
    }

    @Override
    public void leaveMap() {
        long bossDamage = Math.min((long) (this.nPoint.dame * 1.5), 200000000L);
        long bossMaxHealth = Math.min((long) (this.nPoint.hpMax * 1.5), 2000000000L);
        try {
            clan.KhiGasHuyDiet.bosses.add(new Hatchiyack(
                    zone,
                    clan,
                    level,
                    bossDamage,
                    bossMaxHealth
            ));
        } catch (Exception ex) {
        }
        ChangeMapService.gI().exitMap(this);
        this.lastZone = null;
        this.lastTimeRest = System.currentTimeMillis();
        this.changeStatus(BossStatus.REST);
        BossManager.gI().removeBoss(this);
        this.dispose();
    }
}

