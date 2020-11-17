package com.str.escapecontrol;

import android.widget.CompoundButton;

public class MySwitchOnChangeStateListener implements CompoundButton.OnCheckedChangeListener {

    int index;

    public MySwitchOnChangeStateListener(int index){
        this.index = index;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}
