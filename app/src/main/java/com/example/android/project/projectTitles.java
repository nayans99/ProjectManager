package com.example.android.project;

public class projectTitles {
    private String dname,desc,lead,stat,date;

    public projectTitles(String dname, String desc, String lead, String stat, String date) {
        this.dname = dname;
        this.desc = desc;
        this.lead = lead;
        this.stat = stat;
        this.date = date;
    }
    public projectTitles(String title, String dname, String desc, String stat) {
        this.dname = dname;
        this.desc = desc;

        this.lead = title;
        this.stat = stat;
    }

    public projectTitles(String title, String dname) {
        this.dname = title;
        this.desc = dname;
    }

    public String getDname() {
        return dname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
