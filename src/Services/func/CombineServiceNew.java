package Services.func;

import NewCombine.CuongHoaLoSaoPhaLe;
import NewCombine.EpSaoTrangBi;
import NewCombine.GiamDinhSach;
import NewCombine.HoiPhucSach;
import NewCombine.NangCapKichHoat;
import NewCombine.NangCapSachTuyetKy;
import NewCombine.NangCapSaoPhaLe;
import NewCombine.PhaLeHoaTrangBi;
import NewCombine.PhanRaSach;
import NewCombine.TaoDaHematite;
import NewCombine.TaySach;
import Services.RewardService;
import Services.Service;
import Services.InventoryServiceNew;
import Services.ItemService;
import Consts.ConstNpc;
import Daos.PlayerDAO;
import Item.Item;
import Item.Item.ItemOption;
import NPC.Npc;
import NPC.NpcManager;
import Player.Player;
import Server.Manager;
import Server.ServerNotify;
import network.Message;
import Utils.Util;
import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;

public class CombineServiceNew {

    private static final int COST_DOI_VE_DOI_DO_HUY_DIET = 500000000;
    private static final int COST_DAP_DO_KICH_HOAT = 500000000;
    private static final int COST_DOI_MANH_KICH_HOAT = 500000000;
    private static final int COST_PHAN_RA_DO_TL_1 = 200000000;
    private static final int COST_PHAN_RA_DO_TL_2 = 400000000;
    private static final int COST_PHAN_RA_DO_TL_3 = 500000000;

    private static final int COST = 500000000;

    public static final byte MAX_STAR_ITEM = 8;
    private static final byte MAX_LEVEL_ITEM = 8;

    private static final byte OPEN_TAB_COMBINE = 0;
    private static final byte REOPEN_TAB_COMBINE = 1;
    private static final byte COMBINE_SUCCESS = 2;
    private static final byte COMBINE_FAIL = 3;
    private static final byte COMBINE_CHANGE_OPTION = 4;
    private static final byte COMBINE_DRAGON_BALL = 5;
    public static final byte OPEN_ITEM = 6;

    //khaile add
    public static final int CHE_TAO_THIEN_MA = 404;
    public static final int CHE_TAO_VO_CUC_TU_TAI = 405;
    public static final int CHE_TAO_NGOAI_TRANG_VO_CUC_TU_TAI = 406;

    public static final int CHE_TAO_DAN_DUOC_LUYEN_KHI = 444;
    public static final int CHE_TAO_TRUC_CO_DAN = 445;
    public static final int CHE_TAO_TRUC_CO_SO = 446;
    public static final int CHE_TAO_TRUC_CO_TRUNG = 447;
    public static final int CHE_TAO_TRUC_CO_HAU = 448;
    //end khaile add

    public static final int EP_SAO_TRANG_BI = 500;
    public static final int PHA_LE_HOA_TRANG_BI = 501;
    public static final int NANG_CAP_SAO_PHA_LE = 100;
    public static final int CUONG_HOA_LO_SAO_PHA_LE = 102;
    public static final int TAO_DA_HEMATITE = 103;
    public static final int DOI_DIEM = 502;
    public static final int XU_THONG_QUAN = 503;

    public static final int HUY_HIEU_HUY_DIET = 505;

    public static final int NANG_CAP_VAT_PHAM = 510;
    public static final int NANG_CAP_BONG_TAI = 511;
    public static final int NGUYET_AN = 512;
    public static final int NHAP_NGOC_RONG = 513;
    public static final int PHAN_RA_DO_THAN_LINH = 514;
    public static final int NANG_CAP_DO_TS = 515;
    public static final int NANG_CAP_SKH_VIP = 516;
    public static final int MO_CHI_SO_BONG_TAI = 519;
    public static final int CHE_TAO_TRANG_BI_TS = 520;
    public static final int LUYEN_HOA_CHIEN_LINH = 521;
    public static final int EP_AN_TRANG_BI = 522;
    public static final int LAM_PHEP_NHAP_DA = 525;
    public static final int NANG_CAP_KICH_HOAT = 526;

    private static final int GOLD_BONG_TAI = 500_000_000;
    private static final int GEM_BONG_TAI = 5_000;
    private static final int RATIO_BONG_TAI = 50;
    private static final int RATIO_NANG_CAP = 45;

    //--------Sách Tuyệt Kỹ
    public static final int GIAM_DINH_SACH = 1233;
    public static final int TAY_SACH = 1234;
    public static final int NANG_CAP_SACH_TUYET_KY = 1235;
    public static final int HOI_PHUC_SACH = 1236;
    public static final int PHAN_RA_SACH = 1237;
    //------------End

    private static final long GOLD_BONG_TAI2 = 5_000_000_000L;
    private static final int RUBY_BONG_TAI2 = 1_000;
    private static final int GEM_BONG_TAI2 = 1_000_000;
    private static final int GOLD_MOCS_BONG_TAI = 500_000_000;
    private static final int Gem_MOCS_BONG_TAI = 500;
    private static final int RUBY_MOCS_BONG_TAI = 500;
    public static final int NANG_CAP_CHAN_MENH = 5380;

    public final Npc baHatMit;
    private final Npc meoKarin;
    private final Npc whis_64;
//khaile add
    private final Npc DoaTien;
    private final Npc ThienMa;
//end khaile add
    private static CombineServiceNew i;

    public CombineServiceNew() {
        this.baHatMit = NpcManager.getNpc(ConstNpc.BA_HAT_MIT);
        this.meoKarin = NpcManager.getNpc(ConstNpc.THAN_MEO_KARIN);
        this.whis_64 = NpcManager.getNpc(ConstNpc.WHIS);
        //khaile add
        this.DoaTien = NpcManager.getNpc(ConstNpc.DOA_TIEN);
        this.ThienMa = NpcManager.getNpc(ConstNpc.THIEN_MA);
        //end khaile add

    }

    public static CombineServiceNew gI() {
        if (i == null) {
            i = new CombineServiceNew();
        }
        return i;
    }

    /**
     * Mở tab đập đồ
     *
     * @param player
     * @param type kiểu đập đồ
     */
    public void openTabCombine(Player player, int type) {
        player.combineNew.setTypeCombine(type);
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(OPEN_TAB_COMBINE);
            msg.writer().writeUTF(getTextInfoTabCombine(type));
            msg.writer().writeUTF(getTextTopTabCombine(type));
            if (player.iDMark.getNpcChose() != null) {
                msg.writer().writeShort(player.iDMark.getNpcChose().tempId);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    //khaile add helper
    private String getTenManhTrangBi(int manhId) {
        switch (manhId) {
            case 1688:
                return "Áo Vạn Năng";
            case 1689:
                return "Quần Vạn Năng";
            case 1690:
                return "Găng Vạn Năng";
            case 1691:
                return "Giày Vạn Năng";
            case 1692:
                return "Nhẫn Vạn Năng";
            default:
                return "";
        }
    }

    private String getTenTrangBi(int manhId) {
        switch (manhId) {
            case 1688:
                return "Áo Vô Cực";
            case 1689:
                return "Quần Vô Cực";
            case 1690:
                return "Găng Vô Cực";
            case 1691:
                return "Giày Vô Cực";
            case 1692:
                return "Nhẫn Vô Cực";
            default:
                return "";
        }
    }

    private short getIdTrangBi(int manhId) {
        // Map ID mảnh -> ID thành phẩm
        switch (manhId) {
            case 1688:
                return 1682; // ID áo
            case 1689:
                return 1683; // ID quần
            case 1690:
                return 1684; // ID găng
            case 1691:
                return 1685; // ID giày
            case 1692:
                return 1686; // ID trang sức
            default:
                return -1;
        }
    }

    private String getTenTuIdThanhPham(short itemId) {
        switch (itemId) {
            case 1682:
                return "Áo Vô Cực";
            case 1683:
                return "Quần Vô Cực";
            case 1684:
                return "Găng Vô Cực";
            case 1685:
                return "Giày Vô Cực";
            case 1686:
                return "Nhẫn Vô Cực";
            default:
                return "Vô Cực Tự Tại";
        }
    }

    private short getVoucherVoCucTuTaiById(int fragmentId) {
        switch (fragmentId) {
            case 1688:
                return 1693; // Phiếu áo
            case 1689:
                return 1694; // Phiếu quần
            case 1690:
                return 1695; // Phiếu găng
            case 1691:
                return 1696; // Phiếu giày
            case 1692:
                return 1697; // Phiếu trang sức
            default:
                return -1;
        }
    }

    private String getTenTrangBiThienMa(int manhId) {
        switch (manhId) {
            case 1688:
                return "Áo Thiên Ma";
            case 1689:
                return "Quần Thiên Ma";
            case 1690:
                return "Găng Thiên Ma";
            case 1691:
                return "Giày Thiên Ma";
            case 1692:
                return "Nhẫn Thiên Ma";
            default:
                return "";
        }
    }

    private short getIdTrangBiThienMaTuManhTrangBi(int manhId) {
        // Map ID mảnh -> ID thành phẩm
        switch (manhId) {
            case 1688:
                return 1702; // ID áo
            case 1689:
                return 1703; // ID quần
            case 1690:
                return 1704; // ID găng
            case 1691:
                return 1705; // ID giày
            case 1692:
                return 1706; // ID trang sức
            default:
                return -1;
        }
    }

    private String getTenThienMaTuIdThanhPham(short itemId) {
        switch (itemId) {
            case 1702:
                return "Áo Thiên Ma";
            case 1703:
                return "Quần Thiên Ma";
            case 1704:
                return "Găng Thiên Ma";
            case 1705:
                return "Giày Thiên Ma";
            case 1706:
                return "Nhẫn Thiên Ma";
            default:
                return "";
        }
    }

    private void conditionTuTaiItem(Player player) {
        if (player.combineNew.itemsCombine.isEmpty()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Hãy đặt đầy đủ nguyên liệu để chế tạo Vô Cực Tự Tại!", "Đóng");
            return;
        }

        int countDanDuoc = 0;
        int requiredDanDuoc = 3;
        Item manhTrangBi = null;
        Item linhKhi = null;
        Item thoiVang = null;
        List<Item> validItems = new ArrayList<>();

        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                // Check Đan Dược
                if (item.isTrucCoDanDuoc() && item.quantity >= 99) {
                    countDanDuoc++;
                    validItems.add(item);
                } // Check Mảnh Trang Bị
                else if (item.template.id >= 1688 && item.template.id <= 1692) {
                    if (manhTrangBi == null && item.quantity >= 99) {
                        manhTrangBi = item;
                        validItems.add(item);
                    } else {
                        this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Chỉ được dùng 1 loại mảnh trang bị!", "Đóng");
                        return;
                    }
                } // Check Linh Khí
                else if (item.isLinhKhi()) {
                    if (linhKhi == null) {
                        linhKhi = item;
                        validItems.add(item);
                    }
                } // Check Thỏi Vàng
                else if (item.template.id == 457) {
                    if (thoiVang == null) {
                        thoiVang = item;
                        validItems.add(item);
                    }
                }
            }
        }

        // Validate số lượng
        if (player.combineNew.itemsCombine.size() != validItems.size()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Có vật phẩm không hợp lệ hoặc số lượng không đủ!", "Đóng");
            return;
        }

        if (countDanDuoc < requiredDanDuoc) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Cần đủ 3 loại Đan Dược Trúc Cơ x99!", "Đóng");
            return;
        }

        if (manhTrangBi == null) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Cần 99 mảnh trang bị vạn năng!", "Đóng");
            return;
        }

        if (linhKhi == null || linhKhi.quantity < 99_999) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Cần 99,999 Linh Khí!", "Đóng");
            return;
        }

        if (thoiVang == null || thoiVang.quantity < 10000) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Cần 10000 Thỏi Vàng!", "Đóng");
            return;
        }

        if (player.inventory.ruby < 5_000_000) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Cần 5 triệu Hồng Ngọc!", "Đóng");
            return;
        }

        if (player.getSession().vnd < 300_000) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Cần 300,000 VNĐ!", "Đóng");
            return;
        }

        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 2) {
            Service.gI().sendThongBao(player, "Không đủ ô trống!");
            return;
        }

        // Hiển thị thông báo xác nhận
        String confirmMsg = String.format("Chế tạo %s?\nChi phí:\n"
                + "|2|- 3 Đan Dược x99\n"
                + "|2|- 99 Mảnh %s\n"
                + "|2|- 99,999 Linh Khí\n"
                + "|2|- 10000 Thỏi Vàng\n"
                + "|2|- 5M Hồng Ngọc\n"
                + "|2|- 300k VNĐ",
                getTenTrangBi(manhTrangBi.template.id),
                getTenManhTrangBi(manhTrangBi.template.id));

        this.DoaTien.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, confirmMsg, "Chế tạo", "Hủy");
    }

    private void createTuTaiItem(Player player, short itemId, List<ItemOption> itemOptions) {

        int countDanDuoc = 0;
        List<Item> validItems = new ArrayList<>();
        Item linhKhi = null;
        Item thoiVang = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                if (item.isTrucCoDanDuoc() && item.quantity >= 99) {
                    countDanDuoc++;
                    validItems.add(item);
                } else if (item.isLinhKhi()) {
                    linhKhi = item;
                    validItems.add(item);
                } else if (item.template.id == 457) { // Kiểm tra ID Thỏi Vàng
                    thoiVang = item;
                    validItems.add(item);
                }
            }
        }
        Item manhTrangBi = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item.template.id >= 1688 && item.template.id <= 1692) {
                manhTrangBi = item;
                break;
            }
        }
        // Trừ 4 loại đan dược
        int removedCount = 0;
        for (Item item : validItems) {
            if (item.isTrucCoDanDuoc() && item.quantity >= 99) {
                InventoryServiceNew.gI().subQuantityItemsBag(player, item, 99);
                removedCount++;
                if (removedCount == 3) {
                    break;
                }
            }
        }
        // TẠO TRANG BỊ DỰA TRÊN MẢNH
        short idThanhPham = getIdTrangBi(manhTrangBi.template.id);
        Item newItem = ItemService.gI().createNewItem(idThanhPham);
        newItem.itemOptions.addAll(itemOptions);
        InventoryServiceNew.gI().addItemBag(player, newItem);

        // Thêm phiếu đổi cải trang tương ứng
        short voucherId = getVoucherVoCucTuTaiById(manhTrangBi.template.id);
        Item voucherItem = ItemService.gI().createNewItem(voucherId);
        InventoryServiceNew.gI().addItemBag(player, voucherItem);

        // Trừ nguyên liệu
        thoiVang.quantity -= 10_000;
        player.inventory.ruby -= 5_000_000;
        PlayerDAO.subvnd(player, 300_000);
        InventoryServiceNew.gI().subQuantityItemsBag(player, linhKhi, 99_999);
        InventoryServiceNew.gI().subQuantityItemsBag(player, manhTrangBi, 99);

        // cập nhật hồng ngọc hành trang
        Service.gI().sendMoney(player);
        // Cập nhật hành trang
        InventoryServiceNew.gI().sendItemBags(player);

        // Thông báo thành công
        String tenTrangBi = getTenTuIdThanhPham(itemId);
        String tenVoucher = voucherItem.template.name;
        Service.gI().sendThongBao(player, "Chúc mừng! Chế tạo thành công " + "" + tenTrangBi + " và nhận được " + "" + tenVoucher);
        sendEffectSuccessCombine(player);

    }

    private void conditionNgoaiTrangVoCuc(Player player) {
        if (player.combineNew.itemsCombine.isEmpty()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Hãy đặt đầy đủ nguyên liệu để chế tạo!", "Đóng");
            return;
        }

        Set<Short> uniquePhieuDoiNgoaiTrangVoCuc = new HashSet<>();
        int countPhieuDoiNgoaiTrangVoCuc = 0;
        int requiredPhieuDoiNgoaiTrangVoCuc = 5;
        List<Item> validItems = new ArrayList<>();

        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem() && item.isPhieuDoiNgoaiTrangVoCuc() && item.quantity >= 1) {
                uniquePhieuDoiNgoaiTrangVoCuc.add(item.template.id);
                countPhieuDoiNgoaiTrangVoCuc++;
                validItems.add(item);
            }
        }

        if (uniquePhieuDoiNgoaiTrangVoCuc.size() < requiredPhieuDoiNgoaiTrangVoCuc) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Bạn cần đủ 5 phiếu đổi ngoại trang Vô Cực!", "Đóng");
            return;
        }

        if (validItems.size() != player.combineNew.itemsCombine.size()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Có vật phẩm không hợp lệ hoặc số lượng không đủ!", "Đóng");
            return;
        }

        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 1) {
            Service.gI().sendThongBao(player, "Không đủ ô trống trong túi đồ!");
            return;
        }

        String concac = "Bạn có chắc muốn đổi ngoại trang Vô Cực?\nChi phí: \n"
                + "Phiếu đổi ngoại trang Vô Cực (Áo)\n"
                + "Phiếu đổi ngoại trang Vô Cực (Quần)\n"
                + "Phiếu đổi ngoại trang Vô Cực (Găng)\n"
                + "Phiếu đổi ngoại trang Vô Cực (Giày)\n"
                + "Phiếu đổi ngoại trang Vô Cực (Nhẫn)\n";
        this.DoaTien.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, concac, "Chế tạo", "Từ chối");
    }

    private void createNgoaiTrangVoCuc(Player player, short itemId, List<ItemOption> itemOptions) {

        int countPhieuDoiNgoaiTrangVoCuc = 0;
        List<Item> validItems = new ArrayList<>();
        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                if (item.quantity >= 1) {
                    countPhieuDoiNgoaiTrangVoCuc++;
                    validItems.add(item);
                }

            }
        }

        int removedCount = 0;
        for (Item item : validItems) {
            if (item.isPhieuDoiNgoaiTrangVoCuc() && item.quantity >= 1) {
                InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
                removedCount++;
                if (removedCount == 5) {
                    break;
                }
            }
        }

        // Tạo trang bị mới
        Item newItem = ItemService.gI().createNewItem(itemId);
        newItem.itemOptions.addAll(itemOptions); // Thêm danh sách ItemOption
        InventoryServiceNew.gI().addItemBag(player, newItem);
        //cập nhật lại hành trang
        InventoryServiceNew.gI().sendItemBags(player);
        // Thông báo thành công
        Service.gI().sendThongBao(player, "Chúc mừng! Bạn đã đổi thành công ngoại trang Vô Cực!");

    }

    private void conditionThienMaItem(Player player) {
        List<Item> items = player.combineNew.itemsCombine;
        if (items == null || items.isEmpty()) {
            this.ThienMa.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Hãy đưa ta Thiên Ma Thạch và Mảnh Trang Bị để chế tạo.", "Đóng");
            return;
        }

        int tongThienMaThach = 0;
        Item manhTrangBi = null;
        int soLuongManh = 0;
        Set<Short> loaiManhKhacNhau = new HashSet<>();

        for (Item item : items) {
            if (item == null || !item.isNotNullItem()) {
                continue;
            }

            if (item.isThienMaThach()) {
                tongThienMaThach += item.quantity;
            } else if (item.template.id >= 1688 && item.template.id <= 1692) {
                loaiManhKhacNhau.add(item.template.id);
                if (manhTrangBi == null) {
                    manhTrangBi = item;
                }
                soLuongManh += item.quantity;
            } else {
                this.ThienMa.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                        "Có vật phẩm không hợp lệ trong nguyên liệu!", "Đóng");
                return;
            }
        }

        if (tongThienMaThach < 100_000) {
            this.ThienMa.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Cần ít nhất 100.000 Thiên Ma Thạch!", "Đóng");
            return;
        }

        if (loaiManhKhacNhau.size() != 1 || soLuongManh < 19) {
            this.ThienMa.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Chỉ được dùng 1 loại mảnh trang bị và cần ít nhất 19 mảnh!", "Đóng");
            return;
        }

        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 2) {
            Service.gI().sendThongBao(player, "Không đủ ô trống!");
            return;
        }

        String confirmMsg = String.format("Chế tạo %s?\nChi phí:\n"
                + "|2|- 19 Mảnh %s\n"
                + "|2|- 100K Thiên Ma Thạch",
                getTenTrangBiThienMa(manhTrangBi.template.id),
                getTenManhTrangBi(manhTrangBi.template.id));

        this.ThienMa.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, confirmMsg, "Chế tạo", "Từ chối");
    }

    private void createThienMaItem(Player player, short itemId, List<ItemOption> itemOptions) {
        Item manhTrangBi = null;
        Item thienMaThach = null;

        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                if (item.isThienMaThach() && item.quantity >= 100_000) {
                    thienMaThach = item;
                } else if (item.template.id >= 1688 && item.template.id <= 1692 && item.quantity >= 19) {
                    manhTrangBi = item;
                }
            }
        }

        // Kiểm tra nếu thiếu nguyên liệu
        if (thienMaThach == null || manhTrangBi == null) {
            Service.gI().sendThongBao(player, "Thiếu nguyên liệu! Cần 100K Thiên Ma Thạch và 19 mảnh trang bị.");
            return;
        }

        // Tạo trang bị mới
        short idThanhPham = getIdTrangBiThienMaTuManhTrangBi(manhTrangBi.template.id);
        Item newItem = ItemService.gI().createNewItem(idThanhPham);
        newItem.itemOptions.addAll(itemOptions);
        InventoryServiceNew.gI().addItemBag(player, newItem);

        // Trừ nguyên liệu
        InventoryServiceNew.gI().subQuantityItemsBag(player, thienMaThach, 100_000);
        InventoryServiceNew.gI().subQuantityItemsBag(player, manhTrangBi, 19);

        // Cập nhật hành trang
        InventoryServiceNew.gI().sendItemBags(player);

        // Thông báo thành công
        String tenTrangBi = getTenThienMaTuIdThanhPham(itemId);
        Service.gI().sendThongBao(player, "Chúc mừng! Chế tạo thành công " + tenTrangBi);
        sendEffectSuccessCombine(player);
    }

    private void conditionHoangCucDan(Player player) {
        if (player.combineNew.itemsCombine.isEmpty()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Hãy đặt đầy đủ nguyên liệu để chế tạo!", "Đóng");
            return;
        }

        Set<Short> uniqueTanDanIds = new HashSet<>();
        int countDanDuoc = 0;
        int requiredDanDuoc = 9;
        List<Item> validItems = new ArrayList<>();

        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem() && item.isTanDan() && item.quantity >= 99) {
                uniqueTanDanIds.add(item.template.id);
                countDanDuoc++;
                validItems.add(item);
            }
        }

        if (uniqueTanDanIds.size() < requiredDanDuoc) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Bạn cần đủ 9 loại tàn đan x99 mỗi loại!", "Đóng");
            return;
        }

        if (validItems.size() != player.combineNew.itemsCombine.size()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Có vật phẩm không hợp lệ hoặc số lượng không đủ!", "Đóng");
            return;
        }

        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 2) {
            Service.gI().sendThongBao(player, "Không đủ ô trống!");
            return;
        }

        String concac = "Bạn có chắc muốn chế tạo đan dược Luyện Khí?\nChi phí: \n"
                + "|2|- 9 Loại tàn đan x99 mỗi loại\n";
        this.DoaTien.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, concac, "Chế tạo", "Từ chối");
    }

    private void createHoangCucDan(Player player, short itemId) {

        int countDanDuoc = 0;
        List<Item> validItems = new ArrayList<>();
        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                if (item.isTanDan() && item.quantity >= 99) {
                    countDanDuoc++;
                    validItems.add(item);
                }

            }
        }

        // Trừ 4 loại đan dược
        int removedCount = 0;
        for (Item item : validItems) {
            if (item.isTanDan() && item.quantity >= 99) {
                InventoryServiceNew.gI().subQuantityItemsBag(player, item, 99);
                removedCount++;
                if (removedCount == 9) {
                    break;
                }
            }
        }

        // Tạo trang bị mới
        Item newItem = ItemService.gI().createNewItem(itemId);
        InventoryServiceNew.gI().addItemBag(player, newItem);
        //cập nhật lại hành trang
        InventoryServiceNew.gI().sendItemBags(player);

        // Thông báo thành công
        Service.gI().sendThongBao(player, "Chúc mừng! Bạn đã chế tạo đan dược thành công!");

    }

    private void conditionTrucCoDan(Player player) {
        if (player.combineNew.itemsCombine.isEmpty()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Hãy đặt đầy đủ nguyên liệu để chế tạo!", "Đóng");
            return;
        }

        Set<Short> uniqueHoangCucDanIds = new HashSet<>();
        boolean hasTrucCoDanPhuong = false;
        int countDanDuoc = 0;
        int requiredDanDuoc = 1;
        List<Item> validItems = new ArrayList<>();

        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                // Kiểm tra Hoàng Cực Đan (id = 1664)
                if (item.template.id == 1668 && item.quantity >= 100) {
                    uniqueHoangCucDanIds.add(item.template.id);
                    countDanDuoc++;
                    validItems.add(item);
                }
                // Kiểm tra Trúc Cơ Đan Phương
                if (item.template.id == 1681) {
                    hasTrucCoDanPhuong = true;
                    validItems.add(item);
                }
            }
        }

        if (uniqueHoangCucDanIds.size() < requiredDanDuoc) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Bạn cần đủ Hoàng Cực Đan x100!", "Đóng");
            return;
        }

        if (!hasTrucCoDanPhuong) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Bạn cần có Trúc Cơ Đan Phương!", "Đóng");
            return;
        }

        if (validItems.size() != player.combineNew.itemsCombine.size()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Có vật phẩm không hợp lệ hoặc số lượng không đủ!", "Đóng");
            return;
        }

        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 2) {
            Service.gI().sendThongBao(player, "Không đủ ô trống!");
            return;
        }

        String concac = "Bạn có chắc muốn chế tạo Trúc Cơ Đan?\nChi phí: \n"
                + "|2|- Hoàng Cực Đan x100\n"
                + "|2|- Trúc Cơ Đan Phương\n"
                + "|2|- Tỉ lệ 20%\n";

        this.DoaTien.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, concac, "Chế tạo", "Từ chối");
    }

    private void createTrucCoDan(Player player, short itemId) {
        List<Item> hoangCucDanItems = new ArrayList<>();
        Item trucCoDanPhuongItem = null;

        // Đếm số lượng Hoàng Cực Đan và tìm Trúc Cơ Đan Phương
        int countHoangCucDan = 0;
        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                if (item.template.id == 1668 && item.quantity >= 1) { // Hoàng Cực Đan
                    countHoangCucDan += item.quantity;
                    hoangCucDanItems.add(item);
                }
                if (item.template.id == 1681) { // Trúc Cơ Đan Phương
                    trucCoDanPhuongItem = item;
                }
            }
        }

        // Kiểm tra điều kiện đủ nguyên liệu
        if (countHoangCucDan < 100) {
            Service.gI().sendThongBao(player, "Bạn cần ít nhất 100 Hoàng Cực Đan!");
            return;
        }
        if (trucCoDanPhuongItem == null) {
            Service.gI().sendThongBao(player, "Bạn cần có Trúc Cơ Đan Phương!");
            return;
        }

        // Trừ Hoàng Cực Đan (100 viên)
        int removedCount = 0;
        for (Item item : hoangCucDanItems) {
            int toRemove = Math.min(item.quantity, 100 - removedCount);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, toRemove);
            removedCount += toRemove;
            if (removedCount >= 100) {
                break;
            }
        }

        // Trừ Trúc Cơ Đan Phương
        InventoryServiceNew.gI().subQuantityItemsBag(player, trucCoDanPhuongItem, 1);

        // **Cập nhật lại túi đồ trước khi xác định thành công/thất bại**
        InventoryServiceNew.gI().sendItemBags(player);

        // Xác suất thành công 25%
        if (new Random().nextInt(100) < 20) { // 20% tỷ lệ thành công
            // Tạo vật phẩm mới (Trúc Cơ Đan)
            sendEffectSuccessCombine(player);
            Item newItem = ItemService.gI().createNewItem(itemId);
            InventoryServiceNew.gI().addItemBag(player, newItem);
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendThongBao(player, "Chúc mừng! Bạn đã chế tạo Trúc Cơ Đan thành công!");
        } else {
            sendEffectFailCombine(player);
            Service.gI().sendThongBao(player, "Thất bại! Bạn đã không chế tạo được Trúc Cơ Đan.");
        }
    }

    private void conditionTrucCoDan_SoKy(Player player) {
        if (player.combineNew.itemsCombine.isEmpty()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Hãy đặt đầy đủ nguyên liệu để chế tạo!", "Đóng");
            return;
        }

        Set<Short> uniqueHoangCucDanIds = new HashSet<>();
        int countDanDuoc = 0;
        int requiredDanDuoc = 1;
        List<Item> validItems = new ArrayList<>();

        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                // Kiểm tra Hoàng Cực Đan (id = 1668)
                if (item.template.id == 1668 && item.quantity >= 100) {
                    uniqueHoangCucDanIds.add(item.template.id);
                    countDanDuoc++;
                    validItems.add(item);
                }

            }
        }

        if (uniqueHoangCucDanIds.size() < requiredDanDuoc || validItems.size() != player.combineNew.itemsCombine.size()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Có vật phẩm không hợp lệ hoặc số lượng không đủ!", "Đóng");
            return;
        }

//        if (validItems.size() != player.combineNew.itemsCombine.size()) {
//            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
//                    "Có vật phẩm không hợp lệ hoặc số lượng không đủ!", "Đóng");
//            return;
//        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 2) {
            Service.gI().sendThongBao(player, "Không đủ ô trống!");
            return;
        }

        String concac = "Bạn có chắc muốn chế tạo Trúc Cơ Đan Dược Sơ Kỳ?\nChi phí: \n"
                + "|2|- Hoàng Cực Đan x100\n";
        this.DoaTien.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, concac, "Chế tạo", "Từ chối");
    }

    private void createTrucCoDan_SoKy(Player player, short itemId) {
        List<Item> hoangCucDanItems = new ArrayList<>();

        // Đếm số lượng Hoàng Cực Đan và tìm Trúc Cơ Đan Phương
        int countHoangCucDan = 0;
        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                if (item.template.id == 1668 && item.quantity >= 1) { // Hoàng Cực Đan
                    countHoangCucDan += item.quantity;
                    hoangCucDanItems.add(item);
                }

            }
        }

        // Kiểm tra điều kiện đủ nguyên liệu
        if (countHoangCucDan < 100) {
            Service.gI().sendThongBao(player, "Bạn cần ít nhất 100 Hoàng Cực Đan!");
            return;
        }

        // Trừ Hoàng Cực Đan (100 viên)
        int removedCount = 0;
        for (Item item : hoangCucDanItems) {
            int toRemove = Math.min(item.quantity, 100 - removedCount);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, toRemove);
            removedCount += toRemove;
            if (removedCount >= 100) {
                break;
            }
        }
        // Tạo vật phẩm mới (Trúc Cơ Đan Sơ Kỳ)
        sendEffectSuccessCombine(player);
        Item newItem = ItemService.gI().createNewItem(itemId);
        InventoryServiceNew.gI().addItemBag(player, newItem);
        InventoryServiceNew.gI().sendItemBags(player);
        Service.gI().sendThongBao(player, "Chúc mừng! Bạn đã chế tạo Trúc Cơ Đan Sơ Kỳ thành công!");
    }

    private void conditionTrucCoDan_TrungKy(Player player) {
        if (player.combineNew.itemsCombine.isEmpty()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Hãy đặt đầy đủ nguyên liệu để chế tạo!", "Đóng");
            return;
        }

        Set<Short> uniqueLongTuyDanIds = new HashSet<>();
        int countDanDuoc = 0;
        int requiredDanDuoc = 1;
        List<Item> validItems = new ArrayList<>();

        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                // Kiểm tra Long Tủy Đan (id = 1665)
                if (item.template.id == 1664 && item.quantity >= 3) {
                    uniqueLongTuyDanIds.add(item.template.id);
                    countDanDuoc++;
                    validItems.add(item);
                }

            }
        }

        if (uniqueLongTuyDanIds.size() < requiredDanDuoc || validItems.size() != player.combineNew.itemsCombine.size()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Có vật phẩm không hợp lệ hoặc số lượng không đủ!", "Đóng");
            return;
        }

        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 2) {
            Service.gI().sendThongBao(player, "Không đủ ô trống!");
            return;
        }

        String concac = "Bạn có chắc muốn chế tạo Trúc Cơ Đan Dược Trung Kỳ?\nChi phí: \n"
                + "|2|- Long Tủy Đan x3\n";
        this.DoaTien.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, concac, "Chế tạo", "Từ chối");
    }

    private void createTrucCoDan_TrungKy(Player player, short itemId) {
        List<Item> truccodansokyItems = new ArrayList<>();

        // Đếm số lượng 
        int countLongTuyDan = 0;
        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                if (item.template.id == 1664 && item.quantity >= 1) { // Long Tuy Dan
                    countLongTuyDan += item.quantity;
                    truccodansokyItems.add(item);
                }

            }
        }

        // Kiểm tra điều kiện đủ nguyên liệu
        if (countLongTuyDan < 3) {
            Service.gI().sendThongBao(player, "Bạn cần ít nhất 3 Long Tủy Đan!");
            return;
        }

        // Trừ 
        int removedCount = 0;
        for (Item item : truccodansokyItems) {
            int toRemove = Math.min(item.quantity, 3 - removedCount);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, toRemove);
            removedCount += toRemove;
            if (removedCount >= 3) {
                break;
            }
        }
        // Tạo vật phẩm mới (Trúc Cơ Đan Trung Kỳ)
        sendEffectSuccessCombine(player);
        Item newItem = ItemService.gI().createNewItem(itemId);
        InventoryServiceNew.gI().addItemBag(player, newItem);
        InventoryServiceNew.gI().sendItemBags(player);
        Service.gI().sendThongBao(player, "Chúc mừng! Bạn đã chế tạo Trúc Cơ Đan Trung Kỳ thành công!");
    }

    private void conditionTrucCoDan_HauKy(Player player) {
        if (player.combineNew.itemsCombine.isEmpty()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Hãy đặt đầy đủ nguyên liệu để chế tạo!", "Đóng");
            return;
        }

        Set<Short> uniqueChanNguyenDanIds = new HashSet<>();
        int countDanDuoc = 0;
        int requiredDanDuoc = 1;
        List<Item> validItems = new ArrayList<>();

        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                // Kiểm tra 
                if (item.template.id == 1665 && item.quantity >= 3) {
                    uniqueChanNguyenDanIds.add(item.template.id);
                    countDanDuoc++;
                    validItems.add(item);
                }

            }
        }

        if (uniqueChanNguyenDanIds.size() < requiredDanDuoc || validItems.size() != player.combineNew.itemsCombine.size()) {
            this.DoaTien.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                    "Có vật phẩm không hợp lệ hoặc số lượng không đủ!", "Đóng");
            return;
        }

        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 2) {
            Service.gI().sendThongBao(player, "Không đủ ô trống!");
            return;
        }

        String concac = "Bạn có chắc muốn chế tạo Trúc Cơ Đan Dược Hậu Kỳ?\nChi phí: \n"
                + "|2|- Chân Nguyên Đan x3\n";
        this.DoaTien.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, concac, "Chế tạo", "Từ chối");
    }

    private void createTrucCoDan_HauKy(Player player, short itemId) {
        List<Item> truccodantrungkyItems = new ArrayList<>();

        // Đếm số lượng 
        int countChanNguyenDan = 0;
        for (Item item : player.combineNew.itemsCombine) {
            if (item != null && item.isNotNullItem()) {
                if (item.template.id == 1665 && item.quantity >= 1) {
                    countChanNguyenDan += item.quantity;
                    truccodantrungkyItems.add(item);
                }

            }
        }

        // Kiểm tra điều kiện đủ nguyên liệu
        if (countChanNguyenDan < 3) {
            Service.gI().sendThongBao(player, "Bạn cần ít nhất 3 Chân Nguyên Đan!");
            return;
        }

        // Trừ 
        int removedCount = 0;
        for (Item item : truccodantrungkyItems) {
            int toRemove = Math.min(item.quantity, 3 - removedCount);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, toRemove);
            removedCount += toRemove;
            if (removedCount >= 3) {
                break;
            }
        }
        // Tạo vật phẩm mới (Trúc Cơ Đan Trung Kỳ)
        sendEffectSuccessCombine(player);
        Item newItem = ItemService.gI().createNewItem(itemId);
        InventoryServiceNew.gI().addItemBag(player, newItem);
        InventoryServiceNew.gI().sendItemBags(player);
        Service.gI().sendThongBao(player, "Chúc mừng! Bạn đã chế tạo Trúc Cơ Đan Hậu Kỳ thành công!");
    }
    //end khaile add helper

    /**
     * Hiển thị thông tin đập đồ
     *
     * @param player
     * @param index
     */
    public void showInfoCombine(Player player, int[] index) {
        player.combineNew.clearItemCombine();
        if (index.length > 0) {
            for (int i = 0; i < index.length; i++) {
                player.combineNew.itemsCombine.add(player.inventory.itemsBag.get(index[i]));
            }
        }
        switch (player.combineNew.typeCombine) {

            case CHE_TAO_VO_CUC_TU_TAI:
                conditionTuTaiItem(player);
                break;
            case CHE_TAO_NGOAI_TRANG_VO_CUC_TU_TAI:
                conditionNgoaiTrangVoCuc(player);
                break;
            case CHE_TAO_DAN_DUOC_LUYEN_KHI:
                conditionHoangCucDan(player);
                break;
            case CHE_TAO_TRUC_CO_DAN:
                conditionTrucCoDan(player);
                break;
            case CHE_TAO_TRUC_CO_SO:
                conditionTrucCoDan_SoKy(player);
                break;
            case CHE_TAO_TRUC_CO_TRUNG:
                conditionTrucCoDan_TrungKy(player);
                break;
            case CHE_TAO_TRUC_CO_HAU:
                conditionTrucCoDan_HauKy(player);
                break;
            case CHE_TAO_THIEN_MA:
                conditionThienMaItem(player);
                break;
            //khaile fix chan menh theo v1
            case NANG_CAP_CHAN_MENH:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item chanmenh = null;
                    Item manhVo = null;
                    int star = 1;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id >= 1185 && item.template.id <= 1193) {
                            chanmenh = item;
                        } else if (item.template.id == 1318) {
                            manhVo = item;
                        }
                    }
                    if (chanmenh != null && chanmenh.template.id == 1193) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Chân Mệnh đã đạt cấp tối đa", "Đóng");
                        return;
                    }
                    player.combineNew.DiemNangcap = getDiemNangcapChanmenh(star);
                    player.combineNew.DaNangcap = getDaNangcapChanmenh(star);
                    player.combineNew.TileNangcap = getTiLeNangcapChanmenh(star);
                    if (chanmenh != null && manhVo != null && (chanmenh.template.id >= 1185 && chanmenh.template.id <= 1193)) {
                        String npcSay = chanmenh.template.name + "\n|2|";
                        for (Item.ItemOption io : chanmenh.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.TileNangcap + "%" + "\n";
                        if (player.combineNew.DiemNangcap <= player.event.getEventPointBoss()) {
                            npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.DiemNangcap) + " Điểm Săn Boss";
                            baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Nâng cấp\ncần " + player.combineNew.DaNangcap + " Đá Hoàng Kim");
                        } else {
                            npcSay += "Còn thiếu " + Util.numberToMoney(player.combineNew.DiemNangcap - player.event.getEventPointBoss()) + " Điểm";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Chân Mệnh và Đá Hoàng Kim", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Chân Mệnh và Đá Hoàng Kim", "Đóng");
                }
                break;
            //end khaile fix chan menh theo v1
            case NANG_CAP_KICH_HOAT:
                NangCapKichHoat.showInfoCombine(player);
                break;
            case GIAM_DINH_SACH:
                GiamDinhSach.showInfoCombine(player);
                break;
            case TAY_SACH:
                TaySach.showInfoCombine(player);
                break;
            case NANG_CAP_SACH_TUYET_KY:
                NangCapSachTuyetKy.showInfoCombine(player);
                break;
            case HOI_PHUC_SACH:
                HoiPhucSach.showInfoCombine(player);
                break;
            case PHAN_RA_SACH:
                PhanRaSach.showInfoCombine(player);
                break;
            //khaile fix theo v1
            case NANG_CAP_BONG_TAI:
                if (player.combineNew.itemsCombine.size() == 2) {
                    Item bongtai = null;
                    Item manhvobt = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (checkbongtai(item)) { // gán bông tai c1 hoặc c2
                            bongtai = item;
                        } else if (item.template.id == 933) { // gán mảnh vỡ bt
                            manhvobt = item;
                        }
                    }

                    if (bongtai != null && manhvobt != null) {
                        int level = 0;
                        for (ItemOption io : bongtai.itemOptions) {
                            if (io.optionTemplate.id == 72) {
                                level = io.param;
                                break;
                            }
                        }
                        if (level < 4) {
                            int lvbt = lvbt(bongtai);
                            int countmvbt = getcountmvbtnangbt(lvbt);
                            player.combineNew.goldCombine = getGoldnangbt(lvbt);
                            player.combineNew.gemCombine = getgemdnangbt(lvbt);
                            player.combineNew.ratioCombine = getRationangbt(lvbt);

                            String npcSay = "Bông tai Porata Cấp: " + lvbt + " \n|2|";
                            for (ItemOption io : bongtai.itemOptions) {
                                npcSay += io.getOptionString() + "\n";
                            }
                            npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                            if (manhvobt.quantity >= countmvbt) {
                                if (player.combineNew.goldCombine <= player.inventory.gold) {
                                    if (player.combineNew.gemCombine <= player.inventory.gem) {
                                        npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine)
                                                + " vàng";
                                        baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                                "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                                    } else {
                                        npcSay += "Còn thiếu " + Util.numberToMoney(
                                                player.combineNew.gemCombine - player.inventory.gem) + " ngọc";
                                        baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                                    }
                                } else {
                                    npcSay += "Còn thiếu "
                                            + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold)
                                            + " vàng";
                                    baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                                }
                            } else {
                                npcSay += "Còn thiếu " + Util.numberToMoney(countmvbt - manhvobt.quantity)
                                        + " Mảnh vỡ bông tai";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                    "Đã đạt cấp tối đa!)))", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Bông tai Porata cấp 1 hoặc cấp 2 và Mảnh vỡ bông tai", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Bông tai Porata cấp 1 hoặc cấp 2 và Mảnh vỡ bông tai", "Đóng");
                }
                break;
            case MO_CHI_SO_BONG_TAI:
                if (player.combineNew.itemsCombine.size() == 3) {
                    Item bongTai = null;
                    Item manhHon = null;
                    Item daXanhLam = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.template.id == 921 || item.template.id == 1228 || item.template.id == 1229) {
                            bongTai = item;
                        } else if (item.template.id == 934) {
                            manhHon = item;
                        } else if (item.template.id == 935) {
                            daXanhLam = item;
                        }
                    }
                    if (bongTai != null && manhHon != null && daXanhLam != null && manhHon.quantity >= 99 && daXanhLam.quantity >= 1) {

                        player.combineNew.goldCombine = GOLD_MOCS_BONG_TAI;
                        player.combineNew.gemCombine = Gem_MOCS_BONG_TAI;
                        player.combineNew.ratioCombine = RATIO_NANG_CAP;

                        String npcSay = "Bông tai Porata cấp "
                                + (bongTai.template.id == 921 ? bongTai.template.id == 1228 ? bongTai.template.id == 1229 ? "2" : "3" : "4" : "1")
                                + " \n|2|";
                        for (ItemOption io : bongTai.itemOptions) {
                            npcSay += io.getOptionString() + "\n";
                        }
                        npcSay += "|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%" + "\n";
                        if (player.combineNew.goldCombine <= player.inventory.gold) {
                            if (player.combineNew.gemCombine <= player.inventory.gem) {
                                npcSay += "|1|Cần " + Util.numberToMoney(player.combineNew.goldCombine) + " vàng";
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp\ncần " + player.combineNew.gemCombine + " ngọc");
                            } else {
                                npcSay += "Còn thiếu "
                                        + Util.numberToMoney(player.combineNew.gemCombine - player.inventory.gem)
                                        + " ngọc";
                                baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                            }
                        } else {
                            npcSay += "Còn thiếu "
                                    + Util.numberToMoney(player.combineNew.goldCombine - player.inventory.gold)
                                    + " vàng";
                            baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 Bông tai Porata cấp 2 hoặc 3, X99 Mảnh hồn bông tai và x1 Đá xanh lam", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 Bông tai Porata cấp 2 hoặc 3, X99 Mảnh hồn bông tai và x1 Đá xanh lam", "Đóng");
                }
                break;
            //end khaile fix bongtai theo v1
            case CHE_TAO_TRANG_BI_TS:
                if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 6) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isCongThucVip() || item.isCongThucThuong()).count() < 1) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Công thức Vip", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 99).count() < 1) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Mảnh đồ thiên sứ", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDHD() && item.quantity >= 1).count() < 1) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Đồ Hủy Diệt", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaNangCap()).count() < 1 || player.combineNew.itemsCombine.size() == 4 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaNangCap()).count() < 1) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Đá nâng cấp!!", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaMayMan()).count() < 1 || player.combineNew.itemsCombine.size() == 4 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaMayMan()).count() < 1) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Đá may mắn!!", "Đóng");
                        return;
                    }
                    Item mTS = null, daNC = null, daMM = null, CtVip = null, CtThuong = null, dHD = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.isManhTS()) {
                                mTS = item;
                            } else if (item.isDHD()) {
                                dHD = item;
                            } else if (item.isDaNangCap()) {
                                daNC = item;
                            } else if (item.isDaMayMan()) {
                                daMM = item;
                            } else if (item.isCongThucVip() && CtThuong == null) {
                                CtVip = item;
                            } else if (item.isCongThucThuong() && CtVip == null) {
                                CtThuong = item;
                            }
                        }
                    }
                    int tilenew = (CtVip == null ? 15 : 25);
                    int tileNC = 0, tileMM = 0;
                    if (daNC != null && daMM != null) {
                        tileNC += (daNC.template.id - 1073) * 5;
                        tileMM += (daMM.template.id - 1078) * 10;
                        tilenew += tileMM;
                    }
                    String npcSay = "|2|Chế tạo " + player.combineNew.itemsCombine.stream().filter(Item::isManhTS).findFirst().get().typeNameManh() + " Thiên sứ "
                            + player.combineNew.itemsCombine.stream().filter(item -> item.isCongThucVip() || item.isCongThucThuong()).findFirst().get().typeHanhTinh() + "\n"
                            + "|7|Mảnh ghép " + mTS.quantity + "/99\n";
                    if (daNC != null && daMM != null) {
                        npcSay += "|6|Đá nâng cấp " + player.combineNew.itemsCombine.stream().filter(Item::isDaNangCap).findFirst().get().typeDanangcap()
                                + " (+" + tileNC + "% tỉ lệ tối đa các chỉ số)\n";
                        npcSay += "|6|Đá may mắn " + player.combineNew.itemsCombine.stream().filter(Item::isDaMayMan).findFirst().get().typeDaMayman()
                                + " (+" + tileMM + "% tỉ lệ thành công)\n";
                    }
                    npcSay += "|2|Tỉ lệ thành công: " + (tilenew > 100 ? 100 : tilenew) + "%\n";
                    if (player.inventory.gold < 500000000) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Bạn không đủ vàng", "Đóng");
                        return;
                    }
                    this.whis_64.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                            npcSay, "Nâng cấp\n500 Tr vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 4) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Nguyên liệu không phù hợp", "Đóng");
                        return;
                    }
                    this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Không đủ nguyên liệu, mời quay lại sau", "Đóng");
                }
                break;
            case EP_SAO_TRANG_BI:
                EpSaoTrangBi.showInfoCombine(player);
                break;
            case PHA_LE_HOA_TRANG_BI:
                PhaLeHoaTrangBi.showInfoCombine(player);
                break;
            case NANG_CAP_SAO_PHA_LE:
                NangCapSaoPhaLe.showInfoCombine(player);
                break;
//            case CUONG_HOA_LO_SAO_PHA_LE:
//                CuongHoaLoSaoPhaLe.showInfoCombine(player);
//                break;
            case TAO_DA_HEMATITE:
                TaoDaHematite.showInfoCombine(player);
                break;
            case NHAP_NGOC_RONG:
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.combineNew.itemsCombine.size() == 1) {
                        Item item = player.combineNew.itemsCombine.get(0);
                        if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                            String npcSay = "|2|Con có muốn biến 7 " + item.template.name + " thành\n"
                                    + "1 viên " + ItemService.gI().getTemplate((short) (item.template.id - 1)).name + "\n"
                                    + "|7|Cần 7 " + item.template.name;
                            this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay, "Làm phép", "Từ chối");
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 7 viên ngọc rồng 2 sao trở lên", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cần 7 viên ngọc rồng 2 sao trở lên", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hành trang cần ít nhất 1 chỗ trống", "Đóng");
                }
                break;
            case NANG_CAP_VAT_PHAM:
                if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ nâng cấp", "Đóng");
                        break;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đá nâng cấp", "Đóng");
                        break;
                    }
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item
                            -> item.isNotNullItem() && item.template.id == 987).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ nâng cấp", "Đóng");
                        break;
                    }
                    Item itemDo = null;
                    Item itemDNC = null;
                    Item itemDBV = null;
                    for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                        if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                                itemDBV = player.combineNew.itemsCombine.get(j);
                                continue;
                            }
                            if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                                itemDo = player.combineNew.itemsCombine.get(j);
                            } else {
                                itemDNC = player.combineNew.itemsCombine.get(j);
                            }
                        }
                    }
                    if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                        int level = 0;
                        for (Item.ItemOption io : itemDo.itemOptions) {
                            if (io.optionTemplate.id == 72) {
                                level = io.param;
                                break;
                            }
                        }
                        if (level < MAX_LEVEL_ITEM) {
                            player.combineNew.goldCombine = getGoldNangCapDo(level);
                            player.combineNew.ratioCombine = (float) getTileNangCapDo(level);
                            player.combineNew.countDaNangCap = getCountDaNangCapDo(level);
                            player.combineNew.countDaBaoVe = (short) getCountDaBaoVe(level);
                            String npcSay = "|2|Hiện tại " + itemDo.template.name + " (+" + level + ")\n|0|";
                            for (Item.ItemOption io : itemDo.itemOptions) {
                                if (io.optionTemplate.id != 72) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
                            String option = null;
                            int param = 0;
                            for (Item.ItemOption io : itemDo.itemOptions) {
                                if (io.optionTemplate.id == 47
                                        || io.optionTemplate.id == 6
                                        || io.optionTemplate.id == 0
                                        || io.optionTemplate.id == 7
                                        || io.optionTemplate.id == 14
                                        || io.optionTemplate.id == 22
                                        || io.optionTemplate.id == 23
                                        || io.optionTemplate.id == 196
                                        || io.optionTemplate.id == 197
                                        || io.optionTemplate.id == 198
                                        || io.optionTemplate.id == 199) {
                                    option = io.optionTemplate.name;
                                    param = io.param + (io.param * 10 / 100);
                                    break;
                                }
                            }
                            if (option != null) {
                                npcSay += "|2|Sau khi nâng cấp (+" + (level + 1) + ")\n|7|"
                                        + option.replaceAll("#", String.valueOf(param))
                                        + "\n|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%\n"
                                        + (player.combineNew.countDaNangCap > itemDNC.quantity ? "|7|" : "|1|")
                                        + "Cần " + player.combineNew.countDaNangCap + " " + itemDNC.template.name
                                        + "\n" + (player.combineNew.goldCombine > player.inventory.gold ? "|7|" : "|1|")
                                        + "Cần " + Util.formatNumber(player.combineNew.goldCombine) + " vàng";
                            } else {
                                Service.gI().sendThongBao(player, "Vật phẩm không hợp lệ!");
                                return;
                            }

                            String daNPC = player.combineNew.itemsCombine.size() == 3 && itemDBV != null ? String.format("\nCần tốn %s đá bảo vệ", player.combineNew.countDaBaoVe) : "";
                            if ((level == 2 || level == 4 || level == 6) && !(player.combineNew.itemsCombine.size() == 3 && itemDBV != null)) {
                                npcSay += "\nNếu thất bại sẽ rớt xuống (+" + (level - 1) + ")";
                            }
                            if (player.combineNew.countDaNangCap > itemDNC.quantity) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaNangCap - itemDNC.quantity) + " " + itemDNC.template.name);
                            } else if (player.combineNew.goldCombine > player.inventory.gold) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + Util.formatNumber((player.combineNew.goldCombine - player.inventory.gold)) + " vàng");
                            } else if (player.combineNew.itemsCombine.size() == 3 && Objects.nonNull(itemDBV) && itemDBV.quantity < player.combineNew.countDaBaoVe) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaBaoVe - itemDBV.quantity) + " đá bảo vệ");
                            } else {
                                this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                        npcSay, "Nâng cấp\n" + Util.formatNumber(player.combineNew.goldCombine) + " vàng" + daNPC, "Từ chối");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Trang bị của ngươi đã đạt cấp tối đa", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị và 1 loại đá nâng cấp", "Đóng");
                    }
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cất đi con ta không thèm", "Đóng");
                        break;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị và 1 loại đá nâng cấp", "Đóng");
                }
                break;
            case HUY_HIEU_HUY_DIET:
                if (player.combineNew.itemsCombine.size() == 1 || player.combineNew.itemsCombine.size() == 2) {
                    Item item_1 = null, item_2 = null;
                    for (Item dodac : player.combineNew.itemsCombine) {
                        if (dodac.isNotNullItem()) {
                            if (dodac.template.id == 561 || dodac.template.id == 16) {
                                if (item_1 == null) {
                                    item_1 = dodac;
                                } else if (item_2 == null && dodac.template.id != (item_1.template.id == 16 ? 16 : 561)) {
                                    item_2 = dodac;
                                }
                            }
                        }
                        if (item_1 != null) {
                            String npcSay = "|6|Ngươi có muốn chuyển phân rã\n";
                            npcSay += "|2|Thành Huy Hiệu Huỷ Diệt\n|7|Với Tỉ lệ thành công 50%";
                            if (item_2 != null) {
                                this.whis_64.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "Nâng cấp", "Đóng");
                            }
                        } else {
                            this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần ít nhất 1 món đồ Thần của ngươi để Phân rã", "Đóng");
                        }
                    }
                }
                break;

            case PHAN_RA_DO_THAN_LINH:
                if (player.combineNew.itemsCombine.size() == 1 || player.combineNew.itemsCombine.size() == 2 || player.combineNew.itemsCombine.size() == 3) {
                    Item dtl_1 = null, dtl_2 = null, dtl_3 = null;
                    int sum = 0;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id >= 555 && item.template.id <= 567) {
                                if (dtl_1 == null) {
                                    dtl_1 = item;
                                    sum++;
                                } else if (dtl_2 == null && item.template.id != dtl_1.template.id) {
                                    dtl_2 = item;
                                    sum++;
                                } else if (dtl_3 == null && item.template.id != dtl_1.template.id && item.template.id != dtl_2.template.id) {
                                    dtl_3 = item;
                                    sum++;
                                }
                            }
                        }
                    }
                    if (dtl_1 != null) {
                        String npcSay = "|6|Ngươi có muốn chuyển phân rã\n";
                        if (dtl_2 == null && dtl_3 == null) {
                            npcSay += "|4|" + dtl_1.template.name + "\n";
                        } else if (dtl_2 != null && dtl_3 == null) {
                            npcSay += "|4|" + dtl_1.template.name + "\n" + dtl_2.template.name + "\n";
                        } else {
                            npcSay += "|4|" + dtl_1.template.name + "\n" + dtl_2.template.name + "\n" + dtl_3.template.name + "\n";
                        }
                        npcSay += "|2|Thành Ngọc Kích hoạt\n|7|Tỉ lệ thành công " + (dtl_2 != null ? (dtl_3 != null ? "60%" : "30%") : "10%")
                                + "\n|6|Đang phân tách " + sum + " món\n|2|Cần "
                                + (dtl_2 != null ? (dtl_3 != null ? Util.formatNumber(COST_PHAN_RA_DO_TL_3) : Util.formatNumber(COST_PHAN_RA_DO_TL_2)) : Util.formatNumber(COST_PHAN_RA_DO_TL_1))
                                + " vàng";
                        if (player.inventory.gold >= (dtl_2 != null ? (dtl_3 != null ? COST_PHAN_RA_DO_TL_3 : COST_PHAN_RA_DO_TL_2) : COST_PHAN_RA_DO_TL_1)) {
                            this.meoKarin.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                    "Nâng cấp", "Đóng");
                        } else {
                            this.meoKarin.createOtherMenu(player, ConstNpc.IGNORE_MENU, npcSay, "Còn thiếu\n"
                                    + Util.formatNumber(player.inventory.gold - (dtl_2 != null ? (dtl_3 != null ? COST_PHAN_RA_DO_TL_3 : COST_PHAN_RA_DO_TL_2) : COST_PHAN_RA_DO_TL_1)) + " vàng");
                        }
                    } else {
                        this.meoKarin.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ta cần ít nhất 1 món đồ Thần của ngươi để Phân rã", "Đóng");
                    }
                }
                break;

            case XU_THONG_QUAN:
                if (player.combineNew.itemsCombine.size() == 5) {
                    Item item1 = null, item2 = null, item3 = null, item4 = null, item5 = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isNotNullItem()) {
                            if (item.template.id >= 650 && item.template.id <= 662) { // Full set Huy Diet
                                if (item1 == null) {
                                    item1 = item;
                                } else if (item2 == null && item.template.id != item1.template.id) {
                                    item2 = item;
                                } else if (item3 == null && item.template.id != item1.template.id
                                        && item.template.id != item2.template.id) {
                                    item3 = item;
                                } else if (item4 == null && item.template.id != item1.template.id
                                        && item.template.id != item2.template.id && item.template.id != item3.template.id) {
                                    item4 = item;
                                } else if (item5 == null && item.template.id != item1.template.id
                                        && item.template.id != item2.template.id && item.template.id != item3.template.id && item.template.id != item4.template.id) {
                                    item5 = item;
                                }
                            }
                        }
                    }
                    if (item5 != null) {
                        String npcSay = "|6|Ngươi đã chắc muốn\n|2|Thành Xu Thông quan";
                        this.meoKarin.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                "Nâng cấp", "Đóng");
                    } else {
                        this.meoKarin.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ngươi phải mang đầy đủ đồ ta cần\nĐến đây ta mới đưa xu cho ngươi",
                                "Đóng");
                    }
                } else {
                    Service.gI().sendThongBao(player, "Không đủ vật phẩm để thực hiện");
                }
                break;

            case DOI_DIEM:
                if (player.combineNew.itemsCombine.isEmpty()) {
                    this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con hãy đưa cho ta thức ăn", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 1) {
                    Item item = player.combineNew.itemsCombine.get(0);
                    if (item.isNotNullItem() && item.template.id >= 663 && item.template.id <= 667) {
                        if (item.quantity < 99) {
                            this.whis_64.npcChat(player, "Không đủ thức ăn\nHãy đi kiếm thêm!");
                            return;
                        }
                        String npcSay = "|2|Sau khi phân rã vật phẩm\n|7|"
                                + "Bạn sẽ nhận được : 1 điểm\n"
                                + (500000000 > player.inventory.gold ? "|7|" : "|1|")
                                + "Cần " + Util.formatNumber(500000000) + " vàng";
                        if (player.inventory.gold < 500000000) {
                            this.whis_64.npcChat(player, "Con không đủ tiền rồi!!");
                            return;
                        }
                        this.whis_64.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                npcSay, "Thức Ăn", "Từ chối");
                    } else {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Không phải là thức ăn\nHãy mang thức ăn đến cho ta!", "Đóng");
                    }
                } else {
                    this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con hãy đưa cho ta thức ăn", "Đóng");
                }
                break;

            case NANG_CAP_DO_TS:
                if (player.combineNew.itemsCombine.isEmpty()) {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "1 công thức, 1 đá nâng cấp, 999 mảnh thiên sứ, để nâng cấp", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 4) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isCongThucVip()).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu Công Thức", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaNangCap()).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ hủy diệt", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 999).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu mảnh thiên sứ", "Đóng");
                        return;
                    }

                    String npcSay = "|2|Con có muốn đổi các món nguyên liệu ?\n|7|"
                            + "Và nhận được " + player.combineNew.itemsCombine.stream().filter(Item::isManhTS).findFirst().get().typeNameManh() + " thiên sứ tương ứng\n"
                            + "|1|Cần " + Util.formatNumber(COST) + " vàng";

                    if (player.inventory.gold < COST) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con không đủ tiền rồi!!", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.MENU_NANG_CAP_DO_TS,
                            npcSay, "Nâng cấp\n" + Util.formatNumber(COST) + " vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cất đi con ta không thèm", "Đóng");
                        return;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;

            case NANG_CAP_SKH_VIP:
                if (player.combineNew.itemsCombine.size() == 0) {
                    this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy đưa ta 1 món thiên sứ, 1 món hủy diệt và 2 món SKH ngẫu nhiên", "Đóng");
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 4) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTS()).count() < 1) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ thiên sứ", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDHD()).count() < 1) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ hủy diệt", "Đóng");
                        return;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).count() < 2) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ kích hoạt ", "Đóng");
                        return;
                    }
                    String npcSay = "|2|Con có muốn đổi các món nguyên liệu ?\n|7|"
                            + "Và nhận được " + player.combineNew.itemsCombine.stream().filter(Item::isDTS).findFirst().get().typeName() + " kích hoạt VIP tương ứng\n"
                            + "|1|Cần " + Util.numberToMoney(COST) + " vàng";

                    if (player.inventory.gold < COST) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hết tiền rồi\nẢo ít thôi con", "Đóng");
                        return;
                    }
                    this.whis_64.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                            npcSay, "Nâng cấp\n" + Util.numberToMoney(COST) + " vàng", "Từ chối");
                } else {
                    if (player.combineNew.itemsCombine.size() > 4) {
                        this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Nguyên liệu không phù hợp", "Đóng");
                        return;
                    }
                    this.whis_64.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Còn thiếu nguyên liệu để nâng cấp hãy quay lại sau", "Đóng");
                }
                break;
            case EP_AN_TRANG_BI:
                if (player.combineNew.itemsCombine.size() == 3) {
                    Item cailoz = null;
                    Item concak = null;
                    Item thoivang = null;
                    for (Item item : player.combineNew.itemsCombine) {
                        if (item.isSKH() || item.isTrangBiAn()) {
                            cailoz = item;
                        } else if (item.template.id == 674) {
                            concak = item;
                        } else if (item.template.id == 457) {
                            thoivang = item;
                        }
                    }
                    if (cailoz != null && concak != null && thoivang != null && concak.quantity >= 25) {

                        player.combineNew.goldCombine = 50000;
                        player.combineNew.gemCombine = 0;
                        player.combineNew.ratioCombine = 50;

                        String npcSay = "Trang Bị" + "\n" + player.combineNew.ratioCombine + "%\n5000 Hồng Ngọc";
                        for (Item.ItemOption io : cailoz.itemOptions) {
                            if (player.combineNew.goldCombine <= player.inventory.ruby) {
                                baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE, npcSay,
                                        "NÂNG CẤP!!!!");
                            } else {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        "Cần 1 trang bị cá5;i đéo j cũng được, 50000 hồng ngọc, 25 đá ngũ sắc và 1 thỏi vàng", "Đóng");
                            }
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Cần 1 trang bị kích hoạt hoặc ấn, 50000 hồng ngọc, 25 đá ngũ sắc và 1 thỏi vàng", "Đóng");
                    }
                } else {
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                            "Cần 1 trang bị kích hoạt hoặc ấn, 50000 hồng ngọc, 25 đá ngũ sắc và 1 thỏi vàng", "Đóng");
                }
                break;
            case NGUYET_AN:
                if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ nâng cấp", "Đóng");
                        break;
                    }
                    if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đá nâng cấp", "Đóng");
                        break;
                    }
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() < 1) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Thiếu đồ nâng cấp", "Đóng");
                        break;
                    }
                    Item itemDo = null;
                    Item itemDNC = null;
                    Item itemDBV = null;
                    for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                        if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                                itemDBV = player.combineNew.itemsCombine.get(j);
                                continue;
                            }
                            if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                                itemDo = player.combineNew.itemsCombine.get(j);
                            } else {
                                itemDNC = player.combineNew.itemsCombine.get(j);
                            }
                        }
                    }
                    if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                        int level = 0;
                        for (Item.ItemOption io : itemDo.itemOptions) {
                            if (io.optionTemplate.id >= 34 && io.optionTemplate.id <= 36) {
                                level = io.param;
                                break;
                            }
                        }
                        if (level < 1) {
                            player.combineNew.goldCombine = 50000;
                            player.combineNew.ratioCombine = 50;
                            player.combineNew.countDaNangCap = 99;
                            player.combineNew.countDaBaoVe = (short) getCountDaBaoVe(level);
                            String npcSay = "|2|Hiện tại " + itemDo.template.name + " (+" + level + ")\n|0|";
                            for (Item.ItemOption io : itemDo.itemOptions) {
                                if (io.optionTemplate.id != 34 && io.optionTemplate.id != 36 && io.optionTemplate.id != 35 && io.optionTemplate.id != 127 && io.optionTemplate.id != 128 && io.optionTemplate.id != 129 && io.optionTemplate.id != 130 && io.optionTemplate.id != 131 && io.optionTemplate.id != 132 && io.optionTemplate.id != 133 && io.optionTemplate.id != 134 && io.optionTemplate.id != 135) {
                                    npcSay += io.getOptionString() + "\n";
                                }
                            }
//                            String option = null;
//                            int param = 0;
//                            for (Item.ItemOption io : itemDo.itemOptions) {
//                                if (io.optionTemplate.id == 47
//                                        || io.optionTemplate.id == 6
//                                        || io.optionTemplate.id == 0
//                                        || io.optionTemplate.id == 7
//                                        || io.optionTemplate.id == 14
//                                        || io.optionTemplate.id == 22
//                                        || io.optionTemplate.id == 23) {
//                                    option = io.optionTemplate.name;
//                                    param = io.param + (io.param * 0 / 100);
//                                    break;
//                                }
//                            }

                            npcSay += "|2|Nguyệt Ấn"
                                    + "\n|7|Tỉ lệ thành công: " + player.combineNew.ratioCombine + "%\n"
                                    + (player.combineNew.countDaNangCap > itemDNC.quantity ? "|7|" : "|1|")
                                    + "Cần " + player.combineNew.countDaNangCap + " " + itemDNC.template.name
                                    + "\n" + (player.combineNew.goldCombine > player.inventory.ruby ? "|7|" : "|1|")
                                    + "Cần " + Util.formatNumber(50000) + " Hòng Ngọc";
//                            for (Item.ItemOption io : itemDo.itemOptions) {
                            String daNPC = player.combineNew.itemsCombine.size() == 3 && itemDBV != null ? String.format("\nCần tốn %s đá bảo vệ", player.combineNew.countDaBaoVe) : "";
                            if ((level == 2 || level == 4 || level == 6) && !(player.combineNew.itemsCombine.size() == 3 && itemDBV != null)) {
                                npcSay += "\nNếu thất bại sẽ rớt xuống (+" + (level - 1) + ")";
                                break;
                            }
//                            for (Item.ItemOption io : itemDo.itemOptions) {
//                            else if (io.optionTemplate.id >= 35 && io.optionTemplate.id <= 36) {
//                                    npcSay += "|2|Tinh Ấn"
//                                    + "\n|7|Đã Ép Ấn";
//                                    break;
//                                }
                            if (player.combineNew.countDaNangCap > itemDNC.quantity) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaNangCap - itemDNC.quantity) + " " + itemDNC.template.name);
                            } else if (player.combineNew.goldCombine > player.inventory.ruby) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + Util.formatNumber((25000 - player.inventory.ruby)) + " Hòng Ngọc");
                            } else if (player.combineNew.itemsCombine.size() == 3 && Objects.nonNull(itemDBV) && itemDBV.quantity < player.combineNew.countDaBaoVe) {
                                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                        npcSay, "Còn thiếu\n" + (player.combineNew.countDaBaoVe - itemDBV.quantity) + " đá bảo vệ");
                            } else {
                                this.baHatMit.createOtherMenu(player, ConstNpc.MENU_START_COMBINE,
                                        npcSay, "Nâng cấp\n" + Util.formatNumber(player.combineNew.goldCombine) + " Hồng Ngọc" + daNPC, "Từ chối");
                            }
                        } else {
                            this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Trang bị này như cc tao đéo ép", "Đóng");
                        }
                    } else {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị và 1 loại đá nâng cấp", "Đóng");
                    }
                } else {
                    if (player.combineNew.itemsCombine.size() > 3) {
                        this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Cất đi con ta không thèm", "Đóng");
                        break;
                    }
                    this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị và 1 loại đá nâng cấp", "Đóng");
                }
                break;
        }
    }

    /**
     * Bắt đầu đập đồ - điều hướng từng loại đập đồ
     *
     * @param player
     */
    public void startCombine(Player player, int... n) {
        int num = 0;
        if (n.length > 0) {
            num = n[0];
        }
        switch (player.combineNew.typeCombine) {
            case EP_SAO_TRANG_BI:
                EpSaoTrangBi.epSaoTrangBi(player);
                break;
            case PHA_LE_HOA_TRANG_BI:
                PhaLeHoaTrangBi.phaLeHoa(player, num);
                break;
            case NANG_CAP_SAO_PHA_LE:
                NangCapSaoPhaLe.nangCapSaoPhaLe(player);
                break;
            case CUONG_HOA_LO_SAO_PHA_LE:
                CuongHoaLoSaoPhaLe.cuongHoaLoSaoPhaLe(player);
                break;
            case TAO_DA_HEMATITE:
                TaoDaHematite.taoDaHematite(player);
                break;
            case NHAP_NGOC_RONG:
                nhapNgocRong(player);
                break;
            case PHAN_RA_DO_THAN_LINH:
                phanradothanlinh(player);
                break;
            case HUY_HIEU_HUY_DIET:
                huyHieuHuyDiet(player);
                break;
            case XU_THONG_QUAN:
                xuThongQuan(player);
                break;
            case NANG_CAP_DO_TS:
                openDTS(player);
                break;
            case CHE_TAO_TRANG_BI_TS:
                cheTaoDoTS(player);
                break;
            case NANG_CAP_SKH_VIP:
                openSKHVIP(player);
                break;
            case NANG_CAP_VAT_PHAM:
                nangCapVatPham(player);
                break;
            case NANG_CAP_BONG_TAI:
                nangCapBongTai(player);
                break;
            case MO_CHI_SO_BONG_TAI:
                moChiSoBongTai(player);
                break;
            case LUYEN_HOA_CHIEN_LINH:
                //
                break;
            case EP_AN_TRANG_BI:
                epantrangbi(player);
                break;
            case NGUYET_AN:
                nguyetan(player);
                break;
            case DOI_DIEM:
                doidiem(player);
                break;
            case NANG_CAP_KICH_HOAT:
                NangCapKichHoat.startCombine(player);
                break;
            //Sách Tuyệt Kỹ
            case GIAM_DINH_SACH:
                GiamDinhSach.giamDinhSach(player);
                break;
            case TAY_SACH:
                TaySach.taySach(player);
                break;
            case NANG_CAP_SACH_TUYET_KY:
                NangCapSachTuyetKy.nangCapSachTuyetKy(player);
                break;
            case HOI_PHUC_SACH:
                HoiPhucSach.hoiPhucSach(player);
                break;
            case PHAN_RA_SACH:
                PhanRaSach.phanRaSach(player);
                break;
            case NANG_CAP_CHAN_MENH:
                nangCapChanMenh(player);
                break;
            //khaile add
            case CHE_TAO_VO_CUC_TU_TAI:
                combineVoCucTuTai(player);
                break;
            case CHE_TAO_NGOAI_TRANG_VO_CUC_TU_TAI:
                doiNgoaiTrangVoCuc(player);
                break;
            case CHE_TAO_DAN_DUOC_LUYEN_KHI:
                chetaoHoangCucDan(player);
                break;
            case CHE_TAO_TRUC_CO_DAN:
                chetaoTrucCoDan(player);
                break;
            case CHE_TAO_TRUC_CO_SO:
                chetaoTrucCo_So(player);
                break;
            case CHE_TAO_TRUC_CO_TRUNG:
                chetaoTrucCo_Trung(player);
                break;
            case CHE_TAO_TRUC_CO_HAU:
                chetaoTrucCo_Hau(player);
                break;
            case CHE_TAO_THIEN_MA:
                combineThienMa(player);
                break;
            //end khaile add
        }

        player.iDMark.setIndexMenu(ConstNpc.IGNORE_MENU);
        player.combineNew.clearParamCombine();
        player.combineNew.lastTimeCombine = System.currentTimeMillis();

    }

    private boolean issachTuyetKy(Item item) {
        if (item != null && item.isNotNullItem()) {
            if (item.template.type == 77) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean checkHaveOption(Item item, int viTriOption, int idOption) {
        if (item != null && item.isNotNullItem()) {
            if (item.itemOptions.get(viTriOption).optionTemplate.id == idOption) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
private void combineVoCucTuTai(Player player) {
        // Tìm mảnh trang bị trong nguyên liệu
        Item manhTrangBi = player.combineNew.itemsCombine.stream()
                .filter(item -> item.template.id >= 1688 && item.template.id <= 1692)
                .findFirst()
                .orElse(null);

        if (manhTrangBi == null) {
            Service.gI().sendThongBao(player, "Lỗi hệ thống!");
            return;
        }

        // Xác định loại trang bị
        short itemId = getIdTrangBi(manhTrangBi.template.id);
        List<ItemOption> options = createItemVoCucTuTaiOptions(manhTrangBi.template.id);

        // Thực hiện chế tạo
        createTuTaiItem(player, itemId, options);
        player.combineNew.updateItemsCombine(player.inventory.itemsBag);
        reOpenItemCombine(player);
    }

    private List<ItemOption> createItemVoCucTuTaiOptions(int manhId) {
        switch (manhId) {
            case 1688: // Áo
                return Arrays.asList(
                        new ItemOption(199, Util.nextInt(2000, 4000)),
                        new ItemOption(94, Util.nextInt(100, 200)),
                        new ItemOption(192, 0),
                        new ItemOption(189, 100),
                        new ItemOption(194, 25),
                        new ItemOption(191, 550)
                );
            case 1689: // Quần
                return Arrays.asList(
                        new ItemOption(196, Util.nextInt(5000, 10000)),
                        new ItemOption(77, Util.nextInt(300, 400)),
                        new ItemOption(192, 0),
                        new ItemOption(189, 100),
                        new ItemOption(194, 25),
                        new ItemOption(191, 550)
                );
            case 1690: // Găng
                return Arrays.asList(
                        new ItemOption(198, Util.nextInt(500, 1000)),
                        new ItemOption(50, Util.nextInt(100, 200)),
                        new ItemOption(192, 0),
                        new ItemOption(189, 100),
                        new ItemOption(194, 25),
                        new ItemOption(191, 550)
                );
            case 1691: // Giày
                return Arrays.asList(
                        new ItemOption(197, Util.nextInt(5000, 10000)),
                        new ItemOption(103, Util.nextInt(300, 400)),
                        new ItemOption(192, 0),
                        new ItemOption(189, 100),
                        new ItemOption(194, 25),
                        new ItemOption(191, 550)
                );
            case 1692: // Trang sức
                return Arrays.asList(
                        new ItemOption(14, Util.nextInt(175, 275)),
                        new ItemOption(5, Util.nextInt(50, 100)),
                        new ItemOption(192, 0),
                        new ItemOption(189, 100),
                        new ItemOption(194, 25),
                        new ItemOption(191, 550)
                );
            default:
                return Collections.emptyList();
        }
    }

    private void doiNgoaiTrangVoCuc(Player player) {
        sendEffectSuccessCombine(player);
        createNgoaiTrangVoCuc(player, (short) 1687, Arrays.asList(
                new ItemOption(198, 500),//sd
                new ItemOption(199, 500),//giáp
                new ItemOption(196, 2000),//hp
                new ItemOption(197, 2000),//ki

                new ItemOption(94, 50),
                new ItemOption(50, 50),
                new ItemOption(77, 350),
                new ItemOption(103, 350)
        ));
        player.combineNew.clearItemCombine();
        reOpenItemCombine(player);
    }

    private void combineThienMa(Player player) {
        // Tìm mảnh trang bị trong nguyên liệu
        Item manhTrangBi = player.combineNew.itemsCombine.stream()
                .filter(item -> item.template.id >= 1688 && item.template.id <= 1692)
                .findFirst()
                .orElse(null);

        if (manhTrangBi == null) {
            Service.gI().sendThongBao(player, "Lỗi hệ thống!");
            return;
        }

        // Xác định loại trang bị
        short itemId = getIdTrangBiThienMaTuManhTrangBi(manhTrangBi.template.id);
        List<ItemOption> options = createItemThienMaOptions(manhTrangBi.template.id);

        // Thực hiện chế tạo
        createThienMaItem(player, itemId, options);
        player.combineNew.updateItemsCombine(player.inventory.itemsBag);
        reOpenItemCombine(player);
    }

    private List<ItemOption> createItemThienMaOptions(int manhId) {
        switch (manhId) {
            case 1688: // Áo
                return Arrays.asList(
                        new ItemOption(199, Util.nextInt(20, 40)),
                        new ItemOption(94, Util.nextInt(100, 200)),
                        new ItemOption(191, 100),
                        new ItemOption(190, 0)
                );
            case 1689: // Quần
                return Arrays.asList(
                        new ItemOption(196, Util.nextInt(50, 100)),
                        new ItemOption(77, Util.nextInt(300, 400)),
                        new ItemOption(191, 100),
                        new ItemOption(190, 0)
                );
            case 1690: // Găng
                return Arrays.asList(
                        new ItemOption(198, Util.nextInt(5, 10)),
                        new ItemOption(50, Util.nextInt(100, 200)),
                        new ItemOption(191, 100),
                        new ItemOption(190, 0)
                );
            case 1691: // Giày
                return Arrays.asList(
                        new ItemOption(197, Util.nextInt(50, 100)),
                        new ItemOption(103, Util.nextInt(300, 400)),
                        new ItemOption(191, 100),
                        new ItemOption(190, 0)
                );
            case 1692: // Trang sức
                return Arrays.asList(
                        new ItemOption(14, Util.nextInt(25, 50)),//chi mang
                        new ItemOption(5, Util.nextInt(5, 20)),//sdcm
                        new ItemOption(191, 100),
                        new ItemOption(190, 0)
                );
            default:
                return Collections.emptyList();
        }
    }

    private void chetaoHoangCucDan(Player player) {
        sendEffectSuccessCombine(player);
        createHoangCucDan(player, (short) 1668);
        player.combineNew.updateItemsCombine(player.inventory.itemsBag);
        reOpenItemCombine(player);
    }

    private void chetaoTrucCoDan(Player player) {
        createTrucCoDan(player, (short) 1667);
        player.combineNew.updateItemsCombine(player.inventory.itemsBag);
        reOpenItemCombine(player);
    }

    private void chetaoTrucCo_So(Player player) {
        createTrucCoDan_SoKy(player, (short) 1664); // long tuy dan
        player.combineNew.updateItemsCombine(player.inventory.itemsBag);
        reOpenItemCombine(player);
    }

    private void chetaoTrucCo_Trung(Player player) {
        createTrucCoDan_TrungKy(player, (short) 1665); // chan nguyen dan
        player.combineNew.updateItemsCombine(player.inventory.itemsBag);
        reOpenItemCombine(player);
    }

    private void chetaoTrucCo_Hau(Player player) {
        createTrucCoDan_HauKy(player, (short) 1666); // ngu hanh ngung dan
        player.combineNew.updateItemsCombine(player.inventory.itemsBag);
        reOpenItemCombine(player);
    }

    //end khaile add
    private void nangCapChanMenh(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            float tiLe = player.combineNew.TileNangcap;
            int diem = player.combineNew.DiemNangcap;
            if (player.event.getEventPointBoss() < diem) {
                Service.gI().sendThongBao(player, "Không đủ Điểm Săn Boss để thực hiện");
                return;
            }
            long gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }

            Item chanmenh = null;
            Item manhHon = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id >= 1185 && item.template.id <= 1193) {
                    chanmenh = item;
                } else if (item.template.id == 1318) {
                    manhHon = item;
                }
            }
            int star = 0;
            if (chanmenh != null) {
                for (Item.ItemOption io : chanmenh.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        star = Math.max(star, io.param);
                    }
                }
            }

            if (chanmenh != null && manhHon != null && manhHon.quantity > player.combineNew.DaNangcap) {
                if (star >= 9) {
                    Service.gI().sendThongBao(player, "Đã max cấp");
                }

                if (Util.isTrue(tiLe, 100)) {
                    // Tăng option nếu đã có
                    boolean hasOption72 = false;
                    boolean hasOption50 = false;
                    boolean hasOption77 = false;
                    boolean hasOption103 = false;

                    for (Item.ItemOption io : chanmenh.itemOptions) {
                        switch (io.optionTemplate.id) {
                            case 72:
                                io.param = Math.min(io.param + 1, 9);
                                hasOption72 = true;
                                break;
                            case 50:
                                io.param = Math.min(io.param + 3, 24);
                                hasOption50 = true;
                                break;
                            case 77:
                                io.param = Math.min(io.param + 5, 40);
                                hasOption77 = true;
                                break;
                            case 103:
                                io.param = Math.min(io.param + 5, 40);
                                hasOption103 = true;
                                break;
                        }
                    }

                    // Thêm option nếu chưa có
                    if (!hasOption50) {
                        chanmenh.itemOptions.add(new ItemOption(50, 8));
                    }
                    if (!hasOption77) {
                        chanmenh.itemOptions.add(new ItemOption(77, 8));
                    }
                    if (!hasOption103) {
                        chanmenh.itemOptions.add(new ItemOption(103, 8));
                    }
                    if (!hasOption72) {
                        chanmenh.itemOptions.add(new ItemOption(72, 2));
                    }
                    // Cuối cùng mới tăng cấp template
                    chanmenh.template = ItemService.gI().getTemplate((short) (chanmenh.template.id + 1));

                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                player.event.subEventPointBoss(diem);
                InventoryServiceNew.gI().subQuantityItemsBag(player, manhHon, player.combineNew.DaNangcap);
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void giamDinhSach(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {

            Item sachTuyetKy = null;
            Item buaGiamDinh = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (issachTuyetKy(item)) {
                    sachTuyetKy = item;
                } else if (item.template.id == 1270) {
                    buaGiamDinh = item;
                }
            }
            if (sachTuyetKy != null && buaGiamDinh != null) {
                Item sachTuyetKy_2 = ItemService.gI().createNewItem((short) sachTuyetKy.template.id);
                if (checkHaveOption(sachTuyetKy, 0, 217)) {
                    int tyle = new Random().nextInt(10);
                    if (tyle >= 0 && tyle <= 33) {
                        sachTuyetKy_2.itemOptions.add(new ItemOption(50, new Util().nextInt(5, 10)));
                    } else if (tyle > 33 && tyle <= 66) {
                        sachTuyetKy_2.itemOptions.add(new ItemOption(77, new Util().nextInt(10, 15)));
                    } else {
                        sachTuyetKy_2.itemOptions.add(new ItemOption(103, new Util().nextInt(10, 15)));
                    }
                    for (int i = 1; i < sachTuyetKy.itemOptions.size(); i++) {
                        sachTuyetKy_2.itemOptions.add(new ItemOption(sachTuyetKy.itemOptions.get(i).optionTemplate.id, sachTuyetKy.itemOptions.get(i).param));
                    }
                    sendEffectSuccessCombine(player);
                    InventoryServiceNew.gI().addItemBag(player, sachTuyetKy_2);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, sachTuyetKy, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, buaGiamDinh, 1);
                    InventoryServiceNew.gI().sendItemBags(player);
                    reOpenItemCombine(player);
                } else {
                    Service.getInstance().sendThongBao(player, "Còn cái nịt mà giám");
                    return;
                }
            }
        }
    }

    private void nangCapSachTuyetKy(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {

            Item sachTuyetKy = null;
            Item kimBamGiay = null;

            for (Item item : player.combineNew.itemsCombine) {
                if (issachTuyetKy(item)) {
                    sachTuyetKy = item;
                } else if (item.template.id == 1269) {
                    kimBamGiay = item;
                }
            }
            Item sachTuyetKy_2 = ItemService.gI().createNewItem((short) ((short) sachTuyetKy.template.id + 1));
            if (sachTuyetKy != null && kimBamGiay != null) {
                if (kimBamGiay.quantity < 10) {
                    Service.getInstance().sendThongBao(player, "Không đủ Kìm bấm giấy mà đòi nâng cấp");
                    return;
                }
                if (checkHaveOption(sachTuyetKy, 0, 217)) {
                    Service.getInstance().sendThongBao(player, "Chưa giám định mà đòi nâng cấp");
                    return;
                }
                for (int i = 0; i < sachTuyetKy.itemOptions.size(); i++) {
                    sachTuyetKy_2.itemOptions.add(new ItemOption(sachTuyetKy.itemOptions.get(i).optionTemplate.id, sachTuyetKy.itemOptions.get(i).param));
                }
                sendEffectSuccessCombine(player);
                InventoryServiceNew.gI().addItemBag(player, sachTuyetKy_2);
                InventoryServiceNew.gI().subQuantityItemsBag(player, sachTuyetKy, 1);
                InventoryServiceNew.gI().subQuantityItemsBag(player, kimBamGiay, 10);
                InventoryServiceNew.gI().sendItemBags(player);
                reOpenItemCombine(player);

            }
        }
    }

    private void phucHoiSach(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item cuonSachCu = InventoryServiceNew.gI().findItemBag(player, (short) 1392);
            int goldPhanra = 10_000_000;
            Item sachTuyetKy = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (issachTuyetKy(item)) {
                    sachTuyetKy = item;
                }
            }
            if (sachTuyetKy != null) {
                int doBen = 0;
                ItemOption optionLevel = null;
                for (ItemOption io : sachTuyetKy.itemOptions) {
                    if (io.optionTemplate.id == 215) {
                        doBen = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (cuonSachCu == null) {
                    Service.getInstance().sendThongBaoOK(player, "Cần sách tuyệt kỹ và 10 cuốn sách cũ");
                    return;
                }
                if (cuonSachCu.quantity < 10) {
                    Service.getInstance().sendThongBaoOK(player, "Cần sách tuyệt kỹ và 10 cuốn sách cũ");
                    return;
                }
                if (player.inventory.gold < goldPhanra) {
                    Service.getInstance().sendThongBao(player, "Không có tiền mà đòi phục hồi à");
                    return;
                }
                if (doBen != 1000) {
                    for (int i = 0; i < sachTuyetKy.itemOptions.size(); i++) {
                        if (sachTuyetKy.itemOptions.get(i).optionTemplate.id == 215) {
                            sachTuyetKy.itemOptions.get(i).param = 1000;
                            break;
                        }
                    }
                    player.inventory.gold -= 10_000_000;
                    InventoryServiceNew.gI().subQuantityItemsBag(player, cuonSachCu, 10);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    sendEffectSuccessCombine(player);
                    reOpenItemCombine(player);
                } else {
                    Service.getInstance().sendThongBao(player, "Còn dùng được phục hồi ăn cứt à");
                    return;
                }
            }
        }
    }

    private void phanRaSach(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item cuonSachCu = ItemService.gI().createNewItem((short) 1392, 5);
            int goldPhanra = 10_000_000;
            Item sachTuyetKy = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (issachTuyetKy(item)) {
                    sachTuyetKy = item;
                }
            }
            if (sachTuyetKy != null) {
                int luotTay = 0;
                ItemOption optionLevel = null;
                for (ItemOption io : sachTuyetKy.itemOptions) {
                    if (io.optionTemplate.id == 214) {
                        luotTay = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (player.inventory.gold < goldPhanra) {
                    Service.getInstance().sendThongBao(player, "Không có tiền mà đòi phân rã à");
                    return;
                }
                if (luotTay == 0) {

                    player.inventory.gold -= goldPhanra;
                    InventoryServiceNew.gI().subQuantityItemsBag(player, sachTuyetKy, 1);
                    InventoryServiceNew.gI().addItemBag(player, cuonSachCu);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    sendEffectSuccessCombine(player);
                    reOpenItemCombine(player);

                } else {
                    Service.getInstance().sendThongBao(player, "Còn dùng được phân rã ăn cứt à");
                    return;
                }
            }
        }
    }

    private void taySach(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item sachTuyetKy = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (issachTuyetKy(item)) {
                    sachTuyetKy = item;
                }
            }
            if (sachTuyetKy != null) {
                int luotTay = 0;
                ItemOption optionLevel = null;
                for (ItemOption io : sachTuyetKy.itemOptions) {
                    if (io.optionTemplate.id == 214) {
                        luotTay = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (luotTay == 0) {
                    Service.getInstance().sendThongBao(player, "Còn cái nịt mà tẩy");
                    return;
                }
                Item sachTuyetKy_2 = ItemService.gI().createNewItem((short) sachTuyetKy.template.id);
                if (checkHaveOption(sachTuyetKy, 0, 217)) {
                    Service.getInstance().sendThongBao(player, "Còn cái nịt mà tẩy");
                    return;
                }
                int tyle = new Random().nextInt(10);
                for (int i = 1; i < sachTuyetKy.itemOptions.size(); i++) {
                    if (sachTuyetKy.itemOptions.get(i).optionTemplate.id == 214) {
                        sachTuyetKy.itemOptions.get(i).param -= 1;
                    }
                }
                sachTuyetKy_2.itemOptions.add(new ItemOption(217, 0));
                for (int i = 1; i < sachTuyetKy.itemOptions.size(); i++) {
                    sachTuyetKy_2.itemOptions.add(new ItemOption(sachTuyetKy.itemOptions.get(i).optionTemplate.id, sachTuyetKy.itemOptions.get(i).param));
                }
                sendEffectSuccessCombine(player);
                InventoryServiceNew.gI().addItemBag(player, sachTuyetKy_2);
                InventoryServiceNew.gI().subQuantityItemsBag(player, sachTuyetKy, 1);
                InventoryServiceNew.gI().sendItemBags(player);
                reOpenItemCombine(player);
            }
        }
    }

    public void GetTrangBiKichHoathuydiet(Player player, int id) {
        Item item = ItemService.gI().createNewItem((short) id);
        int[][] optionNormal = {{127, 128}, {130, 132}, {133, 135}};
        int[][] paramNormal = {{139, 140}, {142, 144}, {136, 138}};
        int[][] optionVIP = {{129}, {131}, {134}};
        int[][] paramVIP = {{141}, {143}, {137}};
        int random = Util.nextInt(optionNormal.length);
        int randomSkh = Util.nextInt(100);
        if (item.template.type == 0) {
            item.itemOptions.add(new ItemOption(47, Util.nextInt(1500, 2000)));
        }
        if (item.template.type == 1) {
            item.itemOptions.add(new ItemOption(22, Util.nextInt(100, 150)));
        }
        if (item.template.type == 2) {
            item.itemOptions.add(new ItemOption(0, Util.nextInt(9000, 11000)));
        }
        if (item.template.type == 3) {
            item.itemOptions.add(new ItemOption(23, Util.nextInt(90, 150)));
        }
        if (item.template.type == 4) {
            item.itemOptions.add(new ItemOption(14, Util.nextInt(15, 20)));
        }
        if (randomSkh <= 20) {//tile ra do kich hoat
            if (randomSkh <= 5) { // tile ra option vip
                item.itemOptions.add(new ItemOption(optionVIP[player.gender][0], 1));
                item.itemOptions.add(new ItemOption(paramVIP[player.gender][0], 1));
                item.itemOptions.add(new ItemOption(30, 0));
            } else {// 
                item.itemOptions.add(new ItemOption(optionNormal[player.gender][random], 1));
                item.itemOptions.add(new ItemOption(paramNormal[player.gender][random], 1));
                item.itemOptions.add(new ItemOption(30, 0));
            }
        }

        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void GetTrangBiKichHoatthiensu(Player player, int id) {
        Item item = ItemService.gI().createNewItem((short) id);
        int[][] optionNormal = {{127, 128}, {130, 132}, {133, 135}};
        int[][] paramNormal = {{139, 140}, {142, 144}, {136, 138}};
        int[][] optionVIP = {{129}, {131}, {134}};
        int[][] paramVIP = {{141}, {143}, {137}};
        int random = Util.nextInt(optionNormal.length);
        int randomSkh = Util.nextInt(100);
        if (item.template.type == 0) {
            item.itemOptions.add(new ItemOption(47, Util.nextInt(2000, 2500)));
        }
        if (item.template.type == 1) {
            item.itemOptions.add(new ItemOption(22, Util.nextInt(150, 200)));
        }
        if (item.template.type == 2) {
            item.itemOptions.add(new ItemOption(0, Util.nextInt(18000, 20000)));
        }
        if (item.template.type == 3) {
            item.itemOptions.add(new ItemOption(23, Util.nextInt(150, 200)));
        }
        if (item.template.type == 4) {
            item.itemOptions.add(new ItemOption(14, Util.nextInt(20, 25)));
        }
        if (randomSkh <= 20) {//tile ra do kich hoat
            if (randomSkh <= 5) { // tile ra option vip
                item.itemOptions.add(new ItemOption(optionVIP[player.gender][0], 1));
                item.itemOptions.add(new ItemOption(paramVIP[player.gender][0], 1));
                item.itemOptions.add(new ItemOption(30, 0));
            } else {// 
                item.itemOptions.add(new ItemOption(optionNormal[player.gender][random], 1));
                item.itemOptions.add(new ItemOption(paramNormal[player.gender][random], 1));
                item.itemOptions.add(new ItemOption(30, 0));
            }
        }

        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void khilv2(Player player, int id) {
        Item item = ItemService.gI().createNewItem((short) id);
        item.itemOptions.add(new ItemOption(50, 20));//sd
        item.itemOptions.add(new ItemOption(77, 20));//hp
        item.itemOptions.add(new ItemOption(103, 20));//ki
        item.itemOptions.add(new ItemOption(14, 20));//cm
        item.itemOptions.add(new ItemOption(5, 20));//sd cm
        item.itemOptions.add(new ItemOption(106, 0));
        item.itemOptions.add(new ItemOption(34, 0));
        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void khilv3(Player player, int id) {
        Item item = ItemService.gI().createNewItem((short) id);
        item.itemOptions.add(new ItemOption(50, 22));//sd
        item.itemOptions.add(new ItemOption(77, 22));//hp
        item.itemOptions.add(new ItemOption(103, 22));//ki
        item.itemOptions.add(new ItemOption(14, 22));//cm
        item.itemOptions.add(new ItemOption(5, 22));//sd cm
        item.itemOptions.add(new ItemOption(106, 0));
        item.itemOptions.add(new ItemOption(35, 0));
        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void khilv4(Player player, int id) {
        Item item = ItemService.gI().createNewItem((short) id);
        item.itemOptions.add(new ItemOption(50, 24));//sd
        item.itemOptions.add(new ItemOption(77, 24));//hp
        item.itemOptions.add(new ItemOption(103, 24));//ki
        item.itemOptions.add(new ItemOption(14, 24));//cm
        item.itemOptions.add(new ItemOption(5, 24));//sd cm
        item.itemOptions.add(new ItemOption(106, 0));
        item.itemOptions.add(new ItemOption(36, 0));
        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    public void khilv5(Player player, int id) {
        Item item = ItemService.gI().createNewItem((short) id);
        item.itemOptions.add(new ItemOption(50, 26));//sd
        item.itemOptions.add(new ItemOption(77, 26));//hp
        item.itemOptions.add(new ItemOption(103, 26));//ki
        item.itemOptions.add(new ItemOption(14, 26));//cm
        item.itemOptions.add(new ItemOption(5, 26));//sd cm
        item.itemOptions.add(new ItemOption(106, 0));
        item.itemOptions.add(new ItemOption(36, 0));
        InventoryServiceNew.gI().addItemBag(player, item);
        InventoryServiceNew.gI().sendItemBags(player);
    }

    private void doiKiemThan(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            Item keo = null, luoiKiem = null, chuoiKiem = null;
            for (Item it : player.combineNew.itemsCombine) {
                if (it.template.id == 2015) {
                    keo = it;
                } else if (it.template.id == 2016) {
                    chuoiKiem = it;
                } else if (it.template.id == 2017) {
                    luoiKiem = it;
                }
            }
            if (keo != null && keo.quantity >= 99 && luoiKiem != null && chuoiKiem != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2018);
                    item.itemOptions.add(new Item.ItemOption(50, Util.nextInt(9, 15)));
                    item.itemOptions.add(new Item.ItemOption(77, Util.nextInt(8, 15)));
                    item.itemOptions.add(new Item.ItemOption(103, Util.nextInt(8, 15)));
                    if (Util.isTrue(80, 100)) {
                        item.itemOptions.add(new Item.ItemOption(93, Util.nextInt(1, 15)));
                    }
                    InventoryServiceNew.gI().addItemBag(player, item);

                    InventoryServiceNew.gI().subQuantityItemsBag(player, keo, 99);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, luoiKiem, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, chuoiKiem, 1);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiChuoiKiem(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item manhNhua = player.combineNew.itemsCombine.get(0);
            if (manhNhua.template.id == 2014 && manhNhua.quantity >= 99) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2016);
                    InventoryServiceNew.gI().addItemBag(player, item);

                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhNhua, 99);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiLuoiKiem(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item manhSat = player.combineNew.itemsCombine.get(0);
            if (manhSat.template.id == 2013 && manhSat.quantity >= 99) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    sendEffectSuccessCombine(player);
                    Item item = ItemService.gI().createNewItem((short) 2017);
                    InventoryServiceNew.gI().addItemBag(player, item);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhSat, 99);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void doiManhKichHoat(Player player) {
        if (player.combineNew.itemsCombine.size() == 2 || player.combineNew.itemsCombine.size() == 3) {
            Item nr1s = null, doThan = null, buaBaoVe = null;
            for (Item it : player.combineNew.itemsCombine) {
                if (it.template.id == 14) {
                    nr1s = it;
                } else if (it.template.id == 2010) {
                    buaBaoVe = it;
                } else if (it.template.id >= 555 && it.template.id <= 567) {
                    doThan = it;
                }
            }

            if (nr1s != null && doThan != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0
                        && player.inventory.gold >= COST_DOI_MANH_KICH_HOAT) {
                    player.inventory.gold -= COST_DOI_MANH_KICH_HOAT;
                    int tiLe = buaBaoVe != null ? 100 : 50;
                    if (Util.isTrue(tiLe, 100)) {
                        sendEffectSuccessCombine(player);
                        Item item = ItemService.gI().createNewItem((short) 2009);
                        item.itemOptions.add(new Item.ItemOption(30, 0));
                        InventoryServiceNew.gI().addItemBag(player, item);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, nr1s, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, doThan, 1);
                    if (buaBaoVe != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, buaBaoVe, 1);
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            } else {
                this.baHatMit.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Hãy chọn 1 trang bị thần linh và 1 viên ngọc rồng 1 sao", "Đóng");
            }
        }
    }

    public void openDTS(Player player) {
        //check sl đồ tl, đồ hd
        // new update 2 mon huy diet + 1 mon than linh(skh theo style) +  5 manh bat ki
        if (player.combineNew.itemsCombine.size() != 4) {
            Service.gI().sendThongBao(player, "Thiếu đồ");
            return;
        }
        if (player.inventory.gold < COST) {
            Service.gI().sendThongBao(player, "Ảo ít thôi con...");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) < 1) {
            Service.gI().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
            return;
        }
        Item itemTL = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaNangCap()).findFirst().get();
        List<Item> itemHDs = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isCongThucVip()).collect(Collectors.toList());
        Item itemManh = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 999).findFirst().get();

        player.inventory.gold -= COST;
        sendEffectSuccessCombine(player);
        short[][] itemIds = {{1048, 1051, 1054, 1057, 1060}, {1049, 1052, 1055, 1058, 1061}, {1050, 1053, 1056, 1059, 1062}}; // thứ tự td - 0,nm - 1, xd - 2

        Item itemTS = ItemService.gI().DoThienSu(itemIds[player.gender > 2 ? player.gender : player.gender][itemManh.typeIdManh()], player.gender);
        InventoryServiceNew.gI().addItemBag(player, itemTS);

        InventoryServiceNew.gI().subQuantityItemsBag(player, itemTL, 1);
        InventoryServiceNew.gI().subQuantityItemsBag(player, itemManh, 999);
        itemHDs.forEach(item -> InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1));
        InventoryServiceNew.gI().sendItemBags(player);
        Service.gI().sendMoney(player);
        Service.gI().sendThongBao(player, "Bạn đã nhận được " + itemTS.template.name);
        player.combineNew.itemsCombine.clear();
        reOpenItemCombine(player);
    }

    public void cheTaoDoTS(Player player) {
        // Công thức vip + x99 Mảnh thiên sứ + đá nâng cấp + đá may mắn
        if (player.combineNew.itemsCombine.size() < 2 || player.combineNew.itemsCombine.size() > 5) {
            Service.getInstance().sendThongBao(player, "Nguyên liệu không phù hợp!!");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isCongThucVip() || item.isCongThucThuong()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu Công thức!!");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDHD()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu Đồ Hủy Diệt!!");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 99).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu Mảnh thiên sứ!!");
            return;
        }
        if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item
                -> item.isNotNullItem() && item.isDaNangCap()).count() != 1
                || player.combineNew.itemsCombine.size() == 4
                && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaNangCap()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu Đá nâng cấp!!");
            return;
        }
        if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item
                -> item.isNotNullItem() && item.isDaMayMan()).count() != 1
                || player.combineNew.itemsCombine.size() == 4
                && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDaMayMan()).count() != 1) {
            Service.getInstance().sendThongBao(player, "Thiếu Đá may mắn!!");
            return;
        }
        Item mTS = null, daNC = null, daMM = null, CtVip = null, CtThuong = null, dHD = null;
        for (Item item : player.combineNew.itemsCombine) {
            if (item.isNotNullItem()) {
                if (item.isManhTS()) {
                    mTS = item;
                } else if (item.isDHD()) {
                    dHD = item;
                } else if (item.isDaNangCap()) {
                    daNC = item;
                } else if (item.isDaMayMan()) {
                    daMM = item;
                } else if (item.isCongThucVip()) {
                    CtVip = item;
                } else if (item.isCongThucThuong()) {
                    CtThuong = item;
                }
            }
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (player.inventory.gold < 500_000_000) {
                Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            player.inventory.gold -= 500000000;

            int tilemacdinh = 15;
            int tileLucky = (CtVip == null ? 15 : 25);;
            if (daNC != null && daMM != null) {
                tilemacdinh += (daNC.template.id - 1073) * 5;
                tileLucky += (daMM.template.id - 1078) * 10;
            }

            if (Util.nextInt(0, 100) < tileLucky) {
                Item itemCtVip = player.combineNew.itemsCombine.stream()
                        .filter(item -> item.isNotNullItem() && (item.isCongThucVip() || item.isCongThucThuong()))
                        .findFirst().get();

                Item itemManh = player.combineNew.itemsCombine.stream()
                        .filter(item -> item.isNotNullItem() && item.isManhTS() && item.quantity >= 99)
                        .findFirst().get();

                // Tạo đồ thiên sứ
                short[][] itemIds = {{1048, 1051, 1054, 1057, 1060}, {1049, 1052, 1055, 1058, 1061}, {1050, 1053, 1056, 1059, 1062}};
                Item itemTS = ItemService.gI().DoThienSu(itemIds[itemCtVip.template.gender > 2 ? player.gender : itemCtVip.template.gender][itemManh.typeIdManh()], itemCtVip.template.gender);

                // Thêm các options cho đồ thiên sứ
                if (tilemacdinh > 0) {
                    for (byte i = 0; i < itemTS.itemOptions.size(); i++) {
                        if (itemTS.itemOptions.get(i).optionTemplate.id != 21 && itemTS.itemOptions.get(i).optionTemplate.id != 30) {
                            itemTS.itemOptions.get(i).param += (itemTS.itemOptions.get(i).param * tilemacdinh / 100);
                        }
                    }
                }

                InventoryServiceNew.gI().addItemBag(player, itemTS);
                sendEffectSuccessCombine(player);
            } else {
                sendEffectFailCombine(player);
            }

            // Trừ vật phẩm
            if (CtVip != null) {
                InventoryServiceNew.gI().subQuantityItemsBag(player, CtVip, 1);
            } else if (CtThuong != null) {
                InventoryServiceNew.gI().subQuantityItemsBag(player, CtThuong, 1);
            }
            if (mTS != null) {
                InventoryServiceNew.gI().subQuantityItemsBag(player, mTS, 99);
            }
            if (daNC != null) {
                InventoryServiceNew.gI().subQuantityItemsBag(player, daNC, 1);
            }
            if (daMM != null) {
                InventoryServiceNew.gI().subQuantityItemsBag(player, daMM, 1);
            }
            if (dHD != null) { // Trừ 1 đồ hủy diệt
                InventoryServiceNew.gI().subQuantityItemsBag(player, dHD, 1);
            }

            InventoryServiceNew.gI().sendItemBags(player);
            Service.getInstance().sendMoney(player);
            reOpenItemCombine(player);
        } else {
            Service.getInstance().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }

    public void openSKHVIP(Player player) {
        // 1 thiên sứ + 2 món kích hoạt -- món đầu kh làm gốc
        if (player.combineNew.itemsCombine.size() != 4) {
            Service.gI().sendThongBao(player, "Thiếu nguyên liệu");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDTS()).count() != 1) {
            Service.gI().sendThongBao(player, "Thiếu đồ thiên sứ");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isDHD()).count() != 1) {
            Service.gI().sendThongBao(player, "Thiếu đồ hủy diệt");
            return;
        }
        if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).count() != 2) {
            Service.gI().sendThongBao(player, "Thiếu đồ kích hoạt");
            return;
        }
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (player.inventory.gold < 1) {
                Service.gI().sendThongBao(player, "Con cần thêm vàng để đổi...");
                return;
            }
            player.inventory.gold -= COST;
            Item itemTS = player.combineNew.itemsCombine.stream().filter(Item::isDTS).findFirst().get();
            Item itemDHD = player.combineNew.itemsCombine.stream().filter(Item::isDHD).findAny().get();
            List<Item> itemSKH = player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.isSKH()).collect(Collectors.toList());
            CombineServiceNew.gI().sendEffectOpenItem(player, itemTS.template.iconID, itemTS.template.iconID);
            short itemId;
            if (itemTS.template.gender == 3 || itemTS.template.type == 4) {
                itemId = Manager.radaSKHVip[Util.nextInt(0, 5)];
                if (player.getSession().bdPlayer > 0 && Util.isTrue(1, (int) (100 / player.getSession().bdPlayer))) {
                    itemId = Manager.radaSKHVip[6];
                }
            } else {
                itemId = Manager.doSKHVip[itemTS.template.gender][itemTS.template.type][Util.nextInt(0, 5)];
                if (player.getSession().bdPlayer > 0 && Util.isTrue(1, (int) (100 / player.getSession().bdPlayer))) {
                    itemId = Manager.doSKHVip[itemTS.template.gender][itemTS.template.type][6];
                }
            }
            int skhId = ItemService.gI().randomSKHId(itemTS.template.gender);
            Item item;
            if (new Item(itemId).isDTL()) {
                item = Util.ratiItemTL(itemId);
                item.itemOptions.add(new Item.ItemOption(skhId, 1));
                item.itemOptions.add(new Item.ItemOption(ItemService.gI().optionIdSKH(skhId), 1));
                item.itemOptions.remove(item.itemOptions.stream().filter(itemOption -> itemOption.optionTemplate.id == 21).findFirst().get());
                item.itemOptions.add(new Item.ItemOption(21, 15));
                item.itemOptions.add(new Item.ItemOption(30, 1));
            } else {
                item = ItemService.gI().itemSKH(itemId, skhId);
            }
            InventoryServiceNew.gI().addItemBag(player, item);
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemTS, 1);
            InventoryServiceNew.gI().subQuantityItemsBag(player, itemDHD, 1);
            itemSKH.forEach(i -> InventoryServiceNew.gI().subQuantityItemsBag(player, i, 1));
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            player.combineNew.itemsCombine.clear();
            reOpenItemCombine(player);
        } else {
            Service.gI().sendThongBao(player, "Bạn phải có ít nhất 1 ô trống hành trang");
        }
    }

    private void doidiem(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item item = player.combineNew.itemsCombine.get(0);
            InventoryServiceNew.gI().sendItemBags(player);
            player.inventory.gold -= 500000000;
            player.inventory.coupon += 1;
            Service.gI().sendThongBao(player, "Bạn nhận được 1 Điểm!");
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 99);
            player.combineNew.itemsCombine.clear();
            InventoryServiceNew.gI().sendItemBags(player);
            Service.gI().sendMoney(player);
            reOpenItemCombine(player);
        }
    }

    private void huyHieuHuyDiet(Player player) {
        if (player.combineNew.itemsCombine.size() == 1 || player.combineNew.itemsCombine.size() == 2) {
            Item item_1 = null, item_2 = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.id == 561 || item.template.id == 16) {
                        if (item_1 == null) {
                            item_1 = item;
                        } else if (item_2 == null && item.template.id != item_1.template.id) {
                            item_2 = item;
                        }
                    }
                }
                if (item_1 != null && item_2 != null) {
                    Item ngocRong3s = null;
                    try {
                        ngocRong3s = InventoryServiceNew.gI().findItemBag(player, 16);
                    } catch (Exception e) {
                    }
                    if (ngocRong3s == null || ngocRong3s.quantity < 2) {
                        Service.gI().sendThongBao(player, "Ngươi không đủ ngọc rồng 3s!");
                        break;
                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) <= 0) {
                        Service.gI().sendThongBao(player, "Hành trang đầy!");
                    } else {
                        if (Util.isTrue(30, 100)) {
                            sendEffectSuccessCombine(player);
                            Item HuyHieuHuyDiet = ItemService.gI().createNewItem((short) 1405);
                            InventoryServiceNew.gI().addItemBag(player, HuyHieuHuyDiet);
                        } else {
                            sendEffectFailCombine(player);
                        }
                        InventoryServiceNew.gI().subQuantityItemsBag(player, item_1, (item_1.template.id == 16 ? 2 : 1));
                        InventoryServiceNew.gI().subQuantityItemsBag(player, item_2, (item_2.template.id == 16 ? 2 : 1));
                        InventoryServiceNew.gI().sendItemBags(player);
                        reOpenItemCombine(player);
                    }
                }
            }
        }
    }

    private void phanradothanlinh(Player player) {
        if (player.combineNew.itemsCombine.size() == 1 || player.combineNew.itemsCombine.size() == 2 || player.combineNew.itemsCombine.size() == 3) {
            Item dtl_1 = null, dtl_2 = null, dtl_3 = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.id >= 555 && item.template.id <= 567) {
                        if (dtl_1 == null) {
                            dtl_1 = item;
                        } else if (dtl_2 == null && item.template.id != dtl_1.template.id) {
                            dtl_2 = item;
                        } else if (dtl_3 == null && item.template.id != dtl_1.template.id && item.template.id != dtl_2.template.id) {
                            dtl_3 = item;
                        }
                    }
                }
            }

            if (dtl_1 != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    if (player.inventory.gold >= (dtl_2 != null ? (dtl_3 != null ? COST_PHAN_RA_DO_TL_3 : COST_PHAN_RA_DO_TL_2) : COST_PHAN_RA_DO_TL_1)) {
                        player.inventory.gold -= (dtl_2 != null ? (dtl_3 != null ? COST_PHAN_RA_DO_TL_3 : COST_PHAN_RA_DO_TL_2) : COST_PHAN_RA_DO_TL_1);
                        int tiLe = (dtl_2 != null ? (dtl_3 != null ? 60 : 30) : 10);
                        if (Util.isTrue(tiLe, 100)) {
                            sendEffectSuccessCombine(player);
                            Item item = ItemService.gI().createNewItem((short) 1171);
                            InventoryServiceNew.gI().addItemBag(player, item);
                            if (dtl_3 != null && Util.isTrue(10, 100)) {
                                Service.gI().sendThongBao(player, "Ohh mày may mắn đấy được nhận x2 Ngọc!");
                                InventoryServiceNew.gI().addItemBag(player, item);
                            }
                        } else {
                            sendEffectFailCombine(player);
                        }
                        InventoryServiceNew.gI().subQuantityItemsBag(player, dtl_1, 1);
                        if (dtl_2 != null) {
                            InventoryServiceNew.gI().subQuantityItemsBag(player, dtl_2, 1);
                        }
                        if (dtl_3 != null) {
                            InventoryServiceNew.gI().subQuantityItemsBag(player, dtl_3, 1);
                        }
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.gI().sendMoney(player);
                        reOpenItemCombine(player);
                    } else {
                        Service.gI().sendThongBao(player, "Mày làm méo gì có vàng!");
                    }
                } else {
                    Service.gI().sendThongBao(player, "Hành trang đầy!");
                }
            }
        }
    }

    private void xuThongQuan(Player player) {
        if (player.combineNew.itemsCombine.size() == 5) {
            Item item1 = null, item2 = null, item3 = null, item4 = null, item5 = null;
            int checkGender = 0;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.isNotNullItem()) {
                    if (item.template.id >= 650 && item.template.id <= 662) {
                        if (item1 == null && item.template.type == 0) {
                            item1 = item;
                        } else if (item2 == null && item1 != null && item.template.type == 1) {
                            if (item.template.gender == item1.template.gender) {
                                item2 = item;
                            } else {
                                checkGender++;
                            }
                        } else if (item3 == null && item2 != null && item.template.type == 2) {
                            if (item.template.gender == item1.template.gender) {
                                item3 = item;
                            } else {
                                checkGender++;
                            }
                        } else if (item4 == null && item3 != null && item.template.type == 3) {
                            if (item.template.gender == item1.template.gender) {
                                item4 = item;
                            } else {
                                checkGender++;
                            }
                        } else if (item5 == null && item4 != null) {
                            item5 = item;
                        }
                    }
                }
            }
            if (checkGender > 0) {
                Service.gI().sendThongBao(player, "Vật phẩm không cùng\nHành tinh với nhau");
            } else if (item1 == null || item2 == null || item3 == null || item4 == null || item5 == null) {
                Service.gI().sendThongBao(player, "Vật phẩm không đáp ứng yêu cầu!\nHãy thử lại!");
            }
            if (item5 != null) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                    Service.gI().sendThongBao(player, "Bạn đã nhận được\nXu thông quan!");
                    Item item = ItemService.gI().createNewItem((short) 1178);
                    InventoryServiceNew.gI().addItemBag(player, item);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item1, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item2, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item3, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item4, 1);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item5, 1);
                    InventoryServiceNew.gI().sendItemBags(player);
                    reOpenItemCombine(player);
                } else {
                    Service.gI().sendThongBao(player, "Hàng trang đầy!");
                }
            }
        } else {
            Service.gI().sendThongBao(player, "Không đủ vật phẩm để thực hiện");
        }
    }

    private void doiVeHuyDiet(Player player) {
        if (player.combineNew.itemsCombine.size() == 1) {
            Item item = player.combineNew.itemsCombine.get(0);
            if (item.isNotNullItem() && item.template.id >= 555 && item.template.id <= 567) {
                if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0
                        && player.inventory.gold >= COST_DOI_VE_DOI_DO_HUY_DIET) {
                    player.inventory.gold -= COST_DOI_VE_DOI_DO_HUY_DIET;
                    Item ticket = ItemService.gI().createNewItem((short) (2001 + item.template.type));
                    ticket.itemOptions.add(new Item.ItemOption(30, 0));
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
                    InventoryServiceNew.gI().addItemBag(player, ticket);
                    sendEffectOpenItem(player, item.template.iconID, ticket.template.iconID);

                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void nangCapBongTai(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            long gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.getInstance().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item bongtai = null;
            Item manhvobt = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (checkbongtai(item)) {// Kiểm tra có bt c1 hoặc c2 không , id 451 và 921
                    bongtai = item;
                } else if (item.template.id == 933) {// check có mảnh vỡ bông tai và gán vào
                    manhvobt = item;
                }
            }
            if (bongtai != null && manhvobt != null) {
                int level = 0;
                for (ItemOption io : bongtai.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        break;
                    }
                }
                if (level < 4) {
                    int lvbt = lvbt(bongtai);
                    int countmvbt = getcountmvbtnangbt(lvbt);
                    if (countmvbt > manhvobt.quantity) {
                        Service.getInstance().sendThongBao(player, "Không đủ Mảnh vỡ bông tai");
                        return;
                    }
                    player.inventory.gold -= gold;
                    player.inventory.gem -= gem;
                    InventoryServiceNew.gI().subQuantityItemsBag(player, manhvobt, countmvbt);
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        bongtai.template = ItemService.gI().getTemplate(getidbtsaukhilencap(lvbt));
                        bongtai.itemOptions.clear();
                        bongtai.itemOptions.add(new ItemOption(72, lvbt + 1));
                        sendEffectSuccessCombine(player);
                    } else {
                        sendEffectFailCombine(player);
                    }
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.getInstance().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private short getidbtsaukhilencap(int lvbtcu) {
        switch (lvbtcu) {
            case 1:
                return 921;
            case 2:
                return 1228;
            case 3:
                return 1229;

        }
        return 0;
    }

    private void moChiSoBongTai(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            long gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.getInstance().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.getInstance().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item bongTai = null;
            Item manhHon = null;
            Item daXanhLam = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id == 921 || item.template.id == 1228 || item.template.id == 1229) {
                    bongTai = item;
                } else if (item.template.id == 934) {
                    manhHon = item;
                } else if (item.template.id == 935) {
                    daXanhLam = item;
                }
            }
            if (bongTai != null && daXanhLam != null && manhHon.quantity >= 99 && daXanhLam.quantity >= 1) {
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                InventoryServiceNew.gI().subQuantityItemsBag(player, manhHon, 99);
                InventoryServiceNew.gI().subQuantityItemsBag(player, daXanhLam, 1);
                if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                    bongTai.itemOptions.clear();
                    if (bongTai.template.id == 921) {
                        bongTai.itemOptions.add(new ItemOption(72, 2));
                        int rdUp = Util.nextInt(0, 9);
                        switch (rdUp) {
                            case 0:
                                bongTai.itemOptions.add(new ItemOption(50, 5));
                                break;
                            case 1:
                                bongTai.itemOptions.add(new ItemOption(77, 5));
                                break;
                            case 2:
                                bongTai.itemOptions.add(new ItemOption(103, 5));
                                break;
                            case 3:
                                bongTai.itemOptions.add(new ItemOption(108, 5));
                                break;
                            case 4:
                                bongTai.itemOptions.add(new ItemOption(94, 5));
                                break;
                            case 5:
                                bongTai.itemOptions.add(new ItemOption(14, 5));
                                break;
                            case 6:
                                bongTai.itemOptions.add(new ItemOption(80, 5));
                                break;
                            case 7:
                                bongTai.itemOptions.add(new ItemOption(81, 5));
                                break;
                            case 8:
                                bongTai.itemOptions.add(new ItemOption(101, 5));
                                break;
                            case 9:
                                bongTai.itemOptions.add(new ItemOption(5, 5));
                                break;
                        }
                    } else if (bongTai.template.id == 1228) {
                        bongTai.itemOptions.add(new ItemOption(72, 3));
                        int rdUp1 = Util.nextInt(0, 9);
                        switch (rdUp1) {
                            case 0:
                                bongTai.itemOptions.add(new ItemOption(50, Util.nextInt(5, 10)));
                                break;
                            case 1:
                                bongTai.itemOptions.add(new ItemOption(77, Util.nextInt(5, 10)));
                                break;
                            case 2:
                                bongTai.itemOptions.add(new ItemOption(103, Util.nextInt(5, 10)));
                                break;
                            case 3:
                                bongTai.itemOptions.add(new ItemOption(108, Util.nextInt(5, 10)));
                                break;
                            case 4:
                                bongTai.itemOptions.add(new ItemOption(94, Util.nextInt(5, 10)));
                                break;
                            case 5:
                                bongTai.itemOptions.add(new ItemOption(14, Util.nextInt(5, 10)));
                                break;
                            case 6:
                                bongTai.itemOptions.add(new ItemOption(80, Util.nextInt(5, 10)));
                                break;
                            case 7:
                                bongTai.itemOptions.add(new ItemOption(81, Util.nextInt(5, 10)));
                                break;
                            case 8:
                                bongTai.itemOptions.add(new ItemOption(101, Util.nextInt(5, 10)));
                                break;
                            case 9:
                                bongTai.itemOptions.add(new ItemOption(5, Util.nextInt(5, 10)));
                                break;
                        }
                    } else if (bongTai.template.id == 1229) {
                        bongTai.itemOptions.add(new ItemOption(72, 4));
                        int rdUp1 = Util.nextInt(0, 9);
                        switch (rdUp1) {
                            case 0:
                                bongTai.itemOptions.add(new ItemOption(50, Util.nextInt(5, 30)));
                                break;
                            case 1:
                                bongTai.itemOptions.add(new ItemOption(77, Util.nextInt(5, 30)));
                                break;
                            case 2:
                                bongTai.itemOptions.add(new ItemOption(103, Util.nextInt(5, 30)));
                                break;
                            case 3:
                                bongTai.itemOptions.add(new ItemOption(108, Util.nextInt(5, 30)));
                                break;
                            case 4:
                                bongTai.itemOptions.add(new ItemOption(94, Util.nextInt(5, 30)));
                                break;
                            case 5:
                                bongTai.itemOptions.add(new ItemOption(14, Util.nextInt(5, 30)));
                                break;
                            case 6:
                                bongTai.itemOptions.add(new ItemOption(80, Util.nextInt(5, 30)));
                                break;
                            case 7:
                                bongTai.itemOptions.add(new ItemOption(81, Util.nextInt(5, 30)));
                                break;
                            case 8:
                                bongTai.itemOptions.add(new ItemOption(101, Util.nextInt(5, 30)));
                                break;
                            case 9:
                                bongTai.itemOptions.add(new ItemOption(5, Util.nextInt(5, 30)));
                                break;
                        }
                    }
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.getInstance().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void moChiSolinhthu(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            long gold = player.combineNew.goldCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item linhthu = null;
            Item damathuat = null;
            Item thucan = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.id >= 2019 && item.template.id <= 2026) {
                    linhthu = item;
                } else if (item.template.id == 2030) {
                    damathuat = item;
                } else if (item.template.id >= 663 && item.template.id <= 667) {
                    thucan = item;
                }
            }
            if (linhthu != null && damathuat != null && damathuat.quantity >= 99) {
                player.inventory.gold -= gold;
                player.inventory.gem -= gem;
                InventoryServiceNew.gI().subQuantityItemsBag(player, damathuat, 99);
                InventoryServiceNew.gI().subQuantityItemsBag(player, thucan, 1);
                if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                    linhthu.itemOptions.clear();
                    linhthu.itemOptions.add(new Item.ItemOption(72, 2));
                    int rdUp = Util.nextInt(0, 7);
                    if (rdUp == 0) {
                        linhthu.itemOptions.add(new Item.ItemOption(50, Util.nextInt(5, 25)));
                    } else if (rdUp == 1) {
                        linhthu.itemOptions.add(new Item.ItemOption(77, Util.nextInt(5, 25)));
                    } else if (rdUp == 2) {
                        linhthu.itemOptions.add(new Item.ItemOption(103, Util.nextInt(5, 25)));
                    } else if (rdUp == 3) {
                        linhthu.itemOptions.add(new Item.ItemOption(108, Util.nextInt(5, 25)));
                    } else if (rdUp == 4) {
                        linhthu.itemOptions.add(new Item.ItemOption(94, Util.nextInt(5, 15)));
                    } else if (rdUp == 5) {
                        linhthu.itemOptions.add(new Item.ItemOption(14, Util.nextInt(5, 15)));
                    } else if (rdUp == 6) {
                        linhthu.itemOptions.add(new Item.ItemOption(80, Util.nextInt(5, 25)));
                    } else if (rdUp == 7) {
                        linhthu.itemOptions.add(new Item.ItemOption(81, Util.nextInt(5, 25)));
                    }
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void epSaoTrangBi(Player player) {
        if (player.combineNew.itemsCombine.size() == 2) {
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                return;
            }
            Item trangBi = null;
            Item daPhaLe = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (isTrangBiPhaLeHoa(item)) {
                    trangBi = item;
                } else if (isDaPhaLe(item)) {
                    daPhaLe = item;
                }
            }
            int star = 0; //sao pha lê đã ép
            int starEmpty = 0; //lỗ sao pha lê
            if (trangBi != null && daPhaLe != null) {
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : trangBi.itemOptions) {
                    if (io.optionTemplate.id == 102) {
                        star = io.param;
                        optionStar = io;
                    } else if (io.optionTemplate.id == 107) {
                        starEmpty = io.param;
                    }
                }
                if (star < starEmpty) {
                    player.inventory.gem -= gem;
                    int optionId = getOptionDaPhaLe(daPhaLe);
                    int param = getParamDaPhaLe(daPhaLe);
                    Item.ItemOption option = null;
                    for (Item.ItemOption io : trangBi.itemOptions) {
                        if (io.optionTemplate.id == optionId) {
                            option = io;
                            break;
                        }
                    }
                    if (option != null) {
                        option.param += param;
                    } else {
                        trangBi.itemOptions.add(new Item.ItemOption(optionId, param));
                    }
                    if (optionStar != null) {
                        optionStar.param++;
                    } else {
                        trangBi.itemOptions.add(new Item.ItemOption(102, 1));
                    }

                    InventoryServiceNew.gI().subQuantityItemsBag(player, daPhaLe, 1);
                    sendEffectSuccessCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void phaLeHoaTrangBi(Player player) {
        boolean flag = false;
        int solandap = player.combineNew.quantities;
        while (player.combineNew.quantities > 0 && !player.combineNew.itemsCombine.isEmpty() && !flag) {
            long gold = player.combineNew.goldCombine;
            int gem = player.combineNew.gemCombine;
            if (player.inventory.gold < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                break;
            } else if (player.inventory.gem < gem) {
                Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                break;
            }
            Item item = player.combineNew.itemsCombine.get(0);
            if (isTrangBiPhaLeHoa(item)) {
                int star = 0;
                Item.ItemOption optionStar = null;
                for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 107) {
                        star = io.param;
                        optionStar = io;
                        break;
                    }
                }
                if (star < MAX_STAR_ITEM) {
                    player.inventory.gold -= gold;
                    player.inventory.gem -= gem;
                    //float ratio = getRatioPhaLeHoa(star);
                    int epint = (int) getRatioPhaLeHoa(star);
                    flag = Util.isTrue(epint, 100);
                    if (flag) {
                        if (optionStar == null) {
                            item.itemOptions.add(new Item.ItemOption(107, 1));
                        } else {
                            optionStar.param++;
                        }
                        sendEffectSuccessCombine(player);
                        Service.getInstance().sendThongBao(player, "Lên cấp sau " + (solandap - player.combineNew.quantities + 1) + " lần đập");
                        if (optionStar != null && optionStar.param >= 7) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa pha lê hóa "
                                    + "thành công " + item.template.name + " lên " + optionStar.param + " sao pha lê");
                        }
                    } else {
                        sendEffectFailCombine(player);
                    }
                }
            }
            player.combineNew.quantities -= 1;
        }
        if (!flag) {
            sendEffectFailCombine(player);
        }
        InventoryServiceNew.gI().sendItemBags(player);
        Service.gI().sendMoney(player);
        reOpenItemCombine(player);
    }

    private void nhapNgocRong(Player player) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
            if (!player.combineNew.itemsCombine.isEmpty()) {
                Item item = player.combineNew.itemsCombine.get(0);
                if (item != null && item.isNotNullItem() && (item.template.id > 14 && item.template.id <= 20) && item.quantity >= 7) {
                    Item nr = ItemService.gI().createNewItem((short) (item.template.id - 1));
                    InventoryServiceNew.gI().addItemBag(player, nr);
                    InventoryServiceNew.gI().subQuantityItemsBag(player, item, 7);
                    InventoryServiceNew.gI().sendItemBags(player);
                    reOpenItemCombine(player);
//                    sendEffectCombineDB(player, item.template.iconID);
                }
            }
        }
    }

    private void nangCapVatPham(Player player) {
        if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() != 1) {
                return;
            }
            Item itemDo = null;
            Item itemDNC = null;
            Item itemDBV = null;
            for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                        itemDBV = player.combineNew.itemsCombine.get(j);
                        continue;
                    }
                    if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                        itemDo = player.combineNew.itemsCombine.get(j);
                    } else {
                        itemDNC = player.combineNew.itemsCombine.get(j);
                    }
                }
            }
            if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                int countDaNangCap = player.combineNew.countDaNangCap;
                long gold = player.combineNew.goldCombine;
                short countDaBaoVe = player.combineNew.countDaBaoVe;
                if (player.inventory.gold < gold) {
                    Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                    return;
                }

                if (itemDNC.quantity < countDaNangCap) {
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3) {
                    if (Objects.isNull(itemDBV)) {
                        return;
                    }
                    if (itemDBV.quantity < countDaBaoVe) {
                        return;
                    }
                }

                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : itemDo.itemOptions) {
                    if (io.optionTemplate.id == 72) {
                        level = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (level < MAX_LEVEL_ITEM) {
                    Item.ItemOption option = null;
                    Item.ItemOption option2 = null;
                    for (Item.ItemOption io : itemDo.itemOptions) {
                        if (io.optionTemplate.id == 47
                                || io.optionTemplate.id == 6
                                || io.optionTemplate.id == 0
                                || io.optionTemplate.id == 7
                                || io.optionTemplate.id == 14
                                || io.optionTemplate.id == 22
                                || io.optionTemplate.id == 23
                                || io.optionTemplate.id == 196
                                || io.optionTemplate.id == 197
                                || io.optionTemplate.id == 198
                                || io.optionTemplate.id == 199) {
                            option = io;
                        } else if (io.optionTemplate.id == 27
                                || io.optionTemplate.id == 28) {
                            option2 = io;
                        }
                    }
                    player.inventory.gold -= gold;
                    if (Util.isTrue((player.combineNew.ratioCombine - 3 > 0 ? player.combineNew.ratioCombine - 3 : 1), 100)) {
                        option.param += (option.param * 10 / 100 > 1 ? option.param * 10 / 100 : 1);
                        if (option2 != null) {
                            option2.param += (option2.param * 10 / 100);
                        }
                        if (optionLevel == null) {
                            itemDo.itemOptions.add(new Item.ItemOption(72, 1));
                        } else {
                            optionLevel.param++;
                        }
                        if (optionLevel != null && optionLevel.param >= 5) {
                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa nâng cấp "
                                    + "thành công " + itemDo.template.name + " lên +" + optionLevel.param);
                        }
                        sendEffectSuccessCombine(player);
                    } else {
                        if ((level == 2 || level == 4 || level == 6) && (player.combineNew.itemsCombine.size() != 3)) {
                            option.param -= (option.param * 10 / 100);
                            if (option2 != null) {
                                option2.param -= (option2.param * 10 / 100);
                            }
                            optionLevel.param--;
                        }
                        sendEffectFailCombine(player);
                    }
                    if (player.combineNew.itemsCombine.size() == 3) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, itemDBV, countDaBaoVe);
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, itemDNC, player.combineNew.countDaNangCap);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void epantrangbi(Player player) {
        if (player.combineNew.itemsCombine.size() == 3) {
            int gold = 5000;
            if (player.inventory.ruby < gold) {
                Service.gI().sendThongBao(player, "Không đủ vàng để thực hiện");
                return;
            }

            Item cailoz = null;
            Item concac = null;
            Item thoivang = null;
            for (Item item : player.combineNew.itemsCombine) {
                if (item.template.type < 5) {
                    cailoz = item;
                } else if (item.template.id == 674) {
                    concac = item;
                } else if (item.template.id == 457) {
                    thoivang = item;
                }
            }
            if (cailoz != null && concac != null && concac.quantity >= 25) {
                player.inventory.ruby -= 50000;
                player.inventory.gem -= 0;
                InventoryServiceNew.gI().subQuantityItemsBag(player, concac, 25);
                InventoryServiceNew.gI().subQuantityItemsBag(player, thoivang, 1);
                if (Util.isTrue(100, 100)) {
                    cailoz.itemOptions.clear();
                    cailoz.itemOptions.add(new Item.ItemOption(72, 10));
                    cailoz.itemOptions.add(new Item.ItemOption(107, Util.nextInt(3, 6)));
                    cailoz.itemOptions.add(new Item.ItemOption(0, Util.nextInt(5000, 8000)));
                    cailoz.itemOptions.add(new Item.ItemOption(48, Util.nextInt(30000, 60000)));
                    cailoz.itemOptions.add(new Item.ItemOption(21, Util.nextInt(40, 60)));
                    cailoz.itemOptions.add(new Item.ItemOption(30, 0));

                    int rdUp = Util.nextInt(0, 2);
                    if (rdUp == 0) {
                        cailoz.itemOptions.add(new Item.ItemOption(34, 1));
                    } else if (rdUp == 1) {
                        cailoz.itemOptions.add(new Item.ItemOption(35, 1));
                    } else if (rdUp == 2) {
                        cailoz.itemOptions.add(new Item.ItemOption(36, 1));
                    }
                    sendEffectSuccessCombine(player);
                } else {
                    sendEffectFailCombine(player);
                }
                InventoryServiceNew.gI().sendItemBags(player);
                Service.gI().sendMoney(player);
                reOpenItemCombine(player);
            }
        }
    }

    private void nguyetan(Player player) {
        if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() != 1) {
                return;//admin
            }
            Item itemDo = null;
            Item itemDNC = null;
            Item itemDBV = null;
            for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                        itemDBV = player.combineNew.itemsCombine.get(j);
                        continue;
                    }
                    if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                        itemDo = player.combineNew.itemsCombine.get(j);
                    } else {
                        itemDNC = player.combineNew.itemsCombine.get(j);
                    }
                }
            }
            if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                int countDaNangCap = player.combineNew.countDaNangCap;
                int gold = 25000;
                short countDaBaoVe = player.combineNew.countDaBaoVe;
                if (player.inventory.ruby < gold) {
                    Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                    return;
                }

                if (itemDNC.quantity < countDaNangCap) {
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3) {
                    if (Objects.isNull(itemDBV)) {
                        return;
                    }
                    if (itemDBV.quantity < countDaBaoVe) {
                        return;
                    }
                }

                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : itemDo.itemOptions) {
                    if (io.optionTemplate.id == 35) {
//                        player.inventory.ruby -= gold;
                        level = io.param;
                        optionLevel = io;
                        break;
                    }

                }
                if (level <= 2) {

                    Item.ItemOption option = null;
                    Item.ItemOption option2 = null;
                    for (Item.ItemOption io : itemDo.itemOptions) {
                        if (io.optionTemplate.id == 47
                                || io.optionTemplate.id == 6
                                || io.optionTemplate.id == 0
                                || io.optionTemplate.id == 7
                                || io.optionTemplate.id == 14
                                || io.optionTemplate.id == 22
                                || io.optionTemplate.id == 23) {
                            option = io;
                        } else if (io.optionTemplate.id == 27
                                || io.optionTemplate.id == 28) {
                            option2 = io;
                        }
                    }
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        option.param += (option.param * 10 / 100);
                        if (option2 != null) {
                            option2.param += (option2.param * 10 / 100);
                        }
                        if (optionLevel == null) {
                            itemDo.itemOptions.add(new Item.ItemOption(Util.nextInt(35, 35), 1));
                        } else {
                            optionLevel.param++;
                        }
//                        if (optionLevel != null && optionLevel.param >= 5) {
//                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa nâng cấp "
//                                    + "thành công " + trangBi.template.name + " lên +" + optionLevel.param);
//                        }
                        sendEffectSuccessCombine(player);
                    } else {
                        if ((level == 2 || level == 4 || level == 6) && (player.combineNew.itemsCombine.size() != 3)) {
                            option.param -= (option.param * 10 / 100);
                            if (option2 != null) {
                                option2.param -= (option2.param * 10 / 100);
                            }
                            optionLevel.param--;
                        }
                        sendEffectFailCombine(player);
                    }
                    if (player.combineNew.itemsCombine.size() == 3) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, itemDBV, countDaBaoVe);
                    }
                    player.inventory.ruby -= gold;
                    InventoryServiceNew.gI().subQuantityItemsBag(player, itemDNC, player.combineNew.countDaNangCap);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    private void nhatan(Player player) {
        if (player.combineNew.itemsCombine.size() >= 2 && player.combineNew.itemsCombine.size() < 4) {
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type < 5).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.type == 14).count() != 1) {
                return;
            }
            if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.stream().filter(item -> item.isNotNullItem() && item.template.id == 987).count() != 1) {
                return;//admin
            }
            Item itemDo = null;
            Item itemDNC = null;
            Item itemDBV = null;
            for (int j = 0; j < player.combineNew.itemsCombine.size(); j++) {
                if (player.combineNew.itemsCombine.get(j).isNotNullItem()) {
                    if (player.combineNew.itemsCombine.size() == 3 && player.combineNew.itemsCombine.get(j).template.id == 987) {
                        itemDBV = player.combineNew.itemsCombine.get(j);
                        continue;
                    }
                    if (player.combineNew.itemsCombine.get(j).template.type < 5) {
                        itemDo = player.combineNew.itemsCombine.get(j);
                    } else {
                        itemDNC = player.combineNew.itemsCombine.get(j);
                    }
                }
            }
            if (isCoupleItemNangCapCheck(itemDo, itemDNC)) {
                int countDaNangCap = player.combineNew.countDaNangCap;
                int gold = 25000;
                short countDaBaoVe = player.combineNew.countDaBaoVe;
                if (player.inventory.ruby < gold) {
                    Service.gI().sendThongBao(player, "Không đủ ngọc để thực hiện");
                    return;
                }

                if (itemDNC.quantity < countDaNangCap) {
                    return;
                }
                if (player.combineNew.itemsCombine.size() == 3) {
                    if (Objects.isNull(itemDBV)) {
                        return;
                    }
                    if (itemDBV.quantity < countDaBaoVe) {
                        return;
                    }
                }

                int level = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : itemDo.itemOptions) {
                    if (io.optionTemplate.id == 36) {
//                        player.inventory.ruby -= gold;
                        level = io.param;
                        optionLevel = io;
                        break;
                    }

                }
                if (level <= 2) {

                    Item.ItemOption option = null;
                    Item.ItemOption option2 = null;
                    for (Item.ItemOption io : itemDo.itemOptions) {
                        if (io.optionTemplate.id == 47
                                || io.optionTemplate.id == 6
                                || io.optionTemplate.id == 0
                                || io.optionTemplate.id == 7
                                || io.optionTemplate.id == 14
                                || io.optionTemplate.id == 22
                                || io.optionTemplate.id == 23) {
                            option = io;
                        } else if (io.optionTemplate.id == 27
                                || io.optionTemplate.id == 28) {
                            option2 = io;
                        }
                    }
                    if (Util.isTrue(player.combineNew.ratioCombine, 100)) {
                        option.param += (option.param * 10 / 100);
                        if (option2 != null) {
                            option2.param += (option2.param * 10 / 100);
                        }
                        if (optionLevel == null) {
                            itemDo.itemOptions.add(new Item.ItemOption(Util.nextInt(36, 36), 1));
                        } else {
                            optionLevel.param++;
                        }
//                        if (optionLevel != null && optionLevel.param >= 5) {
//                            ServerNotify.gI().notify("Chúc mừng " + player.name + " vừa nâng cấp "
//                                    + "thành công " + trangBi.template.name + " lên +" + optionLevel.param);
//                        }
                        sendEffectSuccessCombine(player);
                    } else {
                        if ((level == 2 || level == 4 || level == 6) && (player.combineNew.itemsCombine.size() != 3)) {
                            option.param -= (option.param * 10 / 100);
                            if (option2 != null) {
                                option2.param -= (option2.param * 10 / 100);
                            }
                            optionLevel.param--;
                        }
                        sendEffectFailCombine(player);
                    }
                    if (player.combineNew.itemsCombine.size() == 3) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, itemDBV, countDaBaoVe);
                    }
                    player.inventory.ruby -= gold;
                    InventoryServiceNew.gI().subQuantityItemsBag(player, itemDNC, player.combineNew.countDaNangCap);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    reOpenItemCombine(player);
                }
            }
        }
    }

    //------------------------------------------------------------ Chức năng bên Class NPC
    public static void TradeChip(Player player, int select, int typeTrade) {
        // typeTrade = 0: Bán ra
        // typeTrade = 1: Nhập vào
        Item TV = null, Chip = null;
        for (Item item : player.inventory.itemsBag) {
            if (TV != null && Chip != null) {
                break;
            } else if (item.isNotNullItem() && item.template.id == 457) {
                TV = item;
            } else if (item.isNotNullItem() && item.template.id == 1179) {
                Chip = item;
            }
        }
        if (select == 0) {
            if (typeTrade == 0) {
                if (TV != null) {
                    Input.gI().Mua_Chip(player);
                } else {
                    Service.gI().sendThongBao(player, "Ngươi không còn Thỏi Vàng!!");
                }
            } else {
                if (Chip != null) {
                    Input.gI().Doi_Chip(player);
                } else {
                    Service.gI().sendThongBao(player, "Ngươi không có Chip nào!!");
                }
            }
        }
    }

    //--------------------------------------------------------------------------
    /**
     * r
     * Hiệu ứng mở item
     *
     * @param player
     */
    public void sendAddItemCombine(Player player, int npcId, Item... items) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(0);
            msg.writer().writeUTF("Dragon Boy");
            msg.writer().writeUTF("Dragon Boy - Đẳng Cấp Là Mãi Mãi");
            msg.writer().writeShort(npcId);
            player.sendMessage(msg);
            msg.cleanup();
            msg = new Message(-81);
            msg.writer().writeByte(1);
            msg.writer().writeByte(items.length);
            for (Item item : items) {
                msg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, item));
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (IOException e) {
        }
    }

    public void sendEffSuccessVip(Player player, int iconID) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(7);
            msg.writer().writeShort(iconID);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (IOException e) {
        }
    }

    public void sendEffFailVip(Player player) {
        try {
            Message msg;
            msg = new Message(-81);
            msg.writer().writeByte(8);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (IOException e) {
        }
    }

    public void sendEffectOpenItem(Player player, short icon1, short icon2) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(OPEN_ITEM);
            msg.writer().writeShort(icon1);
            msg.writer().writeShort(icon2);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Hiệu ứng đập đồ thành công
     *
     * @param player
     */
    public void sendEffectSuccessCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_SUCCESS);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Hiệu ứng đập đồ thất bại
     *
     * @param player
     */
    public void sendEffectFailCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_FAIL);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    /**
     * Gửi lại danh sách đồ trong tab combine
     *
     * @param player
     */
    public void reOpenItemCombine(Player player) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(REOPEN_TAB_COMBINE);
            msg.writer().writeByte(player.combineNew.itemsCombine.size());
            for (Item it : player.combineNew.itemsCombine) {
                for (int j = 0; j < player.inventory.itemsBag.size(); j++) {
                    if (it == player.inventory.itemsBag.get(j)) {
                        msg.writer().writeByte(j);
                    }
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void sendEffectCombineItem(Player player, byte type, short icon1, short icon2) {
        Message msg = null;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(type);
            switch (type) {
                case 0:
                    msg.writer().writeUTF("");
                    msg.writer().writeUTF("");
                    break;
                case 1:
                    msg.writer().writeByte(0);
                    msg.writer().writeByte(-1);
                    break;
                case 2: // success 0 eff 0
                case 3: // success 1 eff 0
                    break;
                case 4: // success 0 eff 1
                    msg.writer().writeShort(icon1);
                    break;
                case 5: // success 0 eff 2
                    msg.writer().writeShort(icon1);
                    break;
                case 6: // success 0 eff 3
                    msg.writer().writeShort(icon1);
                    msg.writer().writeShort(icon2);
                    break;
                case 7: // success 0 eff 4
                    msg.writer().writeShort(icon1);
                    break;
                case 8: // success 1 eff 4
                    break;
            }
            msg.writer().writeShort(-1); // id npc
            player.sendMessage(msg);
        } catch (Exception e) {
        } finally {
            if (msg != null) {
                msg.cleanup();
            }
        }
    }

    /**
     * Hiệu ứng ghép ngọc rồng
     *
     * @param player
     * @param icon
     */
    private void sendEffectCombineDB(Player player, short icon) {
        Message msg;
        try {
            msg = new Message(-81);
            msg.writer().writeByte(COMBINE_DRAGON_BALL);
            msg.writer().writeShort(icon);
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    //--------------------------------------------------------------------------Ratio, cost combine
    public int getGoldPhaLeHoa(int star) {
        switch (star) {
            case 0:
                return 5000000;
            case 1:
                return 20000000;
            case 2:
                return 35000000;
            case 3:
                return 50000000;
            case 4:
                return 75000000;
            case 5:
                return 90000000;
            case 6:
                return 115000000;
            case 7:
                return 150000000;
            case 8:
                return 240000000;
            case 9:
                return 300000000;
            case 10:
                return 450000000;
            case 11:
                return 500000000;
            case 12:
                return 1000000000;
        }
        return 0;
    }

    private float getRatioPhaLeHoa(int star) { //tile dap do chi hat mit
        switch (star) {
            case 0:
                return 100f;
            case 1:
                return 80f;
            case 2:
                return 50f;
            case 3:
                return 30f;
            case 4:
                return 10f;
            case 5:
                return 5f;
            case 6:
                return 2f;
            case 7:
                return 1f;
            case 8:
                return 0.5f;
            case 9:
                return 1f;
            case 10:
                return 0.9f;
            case 11:
                return 0.8f;
            case 12:
                return 0.8f;
        }

        return 0;
    }

    public int getGemPhaLeHoa(int star) {
        switch (star) {
            case 0:
                return 10;
            case 1:
                return 20;
            case 2:
                return 30;
            case 3:
                return 40;
            case 4:
                return 50;
            case 5:
                return 60;
            case 6:
                return 70;
            case 7:
                return 80;
            case 8:
                return 50;
            case 9:
                return 50;
            case 10:
                return 50;
            case 11:
                return 50;
            case 12:
                return 50;
        }
        return 0;
    }

    private int getGemEpSao(int star) {
        switch (star) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 5;
            case 3:
                return 10;
            case 4:
                return 25;
            case 5:
                return 50;
            case 6:
                return 100;
        }
        return 0;
    }

    private double getTileNangCapDo(int level) {
        switch (level) {
            case 0:
                return 100;
            case 1:
                return 80;
            case 2:
                return 50;
            case 3:
                return 30;
            case 4:
                return 20;
            case 5:
                return 10;
            case 6:
                return 5;
            case 7: // 7 sao
                return 1;
            case 8:
                return 0.5;
            case 9:
                return 0.5;
            case 10: // 7 sao
                return 0.3;
            case 11: // 7 sao
                return 1;
            case 12: // 7 sao
                return 1;
        }
        return 0;
    }

    private int getCountDaNangCapDo(int level) {
        switch (level) {
            case 0:
                return 3;
            case 1:
                return 7;
            case 2:
                return 11;
            case 3:
                return 17;
            case 4:
                return 23;
            case 5:
                return 35;
            case 6:
                return 50;
            case 7:
                return 70;
        }
        return 0;
    }

    private int getCountDaBaoVe(int level) {
        return level + 1;
    }

    private int getGoldNangCapDo(int level) {
        switch (level) {
            case 0:
                return 50000;
            case 1:
                return 200000;
            case 2:
                return 800000;
            case 3:
                return 5000000;
            case 4:
                return 30000000;
            case 5:
                return 50000000;
            case 6:
                return 80000000;
            case 7:
                return 300000000;
        }
        return 0;
    }

    private float getRationangbt(int lvbt) { // tỉ lệ nâng cấp bông tai c1 và c2
        switch (lvbt) {
            case 1:
                return 50f;
            case 2:
                return 40f;
            case 3:
                return 25f;
        }
        return 0;
    }

    private long getGoldnangbt(int lvbt) {
        return GOLD_BONG_TAI2 + 500_000_000 * lvbt;
    }

    private int getgemdnangbt(int lvbt) {
        return GEM_BONG_TAI2;
    }

    private int getRubydnangbt(int lvbt) {
        return RUBY_BONG_TAI2 + 2000 * lvbt;
    }

    private int getcountmvbtnangbt(int lvbt) {// so luong mảnh vỡ bông tai cần nâng cấp
        switch (lvbt) {
            case 1:
                return 5999;
            case 2:
                return 9999;
            case 3:
                return 29999;
        }
        return 0;
    }

    private boolean checkbongtai(Item item) {
        if (item.template.id == 454 || item.template.id == 921 || item.template.id == 1228 || item.template.id == 1229) {
            return true;
        }
        return false;
    }

    private int lvbt(Item bongtai) {
        switch (bongtai.template.id) {
            case 454:
                return 1;
            case 921:
                return 2;
            case 1228:
                return 3;
            case 1229:
                return 4;
        }

        return 0;

    }

    private int getDiemNangcapChanmenh(int star) {
        switch (star) {
            case 0:
                return 20;
            case 1:
                return 35;
            case 2:
                return 40;
            case 3:
                return 50;
            case 4:
                return 60;
            case 5:
                return 70;
            case 6:
                return 80;
            case 7:
                return 100;
        }
        return 0;
    }

    private int getDaNangcapChanmenh(int star) {
        switch (star) {
            case 0:
                return 30;
            case 1:
                return 40;
            case 2:
                return 40;
            case 3:
                return 45;
            case 4:
                return 50;
            case 5:
                return 60;
            case 6:
                return 65;
            case 7:
                return 80;
        }
        return 0;
    }

    private float getTiLeNangcapChanmenh(int star) {
        switch (star) {
            case 0:
                return 60f;
            case 1:
                return 40f;
            case 2:
                return 30f;
            case 3:
                return 20f;
            case 4:
                return 10f;
            case 5:
                return 8f;
            case 6:
                return 4f;
            case 7:
                return 1f;
        }
        return 0;
    }

    //--------------------------------------------------------------------------check
    private boolean isCoupleItemNangCap(Item item1, Item item2) {
        Item trangBi = null;
        Item daNangCap = null;
        if (item1 != null && item1.isNotNullItem()) {
            if (item1.template.type < 5) {
                trangBi = item1;
            } else if (item1.template.type == 14) {
                daNangCap = item1;
            }
        }
        if (item2 != null && item2.isNotNullItem()) {
            if (item2.template.type < 5) {
                trangBi = item2;
            } else if (item2.template.type == 14) {
                daNangCap = item2;
            }
        }
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.type == 0 && daNangCap.template.id == 223) {
                return true;
            } else if (trangBi.template.type == 1 && daNangCap.template.id == 222) {
                return true;
            } else if (trangBi.template.type == 2 && daNangCap.template.id == 224) {
                return true;
            } else if (trangBi.template.type == 3 && daNangCap.template.id == 221) {
                return true;
            } else if (trangBi.template.type == 4 && daNangCap.template.id == 220) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isCoupleItemNangCapCheck(Item trangBi, Item daNangCap) {
        if (trangBi != null && daNangCap != null) {
            if (trangBi.template.type == 0 && daNangCap.template.id == 223) {
                return true;
            } else if (trangBi.template.type == 1 && daNangCap.template.id == 222) {
                return true;
            } else if (trangBi.template.type == 2 && daNangCap.template.id == 224) {
                return true;
            } else if (trangBi.template.type == 3 && daNangCap.template.id == 221) {
                return true;
            } else if (trangBi.template.type == 4 && daNangCap.template.id == 220) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isDaPhaLe(Item item) {
        return item != null && (item.template.type == 30 || (item.template.id >= 14 && item.template.id <= 20));
    }

    public boolean isTrangBiPhaLeHoa(Item item) {
        if (item != null && item.isNotNullItem()) {
            if (item.template.type <= 5 || item.template.type == 32) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private int getParamDaPhaLe(Item daPhaLe) {
        if (daPhaLe.template.type == 30) {
            return daPhaLe.itemOptions.get(0).param;
        }
        switch (daPhaLe.template.id) {
            case 20:
                return 5; // +5%hp
            case 19:
                return 5; // +5%ki
            case 18:
                return 5; // +5%hp/30s
            case 17:
                return 5; // +5%ki/30s
            case 16:
                return 3; // +3%sđ
            case 15:
                return 2; // +2%giáp
            case 14:
                return 1; // +5%né đòn
            default:
                return -1;
        }
    }

    private int getOptionDaPhaLe(Item daPhaLe) {
        if (daPhaLe.template.type == 30) {
            return daPhaLe.itemOptions.get(0).optionTemplate.id;
        }
        switch (daPhaLe.template.id) {
            case 20:
                return 77;
            case 19:
                return 103;
            case 18:
                return 80;
            case 17:
                return 81;
            case 16:
                return 50;
            case 15:
                return 94;
            case 14:
                return 108;
            default:
                return -1;
        }
    }

    /**
     * Trả về id item c0
     *
     * @param gender
     * @param type
     * @return
     */
    private int getTempIdItemC0(int gender, int type) {
        if (type == 4) {
            return 12;
        }
        switch (gender) {
            case 0:
                switch (type) {
                    case 0:
                        return 0;
                    case 1:
                        return 6;
                    case 2:
                        return 21;
                    case 3:
                        return 27;
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        return 1;
                    case 1:
                        return 7;
                    case 2:
                        return 22;
                    case 3:
                        return 28;
                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        return 2;
                    case 1:
                        return 8;
                    case 2:
                        return 23;
                    case 3:
                        return 29;
                }
                break;
        }
        return -1;
    }

    //Trả về tên đồ c0
    private String getNameItemC0(int gender, int type) {
        if (type == 4) {
            return "Rada cấp 1";
        }
        switch (gender) {
            case 0:
                switch (type) {
                    case 0:
                        return "Áo vải 3 lỗ";
                    case 1:
                        return "Quần vải đen";
                    case 2:
                        return "Găng thun đen";
                    case 3:
                        return "Giầy nhựa";
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        return "Áo sợi len";
                    case 1:
                        return "Quần sợi len";
                    case 2:
                        return "Găng sợi len";
                    case 3:
                        return "Giầy sợi len";
                }
                break;
            case 2:
                switch (type) {
                    case 0:
                        return "Áo vải thô";
                    case 1:
                        return "Quần vải thô";
                    case 2:
                        return "Găng vải thô";
                    case 3:
                        return "Giầy vải thô";
                }
                break;
        }
        return "";
    }

    //--------------------------------------------------------------------------Text tab combine
    private String getTextTopTabCombine(int type) {
        switch (type) {
            case EP_SAO_TRANG_BI:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở nên mạnh mẽ";
            case PHA_LE_HOA_TRANG_BI:
                return "Ta sẽ phù phép\ncho trang bị của ngươi\ntrở thành trang bị pha lê";
            case NANG_CAP_SAO_PHA_LE:
                return "Ta sẽ phù phép\nnâng cấp Sao Pha Lê\nthành cấp 2";
            case CUONG_HOA_LO_SAO_PHA_LE:
                return "Cường hóa\nÔ Sao Pha Lê";
            case TAO_DA_HEMATITE:
                return "Ta sẽ phù phép\ntạo đá hematite";
            case NHAP_NGOC_RONG:
                return "Ta sẽ phù phép\ncho 7 viên Ngọc Rồng\nthành 1 viên Ngọc Rồng cấp cao";
            case NANG_CAP_VAT_PHAM:
                return "Ta sẽ phù phép cho trang bị của ngươi trở lên mạnh mẽ";
            case PHAN_RA_DO_THAN_LINH:
                return "Ta sẽ phân rã\nĐồ Thần cho ngươi thành\nNgọc Kích Hoạt!";
            case HUY_HIEU_HUY_DIET:
                return "Ta sẽ chuyển hoá\nĐồ Thần của ngươi thành\nHuy Hiệu Huỷ Diệt!";
            case XU_THONG_QUAN:
                return "Ta sẽ đưa cho ngươi Xu thông quan\nNếu ngươi mang cho ta đủ 1 set Huỷ Diệt!";
            case NANG_CAP_DO_TS:
                return "Ta sẽ nâng cấp \n  trang bị của người thành\n đồ thiên sứ!";
            case NANG_CAP_SKH_VIP:
                return "Thiên sứ nhờ ta nâng cấp \n  trang bị của người thành\n SKH VIP!";
            case NANG_CAP_BONG_TAI:
                return "Ta sẽ phù phép\ncho bông tai Porata của ngươi\nthành cấp 2, 3, 4";
            case MO_CHI_SO_BONG_TAI:
                return "Ta sẽ phù phép\ncho bông tai Porata cấp 2 của ngươi\ncó 1 chỉ số ngẫu nhiên";
//            case NANG_CAP_LINH_THU:
//                return "Ta sẽ phù phép\ncho Linh Thú cùi bắp của ngươi\ncó 1 chỉ số ngẫu nhiên";
            case CHE_TAO_TRANG_BI_TS:
                return "Chế tạo\nTrang Bị Thiên Sứ";
            case LUYEN_HOA_CHIEN_LINH:
                return "Ta sẽ cùng người luyện hóa\nchiến linh";
            case EP_AN_TRANG_BI:
                return "Ép Ấn Trang Bị";
            case NGUYET_AN:
                return "Ép Ấn Trang Bị\nNguyệt ấn";
            case DOI_DIEM:
                return "Thức Ăn";
            case NANG_CAP_KICH_HOAT:
                return "Ta sẽ phù phép\nchế tạo trang bị Huỷ Diệt\nthành trang bị Kích Hoạt";
            case GIAM_DINH_SACH:
                return "Ta sẽ phù phép\ngiám định sách đó cho ngươi";
            case TAY_SACH:
                return "Ta sẽ phù phép\ntẩy sách đó cho ngươi";
            case NANG_CAP_SACH_TUYET_KY:
                return "Ta sẽ phù phép\nnâng cấp Sách Tuyệt Kỹ cho ngươi";
            case HOI_PHUC_SACH:
                return "Ta sẽ phù phép\nphục hồi sách cho ngươi";
            case PHAN_RA_SACH:
                return "Ta sẽ phù phép\nphân rã sách đó cho ngươi";
            case NANG_CAP_CHAN_MENH:
                return "Ta sẽ Nâng cấp\nChân Mệnh của ngươi\ncao hơn một bậc";
            default:
                return "";
        }
    }

    private String getTextInfoTabCombine(int type) {
        switch (type) {
            case EP_SAO_TRANG_BI:
                return "Vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc rađa) có ô đặt sao pha lê\nChọn loại sao pha lê\nSau đó chọn 'Nâng cấp'";
            case PHA_LE_HOA_TRANG_BI:
                return "Vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nSau đó chọn 'Nâng cấp'";
            case NANG_CAP_SAO_PHA_LE:
                return "Vào hành trang\nChọn đá Hematite\nChọn loại sao pha lê (cấp 1)\nSau đó chọn 'Nâng cấp'";
            case CUONG_HOA_LO_SAO_PHA_LE:
                return "Vào hành trang\nChọn trang bị có Ô sao thứ 8 trở lên chưa cường hóa\nChọn đá Hematite\nChọn dùi đục\nSau đó chọn 'Cường hóa'";
            case TAO_DA_HEMATITE:
                return "Vào hành trang\nChọn 5 sao pha lê trắng đen cùng màu\nChọn 'Tạo đá Hematite'";
            case NHAP_NGOC_RONG:
                return "Vào hành trang\nChọn 7 viên ngọc cùng sao\nSau đó chọn 'Làm phép'";
            case NANG_CAP_VAT_PHAM:
                return "vào hành trang\nChọn trang bị\n(Áo, quần, găng, giày hoặc rađa)\nChọn loại đá để nâng cấp\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case PHAN_RA_DO_THAN_LINH:
                return "Vào hành trang\nChọn item Đồ Thần\nTừ 1 đến 3 món không trùng nhau\n"
                        + "Càng nhiều thì tỉ lệ thành công càng cao\n"
                        + "Mỗi món ta lấy rẻ 200Tr\nKhuyễn mãi đến món thứ 3 giá chỉ còn 500tr\n"
                        + "Sau đó chọn 'Nâng Cấp'";
            case HUY_HIEU_HUY_DIET:
                return "Ngươi mang cho ta\n1 Nhẫn Thần Linh\n2 Viên Ngọc Rồng 3s\nĐến đây ta chuyển hoá cho ngươi!\n";
            case XU_THONG_QUAN:
                return "Chọn đủ 5 món Huỷ diệt cùng hành tinh\n"
                        + "Bỏ vào theo thứ tự\n"
                        + "(Áo, quần, găng, giày hoặc nhẫn)\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case NANG_CAP_DO_TS:
                return "vào hành trang\nChọn 2 trang bị hủy diệt bất kì\nkèm 1 món đồ thần linh\n và 5 mảnh thiên sứ\n "
                        + "sẽ cho ra đồ thiên sứ từ 0-15% chỉ số"
                        + "Sau đó chọn 'Nâng Cấp'";
            case NANG_CAP_SKH_VIP:
                return "vào hành trang\nChọn 1 trang bị thiên sứ\n1 trang bị hủy diệt bất kì\nChọn tiếp ngẫu nhiên 2 món SKH thường \n "
                        + " đồ SKH VIP sẽ cùng loại \n với đồ thiên sứ!"
                        + "Chỉ cần chọn 'Nâng Cấp'";
            case NANG_CAP_BONG_TAI:
                return "Vào hành trang\nChọn loại bông tai tương ứng\n"
                        + "Bông tai Potara + 5999 mảnh vỡ bông tai\n"
                        + "Bông tai cấp 2 + 9999 mảnh vỡ bông tai\n"
                        + "Bông tai cấp 3 + 29999 mảnh vỡ bông tai\n"
                        + "Sau đó chọn 'Nâng cấp'\n"
                        + " Xịt mất 10% mảnh vỡ ";
            case MO_CHI_SO_BONG_TAI:
                return "Vào hành trang\nChọn bông tai Porata\nChọn mảnh hồn bông tai số lượng 99 cái\nvà đá xanh lam để nâng cấp\nSau đó chọn 'Nâng cấp'";
            case CHE_TAO_TRANG_BI_TS:
                return "Cần 1 công thức vip\nMảnh trang bị tương ứng\n"
                        + "Số Lượng 99\n"
                        + "1 Món Hủy Diệt Bất Kì"
                        + "Có thể thêm\nĐá nâng cấp để tăng tỉ lệ chế tạo\n"
                        + "Đá may mắn để tăng tỉ lệ của\nCác chỉ số cơ bản của trang bị\n"
                        + "Sau đó chọn 'Nâng cấp'";
            case EP_AN_TRANG_BI:
                return "Vào hành trang\nChọn Trang Bị Ấn\nChọn Đá Ngũ Sắc Số Lượng 25\nThêm 1 Thỏi Vàng Vào\nSau đó chọn 'Nâng cấp'";
            case NGUYET_AN:
                return "Vào hành trang\nChọn Trang Bị\nChọn Đá Nâng Cấp Số Lượng 99 \nMấy Viên Kiểu Titan Ruby Các Thứ Ấy\nSau đó chọn 'Nâng cấp'\nCHÚ Ý KHÔNG DÙNG\nTRANG BỊ KÍCH HOẠT";
            case DOI_DIEM:
                return "Vào hành trang\nChọn x99 Thức Ăn\nSau đó chọn 'Nâng cấp'";
            case NANG_CAP_KICH_HOAT:
                return "Vào hành trang\nChọn 1 trang bị Huỷ Diệt\nSau đó chọn 'Nâng cấp'";
            case GIAM_DINH_SACH:
                return "Vào hành trang chọn\n1 sách cần giám định";
            case TAY_SACH:
                return "Vào hành trang chọn\n1 sách cần tẩy";
            case NANG_CAP_SACH_TUYET_KY:
                return "Vào hành trang chọn\nSách Tuyệt Kỹ 1 cần nâng cấp và 10 Kìm bấm giấy";
            case HOI_PHUC_SACH:
                return "Vào hành trang chọn\nCác Sách Tuyệt Kỹ cần phục hồi";
            case PHAN_RA_SACH:
                return "Vào hành trang chọn\n1 sách cần phân rã";
            case NANG_CAP_CHAN_MENH:
                return "Vào hành trang\nChọn Chân mệnh muốn nâng cấp\nChọn Đá Chân Mệnh\n"
                        + "Sau đó chọn 'Nâng cấp'\n";
            default:
                return "";
        }
    }

}
