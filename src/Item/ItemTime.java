package Item;

import Player.NPoint;
import Player.Player;
import Services.Service;
import Utils.Util;
import Services.ItemTimeService;


public class ItemTime {
    
    //id item text
    public static final byte DOANH_TRAI = 0;
    public static final byte BAN_DO_KHO_BAU = 1;
    public static final byte KHI_GAS = 2;
    public static final byte CON_DUONG_RAN_DOC = 3;
    public static final byte TIME_KEO_BUA_BAO = 8;
    public static final int TIME_ITEM = 600000;
    public static final int TIME_OPEN_POWER = 86400000;
    public static final int TIME_MAY_DO = 1800000;
    public static final int TIME_MAY_DO2 = 1800000;
    public static final int TIME_MAY_DO3 = 1800000;
    public static final int TIME_EAT_MEAL = 600000;
    public static final int MAY_DO_KHO_BAU = 900000;

    private Player player;

    public boolean isUseBoHuyet;
    public boolean isUseBoHuyet2;
    public boolean isUseBoKhi;
    public boolean isUseBoKhi2;
    public boolean isUseGiapXen;
    public boolean isUseGiapXen2;
    public boolean isUseCuongNo;
    public boolean isUseCuongNo2;
    public boolean isUseAnDanh;
    public boolean isUseAnDanh2;
    
    public boolean isUseCuaRangMe;
    
    public boolean isUseDuoiKhi;
    public long lastTimeDuoiKhi;
    
    public long lastTimeBoHuyet;
    public long lastTimeBoKhi;
    public long lastTimeGiapXen;
    public long lastTimeCuongNo;
    public long lastTimeAnDanh;
    
    public boolean isUseBoHuyetSC;
    public boolean isUseBoKhiSC;
    public boolean isUseGiapXenSC;
    public boolean isUseCuongNoSC;
    public boolean isUseAnDanhSC;

    public long lastTimeBoHuyet2;
    public long lastTimeBoKhi2;
    public long lastTimeGiapXen2;
    public long lastTimeCuongNo2;
    public long lastTimeAnDanh2;
    
    public long lastTimeBoHuyetSC;
    public long lastTimeBoKhiSC;
    public long lastTimeGiapXenSC;
    public long lastTimeCuongNoSC;
    public long lastTimeAnDanhSC;
    
    //lastime de chung 1 cai neu time = nhau
    public boolean isUseMayDo;
    public long lastTimeUseMayDo;
    public boolean isUseMayDo2;
    public long lastTimeUseMayDo2;
    public boolean isUseMayDo3;
    public long lastTimeUseMayDo3;
    
    public boolean isOpenPower;
    public long lastTimeOpenPower;

    public boolean isUseTDLT;
    public long lastTimeUseTDLT;
    public int timeTDLT;

    public boolean isEatMeal;
    public long lastTimeEatMeal;
    public int iconMeal;

    public boolean istrbsd;
    public boolean istrbhp;
    public boolean istrbki;
    public static final int TIME_TRB = 1800000;
    
    public long lastTimetrbsd;
    public long lastTimetrbhp;
    public long lastTimetrbki;
    
    public int days;
    public int hours;
    public int minutes;
    
    public boolean isUseMayDoKhoBau;
    public long lastTimeUseMayDoKhoBau;
    
    public boolean isUsex2De;
    public long lastTimex2De;
    
    public ItemTime(Player player) {
        this.player = player;
    }
    
    public ItemTime(int days, int hours, int minutes) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
    }

    public void update() {
        if (isUsex2De) {
            if (Util.canDoWithTime(lastTimex2De, MAY_DO_KHO_BAU)) {
                isUsex2De = false;
                Service.gI().point(player);
            }
        }
        if (isUseMayDoKhoBau) {
            if (Util.canDoWithTime(lastTimeUseMayDoKhoBau, MAY_DO_KHO_BAU)) {
                isUseMayDoKhoBau = false;
                Service.gI().point(player);
            }
        }
        if (isEatMeal) {
            if (Util.canDoWithTime(lastTimeEatMeal, TIME_EAT_MEAL)) {
                isEatMeal = false;
                Service.gI().point(player);
            }
        }
        if (isUseBoHuyet) {
            if (Util.canDoWithTime(lastTimeBoHuyet, TIME_ITEM)) {
                isUseBoHuyet = false;
                Service.gI().point(player);
            }
        }
        if (isUseBoKhi) {
            if (Util.canDoWithTime(lastTimeBoKhi, TIME_ITEM)) {
                isUseBoKhi = false;
                Service.gI().point(player);
            }
        }
        if (isUseGiapXen) {
            if (Util.canDoWithTime(lastTimeGiapXen, TIME_ITEM)) {
                isUseGiapXen = false;
            }
        }
        if (isUseCuongNo) {
            if (Util.canDoWithTime(lastTimeCuongNo, TIME_ITEM)) {
                isUseCuongNo = false;
                Service.gI().point(player);
            }
        }
        if (isUseAnDanh) {
            if (Util.canDoWithTime(lastTimeAnDanh, TIME_ITEM)) {
                isUseAnDanh = false;
            }
        }
        if (isUseDuoiKhi) {
            if (Util.canDoWithTime(lastTimeDuoiKhi, TIME_ITEM)) {
                isUseDuoiKhi = false;
                Service.gI().point(player);
            }
        }
        if (isUseBoHuyetSC) {
            if (Util.canDoWithTime(lastTimeBoHuyetSC, TIME_ITEM)) {
                isUseBoHuyetSC = false;
                Service.getInstance().point(player);
            }
        }
        if (isUseBoKhiSC) {
            if (Util.canDoWithTime(lastTimeBoKhiSC, TIME_ITEM)) {
                isUseBoKhiSC = false;
                Service.getInstance().point(player);
            }
        }
        if (isUseGiapXenSC) {
            if (Util.canDoWithTime(lastTimeGiapXenSC, TIME_ITEM)) {
                isUseGiapXenSC = false;
            }
        }
        if (isUseCuongNoSC) {
            if (Util.canDoWithTime(lastTimeCuongNoSC, TIME_ITEM)) {
                isUseCuongNoSC = false;
                Service.getInstance().point(player);
            }
        }
        if (isUseAnDanhSC) {
            if (Util.canDoWithTime(lastTimeAnDanhSC, TIME_ITEM)) {
                isUseAnDanhSC = false;
            }
        }
        if (isOpenPower) {
            if (Util.canDoWithTime(lastTimeOpenPower, TIME_OPEN_POWER)) {
                player.nPoint.limitPower++;
                if (player.nPoint.limitPower > NPoint.MAX_LIMIT) {
                    player.nPoint.limitPower = NPoint.MAX_LIMIT;
                }
                Service.gI().sendThongBao(player, "Giới hạn sức mạnh của bạn đã được tăng lên 1 bậc");
                isOpenPower = false;
            }
        }
        if (isUseMayDo) {
            if (Util.canDoWithTime(lastTimeUseMayDo, TIME_MAY_DO)) {
                isUseMayDo = false;
            }
        }
        if (isUseMayDo2) {
            if (Util.canDoWithTime(lastTimeUseMayDo2, TIME_MAY_DO2)) {
                isUseMayDo2 = false;
            }
        }
        if (isUseMayDo3) {
            if (Util.canDoWithTime(lastTimeUseMayDo3, TIME_MAY_DO3)) {
                isUseMayDo3 = false;
            }
        }
        if (isUseTDLT) {
            if (Util.canDoWithTime(lastTimeUseTDLT, timeTDLT)) {
                this.isUseTDLT = false;
                ItemTimeService.gI().sendCanAutoPlay(this.player);
            }
        }
                if (istrbsd) {
            if (Util.canDoWithTime(lastTimeEatMeal, TIME_TRB)) {
                istrbsd = false;
                Service.gI().point(player);
            }
        }
         
        if (istrbhp) {
            if (Util.canDoWithTime(lastTimeBoHuyet, TIME_TRB)) {
                istrbhp = false;
                Service.gI().point(player);
            }
        }
        
        if (istrbki) {
            if (Util.canDoWithTime(lastTimeBoKhi, TIME_TRB)) {
                istrbki = false;
                Service.gI().point(player);
            }
        }
    }
    
    public void dispose(){
        this.player = null;
    }
}
