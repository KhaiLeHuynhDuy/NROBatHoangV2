/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package The23rdMartialArtCongress;

import Maps.Zone;
import Player.Player;
import Server.Maintenance;
import Utils.Util;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;

/**
 *
 * @author Administrator
 */
public class The23rdMartialArtCongressManager implements Runnable {

    private static The23rdMartialArtCongressManager instance;
    private long lastUpdate;
    private static final List<The23rdMartialArtCongress> list = new ArrayList<>();

    public static The23rdMartialArtCongressManager gI() {
        if (instance == null) {
            instance = new The23rdMartialArtCongressManager();
        }
        return instance;
    }

    @Override
    public void run() {
        while (!Maintenance.isRuning) {
            try {
                long start = System.currentTimeMillis();
                update();
                long timeUpdate = System.currentTimeMillis() - start;
                if (1000 - timeUpdate > 0) {
                    Thread.sleep(1000 - timeUpdate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (Util.canDoWithTime(lastUpdate, 1000)) {
            lastUpdate = System.currentTimeMillis();
            for (int i = list.size() - 1; i >= 0; i--) {
                if (i < list.size()) {
                    list.get(i).update();
                }
            }
        }
    }

    public void add(The23rdMartialArtCongress mc) {
        list.add(mc);
    }

    public void remove(The23rdMartialArtCongress mc) {
        list.remove(mc);
    }

    public The23rdMartialArtCongress getMC(@NonNull Zone zone) {
        for (The23rdMartialArtCongress mc : list) {
            if (mc.getZone().equals(zone)) {
                return mc;
            }
        }
        return null;
    }

    public boolean plCheck(Player player) {
        for (The23rdMartialArtCongress mc : list) {
            if (mc.getPlayer().id == player.id) {
                return true;
            }
        }
        return false;
    }
}
