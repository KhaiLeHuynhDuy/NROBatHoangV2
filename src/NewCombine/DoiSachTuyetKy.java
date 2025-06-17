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
public class DoiSachTuyetKy {

    public static void showCombine(Player player) {
        Item cuonSachCu = InventoryServiceNew.gI().findItemBag(player, 1271);
        Item kimBamGiay = InventoryServiceNew.gI().findItemBag(player, 1269);
        int quantityCuonSachCu = cuonSachCu != null ? cuonSachCu.quantity : 0;
        int quantityKimBamGiay = kimBamGiay != null ? kimBamGiay.quantity : 0;
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_GREEN).append("Đổi sách Tuyệt Kỹ 1\n");
        text.append(quantityCuonSachCu >= 10 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Cuốn sách cũ ").append(quantityCuonSachCu).append("/10\n");
        text.append(quantityKimBamGiay >= 1 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Kìm bấm giấy ").append(quantityKimBamGiay).append("/1\n");
        text.append(quantityCuonSachCu < 10 || quantityKimBamGiay < 1 ? ConstFont.BOLD_RED : ConstFont.BOLD_BLUE).append("Tỉ lệ thành công: 20%\n");
        if (quantityCuonSachCu < 10 || quantityKimBamGiay < 1) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(), "Từ chối");
            return;
        }
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.DOI_SACH_TUYET_KY, text.toString(), "Đồng ý", "Từ chối");
    }

    public static void doiSachTuyetKy(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
            Service.gI().sendThongBao(player, "Cần 1 ô trống trong hành trang.");
            return;
        }
        Item cuonSachCu = InventoryServiceNew.gI().findItemBag(player, 1271);
        Item kimBamGiay = InventoryServiceNew.gI().findItemBag(player, 1269);
        int quantityCuonSachCu = cuonSachCu != null ? cuonSachCu.quantity : 0;
        int quantityKimBamGiay = kimBamGiay != null ? kimBamGiay.quantity : 0;
        if (quantityCuonSachCu < 10 || quantityKimBamGiay < 1) {
            return;
        }
        CombineServiceNew.gI().sendAddItemCombine(player, ConstNpc.BA_HAT_MIT, cuonSachCu, kimBamGiay);
        int subCuonSach;
        if (Util.isTrue(100, 100)) {
            subCuonSach = 10;
            int[] sach = {1262, 1264, 1266};
            Item sachTuyetKy = ItemService.gI().createNewItem((short) sach[Util.nextInt(sach.length)]);
            for (int i = 0; i < (Util.isTrue(999, 1000) ? 1 : Util.nextInt(1, 3)); i++) {
                sachTuyetKy.itemOptions.add(new Item.ItemOption(217, 0));
            }
            sachTuyetKy.itemOptions.add(new Item.ItemOption(21, 40));
            sachTuyetKy.itemOptions.add(new Item.ItemOption(30, 0));
            sachTuyetKy.itemOptions.add(new Item.ItemOption(87, 0));
            sachTuyetKy.itemOptions.add(new Item.ItemOption(214, 5));
            sachTuyetKy.itemOptions.add(new Item.ItemOption(215, 1000));
            InventoryServiceNew.gI().addItemBag(player, sachTuyetKy);
            CombineServiceNew.gI().sendEffSuccessVip(player, sachTuyetKy.template.iconID);
            Util.setTimeout(() -> {
                Service.gI().sendServerMessage(player, "Bạn nhận được " + sachTuyetKy.template.name);
                CombineServiceNew.gI().baHatMit.npcChat(player, "Chúc mừng con nhé");
            }, 2000);
        } else {
            subCuonSach = 5;
            CombineServiceNew.gI().sendEffFailVip(player);
            Util.setTimeout(() -> {
                CombineServiceNew.gI().baHatMit.npcChat(player, "Chúc con may mắn lần sau, đừng buồn con nhé");
            }, 2000);
        }
        InventoryServiceNew.gI().subQuantityItemsBag(player, cuonSachCu, subCuonSach);
        InventoryServiceNew.gI().subQuantityItemsBag(player, kimBamGiay, 1);
        InventoryServiceNew.gI().sendItemBags(player);
    }
}
