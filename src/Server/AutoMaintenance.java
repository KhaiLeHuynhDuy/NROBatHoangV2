package Server;

import Server.Maintenance;
import Utils.Logger;
import java.io.File;
import java.io.IOException;
import static java.time.LocalDate.now;
import java.time.LocalTime;

public class AutoMaintenance extends Thread {

    public static boolean AutoMaintenance = true;
    public static final int hours = 17;
    public static final int mins = 0;
    public static final int second = 1;
    private static AutoMaintenance instance;
    public static boolean isRunning;

    public static AutoMaintenance gI() {
        if (instance == null) {
            instance = new AutoMaintenance();
        }
        return instance;
    }

    @Override
    public void run() {
        while (!isRunning) {
            try {
                if (AutoMaintenance) {
                    LocalTime currentTime = LocalTime.now();
                    LocalTime targetTime = LocalTime.of(hours, mins,second);
                    if (currentTime.isAfter(targetTime) && currentTime.isBefore(targetTime.plusMinutes(1))) {
                        Logger.log(Logger.PURPLE, "Đang Tiến Hành Bảo Trì Tự Động");
                        Maintenance.gI().start(15);
                        isRunning = true;
                        AutoMaintenance = false;
                    }
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                Logger.log(Logger.RED, "Lỗi trong AutoMaintenance: " + e.getMessage());
            }
        }
    }

    public static void runBatchFile(String batchFilePath) throws IOException {
        File batchFile = new File(batchFilePath);
        if (!batchFile.exists()) {
            Logger.log(Logger.RED, "File batch không tồn tại: " + batchFilePath);
            return;
        }
        ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start", batchFilePath);
        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            Logger.log(Logger.RED, "Lỗi khi chạy file batch: " + e.getMessage());
        }
    }

    public static void enableAutoMaintenance() {
        AutoMaintenance = true;
        isRunning = false;
    }
}
