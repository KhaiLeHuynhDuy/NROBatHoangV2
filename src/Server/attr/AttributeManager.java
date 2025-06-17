/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.attr;

import Utils.Util;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author Administrator
 */
public class AttributeManager {

    @Getter
    private List<Attribute> attributes;
    private long lastUpdate;

    public AttributeManager() {
        this.attributes = new ArrayList<>();
    }

    public void add(Attribute at) {
        synchronized (attributes) {
            attributes.add(at);
        }
    }

    public void remove(Attribute at) {
        synchronized (attributes) {
            attributes.remove(at);
        }
    }

    public Attribute find(int templateID) {
        synchronized (attributes) {
            for (Attribute at : attributes) {
                if (at.getTemplate().getId() == templateID) {
                    return at;
                }
            }
        }
        return null;
    }

    public void update() {
        if (Util.canDoWithTime(lastUpdate, 1000)) {
            lastUpdate = System.currentTimeMillis();
            synchronized (attributes) {
                for (Attribute at : attributes) {
                    try {
                        if (!at.isExpired()) {
                            at.update();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean setTime(int templateID, int time) {
        Attribute attr = find(templateID);
        if (attr != null) {
            attr.setTime(time);
            return true;
        }
        return false;
    }

    public boolean setValue(int templateID, int value) {
        Attribute attr = find(templateID);
        if (attr != null) {
            try {
                if (value >= 1) {
                    attr.setValue(value);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
