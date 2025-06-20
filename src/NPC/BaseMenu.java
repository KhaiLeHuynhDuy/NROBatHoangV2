package NPC;

import Player.Player;
import network.Message;
import java.io.IOException;

public class BaseMenu {

    public int npcId;

    public String npcSay;

    public String[] menuSelect;

    public void openMenu(Player player) {
        Message msg;
        try {
            msg = new Message(32);
            msg.writer().writeShort(npcId);
            msg.writer().writeUTF(npcSay);
            msg.writer().writeByte(menuSelect.length);
            for (String menu : menuSelect) {
                msg.writer().writeUTF(menu);
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (IOException e) {
        }
    }
}
