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
import Utils.Util;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class GasService {

    private static GasService instance;

    public static GasService gI() {
        if (instance == null) {
            instance = new GasService();
        }
        return instance;
    }

    public List<Gas> khiGasHuyDiets;

    private GasService() {
        this.khiGasHuyDiets = new ArrayList<>();
        for (int i = 0; i < Gas.AVAILABLE; i++) {
            this.khiGasHuyDiets.add(new Gas(i));
        }
    }

    public void addMapKhiGasHuyDiet(int id, Zone zone) {
        this.khiGasHuyDiets.get(id).getZones().add(zone);
    }

    public void openKhiGasHuyDiet(Player player, byte level) {
        Clan clan = player.clan;
        if (clan != null) {
            ClanMember cm = clan.getClanMember((int) player.id);
            if (cm != null) {
//                if (player.clanMember.getNumDateFromJoinTimeToToday() < 1) {
//                    return;
//                }
                if (clan.members.size() < Gas.N_PLAYER_CLAN) {
                    return;
                }
                if (player.clan.KhiGasHuyDiet == null) {
                    if (level >= 1 && level <= 110) {
                        if (clan.isLeader(player)) {
                            Gas khiGasHuyDiet = null;
                            for (Gas kghd : this.khiGasHuyDiets) {
                                if (!kghd.isOpened) {
                                    khiGasHuyDiet = kghd;
                                    break;
                                }
                            }
                            if (khiGasHuyDiet != null) {
                                if (Util.isAfterMidnight(player.clan.lastTimeOpenKhiGasHuyDiet)) {
                                    player.clan.timesPerDayKGHD = 1;
                                } else {
                                    player.clan.timesPerDayKGHD++;
                                    if (player.clan.timesPerDayKGHD > 3) {
                                        Service.gI().sendThongBao(player, "Hãy chờ đến ngày mai");
                                        return;
                                    }
                                }
                                khiGasHuyDiet.openKhiGasHuyDiet(player, player.clan, level);
                            } else {
                                Service.gI().sendThongBao(player, "Destron Gas đã đầy, hãy quay lại sau 30 phút");
                                return;
                            }
                        }
                    } else {
                        Service.gI().sendThongBao(player, "Không thể thực hiện");
                        return;
                    }
                }
                if (player.clan.KhiGasHuyDiet != null && !player.iDMark.isGoToKGHD()) {
                    player.iDMark.setLastTimeGoToKGHD(System.currentTimeMillis());
                    player.iDMark.setGoToKGHD(true);
                    ChangeMapService.gI().changeMapBySpaceShip(player, 149, -1, -1);
                    Service.gI().Transport(player, 1);
                }
            }
        }
    }
}

