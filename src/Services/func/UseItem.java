package Services.func;

import Services.RewardService;
import Services.NpcService;
import Services.Service;
import Services.MapService;
import Services.ItemTimeService;
import Services.NgocRongNamecService;
import Services.TaskService;
import Services.InventoryServiceNew;
import Services.PlayerService;
import Services.PetService;
import Services.ItemService;
import Card.Card;
import Card.RadarCard;
import Card.RadarService;
import Consts.ConstMap;
import Item.Item;
import Consts.ConstNpc;
import Consts.ConstPlayer;
import Consts.ConstTask;
import BossMain.BossManager;
import Item.Item.ItemOption;
import Maps.Zone;
import Player.Inventory;
import static Player.Inventory.LIMIT_GOLD;
import Player.Player;
import Skill.Skill;
import network.Message;
import Utils.SkillUtil;
import Utils.TimeUtil;
import Utils.Util;
import Server.MySession;
import Utils.Logger;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UseItem {

    private static final int ITEM_BOX_TO_BODY_OR_BAG = 0;
    private static final int ITEM_BAG_TO_BOX = 1;
    private static final int ITEM_BODY_TO_BOX = 3;
    private static final int ITEM_BAG_TO_BODY = 4;
    private static final int ITEM_BODY_TO_BAG = 5;
    private static final int ITEM_BAG_TO_PET_BODY = 6;
    private static final int ITEM_BODY_PET_TO_BAG = 7;

    private static final byte DO_USE_ITEM = 0;
    private static final byte DO_THROW_ITEM = 1;
    private static final byte ACCEPT_THROW_ITEM = 2;
    private static final byte ACCEPT_USE_ITEM = 3;
    public static final int[][][] LIST_ITEM_CLOTHES = {
        // áo , quần , găng ,giày,rada
        //td -> nm -> xd
        {{0, 33, 3, 34, 136, 137, 138, 139, 230, 231, 232, 233, 555}, {6, 35, 9, 36, 140, 141, 142, 143, 242, 243, 244, 245, 556}, {21, 24, 37, 38, 144, 145, 146, 147, 254, 255, 256, 257, 562}, {27, 30, 39, 40, 148, 149, 150, 151, 266, 267, 268, 269, 563}, {12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281, 561}},
        {{1, 41, 4, 42, 152, 153, 154, 155, 234, 235, 236, 237, 557}, {7, 43, 10, 44, 156, 157, 158, 159, 246, 247, 248, 249, 558}, {22, 46, 25, 45, 160, 161, 162, 163, 258, 259, 260, 261, 564}, {28, 47, 31, 48, 164, 165, 166, 167, 270, 271, 272, 273, 565}, {12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281, 561}},
        {{2, 49, 5, 50, 168, 169, 170, 171, 238, 239, 240, 241, 559}, {8, 51, 11, 52, 172, 173, 174, 175, 250, 251, 252, 253, 560}, {23, 53, 26, 54, 176, 177, 178, 179, 262, 263, 264, 265, 566}, {29, 55, 32, 56, 180, 181, 182, 183, 274, 275, 276, 277, 567}, {12, 57, 58, 59, 184, 185, 186, 187, 278, 279, 280, 281, 561}}
    };

    private static UseItem instance;

    private UseItem() {

    }

    public static UseItem gI() {
        if (instance == null) {
            instance = new UseItem();
        }
        return instance;
    }

    public void getItem(MySession session, Message msg) {
        Player player = session.player;

        TransactionService.gI().cancelTrade(player);
        try {
            int type = msg.reader().readByte();
            int index = msg.reader().readByte();
            if (index == -1) {
                return;
            }
            switch (type) {
                case ITEM_BOX_TO_BODY_OR_BAG:
                    InventoryServiceNew.gI().itemBoxToBodyOrBag(player, index);
                    TaskService.gI().checkDoneTaskGetItemBox(player);
                    break;
                case ITEM_BAG_TO_BOX:
                    InventoryServiceNew.gI().itemBagToBox(player, index);
                    break;
                case ITEM_BODY_TO_BOX:
                    InventoryServiceNew.gI().itemBodyToBox(player, index);
                    break;
                case ITEM_BAG_TO_BODY:
                    InventoryServiceNew.gI().itemBagToBody(player, index);
                    break;
                case ITEM_BODY_TO_BAG:
                    InventoryServiceNew.gI().itemBodyToBag(player, index);
                    break;
                case ITEM_BAG_TO_PET_BODY:
                    InventoryServiceNew.gI().itemBagToPetBody(player, index);
                    break;
                case ITEM_BODY_PET_TO_BAG:
                    InventoryServiceNew.gI().itemPetBodyToBag(player, index);
                    break;
            }
            player.setClothes.setup();
            if (player.pet != null) {
                player.pet.setClothes.setup();
            }
            player.setClanMember();
            Service.gI().point(player);
        } catch (Exception e) {
            Logger.logException(UseItem.class, e);

        }
    }

    public void testItem(Player player, Message _msg) {
        TransactionService.gI().cancelTrade(player);
        Message msg;
        try {
            byte type = _msg.reader().readByte();
            int where = _msg.reader().readByte();
            int index = _msg.reader().readByte();
            System.out.println("type: " + type);
            System.out.println("where: " + where);
            System.out.println("index: " + index);
        } catch (Exception e) {
            Logger.logException(UseItem.class, e);
        }
    }

    public void doItem(Player player, Message _msg) {
        TransactionService.gI().cancelTrade(player);
        Message msg;
        byte type;
        try {
            type = _msg.reader().readByte();
            int where = _msg.reader().readByte();
            int index = _msg.reader().readByte();
//            System.out.println(type + " " + where + " " + index);
            switch (type) {
                case DO_USE_ITEM:
                    if (player != null && player.inventory != null) {
                        if (index != -1) {
                            Item item = player.inventory.itemsBag.get(index);
                            if (item.isNotNullItem()) {
                                if (item.template.type == 7) {
                                    msg = new Message(-43);
                                    msg.writer().writeByte(type);
                                    msg.writer().writeByte(where);
                                    msg.writer().writeByte(index);
                                    msg.writer().writeUTF("Bạn chắc chắn học " + player.inventory.itemsBag.get(index).template.name + "?");
                                    player.sendMessage(msg);
                                } else if (item.template.id == 570) {
                                    msg = new Message(-43);
                                    msg.writer().writeByte(type);
                                    msg.writer().writeByte(where);
                                    msg.writer().writeByte(index);
                                    msg.writer().writeUTF("Bạn chắc muốn mở\n" + player.inventory.itemsBag.get(index).template.name + " ?");
                                    player.sendMessage(msg);
                                } else if (item.template.type == 22) {
                                    if (player.zone.items.stream().filter(it -> it != null && it.itemTemplate.type == 22).count() > 2) {
                                        Service.gI().sendThongBaoOK(player, "Mỗi map chỉ đặt được 3 Vệ Tinh");
                                        return;
                                    }
                                    msg = new Message(-43);
                                    msg.writer().writeByte(type);
                                    msg.writer().writeByte(where);
                                    msg.writer().writeByte(index);
                                    msg.writer().writeUTF("Bạn chắc muốn dùng\n" + player.inventory.itemsBag.get(index).template.name + " ?");
                                    player.sendMessage(msg);
                                } else {
                                    UseItem.gI().useItem(player, item, index);
                                }
                            }
                        } else {
                            this.eatPea(player);
                        }
                    }
                    break;
                case DO_THROW_ITEM:
                    if (!(player.zone.map.mapId == 21 || player.zone.map.mapId == 22 || player.zone.map.mapId == 23)) {
                        Item item = null;
                        if (where == 0) {
                            item = player.inventory.itemsBody.get(index);
                        } else {
                            item = player.inventory.itemsBag.get(index);
                        }
                        if (item.isNotNullItem() && item.template.id == 570) {
                            Service.gI().sendThongBao(player, "Không thể bỏ vật phẩm này.");
                            return;
                        }
                        msg = new Message(-43);
                        msg.writer().writeByte(type);
                        msg.writer().writeByte(where);
                        msg.writer().writeByte(index);
                        msg.writer().writeUTF("Bạn chắc chắn muốn vứt " + item.template.name + "?");
                        player.sendMessage(msg);
                    } else {
                        Service.gI().sendThongBao(player, "Không thể thực hiện");
                    }
                    break;
                case ACCEPT_THROW_ITEM:
                    InventoryServiceNew.gI().throwItem(player, where, index);
                    Service.gI().point(player);
                    InventoryServiceNew.gI().sendItemBags(player);
                    break;
                case ACCEPT_USE_ITEM:
                    UseItem.gI().useItem(player, player.inventory.itemsBag.get(index), index);
                    break;
            }
        } catch (Exception e) {
//            Logger.logException(UseItem.class, e);
        }
    }

    private void useItem(Player pl, Item item, int indexBag) {
        if (item.template.id == 570) {
            int time = (int) TimeUtil.diffDate(new Date(), new Date(item.createTime), TimeUtil.DAY);
            if (time == 0) {
                Service.gI().sendThongBao(pl, "Hãy chờ đến ngày mai");
            } else {
                openRuongGo(pl, item);
            }
            return;
        }
        if (item.template.strRequire <= pl.nPoint.power) {
            switch (item.template.type) {
                case 21:
                    if (pl.newpet != null) {
                        ChangeMapService.gI().exitMap(pl.newpet);
                        pl.newpet.dispose();
                        pl.newpet = null;
                    }
                    InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                    PetService.Pet2(pl, item.template.head, item.template.body, item.template.leg);
                    Service.getInstance().point(pl);
                    break;
                case 7: //sách học, nâng skill
                    learnSkill(pl, item);
                    break;
                case 33:
                    UseCard(pl, item);
                    break;
                case 6: //đậu thần
                    this.eatPea(pl);
                    break;
                case 12: //ngọc rồng các loại
                    controllerCallRongThan(pl, item);
//                    controllerCalltrb(pl, item);
                    break;
                case 23: //thú cưỡi mới
                case 24: //thú cưỡi cũ
                    InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                    break;
                case 11: //item bag
                    InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                    Service.gI().sendFlagBag(pl);
                    break;
                case 74:
                    InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                    Service.gI().sendFoot(pl, item.template.id);
                    break;
                case 77:
                    InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
                    break;
//                case 72: {
//                    InventoryServiceNew.gI().itemBagToBody(pl, indexBag);
//                    Service.gI().sendPetFollow(pl, (short) (item.template.iconID - 1));
//                    break;
//                }
                default:
                    switch (item.template.id) {
                        case 992:
                            pl.type = 1;
                            pl.maxTime = 5;
                            Service.gI().Transport(pl);
                            break;
                        case 361:
                            if (pl.idNRNM != -1) {
                                Service.gI().sendThongBao(pl, "Không thể thực hiện");
                                return;
                            }
                            pl.idGo = (short) Util.nextInt(0, 6);
                            NpcService.gI().createMenuConMeo(pl, ConstNpc.CONFIRM_TELE_NAMEC, -1, "1 Sao (" + NgocRongNamecService.gI().getDis(pl, 0, (short) 353) + " m)\n2 Sao (" + NgocRongNamecService.gI().getDis(pl, 1, (short) 354) + " m)\n3 Sao (" + NgocRongNamecService.gI().getDis(pl, 2, (short) 355) + " m)\n4 Sao (" + NgocRongNamecService.gI().getDis(pl, 3, (short) 356) + " m)\n5 Sao (" + NgocRongNamecService.gI().getDis(pl, 4, (short) 357) + " m)\n6 Sao (" + NgocRongNamecService.gI().getDis(pl, 5, (short) 358) + " m)\n7 Sao (" + NgocRongNamecService.gI().getDis(pl, 6, (short) 359) + " m)", "Đến ngay\nViên " + (pl.idGo + 1) + " Sao\n50 ngọc", "Kết thức");
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            InventoryServiceNew.gI().sendItemBags(pl);
                            break;
                        case 211: //nho tím
                        case 212: //nho xanh
                            eatGrapes(pl, item);
                            break;
                        case 1105://hop qua skh, item 2002 xd
                            UseItem.gI().Hopts(pl, item);
                            break;
                        case 342:
                        case 343:
                        case 344:
                        case 345:
                            if (pl.zone.items.stream().filter(it -> it != null && it.itemTemplate.type == 22).count() < 3) {
                                Service.gI().dropSatellite(pl, item, pl.zone, pl.location.x, pl.location.y);
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            } else {
                                Service.gI().sendThongBaoOK(pl, "Mỗi map chỉ đặt được 3 Vệ Tinh");
                            }
                            break;
                        case 460:
                            if (pl.zone.items.stream().filter(it -> it != null && it.itemTemplate.type == 27).count() < 3) {
                                Service.gI().dropSatellite(pl, item, pl.zone, pl.location.x, pl.location.y);
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            } else {
                                Service.gI().sendThongBaoOK(pl, "Mỗi map chỉ đặt được 3 Vệ Tinh");
                            }
                            break;
                        case 380: //cskb
                            openCSKB(pl, item);
                            break;
                        case 1170: //Máy dò BOSS
                            maydoboss(pl);
                            break;
                        case 1460: //Next Nhiệm vụ
                            nextNhiemVu(pl);
                            break;
                        case 1488:
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            Service.gI().addEffectChar(pl, 29, 1, -1, -1, 0);
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Service.gI().removeTitle(pl);
                                }
                            }, 5000);
                            break;
                        case 1470:
                            hopDiemThongNhat(pl, item);
                            break;
                        case 381: //cuồng nộ
                        case 382: //bổ huyết
                        case 383: //bổ khí
                        case 384: //giáp xên
                        case 385: //ẩn danh
                        case 379: //máy dò capsule
                        case 2037: //máy dò cosmos
                        case 2105: //máy dò cosmos
                        case 663: //bánh pudding
                        case 664: //xúc xíc
                        case 665: //kem dâu
                        case 666: //mì ly
                        case 667: //sushi
                        case 579: //đuôi khỉ
                        case 1099:
                        case 1100:
                        case 1101:
                        case 1102:
                        case 1103:
                        case 1484:
                        case 1426:
                            useItemTime(pl, item);
                            break;
                        case 1489:
                            NpcService.gI().createMenuConMeo(pl, ConstNpc.HOP_QUA_THAN_LINH, -1,
                                    "Chọn hành tinh của đồ thần linh muốn nhận.",
                                    "Trái đất", "Namek", "Xayda");
                            break;
                        case 1419:
                            if (pl.lastTimeTitle1 == 0) {
                                pl.lastTimeTitle1 += System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 20);
                            } else {
                                pl.lastTimeTitle1 += (1000 * 60 * 60 * 24 * 20);
                            }
                            pl.isTitleUse1 = true;
                            Service.gI().point(pl);
                            Service.gI().sendTitle(pl, 1);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            Service.gI().sendThongBao(pl, "Bạn nhận được 20); ngày danh hiệu !");
                            break;
                        case 1420:
                            if (pl.lastTimeTitle2 == 0) {
                                pl.lastTimeTitle2 += System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
                            } else {
                                pl.lastTimeTitle2 += (1000 * 60 * 60 * 24 * 30);
                            }
                            pl.isTitleUse2 = true;
                            Service.gI().point(pl);
                            Service.gI().sendTitle(pl, 2);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            Service.gI().sendThongBao(pl, "Bạn nhận được 30 ngày danh hiệu !");
                            break;
                        case 1421:
                            if (pl.lastTimeTitle3 == 0) {
                                pl.lastTimeTitle3 += System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
                            } else {
                                pl.lastTimeTitle3 += (1000 * 60 * 60 * 24 * 30);
                            }
                            pl.isTitleUse3 = true;
                            Service.gI().point(pl);
                            Service.gI().sendTitle(pl, 3);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            Service.gI().sendThongBao(pl, "Bạn nhận được 30 ngày danh hiệu !");
                            break;
                        case 1422:
                            if (pl.lastTimeTitle4 == 0) {
                                pl.lastTimeTitle4 += System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
                            } else {
                                pl.lastTimeTitle4 += (1000 * 60 * 60 * 24 * 30);
                            }
                            pl.isTitleUse4 = true;
                            Service.gI().point(pl);
                            Service.gI().sendTitle(pl, 4);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            Service.gI().sendThongBao(pl, "Bạn nhận được 30 ngày danh hiệu !");
                            break;
                        case 1423:
                            if (pl.lastTimeTitle5 == 0) {
                                pl.lastTimeTitle5 += System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
                            } else {
                                pl.lastTimeTitle5 += (1000 * 60 * 60 * 24 * 30);
                            }
                            pl.isTitleUse5 = true;
                            Service.gI().point(pl);
                            Service.gI().sendTitle(pl, 5);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            Service.gI().sendThongBao(pl, "Bạn nhận được 30 ngày danh hiệu !");
                            break;
                        case 1424:
                            if (pl.lastTimeTitle6 == 0) {
                                pl.lastTimeTitle6 += System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 30);
                            } else {
                                pl.lastTimeTitle6 += (1000 * 60 * 60 * 24 * 30);
                            }
                            pl.isTitleUse6 = true;
                            Service.gI().point(pl);
                            Service.gI().sendTitle(pl, 6);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            Service.gI().sendThongBao(pl, "Bạn nhận được 30 ngày danh hiệu !");
                            break;
                        case 1425:
                            if (pl.lastTimeTitle7 == 0) {
                                pl.lastTimeTitle7 += System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 20);
                            } else {
                                pl.lastTimeTitle7 += (1000 * 60 * 60 * 24 * 20);
                            }
                            pl.isTitleUse7 = true;
                            Service.gI().point(pl);
                            Service.gI().sendTitle(pl, 7);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            Service.gI().sendThongBao(pl, "Bạn nhận được 20 ngày danh hiệu !");
                            break;
                        case 1483:
                            hopquaShare(pl, item);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            break;
                        case 648:
                            hopQuaNoel(pl, item);
                            break;
                        case 1485:
                            caitrang5Ngay(pl, item);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            break;
                        case 1486:
                            caitrang7Ngay(pl, item);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            break;
                        case 521: //tdlt
                            useTDLT(pl, item);
                            break;
                        case 454: //bông tai
                            UseItem.gI().usePorata(pl);
                            break;
                        case 457:
                            openThoiVangMAX(pl, item);
                            break;
                        case 193: //gói 10 viên capsule
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                        case 194: //capsule đặc biệt
                            openCapsuleUI(pl);
                            break;
                        case 401: //đổi đệ tử
                            changePet(pl, item);
                            break;
                        case 1108: //đổi đệ tử
                            changePetBerus(pl, item);
                            break;
                        case 722: //đổi đệ tử
                            changePetPic(pl, item);
                            break;
                        case 402: //sách nâng chiêu 1 đệ tử
                        case 403: //sách nâng chiêu 2 đệ tử
                        case 404: //sách nâng chiêu 3 đệ tử
                        case 759: //sách nâng chiêu 4 đệ tử
                            upSkillPet(pl, item);
                            break;
                        case 921://bông tai
                            pl.fusion.isBTC2 = item.template.id == 921;
                            UseItem.gI().usePorata2(pl);
                            break;
                        case 1228: //bông tai c3
                            UseItem.gI().usePorata3(pl);
                            break;
                        case 1229: //bông tai c4
                            UseItem.gI().usePorata4(pl);
                            break;
                        case 2000://hop qua skh, item 2000 td
                        case 2001://hop qua skh, item 2001 nm
                        case 2002://hop qua skh, item 2002 xd
                            UseItem.gI().ItemSKH(pl, item);
                            break;

                        case 2003://hop qua skh, item 2003 td
                        case 2004://hop qua skh, item 2004 nm
                        case 2005://hop qua skh, item 2005 xd
                            UseItem.gI().ItemDHD(pl, item);
                            break;
                        case 736:
                            ItemService.gI().OpenItem736(pl, item);
                            break;
                        case 987:
                            Service.gI().sendThongBao(pl, "Bảo vệ trang bị không bị rớt cấp"); //đá bảo vệ
                            break;
                        case 1120:
                            useItemHopQuaTanThu(pl, item);
                            break;
                        case 569:
                            useItemQuaDua(pl, item);
                            break;
                        case 2006:
                            Input.gI().createFormChangeNameByItem(pl);
                            break;
                        case 1131:
                            if (pl.pet == null) {
                                Service.gI().sendThongBao(pl, "Ngươi làm gì có đệ tử?");
                                break;
                            }
                            if (pl.pet.playerSkill.skills.get(1).skillId != -1 && pl.pet.playerSkill.skills.get(2).skillId != -1) {
                                pl.pet.openSkill2();
                                pl.pet.openSkill3();
                                InventoryServiceNew.gI().subQuantityItem(pl.inventory.itemsBag, item, 1);
                                InventoryServiceNew.gI().sendItemBags(pl);
                                Service.gI().sendThongBao(pl, "Đã đổi thành công chiêu 2 3 đệ tử");
                            } else {
                                Service.gI().sendThongBao(pl, "Ít nhất đệ tử ngươi phải có chiêu 2 chứ!");
                            }
                            break;
                        case 1490:
                            if (pl.pet == null) {
                                Service.gI().sendThongBao(pl, "Ngươi làm gì có đệ tử?");
                                break;
                            }
                            if (pl.pet.typePet == 2) {
                                if (pl.pet.playerSkill.skills.get(1).skillId != -1) {
                                    pl.pet.openSkillKame();
                                    InventoryServiceNew.gI().subQuantityItem(pl.inventory.itemsBag, item, 1);
                                    InventoryServiceNew.gI().sendItemBags(pl);
                                    Service.gI().sendThongBao(pl, "Đã đổi thành công chiêu 2 đệ tử");
                                } else {
                                    Service.gI().sendThongBao(pl, "Ít nhất đệ tử ngươi phải có chiêu 2 chứ!");
                                }
                            } else {
                                Service.gI().sendThongBao(pl, "Bạn Cần Có Đệ Berus");
                            }
                            break;
                        case 2027:
                        case 2028: {
                            if (InventoryServiceNew.gI().getCountEmptyBag(pl) == 0) {
                                Service.gI().sendThongBao(pl, "Hành trang không đủ chỗ trống");
                            } else {
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                                Item linhThu = ItemService.gI().createNewItem((short) Util.nextInt(2019, 2026));
                                linhThu.itemOptions.add(new Item.ItemOption(50, 10));
                                linhThu.itemOptions.add(new Item.ItemOption(77, 5));
                                linhThu.itemOptions.add(new Item.ItemOption(103, 5));
                                linhThu.itemOptions.add(new Item.ItemOption(95, 3));
                                linhThu.itemOptions.add(new Item.ItemOption(96, 3));
                                InventoryServiceNew.gI().addItemBag(pl, linhThu);
                                InventoryServiceNew.gI().sendItemBags(pl);
                                Service.gI().sendThongBao(pl, "Chúc mừng bạn nhận được Linh thú " + linhThu.template.name);
                            }
                            break;

                        }
                        //khaile add
                        case 1716://sukien he bai bien
                            ruongkhobau(pl, item);
                            break;

                        //khaile add
                        case 1698: { // linh tuu
                            long amount = 5_000_000_000L;
                            pl.nPoint.powerUp(amount);
                            pl.nPoint.tiemNangUp(amount);
                            PlayerService.gI().sendTNSM(pl, (byte) 2, amount);
                            Service.gI().sendThongBao(pl, "Bạn nhận 5 tỷ tu vi");
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            InventoryServiceNew.gI().sendItemBags(pl);
                            break;
                        }
                        case 1668: // Hoàng Cực Đan
                        {
                            int requiredCap = 1;

                            // Kiểm tra cảnh giới
                            if (pl.capTT != requiredCap) {
                                if (pl.capTT > requiredCap) {
                                    Service.gI().sendThongBao(pl, "Cảnh giới vượt mức Luyện Khí, không thể sử dụng Hoàng Cực Đan");
                                } else {
                                    Service.gI().sendThongBao(pl, "Cảnh giới chưa đạt yêu cầu để sử dụng Hoàng Cực Đan");
                                }
                                break;
                            }

                            // Thiết lập giá trị tăng và giới hạn
                            int damegIncrease = 10_000;
                            int hpMpIncrease = damegIncrease * 3;

                            int maxDameg = 3_600_000;
                            int maxHpMp = maxDameg * 3;

                            // Kiểm tra từng thuộc tính đã đạt max hay chưa
                            boolean isDamegMaxed = (pl.nPoint.dameg >= maxDameg);
                            boolean isHpMaxed = (pl.nPoint.hpg >= maxHpMp);
                            boolean isMpMaxed = (pl.nPoint.mpg >= maxHpMp);

                            // Nếu tất cả đều max thì không thể sử dụng
                            if (isDamegMaxed && isHpMaxed && isMpMaxed) {
                                Service.gI().sendThongBao(pl, "Đã đạt giới hạn tối đa, không thể sử dụng Hoàng Cực Đan");
                                break;
                            }

                            // Tăng từng thuộc tính nếu chưa đạt max
                            StringBuilder bonusMessage = new StringBuilder("Bạn nhận được: ");
                            boolean receivedBonus = false;

                            if (!isDamegMaxed) {
                                long oldDameg = pl.nPoint.dameg;
                                pl.nPoint.dameg = Math.min(pl.nPoint.dameg + damegIncrease, maxDameg);
                                if (pl.nPoint.dameg > oldDameg) {
                                    bonusMessage.append(String.format("SD +%d, ", damegIncrease));
                                    receivedBonus = true;
                                }
                            }

                            if (!isHpMaxed) {
                                long oldHp = pl.nPoint.hpg;
                                pl.nPoint.hpg = Math.min(pl.nPoint.hpg + hpMpIncrease, maxHpMp);
                                if (pl.nPoint.hpg > oldHp) {
                                    bonusMessage.append(String.format("HP +%d, ", hpMpIncrease));
                                    receivedBonus = true;
                                }
                            }

                            if (!isMpMaxed) {
                                long oldMp = pl.nPoint.mpg;
                                pl.nPoint.mpg = Math.min(pl.nPoint.mpg + hpMpIncrease, maxHpMp);
                                if (pl.nPoint.mpg > oldMp) {
                                    bonusMessage.append(String.format("KI +%d, ", hpMpIncrease));
                                    receivedBonus = true;
                                }
                            }

                            // Nếu có thuộc tính được tăng thì cập nhật
                            if (receivedBonus) {
                                // Xóa dấu ", " cuối cùng
                                if (bonusMessage.length() > 0) {
                                    bonusMessage.setLength(bonusMessage.length() - 2);
                                }
                                Service.gI().point(pl); // Cập nhật chỉ số
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1); // Trừ item
                                InventoryServiceNew.gI().sendItemBags(pl); // Gửi lại túi đồ
                                Service.gI().sendThongBao(pl, bonusMessage.toString()); // Thông báo chi tiết
                            } else {
                                Service.gI().sendThongBao(pl, "Không thể tăng thêm thuộc tính do đã đạt giới hạn");
                            }
                            break;
                        }
                        case 1667: // Trúc Cơ Đan
                        {
                            int requiredCap = 1;
                            int requiredBinhCanh = 3;

                            // Kiểm tra cảnh giới và bình cảnh
                            if (pl.capTT != requiredCap || pl.capCS != requiredBinhCanh) {
                                if (pl.capTT < requiredCap) {
                                    Service.gI().sendThongBao(pl, "Cảnh giới chưa đạt yêu cầu để sử dụng");
                                } else if (pl.capTT > requiredCap) {
                                    Service.gI().sendThongBao(pl, "Cảnh giới vượt mức yêu cầu để sử dụng");
                                } else if (pl.capCS != requiredBinhCanh) {
                                    Service.gI().sendThongBao(pl, "Bình cảnh chưa đạt yêu cầu để sử dụng");
                                }
                                break;
                            }

                            // Thiết lập giá trị
                            int requiredDame = 3_600_000;
                            int requiredHpKi = requiredDame * 3;

                            int damegIncrease = 1_000_000;
                            int hpMpIncrease = damegIncrease * 3;

                            int maxDameg = 4_600_000;
                            int maxHpMp = maxDameg * 3;

                            // Kiểm tra điều kiện
                            boolean atExactThreshold = (pl.nPoint.dameg == requiredDame)
                                    && (pl.nPoint.hpg == requiredHpKi)
                                    && (pl.nPoint.mpg == requiredHpKi);

                            boolean alreadyMaxed = (pl.nPoint.dameg >= maxDameg)
                                    || (pl.nPoint.hpg >= maxHpMp)
                                    || (pl.nPoint.mpg >= maxHpMp);

                            boolean notEligible = (pl.nPoint.dameg < requiredDame)
                                    || (pl.nPoint.hpg < requiredHpKi)
                                    || (pl.nPoint.mpg < requiredHpKi);

                            // Xử lý logic
                            if (notEligible) {
                                Service.gI().sendThongBao(pl, "Chưa xong luyện khí mà đòi trúc cơ à mày !!!");
                                break;
                            }
                            if (alreadyMaxed) {
                                Service.gI().sendThongBao(pl, "Đã đạt giới hạn, không thể sử dụng");
                                break;
                            }
                            if (!atExactThreshold) {
                                Service.gI().sendThongBao(pl, "Cần đạt chính xác " + requiredDame + " SD và " + requiredHpKi + " HP/KI");
                                break;
                            }

                            // Tăng chỉ số
                            pl.nPoint.dameg = Math.min(pl.nPoint.dameg + damegIncrease, maxDameg);
                            pl.nPoint.hpg = Math.min(pl.nPoint.hpg + hpMpIncrease, maxHpMp);
                            pl.nPoint.mpg = Math.min(pl.nPoint.mpg + hpMpIncrease, maxHpMp);

                            // Đánh dấu đã dùng Trúc Cơ Đan
                            pl.isUseTrucCoDan = true;

                            // Cập nhật
                            Service.gI().point(pl);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            InventoryServiceNew.gI().sendItemBags(pl);
                            Service.gI().sendThongBao(pl, "Đột phá Trúc Cơ thành công! Nhận được: SD +1tr, HP/KI +3tr");
                            break;
                        }
                        case 1664:// truc co so ky
                        {
                            int requiredCap = 2;
                            int requiredBinhCanh = 0;
                            if (pl.capTT != requiredCap || pl.capCS != requiredBinhCanh) {
                                if (pl.capTT < requiredCap) {
                                    Service.gI().sendThongBao(pl, "Cảnh giới chưa đạt yêu cầu để sử dụng Long Tủy Đan");
                                } else if (pl.capTT > requiredCap) {
                                    Service.gI().sendThongBao(pl, "Cảnh giới vượt mức yêu cầu để sử dụng Long Tủy Đan");
                                } else if (pl.capCS != requiredBinhCanh) {
                                    Service.gI().sendThongBao(pl, "Bình cảnh chưa đạt yêu cầu để sử dụng Long Tủy Đan");
                                }
                                break;
                            }
                            if (pl.capTT == requiredCap && pl.capCS == requiredBinhCanh) {
                                int requiredDame = 3_600_000;
                                int requiredHpKi = requiredDame * 3;

                                int damegIncrease = 50_000;
                                int hpMpIncrease = damegIncrease * 3;

                                int maxDameg;
                                int maxHpMp;
                                if (pl.isUseTrucCoDan) {
                                    maxDameg = 7_000_000;
                                    maxHpMp = maxDameg * 3;
                                } else {
                                    maxDameg = 6_000_000;
                                    maxHpMp = maxDameg * 3;
                                }
                                boolean notEligible = pl.nPoint.dameg < requiredDame
                                        || pl.nPoint.hpg < requiredHpKi
                                        || pl.nPoint.mpg < requiredHpKi;

                                if (notEligible) {
                                    Service.gI().sendThongBao(pl, "Éo dùng được đâu em, lo mà về tu luyện tiếp !!!");
                                    break;
                                }
                                // Kiểm tra nếu đã vượt ngưỡng yêu cầu
                                boolean alreadyBeyond = pl.nPoint.dameg >= maxDameg
                                        || pl.nPoint.hpg >= maxHpMp
                                        || pl.nPoint.mpg >= maxHpMp;

                                if (alreadyBeyond) {
                                    Service.gI().sendThongBao(pl, "Đạt giới hạn Trúc Cơ sơ kỳ rồi em!! Đột phá bình cảnh đi !!!");
                                    break;
                                }
                                pl.nPoint.dameg = Math.min(pl.nPoint.dameg + damegIncrease, maxDameg);
                                pl.nPoint.hpg = Math.min(pl.nPoint.hpg + hpMpIncrease, maxHpMp);
                                pl.nPoint.mpg = Math.min(pl.nPoint.mpg + hpMpIncrease, maxHpMp);
                                Service.gI().point(pl);
                                Service.gI().sendThongBao(pl, "Bạn nhận được SD, HP và KI");
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                                InventoryServiceNew.gI().sendItemBags(pl);
                                break;
                            }
                        }
                        case 1665: {// truc co trung ky
                            int requiredCap = 2;
                            int requiredBinhCanh = 1;

                            if (pl.capTT != requiredCap || pl.capCS != requiredBinhCanh) {
                                if (pl.capTT < requiredCap) {
                                    Service.gI().sendThongBao(pl, "Cảnh giới chưa đạt yêu cầu để sử dụng Chân Nguyên Đan");
                                } else if (pl.capTT > requiredCap) {
                                    Service.gI().sendThongBao(pl, "Cảnh giới vượt mức yêu cầu để sử dụng Chân Nguyên Đan");
                                } else if (pl.capCS != requiredBinhCanh) {
                                    Service.gI().sendThongBao(pl, "Bình cảnh chưa đạt yêu cầu để sử dụng Chân Nguyên Đan");
                                }
                                break;
                            }
                            if (pl.capTT == requiredCap && pl.capCS == requiredBinhCanh) {
                                int requiredDame = 6_000_000;
                                int requiredHpKi = requiredDame * 3;

                                int damegIncrease = 50_000;
                                int hpMpIncrease = damegIncrease * 3;

                                int maxDameg;
                                int maxHpMp;
                                if (pl.isUseTrucCoDan) {
                                    maxDameg = 13_000_000;
                                    maxHpMp = maxDameg * 3;
                                } else {
                                    maxDameg = 12_000_000;
                                    maxHpMp = maxDameg * 3;
                                }

                                boolean notEligible = pl.nPoint.dameg < requiredDame
                                        || pl.nPoint.hpg < requiredHpKi
                                        || pl.nPoint.mpg < requiredHpKi;

                                if (notEligible) {
                                    Service.gI().sendThongBao(pl, "Éo dùng được đâu em, lo mà về tu luyện tiếp !!!");
                                    break;
                                }
                                // Kiểm tra nếu đã vượt ngưỡng yêu cầu
                                boolean alreadyBeyond = pl.nPoint.dameg >= maxDameg
                                        || pl.nPoint.hpg >= maxHpMp
                                        || pl.nPoint.mpg >= maxHpMp;

                                if (alreadyBeyond) {
                                    Service.gI().sendThongBao(pl, "Đạt giới hạn Trúc Cơ trung kỳ rồi em!! Đột phá bình cảnh đi !!!");
                                    break;
                                }
                                pl.nPoint.dameg = Math.min(pl.nPoint.dameg + damegIncrease, maxDameg);
                                pl.nPoint.hpg = Math.min(pl.nPoint.hpg + hpMpIncrease, maxHpMp);
                                pl.nPoint.mpg = Math.min(pl.nPoint.mpg + hpMpIncrease, maxHpMp);
                                Service.gI().point(pl);
                                Service.gI().sendThongBao(pl, "Bạn nhận được SD, HP và KI");
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                                InventoryServiceNew.gI().sendItemBags(pl);
                                break;
                            }
                        }
                        case 1666: {// truc co hau ky
                            int requiredCap = 2;
                            int requiredBinhCanh = 2;
                            if (pl.capTT != requiredCap || pl.capCS != requiredBinhCanh) {
                                if (pl.capTT < requiredCap) {
                                    Service.gI().sendThongBao(pl, "Cảnh giới chưa đạt yêu cầu để sử dụng Ngũ Hành Ngưng Đan");
                                } else if (pl.capTT > requiredCap) {
                                    Service.gI().sendThongBao(pl, "Cảnh giới vượt mức yêu cầu để sử dụng Ngũ Hành Ngưng Đan");
                                } else if (pl.capCS != requiredBinhCanh) {
                                    Service.gI().sendThongBao(pl, "Bình cảnh chưa đạt yêu cầu để sử dụng Ngũ Hành Ngưng Đan");
                                }
                                break;
                            }
                            if (pl.capTT == requiredCap && pl.capCS == requiredBinhCanh) {
                                int requiredDame = 12_000_000;
                                int requiredHpKi = requiredDame * 3;

                                int damegIncrease = 50_000;
                                int hpMpIncrease = damegIncrease * 3;

                                int maxDameg;
                                int maxHpMp;
                                if (pl.isUseTrucCoDan) {
                                    maxDameg = 19_000_000;
                                    maxHpMp = maxDameg * 3;
                                } else {
                                    maxDameg = 18_000_000;
                                    maxHpMp = maxDameg * 3;
                                }

                                boolean notEligible = pl.nPoint.dameg < requiredDame
                                        || pl.nPoint.hpg < requiredHpKi
                                        || pl.nPoint.mpg < requiredHpKi;

                                if (notEligible) {
                                    Service.gI().sendThongBao(pl, "Éo dùng được đâu em, lo mà về tu luyện tiếp !!!");
                                    return;
                                }
                                // Kiểm tra nếu đã vượt ngưỡng yêu cầu
                                boolean alreadyBeyond = pl.nPoint.dameg >= maxDameg
                                        || pl.nPoint.hpg >= maxHpMp
                                        || pl.nPoint.mpg >= maxHpMp;

                                if (alreadyBeyond) {
                                    Service.gI().sendThongBao(pl, "Đạt giới hạn Trúc Cơ hậu kỳ rồi em!! Đột phá bình cảnh đi !!!");
                                    return;
                                }
                                pl.nPoint.dameg = Math.min(pl.nPoint.dameg + damegIncrease, maxDameg);
                                pl.nPoint.hpg = Math.min(pl.nPoint.hpg + hpMpIncrease, maxHpMp);
                                pl.nPoint.mpg = Math.min(pl.nPoint.mpg + hpMpIncrease, maxHpMp);
                                Service.gI().point(pl);
                                Service.gI().sendThongBao(pl, "Bạn nhận được SD, HP và KI");
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                                InventoryServiceNew.gI().sendItemBags(pl);
                                break;
                            }
                        }
                        case 1708: {
                            if (pl.dotpha != 3 && !pl.isAdmin()) {
                                Service.gI().sendThongBaoOK(pl, "Có Bug không đó :3");
                                PlayerService.gI().banPlayer((pl));
                                Service.gI().sendThongBao(pl, "Bạn bị ban thành công");
                                return;
                            } else {
                                ChangeMapService.gI().changeMapBySpaceShip(pl, 222, -1, 552);
                            }
                            break;
                        }
                        case 1568: {
                            if (pl.pet == null) {
                                Service.gI().sendThongBaoOK(pl, "Có đệ mới dùng được");
                                break;
                            } else {
                                PetService.gI().createPet_Vip1(pl, pl.pet != null, pl.gender);
                                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                                InventoryServiceNew.gI().sendItemBags(pl);
                                break;
                            }

                        }
                        case 1569: {
                            Item newItem = ItemService.gI().createNewItem((short) 1275, 1);
                            newItem.itemOptions.add(new ItemOption(50, 30));
                            newItem.itemOptions.add(new ItemOption(77, 30));
                            newItem.itemOptions.add(new ItemOption(103, 30));
                            newItem.itemOptions.add(new ItemOption(30, 1));
                            InventoryServiceNew.gI().addItemBag(pl, newItem);
                            break;
                        }
                        case 1570: {
                            break;
                        }
                        case 1571: {
                            break;
                        }
                        case 1572: {
                            break;
                        }
                        case 1573: {
                            break;
                        }
                        //end khaile add
                    }
                    break;
            }
            InventoryServiceNew.gI().sendItemBags(pl);
        } else {
            Service.gI().sendThongBaoOK(pl, "Sức mạnh không đủ yêu cầu");
        }
    }

    private void ruongkhobau(Player player, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 1) {
            int[] vp = {1099,
                1100,
                1101,
                1102, 16};
            int[] vpVip = {1709, 1710, 1630, 1502};
            Item it = null;
            short[] icon = new short[2];
            icon[0] = item.template.iconID;
            if (Util.isTrue(90, 100)) {
                it = ItemService.gI().createNewItem((short) vp[Util.nextInt(0, vp.length - 1)]);
                it.quantity = 1;
            } else {
                it = ItemService.gI().createNewItem((short) vpVip[Util.nextInt(0, vpVip.length - 1)]);
                it.quantity = 1;
                it.itemOptions.add(new ItemOption(50, Util.nextInt(0, 70)));
                it.itemOptions.add(new ItemOption(77, Util.nextInt(0, 70)));
                it.itemOptions.add(new ItemOption(103, Util.nextInt(0, 70)));
                if (Util.isTrue(95, 100)) {
                    it.itemOptions.add(new ItemOption(93, Util.nextInt(1, 7)));
                }
            }
            InventoryServiceNew.gI().addItemBag(player, it);
            icon[1] = it.template.iconID;
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
            InventoryServiceNew.gI().sendItemBags(player);
            CombineServiceNew.gI().sendEffectOpenItem(player, icon[0], icon[1]);
            Service.gI().sendThongBao(player, "Chúc mừng bạn nhận được " + it.template.name);
        } else {
            Service.gI().sendThongBao(player, "Hàng trang đã đầy");
        }
    }

    public void openRuongGo(Player pl, Item item) {
        List<String> textRuongGo = new ArrayList<>();
        int time = (int) TimeUtil.diffDate(new Date(), new Date(item.createTime), TimeUtil.DAY);
        if (time != 0) {
            Item itemReward = null;
            int param = item.itemOptions.get(0).param;
            int gold = 0;
            int[] listItem = {441, 442, 443, 444, 445, 446, 447, 220, 221, 222, 223, 224, 225};
            int[] listClothesReward;
            int[] listItemReward;
            String text = "Bạn nhận được\n";
            if (param < 8) {
                gold = 100000 * param;
                listClothesReward = new int[]{randClothes(param)};
                listItemReward = Util.pickNRandInArr(listItem, 3);
            } else if (param < 10) {
                gold = 250000 * param;
                listClothesReward = new int[]{randClothes(param), randClothes(param)};
                listItemReward = Util.pickNRandInArr(listItem, 4);
            } else {
                gold = 500000 * param;
                listClothesReward = new int[]{randClothes(param), randClothes(param), randClothes(param)};
                listItemReward = Util.pickNRandInArr(listItem, 5);
                int ruby = Util.nextInt(1, 5);
                pl.inventory.ruby += ruby;
                textRuongGo.add(text + "|1| " + ruby + " Hồng Ngọc");
            }
            for (int i : listClothesReward) {
                itemReward = ItemService.gI().createNewItem((short) i);
                RewardService.gI().initBaseOptionClothes(itemReward.template.id, itemReward.template.type, itemReward.itemOptions);
                RewardService.gI().initStarOption(itemReward, new RewardService.RatioStar[]{new RewardService.RatioStar((byte) 1, 1, 2), new RewardService.RatioStar((byte) 2, 1, 3), new RewardService.RatioStar((byte) 3, 1, 4), new RewardService.RatioStar((byte) 4, 1, 5),});
                InventoryServiceNew.gI().addItemBag(pl, itemReward);
                textRuongGo.add(text + itemReward.info);
            }
            for (int i : listItemReward) {
                itemReward = ItemService.gI().createNewItem((short) i);
                RewardService.gI().initBaseOptionSaoPhaLe(itemReward);
                itemReward.quantity = Util.nextInt(1, 5);
                InventoryServiceNew.gI().addItemBag(pl, itemReward);
                textRuongGo.add(text + itemReward.info);
            }
            if (param == 11) {
                itemReward = ItemService.gI().createNewItem((short) 1069);
                itemReward.quantity = Util.nextInt(1, 3);
                InventoryServiceNew.gI().addItemBag(pl, itemReward);
                textRuongGo.add(text + itemReward.info);
            }
            NpcService.gI().createMenuConMeo(pl, ConstNpc.RUONG_GO, -1, "Bạn nhận được\n|1|+" + Util.numberToMoney(gold) + " vàng", "OK [" + textRuongGo.size() + "]");
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            pl.inventory.addGold(gold);
            InventoryServiceNew.gI().sendItemBags(pl);
            PlayerService.gI().sendInfoHpMpMoney(pl);
        }
    }

    private int randClothes(int level) {
        return LIST_ITEM_CLOTHES[Util.nextInt(0, 2)][Util.nextInt(0, 4)][level - 1];
    }

    public void UseCard(Player pl, Item item) {
        RadarCard radarTemplate = RadarService.gI().RADAR_TEMPLATE.stream().filter(c -> c.Id == item.template.id).findFirst().orElse(null);
        if (radarTemplate == null) {
            return;
        }
        if (radarTemplate.Require != -1) {
            RadarCard radarRequireTemplate = RadarService.gI().RADAR_TEMPLATE.stream().filter(r -> r.Id == radarTemplate.Require).findFirst().orElse(null);
            if (radarRequireTemplate == null) {
                return;
            }
            Card cardRequire = pl.Cards.stream().filter(r -> r.Id == radarRequireTemplate.Id).findFirst().orElse(null);
            if (cardRequire == null || cardRequire.Level < radarTemplate.RequireLevel) {
                Service.gI().sendThongBao(pl, "Bạn cần sưu tầm " + radarRequireTemplate.Name + " ở cấp độ " + radarTemplate.RequireLevel + " mới có thể sử dụng thẻ này");
                return;
            }
        }
        Card card = pl.Cards.stream().filter(r -> r.Id == item.template.id).findFirst().orElse(null);
        if (card == null) {
            Card newCard = new Card(item.template.id, (byte) 1, radarTemplate.Max, (byte) -1, radarTemplate.Options);
            if (pl.Cards.add(newCard)) {
                RadarService.gI().RadarSetAmount(pl, newCard.Id, newCard.Amount, newCard.MaxAmount);
                RadarService.gI().RadarSetLevel(pl, newCard.Id, newCard.Level);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                InventoryServiceNew.gI().sendItemBags(pl);
            }
        } else {
            if (card.Level >= 2) {
                Service.gI().sendThongBao(pl, "Thẻ này đã đạt cấp tối đa");
                return;
            }
            card.Amount++;
            if (card.Amount >= card.MaxAmount) {
                card.Amount = 0;
                if (card.Level == -1) {
                    card.Level = 1;
                } else {
                    card.Level++;
                }
                Service.gI().point(pl);
            }
            RadarService.gI().RadarSetAmount(pl, card.Id, card.Amount, card.MaxAmount);
            RadarService.gI().RadarSetLevel(pl, card.Id, card.Level);
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            InventoryServiceNew.gI().sendItemBags(pl);
        }
    }

    private void useItemChangeFlagBag(Player player, Item item) {
        switch (item.template.id) {
            case 994: //vỏ ốc
                break;
            case 995: //cây kem
                break;
            case 996: //cá heo
                break;
            case 997: //con diều
                break;
            case 998: //diều rồng
                break;
            case 999: //mèo mun
                if (!player.effectFlagBag.useMeoMun) {
                    player.effectFlagBag.reset();
                    player.effectFlagBag.useMeoMun = !player.effectFlagBag.useMeoMun;
                } else {
                    player.effectFlagBag.reset();
                }
                break;
            case 1000: //xiên cá
                if (!player.effectFlagBag.useXienCa) {
                    player.effectFlagBag.reset();
                    player.effectFlagBag.useXienCa = !player.effectFlagBag.useXienCa;
                } else {
                    player.effectFlagBag.reset();
                }
                break;
            case 1001: //phóng heo
                if (!player.effectFlagBag.usePhongHeo) {
                    player.effectFlagBag.reset();
                    player.effectFlagBag.usePhongHeo = !player.effectFlagBag.usePhongHeo;
                } else {
                    player.effectFlagBag.reset();
                }
                break;
        }
        Service.gI().point(player);
        Service.gI().sendFlagBag(player);
    }

    private void changePet(Player player, Item item) {
        if (player.pet != null) {
            int gender = player.pet.gender + 1;
            if (gender > 2) {
                gender = 0;
            }
            PetService.gI().changeNormalPet(player, gender);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
        } else {
            Service.gI().sendThongBao(player, "Không thể thực hiện");
        }
    }

    private void changePetBerus(Player player, Item item) {
        if (player.pet != null) {
            int gender = player.pet.gender;
            PetService.gI().changeBerusPet(player, gender);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
        } else {
            Service.gI().sendThongBao(player, "Không thể thực hiện");
        }
    }

    private void changePetPic(Player player, Item item) {
        if (player.pet != null) {
            int gender = player.pet.gender;
            PetService.gI().changePicPet(player, gender);
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
        } else {
            Service.gI().sendThongBao(player, "Không thể thực hiện");
        }
    }

    private void openPhieuCaiTrangHaiTac(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            Item ct = ItemService.gI().createNewItem((short) Util.nextInt(618, 626));
            ct.itemOptions.add(new ItemOption(147, 3));
            ct.itemOptions.add(new ItemOption(77, 3));
            ct.itemOptions.add(new ItemOption(103, 3));
            ct.itemOptions.add(new ItemOption(149, 0));
            if (item.template.id == 2006) {
                ct.itemOptions.add(new ItemOption(93, Util.nextInt(1, 7)));
            } else if (item.template.id == 2007) {
                ct.itemOptions.add(new ItemOption(93, Util.nextInt(7, 30)));
            }
            InventoryServiceNew.gI().addItemBag(pl, ct);
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            InventoryServiceNew.gI().sendItemBags(pl);
            CombineServiceNew.gI().sendEffectOpenItem(pl, item.template.iconID, ct.template.iconID);
        } else {
            Service.gI().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }

    private void eatGrapes(Player pl, Item item) {
        int percentCurrentStatima = pl.nPoint.stamina * 100 / pl.nPoint.maxStamina;
        if (percentCurrentStatima > 50) {
            Service.gI().sendThongBao(pl, "Thể lực vẫn còn trên 50%");
            return;
        } else if (item.template.id == 211) {
            pl.nPoint.stamina = pl.nPoint.maxStamina;
            Service.gI().sendThongBao(pl, "Thể lực của bạn đã được hồi phục 100%");
        } else if (item.template.id == 212) {
            pl.nPoint.stamina += (pl.nPoint.maxStamina * 20 / 100);
            Service.gI().sendThongBao(pl, "Thể lực của bạn đã được hồi phục 20%");
        }
        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
        InventoryServiceNew.gI().sendItemBags(pl);
        PlayerService.gI().sendCurrentStamina(pl);
    }

    private void openCSKB(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            short[] temp = {13, 14, 16, 190, 381, 382, 383, 384, 385};
            int[][] gold = {{1000000, 2000000}};
            byte index = (byte) Util.nextInt(0, temp.length - 1);
            short[] icon = new short[2];
            icon[0] = item.template.iconID;
            if (index <= 3) {
                pl.inventory.gold += Util.nextInt(gold[0][0], gold[0][1]);
                if (pl.inventory.gold > Inventory.LIMIT_GOLD) {
                    pl.inventory.gold = Inventory.LIMIT_GOLD;
                }
                PlayerService.gI().sendInfoHpMpMoney(pl);
                icon[1] = 930;
            } else {
                Item it = ItemService.gI().createNewItem(temp[index]);
                it.itemOptions.add(new ItemOption(73, 0));
                InventoryServiceNew.gI().addItemBag(pl, it);
                icon[1] = it.template.iconID;
            }
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            InventoryServiceNew.gI().sendItemBags(pl);

            CombineServiceNew.gI().sendEffectOpenItem(pl, icon[0], icon[1]);
        } else {
            Service.gI().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }

    private boolean maydoboss(Player pl) {
        try {
            BossManager.gI().showListBoss(pl);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    private void nextNhiemVu(Player pl) { // Code Next Nhiem Vu
        Item item = InventoryServiceNew.gI().findItemBag(pl, 1172);
        int i = 0;
        if (pl.playerTask.taskMain.id == 11) {
            if (pl.playerTask.taskMain.index == 0) {
                TaskService.gI().DoneTask(pl, ConstTask.TASK_11_0);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            } else if (pl.playerTask.taskMain.index == 1) {
                TaskService.gI().DoneTask(pl, ConstTask.TASK_11_1);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            } else if (pl.playerTask.taskMain.index == 2) {
                TaskService.gI().DoneTask(pl, ConstTask.TASK_11_2);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            } else {
                i++;
                Service.getInstance().sendThongBao(pl, "Ta đã giúp con hoàn thành nhiệm vụ rồi mau đi trả nhiệm vụ");
            }
        } else if (pl.playerTask.taskMain.id == 13) {
            if (pl.playerTask.taskMain.index == 0) {
                TaskService.gI().DoneTask(pl, ConstTask.TASK_13_0);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            } else {
                i++;
                Service.getInstance().sendThongBao(pl, "Ta đã giúp con hoàn thành nhiệm vụ rồi mau đi trả nhiệm vụ");
            }
        } else if (pl.playerTask.taskMain.id == 27) {
            if (pl.playerTask.taskMain.index == 0) {
                TaskService.gI().DoneTask(pl, ConstTask.TASK_27_0);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            } else if (pl.playerTask.taskMain.index == 1) {
                TaskService.gI().DoneTask(pl, ConstTask.TASK_27_1);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            } else if (pl.playerTask.taskMain.index == 2) {
                TaskService.gI().DoneTask(pl, ConstTask.TASK_27_2);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            } else {
                i++;
                Service.getInstance().sendThongBao(pl, "Ta đã giúp con hoàn thành nhiệm vụ rồi mau đi trả nhiệm vụ");
            }
        } else {
            i++;
            Service.getInstance().sendThongBao(pl, "Chỉ Hỗ Trợ NHiệm Vụ: Kết giao, gia nhập bang hội, nv bang hội đầu tiên, nhiệm vụ bang hội thứ 2");
        }
        if (i == 0) {
            Service.getInstance().sendThongBao(pl, "Chúc mừng bạn đã qua nhiệm vụ");
        }
        InventoryServiceNew.gI().sendItemBags(pl);
    }

    private void hopDiemThongNhat(Player pl, Item item) {
        Item queDiem = InventoryServiceNew.gI().findItemBag(pl, 1471);
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            if (queDiem != null) {
                short[] temp = {1476, 1477, 1467, 1469, 1472};
                byte index = (byte) Util.nextInt(0, temp.length - 1);
                short[] icon = new short[2];
                icon[0] = item.template.iconID;
                Item it = ItemService.gI().createNewItem(temp[index]);

                if (it.template.id == 1476 || it.template.id == 1477) {
                    it.itemOptions.add(new Item.ItemOption(50, Util.nextInt(25, 35)));
                    it.itemOptions.add(new Item.ItemOption(50, Util.nextInt(25, 35)));
                    it.itemOptions.add(new Item.ItemOption(50, Util.nextInt(25, 35)));
                    it.itemOptions.add(new Item.ItemOption(5, Util.nextInt(1, 5)));
                    it.itemOptions.add(new Item.ItemOption(14, Util.nextInt(5, 15)));
                    it.itemOptions.add(new Item.ItemOption(101, Util.nextInt(20, 30)));
                    it.itemOptions.add(new Item.ItemOption(95, Util.nextInt(25, 35)));
                    it.itemOptions.add(new Item.ItemOption(96, Util.nextInt(25, 35)));
                    if (Util.isTrue(97, 100)) {
                        it.itemOptions.add(new Item.ItemOption(93, Util.nextInt(1, 7)));
                    }
                } else {
                    it.itemOptions.add(new Item.ItemOption(50, Util.nextInt(10, 22)));
                    it.itemOptions.add(new Item.ItemOption(50, Util.nextInt(10, 22)));
                    it.itemOptions.add(new Item.ItemOption(50, Util.nextInt(10, 22)));
                    if (Util.isTrue(97, 100)) {
                        it.itemOptions.add(new Item.ItemOption(93, Util.nextInt(1, 7)));
                    }
                }
                InventoryServiceNew.gI().addItemBag(pl, it);
                pl.point_noel++;
                InventoryServiceNew.gI().subQuantityItemsBag(pl, queDiem, 1);
                InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            } else {
                Service.getInstance().sendThongBao(pl, "Bạn Cần Có Que Diêm để mở Hộp Diêm Thống Nhất");
            }
        } else {
            Service.gI().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }

    private void useItemHopQuaTanThu(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 7) {
            short[] icon = new short[2];
            icon[0] = item.template.iconID;
            Item Frieren = ItemService.gI().createNewItem((short) 1183);
            short baseAo = 1;
            short baseQuan = 7;
            short baseGang = 22;
            short baseGiay = 28;
            short genderAo = (pl.gender == 0) ? -1 : ((pl.gender == 2) ? 1 : (short) 0);
            short genderQuan = (pl.gender == 0) ? -1 : ((pl.gender == 2) ? 1 : (short) 0);
            short genderGang = (pl.gender == 0) ? -1 : ((pl.gender == 2) ? 1 : (short) 0);
            short genderGiay = (pl.gender == 0) ? -1 : ((pl.gender == 2) ? 1 : (short) 0);
            Item ao = ItemService.gI().createNewItem((short) (baseAo + genderAo));
            Item quan = ItemService.gI().createNewItem((short) (baseQuan + genderQuan));
            Item gang = ItemService.gI().createNewItem((short) (baseGang + genderGang));
            Item giay = ItemService.gI().createNewItem((short) (baseGiay + genderGiay));
            Item nhan = ItemService.gI().createNewItem((short) 12);
            Item capsule = ItemService.gI().createNewItem((short) 194);
            Item duiDuc = ItemService.gI().createNewItem((short) 1438);
            Item hematite = ItemService.gI().createNewItem((short) 1458);
            Frieren.itemOptions.add(new ItemOption(50, 30));
            Frieren.itemOptions.add(new ItemOption(77, 30));
            Frieren.itemOptions.add(new ItemOption(103, 30));
            Frieren.itemOptions.add(new ItemOption(101, 100));
            Frieren.itemOptions.add(new ItemOption(93, 5));
            ao.itemOptions.add(new ItemOption(47, 3));
            quan.itemOptions.add(new ItemOption(6, 30));
            gang.itemOptions.add(new ItemOption(0, 4));
            giay.itemOptions.add(new ItemOption(7, 10));
            nhan.itemOptions.add(new ItemOption(14, 1));
            ao.itemOptions.add(new ItemOption(107, 4));
            quan.itemOptions.add(new ItemOption(107, 4));
            gang.itemOptions.add(new ItemOption(107, 4));
            giay.itemOptions.add(new ItemOption(107, 4));
            nhan.itemOptions.add(new ItemOption(107, 4));
            capsule.itemOptions.add(new Item.ItemOption(30, 0));
            duiDuc.quantity += 98;
            hematite.quantity += 98;
            InventoryServiceNew.gI().addItemBag(pl, Frieren);
            InventoryServiceNew.gI().addItemBag(pl, ao);
            InventoryServiceNew.gI().addItemBag(pl, quan);
            InventoryServiceNew.gI().addItemBag(pl, gang);
            InventoryServiceNew.gI().addItemBag(pl, giay);
            InventoryServiceNew.gI().addItemBag(pl, nhan);
            InventoryServiceNew.gI().addItemBag(pl, capsule);
            InventoryServiceNew.gI().addItemBag(pl, duiDuc);
            InventoryServiceNew.gI().addItemBag(pl, hematite);
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            CombineServiceNew.gI().sendEffectOpenItem(pl, icon[0], icon[1]);
        } else {
            Service.getInstance().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }

    private void useItemQuaDua(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            short[] temp = {2069, 2070, 2071, 2072, 2073};
            int[][] gold = {{10000000, 20000000}};
            int[][] ruby = {{10, 20}};
            byte index = (byte) Util.nextInt(0, temp.length - 1);
            short[] icon = new short[2];
            icon[0] = item.template.iconID;
            if (index <= 3) {
                pl.inventory.gold += Util.nextInt(gold[0][0], gold[0][1]);
                if (pl.inventory.gold > Inventory.LIMIT_GOLD) {
                    pl.inventory.gold = Inventory.LIMIT_GOLD;
                }
                PlayerService.gI().sendInfoHpMpMoney(pl);
                icon[1] = 930;
            } else {
                Item it = ItemService.gI().createNewItem(temp[index]);
                it.itemOptions.add(new ItemOption(73, 0));
                InventoryServiceNew.gI().addItemBag(pl, it);
                icon[1] = it.template.iconID;
            }
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            InventoryServiceNew.gI().sendItemBags(pl);

            CombineServiceNew.gI().sendEffectOpenItem(pl, icon[0], icon[1]);
        } else {
            Service.gI().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }

    public void hopquaShare(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            Item thoivang = ItemService.gI().createNewItem((short) 457);
            Item saophale = ItemService.gI().createNewItem((short) 441);
            Item saophale1 = ItemService.gI().createNewItem((short) 442);
            Item saophale2 = ItemService.gI().createNewItem((short) 443);
            Item saophale3 = ItemService.gI().createNewItem((short) 444);
            Item saophale4 = ItemService.gI().createNewItem((short) 445);
            Item saophale5 = ItemService.gI().createNewItem((short) 446);
            Item saophale6 = ItemService.gI().createNewItem((short) 447);
            thoivang.itemOptions.add(new ItemOption(30, 0));
            saophale.itemOptions.add(new ItemOption(95, 5));
            saophale1.itemOptions.add(new ItemOption(96, 5));
            saophale2.itemOptions.add(new ItemOption(97, 5));
            saophale3.itemOptions.add(new ItemOption(98, 5));
            saophale4.itemOptions.add(new ItemOption(99, 5));
            saophale5.itemOptions.add(new ItemOption(100, 5));
            saophale6.itemOptions.add(new ItemOption(101, 5));
            thoivang.quantity += 19;
            saophale.quantity += 19;
            saophale1.quantity += 19;
            saophale2.quantity += 19;
            saophale3.quantity += 19;
            saophale4.quantity += 19;
            saophale5.quantity += 19;
            saophale6.quantity += 19;
            InventoryServiceNew.gI().addItemBag(pl, thoivang);
            InventoryServiceNew.gI().addItemBag(pl, saophale);
            InventoryServiceNew.gI().addItemBag(pl, saophale1);
            InventoryServiceNew.gI().addItemBag(pl, saophale2);
            InventoryServiceNew.gI().addItemBag(pl, saophale3);
            InventoryServiceNew.gI().addItemBag(pl, saophale4);
            InventoryServiceNew.gI().addItemBag(pl, saophale5);
            InventoryServiceNew.gI().addItemBag(pl, saophale6);
        } else {
            Service.gI().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }

    public void hopQuaNoel(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            short[] temp = {1469, 1472};
            int[][] gold = {{10000000, 20000000}};
            int[][] ruby = {{10, 20}};
            byte index = (byte) Util.nextInt(0, temp.length - 1);
            short[] icon = new short[2];
            icon[0] = item.template.iconID;
            if (index <= 3) {
                pl.inventory.gold += Util.nextInt(gold[0][0], gold[0][1]);
                if (pl.inventory.gold > Inventory.LIMIT_GOLD) {
                    pl.inventory.gold = Inventory.LIMIT_GOLD;
                }
                PlayerService.gI().sendInfoHpMpMoney(pl);
                icon[1] = 930;
            } else {
                Item it = ItemService.gI().createNewItem(temp[index]);
                it.itemOptions.add(new ItemOption(50, Util.nextInt(5, 15)));
                it.itemOptions.add(new ItemOption(77, Util.nextInt(5, 15)));
                it.itemOptions.add(new ItemOption(103, Util.nextInt(5, 15)));
                it.itemOptions.add(new ItemOption(30, 0));
                it.itemOptions.add(new ItemOption(73, 0));
                if (Util.isTrue(95, 100)) {
                    it.itemOptions.add(new ItemOption(93, Util.nextInt(1, 7)));
                }
                InventoryServiceNew.gI().addItemBag(pl, it);
                icon[1] = it.template.iconID;
            }
            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
            InventoryServiceNew.gI().sendItemBags(pl);
            CombineServiceNew.gI().sendEffectOpenItem(pl, icon[0], icon[1]);
        } else {
            Service.gI().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }

    public void caitrang5Ngay(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            short[] temp = {1295, 1296, 1297, 1298, 1299};
            byte index = (byte) Util.nextInt(0, temp.length - 1);
            short[] icon = new short[2];
            icon[0] = item.template.iconID;
            Item it = ItemService.gI().createNewItem(temp[index]);
            it.itemOptions.add(new ItemOption(50, 20));
            it.itemOptions.add(new ItemOption(77, 20));
            it.itemOptions.add(new ItemOption(103, 20));
            it.itemOptions.add(new ItemOption(14, 15));
            it.itemOptions.add(new ItemOption(5, 15));
            it.itemOptions.add(new ItemOption(93, 5));
            InventoryServiceNew.gI().addItemBag(pl, it);
            icon[1] = it.template.iconID;
            InventoryServiceNew.gI().sendItemBags(pl);
            CombineServiceNew.gI().sendEffectOpenItem(pl, icon[0], icon[1]);
        } else {
            Service.gI().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }

    public void caitrang7Ngay(Player pl, Item item) {
        if (InventoryServiceNew.gI().getCountEmptyBag(pl) > 0) {
            short[] temp = {1295, 1296, 1297, 1298, 1299};
            byte index = (byte) Util.nextInt(0, temp.length - 1);
            short[] icon = new short[2];
            icon[0] = item.template.iconID;
            Item it = ItemService.gI().createNewItem(temp[index]);
            it.itemOptions.add(new ItemOption(50, 20));
            it.itemOptions.add(new ItemOption(77, 20));
            it.itemOptions.add(new ItemOption(103, 20));
            it.itemOptions.add(new ItemOption(14, 15));
            it.itemOptions.add(new ItemOption(5, 15));
            it.itemOptions.add(new ItemOption(93, 7));
            InventoryServiceNew.gI().addItemBag(pl, it);
            icon[1] = it.template.iconID;
            InventoryServiceNew.gI().sendItemBags(pl);
            CombineServiceNew.gI().sendEffectOpenItem(pl, icon[0], icon[1]);
        } else {
            Service.gI().sendThongBao(pl, "Hàng trang đã đầy");
        }
    }

    private void useItemTime(Player pl, Item item) {
        switch (item.template.id) {
            case 1426:
                pl.itemTime.lastTimex2De = System.currentTimeMillis();
                pl.itemTime.isUsex2De = true;
                break;
            case 1484:
                pl.itemTime.lastTimeUseMayDoKhoBau = System.currentTimeMillis();
                pl.itemTime.isUseMayDoKhoBau = true;
                break;
            case 379: //máy dò capsule
                pl.itemTime.lastTimeUseMayDo = System.currentTimeMillis();
                pl.itemTime.isUseMayDo = true;
                break;
            case 381:
                if (pl.itemTime.isUseCuongNoSC == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng cuồng nộ");
                    return;
                }
                pl.itemTime.lastTimeCuongNo = System.currentTimeMillis();
                pl.itemTime.isUseCuongNo = true;
                Service.getInstance().point(pl);
                break;
            case 382: //bổ huyết
                if (pl.itemTime.isUseBoHuyetSC == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng bổ huyết");
                    return;
                }
                pl.itemTime.lastTimeBoHuyet = System.currentTimeMillis();
                pl.itemTime.isUseBoHuyet = true;
                break;
            case 383: //bổ khí
                if (pl.itemTime.isUseBoKhiSC == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng bổ khí siêu cấp");
                    return;
                }
                pl.itemTime.lastTimeBoKhi = System.currentTimeMillis();
                pl.itemTime.isUseBoKhi = true;
                break;
            case 384: //giáp xên
                if (pl.itemTime.isUseGiapXenSC == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng giáp xên");
                    return;
                }
                pl.itemTime.lastTimeGiapXen = System.currentTimeMillis();
                pl.itemTime.isUseGiapXen = true;
                break;
            case 385: //ẩn danh
                if (pl.itemTime.isUseAnDanhSC == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng ẩn danh đặc biệt");
                    return;
                }
                pl.itemTime.lastTimeAnDanh = System.currentTimeMillis();
                pl.itemTime.isUseAnDanh = true;
                break;
            case 1100: //bổ huyết siêu cấp
                if (pl.itemTime.isUseBoHuyet == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng bổ huyết siêu cấp");
                    return;
                }
                pl.itemTime.lastTimeBoHuyetSC = System.currentTimeMillis();
                pl.itemTime.isUseBoHuyetSC = true;
                break;
            case 1101: //bổ khí siêu cấp
                if (pl.itemTime.isUseBoKhi == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng bổ khí");
                    return;
                }
                pl.itemTime.lastTimeBoKhiSC = System.currentTimeMillis();
                pl.itemTime.isUseBoKhiSC = true;
                break;
            case 1102: //giáp xên siêu cấp
                if (pl.itemTime.isUseGiapXen == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng giáp xên siêu cấp");
                    return;
                }
                pl.itemTime.lastTimeGiapXenSC = System.currentTimeMillis();
                pl.itemTime.isUseGiapXenSC = true;
                break;
            case 1099: //cuồng nộ siêu cấp
                if (pl.itemTime.isUseCuongNo == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng cuồng nộ siêu cấp");
                    return;
                }
                pl.itemTime.lastTimeCuongNoSC = System.currentTimeMillis();
                pl.itemTime.isUseCuongNoSC = true;
                Service.getInstance().point(pl);
                break;
            case 1103: //ẩn danh
                if (pl.itemTime.isUseAnDanh == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng ẩn danh");
                    return;
                }
                pl.itemTime.lastTimeAnDanhSC = System.currentTimeMillis();
                pl.itemTime.isUseAnDanhSC = true;
                break;
            case 579: //đuôi khỉ
                if (pl.itemTime.isUseDuoiKhi == true) {
                    Service.getInstance().sendThongBao(pl, "Bạn đang sử dụng đuôi khỉ");
                    return;
                }
                pl.itemTime.lastTimeDuoiKhi = System.currentTimeMillis();
                pl.itemTime.isUseDuoiKhi = true;
                break;
            case 663: //bánh pudding
            case 664: //xúc xíc
            case 665: //kem dâu
            case 666: //mì ly
            case 667: //sushi
                pl.itemTime.lastTimeEatMeal = System.currentTimeMillis();
                pl.itemTime.isEatMeal = true;
                ItemTimeService.gI().removeItemTime(pl, pl.itemTime.iconMeal);
                pl.itemTime.iconMeal = item.template.iconID;
                break;
            case 2037: //máy dò đồ
                pl.itemTime.lastTimeUseMayDo2 = System.currentTimeMillis();
                pl.itemTime.isUseMayDo2 = true;
                break;
            case 2105: //máy dò đồ
                pl.itemTime.lastTimeUseMayDo3 = System.currentTimeMillis();
                pl.itemTime.isUseMayDo3 = true;
                break;
        }
        Service.gI().point(pl);
        ItemTimeService.gI().sendAllItemTime(pl);
        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
        InventoryServiceNew.gI().sendItemBags(pl);
    }

    private void controllerCallRongThan(Player pl, Item item) {
        int tempId = item.template.id;
        if (tempId >= SummonDragon.NGOC_RONG_1_SAO && tempId <= SummonDragon.NGOC_RONG_7_SAO) {
            switch (tempId) {
                case SummonDragon.NGOC_RONG_1_SAO:
                case SummonDragon.NGOC_RONG_2_SAO:
                case SummonDragon.NGOC_RONG_3_SAO:
                    SummonDragon.gI().openMenuSummonShenron(pl, (byte) (tempId - 13));
                    break;
                default:
                    NpcService.gI().createMenuConMeo(pl, ConstNpc.TUTORIAL_SUMMON_DRAGON,
                            -1, "Bạn chỉ có thể gọi rồng từ ngọc 3 sao, 2 sao, 1 sao", "Hướng\ndẫn thêm\n(mới)", "OK");
                    break;
            }
        } else if (tempId >= SummonDragon.NGOC_RONG_BANG[0] && tempId <= SummonDragon.NGOC_RONG_BANG[6]) {
            switch (tempId) {
                case 925:
                    SummonDragon.gI().openMenuSummonShenron(pl, (byte) 925, SummonDragon.DRAGON_ICE_SHENRON);
                    break;
                default:
                    Service.getInstance().sendThongBao(pl, "Bạn chỉ có thể gọi rồng băng từ ngọc 1 sao");
                    break;
            }
        }
    }

//    private void controllerCalltrb(Player pl, Item item) {
//        int tempId = item.template.id;
//        if (tempId >= SummonDragon.NGOC_RONGTRB1 && tempId <= SummonDragon.NGOC_RONGTRB3) {
//            switch (tempId) {
//                case SummonDragon.NGOC_RONGTRB1:
//                    SummonDragon.gI().openMenuSummonShenronTRB(pl, (byte) (tempId - 2090));
//                    break;
//                default:
//                    NpcService.gI().createMenuConMeo(pl, ConstNpc.TUTORIAL_SUMMON_DRAGONTRB,
//                            -1, "Bạn chỉ có thể gọi rồng từ ngọc 1 sao TRB ", "Hướng\ndẫn thêm\n(mới)", "OK");
//                    break;
//            }
//        }
//    }
    private void learnSkill(Player pl, Item item) {
        Message msg;
        try {
            if (item.template.gender == pl.gender || item.template.gender == 3) {
                String[] subName = item.template.name.split("");
                byte level = Byte.parseByte(subName[subName.length - 1]);
                Skill curSkill = SkillUtil.getSkillByItemID(pl, item.template.id);
                if (curSkill.point == 7) {
                    Service.gI().sendThongBao(pl, "Kỹ năng đã đạt tối đa!");
                } else {
                    if (curSkill.point == 0) {
                        if (level == 1) {
                            curSkill = SkillUtil.createSkill(SkillUtil.getTempSkillSkillByItemID(item.template.id), level);
                            SkillUtil.setSkill(pl, curSkill);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            msg = Service.gI().messageSubCommand((byte) 23);
                            msg.writer().writeShort(curSkill.skillId);
                            pl.sendMessage(msg);
                            msg.cleanup();
                        } else {
                            Skill skillNeed = SkillUtil.createSkill(SkillUtil.getTempSkillSkillByItemID(item.template.id), level);
                            Service.gI().sendThongBao(pl, "Vui lòng học " + skillNeed.template.name + " cấp " + skillNeed.point + " trước!");
                        }
                    } else {
                        if (curSkill.point + 1 == level) {
                            curSkill = SkillUtil.createSkill(SkillUtil.getTempSkillSkillByItemID(item.template.id), level);
                            //System.out.println(curSkill.template.name + " - " + curSkill.point);
                            SkillUtil.setSkill(pl, curSkill);
                            InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                            msg = Service.gI().messageSubCommand((byte) 62);
                            msg.writer().writeShort(curSkill.skillId);
                            pl.sendMessage(msg);
                            msg.cleanup();
                        } else {
                            Service.gI().sendThongBao(pl, "Vui lòng học " + curSkill.template.name + " cấp " + (curSkill.point + 1) + " trước!");
                        }
                    }
                    InventoryServiceNew.gI().sendItemBags(pl);
                }
            } else {
                Service.gI().sendThongBao(pl, "Không thể thực hiện");
            }
        } catch (Exception e) {
            Logger.logException(UseItem.class, e);
        }
    }

    public void usethoivang(Player player) {
        Item tv = null;
        for (Item item : player.inventory.itemsBag) {
            if (item.isNotNullItem() && item.template.id == 457) {
                tv = item;
                break;
            }
        }
        if (tv != null) {
            if (player.inventory.gold <= LIMIT_GOLD) {
                InventoryServiceNew.gI().subQuantityItemsBag(player, tv, 1);
                player.inventory.gold += 500000000;
                PlayerService.gI().sendInfoHpMpMoney(player);
                InventoryServiceNew.gI().sendItemBags(player);
            } else {
                Service.getInstance().sendThongBao(player, "Đã đạt giới hạn vàng");
            }
        }
    }

    private void useTDLT(Player pl, Item item) {
        if (pl.itemTime.isUseTDLT) {
            ItemTimeService.gI().turnOffTDLT(pl, item);
        } else {
            ItemTimeService.gI().turnOnTDLT(pl, item);
        }
    }

    private void openThoiVangMAX(Player pl, Item item) {
        NpcService.gI().createMenuConMeo(pl, ConstNpc.TVMAX, -1,
                "1 thỏi vàng trị giá 500tr vàng\n\nHiện tại có:" + item.quantity
                + " Thỏi vàng\n\nNguoi muốn sài bao nhiêu thỏi?",
                "1 thỏi", "5 thỏi", "10 thỏi", "25 thỏi", "50 thỏi", "100 thỏi");
        return;
    }

    private void usePorata(Player pl) {
        if (pl.pet == null || pl.fusion.typeFusion == 4 || pl.fusion.typeFusion == 8 || pl.fusion.typeFusion == 10 || pl.fusion.typeFusion == 12) {
            Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
        } else {
            if (pl.fusion.typeFusion == ConstPlayer.NON_FUSION) {
                pl.pet.fusion(true);
            } else {
                pl.pet.unFusion();
            }
        }
    }

    private void usePorata2(Player pl) {
        if (pl.fusion.typeFusion == 120) {
            Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
        } else {
            if (pl.fusion.typeFusion == ConstPlayer.NON_FUSION) {
                pl.pet.fusion2(true);
            } else {
                pl.pet.unFusion();
            }
        }
    }

    private void usePorata3(Player pl) {
        if (pl.fusion.typeFusion == 120) {
            Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
        } else {
            if (pl.fusion.typeFusion == ConstPlayer.NON_FUSION) {
                pl.pet.fusion3(true);
            } else {
                pl.pet.unFusion();
            }
        }
    }

    private void usePorata4(Player pl) {
        if (pl.fusion.typeFusion == 120) {
            Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
        } else {
            if (pl.fusion.typeFusion == ConstPlayer.NON_FUSION) {
                pl.pet.fusion4(true);
            } else {
                pl.pet.unFusion();
            }
        }
    }

    private void openCapsuleUI(Player player) {
        if (player.zone.map.mapId == 129 || player.zone.map.mapId == 112 || MapService.gI().isMapMaBu(player.zone.map.mapId)) {
            Service.gI().sendThongBao(player, "Không sử dụng Capsule ở khu vực này!");
            return;
        }
        player.iDMark.setTypeChangeMap(ConstMap.CHANGE_CAPSULE);
        ChangeMapService.gI().openChangeMapTab(player);
    }

    // Chuyển Map Bằng Capsule
    public void choseMapCapsule(Player pl, int index) {
        int zoneId = -1;
        Zone zoneChose = pl.mapCapsule.get(index);
        //Kiểm tra số lượng người trong khu
        if (zoneChose.getNumOfPlayers() > 25
                || MapService.gI().isMapDoanhTrai(zoneChose.map.mapId)
                || MapService.gI().isMapMaBu(zoneChose.map.mapId)
                || MapService.gI().isMapBanDoKhoBau(zoneChose.map.mapId)
                || MapService.gI().isMapHuyDiet(zoneChose.map.mapId)
                || MapService.gI().isMapSatan(zoneChose.map.mapId)) {
            Service.gI().sendThongBao(pl, "Hiện tại không thể vào được khu!");
            return;
        }
        if (index != 0 || zoneChose.map.mapId == 21
                || zoneChose.map.mapId == 22
                || zoneChose.map.mapId == 23) {
            pl.mapBeforeCapsule = pl.zone;
        } else {
            zoneId = pl.mapBeforeCapsule != null ? pl.mapBeforeCapsule.zoneId : -1;
            pl.mapBeforeCapsule = null;
        }
        ChangeMapService.gI().changeMapBySpaceShip(pl, pl.mapCapsule.get(index).map.mapId, zoneId, Util.nextInt(220, 500));
    }

    public void eatPea(Player player) {
        Item pea = null;
        for (Item item : player.inventory.itemsBag) {
            if (item.isNotNullItem() && item.template.type == 6) {
                pea = item;
                break;
            }
        }
        if (pea != null) {
            int hpKiHoiPhuc = 0;
            int lvPea = Integer.parseInt(pea.template.name.substring(13));
            for (Item.ItemOption io : pea.itemOptions) {
                if (io.optionTemplate.id == 2) {
                    hpKiHoiPhuc = io.param * 1000;
                    break;
                }
                if (io.optionTemplate.id == 48) {
                    hpKiHoiPhuc = io.param;
                    break;
                }
            }
            player.nPoint.setHp(player.nPoint.hp + hpKiHoiPhuc);
            player.nPoint.setMp(player.nPoint.mp + hpKiHoiPhuc);
            PlayerService.gI().sendInfoHpMp(player);
            Service.gI().sendInfoPlayerEatPea(player);
            if (player.pet != null && player.zone.equals(player.pet.zone) && !player.pet.isDie()) {
                int statima = 100 * lvPea;
                player.pet.nPoint.stamina += statima;
                if (player.pet.nPoint.stamina > player.pet.nPoint.maxStamina) {
                    player.pet.nPoint.stamina = player.pet.nPoint.maxStamina;
                }
                player.pet.nPoint.setHp(player.pet.nPoint.hp + hpKiHoiPhuc);
                player.pet.nPoint.setMp(player.pet.nPoint.mp + hpKiHoiPhuc);
                Service.gI().sendInfoPlayerEatPea(player.pet);
                Service.gI().chatJustForMe(player, player.pet, "Cảm ơn sư phụ đã cho con đậu thần");
            }

            InventoryServiceNew.gI().subQuantityItemsBag(player, pea, 1);
            InventoryServiceNew.gI().sendItemBags(player);
        }
    }

    private void upSkillPet(Player pl, Item item) {
        if (pl.pet == null) {
            Service.gI().sendThongBao(pl, "Không thể thực hiện");
            return;
        }
        try {
            switch (item.template.id) {
                case 402: //skill 1
                    if (SkillUtil.upSkillPet(pl.pet.playerSkill.skills, 0)) {
                        Service.gI().chatJustForMe(pl, pl.pet, "Cảm ơn sư phụ");
                        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                    } else {
                        Service.gI().sendThongBao(pl, "Không thể thực hiện");
                    }
                    break;
                case 403: //skill 2
                    if (SkillUtil.upSkillPet(pl.pet.playerSkill.skills, 1)) {
                        Service.gI().chatJustForMe(pl, pl.pet, "Cảm ơn sư phụ");
                        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                    } else {
                        Service.gI().sendThongBao(pl, "Không thể thực hiện");
                    }
                    break;
                case 404: //skill 3
                    if (SkillUtil.upSkillPet(pl.pet.playerSkill.skills, 2)) {
                        Service.gI().chatJustForMe(pl, pl.pet, "Cảm ơn sư phụ");
                        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                    } else {
                        Service.gI().sendThongBao(pl, "Không thể thực hiện");
                    }
                    break;
                case 759: //skill 4
                    if (SkillUtil.upSkillPet(pl.pet.playerSkill.skills, 3)) {
                        Service.gI().chatJustForMe(pl, pl.pet, "Cảm ơn sư phụ");
                        InventoryServiceNew.gI().subQuantityItemsBag(pl, item, 1);
                    } else {
                        Service.gI().sendThongBao(pl, "Không thể thực hiện");
                    }
                    break;

            }

        } catch (Exception e) {
            Service.gI().sendThongBao(pl, "Không thể thực hiện");
        }
    }

    private void ItemSKH(Player pl, Item item) {//hop qua skh
        NpcService.gI().createMenuConMeo(pl, item.template.id, -1, "Hãy chọn một món quà", "Áo", "Quần", "Găng", "Giày", "Rada", "Từ Chối");
    }

    private void ItemDHD(Player pl, Item item) {//hop qua do huy diet
        NpcService.gI().createMenuConMeo(pl, item.template.id, -1, "Hãy chọn một món quà", "Áo", "Quần", "Găng", "Giày", "Rada", "Từ Chối");
    }

    private void Hopts(Player pl, Item item) {//hop qua do huy diet
        NpcService.gI().createMenuConMeo(pl, item.template.id, -1, "Chọn hành tinh của mày đi", "Set trái đất", "Set namec", "Set xayda", "Từ chổi");
    }

}
