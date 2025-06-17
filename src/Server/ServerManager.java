package Server;

import Server.attr.AttributeManager;
import network.Network;
import MaQuaTang.MaQuaTangManager;
import com.girlkun.database.GirlkunDB;

import java.net.ServerSocket;

import Daos.HistoryTransactionDAO;
import BossMain.BossManager;
import Item.Item;
//import Challenge.DHVT_CongressManager;
import Challenge.VoDaiManager;
import PVP.DaiHoiVoThuat;
import Player.Player;
import Services.ClanService;
import Services.InventoryServiceNew;
import Services.NgocRongNamecService;
import Services.Service;
import Services.func.ChonAiDay;
import Services.func.TaiXiu;
import Services.func.TopService;
import Utils.Logger;
import Utils.TimeUtil;
import Utils.Util;
import KyGui.ShopKyGuiManager;
import MiniGame.LuckyNumber.LuckyNumber;
import The23rdMartialArtCongress.The23rdMartialArtCongressManager;
import com.girlkun.result.GirlkunResultSet;
import java.io.IOException;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import lombok.Getter;
import lombok.Setter;
import network.Network;
import network.MessageSendCollect;
import network.inetwork.ISessionAcceptHandler;
import network.inetwork.ISession;

public class ServerManager {

    public static String timeStart;

    public static final Map CLIENTS = new HashMap();

    public static String NAME = "Girlkun75";
    public static int PORT = 14445;

    private static ServerManager instance;
    @Getter
    @Setter
    private AttributeManager attributeManager;
    private long lastUpdateAttribute;

    public static ServerSocket listenSocket;
    public static boolean isRunning;

    public void init() {
        Manager.gI();
        try {
            if (Manager.LOCAL) {
                return;
            }
            GirlkunDB.executeUpdate("update account set last_time_login = '2000-01-01', "
                    + "last_time_logout = '2001-01-01'");
        } catch (Exception e) {
        }
        HistoryTransactionDAO.deleteHistory();
    }

    public static ServerManager gI() {
        if (instance == null) {
            instance = new ServerManager();
            instance.init();
        }
        return instance;
    }

    public static void main(String[] args) {
        timeStart = TimeUtil.getTimeNow("dd/MM/yyyy HH:mm:ss");
        ServerManager.gI().run();
//        Menu.start();
    }

    public void run() {
        long delay = 500;
        isRunning = true;
        activeServerSocket();
        activeCommandLine();
        Logger.log(Logger.GREEN, "Time start server: " + ServerManager.timeStart + "\n");
        Logger.log(Logger.BLUE_BACKGROUND, "  _  __   _      \n");
        Logger.log(Logger.BLUE_BACKGROUND, " | |/ /  | |     \n");
        Logger.log(Logger.BLUE_BACKGROUND, " | ' /   | |     \n");
        Logger.log(Logger.BLUE_BACKGROUND, " |  <    | |     \n");
        Logger.log(Logger.BLUE_BACKGROUND, " | . \\   | |____ \n");
        Logger.log(Logger.BLUE_BACKGROUND, " |_|\\_\\  |______|\n");

        MaQuaTangManager.gI().init();
        new Thread(DaiHoiVoThuat.gI(), "Thread DHVT").start();

        NgocRongNamecService.gI().initNgocRongNamec((byte) 0);

        new Thread(NgocRongNamecService.gI(), "Thread NRNM").start();

//        new Thread(TopService.gI(), "Thread TOP").start();
        new Thread(VoDaiManager.gI(), "Update Võ Đài Sinh Tử").start();

        new Thread(LuckyNumber.gI(), "Update Lucky Number").start();

        new Thread(TaiXiu.gI(), "Thread  Tài Xỉu").start();

//        new Thread(AutoMaintenance.gI(), "Thread Auto Bảo Trì").start();
        new Thread(The23rdMartialArtCongressManager.gI(), "Update DHVT23").start();
        BossManager.gI().loadBoss();
        try {
            Thread.sleep(1000);
            Manager.MAPS.forEach(Maps.Map::initBoss);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(BossManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void activeServerSocket() {
        try {
            Network.gI().init().setAcceptHandler(new ISessionAcceptHandler() {
                @Override
                public void sessionInit(ISession is) {
                    if (!canConnectWithIp(is.getIP())) {
                        is.disconnect();
                        return;
                    }
                    is.setMessageHandler(Controller.gI())
                            .setSendCollect(new MessageSendCollect())
                            .setKeyHandler(new MyKeyHandler())
                            .startCollect();
                }

                @Override
                public void sessionDisconnect(ISession session) {
                    Client.gI().kickSession((MySession) session);
                }
            }).setTypeSessioClone(MySession.class)
                    .setDoSomeThingWhenClose(() -> {
                        Logger.error("SERVER CLOSE\n");
                        System.exit(0);
                    })
                    .start(PORT);
        } catch (Exception e) {
        }
    }

    private boolean canConnectWithIp(String ipAddress) {
        Object o = CLIENTS.get(ipAddress);
        if (o == null) {
            CLIENTS.put(ipAddress, 1);
            return true;
        } else {
            int n = Integer.parseInt(String.valueOf(o));
            if (n < Manager.MAX_PER_IP) {
                n++;
                CLIENTS.put(ipAddress, n);
                return true;
            } else {
                return true;
            }
        }
    }

    public void disconnect(MySession session) {
        Object o = CLIENTS.get(session.getIP());
        if (o != null) {
            int n = Integer.parseInt(String.valueOf(o));
            n--;
            if (n < 0) {
                n = 0;
            }
            CLIENTS.put(session.getIP(), n);
        }
    }

    private void activeCommandLine() {
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (true) {
                String line = sc.nextLine();
                if (line.equals("baotri")) {
                    new Thread(() -> {
                        Maintenance.gI().start(1);
                    }).start();
                } else if (line.equals("athread")) {
                    System.out.println("Số thread hiện tại của Server: " + Thread.activeCount());
                } else if (line.equals("nplayer")) {
                    System.out.println("Số lượng người chơi hiện tại của Server: " + Client.gI().getPlayers().size());
                } else if (line.equals("shop")) {
                    Manager.gI().updateShop();
                    System.out.println("===========================DONE UPDATE SHOP===========================");
                } else if (line.equals("a")) {
                    new Thread(() -> {
                        Client.gI().close();
                    }).start();
                }
            }
        }, "Active line").start();
    }
    private boolean batchFileExecuted = false;

    public void close() {
        isRunning = false;
        try {
            ClanService.gI().close();
        } catch (Exception e) {
            Logger.error("Lỗi save clan!\n");
        }
        try {
            ShopKyGuiManager.gI().save();
        } catch (Exception e) {
            Logger.error("Lỗi save shop ký gửi!\n");
        }
        try {
            Manager.gI().updateEventCount();
        } catch (Exception e) {
            Logger.error("Lỗi save event!\n");
        }
        Client.gI().close();
        Logger.success("SUCCESSFULLY MAINTENANCE!\n");
        if (AutoMaintenance.isRunning && !batchFileExecuted) {
            AutoMaintenance.isRunning = false;
            try {
                String batchFilePath = "run.bat";
                AutoMaintenance.runBatchFile(batchFilePath);
                batchFileExecuted = true;
            } catch (IOException e) {
                Logger.error("Lỗi khi chạy file batch: " + e.getMessage());
            }
        }
        System.exit(0);
    }

    public long getNumPlayer() {
        long num = 0;
        try {
            GirlkunResultSet rs = GirlkunDB.executeQuery("SELECT COUNT(*) FROM `player`");
            rs.first();
            num = rs.getLong(1);
        } catch (Exception e) {
        }
        return num;
    }
}
