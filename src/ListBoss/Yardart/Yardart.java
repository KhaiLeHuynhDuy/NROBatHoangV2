/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListBoss.Yardart;

import BossMain.Boss;
import BossMain.BossData;
import BossMain.BossStatus;
import Consts.ConstPlayer;
import Services.func.ChangeMapService;
import Item.Item.ItemOption;
import Maps.ItemMap;
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
public abstract class Yardart extends Boss {

    protected int x;
    protected int x2;
    protected int y;
    protected int y2;
    protected int range;
    protected int range2;

    protected long lastTimeMove;
    protected long lastTimeHoiHP;
    protected int timeHoiHP;

    protected int rewardRatio;

    public Yardart(int id, BossData... data) throws Exception {
        super(id, data);
    }

    @Override
    public void respawn() {
        super.respawn();
        this.init();
    }

    @Override
    public void joinMap() {
        if (zoneFinal != null) {
            joinMapByZone(zoneFinal);
            this.changeStatus(BossStatus.CHAT_S);
            this.wakeupAnotherBossWhenAppear();
            return;
        }
        if (this.zone == null) {
            if (this.parentBoss != null) {
                this.zone = parentBoss.zone;
            } else if (this.lastZone == null) {
                this.zone = getMapJoin();
            } else {
                this.zone = this.lastZone;
            }
        }
        if (this.zone != null) {
            try {
                if (this.currentLevel == 0) {
                    ChangeMapService.gI().changeMap(this, this.zone, x, y);
                    this.wakeupAnotherBossWhenAppear();
                } else {
                    ChangeMapService.gI().changeMap(this, this.zone, this.location.x, this.location.y);
                }
                this.changeStatus(BossStatus.CHAT_S);
            } catch (Exception e) {
                this.changeStatus(BossStatus.REST);
            }
        } else {
            this.changeStatus(BossStatus.RESPAWN);
        }
    }

    protected void init() {
        rewardRatio = 5;
    }

    @Override
    public void reward(Player plKill) {
        if (Util.isTrue(1, rewardRatio)) {
            ItemMap it = new ItemMap(zone, 590, 1, plKill.location.x + (Util.nextInt(-10, 10)), plKill.location.y, plKill.id);
            it.options.clear();
            it.options.add(new ItemOption(31, 1));
            Service.gI().dropItemMap(this.zone, it);
        }
    }

    @Override
    protected int getRangeCanAttackWithSkillSelect() {
        if (this.playerSkill == null || this.playerSkill.skillSelect == null || this.playerSkill.skillSelect.template == null) {
            return 300;
        }
        int skillId = this.playerSkill.skillSelect.template.id;
        switch (skillId) {
            case Skill.DRAGON:
            case Skill.DEMON:
            case Skill.GALICK:
            case Skill.LIEN_HOAN:
            case Skill.KAIOKEN:
                return 50;
            case Skill.KAMEJOKO:
            case Skill.MASENKO:
            case Skill.ANTOMIC:
                return 300;
            case Skill.DICH_CHUYEN_TUC_THOI:
                return 400;
            default:
                return 300;
        }
    }

    @Override
    public void attack() {
        if (Util.canDoWithTime(this.lastTimeAttack, 100) && this.typePk == ConstPlayer.PK_ALL) {
            this.lastTimeAttack = System.currentTimeMillis();
            try {
                if (Util.canDoWithTime(this.lastTimeHoiHP, timeHoiHP)) {
                    if (Util.isTrue(1, 2)) {
                        this.nPoint.hp += this.nPoint.hpMax * Util.nextInt(1, 5) / 100;
                    }
                    this.lastTimeHoiHP = System.currentTimeMillis();
                }
                Player pl = getPlayerAttack();

                if (pl != null && !pl.isDie() && ((Math.abs(pl.location.x - x) < range && Math.abs(pl.location.y - y) < range) || (Math.abs(pl.location.x - x2) < range && Math.abs(pl.location.y - y2) < range) || Util.isTrue(4, 5)) && Util.getDistance(pl, this) < range2) {
                    this.playerSkill.skillSelect = this.playerSkill.skills.get(Util.nextInt(0, this.playerSkill.skills.size() - 1));
                    if (Util.getDistance(this, pl) <= this.getRangeCanAttackWithSkillSelect()) {
                        if (Util.getDistance(this, pl) < 50) {
                            if (SkillUtil.isUseSkillChuong(this)) {
                                while (true) {
                                    this.playerSkill.skillSelect = this.playerSkill.skills.get(Util.nextInt(0, this.playerSkill.skills.size() - 1));
                                    if (!SkillUtil.isUseSkillChuong(this)) {
                                        break;
                                    }
                                }
                            }
                        }
                        if (SkillUtil.isUseSkillDam(this)) {
                            if (Util.getDistance(this, pl) < 50) {
                                this.moveTo(pl.location.x, pl.location.y);
                            } else {
                                if (Util.isTrue(1, 5)) {
                                    while (true) {
                                        this.playerSkill.skillSelect = this.playerSkill.skills.get(Util.nextInt(0, this.playerSkill.skills.size() - 1));
                                        if (SkillUtil.isUseSkillChuong(this)) {
                                            break;
                                        }
                                    }
                                    int dir = pl.location.x > this.location.x ? 1 : -1;
                                    this.moveTo(pl.location.x + (dir * Util.nextInt(100, 150)), pl.location.y);
                                }
                            }
                        } else if (SkillUtil.isUseSkillChuong(this)) {
                            if (Util.isTrue(1, 5)) {
                                int dir = pl.location.x > this.location.x ? 1 : -1;
                                this.moveTo(pl.location.x + (dir * Util.nextInt(100, 150)), pl.location.y);
                            }
                        }
                    } else {
                        if (Util.isTrue(3, 4)) {
                            while (!SkillUtil.isUseSkillChuong(this)) {
                                this.playerSkill.skillSelect = this.playerSkill.skills.get(Util.nextInt(0, this.playerSkill.skills.size() - 1));
                                if (SkillUtil.isUseSkillChuong(this)) {
                                    break;
                                }
                            }
                            if (Util.getDistance(this, pl) <= this.getRangeCanAttackWithSkillSelect()) {
                                if (Util.isTrue(1, 5)) {
                                    int dir = pl.location.x > this.location.x ? 1 : -1;
                                    this.moveTo(pl.location.x + (dir * Util.nextInt(100, 150)), pl.location.y);
                                }
                            }
                        } else {
                            moveToPlayer(pl);
                        }
                    }
                    this.nPoint.dame = ((long) pl.nPoint.hpMax * Util.nextInt(1, 3) / 100) / (Util.nextInt(10, 30));
                    if (this.playerSkill.skillSelect.template.id == Skill.DICH_CHUYEN_TUC_THOI) {
                        if (Util.isTrue(3, 4)) {
                            this.playerSkill.skillSelect = this.playerSkill.skills.get(0);
                        } else {
                            this.nPoint.dame = ((long) pl.nPoint.hpMax * Util.nextInt(5, 10) / 100);
                        }
                    }
                    SkillService.gI().useSkill(this, pl, null, null);
                    checkPlayerDie(pl);
                    return;
                }

                if (Util.canDoWithTime(lastTimeMove, 1500)) {
                    if (this.location.x < x) {
                        Service.gI().setPos(this, x2, y2);
                    } else if (this.location.x > x2) {
                        Service.gI().setPos(this, x, y);
                    } else if (Util.isTrue(1, 2)) {
                        PlayerService.gI().playerMove(this, x2, y2);
                    } else {
                        PlayerService.gI().playerMove(this, x, y);
                    }
                    lastTimeMove = System.currentTimeMillis();
                }

                if (Util.isTrue(1, 10)) {
                    this.nPoint.hp = this.nPoint.hpMax;
                }
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void moveToPlayer(Player pl) {
        if (pl.location != null) {
            moveTo(pl.location.x, pl.location.y);
        }
    }

    @Override
    public void moveTo(int x, int y) {
        byte dir = (byte) (this.location.x - x < 0 ? 1 : -1);
        byte move = (byte) Util.nextInt(10, 15);
        int xMove = x + (dir == 1 ? move : -move);
        int yMove = this.zone.map.yPhysicInTop(xMove, 100);
        PlayerService.gI().playerMove(this, xMove, yMove);
    }

    @Override
    public synchronized long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(10, 100)) {
                this.chat("Xí hụt");
                return 0;
            }
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage / 1;
            }

            if (!piercing && damage > this.nPoint.hpMax / 100) {
                damage = this.nPoint.hpMax / 100;
            }
            if (this.nPoint.hp > damage) {
                this.nPoint.subHP(damage);
            } else {
                Service.gI().hsChar(this, this.nPoint.hpMax, this.nPoint.mpMax);
                this.nPoint.hp = 1;
                if (Util.isTrue(1, 10)) {
                    Service.gI().Send_Info_NV(this);
                }
                //setDie(plAtt);
                //Service.gI().hsChar(this, this.nPoint.hpMax, this.nPoint.mpMax);
                //this.changeToTypePK();
                //this.changeStatus(BossStatus.AFK);
                reward(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }
//
//    @Override
//    public void afk() {
//        Service.gI().hsChar(this, this.nPoint.hpMax, this.nPoint.mpMax);
//        this.changeStatus(BossStatus.CHAT_S);
//        this.nPoint.hp = 1;
//    }

}
