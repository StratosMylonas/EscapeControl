package com.str.escapecontrol;

public class MansionRoomStatus {

    public final int numberOfButtons = 9;
    private final String[] strings = new String[numberOfButtons];

    public MansionRoomStatus(String[] strings){
        System.arraycopy(strings, 0, this.strings, 0, numberOfButtons);
    }

    public String getBtnString(int i){
        return strings[i];
    }
}
