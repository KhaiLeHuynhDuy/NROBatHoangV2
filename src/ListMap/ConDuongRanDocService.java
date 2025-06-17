/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListMap;

import Clan.Clan;
import Clan.ClanMember;
import Services.func.ChangeMapService;
import Maps.Zone;
import Player.Player;
import Services.Service;
import Utils.TimeUtil;
import Utils.Util;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ConDuongRanDocService {

    private static ConDuongRanDocService instance;

    public static ConDuongRanDocService gI() {
        if (instance == null) {
            instance = new ConDuongRanDocService();
        }
        return instance;
    }

    public List<ConDuongRanDoc> conDuongRanDocs;

    private ConDuongRanDocService() {
        this.conDuongRanDocs = new ArrayList<>();
        for (int i = 0; i < ConDuongRanDoc.AVAILABLE; i++) {
            this.conDuongRanDocs.add(new ConDuongRanDoc(i));
        }
    }

    public void addMapConDuongRanDoc(int id, Zone zone) {
        this.conDuongRanDocs.get(id).getZones().add(zone);
    }

    public void openConDuongRanDoc(Player player, byte level) {
        Clan clan = player.clan;
        if (clan != null) {
            ClanMember cm = clan.getClanMember((int) player.id);
            if (cm != null) {
//                if (player.clanMember.getNumDateFromJoinTimeToToday() < 2) {
//                    return;
//                }
                if (!player.joinCDRD && !Util.isTimeDifferenceGreaterThanNDays(player.lastTimeJoinCDRD, 7)) {
                    Service.gI().sendThongBao(player, "Vui lòng đợi " + TimeUtil.getDateLeft(player.lastTimeJoinCDRD, 7 * 24 * 60 * 60) + " nữa");
                    return;
                } else if (!player.joinCDRD) {
                    player.talkToThuongDe = false;
                    player.talkToThanMeo = false;
                }
                if (player.clan.ConDuongRanDoc == null) {
                    if (level >= 1 && level <= 110) {
                        ConDuongRanDoc conDuongRanDoc = null;
                        for (ConDuongRanDoc cdrd : this.conDuongRanDocs) {
                            if (!cdrd.isOpened) {
                                conDuongRanDoc = cdrd;
                                break;
                            }
                        }
                        if (conDuongRanDoc != null) {
                            conDuongRanDoc.openConDuongRanDoc(player, player.clan, level);
                        } else {
                            Service.gI().sendThongBao(player, "Con đường rắn độc đã đầy, hãy quay lại sau 30 phút");
                            return;
                        }
                    } else {
                        Service.gI().sendThongBao(player, "Không thể thực hiện");
                        return;
                    }
                }
                if (player.clan.ConDuongRanDoc != null) {
                    player.joinCDRD = true;
                    player.lastTimeJoinCDRD = player.clan.lastTimeOpenConDuongRanDoc;
                    ChangeMapService.gI().changeMap(player, 143, -1, 1055 + (Util.nextInt(-10, 10)), 0);
                }
            }
        }
    }
}
