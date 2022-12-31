package com.example.marvelcharacters.Objects.CharacterInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Thumbnail {
    public String path;
    public String extension ;
}
