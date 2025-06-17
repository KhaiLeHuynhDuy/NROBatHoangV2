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
public class TaoDaHematite {

    public static void showInfoCombine(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
            Service.gI().sendThongBao(player, "Cần 1 ô trống trong hành trang.");
            Service.gI().hideWaitDialog(player);
            return;
        }
        if (player.combineNew.itemsCombine.size() != 1) {
            Service.gI().sendThongBao(player, "Cần 5 viên sao pha lê cấp 2 cùng màu");
            return;
        }
        Item saoPhaLeC2 = player.combineNew.itemsCombine.get(0);

        if (saoPhaLeC2 == null || !saoPhaLeC2.isNotNullItem() || !saoPhaLeC2.isDaPhaLeC2()) {
            Service.gI().sendThongBao(player, "Cần 5 viên sao pha lê cấp 2 cùng màu");
            return;
        }
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_BLUE).append("Ta sẽ phù phép\n");
        text.append(ConstFont.BOLD_BLUE).append("tạo đá Hematite\n");
        text.append(saoPhaLeC2.quantity >= 5 ? ConstFont.BOLD_GREEN : ConstFont.BOLD_RED).append("Cần 5 Sao pha lê cấp 2 cùng màu\n");
        text.append(player.inventory.gold >= 1_000_000 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Cần 1 Tr vàng\n");
        if (saoPhaLeC2.quantity < 5) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(), "Còn thiếu\n" + (5 - saoPhaLeC2.quantity) + " " + saoPhaLeC2.template.name);
            return;
        }
        if (player.inventory.gold < 1_000_000) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(), "Còn thiếu\n" + Util.numberToMoney(1_000_000 - player.inventory.gold) + " vàng");
            return;
        }
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, text.toString(), "Tạo đá\nHematite", "Từ chối");
    }

    public static void taoDaHematite(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
            return;
        }
        if (player.combineNew.itemsCombine.size() != 1) {
            Service.gI().sendThongBao(player, "Cần 5 viên sao pha lê cấp 2 cùng màu");
            return;
        }
        Item saoPhaLeC2 = player.combineNew.itemsCombine.get(0);

        if (saoPhaLeC2 == null || !saoPhaLeC2.isNotNullItem() || !saoPhaLeC2.isDaPhaLeC2()) {
            return;
        }
        if (player.inventory.gold < 1_000_000 || saoPhaLeC2.quantity < 5) {
            return;
        }
        CombineServiceNew.gI().baHatMit.npcChat(player, "Bư cô lô, ba cô la, bư ra bư zô...");
        Item daHematite = ItemService.gI().createNewItem((short) 1458);
        daHematite.itemOptions.add(new Item.ItemOption(30, 0));
        daHematite.itemOptions.add(new Item.ItemOption(87, 0));
        CombineServiceNew.gI().sendEffectCombineItem(player, (byte) 7, (short) daHematite.template.iconID, (short) -1);
        InventoryServiceNew.gI().addItemBag(player, daHematite);
        Service.gI().sendThongBao(player, "Bạn nhận được " + daHematite.template.name);
        Util.setTimeout(() -> {
            CombineServiceNew.gI().baHatMit.npcChat(player, "Chúc mừng con nhé");
        }, 2000);
        player.inventory.gold -= 1_000_000;
        InventoryServiceNew.gI().subQuantityItemsBag(player, saoPhaLeC2, 1);
        InventoryServiceNew.gI().sendItemBags(player);
        Service.gI().sendMoney(player);
        CombineServiceNew.gI().reOpenItemCombine(player);
    }
}
