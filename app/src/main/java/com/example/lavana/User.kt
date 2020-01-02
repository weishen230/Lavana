package com.example.lavana

class User(val userKey : String, val username : String, val password : String, val email : String, val accType : String, val profilePic : String,
           val name : String, val gender : String, val phone : String, val fee : String, val bio : String, val coachSport : String, val teachDay : String, val teachTime : String)
{
    constructor() : this("","","", "", "", "",
        "", "", "", "", "", "", "", "")
    {

    }
}