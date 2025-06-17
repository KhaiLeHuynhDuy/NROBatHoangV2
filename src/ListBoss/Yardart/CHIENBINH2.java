
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListBoss.Yardart;

import BossMain.BossID;
import BossMain.BossesData;

/**
 *
 * @author Administrator
 */
public class CHIENBINH2 extends Yardart {

    public CHIENBINH2() throws Exception {
        super(BossID.CHIEN_BINH_2, BossesData.CHIEN_BINH_2);
    }

    @Override
    protected void init() {
        x = 582;
        x2 = 652;
        y = 456;
        y2 = 456;
        range = 1000;
        range2 = 150;
        timeHoiHP = 20000;
        rewardRatio = 3;
    }
}
