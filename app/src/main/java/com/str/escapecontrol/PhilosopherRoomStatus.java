package com.str.escapecontrol;

public class PhilosopherRoomStatus {

    private final String coins_prop_btn, harp_btn, passage_btn, knight_video_btn, holding_keys_door_btn,
                         books_open_btn, books_close_btn, mirror_btn, exit_btn, reset_btn;

    public PhilosopherRoomStatus(String coins_prop_btn, String harp_btn, String passage_btn, String knight_video_btn,
                                 String holding_keys_door_btn, String books_open_btn, String books_close_btn,
                                 String mirror_btn, String exit_btn, String reset_btn){
        this.coins_prop_btn = coins_prop_btn;
        this.harp_btn = harp_btn;
        this.passage_btn = passage_btn;
        this.knight_video_btn = knight_video_btn;
        this.holding_keys_door_btn = holding_keys_door_btn;
        this.books_open_btn = books_open_btn;
        this.books_close_btn = books_close_btn;
        this.mirror_btn = mirror_btn;
        this.exit_btn = exit_btn;
        this.reset_btn = reset_btn;
    }

    public String getCoins_prop_btn() {
        return coins_prop_btn;
    }

    public String getHarp_btn() {
        return harp_btn;
    }

    public String getPassage_btn() {
        return passage_btn;
    }

    public String getKnight_video_btn() {
        return knight_video_btn;
    }

    public String getHolding_keys_door_btn() {
        return holding_keys_door_btn;
    }

    public String getBooks_open_btn() {
        return books_open_btn;
    }

    public String getBooks_close_btn() {
        return books_close_btn;
    }

    public String getMirror_btn() {
        return mirror_btn;
    }

    public String getExit_btn() {
        return exit_btn;
    }

    public String getReset_btn() {
        return reset_btn;
    }
}
