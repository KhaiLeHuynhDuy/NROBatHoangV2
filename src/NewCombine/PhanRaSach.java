/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NewCombine;

import Consts.ConstFont;
import Consts.ConstNpc;
import Services.func.CombineServiceNew;
import Item.Item;
import Player.Player;
import Services.InventoryServiceNew;
import Services.ItemService;
import Services.Service;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class PhanRaSach {

    public static void showInfoCombine(Player player) {
        if (player.combineNew.itemsCombine.size() != 1) {
            Service.gI().sendThongBao(player, "Không tìm thấy vật phẩm");
            return;
        }
        Item sachTuyetKy = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item.isSachTuyetKy() || item.isSachTuyetKy2()) {
                sachTuyetKy = item;
            }
        }
        if (sachTuyetKy == null) {
            Service.gI().sendThongBao(player, "Không tìm thấy vật phẩm");
            return;
        }
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_BLUE).append("Phân rã sách\n");
        text.append(ConstFont.BOLD_BLUE).append("Nhận lại 5 cuốn sách cũ\n");
        text.append(player.inventory.gold >= 10_000_000 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Phí rã 10 triệu vàng");
        if (player.inventory.gold < 10_000_000) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(),
                    "Còn thiếu\n" + Util.numberToMoney(10_000_000 - player.inventory.gold) + " vàng");
            return;
        }
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, text.toString(),
                "Đồng ý", "Từ chối");
    }

    public static void phanRaSach(Player player) {
        if (player.combineNew.itemsCombine.size() != 1) {
            return;
        }
        Item sachTuyetKy = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item.isSachTuyetKy() || item.isSachTuyetKy2()) {
                sachTuyetKy = item;
            }
        }
        if (sachTuyetKy == null || player.inventory.gold < 10_000_000) {
            return;
        }
        InventoryServiceNew.gI().subQuantityItemsBag(player, sachTuyetKy, 1);
        Item cuonSachCu = ItemService.gI().createNewItem((short) 1392, 5);
        cuonSachCu.itemOptions.add(new Item.ItemOption(30, 0));
        InventoryServiceNew.gI().addItemBag(player, cuonSachCu);
        CombineServiceNew.gI().sendEffectSuccessCombine(player);
        Service.gI().sendMoney(player);
        InventoryServiceNew.gI().sendItemBags(player);
        CombineServiceNew.gI().reOpenItemCombine(player);
    }
}
