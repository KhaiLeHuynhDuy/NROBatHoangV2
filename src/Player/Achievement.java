/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Player;

import Consts.ConstAchievement;
import Services.func.Template.AchievementQuest;
import Services.func.Template.AchievementTemplate;
import Server.Manager;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author Administrator
 */
public class Achievement {

    private Player player;

    @Getter
    private List<AchievementQuest> achievementList;

    public Achievement(Player player) {
        this.player = player;
        this.achievementList = new ArrayList<>();
    }

    public void add(AchievementQuest achievement) {
        this.achievementList.add(achievement);
    }

    public AchievementQuest get(int index) {
        return index >= 0 && index < achievementList.size() ? achievementList.get(index) : null;
    }

    public long getCompleted(int index) {
        AchievementQuest aq = get(index);
        if (aq != null) {
            switch (index) {
                case 0:
                case 1:
                case 16:
                    aq.completed = player.nPoint.power;
                    break;
                case 2:
                    aq.completed = player.magicTree.level;
                    break;
                case ConstAchievement.HOAT_DONG_CHAM_CHI:
                    return aq.completed / (60 * 60 * 1000);
                default:
            }
            return aq.completed;
        }
        return 0;
    }

    public boolean isFinish(int index, long maxCount) {
        AchievementQuest aq = get(index);
        return aq != null && (aq.isRecieve || getCompleted(index) >= maxCount);
    }

    public boolean isRecieve(int index) {
        AchievementQuest aq = get(index);
        return aq != null && aq.isRecieve;
    }

    public boolean canReward(int index) {
        AchievementQuest aq = get(index);
        AchievementTemplate at = Manager.ACHIEVEMENT_TEMPLATE.get(index);
        return aq != null && !aq.isRecieve && getCompleted(index) >= at.maxCount;
    }

    public void done(int index, long completed) {
        if (index >= 0 && index < achievementList.size()) {
            achievementList.set(index, new AchievementQuest(get(index).completed + completed, get(index).isRecieve));
        }
    }

    public void doneNotAdd(int index, long completed) {
        if (index >= 0 && index < achievementList.size()) {
            achievementList.set(index, new AchievementQuest(completed, get(index).isRecieve));
        }
    }

    public void reward(int index) {
        if (index >= 0 && index < achievementList.size()) {
            achievementList.set(index, new AchievementQuest(get(index).completed, true));
        }
    }

    public void dispose() {
        if (achievementList != null) {
            achievementList.clear();
            achievementList = null;
        }
        player = null;
    }

}
