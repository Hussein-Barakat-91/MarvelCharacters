package com.example.marvelcharacters.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
    public int id;
    public String name;
    public String description;
    public String resourceURI;
    public List<InfoURLs> urls;
    public Thumbnail thumbnail;
    public Comics comics;
    public Stories stories;
    public Events events;
    public Series series;
}
