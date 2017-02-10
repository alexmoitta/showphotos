package com.yourdomain.showphotos.model;

/**
 * Created by asantos on 25/03/15.
 */
public class Image
{
    private String title;
    private String details;
    private String photo; //url
    private String credit;
    private String thumb;

    public Image()
    {
    }

    public Image(String title, String details, String photo, String credit, String thumb)
    {
        this.title = title;
        this.details = details;
        this.photo = photo;
        this.credit = credit;
        this.thumb = thumb;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public void setCredit(String credit)
    {
        this.credit = credit;
    }

    public void setThumb(String thumb)
    {
        this.thumb = thumb;
    }

    public String getTitle()
    {
        return title;
    }

    public String getPhoto()
    {
        return photo;
    }

    public String getCredit()
    {
        return credit;
    }
}
