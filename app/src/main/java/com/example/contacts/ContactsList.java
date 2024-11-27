package com.example.contacts;

import android.graphics.Bitmap;

import java.sql.Blob;

public class ContactsList {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    Bitmap img;
    String name;
    String number;
    public ContactsList(int id, Bitmap img, String name, String number) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.number = number;
    }

    public ContactsList(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public ContactsList(Bitmap img, String name, String number){
        this.img = img;
        this.name = name;
        this.number = number;
    }

    public ContactsList() {
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
