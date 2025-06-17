package BossMain;

import ListBoss.MainBoss.AnTrom;
import ListBoss.MainBoss.Android13;
import ListBoss.MainBoss.BlackGoku;
import ListBoss.MainBoss.Chaien;
import ListBoss.MainBoss.CumberYellow;
import ListBoss.MainBoss.ThanHuyDietChampa;
import ListBoss.MainBoss.Android14;
import ListBoss.MainBoss.ThanKaioshinZamas;
import ListBoss.MainBoss.Frost;
import ListBoss.MainBoss.Xeko;
import ListBoss.MainBoss.CumberBlack;
import ListBoss.MainBoss.Cumber;
import ListBoss.MainBoss.DrKore;
import ListBoss.MainBoss.Vegeta;
import ListBoss.MainBoss.SuperXen;
import ListBoss.MainBoss.CoolerGold;
import ListBoss.MainBoss.Cooler;
import ListBoss.MainBoss.Nobita;
import ListBoss.MainBoss.ThienSuVados;
import ListBoss.MainBoss.KaioshinZamas;
import ListBoss.MainBoss.Xuka;
import ListBoss.MainBoss.SongokuTaAc;
import ListBoss.MainBoss.Berrus;
import ListBoss.MainBoss.SupperBroly;
import ListBoss.MainBoss.SupperBlackGoku;
import ListBoss.MainBoss.ThienSuWhis;
import ListBoss.MainBoss.SuperAndroid17;
import ListBoss.MainBoss.Broly;
import ListBoss.MainBoss.Android15;
import ListBoss.MainBoss.Doraemon;
import ListBoss.MainBoss.ThanHuyDietBerrus;
import ListBoss.MainBoss.Android19;
import Daos.GodGK;

import ListBoss.DoanhTrai.TrungUyTrang;

import ListBoss.NhiemVu.*;

import ListBoss.NgocRongDen.*;

import ListBoss.Mabu12H.*;

import Player.Player;

import Server.ServerManager;

import Services.ItemMapService;
import Services.MapService;
import Services.Service;

import Services.func.ChangeMapService;
import ListBoss.MainBoss.Jackychun;
import ListBoss.MainBoss.OngGiaNoel;
import ListBoss.MainBoss.QuyLaoKame;
import ListBoss.Bojack.BiDo;
import ListBoss.Bojack.BoJack;
import ListBoss.Bojack.BuJin;
import ListBoss.Bojack.KoGu;
import ListBoss.Bojack.Super_BoJack;
import ListBoss.Bojack.ZangYa;
import ListBoss.GinyuForce.SO1;
import ListBoss.GinyuForce.SO2;
import ListBoss.GinyuForce.SO3;
import ListBoss.GinyuForce.SO4;
import ListBoss.GinyuForce.TDT;
import ListBoss.Yardart.CHIENBINH0;
import ListBoss.Yardart.CHIENBINH1;
import ListBoss.Yardart.CHIENBINH2;
import ListBoss.Yardart.CHIENBINH3;
import ListBoss.Yardart.CHIENBINH4;
import ListBoss.Yardart.CHIENBINH5;
import ListBoss.Yardart.DOITRUONG5;
import ListBoss.Yardart.TANBINH0;
import ListBoss.Yardart.TANBINH1;
import ListBoss.Yardart.TANBINH2;
import ListBoss.Yardart.TANBINH3;
import ListBoss.Yardart.TANBINH4;
import ListBoss.Yardart.TANBINH5;
import ListBoss.Yardart.TAPSU0;
import ListBoss.Yardart.TAPSU1;
import ListBoss.Yardart.TAPSU2;
import ListBoss.Yardart.TAPSU3;
import ListBoss.Yardart.TAPSU4;
import Maps.Zone;
import Server.Maintenance;

import Utils.Logger;
import Utils.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import network.Message;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class BossManager implements Runnable {

    private static BossManager I;

    public static int ratioReward = 2;

    public static BossManager gI() {
        if (BossManager.I == null) {
            BossManager.I = new BossManager();
        }
        return BossManager.I;
    }

    private BossManager() {
        this.bosses = new ArrayList<>();
    }

    private boolean loadedBoss;
    private final List<Boss> bosses;

    public void addBoss(Boss boss) {
        this.bosses.add(boss);
    }

    public List<Boss> getBosses() {
        return this.bosses;
    }

    public void removeBoss(Boss boss) {
        this.bosses.remove(boss);
    }

    public void loadBoss() {
        if (this.loadedBoss) {
            return;
        }
        try {
            this.createBoss(BossID.BERRUS);
            this.createBoss(BossID.CUMBER_BLACK);
            this.createBoss(BossID.CUMBER_YELLOW);
            this.createBoss(BossID.SUPER_XEN);
            this.createBoss(BossID.TDST);
            this.createBoss(BossID.FROST);
            this.createBoss(BossID.FROST);
            this.createBoss(BossID.COOLER);
            this.createBoss(BossID.PIC_POC_KING_KONG);
            this.createBoss(BossID.SONGOKU_TA_AC);
            this.createBoss(BossID.CUMBER);
            this.createBoss(BossID.COOLER_GOLD);
            this.createBoss(BossID.XEN_BO_HUNG);
            this.createBoss(BossID.SIEU_BO_HUNG);
            this.createBoss(BossID.XEN_CON_1);
            this.createBoss(BossID.XEN_CON_1);
            this.createBoss(BossID.XEN_CON_1);
            this.createBoss(BossID.XEN_CON_1);
            this.createBoss(BossID.THIEN_SU_VADOS);
            this.createBoss(BossID.THIEN_SU_WHIS);
            this.createBoss(BossID.DORAEMON);
            this.createBoss(BossID.BLACK_GOKU);
            this.createBoss(BossID.BLACK_GOKU);
            this.createBoss(BossID.SUPER_BLACK_GOKU);
            this.createBoss(BossID.KAIOSHIN_ZAMAS);
            this.createBoss(BossID.THAN_THAN_KAIOSHIN_ZAMAS);
            this.createBoss(BossID.KUKU);
            this.createBoss(BossID.MAP_DAU_DINH);
            this.createBoss(BossID.RAMBO);
            this.createBoss(BossID.FIDE);
            this.createBoss(BossID.VEGETA);
            this.createBoss(BossID.DR_KORE);
            this.createBoss(BossID.ANDROID_14);
            this.createBoss(BossID.ANDROID_19);
            this.createBoss(BossID.SUPER_ANDROID_17);
            this.createBoss(BossID.MABU_12H);
            this.createBoss(BossID.JACKY_CHU);
            this.createBoss(BossID.CHIEN_BINH_5);
            this.createBoss(BossID.DOI_TRUONG_5);
            this.createBoss(BossID.TAN_BINH_5);
            this.createBoss(BossID.BOJACK);
            this.createBoss(BossID.SUPER_BOJACK);
            this.createBoss(BossID.SO_4);
           // this.createBoss(BossID.ONG_GIA_NOEL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.loadedBoss = true;
        new Thread(BossManager.I, "Update boss").start();
    }

    public Boss createBoss(int bossID) {
        try {
            switch (bossID) {
//                case BossID.ONG_GIA_NOEL:
//                    return new OngGiaNoel();
                case BossID.BUJIN:
                    return new BuJin();
                case BossID.KOGU:
                    return new KoGu();
                case BossID.ZANGYA:
                    return new ZangYa();
                case BossID.BIDO:
                    return new BiDo();
                case BossID.BOJACK:
                    return new BoJack();
                case BossID.SUPER_BOJACK:
                    return new Super_BoJack();
                case BossID.TAP_SU_0:
                    return new TAPSU0();
                case BossID.TAP_SU_1:
                    return new TAPSU1();
                case BossID.TAP_SU_2:
                    return new TAPSU2();
                case BossID.TAP_SU_3:
                    return new TAPSU3();
                case BossID.TAP_SU_4:
                    return new TAPSU4();
                case BossID.TAN_BINH_5:
                    return new TANBINH5();
                case BossID.TAN_BINH_0:
                    return new TANBINH0();
                case BossID.TAN_BINH_1:
                    return new TANBINH1();
                case BossID.TAN_BINH_2:
                    return new TANBINH2();
                case BossID.TAN_BINH_3:
                    return new TANBINH3();
                case BossID.TAN_BINH_4:
                    return new TANBINH4();
                case BossID.CHIEN_BINH_5:
                    return new CHIENBINH5();
                case BossID.CHIEN_BINH_0:
                    return new CHIENBINH0();
                case BossID.CHIEN_BINH_1:
                    return new CHIENBINH1();
                case BossID.CHIEN_BINH_2:
                    return new CHIENBINH2();
                case BossID.CHIEN_BINH_3:
                    return new CHIENBINH3();
                case BossID.CHIEN_BINH_4:
                    return new CHIENBINH4();
                case BossID.DOI_TRUONG_5:
                    return new DOITRUONG5();
                case BossID.QUY_LAO:
                    return new QuyLaoKame();
                case BossID.JACKY_CHU:
                    return new Jackychun();
                case BossID.FROST:
                    return new Frost();
                case BossID.CUMBER_YELLOW:
                    return new CumberYellow();
                case BossID.CUMBER_BLACK:
                    return new CumberBlack();
                case BossID.BERRUS:
                    return new Berrus();
                case BossID.KUKU:
                    return new Kuku();
                case BossID.MAP_DAU_DINH:
                    return new MapDauDinh();
                case BossID.RAMBO:
                    return new Rambo();
                case BossID.DRABURA:
                    return new Drabura();
                case BossID.DRABURA_2:
                    return new Drabura2();
                case BossID.BUI_BUI:
                    return new BuiBui();
                case BossID.BUI_BUI_2:
                    return new BuiBui2();
                case BossID.YA_CON:
                    return new Yacon();
                case BossID.MABU_12H:
                    return new MaBu();
                case BossID.Rong_1Sao:
                    return new Rong1Sao();
                case BossID.Rong_2Sao:
                    return new Rong2Sao();
                case BossID.Rong_3Sao:
                    return new Rong3Sao();
                case BossID.Rong_4Sao:
                    return new Rong4Sao();
                case BossID.Rong_5Sao:
                    return new Rong5Sao();
                case BossID.Rong_6Sao:
                    return new Rong6Sao();
                case BossID.Rong_7Sao:
                    return new Rong7Sao();
                case BossID.FIDE:
                    return new Fide();
                case BossID.VEGETA:
                    return new Vegeta();
                case BossID.DR_KORE:
                    return new DrKore();
                case BossID.ANDROID_13:
                    return new Android13();
                case BossID.ANDROID_14:
                    return new Android14();
                case BossID.ANDROID_19:
                    return new Android19();
                case BossID.ANDROID_15:
                    return new Android15();
                case BossID.SUPER_XEN:
                    return new SuperXen();
                case BossID.SUPER_ANDROID_17:
                    return new SuperAndroid17();
                case BossID.PIC_POC_KING_KONG:
                    return new PicPocKingKong();
                case BossID.XEN_BO_HUNG:
                    return new XenBoHung();
                case BossID.SIEU_BO_HUNG:
                    return new SieuBoHung();
                case BossID.XUKA:
                    return new Xuka();
                case BossID.NOBITA:
                    return new Nobita();
                case BossID.XEKO:
                    return new Xeko();
                case BossID.CHAIEN:
                    return new Chaien();
                case BossID.DORAEMON:
                    return new Doraemon();
                case BossID.COOLER:
                    return new Cooler();
                case BossID.THAN_THAN_KAIOSHIN_ZAMAS:
                    return new ThanKaioshinZamas();
                case BossID.KAIOSHIN_ZAMAS:
                    return new KaioshinZamas();
                case BossID.BLACK_GOKU:
                    return new BlackGoku();
                case BossID.SUPER_BLACK_GOKU:
                    return new SupperBlackGoku();
                case BossID.XEN_CON_1:
                    return new XenCon();
                case BossID.TIEU_DOI_TRUONG:
                    return new TDT();
                case BossID.SO_4:
                    return new SO4();
                case BossID.SO_3:
                    return new SO3();
                case BossID.SO_2:
                    return new SO2();
                case BossID.SO_1:
                    return new SO1();
                case BossID.COOLER_GOLD:
                    return new CoolerGold();
                case BossID.CUMBER:
                    return new Cumber();
                case BossID.THAN_HUY_DIET_CHAMPA:
                    return new ThanHuyDietChampa();
                case BossID.THIEN_SU_VADOS:
                    return new ThienSuVados();
                case BossID.THAN_HUY_DIET:
                    return new ThanHuyDietBerrus();
                case BossID.THIEN_SU_WHIS:
                    return new ThienSuWhis();
                case BossID.SONGOKU_TA_AC:
                    return new SongokuTaAc();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean existBossOnPlayer(Player player) {
        return player.zone.getBosses().size() > 0;
    }

    public void teleBoss(Player pl, Message _msg) {
        if (pl.isAdmin()) {
            if (_msg != null) {
                try {
                    int id = _msg.reader().readInt();
                    Boss b = getBossById(id);
                    if (b == null) {
                        Player player = GodGK.loadById(id);
                        if (player != null && player.zone != null) {
                            ChangeMapService.gI().changeMapYardrat(pl, player.zone, player.location.x, player.location.y);
                            return;
                        } else {
                            Service.gI().sendThongBao(pl, "Nó trốn rồi");
                            return;
                        }
                    }
                    if (b != null && b.zone != null) {
                        ChangeMapService.gI().changeMapYardrat(pl, b.zone, b.location.x, b.location.y);
                    } else {
                        Service.gI().sendThongBao(pl, "Boss Hẹo Rồi");
                    }
                } catch (IOException e) {
                    System.out.println("Loi tele boss");
                    e.printStackTrace();
                }
            }
        } else {
            Service.gI().sendThongBao(pl, "Bạn chưa có quyền này!!");
        }
    }

    public void summonBoss(Player pl, Message _msg) {
        if (!pl.getSession().isAdmin) {
            Service.gI().sendThongBao(pl, "Chỉ dành cho Admin");
            return;
        }
        if (_msg != null) {
            try {
                int id = _msg.reader().readInt();
                Boss b = getBossById(id);
                if (b != null && b.zone != null) {
                    ChangeMapService.gI().changeMapYardrat(b, pl.zone, pl.location.x, pl.location.y);
                    return;
                }
                if (b == null) {
                    Player player = GodGK.loadById(id);
                    if (player != null && player.zone != null) {
                        ChangeMapService.gI().changeMapYardrat(player, pl.zone, pl.location.x, pl.location.y);
                    } else {
                        Service.gI().sendThongBao(pl, "Nó trốn rồi");
                    }
                }
            } catch (IOException e) {
                System.out.println("Loi summon boss");
            }
        }
    }

    public void showListBoss(Player player) {
        if (!player.isAdmin()) {
            return;
        }
        Message msg;
        try {
            msg = new Message(-96);
            msg.writer().writeByte(1);
            msg.writer().writeUTF("Boss");
            msg.writer().writeByte(
                    (int) bosses.stream()
                            .filter(boss -> !MapService.gI().isMapMaBu(boss.data[0].getMapJoin()[0])
                            && !MapService.gI().isMapDoanhTrai(boss.data[0].getMapJoin()[0])
                            && !MapService.gI().isMapBlackBallWar(boss.data[0].getMapJoin()[0])
                            && !MapService.gI().isMapSatan(boss.data[0].getMapJoin()[0]))
                            .count());
            for (int i = 0; i < bosses.size(); i++) {
                Boss boss = this.bosses.get(i);
                if (MapService.gI().isMapMaBu(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapBlackBallWar(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapBanDoKhoBau(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapDoanhTrai(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapSatan(boss.data[0].getMapJoin()[0])) {
                    continue;
                }
                msg.writer().writeInt((int) boss.id);
                msg.writer().writeInt((int) boss.id);
                msg.writer().writeShort(boss.data[0].getOutfit()[0]);
                if (player.getSession().version > 214) {
                    msg.writer().writeShort(-1);
                }
                msg.writer().writeShort(boss.data[0].getOutfit()[1]);
                msg.writer().writeShort(boss.data[0].getOutfit()[2]);
                msg.writer().writeUTF(boss.data[0].getName());
                if (boss.zone != null) {
                    msg.writer().writeUTF("Sống");
                    msg.writer().writeUTF("Thông Tin Boss\n" + "|7|Map : " + boss.zone.map.mapName + "(" + boss.zone.map.mapId + ") \nZone: " + boss.zone.zoneId + "\nHP: " + Util.powerToString(boss.nPoint.hp) + "\nDame: " + Util.powerToString(boss.nPoint.dame));
                } else {
                    msg.writer().writeUTF("Chết");
                    msg.writer().writeUTF("Boss Respawn\n|7|Time to Reset : " + (boss.secondsRest <= 0 ? "BossAppear" : boss.secondsRest + " giây"));
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showListBossMem(Player player) {
        Message msg;
        try {
            msg = new Message(-96);
            msg.writer().writeByte(1);
            msg.writer().writeUTF("Boss");
            msg.writer().writeByte(
                    (int) bosses.stream()
                            .filter(boss -> !MapService.gI().isMapMaBu(boss.data[0].getMapJoin()[0])
                            && !MapService.gI().isMapDoanhTrai(boss.data[0].getMapJoin()[0])
                            && !MapService.gI().isMapBlackBallWar(boss.data[0].getMapJoin()[0])
                            && !MapService.gI().isMapSatan(boss.data[0].getMapJoin()[0]))
                            .count());
            for (int i = 0; i < bosses.size(); i++) {
                Boss boss = this.bosses.get(i);
                if (MapService.gI().isMapMaBu(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapBlackBallWar(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapBanDoKhoBau(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapDoanhTrai(boss.data[0].getMapJoin()[0])
                        || MapService.gI().isMapSatan(boss.data[0].getMapJoin()[0])) {
                    continue;
                }
                msg.writer().writeInt((int) boss.id);
                msg.writer().writeInt((int) boss.id);
                msg.writer().writeShort(boss.data[0].getOutfit()[0]);
                if (player.getSession().version > 214) {
                    msg.writer().writeShort(-1);
                }
                msg.writer().writeShort(boss.data[0].getOutfit()[1]);
                msg.writer().writeShort(boss.data[0].getOutfit()[2]);
                msg.writer().writeUTF(boss.data[0].getName());
                if (boss.zone != null) {
                    msg.writer().writeUTF("Sống");
                    msg.writer().writeUTF("Thông Tin Boss\n" + "|7|Map : " + boss.zone.map.mapName + "(" + boss.zone.map.mapId + ") \nZone: " + boss.zone.zoneId + "\nHP: " + Util.powerToString(boss.nPoint.hp) + "\nDame: " + Util.powerToString(boss.nPoint.dame));
                } else {
                    msg.writer().writeUTF("Chết");
                    msg.writer().writeUTF("Boss Respawn\n|7|Time to Reset : " + (boss.secondsRest <= 0 ? "BossAppear" : boss.secondsRest + " giây"));
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showListBossBroly(Player player) {
        Message msg;
        try {
            msg = new Message(-96);
            msg.writer().writeByte(0);
            msg.writer().writeUTF("Boss");
            msg.writer().writeByte((int) bosses.stream().filter(boss -> boss instanceof Broly).count());
            for (int i = 0; i < bosses.size(); i++) {
                Boss boss = this.bosses.get(i);
                if (boss instanceof Broly) {
                    msg.writer().writeInt((int) boss.id);
                    msg.writer().writeInt((int) boss.id);
                    msg.writer().writeShort(boss.data[0].getOutfit()[0]);
                    if (player.getSession().version > 214) {
                        msg.writer().writeShort(-1);
                    }
                    msg.writer().writeShort(boss.data[0].getOutfit()[1]);
                    msg.writer().writeShort(boss.data[0].getOutfit()[2]);
                    msg.writer().writeUTF(boss.data[0].getName());
                    if (boss.zone != null) {
                        msg.writer().writeUTF("Sống");
                        if (player.isAdmin()) {
                            msg.writer().writeUTF(boss.zone.map.mapName
                                    + "(" + boss.zone.map.mapId + ") khu " + boss.zone.zoneId + "");
                        } else {
                            msg.writer().writeUTF(boss.zone.map.mapName);
                        }
                    } else {
                        msg.writer().writeUTF("Chết");
                        msg.writer().writeUTF("Chết rồi");
                    }
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.error("Lỗi Show List Boss!!");
        }
    }

    public synchronized void callBoss(Player player, int mapId) {
        try {
            if (BossManager.gI().existBossOnPlayer(player)
                    || player.zone.items.stream().anyMatch(itemMap -> ItemMapService.gI().isBlackBall(itemMap.itemTemplate.id))
                    || player.zone.getPlayers().stream().anyMatch(p -> p.iDMark.isHoldBlackBall())) {
                return;
            }
            Boss k = null;
            switch (mapId) {
                case 85:
                    k = BossManager.gI().createBoss(BossID.Rong_1Sao);
                    break;
                case 86:
                    k = BossManager.gI().createBoss(BossID.Rong_2Sao);
                    break;
                case 87:
                    k = BossManager.gI().createBoss(BossID.Rong_3Sao);
                    break;
                case 88:
                    k = BossManager.gI().createBoss(BossID.Rong_4Sao);
                    break;
                case 89:
                    k = BossManager.gI().createBoss(BossID.Rong_5Sao);
                    break;
                case 90:
                    k = BossManager.gI().createBoss(BossID.Rong_6Sao);
                    break;
                case 91:
                    k = BossManager.gI().createBoss(BossID.Rong_7Sao);
                    break;
            }
            if (k != null) {
                k.currentLevel = 0;
                k.joinMapByZone(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boss getBossById(int bossId) {
        return BossManager.gI().bosses.stream().filter(boss -> boss.id == bossId && !boss.isDie()).findFirst().orElse(null);
    }

    public boolean checkBosses(Zone zone, int BossID) {
        return this.bosses.stream()
                .filter(boss -> boss.id == BossID && boss.zone != null && boss.zone.equals(zone) && !boss.isDie())
                .findFirst().orElse(null) != null;
    }

    public static String covertString(String value) {
        String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
        try {
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("");
        } catch (Exception ex) {
            return temp;
        }
    }

    public Boss getBossByName(String name) {
        try {
            for (Boss boss : this.bosses) {
                if (boss.currentLevel > 0) {
                    if (covertString(boss.data[0].getName()).equalsIgnoreCase(covertString(name))) {
                        return boss;
                    }
                }
                if (boss.name == null) {
                    continue;
                }
                if (covertString(boss.name).equalsIgnoreCase(covertString(name))) {
                    return boss;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void run() {
        while (ServerManager.isRunning) {
            try {
                long st = System.currentTimeMillis();
                for (Boss boss : this.bosses) {
                    boss.update();
                }
                Thread.sleep(150 - (System.currentTimeMillis() - st));
            } catch (Exception ignored) {
            }

        }
    }
//    public void run() {
//        while (!Maintenance.isRuning) {
//            try {
//                int delay = 500;
//                long st = System.currentTimeMillis();
//                for (int i = this.bosses.size() - 1; i >= 0; i--) {
//                    try {
//                        this.bosses.get(i).update();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (delay - (System.currentTimeMillis() - st) > 0) {
//                    Thread.sleep(delay - (System.currentTimeMillis() - st));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
