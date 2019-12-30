package com.aymentlili.aamoomor.Entitys;

public class Biding {
    public String Username;
    public String House_name;
    public String the_bid;
    public String image_url;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getHouse_name() {
        return House_name;
    }

    public void setHouse_name(String house_name) {
        House_name = house_name;
    }

    public String getThe_bid() {
        return the_bid;
    }

    public void setThe_bid(String the_bid) {
        this.the_bid = the_bid;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Biding() {
    }

    public Biding(String username, String house_name, String the_bid, String image_url) {
        Username = username;
        House_name = house_name;
        this.the_bid = the_bid;
        this.image_url = image_url;
    }
}
