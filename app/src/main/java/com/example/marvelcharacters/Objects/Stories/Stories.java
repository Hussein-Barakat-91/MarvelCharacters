package com.example.marvelcharacters.Objects.Stories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stories {
    public int available;
    public int returned;
    public String collectionURI;
    public List<StorySummary> items;
}
