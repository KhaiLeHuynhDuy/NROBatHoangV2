package Services.func;

import com.girlkun.database.GirlkunDB;
import Consts.ConstNpc;
import Daos.PlayerDAO;
import Item.Item;
import Maps.Zone;
import MiniGame.LuckyNumber.LuckyNumberService;
import MiniGame.cost.LuckyNumberCost;
import NPC.Npc;
import NPC.NpcManager;
import Player.Inventory;
import Player.Player;
import network.Message;
import Server.Client;
import Services.Service;
import Services.GiftService;
import Services.InventoryServiceNew;
import Services.ItemService;
import Services.NpcService;
import Utils.Util;

import java.util.HashMap;
import java.util.Map;
import network.inetwork.ISession;

public class Input {

    public static String LOAI_THE;
    public static String MENH_GIA;
    private static final Map<Integer, Object> PLAYER_ID_OBJECT = new HashMap<Integer, Object>();
    public static final int DONATE_CS = 523;
    public static final int CHANGE_PASSWORD = 500;
    public static final int GIFT_CODE = 501;
    public static final int FIND_PLAYER = 502;
    public static final int CHANGE_NAME = 503;
    public static final int CHOOSE_LEVEL_BDKB = 504;
    public static final int CHOOSE_LEVEL_CDRD = 560;
    public static final int CHOOSE_LEVEL_KGHD = 522;
    public static final int SELECT_LUCKYNUMBER = 524;
    public static final int NAP_THE = 505;
    public static final int CHANGE_NAME_BY_ITEM = 506;
    public static final int GIVE_IT = 507;

    public static final int QUY_DOI_COIN = 508;
    public static final int QUY_DOI_HONG_NGOC = 509;

    public static final int TAI = 510;
    public static final int XIU = 511;

    public static final int SEND_ITEM_OP = 512;
    public static final int BUFF_ITEM_PLAYER = 513;
    public static final int SEND_ITEM_SKH = 556;
    public static final int SEND_ITEM_OP_VIP = 557;

    public static final int MUA_CHIP = 514;
    public static final int DOI_CHIP = 515;

    public static final int CREAT_EMAIL = 516;
    public static final int CHECK_DONE_GMAIL = 517;

    public static final int DOI_THOI_VANG = 518;

    public static final byte NUMERIC = 0;
    public static final byte ANY = 1;
    public static final byte PASSWORD = 2;
    public static final int UseGold = 3;
    public static final int XIU_taixiu = 5164;
    public static final int TAI_taixiu = 5165;

    public static final int GIA_MUA_CHIP = 10; // Giá bán
    public static final int GIA_BAN_CHIP = 9; // Giá bán

    private static Input intance;

    private Input() {

    }

    public static Input gI() {
        if (intance == null) {
            intance = new Input();
        }
        return intance;
    }

    public void doInput(Player player, Message msg) {
        try {
            String[] text = new String[msg.reader().readByte()];
            for (int i = 0; i < text.length; i++) {
                text[i] = msg.reader().readUTF();
            }
            switch (player.iDMark.getTypeInput()) {
                case TAI_taixiu:
                    int sotvxiu1 = Integer.valueOf(text[0]);
                    Item thoivang = InventoryServiceNew.gI().findItemBag(player, 457);
                    try {
                        if (sotvxiu1 >= 10 && sotvxiu1 <= 1000) {
                            if (thoivang != null && thoivang.quantity >= sotvxiu1) {
                                InventoryServiceNew.gI().subQuantityItemsBag(player, thoivang, sotvxiu1);
                                player.goldTai += sotvxiu1;
                                TaiXiu.gI().goldTai += sotvxiu1;
                                Service.gI().sendThongBao(player, "Bạn đã đặt " + Util.format(sotvxiu1) + " Thỏi Vàng vào TÀI");
                                TaiXiu.gI().addPlayerTai(player);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.getInstance().sendMoney(player);
                                PlayerDAO.updatePlayer(player);
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ Thỏi Vàng để chơi.");
                            }
                        } else {
                            Service.gI().sendThongBao(player, "Cược ít nhất 10 - nhiều nhất 1.000 Thỏi Vàng");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Service.gI().sendThongBao(player, "Lỗi.");
                    }
                    break;
                case XIU_taixiu:
                    int sotvxiu2 = Integer.valueOf(text[0]);
                    Item thoivang1 = InventoryServiceNew.gI().findItemBag(player, 457);
                    try {
                        if (sotvxiu2 >= 10 && sotvxiu2 <= 1000) {
                            if (thoivang1 != null && thoivang1.quantity >= sotvxiu2) {
                                InventoryServiceNew.gI().subQuantityItemsBag(player, thoivang1, sotvxiu2);
                                player.goldXiu += sotvxiu2;
                                TaiXiu.gI().goldXiu += sotvxiu2;
                                Service.gI().sendThongBao(player, "Bạn đã đặt " + Util.format(sotvxiu2) + " Thỏi Vàng vào XỈU");
                                TaiXiu.gI().addPlayerXiu(player);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.getInstance().sendMoney(player);
                                PlayerDAO.updatePlayer(player);
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ Thỏi Vàng để chơi.");
                            }
                        } else {
                            Service.gI().sendThongBao(player, "Cược ít nhất 10 - nhiều nhất 1.000 Thỏi Vàng");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Service.gI().sendThongBao(player, "Lỗi.");
                        System.out.println("nnnnn2  ");
                    }
                    break;
                case SELECT_LUCKYNUMBER: {
                    int number = Integer.parseInt(text[0]);
                    LuckyNumberService.addNumber(player, number);
                }
                break;
                case DONATE_CS:
                    int csbang = Integer.parseInt(text[0]);
                    Item cscanhan = InventoryServiceNew.gI().findItemBag(player, 1261);
                    if (cscanhan == null && player.clanMember.memberPoint < 1) {
                        Service.gI().sendThongBao(player, "Số điểm capsule bản thân không đủ để thực hiện");
                        break;
                    }
                    InventoryServiceNew.gI().subQuantityItemsBag(player, cscanhan, csbang);
                    InventoryServiceNew.gI().sendItemBags(player);
                    Service.gI().sendMoney(player);
                    player.clanMember.memberPoint -= csbang;
                    player.clan.capsuleClan += csbang;
                    player.clanMember.clanPoint += csbang;
                    Service.gI().sendThongBao(player, "bạn đã quyên góp " + csbang + " điểm bang");
                    break;
                case GIVE_IT:
                    if (player.isAdmin()) {
                        int idItemBuff = Integer.parseInt(text[1]);
                        int quantityItemBuff = Integer.parseInt(text[2]);
                        Player pBuffItem = Client.gI().getPlayer(text[0]);
                        if (pBuffItem != null) {
                            String txtBuff = "Buff to player: " + pBuffItem.name + "\b";
                            if (idItemBuff == -1) {
                                pBuffItem.inventory.gold = Math.min(pBuffItem.inventory.gold + (long) quantityItemBuff, (Inventory.LIMIT_GOLD + pBuffItem.limitgold));
                                txtBuff += quantityItemBuff + " vàng\b";
                                Service.getInstance().sendMoney(player);
                            } else if (idItemBuff == -2) {
                                pBuffItem.inventory.gem = Math.min(pBuffItem.inventory.gem + quantityItemBuff, 2000000000);
                                txtBuff += quantityItemBuff + " ngọc\b";
                                Service.getInstance().sendMoney(player);
                            } else if (idItemBuff == -3) {
                                pBuffItem.inventory.ruby = Math.min(pBuffItem.inventory.ruby + quantityItemBuff, 2000000000);
                                txtBuff += quantityItemBuff + " ngọc khóa\b";
                                Service.getInstance().sendMoney(player);
                            } else {
                                Item itemBuffTemplate = ItemService.gI().createNewItem((short) idItemBuff);
                                itemBuffTemplate.quantity = quantityItemBuff;
                                InventoryServiceNew.gI().addItemBag(pBuffItem, itemBuffTemplate);
                                InventoryServiceNew.gI().sendItemBags(pBuffItem);
                                txtBuff += "x" + quantityItemBuff + " " + itemBuffTemplate.template.name + "\b";
                            }
                            NpcService.gI().createTutorial(player, 24, txtBuff);
                            if (player.id != pBuffItem.id) {
                                NpcService.gI().createTutorial(pBuffItem, 24, txtBuff);
                            }
                        } else {
                            Service.getInstance().sendThongBao(player, "Player không online");
                        }
                        break;
                    }
                    break;
                case SEND_ITEM_OP:
                    if (player.isAdmin()) {
                        int idItemBuff = Integer.parseInt(text[1]);
                        int idOptionBuff = Integer.parseInt(text[2]);
                        int slOptionBuff = Integer.parseInt(text[3]);
                        int slItemBuff = Integer.parseInt(text[4]);
                        Player pBuffItem = Client.gI().getPlayer(text[0]);
                        if (pBuffItem != null) {
                            String txtBuff = "Buff to player: " + pBuffItem.name + "\b";
                            if (idItemBuff == -1) {
                                pBuffItem.inventory.gold = Math.min(pBuffItem.inventory.gold + (long) slItemBuff, Inventory.LIMIT_GOLD);
                                txtBuff += slItemBuff + " vàng\b";
                                Service.getInstance().sendMoney(player);
                            } else if (idItemBuff == -2) {
                                pBuffItem.inventory.gem = Math.min(pBuffItem.inventory.gem + slItemBuff, 2000000000);
                                txtBuff += slItemBuff + " ngọc\b";
                                Service.getInstance().sendMoney(player);
                            } else if (idItemBuff == -3) {
                                pBuffItem.inventory.ruby = Math.min(pBuffItem.inventory.ruby + slItemBuff, 2000000000);
                                txtBuff += slItemBuff + " ngọc khóa\b";
                                Service.getInstance().sendMoney(player);
                            } else {
                                Item itemBuffTemplate = ItemService.gI().createNewItem((short) idItemBuff);
                                itemBuffTemplate.itemOptions.add(new Item.ItemOption(idOptionBuff, slOptionBuff));
                                itemBuffTemplate.quantity = slItemBuff;
                                txtBuff += "x" + slItemBuff + " " + itemBuffTemplate.template.name + "\b";
                                InventoryServiceNew.gI().addItemBag(pBuffItem, itemBuffTemplate);
                                InventoryServiceNew.gI().sendItemBags(pBuffItem);
                            }
                            NpcService.gI().createTutorial(player, 24, txtBuff);
                            if (player.id != pBuffItem.id) {
                                NpcService.gI().createTutorial(player, 24, txtBuff);
                            }
                        } else {
                            Service.getInstance().sendThongBao(player, "Player không online");
                        }
                        break;
                    }
                    break;
                case SEND_ITEM_SKH:
                    if (player.isAdmin()) {
                        int idItemBuff = Integer.parseInt(text[1]);
                        int idOptionSKH = Integer.parseInt(text[2]);
                        int idOptionBuff = Integer.parseInt(text[3]);
                        int slOptionBuff = Integer.parseInt(text[4]);
                        int slItemBuff = Integer.parseInt(text[5]);
                        Player pBuffItem = Client.gI().getPlayer(text[0]);
                        if (pBuffItem != null) {
                            String txtBuff = "Buff to player: " + pBuffItem.name + "\b";
                            if (idItemBuff == -1) {
                                pBuffItem.inventory.gold = Math.min(pBuffItem.inventory.gold + (long) slItemBuff, Inventory.LIMIT_GOLD);
                                txtBuff += slItemBuff + " vàng\b";
                                Service.getInstance().sendMoney(player);
                            } else if (idItemBuff == -2) {
                                pBuffItem.inventory.gem = Math.min(pBuffItem.inventory.gem + slItemBuff, 2000000000);
                                txtBuff += slItemBuff + " ngọc\b";
                                Service.getInstance().sendMoney(player);
                            } else if (idItemBuff == -3) {
                                pBuffItem.inventory.ruby = Math.min(pBuffItem.inventory.ruby + slItemBuff, 2000000000);
                                txtBuff += slItemBuff + " ngọc khóa\b";
                                Service.getInstance().sendMoney(player);
                            } else {
                                Item itemBuffTemplate = ItemService.gI().createNewItem((short) idItemBuff);
                                itemBuffTemplate.itemOptions.add(new Item.ItemOption(idOptionSKH, 0));
                                if (idOptionSKH == 127) {
                                    itemBuffTemplate.itemOptions.add(new Item.ItemOption(139, 0));
                                } else if (idOptionSKH == 128) {
                                    itemBuffTemplate.itemOptions.add(new Item.ItemOption(140, 0));
                                } else if (idOptionSKH == 129) {
                                    itemBuffTemplate.itemOptions.add(new Item.ItemOption(141, 0));
                                } else if (idOptionSKH == 130) {
                                    itemBuffTemplate.itemOptions.add(new Item.ItemOption(142, 0));
                                } else if (idOptionSKH == 131) {
                                    itemBuffTemplate.itemOptions.add(new Item.ItemOption(143, 0));
                                } else if (idOptionSKH == 132) {
                                    itemBuffTemplate.itemOptions.add(new Item.ItemOption(144, 0));
                                } else if (idOptionSKH == 133) {
                                    itemBuffTemplate.itemOptions.add(new Item.ItemOption(136, 0));
                                } else if (idOptionSKH == 134) {
                                    itemBuffTemplate.itemOptions.add(new Item.ItemOption(137, 0));
                                } else if (idOptionSKH == 135) {
                                    itemBuffTemplate.itemOptions.add(new Item.ItemOption(138, 0));
                                }
                                itemBuffTemplate.itemOptions.add(new Item.ItemOption(30, 0));
                                itemBuffTemplate.itemOptions.add(new Item.ItemOption(idOptionBuff, slOptionBuff));
                                itemBuffTemplate.quantity = slItemBuff;
                                txtBuff += "x" + slItemBuff + " " + itemBuffTemplate.template.name + "\b";
                                InventoryServiceNew.gI().addItemBag(pBuffItem, itemBuffTemplate);
                                InventoryServiceNew.gI().sendItemBags(pBuffItem);
                            }
                            NpcService.gI().createTutorial(player, 24, txtBuff);
                            if (player.id != pBuffItem.id) {
                                NpcService.gI().createTutorial(player, 24, txtBuff);
                            }
                        } else {
                            Service.getInstance().sendThongBao(player, "Player không online");
                        }
                        break;

                    }
                    break;
                case SEND_ITEM_OP_VIP:
                    if (player.isAdmin()) {
                        Player pBuffItem = Client.gI().getPlayer(text[0]);
                        int idItemBuff = Integer.parseInt(text[1]);
                        String idOptionBuff = text[2].trim();

                        int slItemBuff = Integer.parseInt(text[3]);

                        try {
                            if (pBuffItem != null) {
                                String txtBuff = "Buff to player: " + pBuffItem.name + "\b";

                                Item itemBuffTemplate = ItemService.gI().createNewItem((short) idItemBuff, slItemBuff);
                                if (!idOptionBuff.isEmpty()) {
                                    String arr[] = idOptionBuff.split("v");
                                    for (int i = 0; i < arr.length; i++) {
                                        String arr2[] = arr[i].split("-");
                                        int idoption = Integer.parseInt(arr2[0].trim());
                                        int param = Integer.parseInt(arr2[1].trim());
                                        itemBuffTemplate.itemOptions.add(new Item.ItemOption(idoption, param));
                                    }

                                }
                                txtBuff += "x" + slItemBuff + " " + itemBuffTemplate.template.name + "\b";
                                InventoryServiceNew.gI().addItemBag(pBuffItem, itemBuffTemplate);
                                InventoryServiceNew.gI().sendItemBags(pBuffItem);
                                NpcService.gI().createTutorial(player, 24, txtBuff);
                                if (player.id != pBuffItem.id) {
                                    NpcService.gI().createTutorial(pBuffItem, 24, txtBuff);
                                }
                            } else {
                                Service.getInstance().sendThongBao(player, "Player không online");
                            }
                        } catch (Exception e) {
                            Service.getInstance().sendThongBao(player, "Đã có lỗi xảy ra vui lòng thử lại");
                        }

                    }
                    break;
                case CHANGE_PASSWORD:
                    Service.gI().changePassword(player, text[0], text[1], text[2]);
                    break;
                case CREAT_EMAIL:
                    Service.gI().creatEmail(player, text[0]);
                    break;
                case CHECK_DONE_GMAIL:
                    Service.gI().checkDoneGmail(player, text[0]);
                    break;
                case GIFT_CODE:
                    GiftService.gI().giftCode(player, text[0]);
                    break;
                case FIND_PLAYER:
                    Player pl = Client.gI().getPlayer(text[0]);
                    if (pl != null) {
                        NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_FIND_PLAYER, -1, "Ngài muốn..?",
                                new String[]{"Đi tới\n" + pl.name, "Gọi " + pl.name + "\ntới đây", "Đổi tên", "Ban", "Kick", "Ấy Cho Nó"},
                                pl);
                    } else {
                        Service.gI().sendThongBao(player, "Người chơi không tồn tại hoặc đang offline");
                    }
                    break;
                case CHANGE_NAME: {
                    Player plChanged = (Player) PLAYER_ID_OBJECT.get((int) player.id);
                    if (plChanged != null) {
                        if (GirlkunDB.executeQuery("select * from player where name = ?", text[0]).next()) {
                            Service.gI().sendThongBao(player, "Tên nhân vật đã tồn tại");
                        } else {
                            plChanged.name = text[0];
                            GirlkunDB.executeUpdate("update player set name = ? where id = ?", plChanged.name, plChanged.id);
                            Service.gI().player(plChanged);
                            Service.gI().Send_Caitrang(plChanged);
                            Service.gI().sendFlagBag(plChanged);
                            Zone zone = plChanged.zone;
                            ChangeMapService.gI().changeMap(plChanged, zone, plChanged.location.x, plChanged.location.y);
                            Service.gI().sendThongBao(plChanged, "Chúc mừng bạn đã có cái tên mới đẹp đẽ hơn tên ban đầu");
                            Service.gI().sendThongBao(player, "Đổi tên người chơi thành công");
                        }
                    }
                }
                break;
                case CHANGE_NAME_BY_ITEM: {
                    if (player != null) {
                        if (GirlkunDB.executeQuery("select * from player where name = ?", text[0]).next()) {
                            Service.gI().sendThongBao(player, "Tên nhân vật đã tồn tại");
                            createFormChangeNameByItem(player);
                        } else {
                            Item theDoiTen = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 2006);
                            if (theDoiTen == null) {
                                Service.gI().sendThongBao(player, "Không tìm thấy thẻ đổi tên");
                            } else {
                                InventoryServiceNew.gI().subQuantityItemsBag(player, theDoiTen, 1);
                                player.name = text[0];
                                GirlkunDB.executeUpdate("update player set name = ? where id = ?", player.name, player.id);
                                Service.gI().player(player);
                                Service.gI().Send_Caitrang(player);
                                Service.gI().sendFlagBag(player);
                                Zone zone = player.zone;
                                ChangeMapService.gI().changeMap(player, zone, player.location.x, player.location.y);
                                Service.gI().sendThongBao(player, "Chúc mừng bạn đã có cái tên mới đẹp đẽ hơn tên ban đầu");
                            }
                        }
                    }
                }
                break;
                case DOI_THOI_VANG:
                    int ratioGold = 30;
                    int coinGold = 10000;
                    int vndTrade = Integer.parseInt(text[0]);
                    int goldTrade = vndTrade / coinGold * ratioGold;
                    if (vndTrade <= 0 || vndTrade > 5000000) {
                        Service.getInstance().sendThongBao(player, "Quá giới hạn, mỗi lần chỉ được tối đa 5,000,000 VNĐ");
                    } else if (player.getSession().vnd >= vndTrade) {
                        PlayerDAO.subvnd(player, vndTrade);
                        Item thoiVang = ItemService.gI().createNewItem((short) 457, goldTrade);
                        InventoryServiceNew.gI().addItemBag(player, thoiVang);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendThongBaoOK(player, "Bạn nhận được " + goldTrade
                                + " " + thoiVang.template.name); // Thông báo số vàng nhận được
                        GirlkunDB.executeUpdate("update account set active = 1 where id = ? and username = ?",
                                player.getSession().userId, player.getSession().uu);
                        player.iDMark.setActive(true);
                        player.pointPvp += goldTrade;
                    } else {
                        Service.getInstance().sendThongBao(player, "Số tiền của bạn là " + player.getSession().vnd + " không đủ để quy"
                                + " đổi. Bạn cần thêm " + (vndTrade - player.getSession().vnd) + " VNĐ để đổi được "
                                + vndTrade / coinGold * ratioGold + " thỏi vàng.");
                    }
                    break;
                case MUA_CHIP:
                    int soChipBan = Integer.parseInt(text[0]);
                    if (soChipBan <= 0) {
                        Service.getInstance().sendThongBaoOK(player, "Tôi xin lỗi số lượng quá ít!");
                    } else {
                        TransactionService.gI().cancelTrade(player);
                        Item tv2 = null;
                        for (Item item : player.inventory.itemsBag) {
                            if (item.isNotNullItem() && item.template.id == 457) {
                                tv2 = item;
                                break;
                            }
                        }
                        int soTV = GIA_MUA_CHIP * soChipBan;
                        try {
                            if (tv2 != null && tv2.quantity >= soTV) {
                                InventoryServiceNew.gI().subQuantityItemsBag(player, tv2, soTV);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Item Chip = ItemService.gI().createNewItem((short) 1179, soChipBan);
                                InventoryServiceNew.gI().addItemBag(player, Chip);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn đã mua thành công!!");
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ Thỏi Vàng để mua!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Service.gI().sendThongBao(player, "Lỗi!!");
                        }
                    }
                    break;
                case DOI_CHIP:
                    int soChipMua = Integer.parseInt(text[0]);
                    if (soChipMua <= 0) {
                        Service.getInstance().sendThongBaoOK(player, "Tôi xin lỗi số lượng quá ít!");
                    } else {
                        TransactionService.gI().cancelTrade(player);
                        Item soChip = null;
                        for (Item item : player.inventory.itemsBag) {
                            if (item.isNotNullItem() && item.template.id == 1179) {
                                soChip = item;
                                break;
                            }
                        }
                        try {
                            if (soChip != null && soChip.quantity >= soChipMua) {
                                int soTV = GIA_BAN_CHIP * soChipMua;
                                InventoryServiceNew.gI().subQuantityItemsBag(player, soChip, soChipMua);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Item thoiVang = ItemService.gI().createNewItem((short) 457, soTV);
                                InventoryServiceNew.gI().addItemBag(player, thoiVang);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn đã đổi thành công!!");
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ Chip để đổi!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Service.gI().sendThongBao(player, "Lỗi!!");
                        }
                    }
                    break;
                case TAI:
                    int soChipTai = Integer.parseInt(text[0]);
                    if (soChipTai <= 0) {
                        Service.getInstance().sendThongBaoOK(player, "?");
                    } else {
                        TransactionService.gI().cancelTrade(player);
                        Item ChipCassinoTai = null;
                        for (Item item : player.inventory.itemsBag) {
                            if (item.isNotNullItem() && item.template.id == 1179) {
                                ChipCassinoTai = item;
                                break;
                            }
                        }
                        try {
                            if (ChipCassinoTai != null && ChipCassinoTai.quantity >= soChipTai) {
                                InventoryServiceNew.gI().subQuantityItemsBag(player, ChipCassinoTai, soChipTai);
                                InventoryServiceNew.gI().sendItemBags(player);
                                int TimeSeconds = 10;
                                Service.gI().sendThongBao(player, "Chờ 10 giây để biết kết quả.");
                                while (TimeSeconds > 0) {
                                    TimeSeconds--;
                                    Thread.sleep(1000);
                                }
                                int x, y, z;
                                if (Util.isTrue(30, 100)) { // Tỉ lệ bú 30%
                                    x = Util.nextInt(1, 2);
                                    y = Util.nextInt(1, 3);
                                    z = Util.nextInt(1, 3);
                                } else {
                                    x = Util.nextInt(3, 6);
                                    y = Util.nextInt(4, 6);
                                    z = Util.nextInt(4, 6);
                                }
                                int tong = (x + y + z);
                                if (tong > 10) {
                                    if (player != null) {
                                        Item soChipThang = ItemService.gI().createNewItem((short) 1179);
                                        soChipThang.quantity = Math.round(soChipTai * 2);
                                        InventoryServiceNew.gI().addItemBag(player, soChipThang);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        Service.gI().sendThongBaoOK(player, "Kết quả" + "\nSố hệ thống quay ra: " + x + " "
                                                + y + " " + z + "\nTổng là : " + tong + "\nBạn đã cược : " + soChipTai
                                                + " Chip vào Tài" + "\nKết quả : Tài" + "\n\nVề bờ");
                                        return;
                                    }
                                } else if (x == y && x == z) {
                                    if (player != null) {
                                        Service.gI().sendThongBaoOK(player, "Kết quả" + "Số hệ thống quay ra: "
                                                + x + " " + y + " " + z + "\nTổng là : " + tong + "\nBạn đã cược : "
                                                + soChipTai + " Chip vào Tài" + "\nKết quả : Tam hoa" + "\nCòn cái nịt.");
                                        return;
                                    }
                                } else if (4 <= tong && tong <= 10) {
                                    if (player != null) {
                                        Service.gI().sendThongBaoOK(player, "Kết quả" + "\nSố hệ thống quay ra là :"
                                                + " " + x + " " + y + " " + z + "\nTổng là : " + tong + "\nBạn đã cược : "
                                                + soChipTai + " Chip vào Tài" + "\nKết quả : Xỉu" + "\nCòn cái nịt!");
                                        return;
                                    }
                                }
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ Chip để chơi!!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Service.gI().sendThongBao(player, "Lỗi!!");
                        }
                    }
                    break;
                case XIU:
                    int soChipXiu = Integer.valueOf(text[0]);
                    if (soChipXiu <= 0) {
                        Service.getInstance().sendThongBao(player, "?");
                    } else {
                        TransactionService.gI().cancelTrade(player);
                        Item ChipCassinoXiu = null;
                        for (Item item : player.inventory.itemsBag) {
                            if (item.isNotNullItem() && item.template.id == 1179) {
                                ChipCassinoXiu = item;
                                break;
                            }
                        }
                        try {
                            if (ChipCassinoXiu != null && ChipCassinoXiu.quantity >= soChipXiu) {
                                InventoryServiceNew.gI().subQuantityItemsBag(player, ChipCassinoXiu, soChipXiu);
                                InventoryServiceNew.gI().sendItemBags(player);
                                int TimeSeconds = 10;
                                Service.gI().sendThongBao(player, "Chờ 10 giây để biết kết quả.");
                                while (TimeSeconds > 0) {
                                    TimeSeconds--;
                                    Thread.sleep(1000);
                                }
                                int x, y, z;
                                if (Util.isTrue(30, 100)) { // Tỉ lệ bú 30%
                                    x = Util.nextInt(1, 2);
                                    y = Util.nextInt(1, 3);
                                    z = Util.nextInt(1, 3);
                                } else {
                                    x = Util.nextInt(3, 6);
                                    y = Util.nextInt(4, 6);
                                    z = Util.nextInt(4, 6);
                                }
                                int tong = (x + y + z);
                                if (tong > 10) {
                                    if (player != null) {
                                        Service.gI().sendThongBaoOK(player, "Kết quả" + "\nSố hệ thống quay ra là :"
                                                + " " + x + " " + y + " " + z + "\nTổng là : " + tong + "\nBạn đã cược : "
                                                + soChipXiu + " Chip vào Xỉu" + "\nKết quả : Tài" + "\nCòn cái nịt!");
                                        return;
                                    }
                                } else if (x == y && x == z) {
                                    if (player != null) {
                                        Service.gI().sendThongBaoOK(player, "Kết quả" + "Số hệ thống quay ra : "
                                                + x + " " + y + " " + z + "\nTổng là : " + tong + "\nBạn đã cược : "
                                                + soChipXiu + " Chip vào Xỉu" + "\nKết quả : Tam hoa" + "\nCòn cái nịt.");
                                        return;
                                    }
                                } else if (4 <= tong && tong <= 10) {
                                    if (player != null) {
                                        Item soChipThang = ItemService.gI().createNewItem((short) 1179);
                                        soChipThang.quantity = Math.round(soChipXiu * 2);
                                        InventoryServiceNew.gI().addItemBag(player, soChipThang);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        Service.gI().sendThongBaoOK(player, "Kết quả" + "\nSố hệ thống quay ra : " + x + " "
                                                + y + " " + z + "\nTổng là : " + tong + "\nBạn đã cược : " + soChipXiu
                                                + " Chip vào Xỉu" + "\nKết quả : Xỉu" + "\n\nVề bờ");
                                        return;
                                    }
                                }
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ Chip để chơi!!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Service.gI().sendThongBao(player, "Lỗi.");
                        }
                    }
                case CHOOSE_LEVEL_BDKB:
                    int level = Integer.parseInt(text[0]);
                    if (level >= 1 && level <= 110) {
                        Npc npc = NpcManager.getByIdAndMap(ConstNpc.QUY_LAO_KAME, player.zone.map.mapId);
                        if (npc != null) {
                            npc.createOtherMenu(player, ConstNpc.MENU_ACCEPT_GO_TO_BDKB,
                                    "Con có chắc chắn muốn tới bản đồ kho báu cấp độ " + level + "?",
                                    new String[]{"Đồng ý", "Từ chối"}, level);
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                    }
                    break;
                case CHOOSE_LEVEL_CDRD:
                    level = Integer.parseInt(text[0]);
                    if (level >= 1 && level <= 110) {
                        Npc npc = NpcManager.getByIdAndMap(ConstNpc.THAN_VU_TRU, player.zone.map.mapId);
                        if (npc != null) {
                            npc.createOtherMenu(player, 3,
                                    "Con có chắc muốn đến\ncon đường rắn độc cấp độ " + level + " ?",
                                    new String[]{"Đồng ý", "Từ chối"}, level);
                        }
                    }
                    break;
                case CHOOSE_LEVEL_KGHD:
                    level = Integer.parseInt(text[0]);
                    if (level >= 1 && level <= 110) {
                        Npc npc = NpcManager.getByIdAndMap(ConstNpc.POPO, player.zone.map.mapId);
                        if (npc != null) {
                            npc.createOtherMenu(player, 2,
                                    "Cậu có chắc muốn đến\nDestron Gas cấp độ " + level + " ?",
                                    new String[]{"Đồng ý", "Từ chối"}, level);
                        }
                    }
                    break;
                case BUFF_ITEM_PLAYER:
                    if (player.isAdmin()) {
                        Player namePlayer = Client.gI().getPlayer(text[0]);
                        int idItem = Integer.parseInt(text[1]);
                        String idOption = text[2].trim();
                        String idParam = text[3].trim();
                        int soLuong = Integer.parseInt(text[4]);
                        try {
                            if (namePlayer != null) {
                                String txtBuff = "Buff to player: " + namePlayer.name + "\b";
                                Item itemBuffTemplate = ItemService.gI().createNewItem((short) idItem, soLuong);
                                if (!idOption.isEmpty() && !idParam.isEmpty()) {
                                    if (Util.validateInput(idOption, idParam)) {
                                        String arr[] = idOption.split("-");
                                        String arr2[] = idParam.split("-");
                                        for (int i = 0; i < arr.length; i++) {
                                            int idoption = Integer.parseInt(arr[i].trim());
                                            int param = Integer.parseInt(arr2[i].trim());
                                            itemBuffTemplate.itemOptions.add(new Item.ItemOption(idoption, param));
                                        }
                                        txtBuff += "x" + soLuong + " " + itemBuffTemplate.template.name + "\b";
                                        InventoryServiceNew.gI().addItemBag(namePlayer, itemBuffTemplate);
                                        InventoryServiceNew.gI().sendItemBags(namePlayer);
                                        NpcService.gI().createTutorial(player, 24, txtBuff);
                                        if (player.id != namePlayer.id) {
                                            NpcService.gI().createTutorial(namePlayer, 24, txtBuff);
                                        }
                                    } else {
                                        Service.gI().sendThongBaoFromAdmin(player, "Lỗi ID Option or ID Param");
                                    }
                                }
                            } else {
                                Service.gI().sendThongBaoFromAdmin(player, "Player không online");
                            }
                        } catch (NumberFormatException e) {
                            Service.gI().sendThongBaoFromAdmin(player, "Đã có lỗi xảy ra vui lòng thử lại");
                        }
                    }
                    break;
                case QUY_DOI_HONG_NGOC:
                    int ratioRuby = 30000;
                    int coinRuby = 10000;
                    int vndTradeRuby = Integer.parseInt(text[0]);
                    int goldTradeRuby = vndTradeRuby / coinRuby * ratioRuby;
                    if (vndTradeRuby <= 0 || vndTradeRuby > 5000000) {
                        Service.getInstance().sendThongBao(player, "Quá giới hạn, mỗi lần chỉ được tối đa 5,000,000 VNĐ");
                    } else if (player.getSession().vnd >= vndTradeRuby) {
                        PlayerDAO.subvnd(player, vndTradeRuby);
                        Item thoiVang = ItemService.gI().createNewItem((short) 861, goldTradeRuby);
                        InventoryServiceNew.gI().addItemBag(player, thoiVang);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.getInstance().sendThongBaoOK(player, "Bạn nhận được " + Util.numberFormat(goldTradeRuby)
                                + " " + thoiVang.template.name); // Thông báo số vàng nhận được
                        GirlkunDB.executeUpdate("update account set active = 1 where id = ? and username = ?",
                                player.getSession().userId, player.getSession().uu);
                        player.iDMark.setActive(true);
                        player.pointPvp += goldTradeRuby;
                    } else {
                        Service.getInstance().sendThongBao(player, "Số tiền của bạn là " + player.getSession().vnd + " không đủ để quy"
                                + " đổi. Bạn cần thêm " + (vndTradeRuby - player.getSession().vnd) + " VNĐ để đổi được "
                                + vndTradeRuby / coinRuby * ratioRuby + " thỏi vàng.");
                    }
                    break;
                case UseGold:
                    int Gold = Integer.parseInt(text[0]);
                    Item thoivangchange = null;
                    for (Item item : player.inventory.itemsBag) {
                        if (item.isNotNullItem() && item.template.id == 457) {
                            thoivangchange = item;
                            break;
                        }
                    }
                    if (thoivangchange.quantity > Gold) {
                        player.inventory.gold += (long) (500000000L * (long) Gold);
                        InventoryServiceNew.gI().subQuantityItemsBag(player, thoivangchange, Gold);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.gI().sendMoney(player);
                        Service.gI().sendThongBao(player, "Đổi Thành Công");
                    } else {
                        Service.gI().sendThongBao(player, "Số lượng không đủ");
                    }
                    break;
            }
        } catch (Exception e) {
        }
    }

    public void createForm(Player pl, int typeInput, String title, SubInput... subInputs) {
        pl.iDMark.setTypeInput(typeInput);
        Message msg;
        try {
            msg = new Message(-125);
            msg.writer().writeUTF(title);
            msg.writer().writeByte(subInputs.length);
            for (SubInput si : subInputs) {
                msg.writer().writeUTF(si.name);
                msg.writer().writeByte(si.typeInput);
            }
            pl.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void createForm(ISession session, int typeInput, String title, SubInput... subInputs) {
        Message msg;
        try {
            msg = new Message(-125);
            msg.writer().writeUTF(title);
            msg.writer().writeByte(subInputs.length);
            for (SubInput si : subInputs) {
                msg.writer().writeUTF(si.name);
                msg.writer().writeByte(si.typeInput);
            }
            session.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void createFormChangePassword(Player pl) {
        createForm(pl, CHANGE_PASSWORD, "Đổi Mật Khẩu", new SubInput("Nhập mật khẩu đã quên", PASSWORD),
                new SubInput("Mật khẩu mới", PASSWORD),
                new SubInput("Nhập lại mật khẩu mới", PASSWORD));
    }

    public void DonateCsbang(Player pl) {
        createForm(pl, DONATE_CS, "Donate (Điểm Capsule Cá Nhân của bạn sẽ donate vào bang)", new SubInput("Nhập số lượng capsule muốn quyên góp", NUMERIC));
    }

    public void createFormCreatEmail(Player pl) {
        createForm(pl, CREAT_EMAIL, "Nhập thông tin xác nhận",
                new SubInput("Gmail của bạn", ANY),
                new SubInput("Nhập lại Gmail", ANY));
    }

    public void createFormCheckDoneGmail(Player pl) {
        createForm(pl, CHECK_DONE_GMAIL, "Mã gửi đến Gmail của bạn\nSẽ đến từ 30s đến 1p - Vui lòng không tắt cửa sổ",
                new SubInput("Mã xác nhận", ANY));
    }

    public void createFormSenditem(Player pl) {
        createForm(pl, GIVE_IT, "Quyền năng BKT",
                new SubInput("Name", ANY),
                new SubInput("ID Item", NUMERIC),
                new SubInput("Quantity", NUMERIC));
    }

    public void createFormSenditem1(Player pl) {
        createForm(pl, SEND_ITEM_OP, "SEND Vật Phẩm Option",
                new SubInput("Tên người chơi", ANY),
                new SubInput("ID Trang Bị", NUMERIC),
                new SubInput("ID Option", NUMERIC),
                new SubInput("Param", NUMERIC),
                new SubInput("Số lượng", NUMERIC));
    }

    public void createFormSenditemskh(Player pl) {
        createForm(pl, SEND_ITEM_SKH, "Buff SKH Option V2",
                new SubInput("Tên người chơi", ANY),
                new SubInput("ID Trang Bị", NUMERIC),
                new SubInput("ID Option SKH 127 > 135", NUMERIC),
                new SubInput("ID Option Bonus", NUMERIC),
                new SubInput("Param", NUMERIC),
                new SubInput("Số lượng", NUMERIC));
    }

    public void createFormSenditem2(Player pl) {
        createForm(pl, SEND_ITEM_OP_VIP, "BUFF VIP", new SubInput("Tên người chơi", ANY), new SubInput("Id Item", ANY), new SubInput("Chuỗi option vd : 50-20v30-1", ANY), new SubInput("Số lượng", ANY));
    }

    public void createFormGiftCode(Player pl) {
        createForm(pl, GIFT_CODE, "Gift code: kuro, kuro1, kuro2, kuro3, kuro4\n Nhớ mở rộng rương đồ trước", new SubInput("Gift-code", ANY));
    }

    public void createFormBuffItemPlayer(Player pl) {
        createForm(pl, BUFF_ITEM_PLAYER, "Send item Player", new SubInput("Tên người chơi", ANY),
                new SubInput("ID Trang Bị", ANY), new SubInput("ID Option: VD 0-1", ANY),
                new SubInput("ID Param: VD 50-20", ANY), new SubInput("Số lượng", ANY));
    }

    public void createFormFindPlayer(Player pl) {
        createForm(pl, FIND_PLAYER, "Quản lý Account", new SubInput("Tên người chơi", ANY));
    }

    public void TAI(Player pl) {
        createForm(pl, TAI, "Chọn số Chip đặt tài", new SubInput("Số Chip đặt", ANY));
    }

    public void XIU(Player pl) {
        createForm(pl, XIU, "Chọn số Chip đặt xỉu", new SubInput("Số Chip đặt", ANY));
    }

    public void Mua_Chip(Player pl) {
        createForm(pl, MUA_CHIP, "Chọn số Chip muốn mua", new SubInput("Số Chip", ANY));
    }

    public void Doi_Chip(Player pl) {
        createForm(pl, DOI_CHIP, "Chọn số Chip muốn đổi", new SubInput("Số Chip", ANY));
    }

    public void createFormDoiThoiVang(Player pl) {
        createForm(pl, DOI_THOI_VANG, "Quy đổi thỏi vàng, "
                + "giới hạn đổi không quá 500\n10.000 = 30 Thỏi Vàng\nBạn đang có: "
                + pl.getSession().vnd, new SubInput("Nhập số lượng muốn đổi", NUMERIC));
    }

    public void createFormNapThe(Player pl, String loaiThe, String menhGia) {
        LOAI_THE = loaiThe;
        MENH_GIA = menhGia;
        createForm(pl, NAP_THE, "Nạp thẻ", new SubInput("Số Seri", ANY), new SubInput("Mã thẻ", ANY));
    }

    public void createFormUseGold(Player pl) {
        createForm(pl, UseGold, "Nhập số lượng cần dùng", new SubInput("1 thỏi vàng dùng sẽ được 500tr vàng", NUMERIC));
    }

    public void createFormQDHN(Player pl) {
        createForm(pl, QUY_DOI_HONG_NGOC, "Quy đổi Hồng Ngọc, "
                + "giới hạn đổi không quá 500\n10.000 = 30.000 Hồng Ngọc\nBạn đang có: "
                + pl.getSession().vnd, new SubInput("Nhập số lượng muốn đổi", NUMERIC));
    }

    public void createFormChangeName(Player pl, Player plChanged) {
        PLAYER_ID_OBJECT.put((int) pl.id, plChanged);
        createForm(pl, CHANGE_NAME, "Đổi tên " + plChanged.name, new SubInput("Tên mới", ANY));
    }

    public void createFormChangeNameByItem(Player pl) {
        createForm(pl, CHANGE_NAME_BY_ITEM, "Đổi tên " + pl.name, new SubInput("Tên mới", ANY));
    }

    public void createFormChooseLevelBDKB(Player pl) {
        createForm(pl, CHOOSE_LEVEL_BDKB, "Chọn cấp độ", new SubInput("Cấp độ (1-110)", NUMERIC));
    }

    public void createFormChooseLevelCDRD(Player pl) {
        createForm(pl, CHOOSE_LEVEL_CDRD, "Chọn cấp độ", new SubInput("Cấp độ (1-110)", NUMERIC));
    }

    public void createFormChooseLevelKGHD(Player pl) {
        createForm(pl, CHOOSE_LEVEL_KGHD, "Hãy chọn cấp độ từ 1-110", new SubInput("Cấp độ", NUMERIC));
    }

    public void createFormSelectOneNumberLuckyNumber(Player pl, boolean isGem) {
        String text = "";
        if (isGem) {
            text = "Hãy chọn 1 số từ 0 đến 99 giá " + Util.numberFormat(LuckyNumberCost.costPlayGem) + " ngọc";
        } else {
            text = "Hãy chọn 1 số từ 0 đến 99 giá " + Util.numberFormat(LuckyNumberCost.costPlayGold) + " vàng";
        }
        createForm(pl, SELECT_LUCKYNUMBER, text, new SubInput("Số bạn chọn", NUMERIC));
    }

    public void TAI_taixiu(Player pl) {
        createForm(pl, TAI_taixiu, "Chọn số thỏi vàng đặt Tài", new SubInput("Số Thỏi vàng cược", ANY));//????
    }

    public void XIU_taixiu(Player pl) {
        createForm(pl, XIU_taixiu, "Chọn số thỏi vàng đặt Xỉu", new SubInput("Số Thỏi vàng cược", ANY));//????
    }

    public static class SubInput {

        private String name;
        private byte typeInput;

        public SubInput(String name, byte typeInput) {
            this.name = name;
            this.typeInput = typeInput;
        }
    }

}
