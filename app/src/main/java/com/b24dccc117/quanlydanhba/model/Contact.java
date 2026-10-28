package com.b24dccc117.quanlydanhba.model;

import com.b24dccc117.quanlydanhba.R;
import java.io.Serializable;
import java.util.UUID;

public class Contact implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String group;
    private String gender;
    private boolean favorite;
    private String notes;

    public Contact() {
        this.id = UUID.randomUUID().toString();
    }

    public Contact(String name, String phone, String group, String gender, boolean favorite, String notes) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.group = group;
        this.gender = gender;
        this.favorite = favorite;
        this.notes = notes;
    }

    public Contact(String id, String name, String phone, String group, String gender, boolean favorite, String notes) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.group = group;
        this.gender = gender;
        this.favorite = favorite;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone != null ? phone : "";
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroup() {
        return group != null ? group : "Khác";
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGender() {
        return gender != null ? gender : "Nam";
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getNotes() {
        return notes != null ? notes : "";
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getInitialLetter() {
        if (name == null || name.trim().isEmpty()) {
            return "?";
        }
        String[] parts = name.trim().split("\\s+");
        String lastWord = parts[parts.length - 1];
        if (lastWord.isEmpty()) return "?";
        return lastWord.substring(0, 1).toUpperCase();
    }

    public int getAvatarColorResId() {
        if (name == null || name.trim().isEmpty()) {
            return R.color.avatar_blue;
        }
        char ch = getInitialLetter().charAt(0);
        switch (ch) {
            case 'A':
                return R.color.avatar_blue;
            case 'B':
                return R.color.avatar_cyan;
            case 'C':
                return R.color.avatar_indigo;
            case 'D':
            case 'Đ':
                return R.color.avatar_teal;
            default:
                return R.color.avatar_orange;
        }
    }
}
