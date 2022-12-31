package com.example.marvelcharacters.Objects.CharacterInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoURLs {
    public String type;
    public String url;
}
