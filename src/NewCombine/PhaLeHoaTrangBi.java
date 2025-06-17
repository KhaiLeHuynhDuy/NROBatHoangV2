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
import Server.ServerNotify;
import Services.InventoryServiceNew;
import Services.Service;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class PhaLeHoaTrangBi {

    private static float getRatio(int star) {
        switch (star) {
            case 0:
                return 100f;
            case 1:
                return 90f;
            case 2:
                return 80f;
            case 3:
                return 70f;
            case 4:
                return 50f;
            case 5:
                return 30f;
            case 6:
                return 10f;
            case 7:
                return 5f;
            case 8:
                return 1f;
            default:
        };
        return 0;
    }

    private static String getRatioStr(int star) {
        int ratio = (int) getRatio(star);
        if (ratio < 1) {
            ratio = 1;
        }
        return String.valueOf(ratio);
    }

    private static int getGold(int star) {
        switch (star) {
            case 0:
                return 5_000_000;
            case 1:
                return 10_000_000;
            case 2:
                return 20_000_000;
            case 3:
                return 40_000_000;
            case 4:
                return 60_000_000;
            case 5:
                return 90_000_000;
            case 6:
                return 120_000_000;
            case 7:
                return 150_000_000;
            case 8:
                return 180_000_000;
            default:
        };
        return 0;
    }

    private static int getGem(int star) {
        switch (star) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 20;
            case 8:
                return 30;
            default:
        };
        return 0;
    }

    public static void showInfoCombine(Player player) {
        if (player.combineNew.itemsCombine.size() != 1) {
            Service.gI().sendThongBao(player, "Trang bị không phù hợp");
            return;
        }
        Item item = player.combineNew.itemsCombine.get(0);
        if (item == null || !item.isNotNullItem()) {
            return;
        }
        if (item.isHaveOption(93)) {
            Service.gI().sendThongBao(player, "Trang bị có hạn sử dụng, không thể thực hiện");
            return;
        }
        if (!item.canPhaLeHoa()) {
            Service.gI().sendThongBao(player, "Trang bị không phù hợp");
            return;
        }
        int star = item.getOptionParam(107);
        int gem = getGem(star);
        int gold = getGold(star);
        if (star >= CombineServiceNew.MAX_STAR_ITEM) {
            Service.gI().sendThongBao(player, "Đã đạt số pha lê tối đa");
            return;
        }
        StringBuilder text = new StringBuilder();
        text.append(ConstFont.BOLD_BLUE).append(item.template.name).append("\n");
        text.append(ConstFont.BOLD_DARK).append(item.getOptionInfo()).append("\n");
        text.append(ConstFont.BOLD_GREEN).append(star + 1).append(" ô Sao Pha Lê\n");
        text.append(ConstFont.BOLD_BLUE).append("Tỉ lệ thành công: ").append(getRatioStr(star)).append("%\n");
        text.append(player.inventory.gold < gold ? ConstFont.BOLD_RED : ConstFont.BOLD_BLUE).append("Cần ").append(Util.numberToMoney(gold)).append(" vàng");
        if (player.inventory.gold < gold) {
            CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, text.toString(),
                    "Còn thiếu\n" + Util.numberToMoney(gold - player.inventory.gold) + " vàng");
            return;
        }
        CombineServiceNew.gI().baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, text.toString(),
                "Nâng cấp\n" + gem + " ngọc\nx100 lần", "Nâng cấp\n" + gem + " ngọc\nx10 lần", "Nâng cấp\n" + gem + " ngọc", "Từ chối");
    }

    public static void phaLeHoa(Player player, int... numm) {
        int n = 1;
        if (numm.length > 0) {
            n = numm[0];
        }
        if (!player.combineNew.itemsCombine.isEmpty()) {
            Item item = player.combineNew.itemsCombine.get(0);
            if (item == null || !item.isNotNullItem() || item.isHaveOption(93) || !item.canPhaLeHoa()) {
                return;
            }
            int star = item.getOptionParam(107);
            if (star >= CombineServiceNew.MAX_STAR_ITEM) {
                return;
            }
            int gold = getGold(star);
            int gem = getGem(star);
            if (n == 1) {
                if (player.inventory.gold < gold) {
                    return;
                } else if (player.inventory.getGem() < gem) {
                    Service.gI().sendThongBao(player, "Bạn không đủ ngọc, còn thiếu " + (gem - player.inventory.getGem()) + " ngọc nữa");
                    return;
                }
            }
            int num = 0;
            boolean success = false;
            for (int i = 0; i < n; i++) {
                num = i + 1;
                if (player.inventory.getGem() < gem) {
                    Service.gI().sendThongBao(player, "Sau " + i + " lần nâng cấp thất bại, bạn không đủ ngọc để tiếp tục.");
                    break;
                }
                if (player.inventory.gold < gold) {
                    Service.gI().sendThongBao(player, "Sau " + i + " lần nâng cấp thất bại, bạn không đủ vàng để tiếp tục.");
                    break;
                }
                player.inventory.gold -= gold;
                player.inventory.subGem(gem);
                if (Util.isTrue(getRatio(star), 2000)) {
                    success = true;
                    break;
                }
            }
            if (success) {
                item.addOptionParam(107, 1);
                if (n > 1) {
                    Service.gI().sendThongBao(player, "Thành công sau " + num + " lần nâng cấp.");
                }
                CombineServiceNew.gI().sendEffectSuccessCombine(player);
            } else {
                CombineServiceNew.gI().sendEffectFailCombine(player);
            }
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            CombineServiceNew.gI().reOpenItemCombine(player);
        }
    }

}
