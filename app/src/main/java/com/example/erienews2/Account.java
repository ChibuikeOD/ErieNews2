package com.example.erienews2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Account implements Serializable
{
    private String userID;
    private String username;
    private String password;

    public Account()
    {
        userID = UUID.randomUUID().toString(); //generates user id on create
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
}
