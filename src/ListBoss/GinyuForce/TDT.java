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
public class TDT extends Boss {

    public TDT() throws Exception {
        super(BossID.TIEU_DOI_TRUONG,BossesData.TIEU_DOI_TRUONG);
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
        if (BossManager.gI().getBossByName("Số 1").zone != null) {
            this.changeToTypeNonPK();
        } else {
            if (this.typePk == ConstPlayer.NON_PK) {
                this.changeToTypePK();
            }
            this.attack();
        }
    }
   
}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
