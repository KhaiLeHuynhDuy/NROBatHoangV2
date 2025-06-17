package NPC;

import NewCombine.CheTaoCuonSachCu;
import NewCombine.DoiSachTuyetKy;
import Services.FriendAndEnemyService;
import Services.ItemMapService;
import Services.OpenPowerService;
import Services.NpcService;
import Services.ClanService;
import Services.TaskService;
import Services.InventoryServiceNew;
import Services.PlayerService;
import Services.ItemService;
import Services.Service;
import Services.MapService;
import Services.NgocRongNamecService;
import Services.PetService;
import Services.IntrinsicService;

import Consts.ConstNpc;
import Consts.ConstMap;
import Consts.ConstPlayer;
import Consts.ConstTask;
import Daos.PlayerDAO;
import BossMain.Boss;
import BossMain.BossData;
import BossMain.BossID;
import BossMain.BossManager;
import BossMain.BossesData;
import Challenge.VoDai;
import Challenge.VoDaiManager;
import Challenge.VoDaiService;
import LeoThap.ThachDauYaritobe;
import ListBoss.MainBoss.NhanBan;
import Clan.Clan;
import Clan.ClanMember;
import Consts.ConstAttribute;
import Consts.ConstMenu;
import Consts.ConstMiniGame;

import java.util.HashMap;
import java.util.List;

import Services.func.ChangeMapService;
import Services.func.SummonDragon;
import static Services.func.SummonDragon.SHENRON_1_STAR_WISHES_1;
import static Services.func.SummonDragon.SHENRON_1_STAR_WISHES_2;
import static Services.func.SummonDragon.SHENRON_SAY;

import Player.Player;
import Item.Item;
import Maps.Map;
import Maps.Zone;

import ListMap.BlackBallWar;
import ListMap.MapMaBu;
import ListMap.DoanhTrai;
import ListMap.DoanhTraiService;
import ListMap.BanDoKhoBau;
import ListMap.BanDoKhoBauService;

import Player.NPoint;
import Matches.PVPService;
import PVP.DaiHoiVoThuat;
import PVP.DaiHoiVoThuatService;
import Shop.ShopServiceNew;
import Skill.Skill;
import Server.Client;
import Server.Maintenance;
import Server.Manager;
import Services.func.CombineServiceNew;
import Services.func.Input;
import Services.func.LuckyRound;
import Utils.CheckDoneGmail;
import Utils.EmailSender;
import Utils.Logger;
import Utils.TimeUtil;
import Utils.Util;
import java.util.ArrayList;
import KyGui.ShopKyGuiService;
import ListBoss.MainBoss.DuongTank;
import Matches.TOP;
import Data.GetData;
import static Services.func.SummonDragon.ICE_SHENRON_SAY;
import static Services.func.SummonDragon.ICE_SHENRON_WISHES;
import Services.func.SummonDragonNamek;
import Services.func.TaiXiu;
import ListMap.ConDuongRanDocService;
import ListMap.Gas;
import ListMap.GasService;
import Services.func.TopService;
import ListMap.TrainingService;
import ListMap.nguhs;
import MiniGame.DecisionMaker.DecisionMaker;
import MiniGame.DecisionMaker.DecisionMakerGem;
import MiniGame.DecisionMaker.DecisionMakerGold;
import MiniGame.DecisionMaker.DecisionMakerRuby;
import MiniGame.LuckyNumber.LuckyNumber;
import MiniGame.LuckyNumber.LuckyNumberService;
import MiniGame.RockPaperScissors.RockPaperScissors;
import static Server.Manager.EVENT_COUNT_NOEL;
import Server.ServerManager;
import Server.ServerNotify;
import Server.attr.AttributeManager;
import Services.AchievementService;
import Services.SkillService;
import static Services.SubMenuService.LOI_CHUC;
import The23rdMartialArtCongress.The23rdMartialArtCongressService;
import Utils.SkillUtil;
import com.girlkun.database.GirlkunDB;
import network.Message;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NpcFactory {

    private static final int COST_HD = 50000000;
    private static final int COST_FIND_BOSS = 50000000;

    private static boolean nhanVang = false;
    private static boolean nhanDeTu = false;

    //playerid - object
    public static final java.util.Map<Long, Object> PLAYERID_OBJECT = new HashMap<Long, Object>();

    private NpcFactory() {

    }

    private static Npc trungLinhThu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đổi Trứng Linh thú cần:\b|7|X99 Hồn Linh Thú + 1 Tỷ vàng", "Đổi Trứng\nLinh thú", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Item honLinhThu = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 2029);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ 99 Hồn Linh thú");
                                    } else if (player.inventory.gold < 1_000_000_000) {
                                        this.npcChat(player, "Bạn không đủ 1 Tỷ vàng");
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {
                                        player.inventory.gold -= 1_000_000_000;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        Service.gI().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 2028);
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Trứng Linh thú");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc kyGui(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, 0, "Cửa hàng chúng tôi chuyên mua bán hàng hiệu, hàng độc, cảm ơn bạn đã ghé thăm.", "Hướng\ndẫn\nthêm", "Mua bán\nKý gửi", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (canOpenNpc(pl)) {
                    switch (select) {
                        case 0:
                            Service.getInstance().sendPopUpMultiLine(pl, tempId, avartar, "Cửa hàng chuyên nhận ký gửi mua bán vật phẩm\bChỉ với 5 hồng ngọc\bGiá trị ký gửi 10k-200Tr vàng hoặc 2-2k ngọc\bMột người bán, vạn người mua, mại dô, mại dô");
                            break;
                        case 1:
                            ShopKyGuiService.gI().openShopKyGui(pl);
//                            Service.gI().sendThongBaoOK(pl, "Đang Lỗi Không Mở");
                            break;

                    }
                }
            }
        };
    }

    private static Npc poTaGe(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 140) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đa vũ trụ song song \b|7|Con muốn gọi con trong đa vũ trụ \b|1|Với giá 200tr vàng không?", "Gọi Boss\nNhân bản", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 140) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Boss oldBossClone = BossManager.gI().getBossById(Util.createIdBossClone((int) player.id));
                                    if (oldBossClone != null) {
                                        this.npcChat(player, "Nhà ngươi hãy tiêu diệt Boss lúc trước gọi ra đã, con boss đó đang ở khu " + oldBossClone.zone.zoneId);
                                    } else if (player.inventory.gold < 200_000_000) {
                                        this.npcChat(player, "Nhà ngươi không đủ 200 Triệu vàng ");
                                    } else {
                                        List<Skill> skillList = new ArrayList<>();
                                        for (byte i = 0; i < player.playerSkill.skills.size(); i++) {
                                            Skill skill = player.playerSkill.skills.get(i);
                                            if (skill.point > 0) {
                                                skillList.add(skill);
                                            }
                                        }
                                        int[][] skillTemp = new int[skillList.size()][3];
                                        for (byte i = 0; i < skillList.size(); i++) {
                                            Skill skill = skillList.get(i);
                                            if (skill.point > 0) {
                                                skillTemp[i][0] = skill.template.id;
                                                skillTemp[i][1] = skill.point;
                                                skillTemp[i][2] = skill.coolDown;
                                            }
                                        }

                                        BossData bossDataClone = new BossData(
                                                "Nhân Bản " + player.name,
                                                player.gender,
                                                new short[]{player.getHead(), player.getBody(), player.getLeg(), player.getFlagBag(), player.idAura, player.getEffFront()},
                                                player.nPoint.hpMax / 200,
                                                new long[]{player.nPoint.dame * 1000},
                                                new int[]{140},
                                                skillTemp,
                                                new String[]{"|-2|Boss nhân bản đã xuất hiện rồi"}, //text chat 1
                                                new String[]{"|-1|Ta sẽ chiếm lấy thân xác của ngươi hahaha!"}, //text chat 2
                                                new String[]{"|-1|Lần khác ta sẽ xử đẹp ngươi"}, //text chat 3
                                                60
                                        );

                                        try {
                                            new NhanBan(Util.createIdBossClone((int) player.id), bossDataClone, player.zone);
                                        } catch (Exception e) {
                                        }
                                        //trừ vàng khi gọi boss
                                        player.inventory.gold -= 200_000_000;
                                        Service.gI().sendMoney(player);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc quyLaoKame(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            public void chatWithNpc(Player player) {
                String[] chat = {
                    "Phong Cách",
                    "Phong Cách",
                    "Phong Cách"
                };
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    int index = 0;

                    @Override
                    public void run() {
                        npcChat(player, chat[index]);
                        index = (index + 1) % chat.length;
                    }
                }, 10000, 10000);
            }

            @Override
            public void openBaseMenu(Player player) {
                chatWithNpc(player);
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Chào con, con muốn ta giúp gì nào?\n",
                                "Nói Chuyện", "Nhận Quà Top Sức Mạnh", "Nhận Quà Top Sức Mạnh");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        Item thoivang;
                        Item caiTrang;
                        Item vPDL;
                        switch (select) {
                            case 0:
                                this.createOtherMenu(player, 181, "Chào con, con muốn ta giúp gì nào?",
                                        "Nhiệm Vụ", "Kho báu dưới biển", "Chức Năng\nBang Hội");
                                break;
                            case 1:
//                                if (player.point_topsm < 1) {
//                                    if (player.name.equals("missav")) {
//                                        caiTrang = ItemService.gI().createNewItem((short) 1432);
//                                        thoivang = ItemService.gI().createNewItem((short) 457);
//                                        thoivang.quantity += 199;
//                                        player.inventory.ruby += 100000;
//                                        caiTrang.itemOptions.add(new Item.ItemOption(50, 35));
//                                        caiTrang.itemOptions.add(new Item.ItemOption(77, 35));
//                                        caiTrang.itemOptions.add(new Item.ItemOption(103, 35));
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        InventoryServiceNew.gI().addItemBag(player, caiTrang);
//                                        player.point_topsm++;
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        Service.gI().sendThongBao(player, "Bạn Nhận Được " + thoivang.template.name + " " + caiTrang.template.name + " và 100k Hồng Ngọc");
//                                    } else if (player.name.equals("lionking")) {
//                                        caiTrang = ItemService.gI().createNewItem((short) 1387);
//                                        thoivang = ItemService.gI().createNewItem((short) 457);
//                                        thoivang.quantity += 149;
//                                        player.inventory.ruby += 70000;
//                                        caiTrang.itemOptions.add(new Item.ItemOption(50, 30));
//                                        caiTrang.itemOptions.add(new Item.ItemOption(77, 30));
//                                        caiTrang.itemOptions.add(new Item.ItemOption(103, 30));
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        InventoryServiceNew.gI().addItemBag(player, caiTrang);
//                                        player.point_topsm++;
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        Service.gI().sendThongBao(player, "Bạn Nhận Được " + thoivang.template.name + "" + caiTrang.template.name + " và 70k Hồng Ngọc");
//                                    } else if (player.name.equals("acient")) {
//                                        vPDL = ItemService.gI().createNewItem((short) 1388);
//                                        thoivang = ItemService.gI().createNewItem((short) 457);
//                                        thoivang.quantity += 99;
//                                        player.inventory.ruby += 50000;
//                                        vPDL.itemOptions.add(new Item.ItemOption(50, 25));
//                                        vPDL.itemOptions.add(new Item.ItemOption(77, 25));
//                                        vPDL.itemOptions.add(new Item.ItemOption(103, 25));
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        InventoryServiceNew.gI().addItemBag(player, vPDL);
//                                        player.point_topsm++;
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        Service.gI().sendThongBao(player, "Bạn Nhận Được " + thoivang.template.name + " " + vPDL.template.name + " và 50k Hồng Ngọc");
//                                    } else if (player.name.equals("imlezard")
//                                            || player.name.equals("phucne")
//                                            || player.name.equals("octiu")
//                                            || player.name.equals("lezard")
//                                            || player.name.equals("cadic")
//                                            || player.name.equals("blackbom")
//                                            || player.name.equals("behan1")) {
//                                        thoivang = ItemService.gI().createNewItem((short) 457);
//                                        thoivang.quantity += 49;
//                                        player.inventory.ruby += 30000;
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        player.point_topsm++;
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        Service.gI().sendThongBao(player, "Bạn Nhận Được " + thoivang.template.name + " và 50k Hồng Ngọc");
//                                    } else {
//                                        Service.gI().sendThongBao(player, "Bạn Không Có Tên Trong Danh Sách");
//                                    }
//                                } else {
//                                    Service.gI().sendThongBao(player, "Bạn Đã Nhận Quà Top Rồi");
//                                }
                                Service.gI().sendThongBao(player, "Đua Top Open Xong Rồi Quay Lại Nhận");
                                break;
                            case 2:
//                                if (player.point_topnv < 1) {
//                                    if (player.name.equals("viemde")) {
//                                        caiTrang = ItemService.gI().createNewItem((short) 1432);
//                                        thoivang = ItemService.gI().createNewItem((short) 457);
//                                        thoivang.quantity += 199;
//                                        player.inventory.ruby += 100000;
//                                        caiTrang.itemOptions.add(new Item.ItemOption(50, 35));
//                                        caiTrang.itemOptions.add(new Item.ItemOption(77, 35));
//                                        caiTrang.itemOptions.add(new Item.ItemOption(103, 35));
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        InventoryServiceNew.gI().addItemBag(player, caiTrang);
//                                        player.point_topnv++;
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        Service.gI().sendThongBao(player, "Bạn Nhận Được " + thoivang.template.name + " " + caiTrang.template.name + " và 100k Hồng Ngọc");
//                                    } else if (player.name.equals("hanhga")) {
//                                        caiTrang = ItemService.gI().createNewItem((short) 1387);
//                                        thoivang = ItemService.gI().createNewItem((short) 457);
//                                        thoivang.quantity += 149;
//                                        player.inventory.ruby += 70000;
//                                        caiTrang.itemOptions.add(new Item.ItemOption(50, 30));
//                                        caiTrang.itemOptions.add(new Item.ItemOption(77, 30));
//                                        caiTrang.itemOptions.add(new Item.ItemOption(103, 30));
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        InventoryServiceNew.gI().addItemBag(player, caiTrang);
//                                        player.point_topnv++;
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        Service.gI().sendThongBao(player, "Bạn Nhận Được " + thoivang.template.name + "" + caiTrang.template.name + " và 70k Hồng Ngọc");
//                                    } else if (player.name.equals("cukit")) {
//                                        vPDL = ItemService.gI().createNewItem((short) 1388);
//                                        thoivang = ItemService.gI().createNewItem((short) 457);
//                                        thoivang.quantity += 99;
//                                        player.inventory.ruby += 50000;
//                                        vPDL.itemOptions.add(new Item.ItemOption(50, 25));
//                                        vPDL.itemOptions.add(new Item.ItemOption(77, 25));
//                                        vPDL.itemOptions.add(new Item.ItemOption(103, 25));
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        InventoryServiceNew.gI().addItemBag(player, vPDL);
//                                        player.point_topnv++;
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        Service.gI().sendThongBao(player, "Bạn Nhận Được " + thoivang.template.name + " " + vPDL.template.name + " và 50k Hồng Ngọc");
//                                    } else if (player.name.equals("zhouchiton")
//                                            || player.name.equals("concunho")
//                                            || player.name.equals("kamii")
//                                            || player.name.equals("zhoutank")
//                                            || player.name.equals("gohann")
//                                            || player.name.equals("melodi")
//                                            || player.name.equals("bavannho")) {
//                                        thoivang = ItemService.gI().createNewItem((short) 457);
//                                        thoivang.quantity += 49;
//                                        player.inventory.ruby += 30000;
//                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
//                                        player.point_topnv++;
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        Service.gI().sendThongBao(player, "Bạn Nhận Được " + thoivang.template.name + " và 50k Hồng Ngọc");
//                                    } else {
//                                        Service.gI().sendThongBao(player, "Bạn Không Có Tên Trong Danh Sách");
//                                    }
//                                } else {
//                                    Service.gI().sendThongBao(player, "Bạn Đã Nhận Quà Top Rồi");
//                                }
                                Service.gI().sendThongBao(player, "Đua Top Open Xong Rồi Quay Lại Nhận");
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == 181) {
                        switch (select) {
                            case 0:
                                Service.gI().sendThongBaoFromQuyLao(player, "|7|Nhiệm Vụ Của Con Hiện Tại:\n" + player.playerTask.taskMain.subTasks.get(player.playerTask.taskMain.index).name);
                                break;
                            case 1:
                                if (player.clan != null) {
                                    if (player.clan.BanDoKhoBau != null) {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPENED_DBKB,
                                                "Bang hội của con đang đi tìm kho báu dưới biển cấp độ "
                                                + player.clan.BanDoKhoBau.level + "\nCon có muốn đi theo không?",
                                                "Đồng ý", "Từ chối");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPEN_DBKB,
                                                "Đây là bản đồ kho báu x4 tnsm\nCác con cứ yên tâm lên đường\n"
                                                + "Ở đây có ta lo\nNhớ chọn cấp độ vừa sức mình nhé",
                                                "Chọn\ncấp độ", "Từ chối");
                                    }
                                } else {
                                    this.npcChat(player, "Con phải có bang hội ta mới có thể cho con đi");
                                }
                                break;
                            case 2:
                                this.createOtherMenu(player, ConstNpc.CHUC_NANG_BANG_HOI,
                                        "Ta có hỗ trợ những chức năng Bang hội, nhà ngươi cần gì?", "Giải tán\nBang", "Nâng cấp\nBang", "Quyên Góp\nĐiểm Capsule", "Lãnh địa\nBang", "Từ chối");
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.CHUC_NANG_BANG_HOI) {
                        switch (select) {
                            case 0:
                                Clan clan = player.clan;
                                if (clan != null) {
                                    ClanMember cm = clan.getClanMember((int) player.id);
                                    if (cm != null) {
                                        if (!clan.isLeader(player)) {
                                            Service.gI().sendThongBao(player, "Yêu cầu phải là bang chủ!");
                                            break;
                                        }
                                        if (clan.members.size() > 1) {
                                            Service.gI().sendThongBao(player, "Yêu cầu bang hội chỉ còn một thành viên!");
                                            break;
                                        }
                                        NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DISSOLUTION_CLAN, -1, "Bạn có chắc chắn muốn giải tán bang hội?\n( Yêu cầu sẽ không thể hoàn tác )",
                                                "Đồng ý", "Từ chối!");
                                        break;
                                    }
                                    break;
                                }
                                Service.gI().sendThongBao(player, "Yêu câu tham gia bang hội");
                                break;
                            case 1:
                                if (player.clan != null) {
                                    if (!player.clan.isLeader(player)) {
                                        Service.gI().sendThongBao(player, "Yêu cầu phải là bang chủ!");
                                        break;
                                    }
                                    if (player.clan.level >= 0 && player.clan.level <= 10) {
                                        this.createOtherMenu(player, ConstNpc.CHUC_NANG_BANG_HOI2,
                                                "Bạn có muốn Nâng cấp lên " + (player.clan.maxMember + 1) + " thành viên không?\n"
                                                + "Cần 2000 Capsule Bang\n"
                                                + "(Thu thập Capsule Bang bằng cách tiêu diệt quái tại Map Lãnh Địa Bang\n"
                                                + "cùng các thành viên khác)", "Nâng cấp\n(20K Ruby)", "Từ chối");
                                    } else {
                                        Service.gI().sendThongBao(player, "Bang của bạn đã đạt cấp tối đa!");
                                        break;
                                    }
                                    break;
                                } else if (player.clan == null) {
                                    Service.gI().sendThongBao(player, "Yêu câu tham gia bang hội");
                                    break;
                                }
                                break;
                            case 2:
                                if (player.clan == null) {
                                    Service.gI().sendThongBao(player, "Yêu câu tham gia bang hội");
                                    break;
                                }
                                Input.gI().DonateCsbang(player);
                                break;
                            case 3:
                                if (player.getSession().player.nPoint.power >= 80000000000L) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 153, -1, 432);
                                } else if (player.clan == null) {
                                    Service.gI().sendThongBao(player, "Yêu câu tham gia bang hội");
                                    break;
                                } else {
                                    this.npcChat(player, "Bạn chưa đủ 80 tỷ sức mạnh để vào");
                                }
                                break;
                            case 4:
                                if (player.getSession().player.nPoint.power >= 80000000000L) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 153, -1, 432);
                                } else if (player.clan == null) {
                                    Service.gI().sendThongBao(player, "Yêu câu tham gia bang hội");
                                    break;
                                } else {
                                    this.npcChat(player, "Bạn chưa đủ 80 tỷ sức mạnh để vào");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.CHUC_NANG_BANG_HOI2) {
                        Clan clan = player.clan;
                        switch (select) {
                            case 0:
                                if (player.clan.capsuleClan >= 2000 && clan.isLeader(player) && player.inventory.ruby >= 20000) {
                                    player.clan.level += 1;
                                    player.clan.maxMember += 1;
                                    player.clan.capsuleClan -= 2000;
                                    player.inventory.subRuby(20000);
                                    player.clan.update();
                                    Service.gI().sendThongBao(player, "Yêu cầu nâng cấp bang hội thành công");
                                    break;
                                } else if (player.inventory.ruby < 20000) {
                                    Service.gI().sendThongBaoOK(player, "Bạn còn thiều " + (20000 - player.inventory.ruby) + " Hồng Ngọc");
                                    break;
                                } else if (player.clan.capsuleClan < 1000) {
                                    Service.gI().sendThongBaoOK(player, "Bang của bạn còn thiều " + (2000 - player.clan.capsuleClan) + " Capsule bang");
                                    break;
                                }
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPENED_DBKB) {
                        switch (select) {
                            case 0:
                                if (player.clan == null) {
                                    Service.gI().sendThongBao(player, "Hãy vào bang hội trước");
                                    return;
                                }
                                if (player.isAdmin() || player.nPoint.power >= BanDoKhoBau.POWER_CAN_GO_TO_DBKB) {
                                    ChangeMapService.gI().goToDBKB(player);
                                } else {
                                    this.npcChat(player, "Yêu cầu sức mạnh lớn hơn "
                                            + Util.numberToMoney(BanDoKhoBau.POWER_CAN_GO_TO_DBKB));
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPEN_DBKB) {
                        switch (select) {
                            case 0:
                                if (player.clan == null) {
                                    Service.gI().sendThongBao(player, "Hãy vào bang hội trước");
                                    return;
                                }
                                if (player.isAdmin() || player.nPoint.power >= BanDoKhoBau.POWER_CAN_GO_TO_DBKB) {
                                    Input.gI().createFormChooseLevelBDKB(player);
                                } else {
                                    this.npcChat(player, "Yêu cầu sức mạnh lớn hơn "
                                            + Util.numberToMoney(BanDoKhoBau.POWER_CAN_GO_TO_DBKB));
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_ACCEPT_GO_TO_BDKB) {
                        switch (select) {
                            case 0:
                                BanDoKhoBauService.gI().openBanDoKhoBau(player, Byte.parseByte(String.valueOf(PLAYERID_OBJECT.get(player.id))));
                                break;
                        }
                    }
                }

            }
        };
    }

    public static Npc truongLaoGuru(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc vuaVegeta(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc ongGohan_ongMoori_ongParagus(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Cố Gắng Có Làm Mới Có Ăn Con, đừng lo lắng cho ta\n"
                                        .replaceAll("%1", player.gender == ConstPlayer.TRAI_DAT ? "Quy lão Kamê"
                                                : player.gender == ConstPlayer.NAMEC ? "Trưởng lão Guru" : "Vua Vegeta")
                                + "|3| Trạng thái: " + (player.getSession().actived ? "Đã kích hoạt thành viên\n" : "Chưa kích hoạt thành viên\n")
                                + "|1| Điểm danh hằng ngày sẽ nhận được "
                                + (player.getSession().actived ? "10" : "5") + " Thỏi vàng",
                                (GetData.checkPlayerXacNhan((int) player.id) ? "Đổi\nMật Khẩu"
                                : EmailSender.isEmailEntered(Util.NQHxMNint(player.id)) ? "Xác nhận\nGmail" : "Nhập Gmail"),
                                "Hỗ Trợ", "Kích hoạt\nTài Khoản", "Điểm Danh", "Quy Đổi", "Shop Tân Thủ");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (GetData.checkPlayerXacNhan((int) player.id)) {
                                    Input.gI().createFormChangePassword(player);
                                } else if (EmailSender.isEmailEntered(Util.NQHxMNint(player.id))) {
                                    Input.gI().createFormCheckDoneGmail(player);
                                    Service.gI().sendThongBao(player, "Mã đang gửi!!");
                                    if (GetData.getAccountIDFromPlayerId(Util.NQHxMNint(player.id)) != -1) {
                                        CheckDoneGmail.Done(GetData.getAccountIDFromPlayerId((int) player.id), player);
                                    }
                                } else {
                                    Input.gI().createFormCreatEmail(player);
                                }
                                break;
                            case 1:
                                this.createOtherMenu(player, ConstNpc.HO_TRO, "Ta sẽ hỗ trợ con!!", "Nhận Ngọc Xanh", "Nhận Đệ Tử");
                                break;
                            case 2:
                                if (!player.getSession().actived) {
                                    if (player.getSession().vnd >= 10000) {
                                        player.getSession().actived = true;
                                        if (PlayerDAO.subvnd(player, 10000)) ;
                                        Item vangnemay = ItemService.gI().createNewItem((short) 457);
                                        vangnemay.quantity += 24;
                                        player.inventory.ruby += 10000;
                                        InventoryServiceNew.gI().addItemBag(player, vangnemay);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        Service.getInstance().sendMoney(player);
                                        Service.gI().sendThongBao(player, "|7|Kích hoạt thành công, bạn nhận được thêm 25 Thỏi Vàng và 10k Hồng Ngọc");
                                    } else {
                                        this.npcChat(player, "Không Đủ Tiền Mở Thành Viên...!");
                                    }
                                } else {
                                    this.npcChat(player, "Bạn đã mở thành viên rồi!");

                                }
                                break;
                            case 3:
                                if (player.diemDanh == 0) {
                                    int thoivang1 = player.getSession().actived ? 10 : 5; // Số lượng thỏi vàng được nhận điểm danh
                                    Item thoivang = ItemService.gI().createNewItem((short) 457, thoivang1);
                                    InventoryServiceNew.gI().addItemBag(player, thoivang);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    player.diemDanh = System.currentTimeMillis();
                                    Service.getInstance().sendThongBao(player, "|7|Bạn vừa nhận được " + thoivang1 + " Thỏi vàng");
                                } else {
                                    this.npcChat(player, "Hôm nay con đã nhận rồi mà !!!");
                                }
                                break;
                            case 4:
                                this.createOtherMenu(player, ConstNpc.QUY_DOI,
                                        "Số tiền của con hiện tại là: " + player.getSession().vnd, "Quy Đổi Hồng Ngọc", "Quy Đổi Thỏi Vàng");
                                break;
                            case 5:
                                ShopServiceNew.gI().opendShop(player, "TAN_THU", false);
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.HO_TRO) {
                        switch (select) {
                            case 0:
                                if (player.inventory.gem == 2000000) {
                                    this.npcChat(player, "Tham Lam");
                                    break;
                                }
                                player.inventory.gem = 2000000;
                                Service.getInstance().sendMoney(player);
                                Service.getInstance().sendThongBao(player, "Bạn vừa nhận được 2 triệu ngọc xanh");
                                break;
                            case 1:
                                if (player.pet == null) {
                                    PetService.gI().createNormalPet(player);
                                } else {
                                    Service.gI().sendThongBao(player, "Bạn đã có đệ tử rồi!");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.QUY_DOI) {
                        switch (select) {
                            case 0:
                                Input.gI().createFormQDHN(player);
                                break;
                            case 1:
                                Input.gI().createFormDoiThoiVang(player);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc ToSuKaio(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Tập luyện với Tổ sư Kaio sẽ tăng " + Util.chiaNho(TrainingService.gI().getTnsmMoiPhut(player)) + " sức mạnh mỗi phút, có thể tăng giảm tùy vào khả năng đánh quái của con",
                            player.dangKyTapTuDong ? "Hủy đăng\nký tập\ntự động" : "Đăng ký\ntập\ntự động", "Đồng ý\nluyện tập", "Không\nđồng ý");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.dangKyTapTuDong) {
                                    player.dangKyTapTuDong = false;
                                    NpcService.gI().createTutorial(player, tempId, avartar, "Con đã hủy thành công đăng ký tập tự động\ntừ giờ con muốn tập Offline hãy tự đến đây trước");
                                    return;
                                }
                                this.createOtherMenu(player, 2001, "Đăng ký để mỗi khi Offline quá 30 phút, con sẽ được tự động luyện tập với tốc độ " + TrainingService.gI().getTnsmMoiPhut(player) + " sức mạnh mỗi phút",
                                        "Hướng\ndẫn\nthêm", "Đồng ý\n1 ngọc\nmỗi lần", "Không\nđồng ý");
                                break;
                            case 1:
                                TrainingService.gI().callBoss(player, BossID.TO_SU_KAIO, false);
                                break;
                            default:
                        }
                    } else if (player.iDMark.getIndexMenu() == 2001) {
                        switch (select) {
                            case 0:
                                NpcService.gI().createTutorial(player, tempId, avartar, ConstNpc.TAP_TU_DONG);
                                break;
                            case 1:
                                player.mapIdDangTapTuDong = mapId;
                                player.dangKyTapTuDong = true;
                                NpcService.gI().createTutorial(player, tempId, avartar, "Từ giờ, quá 30 phút Offline con sẽ được tự động luyện tập");
                                break;
                        }
                    }
                }
            }

        };
    }

    public static Npc toppo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ta ở đây để giúp ngươi Check Boss nhanh hơn\n"
                                + "Do ngươi "
                                + (player.getSession().actived ? "Đã kích hoạt thành viên\n" : "Chưa kích hoạt thành viên\n")
                                + "Nên ta sẽ lấy ngươi với giá: " + (player.getSession().actived ? "2" : "5") + " Thỏi Vàng\n"
                                + "|3| Nếu chưa Kích Hoạt ta chỉ Giúp người Soi được 1 lần trong ngày!!",
                                "Soi Boss", "Từ Chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        Item TV = null;
                        for (Item item : player.inventory.itemsBag) {
                            if (item.isNotNullItem() && item.template.id == 457) {
                                TV = item;
                                break;
                            }
                        }
                        if (TV != null) {
                            switch (select) {
                                case 0:
                                    if (player.getSession().actived) {
                                        BossManager.gI().showListBoss(player);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, TV, 2);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                    } else {
                                        if (player.soiBoss == 0) {
                                            BossManager.gI().showListBoss(player);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, TV, 5);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            player.soiBoss = System.currentTimeMillis();
                                        } else {
                                            this.npcChat(player, "Hôm nay đã hết lượt soi\nYêu cầu Kích Hoạt để Soi tiếp!!!");
                                        }
                                    }
                                    break;
                            }
                        } else {
                            this.npcChat(player, "Ngươi đã hết Thỏi Vàng\nĐi cày thêm đi!!");
                        }
                    }
                }
            }
        };
    }

    public static Npc bulmaQK(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Cậu cần trang bị gì cứ đến chỗ tôi nhé", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                if (player.gender == ConstPlayer.TRAI_DAT) {
                                    ShopServiceNew.gI().opendShop(player, "BUNMA", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Xin lỗi cưng, chị chỉ bán đồ cho người Trái Đất", "Đóng");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc dende(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                new Thread(() -> {
                    try {
                        while (true) {
                            Thread.sleep(5000);
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000);
                                    this.npcChat(player, "Bế Ngọc Rồng NM Về Đây Tao Gọi Rồng Cho");
                                } catch (Exception e) {
                                }
                            }).start();
                        }
                    } catch (Exception e) {
                    }
                }).start();
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (player.idNRNM != -1) {
                            if (player.zone.map.mapId == 7) {
                                this.createOtherMenu(player, 1, "Ồ, ngọc rồng namếc, bạn thật là may mắn\nnếu tìm đủ 7 viên sẽ được Rồng Thiêng Namếc ban cho điều ước", "Hướng\ndẫn\nGọi Rồng", "Gọi rồng", "Từ chối");
                            }
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Anh cần trang bị gì cứ đến chỗ em nhé", "Cửa\nhàng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                if (player.gender == ConstPlayer.NAMEC) {
                                    ShopServiceNew.gI().opendShop(player, "DENDE", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Xin lỗi anh, em chỉ bán đồ cho dân tộc Namếc", "Đóng");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == 1) {
                        if (player.zone.map.mapId == 7 && player.idNRNM != -1) {
                            if (player.idNRNM == 353) {
                                NgocRongNamecService.gI().tOpenNrNamec = System.currentTimeMillis() + 86400000;
                                NgocRongNamecService.gI().firstNrNamec = true;
                                NgocRongNamecService.gI().timeNrNamec = 0;
                                NgocRongNamecService.gI().doneDragonNamec();
                                NgocRongNamecService.gI().initNgocRongNamec((byte) 1);
                                NgocRongNamecService.gI().reInitNrNamec((long) 86399000);
                                SummonDragonNamek.gI().summonNamec(player);
                            } else {
                                Service.gI().sendThongBao(player, "Anh phải có viên ngọc rồng Namếc 1 sao");
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc appule(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                new Thread(() -> {
                    try {
                        while (true) {
                            Thread.sleep(5000);
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000);
                                    this.npcChat(player, "Nhận Mua Bán Đồ");
                                } catch (Exception e) {
                                }
                            }).start();
                        }
                    } catch (Exception e) {
                    }
                }).start();
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi cần trang bị gì cứ đến chỗ ta nhé", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                if (player.gender == ConstPlayer.XAYDA) {
                                    ShopServiceNew.gI().opendShop(player, "APPULE", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Về hành tinh hạ đẳng của ngươi mà mua đồ cùi nhé. Tại đây ta chỉ bán đồ cho người Xayda thôi", "Đóng");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc drDrief(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (this.mapId == 84) {
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                pl.gender == ConstPlayer.TRAI_DAT ? "Đến\nTrái Đất" : pl.gender == ConstPlayer.NAMEC ? "Đến\nNamếc" : "Đến\nXayda");
                    } else if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                    "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                    "Đến\nNamếc", "Đến\nXayda", "Siêu thị");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 84) {
                        ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 24, -1, -1);
                    } else if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 25, -1, -1);
                                break;
                            case 1:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 26, -1, -1);
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc cargo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                    "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                    "Đến\nTrái Đất", "Đến\nXayda", "Siêu thị");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 24, -1, -1);
                                break;
                            case 1:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 26, -1, -1);
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc cui(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        switch (this.mapId) {
                            case 19:
                                int taskId = TaskService.gI().getIdTask(pl);
                                switch (taskId) {
                                    case ConstTask.TASK_19_0:
                                        this.createOtherMenu(pl, ConstNpc.MENU_FIND_KUKU,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến chỗ\nKuku\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)", "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                    case ConstTask.TASK_19_1:
                                        this.createOtherMenu(pl, ConstNpc.MENU_FIND_MAP_DAU_DINH,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến chỗ\nMập đầu đinh\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)", "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                    case ConstTask.TASK_19_2:
                                        this.createOtherMenu(pl, ConstNpc.MENU_FIND_RAMBO,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến chỗ\nRambo\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)", "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                    default:
                                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                }
                                break;
                            case 68:
                                this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                        "Ngươi muốn về Thành Phố Vegeta", "Đồng ý", "Từ chối");
                                break;
                            default:
                                this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                        "Tàu vũ trụ Xayda sử dụng công nghệ mới nhất, "
                                        + "có thể đưa ngươi đi bất kỳ đâu, chỉ cần trả tiền là được.",
                                        "Đến\nTrái Đất", "Đến\nNamếc", "Đến\nSiêu thị");
                                break;
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 26) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 24, -1, -1);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 25, -1, -1);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 19) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_KUKU) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossById(BossID.KUKU);
                                    if (boss != null && !boss.isDie() && boss.zone != null && !MapService.gI().isMapPhoBan(boss.zone.map.mapId)) {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId, boss.zone.zoneId);
                                            if (z.getNumOfPlayers() < z.maxPlayer) {
                                                player.inventory.gold -= COST_FIND_BOSS;
                                                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x, boss.location.y);
                                                Service.gI().sendMoney(player);
                                            } else {
                                                Service.gI().sendThongBao(player, "Khu vực đang full.");
                                            }
                                        } else {
                                            Service.gI().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold) + " vàng");
                                        }
                                        break;
                                    }
                                    Service.gI().sendThongBao(player, "Chết rồi ba...");
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_MAP_DAU_DINH) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossById(BossID.MAP_DAU_DINH);
                                    if (boss != null && !boss.isDie() && boss.zone != null && !MapService.gI().isMapPhoBan(boss.zone.map.mapId)) {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId, boss.zone.zoneId);
                                            if (z.getNumOfPlayers() < z.maxPlayer) {
                                                player.inventory.gold -= COST_FIND_BOSS;
                                                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x, boss.location.y);
                                                Service.gI().sendMoney(player);
                                            } else {
                                                Service.gI().sendThongBao(player, "Khu vực đang full.");
                                            }
                                        } else {
                                            Service.gI().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold) + " vàng");
                                        }
                                        break;
                                    }
                                    Service.gI().sendThongBao(player, "Chết rồi ba...");
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_RAMBO) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossById(BossID.RAMBO);
                                    if (boss != null && !boss.isDie() && boss.zone != null && !MapService.gI().isMapPhoBan(boss.zone.map.mapId)) {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId, boss.zone.zoneId);
                                            if (z.getNumOfPlayers() < z.maxPlayer) {
                                                player.inventory.gold -= COST_FIND_BOSS;
                                                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x, boss.location.y);
                                                Service.gI().sendMoney(player);
                                            } else {
                                                Service.gI().sendThongBao(player, "Khu vực đang full.");
                                            }
                                        } else {
                                            Service.gI().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold) + " vàng");
                                        }
                                        break;
                                    }
                                    Service.gI().sendThongBao(player, "Chết rồi ba...");
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 68) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 19, -1, 1100);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc shopvip(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                new Thread(() -> {
                    try {
                        while (true) {
                            Thread.sleep(5000);
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000);
                                    this.npcChat(player, "Tao Bán Toàn Hàng Hiếm");
                                } catch (InterruptedException e) {
                                }
                            }).start();
                        }
                    } catch (InterruptedException e) {
                    }
                }).start();
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin chào, ta có một số vật phẩm đặt biệt cậu có muốn xem không?",
                            "Cửa hàng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop
                                    ShopServiceNew.gI().opendShop(player, "SHOPLO", false);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc santa(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin chào, ta có một số vật phẩm đặt biệt cậu có muốn xem không?\n"
                            + "GiftCode: kuro, kuro1, kuro2, kuro3, kuro4\n"
                            + "Số dư của con là: " + Util.formatNumber(player.getSession().vnd),
                            "Cửa hàng", "Mở Rộng Hành Trang Rương Đồ", "Nhập Mã Quà Tặng", "Cửa Hàng Sử Dụng", "Danh Hiệu", "Tiệm Hớt Tóc", "Shop Thỏi Vàng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                    if (this.mapId == 5 || this.mapId == 13 || this.mapId == 20) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop
                                    ShopServiceNew.gI().opendShop(player, "SANTA", false);
                                    break;
                                case 1: //shop
                                    ShopServiceNew.gI().opendShop(player, "SANTA_CHEST", false);
                                    break;
                                case 2:
                                    Input.gI().createFormGiftCode(player);
                                    break;
                                case 3:
                                    ShopServiceNew.gI().opendShop(player, "HSD", false);
                                    break;
                                case 4:
                                    createOtherMenu(player, ConstNpc.DANH_HIEU, "Ngươi Muốn Gì Ở Ta", "Shop Danh Hiệu", "Bật Tắt Danh Hiệu");
                                    break;
                                case 5:
                                    ShopServiceNew.gI().opendShop(player, "SANTA_HEAD", false);
                                    break;
                                case 6: //shop
                                    ShopServiceNew.gI().opendShop(player, "SHOP_TV", false);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.DANH_HIEU) {
                            switch (select) {
                                case 0:
                                    ShopServiceNew.gI().opendShop(player, "DANH_HIEU", false);
                                    break;
                                case 1:
                                    this.createOtherMenu(player, 20, "Chào con, con muốn ta giúp gì nào?",
                                            (player.isTitleUse1 == false ? "Bật" : "Tắt") + " Danh Hiệu\nĐại Gia Mới Nhú",
                                            (player.isTitleUse2 == false ? "Bật" : "Tắt") + " Danh Hiệu\nThánh Đập Đồ",
                                            (player.isTitleUse3 == false ? "Bật" : "Tắt") + " Danh Hiệu\nBị Móc Sạch Túi",
                                            (player.isTitleUse4 == false ? "Bật" : "Tắt") + " Danh Hiệu\nTrùm Ước Rồng",
                                            (player.isTitleUse5 == false ? "Bật" : "Tắt") + " Danh Hiệu\nNông Dân Chăm Chỉ",
                                            (player.isTitleUse6 == false ? "Bật" : "Tắt") + " Danh Hiệu\nTrùm Săn Boss",
                                            (player.isTitleUse7 == false ? "Bật" : "Tắt") + " Danh Hiệu\nÔng Thần Ve Chai",
                                            "Đóng");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 20) {
                            switch (select) {
                                case 0:
                                    player.isTitleUse1 = !player.isTitleUse1;
                                    Service.gI().removeTitle(player);
                                    Service.gI().sendThongBao(player, "Đã " + (player.isTitleUse1 == true ? "Bật" : "Tắt") + " Danh Hiệu Đại Gia Mới Nhú!");
                                    break;
                                case 1:
                                    player.isTitleUse2 = !player.isTitleUse2;
                                    Service.gI().removeTitle(player);
                                    Service.gI().sendThongBao(player, "Đã " + (player.isTitleUse2 == true ? "Bật" : "Tắt") + " Danh Hiệu Thánh Đập Đồ!");
                                    break;
                                case 2:
                                    player.isTitleUse3 = !player.isTitleUse3;
                                    Service.gI().removeTitle(player);
                                    Service.gI().sendThongBao(player, "Đã " + (player.isTitleUse3 == true ? "Bật" : "Tắt") + " Danh Hiệu Bị Móc Sạch Túi!");
                                    break;
                                case 3:
                                    player.isTitleUse4 = !player.isTitleUse4;
                                    Service.gI().removeTitle(player);
                                    Service.gI().sendThongBao(player, "Đã " + (player.isTitleUse4 == true ? "Bật" : "Tắt") + " Danh Hiệu Trùm Ước Rồng!");
                                    break;
                                case 4:
                                    player.isTitleUse5 = !player.isTitleUse5;
                                    Service.gI().removeTitle(player);
                                    Service.gI().sendThongBao(player, "Đã " + (player.isTitleUse5 == true ? "Bật" : "Tắt") + " Danh Hiệu Nông Dân Chăm Chỉ!");
                                    break;
                                case 5:
                                    player.isTitleUse6 = !player.isTitleUse6;
                                    Service.gI().removeTitle(player);
                                    Service.gI().sendThongBao(player, "Đã " + (player.isTitleUse6 == true ? "Bật" : "Tắt") + " Danh Hiệu Trùm Săn Boss!");
                                    break;
                                case 6:
                                    player.isTitleUse7 = !player.isTitleUse7;
                                    Service.gI().removeTitle(player);
                                    Service.gI().sendThongBao(player, "Đã " + (player.isTitleUse7 == true ? "Bật" : "Tắt") + " Danh Hiệu Ông Thần Ve Chai!");
                                    break;
                            }

                        }
                    }
                }
            }
        };
    }

    public static Npc chiChi(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin chào, ta có một số vật phẩm đặt biệt cậu có muốn xem không?\n",
                            "Shop Sự Kiện", "Top Sự Kiện", "Đệ Vip");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop
                                    ShopServiceNew.gI().opendShop(player, "CHI_CHI", false);
                                    break;
                                case 1:
                                    Service.gI().showListTop(player, Manager.topNoel);
                                    break;
                                case 2:
                                    this.createOtherMenu(player, ConstNpc.BUY_PET_VIP, "|7|Số tiền của bạn còn : " + player.getSession().vnd + "\n"
                                            + "|7|Đệ Kid Bill: 30.000, Hợp thể tăng 35% chỉ số",
                                            "Kid Bill", "Đóng");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.BUY_PET_VIP) {
                            PreparedStatement ps = null;
                            try (Connection con = GirlkunDB.getConnection();) {
                                switch (select) {
                                    case 0:
                                        if (player.getSession().vnd < 30000) {
                                            Service.gI().sendThongBao(player, "Bạn không có đủ 30k coin");
                                            return;
                                        }
                                        if (player.pet == null) {
                                            Service.gI().sendThongBao(player, "Bạn cần phải có đệ tử thường trước");
                                            return;
                                        }

                                        player.getSession().vnd -= 30000;
                                        PetService.gI().createKidBill(player, true, player.pet.gender);
                                        break;
                                }

                                ps = con.prepareStatement("update account set vnd = ? where id = ?");
                                ps.setInt(1, player.getSession().vnd);
                                ps.setInt(2, player.getSession().userId);
                                ps.executeUpdate();
                                ps.close();

                            } catch (Exception e) {
                                Logger.logException(NpcFactory.class, e, "Lỗi update vnd " + player.name);
                            } finally {
                                try {
                                    if (ps != null) {
                                        ps.close();
                                    }
                                } catch (SQLException ex) {
                                    System.out.println("Lỗi khi update vnd");

                                }
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc shopTanThu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.map.mapId == 21 || this.map.mapId == 22 || this.map.mapId == 23) {

                        createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Xin chào, ta có một số vật phẩm đặt biệt cậu có muốn xem không?\n",
                                "Shop Tân Thủ", "Đóng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.map.mapId == 21 || this.map.mapId == 22 || this.map.mapId == 23) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop
                                    ShopServiceNew.gI().opendShop(player, "TAN_THU", false);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc cayThong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Đang Có " + EVENT_COUNT_NOEL % 10000 + " Lượt Trang Trí\n"
                            + "|1|Trang trí 2000 lượt sẽ tặng: x2 exp toàn máy chủ 12 giờ\n"
                            + "Trang trí 5000 lượt sẽ tặng: x3 exp toàn máy chủ 24 giờ\n"
                            + "Trang trí 10000 lượt sẽ tặng: x3 exp toàn máy chủ 72 giờ\n"
                            + "|7|Cách tìm vật liệu:\n"
                            + "|5|- Chuông đồng và Quả châu: Đánh quái tại các Map lạnh giá - được phép giao dịch. \n"
                            + "- Ngôi sao: đánh quái tại các phó bản - được phép giao dịch.\n"
                            + "- Dây kim tuyến: làm các nhiệm vụ khó ở bò mộng - được phép giao dịch.\n"
                            + "- Móc treo noel: Bán tại Chi Chi 1,2 Tỷ Vàng",
                            "Trang Trí Quả Châu", "Trang Trí Ngọc Rồng 1 Sao", "Vùng Đất Băng Giá Sự Kiện Noel", "Đóng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    Item Chuong = InventoryServiceNew.gI().findItemBag(player, 1478);
                                    Item QuaChau = InventoryServiceNew.gI().findItemBag(player, 1482);
                                    Item NgoiSao = InventoryServiceNew.gI().findItemBag(player, 1481);
                                    Item DayKimTuyen = InventoryServiceNew.gI().findItemBag(player, 1479);
                                    Item MocTreo = InventoryServiceNew.gI().findItemBag(player, 1480);
                                    if (Chuong != null && Chuong.quantity >= 30 && QuaChau != null && QuaChau.quantity >= 30
                                            && NgoiSao != null && NgoiSao.quantity >= 30 && DayKimTuyen != null && DayKimTuyen.quantity >= 2 && MocTreo != null && MocTreo.quantity >= 1) {
                                        createOtherMenu(player, ConstNpc.TRANG_TRI_QUA_TRAU,
                                                "|2|Để trang trí cần\n"
                                                + "|1|Chuông " + Chuong.quantity + "/30\n"
                                                + "Quả Châu " + QuaChau.quantity + "/30\n"
                                                + "Ngôi Sao " + NgoiSao.quantity + "/30\n"
                                                + "Dây Kim Tuyến " + DayKimTuyen.quantity + "/2\n"
                                                + "Móc treo Noel " + MocTreo.quantity + "/1\n",
                                                "Đồng ý", "Từ chối");
                                    } else {
                                        StringBuilder NpcSay = new StringBuilder("|2|Để trang trí cần\n");
                                        NpcSay.append(Chuong == null ? "|7|Chuông 0/30\n" : "|1|Chuôngn" + Chuong.quantity + "/30\n");
                                        NpcSay.append(QuaChau == null ? "|7|Quả Châu 0/30\n" : "|1|Quả Châu " + QuaChau.quantity + "/30\n");
                                        NpcSay.append(NgoiSao == null ? "|7|Ngôi Sao 0/30\n" : "|1|Ngôi Sao" + NgoiSao.quantity + "/30\n");
                                        NpcSay.append(DayKimTuyen == null ? "|7|Dây Kim Tuyến 0/2\n" : "|1|Dây Kim Tuyến " + DayKimTuyen.quantity + "/2\n");
                                        NpcSay.append(MocTreo == null ? "|7|Móc Treo Noel 0/1\n" : "|1|Móc Treo Noel " + MocTreo.quantity + "/1\n");
                                        createOtherMenu(player, ConstNpc.TRANG_TRI_QUA_TRAU, NpcSay.toString(), "Từ chối");
                                    }
                                    break;
                                case 1:
                                    Item nro1 = InventoryServiceNew.gI().findItemBag(player, 14);
                                    Item nro2 = InventoryServiceNew.gI().findItemBag(player, 15);
                                    Item nro3 = InventoryServiceNew.gI().findItemBag(player, 16);
                                    if (nro1 != null && nro1.quantity >= 1 && nro2 != null && nro2.quantity >= 1
                                            && nro3 != null && nro3.quantity >= 3 && player.inventory.gem >= 50) {
                                        createOtherMenu(player, ConstNpc.TRANG_TRI_NRO,
                                                "|2|Để Trang Trí cần\n"
                                                + "|1|Ngọc rồng 1 sao " + nro1.quantity + "/1\n"
                                                + "Ngọc rồng 2 sao " + nro2.quantity + "/1\n"
                                                + "Ngọc rồng 3 sao " + nro3.quantity + "/3\n"
                                                + "|2|Giá ngọc: 50",
                                                "Đồng ý", "Từ chối");
                                    } else {
                                        StringBuilder NpcSay = new StringBuilder("|2|Để trang trí cần\n");
                                        NpcSay.append(nro1 == null ? "|7|Ngọc rồng 1 sao 0/1\n" : "|1|Ngọc rồng 1 sao" + nro1.quantity + "/1\n");
                                        NpcSay.append(nro2 == null ? "|7|Ngọc rồng 2 sao 0/1\n" : "|1|Ngọc rồng 2 sao " + nro2.quantity + "/1\n");
                                        NpcSay.append(nro3 == null ? "|7|Ngọc rồng 3 sao 0/3\n" : "|1|Ngọc rồng 3 sao " + nro3.quantity + "/3\n");
                                        NpcSay.append("|2|Giá ngọc: 50");
                                        createOtherMenu(player, ConstNpc.TRANG_TRI_NRO, NpcSay.toString(), "Từ chối");
                                    }
                                    break;
                                case 2:
                                    if (player.nPoint.power < 10000000000L) {
                                        Service.getInstance().sendThongBao(player,
                                                "Yêu cầu tối thiếu 10 tỷ sức mạnh");
                                        return;
                                    }
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 176, 92, 168);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.TRANG_TRI_QUA_TRAU) {
                            switch (select) {
                                case 0:
                                    Item Chuong = InventoryServiceNew.gI().findItemBag(player, 1478);
                                    Item QuaChau = InventoryServiceNew.gI().findItemBag(player, 1482);
                                    Item NgoiSao = InventoryServiceNew.gI().findItemBag(player, 1481);
                                    Item DayKimTuyen = InventoryServiceNew.gI().findItemBag(player, 1479);
                                    Item MocTreo = InventoryServiceNew.gI().findItemBag(player, 1480);
                                    if (Chuong != null && Chuong.quantity >= 30
                                            && QuaChau != null && QuaChau.quantity >= 30
                                            && NgoiSao != null && NgoiSao.quantity >= 30
                                            && DayKimTuyen != null && DayKimTuyen.quantity >= 2
                                            && MocTreo != null && MocTreo.quantity >= 1) {

                                        EVENT_COUNT_NOEL += 30;

                                        String text = null;
                                        AttributeManager am = ServerManager.gI().getAttributeManager();

                                        if (EVENT_COUNT_NOEL >= 10000) {
                                            am.setTime(ConstAttribute.TNSM, 72 * 3600);
                                            am.setValue(ConstAttribute.TNSM, 3);
                                            text = "Toàn máy chủ đã đạt 10000 điểm! Tăng x3 exp trong 72 giờ!";
                                        } else if (EVENT_COUNT_NOEL >= 5000) {
                                            am.setTime(ConstAttribute.TNSM, 24 * 3600);
                                            am.setValue(ConstAttribute.TNSM, 3);
                                            text = "Toàn máy chủ đã đạt 5000 điểm! Tăng x3 exp trong 24 giờ!";
                                        } else if (EVENT_COUNT_NOEL >= 2000) {
                                            am.setTime(ConstAttribute.TNSM, 12 * 3600);
                                            am.setValue(ConstAttribute.TNSM, 2);
                                            text = "Toàn máy chủ đã đạt 2000 điểm! Tăng x2 exp trong 12 giờ!";
                                        }

                                        if (text != null) {
                                            Service.getInstance().sendThongBaoAllPlayer(text);
                                        }

                                        InventoryServiceNew.gI().subQuantityItemsBag(player, Chuong, 30);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, QuaChau, 30);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, NgoiSao, 30);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, DayKimTuyen, 2);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, MocTreo, 1);
                                        InventoryServiceNew.gI().sendItemBags(player);

                                        Service.getInstance().sendThongBao(player, "Trang trí thành công!");
                                    }
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.TRANG_TRI_NRO) {
                            switch (select) {
                                case 0:
                                    Item nro1 = InventoryServiceNew.gI().findItemBag(player, 14);
                                    Item nro2 = InventoryServiceNew.gI().findItemBag(player, 15);
                                    Item nro3 = InventoryServiceNew.gI().findItemBag(player, 16);
                                    if (nro1 != null && nro1.quantity >= 1
                                            && nro2 != null && nro2.quantity >= 1
                                            && nro3 != null && nro3.quantity >= 3 && player.inventory.gem >= 50) {

                                        EVENT_COUNT_NOEL += 30;

                                        String text = null;
                                        AttributeManager am = ServerManager.gI().getAttributeManager();

                                        // Kiểm tra các mốc điểm để tăng tiềm năng
                                        if (EVENT_COUNT_NOEL >= 10000) {
                                            // Đạt 10000 điểm: x3 exp trong 72 giờ
                                            am.setTime(ConstAttribute.TNSM, 72 * 3600);
                                            am.setValue(ConstAttribute.TNSM, 3);
                                            text = "Toàn máy chủ đã đạt 10000 điểm! Tăng x3 exp trong 72 giờ!";
                                        } else if (EVENT_COUNT_NOEL >= 5000) {
                                            // Đạt 5000 điểm: x3 exp trong 24 giờ
                                            am.setTime(ConstAttribute.TNSM, 24 * 3600);
                                            am.setValue(ConstAttribute.TNSM, 3);
                                            text = "Toàn máy chủ đã đạt 5000 điểm! Tăng x3 exp trong 24 giờ!";
                                        } else if (EVENT_COUNT_NOEL >= 2000) {
                                            // Đạt 2000 điểm: x2 exp trong 12 giờ
                                            am.setTime(ConstAttribute.TNSM, 12 * 3600);
                                            am.setValue(ConstAttribute.TNSM, 2);
                                            text = "Toàn máy chủ đã đạt 2000 điểm! Tăng x2 exp trong 12 giờ!";
                                        }

                                        if (text != null) {
                                            Service.getInstance().sendThongBaoAllPlayer(text);
                                        }
                                        player.inventory.gem -= 50;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, nro1, 1);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, nro2, 1);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, nro3, 3);
                                        InventoryServiceNew.gI().sendItemBags(player);

                                        Service.getInstance().sendThongBao(player, "Trang trí ngọc rồng thành công!");
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc leTan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Kính chào khách hàng!",
                            "Mua\nChip", "Đổi\nChip", "Đóng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.getSession().actived) {
                                    this.createOtherMenu(player, ConstNpc.MUA_CHIP,
                                            "Giá bán của 1 Chip hiện tại là " + Input.GIA_MUA_CHIP
                                            + "TV\nNgươi có muốn mua bao nhiêu?",
                                            "Mua\nChip", "Đóng");
                                } else {
                                    this.npcChat(player, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                }
                                break;
                            case 1:
                                if (player.getSession().actived) {
                                    this.createOtherMenu(player, ConstNpc.DOI_CHIP,
                                            "Giá đổi của 1 Chip hiện tại là " + Input.GIA_BAN_CHIP
                                            + "TV\nNgươi có muốn đổi bao nhiêu?",
                                            "Đổi\nChip", "Đóng");
                                } else {
                                    this.npcChat(player, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MUA_CHIP) {
                        CombineServiceNew.TradeChip(player, select, 0);
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.DOI_CHIP) {
                        CombineServiceNew.TradeChip(player, select, 1);
                    }
                }
            }
        };
    }

    public static Npc tapion(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (mapId == 19) {
                        this.createOtherMenu(player, 0, "Ác quỷ truyền thuyết Hirudegarn\nđã thoát khỏi phong ấn ngàn năm\nHãy giúp tôi chế ngự nó", "OK", "Từ chối");
                    } else if (mapId == 187) {
                        this.createOtherMenu(player, 0, "Tôi sẽ đưa bạn về", "OK", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (mapId == 19) {
                        Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        if (hour >= 22 && hour <= 23) {
                            ChangeMapService.gI().changeMapNonSpaceship(player, 187, 200 + Util.nextInt(-100, 100), 336);
                        } else {
                            Service.gI().sendThongBao(player, "Vui lòng quay lại vào lúc 22h");
                        }
                    } else if (mapId == 187) {
                        ChangeMapService.gI().changeMapNonSpaceship(player, 19, 1000 + Util.nextInt(-100, 100), 360);
                    }
                }
            }
        };
    }

    public static Npc taixiu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                createOtherMenu(player, 0, "\b|8|Trò chơi Tài Xỉu đang được diễn ra\n\n|6|Thử vận may của bạn với trò chơi Tài Xỉu! Đặt cược và dự đoán đúng"
                        + "\n kết quả, bạn sẽ được nhận thưởng lớn. Hãy tham gia ngay và\n cùng trải nghiệm sự hồi hộp, thú vị trong trò chơi này!"
                        + "\n\n|7|(Điều kiện tham gia : mở thành viên)\n\n|2|Đặt tối thiểu: 10 Thỏi Vàng\n Tối đa: 1.000 Thỏi Vàng"
                        + "\n\n|7| Lưu ý : Thoát game khi chốt Kết quả sẽ MẤT Tiền cược và Tiền thưởng", "Thể lệ", "Tham gia");
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (canOpenNpc(pl)) {
                    String time = ((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) + " giây";
                    if (pl.iDMark.getIndexMenu() == 0) {
                        if (select == 0) {
                            createOtherMenu(pl, ConstNpc.IGNORE_MENU, "|5|Có 2 nhà cái Tài và Xĩu, bạn chỉ được chọn 1 nhà để tham gia"
                                    + "\n\n|6|Sau khi kết thúc thời gian đặt cược. Hệ thống sẽ tung xí ngầu để biết kết quả Tài Xỉu"
                                    + "\n\nNếu Tổng số 3 con xí ngầu <=10 : XỈU\nNếu Tổng số 3 con xí ngầu >10 : TÀI\nNếu 3 Xí ngầu cùng 1 số : TAM HOA (Nhà cái lụm hết)"
                                    + "\n\n|7|Lưu ý: Số Thỏi Vàng nhận được sẽ bị nhà cái lụm đi 20%. Trong quá trình diễn ra khi đặt cược nếu thoát game trong lúc phát thưởng phần quà sẽ bị HỦY", "Ok");
                        } else if (select == 1) {
                            if (TaiXiu.gI().baotri == false) {
                                if (pl.goldTai == 0 && pl.goldXiu == 0) {
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                } else if (pl.goldTai > 0) {
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI TÀI XỈU---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                } else {
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                }
                            } else {
                                if (pl.goldTai == 0 && pl.goldXiu == 0) {
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                } else if (pl.goldTai > 0) {
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                } else {
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n\nTổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng\n\n|5|Thời gian còn lại: " + time + "\n\n|7|Bạn đã cược Xỉu : " + Util.format(pl.goldXiu) + " Thỏi Vàng" + "\n\n|7|Hệ thống sắp bảo trì", "Cập nhập", "Đóng");
                                }
                            }
                        }
                    } else if (pl.iDMark.getIndexMenu() == 1) {
                        if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldTai == 0 && pl.goldXiu == 0 && TaiXiu.gI().baotri == false) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                    break;
                                case 1:
                                    if (!pl.getSession().actived) {
                                        Service.gI().sendThongBao(pl, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                    } else {
                                        Input.gI().TAI_taixiu(pl);
                                    }
                                    break;
                                case 2:
                                    if (!pl.getSession().actived) {
                                        Service.gI().sendThongBao(pl, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                    } else {
                                        Input.gI().XIU_taixiu(pl);
                                    }
                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldTai > 0 && TaiXiu.gI().baotri == false) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");

                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldXiu > 0 && TaiXiu.gI().baotri == false) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldTai > 0 && TaiXiu.gI().baotri == true) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");

                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldXiu > 0 && TaiXiu.gI().baotri == true) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");

                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldXiu == 0 && pl.goldTai == 0 && TaiXiu.gI().baotri == true) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---BÁN NHÀ BÁN XE CHƠI TÀI XỈU ĐI CÁC ÔNG---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Thỏi Vàng"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");

                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc uron(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    ShopServiceNew.gI().opendShop(pl, "URON", false);
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc baHatMit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            String[] menuselect = new String[]{};

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    switch (this.mapId) {
                        case 5:
                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Ngươi tìm ta có việc gì?",
                                    "Chức Năng Pha Lê Hóa",
                                    "Nâng Cấp Chân Mệnh",
                                    "Võ Đài\nSinh Tử");
                            break;
                        case 112:
                            if (Util.isAfterMidnight(player.lastTimePKVoDaiSinhTu)) {
                                player.haveRewardVDST = false;
                                player.thoiVangVoDaiSinhTu = 0;
                            }
                            if (VoDaiManager.gI().getVDST(player.zone) != null) {
                                if (VoDaiManager.gI().getVDST(player.zone).getPlayer().equals(player)) {
                                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                            "Ngươi muốn hủy đăng ký thi đấu võ đài?",
                                            "Nhận Quà", "Đồng ý\n" + player.thoiVangVoDaiSinhTu + " thỏi vàng", "Từ chối", "Về\nđảo rùa");
                                    return;
                                }
                                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                        "Ngươi muốn đăng ký thi đấu võ đài?\nnhiều phần thưởng giá trị đang đợi ngươi đó",
                                        "Nhận Quà", "Bình chọn", "Đồng ý\n" + player.thoiVangVoDaiSinhTu + " thỏi vàng", "Từ chối", "Về\nđảo rùa");
                                return;
                            }
                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Ngươi muốn đăng ký thi đấu võ đài?\nnhiều phần thưởng giá trị đang đợi ngươi đó",
                                    "Nhận Quà", "Đồng ý\n" + player.thoiVangVoDaiSinhTu + " thỏi vàng", "Từ chối", "Về\nđảo rùa");
                            break;
                        default:
                            if (player.diemDanhBaHatMit == 0) {
                                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                        "Ngươi tìm ta có việc gì?",
                                        "Nhận Ngẫu\nNhiên X5\nNgọc Rồng", "Cửa hàng\nBùa", "Nâng cấp\nVật phẩm",
                                        "Nhập\nNgọc Rồng", "Sách Tuyệt Kỹ", "Chức Năng\nBông Tai");
                            } else {
                                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                        "Ngươi tìm ta có việc gì?",
                                        "Cửa hàng\nBùa", "Nâng cấp\nVật phẩm",
                                        "Nhập\nNgọc Rồng", "Sách Tuyệt Kỹ", "Chức Năng\nBông Tai");
                            }
                            break;
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    Item laTraTuoi;
                    switch (this.mapId) {
                        case 5:
                            if (player.iDMark.isBaseMenu()) {
                                switch (select) {
                                    case 0:
                                        this.createOtherMenu(player, ConstNpc.CHUC_NANG_PHA_LE_NEW,
                                                "Ngươi tìm ta có việc gì?",
                                                "Ép sao\ntrang bị",
                                                "Pha lê\nhóa trang bị",
                                                "Nâng cấp\nSao pha lê",
                                                "Cường hoá\nLỗ sao\npha lê",
                                                "Tạo đá Hematite");
                                        break;
                                    case 1:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_CHAN_MENH);
                                        break;
                                    case 2:
                                        ChangeMapService.gI().changeMapNonSpaceship(player, 112, 203, 408);
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.CHUC_NANG_PHA_LE_NEW) {
                                switch (select) {
                                    case 0:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.EP_SAO_TRANG_BI);
                                        break;
                                    case 1:
                                        createOtherMenu(player, ConstNpc.MENU_PHA_LE_HOA_TRANG_BI, "Ngươi muốn pha lê hoá trang bị bằng cách nào?", "Bằng ngọc", "Từ chối");
                                        break;
                                    case 2:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_SAO_PHA_LE);
                                        break;
                                    case 3:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.CUONG_HOA_LO_SAO_PHA_LE);
                                        break;
                                    case 4:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TAO_DA_HEMATITE);
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHA_LE_HOA_TRANG_BI) {
                                if (select == 0) {
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHA_LE_HOA_TRANG_BI);
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                                switch (player.combineNew.typeCombine) {
                                    case CombineServiceNew.NANG_CAP_CHAN_MENH:
                                    case CombineServiceNew.EP_SAO_TRANG_BI:
                                    case CombineServiceNew.NANG_CAP_SAO_PHA_LE:
                                    case CombineServiceNew.CUONG_HOA_LO_SAO_PHA_LE:
                                    case CombineServiceNew.TAO_DA_HEMATITE:
                                    case CombineServiceNew.PHA_LE_HOA_TRANG_BI:
                                        switch (select) {
                                            case 0:
                                                CombineServiceNew.gI().startCombine(player, 100);
                                                break;
                                            case 1:
                                                CombineServiceNew.gI().startCombine(player, 10);
                                                break;
                                            case 2:
                                                CombineServiceNew.gI().startCombine(player);
                                                break;
                                        }
                                        break;
                                    default:
                                        CombineServiceNew.gI().startCombine(player, select);
                                        break;
                                }
                            }
                            break;
                        case 112:
                            if (player.iDMark.isBaseMenu()) {
                                if (player.haveRewardVDST) {
                                    switch (select) {
                                        case 0:
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                Item item = ItemService.gI().createNewItem((short) (Util.nextInt(705, 708)));
                                                item.itemOptions.add(new Item.ItemOption(93, 30));
                                                InventoryServiceNew.gI().addItemBag(player, item);
                                                InventoryServiceNew.gI().sendItemBags(player);
                                                Service.gI().sendThongBao(player, "Bạn nhận được " + item.template.name);
                                                player.haveRewardVDST = false;
                                            } else {
                                                Service.gI().sendThongBao(player, "Hành trang không còn chỗ trống, không thể nhặt thêm");
                                            }
                                            break;
                                        case 1:
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                Item item = ItemService.gI().createNewItem((short) 585);
                                                item.itemOptions.add(new Item.ItemOption(93, 30));
                                                InventoryServiceNew.gI().addItemBag(player, item);
                                                InventoryServiceNew.gI().sendItemBags(player);
                                                Service.gI().sendThongBao(player, "Bạn nhận được " + item.template.name);
                                                player.haveRewardVDST = false;
                                            } else {
                                                Service.gI().sendThongBao(player, "Hành trang không còn chỗ trống, không thể nhặt thêm");
                                            }
                                            break;
                                    }
                                    return;
                                }
                                if (VoDaiManager.gI().getVDST(player.zone) != null) {
                                    if (VoDaiManager.gI().getVDST(player.zone).getPlayer().equals(player)) {
                                        switch (select) {
                                            case 0:
                                                break;
                                            case 1:
                                                this.npcChat("Không thể thực hiện");
                                                break;
                                            case 2:
                                                break;
                                            case 3:
                                                ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 1156);
                                                break;
                                        }
                                        return;
                                    }
                                    switch (select) {
                                        case 0:
                                            break;
                                        case 1:
                                            this.createOtherMenu(player, ConstNpc.DAT_CUOC_HAT_MIT,
                                                    "Phí bình chọn là 1 triệu vàng\nkhi trận đấu kết thúc\n90% tổng tiền bình chọn sẽ chia đều cho phe bình chọn chính xác",
                                                    "Bình chọn cho " + VoDaiManager.gI().getVDST(player.zone).getPlayer().name + " (" + VoDaiManager.gI().getVDST(player.zone).getCuocPlayer() + ")",
                                                    "Bình chọn cho hạt mít (" + VoDaiManager.gI().getVDST(player.zone).getCuocBaHatMit() + ")");
                                            break;
                                        case 2:
                                            VoDaiService.gI().startChallenge(player);
                                            break;
                                        case 3:
                                            break;
                                        case 4:
                                            ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 1156);
                                            break;
                                    }
                                    return;
                                }
                                switch (select) {
                                    case 0:
                                        break;
                                    case 1:
                                        VoDaiService.gI().startChallenge(player);
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 1156);
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.DAT_CUOC_HAT_MIT) {
                                if (VoDaiManager.gI().getVDST(player.zone) != null) {
                                    switch (select) {
                                        case 0:
                                            if (player.inventory.gold >= 1_000_000) {
                                                VoDai vdst = VoDaiManager.gI().getVDST(player.zone);
                                                vdst.setCuocPlayer(vdst.getCuocPlayer() + 1);
                                                vdst.addBinhChon(player);
                                                player.binhChonPlayer++;
                                                player.zoneBinhChon = player.zone;
                                                player.inventory.gold -= 1_000_000;
                                                Service.gI().sendMoney(player);
                                            } else {
                                                Service.gI().sendThongBao(player, "Bạn không đủ vàng, còn thiếu " + Util.numberToMoney(1_000_000 - player.inventory.gold) + " vàng nữa");
                                            }
                                            break;
                                        case 1:
                                            if (player.inventory.gold >= 1_000_000) {
                                                VoDai vdst = VoDaiManager.gI().getVDST(player.zone);
                                                vdst.setCuocBaHatMit(vdst.getCuocBaHatMit() + 1);
                                                vdst.addBinhChon(player);
                                                player.binhChonHatMit++;
                                                player.zoneBinhChon = player.zone;
                                                player.inventory.gold -= 1_000_000;
                                                Service.gI().sendMoney(player);
                                            } else {
                                                Service.gI().sendThongBao(player, "Bạn không đủ vàng, còn thiếu " + Util.numberToMoney(1_000_000 - player.inventory.gold) + " vàng nữa");
                                            }
                                            break;
                                    }
                                }
                            }
                            break;
                        case 42:
                        case 43:
                        case 44:
                        case 84:
                            if (player.iDMark.isBaseMenu()) {
                                if (player.diemDanhBaHatMit == 0) {
                                    switch (select) {
                                        case 0:
                                            short[] rdpet1 = new short[]{16, 17, 18};
                                            Item _item = ItemService.gI().createNewItem((short) rdpet1[Util.nextInt(rdpet1.length - 1)], 5);
                                            _item.itemOptions.add(new Item.ItemOption(30, Util.nextInt(1, 24)));
                                            _item.itemOptions.add(new Item.ItemOption(93, Util.nextInt(1, 24)));
                                            InventoryServiceNew.gI().addItemBag(player, _item);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Service.getInstance().sendThongBao(player, "Chúc mừng bạn nhận được " + _item.template.name);
                                            ServerNotify.gI().notify("Chúc mừng " + player.name + " nhận được " + _item.template.name + " ");
                                            player.diemDanhBaHatMit = System.currentTimeMillis();
                                            break;
                                        case 1: //shop bùa
                                            createOtherMenu(player, ConstNpc.MENU_OPTION_SHOP_BUA,
                                                    "Bùa của ta rất lợi hại, nhìn ngươi yếu đuối thế này, chắc muốn mua bùa để "
                                                    + "mạnh mẽ à, mua không ta bán cho, xài rồi lại thích cho mà xem.",
                                                    "Bùa\n1 giờ", "Bùa\n8 giờ", "Bùa\n1 tháng", "Đóng");
                                            break;
                                        case 2:
                                            CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_VAT_PHAM);
                                            break;
                                        case 3:
                                            CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NHAP_NGOC_RONG);
                                            break;
                                        case 4:
                                            createOtherMenu(player, ConstNpc.SACH_TUYET_KY, "Ta có thể giúp gì cho ngươi ?",
                                                    "Đóng thành\nSách cũ",
                                                    "Đổi Sách\nTuyệt kỹ",
                                                    "Giám định\nSách",
                                                    "Tẩy\nSách",
                                                    "Nâng cấp\nSách\nTuyệt kỹ",
                                                    "Hồi phục\nSách",
                                                    "Phân rã\nSách");
                                            break;
                                        case 5:
                                            createOtherMenu(player, ConstNpc.CHUC_NANG_BONG_TAI_NE, "Ta có thể giúp gì cho ngươi ?",
                                                    "Nâng Cấp\nBông Tai",
                                                    "Mở Chỉ Số\nBông Tai");
                                            break;
                                    }
                                } else {
                                    switch (select) {
                                        case 0: //shop bùa
                                            createOtherMenu(player, ConstNpc.MENU_OPTION_SHOP_BUA,
                                                    "Bùa của ta rất lợi hại, nhìn ngươi yếu đuối thế này, chắc muốn mua bùa để "
                                                    + "mạnh mẽ à, mua không ta bán cho, xài rồi lại thích cho mà xem.",
                                                    "Bùa\n1 giờ", "Bùa\n8 giờ", "Bùa\n1 tháng", "Đóng");
                                            break;
                                        case 1:
                                            CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_VAT_PHAM);
                                            break;
                                        case 2:
                                            CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NHAP_NGOC_RONG);
                                            break;
                                        case 3:
                                            createOtherMenu(player, ConstNpc.SACH_TUYET_KY, "Ta có thể giúp gì cho ngươi ?",
                                                    "Đóng thành\nSách cũ",
                                                    "Đổi Sách\nTuyệt kỹ",
                                                    "Giám định\nSách",
                                                    "Tẩy\nSách",
                                                    "Nâng cấp\nSách\nTuyệt kỹ",
                                                    "Hồi phục\nSách",
                                                    "Phân rã\nSách");
                                            break;
                                        case 4:
                                            createOtherMenu(player, ConstNpc.CHUC_NANG_BONG_TAI_NE, "Ta có thể giúp gì cho ngươi ?",
                                                    "Nâng Cấp\nBông Tai",
                                                    "Mở Chỉ Số\nBông Tai");
                                            break;
                                    }
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.CHUC_NANG_BONG_TAI_NE) {
                                switch (select) {
                                    case 0:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI);
                                        break;
                                    case 1:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_BONG_TAI);
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.SACH_TUYET_KY) {
                                switch (select) {
                                    case 0:
                                        CheTaoCuonSachCu.showCombine(player);
                                        break;
                                    case 1:
                                        DoiSachTuyetKy.showCombine(player);
                                        break;
                                    case 2:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.GIAM_DINH_SACH);
                                        break;
                                    case 3:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TAY_SACH);
                                        break;
                                    case 4:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_SACH_TUYET_KY);
                                        break;
                                    case 5:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.HOI_PHUC_SACH);
                                        break;
                                    case 6:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHAN_RA_SACH);
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.DONG_THANH_SACH_CU) {
                                CheTaoCuonSachCu.cheTaoCuonSachCu(player);
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.DOI_SACH_TUYET_KY) {
                                DoiSachTuyetKy.doiSachTuyetKy(player);
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_SHOP_BUA) {
                                switch (select) {
                                    case 0:
                                        ShopServiceNew.gI().opendShop(player, "BUA_1H", true);
                                        break;
                                    case 1:
                                        ShopServiceNew.gI().opendShop(player, "BUA_8H", true);
                                        break;
                                    case 2:
                                        ShopServiceNew.gI().opendShop(player, "BUA_1M", true);
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                                switch (player.combineNew.typeCombine) {
                                    case CombineServiceNew.NANG_CAP_VAT_PHAM:
                                    case CombineServiceNew.NHAP_NGOC_RONG:
                                    case CombineServiceNew.GIAM_DINH_SACH:
                                    case CombineServiceNew.TAY_SACH:
                                    case CombineServiceNew.NANG_CAP_SACH_TUYET_KY:
                                    case CombineServiceNew.HOI_PHUC_SACH:
                                    case CombineServiceNew.PHAN_RA_SACH:
                                    case CombineServiceNew.NANG_CAP_BONG_TAI:
                                    case CombineServiceNew.MO_CHI_SO_BONG_TAI:
                                        if (select == 0) {
                                            CombineServiceNew.gI().startCombine(player);
                                        }
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_DOI_SKH_VIP) {
                                if (select == 0) {
                                    CombineServiceNew.gI().startCombine(player);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        };
    }

    public static Npc ruongDo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    InventoryServiceNew.gI().sendItemBox(player);
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc duongtank(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (mapId == 0) {
                        nguhs.gI().setTimeJoinnguhs();
                        long now = System.currentTimeMillis();
                        if (now > nguhs.TIME_OPEN_NHS && now < nguhs.TIME_CLOSE_NHS) {
                            this.createOtherMenu(player, 0, "|7|[ • MAP NGŨ HÀNH SON ĐÃ MỞ CỬA • ]\n"
                                    + "50 hồng ngọc 1 lần vào, tham gia ngay?\n"
                                    + "Bạn Cần Đạt Đủ 80 Tỷ Sức Mạnh và 50 Hồng Ngọc Để Có Thể Vào",
                                    "Chiến Ngay", "Đổi Chân Mệnh", "Đóng");
                        } else {
                            this.createOtherMenu(player, 0, "|7|Map Ngũ Hành Sơ đã mở cửa, 50 hồng ngọc 1 lần vào, tham gia ngay?", "Đổi Chân Mệnh", "Đóng");
                        }
                    }
                    if (mapId == 122) {
                        this.createOtherMenu(player, 0, "Bạn Muốn Quay Trở Lại Làng Aru?", "OK", "Từ chối");

                    }
                    if (mapId == 124) {
                        this.createOtherMenu(player, 0, "Xia xia thua phùa\b|7|Thí chủ đang có: " + player.NguHanhSonPoint + " điểm ngũ hành sơn\b|1|Thí chủ muốn đổi cải trang x4 chưởng ko?", "Âu kê", "Top Ngu Hanh Son", "No");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                Item daChanmenh;
                if (canOpenNpc(player)) {
                    if (mapId == 0) {
                        switch (select) {
                            case 0:
                                if (player.nPoint.power < 80000000000L) {
                                    Service.getInstance().sendThongBao(player, "Sức mạnh bạn không đủ để qua map!");
                                    return;
                                } else if (player.inventory.ruby < 50) {
                                    Service.getInstance().sendThongBao(player, "Phí vào là 50 hồng ngọc một lần bạn ey!\nBạn không đủ!");
                                    return;
                                } else {
                                    player.inventory.ruby -= 50;
                                    PlayerService.gI().sendInfoHpMpMoney(player);
                                    ChangeMapService.gI().changeMapInYard(player, 122, -1, -1);
                                }
                                break;
                            case 1:
                                daChanmenh = InventoryServiceNew.gI().findItemBag(player, 1241);
                                if (daChanmenh != null && daChanmenh.quantity < 99) {
                                    this.npcChat(player, "Bạn còn thiếu x" + (99 - daChanmenh.quantity) + " Đá Chân Mệnh.");
                                } else if (daChanmenh == null) {
                                    this.npcChat(player, "Bạn không có Đá Chân Mệnh.");
                                } else if (player.inventory.gold < 500_000_000) {
                                    this.npcChat(player, "Bạn còn thiếu x" + (Util.numberToMoney(500_000_000 - player.inventory.gold)) + " Vàng.");
                                } else {
                                    Item chanMenh = ItemService.gI().createNewItem((short) 1232);
                                    daChanmenh.quantity -= 99;
                                    player.inventory.gold -= 500_000_000;
                                    chanMenh.itemOptions.add(new Item.ItemOption(50, 1));
                                    chanMenh.itemOptions.add(new Item.ItemOption(77, 1));
                                    chanMenh.itemOptions.add(new Item.ItemOption(103, 1));
                                    Service.gI().sendMoney(player);
                                    InventoryServiceNew.gI().addItemBag(player, chanMenh);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "|7|Bạn đã nhận được " + chanMenh.template.name);
                                }
                                break;

                        }
                    } else if (mapId == 122) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapInYard(player, 0, -1, 469);
                        }
                    } else if (mapId == 124) {
                        if (select == 0) {
                            if (player.NguHanhSonPoint >= 500) {
                                player.NguHanhSonPoint -= 500;
                                Item item = ItemService.gI().createNewItem((short) (711));
                                item.itemOptions.add(new Item.ItemOption(49, 25));
                                item.itemOptions.add(new Item.ItemOption(77, 25));
                                item.itemOptions.add(new Item.ItemOption(103, 25));
                                item.itemOptions.add(new Item.ItemOption(207, 0));
                                item.itemOptions.add(new Item.ItemOption(33, 0));
                                InventoryServiceNew.gI().addItemBag(player, item);
                                Service.gI().sendThongBao(player, "Chúc Mừng Bạn Đổi Vật Phẩm Thành Công !");
                            } else {
                                Service.gI().sendThongBao(player, "Không đủ điểm, bạn còn " + (500 - player.pointPvp) + " điểm nữa");
                            }

                        }
                    }

                }
            }
        };
    }

    public static Npc dauThan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    player.magicTree.openMenuTree();
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MAGIC_TREE_NON_UPGRADE_LEFT_PEA:
                            if (select == 0) {
                                player.magicTree.harvestPea();
                            } else if (select == 1) {
                                if (player.magicTree.level == 10) {
                                    player.magicTree.fastRespawnPea();
                                } else {
                                    player.magicTree.showConfirmUpgradeMagicTree();
                                }
                            } else if (select == 2) {
                                player.magicTree.fastRespawnPea();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_NON_UPGRADE_FULL_PEA:
                            if (select == 0) {
                                player.magicTree.harvestPea();
                            } else if (select == 1) {
                                player.magicTree.showConfirmUpgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_CONFIRM_UPGRADE:
                            if (select == 0) {
                                player.magicTree.upgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_UPGRADE:
                            if (select == 0) {
                                player.magicTree.fastUpgradeMagicTree();
                            } else if (select == 1) {
                                player.magicTree.showConfirmUnuppgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_CONFIRM_UNUPGRADE:
                            if (select == 0) {
                                player.magicTree.unupgradeMagicTree();
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc calick(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            private final byte COUNT_CHANGE = 50;
            private int count;

            private void changeMap() {
                if (this.mapId != 102) {
                    count++;
                    if (this.count >= COUNT_CHANGE) {
                        count = 0;
                        this.map.npcs.remove(this);
                        Map map = MapService.gI().getMapForCalich();
                        this.mapId = map.mapId;
                        this.cx = Util.nextInt(100, map.mapWidth - 100);
                        this.cy = map.yPhysicInTop(this.cx, 0);
                        this.map = map;
                        this.map.npcs.add(this);
                    }
                }
            }

            @Override
            public void openBaseMenu(Player player) {
                player.iDMark.setIndexMenu(ConstNpc.BASE_MENU);
                if (TaskService.gI().getIdTask(player) < ConstTask.TASK_20_0) {
                    Service.gI().hideWaitDialog(player);
                    Service.gI().sendThongBao(player, "Không thể thực hiện");
                    return;
                }
                if (this.mapId != player.zone.map.mapId) {
                    Service.gI().sendThongBao(player, "Calích đã rời khỏi map!");
                    Service.gI().hideWaitDialog(player);
                    return;
                }

                if (this.mapId == 102) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào chú, cháu có thể giúp gì?",
                            "Kể\nChuyện", "Quay về\nQuá khứ");
                } else {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào chú, cháu có thể giúp gì?", "Kể\nChuyện", "Đi đến\nTương lai", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (this.mapId == 102) {
                    if (player.iDMark.isBaseMenu()) {
                        if (select == 0) {
                            //kể chuyện
                            NpcService.gI().createTutorial(player, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                        } else if (select == 1) {
                            //về quá khứ
                            ChangeMapService.gI().goToQuaKhu(player);
                        }
                    }
                } else if (player.iDMark.isBaseMenu()) {
                    if (select == 0) {
                        //kể chuyện
                        NpcService.gI().createTutorial(player, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                    } else if (select == 1) {
                        //đến tương lai
//                                    changeMap();
                        if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_20_0) {
                            ChangeMapService.gI().goToTuongLai(player);
                        }
                    } else {
                        Service.gI().sendThongBao(player, "Không thể thực hiện");
                    }
                }
            }
        };
    }

    public static Npc jaco(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Gô Tên, Calich và Monaka đang gặp chuyện ở hành tinh Potaufeu \n Hãy đến đó ngay", "Đến \nPotaufeu");
                    } else if (this.mapId == 139) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        if (player.getSession().player.nPoint.power >= 800000000L) {

                            ChangeMapService.gI().goToPotaufeu(player);
                        } else {
                            this.npcChat(player, "Bạn chưa đủ 800tr sức mạnh để vào!");
                        }
                    } else if (this.mapId == 139) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                //về trạm vũ trụ
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 24 + player.gender, -1, -1);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc npclytieunuong54(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                    createOtherMenu(player, ConstMiniGame.MENU_CHINH, "Mini game.", "Kéo\nBúa\nBao", "Con số\nmay mắn\nvàng", "Con số\nmay mắn\nngọc xanh", "Chọn ai đây", "Đóng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstMiniGame.MENU_CHINH:
                            switch (select) {
                                case 0: // Kéo búa bao
                                    createOtherMenu(player, ConstMiniGame.MENU_KEO_BUA_BAO, "Hãy chọn mức cược.", "1 Tr vàng", "5 Tr vàng", "10 Tr vàng");
                                    break;
                                case 1: // Con số may mắn vàng
                                    LuckyNumber.showMenu(this, player, false);
                                    player.iDMark.setGemCSMM(false);
                                    break;
                                case 2: // Con số may mắn ngọc
                                    LuckyNumber.showMenu(this, player, true);
                                    player.iDMark.setGemCSMM(true);
                                    break;
                                case 3: // Chọn ai đây
                                    DecisionMaker.gI().showMenu(this, player);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case ConstMiniGame.MENU_KEO_BUA_BAO:
                            RockPaperScissors.confirmMenu(this, player, select);
                            break;
                        case ConstMiniGame.MENU_PLAY_KEO_BUA_BAO:
                            if (player.iDMark.getTimePlayKeoBuaBao() - System.currentTimeMillis() > 0) {
                                RockPaperScissors.confirmPlay(this, player, select);
                            } else {
                                createOtherMenu(player, ConstMiniGame.MENU_KEO_BUA_BAO, "Hãy chọn mức cược.", "1 Tr vàng", "5 Tr vàng", "10 Tr vàng");
                            }
                            break;
                        case ConstMiniGame.MENU_CON_SO_MAY_MAN_VANG:

                            break;
                        case ConstMiniGame.MENU_CON_SO_MAY_MAN_NGOC:

                            break;
                        case ConstMiniGame.MENU_CHON_AI_DAY:
                            switch (select) {
                                case 0:
                                    DecisionMaker.gI().showTutorial(this, player);
                                    break;
                                case 1:
                                    DecisionMakerGold.showMenuSelect(this, player);
                                    break;
                                case 2:
                                    DecisionMakerRuby.showMenuSelect(this, player);
                                    break;
                                case 3:
                                    DecisionMakerGem.showMenuSelect(this, player);
                                    break;
                            }
                            break;
                        case ConstMiniGame.MENU_LUCKY_NUMBER:
                            if (select == 0) {
                                LuckyNumber.showMenu(this, player, player.iDMark.isGemCSMM());
                            }
                            break;
                        case ConstMiniGame.MENU_PLAY_LUCKY_NUMBER_GOLD:
                        case ConstMiniGame.MENU_PLAY_LUCKY_NUMBER_GEM:
                            switch (select) {
                                case 0:
                                    LuckyNumber.showMenu(this, player, player.iDMark.isGemCSMM());
                                    break;
                                case 1:
                                    Input.gI().createFormSelectOneNumberLuckyNumber(player, player.iDMark.isGemCSMM());
                                    break;
                                case 2:
                                    LuckyNumberService.addOneNumber(player, true);
                                    break;
                                case 3:
                                    LuckyNumberService.addOneNumber(player, false);
                                    break;
                                case 4:
                                    LuckyNumber.showMenuTutorials(this, player);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case ConstMiniGame.MENU_PLAY_DECISION_MAKER_GOLD:
                            switch (select) {
                                case 0:
                                    DecisionMakerGold.showMenuSelect(this, player);
                                    break;
                                case 1:
                                    DecisionMakerGold.selectPlay(this, player, true);
                                    break;
                                case 2:
                                    DecisionMakerGold.selectPlay(this, player, false);
                                    break;
                            }
                            break;
                        case ConstMiniGame.MENU_PLAY_DECISION_MAKER_GEM:
                            switch (select) {
                                case 0:
                                    DecisionMakerGem.showMenuSelect(this, player);
                                    break;
                                case 1:
                                    DecisionMakerGem.selectPlay(this, player, true);
                                    break;
                                case 2:
                                    DecisionMakerGem.selectPlay(this, player, false);
                                    break;
                            }
                            break;
                        case ConstMiniGame.MENU_PLAY_DECISION_MAKER_RUBY:
                            switch (select) {
                                case 0:
                                    DecisionMakerRuby.showMenuSelect(this, player);
                                    break;
                                case 1:
                                    DecisionMakerRuby.selectPlay(this, player, true);
                                    break;
                                case 2:
                                    DecisionMakerRuby.selectPlay(this, player, false);
                                    break;
                            }
                            break;
                        case ConstMiniGame.MENU_WAIT_NEW_GAME:
                            if (select == 0) {
                                DecisionMaker.gI().showTutorial(this, player);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

        };
    }

    public static Npc thuongDe(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    switch (mapId) {
                        case 45:
                            switch (player.levelLuyenTap) {
                                case 2:
                                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                            "Pôpô là đệ tử của ta, luyện tập với Pôpô con sẽ có thêm nhiều kinh nghiệm\nđánh bại được Pôpô ta sẽ dạy võ công cho con",
                                            player.dangKyTapTuDong ? "Hủy đăng\nký tập\ntự động" : "Đăng ký\ntập\ntự động", "Tập luyện\nvới\nMr.PôPô", "Thách đấu\nMr.PôPô", "Đến\nKaio", "Quay ngọc\nMay mắn");
                                    break;
                                case 3:
                                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                            "Từ nay con sẽ là đệ tử của ta. Ta sẽ truyền cho con tất cả tuyệt kĩ",
                                            player.dangKyTapTuDong ? "Hủy đăng\nký tập\ntự động" : "Đăng ký\ntập\ntự động", "Tập luyện\nvới\nThượng Đế", "Thách đấu\nThượng Đế", "Đến\nKaio", "Quay ngọc\nMay mắn");
                                    break;
                                default:
                                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                            "Con đã mạnh hơn ta, ta sẽ chỉ đường cho con đến Kaio\nđể gặp thần Vũ Trụ Phương Bắc\nNgài là thần cai quản vũ trụ này, hãy theo ngài ấy học võ công.",
                                            player.dangKyTapTuDong ? "Hủy đăng\nký tập\ntự động" : "Đăng ký\ntập\ntự động", "Tập luyện\nvới\nMr.PôPô", "Tập luyện\nvới\nThượng Đế", "Đến\nKaio", "Quay ngọc\nMay mắn");
                            }
                            break;
                        case 141:
                            this.createOtherMenu(player, 0,
                                    "Hãy nắm lấy tay ta mau!", "về\nthần điện");
                            break;
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (mapId) {
                        case 45:
                            if (player.iDMark.isBaseMenu()) {
                                switch (select) {
                                    case 0:
                                        if (player.clan != null && player.clan.ConDuongRanDoc != null && player.joinCDRD && player.clan.ConDuongRanDoc.allMobsDead && !player.talkToThuongDe) {
                                            player.talkToThuongDe = true;
                                            return;
                                        }
                                        if (player.dangKyTapTuDong) {
                                            player.dangKyTapTuDong = false;
                                            NpcService.gI().createTutorial(player, tempId, avartar, "Con đã hủy thành công đăng ký tập tự động\ntừ giờ con muốn tập Offline hãy tự đến đây trước");
                                            return;
                                        }
                                        this.createOtherMenu(player, 2001, "Đăng ký để mỗi khi Offline quá 30 phút, con sẽ được tự động luyện tập với tốc độ 1280 sức mạnh mỗi phút",
                                                "Hướng\ndẫn\nthêm", "Đồng ý\n1 ngọc\nmỗi lần", "Không\nđồng ý");
                                        break;
                                    case 1:
                                        switch (player.levelLuyenTap) {
                                            case 3:
                                                this.createOtherMenu(player, 2002, "Con có chắc muốn tập luyện ?\nTập luyện với ta sẽ tăng 160 sức mạnh mỗi phút",
                                                        "Đồng ý\nluyện tập", "Không\nđồng ý");
                                            default:
                                                this.createOtherMenu(player, 2002, "Con có chắc muốn tập luyện ?\nTập luyện với Mr.PôPô sẽ tăng 80 sức mạnh mỗi phút",
                                                        "Đồng ý\nluyện tập", "Không\nđồng ý");
                                        }
                                        break;
                                    case 2:
                                        switch (player.levelLuyenTap) {
                                            case 2:
                                                this.createOtherMenu(player, 2003, "Con có chắc muốn thách đấu ?\nNếu thắng Mr.PôPô sẽ được tập với ta, tăng 160 sức mạnh mỗi phút",
                                                        "Đồng ý\ngiao đấu", "Không\nđồng ý");
                                            case 3:
                                                this.createOtherMenu(player, 2003, "Con có chắc muốn thách đấu ?\nNếu thắng được ta, con sẽ được học võ với người mạnh hơn ta để tăng đến 320 sức mạnh mỗi phút",
                                                        "Đồng ý\ngiao đấu", "Không\nđồng ý");
                                                break;
                                            default:
                                                this.createOtherMenu(player, 2003, "Con có chắc muốn tập luyện ?\nTập luyện với ta sẽ tăng 160 sức mạnh mỗi phút",
                                                        "Đồng ý\nluyện tập", "Không\nđồng ý");
                                                break;
                                        }
                                        break;
                                    case 3:
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 48, -1, 354);
                                        break;
                                    case 4:
                                        this.createOtherMenu(player, ConstNpc.MENU_CHOOSE_LUCKY_ROUND,
                                                "Con muốn làm gì nào?", "Quay bằng\nvàng",
                                                "Quay bằng\nNgọc",
                                                "Rương phụ\n("
                                                + (player.inventory.itemsBoxCrackBall.size()
                                                - InventoryServiceNew.gI().getCountEmptyListItem(player.inventory.itemsBoxCrackBall))
                                                + " món)",
                                                "Xóa hết\ntrong rương", "Đóng");
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == 2001) {
                                switch (select) {
                                    case 0:
                                        NpcService.gI().createTutorial(player, tempId, avartar, ConstNpc.TAP_TU_DONG);
                                        break;
                                    case 1:
                                        player.mapIdDangTapTuDong = mapId;
                                        player.dangKyTapTuDong = true;
                                        NpcService.gI().createTutorial(player, tempId, avartar, "Từ giờ, quá 30 phút Offline con sẽ được tự động luyện tập");
                                        break;
                                }

                            } else if (player.iDMark.getIndexMenu() == 2002) {
                                switch (player.levelLuyenTap) {
                                    case 3:
                                        TrainingService.gI().callBoss(player, BossID.THUONG_DE, false);
                                        break;
                                    default:
                                        TrainingService.gI().callBoss(player, BossID.MRPOPO, false);
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == 2003) {
                                switch (player.levelLuyenTap) {
                                    case 2:
                                        TrainingService.gI().callBoss(player, BossID.MRPOPO, true);
                                        break;
                                    case 3:
                                        TrainingService.gI().callBoss(player, BossID.THUONG_DE, true);
                                        break;
                                    default:
                                        TrainingService.gI().callBoss(player, BossID.THUONG_DE, false);
                                        break;
                                }
                            } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_CHOOSE_LUCKY_ROUND) {
                                switch (select) {
                                    case 0:
                                        LuckyRound.gI().openCrackBallUI(player, LuckyRound.USING_GOLD);
                                        break;
                                    case 1:
                                        LuckyRound.gI().openCrackBallUI(player, LuckyRound.USING_GEM);
                                        break;
                                    case 2:
                                        ShopServiceNew.gI().opendShop(player, "ITEMS_LUCKY_ROUND", true);
                                        break;
                                    case 3:
                                        NpcService.gI().createMenuConMeo(player,
                                                ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND, this.avartar,
                                                "Con có chắc muốn xóa hết vật phẩm trong rương phụ? Sau khi xóa "
                                                + "sẽ không thể khôi phục!",
                                                "Đồng ý", "Hủy bỏ");
                                        break;
                                }
                            }
                            break;
                        case 141:
                            switch (select) {
                                case 0:
                                    if (player.clan == null || player.clan.ConDuongRanDoc == null || !player.clan.ConDuongRanDoc.allMobsDead) {
                                        Service.gI().sendThongBao(player, "Chưa hạ hết đối thủ");
                                        return;
                                    }
                                    ChangeMapService.gI().changeMapYardrat(player, ChangeMapService.gI().getMapCanJoin(player, 45), 295, 408);
                                    Service.gI().sendThongBao(player, "Hãy xuống gặp thần mèo Karin");
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc thanVuTru(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        switch (player.levelLuyenTap) {
                            case 4:
                                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                        "Thượng đế đưa ngươi đến đây, chắc muốn ta dạy võ chứ gì\nBắt được con khỉ Bubbles rồi hãy tính",
                                        player.dangKyTapTuDong ? "Hủy đăng\nký tập\ntự động" : "Đăng ký\ntập\ntự động", "Tập luyện\nvới\nBubbles", "Thách đấu\nBubbles", "Di chuyển");
                                break;
                            case 5:
                                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                        "Ta là Thần Vũ Trụ Phương Bắc cai quản khu vực bắc vũ trụ\nnếu thắng được ta, ngươi sẽ được đến\nLãnh Địa Kaio, nơi ở của Thần Linh",
                                        player.dangKyTapTuDong ? "Hủy đăng\nký tập\ntự động" : "Đăng ký\ntập\ntự động", "Tập luyện\nvới\nThần Vũ Trụ", "Thách đấu\nThần Vũ Trụ", "Di chuyển");
                                break;
                            default:
                                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                        "Con mạnh nhất phía bắc vũ trụ này rồi đấy\nnhưng ngoài vũ trụ bao la kia vẫn có những kẻ mạnh hơn nhìu\ncon cần phải tập luyện để mạnh hơn nữa",
                                        player.dangKyTapTuDong ? "Hủy đăng\nký tập\ntự động" : "Đăng ký\ntập\ntự động", "Tập luyện\nvới\nBubbles", "Tập luyện\nvới\nThần Vũ Trụ", "Di chuyển");
                                break;
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    if (player.dangKyTapTuDong) {
                                        player.dangKyTapTuDong = false;
                                        NpcService.gI().createTutorial(player, tempId, avartar, "Con đã hủy thành công đăng ký tập tự động\ntừ giờ con muốn tập Offline hãy tự đến đây trước");
                                        return;
                                    }
                                    this.createOtherMenu(player, 2001, "Đăng ký để mỗi khi Offline quá 30 phút, con sẽ được tự động luyện tập với tốc độ 1280 sức mạnh mỗi phút",
                                            "Hướng\ndẫn\nthêm", "Đồng ý\n1 ngọc\nmỗi lần", "Không\nđồng ý");
                                    break;
                                case 1:
                                    switch (player.levelLuyenTap) {
                                        case 5:
                                            this.createOtherMenu(player, 2002, "Con có chắc muốn tập luyện ?\nTập luyện với ta sẽ tăng 640 sức mạnh mỗi phút",
                                                    "Đồng ý\nluyện tập", "Không\nđồng ý");
                                            break;
                                        default:
                                            this.createOtherMenu(player, 2002, "Con có chắc muốn tập luyện ?\nTập luyện với Khỉ Bubbles sẽ tăng 320 sức mạnh mỗi phút",
                                                    "Đồng ý\nluyện tập", "Không\nđồng ý");
                                            break;
                                    }
                                    break;
                                case 2:
                                    switch (player.levelLuyenTap) {
                                        case 4:
                                            this.createOtherMenu(player, 2003, "Con có chắc muốn thách đấu ?\nNếu thắng Khỉ Bubbles sẽ được tập với ta, tăng 640 sức mạnh mỗi phút",
                                                    "Đồng ý\ngiao đấu", "Không\nđồng ý");
                                            break;
                                        case 5:
                                            this.createOtherMenu(player, 2003, "Con có chắc muốn thách đấu ?\nNếu thắng được ta, con sẽ được học võ với người mạnh hơn ta để tăng đến 1280 sức mạnh mỗi phút",
                                                    "Đồng ý\ngiao đấu", "Không\nđồng ý");
                                            break;
                                        default:
                                            this.createOtherMenu(player, 2003, "Con có chắc muốn tập luyện ?\nTập luyện với ta sẽ tăng 640 sức mạnh mỗi phút",
                                                    "Đồng ý\nluyện tập", "Không\nđồng ý");
                                            break;
                                    }
                                    break;
                                case 3:
                                    this.createOtherMenu(player, ConstNpc.MENU_DI_CHUYEN,
                                            "Ta sẽ đưa con đi",
                                            "Về\nthần điện",
                                            "Thánh địa\nKaio",
                                            "Con\nđường\nrắn độc",
                                            "Từ chối");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 2001) {
                            switch (select) {
                                case 0:
                                    NpcService.gI().createTutorial(player, tempId, avartar, ConstNpc.TAP_TU_DONG);
                                    break;
                                case 1:
                                    player.mapIdDangTapTuDong = mapId;
                                    player.dangKyTapTuDong = true;
                                    NpcService.gI().createTutorial(player, tempId, avartar, "Từ giờ, quá 30 phút Offline con sẽ được tự động luyện tập");
                                    break;
                            }

                        } else if (player.iDMark.getIndexMenu() == 2002) {
                            switch (player.levelLuyenTap) {
                                case 5:
                                    TrainingService.gI().callBoss(player, BossID.THAN_VU_TRU, false);
                                    break;
                                default:
                                    TrainingService.gI().callBoss(player, BossID.KHI_BUBBLES, false);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 2003) {
                            switch (player.levelLuyenTap) {
                                case 4:
                                    TrainingService.gI().callBoss(player, BossID.KHI_BUBBLES, true);
                                    break;
                                case 5:
                                    TrainingService.gI().callBoss(player, BossID.THAN_VU_TRU, true);
                                    break;
                                default:
                                    TrainingService.gI().callBoss(player, BossID.THAN_VU_TRU, false);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DI_CHUYEN) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 45, -1, 354);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    break;
                                case 2:
                                    if (player.clan != null) {
                                        if (player.clan.ConDuongRanDoc != null) {
                                            this.createOtherMenu(player, 2,
                                                    "Bang hội con đang ở con đường rắn độc cấp độ "
                                                    + player.clan.ConDuongRanDoc.level + "\ncon có muốn đi cùng họ không? ("
                                                    + TimeUtil.convertTimeNow(player.clan.ConDuongRanDoc.getLastTimeOpen())
                                                    + " trước)",
                                                    "Top\nBang hội", "Thành tích\nBang", "Đồng ý", "Từ chối");
                                        } else {
                                            this.createOtherMenu(player, 2,
                                                    "Hãy mau trở về bằng con đường rắn độc\nbọn Xayda đã đến Trái Đất",
                                                    "Top\nBang hội", "Thành tích\nBang", "Chọn\ncấp độ", "Từ chối");
                                        }
                                    } else {
                                        NpcService.gI().createTutorial(player, tempId, this.avartar,
                                                "Hãy vào bang hội trước");
                                    }
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 2) {
                            switch (select) {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    if (player.clan == null) {
                                        return;
                                    }
                                    if (player.clanMember.getNumDateFromJoinTimeToToday() < 2) {
                                        NpcService.gI().createTutorial(player, tempId, this.avartar,
                                                "Gia nhập bang hội trên 1 ngày mới được tham gia");
                                        return;
                                    }
                                    if (player.clan.ConDuongRanDoc == null) {
                                        Input.gI().createFormChooseLevelCDRD(player);
                                    } else {
                                        ConDuongRanDocService.gI().openConDuongRanDoc(player, (byte) 0);
                                    }
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 3) {
                            if (select == 0) {
                                if (player.clan.ConDuongRanDoc != null) {
                                    ConDuongRanDocService.gI().openConDuongRanDoc(player, (byte) 0);
                                } else {
                                    ConDuongRanDocService.gI().openConDuongRanDoc(player, Byte.parseByte(String.valueOf(PLAYERID_OBJECT.get(player.id))));
                                }
                            }
                        }
                    }
                }
            }

        };
    }

    public static Npc kibit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến\nKaio", "Từ chối");
                    }
                    if (this.mapId == 114) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 48, -1, 354, 240);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc osin(int mapId, int status, int cx, int cy, int tempId, int avartar) {

        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến\nKaio", "Đến\nhành tinh\nBill", "Từ chối");
                    } else if (this.mapId == 154) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Về thánh địa", "Đến\nhành tinh\nngục tù", "Từ chối");
                    } else if (this.mapId == 155) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else if (this.mapId == 52) {
                        try {
                            MapMaBu.gI().setTimeJoinMapMaBu();
                            if (this.mapId == 52) {
                                long now = System.currentTimeMillis();
                                if (now > MapMaBu.TIME_OPEN_MABU && now < MapMaBu.TIME_CLOSE_MABU) {
                                    this.createOtherMenu(player, ConstNpc.MENU_OPEN_MMB, "Đại chiến Ma Bư đã mở, "
                                            + "ngươi có muốn tham gia không?",
                                            "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_MMB,
                                            "Ta có thể giúp gì cho ngươi?", "Hướng dẫn", "Từ chối");
                                }
                            }
                        } catch (Exception ex) {
                            Logger.error("Lỗi mở menu osin");
                        }
                    } else if (this.mapId == 114 && this.mapId != 116) {
                        menuZoneMapMabu(player, player.isTang1);
                    } else if (this.mapId == 115 && this.mapId != 116) {
                        menuZoneMapMabu(player, player.isTang2);
                    } else if (this.mapId == 117 && this.mapId != 116) {
                        menuZoneMapMabu(player, player.isTang3);
                    } else if (this.mapId == 118 && this.mapId != 116) {
                        menuZoneMapMabu(player, player.isTang4);
                    } else if (this.mapId == 119 && this.mapId != 116) {
                        menuZoneMapMabu(player, player.isTang5);
                    } else if (this.mapId == 120) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 48, -1, 354, 240);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                                    break;
                            }
                        }
                    } else if (this.mapId == 154) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    if (player.nPoint.power >= 80000000000L) {
                                        ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    } else {
                                        Service.gI().sendThongBaoOK(player, "Yêu Cầu 80 Tỷ Sức Mạnh");
                                    }
                                    break;
                                case 1:
                                    if (player.nPoint.power >= 80000000000L) {
                                        ChangeMapService.gI().changeMap(player, 155, -1, 111, 792);
                                    } else {
                                        Service.gI().sendThongBaoOK(player, "Yêu Cầu 80 Tỷ Sức Mạnh");
                                    }
                                    break;
                            }
                        }
                    } else if (this.mapId == 155) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                if (player.nPoint.power >= 80000000000L) {
                                    ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                                } else {
                                    Service.gI().sendThongBaoOK(player, "Yêu Cầu 80 Tỷ Sức Mạnh");
                                }

                            }
                        }
                    } else if (this.mapId == 52) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.MENU_REWARD_MMB:
                                break;
                            case ConstNpc.MENU_OPEN_MMB:
                                if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                } else if (select == 1) {
//                                    if (!player.getSession().actived) {
//                                        Service.gI().sendThongBao(player, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
//                                    } else
                                    ChangeMapService.gI().changeMap(player, 114, -1, 318, 336);
                                }
                                break;
                            case ConstNpc.MENU_NOT_OPEN_BDW:
                                if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                }
                                break;
                        }
                    } else if (this.mapId >= 114 && this.mapId < 120 && this.mapId != 116) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.GO_UPSTAIRS_MENU) {
                            if (select == 0) {
                                if (this.mapId == 114 && !player.isTang1) {
                                    player.fightMabu.clear();
                                    player.isTang1 = true;
                                } else if (this.mapId == 115 && !player.isTang2) {
                                    player.fightMabu.clear();
                                    player.isTang2 = true;
                                } else if (this.mapId == 117 && !player.isTang3) {
                                    player.fightMabu.clear();
                                    player.isTang3 = true;
                                } else if (this.mapId == 118 && !player.isTang4) {
                                    player.fightMabu.clear();
                                    player.isTang4 = true;
                                } else if (this.mapId == 119 && !player.isTang5) {
                                    player.fightMabu.clear();
                                    player.isTang5 = true;
                                }
                                ChangeMapService.gI().changeMap(player, this.map.mapIdNextMabu((short) this.mapId), -1, this.cx, this.cy);
                            } else if (select == 1) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        } else {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    } else if (this.mapId == 120) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    }
                }
            }

            private void menuZoneMapMabu(Player player, boolean isTang) {
                String message;
                if (!isTang && player.fightMabu.pointMabu < 100) {
                    message = "|6|Hiện tại ngươi đang có " + player.fightMabu.pointMabu + " điểm "
                            + (player.fightMabu.pointMabu < 100 ? "\n|3|Chưa thể xuống tầng" : "\n|4|Có thể xuống tầng")
                            + "\n|6|Ta có thể giúp gì cho ngươi ?";
                } else {
                    message = "|6|Hiện tại ngươi đã đủ điều kiện xuống tầng\n"
                            + "Ta có thể giúp gì cho ngươi ?";
                }
                if (player.fightMabu.pointMabu >= player.fightMabu.POINT_MAX || isTang) {
                    this.createOtherMenu(player, ConstNpc.GO_UPSTAIRS_MENU,
                            message, "Xuống Tầng!", "Quay về", "Từ chối");
                } else {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            message, "Quay về", "Từ chối");
                }
            }
        };
    }

    public static Npc DocNhan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (mapId == 57) {
                        if (!player.clan.doanhTrai.winDT) {
                            NpcService.gI().createTutorial(player, tempId, this.avartar, "Bọn mi đừng hòng thoát khỏi nơi đây");
                        } else {
                            NpcService.gI().createTutorial(player, tempId, this.avartar, "Ta chịu thua, nhưng các ngươi đừng có mong lấy được ngọc của ra\nta đã giấu ngọc 4 sao và 1 đống ngọc 7 sao trong doanh trại này...\nCác ngươi chỉ có 5 phút đi tìm, đố các ngươi tìm ra hahaha");
                            if (!player.clan.doanhTrai.isTimePicking) {
                                Service.gI().sendThongBao(player, "Trại Độc Nhãn đã bị tiêu diệt, bạn có 5 phút để tìm kiếm viên ngọc 4 sao trước khi phi thuyền đến đón");
                                player.clan.doanhTrai.isTimePicking = true;
                                player.clan.doanhTrai.lastTimePick = System.currentTimeMillis();
                                player.clan.doanhTrai.randomNR();
                                player.clan.doanhTrai.sendTextTimePickDoanhTrai();
                            }
                        }

                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
            }
        };
    }

    public static Npc linhCanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.clan == null) {
                        NpcService.gI().createTutorial(player, tempId, this.avartar,
                                "Chỉ tiếp các bang hội, miễn tiếp khách vãng lai");
                        return;
                    }
                    if (player.clan.getMembers().size() < DoanhTrai.N_PLAYER_CLAN) {
                        NpcService.gI().createTutorial(player, tempId, this.avartar,
                                "Bang hội phải có ít nhất " + DoanhTrai.N_PLAYER_CLAN + " thành viên mới có thể tham gia");
                        return;
                    }
                    int day = 0;
                    if (player.clanMember.getNumDateFromJoinTimeToToday() < day) {
                        NpcService.gI().createTutorial(player, tempId, this.avartar,
                                "Gia nhập bang hội trên " + day + " ngày mới được tham gia");
                        return;
                    }
                    if (player.clan.doanhTrai != null) {
                        createOtherMenu(player, ConstNpc.MENU_JOIN_DOANH_TRAI,
                                "Bang hội của ngươi đang đánh trại độc nhãn\nThời gian còn lại là "
                                + TimeUtil.getTimeLeft(player.clan.doanhTrai.getLastTimeOpen(), DoanhTrai.TIME_DOANH_TRAI / 1000)
                                + ". Ngươi có muốn tham gia không?",
                                "Tham gia", "Không", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    int nPlSameClan = 0;
                    for (Player pl : player.zone.getPlayers()) {
                        if (!pl.equals(player) && pl.clan != null
                                && pl.clan.equals(player.clan) && pl.location.x >= 1285
                                && pl.location.x <= 1645) {
                            nPlSameClan++;
                        }
                    }
                    if (nPlSameClan < DoanhTrai.N_PLAYER_MAP) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ngươi phải có ít nhất " + DoanhTrai.N_PLAYER_MAP + " đồng đội cùng bang đứng gần mới có thể vào\n"
                                + "tuy nhiên ta khuyên ngươi nên đi cùng với 3-4 người để khỏi chết. "
                                + "Hahaha.", "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    if (player.nPoint.dameg < 300) {
                        NpcService.gI().createTutorial(player, tempId, this.avartar,
                                "Yêu cầu sức đánh gốc từ 300 trở lên");
                        return;
                    }
                    if (player.clan.haveGoneDoanhTrai && !Util.isAfterMidnight(player.clan.lastTimeOpenDoanhTrai) && false) {
                        if (!Util.isAfterMidnight(player.lastTimeJoinDT)) {
                            NpcService.gI().createTutorial(player, tempId, this.avartar,
                                    "Hôm nay bạn đã tham gia doanh trại rồi, hẹn gặp bạn vào ngày mai");
                            return;
                        }
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Bang hội của ngươi ngày hôm nay đã vào 1 lần rồi (thành viên " + player.clan.playerOpenDoanhTrai.name + ") lúc " + TimeUtil.formatTime(player.clan.lastTimeOpenDoanhTrai, "HH:mm") + "\n"
                                + "Nên ngươi không thể vào được nữa.\n"
                                + "Hãy chờ đến ngày mai để có thể vào miễn phí", "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    createOtherMenu(player, ConstNpc.MENU_JOIN_DOANH_TRAI,
                            "Hôm nay bang hội của ngươi chưa vào trại lần nào. Ngươi có muốn vào\nkhông?\nĐể vào, ta khuyên ngươi nên có 3-4 người cùng bang đi cùng.",
                            "Vào\n(miễn phí)", "Không", "Hướng\ndẫn\nthêm");
                }
            }

            @Override

            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_JOIN_DOANH_TRAI:
                            if (select == 0) {
                                DoanhTraiService.gI().joinDoanhTrai(player);
                            } else if (select == 2) {
                                NpcService.gI().createTutorial(player, tempId, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                        case ConstNpc.IGNORE_MENU:
                            if (select == 1) {
                                NpcService.gI().createTutorial(player, tempId, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc quaTrung(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_AP_TRUNG_NHANH = 1000000000;

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == (21 + player.gender)) {
                        player.mabuEgg.sendMabuEgg();
                        if (player.mabuEgg.getSecondDone() != 0) {
                            this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_EGG, "Bư bư bư...",
                                    "Hủy bỏ\ntrứng", "Ấp nhanh\n" + Util.formatNumber(COST_AP_TRUNG_NHANH) + " vàng", "Đóng");
                        } else {
                            this.createOtherMenu(player, ConstNpc.CAN_OPEN_EGG, "Bư bư bư...", "Nở", "Hủy bỏ\ntrứng", "Đóng");
                        }
                    }
                    if (this.mapId == 7) {
                        player.billEgg.sendBillEgg();
                        if (player.billEgg.getSecondDone() != 0) {
                            this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_EGG, "Bư bư bư...",
                                    "Hủy bỏ\ntrứng", "Ấp nhanh\n" + Util.formatNumber(COST_AP_TRUNG_NHANH) + " vàng", "Đóng");
                        } else {
                            this.createOtherMenu(player, ConstNpc.CAN_OPEN_EGG, "Bư bư bư...", "Nở", "Hủy bỏ\ntrứng", "Đóng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == (21 + player.gender)) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.CAN_NOT_OPEN_EGG:
                                if (select == 0) {
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                            "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                } else if (select == 1) {
                                    if (player.inventory.gold >= COST_AP_TRUNG_NHANH) {
                                        player.inventory.gold -= COST_AP_TRUNG_NHANH;
                                        player.mabuEgg.timeDone = 0;
                                        Service.gI().sendMoney(player);
                                        player.mabuEgg.sendMabuEgg();
                                    } else {
                                        Service.gI().sendThongBao(player,
                                                "Bạn không đủ vàng để thực hiện, còn thiếu "
                                                + Util.formatNumber((COST_AP_TRUNG_NHANH - player.inventory.gold)) + " vàng");
                                    }
                                }
                                break;
                            case ConstNpc.CAN_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_OPEN_EGG,
                                                "Bạn có chắc chắn cho trứng nở?\n"
                                                + "Đệ tử của bạn sẽ được thay thế bằng đệ Mabư",
                                                "Đệ mabư\nTrái Đất", "Đệ mabư\nNamếc", "Đệ mabư\nXayda", "Từ chối");
                                        break;
                                    case 1:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                                "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        player.mabuEgg.openEgg(ConstPlayer.TRAI_DAT);
                                        break;
                                    case 1:
                                        player.mabuEgg.openEgg(ConstPlayer.NAMEC);
                                        break;
                                    case 2:
                                        player.mabuEgg.openEgg(ConstPlayer.XAYDA);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_DESTROY_EGG:
                                if (select == 0) {
                                    player.mabuEgg.destroyEgg();
                                }
                                break;
                        }
                    }
                    /*  if (this.mapId == 7) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.CAN_NOT_OPEN_BILL:
                                if (select == 0) {
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_BILL,
                                            "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                } else if (select == 1) {
                                    if (player.inventory.gold >= COST_AP_TRUNG_NHANH) {
                                        player.inventory.gold -= COST_AP_TRUNG_NHANH;
                                        player.billEgg.timeDone = 0;
                                        Service.gI().sendMoney(player);
                                        player.billEgg.sendBillEgg();
                                    } else {
                                        Service.gI().sendThongBao(player,
                                                "Bạn không đủ vàng để thực hiện, còn thiếu "
                                                + Util.formatNumber((COST_AP_TRUNG_NHANH - player.inventory.gold)) + " vàng");
                                    }
                                }
                                break;
                            case ConstNpc.CAN_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_OPEN_BILL,
                                                "Bạn có chắc chắn cho trứng nở?\n"
                                                + "Đệ tử của bạn sẽ được thay thế bằng đệ Mabư",
                                                "Đệ mabư\nTrái Đất", "Đệ mabư\nNamếc", "Đệ mabư\nXayda", "Từ chối");
                                        break;
                                    case 1:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_BILL,
                                                "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_OPEN_BILL:
                                switch (select) {
                                    case 0:
                                        player.billEgg.openEgg(ConstPlayer.TRAI_DAT);
                                        break;
                                    case 1:
                                        player.billEgg.openEgg(ConstPlayer.NAMEC);
                                        break;
                                    case 2:
                                        player.billEgg.openEgg(ConstPlayer.XAYDA);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_DESTROY_BILL:
                                if (select == 0) {
                                    player.billEgg.destroyEgg();
                                }
                                break;
                        }
                    }
                     */
                }
            }
        };
    }

    public static Npc duahau(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_AP_TRUNG_NHANH = 1000000000;

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {

                    if (this.mapId == 7 * player.gender) {
                        player.billEgg.sendBillEgg();
                        if (player.billEgg.getSecondDone() != 0) {
                            this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_EGG, "Mang Đến Gặp Vua Hùng Để Được Những Món Quà Vô Giá...",
                                    "Thu Hoạch\nSớm" + Util.formatNumber(COST_AP_TRUNG_NHANH) + " vàng", "Đóng");
                        } else {
                            this.createOtherMenu(player, ConstNpc.CAN_OPEN_EGG, "Mau thu hoạch nào...", "Thu Hoạch", "Đóng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                    if (this.mapId == 7 * player.gender) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.CAN_NOT_OPEN_BILL:
                                if (select == 0) {
                                    if (player.inventory.gold >= COST_AP_TRUNG_NHANH) {
                                        player.inventory.gold -= COST_AP_TRUNG_NHANH;
                                        player.billEgg.timeDone = 0;
                                        Service.gI().sendMoney(player);
                                        player.billEgg.sendBillEgg();
                                    } else {
                                        Service.gI().sendThongBao(player,
                                                "Bạn không đủ vàng để thực hiện, còn thiếu "
                                                + Util.formatNumber((COST_AP_TRUNG_NHANH - player.inventory.gold)) + " vàng");
                                    }
                                }
                                break;
                            case ConstNpc.CAN_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_OPEN_BILL,
                                                "ôi bạn ơi?\n"
                                                + "Chọn Một Trong Những Món Quà Giá Trị Nào",
                                                "Ngọc Rồng\nTorobo", "Dưa\nHấu", "Ngọc\nBội", "Từ chối");
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_OPEN_BILL:
                                switch (select) {
                                    case 0:
                                        ItemService.gI().openBoxtorobo(player);
                                        player.billEgg.destroyEgg();
//                                        player.billEgg.openEgg(ConstPlayer.TRAI_DAT);
                                        break;
                                    case 1:
                                        ItemService.gI().openBoxdua(player);
                                        player.billEgg.destroyEgg();
//                                if (player.inventory.ruby == 10000) {
//                                    this.npcChat(player, "Bú ít thôi con");
//                                    break;
//                                }
//                                player.inventory.ruby = 100;
//                                Service.gI().sendMoney(player);
//                                Service.gI().sendThongBao(player, "Bạn vừa nhận được 200K Hồng Ngọc");
                                        break;
                                    case 2://2072
                                        ItemService.gI().openBoxngocboi(player);
                                        player.billEgg.destroyEgg();
//                                        player.billEgg.openEgg(ConstPlayer.XAYDA);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_DESTROY_BILL:
                                if (select == 0) {
                                    player.billEgg.destroyEgg();
                                }
                                break;
                        }
                    }

                }
            }
        };
    }

    public static Npc quocVuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                        "Con muốn nâng giới hạn sức mạnh cho bản thân hay đệ tử?",
                        "Bản thân", "Đệ tử", "Từ chối");
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                    this.createOtherMenu(player, ConstNpc.OPEN_POWER_MYSEFT,
                                            "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của bản thân lên "
                                            + Util.formatNumber(player.nPoint.getPowerNextLimit()),
                                            "Nâng\ngiới hạn\nsức mạnh",
                                            "Nâng ngay\n" + Util.formatNumber(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                            "Sức mạnh của con đã đạt tới giới hạn",
                                            "Đóng");
                                }
                                break;
                            case 1:
                                if (player.pet != null) {
                                    if (player.pet.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                        this.createOtherMenu(player, ConstNpc.OPEN_POWER_PET,
                                                "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của đệ tử lên "
                                                + Util.formatNumber(player.pet.nPoint.getPowerNextLimit()),
                                                "Nâng ngay\n" + Util.formatNumber(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Sức mạnh của đệ con đã đạt tới giới hạn",
                                                "Đóng");
                                    }
                                } else {
                                    Service.gI().sendThongBao(player, "Không thể thực hiện");
                                }
                                //giới hạn đệ tử
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_MYSEFT) {
                        switch (select) {
                            case 0:
                                OpenPowerService.gI().openPowerBasic(player);
                                break;
                            case 1:
                                if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                    if (OpenPowerService.gI().openPowerSpeed(player)) {
                                        player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                        Service.gI().sendMoney(player);
                                    }
                                } else {
                                    Service.gI().sendThongBao(player,
                                            "Bạn không đủ vàng để mở, còn thiếu "
                                            + Util.formatNumber((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_PET) {
                        if (select == 0) {
                            if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                if (OpenPowerService.gI().openPowerSpeed(player.pet)) {
                                    player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                    Service.gI().sendMoney(player);
                                }
                            } else {
                                Service.gI().sendThongBao(player,
                                        "Bạn không đủ vàng để mở, còn thiếu "
                                        + Util.formatNumber((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc bulmaTL(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 102) {
                        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu bé muốn mua gì nào?", "Cửa hàng", "Đóng");
                        }
                    } else if (this.mapId == 104) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Kính chào Ngài Linh thú sư!", "Cửa hàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 102) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "BUNMA_FUTURE", true);
                            }
                        }
                    } else if (this.mapId == 104) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "BUNMA_LINHTHU", true);
                            }
//                            if (select == 1) {
//                                    Item honLinhThu = null;
//                                    try {
//                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 2029);
//                                    } catch (Exception e) {
////                                        throw new RuntimeException(e);
//                                    }
//                                    if (honLinhThu == null || honLinhThu.quantity < 99) {
//                                        this.npcChat(player, "Bạn không đủ 99 Hồn Linh thú");
//                                    } else if (player.inventory.gold < 1_000_000_000) {
//                                        this.npcChat(player, "Bạn không đủ 1 Tỷ vàng");
//                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
//                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
//                                    } else {
//                                        player.inventory.gold -= 1_000_000_000;
//                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
//                                        Service.gI().sendMoney(player);
//                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 2028);
//                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
//                                        InventoryServiceNew.gI().sendItemBags(player);
//                                        this.npcChat(player, "Bạn nhận được 1 Trứng Linh thú");
//                                    }
//                                }
                        }
                    }
                }
            }
        };
    }

    public static Npc rongOmega(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    BlackBallWar.gI().setTime();
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        try {
                            long now = System.currentTimeMillis();
                            if (now > BlackBallWar.TIME_OPEN && now < BlackBallWar.TIME_CLOSE) {
                                this.createOtherMenu(player, ConstNpc.MENU_OPEN_BDW, "Đường đến với ngọc rồng sao đen đã mở, "
                                        + "ngươi có muốn tham gia không?",
                                        "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                            } else {
                                String[] optionRewards = new String[7];
                                int index = 0;
                                for (int i = 0; i < 7; i++) {
                                    if (player.rewardBlackBall.timeOutOfDateReward[i] > System.currentTimeMillis()) {
                                        String quantily = player.rewardBlackBall.quantilyBlackBall[i] > 1 ? "x" + player.rewardBlackBall.quantilyBlackBall[i] + " " : "";
                                        optionRewards[index] = quantily + (i + 1) + " sao";
                                        index++;
                                    }
                                }
                                if (index != 0) {
                                    String[] options = new String[index + 1];
                                    for (int i = 0; i < index; i++) {
                                        options[i] = optionRewards[i];
                                    }
                                    options[options.length - 1] = "Từ chối";
                                    this.createOtherMenu(player, ConstNpc.MENU_REWARD_BDW, "Ngươi có một vài phần thưởng ngọc "
                                            + "rồng sao đen đây!",
                                            options);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_BDW,
                                            "Ta có thể giúp gì cho ngươi?", "Hướng dẫn", "Từ chối");
                                }
                            }
                        } catch (Exception ex) {
                            Logger.error("Lỗi mở menu rồng Omega");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_REWARD_BDW:
                            player.rewardBlackBall.getRewardSelect((byte) select);
                            break;
                        case ConstNpc.MENU_OPEN_BDW:
                            if (select == 0) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_BLACK_BALL_WAR);
                            } else if (select == 1) {
//                                if (!player.getSession().actived) {
//                                    Service.gI().sendThongBao(player, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
//
//                                } else
                                player.iDMark.setTypeChangeMap(ConstMap.CHANGE_BLACK_BALL);
                                ChangeMapService.gI().openChangeMapTab(player);
                            }
                            break;
                        case ConstNpc.MENU_NOT_OPEN_BDW:
                            if (select == 0) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_BLACK_BALL_WAR);
                            }
                            break;
                    }
                }
            }

        };
    }

    public static Npc rong1_to_7s(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isHoldBlackBall()) {
                        this.createOtherMenu(player, ConstNpc.MENU_PHU_HP, "Ta có thể giúp gì cho ngươi?", "Phù hộ", "Từ chối");
                    } else {
                        if (BossManager.gI().existBossOnPlayer(player)
                                || player.zone.items.stream().anyMatch(itemMap -> ItemMapService.gI().isBlackBall(itemMap.itemTemplate.id))
                                || player.zone.getPlayers().stream().anyMatch(p -> p.iDMark.isHoldBlackBall())) {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_GO_HOME, "Ta có thể giúp gì cho ngươi?", "Về nhà", "Từ chối");
                        } else {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_GO_HOME, "Ta có thể giúp gì cho ngươi?", "Về nhà", "Từ chối", "Gọi BOSS");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHU_HP) {
                        if (select == 0) {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_PHU_HP,
                                    "Ta sẽ giúp ngươi tăng HP lên mức kinh hoàng, ngươi chọn đi",
                                    "x3 HP\n" + Util.formatNumber(BlackBallWar.COST_X3) + " vàng",
                                    "x5 HP\n" + Util.formatNumber(BlackBallWar.COST_X5) + " vàng",
                                    "x7 HP\n" + Util.formatNumber(BlackBallWar.COST_X7) + " vàng",
                                    "Từ chối"
                            );
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_GO_HOME) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
                        } else if (select == 2) {
                            BossManager.gI().callBoss(player, mapId);
                        } else if (select == 1) {
                            this.npcChat(player, "Để ta xem ngươi trụ được bao lâu");
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_PHU_HP) {
                        if (player.effectSkin.xHPKI > 1) {
                            Service.gI().sendThongBao(player, "Bạn đã được phù hộ rồi!");
                            return;
                        }
                        switch (select) {
                            case 0:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X3);
                                break;
                            case 1:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X5);
                                break;
                            case 2:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X7);
                                break;
                            case 3:
                                this.npcChat(player, "Để ta xem ngươi trụ được bao lâu");
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc bill(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 48 || this.mapId == 154) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Gặp Whis Để Đổi Thức Ăn Lấy Điểm\nSau Đó Gặp Ta Để Mua Trang Bị Hủy Diệt\n|3|Lưu ý: "
                            + "Ngươi Có " + player.inventory.coupon + " Điểm",
                            "Shop Hủy Diệt", "Đóng");
                }
            }

            private void rewardPlayer(Player player, String itemName, short itemId) {
                Service.gI().sendThongBao(player, "Bạn đã nhận được " + itemName + "!");
                Item item = InventoryServiceNew.gI().findItemBag(player, 1405);
                Item rewardBox = ItemService.gI().createNewItem(itemId);
                InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
                InventoryServiceNew.gI().addItemBag(player, rewardBox);
                player.inventory.coupon -= 1;
                InventoryServiceNew.gI().sendItemBags(player);
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (this.mapId) {
                        case 48:
                            if (player.iDMark.isBaseMenu()) {
                                if (select == 0) {
                                    if (player.inventory.coupon <= 0) {
                                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Ngươi Không Có Điểm Vui Lòng Đổi Điểm Bằng Thức Ăn", "Đóng");
                                    } else {
                                        this.createOtherMenu(player, 1,
                                                "Bạn muốn đồ Huỷ Diệt của hành tinh nào\n|5|Giá mỗi hộp là 1 Huy Hiệu Huỷ Diệt và 1 điểm thức ăn",
                                                "Trái Đất", "Namếc", "Xayda", "Từ chối");
                                    }
                                }
                            } else if (player.iDMark.getIndexMenu() == 1) {
                                Item item = null;
                                try {
                                    item = InventoryServiceNew.gI().findItemBag(player, 1405);
                                } catch (Exception ignored) {
                                }
                                if (item != null && item.quantity >= 1) {
                                    switch (select) {
                                        case 0:
                                            rewardPlayer(player, "Hộp đồ Huỷ Diệt Trái Đất!", (short) 2003);
                                            break;
                                        case 1:
                                            rewardPlayer(player, "Hộp đồ Huỷ Diệt Namec!", (short) 2005);
                                            break;
                                        case 2:
                                            rewardPlayer(player, "Hộp đồ Huỷ Diệt Xayda!", (short) 2004);
                                            break;
                                    }
                                } else {
                                    this.npcChat(player, "Ngươi đang không có Huy Hiệu Huỷ Diệt!");
                                }
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc khidaumoi(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 14) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Bạn muốn nâng cấp khỉ ư?", "Nâng cấp\nkhỉ", "Shop của Khỉ", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 14) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, 1,
                                            "|7|Cần Khỉ Lv1 hoặc 2,4,6 để nâng cấp lên lv8\b|2|Mỗi lần nâng cấp tiếp thì mỗi cấp cần thêm 10 đá ngũ sắc",
                                            "Khỉ\ncấp 2",
                                            "Khỉ\ncấp 4",
                                            "Khỉ\ncấp 6",
                                            "Khỉ\ncấp 8",
                                            "Từ chối");
                                    break;
                                case 1: //shop
                                    ShopServiceNew.gI().opendShop(player, "KHI", false);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 1) { // action đổi dồ húy diệt
                            switch (select) {
                                case 0: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item klv1 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1137);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1137 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 1137 + i) && soLuong >= 20) {
                                            CombineServiceNew.gI().khilv2(player, 1138 + i);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 20);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                            this.npcChat(player, "Upgrede Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần cái trang khỉ cấp 1 với 20 đá ngũ sắc");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 1: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item klv2 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1138);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1138 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 1138 + i) && soLuong >= 30) {
                                            CombineServiceNew.gI().khilv3(player, 1139 + i);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 30);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                            this.npcChat(player, "Upgrede Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần cái trang khỉ cấp 2 với 30 đá ngũ sắc");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 2: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item klv2 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1139);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1139 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 1139 + i) && soLuong >= 40) {
                                            CombineServiceNew.gI().khilv4(player, 1140 + i);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 40);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                            this.npcChat(player, "Upgrede Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần cái trang khỉ cấp 3 với 40 đá ngũ sắc");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;
                                case 3: // trade
                                try {
                                    Item dns = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 674);
                                    Item klv2 = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1140);
                                    int soLuong = 0;
                                    if (dns != null) {
                                        soLuong = dns.quantity;
                                    }
                                    for (int i = 0; i < 12; i++) {
                                        Item klv = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1140 + i);

                                        if (InventoryServiceNew.gI().isExistItemBag(player, 1140 + i) && soLuong >= 50) {
                                            CombineServiceNew.gI().khilv5(player, 1136 + i);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, dns, 50);
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, klv, 1);
                                            this.npcChat(player, "Upgrede Thành Công!");

                                            break;
                                        } else {
                                            this.npcChat(player, "Yêu cầu cần cái trang khỉ cấp 3 với 50 đá ngũ sắc");
                                        }

                                    }
                                } catch (Exception e) {

                                }
                                break;

                                case 5: // canel
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc whis154(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.map.mapId == 154) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Thử đánh với ta xem nào.\nNgươi còn 1 lượt nữa cơ mà.",
                            "Nói chuyện",
                            "Học\ntuyệt kỹ",
                            "Top 100",
                            "[LV:" + (player.traning.getTop() + 1) + "]"
                    );
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        if (this.map.mapId == 154) {
                            Item BiKiepTuyetKy = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1459);
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, 5,
                                            "Ta sẽ giúp ngươi chế tạo trang bị thiên sứ",
                                            "Chế tạo",
                                            "Shop Thiên Sứ",
                                            "Từ chối");
                                    break;
                                case 1:
                                    int idskill = Skill.MA_PHONG_BA;
                                    if (player.gender == 0) {
                                        idskill = Skill.SUPER_KAME;
                                    } else if (player.gender == 2) {
                                        idskill = Skill.LIEN_HOAN_CHUONG;
                                    }
                                    Skill curSkill = SkillUtil.getSkillbyId(player, idskill);
                                    boolean checkskill = false;
                                    if (curSkill == null) {
                                        checkskill = true;
                                    } else if (curSkill.point == 0) {
                                        checkskill = true;
                                    }
                                    boolean duSachTuyetKy = false;
                                    boolean duVang = false;
                                    boolean duNgoc = false;
                                    String nameSkill = player.gender == 0 ? "Super kamejoko" : player.gender == 1 ? "Ma phong ba" : "Ca đíc liên hoàn chưởng";
                                    if (BiKiepTuyetKy.quantity >= 9999) {
                                        duSachTuyetKy = true;
                                    }
                                    if (player.inventory.gold >= 10_000_000) {
                                        duVang = true;
                                    }
                                    if (player.inventory.gem >= 99) {
                                        duNgoc = true;
                                    }
                                    if (checkskill) {
                                        if (duSachTuyetKy && duVang && duNgoc) {
                                            this.createOtherMenu(player, 6,
                                                    "|1|Ta sẽ dạy ngươi tuyệt kỹ " + nameSkill + " 1"
                                                    + "\n|2|Bí kiếp tuyệt kỹ " + BiKiepTuyetKy.quantity + "/999\n"
                                                    + "|2|Giá vàng: 10.000.000\n"
                                                    + "|2|Giá ngọc: 99",
                                                    "Đồng ý", "Từ chối");
                                        } else {
                                            this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                    "|1|Ta sẽ dạy ngươi tuyệt kỹ " + nameSkill + " 1"
                                                    + "\n" + (duSachTuyetKy ? "|2|" : "|7|") + "Bí kiếp tuyệt kỹ " + BiKiepTuyetKy.quantity + "/999"
                                                    + "\n" + (duVang ? "|2|" : "|7|") + "Giá vàng: 10.000.000"
                                                    + "\n" + (duNgoc ? "|2|" : "|7|") + "Giá ngọc: 99", "Từ chối");
                                        }
                                    } else {
                                        if (duSachTuyetKy && duVang && duNgoc) {
                                            this.createOtherMenu(player, 6,
                                                    "|1|Ta sẽ dạy ngươi tuyệt kỹ " + nameSkill + " " + (curSkill.point + 1)
                                                    + "\n|2|Bí kiếp tuyệt kỹ " + BiKiepTuyetKy.quantity + "/999\n"
                                                    + "|2|Giá vàng: 10.000.000\n"
                                                    + "|2|Giá ngọc: 99",
                                                    "Đồng ý", "Từ chối");
                                        } else {
                                            this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                    "|1|Ta sẽ dạy ngươi tuyệt kỹ " + nameSkill + " " + (curSkill.point + 1)
                                                    + "\n" + (duSachTuyetKy ? "|2|" : "|7|") + "Bí kiếp tuyệt kỹ " + BiKiepTuyetKy.quantity + "/999"
                                                    + "\n" + (duVang ? "|2|" : "|7|") + "Giá vàng: 10.000.000"
                                                    + "\n" + (duNgoc ? "|2|" : "|7|") + "Giá ngọc: 99", "Từ chối");
                                        }
                                    }
                                    break;
                                case 2:
                                    Service.gI().showListTop(player, Manager.topWHIS);
                                    break;
                                case 3:
                                    TrainingService.gI().callBoss(player, BossID.WHIS, false);
                                    break;
                            }
                        }
                    } else if (player.iDMark.getIndexMenu() == 5) {
                        switch (select) {
                            case 0:
                                CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.CHE_TAO_TRANG_BI_TS);
                                break;
                            case 1:
                                ShopServiceNew.gI().opendShop(player, "WHIS", true);
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == 6) {
                        switch (select) {
                            case 0:
                                Item sach = InventoryServiceNew.gI().findItemBag(player, 1459);
                                if (sach != null && player.inventory.gold >= 10000000 && player.inventory.gem > 99 && player.nPoint.power >= 60000000000L) {
                                    int idskill = Skill.MA_PHONG_BA;
                                    int iconSkill = 17258;
                                    if (player.gender == 0) {
                                        idskill = Skill.SUPER_KAME;
                                        iconSkill = 17256;
                                    } else if (player.gender == 2) {
                                        idskill = Skill.LIEN_HOAN_CHUONG;
                                        iconSkill = 17257;
                                    }
                                    Skill curSkill = SkillUtil.getSkillbyId(player, idskill);
                                    boolean checkskill = false;
                                    if (curSkill == null) {
                                        checkskill = true;
                                    } else if (curSkill.point == 0) {
                                        checkskill = true;
                                    }
                                    if (checkskill) {
                                        if (sach.quantity >= 9999) {
                                            try {
                                                int trubk = 99;
                                                String msg = "Tư chất kém!";
                                                String msg2 = "Ngu dốt!";
                                                if (Util.isTrue(1, 15)) {
                                                    trubk = 9999;
                                                    msg = "Học skill thành công!";
                                                    msg2 = "Chúc mừng con nhé!";
                                                    switch (player.gender) {
                                                        case 0:
                                                            SkillService.gI().learSkillSpecial(player, Skill.SUPER_KAME);
                                                            break;
                                                        case 2:
                                                            SkillService.gI().learSkillSpecial(player, Skill.LIEN_HOAN_CHUONG);
                                                            break;
                                                        default:
                                                            SkillService.gI().learSkillSpecial(player, Skill.MA_PHONG_BA);
                                                            break;
                                                    }
                                                } else {
                                                    iconSkill = 15313;
                                                }
                                                Message msgg;
                                                msgg = new Message(-81);
                                                msgg.writer().writeByte(0);
                                                msgg.writer().writeUTF("Skill 9");
                                                msgg.writer().writeUTF("DragonBoy");
                                                msgg.writer().writeShort(tempId);
                                                player.sendMessage(msgg);
                                                msgg.cleanup();
                                                msgg = new Message(-81);
                                                msgg.writer().writeByte(1);
                                                msgg.writer().writeByte(1);
                                                msgg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, sach));
                                                player.sendMessage(msgg);
                                                msgg.cleanup();
                                                msgg = new Message(-81);
                                                msgg.writer().writeByte(trubk == 99 ? 8 : 7);
                                                msgg.writer().writeShort(iconSkill);
                                                player.sendMessage(msgg);
                                                msgg.cleanup();
                                                this.npcChat(player, msg2);
                                                Service.gI().sendThongBao(player, msg);
                                                InventoryServiceNew.gI().subQuantityItemsBag(player, sach, trubk);
                                                player.inventory.gold -= 10000000;
                                                player.inventory.gem -= 99;
                                                InventoryServiceNew.gI().sendItemBags(player);
                                                Service.gI().sendThongBao(player, msg);
                                            } catch (IOException e) {
                                            }
                                        } else {
                                            int sosach = 9999 - sach.quantity;
                                            Service.gI().sendThongBao(player, "Ngươi còn thiếu " + sosach + " bí kíp nữa.\nHãy tìm đủ rồi đến gặp ta.");
                                        }
                                    } else {
                                        if (sach.quantity >= 999) {
                                            if (curSkill.currLevel < 1000) {
                                                npcChat(player, "Ngươi chưa luyện skill đến mức thành thạo. Luyện thêm đi.");
                                            } else if (curSkill.point >= 9) {
                                                npcChat(player, "Skill của ngươi đã đến cấp độ tối đa");
                                            } else {
                                                try {
                                                    int trubk = 99;
                                                    String msg = "Tư chất kém!";
                                                    String msg2 = "Ngu dốt!";
                                                    if (Util.isTrue(1, 30)) {
                                                        trubk = 999;
                                                        msg = "Nâng skill thành công!";
                                                        msg2 = "Chúc mừng con nhé!";
                                                        curSkill.point++;
                                                        curSkill.currLevel = 0;
                                                        SkillService.gI().sendCurrLevelSpecial(player, curSkill);
                                                    } else {
                                                        iconSkill = 18480;
                                                    }
                                                    Message msgg;
                                                    msgg = new Message(-81);
                                                    msgg.writer().writeByte(0);
                                                    msgg.writer().writeUTF("Skill 9");
                                                    msgg.writer().writeUTF("DragonBoy");
                                                    msgg.writer().writeShort(tempId);
                                                    player.sendMessage(msgg);
                                                    msgg.cleanup();
                                                    msgg = new Message(-81);
                                                    msgg.writer().writeByte(1);
                                                    msgg.writer().writeByte(1);
                                                    msgg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, sach));
                                                    player.sendMessage(msgg);
                                                    msgg.cleanup();
                                                    msgg = new Message(-81);
                                                    msgg.writer().writeByte(trubk == 99 ? 8 : 7);
                                                    msgg.writer().writeShort(iconSkill);
                                                    player.sendMessage(msgg);
                                                    msgg.cleanup();
                                                    this.npcChat(player, msg2);
                                                    Service.gI().sendThongBao(player, msg);
                                                    InventoryServiceNew.gI().subQuantityItemsBag(player, sach, trubk);
                                                    player.inventory.gold -= 10000000;
                                                    player.inventory.gem -= 99;
                                                    InventoryServiceNew.gI().sendItemBags(player);
                                                    Service.gI().sendThongBao(player, msg);
                                                } catch (IOException e) {
                                                }
                                            }
                                        } else {
                                            int sosach = 999 - sach.quantity;
                                            Service.gI().sendThongBao(player, "Ngươi còn thiếu " + sosach + " bí kíp nữa.\nHãy tìm đủ rồi đến gặp ta.");
                                        }
                                    }
                                } else if (player.nPoint.power < 60000000000L) {
                                    Service.gI().sendThongBao(player, "Ngươi không đủ sức mạnh để học tuyệt kỹ");
                                } else if (player.inventory.gold <= 10000000) {
                                    Service.gI().sendThongBao(player, "Hãy có đủ vàng thì quay lại gặp ta.");
                                } else if (player.inventory.gem <= 99) {
                                    Service.gI().sendThongBao(player, "Hãy có đủ ngọc xanh thì quay lại gặp ta.");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                        switch (player.combineNew.typeCombine) {
                            case CombineServiceNew.CHE_TAO_TRANG_BI_TS:
                                if (select == 0) {
                                    CombineServiceNew.gI().startCombine(player);
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc whis(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                switch (this.mapId) {
                    case 0:
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh "
                                + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                        break;
                    case 7:
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh "
                                + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                        break;
                    case 14:
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh "
                                + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                        break;
                    case 48:
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Đã tìm đủ nguyên liệu cho tôi chưa?\n Tôi sẽ giúp cậu mạnh lên kha khá đấy!",
                                "Ép Huy Hiệu\nHuỷ Diệt", "Đổi Thức Ăn\nLấy Điểm", "Nâng cấp\nSet Kich Hoạt Vip", "Từ Chối");
                        break;
                    case 146:
                    case 147:
                    case 148:
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nCậu sẽ khó mà mạnh lên được", "Trốn về", "Ở lại");
                        break;
                    case 154:
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Đã tìm đủ nguyên liệu cho tôi chưa?\n Tôi sẽ giúp cậu mạnh lên kha khá đấy!",
                                "Chế Tạo trang bị thiên sứ", "Cửa Hàng\nBán Đồ", "Từ chối");
                        break;
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (this.mapId) {
                            case 48:
                                switch (select) {
                                    case 0:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.HUY_HIEU_HUY_DIET);
                                        break;
                                    case 1:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.DOI_DIEM);
                                        break;
                                    case 2:
                                        CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_SKH_VIP);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 7:
                                if (select == 0) {
                                    if (player.getSession().player.nPoint.power >= 80000000000L && player.inventory.gold > COST_HD) {
                                        player.inventory.gold -= COST_HD;
                                        Service.gI().sendMoney(player);
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 146, -1, 168);
                                    } else {
                                        this.npcChat(player, "Bạn chưa đủ điều kiện để vào!");
                                    }
                                }
                                break;
                            case 14:
                                if (select == 0) {
                                    if (player.getSession().player.nPoint.power >= 80000000000L && player.inventory.gold > COST_HD) {
                                        player.inventory.gold -= COST_HD;
                                        Service.gI().sendMoney(player);
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 148, -1, 168);
                                    } else {
                                        this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                                    }
                                }
                                break;
                            case 0:
                                if (select == 0) {
                                    if (player.getSession().player.nPoint.power >= 80000000000L && player.inventory.gold > COST_HD) {
                                        player.inventory.gold -= COST_HD;
                                        Service.gI().sendMoney(player);
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 147, -1, 168);
                                    } else {
                                        this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                                    }
                                }
                                break;
                            case 146:
                                if (select == 0) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 7, -1, 640);
                                }
                                break;
                            case 147:
                                if (select == 0) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 0, -1, 560);
                                }
                                break;
                            case 148:
                                if (select == 0) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 14, -1, 660);
                                }
                                break;
                            case 154:
                                if (select == 0) {
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.CHE_TAO_TRANG_BI_TS);
                                } else if (select == 1) {
                                    ShopServiceNew.gI().opendShop(player, "WHIS", true);
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                        switch (player.combineNew.typeCombine) {
                            case CombineServiceNew.HUY_HIEU_HUY_DIET:
                            case CombineServiceNew.DOI_DIEM:
                            case CombineServiceNew.NANG_CAP_SKH_VIP:
                            case CombineServiceNew.CHE_TAO_TRANG_BI_TS:
                                if (select == 0) {
                                    CombineServiceNew.gI().startCombine(player);
                                }
                                break;
                        }
                    }
                }
            }

        };
    }

    public static Npc pvp(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 174) {
                        this.createOtherMenu(player, 0,
                                "Con muốn gì nào?\nCon đang còn : " + player.pointPvp + " điểm PvP Point", "Làm Florentino", "Đổi Huy Hiệu");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 174) {
                        if (player.iDMark.getIndexMenu() == 0) { // 
                            switch (select) {
                                case 0:
                                    if (player.getSession().player.nPoint.power < 10000000000L) {
                                        Service.gI().sendThongBao(player, "Cần Có Sức Mạnh Là 10 Tỉ");
                                    } else if (player.getSession().player.inventory.gold < 200000000) {
                                        Service.gI().sendThongBao(player, "Cần 200tr Vàng");
                                    } else {
                                        player.nPoint.power -= 10000000;
                                        player.getSession().player.inventory.gold -= 200000000;
                                        player.nPoint.teleport = true;
                                        player.name = "[Florentino]\n" + player.name;
                                        Service.gI().player(player);
                                        Service.gI().Send_Caitrang(player);
                                        Service.gI().sendFlagBag(player);
                                        Zone zone = player.zone;
                                        ChangeMapService.gI().changeMap(player, zone, player.location.x, player.location.y);
                                        Service.gI().changeFlag(player, 8);
                                        PlayerService.gI().changeAndSendTypePK(player, ConstPlayer.PK_ALL);
                                        new Thread(() -> {
                                            try {
                                                Thread.sleep(240000);
                                            } catch (Exception e) {
                                            }
                                            Client.gI().kickSession(player.getSession());
                                        }).start();
                                        Service.gI().sendThongBaoAllPlayer("Thằng " + player.name + " Đang Ở " + player.zone.map.mapName + " Khu " + player.zone.zoneId);
                                        break;
                                    }
                                case 1:  // 
                                    this.createOtherMenu(player, 1,
                                            "Bạn có muốn đổi 500 điểm PVP lấy \n|6|Hào Quang Chiến Thần\n ", "Ok", "Tu choi");
                                    // bat menu doi item
                                    break;

                            }
                        }
                        if (player.iDMark.getIndexMenu() == 1) { // action doi item
                            switch (select) {
                                case 0: // trade
                                    if (player.pointPvp >= 500) {
                                        player.pointPvp -= 500;
                                        Item item = ItemService.gI().createNewItem((short) (1175));
                                        item.itemOptions.add(new Item.ItemOption(50, 20));
                                        item.itemOptions.add(new Item.ItemOption(77, 20));
                                        item.itemOptions.add(new Item.ItemOption(103, 20));
                                        item.itemOptions.add(new Item.ItemOption(30, 0));
//                                      
                                        InventoryServiceNew.gI().addItemBag(player, item);
                                        Service.getInstance().sendThongBao(player, "Chúc Mừng Bạn Đổi Hào Quang Thành Công !");
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Không đủ điểm bạn còn " + (500 - player.pointPvp) + " Điểm nữa");
                                    }
                                    break;
                            }

                        }
                    }

                }
            }
        };
    }

    public static Npc berry(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 5) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới Đấu Trường với điều kiện\n 2. đạt 150 tỷ sức mạnh "
                            + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                } else if (this.mapId == 174) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nThế thì mạnh thế đéo nào được?",
                            "Trốn về", "Ở lại");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu() && this.mapId == 5) {
                        if (select == 0) {
                            if (player.getSession().player.nPoint.power >= 150000000000L && player.inventory.gold > COST_HD) {
                                // Chức năng hiện chưa mở
//                                player.inventory.gold -= COST_HD;
//                                Service.gI().sendMoney(player);
//                                ChangeMapService.gI().changeMapBySpaceShip(player, 174, -1, 1526);
                                this.npcChat(player, "Hiện tại chức năng đang bảo trì!!");
                            } else {
                                this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                            }
                        }
                        if (select == 1) {
                        }
                    }

                    if (player.iDMark.isBaseMenu() && this.mapId == 174) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 180);
                        }
                        if (select == 1) {
                        }

                    }
                }
            }

        };
    }

    public static Npc boMong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 47 || this.mapId == 84) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Xin chào, cậu muốn tôi giúp gì?", "Nhiệm vụ\nhàng ngày",
                                "Nhận Ngọc\nMiễn Phí", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 47 || this.mapId == 84) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    if (player.playerTask.sideTask.template != null) {
                                        String npcSay = "Nhiệm vụ hiện tại: " + player.playerTask.sideTask.getName() + " ("
                                                + player.playerTask.sideTask.getLevel() + ")"
                                                + "\nHiện tại đã hoàn thành: " + player.playerTask.sideTask.count + "/"
                                                + player.playerTask.sideTask.maxCount + " ("
                                                + player.playerTask.sideTask.getPercentProcess() + "%)\nSố nhiệm vụ còn lại trong ngày: "
                                                + player.playerTask.sideTask.leftTask + "/" + ConstTask.MAX_SIDE_TASK;
                                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_PAY_SIDE_TASK,
                                                npcSay, "Trả nhiệm\nvụ", "Hủy nhiệm\nvụ");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_LEVEL_SIDE_TASK,
                                                "Tôi có vài nhiệm vụ theo cấp bậc, "
                                                + "sức cậu có thể làm được cái nào?",
                                                "Dễ", "Bình thường", "Khó", "Siêu khó", "Địa ngục", "Từ chối");
                                    }
                                    break;
                                case 1:
                                    AchievementService.gI().openAchievementUI(player);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_LEVEL_SIDE_TASK) {
                            switch (select) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    TaskService.gI().changeSideTask(player, (byte) select);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_PAY_SIDE_TASK) {
                            switch (select) {
                                case 0:
                                    TaskService.gI().paySideTask(player);
                                    break;
                                case 1:
                                    TaskService.gI().removeSideTask(player);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc karin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        return;
                    }
                    if (this.mapId == 46) {
                        if (player.levelLuyenTap >= 0) {
                            switch (player.levelLuyenTap) {
                                case 0:
                                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Muốn chiến thắng Tàu Pảy Pảy phải đánh bại được ta đã", player.dangKyTapTuDong ? "Hủy đăng\nký tập\ntự động" : "Đăng ký\ntập\ntự động", "Nhiệm vụ", "Tập luyện\nvới\nThần Mèo", "Thách đấu\nThần Mèo");
                                    break;
                                case 1:
                                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Từ giờ Yajirô sẽ luyện tập cùng ngươi. Yajirô đã từng lên đây tập luyện và bây giờ hắn mạnh hơn ta đấy", player.dangKyTapTuDong ? "Hủy đăng\nký tập\ntự động" : "Đăng ký\ntập\ntự động", "Tập luyện\nvới\nYajirô", "Thách đấu\nYajirô");
                                default:
                                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Con hãy bay theo cây Gậy Như Ý trên đỉnh tháp để đến Thần Điện gặp Thượng đế\nCon rất xứng đáng để làm đệ tử ông ấy.", player.dangKyTapTuDong ? "Hủy đăng\nký tập\ntự động" : "Đăng ký\ntập\ntự động", "Tập luyện\nvới\nThần Mèo", "Tập luyện\nvới\nYajirô");
                            }
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 46) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (player.levelLuyenTap) {
                                case 0:
                                    switch (select) {
                                        case 0:
                                            if (player.dangKyTapTuDong) {
                                                player.dangKyTapTuDong = false;
                                                NpcService.gI().createTutorial(player, tempId, avartar, "Con đã hủy thành công đăng ký tập tự động\ntừ giờ con muốn tập Offline hãy tự đến đây trước");
                                                return;
                                            }
                                            this.createOtherMenu(player, 2001, "Đăng ký để mỗi khi Offline quá 30 phút, con sẽ được tự động luyện tập với tốc độ 1280 sức mạnh mỗi phút",
                                                    "Hướng\ndẫn\nthêm", "Đồng ý\n1 ngọc\nmỗi lần", "Không\nđồng ý");
                                            break;
                                        case 1:
                                            this.npcChat(player, "");
                                            break;
                                        case 2:
                                            this.createOtherMenu(player, 2002, "Con có chắc muốn tập luyện ?\nTập luyện với ta sẽ tăng 20 sức mỗi phút",
                                                    "Đồng ý\nluyện tập", "Không\nđồng ý");
                                            break;
                                        case 3:
                                            this.createOtherMenu(player, 2003, "Con có chắc muốn thách đấu ?\nNếu thắng ta sẽ được tập luyện với Yajirô, tăng 40 sức mạnh mỗi phút",
                                                    "Đồng ý\ngiao đấu", "Không\nđồng ý");
                                    }
                                    break;

                                case 1:
                                    switch (select) {
                                        case 0:
                                            if (player.dangKyTapTuDong) {
                                                player.dangKyTapTuDong = false;
                                                NpcService.gI().createTutorial(player, tempId, avartar, "Con đã hủy thành công đăng ký tập tự động\ntừ giờ con muốn tập Offline hãy tự đến đây trước");
                                                return;
                                            }
                                            this.createOtherMenu(player, 2001, "Đăng ký để mỗi khi Offline quá 30 phút, con sẽ được tự động luyện tập với tốc độ 1280 sức mạnh mỗi phút",
                                                    "Hướng\ndẫn\nthêm", "Đồng ý\n1 ngọc\nmỗi lần", "Không\nđồng ý");
                                            break;
                                        case 1:
                                            this.createOtherMenu(player, 2002, "Con có chắc muốn tập luyện ?\nTập luyện với Yajirô sẽ tăng 40 sức mỗi phút",
                                                    "Đồng ý\nluyện tập", "Không\nđồng ý");
                                            break;
                                        case 2:
                                            this.createOtherMenu(player, 2003, "Con có chắc muốn thách đấu ?\nNếu thắng được Yajirô, con sẽ được học võ với người mạnh hơn để tăng đến 80 sức mạnh mỗi phút",
                                                    "Đồng ý\ngiao đấu", "Không\nđồng ý");
                                            break;
                                    }

                                default:
                                    switch (select) {
                                        case 0:
                                            if (player.dangKyTapTuDong) {
                                                player.dangKyTapTuDong = false;
                                                NpcService.gI().createTutorial(player, tempId, avartar, "Con đã hủy thành công đăng ký tập tự động\ntừ giờ con muốn tập Offline hãy tự đến đây trước");
                                                return;
                                            }
                                            this.createOtherMenu(player, 2001, "Đăng ký để mỗi khi Offline quá 30 phút, con sẽ được tự động luyện tập với tốc độ 1280 sức mạnh mỗi phút",
                                                    "Hướng\ndẫn\nthêm", "Đồng ý\n1 ngọc\nmỗi lần", "Không\nđồng ý");
                                            break;
                                        case 1:
                                            this.createOtherMenu(player, 2002, "Con có chắc muốn tập luyện ?\nTập luyện với ta sẽ tăng 20 sức mỗi phút",
                                                    "Đồng ý\nluyện tập", "Không\nđồng ý");
                                            break;
                                        case 2:
                                            this.createOtherMenu(player, 2003, "Con có chắc muốn tập luyện ?\nTập luyện với Yajirô sẽ tăng 40 sức mỗi phút",
                                                    "Đồng ý\nluyện tập", "Không\nđồng ý");
                                            break;
                                    }

                            }
                        } else if (player.iDMark.getIndexMenu() == 2001) {
                            switch (select) {
                                case 0:
                                    NpcService.gI().createTutorial(player, tempId, avartar, ConstNpc.TAP_TU_DONG);
                                    break;
                                case 1:
                                    player.mapIdDangTapTuDong = mapId;
                                    player.dangKyTapTuDong = true;
                                    NpcService.gI().createTutorial(player, tempId, avartar, "Từ giờ, quá 30 phút Offline con sẽ được tự động luyện tập");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 2002) {
                            if (select == 0) {
                                switch (player.levelLuyenTap) {
                                    case 0:
                                        TrainingService.gI().callBoss(player, BossID.KARIN, false);
                                        break;
                                    case 1:
                                        TrainingService.gI().callBoss(player, BossID.YAJIRO, false);
                                        break;
                                    default:
                                        TrainingService.gI().callBoss(player, BossID.KARIN, false);
                                }
                            }
                        } else if (player.iDMark.getIndexMenu() == 2003) {
                            if (select == 0) {
                                switch (player.levelLuyenTap) {
                                    case 0:
                                        TrainingService.gI().callBoss(player, BossID.KARIN, true);
                                        break;
                                    case 1:
                                        TrainingService.gI().callBoss(player, BossID.YAJIRO, true);
                                        break;
                                    default:
                                        TrainingService.gI().callBoss(player, BossID.YAJIRO, false);
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc vados(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "|2|Ta Vừa Hắc Mắp Xêm Được Tóp Của Toàn Server\b|7|Người Muốn Xem Tóp Gì?",
                            "Tóp Sức Mạnh", "Top Nhiệm Vụ", "Top Săn Trộm", "Đóng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.BASE_MENU:
                                switch (select) {
                                    case 0:
                                        Service.gI().showListTop(player, Manager.topSM);
                                        break;
                                    case 1:
                                        Service.gI().showListTop(player, Manager.topNV);
                                        break;
                                    case 2:
                                        Service.gI().showListTop(player, Manager.topNHS);
                                        break;
                                }
                        }
                    }
                }
            }
        };
    }

    public static Npc gokuSSJ_1(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 80) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta mới hạ Fide, nhưng nó đã kịp đào 1 cái lỗ\nHành tinh này sắp nổ tung rồi\nMau lượn thôi",
                                "Chuẩn");
                    } else if (this.mapId == 131) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đây là đâu? Xong cmnr", "Bó tay", "Về chỗ cũ");
                    } else {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.BASE_MENU:
                            if (this.mapId == 131) {
                                if (select == 1) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 80, -1, 870);
                                }
                            }
                            if (this.mapId == 80) {
                                if (select == 0) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 131, -1, 870);
                                }
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc gokuSSJ_2(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Hãy cố gắng luyện tập\nThu thập 9.999 bí kiếp để đổi trang phục Yardrat nhé!",
                            "Nhận\nthưởng", "OK");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (select == 0) {
                    Item soluong = InventoryServiceNew.gI().findItemBag(player, 590);
                    if (soluong.quantity >= 9999) {
                        Item yardart = ItemService.gI().createNewItem((short) (player.gender + 592));
                        yardart.itemOptions.add(new Item.ItemOption(47, 400));
                        yardart.itemOptions.add(new Item.ItemOption(97, 10));
                        yardart.itemOptions.add(new Item.ItemOption(14, 15));
                        yardart.itemOptions.add(new Item.ItemOption(147, 30));
                        yardart.itemOptions.add(new Item.ItemOption(108, 10));
                        InventoryServiceNew.gI().subQuantityItemsBag(player, soluong, 9999);
                        InventoryServiceNew.gI().addItemBag(player, yardart);
                        InventoryServiceNew.gI().sendItemBags(player);
                        Service.gI().sendThongBao(player, "Bạn nhận được võ phục của người Yardrat");
                    } else {
                        Service.gI().sendThongBao(player, "Bạn chưa đủ Bí Kiếp");
                    }
                }
            }
        };
    }

    public static Npc Giuma(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 153) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Xin chào, tôi có thể giúp gì cho cậu?",
                                "Tây thánh địa", "Quay về", "Từ chối");
                    } else if (this.mapId == 156) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 153) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: // Đến Tây thánh địa
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 156, -1, 852);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 1088);
                                    break;
                            }
                        }
                    } else if (this.mapId == 156) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                //về lanh dia bang hoi
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 153, -1, 432);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc GhiDanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            String[] menuselect = new String[]{};

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    switch (this.mapId) {
                        case 52:
                            createOtherMenu(player, 0,
                                    DaiHoiVoThuatService.gI(DaiHoiVoThuat.gI().getDaiHoiNow()).Giai(player),
                                    "Thông tin\nChi tiết", DaiHoiVoThuatService.gI(DaiHoiVoThuat.gI().getDaiHoiNow()).CanReg(player) ? "Đăng ký" : "OK",
                                    "Đại Hội\nVõ Thuật\nLần thứ\n23", "Giải\nSiêu Hạng");
                            break;
                        case 129:
                            if (Util.isAfterMidnight(player.lastTimePKDHVT23)) {
                                player.goldChallenge = 50_000_000;
                                player.rubyChallenge = 100;
                                player.levelWoodChest = 0;
                            }
                            long goldchallenge = player.goldChallenge;
                            long rubychallenge = player.rubyChallenge;
                            // Hướng dẫn thêm - Hủy\nđăng kí - về dhvt
                            if (player.levelWoodChest == 0) {
                                menuselect = new String[]{"Hướng\ndẫn\nthêm", "Thi đấu\n" + Util.numberToMoney(rubychallenge) + " hồng ngọc", "Thi đấu\n" + Util.numberToMoney(goldchallenge) + " vàng", "Về\nĐại Hội\nVõ Thuật"};
                            } else {
                                menuselect = new String[]{"Hướng\ndẫn\nthêm", "Thi đấu\n" + Util.numberToMoney(rubychallenge) + " hồng ngọc", "Thi đấu\n" + Util.numberToMoney(goldchallenge) + " vàng", "Nhận\nthưởng\nRương Cấp\n" + player.levelWoodChest, "Về\nĐại Hội\nVõ Thuật"};
                            }
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đại hội võ thuật lần thứ 23\nDiễn ra bất kể ngày đêm, ngày nghỉ, ngày lễ\nPhần thưởng vô cùng quý giá\nNhanh chóng tham gia nào", menuselect, "Từ chối");
                            break;
                        default:
                            super.openBaseMenu(player);
                            break;
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 52) {
                        switch (select) {
                            case 0:
                                Service.gI().sendPopUpMultiLine(player, tempId, avartar, DaiHoiVoThuat.gI().Info());
                                break;
                            case 1:
                                if (DaiHoiVoThuatService.gI(DaiHoiVoThuat.gI().getDaiHoiNow()).CanReg(player)) {
                                    DaiHoiVoThuatService.gI(DaiHoiVoThuat.gI().getDaiHoiNow()).Reg(player);
                                }
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapNonSpaceship(player, 129, player.location.x, 360);
                                break;
                            case 3:
                                ChangeMapService.gI().changeMapNonSpaceship(player, 113, player.location.x, 360);
                                break;
                        }
                    } else if (this.mapId == 129) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.BASE_MENU:
                                long goldchallenge = player.goldChallenge;
                                long rubychallenge = player.rubyChallenge;
                                if (player.levelWoodChest == 0) {
                                    switch (select) {
                                        case 0:
                                            NpcService.gI().createTutorial(player, tempId, this.avartar, ConstNpc.NPC_DHVT23);
                                            break;
                                        case 1:
                                        case 2:
                                            if (player.levelWoodChest != 12) {
                                                if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                                    if (select == 1) {
                                                        if (player.inventory.ruby >= rubychallenge) {
                                                            The23rdMartialArtCongressService.gI().startChallenge(player);
                                                            player.inventory.ruby -= (rubychallenge);
                                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                                            player.goldChallenge += 50000000;
                                                            player.rubyChallenge += 100;
                                                        } else {
                                                            Service.gI().sendThongBao(player, "Bạn không đủ hồng ngọc, còn thiếu " + Util.numberToMoney(rubychallenge - player.inventory.ruby) + " hồng ngọc nữa");
                                                        }
                                                    } else {
                                                        if (player.inventory.gold >= goldchallenge) {
                                                            The23rdMartialArtCongressService.gI().startChallenge(player);
                                                            player.inventory.gold -= (goldchallenge);
                                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                                            player.goldChallenge += 50000000;
                                                            player.rubyChallenge += 100;
                                                        } else {
                                                            Service.gI().sendThongBao(player, "Bạn không đủ vàng, còn thiếu " + Util.numberToMoney(goldchallenge - player.inventory.gold) + " vàng nữa");
                                                        }
                                                    }
                                                } else {
                                                    Service.gI().sendThongBao(player, "Hãy mở rương báu vật trước");
                                                }
                                            } else {
                                                Service.gI().sendThongBao(player, "Bạn đã vô địch giải. Vui lòng chờ đến ngày mai");
                                            }
                                            break;
                                        case 3:
                                            ChangeMapService.gI().changeMapNonSpaceship(player, 52, player.location.x, 336);
                                            break;
                                    }
                                } else {
                                    switch (select) {
                                        case 0:
                                            NpcService.gI().createTutorial(player, tempId, this.avartar, ConstNpc.NPC_DHVT23);
                                            break;
                                        case 1:
                                        case 2:
                                            if (player.levelWoodChest != 12) {
                                                if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                                    if (select == 1) {
                                                        if (player.inventory.ruby >= rubychallenge) {
                                                            The23rdMartialArtCongressService.gI().startChallenge(player);
                                                            player.inventory.ruby -= (rubychallenge);
                                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                                            player.goldChallenge += 50000000;
                                                            player.rubyChallenge += 100;
                                                        } else {
                                                            Service.gI().sendThongBao(player, "Bạn không đủ hồng ngọc, còn thiếu " + Util.numberToMoney(rubychallenge - player.inventory.ruby) + " hồng ngọc nữa");
                                                        }
                                                    } else {
                                                        if (player.inventory.gold >= goldchallenge) {
                                                            The23rdMartialArtCongressService.gI().startChallenge(player);
                                                            player.inventory.gold -= (goldchallenge);
                                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                                            player.goldChallenge += 50000000;
                                                            player.rubyChallenge += 100;
                                                        } else {
                                                            Service.gI().sendThongBao(player, "Bạn không đủ vàng, còn thiếu " + Util.numberToMoney(goldchallenge - player.inventory.gold) + " vàng nữa");
                                                        }
                                                    }
                                                } else {
                                                    Service.gI().sendThongBao(player, "Hãy mở rương báu vật trước");
                                                }
                                            } else {
                                                Service.gI().sendThongBao(player, "Bạn đã vô địch giải. Vui lòng chờ đến ngày mai");
                                            }
                                            break;
                                        case 3:
                                            this.createOtherMenu(player, 1, "Phần thưởng của bạn đang ở cấp " + player.levelWoodChest + " / 12\n"
                                                    + "Mỗi ngày chỉ được nhận được nhận thưởng 1 lần\n"
                                                    + "bạn có chắc sẽ nhận phần thưởng ngay bây giờ?", "OK", "Từ chối");
                                            break;
                                        case 4:
                                            ChangeMapService.gI().changeMapNonSpaceship(player, 52, player.location.x, 336);
                                            break;
                                    }
                                }
                                break;
                            case 1:
                                if (select == 0) {
                                    if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                            Item it = ItemService.gI().createNewItem((short) 570);
                                            it.itemOptions.add(new Item.ItemOption(72, player.levelWoodChest));
                                            it.itemOptions.add(new Item.ItemOption(30, 0));
                                            it.createTime = System.currentTimeMillis();
                                            InventoryServiceNew.gI().addItemBag(player, it);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            player.levelWoodChest = 0;
                                            player.lastTimeRewardWoodChest = System.currentTimeMillis();
                                            NpcService.gI().createMenuConMeo(player, -1, -1, "Bạn nhận được\n|1|Rương Gỗ\n|2|Giấu bên trong nhiều vật phẩm quý giá", "OK");
                                        } else {
                                            this.npcChat(player, "Hành trang đã đầy, cần một ô trống trong hành trang để nhận vật phẩm");
                                        }
                                    } else {
                                        Service.gI().sendThongBao(player, "Hãy mở rương báu vật trước");
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc unkonw(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, 0,
                                "Éc éc Bạn muốn gì ở tôi :3?", "Đến Võ đài Unknow", "Võ Đài Siêu Cấp");

                    }
                    if (this.mapId == 112) {
                        this.createOtherMenu(player, 0,
                                "Bạn đang còn : " + player.pointPvp + " điểm PvP Point", "Về đảo Kame", "Đổi Cải trang sự kiên", "Top PVP");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.getIndexMenu() == 0) { // 
                            switch (select) {
                                case 0:
                                    if (player.getSession().player.nPoint.power >= 10000000000L) {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 112, -1, 495);
                                        Service.gI().changeFlag(player, Util.nextInt(8));
                                    } else {
                                        this.npcChat(player, "Bạn cần 10 tỷ sức mạnh mới có thể vào");
                                    }
                                    break; // qua vo dai
                                case 1:
                                    if (player.getSession().player.nPoint.power >= 10000000000L) {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 145, -1, 495);
                                        Service.gI().changeFlag(player, Util.nextInt(8));
                                    } else {
                                        this.npcChat(player, "Bạn cần 10 tỷ sức mạnh mới có thể vào");
                                    }
                                    break; // qua vo dai

                            }
                        }
                    }

                    if (this.mapId == 112) {
                        if (player.iDMark.getIndexMenu() == 0) { // 
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 319);
                                    break; // ve dao kame
                                case 1:  // 
                                    this.createOtherMenu(player, 1,
                                            "Bạn có muốn đổi 500 điểm PVP lấy \n|6|Cải trang Mèo Kid Lân với tất cả chỉ số là 80%\n ", "Ok", "Không");
                                    // bat menu doi item
                                    break;

                                case 2:  // 
                                    Service.gI().showListTop(player, Manager.topPVP);
                                    // mo top pvp
                                    break;
                            }
                        }
                        if (player.iDMark.getIndexMenu() == 1) { // action doi item
                            switch (select) {
                                case 0: // trade
                                    if (player.pointPvp >= 500) {
                                        player.pointPvp -= 500;
                                        Item item = ItemService.gI().createNewItem((short) (1104));
                                        item.itemOptions.add(new Item.ItemOption(49, 30));
                                        item.itemOptions.add(new Item.ItemOption(77, 15));
                                        item.itemOptions.add(new Item.ItemOption(103, 20));
                                        item.itemOptions.add(new Item.ItemOption(207, 0));
                                        item.itemOptions.add(new Item.ItemOption(33, 0));
                                        InventoryServiceNew.gI().addItemBag(player, item);
                                        Service.gI().sendThongBao(player, "Chúc Mừng Bạn Đổi Cải Trang Thành Công !");
                                    } else {
                                        Service.gI().sendThongBao(player, "Không đủ điểm bạn còn " + (500 - player.pointPvp) + " Điểm nữa");
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc monaito(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 7) {
                        this.createOtherMenu(player, 0,
                                "Chào bạn tôi sẽ đưa bạn đến hành tinh Cereal?", "Đồng ý", "Từ chối");
                    }
                    if (this.mapId == 170) {
                        this.createOtherMenu(player, 0,
                                "Ta ở đây để đưa con về", "Về Làng Mori", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 7) {
                        if (player.iDMark.getIndexMenu() == 0) { // 
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 170, -1, 264);
                                    break; // den hanh tinh cereal
                            }
                        }
                    }
                    if (this.mapId == 170) {
                        if (player.iDMark.getIndexMenu() == 0) { // 
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 7, -1, 432);
                                    break; // quay ve

                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc Yaritobe_do(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến Map\nYaritobe");
                    } else if (this.mapId == 181) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi muốn tiếp tục leo tháp chứ!\n Tháp hiện tại của ngươi là :" + player.capYari,
                                "Thách Đấu", "Xem Top", "Về Đảo Kame", "Từ chối");
                    }
                }
            }

            @Override

            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        if (this.mapId == 181) {
                            switch (select) {
                                case 0:
                                    if (player.inventory.gold < 200000000) {
                                        this.npcChat(player, "Cần 200tr vàng để Thách đấu");
                                        return;
                                    }
                                    if (player.nPoint.hpMax + player.nPoint.dame < 20000) {
                                        this.npcChat(player, "Bạn còn quá yếu vui lòng quay lại sau");
                                        return;
                                    }
                                    Boss oldBossClone = BossManager.gI().getBossById(Util.createIdBossLV(player.id));
                                    if (oldBossClone != null) {
                                        oldBossClone.setDieLv(oldBossClone);
                                        this.npcChat(player, "Ấn thách đấu lại xem!");
                                    } else {
                                        int dk = (player.capYari + 1) * 2;
                                        long hptong = player.nPoint.hpMax * dk * (player.capYari >= 5 ? 2 * dk : 1);
                                        BossData bossDataClone = new BossData(
                                                "Yaritobe Red [Lv: " + player.capYari + "]",
                                                ConstPlayer.NAMEC,
                                                new short[]{1441, 1442, 1443, -1, -1, -1},
                                                10_000 * dk,
                                                new long[]{hptong * dk},
                                                new int[]{174},
                                                new int[][]{
                                                    {Skill.LIEN_HOAN, 7, 500},
                                                    {Skill.KAMEJOKO, 7, 3000},
                                                    {Skill.DICH_CHUYEN_TUC_THOI, 7, 60000},
                                                    {Skill.BIEN_KHI, 1, 60000}
                                                },
                                                new String[]{"|-2|Ta sẽ tiêu diệt ngươi"}, // text
                                                // chat 1
                                                new String[]{"|-1|Ta Sẽ đập nát đầu ngươi!"}, // text chat 2
                                                new String[]{"|-1|Hẹn người lần sau"}, // text chat 3
                                                1);
                                        try {
                                            new ThachDauYaritobe(Util.createIdBossLV(player.id), bossDataClone, player.zone,
                                                    player.name, player.capYari, player);

                                        } catch (Exception e) {
                                            Logger.logException(NpcFactory.class,
                                                    e);
                                        }
                                        player.inventory.gold -= 200000000;
                                        Service.gI().sendMoney(player);
                                    }
                                    break;
                                case 1:
                                    Service.gI().showListTop(player, Manager.topYari);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMap(player, 5, -1, 1043, 168);
                            }
                        } else if (this.mapId == 5) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 181, 513, 480, true);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc TrongTai(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 113) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Đại hội võ thuật Siêu Hạng\n\ndiễn ra 24/7 kể cả ngày lễ và chủ nhật\n"
                                + "Hãy thi đấu để khẳng định đẳng cấp của mình nhé",
                                "Top 100\nCao thủ\n", "Hướng\ndẫn\nthêm", "Đấu ngay\n",
                                "Về\nĐại Hội\nVõ Thuật");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 113) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    try (Connection con = GirlkunDB.getConnection()) {
                                    Manager.topSieuHang = Manager.realTopSieuHang(con);
                                } catch (Exception ignored) {
                                    Logger.error("Lỗi đọc top");
                                }
                                Service.gI().showListTop(player, Manager.topSieuHang, (byte) 1);
                                break;
                                case 2:
                                    List<TOP> tops = new ArrayList<>();
                                    tops.addAll(Manager.realTopSieuHang(player));
                                    Service.gI().showListTop(player, tops, (byte) 1);
                                    tops.clear();
                                    break;
                                case 3:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 52, -1, 336);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    private static Npc popo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 0) {
                        if (player.clan != null) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Thượng Đế vừa phát hiện ra 1 loại khí đang âm thầm\nhủy diệt mọi mầm sống trên Trái Đất,\nnó được gọi là Destron Gas.\nTa sẽ đưa các cậu đến nơi ấy, các cậu đã sẵn sàng chưa?",
                                    "Thông tin\nChi tiết", "Top 100\nBang hội", "Thành tích\nBang", "OK", "Từ chối");
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Thượng Đế vừa phát hiện ra 1 loại khí đang âm thầm\nhủy diệt mọi mầm sống trên Trái Đất,\nnó được gọi là Destron Gas.\nTa sẽ đưa các cậu đến nơi ấy, các cậu đã sẵn sàng chưa?",
                                    "Thông tin\nChi tiết", "Top 100\nBang hội", "OK", "Từ chối");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 0) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    NpcService.gI().createTutorial(player, tempId, this.avartar, ConstNpc.HUONG_DAN_KHI_GAS_HUY_DIET);
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    Clan clan = player.clan;
                                    if (clan != null) {
                                        ClanMember cm = clan.getClanMember((int) player.id);
                                        if (cm != null) {
                                            if (player.clanMember.getNumDateFromJoinTimeToToday() < 1) {
                                                NpcService.gI().createTutorial(player, tempId, this.avartar,
                                                        "Gia nhập bang hội trên 2 ngày mới được tham gia");
                                                return;
                                            }
                                            if (player.clan.KhiGasHuyDiet != null) {
                                                createOtherMenu(player, 2,
                                                        "Bang hội của cậu đang tham gia Destron Gas cấp độ " + player.clan.KhiGasHuyDiet.level + "\n"
                                                        + "cậu có muốn đi cùng họ không ? ("
                                                        + TimeUtil.convertTimeNow(player.clan.KhiGasHuyDiet.getLastTimeOpen())
                                                        + " trước)", "Đồng ý", "Từ chối");
                                                return;
                                            }
                                            if (!clan.isLeader(player)) {
                                                NpcService.gI().createTutorial(player, tempId, this.avartar, "Chức năng chỉ dành cho bang chủ");
                                                return;
                                            }
                                            if (clan.members.size() < Gas.N_PLAYER_CLAN) {
                                                NpcService.gI().createTutorial(player, tempId, this.avartar,
                                                        "Bang hội phải có ít nhất 5 thành viên mới có thể tham gia");
                                                return;
                                            }
                                            Input.gI().createFormChooseLevelKGHD(player);
                                        }
                                    }
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 2) {
                            if (select == 0) {
                                if (player.clan.KhiGasHuyDiet == null) {
                                    GasService.gI().openKhiGasHuyDiet(player, Byte.parseByte(String.valueOf(PLAYERID_OBJECT.get(player.id))));
                                } else {
                                    GasService.gI().openKhiGasHuyDiet(player, (byte) 0);
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc createNPC(int mapId, int status, int cx, int cy, int tempId) {
        int avatar = Manager.NPC_TEMPLATES.get(tempId).avatar;
        try {
            switch (tempId) {
                case ConstNpc.UNKOWN:
                    return unkonw(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GHI_DANH:
                    return GhiDanh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRUNG_LINH_THU:
                    return trungLinhThu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.POTAGE:
                    return poTaGe(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUY_LAO_KAME:
                    return quyLaoKame(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.POPO:
                    return popo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.SHOP_BIP:
                    return shopvip(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TAI_XIU:
                    return taixiu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRUONG_LAO_GURU:
                    return truongLaoGuru(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.VUA_VEGETA:
                    return vuaVegeta(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.ONG_GOHAN:
                case ConstNpc.ONG_MOORI:
                case ConstNpc.ONG_PARAGUS:
                    return ongGohan_ongMoori_ongParagus(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BUNMA:
                    return bulmaQK(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TOPPO:
                    return toppo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DUA_HAU:
                    return duahau(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DENDE:
                    return dende(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.APPULE:
                    return appule(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DR_DRIEF:
                    return drDrief(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CARGO:
                    return cargo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CUI:
                    return cui(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.SANTA:
                    return santa(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.URON:
                    return uron(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BA_HAT_MIT:
                    return baHatMit(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.SHOP_TAN_THU:
                    return shopTanThu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TAPION:
                    return tapion(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CHI_CHI:
                    return chiChi(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CAY_THONG:
                    return cayThong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RUONG_DO:
                    return ruongDo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DAU_THAN:
                    return dauThan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CALICK:
                    return calick(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.JACO:
                    return jaco(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THUONG_DE:
                    return thuongDe(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CUA_HANG_KY_GUI:
                    return kyGui(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.YARITOBE_DO:
                    return Yaritobe_do(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GIUMA_DAU_BO:
                    return Giuma(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.Monaito:
                    return monaito(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.VADOS:
                    return vados(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.KHI_DAU_MOI:
                    return khidaumoi(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THAN_VU_TRU:
                    return thanVuTru(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.KIBIT:
                    return kibit(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.OSIN:
                    return osin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TO_SU_KAIO:
                    return ToSuKaio(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LY_TIEU_NUONG:
                    return npclytieunuong54(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LINH_CANH:
                    return linhCanh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DOC_NHAN:
                    return DocNhan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUA_TRUNG:
                    return quaTrung(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LE_TAN:
                    return leTan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUOC_VUONG:
                    return quocVuong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BUNMA_TL:
                    return bulmaTL(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RONG_OMEGA:
                    return rongOmega(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RONG_1S:
                case ConstNpc.RONG_2S:
                case ConstNpc.RONG_3S:
                case ConstNpc.RONG_4S:
                case ConstNpc.RONG_5S:
                case ConstNpc.RONG_6S:
                case ConstNpc.RONG_7S:
                    return rong1_to_7s(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.WHIS:
                    return whis(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.WHIS_154:
                    return whis154(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.PVP:
                    return pvp(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BERRY:
                    return berry(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BILL:
                    return bill(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BO_MONG:
                    return boMong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THAN_MEO_KARIN:
                    return karin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOKU_SSJ:
                    return gokuSSJ_1(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOKU_SSJ_2:
                    return gokuSSJ_2(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DUONG_TANG:
                    return duongtank(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRONG_TAI:
                    return TrongTai(mapId, status, cx, cy, tempId, avatar);
                default:
                    return new Npc(mapId, status, cx, cy, tempId, avatar) {
                        @Override
                        public void openBaseMenu(Player player) {
                            if (canOpenNpc(player)) {
                                super.openBaseMenu(player);
                            }
                        }

                        @Override
                        public void confirmMenu(Player player, int select) {
                            if (canOpenNpc(player)) {
//                                ShopService.gI().openShopNormal(player, this, ConstNpc.SHOP_BUNMA_TL_0, 0, player.gender);
                            }
                        }
                    };

            }
        } catch (Exception e) {
            Logger.logException(NpcFactory.class,
                    e, "Lỗi load npc");
            return null;
        }
    }

    public static void createNpcRongThieng() {
        Npc npc = new Npc(-1, -1, -1, -1, ConstNpc.RONG_THIENG, -1) {
            @Override
            public void confirmMenu(Player player, int select) {
                switch (player.iDMark.getIndexMenu()) {
                    case ConstNpc.IGNORE_MENU:
                        break;
                    case ConstNpc.SHOW_SHENRON_NAMEK_CONFIRM:
                        SummonDragonNamek.gI().showConfirmShenron(player, player.iDMark.getIndexMenu(), (byte) select);
                        break;
                    case ConstNpc.SHENRON_NAMEK_CONFIRM:
                        if (select == 0) {
                            SummonDragonNamek.gI().confirmWish();
                        } else if (select == 1) {
                            SummonDragonNamek.gI().sendWhishesNamec(player);
                        }
                        break;
                    case ConstNpc.SHENRON_CONFIRM:
                        if (select == 0) {
                            SummonDragon.gI().confirmWish();
                        } else if (select == 1) {
                            SummonDragon.gI().reOpenShenronWishes(player);
                        }
                        break;
                    case ConstNpc.SHENRON_1_1:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_1_1 && select == SHENRON_1_STAR_WISHES_1.length - 1) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_1_2, SHENRON_SAY, SHENRON_1_STAR_WISHES_2);
                            break;
                        }
                    case ConstNpc.SHENRON_1_2:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_1_2 && select == SHENRON_1_STAR_WISHES_2.length - 1) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_1_1, SHENRON_SAY, SHENRON_1_STAR_WISHES_1);
                            break;
                        }
                    case ConstNpc.ICE_SHENRON:
                        if (player.iDMark.getIndexMenu() == ConstNpc.ICE_SHENRON && select == ICE_SHENRON_WISHES.length) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.ICE_SHENRON, ICE_SHENRON_SAY, ICE_SHENRON_WISHES);
                            break;
                        }
                    default:
                        SummonDragon.gI().showConfirmShenron(player, player.iDMark.getIndexMenu(), (byte) select);
                        break;
                }
            }
        };
    }

    public static void createNpcConMeo() {
        Npc npc = new Npc(-1, -1, -1, -1, ConstNpc.CON_MEO, 351) {
            @Override
            public void confirmMenu(Player player, int select) {
                switch (player.iDMark.getIndexMenu()) {
                    case ConstNpc.IGNORE_MENU:

                        break;
                    case ConstNpc.MAKE_MATCH_PVP: //                        if (player.getSession().actived) 
                    {
                        if (Maintenance.isRuning) {
                            break;
                        }
                        PVPService.gI().sendInvitePVP(player, (byte) select);
                        break;
                    }
//                        else {
//                            Service.gI().sendThongBao(player, "|5|VUI LÒNG KÍCH HOẠT TÀI KHOẢN TẠI\n|7|NROGOD.COM\n|5|ĐỂ MỞ KHÓA TÍNH NĂNG");
//                            break;
//                        }
                    case ConstNpc.MAKE_FRIEND:
                        if (select == 0) {
                            Object playerId = PLAYERID_OBJECT.get(player.id);
                            if (playerId != null) {
                                FriendAndEnemyService.gI().acceptMakeFriend(player,
                                        Integer.parseInt(String.valueOf(playerId)));
                            }
                        }
                        break;
                    case ConstNpc.REVENGE:
                        if (select == 0) {
                            PVPService.gI().acceptRevenge(player);
                        }
                        break;
                    case ConstNpc.TUTORIAL_SUMMON_DRAGON:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TUTORIAL);
                        }
                        break;
                    case ConstNpc.SUMMON_SHENRON:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TUTORIAL);
                        } else if (select == 1) {
                            SummonDragon.gI().summonShenron(player);
                        }
                        break;
                    case ConstNpc.TUTORIAL_SUMMON_DRAGONTRB://TRB
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TRB);
                        }
                        break;
                    case ConstNpc.SUMMON_SHENRONTRB:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TRB);
                        } else if (select == 1) {
                            SummonDragon.gI().summonShenronTRB(player);
                        }
                        break;
                    case ConstNpc.SUMMON_ICE_SHENRON:
                        if (select == 0) {
                            SummonDragon.gI().summonIceShenron(player);
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM1105:
                        if (select == 0) {
                            IntrinsicService.gI().sattd(player);
                        } else if (select == 1) {
                            IntrinsicService.gI().satnm(player);
                        } else if (select == 2) {
                            IntrinsicService.gI().setxd(player);
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2000:
                    case ConstNpc.MENU_OPTION_USE_ITEM2001:
                    case ConstNpc.MENU_OPTION_USE_ITEM2002:
                        try {
                        ItemService.gI().OpenSKH(player, player.iDMark.getIndexMenu(), select);
                    } catch (Exception e) {
                        Logger.error("Lỗi mở hộp quà");
                    }
                    break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2003:
                    case ConstNpc.MENU_OPTION_USE_ITEM2004:
                    case ConstNpc.MENU_OPTION_USE_ITEM2005:
                        try {
                        ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                    } catch (Exception e) {
                        Logger.error("Lỗi mở hộp quà");
                    }
                    break;
                    case ConstNpc.MENU_OPTION_USE_ITEM736:
                        try {
                        ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                    } catch (Exception e) {
                        Logger.error("Lỗi mở hộp quà");
                    }
                    break;
                    case ConstNpc.INTRINSIC:
                        if (select == 0) {
                            IntrinsicService.gI().showAllIntrinsic(player);
                        } else if (select == 1) {
                            IntrinsicService.gI().showConfirmOpen(player);
                        } else if (select == 2) {
                            IntrinsicService.gI().showConfirmOpenVip(player);
                        }
                        break;
                    case ConstNpc.TTMEM:
                        switch (select) {
                            case 1:
                                NpcService.gI().createMenuConMeo(player, ConstNpc.TTMEM, 11039,
                                        "|7|Thông tin Sư Phụ "
                                        + "\n|8|Sức Mạnh: " + Util.formatNumber(player.nPoint.power)
                                        + "\n|4|Hp: " + Util.Format(player.nPoint.hp) + "/" + Util.Format(player.nPoint.hpMax)
                                        + "\n|4|Ki: " + Util.Format(player.nPoint.mp) + "/" + Util.Format(player.nPoint.mpMax)
                                        + "\n|4|Sức đánh: " + Util.Format(player.nPoint.dame),
                                        new String[]{"Chi Tiết", "Thông Tin\n Sư Phụ", "Thông Tin\n Đệ Tử", "Đóng"},
                                        player);
                                break;
                            case 2:
                                NpcService.gI().createMenuConMeo(player, ConstNpc.TTMEM, 11039,
                                        "|7|Thông tin đệ tử "
                                        + "\n|8|Sức Mạnh: " + Util.formatNumber(player.pet.nPoint.power)
                                        + "\n|4|Hp: " + Util.Format(player.pet.nPoint.hp) + "/" + Util.Format(player.pet.nPoint.hpMax)
                                        + "\n|4|Ki: " + Util.Format(player.pet.nPoint.mp) + "/" + Util.Format(player.pet.nPoint.mpMax)
                                        + "\n|4|Sức đánh: " + Util.Format(player.pet.nPoint.dame),
                                        new String[]{"Chi Tiết", "Thông Tin\n Sư Phụ", "Thông Tin\n Đệ Tử", "Đóng"},
                                        player);
                                break;
                        }
                        break;
                    case ConstNpc.CONFIRM_OPEN_INTRINSIC:
                        if (select == 0) {
                            IntrinsicService.gI().open(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_OPEN_INTRINSIC_VIP:
                        if (select == 0) {
                            IntrinsicService.gI().openVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_LEAVE_CLAN:
                        if (select == 0) {
                            ClanService.gI().leaveClan(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_NHUONG_PC:
                        if (select == 0) {
                            ClanService.gI().phongPc(player, (int) PLAYERID_OBJECT.get(player.id));
                        }
                        break;
                    case ConstNpc.BAN_PLAYER:
                        if (select == 0) {
                            PlayerService.gI().banPlayer((Player) PLAYERID_OBJECT.get(player.id));
                            Service.gI().sendThongBao(player, "Ban người chơi " + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                        }
                        break;
                    case ConstNpc.LOI_CHUC:
                        if (select == 0) {
                            if (Util.isTrue(30, 100)) {
                                Item loiChuc = ItemService.gI().createNewItem((short) 1404);
                                loiChuc.quantity++;
                                Service.gI().sendThongBao((player), "Mình đã nghĩ ra 1 lời chúc cho bạn nè");
                            } else {
                                Service.gI().sendThongBao((player), "Mình không nghĩ ra được gì, hẹn bạn lần sau.");
                            }
                        }
                        break;
                    case ConstNpc.BUFF_PET:
                        if (select == 0) {
                            Player pl = (Player) PLAYERID_OBJECT.get(player.id);
                            if (pl.pet == null) {
                                PetService.gI().createNormalPet(pl);
                                Service.gI().sendThongBao(player, "Phát đệ tử cho " + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                            }
                        }
                        break;
                    case ConstNpc.ACTIVE_PLAYER:
                        if (select == 0) {
                            PlayerService.gI().ActivePlayer((Player) PLAYERID_OBJECT.get(player.id));
                            Service.getInstance().sendThongBao(player, "Activated  " + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                        }
                        break;
                    case ConstNpc.TVMAX:
                        Item thoivangne = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 457);
                        switch (select) {
                            case 0:
                                if (thoivangne.quantity < 1) {
                                    Service.gI().sendThongBao(player,
                                            "Bạn không đủ 1 thỏi vàng");
                                } else if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                    player.inventory.gold += 500000000;
                                    Service.gI().sendThongBao(player, "Bạn vừa dùng thỏi vàng và nhận được 500tr vàng");
                                    InventoryServiceNew.gI().subQuantityItemsBag(player, thoivangne, 1);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.getInstance().sendMoney(player);
                                } else {
                                    Service.gI().sendThongBao(player, "Hàng trang đã đầy");
                                }
                                break;
                            case 1:
                                if (thoivangne.quantity < 5) {
                                    Service.gI().sendThongBao(player,
                                            "Bạn không đủ 5 thỏi vàng");
                                } else if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                    player.inventory.gold += 2500000000L;
                                    Service.gI().sendThongBao(player, "Bạn vừa dùng thỏi vàng và nhận được 2 tỷ vàng");
                                    InventoryServiceNew.gI().subQuantityItemsBag(player, thoivangne, 5);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.getInstance().sendMoney(player);
                                } else {
                                    Service.gI().sendThongBao(player, "Hàng trang đã đầy");
                                }
                                break;
                            case 2:
                                if (thoivangne.quantity < 10) {
                                    Service.gI().sendThongBao(player,
                                            "Bạn không đủ 10 thỏi vàng");
                                } else if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                    player.inventory.gold += 5000000000L;
                                    Service.gI().sendThongBao(player, "Bạn vừa dùng thỏi vàng và nhận được 5 tỷ vàng");
                                    InventoryServiceNew.gI().subQuantityItemsBag(player, thoivangne, 10);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.getInstance().sendMoney(player);
                                } else {
                                    Service.gI().sendThongBao(player, "Hàng trang đã đầy");
                                }
                                break;
                            case 3:
                                if (thoivangne.quantity < 25) {
                                    Service.gI().sendThongBao(player,
                                            "Bạn không đủ 25 thỏi vàng");
                                } else if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                    player.inventory.gold += 12500000000L;
                                    Service.gI().sendThongBao(player, "Bạn vừa dùng thỏi vàng và nhận được 12 tỷ 5 vàng");
                                    InventoryServiceNew.gI().subQuantityItemsBag(player, thoivangne, 25);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.getInstance().sendMoney(player);
                                } else {
                                    Service.gI().sendThongBao(player, "Hàng trang đã đầy");
                                }
                                break;
                            case 4:
                                if (thoivangne.quantity < 50) {
                                    Service.gI().sendThongBao(player,
                                            "Bạn không đủ 50 thỏi vàng");
                                } else if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                    player.inventory.gold += 25000000000L;
                                    Service.gI().sendThongBao(player, "Bạn vừa dùng thỏi vàng và nhận được 25 tỷ vàng");
                                    InventoryServiceNew.gI().subQuantityItemsBag(player, thoivangne, 50);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.getInstance().sendMoney(player);
                                } else {
                                    Service.gI().sendThongBao(player, "Hàng trang đã đầy");
                                }
                                break;
                            case 5:
                                if (thoivangne.quantity < 100) {
                                    Service.gI().sendThongBao(player,
                                            "Bạn không đủ 100 thỏi vàng");
                                } else if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                    player.inventory.gold += 50000000000L;
                                    Service.gI().sendThongBao(player,
                                            "Bạn vừa dùng thỏi vàng và nhận được 50 tỷ vàng");
                                    InventoryServiceNew.gI().subQuantityItemsBag(player, thoivangne, 100);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.getInstance().sendMoney(player);
                                } else {
                                    Service.gI().sendThongBao(player, "Hàng trang đã đầy");
                                }
                                break;

                        }
                        break;
                    case ConstNpc.HOP_QUA_THAN_LINH:
                        switch (select) {
                            case 0:
                                NpcService.gI().createMenuConMeo(player, ConstNpc.SKH_TRAI_DAT, -1,
                                        "Chọn hành tinh của đồ thần linh muốn nhận.",
                                        "Set\nKamejoko", "Set\nKaioken", "Set\nThên xin hăng");
                                break;
                            case 1:
                                NpcService.gI().createMenuConMeo(player, ConstNpc.SKH_NAMEC, -1,
                                        "Chọn hành tinh của đồ thần linh muốn nhận.",
                                        "Set\nLiên hoàn", "Set\nPicolo", "Set\nPikkoro Daimao");
                                break;
                            case 2:
                                NpcService.gI().createMenuConMeo(player, ConstNpc.SKH_XAYDA, -1,
                                        "Chọn hành tinh của đồ thần linh muốn nhận.",
                                        "Set\nKakarot", "Set\nCađíc", "Set\nNappa");
                                break;
                        }
                        break;
                    case ConstNpc.SKH_TRAI_DAT:
                        Item HopQuaThanLinh = InventoryServiceNew.gI().findItemBag(player, 1489);
                        Item aotl_td = ItemService.gI().createNewItem((short) 555);
                        Item quantl_td = ItemService.gI().createNewItem((short) 556);
                        Item gangtl_td = ItemService.gI().createNewItem((short) 562);
                        Item giaytl_td = ItemService.gI().createNewItem((short) 563);
                        Item nhan = ItemService.gI().createNewItem((short) 561);
                        aotl_td.itemOptions.add(new Item.ItemOption(47, 800 + new Random().nextInt(200)));
                        aotl_td.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        aotl_td.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        quantl_td.itemOptions.add(new Item.ItemOption(22, 47 + new Random().nextInt(5)));
                        quantl_td.itemOptions.add(new Item.ItemOption(27, (47 + new Random().nextInt(5)) * 1000 * 15 / 100));
                        quantl_td.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        quantl_td.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        gangtl_td.itemOptions.add(new Item.ItemOption(0, 3500 + new Random().nextInt(1200)));
                        gangtl_td.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        gangtl_td.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        giaytl_td.itemOptions.add(new Item.ItemOption(23, 42 + new Random().nextInt(5)));
                        giaytl_td.itemOptions.add(new Item.ItemOption(28, (42 + new Random().nextInt(5)) * 1000 * 15 / 100));
                        giaytl_td.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        giaytl_td.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        nhan.itemOptions.add(new Item.ItemOption(14, 14 + new Random().nextInt(4)));
                        nhan.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        nhan.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        switch (select) {
                            case 0:
                                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 5) {
                                    Service.gI().sendThongBao(player, "Cần 5 ô hành trang mới có thể mở!!!");
                                    return;
                                }
                                aotl_td.itemOptions.add(new Item.ItemOption(129, 0));
                                aotl_td.itemOptions.add(new Item.ItemOption(141, 0));
                                quantl_td.itemOptions.add(new Item.ItemOption(129, 0));
                                quantl_td.itemOptions.add(new Item.ItemOption(141, 0));
                                gangtl_td.itemOptions.add(new Item.ItemOption(129, 0));
                                gangtl_td.itemOptions.add(new Item.ItemOption(141, 80));
                                giaytl_td.itemOptions.add(new Item.ItemOption(129, 0));
                                giaytl_td.itemOptions.add(new Item.ItemOption(141, 0));
                                nhan.itemOptions.add(new Item.ItemOption(129, 0));
                                nhan.itemOptions.add(new Item.ItemOption(141, 0));
                                InventoryServiceNew.gI().addItemBag(player, aotl_td);
                                InventoryServiceNew.gI().addItemBag(player, quantl_td);
                                InventoryServiceNew.gI().addItemBag(player, gangtl_td);
                                InventoryServiceNew.gI().addItemBag(player, giaytl_td);
                                InventoryServiceNew.gI().addItemBag(player, nhan);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, HopQuaThanLinh, 1);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn nhận được 1 set thần linh trái đất");
                                break;
                            case 1:
                                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 5) {
                                    Service.gI().sendThongBao(player, "Cần 5 ô hành trang mới có thể mở!!!");
                                    return;
                                }
                                aotl_td.itemOptions.add(new Item.ItemOption(128, 0));
                                aotl_td.itemOptions.add(new Item.ItemOption(140, 0));
                                quantl_td.itemOptions.add(new Item.ItemOption(128, 0));
                                quantl_td.itemOptions.add(new Item.ItemOption(140, 0));
                                gangtl_td.itemOptions.add(new Item.ItemOption(128, 0));
                                gangtl_td.itemOptions.add(new Item.ItemOption(140, 0));
                                giaytl_td.itemOptions.add(new Item.ItemOption(128, 0));
                                giaytl_td.itemOptions.add(new Item.ItemOption(140, 0));
                                nhan.itemOptions.add(new Item.ItemOption(128, 0));
                                nhan.itemOptions.add(new Item.ItemOption(140, 0));
                                InventoryServiceNew.gI().addItemBag(player, aotl_td);
                                InventoryServiceNew.gI().addItemBag(player, quantl_td);
                                InventoryServiceNew.gI().addItemBag(player, gangtl_td);
                                InventoryServiceNew.gI().addItemBag(player, giaytl_td);
                                InventoryServiceNew.gI().addItemBag(player, nhan);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, HopQuaThanLinh, 1);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn nhận được 1 set thần linh trái đất");
                                break;
                            case 2:
                                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 5) {
                                    Service.gI().sendThongBao(player, "Cần 5 ô hành trang mới có thể mở!!!");
                                    return;
                                }
                                aotl_td.itemOptions.add(new Item.ItemOption(127, 0));
                                aotl_td.itemOptions.add(new Item.ItemOption(139, 0));
                                quantl_td.itemOptions.add(new Item.ItemOption(127, 0));
                                quantl_td.itemOptions.add(new Item.ItemOption(139, 0));
                                gangtl_td.itemOptions.add(new Item.ItemOption(127, 0));
                                gangtl_td.itemOptions.add(new Item.ItemOption(139, 0));
                                giaytl_td.itemOptions.add(new Item.ItemOption(127, 0));
                                giaytl_td.itemOptions.add(new Item.ItemOption(139, 0));
                                nhan.itemOptions.add(new Item.ItemOption(127, 0));
                                nhan.itemOptions.add(new Item.ItemOption(139, 0));
                                InventoryServiceNew.gI().addItemBag(player, aotl_td);
                                InventoryServiceNew.gI().addItemBag(player, quantl_td);
                                InventoryServiceNew.gI().addItemBag(player, gangtl_td);
                                InventoryServiceNew.gI().addItemBag(player, giaytl_td);
                                InventoryServiceNew.gI().addItemBag(player, nhan);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, HopQuaThanLinh, 1);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn nhận được 1 set thần linh trái đất");
                                break;
                        }
                        break;
                    case ConstNpc.SKH_NAMEC:
                        Item HopQuaThanLinhn = InventoryServiceNew.gI().findItemBag(player, 1489);
                        Item aotl_nm = ItemService.gI().createNewItem((short) 557);
                        Item quantl_nm = ItemService.gI().createNewItem((short) 558);
                        Item gangtl_nm = ItemService.gI().createNewItem((short) 564);
                        Item giaytl_nm = ItemService.gI().createNewItem((short) 565);
                        Item nhann = ItemService.gI().createNewItem((short) 561);
                        aotl_nm.itemOptions.add(new Item.ItemOption(47, 800 + new Random().nextInt(200)));
                        aotl_nm.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        aotl_nm.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        quantl_nm.itemOptions.add(new Item.ItemOption(22, 47 + new Random().nextInt(5)));
                        quantl_nm.itemOptions.add(new Item.ItemOption(27, (47 + new Random().nextInt(5)) * 1000 * 15 / 100));
                        quantl_nm.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        quantl_nm.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        gangtl_nm.itemOptions.add(new Item.ItemOption(0, 3500 + new Random().nextInt(1200)));
                        gangtl_nm.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        gangtl_nm.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        giaytl_nm.itemOptions.add(new Item.ItemOption(23, 42 + new Random().nextInt(5)));
                        giaytl_nm.itemOptions.add(new Item.ItemOption(28, (42 + new Random().nextInt(5)) * 1000 * 15 / 100));
                        giaytl_nm.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        giaytl_nm.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        nhann.itemOptions.add(new Item.ItemOption(14, 14 + new Random().nextInt(4)));
                        nhann.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        nhann.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        switch (select) {
                            case 0:
                                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 5) {
                                    Service.gI().sendThongBao(player, "Cần 5 ô hành trang mới có thể mở!!!");
                                    return;
                                }
                                aotl_nm.itemOptions.add(new Item.ItemOption(131, 0));
                                aotl_nm.itemOptions.add(new Item.ItemOption(143, 0));
                                quantl_nm.itemOptions.add(new Item.ItemOption(131, 0));
                                quantl_nm.itemOptions.add(new Item.ItemOption(143, 0));
                                gangtl_nm.itemOptions.add(new Item.ItemOption(131, 0));
                                gangtl_nm.itemOptions.add(new Item.ItemOption(143, 0));
                                giaytl_nm.itemOptions.add(new Item.ItemOption(131, 0));
                                giaytl_nm.itemOptions.add(new Item.ItemOption(143, 0));
                                nhann.itemOptions.add(new Item.ItemOption(131, 0));
                                nhann.itemOptions.add(new Item.ItemOption(143, 0));
                                InventoryServiceNew.gI().addItemBag(player, aotl_nm);
                                InventoryServiceNew.gI().addItemBag(player, quantl_nm);
                                InventoryServiceNew.gI().addItemBag(player, gangtl_nm);
                                InventoryServiceNew.gI().addItemBag(player, giaytl_nm);
                                InventoryServiceNew.gI().addItemBag(player, nhann);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, HopQuaThanLinhn, 1);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn nhận được 1 set thần linh trái đất");
                                break;
                            case 1:
                                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 5) {
                                    Service.gI().sendThongBao(player, "Cần 5 ô hành trang mới có thể mở!!!");
                                    return;
                                }
                                aotl_nm.itemOptions.add(new Item.ItemOption(130, 0));
                                aotl_nm.itemOptions.add(new Item.ItemOption(142, 0));
                                quantl_nm.itemOptions.add(new Item.ItemOption(130, 0));
                                quantl_nm.itemOptions.add(new Item.ItemOption(142, 0));
                                gangtl_nm.itemOptions.add(new Item.ItemOption(130, 0));
                                gangtl_nm.itemOptions.add(new Item.ItemOption(142, 0));
                                giaytl_nm.itemOptions.add(new Item.ItemOption(130, 0));
                                giaytl_nm.itemOptions.add(new Item.ItemOption(142, 0));
                                nhann.itemOptions.add(new Item.ItemOption(130, 0));
                                nhann.itemOptions.add(new Item.ItemOption(142, 0));
                                InventoryServiceNew.gI().addItemBag(player, aotl_nm);
                                InventoryServiceNew.gI().addItemBag(player, quantl_nm);
                                InventoryServiceNew.gI().addItemBag(player, gangtl_nm);
                                InventoryServiceNew.gI().addItemBag(player, giaytl_nm);
                                InventoryServiceNew.gI().addItemBag(player, nhann);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, HopQuaThanLinhn, 1);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn nhận được 1 set thần linh trái đất");
                                break;
                            case 2:
                                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 5) {
                                    Service.gI().sendThongBao(player, "Cần 5 ô hành trang mới có thể mở!!!");
                                    return;
                                }
                                aotl_nm.itemOptions.add(new Item.ItemOption(132, 0));
                                aotl_nm.itemOptions.add(new Item.ItemOption(144, 0));
                                quantl_nm.itemOptions.add(new Item.ItemOption(132, 0));
                                quantl_nm.itemOptions.add(new Item.ItemOption(144, 0));
                                gangtl_nm.itemOptions.add(new Item.ItemOption(132, 0));
                                gangtl_nm.itemOptions.add(new Item.ItemOption(144, 0));
                                giaytl_nm.itemOptions.add(new Item.ItemOption(132, 0));
                                giaytl_nm.itemOptions.add(new Item.ItemOption(144, 0));
                                nhann.itemOptions.add(new Item.ItemOption(132, 0));
                                nhann.itemOptions.add(new Item.ItemOption(144, 0));
                                InventoryServiceNew.gI().addItemBag(player, aotl_nm);
                                InventoryServiceNew.gI().addItemBag(player, quantl_nm);
                                InventoryServiceNew.gI().addItemBag(player, gangtl_nm);
                                InventoryServiceNew.gI().addItemBag(player, giaytl_nm);
                                InventoryServiceNew.gI().addItemBag(player, nhann);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, HopQuaThanLinhn, 1);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn nhận được 1 set thần linh trái đất");
                                break;
                        }
                        break;
                    case ConstNpc.SKH_XAYDA:
                        Item HopQuaThanLinhnn = InventoryServiceNew.gI().findItemBag(player, 1489);
                        Item aotl_xd = ItemService.gI().createNewItem((short) 559);
                        Item quantl_xd = ItemService.gI().createNewItem((short) 560);
                        Item gangtl_xd = ItemService.gI().createNewItem((short) 566);
                        Item giaytl_xd = ItemService.gI().createNewItem((short) 567);
                        Item nhannn = ItemService.gI().createNewItem((short) 561);
                        aotl_xd.itemOptions.add(new Item.ItemOption(47, 800 + new Random().nextInt(200)));
                        aotl_xd.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        aotl_xd.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        quantl_xd.itemOptions.add(new Item.ItemOption(22, 47 + new Random().nextInt(5)));
                        quantl_xd.itemOptions.add(new Item.ItemOption(27, (47 + new Random().nextInt(5)) * 1000 * 15 / 100));
                        quantl_xd.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        quantl_xd.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        gangtl_xd.itemOptions.add(new Item.ItemOption(0, 3500 + new Random().nextInt(1200)));
                        gangtl_xd.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        gangtl_xd.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        giaytl_xd.itemOptions.add(new Item.ItemOption(23, 42 + new Random().nextInt(5)));
                        giaytl_xd.itemOptions.add(new Item.ItemOption(28, (42 + new Random().nextInt(5)) * 1000 * 15 / 100));
                        giaytl_xd.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        giaytl_xd.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        nhannn.itemOptions.add(new Item.ItemOption(14, 14 + new Random().nextInt(4)));
                        nhannn.itemOptions.add(new Item.ItemOption(21, 18)); // ycsm 18 tỉ
                        nhannn.itemOptions.add(new Item.ItemOption(30, 1)); // ycsm 18 tỉ
                        switch (select) {
                            case 0:
                                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 5) {
                                    Service.gI().sendThongBao(player, "Cần 5 ô hành trang mới có thể mở!!!");
                                    return;
                                }
                                aotl_xd.itemOptions.add(new Item.ItemOption(133, 0));
                                aotl_xd.itemOptions.add(new Item.ItemOption(136, 0));
                                quantl_xd.itemOptions.add(new Item.ItemOption(133, 0));
                                quantl_xd.itemOptions.add(new Item.ItemOption(136, 0));
                                gangtl_xd.itemOptions.add(new Item.ItemOption(133, 0));
                                gangtl_xd.itemOptions.add(new Item.ItemOption(136, 0));
                                giaytl_xd.itemOptions.add(new Item.ItemOption(133, 0));
                                giaytl_xd.itemOptions.add(new Item.ItemOption(136, 0));
                                nhannn.itemOptions.add(new Item.ItemOption(133, 0));
                                nhannn.itemOptions.add(new Item.ItemOption(136, 0));
                                InventoryServiceNew.gI().addItemBag(player, aotl_xd);
                                InventoryServiceNew.gI().addItemBag(player, quantl_xd);
                                InventoryServiceNew.gI().addItemBag(player, gangtl_xd);
                                InventoryServiceNew.gI().addItemBag(player, giaytl_xd);
                                InventoryServiceNew.gI().addItemBag(player, nhannn);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, HopQuaThanLinhnn, 1);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn nhận được 1 set thần linh trái đất");
                                break;
                            case 1:
                                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 5) {
                                    Service.gI().sendThongBao(player, "Cần 5 ô hành trang mới có thể mở!!!");
                                    return;
                                }
                                aotl_xd.itemOptions.add(new Item.ItemOption(134, 0));
                                aotl_xd.itemOptions.add(new Item.ItemOption(137, 0));
                                quantl_xd.itemOptions.add(new Item.ItemOption(134, 0));
                                quantl_xd.itemOptions.add(new Item.ItemOption(137, 0));
                                gangtl_xd.itemOptions.add(new Item.ItemOption(134, 0));
                                gangtl_xd.itemOptions.add(new Item.ItemOption(137, 0));
                                giaytl_xd.itemOptions.add(new Item.ItemOption(134, 0));
                                giaytl_xd.itemOptions.add(new Item.ItemOption(137, 0));
                                nhannn.itemOptions.add(new Item.ItemOption(134, 0));
                                nhannn.itemOptions.add(new Item.ItemOption(137, 0));
                                InventoryServiceNew.gI().addItemBag(player, aotl_xd);
                                InventoryServiceNew.gI().addItemBag(player, quantl_xd);
                                InventoryServiceNew.gI().addItemBag(player, gangtl_xd);
                                InventoryServiceNew.gI().addItemBag(player, giaytl_xd);
                                InventoryServiceNew.gI().addItemBag(player, nhannn);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, HopQuaThanLinhnn, 1);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn nhận được 1 set thần linh trái đất");
                                break;
                            case 2:
                                if (InventoryServiceNew.gI().getCountEmptyBag(player) < 5) {
                                    Service.gI().sendThongBao(player, "Cần 5 ô hành trang mới có thể mở!!!");
                                    return;
                                }
                                aotl_xd.itemOptions.add(new Item.ItemOption(135, 0));
                                aotl_xd.itemOptions.add(new Item.ItemOption(138, 0));
                                quantl_xd.itemOptions.add(new Item.ItemOption(135, 0));
                                quantl_xd.itemOptions.add(new Item.ItemOption(138, 0));
                                gangtl_xd.itemOptions.add(new Item.ItemOption(135, 0));
                                gangtl_xd.itemOptions.add(new Item.ItemOption(138, 0));
                                giaytl_xd.itemOptions.add(new Item.ItemOption(135, 0));
                                giaytl_xd.itemOptions.add(new Item.ItemOption(138, 0));
                                nhannn.itemOptions.add(new Item.ItemOption(135, 0));
                                nhannn.itemOptions.add(new Item.ItemOption(138, 0));
                                InventoryServiceNew.gI().addItemBag(player, aotl_xd);
                                InventoryServiceNew.gI().addItemBag(player, quantl_xd);
                                InventoryServiceNew.gI().addItemBag(player, gangtl_xd);
                                InventoryServiceNew.gI().addItemBag(player, giaytl_xd);
                                InventoryServiceNew.gI().addItemBag(player, nhannn);
                                InventoryServiceNew.gI().subQuantityItemsBag(player, HopQuaThanLinhnn, 1);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn nhận được 1 set thần linh trái đất");
                                break;
                        }
                        break;
                    case ConstNpc.MENU_ADMIN:
                        switch (select) {
                            case 0:
                                this.createMenuConMeo(player, ConstNpc.ADMIN, -1, "|7| Admin Ngọc Rồng One Puch Man\b|2| Bùi Kim Trường\b|4| Người Đang Chơi: " + Client.gI().getPlayers().size() + "\n" + "|8|Current thread: " + (Thread.activeCount()) + "\n",
                                        "Ngọc Rồng", "Đệ Tử", "Bảo Trì", "Tìm Kiếm\nPlayer", "Chat All", "Đóng");
                                break;
                            case 1:
                                this.createOtherMenu(player, ConstNpc.CALL_BOSS,
                                        "Chọn Boss?", "Full Cụm\nANDROID", "BLACK", "BROLY", "Cụm\nCell",
                                        "Cụm\nDoanh trại", "DOREMON", "FIDE", "FIDE\nBlack", "Cụm\nGINYU", "Cụm\nNAPPA", "Gắp Thú");
                                break;
                            case 2:
                                this.createOtherMenu(player, ConstNpc.BUFF_ITEM,
                                        "Buff Item", "Buff Item", "Item Option", "Buff Skh", "Buff Item Vip");
                                break;
                        }
                        break;
                    case ConstNpc.ADMIN:
                        switch (select) {
                            case 0:
                                for (int i = 14; i <= 20; i++) {
                                    Item itemm = ItemService.gI().createNewItem((short) i);
                                    InventoryServiceNew.gI().addItemBag(player, itemm);
                                }
                                InventoryServiceNew.gI().sendItemBags(player);
                                break;
                            case 1:
                                if (player.pet == null) {
                                    PetService.gI().createNormalPet(player);
                                } else {
                                    if (player.pet.typePet == 1) {
                                        PetService.gI().changePicPet(player);
                                    } else if (player.pet.typePet == 2) {
                                        PetService.gI().changeMabuPet(player);
                                    }
                                    PetService.gI().changeBerusPet(player);
                                }
                                break;
                            case 2:
                                if (player.isAdmin()) {
                                    Maintenance.gI().start(15);
                                    System.out.println(player.name);
                                }
                                break;
                            case 3:
                                Input.gI().createFormFindPlayer(player);
                                break;

                        }
                        break;
                    case ConstNpc.BUFF_ITEM:
                        switch (select) {
                            case 0:
                                Input.gI().createFormSenditem(player);
                                break;
                            case 1:
                                Input.gI().createFormSenditem1(player);
                                break;
                            case 2:
                                Input.gI().createFormSenditemskh(player);
                                break;
                            case 3:
                                Input.gI().createFormSenditem2(player);
                                break;
                        }
                        break;
                    case ConstNpc.CALL_BOSS:
                        switch (select) {
                            case 0:
                                BossManager.gI().createBoss(BossID.ANDROID_14);
                                BossManager.gI().createBoss(BossID.DR_KORE);
                                BossManager.gI().createBoss(BossID.PIC_POC_KING_KONG);
                                BossManager.gI().createBoss(BossID.SUPER_ANDROID_17);
                                break;
                            case 1:
                                BossManager.gI().createBoss(BossID.BLACK_GOKU);
                                BossManager.gI().createBoss(BossID.THAN_THAN_KAIOSHIN_ZAMAS);
                                BossManager.gI().createBoss(BossID.KAIOSHIN_ZAMAS);
                                break;
                            case 2:
//                                BossManager.gI().createBoss(BossID.BROLY);
                                BossManager.gI().createBoss(BossID.SUPPER_BROLY);
                                break;
                            case 3:
                                BossManager.gI().createBoss(BossID.SIEU_BO_HUNG);
                                BossManager.gI().createBoss(BossID.XEN_BO_HUNG);
                                BossManager.gI().createBoss(BossID.XEN_CON_1);
                                break;
                            case 4:
                                BossManager.gI().createBoss(BossID.COOLER);
                                BossManager.gI().createBoss(BossID.VEGETA);
                                break;
                            case 5:
                                BossManager.gI().createBoss(BossID.DORAEMON);
                                BossManager.gI().createBoss(BossID.NOBITA);
                                BossManager.gI().createBoss(BossID.XUKA);
                                BossManager.gI().createBoss(BossID.CHAIEN);
                                BossManager.gI().createBoss(BossID.XEKO);
                                break;
                            case 6:
                                BossManager.gI().createBoss(BossID.FIDE);
                                break;
                            case 7:
//                                BossManager.gI().createBoss(BossID.D_TANG);
                                break;
                            case 8:
                                BossManager.gI().createBoss(BossID.TDST);
                                break;
                            case 9:
                                BossManager.gI().createBoss(BossID.KUKU);
                                BossManager.gI().createBoss(BossID.MAP_DAU_DINH);
                                BossManager.gI().createBoss(BossID.RAMBO);
                                break;
                            case 10:
                                BossManager.gI().createBoss(BossID.SONGOKU_TA_AC);
                                break;
                            case 11:
//                                BossManager.gI().createBoss(BossID.WIND);
//                                BossManager.gI().createBoss(BossID.ICE);
                                break;
                            case 12:
//                                BossManager.gI().createBoss(BossID.Brook);
//                                BossManager.gI().createBoss(BossID.Mihawk);
//                                BossManager.gI().createBoss(BossID.Along);
//                                BossManager.gI().createBoss(BossID.Kaido);
//                                BossManager.gI().createBoss(BossID.linh); 
                                break;
                            case 13:
//                                BossManager.gI().createBoss(BossID.GAS);
//                                BossManager.gI().createBoss(BossID.GRANOLA);
                                break;
                            case 14:
//                                BossManager.gI().createBoss(BossID.tan);
//                                BossManager.gI().createBoss(BossID.nezu);
//                                BossManager.gI().createBoss(BossID.zen);
//                                BossManager.gI().createBoss(BossID.hino);
//                                BossManager.gI().createBoss(BossID.gojo);
//                                BossManager.gI().createBoss(BossID.akaza); 
                                break;
                            case 15:
//                                BossManager.gI().createBoss(BossID.THOR_2);
//                                BossManager.gI().createBoss(BossID.MIU);
//                                BossManager.gI().createBoss(BossID.HO);
                                break;
                            case 16:
//                                BossManager.gI().createBoss(BossID.Janemba);
//                                BossManager.gI().createBoss(BossID.pikkon);
                                break;
                            case 17:
                                BossManager.gI().createBoss(BossID.THIEN_SU_VADOS);
                                BossManager.gI().createBoss(BossID.THIEN_SU_WHIS);
                                break;
                        }
                        break;
                    case ConstNpc.menutd:
                        switch (select) {
                            case 0:
                                try {
                                ItemService.gI().settaiyoken(player);
                            } catch (Exception e) {
                            }
                            break;
                            case 1:
                                try {
                                ItemService.gI().setgenki(player);
                            } catch (Exception e) {
                            }
                            break;
                            case 2:
                                try {
                                ItemService.gI().setkamejoko(player);
                            } catch (Exception e) {
                            }
                            break;
                        }
                        break;

                    case ConstNpc.menunm:
                        switch (select) {
                            case 0:
                                try {
                                ItemService.gI().setgodki(player);
                            } catch (Exception e) {
                            }
                            break;
                            case 1:
                                try {
                                ItemService.gI().setgoddam(player);
                            } catch (Exception e) {
                            }
                            break;
                            case 2:
                                try {
                                ItemService.gI().setsummon(player);
                            } catch (Exception e) {
                            }
                            break;
                        }
                        break;

                    case ConstNpc.menuxd:
                        switch (select) {
                            case 0:
                                try {
                                ItemService.gI().setgodgalick(player);
                            } catch (Exception e) {
                            }
                            break;
                            case 1:
                                try {
                                ItemService.gI().setmonkey(player);
                            } catch (Exception e) {
                            }
                            break;
                            case 2:
                                try {
                                ItemService.gI().setgodhp(player);
                            } catch (Exception e) {
                            }
                            break;
                        }
                        break;

                    case ConstNpc.CONFIRM_DISSOLUTION_CLAN:
                        switch (select) {
                            case 0:
                                Clan clan = player.clan;
                                clan.deleteDB(clan.id);
                                Manager.CLANS.remove(clan);
                                player.clan = null;
                                player.clanMember = null;
                                ClanService.gI().sendMyClan(player);
                                ClanService.gI().sendClanId(player);
                                Service.gI().sendThongBao(player, "Đã giải tán bang hội.");
                                break;
                        }
                        break;
//                    case ConstNpc.CONFIRM_ACTIVE:
//                        switch (select) {
//                            case 0:
//                                if (player.getSession().goldBar >= 20) {
//                                    player.getSession().actived = true;
//                                    if (PlayerDAO.subGoldBar(player, 20)) {
//                                        Service.gI().sendThongBao(player, "Đã mở thành viên thành công!");
//                                        break;
//                                    } else {
//                                        this.npcChat(player, "Lỗi vui lòng báo admin...");
//                                    }
//                                }
////                                Service.gI().sendThongBao(player, "Bạn không có vàng\n Vui lòng NROGOD.COM để nạp thỏi vàng");
//                                break;
//                        }
//                        break;
                    case ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND:
                        if (select == 0) {
                            for (int i = 0; i < player.inventory.itemsBoxCrackBall.size(); i++) {
                                player.inventory.itemsBoxCrackBall.set(i, ItemService.gI().createItemNull());
                            }
                            player.inventory.itemsBoxCrackBall.clear();
                            Service.gI().sendThongBao(player, "Đã xóa hết vật phẩm trong rương");
                        }
                        break;
                    case ConstNpc.MENU_FIND_PLAYER:
                        Player p = (Player) PLAYERID_OBJECT.get(player.id);
                        if (p != null) {
                            switch (select) {
                                case 0:
                                    if (p.zone != null) {
                                        ChangeMapService.gI().changeMapYardrat(player, p.zone, p.location.x, p.location.y);
                                    }
                                    break;
                                case 1:
                                    if (p.zone != null) {
                                        ChangeMapService.gI().changeMap(p, player.zone, player.location.x, player.location.y);
                                    }
                                    break;
                                case 2:
                                    Input.gI().createFormChangeName(player, p);
                                    break;
                                case 3:
                                    String[] selects = new String[]{"Đồng ý", "Hủy"};
                                    NpcService.gI().createMenuConMeo(player, ConstNpc.BAN_PLAYER, -1,
                                            "Bạn có chắc chắn muốn ban " + p.name, selects, p);
                                    break;
                                case 4:
                                    Service.getInstance().sendThongBao(player, "Kich người chơi " + p.name + " thành công");
                                    Client.gI().getPlayers().remove(p);
                                    Client.gI().kickSession(p.getSession());
                                    break;
                                case 5:
                                    String[] selectss = new String[]{"Đồng ý", "Hủy"};
                                    NpcService.gI().createMenuConMeo(player, ConstNpc.ACTIVE_PLAYER, -1,
                                            "Mở Thành Viên Cho " + p.name + " ?", selectss, p);
                                    break;
                            }
                        }
                    case ConstNpc.MENU_EVENT:
                        switch (select) {
                            case 0:
                                Service.gI().sendThongBaoOK(player, "Điểm sự kiện: " + player.inventory.event + " ngon ngon...");
                                break;
                            case 1:
                                Service.gI().showListTop(player, Manager.topSK);
                                break;
                            case 2:
                                Service.gI().sendThongBao(player, "Sự kiện đã kết thúc...");
//                                NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_GIAO_BONG, -1, "Người muốn giao bao nhiêu bông...",
//                                        "100 bông", "1000 bông", "10000 bông");
                                break;
                            case 3:
                                Service.gI().sendThongBao(player, "Sự kiện đã kết thúc...");
//                                NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN, -1, "Con có thực sự muốn đổi thưởng?\nPhải giao cho ta 3000 điểm sự kiện đấy... ",
//                                        "Đồng ý", "Từ chối");
                                break;

                        }
                        break;
//                    case ConstNpc.MENU_GIAO_BONG:
//                        ItemService.gI().giaobong(player, (int) Util.tinhLuyThua(10, select + 2));
//                        break;
                    case ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN:
                        if (select == 0) {
                            ItemService.gI().openBoxVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_DOI_DIEM_DUA:
                        if (select == 0) {
                            ItemService.gI().openBoxCongThuc(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_DOI_DIEM_ITEMC2:
                        if (select == 0) {
                            ItemService.gI().openBoxItemc2(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_DOI_ITEM_NR:
                        if (select == 0) {
                            ItemService.gI().openBoxItemNR(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_DOI_DIEM_CT:
                        if (select == 0) {
                            ItemService.gI().openBoxCt(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_TELE_NAMEC:
                        if (select == 0) {
                            NgocRongNamecService.gI().teleportToNrNamec(player);
                            player.inventory.subGemAndRuby(50);
                            Service.gI().sendMoney(player);
                        }
                        break;
                }
            }
        };
    }
}
