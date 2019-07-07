/*
 * ToolbarButton.java
 *
 * Created on 2 de noviembre de 2006, 18:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.data;

/**
 *
 * @author fferrara
 */
public class ToolbarButton {
    
    public static final int ColumnsVisibility = 0;
    public static final int ColumnsFilters = 1;
    public static final int ColumnsOrders = 2;
    public static final int ExportPDF = 3;
    public static final int ExportExcel = 4;
    public static final int ExportTXT = 5;
    
    private int id;
    
    /** Creates a new instance of ToolbarButton */
    public ToolbarButton(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
}
