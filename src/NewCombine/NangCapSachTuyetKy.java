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
public class NangCapSachTuyetKy {

    public static void showInfoCombine(Player player) {
        if (player.combineNew.itemsCombine.size() != 2) {
            Service.gI().sendThongBao(player, "Cần Sách Tuyệt Kỹ 1 và 10 Kìm bấm giấy.");
            return;
        }
        Item sachTuyetKy = null;
        Item kimBamGiay = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item.isSachTuyetKy()) {
                sachTuyetKy = item;
            } else if (item.template.id == 1269) {
                kimBamGiay = item;
            }
        }
        if (sachTuyetKy == null || kimBamGiay == null) {
            Service.gI().sendThongBao(player, "Cần Sách Tuyệt Kỹ 1 và 10 Kìm bấm giấy.");
            return;
        }
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_BLUE).append("Nâng cấp sách tuyệt kỹ\n");
        text.append(kimBamGiay.quantity >= 10 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Cần 10 Kìm bấm giấy\n");
        text.append(ConstFont.BOLD_BLUE).append("Tỉ lệ thành công: 10%\n");
        text.append(ConstFont.BOLD_BLUE).append("Nâng cấp thất bại sẽ mất 10 Kìm bấm giấy");
        if (kimBamGiay.quantity < 10) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(),
                    "Còn thiếu\n" + (10 - kimBamGiay.quantity) + " Kìm bấm giấy");
            return;
        }
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, text.toString(),
                "Nâng cấp", "Từ chối");
    }

    public static void nangCapSachTuyetKy(Player player) {
        if (player.combineNew.itemsCombine.size() != 2) {
            return;
        }
        Item sachTuyetKy = null;
        Item kimBamGiay = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item.isSachTuyetKy()) {
                sachTuyetKy = item;
            } else if (item.template.id == 1269) {
                kimBamGiay = item;
            }
        }
        if (sachTuyetKy == null || kimBamGiay == null) {
            return;
        }
        if (Util.isTrue(10, 100)) {
            switch (sachTuyetKy.template.id) {
                case 1262:
                    sachTuyetKy.template = ItemService.gI().getTemplate(1263);
                    break;
                case 1264:
                    sachTuyetKy.template = ItemService.gI().getTemplate(1265);
                    break;
                case 1266:
                    sachTuyetKy.template = ItemService.gI().getTemplate(1267);
                    break;
            }
            CombineServiceNew.gI().sendEffectSuccessCombine(player);
        } else {
            CombineServiceNew.gI().sendEffectFailCombine(player);
        }
        InventoryServiceNew.gI().subQuantityItemsBag(player, kimBamGiay, 10);
        InventoryServiceNew.gI().sendItemBags(player);
        CombineServiceNew.gI().reOpenItemCombine(player);
    }

}
