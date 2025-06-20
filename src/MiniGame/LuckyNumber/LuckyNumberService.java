/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MiniGame.LuckyNumber;

import MiniGame.cost.LuckyNumberCost;
import Player.Player;
import Services.ItemTimeService;
import Services.Service;
import Utils.Util;
import network.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrator
 */
public class LuckyNumberService {
    public static void showNumberPlayer(Player player, String number) {
        Message msg = null;
        try {
            msg = new Message(-126);
            msg.writer().writeByte(0); // type
            msg.writer().writeUTF(number); // number player select
            player.sendMessage(msg);
        } catch (Exception e) {
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public static void spinNumber(Player player, String result, String finish) {
        Message msg = null;
        try {
            msg = new Message(-126);
            msg.writer().writeByte(1); // type
            msg.writer().writeByte(1); // type
            msg.writer().writeUTF(result); // kết quả
            msg.writer().writeUTF(finish); // thông báo cho người chơi
            player.sendMessage(msg);
        } catch (Exception e) {
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    public static void addData(Player player, int number, boolean isGem) {
        LuckNumberData data = new LuckNumberData();
        data.id = player.id;
        data.number = number;
        data.isGem = isGem;
        data.isReward = false;
        MiniGame.LuckyNumber.LuckyNumber.players.add(data);
        ItemTimeService.gI().sendItemTime(player, 2295, LuckyNumberCost.timeGame);
    }

    public static void addDataResul(Player player, int number, int money, String text) {
        LuckNumberData.LuckyNumberResul data = new LuckNumberData.LuckyNumberResul();
        data.id = player.id;
        data.money = money;
        data.number = number;
        data.text = text;
        LuckyNumber.DATA_REWARD_PLAYER_WIN.add(data);
    }

    public static List listNumberSelect(Player pl) {
        List<Integer> listData = new ArrayList<>();
        for (LuckNumberData data : MiniGame.LuckyNumber.LuckyNumber.players) {
            if (data.id == pl.id) {
                listData.add(data.number);
            }
        }
        return listData;
    }

    public static String strNumber(long id) {
        String number = "";
        List<LuckNumberData> pl = MiniGame.LuckyNumber.LuckyNumber.players.stream().filter(d -> d.id == id).toList();
        for (int i = 0; i < pl.size(); i++) {
            LuckNumberData d = pl.get(i);
            if (d.id == id) {
                number += d.number + (i >= pl.size() - 1 ? "" : ",");
            }
        }
        return number;
    }

    public static String strNumber(int id, boolean isGem) {
        String number = "";
        List<LuckNumberData> pl = MiniGame.LuckyNumber.LuckyNumber.players.stream().filter(d -> d.id == id).toList();
        for (int i = 0; i < pl.size(); i++) {
            LuckNumberData d = pl.get(i);
            number += d.number + (i >= pl.size() - 1 ? "" : ",");
        }
        return number;
    }

    public static int randomOneOddNumber() {
        Random random = new Random();
        int randomOddNumber;
        do {
            randomOddNumber = random.nextInt(100);
        } while (randomOddNumber % 2 == 0);
        return randomOddNumber;
    }

    public static int randomOneEvenNumber() {
        Random random = new Random();
        int randomOddNumber;
        do {
            randomOddNumber = random.nextInt(100);
        } while (randomOddNumber % 2 != 0);
        return randomOddNumber;
    }

    public static void addNumber(Player pl, int number) {
        if (!LuckyNumber.players.stream().filter(d -> d.id == pl.id && d.id == pl.id && d.number == number).toList().isEmpty()) {
            Service.gI().sendThongBao(pl, "Số này bạn đã chọn rồi vui lòng chọn số khác.");
            return;
        }

        if (MiniGame.LuckyNumber.LuckyNumber.players.stream().filter(d -> d.id == pl.id).toList().size() >= 10) {
            Service.gI().sendThongBao(pl, "Bạn đã chọn 10 số rồi không thể chọn thêm");
            return;
        }
        if (pl.iDMark.isGemCSMM()) {
            if (pl.inventory.gem < LuckyNumberCost.costPlayGem) {
                Service.gI().sendThongBao(pl, "Bạn không đủ ngọc, còn thiếu " + (LuckyNumberCost.costPlayGem - pl.inventory.gem) + " ngọc nữa");
                return;
            }
            pl.inventory.gem -= LuckyNumberCost.costPlayGem;
        } else {
            if (pl.inventory.gold < LuckyNumberCost.costPlayGold) {
                Service.gI().sendThongBao(pl, "Bạn không đủ vàng, còn thiếu " + Util.numberToMoney(LuckyNumberCost.costGold - pl.inventory.gold) + " vàng nữa");
                return;
            }
            pl.inventory.gold -= LuckyNumberCost.costPlayGold;
        }
        Service.gI().sendMoney(pl);
        addData(pl, number, pl.iDMark.isGemCSMM());
        showNumberPlayer(pl, strNumber((int) pl.id));
    }

    public static void addOneNumber(Player pl, boolean isOdd) {
        List<Integer> list = listNumberSelect(pl);
        int numberRandom = isOdd ? randomOneOddNumber() : randomOneEvenNumber();
        for (Integer number : list) {
            if (numberRandom == number) {
                addOneNumber(pl, isOdd);
                return;
            }
        }
        if (MiniGame.LuckyNumber.LuckyNumber.players.stream().filter(d -> d.id == pl.id).toList().size() >= 10) {
            Service.gI().sendThongBao(pl, "Bạn đã chọn 10 số rồi không thể chọn thêm");
            return;
        }
        if (pl.iDMark.isGemCSMM()) {
            if (pl.inventory.gem < LuckyNumberCost.costPlayGem) {
                Service.gI().sendThongBao(pl, "Bạn không đủ ngọc, còn thiếu " + (LuckyNumberCost.costPlayGem - pl.inventory.gem) + " ngọc nữa");
                return;
            }
            pl.inventory.gem -= LuckyNumberCost.costPlayGem;
        } else {
            if (pl.inventory.gold < LuckyNumberCost.costPlayGold) {
                Service.gI().sendThongBao(pl, "Bạn không đủ vàng, còn thiếu " + Util.numberToMoney(LuckyNumberCost.costGold - pl.inventory.gold) + " vàng nữa");
                return;
            }
            pl.inventory.gold -= LuckyNumberCost.costPlayGold;
        }
        Service.gI().sendMoney(pl);
        addData(pl, numberRandom, pl.iDMark.isGemCSMM());
        showNumberPlayer(pl, strNumber((int) pl.id));
    }
}
