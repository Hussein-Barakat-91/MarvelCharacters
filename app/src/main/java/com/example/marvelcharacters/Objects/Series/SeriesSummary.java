package com.example.marvelcharacters.Objects.Series;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriesSummary {
    public String resourceURI;
    public String name;
}
