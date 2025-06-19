package Services.func;

import Item.Item;
import java.util.ArrayList;
import java.util.List;

public class CombineNew {

    public long lastTimeCombine;

    public List<Item> itemsCombine;
    public int typeCombine;
    public int DiemNangcap;
    public int DaNangcap;
    public float TileNangcap;
    public long goldCombine;
    public int gemCombine;
    public float ratioCombine;
    public int countDaNangCap;
    public short countDaBaoVe;
    public short quantities = 1;

    public CombineNew() {
        this.itemsCombine = new ArrayList<>();
    }

    public void setTypeCombine(int type) {
        this.typeCombine = type;
    }

    public void clearItemCombine() {
        this.itemsCombine.clear();
    }

    public void clearParamCombine() {
        this.goldCombine = 0;
        this.gemCombine = 0;
        this.ratioCombine = 0;
        this.countDaNangCap = 0;
        this.countDaBaoVe = 0;

    }
    //khaile add update

    public void updateItemsCombine(List<Item> itemsBag) {
        List<Item> updatedItems = new ArrayList<>();

        for (Item item : this.itemsCombine) {
            // Kiểm tra item còn tồn tại trong túi đồ không
            if (itemsBag.contains(item)) {
                // Nếu item vẫn còn nhưng số lượng giảm, cập nhật số lượng mới
                for (Item bagItem : itemsBag) {
                    if (bagItem.equals(item) && bagItem.quantity > 0) {
                        updatedItems.add(bagItem);
                        break;
                    }
                }
            }
        }

        // Cập nhật danh sách itemsCombine
        this.itemsCombine = updatedItems;
    }

    //end khaile add
    public void dispose() {
        this.itemsCombine = null;
    }
}
