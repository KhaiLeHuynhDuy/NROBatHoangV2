package Player;

import Consts.ConstNpc;
import Maps.Zone;
import NPC.Npc;
import Shop.Shop;
import lombok.Data;


@Data
public class IDMark {
    private long damePST;
    private boolean acpTrade;
    private int moneyKeoBuaBao;
    private long timePlayKeoBuaBao;

    private byte keoBuaBaoPlayer;
    private byte keoBuaBaoServer;

    private boolean isGemCSMM;

     private Zone zoneKhiGasHuyDiet;
    private int xMapKhiGasHuyDiet;
    private int yMapKhiGasHuyDiet;
    private boolean goToKGHD;
    private long lastTimeGoToKGHD;

    private boolean goToCDRD;
    private long lastTimeGoToCDRD;
    private int idItemUpTop;
    private int typeChangeMap; //capsule, ngọc rồng đen...
    private int indexMenu; //menu npc
    private int typeInput; //input
    private byte typeLuckyRound; //type lucky round

    private long idPlayThachDau; //id người chơi được mời thách đấu
    private int goldThachDau; //vàng thách đấu

    private long idEnemy; //id kẻ thù - trả thù

    private Shop shopOpen; //shop người chơi đang mở
    private String tagNameShop; //thẻ tên shop đang mở

    /**
     * loại tàu vận chuyển dùng ;
     * 0 - Không dùng ;
     * 1 - Tàu vũ trụ ;
     * 2 - Dịch chuyển tức thời ;
     * 3 - Tàu tennis
     */
    private byte idSpaceShip;

    private long lastTimeBan;
    private boolean isBan;
    private boolean isActive;
    //giao dịch
    private int playerTradeId = -1;
    private Player playerTrade;
    private long lastTimeTrade;

    private long lastTimeNotifyTimeHoldBlackBall;
    private long lastTimeHoldBlackBall;
    private int tempIdBlackBallHold = -1;
    private boolean holdBlackBall;

    private int tempIdNamecBallHold = -1;
    private boolean holdNamecBall;

    private boolean loadedAllDataPlayer; //load thành công dữ liệu người chơi từ database

    private long lastTimeChangeFlag;

    //tới tương lai
    private boolean gotoFuture;
    private long lastTimeGoToFuture;

    private long lastTimeChangeZone;
    private long lastTimeChatGlobal;
    private long lastTimeChatPrivate;
    
    private long lastTimePickItem;
    
    private boolean goToBDKB;
    private long lastTimeGoToBDKB;
    private long lastTimeAnXienTrapBDKB;

    private boolean goToKG;
    private long lastTimeGoToKG;
    private long lastTimeAnXienTrapKG;
    
    private Npc npcChose; //npc mở
    
    private byte loaiThe; //loại thẻ nạp
    
    public boolean isBaseMenu() {
        return this.indexMenu == ConstNpc.BASE_MENU;
    }

    public void dispose() {
        if (this.shopOpen != null) {
            this.shopOpen.dispose();
            this.shopOpen = null;
        }
        this.npcChose = null;
        this.tagNameShop = null;
        this.playerTrade = null;
    }
}
