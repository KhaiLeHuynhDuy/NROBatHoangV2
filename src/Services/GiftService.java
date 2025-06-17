package Services;

import MaQuaTang.MaQuaTangManager;
import Item.Item;
import Player.Player;
import Data.GetData;

import java.sql.Timestamp;
import java.util.ArrayList;

public class GiftService {
    
    private static GiftService i;
    
    private GiftService(){
        
    }
    
    public String code;
    public int idGiftcode;
    public int gold;
    public int gem;
    public int dayexits;
    public Timestamp timecreate;
    public ArrayList<Item> listItem = new ArrayList<>();
    public static ArrayList<GiftService> gifts = new ArrayList<>();
    
    public static GiftService gI(){
        if(i == null){
            i = new GiftService();
        }
        return i;
    }
   
    public void giftCode(Player player, String code) {
        MaQuaTangManager manager = MaQuaTangManager.gI();
        MaQuaTangManager.GiftCodeResult result = manager.processGiftCode((int) player.id, code);

        switch (result.errorCode) {
            case MaQuaTangManager.CODE_NOT_EXIST:
                sendError(player, "Code này không tồn tại!");
                break;
            case MaQuaTangManager.CODE_USED_UP:
                sendError(player, "Code này đã hết số lượng sử dụng!");
                break;
            case MaQuaTangManager.CODE_ALREADY_USED:
                sendError(player, "Ngươi đã nhập code này rồi!");
                break;
            case MaQuaTangManager.CODE_EXPIRED:
                sendError(player, "Code đã hết hạn!");
                break;
            case MaQuaTangManager.CODE_VALID:
                if (GetData.hasPlayerUsedGiftcode(player, code)) {
                    sendError(player, "Ngươi đã nhập code này rồi mà!");
                } else {
                    GetData.AddPlayerUseGiftCode(player, code);
                    InventoryServiceNew.gI().addItemGiftCodeToPlayer(player, result.giftCode);
                }
                break;
            default:
                sendError(player, "Lỗi không xác định");
        }
    }

    private void sendError(Player player, String message) {
        Service.gI().sendThongBaoOK(player, message);
    }

}
