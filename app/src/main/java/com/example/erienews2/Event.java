package com.example.erienews2;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class Event implements Serializable

{
    private String userID;
    private String name;
    private String eventOrganizerUsername;
    private String eventDate;
    private Date startTime;
    private Date endTime;

    private String description;
    private String address;

    private Account[] attendees;

    public Event()
    {
        userID = UUID.randomUUID().toString(); //generates user id on create
    }
    public Event(String userID) { this.userID = userID;}
    public void setName(String nName)
    {
        name = nName;
    }
    public void setEventOrganizerUsername(String username){eventOrganizerUsername = username;}

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getName()
    {
        return this.name;
    }
    public String getEventOrganizerUsername(){return eventOrganizerUsername;}

    public String getAddress(){ return this.address; }

    public String getEventDate()
    {
        return this.eventDate;
    }

    public Date getStartTime()
    {
        return this.startTime;
    }

    public Date getEndTime()
    {
        return this.endTime;
    }

    public String getUserID()
    {
        return this.userID;
    }

    public String getDescription()
    {
        return this.description;
    }

    public Account[] getAttendees() {
        return attendees;
    }
}

