package BossMain;

import Consts.ConstPlayer;
import Skill.Skill;
import Utils.Util;

public class BossesData {

    /**
     * Prefix text chat |-1| Boss chat |-2| Player in map chat |-3| Parent chat
     * |0|,|1|,|n| Index boss in list chat
     */
    private static final int[][] FULL_DRAGON = new int[][]{
        {Skill.DRAGON, 1},
        {Skill.DRAGON, 2},
        {Skill.DRAGON, 3},
        {Skill.DRAGON, 4},
        {Skill.DRAGON, 5},
        {Skill.DRAGON, 6},
        {Skill.DRAGON, 7}};
    private static final int[][] FULL_DEMON = new int[][]{
        {Skill.DEMON, 1},
        {Skill.DEMON, 2},
        {Skill.DEMON, 3},
        {Skill.DEMON, 4},
        {Skill.DEMON, 5},
        {Skill.DEMON, 6},
        {Skill.DEMON, 7}};
    private static final int[][] FULL_GALICK = new int[][]{
        {Skill.GALICK, 1},
        {Skill.GALICK, 2},
        {Skill.GALICK, 3},
        {Skill.GALICK, 4},
        {Skill.GALICK, 5},
        {Skill.GALICK, 6},
        {Skill.GALICK, 7}};
    private static final int[][] FULL_KAMEJOKO = new int[][]{
        {Skill.KAMEJOKO, 1},
        {Skill.KAMEJOKO, 2},
        {Skill.KAMEJOKO, 3},
        {Skill.KAMEJOKO, 4},
        {Skill.KAMEJOKO, 5},
        {Skill.KAMEJOKO, 6},
        {Skill.KAMEJOKO, 7}};
    private static final int[][] FULL_MASENKO = new int[][]{
        {Skill.MASENKO, 1},
        {Skill.MASENKO, 2},
        {Skill.MASENKO, 3},
        {Skill.MASENKO, 4},
        {Skill.MASENKO, 5},
        {Skill.MASENKO, 6},
        {Skill.MASENKO, 7}};
    private static final int[][] FULL_ANTOMIC = new int[][]{
        {Skill.ANTOMIC, 1},
        {Skill.ANTOMIC, 2},
        {Skill.ANTOMIC, 3},
        {Skill.ANTOMIC, 4},
        {Skill.ANTOMIC, 5},
        {Skill.ANTOMIC, 6},
        {Skill.ANTOMIC, 7}};
    private static final int[][] FULL_LIENHOAN = new int[][]{{Skill.LIEN_HOAN, 1}, {Skill.LIEN_HOAN, 2}, {Skill.LIEN_HOAN, 3}, {Skill.LIEN_HOAN, 4}, {Skill.LIEN_HOAN, 5}, {Skill.LIEN_HOAN, 6}, {Skill.LIEN_HOAN, 7}};
    private static final int[][] FULL_TDHS = new int[][]{{Skill.THAI_DUONG_HA_SAN, 1}, {Skill.THAI_DUONG_HA_SAN, 2}, {Skill.THAI_DUONG_HA_SAN, 3}, {Skill.THAI_DUONG_HA_SAN, 4}, {Skill.THAI_DUONG_HA_SAN, 5}, {Skill.THAI_DUONG_HA_SAN, 6}, {Skill.THAI_DUONG_HA_SAN, 7}};

    private static final int REST_1_S = 1;
    private static final int REST_5_S = 5;
    private static final int REST_10_S = 10;
    private static final int REST_20_S = 20;
    private static final int REST_30_S = 30;
    private static final int REST_1_M = 60;
    private static final int REST_2_M = 120;
    private static final int REST_5_M = 300;
    private static final int REST_10_M = 600;
    private static final int REST_15_M = 900;
    private static final int REST_30_M = 1800;
    private static final int REST_24_H = 86400000;

    public static final BossData DUONG_TANG_HO_TONG = new BossData(
            "Đường Tank",
            (byte) 0,
            new short[]{467, 468, 469, -1, -1, -1},
            100000,
            new long[]{100},
            new int[]{0},
            new int[][]{
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 15000}
            },
            new String[]{}, // text chat 1
            new String[]{"|-1|Chậm thôi",
                "|-1|Cứu ta ",
                "|-1|Ui da đau quá",
                "|-1|Nhanh thế? ",
                "|-1|Lề mề quá, đứng gần ta thôi"
            },
            new String[]{}, // text chat 3
            60);

    //**************************************************************************Boss Nappa
    public static final BossData KUKU = new BossData(
            "Kuku", // name
            ConstPlayer.XAYDA, // gender
            new short[]{159, 160, 161, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            9000, // dame
            new long[]{6_000_000}, // hp
            new int[]{68, 69, 70, 71, 72}, // map join
            new int[][]{
                {Skill.MASENKO, 3, 1000},
                {Skill.LIEN_HOAN, 7, 1000}},
            new String[]{}, // text chat 1
            new String[]{"|-1|Ta sẽ tàn sát khu này trong vòng 5 phút nữa",
                "|-1|Haha, mày đây rồi",
                "|-1|Tao đã có lệnh của đại ca Fide rồi",
                "|-1|Tao hơn hẳn mày, nhìn máy đo đi",
                "|-1|Mày yếu đi đó, với sức chiến đấu đó sao có thể thắng được tao",
                "|-1|Định chạy trốn hả, hử"
            }, // text chat 2
            new String[]{}, // text chat 3
            REST_15_M // second rest
    );

    public static final BossData MAP_DAU_DINH = new BossData(
            "Mập Đầu Đinh", // name
            ConstPlayer.XAYDA, // gender
            new short[]{165, 166, 167, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{7_000_000}, // hp
            new int[]{63, 64, 65, 66, 67}, // map join
            new int[][]{
                {Skill.GALICK, 7, 1000},
                {Skill.ANTOMIC, 7, 10000},}, // skill //skill
            new String[]{}, // text chat 1
            new String[]{"|-1|HAHAHA",
                "|-1|Tao chỉ cần 10 giây để giết hết bọn mày",
                "|-1|Được rồi tao sẽ thổi bay hết",
                "|-1|Chết hết đi cho tao",
                "|-1|Ta sẽ tàn sát khu này trong vòng 5 phút nữa",
                "|-1|Tao sẽ giết hết bọn mày"}, // text chat 2
            new String[]{}, // text chat 3
            REST_15_M // second rest
    );

    public static final BossData RAMBO = new BossData(
            "Rambo", // name
            ConstPlayer.XAYDA, // gender
            new short[]{162, 163, 164, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            12400, // dame
            new long[]{8_000_000}, // hp
            new int[]{74, 75, 76, 77}, // map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.KAMEJOKO, 1, 1000},}, // skill //skill
            new String[]{}, // text chat 1
            new String[]{"|-1|HAHAHA",
                "|-1|Tao chỉ cần 10 giây để giết hết bọn mày",
                "|-1|Thấy ta đẹp trai không",
                "|-1|Mày sợ tao chưa",
                "|-1|Ta sẽ tàn sát khu này trong vòng 5 phút nữa",
                "|-1|Tao sẽ giết hết bọn mày"}, // text chat 2
            new String[]{"|-1|Ôi bạn ơi..."}, // text chat 3
            REST_15_M // second rest
    );

    //************************************************************************** Boss tiểu đội sát thủ
    public static final BossData SO_4 = new BossData(
            "Số 4", //name
            ConstPlayer.XAYDA, //gender
            new short[]{168, 169, 170, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            10000, //dame
            new long[]{25_000_000}, //hp
            new int[]{79, 81, 82, 83}, // map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.MASENKO, 7, 1000},
                {Skill.THOI_MIEN, 7, 100000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Oải rồi hả",
                "|-1|Ê cố lên nhóc",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|Chán",
                "|-1|Đại ca Fide có nhầm không nhỉ",
                "|-1|Chỉ là bọn con nít",
                "|-1|HAHAHA"
            }, // text chat 2
            new String[]{"|-1|Fide gọi ta về, ngươi có ngon thì chờ ở đây",
                "|2|Để tao xử nó cho"}, // text chat 3
            REST_15_M,
            new int[]{BossID.SO_3, BossID.SO_2, BossID.SO_1, BossID.TIEU_DOI_TRUONG}
    );

    public static final BossData SO_3 = new BossData(
            "Số 3", //name
            ConstPlayer.XAYDA, //gender
            new short[]{174, 175, 176, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            11000, //dame
            new long[]{35_000_000}, //hp
            new int[]{79, 81, 82, 83}, // map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.ANTOMIC, 4, 1000}},//skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Oải rồi hả",
                "|-1|Ê cố lên nhóc",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|Chán",
                "|-1|Đại ca Fide có nhầm không nhỉ",
                "|-1|Chỉ là bọn con nít",
                "|-1|HAHAHA"
            }, // text chat 2
            new String[]{"|-1|Fide gọi ta về, ngươi có ngon thì chờ ở đây",
                "|1|Để tao xử nó cho",
                "|0|Để tao xử nó cho"}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData SO_2 = new BossData(
            "Số 2", //name
            ConstPlayer.XAYDA, //gender
            new short[]{171, 172, 173, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            12000, // dame
            new long[]{40_000_000}, // hp
            new int[]{79, 81, 82, 83}, // map join
            new int[][]{
                {Skill.GALICK, 7, 1000},
                {Skill.ANTOMIC, 3, 3000},}, // skill//skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Oải rồi hả",
                "|-1|Ê cố lên nhóc",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|Chán",
                "|-1|Đại ca Fide có nhầm không nhỉ",
                "|-1|Chỉ là bọn con nít",
                "|-1|HAHAHA"
            }, // text chat 2
            new String[]{"|-1|Fide gọi ta về, ngươi có ngon thì chờ ở đây",
                "|3|Để tao xử nó cho"}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData SO_1 = new BossData(
            "Số 1", //name
            ConstPlayer.XAYDA, //gender
            new short[]{177, 178, 179, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            12500, // dame
            new long[]{50_000_000}, // hp
            new int[]{79, 81, 82, 83}, // map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.KAMEJOKO, 4, 10000},}, // skill//skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Oải rồi hả",
                "|-1|Ê cố lên nhóc",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|Chán",
                "|-1|Đại ca Fide có nhầm không nhỉ",
                "|-1|Chỉ là bọn con nít",
                "|-1|HAHAHA"
            }, // text chat 2
            new String[]{"|-1|Fide gọi ta về, ngươi có ngon thì chờ ở đây",
                "|3|Để tao xử nó cho"}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData TIEU_DOI_TRUONG = new BossData(
            "Tiểu đội trưởng Ginyu", //name
            ConstPlayer.XAYDA, //gender
            new short[]{180, 181, 182, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            13000, // dame
            new long[]{60_000_000}, // hp
            new int[]{79, 81, 82, 83}, // map join
            new int[][]{
                {Skill.MASENKO, 7, 1000},
                {Skill.GALICK, 7, 1000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Oải rồi hả",
                "|-1|Ê cố lên nhóc",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|Chán",
                "|-1|Đại ca Fide có nhầm không nhỉ",
                "|-1|Chỉ là bọn con nít",
                "|-1|HAHAHA"
            }, // text chat 2
            new String[]{"|-1|Fide gọi ta về, ngươi có ngon thì chờ ở đây"}, // text chat 33
            TypeAppear.APPEAR_WITH_ANOTHER //type 
    );
    //************************************************************************** Boss Frost
    public static final BossData FROST_FIRST_FORM = new BossData(
            "Frost 1", //name
            ConstPlayer.XAYDA, //gender
            new short[]{493, 494, 495, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            24000, //dame
            new long[]{240000000}, //hp 240000000
            new int[]{79, 82, 83}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.ANTOMIC, 3, 3000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Frieza có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{"|-1|Cay quá!",
                "|-1|Ta mà lại thua được sao?",
                "|-1|Hãy trả thù cho ta!"
            }, //text chat 3
            REST_15_M //type appear
    );
    public static final BossData FROST_SECOND_FORM = new BossData(
            "Frost 2", //name
            ConstPlayer.XAYDA, //gender
            new short[]{496, 497, 498, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            25000, //dame
            new long[]{25000000}, //hp 25000000
            new int[]{79, 82, 83}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.KAMEJOKO, 4, 10000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Frieza có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{"|-1|Cay quá!",
                "|-1|Ta mà lại thua được sao?",
                "|-1|Hãy trả thù cho ta!"
            }, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );
    public static final BossData FROST_FINAL_FORM = new BossData(
            "Frost Hoàn Thiện", //name
            ConstPlayer.XAYDA, //gender
            new short[]{499, 500, 501, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            27000, //dame
            new long[]{270000000}, //hp 270000000
            new int[]{79, 82, 83}, //map join
            new int[][]{
                {Skill.SOCOLA, 7, 1000},
                {Skill.MASENKO, 7, 1000},
                {Skill.GALICK, 7, 1000},},//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Frieza có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{"|-1|Cay quá!"
            }, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );
    //************************************************************************** Boss Frieza
    public static final BossData FIDE_DAI_CA_1 = new BossData(
            "Frieza đại ca 1", //name
            ConstPlayer.XAYDA, //gender
            new short[]{183, 184, 185, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            32000, // dame
            new long[]{50_000_000}, // hp
            new int[]{80}, // map join
            (int[][]) Util.addArray(FULL_ANTOMIC, FULL_GALICK), // skill
            new String[]{"|-2|Fide!!!, với những gì ngươi đã làm với người Xayda và Namek...",
                "|-2|Ta phán ngươi tội: tử hình",
                "|-1|Khẩu khí ngang tàng lắm",
                "|-1|Xem bản lĩnh của ngươi thế nào đã"
            }, // text chat 1
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                "|-1|Trận địa pháo mini"
            }, // text chat 2
            new String[]{"|-1|Ác quỷ biến hình, hây aaaa..."}, // text chat 3
            REST_15_M //second rest
    );
    public static final BossData FIDE_DAI_CA_2 = new BossData(
            "Frieza đại ca 2", //name
            ConstPlayer.XAYDA, //gender
            new short[]{186, 187, 188, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            50000, // dame
            new long[]{75_000_000}, // hp
            new int[]{80}, // map join
            (int[][]) Util.addArray(FULL_ANTOMIC, FULL_LIENHOAN), // skill
            new String[]{"|-1|Hê hê, cẩn thận đi",
                "|-1|Nếu đã biến thành thế này thì ta sẽ không nhùn nhặn như trước đâu"
            }, // text chat 1
            new String[]{"|-1|Oải rồi hả?",
                "|-1|Ê cố lên nhóc",
                "|-1|Ôi, xin lỗi nhé. Sức mạnh lớn quá nên ta cũng chẳng điều khiển nổi nữa!",
                "|-1|Hahaha! Ấn tượng đấy! Tên nào cũng lủi rất nhanh!",
                "|-2|A...Tốc độ nhanh quá!",
                "|-1|Hình như... mày không phải là một thằng nhóc bình thường!",
                "|-1|Mấy đòn vừa rồi, nói thật là cũng đau đấy!",
                "|-1|Nhưng tiếc rằng đối thủ của mày lại là Fide này...",
                "|-2|Chết tiệt.. chúng ta đã đánh giá quá thấp sức mạnh của hắn!!",
                "|-2|Đồ..Đồ quái vật..!",
                "|-2|Tốc độ kinh hoàng quá! Ai mà né nổi chứ!",}, // text chat 2
            new String[]{"|-1|Ác quỷ biến hình, Graaaaa...."}, // text chat 3
            TypeAppear.ANOTHER_LEVEL //type appear
    );
    public static final BossData FIDE_DAI_CA_3 = new BossData(
            "Frieza đại ca 3", //name
            ConstPlayer.XAYDA, //gender
            new short[]{189, 190, 191, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            122000, // dame
            new long[]{100_000_000}, // hp
            new int[]{80}, // map join
            (int[][]) Util.addArray(FULL_MASENKO, FULL_LIENHOAN), // skill
            new String[]{"|-1|Ta sẽ cho các ngươi thấy đâu mới là sức mạnh của ta!!"}, // text chat 1
            new String[]{"|-1|Ta nói các ngươi rồi! Sức mạnh này của ta còn đáng sợ hơn địa ngục!!",
                "|-1|Ta chơi thêm chút nữa chắc ngươi chóng mặt buồn nôn mất!!",
                "|-2|Ăn gì mà khỏe thế!",
                "|-2|Chết đi Fide!!!!",
                "|-1|Hô hô hô hô",
                "|-1|Chán thật! Khí của ngươi sắp hết rồi. Để ta tiễn ngươi về địa ngục!",
                "|-1|Ngươi quá tự cao rồi đấy,xem ta đây!",}, // text chat 2
            new String[]{"|-1|Lũ khốn..",
                "|-1|..Một ngày nào đó ta sẽ quay lại và trả thù các ngươi",
                "|-1|Nhớ mặt tao đấy !",}, // text chat 3
            TypeAppear.ANOTHER_LEVEL //type appear
    );
    //************************************************************************** Boss TINHd
    public static final BossData DR_KORE = new BossData(
            "Dr.Kôrê", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{255, 256, 257, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            12000, // dame
            new long[]{500_000_000}, // hp
            new int[]{96, 94, 93}, // map join
            new int[][]{
                {Skill.THOI_MIEN, 3, 10000},
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 1000},}, // skill
            new String[]{"|-2|Chào anh! em đứng đây từ chiều",
                "|-1|Quái lạ! Sao chúng biết rõ tung tích của bọn ta thế nhỉ?",
                "|-1|Chúng còn biết chính xác ta sẽ xuất hiện ở đây để đón đánh nữa!",
                "|-1|Chúng mày là ai từ đâu tới?Cho tao xin cái địa chỉ",
                "|-2|Điều ấy biết hay không cũng không còn quan trọng nữa",
                "|-1|Ừ bọn bây chỉ là hạng tôm tép ta chẳng cần biết tên làm gì!",
                "|-1|Số 19! Xuất chiêu đi nào",
                "|0|Okê đại ca, em sẽ xử lý bọn này trong vòng 2 tiếng."
            }, // text chat 1
            new String[]{"|-1|Oải rồi hả?",
                "|-1|Ê cố lên nhóc",
                "|-1|Chán",
                "|-1|Mi khá đấy, nhưng so với ta cũng chỉ là hạng tôm tép",
                "|-1|Lôi Công Trảo",
                "|-1|Cho dù ngươi có mạnh đến đâu.. thì cũng không đánh bại được rôbốt bọn ta",
                "|-2|Lão già khôn thật!!",
                "|-2|Hừ! Lão già khốn kiếp!",}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );
    public static final BossData ANDROID_19 = new BossData(
            "Android 19", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{249, 250, 251, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            12200, // dame
            new long[]{30_000_000}, // hp
            new int[]{96, 94, 93}, // map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.LIEN_HOAN, 7, 10000},}, // skill//skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Oải rồi hả?",
                "|-1|Ê cố lên nhóc",
                "|-1|Chán",
                "|-1|Ngươi sẽ không bao giờ thắng được đâu!!",
                "|-2|Ngươi vừa hút được nhiều rồi đấy, nhưng giờ thì đừng hòng!!",}, // text
            // chat 2
            new String[]{}, // text chat 3
            REST_15_M, //second rest
            new int[]{BossID.DR_KORE}
    );

    //**************************************************************************
    public static final BossData ANDROID_13 = new BossData(
            "Android 13", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{252, 253, 254, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            12055, // dame
            new long[]{50_000_000}, // hp
            new int[]{104}, // map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 1000},}, // skill
            new String[]{"|-1|Sôn..gôku",
                "|-2|Lại là tiến sĩ Kôrê à.. rốt cuộc ông ta đã tạo ra bao nhiêu rôbốt nhân tạo thế nhỉ?",
                "|-1|Bọn ta là rôbốt sát thủ, sinh ra từ máy tính ngài Kôrê,..",
                "|-1|..cho một mục tiêu duy nhất là giết Sôngôku!",
                "|-2|Máy tính? Để giết Gôku sao?",
                "|-1|Mong muốn trả thù Gôku của ngài Kôrê đã được lưu hết vào máy tính..",
                "|-1|.., Bọn ta sinh ra từ lòng căm thù ngày một tăng bên trong chiếc máy tính có chứa mong muốn trả thù",
                "|-1|Mục tiêu của bọn ta chỉ là Gôku, nhưng mà.. nếu ngươi mà cản đường thì là chuyện khác!",}, // text
            // chat
            // 1
            new String[]{"|-1|Sao thế hả? Ta mới chỉ khởi động thôi mà!",
                "|-2|Ngươi đánh giá thấp bọn ta quá đấy!",
                "|-2|Đừng có tưởng bở, lũ sâu bọ!",
                "|-1|Nếu có ý định gây trở ngại cho cuộc chiến giữa ta và Sôngôku, thì ta cũng sẽ giết ngươi ngay lập tức",
                "|-2|Ngươi tưởng ta để cho ngươi giết được ta ngay à?",
                "|-2|Đúng là mạnh mồm thật đấy!",
                "|-2|Đỡ này",}, // text chat 2
            new String[]{"|-1|Sô..Sông...gôku....."}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );

    public static final BossData ANDROID_14 = new BossData(
            "Android 14", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{246, 247, 248, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            12000, // dame
            new long[]{50_000_000}, // hp
            new int[]{104}, // map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 1000},}, // skill
            new String[]{"|-2|Các ngươi là ai?",
                "|-2|Ta không thể cảm nhận được khí của các ngươi, các ngươi không phải là con người đúng chứ?",
                "|-2|Ta hiểu rồi, các ngươi là rôbốt sát thủ do tiến sĩ Kôrê tạo ra chứ gì?"
            }, // text chat 1
            new String[]{}, // text chat 2
            new String[]{"|0|Số 14 và số 15 tiêu tùng cả rồi à?"}, // text chat 3
            REST_15_M,
            new int[]{BossID.ANDROID_13, BossID.ANDROID_15}
    );

    public static final BossData ANDROID_15 = new BossData(
            "Android 15", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{261, 262, 263, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            19200, // dame
            new long[]{100_000_000}, // hp
            new int[]{104}, // map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 1000},}, // skill//skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{"|-2|Thì ra vẫn chỉ là một đống sắt vụn!"}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );

    public static final BossData SUPER_ANDROID_17 = new BossData(
            "Super Android 17", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{636, 637, 638, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            180000, //dame
            new long[]{1500000000}, //hp
            new int[]{98, 99, 100, 96, 92, 93}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 5, 700},
                {Skill.KAMEJOKO, 7, 600},
                {Skill.TAI_TAO_NANG_LUONG, 3, 15000}
            },
            new String[]{}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            REST_15_M
    );
//**************************************************************************
    public static final BossData POC = new BossData(
            "Poc", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{240, 241, 242, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            18000, // dame
            new long[]{75_000_000}, // hp
            new int[]{97, 98, 99}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO), // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Đừng tưởng ta đây là con gái mà dễ bắt nạt nhé",
                "|-1|Khôn hồn thì chỉ chỗ Gôku cho bọn ta nhanh đi",
                "|-3|Coi kìa, một lũ xúm lại bắt nạt một cô gái kìa..",
                "|-1|Đừng có mà trọng nam khinh nữ",
                "|-2|Tại sao cô gái xinh đẹp thế này mà lại là rôbốt nhỉ?"
            }, // text chat 2
            new String[]{"|-2|Cô gái xinh đẹp vậy mà lại bị tên tiến sĩ Kôrê biến thành người máy.."}, // text
            REST_15_M
    );

    public static final BossData PIC = new BossData(
            "Pic", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{237, 238, 239, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            17022, // dame
            new long[]{70_000_000}, // hp
            new int[]{97, 98, 99}, // map join
            (int[][]) Util.addArray(FULL_GALICK, FULL_KAMEJOKO), // skill
            new String[]{"|-1|Chào! Có Gôku ở đây không?",
                "|-3|Tôi không nghĩ Gôku ở đây đâu!",
                "|-2|Biến khỏi đây đi, Gôku không có ở đây đâu!",
                "|-1|Bọn ta cũng nghĩ vậy, ngươi nói cho bọn ta biết hắn ở đâu được không!?",
                "|-2|Ngươi nghĩ bọn ta sẽ nói sao??",
                "|-1|Nếu ngươi không chịu nói bọn ta sẽ phải ra tay.."
            }, // text chat 1
            new String[]{"|-1|Ngươi thực sự rất mạnh dù không phải là một rôbốt. Ngươi không phải là Piccôlô",
                "|-1|Nhưng ta không quan tâm ngươi là ai, ta chỉ cần biết Gôku đang ở đâu!",
                "|-1|Sao ngươi không chịu nói cho ta biết chứ!?",
                "|-2|Mục đích của ngươi không phải là giết Gôku sao? Vì vậy ta không nói cho ngươi biết cậu ấy đang ở đâu",
                "|-1|Vậy thì ta bắt buộc phải tiếp tục đánh buộc ngươi phải nói ra!",
                "|-1|Lần này ta không nương tay đâu!",
                "|-2|Ngươi thực sự rất nhanh. Nhưng chưa đủ thực lực đâu!!",
                "|-1|Cái gì!? Đó là điều ngu ngốc nhất ta từng nghe.. ta là chiến binh mạnh nhất từ trước đến giờ.!",
                "|1|Hắn thực sự rất mạnh, đúng là cuộc chiến cân sức",
                "|-3|Pic, trả nhẽ cậu lại để thua mấy tên nhóc vặt này sao?"
            }, // text chat 2
            new String[]{"|1|Pic tiêu rồi, tớ lên trước nhé!",
                "|-3|Okê, xin cứ tự nhiên"
            }, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );

    public static final BossData KING_KONG = new BossData(
            "King Kong", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{243, 244, 245, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            12000, // dame
            new long[]{80_000_000}, // hp
            new int[]{97, 98, 99}, // map join
            (int[][]) Util.addArray(FULL_LIENHOAN, FULL_MASENKO), // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Mau đền mạng cho những người bạn của ta",
                "|-1|Sức mạnh của ta chênh nhau với các ngươi một trời một vực đấy!",
                "|-1|Thằng kia đừng để bọn nó trói tao !"
            }, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );
    //************************************************************************** Boss cell

    public static final BossData XEN_BO_HUNG_1 = new BossData(
            "Xên bọ hung 1",
            ConstPlayer.XAYDA,
            new short[]{228, 229, 230, -1, -1, -1},
            100000,
            new long[]{100_000_000},
            new int[]{100},
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.LIEN_HOAN, 7, 10000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 10000}}, // skill
            new String[]{"|-2|Cái gì kia vậy!? Đó là loài gì thế!!?",
                "|-1|Hôm nay sẽ là ngày đáng nhớ đây!",
                "|-1|Ta sẽ hấp thụ số 17 và 18 để đạt được dạng hoàn hảo!",
                "|-1|Sao vậy, Picôlô? Nếu ngươi muốn ngăn ta lại thì xong lên đi chứ!?"
            }, // text chat 1
            new String[]{"|-2|Hắn làm ta bất ngờ đấy! Khốn kiếp!",
                "|-2|Tên đó muốn hấp thụ số 17 và 18 sao?",
                "|-1|Đến đây nào! Khi kết hợp với ta, ngươi sẽ trở nên bất bại, trở thành một thể sống hoàn mỹ!",
                "|-2|Mơ đi, sao ta phải để ngươi hấp thu hả!?",
                "|-2|Dù muốn hay không, ngươi cũng sẽ bị ta hấp thu thôi..",
                "|-2|Chúng ta không thể để hắn đạt đến dạng hoàn hảo được!",
                "|-2|Mục tiêu của hắn không phải là Sôngôku.., mà là phá hủy cả vũ trụ này!",
                "|-1|Làm đứt đuôi ta ư? Đừng quên ta có tế bào của Picôlô!!",
                "|-1|Ta có thể tái tạo.. mọi bộ phận cơ thể!",
                "|-2|Vậy thì để ngăn cản ngươi đạt đến dạng hoàn hảo, ta phải tiêu diệt ngươi!",
                "|-2|Hắn quá mạnh! Mình có thể làm được gì đây!?",
                "|-1|Có vẻ như ta đã trở nên quá mạnh, chắc là ta đã giết nhiều người hơn dự tính!!",
                "|-1|Ngươi không thể thắng nổi ta! Từ bỏ đi!!",
                "|-1|Đến lúc ta hấp thu ngươi rồi",
                "|-2|Đồ quái vật chết tiệt...",
                "|-1|Hê hê hê, rồi ngươi sẽ trở thành một phần của con quái vật này thôi!",
                "|-1|Lại thêm một tên ngốc nữa chán sống!"
            }, // text chat 2
            new String[]{"|-2|Khốn kiếp, Pic.. hắn bị Cell hấp thu rồi!!"}, // text chat 3
            REST_15_M
    );

    public static final BossData XEN_BO_HUNG_2 = new BossData(
            "Xên bọ hung 2",
            ConstPlayer.XAYDA,
            new short[]{231, 232, 233, -1, -1, -1},
            100000,
            new long[]{200_000_000},
            new int[]{100},
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.TAI_TAO_NANG_LUONG, 7, 20000},
                {Skill.LIEN_HOAN, 7, 10000}}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-2|Nguy rồi... thực sự nguy to rồi!",
                "|-1|Các ngươi nghĩ có thể chạy được sao!?",
                "|-1|Muốn chạy khỏi ta thì đừng hòng!!",
                "|-1|Ta cũng ngạc nhiên với tốc độ của mình! Đó tất nhiên là do ta hấp thụ được số 17!",
                "|-2|Hắn nhanh quá!!",
                "|-1|Ta muốn thử xem sức mạnh này đến đâu...",
                "|-1|Hmm.. có vẻ như sức mạnh này đã tăng lên gấp bội!",
                "|-1|Đã đến lúc ta đạt đến trạng thái hoàn hảo.!",
                "|-1|Có vẻ như ngươi muốn bị ép hơn là tự nguyện!!",
                "|-2|Bây giờ ta chưa thể thắng được ngươi!! Nhưng ngươi đừng hòng huyênh hoang!!!",
                "|-1|Muốn chạy à!!? Ta sẽ không để ngươi thoát đâu!!",}, // text chat 2
            new String[]{"|-1|Đến lúc rồi!"}, // text chat 3
            TypeAppear.ANOTHER_LEVEL
    );

    public static final BossData XEN_BO_HUNG_3 = new BossData(
            "Xên hoàn thiện",
            ConstPlayer.XAYDA,
            new short[]{234, 235, 236, -1, -1, -1},
            100000,
            new long[]{300_000_000},
            new int[]{100},
            new int[][]{
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 10000},
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                {Skill.TAI_TAO_NANG_LUONG, 5, 20000},
                {Skill.LIEN_HOAN, 7, 10000},
                {Skill.THOI_MIEN, 7, 100000}},
            // skill

            new String[]{"|-2|Cuối cùng hắn cũng đã biến đổi",
                "|-2|Khốn kiếp! Phải kết liễu hắn ngay lúc này!"
            }, // text chat 1
            new String[]{"|-2|Cell đã đạt đến dạng hoàn hảo rồi!",
                "|-2|Đồ khốn, sao ngươi dám làm vậy với số 18!!",
                "|-2|Không ấn tượng lắm với dạng hoàn hảo của ngươi..",
                "|-2|Sao hắn không hề hấn gì?",
                "|-1|Xin lỗi.. Ngươi có thể giúp ta làm nóng cơ thể lên không !?",
                "|-2|Tình hình nguy cấp rồi!",
                "|-2|Khốn kiếp! Ngươi không chú tâm vào trận đấu!",
                "|-1|Thì ta đã bảo đây chỉ là làm nóng cơ thể mà!!",
                "|-1|Giờ ngươi chỉ là rác rưởi mà thôi!",
                "|-2|Không thể nào! Ngươi dù sao cũng chỉ là đồ sâu bọ!",}, // text chat 2
            new String[]{"|-1|Oái.. không...",
                "|-1|Cơ thể hoàn hảo của ta!!",
                "|-1|Ta không tin chuyện này sẽ xảy ra!!",
                "|-1|Đồ khốn kiếp!! Rồi ngươi sẽ phải trả giá"
            }, // text chat 3
            TypeAppear.ANOTHER_LEVEL
    );//**************************************************************************
    //*************
    public static final BossData SIEU_BO_HUNG_1 = new BossData(
            "Siêu Bọ Hung 1",
            ConstPlayer.XAYDA,
            new short[]{234, 235, 236, -1, -1, -1},
            100000,
            new long[]{350_000_000},
            new int[]{103},
            new int[][]{
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 20000},
                {Skill.TAI_TAO_NANG_LUONG, 7, 120000},
                {Skill.GALICK, 7, 1000},
                {Skill.THAI_DUONG_HA_SAN, 7, 50000}
            },
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_15_M
    );

    public static final BossData SIEU_BO_HUNG_2 = new BossData(
            "Siêu Bọ Hung 2",
            ConstPlayer.XAYDA,
            new short[]{234, 235, 236, -1, -1, -1},
            300000,
            new long[]{500_000_000},
            new int[]{103},
            new int[][]{
                {Skill.DEMON, 3, 1}, {Skill.DEMON, 6, 2}, {Skill.DRAGON, 7, 3},
                {Skill.DRAGON, 1, 4}, {Skill.GALICK, 5, 5},
                {Skill.KAMEJOKO, 7, 6}, {Skill.KAMEJOKO, 6, 7}, {Skill.KAMEJOKO, 5, 8},
                {Skill.KAMEJOKO, 4, 9}, {Skill.KAMEJOKO, 3, 10}, {Skill.KAMEJOKO, 2, 11},
                {Skill.KAMEJOKO, 1, 12},
                {Skill.ANTOMIC, 1, 13}, {Skill.ANTOMIC, 2, 14}, {Skill.ANTOMIC, 3, 15},
                {Skill.ANTOMIC, 4, 16}, {Skill.ANTOMIC, 5, 17}, {Skill.ANTOMIC, 6, 19},
                {Skill.ANTOMIC, 7, 20},
                {Skill.MASENKO, 1, 21}, {Skill.MASENKO, 5, 22}, {Skill.MASENKO, 6, 23},
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                {Skill.KHIEN_NANG_LUONG, 7, 180000},
                {Skill.TAI_TAO_NANG_LUONG, 7, 120000},
                {Skill.THOI_MIEN, 7, 30000}},
            // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.ANOTHER_LEVEL
    );

    public static final BossData SIEU_BO_HUNG_3 = new BossData(
            "Siêu Bọ Hung Hoàn Thiện",
            ConstPlayer.XAYDA,
            new short[]{234, 235, 236, -1, -1, -1},
            160000,
            new long[]{1600000000},
            new int[]{103},
            new int[][]{
                {Skill.DEMON, 3, 1}, {Skill.DEMON, 6, 2}, {Skill.DRAGON, 7, 3}, {Skill.DRAGON, 1, 4}, {Skill.GALICK, 5, 5},
                {Skill.KAMEJOKO, 7, 6}, {Skill.KAMEJOKO, 6, 7}, {Skill.KAMEJOKO, 5, 8}, {Skill.KAMEJOKO, 4, 9}, {Skill.KAMEJOKO, 3, 10}, {Skill.KAMEJOKO, 2, 11}, {Skill.KAMEJOKO, 1, 12},
                {Skill.ANTOMIC, 1, 13}, {Skill.ANTOMIC, 2, 14}, {Skill.ANTOMIC, 3, 15}, {Skill.ANTOMIC, 4, 16}, {Skill.ANTOMIC, 5, 17}, {Skill.ANTOMIC, 6, 19}, {Skill.ANTOMIC, 7, 20},
                {Skill.MASENKO, 1, 21}, {Skill.MASENKO, 5, 22}, {Skill.MASENKO, 6, 23},
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.QUA_CAU_KENH_KHI, 7, 100000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 100000},
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                // {Skill.TAI_TAO_NANG_LUONG, 7, 100000},
                {Skill.THOI_MIEN, 7, 100000}},
            //skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.ANOTHER_LEVEL
    );
    //**************************************************************************Boss doanh trại
    public static final BossData TRUNG_UY_TRANG = new BossData(
            "Trung úy trắng", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{141, 142, 143, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            1500, //dame
            new long[]{15000000}, //hp
            new int[]{62}, //map join
            (int[][]) Util.addArray(FULL_DEMON), //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Nhóc con"}, //text chat 2
            new String[]{}, //text chat 3
            REST_10_S
    );
    //**************************************************************************
    public static final BossData COOLER_GOLD = new BossData(
            "Cooler Vàng", //name
            ConstPlayer.XAYDA, //gender
            new short[]{709, 710, 711, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            50000, //dame
            new long[]{1500000000}, //hp
            new int[]{176, 177, 178}, //map join
            new int[][]{
                {Skill.THOI_MIEN, 7, 100000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 4, 1000},
                {Skill.TAI_TAO_NANG_LUONG, 7, 50000},
                {Skill.MASENKO, 7, 10000}}, //skill
            new String[]{"|-1|Hello cục cưng",
                "|-1|Mày có biết tao là ai không?",
                "|-2|Tao không cần biết mày là ai, mày nghĩ mày dọa được tao à?",
                "|-1|Thôi không nói nhiều nữa,giờ tao cho mày biết tao là ai."
            }, //text chat 1
            new String[]{"|-1|Tao hơn hẳn mày, mày nên cầu cho may mắn ở phía mày đi",
                "|-1|Ghê chưa ghê chưa!",
                "|-1|Tao có rất nhiều vật phẩm quý giá,nhưng với mày thì có cái..nịt",
                "|-1|Đánh tao à,lo mà luyện tập thêm đi",
                "|-1|Nói cho mày biết,tao là anh trai của Frieza",
                "|-1|trạng thái Goldend Meta Cooler sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Đêm qua em đẹp lắm!"}, //text chat 3
            REST_15_M //second rest
    );

    public static final BossData CUMBER = new BossData(
            "Sayan Tà Ác Cumber", //name
            ConstPlayer.XAYDA, //gender
            new short[]{1207, 1208, 1209, -1, 0, -1}, //outfit {head, body, leg, bag, aura, eff}
            800000, // dame
            new long[]{1_000_000_000}, // hp
            new int[]{155}, // map join
            (int[][]) Util.addArray(FULL_GALICK, FULL_ANTOMIC, FULL_TDHS), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_15_M //second rest
    );
    //**************************************************************************
    public static final BossData XEN_CON = new BossData(
            "Xên con", //name
            ConstPlayer.XAYDA, //gender
            new short[]{264, 265, 266, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            150000, // dame
            new long[]{150_000_000}, // hp
            new int[]{103}, // map join
            (int[][]) Util.addArray(FULL_DEMON, FULL_MASENKO), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_15_M //second rest
    );
    public static final BossData MABU = new BossData(
            "Broly", //name
            ConstPlayer.XAYDA, //gender
            new short[]{291, 292, 293, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            Util.nextInt(100, 500), //dame
            new long[]{Util.nextInt(500, 5000)}, //hp
            new int[]{6, 27, 28, 29, 30, 13, 31, 32, 33, 34, 10, 19, 20, 35, 36, 37, 38}, //map join
            new int[][]{{Skill.KAMEJOKO, 7, 100},
            {Skill.TAI_TAO_NANG_LUONG, 1, 15000}}, //skill
            new String[]{
                "|-1|Tuy không biết các ngươi là ai, nhưng ta rất ấn tượng đấy!",
                "|-2|Ta cũng cảm thấy phấn khích lắm!"
            }, //text chat 1
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                "|-1|Gaaaaaa",
                "|-2|Không..thể..nào!!",
                "|-2|Tên này điên thật rồi!!",
                "|-1|Được thôi, nếu muốn chết đến vậy, ta rất vui lòng!!"
            }, //text chat 2
            new String[]{"|-1|Khôngggggggg!!"}, //text chat 3
            REST_30_S //type appear
    );

    public static final BossData BERRUS = new BossData(
            "Berrus", //name
            ConstPlayer.XAYDA, //gender
            new short[]{508, 509, 510, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            250000, //dame
            new long[]{150000000}, //hp
            new int[]{105, 106, 107, 108, 109, 110}, //map join
            (int[][]) Util.addArray(FULL_DEMON, FULL_MASENKO), //skill
            new String[]{"|-1|Hello cục cưng",
                "|-1|Mày có biết tao là ai không?",
                "|-2|Tao không cần biết mày là ai, mày nghĩ mày dọa được tao à?",
                "|-1|Thôi không nói nhiều nữa,giờ tao cho mày biết tao là ai."
            }, //text chat 1
            new String[]{"|-1|Tao hơn hẳn mày, mày nên cầu cho may mắn ở phía mày đi",
                "|-1|Ghê chưa ghê chưa!",
                "|-1|Tao có rất nhiều vật phẩm quý giá,nhưng với mày thì có cái..nịt",
                "|-1|Đánh tao à,lo mà luyện tập thêm đi",
                "|-1|Nói cho mày biết,tao là con của Xên ",
                "|-1|Tao sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Đêm qua em đẹp lắm!"}, //text chat 3
            REST_15_M //second rest
    );

    public static final BossData QUY_LAO = new BossData(
            "Quy Lão Kame", //name
            ConstPlayer.XAYDA, //gender
            new short[]{554, 555, 556, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            111110, //dame
            new long[]{550000000}, //hp
            new int[]{6}, //map join
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.KAMEJOKO, 7, 10000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );
    public static final BossData JACK_KY = new BossData(
            "Jacky-Chun",
            ConstPlayer.XAYDA,
            new short[]{356, 357, 358, -1, -1, -1},
            160000,
            new long[]{650000000},
            new int[]{6},
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 3, 10000},
                {Skill.KAMEJOKO, 7, 10000},},//skill//skill
            new String[]{"|-2|Con Chào Sư Phụ !!!!!"}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            REST_15_M, //second rest
            new int[]{BossID.QUY_LAO}
    );

    //**************************************************************************
    public static final BossData XUKA = new BossData(
            "Shizuka", //name
            ConstPlayer.XAYDA, //gender
            new short[]{802, 803, 804, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            111111, //dame
            new long[]{500000000}, //hp
            new int[]{19}, //map join
            new int[][]{
                {Skill.MASENKO, 7, 1000},
                {Skill.DE_TRUNG, 7, 1000},},//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Khoa có nhầm không nhỉ",
                "|-1|Các ngươi không nhúc nhích được sao?",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData XEKO = new BossData(
            "Suneo", //name
            ConstPlayer.XAYDA, //gender
            new short[]{850, 851, 852, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            111111, //dame
            new long[]{500000000}, //hp
            new int[]{19}, //map join
            new int[][]{
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 100000},
                {Skill.KAMEJOKO, 7, 1000},},//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData CHAIEN = new BossData(
            "Chaien", //name
            ConstPlayer.XAYDA, //gender
            new short[]{847, 848, 849, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            111111, //dame
            new long[]{500000000}, //hp
            new int[]{19}, //map join
            new int[][]{
                {Skill.GALICK, 7, 10000},
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.BIEN_KHI, 1, 600000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData NOBITA = new BossData(
            "Nobita", //name
            ConstPlayer.XAYDA, //gender
            new short[]{844, 845, 846, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            111110, //dame
            new long[]{550000000}, //hp
            new int[]{19}, //map join
            new int[][]{
                {Skill.MASENKO, 7, 1000},
                {Skill.ANTOMIC, 7, 10000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán", "|-1|Đại ca Doraemon có nhầm không nhỉ",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, //text chat 2
            new String[]{}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER //type appear
    );

    public static final BossData DORAEMON = new BossData(
            "Doraemon",
            ConstPlayer.XAYDA,
            new short[]{790, 791, 792, -1, -1, -1},
            160000,
            new long[]{650000000},
            new int[]{19},
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, //skill//skill
            new String[]{"|-2|Á đù, Doraemon !!!!!"}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            REST_15_M, //second rest
            new int[]{BossID.CHAIEN, BossID.XEKO, BossID.NOBITA, BossID.XUKA}
    );

    //**************************************************************************
    public static final BossData VEGETA = new BossData(
            "Vegeta", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{940, 941, 942, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            35000, //dame
            new long[]{1500000000}, //hp
            new int[]{105, 106, 107, 108, 109, 110}, //map join
            (int[][]) Util.addArray(FULL_LIENHOAN, FULL_MASENKO), //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|lewlew đồ không có ny?"
            }, //text chat 2
            new String[]{"|-2|lewlew"}, //text chat 3
            REST_15_M
    );

    public static final BossData BLACK_GOKU_BASE = new BossData(
            "Black Goku", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{879, 880, 881, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            87700, //dame
            new long[]{1000000000}, //hp
            new int[]{0, 1, 2, 3, 4, 5, 6, 27, 28, 29}, //map join
            (int[][]) Util.addArray(FULL_GALICK, FULL_KAMEJOKO, FULL_LIENHOAN), //skill
            new String[]{"|-1|Ta là Sôn Gô Ku",
                "|-1|Cơ thể này,sức mạnh này",
                "|-1|Ta khá thích việc loại bỏ các ngươi",
                "|-1|Mau chấp nhận số phận đi lũ sâu bọ"
            }, //text chat 1
            new String[]{"|-1|Các ngươi chỉ có vậy thôi sao?",
                "|-1|Đúng là loài người thấp kém",
                "|-2|Ngươi nói như thể ngươi không phải con người vậy?",
                "|-2|Chiếc nhẫn kia lẽ nào ngươi là một Kaioshin?!",
                "|-1|Các ngươi không nên biết quá nhiều",
                "|-2|Xem đòn đánh của ta đây !",
                "|-1|Được thôi, nếu muốn chết đến vậy, ta rất vui lòng!!"
            }, //text chat 2
            new String[]{"|-1|Biến hình! Super Sayan Rose"}, //text chat 3
            REST_15_M //second rest
    );
    //**************************************************************************
    public static final BossData BLACK_GOKU = new BossData(
            "Black Goku", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{879, 880, 881, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            97700, //dame
            new long[]{977000000}, //hp
            new int[]{102, 92, 93, 94, 96, 97, 98, 99, 100}, //map join
            (int[][]) Util.addArray(FULL_GALICK, FULL_KAMEJOKO), //skill
            new String[]{"|-1|Ta là Sôn Gô Ku",
                "|-1|Cơ thể này,sức mạnh này",
                "|-1|Ta khá thích việc loại bỏ các ngươi",
                "|-1|Mau chấp nhận số phận đi lũ sâu bọ"
            }, //text chat 1
            new String[]{"|-1|Các ngươi chỉ có vậy thôi sao?",
                "|-1|Đúng là loài người thấp kém",
                "|-2|Ngươi nói như thể ngươi không phải con người vậy?",
                "|-2|Chiếc nhẫn kia lẽ nào ngươi là một Kaioshin?!",
                "|-1|Các ngươi không nên biết quá nhiều",
                "|-2|Xem đòn đánh của ta đây !",
                "|-1|Được thôi, nếu muốn chết đến vậy, ta rất vui lòng!!"
            }, //text chat 2
            new String[]{"|-1|Biến hình! Super Sayan Rose"}, //text chat 3
            REST_15_M //second rest
    );

    public static final BossData SUPER_BLACK_GOKU = new BossData(
            "Super Black Goku", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{553, 880, 881, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            125000, //dame
            new long[]{1250000000}, //hp
            new int[]{102, 92, 93, 94, 96, 97, 98, 99, 100}, //map join
            new int[][]{
                {Skill.THAI_DUONG_HA_SAN, 7, 30000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 100000},
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 10000}},
            //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Ta chính là người mang thân thể của Songoku",
                "|-1|Sức mạnh của ta là không có giới hạn",
                "|-1|Ta sẽ thống trị vũ trụ",
                "|-1|Để ta nói cho nghe,người Sayan sau khi hồi phục sức mạnh sẽ tăng lên rất nhiều",
                "|-2|Tại sao ngươi lại lấy thân thể của songoku chứ?"
            }, //text chat 2

            new String[]{"|-1|Chúng ta sẽ gặp lại nhau sớm thôi",
                "|-2|Ngươi nói gì chứ?"}, //text chat 3
            REST_15_M
    );

    //-------------------------------------------------------------------
    public static final BossData KAIOSHIN_ZAMAS = new BossData(
            "Kaioshin Zamas", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{433, 904, 905, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            75500, //dame
            new long[]{750000000}, //hp
            new int[]{102, 92, 93, 94, 96, 97, 98, 99, 100}, //map join
            new int[][]{
                {Skill.GALICK, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 100000},
                {Skill.TAI_TAO_NANG_LUONG, 4, 1000},
                {Skill.KAMEJOKO, 7, 30000}}, //skill
            new String[]{"|-1|Kia là một con người sao?",
                "|-3|Ủa tên kia là ai vậy?",
                "|-2|Lẽ nào đúng như chúng ta đã nghĩ",
                "|-1|Lũ con người không đủ tư cách để nói chuyện với ta",
                "|-2|Zamas! Tại sao chứ !",
                "|-1|Ta sẽ cho người biết sức mạnh của một vị thần là như thế nào !"
            }, //text chat 1
            new String[]{"|-1|Ta là kaioshin của vũ trụ thứ 10 ",
                "|-1|Tên của ta là Zamas, ta sẽ thay đổi thế giới này",
                "|-1|Lũ con người các ngươi là những thứ ta cần loại bỏ đầu tiên",
                "|-2|Tại sao các ngươi lại nhắm tới con người bọn ta chứ?",
                "|-1|Bởi vì ta muốn thực hiện kế hoạch đưa con người về số 0 !",
                "|-1|Lần này ta không nương tay đâu!",
                "|-2|Ngươi thực sự rất mạnh. Nhưng chưa đủ thực lực đâu!!",
                "|-1|Cái gì!? Đó là điều ngu ngốc nhất ta từng nghe! Mau biến đi",
                "|-1|Hắn thực sự rất mạnh, đúng là cuộc chiến hay",
                "|-3|Không lí nào ta lại run sợ bọn con người sao"
            }, //text chat 2

            new String[]{"|-1|Chỉ còn một cách duy nhất mà thôi",
                "|-1|Bông tai Porata!"}, //text chat 3
            REST_15_M
    );

    public static final BossData THAN_KAIOSHIN_ZAMAS = new BossData(
            "Thần Zamas Tối Thượng", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{903, 904, 905, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            170000, //dame
            new long[]{1700000000}, //hp
            new int[]{102, 92, 93, 94, 96, 97, 98, 99, 100}, //map join
            new int[][]{
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 50000},
                {Skill.KAMEJOKO, 5, 3000},
                {Skill.TAI_TAO_NANG_LUONG, 1, 10000},
                {Skill.QUA_CAU_KENH_KHI, 7, 120000}},
            //skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Ta chính là thế giới",
                "|-1|Ta chính là công lí",
                "|-1|Hãy chiêm ngưỡng vẻ đẹp của ta !Hỡi con người",
                "|-1|Sức mạnh to lớn nằm trong cơ thể bất tử",
                "|-1|Ta sẽ đem công lí tới toàn bộ vũ trụ này", "|-2|Ngươi cứ lải nhải hoài 2 chữ công lí vậy?", "|-1|Lũ các ngươi làm ta thấy đau rồi ấy haha"
            }, //text chat 2
            new String[]{}, //text chat 3
            REST_15_M
    );

    //**************************************************************************
    public static final BossData SUPPERBROLY = new BossData(
            "Super Broly", //name
            ConstPlayer.XAYDA, //gender
            new short[]{294, 295, 296, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            Util.nextInt(1, 2), //dame
            new long[]{Util.nextInt(1000000, 16077777)}, //hp
            new int[]{6, 27, 28, 29, 30, 13, 31, 32, 33, 34, 10, 19, 20, 35, 36, 37, 38}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 7, 100},
                {Skill.TAI_TAO_NANG_LUONG, 1, 15000}},
            new String[]{
                "|-1|Tuy không biết các ngươi là ai, nhưng ta rất ấn tượng đấy!",
                "|-2|Ta cũng cảm thấy phấn khích lắm!"
            },
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                "|-1|Gaaaaaa",
                "|-2|Không..thể..nào!!",
                "|-2|Tên này điên thật rồi!!",
                "|-1|Được thôi, nếu muốn chết đến vậy, ta rất vui lòng!!"
            }, //text chat 2
            new String[]{"|-1|Khôngggggggg!!"},
            REST_15_M
    );

    public static final BossData BROLY_1 = new BossData(
            "Broly", //name
            ConstPlayer.XAYDA, //gender
            new short[]{291, 292, 293, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            4000,
            new long[]{Util.nextInt(4900, 4900)}, //hp
            new int[]{6, 27, 28, 29, 30, 13, 31, 32, 33, 34, 10, 19, 20, 35, 36, 37, 38}, //map join
            new int[][]{{Skill.KAMEJOKO, 7, 100},
            {Skill.TAI_TAO_NANG_LUONG, 1, 10000}}, //skill
            new String[]{
                "|-1|Tuy không biết các ngươi là ai, nhưng ta rất ấn tượng đấy!",
                "|-2|Ta cũng cảm thấy phấn khích lắm!"
            }, //text chat 1
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                "|-1|Gaaaaaa",
                "|-2|Không..thể..nào!!",
                "|-2|Tên này điên thật rồi!!",
                "|-1|Được thôi, nếu muốn chết đến vậy, ta rất vui lòng!!"
            }, //text chat 2
            new String[]{"|-1|Khôngggggggg!!"}, //text chat 3
            REST_15_M //type appear
    );
    //************************************************************************** Boss hủy diệt
    public static final BossData THIEN_SU_WHIS = new BossData(
            "Thiên sứ Whis", //name
            ConstPlayer.NAMEC, //gender
            new short[]{838, 839, 840, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100, //dame
            new long[]{150}, //hp
            new int[]{146, 147, 148}, //map join
            new int[][]{
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 50000},},//skill
            new String[]{"|-2|Kia là ai thế",
                "|-1|Xin chào! Chúng tôi tới đây để tìm người!",
                "|-1|Ngài Berrus! Có vẻ hắn ta không có ở đây",
                "|-1|Ta có thể hỏi các ngươi",
                "|-2|Chuyện gì vậy?",
                "|-1|Các ngươi có biết tên Super Sayan God ở đâu không?",
                "|-2|Super Sayan God? đó là gì vậy?",
                "|-1|Có vẻ giấc mơ của ngài Berrus là bịp rồi! Hô Hô",
                "|0|Nếu các ngươi không biết hắn ở đâu",
                "|0|Chắc ta phải phá hủy hành tinh này vậy"
            }, //text chat 1
            new String[]{"|-1|Ta có thể ngồi ăn một chút được rồi!",
                "|-1|Các ngươi vẫn yếu vẫn như mọi khi",
                "|0|Thật là bực mình!",
                "|-2|Đây là sức mạnh của một thiên sứ sao?",
                "|-1|Hô Hô",
                "|-1|Các ngươi không đánh bại được ta đâu!",
                "|-2|Không thể nào",
                "|-2|Tại sao lại vậy chứ !",}, //text chat 2
            new String[]{"|-1|Ta đi về đây!Cảm ơn vì món ăn"}, //text chat 3
            REST_15_M, //second rest
            new int[]{BossID.THAN_HUY_DIET}
    );
    public static final BossData THAN_HUY_DIET = new BossData(
            "Thần Hủy Diệt Berrus", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{508, 509, 510, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            180000, //dame
            new long[]{1800000000}, //hp
            new int[]{146, 147, 148}, //map join
            new int[][]{
                {Skill.MASENKO, 7, 200},
                {Skill.LIEN_HOAN, 7, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Ta sẽ phá hủy hành tinh này",
                "|-1|Chán quá!",
                "|-1|Ta vẫn chưa dùng hết sức đâu!",
                "|-2|Hắn ta không cần phòng thủ luôn!",}, //text chat 2
            new String[]{"|-1|Ta buồn ngủ quá!"}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );
    public static final BossData THIEN_SU_VADOS = new BossData(
            "Thiên sứ Vados", //name
            ConstPlayer.NAMEC, //gender
            new short[]{530, 531, 532, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            100, //dame
            new long[]{150}, //hp
            new int[]{146, 147, 148}, //map join
            new int[][]{
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 50000},},//skill
            new String[]{"|-2|Kia là ai thế",
                "|-1|Ta là Vados",
                "|-1|Ta tới đây để kiếm một thứ",
                "|-1|Đó chính là Ngọc Rồng Siêu Cấp",
                "|0|Bọn này ta cho 1 búng là bay !"
            }, //text chat 1
            new String[]{"|-1|Ồ",
                "|-1|Ta làm vậy có hơi quá không?",
                "|0|Thật là bực mình!",
                "|-2|Sao ông ta lại mạnh tới vậy ?",
                "|-1|Hô Hô",
                "|-1|Các ngươi muốn đánh bại một Thiên Sứ sao?",
                "|-2|Khốn khiếp!",
                "|-2|Tại sao lại vậy chứ !",}, //text chat 2
            new String[]{"|-1|Hẹn gặp lại,ta rất hài lòng về cuộc chiến"}, //text chat 3
            REST_15_M, //second rest
            new int[]{BossID.THAN_HUY_DIET_CHAMPA}
    );
    public static final BossData THAN_HUY_DIET_CHAMPA = new BossData(
            "Thần Hủy Diệt Champa", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{511, 512, 513, -1, -1, 77}, //outfit {head, body, leg, bag, aura, eff}
            180000, //dame
            new long[]{1800000000}, //hp
            new int[]{146, 147, 148}, //map join
            new int[][]{
                {Skill.MASENKO, 3, 400},
                {Skill.LIEN_HOAN, 7, 300},},//skill//skill
            new String[]{"|-1|Các ngươi có biết",
                "|-1|Ngọc Rồng Siêu Cấp đang ở đâu hay không?",
                "|-2|Ai mà biết được",}, //text chat 1
            new String[]{"|-1|Một lũ yếu ớt",
                "|-1|Ta sẽ phá hủy hành tinh này",
                "|-1|Chán quá!",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-2|Hắn ta không cần phòng thủ luôn!",}, //text chat 2
            new String[]{"|-1|Chết tiệt!"}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );
    //**************************************************************************
    //************************************************************************** Boss goku
    public static final BossData SONGOKU_TA_AC = new BossData(
            "Siêu Goku Tà Ác", //name
            ConstPlayer.XAYDA, //gender
            new short[]{543, 57, 999, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            180000, //dame
            new long[]{1800000000}, //hp
            new int[]{155}, //map join
            new int[][]{
                {Skill.THAI_DUONG_HA_SAN, 7, 100000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 100000},
                {Skill.KAMEJOKO, 7, 10000},
                {Skill.LIEN_HOAN, 7, 1000}},
            new String[]{"|-1|Ta bị sao thế này !",
                "|-2|Chú Goku",
                "|-2|Đó không còn là chú Goku nữa rồi",
                "|-1|GAAAAAAAAAAAAAA!."
            }, //text chat 1
            new String[]{"|-2|Tỉnh lại đi chú Goku",
                "|-2|Đừng để bị hắn chi phối!",
                "|-1|Định chạy trốn hả?",
                "|-1|Ta sẽ tàn sát khu này trong vòng 5 phút nữa",
                "|-2|Không được rồi!",
                "|-2|Phải cố hết sức thôi"
            }, //text chat 2
            new String[]{"|-2|Mau nghỉ ngơi nào chú Goku"}, //text chat 3
            REST_15_M //second rest
    );

    //************************************************************************** Boss nrd
    public static final BossData Rong_1Sao = new BossData(
            "Rồng Syn 1 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{204, 205, 206, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            10000, //dame
            new long[]{50000000}, //hp
            new int[]{85}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 10000},
                {Skill.KAMEJOKO, 5, 10000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest
    );
    public static final BossData Rong_2Sao = new BossData(
            "Rồng Haze 2 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{219, 220, 221, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            10000, //dame
            new long[]{50000000}, //hp
            new int[]{86}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.KAMEJOKO, 5, 1000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest

    );
    public static final BossData Rong_3Sao = new BossData(
            "Rồng Eis 3 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{207, 208, 209, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            10000, //dame
            new long[]{50000000}, //hp
            new int[]{87}, //map join
            new int[][]{
                {Skill.THOI_MIEN, 4, 125000},
                {Skill.THAI_DUONG_HA_SAN, 3, 150000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 100000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest

    );
    public static final BossData Rong_4Sao = new BossData(
            "Rồng Nuova 4 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{210, 211, 212, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            10000, //dame
            new long[]{50000000}, //hp
            new int[]{88}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 10000},
                {Skill.TROI, 7, 100000},
                {Skill.KAMEJOKO, 5, 10000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest
    );
    public static final BossData Rong_5Sao = new BossData(
            "Rồng Rage 5 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{213, 214, 215, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            10000, //dame
            new long[]{50000000}, //hp
            new int[]{89}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 5, 10000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest
    );
    public static final BossData Rong_6Sao = new BossData(
            "Rồng Oceanus 6 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{222, 223, 224, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            10000, //dame
            new long[]{50000000}, //hp
            new int[]{90}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 7, 4000},
                {Skill.KAMEJOKO, 5, 10000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest
    );
    public static final BossData Rong_7Sao = new BossData(
            "Rồng Naturon 7 Sao", //name
            ConstPlayer.XAYDA, //gender
            new short[]{216, 217, 218, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            10000, //dame
            new long[]{50000000}, //hp
            new int[]{91}, //map join
            new int[][]{
                {Skill.KAMEJOKO, 5, 1000}}, //skill
            new String[]{"|-1|Gaaaaaa !!!!!!!!",
                "|-2|Tên kia là ai vậy",
                "|-1|Sức mạnh tà ác !"
            }, //text chat 1
            new String[]{"|-1|Ta muốn tìm một đối thủ xứng tầm",
                "|-1|Đi chết đi!",
                "|-1|Các ngươi không phải đối thủ của ta đâu",
                "|-1|trạng thái Tà Ác sẽ thiêu rụi mày"
            }, //text chat 2
            new String[]{"|-2|Tên đó mạnh thật!"}, //text chat 3
            REST_24_H //second rest
    );
    //************************************************************************** Team Mabu 12h
    public static final BossData DRABURA = new BossData(
            "Ma Vương Dabura",
            ConstPlayer.XAYDA,
            new short[]{418, 419, 420, -1, -1, -1},
            10000,
            new long[]{50000000},
            new int[]{114},
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.DEMON, 7, 10000}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_1_S
    );
    public static final BossData DRABURA_2 = new BossData(
            "Ma Vương Dabura",
            ConstPlayer.XAYDA,
            new short[]{418, 419, 420, -1, -1, -1},
            20000,
            new long[]{50000000},
            new int[]{115},
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.DEMON, 7, 10000}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_1_S
    );
    public static final BossData BUI_BUI = new BossData(
            "Bui Bui",
            ConstPlayer.XAYDA,
            new short[]{451, 452, 453, -1, -1, -1},
            20000,
            new long[]{300000000},
            new int[]{117},
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.DEMON, 7, 10000}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_1_S
    );
    public static final BossData BUI_BUI_2 = new BossData(
            "Bui Bui",
            ConstPlayer.XAYDA,
            new short[]{451, 452, 453, -1, -1, -1},
            20000,
            new long[]{500000000},
            new int[]{118},
            new int[][]{
                {Skill.LIEN_HOAN, 7, 1000},
                {Skill.DEMON, 7, 10000}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_1_S
    );
    public static final BossData YACON = new BossData(
            "Yacôn",
            ConstPlayer.XAYDA,
            new short[]{415, 416, 417, -1, -1, -1},
            20000,
            new long[]{500000000},
            new int[]{119},
            new int[][]{
                {Skill.DEMON, 7, 100}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"}, //text chat 1
            new String[]{"|-1|Thấy ảo chưa nè!"}, //text chat 2
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."}, //text chat 3
            REST_1_S
    );
    public static final BossData MABU_12H = new BossData(
            "Mabư",
            ConstPlayer.XAYDA,
            new short[]{297, 298, 299, -1, -1, -1},
            1000000, // Dame
            new long[]{1500000000}, // Hp
            new int[]{52},
            new int[][]{
                {Skill.KHIEN_NANG_LUONG, 7, 20000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 20000},
                {Skill.DE_TRUNG, 7, 1000},
                {Skill.KAMEJOKO, 7, 100}},
            new String[]{"|-2|Ma nhân Bư đã xuất hiện rồi"},
            new String[]{"|-1|Thấy ảo chưa nè!"},
            new String[]{"|-1|Nhớ mặt tao đấy",
                "|-1|Tobe continue.."},
            REST_30_M
    );
    //************************************************************************** Cooler
    public static final BossData COOLER_1 = new BossData(
            "Cooler", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{317, 318, 319, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            65000, //dame
            new long[]{650000000}, // hp 650000000
            new int[]{105, 106, 107, 108, 109, 110}, //map join
            new int[][]{
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 100000},
                {Skill.DE_TRUNG, 7, 1000},
                {Skill.LIEN_HOAN, 1, 400},
                {Skill.MASENKO, 2, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Chán quá!",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Ta sẽ cho ngươi cái nịt!",}, //text chat 2
            new String[]{"|-1|Biến hình !!!!!!!!!!!!"}, //text chat 3
            REST_15_M
    );
    public static final BossData COOLER_2 = new BossData(
            "Cooler 2", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{320, 321, 322, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            85000, //dame
            new long[]{850000000}, // hp 850000000
            new int[]{105, 106, 107, 108, 109, 110}, //map join
            new int[][]{
                {Skill.DE_TRUNG, 7, 1000},
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                {Skill.KAMEJOKO, 1, 400},
                {Skill.LIEN_HOAN, 2, 300},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{"|-1|Các ngươi thật là yếu ớt",
                "|-1|Ta sẽ phá hủy hành tinh này",
                "|-1|Chán quá!",
                "|-1|Không có ai đủ mạnh để đấu với ta sao?",
                "|-1|Ta nghèo lắm!Đừng săn ta nữa",}, //text chat 2
            new String[]{"|-1|Đen lắm em trai !"}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );
    public static final BossData COOLER_HOAN_THIEN = new BossData(
            "Cooler Hoàn Thiện",
            ConstPlayer.XAYDA,
            new short[]{712, 713, 714, -1, -1, -1},
            180000,
            new long[]{1800000000}, // hp 1800000000
            new int[]{131, 132, 133},
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_LIENHOAN), //skill
            new String[]{"|-2|Hắn ta là ai vậy?"}, //text chat 1
            new String[]{"|-1|Thì ra đây là trái đất",
                "|-1|Hành tinh này bán đi chắc cũng kha khá đó!",
                "|-2|Ngươi làm ta khó chịu rồi đấy",
                "|-1|Ngươi sẽ không bao giờ thắng được đâu!!",
                "|-2|Tên này mạnh quá",}, //text chat 2
            new String[]{"|-1|Xin hãy tha cho ta !",
                "|-1|Ta sẽ cho ngươi nửa số hành tinh ta đang giữ!",
                "|-1|Đừng màaa!"}, //text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER
    );
    //**************************************************************************
    public static final BossData AN_TROM = new BossData(
            "Ăn Trộm", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{201, 202, 203, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            800000000, //dame
            new long[]{10}, //hp
            new int[]{49}, //map join
            new int[][]{
                {Skill.TAI_TAO_NANG_LUONG, 1, 10000},
                {Skill.THAI_DUONG_HA_SAN, 7, 15000}},
            new String[]{"|-2|Hi Hi Hi"}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{"|-1|Ọc Ọc"}, //text chat 3
            REST_5_M
    );

    public static final BossData SUPER_XEN = new BossData(
            "Super Xên", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{2000, 2001, 2002, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            180000, //dame
            new long[]{1800000000}, //hp
            new int[]{103, 102}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 1, 300}, {Skill.LIEN_HOAN, 2, 400}, {Skill.LIEN_HOAN, 3, 500}, {Skill.LIEN_HOAN, 4, 600}, {Skill.LIEN_HOAN, 5, 700}, {Skill.LIEN_HOAN, 6, 800}, {Skill.LIEN_HOAN, 7, 900},
                {Skill.KAMEJOKO, 7, 600}, {Skill.KAMEJOKO, 6, 700}, {Skill.KAMEJOKO, 5, 800}, {Skill.KAMEJOKO, 4, 900}, {Skill.KAMEJOKO, 3, 1000}, {Skill.KAMEJOKO, 2, 1100}, {Skill.KAMEJOKO, 1, 1002},
                {Skill.ANTOMIC, 1, 130}, {Skill.ANTOMIC, 2, 140}, {Skill.ANTOMIC, 3, 150}, {Skill.ANTOMIC, 4, 160}, {Skill.ANTOMIC, 5, 170}, {Skill.ANTOMIC, 6, 190}, {Skill.ANTOMIC, 7, 200},
                {Skill.MASENKO, 1, 210}, {Skill.MASENKO, 5, 220}, {Skill.MASENKO, 6, 230},
                {Skill.TAI_TAO_NANG_LUONG, 1, 5000}, {Skill.TAI_TAO_NANG_LUONG, 3, 10000}, {Skill.TAI_TAO_NANG_LUONG, 7, 15000},
                {Skill.KHIEN_NANG_LUONG, 7, 50000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            REST_15_M
    );

    public static final BossData CUMBER_BLACK = new BossData(
            "CumBer Black", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{1204, 1205, 1206, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            5500000, //dame
            new long[]{1800000000}, //hp
            new int[]{98, 99, 100, 96, 92, 93}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 1, 300}, {Skill.LIEN_HOAN, 2, 400}, {Skill.LIEN_HOAN, 3, 500}, {Skill.LIEN_HOAN, 4, 600}, {Skill.LIEN_HOAN, 5, 700}, {Skill.LIEN_HOAN, 6, 800}, {Skill.LIEN_HOAN, 7, 900},
                {Skill.KAMEJOKO, 7, 600}, {Skill.KAMEJOKO, 6, 700}, {Skill.KAMEJOKO, 5, 800}, {Skill.KAMEJOKO, 4, 900}, {Skill.KAMEJOKO, 3, 1000}, {Skill.KAMEJOKO, 2, 1100}, {Skill.KAMEJOKO, 1, 1002},
                {Skill.ANTOMIC, 1, 130}, {Skill.ANTOMIC, 2, 140}, {Skill.ANTOMIC, 3, 150}, {Skill.ANTOMIC, 4, 160}, {Skill.ANTOMIC, 5, 170}, {Skill.ANTOMIC, 6, 190}, {Skill.ANTOMIC, 7, 200},
                {Skill.MASENKO, 1, 210}, {Skill.MASENKO, 5, 220}, {Skill.MASENKO, 6, 230},
                {Skill.DE_TRUNG, 7, 1000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},
                {Skill.TAI_TAO_NANG_LUONG, 1, 5000}, {Skill.TAI_TAO_NANG_LUONG, 3, 10000}, {Skill.TAI_TAO_NANG_LUONG, 7, 15000},
                {Skill.KHIEN_NANG_LUONG, 7, 50000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            REST_15_M
    );

    public static final BossData CUMBER_YELLOW = new BossData(
            "Cumber Vàng", //name
            ConstPlayer.TRAI_DAT, //gender
            new short[]{1207, 1208, 1209, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            70000000, //dame
            new long[]{1800000000}, //hp
            new int[]{98, 99, 100, 96, 92, 93}, //map join
            new int[][]{
                {Skill.LIEN_HOAN, 1, 300}, {Skill.LIEN_HOAN, 2, 400}, {Skill.LIEN_HOAN, 3, 500}, {Skill.LIEN_HOAN, 4, 600}, {Skill.LIEN_HOAN, 5, 700}, {Skill.LIEN_HOAN, 6, 800}, {Skill.LIEN_HOAN, 7, 900},
                {Skill.KAMEJOKO, 7, 600}, {Skill.KAMEJOKO, 6, 700}, {Skill.KAMEJOKO, 5, 800}, {Skill.KAMEJOKO, 4, 900}, {Skill.KAMEJOKO, 3, 1000}, {Skill.KAMEJOKO, 2, 1100}, {Skill.KAMEJOKO, 1, 1002},
                {Skill.ANTOMIC, 1, 130}, {Skill.ANTOMIC, 2, 140}, {Skill.ANTOMIC, 3, 150}, {Skill.ANTOMIC, 4, 160}, {Skill.ANTOMIC, 5, 170}, {Skill.ANTOMIC, 6, 190}, {Skill.ANTOMIC, 7, 200},
                {Skill.MASENKO, 1, 210}, {Skill.MASENKO, 5, 220}, {Skill.MASENKO, 6, 230},
                {Skill.DE_TRUNG, 7, 1000},
                {Skill.THAI_DUONG_HA_SAN, 5, 100000},
                {Skill.TAI_TAO_NANG_LUONG, 1, 5000}, {Skill.TAI_TAO_NANG_LUONG, 3, 10000}, {Skill.TAI_TAO_NANG_LUONG, 7, 15000},
                {Skill.KHIEN_NANG_LUONG, 7, 50000},},//skill//skill
            new String[]{}, //text chat 1
            new String[]{}, //text chat 2
            new String[]{}, //text chat 3
            REST_15_M
    );
    public static final BossData SOI_HEC_QUYN = new BossData(
            "Sói hẹc quyn", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{394, 395, 396, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            1000, // dame
            new long[]{100_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );

    public static final BossData O_DO = new BossData(
            "Ở dơ", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{400, 401, 402, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            3000, // dame
            new long[]{250_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );

    public static final BossData XINBATO = new BossData(
            "Xinbatô", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{359, 360, 361, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            6000, // dame
            new long[]{500_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );

    public static final BossData CHA_PA = new BossData(
            "Cha pa", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{362, 363, 364, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            9000, // dame
            new long[]{1_000_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );
    public static final BossData PON_PUT = new BossData(
            "Pon put", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{365, 366, 367, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            15000, // dame
            new long[]{2_500_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );

    public static final BossData CHAN_XU = new BossData(
            "Chan xư", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{371, 372, 373, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            20000, // dame
            new long[]{5_000_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );

    public static final BossData TAU_PAY_PAY = new BossData(
            "Tàu Pảy Pảy", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{92, 93, 94, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            30000, // dame
            new long[]{20_000_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );

    public static final BossData YAMCHA = new BossData(
            "Yamcha", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{374, 375, 376, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            40000, // dame
            new long[]{50_000_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );

    public static final BossData JACKY_CHUN = new BossData(
            "Jacky Chun", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{356, 357, 358, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            50000, // dame
            new long[]{250_000_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );

    public static final BossData THIEN_XIN_HANG = new BossData(
            "Thiên xin hăng", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{368, 369, 370, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            100000, // dame
            new long[]{750_000_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );
    public static final BossData THIEN_XIN_HANG_CLONE = new BossData(
            "Thiên xin hăng", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{368, 369, 370, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            100000, // dame
            new long[]{75_000_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK, FULL_TDHS), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );

    public static final BossData LIU_LIU = new BossData(
            "Liu Liu", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{397, 398, 399, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            250000, // dame
            new long[]{1_000_000_000}, // hp
            new int[]{168}, // map join
            (int[][]) Util.addArray(FULL_KAMEJOKO, FULL_GALICK), // skill
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_5_S // second rest
    );
    //************************************************************************** Boss Võ Đài Sinh Tử
    public static final BossData DRACULA = BossData.builder()
            .name("Dracula")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(50000)
            .hp(new long[]{7000000})
            .outfit(new short[]{353, 354, 355, 144, 50, 85})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
        {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
        {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
        {Skill.GALICK, 1, 100}
    })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData NGUOIVOHINH = BossData.builder()
            .name("Người Vô Hình")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(60000)
            .hp(new long[]{15000000})
            .outfit(new short[]{377, 378, 379, 144, 50, 85})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
        {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
        {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
        {Skill.GALICK, 1, 100}
    })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData BONGBANG = BossData.builder()
            .name("Bông Băng")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(65000)
            .hp(new long[]{25000000})
            .outfit(new short[]{350, 351, 352, 144, 50, 85})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
        {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
        {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
        {Skill.GALICK, 1, 100}
    })
            .secondsRest(REST_5_S)
            .build();

    public static final BossData SATAN = BossData.builder()
            .name("Sa Tăng")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(70000)
            .hp(new long[]{50000000})
            .outfit(new short[]{344, 345, 346, 144, 50, 85})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
        {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
        {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
        {Skill.GALICK, 1, 100}
    })
            .secondsRest(REST_5_S)
            .build();
    public static final BossData THODAUBAC = BossData.builder()
            .name("Thỏ Đầu Bạc")
            .gender(ConstPlayer.TRAI_DAT)
            .dame(40000)
            .hp(new long[]{5000000})
            .outfit(new short[]{347, 348, 349, 144, 50, 85})
            .mapJoin(new int[]{129})
            .skillTemp(new int[][]{
        {Skill.DRAGON, 1, 100}, {Skill.DRAGON, 2, 200}, {Skill.DRAGON, 3, 300}, {Skill.DRAGON, 7, 700},
        {Skill.KAMEJOKO, 1, 1000}, {Skill.KAMEJOKO, 2, 1200}, {Skill.KAMEJOKO, 5, 1500}, {Skill.KAMEJOKO, 7, 1700},
        {Skill.GALICK, 1, 100}
    })
            .secondsRest(REST_5_S)
            .build();
    public static final BossData TAP_SU_0 = new BossData(
            "Tập sự-0", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{526, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{350_000}, // hp
            new int[]{131}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 500},
                {Skill.ANTOMIC, 1, 500},
                {Skill.KAMEJOKO, 1, 500},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 1, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData TAP_SU_1 = new BossData(
            "Tập sự-1", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{526, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{350_000}, // hp
            new int[]{131}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 2, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData TAP_SU_2 = new BossData(
            "Tập sự-2", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{526, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{350_000}, // hp
            new int[]{131}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 3, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData TAP_SU_3 = new BossData(
            "Tập sự-3", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{526, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{350_000}, // hp
            new int[]{131}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 4, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData TAP_SU_4 = new BossData(
            "Tập sự-4", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{526, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{350_000}, // hp
            new int[]{131}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 5, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData TAN_BINH_5 = new BossData(
            "Tân binh-5", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{527, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{450_000}, // hp
            new int[]{131}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 6, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            REST_15_M,
            new int[]{BossID.TAP_SU_0, BossID.TAP_SU_1, BossID.TAP_SU_2, BossID.TAP_SU_3,
                BossID.TAP_SU_4} // type appear
    );

    public static final BossData TAN_BINH_0 = new BossData(
            "Tân binh-0", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{527, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{450_000}, // hp
            new int[]{132}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 1, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData TAN_BINH_1 = new BossData(
            "Tân binh-1", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{527, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{450_000}, // hp
            new int[]{132}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 2, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData TAN_BINH_2 = new BossData(
            "Tân binh-2", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{527, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{450_000}, // hp
            new int[]{132}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 3, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData TAN_BINH_3 = new BossData(
            "Tân binh-3", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{527, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{450_000}, // hp
            new int[]{132}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 4, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData TAN_BINH_4 = new BossData(
            "Tân binh-4", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{527, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{450_000}, // hp
            new int[]{132}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 5, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData CHIEN_BINH_5 = new BossData(
            "Chiến binh-5", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{528, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{500_000}, // hp
            new int[]{132}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 6, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            REST_15_M,
            new int[]{BossID.TAN_BINH_0, BossID.TAN_BINH_1, BossID.TAN_BINH_2, BossID.TAN_BINH_3,
                BossID.TAN_BINH_4} // type appear
    );

    public static final BossData CHIEN_BINH_0 = new BossData(
            "Chiến binh-0", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{528, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{500_000}, // hp
            new int[]{133}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 2, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData CHIEN_BINH_1 = new BossData(
            "Chiến binh-1", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{528, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{500_000}, // hp
            new int[]{133}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 3, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData CHIEN_BINH_2 = new BossData(
            "Chiến binh-2", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{528, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{500_000}, // hp
            new int[]{133}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 4, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData CHIEN_BINH_3 = new BossData(
            "Chiến binh-3", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{528, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{500_000}, // hp
            new int[]{133}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 5, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData CHIEN_BINH_4 = new BossData(
            "Chiến binh-4", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{528, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{500_000}, // hp
            new int[]{133}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 6, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData DOI_TRUONG_5 = new BossData(
            "Đội trưởng-5", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{529, 525, 524, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{1_000_000}, // hp
            new int[]{133}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DEMON, 1, 1000},
                {Skill.GALICK, 1, 1000},
                {Skill.MASENKO, 1, 1000},
                {Skill.ANTOMIC, 1, 1000},
                {Skill.KAMEJOKO, 1, 1000},
                {Skill.LIEN_HOAN, 1, 1000},
                {Skill.BIEN_KHI, 7, 3000000},
                {Skill.DICH_CHUYEN_TUC_THOI, 7, 30000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Khí công pháo"}, // text chat 2
            new String[]{}, // text chat 3
            REST_15_M,
            new int[]{BossID.CHIEN_BINH_0, BossID.CHIEN_BINH_1, BossID.CHIEN_BINH_2, BossID.CHIEN_BINH_3,
                BossID.CHIEN_BINH_4} // type appear
    );
    /**
     * ********************************BUJIN***************************************
     */
    public static final BossData BUJIN = new BossData(
            "Bujin", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{341, 342, 343, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            170000, // dame
            new long[]{150_000_000}, // hp
            new int[]{3, 4, 5, 6, 27, 28, 29, 30}, // map join
            new int[][]{
                {Skill.DEMON, 7, 1000},
                {Skill.MASENKO, 7, 1000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán",
                "|-1|Các ngươi không nhúc nhích được sao?",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, // text chat 2
            new String[]{"|-1|Cay quá!",
                "|-1|Ta mà lại thua được sao?",
                "|-1|Hãy trả thù cho ta!"
            }, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER);

    public static final BossData KOGU = new BossData(
            "Kogu", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{329, 330, 331, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            180000, // dame
            new long[]{160_000_000}, // hp
            new int[]{3, 4, 5, 6, 27, 28, 29, 30}, // map join
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.TROI, 4, 50000},
                {Skill.ANTOMIC, 4, 1000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Trói"
            }, // text chat 2
            new String[]{"|-1|Cứu"
            }, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData ZANGYA = new BossData(
            "Zangya", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{332, 333, 334, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            207200, // dame
            new long[]{170_000_000}, // hp
            new int[]{3, 4, 5, 6, 27, 28, 29, 30}, // map join
            new int[][]{
                {Skill.GALICK, 7, 1000},
                {Skill.TROI, 5, 50000},
                {Skill.ANTOMIC, 3, 3000},}, // skill//skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Trói"
            }, // text chat 2
            new String[]{"|-1|Cứu"
            }, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData BIDO = new BossData(
            "Bido", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{335, 336, 337, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            250200, // dame
            new long[]{200_000_000}, // hp
            new int[]{3, 4, 5, 6, 27, 28, 29, 30}, // map join
            new int[][]{
                {Skill.DRAGON, 7, 1000},
                {Skill.KAMEJOKO, 4, 10000},}, // skill//skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Oải rồi hả?", "|-1|Ê cố lên nhóc",
                "|-1|Chán",
                "|-1|Một mình tao chấp hết tụi bây",
                "|-1|HAHAHAHA", "|-1|Chỉ là bọn con nít"
            }, // text chat 2
            new String[]{"|-1|Cay quá!"
            }, // text chat 3
            TypeAppear.APPEAR_WITH_ANOTHER // type appear
    );

    public static final BossData BOJACK = new BossData(
            "Bojack", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{323, 324, 325, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            300000, // dame
            new long[]{220_000_000}, // hp
            new int[]{3, 4, 5, 6, 27, 28, 29, 30}, // map join
            new int[][]{
                {Skill.TROI, 7, 120000},
                {Skill.MASENKO, 7, 1000},
                {Skill.GALICK, 7, 1000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Hahaha"
            }, // text chat 2
            new String[]{"|-1|Hahaha"
            }, // text chat 3
            REST_15_M,
            new int[]{BossID.BUJIN, BossID.KOGU, BossID.BIDO, BossID.ZANGYA,} // type appear
    );

    public static final BossData SUPER_BOJACK = new BossData(
            "Siêu Bojack", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{326, 327, 328, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            300000, // dame
            new long[]{220_000_000}, // hp
            new int[]{3, 4, 5, 6, 27, 28, 29, 30}, // map join
            new int[][]{
                {Skill.THOI_MIEN, 7, 100000},
                {Skill.KHIEN_NANG_LUONG, 7, 100000},
                {Skill.GALICK, 7, 1000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Hahaha"
            }, // text chat 2
            new String[]{"|-1|Hahaha"
            }, // text chat 3
            REST_15_M,
            TypeAppear.ANOTHER_LEVEL // type appear
    );
    public static final BossData SUPER_BOJACK_2 = new BossData(
            "Siêu Bojack", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{326, 327, 328, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            300000, // dame
            new long[]{220_000_000}, // hp
            new int[]{3, 4, 5, 6, 27, 28, 29, 30}, // map join
            new int[][]{
                {Skill.TROI, 3, 60000},
                {Skill.KAMEJOKO, 7, 1000},
                {Skill.GALICK, 7, 1000},}, // skill
            new String[]{}, // text chat 1
            new String[]{"|-1|Hahaha"
            }, // text chat 2
            new String[]{"|-1|Hahaha"
            }, // text chat 3
            REST_15_M);
    public static final BossData ONG_GIA_NOEL = new BossData(
            "Ông già Noel", // name
            ConstPlayer.XAYDA, // gender
            new short[]{657, 658, 659, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            5_000_000, // dame
            new long[]{500}, // hp
            new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 24, 25,
                26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 63, 64, 65, 66, 67, 68, 69, 70,
                71, 72, 73, 74, 75, 76, 77, 79, 80, 81, 82, 83, 84, 92, 93, 94, 96, 97, 98, 99,
                100, 102, 103, 104, 105, 106, 107, 108, 109, 110}, // map join
            new int[][]{
                {Skill.TAI_TAO_NANG_LUONG, 7, Util.nextInt(5000, 10000)}},
            new String[]{}, // text chat 1
            new String[]{"|-1|Mé ri chịch mệt",
                "|-1|Hô hô hô",
                "|-1|Giáng sinh vui vẻ!"}, // text chat 2
            new String[]{"|-1|Giáng sinh vui vẻ!"}, // text chat 3
            REST_1_M // type appear
    );
    public static final BossData KARIN = new BossData(
            "Karin", // name
            ConstPlayer.XAYDA, // gender
            new short[]{89, 90, 91, -1, 1, -1}, // outfit {head, body, leg, bag, aura, eff}
            250, // dame
            new long[]{500}, // hp
            new int[]{46}, // map join
            new int[][]{
                {Skill.GALICK, 1, 1000}},
            new String[]{"|-1|Ta sẽ dạy ngươi vài chiêu",
                "|-1|Ta sẽ đánh hết sức, ngươi cẩn thận nhé"}, // text chat 1
            new String[]{"|-1|Haizzzzz",
                "|-1|Hahaha",
                "|-1|Xem đây"}, // text chat 2
            new String[]{"|-1|OK ta chịu thua"}, // text chat 3
            REST_1_S);

    public static final BossData TAUPAYPAY = new BossData(
            "Tàu Pảy Pảy", // name
            ConstPlayer.XAYDA, // gender
            new short[]{92, 93, 94, -1, 1, -1}, // outfit {head, body, leg, bag, aura, eff}
            300, // dame
            new long[]{10_000}, // hp
            new int[]{47}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},
                {Skill.DRAGON, 2, 2000},
                {Skill.DRAGON, 3, 3000},
                {Skill.DRAGON, 7, 7000},
                {Skill.KAMEJOKO, 7, 2000},
                {Skill.KAMEJOKO, 6, 1800},
                {Skill.KAMEJOKO, 4, 1500},
                {Skill.KAMEJOKO, 2, 1000}
            },
            new String[]{"|-1|Ta sẽ dạy ngươi vài chiêu",
                "|-1|Ta sẽ đánh hết sức, ngươi cẩn thận nhé"}, // text chat 1
            new String[]{"|-1|Haizzzzz",
                "|-1|Hahaha",
                "|-1|Xem đây"}, // text chat 2
            new String[]{"|-1|OK ta chịu thua"}, // text chat 3
            REST_1_S);

    public static final BossData YAJIRO = new BossData(
            "Yajirô", // name
            ConstPlayer.XAYDA, // gender
            new short[]{77, 78, 79, -1, 1, -1}, // outfit {head, body, leg, bag, aura, eff}
            1100, // dame
            new long[]{1100}, // hp
            new int[]{46}, // map join
            new int[][]{
                {Skill.GALICK, 1, 1000}},
            new String[]{"|-1|Ngon nhào vô",
                "|-1|Cho mi biết sự lợi hại của ta"}, // text chat 1
            new String[]{"|-1|Haizzzzz",
                "|-1|Hahaha",
                "|-1|Xem đây"}, // text chat 2
            new String[]{"|-1|Ngươi thật lợi hại"}, // text chat 3
            REST_1_S);

    public static final BossData MRPOPO = new BossData(
            "Mr.PôPô", // name
            ConstPlayer.XAYDA, // gender
            new short[]{83, 84, 85, -1, 1, -1}, // outfit {head, body, leg, bag, aura, eff}
            1100, // dame
            new long[]{5100}, // hp
            new int[]{46}, // map join
            new int[][]{{Skill.GALICK, 1, 30000},
            {Skill.KAMEJOKO, 1, 30000},
            {Skill.THAI_DUONG_HA_SAN, 3, 30000},},
            new String[]{"|-1|Đánh trúng ta 1 cái coi như ngươi thắng",
                "|-1|Đánh trúng ta 3 cái coi như ngươi thắng"}, // text chat 1
            new String[]{"|-1|Haizzzzz",
                "|-1|Hahaha",
                "|-1|Xem đây",
                "|-1|Phù.."}, // text chat 2
            new String[]{"|-1|Thua thì thua"}, // text chat 3
            REST_1_S);

    public static final BossData THUONG_DE = new BossData(
            "Thượng đế", // name
            ConstPlayer.XAYDA, // gender
            new short[]{86, 87, 88, -1, 1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{10_000}, // hp
            new int[]{49}, // map join
            new int[][]{
                {Skill.DRAGON, 1, 1000},},
            new String[]{"|-1|Ta sẽ dạy võ cho con trong phòng tập thời gian này",
                "|-1|Con hãy đánh hết sức nhé, ta sẽ không nương tay đâu"}, // text chat 1
            new String[]{"|-1|Haizzzzz",
                "|-1|Hahaha",
                "|-1|Xem đây"}, // text chat 2
            new String[]{"|-1|Ta rất tự hào về con"}, // text chat 3
            REST_1_S);

    public static final BossData KHI_BUBBLES = new BossData(
            "Khỉ Bubbles", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{95, 96, 97, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            30000, // dame
            new long[]{30_000}, // hp
            new int[]{48}, // map join
            new int[][]{
                {Skill.GALICK, 7, 1000}}, // skill
            new String[]{"|-1|Ù ù khẹt khẹt",
                "|-1|Ù ù khẹt khẹt"}, // text chat 1
            new String[]{"|-1|Ù ù khẹt khẹt",
                "|-1|khẹt khẹt",
                "|-1|ù ù khẹc khẹc",
                "|-1|khẹc khẹc",
                "|-1|éc éc",}, // text chat 2
            new String[]{"|-1|Éc Éc Éc Éc!"}, // text chat 3
            REST_1_S // second rest
    );

    public static final BossData THAN_VU_TRU = new BossData(
            "Thần Vũ Trụ", // name
            ConstPlayer.XAYDA, // gender
            new short[]{98, 99, 100, -1, 1, -1}, // outfit {head, body, leg, bag, aura, eff}
            45000, // dame
            new long[]{45_000}, // hp
            new int[]{48}, // map join
            new int[][]{
                {Skill.GALICK, 7, 1000},
                {Skill.THAI_DUONG_HA_SAN, 7, 20000},},
            new String[]{"|-1|Ta sẽ dạy ngươi chiêu kaio-ken",
                "|-1|Ngươi cũng to gan lắm"}, // text chat 1
            new String[]{"|-1|Haizzzzz",
                "|-1|Hahaha",
                "|-1|Xem đây"}, // text chat 2
            new String[]{"|-1|Tại hôm nay ta...ta hơi bị đau bụng"}, // text chat 3
            REST_1_S);

    public static final BossData TO_SU_KAIO = new BossData(
            "Tổ sư Kaio", // name
            ConstPlayer.XAYDA, // gender
            new short[]{448, 449, 450, -1, 1, -1}, // outfit {head, body, leg, bag, aura, eff}
            45000, // dame
            new long[]{45000}, // hp
            new int[]{50}, // map join
            new int[][]{
                {Skill.GALICK, 1, 60000},},
            new String[]{}, // text chat 1
            new String[]{}, // text chat 2
            new String[]{}, // text chat 3
            REST_1_S);

    public static final BossData WHIS = new BossData(
            "Whis", // name
            ConstPlayer.TRAI_DAT, // gender
            new short[]{838, 839, 840, -1, -1, -1}, // outfit {head, body, leg, bag, aura, eff}
            10000, // dame
            new long[]{550_000}, // hp
            new int[]{154}, // map join
            new int[][]{
                {Skill.DRAGON, 7, 500}}, // skill
            new String[]{"|-1|Ta sẽ dạy ngươi vài chiêu"}, // text chat 1
            new String[]{"|-1|Xem đây",
                "|-1|Haizzzzz",
                "|-1|Hahaha", "|-1|AAAAAAAAAA"}, // text chat 2
            new String[]{}, // text chat 3
            REST_1_M);

}
