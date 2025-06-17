package ListBoss.MainBoss;

import BossMain.Boss;
import BossMain.BossID;
import BossMain.BossesData;
import Player.Player;
import network.Message;
import Server.Client;
import Services.EffectSkillService;
import Services.InventoryServiceNew;
import Services.Service;
import Services.func.ChangeMapService;
import Utils.Util;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnTrom extends Boss {

    private long time;
    private int antrom;
    private Message msg;

    public AnTrom() throws Exception {
        super(BossID.AN_TROM, BossesData.AN_TROM);
    }

    @Override
    public void reward(Player plKill) {
        if (Util.isTrue(100, 100)) {
            Service.getInstance().dropItemMap(this.zone, Util.manhTS(zone, 190, (int) this.inventory.gold / 4, plKill.location.x - 10, plKill.location.y, plKill.id));
            if (Util.isTrue(100, 100)) {
                Service.getInstance().dropItemMap(this.zone, Util.manhTS(zone, 190, (int) this.inventory.gold / 4, plKill.location.x - 15, plKill.location.y, plKill.id));
            }
            if (Util.isTrue(100, 100)) {
                Service.getInstance().dropItemMap(this.zone, Util.manhTS(zone, 190, (int) this.inventory.gold / 4, plKill.location.x - 20, plKill.location.y, plKill.id));
            }
            if (Util.isTrue(100, 100)) {
                Service.getInstance().dropItemMap(this.zone, Util.manhTS(zone, 190, (int) this.inventory.gold / 4, plKill.location.x - 25, plKill.location.y, plKill.id));
            }
            Service.gI().sendThongBaoAllPlayer(plKill.name + " Vừa Tiêu Diệt Ăn Trộm Và Nhận Được " + this.inventory.gold + " Vàng");
            plKill.NguHanhSonPoint += 1;
        }
    }

    @Override
    public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (Util.isTrue(50, 100) && plAtt != null) {//tỉ lệ hụt của thiên sứ
            Util.isTrue(this.nPoint.tlNeDon, 100000);
            if (Util.isTrue(1, 100)) {
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
    @Override
    public void active() {
        super.active();
        this.changeToTypePK();
        this.antrom();
        this.time();

    }

    @Override
    public void joinMap() {
        super.joinMap();
        this.inventory.gold = 0;
        st = System.currentTimeMillis();
    }
    private long st;

    private void antrom() {
        try {
            int seggs = 0;
            seggs = Util.nextInt(100000, 200000);
            if (!Util.canDoWithTime(this.time, this.antrom)) {
                return;
            }
            Player pl = this.zone.getRandomPlayerInMap();
            if (pl == null || pl.isDie() || pl.isPet || pl.isNewPet || pl.inventory.gold <= 5000000 || !pl.getSession().actived || this.location.x - pl.location.x >= 50) {
                return;
            }
            pl.inventory.gold -= seggs;
            this.inventory.gold += seggs;
            this.chat("Haha,ăn trộm được " + this.inventory.gold + " vàng rồi!!");
            this.time = System.currentTimeMillis();
            this.antrom = 500;
            msg = new Message(95);
            msg.writer().writeInt(-seggs);
            this.zone.getRandomPlayerInMap().isPl();
            Service.gI().sendMoney(pl);
            pl.sendMessage(msg);
            InventoryServiceNew.gI().sendItemBags(pl);
        } catch (IOException ex) {
            Logger.getLogger(AnTrom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void time() {
        Player randomplayer = Client.gI().getPlayers().get(Util.nextInt(Client.gI().getPlayers().size()));
        if (randomplayer != null || !randomplayer.isPet || !randomplayer.isNewPet || !randomplayer.isAdmin()) {
            if (randomplayer.zone.map.mapId != 21 && randomplayer.zone.map.mapId != 22 && randomplayer.zone.map.mapId != 23 && randomplayer.zone.map.mapId != 45 && randomplayer.zone.map.mapId != 46 && randomplayer.zone.map.mapId != 47 && randomplayer.zone.map.mapId != 48 && randomplayer.zone.map.mapId != 49 && randomplayer.zone.map.mapId != 50) {
                if (Util.canDoWithTime(st, 120000)) {
                    ChangeMapService.gI().changeMap(this, randomplayer.zone.zoneId, violate, randomplayer.location.x, randomplayer.location.y);
                    ChangeMapService.gI().exitMap(this);
                    this.zoneFinal = null;
                    this.lastZone = null;
                    this.zone = randomplayer.zone;
                    this.location.x = Util.nextInt(100, zone.map.mapWidth - 100);
                    this.location.y = zone.map.yPhysicInTop(this.location.x, 100);
                    this.joinMap();
                }
            }
        }

    }
}
