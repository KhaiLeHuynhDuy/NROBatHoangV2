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
public class Hatchiyack extends Boss {

    private final int level;
    private Clan clan;

    private static final int[][] FULL_DEMON = new int[][]{{Skill.DEMON, 1}, {Skill.DEMON, 2}, {Skill.DEMON, 3}, {Skill.DEMON, 4}, {Skill.DEMON, 5}, {Skill.DEMON, 6}, {Skill.DEMON, 7}};

    public Hatchiyack(Zone zone, Clan clan, int level, long dame, long hp) throws Exception {
        super( BossID.HATCHIYACK, new BossData(
                " ",
                ConstPlayer.TRAI_DAT,
                new short[]{639, 640, 641, -1, -1, -1},
                (dame),
                new long[]{hp},
                new int[]{148},
                (int[][]) Util.addArray(FULL_DEMON),
                new String[]{"|-1|Các ngươi dám hạ sư phụ ta",
                    "|-1|Ta sẽ tiêu diệt hết các ngươi"},
                new String[]{"|-1|Đại bác báo thù...",
                    "|-1|Heyyyyyyyy Yaaaaa"},
                new String[]{"|-1|Các ngươi khó mà rời khỏi nơi đây"},
                60
        ));
        this.zone = zone;
        this.level = level;
        this.clan = clan;
    }

    @Override
    public synchronized long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.level + 10, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }

            if (plAtt != null && plAtt.idNRNM != -1) {
                return 1;
            }

            damage = this.nPoint.subDameInjureWithDeff(damage + Util.nextInt(-200 * this.level, 0));

            damage -= damage / 100 * (this.level / 5);

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
        try {
            ItemMap it = new ItemMap(zone, 729, 1, this.location.x + x, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), -1);
            it.options.clear();
            int ParamMax = (int) 11 + (level / 3) - (level > 55 ? Util.nextInt(level / 10) : 0);
            if (ParamMax < 3) {
                ParamMax = 3;
            }
            int ParamMin = ParamMax - 3;
            if (ParamMin < 3) {
                ParamMin = 3;
            }
//            int ParamMaxSDCM = ParamMax < 41 ? ParamMax : 40;
//            int ParamMinSDCM = ParamMaxSDCM - 3;
//            if (ParamMinSDCM < 3) {
//                ParamMinSDCM = 3;
//            }
            int hsd = Util.nextInt(ParamMin, ParamMax);
            it.options.add(new ItemOption(50, Util.nextInt(ParamMin, ParamMax)));
            it.options.add(new ItemOption(77, Util.nextInt(ParamMin, ParamMax)));
            it.options.add(new ItemOption(103, Util.nextInt(ParamMin, ParamMax)));
            it.options.add(new ItemOption(5, Util.nextInt(ParamMin, ParamMax)));
//            it.options.add(new Item.ItemOption(5, Util.nextInt(ParamMinSDCM, ParamMaxSDCM)));
            it.options.add(new ItemOption(93, hsd > 21 ? 21 : hsd));
            it.options.add(new ItemOption(30, 0));
            Service.gI().dropItemMap(this.zone, it);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (clan != null && clan.KhiGasHuyDiet != null) {
            clan.KhiGasHuyDiet.hatchiyatchDead = true;
        }
        ChangeMapService.gI().exitMap(this);
        this.lastZone = null;
        this.lastTimeRest = System.currentTimeMillis();
        this.changeStatus(BossStatus.REST);
        BossManager.gI().removeBoss(this);
        this.dispose();
    }
}
