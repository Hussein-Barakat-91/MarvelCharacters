package com.example.marvelcharacters.Responses;

import com.example.marvelcharacters.Objects.CharacterDataContainer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllMarvelCharactersResponse {
    public int code;
    public String status;
    public String attributionText;
    public CharacterDataContainer data;
}
