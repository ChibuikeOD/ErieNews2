package com.example.erienews2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Account implements Serializable
{
    private String userID;
    private String username;
    private String password;

    private List<Account> friendsList;

    public Account()
    {
        userID = UUID.randomUUID().toString(); //generates user id on create
        friendsList = new ArrayList<Account>();

    }

     void setUsername(String name)
     {
         username = name;
     }

     void setPassword(String pass)
     {
         password = pass;
     }

     public String getUserID()
     {
         return this.userID;
     }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void addFriend(Account friend)
    {
        this.friendsList.add(friend);
    }

    public void removeFriend(Account friend)
    {
        friendsList.remove(friend);

    }

    public List<Account> getFriendsList()
    {
        return friendsList;
    }

    public void setFriendsList(List<Account> friendsList) {
        this.friendsList = friendsList;
    }
}
