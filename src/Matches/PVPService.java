package Matches;

import PVP.TraThu;
import PVP.ThachDau;
import Consts.ConstNpc;
import Maps.Zone;
import Player.Player;
import network.Message;
import Server.Client;
import Services.NpcService;
import Services.Service;
import Services.func.ChangeMapService;
import Utils.Util;
import java.io.IOException;

public class PVPService {

    private static final int[] GOLD_CHALLENGE = {10000000, 100000000, 1000000000};
    private String[] optionsGoldChallenge;
    //cmd controller
    private static final byte OPEN_GOLD_SELECT = 0;
    private static final byte ACCEPT_PVP = 1;

    private static PVPService i;

    public static PVPService gI() {
        if (i == null) {
            i = new PVPService();
        }
        return i;
    }

    public PVPService() {
        this.optionsGoldChallenge = new String[GOLD_CHALLENGE.length];
        for (int i = 0; i < GOLD_CHALLENGE.length; i++) {
            this.optionsGoldChallenge[i] = Util.formatNumber(GOLD_CHALLENGE[i]) + " vàng";
        }
    }

    //**************************************************************************THÁCH ĐẤU
    public void controllerThachDau(Player player, Message message) {
        try {
            byte action = message.reader().readByte();
            byte type = message.reader().readByte();
            int playerId = message.reader().readInt();
            Player plMap = player.zone.getPlayerInMap(playerId);
            switch (action) {
                case OPEN_GOLD_SELECT:
                    openSelectGold(player, plMap);
                    break;
                case ACCEPT_PVP:
                    acceptPVP(player);
                    break;
            }
        } catch (IOException ex) {

        }
    }

    private void openSelectGold(Player pl, Player plMap) {
        if (pl == null || plMap == null) {
            return;
        }
        if (pl.pvp != null || plMap.pvp != null) {
            Service.gI().hideWaitDialog(pl);
            Service.gI().sendThongBao(pl, "Không thể thực hiện");
            return;
        }
        pl.iDMark.setIdPlayThachDau(plMap.id);
        NpcService.gI().createMenuConMeo(pl, ConstNpc.MAKE_MATCH_PVP,
                -1, plMap.name + " (sức mạnh " + Util.formatNumber(plMap.nPoint.power)
                        + ")\nBạn muốn cược bao nhiêu vàng?",
                this.optionsGoldChallenge);
    }

    public void sendInvitePVP(Player pl, byte selectGold) {
        if (pl == null) {
            return;
        }
        Player plMap = pl.zone.getPlayerInMap(pl.iDMark.getIdPlayThachDau());
        if (plMap == null) {
            Service.gI().sendThongBao(pl, "Đối thủ đã rời khỏi map");
            return;
        }
//        if (!plMap.getSession().actived) {
//            Service.gI().sendThongBao(pl, "Nó Chưa Kích Hoạt Tài Khoản"
//                    + "");
//            return;
//        }
        int goldThachDau = GOLD_CHALLENGE[selectGold];
        if (pl.inventory.gold < goldThachDau) {
            Service.gI().sendThongBao(pl, "Bạn chỉ có " + pl.inventory.gold + " vàng, không đủ tiền cược");
            return;
        }
        if (plMap.inventory.gold < goldThachDau) {
            Service.gI().sendThongBao(pl, "Đối thủ chỉ có " + plMap.inventory.gold + " vàng, không đủ tiền cược");
            return;
        }

        plMap.iDMark.setIdPlayThachDau(pl.id);
        plMap.iDMark.setGoldThachDau(goldThachDau);

        //Gửi message
        Message msg = null;
        try {
            msg = new Message(-59);
            msg.writer().writeByte(3);
            msg.writer().writeInt((int) pl.id);
            msg.writer().writeInt(goldThachDau);
            msg.writer().writeUTF(pl.name + " (sức mạnh " + Util.formatNumber(pl.nPoint.power) + ") muốn thách đấu bạn với mức cược " + goldThachDau);
            plMap.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    private void acceptPVP(Player pl) {
        if (pl == null) {
            return;
        }
        Player plMap = pl.zone.getPlayerInMap(pl.iDMark.getIdPlayThachDau());
        if (plMap == null) {
            Service.gI().sendThongBao(pl, "Đối thủ đã rời khỏi map");
            return;
        }
        if (pl.pvp != null || plMap.pvp != null) {
            Service.gI().hideWaitDialog(pl);
            Service.gI().sendThongBao(pl, "Không thể thực hiện");
            return;
        }
        if (!plMap.getSession().actived) {
            Service.gI().hideWaitDialog(pl);
            Service.gI().sendThongBao(pl, "1 Trong 2 thằng chưa kích hoạt tài khoản");
            return;
        } 
        int goldThachDau = pl.iDMark.getGoldThachDau();

        if (pl.inventory.gold < goldThachDau) {
            Service.gI().sendThongBao(pl, "Không đủ vàng để thực hiện");
            return;
        }
        if (plMap.inventory.gold < goldThachDau) {
            Service.gI().sendThongBao(pl, "Đối thủ không đủ vàng để thực hiện");
            return;
        }
        ThachDau thachDau = new ThachDau(pl, plMap, goldThachDau);
    }

  //**************************************************************************TRẢ THÙ
    public void openSelectRevenge(Player pl, long idEnemy) {
        Player enemy = Client.gI().getPlayer(idEnemy);
        if (enemy == null) {
            Service.gI().sendThongBao(pl, "Kẻ thù hiện đang offline");
            return;
        }

        pl.iDMark.setIdEnemy(idEnemy);
        NpcService.gI().createMenuConMeo(pl, ConstNpc.REVENGE,
                -1, "Bạn muốn đến ngay chỗ hắn?", "Ok", "Từ chối");
    }

    public void acceptRevenge(Player pl) {
        Player enemy = Client.gI().getPlayer(pl.iDMark.getIdEnemy());
        if (enemy == null) {
            Service.gI().sendThongBao(pl, "Kẻ thù hiện đang offline");
            return;
        }
        if (pl.pvp != null || enemy.pvp != null) {
            Service.gI().hideWaitDialog(pl);
            Service.gI().sendThongBao(pl, "Không thể thực hiện");
            return;
        }
        Zone mapGo = enemy.zone;
        if ((mapGo = ChangeMapService.gI().checkMapCanJoin(pl, mapGo)) == null || mapGo.isFullPlayer()) {
            Service.gI().sendThongBao(pl, "Không thể tới ngay lúc này, vui lòng đợi sau ít phút");
            return;
        }
        TraThu traThu = new TraThu(pl, enemy);
    }
}
