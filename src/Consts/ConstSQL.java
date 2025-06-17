/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Consts;

/**
 *
 * @author Administrator
 */
public class ConstSQL {

    public static final String TOP_WHIS = "SELECT name, player.id, gender, items_body, CAST( JSON_EXTRACT(data_luyentap, '$[5]') AS UNSIGNED) AS top, CAST( JSON_EXTRACT(data_luyentap, '$[6]') AS UNSIGNED) AS time, CAST( JSON_EXTRACT(data_luyentap, '$[7]') AS UNSIGNED) AS lasttime FROM player INNER JOIN account ON account.id = player.account_id WHERE account.ban = 0 AND CAST( JSON_EXTRACT(data_luyentap, '$[5]') AS UNSIGNED) > 0 ORDER BY CAST( JSON_EXTRACT(data_luyentap, '$[5]') AS UNSIGNED) DESC, CAST( JSON_EXTRACT(data_luyentap, '$[6]') AS UNSIGNED) ASC LIMIT 100;";
    public static final String TOP_3_WHIS = "SELECT name, id, gender, items_body, CAST( JSON_EXTRACT(data_luyentap, '$[5]') AS UNSIGNED) AS top, CAST( JSON_EXTRACT(data_luyentap, '$[6]') AS UNSIGNED) AS time, CAST( JSON_EXTRACT(data_luyentap, '$[7]') AS UNSIGNED) AS lasttime FROM player INNER JOIN account ON account.id = player.account_id WHERE account.ban = 0 AND CAST( JSON_EXTRACT(data_luyentap, '$[5]') AS UNSIGNED) > 0 ORDER BY CAST( JSON_EXTRACT(data_luyentap, '$[5]') AS UNSIGNED) DESC, CAST( JSON_EXTRACT(data_luyentap, '$[6]') AS UNSIGNED) ASC LIMIT 3;";
}
