package com.example.marvelcharacters.Responses;

import com.example.marvelcharacters.Objects.ComicDataContainer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMarvelCharacterComicsResponse {
    public ComicDataContainer data;
}
