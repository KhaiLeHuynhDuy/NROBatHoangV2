package com.girlkun.models.map.sieuhang;

import BossMain.BossData;
import ListBoss.The23rdMartialArtCongress.The23rdMartialArtCongress;
import Player.Player;
import Utils.Util;

public class ClonePlayer extends The23rdMartialArtCongress {

    public ClonePlayer(Player player, BossData data, int id) throws Exception {
        super(Util.randomBossId(), data,5000);
        this.playerAtt = player;
        this.idPlayer = id;
    }
}

