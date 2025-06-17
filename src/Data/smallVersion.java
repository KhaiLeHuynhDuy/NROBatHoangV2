/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import Server.MySession;
import Utils.FileIO;
import network.Message;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author Administrator
 */
public class smallVersion {

    public static byte[][] smallVersion;

    public static void get() {
        try {
            smallVersion = new byte[4][];
            File[] files;
            int max = -1;
            int id;
            for (int i = 0; i < 4; i++) {
                files = new File("data/girlkun/icon/x" + (i + 1)).listFiles();
                for (File file : files) {
                    if ((id = Integer.parseInt(FileIO.replacePng(file.getName()))) > max) {
                        max = id;
                    }
                }
                smallVersion[i] = new byte[max + 1];
                for (File file : files) {
                    smallVersion[i][Integer.parseInt(FileIO.replacePng(file.getName()))] = (byte) (Files.readAllBytes(file.toPath()).length % 127);
                }
            }
        } catch (IOException ex) {
        }
    }

    public static void send(MySession session) {
        try {
            byte[] data = smallVersion[session.zoomLevel - 1];
            Message msg = new Message(-77);
            msg.writer().writeShort(data.length);
            msg.writer().write(data);
            msg.writer().flush();
            session.sendMessage(msg);
            msg.cleanup();
        } catch (IOException ex) {
        }
    }
}

