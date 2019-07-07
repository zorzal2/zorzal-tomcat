/*
 * Created on November 14, 2006, 1:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.data.bean;

import com.pragma.util.interfaces.Bean;


/**
 * @author llobeto
 */
public class UserConfigItemBean implements Bean {

    private static final long serialVersionUID = 1L;

    private Long id; 
    private String uri;
    private String userId;
    private VariantBean value;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public VariantBean getValue() {
        return value;
    }
    public void setValue(VariantBean value) {
        this.value = value;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

}