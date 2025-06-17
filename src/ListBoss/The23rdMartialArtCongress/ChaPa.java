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
public class ChaPa extends The23rdMartialArtCongress {

    public ChaPa(Player player) throws Exception {
        super( BossID.CHA_PA, BossesData.CHA_PA);
        this.playerAtt = player;
    }
}
