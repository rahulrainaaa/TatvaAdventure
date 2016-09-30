package com.tatva.tatvaadventure.model;


import org.apache.commons.codec.binary.Base64;

/**
 * Model Class for Detail Events
 */
public class EventDetail {

    private int id = -1;
    private String title = null;
    private String place = null;
    private String time = null;
    private String description = null;

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
        this.title = new String(Base64.decodeBase64(title.getBytes()));
    }

    public String getTitle() {
        return this.title;
    }

    /**
     * @param place
     */
    public void setPlace(String place) {
        this.place = new String(Base64.decodeBase64(place.getBytes()));
    }

    public String getPlace() {
        return this.place;
    }

    /**
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = new String(Base64.decodeBase64(description.getBytes()));
    }

    public String getDescription() {
        return this.description;
    }

}
