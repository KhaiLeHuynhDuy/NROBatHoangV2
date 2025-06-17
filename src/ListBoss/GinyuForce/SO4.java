/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListBoss.GinyuForce;

import BossMain.Boss;
import BossMain.BossID;
import BossMain.BossManager;
import BossMain.BossStatus;
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
public class SO4 extends Boss {

    public SO4() throws Exception {
        super(BossID.SO_4,BossesData.SO_4);
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
        super.active(); //To change body of generated methods, choose Tools | Templates.
        if (Util.canDoWithTime(st, 900000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }
   
}
