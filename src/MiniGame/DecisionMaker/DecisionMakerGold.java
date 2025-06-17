/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MiniGame.DecisionMaker;

import Consts.ConstMiniGame;
import MiniGame.cost.DecisionMakerCost;
import NPC.Npc;
import Player.Player;
import Services.Service;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class DecisionMakerGold {

    public static void showMenuSelect(Npc npc, Player player) {
        long totalNormal = DecisionMakerService.getTotalMoney(DecisionMakerCost.VANG, true);
        long totalVIP = DecisionMakerService.getTotalMoney(DecisionMakerCost.VANG, false);
        npc.createOtherMenu(player, ConstMiniGame.MENU_PLAY_DECISION_MAKER_GOLD,
                "Tổng giải thưởng: " + Util.numberToText(totalNormal) + " vàng, cơ hội trúng của bạn là: " + DecisionMakerService.getPercent(player, DecisionMakerCost.VANG, true) + "%\n"
                        + "Tổng giải VIP: " + Util.numberToText(totalVIP) + " vàng, cơ hội trúng của bạn là: " + DecisionMakerService.getPercent(player, DecisionMakerCost.VANG, false) + "%\n"
                        + "Thời gian còn lại: " + DecisionMakerCost.timeGame + " giây.",
                "Cập nhật",
                "Thường\n1 triệu\nvàng",
                "VIP\n10 triệu\nvàng",
                "Đóng"
        );
    }

    public static void selectPlay(Npc npc, Player player, boolean isNormal) {
        int money = isNormal ? DecisionMakerCost.COST_GOLD_NORMAL : DecisionMakerCost.COST_GOLD_VIP;
        if (player.inventory.gold < money) {
            Service.gI().sendThongBao(player, "Bạn không đủ vàng, còn thiếu " + (money - player.inventory.gold) + " vàng nữa");
            return;
        }
        player.inventory.gold -= money;
        Service.gI().sendMoney(player);
        DecisionMakerService.newData(player, money, DecisionMakerCost.VANG, isNormal);
        showMenuSelect(npc, player);
    }

}

