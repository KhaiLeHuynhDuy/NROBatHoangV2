package Server;

import BossMain.BossManager;
import Card.Card;
import Card.RadarCard;
import Card.RadarService;
import Consts.ConstAchievement;
import Consts.ConstIgnoreName;
import Consts.ConstMap;
import Consts.ConstNpc;
import Consts.ConstTask;
import Daos.PlayerDAO;
import Data.DataGame;
import Data.ItemData;
import Services.func.ChangeMapService;
import Services.func.CombineServiceNew;
import Services.func.Input;
import Services.func.LuckyRound;
import Services.func.TransactionService;
import Services.func.UseItem;
import Item.ItemTime;
import static Item.ItemTime.KHI_GAS;
import KyGui.ShopKyGuiService;
import ListMap.BlackBallWar;
import ListMap.TrainingService;
import Matches.PVPService;
import NPC.NpcManager;
import Player.Player;
import Services.AchievementService;
import Services.ChatGlobalService;
import Services.ClanService;
import Services.FlagBagService;
import Services.FriendAndEnemyService;
import Services.IntrinsicService;
import Services.InventoryServiceNew;
import Services.ItemMapService;
import Services.ItemTimeService;
import Services.MapService;
import Services.NpcService;
import Services.PlayerService;
import Services.Service;
import Services.SkillService;
import Services.SubMenuService;
import Services.TaskService;
import Shop.ShopServiceNew;
import Skill.Skill;
import Utils.Logger;
import Utils.Util;
import com.girlkun.database.GirlkunDB;
import com.girlkun.models.map.sieuhang.SieuHangService;
import com.girlkun.result.GirlkunResultSet;
import java.io.IOException;
import java.util.Arrays;
import network.Message;
import network.inetwork.IMessageHandler;
import network.inetwork.ISession;

public class Controller implements IMessageHandler {

    private int errors;

    private static Controller instance;

    public static Controller gI() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    @Override
    public void onMessage(ISession s, Message _msg) {
        long st = System.currentTimeMillis();
        MySession _session = (MySession) s;
        Player player = null;
        try {
            player = _session.player;
            byte cmd = _msg.command;
//            if (cmd != -29 && cmd != -107 && cmd != 29 && cmd != -30 && cmd != 74 && cmd != -16 && cmd != -101 
//              && cmd != 126 && cmd != -74 && cmd != 21 && cmd != -15 && cmd != -103) {
//                System.out.println(cmd);
//            }
//            System.out.println("***************************CMD receive: " + cmd);
            switch (cmd) {
                case -100:
                    byte action = _msg.reader().readByte();
                    switch (action) {
                        case 0:
                            short idItem = _msg.reader().readShort();
                            byte moneyType = _msg.reader().readByte();
                            int money = _msg.reader().readInt();
                            int quantity;
                            if (player.getSession().version >= 222) {
                                quantity = _msg.reader().readInt();
                            } else {
                                quantity = _msg.reader().readByte();
                            }
                            if (quantity > 0) {
                                ShopKyGuiService.gI().KiGui(player, idItem, money, moneyType, quantity);
                            }
                            break;
                        case 1:
                        case 2:
                            idItem = _msg.reader().readShort();
                            ShopKyGuiService.gI().claimOrDel(player, action, idItem);
                            break;
                        case 3:
                            idItem = _msg.reader().readShort();
                            _msg.reader().readByte();
                            _msg.reader().readInt();
                            ShopKyGuiService.gI().buyItem(player, idItem);
                            break;
                        case 4:
                            moneyType = _msg.reader().readByte();
                            money = _msg.reader().readByte();
                            ShopKyGuiService.gI().openShopKyGui(player, moneyType, money);
                            break;
                        case 5:
                            idItem = _msg.reader().readShort();
                            ShopKyGuiService.gI().upItemToTop(player, idItem);
                            break;
                        default:
                            Service.getInstance().sendThongBao(player, "Không thể thực hiện");
                            break;
                    }
                    break;
                case 127:
                    if (player != null) {
                        byte actionRadar = _msg.reader().readByte();
                        switch (actionRadar) {
                            case 0:
                                RadarService.gI().sendRadar(player, player.Cards);
                                break;
                            case 1:
                                short idC = _msg.reader().readShort();
                                Card card = player.Cards.stream().filter(r -> r != null && r.Id == idC).findFirst().orElse(null);
                                if (card != null) {
                                    if (card.Level == 0) {
                                        return;
                                    }
                                    if (card.Used == 0) {
                                        if (player.Cards.stream().anyMatch(c -> c != null && c.Used == 1)) {
                                            Service.gI().sendThongBao(player, "Số thẻ sử dụng đã đạt tối đa");
                                            return;
                                        }
                                        card.Used = 1;
                                        RadarCard radarTemplate = RadarService.gI().RADAR_TEMPLATE.stream().filter(r -> r.Id == idC).findFirst().orElse(null);
                                        if (radarTemplate != null && card.Level >= 2) {
                                            player.idAura = radarTemplate.AuraId;
                                        }
                                    } else {
                                        card.Used = 0;
                                        player.idAura = -1;
                                    }
                                    RadarService.gI().Radar1(player, idC, card.Used);
                                    Service.gI().point(player);
                                }
                                break;
                        }
                    }
                    break;
                case -105:
                    if (player.type == 0 && player.maxTime == 30) {
                        ChangeMapService.gI().changeMap(player, 102, 0, 100, 336);
                    } else if (player.type == 1 && player.maxTime == 5) {
                        ChangeMapService.gI().changeMap(player, 160, 0, -1, 5);
                    } else if (player.type == 2 && player.maxTime == 5) {
                        if (player.iDMark != null && player.iDMark.isGoToBDKB()) {
                            ChangeMapService.gI().changeMap(player, MapService.gI().getMapCanJoin(player, 135, -1),
                                    35, 35);
                            player.iDMark.setGoToBDKB(false);
                        }
                    } else if (player.type == 3 && player.maxTime == 5) {
                        ChangeMapService.gI().changeMap(player, player.iDMark.getZoneKhiGasHuyDiet(),
                                player.iDMark.getXMapKhiGasHuyDiet(), player.iDMark.getYMapKhiGasHuyDiet());
                        player.iDMark.setZoneKhiGasHuyDiet(null);
                    } else if (player.type == 4 && player.maxTime == 5) {
                        if (player.iDMark != null && player.iDMark.isGoToKGHD()) {
                            ChangeMapService.gI().changeMap(player, MapService.gI().getMapCanJoin(player, 149, -1),
                                    100 + (Util.nextInt(-10, 10)), 336);
                            player.iDMark.setGoToKGHD(false);
                        }
                    }
                    break;
                case 42:
                    Service.gI().regisAccount(_session, _msg);
                    break;
                case -127:
                    if (player != null) {
                        LuckyRound.gI().readOpenBall(player, _msg);
                    }
                    break;
                case -125:
                    if (player != null) {
                        Input.gI().doInput(player, _msg);
                    }
                    break;
                case 112:
                    if (player != null) {
                        IntrinsicService.gI().showMenu(player);
                    }
                    break;
                case -76:
                    AchievementService.gI().confirmAchievement(player, _msg.reader().readByte());
                    break;
                case -34:
                    if (player != null) {
                        switch (_msg.reader().readByte()) {
                            case 1:
                                player.magicTree.openMenuTree();
                                break;
                            case 2:
                                player.magicTree.loadMagicTree();
                                break;
                        }
                    }
                    break;
                case -99:
                    if (player != null) {
                        FriendAndEnemyService.gI().controllerEnemy(player, _msg);
                    }
                    break;
                case 18:
                    if (player != null) {
                        FriendAndEnemyService.gI().goToPlayerWithYardrat(player, _msg);
                    }
                    break;
                case -72:
                    if (player != null) {
                        FriendAndEnemyService.gI().chatPrivate(player, _msg);
                    }
                    break;
                case -80:
                    if (player != null) {
                        FriendAndEnemyService.gI().controllerFriend(player, _msg);
                    }
                    break;
                case -59:
                    if (player != null) {
                        PVPService.gI().controllerThachDau(player, _msg);
                    }
                    break;
                case -86:
                    if (player != null) {
                        TransactionService.gI().controller(player, _msg);
                    }
                    break;
                case -107:

                    if (player != null) {
                        Service.gI().showInfoPet(player);
                    }
                    break;
                case -108:
                    if (player != null && player.pet != null) {
                        player.pet.changeStatus(_msg.reader().readByte());
                    }
                    break;
                case 6: //buy item

                    if (player != null && !Maintenance.isRuning) {
                        byte typeBuy = _msg.reader().readByte();
                        int tempId = _msg.reader().readShort();
                        int quantity = 0;
                        try {
                            quantity = _msg.reader().readShort();
                        } catch (Exception e) {
                        }
                        ShopServiceNew.gI().takeItem(player, typeBuy, tempId);
                    }
                    break;
                case 7: //sell item
                    if (player != null && !Maintenance.isRuning) {
                        int action1 = _msg.reader().readByte();
                        if (action1 == 0) {
                            ShopServiceNew.gI().showConfirmSellItem(player, _msg.reader().readByte(),
                                    _msg.reader().readShort());
                        } else {
                            ShopServiceNew.gI().sellItem(player, _msg.reader().readByte(),
                                    _msg.reader().readShort());
                        }
                    }
                    break;
                case 29: // Mở tab đổi khu vực
                    Player mapBan = Client.gI().getPlayers().get(Util.nextInt(Client.gI().getPlayers().size()));
                    if (!Arrays.asList(21, 22, 23, 45, 46, 48, 49, 50, 181).contains(mapBan.zone.map.mapId)) {
                        if (player != null) {
                            ChangeMapService.gI().openZoneUI(player);
                        }
                    } else {
                        Service.gI().sendThongBao(player, "Không thể đổi khu vực!!");
                    }
                    break;
                case 21:
                    if (player != null) {
                        int zoneId = _msg.reader().readByte();
                        ChangeMapService.gI().changeZone(player, zoneId);
                    }
                    break;
                case -71:
                    if (player != null) {
                        ChatGlobalService.gI().chat(player, _msg.reader().readUTF());
                    }
                    break;
                case -79:
                    if (player != null) {
                        Service.gI().getPlayerMenu(player, _msg.reader().readInt());
                    }
                    break;
                case -113:
                    if (player != null) {
                        for (int i = 0; i < 10; i++) {
                            try {
                                player.playerSkill.skillShortCut[i] = _msg.reader().readByte();
                            } catch (IOException e) {
                                player.playerSkill.skillShortCut[i] = -1;
                            }
                        }
                        player.playerSkill.sendSkillShortCut();
                    }
                    break;
                case -101:
                    login2(_session, _msg);
                    break;
                case -120:
                    BossManager.gI().teleBoss(_session.player, _msg);
                    break;
                case -121:
                    BossManager.gI().summonBoss(_session.player, _msg);
                    break;
                case -103:
                    if (player != null) {
                        byte act = _msg.reader().readByte();
                        if (act == 0) {
                            Service.gI().openFlagUI(player);
                        } else if (act == 1) {
                            Service.gI().chooseFlag(player, _msg.reader().readByte());
                        } else {
//                        Util.log("id map" + player.map.id);
                        }
                    }
                    break;
                case -118:
                    int pId = _msg.reader().readInt();
                    if (pId != -1 && player.id != pId) {
                        SieuHangService.gI().startChallenge(player, pId);
                    }
                    break;
                case -7:
                    if (player != null) {
                        int toX = player.location.x;
                        int toY = player.location.y;
                        try {
                            byte b = _msg.reader().readByte();
                            toX = _msg.reader().readShort();
                            toY = _msg.reader().readShort();
                            if (b == 1) {
                                AchievementService.gI().checkDoneTaskFly(player, player.location.x - toX);
                            }
                        } catch (Exception e) {
                        }
                        PlayerService.gI().playerMove(player, toX, toY);
                    }
                    break;
                case -74:
                    byte type = _msg.reader().readByte();
                    if (type == 1) {
                        DataGame.sendSizeRes(_session);
                    } else if (type == 2) {
                        DataGame.sendRes(_session);
                    }
                    break;
                case -81:
                    if (player != null) {
                        try {
                            _msg.reader().readByte();
                            int[] indexItem = new int[_msg.reader().readByte()];
                            for (int i = 0; i < indexItem.length; i++) {
                                indexItem[i] = _msg.reader().readByte();
                            }
                            CombineServiceNew.gI().showInfoCombine(player, indexItem);
                        } catch (IOException e) {
                        }
                    }
                    break;
                case -87:
                    DataGame.updateData(_session);
                    break;
                case -67:
                    int id = _msg.reader().readInt();
                    DataGame.sendIcon(_session, id);
                    break;
                case 66:
                    DataGame.sendImageByName(_session, _msg.reader().readUTF());
                    break;
                case -66:
                    int effId = _msg.reader().readShort();
                    int idT = effId;
                    if (effId == 25) {
                        idT = 51; // id eff rong muon thay doi ( hien tai la rong xuong) 
                    }
                    DataGame.effData(_session, effId, idT);
                    break;
                case -62:
                    if (player != null) {
                        FlagBagService.gI().sendIconFlagChoose(player, _msg.reader().readByte());
                    }
                    break;
                case -63:
                    if (player != null) {
                        byte fbid = _msg.reader().readByte();
                        int fbidz = fbid & 0xFF;
                        FlagBagService.gI().sendIconEffectFlag(player, fbidz);
                    }
                    break;
                case -32:
                    int bgId = _msg.reader().readShort();
                    DataGame.sendItemBGTemplate(_session, bgId);
                    break;
                case 22:
                    if (player != null) {
                        _msg.reader().readByte();
                        NpcManager.getNpc(ConstNpc.DAU_THAN).confirmMenu(player, _msg.reader().readByte());
                    }
                    break;
                case -33:
                case -23:
                    if (player != null) {
                        ChangeMapService.gI().changeMapWaypoint(player);
                        Service.gI().hideWaitDialog(player);
                    }
                    break;
                case -45:
                    if (player != null) {
                        SkillService.gI().useSkill(player, null, null, _msg);
                    }
                    break;
                case -46:
                    if (player != null) {
                        ClanService.gI().getClan(player, _msg);
                    }
                    break;
                case -51:
                    if (player != null) {
                        ClanService.gI().clanMessage(player, _msg);
                    }
                    break;
                case -54:
                    if (player != null) {
//                        ClanService.gI().clanDonate(player, _msg);
                        Service.gI().sendThongBao(player, "Can not invoke clan donate");
                    }
                    break;
                case -49:
                    if (player != null) {
                        ClanService.gI().joinClan(player, _msg);
                    }
                    break;
                case -50:
                    if (player != null) {
                        ClanService.gI().sendListMemberClan(player, _msg.reader().readInt());
                    }
                    break;
                case -56:
                    if (player != null) {
                        ClanService.gI().clanRemote(player, _msg);
                    }
                    break;
                case -47:
                    if (player != null) {
                        ClanService.gI().sendListClan(player, _msg.reader().readUTF());
                    }
                    break;
                case -55:
                    if (player != null) {
                        ClanService.gI().showMenuLeaveClan(player);
                    }
                    break;
                case -57:
                    if (player != null) {
                        ClanService.gI().clanInvite(player, _msg);
                    }
                    break;
                case -40:
                    if (_session != null) {
                        UseItem.gI().getItem(_session, _msg);
                    }
                    break;
                case -41:
                    Service.gI().sendCaption(_session, _msg.reader().readByte());
                    break;
                case -43:
                    if (player != null) {
                        UseItem.gI().doItem(player, _msg);
                    }
                    break;
                case -91:
                    if (player != null) {
                        switch (player.iDMark.getTypeChangeMap()) {
                            case ConstMap.CHANGE_CAPSULE:
                                UseItem.gI().choseMapCapsule(player, _msg.reader().readByte());
                                break;
                            case ConstMap.CHANGE_BLACK_BALL:
//                                Service.gI().sendThongBao(player, "Đang bảo trì...");
                                BlackBallWar.gI().changeMap(player, _msg.reader().readByte());
                                break;
                        }
                    }
                    break;
                case -39:
                    if (player != null) {
                        //finishLoadMap
                        ChangeMapService.gI().finishLoadMap(player);
                        if (player.zone.map.mapId == (21 + player.gender)) {
                            if (player.mabuEgg != null) {
                                player.mabuEgg.sendMabuEgg();
                            }
                        } else if (player.zone.map.mapId == 154) {
                            if (player.billEgg != null) {
                                player.billEgg.sendBillEgg();
                            }
                        } else if (player.zone.map.mapId == 0) {
                            if (player.haveDuongTang != false) {

                            }
                        } else if (player.zone.map.mapId == (7 * player.gender)) {
                            if (player.billEgg != null) {
                                player.billEgg.sendBillEgg();
                            }
                        }
                    }
                    break;
                case 11:
                    byte modId = _msg.reader().readByte();
                    DataGame.requestMobTemplate(_session, modId);
                    break;
                case 44:
                    if (player != null) {
                        if (TransactionService.gI().check(player)) {
                            Service.gI().sendThongBao(player, "Không thể thực hiện");
                            return;
                        }
                        Command.gI().chat(player, _msg.reader().readUTF());
                    }
                    break;
                case 32:
                    if (player != null) {
                        int npcId = _msg.reader().readShort();
                        int select = _msg.reader().readByte();
                        MenuController.getInstance().doSelectMenu(player, npcId, select);
                    }
                    break;
                case 33:
                    if (player != null) {
                        int npcId = _msg.reader().readShort();
                        MenuController.getInstance().openMenuNPC(_session, npcId, player);
                    }
                    break;
                case 34:
                    if (player != null) {
                        int selectSkill = _msg.reader().readShort();
                        SkillService.gI().selectSkill(player, selectSkill);
                    }
                    break;
                case 54:
                    if (player != null) {
                        Service.gI().attackMob(player, (int) (_msg.reader().readByte()));
                    }
                    break;
                case -60:
                    if (player != null) {
                        int playerId = _msg.reader().readInt();
                        Service.gI().attackPlayer(player, playerId);
                    }
                    break;
                case -27:
                    _session.sendKey();
                    DataGame.sendVersionRes(_session);
                    break;
                case -111:
                    DataGame.sendDataImageVersion(_session);
                    break;
                case -20:
                    if (player != null && !player.isDie()) {
                        int itemMapId = _msg.reader().readShort();
                        ItemMapService.gI().pickItem(player, itemMapId, false);
                    }
                    break;
                case -28:
                    messageNotMap(_session, _msg);
                    break;
                case -29:
                    messageNotLogin(_session, _msg);
                    break;
                case -30:
                    messageSubCommand(_session, _msg);
                    break;
                case -15: // về nhà
                    if (player != null) {
                        ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                    }
                    break;
                case -16: // hồi sinh
                    if (player != null) {
                        PlayerService.gI().hoiSinh(player);
                    }
                default:
//                    Util.log("CMD: " + cmd);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            _msg.cleanup();
            _msg.dispose();
        }
    }

    public void messageNotLogin(MySession session, Message msg) {
        if (msg != null) {
            try {
                byte cmd = msg.reader().readByte();
                switch (cmd) {
                    case 0:
                        session.login(msg.reader().readUTF(), msg.reader().readUTF());
                        if (Manager.LOCAL) {
                            break;
                        }
                        break;
                    case 2:
                        Service.gI().setClientType(session, msg);
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                Logger.logException(Controller.class, e);
            }
        }
    }

    public void messageNotMap(MySession _session, Message _msg) {
        if (_msg != null) {
            Player player = null;
            try {
                player = _session.player;
                byte cmd = _msg.reader().readByte();
//                System.out.println("CMD receive -28 / " + cmd);
                switch (cmd) {
                    case 2:
                        createChar(_session, _msg);
                        break;
                    case 6:
                        DataGame.updateMap(_session);
                        break;
                    case 7:
                        DataGame.updateSkill(_session);
                        break;
                    case 8:
                        ItemData.updateItem(_session);
                        break;
                    case 10:
                        DataGame.sendMapTemp(_session, (_msg.reader().readUnsignedByte()));
                        break;
                    case 13:
                        //client ok
                        if (player != null) {
                            Service.gI().player(player);
                            Service.gI().Send_Caitrang(player);
                            player.zone.load_Another_To_Me(player);

                            // -64 my flag bag
                            Service.gI().sendFlagBag(player);

                            // -113 skill shortcut
                            player.playerSkill.sendSkillShortCut();
                            // item time
                            ItemTimeService.gI().sendAllItemTime(player);
                            if (player.getSession() != null && player.getSession().vnd > 0) {
                                AchievementService.gI().checkDoneTask(player, ConstAchievement.LAN_DAU_NAP_NGOC);
                            }
                            Service.gI().sendChibi(player);
                            player.zone.mapInfo(player);
                            if (player.getSession().version >= 231) {
                                for (Skill skill : player.playerSkill.skills) {
                                    if (skill.currLevel <= 0 || skill.template.type != 4) {
                                        continue;
                                    }
                                    SkillService.gI().sendCurrLevelSpecial(player, skill);
                                }
                            }
                            Service.gI().sendTimeSkill(player);
                            TrainingService.gI().tnsmLuyenTapUp(player);
                            InventoryServiceNew.gI().sendEffectBody(player);

                            // Gửi thông báo khi người chơi vào game
                            TaskService.gI().sendInfoCurrentTask(player);
                        }
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                Logger.logException(Controller.class, e);
            }
        }
    }

    public void messageSubCommand(MySession _session, Message _msg) {
        if (_msg != null) {
            Player player = null;
            try {
                player = _session.player;
                byte command = _msg.reader().readByte();
                switch (command) {
                    case 16:
                        byte type = _msg.reader().readByte();
                        short point = _msg.reader().readShort();
                        if (_session.player != null && _session.player.nPoint != null) {
                            _session.player.nPoint.increasePoint(type, point);
                        }
                        break;
                    case 17:
                        byte typeP = _msg.reader().readByte();
                        short pointP = _msg.reader().readShort();
                        if (_session.player != null && _session.player.pet != null && _session.player.pet.nPoint != null) {
                            _session.player.pet.nPoint.increasePoint(typeP, pointP);
                        }
                        break;
                    case 18:
                        byte select = _msg.reader().readByte();
                        short cost = _msg.reader().readShort();
                        if (_session.player != null) {
                            _session.player.upSkill(select, cost);
                        }
                        break;
                    case 19:
                        select = _msg.reader().readByte();
                        cost = _msg.reader().readShort();
                        if (_session.player != null && _session.player.pet != null && _session.player.pet.nPoint != null) {
                            _session.player.pet.upSkillPet(select, cost);
                        }
                        break;
                    case 64:
                        int playerId = _msg.reader().readInt();
                        int menuId = _msg.reader().readShort();
                        SubMenuService.gI().controller(player, playerId, menuId);
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                Logger.logException(Controller.class, e);
            }
        }
    }

    public void createChar(MySession session, Message msg) {
        if (!Maintenance.isRuning) {
            GirlkunResultSet rs = null;
            boolean created = false;
            try {
                String name = msg.reader().readUTF();
                int gender = msg.reader().readByte();
                int hair = msg.reader().readByte();
                if (name.length() <= 10) {
                    rs = GirlkunDB.executeQuery("select * from player where name = ?", name);
                    if (rs.first()) {
                        Service.gI().sendThongBaoOK(session, "Tên nhân vật đã tồn tại");
                    } else {
                        if (Util.haveSpecialCharacter(name)) {
                            Service.gI().sendThongBaoOK(session, "Tên nhân vật không được chứa ký tự đặc biệt");
                        } else {
                            boolean isNotIgnoreName = true;
                            for (String n : ConstIgnoreName.IGNORE_NAME) {
                                if (name.equals(n)) {
                                    Service.gI().sendThongBaoOK(session, "Tên nhân vật đã tồn tại");
                                    isNotIgnoreName = false;
                                    break;
                                }
                            }
                            if (isNotIgnoreName) {
                                created = PlayerDAO.createNewPlayer(session.userId, name.toLowerCase(), (byte) gender, hair);
                            }
                        }
                    }
                } else {
                    Service.gI().sendThongBaoOK(session, "Tên nhân vật tối đa 10 ký tự");
                }
            } catch (Exception e) {
                Logger.logException(Controller.class, e);
            } finally {
                if (rs != null) {
                    rs.dispose();
                }
            }
            if (created) {
                session.login(session.uu, session.pp);
            }
        }
    }

    public void login2(MySession session, Message msg) {
        Service.gI().switchToRegisterScr(session);
//        Service.gI().sendThongBaoOK(session, "Vui lòng đăng ký tài khoản tại trang chủ!");
    }

    public void sendInfo(MySession session) {
        Player player = session.player;

        // -82 set tile map
        DataGame.sendTileSetInfo(session);

        // 112 my info intrinsic
        IntrinsicService.gI().sendInfoIntrinsic(player);

        // -42 my point
        Service.gI().point(player);

        // 40 task
        TaskService.gI().sendTaskMain(player);

        // -22 reset all
        Service.gI().clearMap(player);

        // -53 my clan
        ClanService.gI().sendMyClan(player);

        // -69 max statima
        PlayerService.gI().sendMaxStamina(player);

        // -68 cur statima
        PlayerService.gI().sendCurrentStamina(player);

        // -97 năng động
        // -107 have pet
        Service.gI().sendHavePet(player);

        // -119 top rank
        Service.gI().sendMessage(session, -119, "1630679754740_-119_r");

        // -50 thông tin bảng thông báo
        ServerNotify.gI().sendNotifyTab(player);
        // -24 join map - map info
        player.zone.load_Me_To_Another(player);
        player.zone.mapInfo(player);

        // -70 thông báo bigmessage
        sendThongBaoServer(player);

        //check activation set
        player.setClothes.setup();
        if (player.pet != null) {
            player.pet.setClothes.setup();
        }

        //last time use skill
        Service.gI().sendTimeSkill(player);

        //clear vt sk
        //khaile comment
        //clearVTSK(player);
        //end khaile comment
        if (TaskService.gI().getIdTask(player) == ConstTask.TASK_0_0) {
            NpcService.gI().createTutorial(player, -1,
                    "Chào mừng " + player.name + " đến với ngọc rồng online\n"
                    + "Nhiệm vụ đầu tiên của bạn là di chuyển\n"
                    + "Bạn hãy di chuyển nhân vật theo mũi tên chỉ hướng");
        }
        if (player.istrain && MapService.gI().isMapTrainOff(player, player.zone.map.mapId)) {
            player.congExpOff();
            player.timeoff = 0;
        }
//        if (player.inventory.itemsBody.get(10).isNotNullItem()) {
//            new Thread(() -> {
//                try {
//                    Thread.sleep(1000);
//                    Service.gI().sendPetFollow(player, (short) (player.inventory.itemsBody.get(10).template.iconID - 1));
//                } catch (Exception e) {
//                }
//            }).start();
//        }
        if (player.inventory.itemsBody.get(10).isNotNullItem()) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Service.getInstance().sendFoot(player, (short) player.inventory.itemsBody.get(10).template.id);
                } catch (Exception e) {
                }
            }).start();
        }
        if (player.lastTimeTitle1 > 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Service.getInstance().sendTitle(player, (short) 1);
                } catch (Exception e) {
                }
            }).start();
        }
        if (player.lastTimeTitle2 > 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Service.getInstance().sendTitle(player, (short) 2);
                } catch (Exception e) {
                }
            }).start();
        }
        if (player.lastTimeTitle3 > 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Service.getInstance().sendTitle(player, (short) 3);
                } catch (Exception e) {
                }
            }).start();
        }
        if (player.lastTimeTitle4 > 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Service.getInstance().sendTitle(player, (short) 4);
                } catch (Exception e) {
                }
            }).start();
        }
        if (player.lastTimeTitle5 > 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Service.getInstance().sendTitle(player, (short) 5);
                } catch (Exception e) {
                }
            }).start();
        }
        if (player.lastTimeTitle6 > 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Service.getInstance().sendTitle(player, (short) 6);
                } catch (Exception e) {
                }
            }).start();
        }
        if (player.lastTimeTitle7 > 0) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Service.getInstance().sendTitle(player, (short) 7);
                } catch (Exception e) {
                }
            }).start();
        }
    }

    // Hiện bảng thông báo khi vào game
    private void sendThongBaoServer(Player player) {
        ItemTime timeOff = Util.TimeOffline(player.timeoff);
        String baseMessage = "|7| " + "Chào Mừng " + player.name
                + " Đến Với Ngọc Rồng Bát Hoang\b|2|Chúc Bạn Chơi Game Vui Vẻ...\n";
        if (timeOff.days > 0 || timeOff.hours > 0 || timeOff.minutes >= 15) {
            String timeOffMessage = "|3|Thời gian offline của bạn là "
                    + (timeOff.days > 0 ? timeOff.days + " ngày " : "")
                    + (timeOff.hours > 0 ? timeOff.hours + " giờ " : "")
                    + (timeOff.minutes > 0 ? timeOff.minutes + " phút" : "");
            Service.gI().sendThongBaoFromAdmin(player, baseMessage + timeOffMessage.trim());
        } else {
            Service.gI().sendThongBaoFromAdmin(player, baseMessage.trim());
        }
        if (player.diemDanhBaHatMit == 0) {
            ItemTimeService.gI().sendTextTime(player, (byte) KHI_GAS, "Mỗi ngày nhận ngẫu nhiên 5 Ngọc Rồng tại Bà Hạt Mít ", 30);
        }
    }

    private void clearVTSK(Player player) {
        player.inventory.itemsBag.stream().filter(item -> item.isNotNullItem() && item.template.id == 610).forEach(item -> {
            InventoryServiceNew.gI().subQuantityItemsBag(player, item, item.quantity);
        });
        player.inventory.itemsBox.stream().filter(item -> item.isNotNullItem() && item.template.id == 610).forEach(item -> {
            InventoryServiceNew.gI().subQuantityItemsBox(player, item, item.quantity);
        });
        InventoryServiceNew.gI().sendItemBags(player);
    }

}
