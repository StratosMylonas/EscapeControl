package com.str.escapecontrol;

public class MissionRoomStatus {

    public final int numberOfButtons = 16;
    private final String[] strings = new String[numberOfButtons];

    public MissionRoomStatus(String[] strings){
        System.arraycopy(strings, 0, this.strings, 0, numberOfButtons);
    }

    public String getBtnString(int i){
        return strings[i];
    }

}
