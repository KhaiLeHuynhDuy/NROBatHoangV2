/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListMap;

import BossMain.Boss;
import BossMain.BossID;
import Consts.ConstNpc;
import Services.func.ChangeMapService;
import ListBoss.Training.TauPayPay;
import ListBoss.Training.Karin;
import ListBoss.Training.KhiBubbles;
import ListBoss.Training.MrPoPo;
import ListBoss.Training.ThanVuTru;
import ListBoss.Training.ThuongDe;
import ListBoss.Training.ToSuKaio;
import ListBoss.Training.Whis;
import ListBoss.Training.Yajiro;
import Maps.Zone;
import Player.Player;
import Services.MapService;
import Services.NpcService;
import Services.Service;
import Utils.Logger;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class TrainingService {

    private static TrainingService instance;

    public static TrainingService gI() {
        if (instance == null) {
            instance = new TrainingService();
        }
        return instance;
    }

    public Player getNonInteractiveNPC(Zone zone, int id) {
        for (Player pl : zone.getNonInteractiveNPCs()) {
            if (pl != null && pl.id == id) {
                return pl;
            }
        }
        return null;
    }

    public int getNpc(int BossId) {
        switch (BossId) {
            case BossID.KARIN:
                return ConstNpc.THAN_MEO_KARIN;
            case BossID.THUONG_DE:
                return ConstNpc.THUONG_DE;
            case BossID.THAN_VU_TRU:
                return ConstNpc.THAN_VU_TRU;
            case BossID.TO_SU_KAIO:
                return ConstNpc.TO_SU_KAIO;
            case BossID.WHIS:
                return ConstNpc.WHIS;
        }
        return -1;
    }

    public void luyenTapEnd(Player pl, int bossID) {
        if (getNpc(bossID) != -1) {
            Service.gI().sendHideNpc(pl, getNpc(bossID), false);
        }
    }

    public Boss callBoss(Player pl, int bossID, boolean isThachDau) {
        try {
            pl.isThachDau = isThachDau;
            if (getNpc(bossID) != -1) {
                Service.gI().sendHideNpc(pl, getNpc(bossID), true);
            }
            switch (bossID) {
                case BossID.KARIN:
                    return new Karin(pl);
                case BossID.TAUPAYPAY:
                    return new TauPayPay(pl);
                case BossID.YAJIRO:
                    return new Yajiro(pl);
                case BossID.MRPOPO:
                    return new MrPoPo(pl);
                case BossID.THUONG_DE:
                    return new ThuongDe(pl);
                case BossID.KHI_BUBBLES:
                    return new KhiBubbles(pl);
                case BossID.THAN_VU_TRU:
                    return new ThanVuTru(pl);
                case BossID.TO_SU_KAIO:
                    return new ToSuKaio(pl);
                case BossID.WHIS:
                    return new Whis(pl);
            }
        } catch (Exception e) {
            Logger.logException(TrainingService.class, e);
        }
        return null;
    }

    public int getTnsmMoiPhut(Player player) {
        switch (player.levelLuyenTap) {
            case 0:
                return 20;
            case 1:
                return 40;
            case 2:
                return 80;
            case 3:
                return 160;
            case 4:
                return 320;
            case 5:
                return 640;
        };
        return player.tnsmLuyenTap > 1280 ? player.tnsmLuyenTap : 1280;
    }

    public void tangTnsmLuyenTap(Player player, long tnsm) {
        if (player.isPl()) {
            player.tnsmLuyenTap += Math.max(100, tnsm / (100 * (Service.gI().getCurrLevel(player) + 1)));
            if (player.tnsmLuyenTap > 10_000_000) {
                player.tnsmLuyenTap = 10_000_000;
            }
        }
    }

    public void tnsmLuyenTapUp(Player player) {
        long tnsm;
        int time = (int) ((System.currentTimeMillis() - player.lastTimeOffline) / 1000);
        if (time > 60) {
            tnsm = ((long) getTnsmMoiPhut(player) * (long) ((time > 86400 ? 86400 : time)) / 60);
            if (MapService.gI().isMapLuyenTap(player.zone.map.mapId)) {
                NpcService.gI().createTutorial(player, -1, "Bạn tăng được " + Util.numberToMoney(tnsm) + " sức mạnh trong thời gian " + (time / 60) + " phút tập luyện Offline");
                Service.gI().addSMTN(player, (byte) 2, tnsm, false);
            } else if (player.dangKyTapTuDong && time > 1800) {
                if (player.inventory.getGemAndRuby() > 1) {
                    new Thread(() -> {
                        try {
                            player.inventory.subGemAndRuby(1);
                            Thread.sleep(1000);
                            if (player.zone == null) {
                                return;
                            }
                            player.lastMapOffline = player.zone.map.mapId;
                            player.lastZoneOffline = player.zone.zoneId;
                            player.lastXOffline = player.location.x;
                            Service.gI().addSMTN(player, (byte) 2, tnsm, false);
                            player.teleTapTuDong = true;
                            player.thongBaoTapTuDong = "Bạn tăng được " + Util.numberToMoney(tnsm) + " sức mạnh trong thời gian " + (time / 60) + " phút tập luyện Offline, -1 ngọc (phí đăng ký tập tự động)";
                            ChangeMapService.gI().changeMapBySpaceShip(player, player.mapIdDangTapTuDong, 0, Util.nextInt(200, 400));
                            Service.gI().sendMoney(player);
                        } catch (InterruptedException e) {
                        }
                    }, "Luyện Tập").start();
                } else {
                    player.dangKyTapTuDong = false;
                    Service.gI().sendThongBao(player, "Bạn không đủ ngọc, đăng ký luyện tập tự động đã bị hủy");
                }
            }
        }
        if (Util.isAfterMidnight(player.lastTimeOffline)) {
            if (player.tnsmLuyenTap > 1) {
                player.tnsmLuyenTap -= player.tnsmLuyenTap / 3;
            }
            player.lastTimeOffline = System.currentTimeMillis();
        }
    }
}
