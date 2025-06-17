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
public class TAPSU2 extends Yardart {

    public TAPSU2() throws Exception {
        super( BossID.TAP_SU_2, BossesData.TAP_SU_2);
    }

    @Override
    protected void init() {
        x = 582;
        x2 = 652;
        y = 432;
        y2 = 456;
        range = 1000;
        range2 = 150;
        timeHoiHP = 30000;
        rewardRatio = 5;
    }
}
