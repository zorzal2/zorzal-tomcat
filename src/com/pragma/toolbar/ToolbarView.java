/*
 * ToolbarView.java
 *
 * Created on 2 de noviembre de 2006, 18:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar;

import java.util.Collection;

import com.pragma.toolbar.config.user_config.UserToolbarConfiguration;

/**
 *
 * @author fferrara
 */
public class ToolbarView {
    
    private Collection columns;
    private Collection buttons;
    private Collection actions;
    private String decoratorClass;
    private String actionsTitle;
    private String styleClass;
    
    //private String idUser;
    //private String idToolbar;
        
    /** Creates a new instance of ToolbarView */
    public ToolbarView(String idUser, String idToolbar) {
        
        ///this.idUser = idUser;
        ///this.idToolbar = idToolbar;
        
        UserToolbarConfiguration toolbarConfig = UserToolbarConfiguration.get(idUser, idToolbar);
        
        columns = toolbarConfig.getColumns();
        buttons = toolbarConfig.getButtons();
        actions = toolbarConfig.getActions();
        decoratorClass = toolbarConfig.getDecorator();
        actionsTitle = toolbarConfig.getActionsTitle();
        styleClass = toolbarConfig.getStyleClass();
    }

    public Collection getColumns() {
        return columns;
    }

    public void setColumns(Collection columns) {
        this.columns = columns;
    }

    public Collection getButtons() {
        return buttons;
    }

    public void setButtons(Collection buttons) {
        this.buttons = buttons;
    }

    public Collection getActions() {
        return actions;
    }

    public void setActions(Collection actions) {
        this.actions = actions;
    }

    public String getDecoratorClass() {
        return decoratorClass;
    }

    public void setDecoratorClass(String decoratorClass) {
        this.decoratorClass = decoratorClass;
    }
    
    public String getActionsTitle() {
        return actionsTitle;
    }

    public void setActionsTitle(String actionsTitle) {
        this.actionsTitle = actionsTitle;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
}



