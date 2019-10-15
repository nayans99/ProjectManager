package com.example.android.project;

public class note {
    private String Title;
    private String Description;
    private String Date;

    public note()
    {}


    public note(String Title,String Description,String Date){
            this.Date=Title;
            this.Description=Description;
            this.Date = Date;
    };

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
