package Matches;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TOP {

    private int id_player;
    private long power;
    private long ki;
    private long hp;
    private long sd;
    private byte nv;
    private int sk;
    private int pvp;
    private String info1;
    private String info2;
    public int rank;
    private String name;
    private byte gender;
    private short head;
    private short body;
    private short leg;
    private byte subnv;
    private int dicanh;
    private int juventus;
    private long lasttime;
    private long time;
    private int level;
}
