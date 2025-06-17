/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.attr;

import com.girlkun.database.GirlkunDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdk.internal.org.jline.utils.Log;

/**
 *
 * @author Administrator
 */
public class AttributeTemplateManager {

    private static final AttributeTemplateManager instance = new AttributeTemplateManager();

    public static AttributeTemplateManager getInstance() {
        return instance;
    }

    private final List<AttributeTemplate> list = new ArrayList<>();

    public void load() {
        try {
            try (PreparedStatement ps = GirlkunDB.getConnection().prepareStatement("SELECT * FROM `attribute_template`"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    AttributeTemplate at = AttributeTemplate.builder()
                            .id(id)
                            .name(name)
                            .build();
                    add(at);
                }
            }
        } catch (SQLException ex) {
            Log.error(AttributeTemplateManager.class, ex, "Load attribute template err");
        }
    }

    public void add(AttributeTemplate at) {
        list.add(at);
    }

    public void remove(AttributeTemplate at) {
        list.remove(at);
    }

    public AttributeTemplate find(int id) {
        for (AttributeTemplate at : list) {
            if (at.getId() == id) {
                return at;
            }
        }
        return null;
    }
}
