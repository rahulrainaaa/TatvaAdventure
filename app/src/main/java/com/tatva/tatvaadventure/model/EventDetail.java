package com.tatva.tatvaadventure.model;

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
     *
     * @param id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return this.title;
    }

    /**
     *
     * @param place
     */
    public void setPlace(String place)
    {
        this.place = place;
    }

    public String getPlace()
    {
        return this.place;
    }

    /**
     *
     * @param time
     */
    public void setTime(String time)
    {
        this.time = time;
    }

    public String getTime()
    {
        return this.time;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return this.description;
    }

}
