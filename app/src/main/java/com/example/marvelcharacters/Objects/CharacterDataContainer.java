package com.example.marvelcharacters.Objects;

import com.example.marvelcharacters.Objects.CharacterInfo.Character;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterDataContainer {
    public int count;
    public List<Character> results;
}
