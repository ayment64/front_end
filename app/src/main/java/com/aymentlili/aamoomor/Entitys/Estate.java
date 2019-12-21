/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.aymentlili.aamoomor.Entitys;

import org.json.JSONException;
import org.json.JSONObject;

public class Estate {
    public String adress;
    public int bathrooms;
    public int bedrooms;
    public String forr;
    public int gardens;
    public String image;
    public int kitchens;
    public int livingrooms;
    public String name;
    public String owner;
    public String prix;
    public String type;

    public Estate() {
    }

    public Estate(String string2) {
        this.adress = string2;
    }

    public Estate(String string2, String string3, String string4, String string5, int n, int n2, int n3, int n4, int n5, String string6) {
        this.adress = string2;
        this.owner = string3;
        this.type = string4;
        this.forr = string5;
        this.bedrooms = n;
        this.bathrooms = n2;
        this.livingrooms = n3;
        this.kitchens = n4;
        this.gardens = n5;
        this.image = string6;
    }

    public String getAdress() {
        return this.adress;
    }

    public int getBathrooms() {
        return this.bathrooms;
    }

    public int getBedrooms() {
        return this.bedrooms;
    }

    public String getForr() {
        return this.forr;
    }

    public int getGardens() {
        return this.gardens;
    }

    public String getImage() {
        return this.image;
    }

    public int getKitchens() {
        return this.kitchens;
    }

    public int getLivingrooms() {
        return this.livingrooms;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getType() {
        return this.type;
    }

    public void setAdress(String string2) {
        this.adress = string2;
    }

    public void setBathrooms(int n) {
        this.bathrooms = n;
    }

    public void setBedrooms(int n) {
        this.bedrooms = n;
    }

    public void setForr(String string2) {
        this.forr = string2;
    }

    public void setGardens(int n) {
        this.gardens = n;
    }

    public void setImage(String string2) {
        this.image = string2;
    }

    public void setKitchens(int n) {
        this.kitchens = n;
    }

    public void setLivingrooms(int n) {
        this.livingrooms = n;
    }

    public void setOwner(String string2) {
        this.owner = string2;
    }

    public void setType(String string2) {
        this.type = string2;
    }

    public String toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("adresse", (Object)this.adress);
            jSONObject.put("owner", (Object)this.owner);
            jSONObject.put("type", (Object)this.type);
            jSONObject.put("forr", (Object)this.forr);
            jSONObject.put("bedrooms", this.bedrooms);
            jSONObject.put("bathrooms", this.bathrooms);
            jSONObject.put("livingrooms", this.livingrooms);
            jSONObject.put("kitchens", this.kitchens);
            jSONObject.put("gardens", this.gardens);
            jSONObject.put("picture", (Object)this.image);
            jSONObject.put("name", (Object)this.name);
            jSONObject.put("prix", (Object)this.prix);
            String string2 = jSONObject.toString();
            return string2;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            return "";
        }
    }
}

