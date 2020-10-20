package com.str.escapecontrol;

public class AtlantisRoomStatus {

    private String  tritons_key_status, room_door_1_2_status, holy_molly_status, poseidons_chest_status,
                    hexagon_cabinet_status;

    public AtlantisRoomStatus(String tritons_key_status, String room_door_1_2_status, String holy_molly_status,
                              String poseidons_chest_status, String hexagon_cabinet_status){
        this.tritons_key_status = tritons_key_status;
        this.room_door_1_2_status = room_door_1_2_status;
        this.holy_molly_status = holy_molly_status;
        this.poseidons_chest_status = poseidons_chest_status;
        this.hexagon_cabinet_status = hexagon_cabinet_status;
    }

    public void setTritons_key_status(String tritons_key_status){
        this.tritons_key_status = tritons_key_status;
    }

    public String getTritons_key_status() {
        return tritons_key_status;
    }

    public void setRoom_door_1_2_status(String room_door_1_2_status) {
        this.room_door_1_2_status = room_door_1_2_status;
    }

    public String getRoom_door_1_2_status() {
        return room_door_1_2_status;
    }

    public void setHoly_molly_status(String holy_molly_status) {
        this.holy_molly_status = holy_molly_status;
    }

    public String getHoly_molly_status() {
        return holy_molly_status;
    }

    public void setPoseidons_chest_status(String poseidons_chest_status) {
        this.poseidons_chest_status = poseidons_chest_status;
    }

    public String getPoseidons_chest_status() {
        return poseidons_chest_status;
    }

    public void setHexagon_cabinet_status(String hexagon_cabinet_status) {
        this.hexagon_cabinet_status = hexagon_cabinet_status;
    }

    public String getHexagon_cabinet_status() {
        return hexagon_cabinet_status;
    }
}