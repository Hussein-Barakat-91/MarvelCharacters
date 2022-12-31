package com.example.marvelcharacters.Objects.Stories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StorySummary {
    public String resourceURI;
    public String name;
    public String type;
}
