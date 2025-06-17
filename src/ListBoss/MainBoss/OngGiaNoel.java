/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListBoss.MainBoss;

import BossMain.Boss;
import BossMain.BossID;
import BossMain.BossManager;
import BossMain.BossStatus;
import static BossMain.BossStatus.ATTACK;
import BossMain.BossesData;
import Consts.ConstMap;
import Consts.ConstPlayer;
import Consts.ConstRatio;
import Services.func.ChangeMapService;
import Maps.ItemMap;
import Maps.Map;
import Maps.Zone;
import Player.Player;
import Player.Referee;
import Server.Client;
import Services.MapService;
import Services.PlayerService;
import Services.Service;
import Utils.Logger;
import Utils.Util;
import java.util.Random;

/**
 *
 * @author Administrator
 */
public class OngGiaNoel extends Boss {

    private long phatquane;
    private long timedephatquane;
    private static final long timenhay = 1000;
    private long lastTimeJoinMap;

    public OngGiaNoel() throws Exception {
        super(BossID.ONG_GIA_NOEL, BossesData.ONG_GIA_NOEL);
    }

    @Override
    public Zone getMapJoin() {
        int mapId = this.data[this.currentLevel].getMapJoin()[Util.nextInt(0, this.data[this.currentLevel].getMapJoin().length - 1)];
        return MapService.gI().getMapById(mapId).zones.get(0);
    }

    @Override
    public void active() {
        this.phatquane();
        this.nPoint.tlNeDon = (short) 100000;
        if (Util.canDoWithTime(st, 600000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }

    @Override
    public Player getPlayerAttack() {
        return super.getPlayerAttack();
    }

    @Override
    public void joinMap() {
        super.joinMap();
        st = System.currentTimeMillis();
        lastTimeJoinMap = System.currentTimeMillis() + timenhay;
    }
    private long st;

    @Override
    public void moveTo(int x, int y) {
        byte dir = (byte) (this.location.x - x < 0 ? 1 : -1);
        byte move = (byte) Util.nextInt(20, 30);
        PlayerService.gI().playerMove(this, this.location.x + (dir == 1 ? move : -move), y);
    }

    public void phatquane() {
        if (Util.canDoWithTime(st, 60000)) {
            Player ramdonPlayer = Client.gI().getPlayers().get(Util.nextInt(Client.gI().getPlayers().size()));
            if (ramdonPlayer != null && ramdonPlayer.isPl() && !ramdonPlayer.isPet && !ramdonPlayer.isNewPet && !(ramdonPlayer instanceof Referee)) {
                if (ramdonPlayer.zone.map.mapId != 51 && ramdonPlayer.zone.map.mapId != 5 && ramdonPlayer.zone.map.mapId != 21 && ramdonPlayer.zone.map.mapId != 22 && ramdonPlayer.zone.map.mapId != 23 && ramdonPlayer.zone.map.mapId != 113 && ramdonPlayer.zone.map.mapId != 129 && ramdonPlayer.zone.map.mapId != 52) {
                    ChangeMapService.gI().spaceShipArrive(this, (byte) 2, ChangeMapService.DEFAULT_SPACE_SHIP);
                    ChangeMapService.gI().exitMap(this);
                    this.zoneFinal = null;
                    this.lastZone = null;
                    this.zone = ramdonPlayer.zone;
                    this.location.x = Util.nextInt(100, zone.map.mapWidth - 100);
                    this.location.y = zone.map.yPhysicInTop(this.location.x, 100);
                    this.joinMap();
                }
            }
        }
        if (Util.canDoWithTime(st, 1000)) {
            Player pl = this.zone.getRandomPlayerInMap();
            if (pl == null || pl.isDie() || pl.isPet || pl.isNewPet) {
                return;
            }
            this.moveToPlayer(pl);
        }
        if (!Util.canDoWithTime(this.phatquane, this.timedephatquane)) {
            return;
        }
        if (Util.canDoWithTime(this.phatquane, this.timedephatquane)) {
             ItemMap item = new ItemMap(zone, 648, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), -1);
            if (Util.isTrue(1, 3)) {
                Service.gI().dropItemMap(this.zone, item);
            }
            this.chat("Hô hô hô");
        }
        this.timedephatquane = 5000;
        this.phatquane = System.currentTimeMillis();
    }

    @Override
    public void update() {
        super.update();
        if (Client.gI().getPlayers().isEmpty() || this.isDie()) {
            return;
        }
        try {
            Player ramdonPlayer = Client.gI().getPlayers().get(Util.nextInt(Client.gI().getPlayers().size()));
            if (ramdonPlayer != null && ramdonPlayer.isPl() && !ramdonPlayer.isPet && !ramdonPlayer.isNewPet && !(ramdonPlayer instanceof Referee)) {
                if (ramdonPlayer.zone.map.mapId != 51 && ramdonPlayer.zone.map.mapId != 5 && ramdonPlayer.zone.map.mapId != 21 && ramdonPlayer.zone.map.mapId != 22 && ramdonPlayer.zone.map.mapId != 23 && ramdonPlayer.zone.map.mapId != 113 && ramdonPlayer.zone.map.mapId != 129 && ramdonPlayer.zone.map.mapId != 52 && this.zone.getPlayers().size() <= 0 && System.currentTimeMillis() > this.lastTimeJoinMap) {
                    lastTimeJoinMap = System.currentTimeMillis() + timenhay;
                    ChangeMapService.gI().spaceShipArrive(this, (byte) 2, ChangeMapService.DEFAULT_SPACE_SHIP);
                    ChangeMapService.gI().exitMap(this);
                    this.zoneFinal = null;
                    this.lastZone = null;
                    this.zone = ramdonPlayer.zone;
                    this.location.x = Util.nextInt(100, zone.map.mapWidth - 100);
                    this.location.y = zone.map.yPhysicInTop(this.location.x, 100);
                    this.joinMap();
                }
            }
        } catch (Exception e) {
        }
    }
}
