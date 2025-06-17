/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListBoss.The23rdMartialArtCongress;

import BossMain.BossID;
import BossMain.BossesData;
import Player.Player;

/**
 *
 * @author Administrator
 */
public class Xinbato extends The23rdMartialArtCongress {

    public Xinbato(Player player) throws Exception {
        super( BossID.XINBATO, BossesData.XINBATO);
        this.playerAtt = player;
    }
}
