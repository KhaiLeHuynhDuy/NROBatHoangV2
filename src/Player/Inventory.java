package Player;

import java.util.ArrayList;
import java.util.List;
import Item.Item;
import Item.Item.ItemOption;

public class Inventory {

    public static final long LIMIT_GOLD = 2000000000000L;

    public static final int MAX_ITEMS_BAG = 100;
    public static final int MAX_ITEMS_BOX = 100;

    public Item trainArmor;
    public List<String> giftCode;
    public List<Item> itemsBody;
    public List<Item> itemsBag;
    public List<Item> itemsBox;

    public List<Item> itemsBoxCrackBall;

    public long gold;
    public int gem;
    public int ruby;
    public int coupon;
    public int event;

    public Inventory() {
        itemsBody = new ArrayList<>();
        itemsBag = new ArrayList<>();
        itemsBox = new ArrayList<>();
        itemsBoxCrackBall = new ArrayList<>();
        giftCode = new ArrayList<>();
    }

    public int getGemAndRuby() {
        return this.gem + this.ruby;
    }
    public int getGem() {
        return this.gem;
    }
    
    public int getParam(Item it , int id){
        for(ItemOption op : it.itemOptions){
            if(op!=null&&op.optionTemplate.id ==id){
                return op.param;
            }
        }
        return 0;
    }
    
    public boolean haveOption(List<Item> l , int index , int id){
        Item it = l.get(index);
        if(it != null && it.isNotNullItem()){
            return it.itemOptions.stream().anyMatch(op -> op != null && op.optionTemplate.id == id);
        }
        return false;
    }

    public void subGemAndRuby(int num) {
        this.ruby -= num;
        if (this.ruby < 0) {
            this.gem += this.ruby;
            this.ruby = 0;
        }
    }
    
    public void subGem (int num) {
        this.gem -= num;
    }

    public void addGold(long gold) {
        this.gold += gold;
        if (this.gold > LIMIT_GOLD) {
            this.gold = LIMIT_GOLD;
        }
    }

    public void dispose() {
        if (this.trainArmor != null) {
            this.trainArmor.dispose();
        }
        this.trainArmor = null;
        if(this.itemsBody!= null){
            for(Item it : this.itemsBody){
                it.dispose();
            }
            this.itemsBody.clear();
        }
        if(this.itemsBag!= null){
            for(Item it : this.itemsBag){
                it.dispose();
            }
            this.itemsBag.clear();
        }
        if(this.itemsBox!= null){
            for(Item it : this.itemsBox){
                it.dispose();
            }
            this.itemsBox.clear();
        }
        if(this.itemsBoxCrackBall!= null){
            for(Item it : this.itemsBoxCrackBall){
                it.dispose();
            }
            this.itemsBoxCrackBall.clear();
        }
        this.itemsBody = null;
        this.itemsBag = null;
        this.itemsBox = null;
        this.itemsBoxCrackBall = null;
    }
    public void subRuby(int num) {
        this.ruby -= num;
        if (this.ruby < 0) {
            this.ruby = 0;
        }
    }

}
