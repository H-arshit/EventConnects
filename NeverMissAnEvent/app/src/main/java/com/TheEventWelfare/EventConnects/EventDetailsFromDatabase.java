package com.TheEventWelfare.EventConnects;

public class EventDetailsFromDatabase {

    private boolean taskdone;
    private int id;
    private String title , type , venue , price , dates , timing , organizer , contact , emailid , weblink , directlink , description , upcomingpicpath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTaskdone() {
        return taskdone;
    }

    public String getDates() {
        return dates;
    }

    public String getContact() {
        return contact;
    }

    public String getDirectlink() {
        return directlink;
    }

    public String getDescription() {
        return description;
    }

    public String getEmailid() {
        return emailid;
    }

    public String getOrganizer() {
        return organizer;
    }

    public String getPrice() {
        return price;
    }

    public String getTiming() {
        return timing;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUpcomingpicpath() {
        return upcomingpicpath;
    }

    public String getVenue() {
        return venue;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirectlink(String directlink) {
        this.directlink = directlink;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTaskdone(boolean taskdone) {
        this.taskdone = taskdone;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUpcomingpicpath(String upcomingpicpath) {
        this.upcomingpicpath = upcomingpicpath;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }
}

