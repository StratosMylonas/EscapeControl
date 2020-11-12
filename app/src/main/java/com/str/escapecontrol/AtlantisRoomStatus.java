package com.str.escapecontrol;

public class AtlantisRoomStatus {

    private final int numberOfButtons = 11;
    private final String[] strings = new String[numberOfButtons];

    public AtlantisRoomStatus(String[] strings){
        System.arraycopy(strings, 0, this.strings, 0, numberOfButtons);
    }

    public String getBtnString(int i){
        return strings[i];
    }
}