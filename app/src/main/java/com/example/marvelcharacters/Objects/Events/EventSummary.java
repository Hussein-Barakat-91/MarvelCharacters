package com.example.marvelcharacters.Objects.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventSummary {
    public String resourceURI;
    public String name;
}
