package PVP;

import Consts.ConstAchievement;
import Matches.PVP;
import Matches.TYPE_LOSE_PVP;
import Matches.TYPE_PVP;
import Player.Player;
import Services.AchievementService;
import Services.Service;
import Utils.Util;


public class ThachDau extends PVP {

    private int goldThachDau;
    private long goldReward;

    public ThachDau(Player p1, Player p2, int goldThachDau) {
        super(TYPE_PVP.THACH_DAU, p1, p2);
        this.goldThachDau = goldThachDau;
        this.goldReward = goldThachDau / 100 * 80;
    }

    @Override
    public void start() {
        if (this.p1.getSession().actived && this.p2.getSession().actived) {
        this.p1.inventory.gold -= this.goldThachDau;
        this.p2.inventory.gold -= this.goldThachDau;
        Service.gI().sendMoney(this.p1);
        Service.gI().sendMoney(this.p2);
        super.start();}
        else if (!this.p1.getSession().actived){
            Service.gI().sendThongBaoOK(this.p1, "Chưa Kích Hoạt Tài Khoản");
            Service.gI().sendThongBaoOK(this.p2, "Thằng Kia Nó Chưa Kích Hoạt Tài Khoản");}
        else if (!this.p2.getSession().actived){
            Service.gI().sendThongBaoOK(this.p2, "Chưa Kích Hoạt Tài Khoản");
            Service.gI().sendThongBaoOK(this.p1, "Thằng Kia Nó Chưa Kích Hoạt Tài Khoản");}
    }

    @Override
    public void finish() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void update() {
    }

    @Override
    public void reward(Player plWin) {
        plWin.inventory.gold += this.goldReward;
        Service.gI().sendMoney(plWin);
    }

    @Override
    public void sendResult(Player plLose, TYPE_LOSE_PVP typeLose) {
        if (typeLose == TYPE_LOSE_PVP.RUNS_AWAY) {
            Service.gI().sendThongBao(p1.equals(plLose) ? p2 : p1, "Đối thủ kiệt sức. Bạn thắng nhận được " + Util.formatNumber(this.goldReward) + " vàng");
            Service.gI().sendThongBao(p1.equals(plLose) ? p1 : p2, "Bạn bị xử thua vì kiệt sức");
            (p1.equals(plLose) ? p1 : p2).inventory.gold -= this.goldThachDau;
        } else if (typeLose == TYPE_LOSE_PVP.DEAD) {
            Service.gI().sendThongBao(p1.equals(plLose) ? p2 : p1, "Đối thủ kiệt sức. Bạn thắng nhận được " + Util.formatNumber(this.goldReward) + " vàng");
            Service.gI().sendThongBao(p1.equals(plLose) ? p1 : p2, "Bạn bị xử thua vì kiệt sức");
            (p1.equals(plLose) ? p1 : p2).inventory.gold -= this.goldThachDau;
        }
        Service.gI().sendMoney(p1.equals(plLose) ? p1 : p2);
        if (!p1.equals(plLose)) {
            AchievementService.gI().checkDoneTask(p1, ConstAchievement.TRAM_TRAN_TRAM_THANG);
        }
    }

}