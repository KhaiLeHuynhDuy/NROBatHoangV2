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
public class EpSaoTrangBi {

    public static int getGem(int star) {
        switch (star) {
            case 7:
                return 20;
            case 8:
                return 30;
            default:
        };
        return 10;
    }

    public static void showInfoCombine(Player player) {
        if (player.combineNew.itemsCombine.size() != 2) {
            Service.gI().sendThongBao(player, "Cần 1 trang bị có lỗ sao pha lê và 1 loại ngọc để ép vào.");
            return;
        }
        Item trangBi = null;
        Item daPhaLe = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item.canPhaLeHoa()) {
                trangBi = item;
            } else if (item.isDaPhaLeEpSao()) {
                daPhaLe = item;
            }
        }
        if (trangBi == null || !trangBi.isNotNullItem() || daPhaLe == null || !daPhaLe.isNotNullItem()) {
            Service.gI().sendThongBao(player, "Cần 1 trang bị có lỗ sao pha lê và 1 loại ngọc để ép vào.");
            return;
        }
        int star = trangBi.getOptionParam(102);
        int starEmpty = trangBi.getOptionParam(107);
        int cuongHoa = trangBi.getOptionParam(249);
        if (star < 7 && daPhaLe.isDaPhaLeMoi()) {
            Service.gI().sendThongBao(player, "Sao pha lê cấp 2 hoặc lấp lánh chỉ dùng cho ô thứ 8 đã cường hóa trở lên.");
            return;
        }
        if (star >= 7 && daPhaLe.isDaPhaLeCu()) {
            Service.gI().sendThongBao(player, "Chỉ có thể nạm Sao pha lê mới.");
            return;
        }
        if (star >= starEmpty) {
            Service.gI().sendThongBao(player, "Cần 1 trang bị có lỗ sao pha lê và 1 loại ngọc để ép vào.");
            return;
        }
        if (star == 7 && cuongHoa == 0 || star >= 8 && cuongHoa < star + 1) {
            Service.gI().sendThongBao(player, "Cần cường hóa ô sao pha lê này trước");
            return;
        }
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_BLUE).append(trangBi.template.name).append("\n");
        text.append(ConstFont.BOLD_DARK).append(star >= 7 ? trangBi.getOptionInfoCuongHoa(daPhaLe) : trangBi.getOptionInfo(daPhaLe)).append("\n");
        text.append(player.inventory.getGemAndRuby() < getGem(star) ? ConstFont.BOLD_RED : ConstFont.BOLD_BLUE).append("Cần 10 ngọc");
        if (player.inventory.getGemAndRuby() < getGem(star)) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(), "Còn thiếu\n" + (getGem(star) - player.inventory.getGemAndRuby()) + " ngọc");
            return;
        }
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, text.toString(), "Nâng cấp\n" + getGem(star) + " ngọc", "Từ chối");
    }

    public static void epSaoTrangBi(Player player) {
        if (player.combineNew.itemsCombine.size() != 2) {
            return;
        }
        Item trangBi = null;
        Item daPhaLe = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item.canPhaLeHoa()) {
                trangBi = item;
            } else if (item.isDaPhaLeEpSao()) {
                daPhaLe = item;
            }
        }
        if (trangBi == null || !trangBi.isNotNullItem() || daPhaLe == null || !daPhaLe.isNotNullItem()) {
            return;
        }
        int star = trangBi.getOptionParam(102);
        int starEmpty = trangBi.getOptionParam(107);
        int cuongHoa = trangBi.getOptionParam(249);
        if (star >= starEmpty || star < 7 && daPhaLe.isDaPhaLeMoi() || star >= 7 && daPhaLe.isDaPhaLeCu()
                || star == 7 && cuongHoa == 0 || star >= 8 && cuongHoa < star + 1
                || player.inventory.getGemAndRuby() < getGem(star)) {
            return;
        }
        trangBi.addOptionParam(102, 1);
        if (star >= 7) {
            if (star == 7) {
                trangBi.itemOptions.add(new Item.ItemOption(248, 0));
            }
            trangBi.itemOptions.add(new Item.ItemOption(daPhaLe.getOptionDaPhaLe().optionTemplate.id, daPhaLe.getOptionDaPhaLe().param));
        } else {
            trangBi.addOptionParam(daPhaLe.getOptionDaPhaLe().optionTemplate.id, daPhaLe.getOptionDaPhaLe().param);
        }
        player.inventory.subGemAndRuby(getGem(star));
        InventoryServiceNew.gI().subQuantityItemsBag(player, daPhaLe, 1);
        CombineServiceNew.gI().sendEffectSuccessCombine(player);
        InventoryServiceNew.gI().sendItemBags(player);
        Service.gI().sendMoney(player);
        CombineServiceNew.gI().reOpenItemCombine(player);
    }
}
