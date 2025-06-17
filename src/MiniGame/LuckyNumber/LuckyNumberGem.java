/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MiniGame.LuckyNumber;

import Consts.ConstMiniGame;
import MiniGame.cost.LuckyNumberCost;
import NPC.Npc;
import Player.Player;
import Utils.Util;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Administrator
 */
public class LuckyNumberGem {
    public static String showOneResult() {
        return !MiniGame.LuckyNumber.LuckyNumber.DATA_RESULT.isEmpty() ? MiniGame.LuckyNumber.LuckyNumber.DATA_RESULT.get(MiniGame.LuckyNumber.LuckyNumber.DATA_RESULT.size() - 1).toString().formatted("%02d") : "";
    }

    public static String showTenResult() {
        StringBuilder previousResults = new StringBuilder();
        List<Integer> dataKQ_CSMM = MiniGame.LuckyNumber.LuckyNumber.DATA_RESULT;

        if (dataKQ_CSMM != null && !dataKQ_CSMM.isEmpty()) {
            int start = Math.max(0, dataKQ_CSMM.size() - 10);
            List<Integer> lastTenResults = dataKQ_CSMM.subList(start, dataKQ_CSMM.size());

            String resultString = lastTenResults.stream()
                    .map(i -> String.format("%02d", i))
                    .collect(Collectors.joining(","));

            previousResults.append(resultString);
        }
        return previousResults.toString();
    }

    public static String showTenPlayResult() {
        StringBuilder previousResults = new StringBuilder();
        List<String> dataKQ_CSMM = MiniGame.LuckyNumber.LuckyNumber.DATA_PLAYER_RESULT;

        if (dataKQ_CSMM != null && !dataKQ_CSMM.isEmpty()) {
            int start = Math.max(0, dataKQ_CSMM.size() - 10);
            List<String> lastTenResults = dataKQ_CSMM.subList(start, dataKQ_CSMM.size());

            String resultString = lastTenResults.stream().collect(Collectors.joining(","));

            previousResults.append(resultString);
        }
        return previousResults.toString();
    }

    public static void showMenuCSMM(Npc npc, Player player) {
        String ketQua = showOneResult();
        String listKetQua = showTenResult();
        String listPlayer = showTenPlayResult();
        String resultPlayerSelect = LuckyNumberService.strNumber((int) player.id, true);
        String npcSay = "";
        if (!ketQua.isEmpty()) {
            npcSay += "Kết quả giải trước: " + ketQua + "\n";
        }
        if (!listKetQua.isEmpty()) {
            npcSay += listKetQua + "\n";
        }
        if (!listPlayer.isEmpty()) {
            npcSay += listPlayer + "\n";
        }
        npcSay += "Tổng giải thưởng: " + Util.numberFormat(LuckyNumberCost.costGem) + " ngọc\n"
                + "<" + LuckyNumberCost.timeGame + "> giây";
        if (!resultPlayerSelect.isEmpty()) {
            npcSay += "\nCác số bạn chọn: " + resultPlayerSelect;
        }
        npc.createOtherMenu(player, ConstMiniGame.MENU_PLAY_LUCKY_NUMBER_GEM, npcSay,
                "Cập nhật",
                "1 Số\n5 ngọc xanh",
                "Ngẫu nhiên\n1 số lẻ\n5 ngọc xanh",
                "Ngẫu nhiên\n1 số chẵn\n5 ngọc xanh",
                "Hướng\ndẫn\nthêm",
                "Đóng");
    }
}

