package Maps;

import java.util.ArrayList;
import java.util.List;
import Services.func.Template.ItemTemplate;
import Item.Item.ItemOption;
import Player.Player;
import Utils.Util;
import Services.ItemMapService;
import Services.ItemService;
import Services.MapService;
import Services.PlayerService;
import Services.Service;

public class ItemMap {

    public Zone zone;
    public int itemMapId;
    public ItemTemplate itemTemplate;
    public int quantity;

    public int x;
    public int y;
    public long playerId;
    public List<ItemOption> options;

    public long createTime;

    public int clanId = -1;

    public boolean isBlackBall;
    public boolean isNamecBall;

    public ItemMap(Zone zone, int tempId, int quantity, int x, int y, long playerId) {
        this.zone = zone;
        this.itemMapId = zone.countItemAppeaerd++;
        if (zone.countItemAppeaerd >= 2000000000) {
            zone.countItemAppeaerd = 0;
        }
        this.itemTemplate = ItemService.gI().getTemplate((short) tempId);
        this.quantity = quantity;
        this.x = x;
        this.y = y;
        this.playerId = playerId != -1 ? Math.abs(playerId) : playerId;
        this.createTime = System.currentTimeMillis();
        this.options = new ArrayList<>();
        this.isBlackBall = ItemMapService.gI().isBlackBall(this.itemTemplate.id);
        this.isNamecBall = ItemMapService.gI().isNamecBall(this.itemTemplate.id);
        this.lastTimeMoveToPlayer = System.currentTimeMillis();
        this.zone.addItem(this);
    }

    public ItemMap(Zone zone, ItemTemplate temp, int quantity, int x, int y, long playerId) {
        this.zone = zone;
        this.itemMapId = zone.countItemAppeaerd++;
        if (zone.countItemAppeaerd >= 2000000000) {
            zone.countItemAppeaerd = 0;
        }
        this.itemTemplate = temp;
        this.quantity = quantity;
        this.x = x;
        this.y = y;
        this.playerId = playerId != -1 ? Math.abs(playerId) : playerId;
        this.createTime = System.currentTimeMillis();
        this.options = new ArrayList<>();
        this.isBlackBall = ItemMapService.gI().isBlackBall(this.itemTemplate.id);
        this.isNamecBall = ItemMapService.gI().isNamecBall(this.itemTemplate.id);
        this.lastTimeMoveToPlayer = System.currentTimeMillis();
        this.zone.addItem(this);
    }

    public ItemMap(ItemMap itemMap) {
        this.zone = itemMap.zone;
        this.itemMapId = itemMap.itemMapId;
        this.itemTemplate = itemMap.itemTemplate;
        this.quantity = itemMap.quantity;
        this.x = itemMap.x;
        this.y = itemMap.y;
        this.playerId = itemMap.playerId;
        this.options = itemMap.options;
        this.isBlackBall = itemMap.isBlackBall;
        this.isNamecBall = itemMap.isNamecBall;
        this.lastTimeMoveToPlayer = itemMap.lastTimeMoveToPlayer;
        this.createTime = System.currentTimeMillis();
        this.zone.addItem(this);
    }

    public void update() {
        if (this.isBlackBall) {
            if (Util.canDoWithTime(lastTimeMoveToPlayer, timeMoveToPlayer)) {
                if (this.zone != null && !this.zone.getPlayers().isEmpty()) {
                    Player player = this.zone.getPlayers().get(0);
                    if (player.zone != null && player.zone.equals(this.zone)) {
                        this.x = player.location.x;
                        this.y = this.zone.map.yPhysicInTop(this.x, player.location.y - 24);
                        reAppearItem();
                        this.lastTimeMoveToPlayer = System.currentTimeMillis();
                    }
                }
            }
            return;
        }
        if (this.itemTemplate.type == 22) {
            satelliteUpdate();
        }
        if ((Util.canDoWithTime(createTime, 50000) && isNotNullItem() && itemTemplate.type != 22 || Util.canDoWithTime(createTime, 180000)) && !this.isNamecBall) {
            if (this.zone != null && this.zone.map.mapId != 21 && this.zone.map.mapId != 22
                    && this.zone.map.mapId != 23 && this.itemTemplate.id != 78
                    && this.itemTemplate.id != 726 && !(MapService.gI().isMapDoanhTrai(this.zone.map.mapId) && this.itemTemplate.id >= 14 && this.itemTemplate.id <= 20)) {
                ItemMapService.gI().removeItemMapAndSendClient(this);
            }
        }

        if (Util.canDoWithTime(createTime, 20000) && !this.isNamecBall) {
            if (this.zone.map.mapId != 21 && this.zone.map.mapId != 22
                    && this.zone.map.mapId != 23 && this.itemTemplate.id != 78) {
                ItemMapService.gI().removeItemMapAndSendClient(this);
            }
        }
        if (Util.canDoWithTime(createTime, 15000)) {
            this.playerId = -1;
        }
         if (this.zone != null && isNotNullItem() && this.itemTemplate.id == 648 && this.playerId == 123456789 && Util.canDoWithTime(createTime, 5000)) {
                ItemMapService.gI().removeItemMapAndSendClient(this);
            }
    }

    private void satelliteUpdate() {
        for (Player pl : this.zone.getPlayers()) {
            if (!pl.isDie() && Util.getDistance(pl.location.x, pl.location.y, x, y) < 200 && pl.satellite != null && (pl.id == this.playerId || this.clanId != -1 && pl.clan != null && pl.clan.id == this.clanId)) {
                switch (this.itemTemplate.id) {
                    case 342:
                        if (!pl.satellite.isMP) {
                            pl.satellite.isMP = true;
                            pl.satellite.lastMPTime = System.currentTimeMillis();
                            if (pl.nPoint.mp < pl.nPoint.mpMax) {
                                pl.nPoint.addMp(pl.nPoint.mpMax / 10);
                                PlayerService.gI().sendInfoMp(pl);
                            }
                        }
                        break;
                    case 343:
                        if (!pl.satellite.isIntelligent) {
                            pl.satellite.isIntelligent = true;
                            pl.satellite.lastIntelligentTime = System.currentTimeMillis();
                        }
                        break;
                    case 344:
                        if (!pl.satellite.isDefend) {
                            pl.satellite.isDefend = true;
                            pl.satellite.lastDefendTime = System.currentTimeMillis();
                        }
                        break;
                    case 345:
                        if (!pl.satellite.isHP) {
                            pl.satellite.isHP = true;
                            pl.satellite.lastHPTime = System.currentTimeMillis();
                            if (pl.nPoint.hp < pl.nPoint.hpMax) {
                                pl.nPoint.addHp(pl.nPoint.hpMax / 10);
                                PlayerService.gI().sendInfoHp(pl);
                                Service.gI().Send_Info_NV(pl);
                            }
                        }
                        break;
                }
            }
        }
    }

    public boolean isNotNullItem() {
        return itemTemplate != null;
    }

    private final int timeMoveToPlayer = 10000;
    private long lastTimeMoveToPlayer;

    private void reAppearItem() {
        ItemMapService.gI().sendItemMapDisappear(this);
        Service.gI().dropItemMap(this.zone, this);
    }

    public void dispose() {
        this.zone = null;
        this.itemTemplate = null;
        this.options = null;
    }
}
