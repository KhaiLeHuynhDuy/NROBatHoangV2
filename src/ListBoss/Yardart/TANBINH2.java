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
public class TANBINH2 extends Yardart {

    public TANBINH2() throws Exception {
        super( BossID.TAN_BINH_2, BossesData.TAN_BINH_2);
    }

    @Override
    protected void init() {
        x = 582;
        x2 = 652;
        y = 432;
        y2 = 432;
        range = 1000;
        range2 = 150;
        timeHoiHP = 25000;
        rewardRatio = 4;
    }
}
