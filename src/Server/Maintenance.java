package Server;

import Services.Service;
import Utils.Logger;

public class Maintenance extends Thread {

    public static boolean isRuning = false;

    private static Maintenance i;

    private int time;

    private Maintenance() {

    }

    public static Maintenance gI() {
        if (i == null) {
            i = new Maintenance();
        }
        return i;
    }

    public void start(int min) {
        if (!isRuning) {
            isRuning = true;
            this.time = min;
            this.start();
        }
    }

    public void startNew(int min) {
        if (!isRuning) {
            isRuning = true;
            this.time = min;
            new Thread(Maintenance.gI(), "Thread Bảo Trì").start();
        }
    }

    public void startImmediately() {
        if (!isRuning) {
            isRuning = true;
            Logger.log(Logger.YELLOW, "BEGIN MAINTENANCE\n");
            ServerManager.gI().close();
        }
    }

    @Override
    public void run() {
        while (this.time > 0) {
            if (this.time == 60) {
                Service.gI().sendThongBaoAllPlayer("Hệ thống sẽ bảo trì sau 1 phút nữa hãy thoát game ngay để tránh mất mát vật phẩm.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                this.time--;
            } else if (time < 60) {
                Service.gI().sendThongBaoAllPlayer("Hệ thống sẽ bảo trì sau " + time + " giây nữa");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                this.time--;
            } else {
                int hour = this.time / 3600;
                int min = (this.time - hour * 3600) / 60;
                int sec = this.time % 60;

                String hourStr = (hour > 0) ? hour + " giờ " : "";
                String minStr = (min > 0) ? min + " phút " : "";
                String secStr = (sec > 0) ? sec + " giây " : "";

                Service.gI().sendThongBaoAllPlayer("Hệ thống sẽ bảo trì sau " + hourStr + minStr + secStr
                        + "nữa");
                Logger.log(Logger.YELLOW, "Hệ thống sẽ bảo trì sau " + hourStr + minStr + secStr
                        + "nữa\n");
                if (sec == 0 && this.time > 60) {
                    sec = 60;
                } else if (sec == 0) {
                    sec = 1;
                }
                this.time -= sec;
                try {
                    Thread.sleep(sec * 1000);
                } catch (InterruptedException e) {
                }
            }
        }
        Logger.log(Logger.YELLOW, "BEGIN MAINTENANCE\n");
        ServerManager.gI().close();
    }

}
