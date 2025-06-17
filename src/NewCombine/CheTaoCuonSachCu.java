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
public class CheTaoCuonSachCu {

    public static void showCombine(Player player) {
        Item trangSachCu = InventoryServiceNew.gI().findItemBag(player, 1272);
        Item biaSach = InventoryServiceNew.gI().findItemBag(player, 1268);
        int quantityTrangSachCu = trangSachCu != null ? trangSachCu.quantity : 0;
        int quantityBiaSach = biaSach != null ? biaSach.quantity : 0;
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_GREEN).append("Chế tạo Cuốn sách cũ\n");
        text.append(quantityTrangSachCu >= 9999 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Trang sách cũ ").append(quantityTrangSachCu).append("/9999\n");
        text.append(quantityBiaSach >= 1 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Bìa sách ").append(quantityBiaSach).append("/1\n");
        text.append(quantityTrangSachCu < 9999 || quantityBiaSach < 1 ? ConstFont.BOLD_RED : ConstFont.BOLD_BLUE).append("Tỉ lệ thành công: 20%\n");
        text.append(ConstFont.BOLD_RED).append("Thất bại mất 99 trang sách và 1 bìa sách");
        if (quantityTrangSachCu < 9999 || quantityBiaSach < 1) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(), "Từ chối");
            return;
        }
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.DONG_THANH_SACH_CU, text.toString(), "Đồng ý", "Từ chối");
    }

    public static void cheTaoCuonSachCu(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0 && InventoryServiceNew.gI().findItemBag(player, 1271) == null) {
            Service.gI().sendThongBao(player, "Cần 1 ô trống trong hành trang.");
            return;
        }
        Item trangSachCu = InventoryServiceNew.gI().findItemBag(player, 1272);
        Item biaSach = InventoryServiceNew.gI().findItemBag(player, 1268);
        int quantityTrangSachCu = trangSachCu != null ? trangSachCu.quantity : 0;
        int quantityBiaSach = biaSach != null ? biaSach.quantity : 0;
        if (quantityTrangSachCu < 9999 || quantityBiaSach < 1) {
            return;
        }
        CombineServiceNew.gI().sendAddItemCombine(player, ConstNpc.BA_HAT_MIT, trangSachCu, biaSach);
        int subTrangSach;
        if (Util.isTrue(20, 100)) {
            subTrangSach = 9999;
            Item cuonSachCu = ItemService.gI().createNewItem((short) 1271);
            cuonSachCu.itemOptions.add(new Item.ItemOption(30, 0));
            InventoryServiceNew.gI().addItemBag(player, cuonSachCu);
            CombineServiceNew.gI().sendEffSuccessVip(player, cuonSachCu.template.iconID);
            Util.setTimeout(() -> {
                Service.gI().sendServerMessage(player, "Bạn nhận được " + cuonSachCu.template.name);
                CombineServiceNew.gI().baHatMit.npcChat(player, "Chúc mừng con nhé");
            }, 2000);
        } else {
            subTrangSach = 99;
            CombineServiceNew.gI().sendEffFailVip(player);
            Util.setTimeout(() -> {
                CombineServiceNew.gI().baHatMit.npcChat(player, "Chúc con may mắn lần sau, đừng buồn con nhé");
            }, 2000);
        }
        InventoryServiceNew.gI().subQuantityItemsBag(player, trangSachCu, subTrangSach);
        InventoryServiceNew.gI().subQuantityItemsBag(player, biaSach, 1);
        InventoryServiceNew.gI().sendItemBags(player);
    }

}
