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
public class CuongHoaLoSaoPhaLe {

    public static void showInfoCombine(Player player) {
        if (player.combineNew.itemsCombine.size() != 3) {
            Service.gI().sendThongBao(player, "Cần 1 trang bị có ô sao pha lê thứ 8 trở lên chưa cường hóa\n1 đá Hematite\n1 dùi đục");
            return;
        }
        Item hematite = null;
        Item duiDuc = null;
        Item trangBi = null;

        for (Item item : player.combineNew.itemsCombine) {
            if (item.template.type < 5) {
                trangBi = item;
            } else if (item.template.id == 1438) {
                duiDuc = item;
            } else if (item.template.id == 1458) {
                hematite = item;
            }
        }

        if (trangBi == null || duiDuc == null || hematite == null) {
            Service.gI().sendThongBao(player, "Cần 1 trang bị có ô sao pha lê thứ 8 trở lên chưa cường hóa\n1 đá Hematite\n1 dùi đục");
            return;
        }

        int star = trangBi.getOptionParam(107);
        int starCuongHoa = trangBi.getOptionParam(249);

        if (star < 8 || star == starCuongHoa) {
            Service.gI().sendThongBao(player, "Cần 1 trang bị có ô sao pha lê thứ 8 trở lên chưa cường hóa\n1 đá Hematite\n1 dùi đục");
            return;
        }
        starCuongHoa++;
        if (starCuongHoa < 8) {
            starCuongHoa = 8;
        }
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_BLUE).append("Cường hóa\n");
        text.append(ConstFont.BOLD_BLUE).append("Ô Sao Pha lê thứ ").append(starCuongHoa).append("\n");
        text.append(ConstFont.BOLD_GREEN).append("Cần 1 Hematite\n");
        text.append(ConstFont.BOLD_GREEN).append(trangBi.template.name).append("\n");
        text.append(ConstFont.BOLD_GREEN).append("Tỉ lệ thành công: 50%\n");
        text.append(player.inventory.getGemAndRuby() >= 50 ? ConstFont.BOLD_BLUE : ConstFont.BOLD_RED).append("Cần 50 ngọc");
        if (player.inventory.getGemAndRuby() < 50) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(), "Còn thiếu\n" + Util.numberToMoney(50 - player.inventory.getGemAndRuby()) + " ngọc");
            return;
        }
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, text.toString(), "Cường hóa", "Từ chối");
    }

    public static void cuongHoaLoSaoPhaLe(Player player) {
        if (player.combineNew.itemsCombine.size() != 3) {
            return;
        }
        Item hematite = null;
        Item duiDuc = null;
        Item trangBi = null;

        for (Item item : player.combineNew.itemsCombine) {
            if (item.template.type < 5) {
                trangBi = item;
            } else if (item.template.id == 1438) {
                duiDuc = item;
            } else if (item.template.id == 1458) {
                hematite = item;
            }
        }

        if (trangBi == null || duiDuc == null || hematite == null) {
            return;
        }

        int star = trangBi.getOptionParam(107);
        int starCuongHoa = trangBi.getOptionParam(249);

        if (star < 8 || star == starCuongHoa) {
            return;
        }
        starCuongHoa++;
        if (starCuongHoa < 8) {
            starCuongHoa = 8;
        }
        if (player.inventory.getGemAndRuby() < 50) {
            return;
        }
        CombineServiceNew.gI().baHatMit.npcChat(player, "Bư cô lô, ba cô la, bư ra bư zô...");
        if (Util.isTrue(50, 100)) {
            if (starCuongHoa == 8) {
                trangBi.addOptionParam(249, 8);
            } else {
                trangBi.addOptionParam(249, 1);
            }
            CombineServiceNew.gI().sendEffectCombineItem(player, (byte) 7, (short) trangBi.template.iconID, (short) -1);
            Util.setTimeout(() -> {
                CombineServiceNew.gI().baHatMit.npcChat(player, "Chúc mừng con nhé");
            }, 2000);
        } else {
            CombineServiceNew.gI().sendEffectCombineItem(player, (byte) 8, (short) -1, (short) -1);
            Util.setTimeout(() -> {
                CombineServiceNew.gI().baHatMit.npcChat(player, "Chúc con may mắn lần sau, đừng buồn con nhé");
            }, 2000);
        }
        player.inventory.subGemAndRuby(50);
        InventoryServiceNew.gI().subQuantityItemsBag(player, hematite, 1);
        InventoryServiceNew.gI().subQuantityItemsBag(player, duiDuc, 1);
        InventoryServiceNew.gI().sendItemBags(player);
        Service.gI().sendMoney(player);
        CombineServiceNew.gI().reOpenItemCombine(player);
    }
}
