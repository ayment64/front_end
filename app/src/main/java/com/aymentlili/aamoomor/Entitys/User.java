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

public class User {
    public String Description;
    public String Email;
    public String First_name;
    public String Job;
    public String Name;
    public String Password;
    public String Phone_Number;
    public String Username;
    public String image;

    public User() {
    }

    public User(String string2, String string3, String string4, String string5) {
        this.Username = string2;
        this.Password = string3;
        this.First_name = string4;
        this.Name = string5;
    }

    public User(String string2, String string3, String string4, String string5, String string6) {
        this.Username = string2;
        this.Password = string3;
        this.First_name = string4;
        this.Name = string5;
        this.Email = string6;
    }

    public User(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10) {
        this.Username = string2;
        this.Password = string3;
        this.First_name = string4;
        this.Name = string5;
        this.Email = string6;
        this.Phone_Number = string7;
        this.Job = string8;
        this.Description = string9;
        this.image = string10;
    }

    public String getDescription() {
        return this.Description;
    }

    public String getEmail() {
        return this.Email;
    }

    public String getFirst_name() {
        return this.First_name;
    }

    public String getImage() {
        return this.image;
    }

    public String getJob() {
        return this.Job;
    }

    public String getName() {
        return this.Name;
    }

    public String getPassword() {
        return this.Password;
    }

    public String getPhone_Number() {
        return this.Phone_Number;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setDescription(String string2) {
        this.Description = string2;
    }

    public void setEmail(String string2) {
        this.Email = string2;
    }

    public void setFirst_name(String string2) {
        this.First_name = string2;
    }

    public void setImage(String string2) {
        this.image = string2;
    }

    public void setJob(String string2) {
        this.Job = string2;
    }

    public void setName(String string2) {
        this.Name = string2;
    }

    public void setPassword(String string2) {
        this.Password = string2;
    }

    public void setPhone_Number(String string2) {
        this.Phone_Number = string2;
    }

    public void setUsername(String string2) {
        this.Username = string2;
    }

    public String toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("job", (Object)this.Job);
            jSONObject.put("Username", (Object)this.Username);
            jSONObject.put("Password", (Object)this.Password);
            jSONObject.put("FirstName", (Object)this.First_name);
            jSONObject.put("Name", (Object)this.Name);
            jSONObject.put("Email", (Object)this.Email);
            jSONObject.put("phone_number", (Object)this.Phone_Number);
            jSONObject.put("description", (Object)this.Description);
            String string2 = jSONObject.toString();
            return string2;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            return "";
        }
    }
}

