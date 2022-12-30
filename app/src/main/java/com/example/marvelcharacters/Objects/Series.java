package com.example.marvelcharacters.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Series {
    public int available;
    public int returned;
    public String collectionURI;
    public List<SeriesSummary> items;
}
