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

/**
 *
 * @author Administrator
 */
public class TaySach {

    public static void showInfoCombine(Player player) {
        if (player.combineNew.itemsCombine.size() != 1) {
            Service.gI().sendThongBao(player, "Cần Sách Tuyệt Kỹ để tẩy.");
        }
        Item sachTuyetKy = player.combineNew.itemsCombine.get(0);
        if (sachTuyetKy == null || !sachTuyetKy.isSachTuyetKy() && !sachTuyetKy.isSachTuyetKy2()) {
            Service.gI().sendThongBao(player, "Cần Sách Tuyệt Kỹ để tẩy.");
            return;
        }
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, ConstFont.BOLD_BLUE + "Tẩy Sách Tuyệt Kỹ ?", "Đồng ý", "Từ chối");
    }

    public static void taySach(Player player) {
        if (player.combineNew.itemsCombine.size() != 1) {
        }
        Item sachTuyetKy = player.combineNew.itemsCombine.get(0);
        if (sachTuyetKy == null || !sachTuyetKy.isSachTuyetKy() && !sachTuyetKy.isSachTuyetKy2()) {
            return;
        }
        if (sachTuyetKy.getOptionParam(214) <= 0 || sachTuyetKy.isHaveOption(217)) {
            Service.gI().sendServerMessage(player, "Không thể thực hiện");
            return;
        }
        for (int i = 0; i < sachTuyetKy.itemOptions.size(); i++) {
            Item.ItemOption io = sachTuyetKy.itemOptions.get(i);
            if (io.optionTemplate.id == 21) {
                break;
            }
            sachTuyetKy.itemOptions.set(i, new Item.ItemOption(217, 0));
        }
        sachTuyetKy.subOptionParam(214, 1);
        CombineServiceNew.gI().sendEffectSuccessCombine(player);
        InventoryServiceNew.gI().sendItemBags(player);
        CombineServiceNew.gI().reOpenItemCombine(player);
    }

}
