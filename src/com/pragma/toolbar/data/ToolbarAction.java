/*
 * ToolbarAction.java
 *
 * Created on 2 de noviembre de 2006, 17:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.data;

/**
 *
 * @author fferrara
 */
public class ToolbarAction {
        
    private String name;
    
    /** Creates a new instance of ToolbarAction */
    public ToolbarAction(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
