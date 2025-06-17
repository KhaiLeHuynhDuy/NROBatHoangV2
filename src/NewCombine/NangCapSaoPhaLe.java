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
public class NangCapSaoPhaLe {

    public static void showInfoCombine(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
            Service.gI().sendThongBao(player, "Cần 1 ô trống trong hành trang.");
            Service.gI().hideWaitDialog(player);
            return;
        }
        if (player.combineNew.itemsCombine.size() != 2) {
            Service.gI().sendThongBao(player, "Cần 1 đá Hematite và 1 Sao Pha Lê cấp 1");
            return;
        }
        Item hematite = null;
        Item saoPhaLeC1 = null;

        for (Item item : player.combineNew.itemsCombine) {
            if (item.template.id == 1458) {
                hematite = item;
            } else if (item.isDaPhaLeC1()) {
                saoPhaLeC1 = item;
            }
        }

        if (hematite == null || saoPhaLeC1 == null) {
            Service.gI().sendThongBao(player, "Cần 1 đá Hematite và 1 Sao Pha Lê cấp 1");
            return;
        }
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_BLUE).append("Nâng cấp Sao Pha lê lên cấp 2\n");
        text.append(ConstFont.BOLD_GREEN).append("Cần 1 Hematite\n");
        text.append(ConstFont.BOLD_GREEN).append("Cần 1 ").append(saoPhaLeC1.template.name).append("\n");
        text.append(ConstFont.BOLD_GREEN).append("Tỉ lệ thành công: 50%\n");
        text.append(player.inventory.gold >= 100_000_000 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Cần 100 Tr vàng\n");
        text.append(player.inventory.getGemAndRuby() >= 50 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Cần 50 ngọc");
        if (player.inventory.getGemAndRuby() < 50) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(), "Còn thiếu\n" + Util.numberToMoney(50 - player.inventory.getGemAndRuby()) + " ngọc");
            return;
        }
        if (player.inventory.gold < 100_000_000) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(), "Còn thiếu\n" + Util.numberToMoney(100_000_000 - player.inventory.gold) + " vàng");
            return;
        }
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, text.toString(), "Làm phép", "Từ chối");
    }

    public static void nangCapSaoPhaLe(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
            return;
        }
        if (player.combineNew.itemsCombine.size() != 2) {
            return;
        }
        Item hematite = null;
        Item saoPhaLeC1 = null;

        for (Item item : player.combineNew.itemsCombine) {
            if (item.template.id == 1458) {
                hematite = item;
            } else if (item.isDaPhaLeC1()) {
                saoPhaLeC1 = item;
            }
        }

        if (hematite == null || saoPhaLeC1 == null) {
            return;
        }
        if (player.inventory.getGemAndRuby() < 50 || player.inventory.gold < 100_000_000) {
            return;
        }
        CombineServiceNew.gI().baHatMit.npcChat(player, "Bư cô lô, ba cô la, bư ra bư zô...");
        if (Util.isTrue(50, 100)) {
            Item saoPhaLeC2 = saoPhaLeC1.cloneItem();
            saoPhaLeC2.quantity = 1;
            saoPhaLeC2.template = ItemService.gI().getTemplate(saoPhaLeC1.template.id + 1010);
            saoPhaLeC2.itemOptions.add(new Item.ItemOption(30, 0));
            saoPhaLeC2.itemOptions.add(new Item.ItemOption(87, 0));
            CombineServiceNew.gI().sendEffectSuccessCombine(player);
            InventoryServiceNew.gI().addItemBag(player, saoPhaLeC2);
            Util.setTimeout(() -> {
                Service.gI().sendThongBao(player, "Bạn nhận được " + saoPhaLeC2.template.name);
                CombineServiceNew.gI().baHatMit.npcChat(player, "Chúc mừng con nhé");
            }, 2000);
        } else {
            CombineServiceNew.gI().sendEffectFailCombine(player);
            Util.setTimeout(() -> {
                CombineServiceNew.gI().baHatMit.npcChat(player, "Chúc con may mắn lần sau, đừng buồn con nhé");
            }, 2000);
        }
        player.inventory.subGemAndRuby(50);
        player.inventory.gold -= 100_000_000;
        InventoryServiceNew.gI().subQuantityItemsBag(player, hematite, 1);
        InventoryServiceNew.gI().subQuantityItemsBag(player, saoPhaLeC1, 1);
        InventoryServiceNew.gI().sendItemBags(player);
        Service.gI().sendMoney(player);
        CombineServiceNew.gI().reOpenItemCombine(player);
    }
}

