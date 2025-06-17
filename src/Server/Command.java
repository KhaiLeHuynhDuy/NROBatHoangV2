/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import BossMain.BossManager;
import Consts.ConstNpc;
import Services.func.ChangeMapService;
import Services.func.Input;
import Item.Item;
import MaQuaTang.MaQuaTangManager;
import MiniGame.LuckyNumber.LuckyNumber;
import Player.Pet;
import Player.Player;
import Services.InventoryServiceNew;
import Services.ItemService;
import Services.NpcService;
import Services.PetService;
import Services.Service;
import Services.SkillService;
import Skill.Skill;
import Utils.Logger;
import Utils.Util;
import network.Message;
import com.girlkun.network.server.GirlkunSessionManager;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.Set;
import network.SessionManager;

/**
 *
 * @author Administrator
 */
public class Command {

    private static Command instance;

    public static Command gI() {
        if (instance == null) {
            instance = new Command();
        }
        return instance;
    }

    public void chat(Player player, String text) {
        if (!check(player, text)) {
            Service.gI().chat(player, text);
        }
    }

    public boolean check(Player player, String text) {
        if (player.isAdmin()) {
            if (text.equals("tt")) {
                NpcService.gI().createMenuConMeo(player, ConstNpc.TTMEM, 11039,
                        "|7|Nhân vật: " + player.name
                        + "\n|8|Số Lần Chuyển Sinh: " + player.NguHanhSonPoint
                        + "\n|3|Số dư: " + Util.formatNumber(player.getSession().coinBar)
                        + "\n|2|[Trạng thái tài khoản : " + (!player.getSession().actived ? " Chưa MTV]" : " Đã MTV]")
                        + "\n|7|Nhiệm vụ hiện tại: " + player.playerTask.taskMain.name,
                        new String[]{"Chi Tiết", "Thông Tin\n Sư Phụ", "Thông Tin\n Đệ Tử", "Đóng"});
            }
            if (player.getSession() != null && player.isAdmin()) {
                if (text.equals("logskill")) {
                    Service.gI().sendThongBao(player, player.playerSkill.skillSelect.coolDown + "");
                }
                if (text.equals("client")) {
                    Client.gI().show(player);
                } else if (text.equals("m")) {
                    Service.gI().sendThongBao(player, "Map " + player.zone.map.mapName + " (" + player.zone.map.mapId + ")");

                } else if (text.equals("vt")) {
                    Service.gI().sendThongBao(player, player.location.x + " - " + player.location.y + "\n"
                            + player.zone.map.yPhysicInTop(player.location.x, player.location.y));
                } else if (text.equals("listgift")) {
                    MaQuaTangManager manager = MaQuaTangManager.gI();
                    manager.showAllGiftCodes(player);

                } else if (text.equals("nrnm")) {
                    Service.gI().activeNamecShenron(player);

                } else if (text.equals("time")) {
                    Service.gI().sendThongBao(player, "Time start server: " + ServerManager.timeStart + "\n");

                } else if (text.equals("r")) { // hồi all skill, Ki
                    Service.getInstance().releaseCooldownSkill(player);

                } else if (text.equals("skillxd")) {
                    SkillService.gI().learSkillSpecial(player, Skill.LIEN_HOAN_CHUONG);

                } else if (text.equals("skilltd")) {
                    SkillService.gI().learSkillSpecial(player, Skill.SUPER_KAME);

                } else if (text.equals("skillnm")) {
                    SkillService.gI().learSkillSpecial(player, Skill.MA_PHONG_BA);

                } else if (text.equals("mapbroly")) {
                    BossManager.gI().showListBossBroly(player);

                } else if (text.equals("a")) {
                    BossManager.gI().showListBoss(player);
                } else if (text.startsWith("kq")) {
                    Service.gI().sendThongBao(player, "Kết quả Lucky Round tiếp theo là: " + LuckyNumber.RESULT);

                }
                com.sun.management.OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                long totalPhysicalMemorySize = operatingSystemMXBean.getTotalPhysicalMemorySize();
                long freePhysicalMemorySize = operatingSystemMXBean.getFreePhysicalMemorySize();
                long usedPhysicalMemory = totalPhysicalMemorySize - freePhysicalMemorySize;
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String cpuUsage = decimalFormat.format(operatingSystemMXBean.getSystemCpuLoad() * 100);
                String usedPhysicalMemoryStr = decimalFormat.format((double) usedPhysicalMemory / (1024 * 1024 * 1024));
                if (text.equals("ad")) {
                    NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_ADMIN, -1, "|7| ADMIN \n •⊹٭Ngọc Rồng One Puch Man٭⊹•\b|2| •⊹٭♥Bùi Kim Trường♥٭⊹•\b|4| Người Đang Chơi: " + Client.gI().getPlayers().size() + "\n" + "|8|Current thread: " + (Thread.activeCount())
                            + " : SeeSion " + SessionManager.gI().getSessions().size()
                            + "\n|7|CPU : " + cpuUsage + "/100%" + " ♥ " + "RAM : " + usedPhysicalMemoryStr 
                            + "\n|7|Time start server: " + ServerManager.timeStart,
                            "Menu Admin", "Call Boss", "Buff Item", "Đóng");

                } else if (text.startsWith("tndt")) {
                    try {
                        long power = Long.parseLong(text.replaceAll("tndt", ""));
                        Service.gI().addSMTN(player.pet, (byte) 2, power, false);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (text.startsWith("tnsp")) {
                    try {
                        long power = Long.parseLong(text.replaceAll("tnsp", ""));
                        Service.gI().addSMTN(player, (byte) 2, power, false);

                    } catch (Exception e) {
                    }

                } else if (text.startsWith("upp")) {
                    try {
                        long power = Long.parseLong(text.replaceAll("upp", ""));
                        Service.gI().addSMTN(player.pet, (byte) 2, power, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (text.startsWith("up")) {
                    try {
                        long power = Long.parseLong(text.replaceAll("up", ""));
                        Service.gI().addSMTN(player.pet, (byte) 2, power, false);
                    } catch (Exception e) {
                    }

                } else if (text.startsWith("m")) {
                    try {
                        int mapId = Integer.parseInt(text.replace("m", ""));
                        ChangeMapService.gI().changeMapInYard(player, mapId, -1, -1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (text.startsWith("i")) {
                    try {
                        String[] item = text.replace("i", "").split(" ");
                        if (Short.parseShort(item[0]) <= 2118) {
                            Item it = ItemService.gI().createNewItem((short) Short.parseShort(item[0]));
                            if (it != null && item.length == 1) {
                                InventoryServiceNew.gI().addItemBag(player, it);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Đã nhận được " + it.template.name);
                            } else if (it != null && item.length == 2 && Client.gI().getPlayer(String.valueOf(item[1])) == null) {
                                it.quantity = Integer.parseInt(item[1]);
                                InventoryServiceNew.gI().addItemBag(player, it);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Đã nhận được x" + Integer.valueOf(item[1]) + " " + it.template.name);
                            } else if (it != null && item.length == 2 && Client.gI().getPlayer(String.valueOf(item[1])) != null) {
                                String name = String.valueOf(item[1]);
                                InventoryServiceNew.gI().addItemBag(Client.gI().getPlayer(name), it);
                                InventoryServiceNew.gI().sendItemBags(Client.gI().getPlayer(name));
                                Service.gI().sendThongBao(player, "Đã buff " + it.template.name + " đến player " + name);
                                Service.gI().sendThongBao(Client.gI().getPlayer(name), "Đã nhận được " + it.template.name);
                            } else if (it != null && item.length == 3 && Client.gI().getPlayer(String.valueOf(item[2])) != null) {
                                String name = String.valueOf(item[2]);
                                it.quantity = Integer.parseInt(item[1]);
                                InventoryServiceNew.gI().addItemBag(Client.gI().getPlayer(name), it);
                                InventoryServiceNew.gI().sendItemBags(Client.gI().getPlayer(name));
                                Service.gI().sendThongBao(player, "Đã buff x" + Integer.valueOf(item[1]) + " " + it.template.name + " đến player " + name);
                                Service.gI().sendThongBao(Client.gI().getPlayer(name), "Đã nhận được x" + Integer.valueOf(item[1]) + " " + it.template.name);
                            } else {
                                Service.gI().sendThongBao(player, "Không tìm thấy player");
                            }
                        } else {
                            Service.gI().sendThongBao(player, "Không tìm thấy item");
                        }
                    } catch (NumberFormatException e) {
                        Service.gI().sendThongBao(player, "Không tìm thấy player");
                    }

                } else if (text.equals("buff")) {
                    Input.gI().createFormSenditem(player);
                } else if (text.equals("ban")) {
                    Input.gI().createFormSenditem1(player);
                } else if (text.equals("thread")) {
                    Service.gI().sendThongBao(player, "Current thread: " + Thread.activeCount());
                    Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

                } else if (text.startsWith("s")) {
                    try {
                        player.nPoint.speed = (byte) Integer.parseInt(text.substring(1));
                        Service.gI().point(player);

                    } catch (Exception e) {
                    }
                }
            }

            if (text.startsWith("ten con la ")) {
                PetService.gI().changeNamePet(player, text.replaceAll("ten con la ", ""));
            }

            if (player.pet != null) {
                if (text.equals("di theo") || text.equals("follow")) {
                    player.pet.changeStatus(Pet.FOLLOW);
                } else if (text.equals("bao ve") || text.equals("protect")) {
                    player.pet.changeStatus(Pet.PROTECT);
                } else if (text.equals("tan cong") || text.equals("attack")) {
                    player.pet.changeStatus(Pet.ATTACK);
                } else if (text.equals("ve nha") || text.equals("go home")) {
                    player.pet.changeStatus(Pet.GOHOME);
                } else if (text.equals("bien hinh")) {
                    player.pet.transform();
                }
            }
        }
        return false;
    }
}
