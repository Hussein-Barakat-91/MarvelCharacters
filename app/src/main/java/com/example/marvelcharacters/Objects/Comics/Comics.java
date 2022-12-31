package com.example.marvelcharacters.Objects.Comics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comics {
    public int available;
    public int returned;
    public String collectionURI;
    public List<ComicSummary> items;
}
