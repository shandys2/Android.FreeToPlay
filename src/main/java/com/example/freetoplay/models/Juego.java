package com.example.freetoplay.models;

import java.util.ArrayList;

public class Juego {
    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
    public Root root;
    Screenshot screenshot;
    MinimumSystemRequirements minimumSystemRequirements;

    public Juego(){
        this.root=new Root();
    }


    public class MinimumSystemRequirements{
        public String os;
        public String processor;
        public String memory;
        public String graphics;
        public String storage;
    }

    public class Root{
        public  int id;
        public String title;
        public String thumbnail;
        public String status;
        public String short_description;
        public String description;
        public String game_url;
        public String genre;
        public String platform;
        public String publisher;
        public String developer;
        public String release_date;
        public String freetogame_profile_url;
        public MinimumSystemRequirements minimum_system_requirements = new MinimumSystemRequirements();
        public ArrayList<Screenshot> screenshots =new ArrayList<>();
    }

    public static class Screenshot{
        public int id;
        public String image;
    }

}
