package com.tatva.tatvaadventure.model;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Model Class for Detail Events
 */
public class EventDetail {

    private int id = -1;
    private String title = null;
    private String place = null;
    private String time = null;
    private String description = null;
    private Bitmap image = null;
    private String img = null;

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        if (title == null) {
            this.title = "";
        }
        this.title = new String(Base64.decode(title, Base64.DEFAULT));
    }

    public String getTitle() {
        return this.title;
    }

    /**
     * @param place
     */
    public void setPlace(String place) {
        this.place = new String(Base64.decode(place, Base64.DEFAULT));
    }

    public String getPlace() {
        return this.place;
    }

    /**
     * @param time
     */
    public void setTime(String time) {
        if (time == null) {
            this.title = "";
        }
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        if (description == null) {
            this.description = null;
            return;
        }
        this.description = new String(Base64.decode(description, Base64.DEFAULT));
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * @param image
     */
    public void setImage(String image) {
        this.img = image;
        if (image == null) {
            this.image = null;
            return;
        } else {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            this.image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return this.image;
    }

    public void saveImage(Activity activity) {
        SharedPreferences.Editor se = activity.getSharedPreferences("images", Context.MODE_PRIVATE).edit();
        se.putString("" + id, img);
        se.commit();
    }

    public void loadBitmap(Activity activity) {
        SharedPreferences s = activity.getSharedPreferences("images", Context.MODE_PRIVATE);
        this.img = s.getString("" + id, "");
        if (this.img.isEmpty()) {
            this.img = null;
            this.image = null;
        } else {
            setImage(this.img);
        }
    }
}
