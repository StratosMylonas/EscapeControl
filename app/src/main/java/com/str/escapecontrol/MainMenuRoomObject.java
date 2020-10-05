package com.str.escapecontrol;

public class MainMenuRoomObject {

    private String roomName;
    private String roomStatus;
    private String doorLockState;
    private String relay1State;
    private String relay2State;

    public MainMenuRoomObject(String roomName, String roomStatus, String doorLockState, String relay1State, String relay2State) {
        this.roomName = roomName;
        this.roomStatus = roomStatus;
        this.doorLockState = doorLockState;
        this.relay1State = relay1State;
        this.relay2State = relay2State;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getDoorLockState() {
        return doorLockState;
    }

    public void setDoorLockState(String doorLockState) {
        this.doorLockState = doorLockState;
    }

    public String getRelay1State() {
        return relay1State;
    }

    public void setRelay1State(String relay1State) {
        this.relay1State = relay1State;
    }

    public String getRelay2State() {
        return relay2State;
    }

    public void setRelay2State(String relay2State) {
        this.relay2State = relay2State;
    }
}