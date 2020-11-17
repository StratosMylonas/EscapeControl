package com.str.escapecontrol;

public class RoomStatus {

    private final String[] strings;

    public RoomStatus(String[] strings, int numberOfButtons){
        this.strings = new String[numberOfButtons+1];
        System.arraycopy(strings, 0, this.strings, 0, numberOfButtons+1);
    }

    public String getBtnString(int i){
        return strings[i];
    }

}