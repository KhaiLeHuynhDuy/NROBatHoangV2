package ListMap;

import Maps.Zone;
import Player.Player;
import Services.Service;
import Services.func.ChangeMapService;
import Utils.Util;
import java.util.ArrayList;
import java.util.List;


public class DoanhTraiService {

    private static DoanhTraiService instance;

    public static DoanhTraiService gI() {
        if (DoanhTraiService.instance == null) {
            DoanhTraiService.instance = new DoanhTraiService();
        }
        return DoanhTraiService.instance;
    }

    public List<DoanhTrai> doanhTrais;

    private DoanhTraiService() {
        this.doanhTrais = new ArrayList<>();
        for (int i = 0; i < DoanhTrai.AVAILABLE; i++) {
            this.doanhTrais.add(new DoanhTrai(i));
        }
    }

    public void addMapDoanhTrai(int id, Zone zone) {
        this.doanhTrais.get(id).getZones().add(zone);
    }

    public void joinDoanhTrai(Player pl) {
        if (pl.clan == null) {
            Service.gI().sendThongBao(pl, "Không thể thực hiện");
            return;
        }
        if (pl.clan.haveGoneDoanhTrai && !Util.isAfterMidnight(pl.clan.lastTimeOpenDoanhTrai) && false) {
            Service.gI().sendThongBao(pl, "Vui lòng chờ đến ngày mai");
            return;
        }
//        if (pl.clanMember.getNumDateFromJoinTimeToToday() < 1) {
//            return;
//        }
        if (pl.clan.doanhTrai != null) {
            pl.lastTimeJoinDT = System.currentTimeMillis();
            pl.clan.doanhTrai.updateHPDame();
            ChangeMapService.gI().changeMapInYard(pl, 53, -1, 60);
            return;
        }
        DoanhTrai doanhTrai = null;
        for (DoanhTrai dt : this.doanhTrais) {
            if (dt.getClan() == null) {
                doanhTrai = dt;
                break;
            }
        }
        if (doanhTrai == null) {
            Service.gI().sendThongBao(pl, "Doanh trại đã đầy, hãy quay lại vào lúc khác!");
            return;
        }
        doanhTrai.openDoanhTrai(pl);
    }
}