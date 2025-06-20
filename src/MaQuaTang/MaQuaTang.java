package MaQuaTang;

import Item.Item.ItemOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MaQuaTang {
    
    String code;
    int countLeft;
    public HashMap<Integer, Integer> detail = new HashMap<>();
    public ArrayList<Integer> listIdPlayer = new ArrayList<>();
    public ArrayList<ItemOption> option = new ArrayList<>();
    Timestamp datecreate;
    Timestamp dateexpired;
    
    public boolean isUsedGiftCode(int idPlayer) {
        return listIdPlayer.contains(idPlayer);
    }

    public void addPlayerUsed(int idPlayer) {
        listIdPlayer.add(idPlayer);
    }
    
    public boolean timeCode(){
        Date now = new Date();
        return now.getTime() > this.dateexpired.getTime();
    }
}
