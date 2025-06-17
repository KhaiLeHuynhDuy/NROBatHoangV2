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
public class DecisionMakerRuby {
    public static void showMenuSelect(Npc npc, Player player) {
        long totalNormal = DecisionMakerService.getTotalMoney(DecisionMakerCost.HONG_NGOC, true);
        long totalVIP = DecisionMakerService.getTotalMoney(DecisionMakerCost.HONG_NGOC, false);
        npc.createOtherMenu(player, ConstMiniGame.MENU_PLAY_DECISION_MAKER_RUBY,
                "Tổng giải thưởng: " + Util.numberToText(totalNormal) + " hồng ngọc, cơ hội trúng của bạn là: " + DecisionMakerService.getPercent(player, DecisionMakerCost.HONG_NGOC, true) + "%\n"
                        + "Tổng giải VIP: " + Util.numberToText(totalVIP) + " hồng ngọc, cơ hội trúng của bạn là: " + DecisionMakerService.getPercent(player, DecisionMakerCost.HONG_NGOC, false) + "%\n"
                        + "Thời gian còn lại: " + DecisionMakerCost.timeGame + " giây.",
                "Cập nhật",
                "Thường\n10 ngọc\nxanh",
                "VIP\n100 ngọc\nxanh",
                "Đóng"
        );
    }

    public static void selectPlay(Npc npc, Player player, boolean isNormal) {
        int money = isNormal ? DecisionMakerCost.COST_RUBY_NORMAL : DecisionMakerCost.COST_RUBY_VIP;
        if (player.inventory.ruby < money) {
            Service.gI().sendThongBao(player, "Bạn không đủ ngọc, còn thiếu " + (money - player.inventory.ruby) + " ngọc nữa");
            return;
        }
        player.inventory.ruby -= money;
        Service.gI().sendMoney(player);
        DecisionMakerService.newData(player, money, DecisionMakerCost.HONG_NGOC, isNormal);
        showMenuSelect(npc, player);
    }
}