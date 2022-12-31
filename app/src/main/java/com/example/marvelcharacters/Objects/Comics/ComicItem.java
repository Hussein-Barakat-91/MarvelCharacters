package com.example.marvelcharacters.Objects.Comics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComicItem {
    public int id;
    public String title;
    public String description;
}
