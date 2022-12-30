package com.example.marvelcharacters.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StorySummary {
    public String resourceURI;
    public String name;
    public String type;
}
