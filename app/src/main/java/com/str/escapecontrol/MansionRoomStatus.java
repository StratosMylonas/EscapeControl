package com.str.escapecontrol;

public class MansionRoomStatus {
    private final String mirror_drawer, mirror_cabinet, living_room_door, kids_room_door,
                         passage, window_doors, tarrot_combination_1_btn, tarrot_combination_2_btn,
                         tarrot_combination_3_btn, radio_on_btn, doll_btn, shelf_1_btn, shelf_2_btn,
                         window_doors_btn, exit_btn, reset_btn;

    public MansionRoomStatus(String mirror_drawer, String mirror_cabinet, String living_room_door,
                             String kids_room_door, String passage, String window_doors,
                             String tarrot_combination_1_btn, String tarrot_combination_2_btn,
                             String tarrot_combination_3_btn, String radio_on_btn, String doll_btn,
                             String shelf_1_btn, String shelf_2_btn, String window_doors_btn, String exit_btn, String reset_btn){
        this.mirror_drawer = mirror_drawer;
        this.mirror_cabinet = mirror_cabinet;
        this.living_room_door = living_room_door;
        this.kids_room_door = kids_room_door;
        this.passage = passage;
        this.window_doors = window_doors;
        this.tarrot_combination_1_btn = tarrot_combination_1_btn;
        this.tarrot_combination_2_btn = tarrot_combination_2_btn;
        this.tarrot_combination_3_btn = tarrot_combination_3_btn;
        this.radio_on_btn = radio_on_btn;
        this.doll_btn = doll_btn;
        this.shelf_1_btn = shelf_1_btn;
        this.shelf_2_btn = shelf_2_btn;
        this.window_doors_btn = window_doors_btn;
        this.exit_btn = exit_btn;
        this.reset_btn = reset_btn;
    }

    public String getMirror_drawer() {
        return mirror_drawer;
    }

    public String getMirror_cabinet() {
        return mirror_cabinet;
    }

    public String getLiving_room_door() {
        return living_room_door;
    }

    public String getKids_room_door() {
        return kids_room_door;
    }

    public String getPassage() {
        return passage;
    }

    public String getWindow_doors() {
        return window_doors;
    }

    public String getTarrot_combination_1_btn() {
        return tarrot_combination_1_btn;
    }

    public String getTarrot_combination_2_btn() {
        return tarrot_combination_2_btn;
    }

    public String getTarrot_combination_3_btn() {
        return tarrot_combination_3_btn;
    }

    public String getRadio_on_btn() {
        return radio_on_btn;
    }

    public String getDoll_btn() {
        return doll_btn;
    }

    public String getShelf_1_btn() {
        return shelf_1_btn;
    }

    public String getShelf_2_btn() {
        return shelf_2_btn;
    }

    public String getWindow_doors_btn() {
        return window_doors_btn;
    }

    public String getExit_btn() {
        return exit_btn;
    }

    public String getReset_btn() {
        return reset_btn;
    }
}
