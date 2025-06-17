package ListMap;

import ListBoss.BanDoKhoBau.TrungUyXanhLo;
import Item.Item;
import Maps.Zone;
import Player.Player;
import Services.InventoryServiceNew;
import Services.Service;
import Utils.Logger;
import Utils.Util;
import java.util.ArrayList;
import java.util.List;

public class BanDoKhoBauService {

    private static BanDoKhoBauService instance;

    public static BanDoKhoBauService gI() {
        if (instance == null) {
            instance = new BanDoKhoBauService();
        }
        return instance;
    }

    public List<BanDoKhoBau> banDoKhoBaus;

    private BanDoKhoBauService() {
        this.banDoKhoBaus = new ArrayList<>();
        for (int i = 0; i < BanDoKhoBau.AVAILABLE; i++) {
            this.banDoKhoBaus.add(new BanDoKhoBau(i));
        }
    }

    public void addMapBanDoKhoBau(int id, Zone zone) {
        this.banDoKhoBaus.get(id).getZones().add(zone);
    }

    public void openBanDoKhoBau(Player player, byte level) {
        if (level >= 1 && level <= 110) {
            if (player.clan != null && player.clan.BanDoKhoBau == null) {
                Item item = InventoryServiceNew.gI().findItemBag(player, 611);
                if (item != null && item.quantity > 0) {
                    BanDoKhoBau banDoKhoBau = null;
                    for (BanDoKhoBau bdkb : this.banDoKhoBaus) {
                        if (!bdkb.isOpened) {
                            banDoKhoBau = bdkb;
                            break;
                        }
                    }
                    if (banDoKhoBau != null) {
                        if (Util.isAfterMidnight(player.lastTimeJoinBDKB)) {
                            player.timesPerDayBDKB = 1;
                        } else if (player.lastTimeJoinBDKB != player.clan.lastTimeOpenBanDoKhoBau) {
                            if (player.timesPerDayBDKB >= 3) {
                                Service.gI().sendThongBao(player, "Bạn đã vào hang kho báu 3 lần trong hôm nay, hẹn gặp lại ngày mai");
                                return;
                            }
                        }

                        InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
                        InventoryServiceNew.gI().sendItemBags(player);
                        banDoKhoBau.openBanDoKhoBau(player, player.clan, level);
                    } else {
                        Service.gI().sendThongBao(player, "Hang kho báu đã đầy, hãy quay lại sau 30 phút");
                    }
                } else {
                    Service.gI().sendThongBao(player, "Không tìm thấy bản đồ kho báu");
                }
            } else {
                Service.gI().sendThongBao(player, "Không thể thực hiện");
            }
        }
    }
}
