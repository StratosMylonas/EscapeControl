package com.str.escapecontrol;

public class PhilosopherRoomStatus {

    public final int numberOfButtons = 9;
    private final String[] strings = new String[numberOfButtons];

    public PhilosopherRoomStatus(String[] strings){
        System.arraycopy(strings, 0, this.strings, 0, numberOfButtons);
    }

    public String getBtnString(int i){
        return strings[i];
    }
}
