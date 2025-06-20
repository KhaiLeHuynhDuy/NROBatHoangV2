/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MiniGame.RockPaperScissors;

import Consts.ConstFont;
import Consts.ConstMiniGame;
import NPC.Npc;
import Player.Player;
import Services.Service;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class RockPaperScissorsService {

    public static void loseKeoBuaBao(Npc npc, Player player) {
        String ketQuaPlayer = convertNumberToString(player.iDMark.getKeoBuaBaoPlayer());
        String ketQuaServer = convertNumberToString(player.iDMark.getKeoBuaBaoServer());
        String money = Util.numberFormat(player.iDMark.getMoneyKeoBuaBao());
        npc.createOtherMenu(player, ConstMiniGame.MENU_PLAY_KEO_BUA_BAO,
                ConstFont.BOLD_RED + "Bạn ra cái <" + ketQuaPlayer + ">\n" +
                        "Tôi ra cái <" + ketQuaServer + ">\n" +
                        "Tôi thắng nhé hihi\n" +
                        "Bạn bị trừ " + money + " vàng",
                "Kéo", "Búa", "Bao", "Đổi\nmức cược", "Nghỉ chơi");
        player.inventory.gold -= player.iDMark.getMoneyKeoBuaBao();
        Service.gI().sendMoney(player);
    }

    public static void winKeoBuaBao(Npc npc, Player player) {
        String ketQuaPlayer = convertNumberToString(player.iDMark.getKeoBuaBaoPlayer());
        String ketQuaServer = convertNumberToString(player.iDMark.getKeoBuaBaoServer());
        String money = Util.numberFormat(player.iDMark.getMoneyKeoBuaBao());
        npc.createOtherMenu(player, ConstMiniGame.MENU_PLAY_KEO_BUA_BAO,
                ConstFont.BOLD_GREEN + "Bạn ra cái <" + ketQuaPlayer + ">\n" +
                        "Tôi ra cái <" + ketQuaServer + ">\n" +
                        "Bạn thắng rồi huhu\n" +
                        "Bạn nhận được " + money + " vàng",
                "Kéo", "Búa", "Bao", "Đổi\nmức cược", "Nghỉ chơi");
        player.inventory.gold += player.iDMark.getMoneyKeoBuaBao();
        Service.gI().sendMoney(player);
    }

    public static void hoaKeoBuaBao(Npc npc, Player player) {
        String ketQuaPlayer = convertNumberToString(player.iDMark.getKeoBuaBaoPlayer());
        String ketQuaServer = convertNumberToString(player.iDMark.getKeoBuaBaoServer());
        String money = Util.numberFormat(player.iDMark.getMoneyKeoBuaBao());
        npc.createOtherMenu(player, ConstMiniGame.MENU_PLAY_KEO_BUA_BAO,
                ConstFont.BOLD_BLUE + "Bạn ra cái <" + ketQuaPlayer + ">\n" +
                        "Tôi ra cái <" + ketQuaServer + ">\n" +
                        "Hoà nhau nhé haha",
                "Kéo", "Búa", "Bao", "Đổi\nmức cược", "Nghỉ chơi");
    }

    public static String convertNumberToString(int i) {
        switch (i) {
            case 0:
                return "Kéo";
            case 1:
                return "Búa";
            case 2:
                return "Bao";
        }
        return "";
    }

    public static int checkWinLose(Player player) { // 1 là win, 2 là thua, 3 là hoà
        if (player.iDMark.getKeoBuaBaoPlayer() == player.iDMark.getKeoBuaBaoServer()) {
            return 3;
        }
        switch (player.iDMark.getKeoBuaBaoPlayer()) {
            case RockPaperScissors.KEO:
                if (player.iDMark.getKeoBuaBaoServer() == RockPaperScissors.BUA) {
                    return 2;
                } else if (player.iDMark.getKeoBuaBaoServer() == RockPaperScissors.BAO) {
                    return 1;
                }
                break;
            case RockPaperScissors.BUA:
                if (player.iDMark.getKeoBuaBaoServer() == RockPaperScissors.KEO) {
                    return 1;
                } else if (player.iDMark.getKeoBuaBaoServer() == RockPaperScissors.BAO) {
                    return 2;
                }
                break;
            case RockPaperScissors.BAO:
                if (player.iDMark.getKeoBuaBaoServer() == RockPaperScissors.KEO) {
                    return 2;
                } else if (player.iDMark.getKeoBuaBaoServer() == RockPaperScissors.BUA) {
                    return 1;
                }
                break;
        }
        return 2;
    }
}

