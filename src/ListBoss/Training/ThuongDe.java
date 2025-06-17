/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListBoss.Training;

import BossMain.BossID;
import BossMain.BossManager;
import BossMain.BossStatus;
import BossMain.BossesData;
import Services.func.ChangeMapService;
import ListMap.TrainingService;
import Player.Player;
import Services.MapService;
import Services.Service;
import Utils.Util;

/**
 *
 * @author Administrator
 */
public class ThuongDe extends TrainingBoss {

    public long lastTimeJoinMap = System.currentTimeMillis();

    public ThuongDe(Player player) throws Exception {
        super(BossID.THUONG_DE, BossesData.THUONG_DE);
        this.playerAtt = player;
    }

    @Override
    public void joinMap() {
        if (playerAtt.zone != null) {
            this.zone = playerAtt.zone;
            ChangeMapService.gI().changeMap(this, this.zone, 408, 408);
            this.changeStatus(BossStatus.CHAT_S);
        }
    }

    @Override
    public boolean chatS() {
        if (Util.canDoWithTime(lastTimeJoinMap, 4000)) {
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
                this.lastTimeChatS = System.currentTimeMillis();
                this.timeChatS = 3000;
                doneChatS = true;
            }
        }
        return false;
    }

    @Override
    public void afk() {
        if (Util.canDoWithTime(lastTimeAFK, 5000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
            if (!isPlayerDie) {
                ChangeMapService.gI().changeMap(playerAtt, MapService.gI().getMapCanJoin(playerAtt, 45, 0), 354, 408);
            }
        }
    }

    @Override
    public void leaveMap() {
        ChangeMapService.gI().exitMap(this);
        Player npc = TrainingService.gI().getNonInteractiveNPC(zone, (int) this.id);
        if (npc != null) {
            this.nPoint.hp = this.nPoint.hpMax;
            Service.gI().Send_Info_NV(this);
            this.goToPlayer(npc, false);
        } else {
            TrainingService.gI().luyenTapEnd(playerAtt, (int) this.id);
        }

        this.lastZone = null;
        this.lastTimeRest = System.currentTimeMillis();
        this.changeStatus(BossStatus.REST);
        BossManager.gI().removeBoss(this);
        this.dispose();
    }
}
