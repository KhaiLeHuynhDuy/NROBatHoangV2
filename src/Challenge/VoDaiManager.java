package Challenge;

import Maps.Zone;
import Server.Maintenance;
import Utils.Util;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;

public class VoDaiManager implements Runnable {

    private static VoDaiManager instance;
    private volatile long lastUpdate;
    private static final List<VoDai> list = new ArrayList<>();

    public static VoDaiManager gI() {
        if (instance == null) {
            instance = new VoDaiManager();
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
            } catch (InterruptedException ex) {
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

    public void add(VoDai vdst) {
        list.add(vdst);
    }

    public void remove(VoDai vdst) {
        list.remove(vdst);
    }

    public VoDai getVDST(@NonNull Zone zone) {
        for (VoDai vdst : list) {
            if (vdst.getZone().equals(zone)) {
                return vdst;
            }
        }
        return null;
    }
}
