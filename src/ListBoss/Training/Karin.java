/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListBoss.Training;

import BossMain.BossID;
import BossMain.BossStatus;
import BossMain.BossesData;
import Consts.ConstTask;
import Services.func.ChangeMapService;
import Player.Player;
import Services.Service;
import Services.TaskService;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class Karin extends TrainingBoss {

    private long lastTimeBay;
    private long lastTimeBay2;

    public Karin(Player player) throws Exception {
        super( BossID.KARIN, BossesData.KARIN);
        this.playerAtt = player;
    }

    @Override
    public void joinMap() {
        if (playerAtt.zone != null) {
            this.zone = playerAtt.zone;
            ChangeMapService.gI().changeMap(this, this.zone, 420, 408);
            this.changeStatus(BossStatus.CHAT_S);
            System.out.println("HP: "+this.nPoint.hpMax);
        }
    }

//    @Override
//    public void die(Player plKill) {
//        this.changeStatus(BossStatus.AFK);
//        this.chatE();
//        this.lastTimeAFK = System.currentTimeMillis();
//        Service.gI().sendPlayerVS(playerAtt, null, (byte) 0);
//    }

    @Override
    public boolean chatS() {
        if (Util.canDoWithTime(lastTimeChatS, timeChatS)) {
            if (this.doneChatS) {
                return true;
            }
            String textChat = this.data[this.currentLevel].getTextS()[playerAtt.isThachDau ? 1 : 0];
            int prefix = Integer.parseInt(textChat.substring(1, textChat.lastIndexOf("|")));
            textChat = textChat.substring(textChat.lastIndexOf("|") + 1);
            if (!this.chat(prefix, textChat)) {
                return false;
            }
            this.moveToPlayer(playerAtt);
            this.lastTimeChatS = System.currentTimeMillis();
            this.timeChatS = 2000;
            doneChatS = true;
        }
        return false;
    }

    @Override
    public void afk() {
        if (Util.canDoWithTime(lastTimeMove, 1500)) {
            this.goToXY(playerAtt.location.x, playerAtt.location.y);
            this.lastTimeMove = System.currentTimeMillis();
        }
        if (Util.canDoWithTime(lastTimeAFK, 3000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }
}
