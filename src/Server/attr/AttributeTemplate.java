/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server.attr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
/**
 *
 * @author Administrator
 */
@Getter
@Builder
@AllArgsConstructor
public class AttributeTemplate {

    private int id;
    private String name;
}