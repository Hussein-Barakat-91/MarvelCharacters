package com.example.marvelcharacters.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoURLs {
    public String type;
    public String url;
}
