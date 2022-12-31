package com.example.marvelcharacters.Objects.Comics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComicSummary {
    public String resourceURI;
    public String name;
}
