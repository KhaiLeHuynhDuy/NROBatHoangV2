package Player;

import Item.Item;

public class SetClothes {

    private Player player;
    private boolean huydietClothers;

    public SetClothes(Player player) {
        this.player = player;
    }
    // Set Trái Đất
    public byte setSongoku;
    public byte setThienXinHang;
    public byte setKirin;
    // Set Namếc
    public byte setOcTieu;
    public byte setPicoloDiamao;
    public byte setPicolo;
    // Set Xayda
    public byte setKakarot;
    public byte setCadic;
    public byte setNappa;
    // Set Đặc Biệt
    public byte setTinhAn;
    public byte setNhatAn;
    public byte setNguyetAn;

    public byte worldcup;

    public boolean godClothes;
    public int ctHaiTac = -1;
//khaile add
    public byte setVoCucTuTai;
    public byte setThienMa;
//end khaile add

    public void setup() {
        setDefault();
        setupSKH();
        this.godClothes = true;
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id > 567 || item.template.id < 555) {
                    this.godClothes = false;
                    break;
                }
            } else {
                this.godClothes = false;
                break;
            }
        }
        Item ct = this.player.inventory.itemsBody.get(5);
        if (ct.isNotNullItem()) {
            switch (ct.template.id) {
                case 618:
                case 619:
                case 620:
                case 621:
                case 622:
                case 623:
                case 624:
                case 626:
                case 627:
                    this.ctHaiTac = ct.template.id;
                    break;
            }
        }
    }

    private void setupSKH() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                boolean isActSet = false;
                for (Item.ItemOption io : item.itemOptions) {
                    switch (io.optionTemplate.id) {
                        case 129:
                        case 141:
                            isActSet = true;
                            setSongoku++;
                            break;
                        case 127:
                        case 139:
                            isActSet = true;
                            setThienXinHang++;
                            break;
                        case 128:
                        case 140:
                            isActSet = true;
                            setKirin++;
                            break;
                        case 131:
                        case 143:
                            isActSet = true;
                            setOcTieu++;
                            break;
                        case 132:
                        case 144:
                            isActSet = true;
                            setPicoloDiamao++;
                            break;
                        case 130:
                        case 142:
                            isActSet = true;
                            setPicolo++;
                            break;
                        case 135:
                        case 138:
                            isActSet = true;
                            setNappa++;
                            break;
                        case 133:
                        case 136:
                            isActSet = true;
                            setKakarot++;
                            break;
                        case 134:
                        case 137:
                            isActSet = true;
                            setCadic++;
                            break;
                        case 34:
                            isActSet = true;
                            setTinhAn++;
                            break;
                        case 35:
                            isActSet = true;
                            setNguyetAn++;
                            break;
                        case 36:
                            isActSet = true;
                            setNhatAn++;
                            break;
                        //khaile add
                        case 190:
                            isActSet = true;
                            setThienMa++;
                            break;
                        case 192:
                            isActSet = true;
                            setVoCucTuTai++;
                            break;
                        //end khaile add
                    }

                    if (isActSet) {
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    // Check mặc full set Thần
    public boolean setGod() {
        this.godClothes = true;
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (!item.isNotNullItem() || (item.template.id < 555 || item.template.id > 567)) {
                this.godClothes = false; // Nếu tìm thấy trang bị không phải thần, đặt godClothes là false
                break; // Thoát khỏi vòng lặp
            }
        }
        return this.godClothes;
    }

    // Check mặc full set Huỷ Diệt
    public boolean setHuyDiet() {
        this.huydietClothers = true;
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (!item.isNotNullItem() || (item.template.id < 650 || item.template.id > 662)) {
                this.godClothes = false;
                break;
            }
        }
        return this.huydietClothers;
    }

    private void setDefault() {
        this.setSongoku = 0;
        this.setThienXinHang = 0;
        this.setKirin = 0;
        this.setOcTieu = 0;
        this.setPicoloDiamao = 0;
        this.setPicolo = 0;
        this.setKakarot = 0;
        this.setCadic = 0;
        this.setNappa = 0;
        this.worldcup = 0;
        this.setNhatAn = 0;
        this.setTinhAn = 0;
        this.setNguyetAn = 0;
        this.godClothes = false;
        this.ctHaiTac = -1;
        this.setVoCucTuTai = 0;
        this.setThienMa = 0;
    }

    public void dispose() {
        this.player = null;
    }
}
