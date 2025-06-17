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
import Services.Service;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class GiamDinhSach {

    public static void showInfoCombine(Player player) {
        if (player.combineNew.itemsCombine.size() != 2) {
            Service.gI().sendThongBao(player, "Cần Sách Tuyệt Kỹ và bùa giám định.");
            return;
        }
        Item sachTuyetKy = null;
        Item buaGiamDinh = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item.isSachTuyetKy() || item.isSachTuyetKy2()) {
                sachTuyetKy = item;
            } else if (item.template.id == 1270) {
                buaGiamDinh = item;
            }
        }
        if (sachTuyetKy == null || buaGiamDinh == null) {
            Service.gI().sendThongBao(player, "Cần Sách Tuyệt Kỹ và bùa giám định.");
            return;
        }
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_GREEN).append("Giám định ").append(sachTuyetKy.template.name).append(" ?\n");
        text.append(ConstFont.BOLD_BLUE).append("Bùa giám định ").append(buaGiamDinh.quantity).append("/1");
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, text.toString(), "Giám định", "Từ chối");
    }

    public static void giamDinhSach(Player player) {
        if (player.combineNew.itemsCombine.size() != 2) {
            return;
        }
        Item sachTuyetKy = null;
        Item buaGiamDinh = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item.isSachTuyetKy() || item.isSachTuyetKy2()) {
                sachTuyetKy = item;
            } else if (item.template.id == 1270) {
                buaGiamDinh = item;
            }
        }
        if (sachTuyetKy == null || buaGiamDinh == null) {
            return;
        }
        if (!sachTuyetKy.isHaveOption(217)) {
            Service.gI().sendServerMessage(player, "Còn cái nịt mà giám");
            return;
        }
        int[] options = {77, 103, 50, 108, 94, 14, 80, 81, 175, 5, 250, 252};
        for (int i = 0; i < sachTuyetKy.itemOptions.size(); i++) {
            Item.ItemOption io = sachTuyetKy.itemOptions.get(i);
            if (io.optionTemplate.id == 217) {
                sachTuyetKy.itemOptions.set(i, new Item.ItemOption(options[Util.nextInt(options.length)], Util.nextInt(1, 10 / Util.nextInt(1, 3))));
            }
        }
        CombineServiceNew.gI().sendEffectSuccessCombine(player);
        InventoryServiceNew.gI().subQuantityItemsBag(player, buaGiamDinh, 1);
        InventoryServiceNew.gI().sendItemBags(player);
        CombineServiceNew.gI().reOpenItemCombine(player);
    }

}
