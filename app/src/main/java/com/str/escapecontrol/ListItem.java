package com.str.escapecontrol;

import androidx.appcompat.widget.SwitchCompat;

public class ListItem {
    private String attribute;
    private final String status;

    public ListItem(String attribute, String status){
        this.attribute = attribute;
        this.status = status;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getStatus() {
        return status;
    }
}
