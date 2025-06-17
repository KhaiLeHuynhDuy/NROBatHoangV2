//package nro.services;
//
//import nro.models.item.Item;
//import nro.models.player.Player;
//import java.util.Random;
//import nro.jdbc.daos.PlayerDAO;
//import nro.utils.Logger;
//
//public class DoKiepService {
//
//    private static DoKiepService instance;
//    private final Random rand;
//    private static final int MAX_CapTuTien = 3;
//
//    // Cấu hình mới
//    private static final int ITEM_ID_REQUIRED = 457;
//    private static final int[] ITEM_REQUIRE = {
//        15, // Lv0 -> Lv1
//        55, // Lv1 -> Lv2
//        255, // Lv2 -> Lv3
//        550, // Lv3 -> Lv4
//        1555 // Lv4 -> Lv5
//    };
//
//    private static final int[] DAME_REQUIRE = {
//        25_000, // 1tr cho Lv0 -> Lv1
//        3_600_000, // 3.6tr cho Lv1 -> Lv2
//        5_000_000, // 5tr cho Lv2 -> Lv3
//        7_500_000, // 7.5tr cho Lv3 -> Lv4
//        10_000_000 // 10tr cho Lv4 -> Lv5
//    };
//
//    private static final float[] SUCCESS_RATE = {
//        10, // Lv0 -> Lv1
//        5, // Lv1 -> Lv2 
//        0.001f, // Lv2 -> Lv3
//        0.5f, // Lv3 -> Lv4
//        0.1f // Lv4 -> Lv5
//    };
//
//    public static DoKiepService gI() {
//        if (instance == null) {
//            instance = new DoKiepService();
//        }
//        return instance;
//    }
//
//    private DoKiepService() {
//        rand = new Random();
//    }
//
//    public void process(Player player, int times) {
//        try {
//            if (player.canh_gioi >= MAX_CapTuTien) {
//                Service.gI().sendThongBao(player, "Bạn đã đạt cảnh giới tối đa");
//                return;
//            }
//            if (player.canh_gioi > 0 && player.binh_canh != 3) {
//                Service.gI().sendThongBao(player, "Bạn chưa đạt đỉnh phong không thể độ kiếp");
//                return;
//            }
//
//            int initialLevel = player.canh_gioi;
//            int totalItemsUsed = 0;
//            int successCount = 0;
//
//            for (int i = 0; i < times; i++) {
//                int currentLevel = player.canh_gioi;
//                if (currentLevel >= MAX_CapTuTien) {
//                    break;
//                }
//
//                // Kiểm tra điều kiện
//                int targetLevel = currentLevel + 1;
//                int requiredItems = ITEM_REQUIRE[currentLevel];
//                int requiredDame = DAME_REQUIRE[currentLevel];
//
//                // Kiểm tra vật phẩm
//                Item requiredItem = findItem(player);
//                if (requiredItem == null || requiredItem.quantity < requiredItems) {
//                    Service.gI().sendThongBao(player, "Cần " + requiredItems + " Thỏi vàng để độ kiếp");
//                    break;
//                }
//
//                // Kiểm tra sức đánh
//                if (player.nPoint.dame < requiredDame) {
//                    String dameMsg = String.format("Sức đánh tối thiểu: %,d (Hiện tại: %,d)",
//                            requiredDame, player.nPoint.dame);
//                    Service.gI().sendThongBao(player, dameMsg);
//                    break;
//                }
//
//                // Trừ vật phẩm
//                InventoryServiceNew.gI().subQuantityItemsBag(player, requiredItem, requiredItems);
//                totalItemsUsed += requiredItems;
//
//                // Tính toán thành công
//                boolean success = rand.nextFloat(100) < SUCCESS_RATE[currentLevel];
//
//                if (success) {
//                    player.canh_gioi = (byte) targetLevel;
//                    player.binh_canh = 0;
//                    int newplayerpower = 1_500_000;
//                    long oldplayerpower = player.nPoint.power;
//                    long subplayerpower = newplayerpower - oldplayerpower;
//                    PlayerService.gI().sendTNSM(player, (byte) 0, subplayerpower);
//                    player.nPoint.power = newplayerpower;
//                    successCount++;
//                    PlayerDAO.updatePlayer(player);
//                    InventoryServiceNew.gI().sendItemBags(player);
//
//                    break;
//                }
//            }
//
//            // Thông báo tổng kết
//            String resultMsg = String.format(
//                    "Kết quả độ kiếp %d lần:\n"
//                    + "- Cảnh giới: %s ➔ %s\n"
//                    + "- Đã dùng: %,d Thỏi vàng\n",
//                    times,
//                    getRealNameCanhGioi(player, initialLevel),
//                    getRealNameCanhGioi(player, player.canh_gioi),
//                    totalItemsUsed
//            );
//
//            Service.gI().sendThongBaoOK(player, resultMsg);
//
//        } catch (Exception e) {
//            Logger.error("Lỗi độ kiếp: " + e.getMessage());
//            Service.gI().sendThongBao(player, "Có lỗi xảy ra, vui lòng thử lại.");
//        } finally {
//            InventoryServiceNew.gI().sendItemBags(player);
//        }
//    }
//
//    private Item findItem(Player player) {
//        for (Item item : player.inventory.itemsBag) {
//            if (item != null && item.template.id == ITEM_ID_REQUIRED) {
//                return item;
//            }
//        }
//        return null;
//    }
//
//    public String getRealNameCanhGioi(Player player, int level) {
//        switch (level) {
//            case 0:
//                return "Phàm Nhân";
//            case 1:
//                return "Luyện Khí";
//            case 2:
//                if (player.isUseTrucCoDan) {
//                    return "Thiên Đạo Trúc Cơ";
//                } else {
//                    return "Trúc Cơ";
//                }
//            case 3:
//                return "Cụ Linh";
//            case 4:
//                return "Kim Đan";
//            case 5:
//                return "Nguyên Anh";
//            default:
//                return "Không xác định";
//        }
//    }
//
//}
package Services;

import Daos.PlayerDAO;
import Item.Item;
import Player.Player;
import Utils.Logger;
import java.util.Random;

public class DoKiepService {

    private static DoKiepService instance;
    private final Random rand;
    private static final int MAX_CAP_TU_TIEN = 3;
    private static final int ITEM_ID_REQUIRED = 457;

    private static final int[] ITEM_REQUIRE = {45, 180, 750, 5500, 15055};
    private static final int[] DAME_REQUIRE = {25_000, 3_000_000, 10_000_000, 19_000_000, 30_000_000};
    private static final double[] SUCCESS_RATE = {10.0, 0.05, 0.015, 0.00005, 0.00001};

    public static DoKiepService gI() {
        if (instance == null) {
            instance = new DoKiepService();
        }
        return instance;
    }

    private DoKiepService() {
        rand = new Random();
    }

    public void process(Player player, int times) {
        try {
            int totalItemsUsed = 0;
            int failCount = 0;
            boolean successFlag = false;
            boolean stoppedDueToLackItem = false;

            for (int i = 0; i < times; i++) {
                if (player.canh_gioi >= MAX_CAP_TU_TIEN) {
                    break;
                }

                int level = player.canh_gioi;
                int requiredItems = ITEM_REQUIRE[level];
                Item item = findItem(player);

                // ❗ Kiểm tra nếu không còn đủ Thỏi vàng
                if (item == null || item.quantity < requiredItems) {
                    stoppedDueToLackItem = true;
                    break;
                }

                subtractRequiredItems(player, item, requiredItems);
                totalItemsUsed += requiredItems;

                if (isSuccess(level)) {
                    successFlag = true;
                    levelUp(player);
                    break;
                } else {
                    failCount++;
                }
            }

            String resultMsg = String.format(
                    "Kết quả Độ kiếp %d lần:\n- Thất bại: %d/%d\n- Dùng: %,d Thỏi vàng",
                    times, failCount, times, totalItemsUsed
            );

            if (successFlag) {
                resultMsg += "\nChúc mừng đạo hữu Độ kiếp thành công, tiên lộ rộng mở!!!";
            }

            if (failCount == times) {
                resultMsg += "\nChia buồn với đạo hữu lần Độ kiếp này thất bại hoàn toàn -.-";
            }

            if (stoppedDueToLackItem) {
                resultMsg += "\nĐộ kiếp đã dừng vì không đủ Thỏi vàng!";
            }

            Service.gI().sendThongBaoOK(player, resultMsg);

        } catch (Exception e) {
            Logger.error("Lỗi độ kiếp: " + e.getMessage());
            Service.gI().sendThongBao(player, "Có lỗi xảy ra, vui lòng thử lại.");
        } finally {
            InventoryServiceNew.gI().sendItemBags(player);
        }
    }

    private void subtractRequiredItems(Player player, Item item, int quantity) {
        InventoryServiceNew.gI().subQuantityItemsBag(player, item, quantity);
    }

    public boolean canProcess(Player player) {
        if (player.canh_gioi >= MAX_CAP_TU_TIEN) {
            Service.gI().sendThongBaoOK(player, "Bạn đã đạt cảnh giới tối đa");
            return false;
        }

        if (player.canh_gioi > 0 && player.binh_canh != 3) {
            Service.gI().sendThongBaoOK(player, "Bạn chưa đạt đỉnh phong để độ kiếp");
            return false;
        }

        int level = player.canh_gioi;
        int requiredItems = ITEM_REQUIRE[level];
        Item goldBar = findItem(player);
        if (goldBar == null) {
            Service.gI().sendThongBaoOK(player, "Không tìm thấy Thỏi vàng trong hành trang.");
            return false;
        } else if (goldBar.quantity < requiredItems) {
            Service.gI().sendThongBaoOK(player,
                    String.format("Không đủ Thỏi vàng. Cần %,d Thỏi vàng để độ kiếp 1 lần.", requiredItems));
            return false;
        }

        int requiredDame = DAME_REQUIRE[level];
        if (player.nPoint.dameg < requiredDame) {
            Service.gI().sendThongBaoOK(player,
                    String.format("Sức đánh gốc yêu cầu: %,d (Hiện tại: %,d)", requiredDame, player.nPoint.dameg));
            return false;
        }

        return true;
    }

    private boolean isSuccess(int level) {
        return rand.nextDouble() * 100 < SUCCESS_RATE[level];
    }

    private void levelUp(Player player) {
        player.canh_gioi++;
        player.binh_canh = 0;

        int newplayerpower = 1_500_000;
        long oldplayerpower = player.nPoint.power;
        long subplayerpower = newplayerpower - oldplayerpower;

        PlayerService.gI().sendTNSM(player, (byte) 0, subplayerpower);
        player.nPoint.power = newplayerpower;
        PlayerDAO.updatePlayer(player);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    private Item findItem(Player player) {
        for (Item item : player.inventory.itemsBag) {
            if (item != null && item.template == null) {
                Logger.error("Item có template null, ID item = " + item.template.id);
            }
            if (item != null && item.template != null && item.template.id == ITEM_ID_REQUIRED) {
                return item;
            }
        }
        return null;
    }

    public String getRealNameCanhGioi(Player player, int level) {
        switch (level) {
            case 0:
                return "Phàm Nhân";
            case 1:
                return "Luyện Khí";
            case 2:
                return player.isUseTrucCoDan ? "Thiên Đạo Trúc Cơ" : "Trúc Cơ";
            case 3:
                return "Cụ Linh";
            case 4:
                return "Kim Đan";
            case 5:
                return "Nguyên Anh";
            default:
                return "Không xác định";
        }
    }
}
