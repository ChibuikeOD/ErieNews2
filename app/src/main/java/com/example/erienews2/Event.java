package com.example.erienews2;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Event implements Serializable

{
    private String userID;
    private String name;
    private String eventDate;
    private String startTime;
    private String endTime;

    private String desription;
    private String address;

    private Account[] attendees;

    public Event()
    {
        userID = UUID.randomUUID().toString(); //generates user id on create
    }
    public void setName(String nName)
    {
        name = nName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getName()
    {
        return this.name;
    }

    public String getEventDate()
    {
        return this.eventDate;
    }

    public String getStartTime()
    {
        return this.startTime;
    }

    public String getEndTime()
    {
        return this.endTime;
    }

    public String getUserID()
    {
        return this.userID;
    }

    public String getDesription()
    {
        return this.desription;
    }
}

