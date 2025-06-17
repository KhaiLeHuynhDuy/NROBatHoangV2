/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NewCombine;

import Consts.ConstNpc;
import Services.func.CombineServiceNew;
import Item.Item;
import Player.Player;
import Server.Manager;
import Services.InventoryServiceNew;
import Services.ItemService;
import Services.RewardService;
import Services.Service;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class NangCapKichHoat {
    
    Item bkt = null;

    public static boolean isDoThanLinh(Item item) {
        if (item.template.id >= 555 && item.template.id <= 567) {
            return true;
        }
        return false;
    }

    public static void showInfoCombine(Player player) {
        if (player.combineNew != null && player.combineNew.itemsCombine != null && player.combineNew.itemsCombine.size() == 1) {
            Item trangbiThanLinh = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (isDoThanLinh(item)) {
                    trangbiThanLinh = item;
                }
            }
            player.combineNew.goldCombine = 1_000_000_000;
            int goldCombie = player.combineNew.goldCombine;
            if (trangbiThanLinh != null) {
                String npcSay = "Sau khi cường hoá, sẽ được nâng cấp trang bị Huỷ Diệt thành trang bị Kích hoạt";
                CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                        "Cường hoá\n" + Util.numberToMoney(goldCombie) + " vàng", "Từ chối");
            } else {
                Service.gI().sendThongBaoOK(player, "Cần 1 trang bị huỷ diệt");
            }
        } else {
            Service.gI().sendThongBaoOK(player, "Cần 1 trang bị huỷ diệt");
        }
    }

    public static void startCombine(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            int gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Bạn không đủ vàng, còn thiếu " + Util.numberToMoney(gold - player.inventory.gold) + " vàng nữa");
                Service.gI().sendMoney(player);
                return;
            }
            Item trangbiThanLinh = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (isDoThanLinh(item)) {
                    trangbiThanLinh = item;
                }
            }
            int gender = trangbiThanLinh.template.gender;
            int playerGender = player.gender;
            int[] maleOptions = {129, 141, 127, 139, 128, 140};
            int[] femaleOptions = {132, 144, 131, 143, 130, 142};
            int[] otherOptions = {135, 138, 133, 136, 134, 137};
            int[] selectedOptions;
            if (gender == 0 || gender == 3 && playerGender == 0) {
                selectedOptions = maleOptions;
            } else if (gender == 1 || gender == 3 && playerGender == 1) {
                selectedOptions = femaleOptions;
            } else {
                selectedOptions = otherOptions;
            }
            Item newItem = null;
            if (trangbiThanLinh.template.type == 4) {
                newItem = ItemService.gI().createNewItem((short) 12);
            } else {
                newItem = ItemService.gI().createNewItem(Manager.trangBiKichHoat[gender][trangbiThanLinh.template.type]);
            }
            RewardService.gI().initBaseOptionClothes(newItem.template.id, newItem.template.type, newItem.itemOptions);
            if (Util.isTrue(15, 100)) {
                newItem.itemOptions.add(new Item.ItemOption(selectedOptions[0], 0));
                newItem.itemOptions.add(new Item.ItemOption(selectedOptions[1], 0));
            } else {
                if (Util.isTrue(75, 100)) {
                    newItem.itemOptions.add(new Item.ItemOption(selectedOptions[2], 0));
                    newItem.itemOptions.add(new Item.ItemOption(selectedOptions[3], 0));
                } else {
                    newItem.itemOptions.add(new Item.ItemOption(selectedOptions[4], 0));
                    newItem.itemOptions.add(new Item.ItemOption(selectedOptions[5], 0));
                }
            }
            InventoryServiceNew.gI().addItemBag(player, newItem);
            InventoryServiceNew.gI().subQuantityItemsBag(player, trangbiThanLinh, 1);
            CombineServiceNew.gI().sendEffectSuccessCombine(player);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            CombineServiceNew.gI().reOpenItemCombine(player);
        }
    }
}
