package MaQuaTang;

import com.girlkun.database.GirlkunDB;
import Item.Item.ItemOption;
import Player.Player;
import Data.GetData;
import Services.NpcService;
import Services.Service;
import Utils.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MaQuaTangManager {
    
    public static final int CODE_NOT_EXIST = 1;
    public static final int CODE_USED_UP = 2;
    public static final int CODE_ALREADY_USED = 3;
    public static final int CODE_EXPIRED = 4;
    public static final int CODE_VALID = 0;
    
    public String name;
    public final ArrayList<MaQuaTang> listGiftCode = new ArrayList<>();
    private static MaQuaTangManager instance;
    
    public static MaQuaTangManager gI() {
        if (instance == null) {
            instance = new MaQuaTangManager();
        }
        return instance;
    }
    
    public List<MaQuaTang> getAllGiftCodes() {
        return new ArrayList<>(listGiftCode);
    }
    
    public static class GiftCodeResult {
        public MaQuaTang giftCode;
        public int errorCode;

        public GiftCodeResult(MaQuaTang giftCode, int errorCode) {
            this.giftCode = giftCode;
            this.errorCode = errorCode;
        }
    }
    
    public void init() {
        try(Connection con = GirlkunDB.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM giftcode");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MaQuaTang giftcode = new MaQuaTang();
                giftcode.code = rs.getString("code");
                giftcode.countLeft = rs.getInt("count_left");
                giftcode.datecreate = rs.getTimestamp("datecreate");
                giftcode.dateexpired = rs.getTimestamp("expired");
                JSONArray jar = (JSONArray) JSONValue.parse(rs.getString("detail"));
                if (jar != null) {
                    for (int i = 0; i < jar.size(); ++i) {
                        JSONObject jsonObj = (JSONObject) jar.get(i);
                        giftcode.detail.put(Integer.parseInt(jsonObj.get("id").toString()), Integer.parseInt(jsonObj.get("quantity").toString()));
                        jsonObj.clear();
                    }
                } 
                JSONArray option= (JSONArray) JSONValue.parse(rs.getString("itemoption"));
//                Logger.log("Done Code: " + giftcode.code + " -------------------" + option.toString());
                if(option != null){
                    for(int u = 0;u<option.size();u++){
                        JSONObject jsonobject = (JSONObject) option.get(u);
                        giftcode.option.add(new ItemOption(Integer.parseInt(jsonobject.get("id").toString()),Integer.parseInt(jsonobject.get("param").toString())));
                        jsonobject.clear();
                    }
                }
                listGiftCode.add(giftcode);
            }
            con.close();
        } catch (Exception erorlog) {}
    }   
      
    public void sizeList(Player pl){
        Service.gI().sendThongBao(pl, "" + MaQuaTang.class);
    }
    
    public GiftCodeResult processGiftCode(int idPlayer, String code) {
        for (MaQuaTang giftCode : listGiftCode) {
            if (giftCode.code.equals(code)) {
                if (giftCode.isUsedGiftCode(idPlayer)) {
                    return new GiftCodeResult(null, CODE_ALREADY_USED);
                }
                else if (giftCode.countLeft <= 0) {
                    return new GiftCodeResult(null, CODE_USED_UP);
                }
                else if (giftCode.timeCode()) {
                    return new GiftCodeResult(null, CODE_EXPIRED);
                }
                giftCode.addPlayerUsed(idPlayer);
                giftCode.countLeft -= 1;
                GetData.SaveCountLeftGiftCode(giftCode.countLeft, code);
                return new GiftCodeResult(giftCode, CODE_VALID);
            }
        }
        return new GiftCodeResult(null, CODE_NOT_EXIST);
    }
    
    public void checkInfomationGiftCode(Player p) {
        StringBuilder sb = new StringBuilder();
        for (MaQuaTang giftCode: listGiftCode) {
            sb.append("Code: ").append(giftCode.code).append(", Số lượng: ").append(giftCode.countLeft).append("\b").append(", Ngày tạo: ")
                    .append(giftCode.datecreate).append("Ngày hết hạn").append(giftCode.dateexpired);
        }
        NpcService.gI().createTutorial(p, 5073, sb.toString());
    }
    
    public void showAllGiftCodes(Player player) {
        MaQuaTangManager manager = MaQuaTangManager.gI();
        List<MaQuaTang> allGiftCodes = manager.getAllGiftCodes();

        if (allGiftCodes.isEmpty()) {
            sendError(player, "Không có GiftCode nào hiện có.");
        } else {
            StringBuilder message = new StringBuilder("Các GiftCode hiện có:\n");
            for (MaQuaTang giftCode : allGiftCodes) {
                message.append("Code: ").append(giftCode.code)
                       .append(" - Số lượng: ").append(giftCode.countLeft)
                       .append(" - ").append(giftCode.timeCode() ? "Đã Hết" : "Chưa Hết")
                       .append(" Hạn\n");
            }
            Service.gI().sendThongBaoOK(player, message.toString());
        }
    }

    private void sendError(Player player, String message) {
        Service.gI().sendThongBaoOK(player, message);
    }
    
}
