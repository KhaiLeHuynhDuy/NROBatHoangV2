/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListBoss.GinyuForce;

import BossMain.Boss;
import BossMain.BossID;
import BossMain.BossManager;
import BossMain.BossesData;
import Consts.ConstPlayer;
import Maps.ItemMap;
import Player.Player;
import Services.Service;
import Services.TaskService;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class SO3 extends Boss {

    public SO3() throws Exception {
        super(BossID.SO_3,BossesData.SO_3);
    }

   @Override
    public void reward(Player plKill) {
        super.reward(plKill);
        if (this.currentLevel == 1) {
            return;
        }
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }

    @Override
      protected void notifyJoinMap() {
        if (this.currentLevel == 1) {
            return;
        }
        super.notifyJoinMap();
    }
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }

    private long st;

    @Override
    public void active() {
        if (BossManager.gI().getBossByName("Số 4").zone != null) {
            this.changeToTypeNonPK();
        } else {
            if (this.typePk == ConstPlayer.NON_PK) {
                this.changeToTypePK();
            }
            this.attack();
        }
    }
   
}


